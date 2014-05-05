package com.abc.ceop.phonepoll.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abc.ceop.common.service.CampaignService;
import com.abc.ceop.common.service.ConditionService;
import com.abc.ceop.common.service.FTPService;
import com.abc.ceop.model.dto.FTPCredentials;
import com.abc.ceop.model.dto.SecondProcessCommonData;
import com.abc.ceop.model.dto.SecondProcessResultInfo;
import com.abc.ceop.model.entities.Campaign;
import com.abc.ceop.model.entities.Configuration.SecondProcessConfiguration;
import com.abc.ceop.phoneapprover.service.SecondProcessEmailService;
import com.abc.ceop.phonepoll.service.DialRecordCsvFileCreator;
import com.abc.ceop.phonepoll.service.DialRecordExcelFileCreator;
import com.abc.ceop.phonepoll.service.DialRecordExcelFileReader;
import com.abc.ceop.phonepoll.service.DialRecordTxtFileCreator;
import com.abc.ceop.phonepoll.service.PollQuestionMatcherService;
import com.abc.ceop.phonepoll.service.SecondProcess;
import com.abc.ceop.phonepoll.service.SecondProcessConfigurationService;
import com.abc.ceop.util.CampaignUtils;

@Service
public class SecondProcessImplementation implements SecondProcess {

	private final Logger logger = LoggerFactory.getLogger(SecondProcessImplementation.class);
	
	private final DialRecordExcelFileReader dialRecordExcelFileReader;
	private final DialRecordExcelFileCreator dialRecordExcelFileCreator;
	private final DialRecordCsvFileCreator dialRecordCsvFileCreator;
	private final DialRecordTxtFileCreator dialRecordTxtFileCreator;
	private final FTPService ftpService;
	private final CampaignService campaignService;
	private final SecondProcessConfigurationService configurationService;
	private final SecondProcessEmailService emailService;
	private final PollQuestionMatcherService pollQuestionMatcherService;
	private final ConditionService conditionService;
//	private final RecordDetailService recordDetailService;
	
	@Autowired
	public SecondProcessImplementation (
			 DialRecordExcelFileReader dialRecordExcelFileReader
			,DialRecordExcelFileCreator dialRecordExcelFileCreator
			,DialRecordCsvFileCreator dialRecordCsvFileCreator
			,DialRecordTxtFileCreator dialRecordTxtFileCreator
			,FTPService ftpService
			,CampaignService campaignService
			,SecondProcessConfigurationService configurationService
			,SecondProcessEmailService emailService
			,PollQuestionMatcherService pollQuestionMatcherService
//			,RecordDetailService recordDetailService
			,ConditionService conditionService) {
		super();
		this.dialRecordExcelFileReader = dialRecordExcelFileReader;
		this.dialRecordExcelFileCreator = dialRecordExcelFileCreator;
		this.dialRecordCsvFileCreator = dialRecordCsvFileCreator;
		this.dialRecordTxtFileCreator = dialRecordTxtFileCreator;
		this.ftpService = ftpService;
		this.campaignService = campaignService;
		this.configurationService = configurationService;
		this.emailService = emailService;
		this.pollQuestionMatcherService = pollQuestionMatcherService;
		this.conditionService = conditionService;
//		this.recordDetailService = recordDetailService;
	}

	@Override
	@Transactional(readOnly=false)
	public void execute() {
		logger.info("Inicializando Segundo proceso. Version 06/11/2012");
		
		String filename = null;
		do {
			filename =  ftpService.copyLocalAndRemove(configurationService.getConfigurationValue(SecondProcessConfiguration.INPUT_FTP_PATH),
					configurationService.getConfigurationValue(SecondProcessConfiguration.TEMP_PATH), getFTPInputCredentials());
			if (filename != null) {
				logger.info("Dispatching thread for file {}", filename);
				executeProcess(filename);
			}
		} while (filename != null);
		logger.info("Segundo proceso finalizado.");
	}
	
	private SecondProcessCommonData loadCommonData(String remoteFileName) {
	    
	    logger.info("executing loadCommonData: remoteFileName -> " + remoteFileName);
	    
		SecondProcessCommonData cd = new SecondProcessCommonData();
		cd.setDateNoSuscriber(configurationService.getConfigurationValue(SecondProcessConfiguration.NO_SUSCRIBER_TIME));
		cd.setTempPath(configurationService.getConfigurationValue(SecondProcessConfiguration.TEMP_PATH));
		cd.setCurrentTime(new SimpleDateFormat("hhmmss-SSS").format(new Date()));
		cd.setCampaignIdTrabajo(CampaignUtils.getCampaignIdTrabajo(remoteFileName));
		cd.setCampaigndate(CampaignUtils.getCampaignDate(remoteFileName));
		
		logger.info("Obteniendo Campaign...");
		Campaign camp = campaignService.getCampaignByCampaignId(CampaignUtils.getCampaignIdTrabajo(remoteFileName));
		
		if (camp == null){
		    logger.info("Campaign es null...");
		}else{
		    logger.info("Campaign obtenida nombre -> " + camp.getCampaign() +" " + camp.getCountry() );
		}
		
		cd.setCampaign(camp);
		
		cd.setDialedRecords(dialRecordExcelFileReader.readExcel(remoteFileName));
		
		//Aunque no haya records se necesita cargar del if para abajo para levantar encuestas web.
//		if(cd.getDialedRecords() != null &&
//				cd.getDialedRecords().size() > 0) {
			cd.setIdQuestionYear(String.valueOf(pollQuestionMatcherService.getCallIdByCode("year")));
			cd.setIdQuestionMonth(String.valueOf(pollQuestionMatcherService.getCallIdByCode("month")));
			cd.setIdQuestionDay(String.valueOf(pollQuestionMatcherService.getCallIdByCode("day")));
			cd.setFlagEfectivoCondition(conditionService.getConditionsByDestinationCampaign("FLAGEFECTIVO", cd.getCampaign()));
			cd.setFlagSOSCondition(conditionService.getConditionsByDestinationCampaign("FLAGSOS", cd.getCampaign()));
			cd.setCutConditions(conditionService.getConditionsByDestination("CUTTINGCONDITION"));
//		}
		return cd;
	}
	
