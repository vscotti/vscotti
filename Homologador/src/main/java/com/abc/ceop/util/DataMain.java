package com.abc.ceop.util;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abc.ceop.dao.DataLoaderDao;
import com.abc.ceop.dao.LoadLocationsDAO;

public class DataMain {

	private DataMain() {}
	
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/model-context.xml");
		LoadLocationsDAO loadLocationsDao = (LoadLocationsDAO) context.getBean("loadLocationDao");
		DataLoaderDao dataLoaderDao = (DataLoaderDao) context.getBean("dataLoaderDao");
		

		dataLoaderDao.deleteTables();
		dataLoaderDao.loadCampaigns();
		dataLoaderDao.loadCsvFileConfigurations();
		dataLoaderDao.loadEmails();
		dataLoaderDao.loadQuestions();
		dataLoaderDao.loadThresholds(context);
		dataLoaderDao.loadLocationSearchConfig();
		dataLoaderDao.deleteConfigTables();
		dataLoaderDao.loadFirstProcessConfiguration();
		dataLoaderDao.loadSecondProcessConfiguration();
		
		if(args != null &&
				args.length >= 1) {
			dataLoaderDao.deleteLocationTables();
		}
	
		dataLoaderDao.loadCountries();
		
		Date startDateNacional = new Date();
		
		if(args != null &&
				args.length >= 1) {
			// Nacionales
			loadLocationsDao.loadLocation(context, args[0]);
		}
		
		Date startDateInternacional = new Date();
		
		if(args != null &&
				args.length >= 2) {
			 //Internacionales
			loadLocationsDao.loadLocation(context, args[1]);
		}
		
		Date finishDate = new Date();
		
		long diffNacional = (startDateInternacional.getTime() - startDateNacional.getTime())/1000;
		long diffInternacional = (startDateInternacional.getTime() - startDateNacional.getTime())/1000;
		
		System.out.println("Proceso finalizado...");
		System.out.println("Hora de inicio carga nacional: " + startDateNacional);		
		System.out.println("Hora de finalizacion carga nacional: " + startDateInternacional);		
		System.out.println("Demora carga nacional (segundos): " + diffNacional);
		System.out.println("Hora de inicio carga internacional: " + startDateInternacional);		
		System.out.println("Hora de finalizacion carga internacional: " + finishDate);		
		System.out.println("Demora carga internacional (segundos): " + diffInternacional);
	}
	
}
