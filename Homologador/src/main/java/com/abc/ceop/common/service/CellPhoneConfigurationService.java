package com.abc.ceop.common.service;

import java.util.List;

import com.abc.ceop.model.entities.CellPhoneConfiguration;
import com.abc.ceop.model.entities.Country;


public interface CellPhoneConfigurationService {
	
	List<CellPhoneConfiguration> getAll();
	
	List<CellPhoneConfiguration> getCellPhoneConfigurationByCountrry(Country country);
}
