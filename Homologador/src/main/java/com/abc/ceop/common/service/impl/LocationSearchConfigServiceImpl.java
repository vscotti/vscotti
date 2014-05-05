package com.abc.ceop.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.LocationSearchConfigService;
import com.abc.ceop.dao.LocationSearchConfigDao;
import com.abc.ceop.model.entities.LocationSearchConfig;
@Service
public class LocationSearchConfigServiceImpl implements LocationSearchConfigService {
	
	
	private final LocationSearchConfigDao locationSearchConfigDao;
	
	
	@Autowired
	public LocationSearchConfigServiceImpl(
			LocationSearchConfigDao locationSearchConfigDao) {
		super();
		this.locationSearchConfigDao = locationSearchConfigDao;
	}



	@Override
	public LocationSearchConfig getLocationSearchConfig(String countryCampaign) {
		
		return locationSearchConfigDao.getLocationSearchConfig(countryCampaign);
	}

}
