package com.abc.ceop.model.entities;

import java.util.Map;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import com.abc.ceop.util.StringUtil;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Immutable
@Table(
		uniqueConstraints = @UniqueConstraint(columnNames = { "country_id", "state", "largeCity", "smallCity" })
	)
public class Location {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	private Country country;
	private String state;
	private String largeCity;


	private String smallCity;

	private String nationalCode;
	private String fixedPhoneDigitsCount;
	private String cellPhoneDigitsCount;

	public static Location createLocation(String country, 
										String state,
										String largeCity, 
										String smallCity) {
		Country countryObj = new Country(null, country, null, null, null, null);
		Location location = new Location(smallCity, largeCity, state, countryObj,
				null, null, null);
		return location;
	}
	
	public Location() {

	}
	
	public Location(String smallCity, 
			String largeCity, 
			String state,
			Country country, 
			String nationalCode, 
			String fixedPhoneDigitsCount,
			String cellPhoneDigitsCount) {
		if (country != null) {
			this.country = country;
		}
		if (StringUtils.isNotBlank(state)) {
			this.state = StringUtil.removeDiacritics(state);
		}
		if (StringUtils.isNotBlank(largeCity)) {
			this.largeCity = StringUtil.removeDiacritics(largeCity);
		}
		if (StringUtils.isNotBlank(smallCity)) {
			this.smallCity = StringUtil.removeDiacritics(smallCity);
		}
		this.nationalCode = nationalCode;
		this.fixedPhoneDigitsCount = fixedPhoneDigitsCount;
		this.cellPhoneDigitsCount = cellPhoneDigitsCount;
	}

	
	public void setCountry(Country country) {
		this.country = country;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setLargeCity(String largeCity) {
		this.largeCity = largeCity;
	}

	public void setSmallCity(String smallCity) {
		this.smallCity = smallCity;
	}
	
	public Long getId() {
		return id;
	}
	public Country getCountry() {
		return country;
	}
	public String getState() {
		return state;
	}
	public String getLargeCity() {
		return largeCity;
	}
	public String getSmallCity() {
		return smallCity;
	}	
	public String getNationalCode() {
		return nationalCode;
	}
	public String getFixedPhoneDigitsCount() {
		return fixedPhoneDigitsCount;
	}
	public String getCellPhoneDigitsCount() {
		return cellPhoneDigitsCount;
	}	

	@Override
	public String toString() {
		return new StringBuilder()
				 .append("Country / State / Large City / Small City: ")
				 .append(country)
				 .append(" / ")
				 .append(state)
				 .append(" / ")
				 .append(largeCity)
				 .append(" / ")
				 .append(smallCity)
				 .append(" ")
				 .toString();
	}

	@Override
	public boolean equals(Object anObject) {
		if (anObject instanceof Location) {
			Location location = (Location) anObject;
			return ObjectUtils.equals(country.getName(), location.getCountry().getName())
					&& ObjectUtils.equals(state, location.getState())
					&& ObjectUtils.equals(largeCity, location.getLargeCity())
					&& ObjectUtils.equals(smallCity, location.getSmallCity());
		} else {
			return false;
		}
	}
	
	
	public boolean compareWith(Location loc, Map<String, Boolean> searchLocationMap) {
		if(locationPropertyisNotEqualTo("smallCity", searchLocationMap, smallCity, loc.getSmallCity()) ||
		   locationPropertyisNotEqualTo("largeCity", searchLocationMap, largeCity, loc.getLargeCity()) ||
		   locationPropertyisNotEqualTo("state", searchLocationMap, state, loc.getState())         ||
		   locationPropertyisNotEqualTo("country", searchLocationMap,country.getName(), loc.getCountry().getName())) {
			return false;
		}
		return true;
	}
	
	private boolean locationPropertyisNotEqualTo(String locationProperty,Map<String, Boolean> searchLocationMap,String property, String propertyToCompare) {
		if(searchLocationMap.get(locationProperty)) {
			return !ObjectUtils.equals(property, propertyToCompare);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "id");
	}

}
