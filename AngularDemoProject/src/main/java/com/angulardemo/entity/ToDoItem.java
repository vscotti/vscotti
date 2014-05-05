package com.angulardemo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity  
@Table(name = "todoitem")  
public class ToDoItem {

    @Id  
    @GeneratedValue 
	private Long id;
	
	private String description;
	
	private Integer priority;
	private Integer status;
	
	private Long creationDate;
	private Long dueDate;
	
	@ManyToOne
	private User user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Long getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Long creationDate) {
		this.creationDate = creationDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getDueDate() {
		return dueDate;
	}
	public void setDueDate(Long dueDate) {
		this.dueDate = dueDate;
	}
	
	@Override
	public boolean equals(Object obj) {
		return id.equals(((ToDoItem)obj).getId());
	}
}
