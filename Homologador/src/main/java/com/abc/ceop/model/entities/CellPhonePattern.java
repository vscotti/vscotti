package com.abc.ceop.model.entities;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Immutable
public class CellPhonePattern {
	
	@SuppressWarnings("unused")
	@Id
	@GeneratedValue
	private Long id;
	private String pattern;
	private String cellIndicator;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Location location;
	
	public CellPhonePattern() {
		
	}
	
	public CellPhonePattern(String cellIndicator, String pattern, Location location) {
		this.pattern = pattern;
		this.cellIndicator = cellIndicator;
		this.location = location;
	}
	
	public String getPattern() {
		return pattern;
	}

	public String getCellIndicator() {
		return cellIndicator;
	}

	public Location getLocation() {
		return location;
	}

}
