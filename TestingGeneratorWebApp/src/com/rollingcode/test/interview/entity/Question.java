package com.rollingcode.test.interview.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document  
public class Question {

	@Id  
	private String Id;
	private String question;
	private String answer;
	private String active;

	@DBRef
	private Category category;

	@DBRef
	private Seniority seniority;

	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Seniority getSeniority() {
		return seniority;
	}
	public void setSeniority(Seniority seniority) {
		this.seniority = seniority;
	}
}
