package com.abc.ceop.phonepoll.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.RecordDetailService;
import com.abc.ceop.model.dto.DialedOption;
import com.abc.ceop.model.dto.DialedRecord;
import com.abc.ceop.model.dto.SecondProcessCommonData;
import com.abc.ceop.model.entities.RecordDetail;
import com.abc.ceop.phonepoll.service.DialRecordTxtFileCreator;
import com.abc.ceop.phonepoll.service.PollQuestionMatcherService;
import com.abc.ceop.util.ConditionUtils;

@Service
public class DialRecordTxtFileCreatorImpl implements DialRecordTxtFileCreator {

	@Resource
	private PollQuestionMatcherService pollQuestionMatcherService;
	@Resource
	private RecordDetailService recordDetailService;
	
	private final Logger logger = LoggerFactory
			.getLogger(DialRecordTxtFileCreatorImpl.class);
	
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
	public String createFile(SecondProcessCommonData secondProcessCommonData, boolean lastHourcheck) {

		String fileName = secondProcessCommonData.getTempPath() 
						+ "/" 
						+ secondProcessCommonData.getCountryName() 
						+ secondProcessCommonData.getCampaignIdTrabajo() 
						+ "-" 
						+ secondProcessCommonData.getCampaigndate() 
						+ "-" 
						+ secondProcessCommonData.getCurrentTime() + "-email.txt";
		
		boolean hasValues = false;
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(new File(fileName));
			StringBuilder sb = new StringBuilder();
			sb.append(idCallerHeaderName + separator);
			
			int pipesCount = 0;
			RecordDetail recordDetail = null;

			if(secondProcessCommonData.getDialedRecords() != null) {
				for (DialedRecord dialedRecord : secondProcessCommonData.getDialedRecords()) {
					recordDetail = recordDetailService.getRecordDetailByPhoneId(dialedRecord.getIdLlamada());
					if(recordDetail != null) {
						sb.append(recordDetail.getHeader());
						pipesCount = StringUtils.countMatches(recordDetail.getHeader(), separator);
						break;
					}
				}
			}
			
			sb.append("CAMPANA" + separator);
			sb.append("ANO" + separator);
			sb.append("MES" + separator);
			sb.append("DIA" + separator);
			
 			List<DialedOption> header = pollQuestionMatcherService.getAllQuestionsByCampaing(secondProcessCommonData.getCampaignIdTrabajo());
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
			
			for (DialedRecord dialedRecord : secondProcessCommonData.getDialedRecords()) {
				
				Date to = new Date();
				Calendar c = Calendar.getInstance();
				c.setTime(to);
				c.add(Calendar.HOUR, -1);
				Date from = c.getTime();

				if(!lastHourcheck || (lastHourcheck && dialedRecord.getCallDate().after(from) && dialedRecord.getCallDate().before(to))) {
					recordDetail = recordDetailService.getRecordDetailByPhoneId(dialedRecord.getIdLlamada());
					Map<DialedOption, Integer> fields = createEmptyDialedRecordsMap(header);
					
					for (DialedOption dialedOption : dialedRecord.getDialedValues().keySet()) {
						fields.put(dialedOption, dialedRecord.getDialedValues().get(dialedOption));
					}
					
					if(ConditionUtils.evalcondition(secondProcessCommonData.getFlagSOSCondition(), fields)) {
						hasValues = true;
						if (dialedRecord.getCallerId() != null){
							sb.append(String.valueOf(dialedRecord.getCallerId())).append(separator);
						}else {
							sb.append("").append(separator);
						}
						
						if(recordDetail != null) {
							sb.append(recordDetail.getRowValues());
						} else {
							sb.append(StringUtils.repeat(separator, pipesCount));
						}
						
						if(StringUtils.isNotBlank(secondProcessCommonData.getCampaignIdTrabajo())) {
							sb.append(secondProcessCommonData.getCampaignIdTrabajo() + separator);
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
						
						sb.append("1");
						
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
				}
			}
			writer.write(sb.toString());
			logger.info("El archivo {} fue generado existosamente.", fileName);
		} catch (IOException e) {
			logger.error("Ha ocurrido un error mientras se generaba el archivo {} en DialRecordTxtFileCreatorImpl / createFile.", fileName);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Un error a ocurrido en DialRecordTxtFileCreatorImpl / createFile.");
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				logger.error("Un error a ocurrido en DialRecordTxtFileCreatorImpl / createFile: {} / {}", e.getClass().toString(), e.getMessage());
			}
		}
		if(!hasValues) {
			return null;
		}
		return fileName;
	}
	
}
