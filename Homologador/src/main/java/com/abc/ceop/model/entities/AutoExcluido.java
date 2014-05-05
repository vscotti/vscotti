package com.abc.ceop.model.entities;

import javax.annotation.concurrent.Immutable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Cacheable
@Cache (usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Immutable

public class AutoExcluido {
	
	@Id
	@GeneratedValue
	@Column (name="id_autoexcluido")
	private Long id;
	
	@Column (nullable=true)
	private int id_contacto;
	@Column (nullable=true)
	private int suscriber;
	@Column (nullable=true)
	private String countryCampaing;
	
	public AutoExcluido(){}
	
	public int getId_contacto() {
		return id_contacto;
	}

	public void setId_contacto(int id_contacto) {
		this.id_contacto = id_contacto;
	}

	public int getSuscriber() {
		return suscriber;
	}

	public void setSuscriber(int suscriber) {
		this.suscriber = suscriber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getcountryCampaing() {
		return countryCampaing;
	}

	public void setcountryCampaing(String countryCampaing) {
		this.countryCampaing = countryCampaing;
	}

		
}
