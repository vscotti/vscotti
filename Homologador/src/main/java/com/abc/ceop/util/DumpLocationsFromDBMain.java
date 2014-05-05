package com.abc.ceop.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abc.ceop.common.service.LocationService;
import com.abc.ceop.model.entities.Location;

public class DumpLocationsFromDBMain {

	private DumpLocationsFromDBMain() {}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/model-context.xml");

		LocationService locationService = context.getBean(LocationService.class);

		List<Location> locations = locationService.getAll();

		System.out.println("Locaciones total:" + locations.size());
		
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Locaciones"); 
			
			int i = 0;
			Row row = sheet.createRow(i);
			row.createCell(0).setCellValue("PAIS");
			row.createCell(1).setCellValue("ESTADO/PROVINCIA");
			row.createCell(2).setCellValue("SMALL CITY");
			row.createCell(3).setCellValue("LARGE CITY");
			row.createCell(4).setCellValue("PREFIJO");
			
			for(Location location : locations) {
				row = sheet.createRow(++i);
				row.createCell(0).setCellValue(location.getCountry().getName());
				row.createCell(1).setCellValue(location.getState());
				row.createCell(2).setCellValue(location.getSmallCity());
				row.createCell(3).setCellValue(location.getLargeCity());
				row.createCell(4).setCellValue(location.getNationalCode());
			}
			String dest = "resultados/dumplocations-" + new Date().getTime() + ".xls";
			new File("resultados/").mkdir();
			workbook.write(new FileOutputStream(dest));
			
			System.out.println("Se genero el archivo: " + dest);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
