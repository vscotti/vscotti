package com.abc.ceop.phonepoll.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.abc.ceop.dao.ConfigurationVariableDao;
import com.abc.ceop.model.entities.Configuration.SecondProcessConfiguration;
import com.abc.ceop.model.entities.SecondProcessConfigurationVariable;
import com.abc.ceop.phonepoll.service.SecondProcessConfigurationService;

@Service
public class SecondProcessConfigurationServiceImpl implements SecondProcessConfigurationService {

	private final ConfigurationVariableDao dao;
	
	@Autowired
	public SecondProcessConfigurationServiceImpl(ConfigurationVariableDao configurationVariableDao) {
		this.dao = configurationVariableDao;
	}
	
	@Cacheable("getConfigurationValue")
	@Override
	public String getConfigurationValue(SecondProcessConfiguration type) {
		SecondProcessConfigurationVariable lookup = dao.get(type);
		if (lookup != null) {
			return lookup.getValue();
		}
		return null;
	}

}
