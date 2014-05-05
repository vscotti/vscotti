package com.abc.ceop.model.entities;

import java.util.Date;

import javax.annotation.concurrent.Immutable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Cacheable
@Cache (usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Immutable


public class RecordDetail {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String phone;
	private String countryCampaing;
	private String suscriberId;

	private Date date;

	@Column (nullable=true)
	private String fileName;
	
	@Column (nullable=true)
	private String email;

//	@Column (nullable=true)
//	private int contacted;
		
	@Lob
	private String header;
	@Lob
	private String rowValues;
	
	
	public RecordDetail() {}
	
	public RecordDetail(String phone, Date date, String header, String rowValues,
			String countryCampaing, String suscriberId, String fileName, String email) {
		this.phone = phone;
		this.date = date;
		this.header = header;
		this.rowValues = rowValues;
		this.countryCampaing = countryCampaing;
		this.suscriberId = suscriberId;
		this.fileName = fileName;
		this.email= email;
//		this.contacted = contacted;
		//agregar int contacted en constructor
	}	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public String getCountryCampaing() {
		return countryCampaing;
	}
	public void setCountryCampaing(String countryCampaing) {
		this.countryCampaing = countryCampaing;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getHeader() {
		return header;
	}
//	public int getContacted() {
//		return contacted;
//	}
//	public void setContacted(int contacted) {
//		this.contacted = contacted;
//	}

	public void setHeader(String header) {
		this.header = header;
	}
	public String getRowValues() {
		return rowValues;
	}
	public void setRowValues(String rowValues) {
		this.rowValues = rowValues;
	}
	public String getSuscriberId() {
		return suscriberId;
	}
	public void setSuscriberId(String suscriberId) {
		this.suscriberId = suscriberId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
}
