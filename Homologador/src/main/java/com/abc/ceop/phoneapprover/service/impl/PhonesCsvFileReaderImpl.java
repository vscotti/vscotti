package com.abc.ceop.phoneapprover.service.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVReader;

import com.abc.ceop.common.service.RecordDetailFilterService;
import com.abc.ceop.model.dto.Record;
import com.abc.ceop.model.entities.Campaign;
import com.abc.ceop.model.entities.CsvFileConfiguration;
import com.abc.ceop.model.entities.Location;
import com.abc.ceop.model.entities.RecordDetailFilter;
import com.abc.ceop.phoneapprover.service.PhonesCsvFileReader;
/*
 * Esta clase Lee linea por linea el txt para verificando que esten correctas y si es asi las guarda en una lista de records 
 */
@Service
public class PhonesCsvFileReaderImpl implements PhonesCsvFileReader {

	private final Logger logger = LoggerFactory.getLogger(PhonesCsvFileReaderImpl.class);
	
	@Resource
	private RecordDetailFilterService recordDetailFilterService;
	
	public PhonesCsvFileReaderImpl() {
	}

	public List<Record> readCsvFile(String filePath, Campaign campaign, CsvFileConfiguration config) {
		List<Record> csvRecords = new ArrayList<Record>();
		try {
			CSVReader csvReader = new CSVReader(new FileReader(new File(filePath)), ',');
	
			List<String[]> list = csvReader.readAll();
			list.toString();
		} catch (Exception exception) {
			logger.error("Ha ocurrido un error mientras se leia el archivo CSV. {} / {}.", exception.getClass().toString() , exception.getMessage());
			exception.printStackTrace();
		} finally {
		}
		
		return csvRecords;
	}

