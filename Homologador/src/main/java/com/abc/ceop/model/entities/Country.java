package com.abc.ceop.model.entities;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Immutable
@Table(uniqueConstraints = {
		@UniqueConstraint(name = "countryName", columnNames = {"name"})
	})
public class Country {

	@Id
	private Long id;
	private String name;
	private String internationalCode;
	private String cellIndicator;
	private Long fixPhoneLength;
	private Long cellPhoneLength;
	
	public Country() {
		
	}
	
	public Country(Long id, 
				   String name, 
				   String internationalCode, 
				   String cellIndicator,
				   Long fixPhoneLength,
				   Long cellPhoneLength) {
		this.id = id;
		this.name = name;
		this.internationalCode = internationalCode;
		this.cellIndicator = cellIndicator;
		this.fixPhoneLength = fixPhoneLength;
		this.cellPhoneLength = cellPhoneLength;
	}

	public String getName() {
		return name;
	}
	public String getInternationalCode() {
		return internationalCode;
	}
	public String getCellIndicator() {
		return cellIndicator;
	}
	public Long getId() {
		return id;
	}
	public Long getFixPhoneLength() {
		return fixPhoneLength;
	}
	public Long getCellPhoneLength() {
		return cellPhoneLength;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
