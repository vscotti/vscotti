package com.abc.ceop.model.dto;

import java.util.Date;
import java.util.List;

import com.abc.ceop.model.entities.CellPhoneConfiguration;
import com.abc.ceop.model.entities.Location;

public class FirstProcessCommonData {
	
	private Integer maxNationalCodeLength;
	private Integer timeWithoutData;
	private Integer minPhoneCount;
	
	private String localPrefix;
	private String internationalPrefix;
	
	private Date lastDateForCountry;
	private Date lastSentMail;
	
	private boolean dialedDirect;
	
	private List<CellPhoneConfiguration> cellPhoneConfigurationForCountry;
	
	public List<CellPhoneConfiguration> getCellPhoneConfigurationForCountry() {
		return cellPhoneConfigurationForCountry;
	}
	public void setCellPhoneConfigurationForCountry(
			List<CellPhoneConfiguration> cellPhoneConfigurationForCountry) {
		this.cellPhoneConfigurationForCountry = cellPhoneConfigurationForCountry;
	}
	public boolean isDialedDirect() {
		return dialedDirect;
	}
	public void setDialedDirect(boolean dialedDirect) {
		this.dialedDirect = dialedDirect;
	}
	private List<Location> locationsForcountry;
	
	public Integer getMaxNationalCodeLength() {
		return maxNationalCodeLength;
	}
	public void setMaxNationalCodeLength(Integer maxNationalCodeLength) {
		this.maxNationalCodeLength = maxNationalCodeLength;
	}
	public String getLocalPrefix() {
		return localPrefix;
	}
	public void setLocalPrefix(String localPrefix) {
		this.localPrefix = localPrefix;
	}
	public String getInternationalPrefix() {
		return internationalPrefix;
	}
	public void setInternationalPrefix(String internationalPrefix) {
		this.internationalPrefix = internationalPrefix;
	}
	public List<Location> getLocationsForcountry() {
		return locationsForcountry;
	}
	public void setLocationsForcountry(List<Location> locationsForcountry) {
		this.locationsForcountry = locationsForcountry;
	}
	public Integer getTimeWithoutData() {
		return timeWithoutData;
	}
	public void setTimeWithoutData(Integer timeWithoutData) {
		this.timeWithoutData = timeWithoutData;
	}
	public Integer getMinPhoneCount() {
		return minPhoneCount;
	}
	public void setMinPhoneCount(Integer minPhoneCount) {
		this.minPhoneCount = minPhoneCount;
	}
	public Date getLastDateForCountry() {
		return lastDateForCountry;
	}
	public void setLastDateForCountry(Date lastDateForCountry) {
		this.lastDateForCountry = lastDateForCountry;
	}
	public Date getLastSentMail() {
		return lastSentMail;
	}
	public void setLastSentMail(Date lastSentMail) {
		this.lastSentMail = lastSentMail;
	}
}
