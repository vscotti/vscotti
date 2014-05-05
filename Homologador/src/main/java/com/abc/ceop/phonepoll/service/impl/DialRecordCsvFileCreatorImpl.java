package com.abc.ceop.phonepoll.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.AutoExcluidoService;
import com.abc.ceop.common.service.CampaignService;
import com.abc.ceop.common.service.EmailSuscriberWebService;
import com.abc.ceop.common.service.QuestionnaireService;
import com.abc.ceop.common.service.QuienRespondeLaEncuestaService;
import com.abc.ceop.common.service.RecordDetailService;
import com.abc.ceop.common.service.RspstsService;
import com.abc.ceop.dao.GenericDao;
import com.abc.ceop.model.dto.DialedOption;
import com.abc.ceop.model.dto.DialedRecord;
import com.abc.ceop.model.dto.SecondProcessCommonData;
import com.abc.ceop.model.entities.AutoExcluido;
import com.abc.ceop.model.entities.Campaign;
import com.abc.ceop.model.entities.Configuration.SecondProcessConfiguration;
import com.abc.ceop.model.entities.EmailSuscriberWeb;
import com.abc.ceop.model.entities.Questionnaire;
import com.abc.ceop.model.entities.QuienRespondeLaEncuesta;
import com.abc.ceop.model.entities.RecordDetail;
import com.abc.ceop.model.entities.Rspsts;
import com.abc.ceop.phoneapprover.service.EmailRenderer;
import com.abc.ceop.phoneapprover.service.EmailSenderService;
import com.abc.ceop.phonepoll.service.DialRecordCsvFileCreator;
import com.abc.ceop.phonepoll.service.PollQuestionMatcherService;
import com.abc.ceop.phonepoll.service.SecondProcessConfigurationService;
import com.abc.ceop.util.ConditionUtils;
import com.abc.ceop.util.EmailExtractor;

@Service
public class DialRecordCsvFileCreatorImpl implements DialRecordCsvFileCreator {

	@Resource
	private PollQuestionMatcherService pollQuestionMatcherService;
	@Resource
	private RecordDetailService recordDetailService;
	@Resource
	private EmailSenderService emailSenderService;
	@Resource
	private  EmailRenderer emailRenderer;
	@Resource
	private GenericDao genericDao;
	@Resource
	private CampaignService campaignService;
	@Resource
	private QuienRespondeLaEncuestaService quienRespondeLaEncuestaService;
	@Resource
	private RspstsService rspstsService;
	@Resource
	private AutoExcluidoService autoExcluidoService;
	@Resource
	private SecondProcessConfigurationService configurationService;
	@Resource
	private EmailSuscriberWebService emailSuscriberWebService;
	@Resource
	private QuestionnaireService questionnaireService;
	
	
		
	private final Logger logger = LoggerFactory
			.getLogger(DialRecordCsvFileCreatorImpl.class);
	
	
	private final static int NOT_CONTACT_FLAG = 0;
	
	private String separator = "|";
	private String idCallerHeaderName = "IDLLAMADAS";

	private Map<DialedOption, Integer> createEmptyDialedRecordsMap(List<DialedOption> header) {
		Map<DialedOption, Integer> emptyFields = new LinkedHashMap<DialedOption, Integer>();
		for (DialedOption dialedOption : header) {
			emptyFields.put(dialedOption, null);
		}
		return emptyFields;
	}
	
	
	@Override
	public String createFile(SecondProcessCommonData secondProcessCommonData) {
		List<DialedRecord> dialledRecordsAux = new ArrayList<DialedRecord>();
		dialledRecordsAux.addAll(secondProcessCommonData.getDialedRecords());
		List <RecordDetail> uncontactedWithEmail = new ArrayList<RecordDetail>();
		
		
		String fileName = secondProcessCommonData.getTempPath() 
							+ "/" 
							+ secondProcessCommonData.getCountryName() 
							+ secondProcessCommonData.getCampaignIdTrabajo() 
							+ "-" 
							+ secondProcessCommonData.getCampaigndate() 
							+ "-" 
							+ secondProcessCommonData.getCurrentTime() 
							+ ".csv";
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(new File(fileName));
			StringBuilder sb = new StringBuilder();
			sb.append(idCallerHeaderName + separator);
			
			int pipesCount = 0;
			RecordDetail recordDetail = null;
			List<RecordDetail> possibleUncontactedPhones = new ArrayList<RecordDetail>();
			if(dialledRecordsAux != null) {
				for (DialedRecord dialedRecord : dialledRecordsAux) {
					recordDetail = recordDetailService.getRecordDetailByPhoneId(dialedRecord.getIdLlamada());
					if(recordDetail != null) {
						sb.append(recordDetail.getHeader());
						pipesCount = StringUtils.countMatches(recordDetail.getHeader(), separator);
						break;
					}
				}
			}
			
			String suscriberProcessDate = secondProcessCommonData.getDateNoSuscriber();
			int hour = 21;
			int minutes = 29;
			int second = 59;
			
			Date dateTo = new Date();
			
			if(suscriberProcessDate != null) {
				String[] time = suscriberProcessDate.split(":");
				if(time != null &&
						time.length > 0) {
					hour = 0;
					minutes = 0;
					second = 0;
					if(time.length >= 1) {
						if(time[0] != null &&
								StringUtils.isNumeric(time[0])) {
							hour = Integer.parseInt(time[0]);
						}
					}
					if(time.length >= 2) {
						if(time[1] != null &&
								StringUtils.isNumeric(time[1])) {
							minutes = Integer.parseInt(time[1]);
						}
					}
					if(time.length >= 3) {
						if(time[2] != null &&
								StringUtils.isNumeric(time[2])) {
							second = Integer.parseInt(time[2]);
						}
					}
				}
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateTo);
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, minutes);
			calendar.set(Calendar.SECOND, second);
			Date dateToProcessData = calendar.getTime();

