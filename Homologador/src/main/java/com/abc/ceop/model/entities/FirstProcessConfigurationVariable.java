package com.abc.ceop.model.entities;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import com.abc.ceop.model.entities.Configuration.FirstProcessConfiguration;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Immutable
public class FirstProcessConfigurationVariable
		extends ConfigurationVariable<Configuration.FirstProcessConfiguration> {

	public FirstProcessConfigurationVariable(FirstProcessConfiguration name,
			String value) {
		super(name, value, null);
	}
	
	public FirstProcessConfigurationVariable(FirstProcessConfiguration name,
			String value, String description) {
		super(name, value, description);
	}
	
	public FirstProcessConfigurationVariable() {
	
	}
	
}
