package com.abc.ceop.phonepoll.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.model.dto.DialedOption;
import com.abc.ceop.model.dto.DialedRecord;
import com.abc.ceop.model.dto.OutputRepresentation;
import com.abc.ceop.model.dto.SecondProcessCommonData;
import com.abc.ceop.phonepoll.service.DialRecordExcelFileCreator;
import com.abc.ceop.phonepoll.service.PollQuestionMatcherService;
import com.abc.ceop.util.ConditionUtils;

@Service
public class DialRecordExcelFileCreatorImpl implements DialRecordExcelFileCreator {
	
	private final Logger logger = LoggerFactory.getLogger(DialRecordExcelFileCreatorImpl.class);

	private final PollQuestionMatcherService pollQuestionMatcherService;
	
	private static final int NSUBRESP = 1;
	private static final int ID_OPCION = 1;
	private static final int POND = 1;
	private static final int POND_COUNT = 3;
	
	@Autowired
	public DialRecordExcelFileCreatorImpl(PollQuestionMatcherService pollQuestionMatcherService) {
		this.pollQuestionMatcherService = pollQuestionMatcherService;
	}

	private List<String> createHeader() {
		List<String> fields = new ArrayList<String>();
		fields.add("Id_trabajo");
		fields.add("Id_encuesta");
		fields.add("Id_pregunta");
		fields.add("Nsubresp");
		fields.add("Id_opcion");
		fields.add("Valor");
		fields.add("POND1");
		fields.add("POND2");
		fields.add("POND3");
		return fields;
	}
	
	private List<OutputRepresentation> create(String campaignName, List<DialedRecord> dialedRecords) {
		List<OutputRepresentation> outputList = new ArrayList<OutputRepresentation>(dialedRecords.size());
		int i = 1;
		for (DialedRecord dialedRecord : dialedRecords) {
			for (DialedOption dialedOption : dialedRecord.getDialedValues().keySet()) {
					OutputRepresentation output = new OutputRepresentation();
					Long idPregunta = pollQuestionMatcherService.getCallIdByCode(dialedOption.getColumn());
					output.setIdTrabajo(campaignName != null ? campaignName : "NAN");
					if(campaignName == null) {
						logger.error("IDTrabajo para el nombre de columna {} no fue encontrado", dialedOption.getColumn());
					}
					output.setIdEncuesta(i + "");
					output.setIdPregunta(idPregunta != null ? idPregunta.toString() : "NAN");
					if(idPregunta == null) {
						logger.error("IDPregunta para el nombre de columna {} no fue encontrado", dialedOption.getColumn());
					}
					output.setNsubresp(NSUBRESP + "");
					output.setIdOpcion(ID_OPCION + "");
					output.setValor(dialedRecord.getDialedValues().get(dialedOption) + "");
					
					for (int j = 1; j <= POND_COUNT; j++) {
						output.getPonds().add(POND + "");
					}
					outputList.add(output);
			}
			i++;
		}
		return outputList;
	}
	
	private String[] splitCampaignDate(String campaignDate) {
		String[] dateArray = new String[campaignDate.length()];
		switch(campaignDate.length()) {
			case 8 :
				dateArray[0] = campaignDate.substring(0, 4);
				dateArray[1] = campaignDate.substring(4, 6);
				dateArray[2] = campaignDate.substring(6, 8);
				break;
			case 7 :
				dateArray[0] = campaignDate.substring(0, 4);
				dateArray[1] = campaignDate.substring(4, 6);
				dateArray[2] = campaignDate.substring(6, 7);
				if (StringUtils.isNumeric(dateArray[1]) &&
						Integer.parseInt(dateArray[1]) > 12) {
					dateArray[1] = campaignDate.substring(4, 5);
					dateArray[2] = campaignDate.substring(5, 7);
				}
				break;
			case 6 :
				dateArray[0] = campaignDate.substring(0, 4);
				dateArray[1] = campaignDate.substring(4, 5);
				dateArray[2] = campaignDate.substring(5, 6);
				break;
		}
		
		return dateArray;
	}
	