			calendar.add(Calendar.DATE, -1);
			
			Date dateFrom = calendar.getTime();
			
			if(dateTo.after(dateToProcessData)) {
				possibleUncontactedPhones = recordDetailService.getRecordDetails(dateFrom, dateTo, secondProcessCommonData.getCampaign().getCountry());
				List<RecordDetail> recordDetailUnmatched = new ArrayList<RecordDetail>();
				List<String> suscribers = new ArrayList<String>();
				List<DialedRecord> dialedRecordsWebcati = new ArrayList<DialedRecord>();
				List<RecordDetail> uncontacted = new ArrayList<RecordDetail>();
				List<RecordDetail> allContacted = new ArrayList<RecordDetail>();
				
				if(possibleUncontactedPhones != null) {
					for (RecordDetail rd : possibleUncontactedPhones) {
						boolean exists = false;
						for (DialedRecord dialedRecord : secondProcessCommonData.getDialedRecords()) {
							if (dialedRecord.getIdLlamada().equals(rd.getPhone())) {
								exists = true;
							}
						}
						if(!exists) {
							//Personas que fueron contactadas
							recordDetailUnmatched.add(rd);
							uncontacted.add(rd);
						} else {
							//Personas no fueron contactadas
							suscribers.add(rd.getSuscriberId());
							allContacted.add(rd);
							
						}
					}
				}
				for (RecordDetail rd : recordDetailUnmatched) {
					if(!suscribers.contains(rd.getSuscriberId())) {
						DialedRecord dialledRecord = new DialedRecord(null, new HashMap<DialedOption, Integer>(), rd.getPhone(), null, null);
						dialledRecordsAux.add(dialledRecord);
						suscribers.add(rd.getSuscriberId());
					}
				}
				
				if (secondProcessCommonData.getCampaign().getProcessWebSurveyOnOff() == 1){
					
					completedWebSurvey(secondProcessCommonData, dialedRecordsWebcati);
					dialledRecordsAux.addAll(dialedRecordsWebcati);
					secondProcessCommonData.getDialedRecords().addAll(dialedRecordsWebcati);
				}
				else { logger.info("No fue seteada la busqueda de encuestas via web para la campaña:  " + secondProcessCommonData.getCountryName() );
				
				}
				
				
				if (secondProcessCommonData.getCampaign().getSendMailOnOff() == 1){
				
					getUncontactedAndUncontactedwithMail(secondProcessCommonData, uncontacted, allContacted, uncontactedWithEmail);
				}
				else { logger.info("No fue seteado el envio de mail para la campaña " + secondProcessCommonData.getCountryName() );
					
				}
			}
						
			sb.append("CAMPANA" + separator);
			sb.append("ANO" + separator);
			sb.append("MES" + separator);
			sb.append("DIA" + separator);
			
 			List<DialedOption> header = pollQuestionMatcherService.getAllQuestionsByCampaing(secondProcessCommonData.getCampaign().getCampaign());
			for (DialedOption title : header) {
				if(StringUtils.isNotBlank(title.getColumnSynonym())) {
					sb.append(title.getColumnSynonym() + separator);
				} else {
					sb.append(title.getColumn() + separator);
				}
			}
			