	public void executeProcess(String filename) {
	    
	    logger.info("executing executeProcess: fileName -> " + filename);
	    
		SecondProcessCommonData cd = loadCommonData(filename);
//			checkContactedPhones(cd);
	
		//Las lineas de abajo fueron comentadas para poder procesar NO_SUSCRIBER_TIME para poder cargar un excel vacio de la campaña a buscar encuestas realizadas via web.		  		
//		if (cd.getDialedRecords().isEmpty()) {
//			logger.error("No se encontraron Records en el archivo {}. Finalizando proceso.", filename);
//			return;
//		}
		logger.info("{} records fueron encontrados en el archivo {}.", new Object[] {cd.getDialedRecords().size(), filename});
		
		String firstOutputFilename = dialRecordCsvFileCreator.createFile(cd);
		logger.info("El archivo {} fue convertido a {}.", new Object[] {filename, firstOutputFilename});
		validateAndUploadOutputFileName(firstOutputFilename);
		
		String secondOutputFilename = dialRecordExcelFileCreator.createFile(cd);
		logger.info("El archivo {} fue convertido a {}.", new Object[] {filename, secondOutputFilename});
		validateAndUploadOutputFileName(secondOutputFilename);
		
		String thirdOutputFilename = dialRecordTxtFileCreator.createFile(cd, false);
		if(thirdOutputFilename != null) {
			logger.info("El archivo {} fue convertido a {}.", new Object[] {filename, firstOutputFilename});
			validateAndUploadOutputFileName(thirdOutputFilename);
		}

		String fourthOutputFilename = dialRecordTxtFileCreator.createFile(cd, true);
		if(fourthOutputFilename != null) {
			SecondProcessResultInfo info = new SecondProcessResultInfo();
			info.setFileName(fourthOutputFilename);
			info.setCampaign(CampaignUtils.getCampaignIdTrabajo(filename));
			emailService.sendEmail(info);
		}
		
	}

    
		  
//		private void checkContactedPhones (SecondProcessCommonData cd){
//
//			int hour = 01;
//			int minutes = 59;
//			int second = 59;
//			
//			Date dateTo = new Date();
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(dateTo);
//			calendar.set(Calendar.HOUR_OF_DAY, hour);
//			calendar.set(Calendar.MINUTE, minutes);
//			calendar.set(Calendar.SECOND, second);
//
//			calendar.add(Calendar.DATE, -1);
//			Date dateFrom = calendar.getTime();
//						
//			List<RecordDetail> contactedPhones = new ArrayList<RecordDetail>();
//			contactedPhones = recordDetailService.getRecordDetails(dateFrom, dateTo, cd.getCampaign().getCountry());
//			List<DialedRecord> dialledRecordsCompare = new ArrayList<DialedRecord>();
//			dialledRecordsCompare.addAll(cd.getDialedRecords());
//			
//			if (contactedPhones != null){
//						
//			for (RecordDetail rdCompare : contactedPhones) {
//				for (DialedRecord dr :dialledRecordsCompare){
//					if (rdCompare.getPhone().equals(dr.getIdLlamada())){
//						recordDetailService.saveRecordDetail(rdCompare.getSuscriberId(), 1);
//						}
//					}
//				}
//			}
//		}
		
	private void validateAndUploadOutputFileName(String outputFilename) {
		if (outputFilename != null) {
			boolean success = ftpService.upload(outputFilename, configurationService.getConfigurationValue(SecondProcessConfiguration.OUTPUT_FTP_PATH), getFTPOutputCredentials());
			if (!success) {
				logger.error("Ha ocurrido un error mientras se subia el archivo {} al ftp", outputFilename);
			} else {
				FileUtils.deleteQuietly(new File(outputFilename));
				logger.info("El archivo {} fue subido existosamente al FTP.", new File(outputFilename).getName());
			}
		}
	}
	
	private FTPCredentials getFTPInputCredentials() {
		return new FTPCredentials(
				configurationService.getConfigurationValue(SecondProcessConfiguration.INPUT_FTP_HOST),
				configurationService.getConfigurationValue(SecondProcessConfiguration.INPUT_FTP_USERNAME),
				configurationService.getConfigurationValue(SecondProcessConfiguration.INPUT_FTP_PASSWORD));
	}
	
	private FTPCredentials getFTPOutputCredentials() {
		return new FTPCredentials(
				configurationService.getConfigurationValue(SecondProcessConfiguration.OUTPUT_FTP_HOST),
				configurationService.getConfigurationValue(SecondProcessConfiguration.OUTPUT_FTP_USERNAME),
				configurationService.getConfigurationValue(SecondProcessConfiguration.OUTPUT_FTP_PASSWORD));
	}
	
}
