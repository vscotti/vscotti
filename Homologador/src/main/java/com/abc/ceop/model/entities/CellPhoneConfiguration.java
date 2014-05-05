package com.abc.ceop.model.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "country_id", "cellIndicator", "pattern" }))
public class CellPhoneConfiguration {

	@Id
	@GeneratedValue
	private Long id;

	private String cellIndicator;
	private String pattern;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Country country;
	
	public CellPhoneConfiguration() {
	}
	
	public CellPhoneConfiguration(String cellIndicator,
								  String pattern,
								  Country country) {
		this.cellIndicator = cellIndicator;
		this.pattern = pattern;
		this.country = country;
	}
	
	public Long getId() {
		return id;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public String getCellIndicator() {
		return cellIndicator;
	}
	public void setCellIndicator(String cellIndicator) {
		this.cellIndicator = cellIndicator;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
}
