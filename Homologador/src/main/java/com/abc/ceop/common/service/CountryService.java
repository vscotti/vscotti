package com.abc.ceop.common.service;

import com.abc.ceop.model.entities.Country;

public interface CountryService {

	Country getCountryByName(String name);
	
	Country getCountryById(Long id);
}
