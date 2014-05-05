package com.abc.ceop.dao;

import com.abc.ceop.model.entities.Country;

public interface CountryDao {
		
	Country getCountryByName(String name);

	Country getCountryById(Long id);
}
