package com.abc.ceop.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abc.ceop.common.service.CsvFileConfigurationService;
import com.abc.ceop.common.service.LocationService;
import com.abc.ceop.model.dto.Record;
import com.abc.ceop.model.entities.CsvFileConfiguration;
import com.abc.ceop.model.entities.Location;
import com.abc.ceop.phoneapprover.service.PhonesCsvFileReader;

public class BatchDatabaseMain {

	private BatchDatabaseMain() {}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/model-context.xml");

		LocationService locationService = context.getBean(LocationService.class);
		CsvFileConfigurationService csvFileConfigurationService = context.getBean(CsvFileConfigurationService.class);
		PhonesCsvFileReader phonesCsvFileReader = context.getBean(PhonesCsvFileReader.class);

		List<Record> unprocessedRecords = new ArrayList<Record>();
		List<Location> locations = new ArrayList<Location>();
		List<String> paths = new ArrayList<String>();
//		paths.add("src/test/resources/data1/");
//		paths.add("src/test/resources/data2/");
//		paths.add("src/test/resources/data3/");
//		paths.add("src/test/resources/data4/");
		paths.add("src/test/resources/data5/");
		
		for (String path : paths) {
		    File folder = new File(path);
		    File[] listOfFiles = folder.listFiles();
		    for (File file : listOfFiles) {
		    	if (!file.isFile()) {
		    		continue;
		    	}
		    
				String filepath = path + file.getName();
				String countryName = getCountryNameFromFile(filepath);
				CsvFileConfiguration config = csvFileConfigurationService.getCsvFileConfiguration(countryName);
				unprocessedRecords.addAll(phonesCsvFileReader.readFile(filepath, config));
				for (Record record : unprocessedRecords) {
					if(!DataLoader.validateDuplicates(locations, record.getLocation())) {
						locations.add(record.getLocation());
					}
				}
			}
		}

	    System.out.println("Locaciones total:" + unprocessedRecords.size());
	    System.out.println("Locaciones duplicadas:" + (unprocessedRecords.size() - locations.size()));
	    System.out.println("Locaciones encontradas:" + locations.size());
	    
	    List<Location> list = new ArrayList<Location>();
	  
		//Mapa por defecto todo en true, no lee la tabla LocationSearchConfig donde seteamos como buscar una locacion 
	    Map <String, Boolean> map = generateSearchLocationMap();
	    
	    for (Location loc : locations) {
			Location location = locationService.lookupLocationLike(loc, map);
			if(location == null) {
				list.add(loc);
			}
		}
	    locations = list;

		System.out.println("Locaciones no encontradas en base:" + locations.size());
		
		try {
//			InputStream inputStream = new FileInputStream("internacional.xls");
//			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Respuestas"); 
			
			int i = 0;
			for(Location location : locations) {
				Row row = sheet.createRow(++i);
				row.createCell(0).setCellValue(location.getCountry().getName());
				row.createCell(1).setCellValue(location.getState());
				row.createCell(2).setCellValue(location.getSmallCity());
				row.createCell(3).setCellValue(location.getLargeCity());
			}
			workbook.write(new FileOutputStream("src/test/resources/uruguay.xls"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Cantidad total de Locations: " + unprocessedRecords.size());
		System.out.println("Cantidad total de Locations agregadas: " + locations.size());
	}
	
	private static String getCountryNameFromFile(String filepath) {
		return new File(filepath).getName().split("\\d")[0];
	}
	
	private static Map<String, Boolean> generateSearchLocationMap() {
		
		Map<String, Boolean> map = new LinkedHashMap<String, Boolean>();
		map.put("country", true);
		map.put("state", true);
		map.put("largeCity", true);
		map.put("smallCity", true);
		
		return map;
	}
}
