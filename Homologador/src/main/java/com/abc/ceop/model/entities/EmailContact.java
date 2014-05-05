package com.abc.ceop.model.entities;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Immutable
public class EmailContact {
	
	@SuppressWarnings("unused")
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String surname;
	private String emailAddress;
	private String type;
	private String campaign;
	
	public EmailContact() {
	}
	
	public EmailContact(String name, String surname, String email, String type, String campaign) {
		this.name = name;
		this.surname = surname;
		this.emailAddress = email;
		this.type = type;
		this.campaign = campaign;
	}

	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getEmail() {
		return emailAddress;
	}
	public String getType() {
		return type;
	}
	public String getCampaign() {
		return campaign;
	}
	
}
