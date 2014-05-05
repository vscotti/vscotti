package com.abc.ceop.phoneapprover.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.abc.ceop.dao.ConfigurationVariableDao;
import com.abc.ceop.model.entities.Configuration.FirstProcessConfiguration;
import com.abc.ceop.model.entities.FirstProcessConfigurationVariable;
import com.abc.ceop.phoneapprover.service.FirstProcessConfigurationService;

@Service
public class FirstProcessConfigurationServiceImpl implements FirstProcessConfigurationService {

	private final ConfigurationVariableDao dao;
	
	@Autowired
	public FirstProcessConfigurationServiceImpl(ConfigurationVariableDao configurationVariableDao) {
		this.dao = configurationVariableDao;
	}
	
	@Cacheable("getConfigurationValue")
	@Override
	public String getConfigurationValue(FirstProcessConfiguration type) {
		FirstProcessConfigurationVariable lookup = dao.get(type);
		if (lookup != null) {
			return lookup.getValue();
		}
		return null;
	}

	@Override
	public List<String> getConfigurations(FirstProcessConfiguration type) {
		List<FirstProcessConfigurationVariable> list = dao.gets(type);
		List<String> values = new ArrayList<String>();
		for (FirstProcessConfigurationVariable firstProcessConfigurationVariable : list) {
			values.add(firstProcessConfigurationVariable.getValue());
		}
		return values;
	}

}
