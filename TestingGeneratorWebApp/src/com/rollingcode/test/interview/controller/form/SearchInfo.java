package com.rollingcode.test.interview.controller.form;

import java.util.List;


public class SearchInfo {

	private List<Long> idCategoriaSelected;
	
	private Long idSenioritySelected;
	
	private Integer questionsByQuery;
	private Integer questionsByCategory;
	private Integer amountExams;
	
	private String message;
	private String examenProposal;
	
	public List<Long> getIdCategoriaSelected() {
		return idCategoriaSelected;
	}
	public void setIdCategoriaSelected(List<Long> idCategoriaSelected) {
		this.idCategoriaSelected = idCategoriaSelected;
	}
	public Long getIdSenioritySelected() {
		return idSenioritySelected;
	}
	public void setIdSenioritySelected(Long idSenioritySelected) {
		this.idSenioritySelected = idSenioritySelected;
	}
	public Integer getQuestionsByQuery() {
		return questionsByQuery;
	}
	public void setQuestionsByQuery(Integer questionsByQuery) {
		this.questionsByQuery = questionsByQuery;
	}
	public Integer getQuestionsByCategory() {
		return questionsByCategory;
	}
	public void setQuestionsByCategory(Integer questionsByCategory) {
		this.questionsByCategory = questionsByCategory;
	}
	public Integer getAmountExams() {
		return amountExams;
	}
	public void setAmountExams(Integer amountExams) {
		this.amountExams = amountExams;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getExamenProposal() {
		return examenProposal;
	}
	public void setExamenProposal(String examenProposal) {
		this.examenProposal = examenProposal;
	}
}
