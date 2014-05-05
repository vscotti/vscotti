package com.abc.ceop.common.service;

import com.abc.ceop.model.entities.Configuration;

public interface ConfigurationService<T extends Configuration> {

	String getConfigurationValue(T type);
	
}
