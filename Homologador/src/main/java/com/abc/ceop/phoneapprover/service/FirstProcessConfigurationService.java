package com.abc.ceop.phoneapprover.service;

import java.util.List;

import com.abc.ceop.common.service.ConfigurationService;
import com.abc.ceop.model.entities.Configuration;
import com.abc.ceop.model.entities.Configuration.FirstProcessConfiguration;

public interface FirstProcessConfigurationService extends ConfigurationService<Configuration.FirstProcessConfiguration> {
	List<String> getConfigurations(FirstProcessConfiguration type);
}