package com.rollingcode.test.interview.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document  
public class Category {

	@Id  
	private String Id;
	
	@Indexed(unique = true)
	private String name;
	
	private String description;
	private String active;

	public Category() {
	}
	
	public Category(String name) {
		this.name = name;
		this.active = "Y";
	}
	
	public Category(String name, String active) {
		this.name = name;
		this.active = active;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
}
