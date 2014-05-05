package com.abc.ceop.util;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abc.ceop.dao.InternationalizableDao;
import com.abc.ceop.dao.LoadLocationsDAO;

public class LocationDataMain {

    private static final String XLS_FILE_NAME = "internacional.xls";
    private static final String XLS_FILE_NAME_2 = "internacional2.xls";

    private LocationDataMain() {}
	
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/model-context.xml");
		LoadLocationsDAO loadLocationsDao = (LoadLocationsDAO) context.getBean("loadLocationDao");
		InternationalizableDao internationalizableDao = (InternationalizableDao) context.getBean("internationalizableDao");

		Date startDate = new Date();
		
		System.out.println("Cargando Locaciones...");
		
				
		if(args != null &&
				args.length > 0) {
		loadLocationsDao.loadLocation(context, args[0]);
		internationalizableDao.moveToInternational(args[0], XLS_FILE_NAME, XLS_FILE_NAME_2);
		
		} else {
			System.out.println("No se encontro el archivo data.xls.");
		}
		
		Date finishDate = new Date();
	    long diff = (finishDate.getTime() - startDate.getTime())/1000;
	    
		System.out.println("Proceso finalizado...");
		System.out.println("Hora de inicio: " + startDate);		
		System.out.println("Hora de finalizacion: " + finishDate);		
		System.out.println("Demora (segundos): " + diff);
	}
	
	
	
}
