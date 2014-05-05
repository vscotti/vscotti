package com.abc.ceop.dao;

import org.springframework.context.ApplicationContext;


public interface LoadLocationsDAO {
		
	void loadLocation(ApplicationContext context, String file);

	void deleteLocationTables();
	
	
}
