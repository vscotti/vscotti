package com.abc.ceop.model.dto;

import java.util.List;

import com.abc.ceop.model.entities.Campaign;
import com.abc.ceop.model.entities.Thresholds;


public class SecondProcessCommonData {
	
	private String dateNoSuscriber;
	private String tempPath;
	private String currentTime;
	private String campaigndate;
	private String campaignIdTrabajo;
	private String idQuestionYear;
	private String idQuestionMonth;
	private String idQuestionDay;
	
	private List<DialedRecord> dialedRecords;
	
	private List<Thresholds> flagEfectivoCondition;
	private List<Thresholds> flagSOSCondition;
	private List<Thresholds> cutConditions;
	
	private Campaign campaign;

	public List<DialedRecord> getDialedRecords() {
		return dialedRecords;
	}
	public void setDialedRecords(List<DialedRecord> dialedRecords) {
		this.dialedRecords = dialedRecords;
	}
	public String getCampaignIdTrabajo() {
		return campaignIdTrabajo;
	}
	public void setCampaignIdTrabajo(String campaignIdTrabajo) {
		this.campaignIdTrabajo = campaignIdTrabajo;
	}
	public String getDateNoSuscriber() {
		return dateNoSuscriber;
	}
	public void setDateNoSuscriber(String dateNoSuscriber) {
		this.dateNoSuscriber = dateNoSuscriber;
	}
	public String getTempPath() {
		return tempPath;
	}
	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public String getCampaigndate() {
		return campaigndate;
	}
	public void setCampaigndate(String campaigndate) {
		this.campaigndate = campaigndate;
	}
	public Campaign getCampaign() {
		return campaign;
	}
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	public String getIdQuestionYear() {
		return idQuestionYear;
	}
	public void setIdQuestionYear(String idQuestionYear) {
		this.idQuestionYear = idQuestionYear;
	}
	public String getIdQuestionMonth() {
		return idQuestionMonth;
	}
	public void setIdQuestionMonth(String idQuestionMonth) {
		this.idQuestionMonth = idQuestionMonth;
	}
	public String getIdQuestionDay() {
		return idQuestionDay;
	}
	public void setIdQuestionDay(String idQuestionDay) {
		this.idQuestionDay = idQuestionDay;
	}
	public List<Thresholds> getFlagEfectivoCondition() {
		return flagEfectivoCondition;
	}
	public void setFlagEfectivoCondition(List<Thresholds> flagEfectivoCondition) {
		this.flagEfectivoCondition = flagEfectivoCondition;
	}
	public List<Thresholds> getFlagSOSCondition() {
		return flagSOSCondition;
	}
	public void setFlagSOSCondition(List<Thresholds> flagSOSCondition) {
		this.flagSOSCondition = flagSOSCondition;
	}
	public List<Thresholds> getCutConditions() {
		return cutConditions;
	}
	public void setCutConditions(List<Thresholds> cutConditions) {
		this.cutConditions = cutConditions;
	}
	
	public String getCountryName() {
		String countryName = "";
		if (getCampaign() != null) {
			countryName = getCampaign().getCountry();
		}
		return countryName;
	}
}
