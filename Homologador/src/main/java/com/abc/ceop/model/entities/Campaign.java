package com.abc.ceop.model.entities;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"country"}))
public class Campaign {

	@Id
	@GeneratedValue
	private Long id;
	private String country;
	private String campaign;
	private Long country_id;
	@Column (nullable=true)
	private String surveyNumberWebCati;
	@Column (nullable=true)
	private Integer sendMailOnOff;
	@Column (nullable=true)
	private Integer processWebSurveyOnOff;
	@Column (nullable=true)
	private String columnLogFilter;
	
	
	public Campaign(String country, String campaign, Long country_id,
			String surveyNumberWebCati, Integer sendMailOnOff, Integer processWebSurveyOnOff, String columnLogFilter) {
		super();
		this.country = country;
		this.campaign = campaign;
		this.country_id = country_id;
		this.surveyNumberWebCati = surveyNumberWebCati;
		this.sendMailOnOff = sendMailOnOff;
		this.processWebSurveyOnOff = processWebSurveyOnOff;
		this.setColumnLogFilter(columnLogFilter);
	}

	public Campaign() {
		
	}
	
	public Long getId() {
		return id;
	}
	public String getCountry() {
		return country;
	}
	public String getCampaign() {
		return campaign;
	}
	public Long getCountry_id() {
		return country_id;
	}
	public String getSurveyNumberWebCati () {
		return surveyNumberWebCati;
	}

	public Integer getSendMailOnOff() {
		return sendMailOnOff;
	}

	public void setSendMailOnOff(Integer sendMailOnOff) {
		this.sendMailOnOff = sendMailOnOff;
	}

	public Integer getProcessWebSurveyOnOff() {
		return processWebSurveyOnOff;
	}

	public void setProcessWebSurveyOnOff(Integer processWebSurveyOnOff) {
		this.processWebSurveyOnOff = processWebSurveyOnOff;
	}

    public String getColumnLogFilter() {
        return columnLogFilter;
    }

    public void setColumnLogFilter(String columnLogFilter) {
        this.columnLogFilter = columnLogFilter;
    }
	
}
