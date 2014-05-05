package com.rollingcode.test.interview.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document  
public class Exam {

	@Id  
	private String Id;
	private String examNumber;
	private String proposal;
	private String creationDate;
	
	@DBRef
	private Question preguntas;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getExamNumber() {
		return examNumber;
	}
	public void setExamNumber(String examNumber) {
		this.examNumber = examNumber;
	}
	public String getProposal() {
		return proposal;
	}
	public void setProposal(String proposal) {
		this.proposal = proposal;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public Question getPreguntas() {
		return preguntas;
	}
	public void setPreguntas(Question preguntas) {
		this.preguntas = preguntas;
	}
}
