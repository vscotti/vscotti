package com.abc.ceop.model.entities;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import com.abc.ceop.model.entities.Configuration.SecondProcessConfiguration;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Immutable
public class SecondProcessConfigurationVariable
	extends ConfigurationVariable<Configuration.SecondProcessConfiguration> {

	public SecondProcessConfigurationVariable(SecondProcessConfiguration name,
			String value) {
		super(name, value, null);
	}
	
	public SecondProcessConfigurationVariable(SecondProcessConfiguration name,
			String value, String description) {
		super(name, value, description);
	}
	
	public SecondProcessConfigurationVariable() {
	
	}

}
