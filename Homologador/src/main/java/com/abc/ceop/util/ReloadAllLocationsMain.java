package com.abc.ceop.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abc.ceop.dao.LoadLocationsDAO;

public class ReloadAllLocationsMain {

	private ReloadAllLocationsMain() {}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/model-context.xml");
		
		LoadLocationsDAO loadLocationsDao = (LoadLocationsDAO) context.getBean("loadLocationDao");

//		LocalContainerEntityManagerFactoryBean emf = context.getBean(LocalContainerEntityManagerFactoryBean.class);
//		EntityManager em = emf.getObject().createEntityManager();
		
		if(args != null &&
				args.length >= 1) {
//			DataLoader.deleteLocationTables(em);
			loadLocationsDao.deleteLocationTables();
		}
		
		if(args != null &&
				args.length >= 1) {
			// Nacionales
//			DataLoader.loadLocations(context,  args[0], 0, 1, 2, 3, 4);	
			loadLocationsDao.loadLocation(context, args[0]);
		}
		
		if(args != null &&
				args.length >= 2) {
			// Internacionales
//			DataLoader.loadLocations(context, em, args[1], 0, 1, 2, 3, 4);	
			loadLocationsDao.loadLocation(context, args[1]);
		}
	}
	
}