			sb.append("Flag_efectivas" + separator);
			sb.append("Flag_SOS" + separator);
			sb.append("DATA1" + separator);
			sb.append("DATA2" + separator);
			sb.append("DATA3" + separator);
			sb.append("DATA4" + separator);
			sb.append("DATA5" + separator);
			sb.append("ORIGEN"+ separator);
			
			sb.append("\n");
			
			for (DialedRecord dialedRecord : dialledRecordsAux) {
				recordDetail = recordDetailService.getRecordDetailByPhoneId(dialedRecord.getIdLlamada());
				sb.append(dialedRecord.getCallerId() == null ? "" : String.valueOf(dialedRecord.getCallerId())).append(separator);
				Map<DialedOption, Integer> fields = createEmptyDialedRecordsMap(header);
				for (DialedOption dialedOption : dialedRecord.getDialedValues().keySet()) {
					fields.put(dialedOption, dialedRecord.getDialedValues().get(dialedOption));
				}
				if(recordDetail != null) {
					int pipesCountRow = StringUtils.countMatches(recordDetail.getRowValues(), separator);
					if(pipesCountRow < pipesCount) {
						sb.append(recordDetail.getRowValues());
						sb.append(StringUtils.repeat(separator, pipesCount - pipesCountRow));
					} else if(pipesCountRow > pipesCount) {
						String str = recordDetail.getRowValues();
						int pipesCountRowAux = StringUtils.countMatches(str, separator);
						while(pipesCountRowAux > pipesCount) {
							str = str.substring(0, str.lastIndexOf(separator));
							pipesCountRowAux = StringUtils.countMatches(str, separator);
						}
						sb.append(str);
					} else {
						sb.append(recordDetail.getRowValues());
					}
				} else {
					sb.append(StringUtils.repeat(separator, pipesCount));
				}
				
				if(StringUtils.isNotBlank(secondProcessCommonData.getCampaign().getCampaign())) {
					sb.append(secondProcessCommonData.getCampaign().getCampaign() + separator);
				} else {
					sb.append("" + separator);
				}
				if(StringUtils.isNotBlank(secondProcessCommonData.getCampaigndate())) {
					String year = "";
					String month = "";
					String day = "";
					switch (secondProcessCommonData.getCampaigndate().length()) {
						case 6:
							year = secondProcessCommonData.getCampaigndate().substring(0, 4);
							month = secondProcessCommonData.getCampaigndate().substring(4, 5);
							day = secondProcessCommonData.getCampaigndate().substring(5, 6);
							break;
						case 7:
							year = secondProcessCommonData.getCampaigndate().substring(0, 4);
							month = secondProcessCommonData.getCampaigndate().substring(4, 6);
							day = secondProcessCommonData.getCampaigndate().substring(6, 7);
							if(StringUtils.isNumeric(month) &&
									Integer.parseInt(month) > 12) {
								month = secondProcessCommonData.getCampaigndate().substring(4, 5);
								day = secondProcessCommonData.getCampaigndate().substring(5, 7);
							}
							break;
						case 8:
							year = secondProcessCommonData.getCampaigndate().substring(0, 4);
							month = secondProcessCommonData.getCampaigndate().substring(4, 6);
							day = secondProcessCommonData.getCampaigndate().substring(6, 8);
							break;
					}
					sb.append(year + separator);
					sb.append(month + separator);
					sb.append(day + separator);
				} else {
					sb.append(StringUtils.repeat(separator, 3));
				}
				
				for (DialedOption dialedOption : fields.keySet()) {
					sb.append(fields.get(dialedOption) == null ? "" : fields.get(dialedOption));
					sb.append(separator);
				}
				
				if(ConditionUtils.evalcondition(secondProcessCommonData.getFlagEfectivoCondition(), fields)) {
					sb.append("1");
				} else {
					sb.append("0");
				}
				sb.append(separator);
				if(ConditionUtils.evalcondition(secondProcessCommonData.getFlagSOSCondition(), fields)) {
					sb.append("1");
				} else {
					sb.append("0");
				}
				sb.append(separator);
				
				sb.append(separator);
				sb.append(separator);
				sb.append(separator);
				sb.append(separator);
				sb.append(separator);
				
				if (dialedRecord.getSource() == null) {
					sb.append("IVR");
				} else {
					sb.append("WEB");
				}
				
				sb.append("\n");
			}
			sb.append("\n");
			
