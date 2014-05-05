package com.abc.ceop.model.entities;

import javax.persistence.Cacheable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Immutable
@MappedSuperclass
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"name"}))
public abstract class ConfigurationVariable<T extends Configuration> {

	@SuppressWarnings("unused")
	@Id
	@GeneratedValue
	private Long id;
	@Enumerated(EnumType.STRING)
	private T name;
	private String value;
	private String description;

	public ConfigurationVariable(T name, String value, String description) {
		this.name = name;
		this.value = value;
		this.description = description;
	}
	
	public ConfigurationVariable() {
	}
	
	public T getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

}
