package com.abc.ceop.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.CountryService;
import com.abc.ceop.dao.CountryDao;
import com.abc.ceop.model.entities.Country;

@Service
public class CountryServiceImpl implements CountryService {

	private final CountryDao countryDao;
	
	@Autowired
	public CountryServiceImpl(CountryDao countryDao) {
		this.countryDao = countryDao;
	}

	@Override
	public Country getCountryByName(String name) {
		return countryDao.getCountryByName(name);
	}

	@Override
	public Country getCountryById(Long id) {
		return countryDao.getCountryById(id);
	}

}
