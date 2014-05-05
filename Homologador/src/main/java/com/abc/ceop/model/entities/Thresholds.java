package com.abc.ceop.model.entities;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
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
public class Thresholds {

	@SuppressWarnings("unused")
	@Id
	@GeneratedValue
	private Long id;
	
	private String destination;
	private String description;
	private String conditionRule;
	
	@ManyToOne
	private Campaign campaign;
	
	public Thresholds(String destination, 
					String description,
					String conditionRule,
					Campaign campaign) {
		this.destination = destination;
		this.description = description;
		this.conditionRule = conditionRule;
		this.campaign = campaign;
	}
	
	public Thresholds() {
	}

	public String getDestination() {
		return destination;
	}
	public String getDescription() {
		return description;
	}
	public String getConditionRule() {
		return conditionRule;
	}
	public Campaign getCampaign() {
		return campaign;
	}
}
