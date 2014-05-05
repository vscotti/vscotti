package com.abc.ceop.model.entities;

import java.util.Date;

import javax.annotation.concurrent.Immutable;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache (usage = CacheConcurrencyStrategy.READ_ONLY)
@Immutable

public class EmailLogs {


	@Id
	@GeneratedValue
	private Long id;
	
	private Date date;
	
	@ManyToOne
	private Campaign campaign;
		
	
	public EmailLogs (Date date, Campaign campaign){
		
		this.date=date;
		this.campaign=campaign;
	}

	public EmailLogs() {}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}


}
