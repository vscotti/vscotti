package com.abc.ceop.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.abc.ceop.common.service.CellPhoneConfigurationService;
import com.abc.ceop.dao.CellPhoneConfigurationDao;
import com.abc.ceop.model.entities.CellPhoneConfiguration;
import com.abc.ceop.model.entities.Country;

@Repository
public class CellPhoneConfigurationServiceImpl implements CellPhoneConfigurationService {
	
	@Resource
	private CellPhoneConfigurationDao cellPhoneConfigurationDao;

	@Override
	public List<CellPhoneConfiguration> getAll() {
		return cellPhoneConfigurationDao.getAll();
	}

	@Override
	public List<CellPhoneConfiguration> getCellPhoneConfigurationByCountrry(
			Country country) {
		return cellPhoneConfigurationDao.getCellPhoneConfigurationByCountrry(country);
	}
	
}