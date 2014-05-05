package com.abc.ceop.dao;

import java.util.List;

import com.abc.ceop.model.entities.CellPhoneConfiguration;
import com.abc.ceop.model.entities.Country;


public interface CellPhoneConfigurationDao {
	
		List<CellPhoneConfiguration> getAll();
		
		List<CellPhoneConfiguration> getCellPhoneConfigurationByCountrry(Country country);
}