			writer.write(sb.toString());
			logger.info("Archivo {} fue generado exitosamente.", fileName);
		} catch (IOException e) {
			logger.error("Ha ocurrido un error mientras se generaba el archivo {} en DialRecordCsvFileCreatorImpl / createFile.", fileName);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Ha ocurrido un error en DialRecordCsvFileCreatorImpl / createFile: {} / {}.", e.getClass().toString(), e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				logger.error("Ha ocurrido un error en DialRecordCsvFileCreatorImpl / createFile: {} / {}.", e.getClass().toString(), e.getMessage());
			}
		}
						 
		return fileName;
	}
	
	
	private void removeDontContactMe(List<RecordDetail> uncontactedWithEmail, SecondProcessCommonData secondProcessCommonData ) {
		
		logger.info("removiendo contactos que no quieren el envio de mail");
		
		String contryCampaing = secondProcessCommonData.getCountryName();
		
		if (uncontactedWithEmail != null && uncontactedWithEmail.size() >= 0 ) {
		
		List <AutoExcluido> dontContactMe = new ArrayList<AutoExcluido>();
		dontContactMe = autoExcluidoService.outOfTheWebSurvey(NOT_CONTACT_FLAG, contryCampaing);
		
		if (dontContactMe != null && dontContactMe.size() >=0) {
		List<RecordDetail> recordsToDelete = new ArrayList<RecordDetail>();
		for (RecordDetail suscribersWithMail : uncontactedWithEmail) {
			for (AutoExcluido suscribersDontContactMe : dontContactMe){
				if (suscribersWithMail.getSuscriberId().equals(Integer.toString(suscribersDontContactMe.getSuscriber()))) {
						recordsToDelete.add(suscribersWithMail);
				}
			}
		}
		uncontactedWithEmail.removeAll(recordsToDelete);
		}
		sendMailSurveyWeb(secondProcessCommonData, uncontactedWithEmail);
		}
		else {
			logger.info("No se encontraron personas con email que no hayan sido contactadas");
		}
	}

	
	private void getUncontactedAndUncontactedwithMail(SecondProcessCommonData secondProcessCommonData, List <RecordDetail> unContacted, List <RecordDetail> allContacted, List <RecordDetail> uncontactedWithEmail ) {
		
		logger.info("Buscando los no contactados que tengan email");
		
		if (allContacted != null  && allContacted.size() >= 0 ){
				for (RecordDetail recordsDetailContacted : allContacted) {
					for (DialedRecord dialedRecord : secondProcessCommonData.getDialedRecords()){
						if (dialedRecord.getIdLlamada().equals(recordsDetailContacted.getPhone()) && recordsDetailContacted.getEmail() != null && dialedRecord.getDialedValues().isEmpty()) {
							   uncontactedWithEmail.add(recordsDetailContacted);
							}
						}
					}			
 				}
			
		if (unContacted != null  && unContacted.size() >= 0 ){
			for (RecordDetail recordsDetailUnContacted : unContacted){
				if (recordsDetailUnContacted.getEmail() != null){
					uncontactedWithEmail.add(recordsDetailUnContacted);
			}
		}
			removeDontContactMe(uncontactedWithEmail,  secondProcessCommonData);
		}
		else { logger.info("no se encontraron contactos que no hayan constestado la encuesta con Email");
	}
		
	
		
	}

		
	private  void completedWebSurvey (SecondProcessCommonData secondProcessCommonData, List<DialedRecord> dialedRecordsWebcati) {
		
		logger.info("Buscando clientes que contestaron la encuesta via Web");
		Campaign campaign = campaignService.getCampaignName(secondProcessCommonData.getCountryName());
		String surveyNumberWebCati = campaign.getSurveyNumberWebCati() ;
		List<Questionnaire> listQuestionnaires = questionnaireService.getQuestionnaires(campaign.getCampaign());
		Calendar calendar = Calendar.getInstance();
		Date dateFrom = calendar.getTime();
		
		Integer diasARestarQuienRespondeLaEncuesta = Integer.parseInt(configurationService.getConfigurationValue(SecondProcessConfiguration.DAYS_QRLE));
		Integer diasARestarEmailSuscriberWeb = Integer.parseInt(configurationService.getConfigurationValue(SecondProcessConfiguration.DAYS_EMAIL_SUS_WEB));
		if (diasARestarQuienRespondeLaEncuesta != null && diasARestarEmailSuscriberWeb  != null ) {

			List <String> suscribers = emailSuscriberWebService.getCompletedSurvey(0, campaign.getCountry(), diasARestarEmailSuscriberWeb, dateFrom );
			List<QuienRespondeLaEncuesta> quienesRespondieronLaEncuesta = new ArrayList<QuienRespondeLaEncuesta>();
			if (suscribers != null){
				quienesRespondieronLaEncuesta = quienRespondeLaEncuestaService.getSurveyCompleteNdays(surveyNumberWebCati, diasARestarQuienRespondeLaEncuesta, suscribers);
			}
			
			if (campaign != null && surveyNumberWebCati != null && quienesRespondieronLaEncuesta != null && quienesRespondieronLaEncuesta.size() >= 0 &&
					suscribers != null &&  suscribers.size() >= 0 ) {

				
				DialedRecord dialedRecord;
				RecordDetail recordetail;

				for (QuienRespondeLaEncuesta quienRespondio : quienesRespondieronLaEncuesta) {
					emailSuscriberWebService.updateSWebs(quienRespondio.getId_entrevistado());
					int encuestaId = quienRespondio.getId_encuesta();
					List <Rspsts> answersWebSurveys = rspstsService.valuesWebSurvey(encuestaId, surveyNumberWebCati);
					if (answersWebSurveys != null && answersWebSurveys.size() >=0){
						dialedRecord = new DialedRecord();	
						recordetail =recordDetailService.getRecordDetailBySuscriberID(String.valueOf(quienRespondio.getId_entrevistado()));
						if (recordetail != null){
							String phone = recordetail.getPhone();
							dialedRecord.setIdLlamada(phone);
						}
						dialedRecord.setCallDate(quienRespondio.getFecha());
						dialedRecord.setSource("WEB");
				
					for (Rspsts answersWebSurvey : answersWebSurveys) {
						String nd = pollQuestionMatcherService.getCodeByCallId((long) answersWebSurvey.getId_pregunta());
						if (nd !=null){
						DialedOption dialedOption = new DialedOption(answersWebSurvey.getId_pregunta(),  nd );
						for (Questionnaire value : listQuestionnaires){			
							if(value.getQuestion().getCode().equals(nd)){
								answersWebSurvey.setValor(answersWebSurvey.getValor() + value.getValueWebCati());	//sumando el valor de las respuesta
								break;
							}
						}
						dialedRecord.getDialedValues().put(dialedOption,  answersWebSurvey.getValor()); 
						} 
					}
					dialedRecordsWebcati.add(dialedRecord);
					logger.info(dialedRecordsWebcati.size() +" records fueron encontrados en la base.");
					}
				}
			}
		else {
			logger.info("No se encontraron clientes que hayan constestado la encuesta por la Pagina Web");
			}
		}
		else {
			logger.error("No fue seteada la cantidad de dias para tarer las encuestas completadas");
			}
	}

	private void sendMailSurveyWeb( SecondProcessCommonData secondProcessCommonData, List<RecordDetail> unContactedWithEmail) {

		Campaign campaign = campaignService.getCampaignName(secondProcessCommonData.getCountryName());
		logger.info("enviando mails para completar la encuesta");
		
		Calendar calendar = Calendar.getInstance();
		Date dateToProcessData = calendar.getTime();
		int webSurveyCompletedDefault = 0;
		String campaignId = campaign.getCountry_id().toString();
		String title = configurationService.getConfigurationValue(SecondProcessConfiguration.SUBJECT_MAIL);
		
		//TODO: no funciona mas el FromAddres, hay que removerlo.
		String fromAddress = "FS@ceoplatam.com";
		
		for (RecordDetail mail : unContactedWithEmail){
			
			String suscriber = mail.getSuscriberId();
			String surveyNumberWebCati = campaign.getSurveyNumberWebCati() ; 
			String email = EmailExtractor.extractEmail(mail.getEmail());
			if (surveyNumberWebCati != null &&  suscriber != null && campaignId !=null && title !=null && email != null) {
			    String suscriberId = String.valueOf (suscriber.length());
			    if (suscriber.length() < 10){
			      suscriberId  =  String.valueOf("0" + suscriber.length());
			    } 			    
			String encrypterSubscriber =  String.valueOf(surveyNumberWebCati.length()) + suscriberId +  surveyNumberWebCati + suscriber;
			
			if ( encrypterSubscriber != null) {		
	
			try {
				
				String processedTemplate = emailRenderer.render(title, encrypterSubscriber);
				emailSenderService.postSingleMail(email, title, processedTemplate, null, fromAddress);
				
				EmailSuscriberWeb emailSuscriberWeb = new EmailSuscriberWeb(campaign.getCountry(), mail.getSuscriberId(), email, dateToProcessData , webSurveyCompletedDefault);
				genericDao.update(emailSuscriberWeb);
				
			} catch (Exception exception) {
				logger.error("Error enviando email para completar la encuesta via web: {} / {}" ,exception.getClass().toString() ,exception.getMessage());
					}
				}
			}
			else {
				logger.warn("No se encontraron surveyNumberWebCati o suscriber o campaignId o title (ubicado en la SecondProcessConfigurationVariable O el email fue invalido" );
			}
		}
	}
}
	