	private List<DialedRecord> getFilteredUncompletedRecords(SecondProcessCommonData secondProcessCommonData) {
		List<DialedRecord> filteredValues = new ArrayList<DialedRecord>();
		boolean pollcompleted = false;
		
		for (DialedRecord dialedRecord : secondProcessCommonData.getDialedRecords()) {
			if(secondProcessCommonData.getCutConditions() != null &&
					secondProcessCommonData.getCutConditions().size() > 0) {
				pollcompleted = ConditionUtils.evalcondition(secondProcessCommonData.getCutConditions(), dialedRecord.getDialedValues());
			} else {
				boolean containsP2;
				boolean p2ValueEqualsTo2;
				boolean containsP9;
				boolean p9Value;

				for (DialedOption dialedOption : dialedRecord.getDialedValues().keySet()) {
					p2ValueEqualsTo2 = dialedRecord.getDialedValues().get(dialedOption) == 2;
					p9Value = dialedRecord.getDialedValues().get(dialedOption) != -1;
					containsP2 = "p2".equals(dialedOption.getColumn());
					containsP9 = "p9".equals(dialedOption.getColumn());
					if ((containsP2 && p2ValueEqualsTo2) || (containsP9 && p9Value)) {
						pollcompleted = true;
					}
				}
			}
			if (pollcompleted) {
				filteredValues.add(dialedRecord);
				pollcompleted = false;
			}
		}
		return filteredValues;
	}
	
	@Override
	public String createFile(SecondProcessCommonData secondProcessCommonData) {
		FileOutputStream fileOut = null;
		
		String fileName = secondProcessCommonData.getTempPath() 
						+ "/" 
						+ secondProcessCommonData.getCountryName() 
						+ secondProcessCommonData.getCampaignIdTrabajo() 
						+ "-" 
						+ secondProcessCommonData.getCampaigndate() 
						+ "-" 
						+ secondProcessCommonData.getCurrentTime() + ".xls";
		
		try {
			Workbook workbook = new HSSFWorkbook();
			Sheet sheet = workbook.createSheet("Respuestas"); 
			Row headerRow = sheet.createRow(0);
			int i = 0;
			for (String header : createHeader()) {
				headerRow.createCell(i).setCellValue(header);
				sheet.setColumnWidth(i++, 3000);
			}
			List<DialedRecord> filteredValues = getFilteredUncompletedRecords(secondProcessCommonData);
			List<OutputRepresentation> outputList = create(secondProcessCommonData.getCampaignIdTrabajo(), filteredValues);
			i = 0;
			String previousIdEncuesta = "";
			String currentIdEncuesta = "";
			for (OutputRepresentation output : outputList) {
				
				Row row = null;
				currentIdEncuesta = output.getIdEncuesta();
				String[] dateArray = splitCampaignDate(secondProcessCommonData.getCampaigndate());
				if (!currentIdEncuesta.equals(previousIdEncuesta)) {
					int nextRow = 1;
					previousIdEncuesta = currentIdEncuesta;
					while(nextRow <= 3) {

						OutputRepresentation fakeOutput = new OutputRepresentation();
						if (nextRow == 1) {
							fakeOutput.setIdPregunta(secondProcessCommonData.getIdQuestionYear());
							fakeOutput.setValor(dateArray[0]);
						} else if (nextRow == 2) {
							fakeOutput.setIdPregunta(secondProcessCommonData.getIdQuestionMonth());
							fakeOutput.setValor(dateArray[1]);
						} else if (nextRow == 3) {
							fakeOutput.setIdPregunta(secondProcessCommonData.getIdQuestionDay());
							fakeOutput.setValor(dateArray[2]);
						}
						row = sheet.createRow(++i);
						row.createCell(0).setCellValue(output.getIdTrabajo());
						row.createCell(1).setCellValue(output.getIdEncuesta());
						row.createCell(2).setCellValue(fakeOutput.getIdPregunta());
						row.createCell(3).setCellValue(output.getNsubresp());
						row.createCell(4).setCellValue(output.getIdOpcion());
						row.createCell(5).setCellValue(fakeOutput.getValor());
						int j = 6;
						for (String pond : output.getPonds()) {
							row.createCell(j++).setCellValue(pond);
						}
						nextRow++;
					}
				}
				row = sheet.createRow(++i);
				row.createCell(0).setCellValue(output.getIdTrabajo());
				row.createCell(1).setCellValue(output.getIdEncuesta());
				row.createCell(2).setCellValue(output.getIdPregunta());
				row.createCell(3).setCellValue(output.getNsubresp());
				row.createCell(4).setCellValue(output.getIdOpcion());
				row.createCell(5).setCellValue(output.getValor());
				int j = 6;
				for (String pond : output.getPonds()) {
					row.createCell(j++).setCellValue(pond);
				}
			}
			
			fileOut = new FileOutputStream(fileName);
			workbook.write(fileOut);
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Ha ocurrido un error mientras se creaba el archivo EXCEL. {} / {}." ,exception.getClass().toString() , exception.getMessage());
		} finally {
			try {
				if (fileOut != null) {
					fileOut.close();
				}
			} catch (IOException exception) {
				logger.error("Ha ocurrido un error mientras se cerraba el archivo EXCEL. {} / {}." ,exception.getClass().toString() , exception.getMessage());
			}
		}
		return fileName;
	}

}
