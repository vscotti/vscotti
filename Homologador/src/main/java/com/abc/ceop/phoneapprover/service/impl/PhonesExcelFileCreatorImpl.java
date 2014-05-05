package com.abc.ceop.phoneapprover.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.abc.ceop.model.dto.Record;
import com.abc.ceop.phoneapprover.service.PhonesExcelFileCreator;

@Service
public class PhonesExcelFileCreatorImpl implements PhonesExcelFileCreator {

	private final Logger logger = LoggerFactory.getLogger(PhonesExcelFileCreatorImpl.class);
	private static final int TELEPHONE_NUMBER_COUNT = 5;
	private static final int FIELD_NUMBER_COUNT = 10;
	private static final int DATA_FIELD_NUMBER = 5;
	
	private List<String> createHeader() {
		List<String> fields = new ArrayList<String>();

		for (int i = 1; i <= TELEPHONE_NUMBER_COUNT; i++) {
			fields.add("TELEFONO" + i);
		}

		fields.add("DATA");
		for (int i = 1; i <= FIELD_NUMBER_COUNT; i++) {
			fields.add("CAMPO" + i);
		}
		
		return fields;
	}

	@Override
	public String createPhonesExcelFile(String excelFilePath, List<Record> values) {
		FileOutputStream fileOut = null;
		try {
			Workbook workbook = new HSSFWorkbook();
			Sheet sheet = workbook.createSheet("Contacts"); // Creates Sheet
			
			Row headerRow = sheet.createRow(0); // Creates Header
			
			int i = 0;
			for (String headerField : createHeader()) {
				headerRow.createCell(i).setCellValue(headerField);
				sheet.setColumnWidth(i++, 5000);
			}
		
			i = 1;
			for (Record record : values) {
				Row row = sheet.createRow(i++);
				int j = 0;
				int campo = 6;
				for (String value : record.getTelephones()) {
					row.createCell(j++).setCellValue(value);
				}
				if(record.getMobile() != null) {
					row.createCell(j++).setCellValue(record.getMobile());
				}
				row.createCell(DATA_FIELD_NUMBER).setCellValue(record.getTypeOfService() != null? record.getTypeOfService() : "");
				row.createCell(DATA_FIELD_NUMBER + 1).setCellValue(record.getCampo1() != null? record.getCampo1() : "");
				row.createCell(DATA_FIELD_NUMBER + 2).setCellValue(record.getCampo2() != null? record.getCampo2() : "");
				row.createCell(DATA_FIELD_NUMBER + 3).setCellValue(record.getCampo3() != null? record.getCampo3() : "");
				row.createCell(DATA_FIELD_NUMBER + 4).setCellValue(record.getCampo4() != null? record.getCampo4() : "");
				row.createCell(DATA_FIELD_NUMBER + 5).setCellValue(record.getCampo5() != null? record.getCampo5() : "");
				row.createCell(DATA_FIELD_NUMBER + 6).setCellValue(record.getCampo6() != null? record.getCampo6() : "");
				row.createCell(DATA_FIELD_NUMBER + 7).setCellValue(record.getCampo7() != null? record.getCampo7() : "");
				row.createCell(DATA_FIELD_NUMBER + 8).setCellValue(record.getCampo8() != null? record.getCampo8() : "");
				row.createCell(DATA_FIELD_NUMBER + 9).setCellValue(record.getCampo9() != null? record.getCampo9() : "");
				row.createCell(DATA_FIELD_NUMBER + 10).setCellValue(record.getCampo10() != null? record.getCampo10() : "");
				if (record.getSuscriber() != null) {
					row.createCell (campo).setCellValue(record.getSuscriber());
				}
			}
			
			String path = excelFilePath.replace(new File(excelFilePath).getName(), "");
			new File(path).mkdirs();
			fileOut = new FileOutputStream(excelFilePath);
			workbook.write(fileOut);
			return excelFilePath;
			
		} catch (IOException exception) {
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
		return null;
	}

}
