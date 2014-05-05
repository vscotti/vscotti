package com.abc.ceop.model.entities;

import java.util.Date;

import javax.annotation.concurrent.Immutable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;




@Entity
@Cacheable
@Cache (usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Immutable
public class EmailSuscriberWeb {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;
	private String suscriber;
	private String email;
	private Date date;
	private int webSurveyCompleted;
	private String country;
	public EmailSuscriberWeb () {}

	public EmailSuscriberWeb(String country, String suscriber, String email, Date date,
			int webSurveyCompleted) {
		super();
		this.country = country;
		this.suscriber = suscriber;
		this.email = email;
		this.date = date;
		this.webSurveyCompleted = webSurveyCompleted;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSuscriber() {
		return suscriber;
	}

	public void setSuscriber(String suscriber) {
		this.suscriber = suscriber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getWebSurveyCompleted() {
		return webSurveyCompleted;
	}

	public void setWebSurveyompleted(int webSurveyCompleted) {
		this.webSurveyCompleted = webSurveyCompleted;
	}
	

	
	
	

}