	public List<Record> readTxtFile(String filePath, CsvFileConfiguration config) {
		List<Record> csvRecords = new ArrayList<Record>();
		String country =  getCountryNameFromFile(filePath);
		List<RecordDetailFilter> recordDetailFilterList = recordDetailFilterService.getRecordDetailFilter(country);
		 
		if (recordDetailFilterList != null) {
		     logger.info("para el pais " + country + " se aplicara el patron de filtro de la tabla RecordDetailFilter");
		 }

		DataInputStream in = null;
		try {
			String separatorStr = "\t";
			if(config != null &&
					StringUtils.isNotBlank(config.getSeparator()) && config.getSeparator().length() > 0) {
				separatorStr = config.getSeparator();
			}
			
			FileInputStream fstream = new FileInputStream(filePath);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
								
			StringBuilder headerSb = new StringBuilder();

			String strLine = br.readLine();
			String[] header = strLine.split(separatorStr);
			for (String value : header) {
				headerSb.append(value).append("|");
			}
			
			String[] line = null;
			int lineLogger = 1; 
			
            while ((strLine = br.readLine()) != null) {
                try {
                   
                    line = strLine.split(separatorStr);
                    lineLogger++;

                    if (recordDetailFilterList == null || recordDetailFilterList.isEmpty()) {
                        createRecords(config, csvRecords, separatorStr, headerSb, strLine, line);
                        
                    } else {
                        
                        boolean lineHasPattern = false;

                        for (RecordDetailFilter recordDetailFilter : recordDetailFilterList) {
                            String lineValueToFilter = line[recordDetailFilter.getColumnTofilter() - 1];

                            if (lineValueToFilter.equals(recordDetailFilter.getPattern())) {
                                lineHasPattern = true;
                                break;
                            }
                        }
                        
                        if (!lineHasPattern) {
                            createRecords(config, csvRecords, separatorStr, headerSb, strLine, line);
                        } else {
                            logger.info("la linea : " + lineLogger  + " del txt, contiene un patron de filtro y no fue agregada ");
                        }
                    }

                } catch (Exception exception) {
                    logger.error("Ha ocurrido un error mientras se leia el archivo CSV. {} / {}.", exception.getClass().toString(),
                            exception.getMessage());
                    exception.printStackTrace();
                }
            }
		} catch (Exception exception) {
			logger.error("Ha ocurrido un error mientras se leia el archivo CSV. {} / {}.", exception.getClass().toString() , exception.getMessage());
			exception.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException exception) {
				logger.error("Ha ocurrido un error mientras se cerraba el archivo CSV. {} / {}.", exception.getClass().toString() , exception.getMessage());
			}
		}
		return csvRecords;
	}

    private void createRecords(CsvFileConfiguration config, List<Record> csvRecords, String separatorStr, StringBuilder headerSb, String strLine, String[] line) {
        
        Record csvRecord;
        StringBuilder rowSb;
        rowSb = new StringBuilder();
        rowSb.append(strLine.replaceAll(separatorStr, "|")).append("|");
                
        if (line.length < config.getCountryPosition() - 1 ||
        		line.length < config.getStatePosition() - 1 ||
        		line.length < config.getLargeCityPosition() - 1 ||
        		line.length < config.getSmallCityPosition() - 1 ||
        		line.length < config.getTypeOfServicePosition() - 1 ||
        		line.length < config.getCountryPosition() - 1 ||
        		line.length < config.getSuscriberPosition() - 1 ||
        		(config.getCampo1() != null && line.length < config.getCampo1() - 1) ||
        		(config.getCampo2() != null && line.length < config.getCampo2() - 1) ||
        		(config.getCampo3() != null && line.length < config.getCampo3() - 1) ||
        		(config.getCampo4() != null && line.length < config.getCampo4() - 1) ||
        		(config.getCampo5() != null && line.length < config.getCampo5() - 1) ||
        		(config.getCampo6() != null && line.length < config.getCampo6() - 1) ||
        		(config.getCampo7() != null && line.length < config.getCampo7() - 1) ||
        		(config.getCampo8() != null && line.length < config.getCampo8() - 1) ||
        		(config.getCampo9() != null && line.length < config.getCampo9() - 1) ||
        		(config.getCampo10() != null && line.length < config.getCampo10() - 1)) {
        	System.out.println("Ha ocurrido un error mientras se leia el archivo CSV. Linea invalida " + printList(line));
        	logger.error("Ha ocurrido un error mientras se leia el archivo CSV. Linea invalida. {}", printList(line));
        } else {
        	csvRecord = new Record(
        						Location.createLocation(
        								line[config.getCountryPosition() - 1],
        								line[config.getStatePosition() - 1],
        								line[config.getLargeCityPosition() - 1],
        								line[config.getSmallCityPosition() - 1])
        						, line[config.getMobilePosition() - 1]
        						, getPhones(config.getPhonePositionsAsInt(), line)
        						, headerSb.toString()
        						, rowSb.toString()
        						, line[config.getTypeOfServicePosition() - 1]
        						, line[config.getSuscriberPosition() - 1]
        						, line.length >=  config.getEmailPosition() ?  line[config.getEmailPosition()- 1] : null
//											,line[config.getEmailPosition()- 1]!=null?line[config.getEmailPosition()- 1]:null
        						, config.getCampo1() != null? line[config.getCampo1() - 1] : null
        						, config.getCampo2() != null? line[config.getCampo2() - 1] : null
        						, config.getCampo3() != null? line[config.getCampo3() - 1] : null
        						, config.getCampo4() != null? line[config.getCampo4() - 1] : null
        						, config.getCampo5() != null? line[config.getCampo5() - 1] : null
        						, config.getCampo6() != null? line[config.getCampo6() - 1] : null
        						, config.getCampo7() != null? line[config.getCampo7() - 1] : null
        						, config.getCampo8() != null? line[config.getCampo8() - 1] : null
        						, config.getCampo9() != null? line[config.getCampo9() - 1] : null
        						, config.getCampo10() != null? line[config.getCampo10() - 1] : null
        						);
        	csvRecords.add(csvRecord);
        }
    }

	@Override
	public List<Record> readFile(String filePath, CsvFileConfiguration config) { 
		return readTxtFile(filePath, config);
	}
	
	private String printList(String[] line) {
		String str = "[";
		for (String string : line) {
			str = str + string + ", ";
		}
		return str + "]";
	}
	
	private List<String> getPhones(int[] positions, String[] line) {
		List<String> phones = new ArrayList<String>();
		for (int position : positions) {
			String phoneCandidate = line[position - 1];
			if (StringUtils.isNotBlank(phoneCandidate)) {
				phones.add(phoneCandidate);
			}
		}
		return phones;
	}
	private String getCountryNameFromFile(String filepath) {
        return new File(filepath).getName().split("\\d")[0];
    }
	
}
