package com.rollingcode.test.interview.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Seniority {

	@Id  
	private String Id;
	
	@Indexed(unique = true)
	private String name;

	public Seniority() {
	}
	
	public Seniority(String name) {
		this.name = name;
	}
	
	public Seniority(String Id, String name) {
		this.Id = Id;
		this.name = name;
	}
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
