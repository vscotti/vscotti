package com.abc.ceop.model.dto;

import com.abc.ceop.model.entities.Campaign;

public class FirstProcessResultInfo {
	
	private FirstProcessStats stats = new FirstProcessStats();
	private String result;
	private String fileName;
	
	private boolean noGeneratedFile;
	
	private Integer noDBDetected;
	private Integer averaragePhones;
	
	private Campaign campaignCountry;
	
	public String getResult() {
		return result;
	}
	public FirstProcessStats getStats() {
		return stats;
	}
	public Campaign getCampaignCountry() {
		return campaignCountry;
	}
	public Integer getNoDBDetected() {
		return noDBDetected;
	}
	public Integer getAveraragePhones() {
		return averaragePhones;
	}

	public void setResult(String result) {
		this.result = result;
	}
	public void setCampaignCountry(Campaign campaignCountry) {
		this.campaignCountry = campaignCountry;
	}
	public void setNoDBDetected(Integer noDBDetected) {
		this.noDBDetected = noDBDetected;
	}
	public void setAveraragePhones(Integer averaragePhones) {
		this.averaragePhones = averaragePhones;
	}
	public boolean isNoGeneratedFile() {
		return noGeneratedFile;
	}
	public void setNoGeneratedFile(boolean noGeneratedFile) {
		this.noGeneratedFile = noGeneratedFile;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
