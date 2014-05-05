package com.abc.ceop.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LocationSearchConfig {
	@SuppressWarnings("unused")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String countryCampaign;
	private boolean country;
	private boolean state;
	private boolean largeCity;
	private boolean smallCity;
	
	
	public LocationSearchConfig() {
		
	}


	public LocationSearchConfig (String countryCampaign, boolean country, boolean state, boolean largeCity,
			boolean smallCity) {
		
		this.countryCampaign = countryCampaign;
		this.country = country;
		this.state = state;
		this.largeCity = largeCity;
		this.smallCity = smallCity;
	}


	public String getCountryCampaign() {
		return countryCampaign;
	}


	public void setCountryCampaign(String countryCampaign) {
		this.countryCampaign = countryCampaign;
	}


	public boolean isCountry() {
		return country;
	}


	public void setCountry(boolean country) {
		this.country = country;
	}


	public boolean isState() {
		return state;
	}


	public void setState(boolean state) {
		this.state = state;
	}


	public boolean isLargeCity() {
		return largeCity;
	}


	public void setLargeCity(boolean largeCity) {
		this.largeCity = largeCity;
	}


	public boolean isSmallCity() {
		return smallCity;
	}


	public void setSmallCity(boolean smallCity) {
		this.smallCity = smallCity;
	}


	
	

}
