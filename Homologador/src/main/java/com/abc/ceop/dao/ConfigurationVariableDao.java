package com.abc.ceop.dao;

import java.util.List;

import com.abc.ceop.model.entities.Configuration.FirstProcessConfiguration;
import com.abc.ceop.model.entities.Configuration.SecondProcessConfiguration;
import com.abc.ceop.model.entities.FirstProcessConfigurationVariable;
import com.abc.ceop.model.entities.SecondProcessConfigurationVariable;

public interface ConfigurationVariableDao {
	
	List<FirstProcessConfigurationVariable> gets(FirstProcessConfiguration name);
	
	FirstProcessConfigurationVariable get(FirstProcessConfiguration name);
	
	SecondProcessConfigurationVariable get(SecondProcessConfiguration name);
	
}
