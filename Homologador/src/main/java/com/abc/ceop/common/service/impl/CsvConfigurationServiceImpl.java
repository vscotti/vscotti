package com.abc.ceop.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.CsvFileConfigurationService;
import com.abc.ceop.dao.CsvFileConfigurationDao;
import com.abc.ceop.model.entities.CsvFileConfiguration;

@Service
public class CsvConfigurationServiceImpl implements CsvFileConfigurationService {

	private final CsvFileConfigurationDao csvFileConfigurationDao;
	
	@Autowired
	public CsvConfigurationServiceImpl(CsvFileConfigurationDao csvFileConfigurationDao) {
		this.csvFileConfigurationDao = csvFileConfigurationDao;
	}
	
	@Override
	public CsvFileConfiguration getCsvFileConfiguration(String code) {
		return csvFileConfigurationDao.getCsvFileConfiguration(code);
	}

}
