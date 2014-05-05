package com.abc.ceop.model.entities;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Immutable
@Table(uniqueConstraints = {
		@UniqueConstraint(name = "both", columnNames = {"campaign", "question_code"})
	})
public class Questionnaire {

	@SuppressWarnings("unused")
	@Id
	@GeneratedValue
	private Long id;
	private String campaign;
	private String value;
	@Column(nullable = true)
	private int valueWebCati;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private PollQuestionMatcher question;
	
	public Questionnaire() {
	}
	
	public Questionnaire(String campaign,
						 String value,
						 PollQuestionMatcher question, int valueWebCati ) {
		this.campaign = campaign;
		this.value = value;
		this.question = question;
		this.valueWebCati = valueWebCati;
	}

	public String getCampaign() {
		return campaign;
	}
	public String getValue() {
		return value;
	}
	public PollQuestionMatcher getQuestion() {
		return question;
	}

	public int getValueWebCati() {
		return valueWebCati;
	}

	public void setValueWebCati(int valueWebCati) {
		this.valueWebCati = valueWebCati;
	}
	
}
