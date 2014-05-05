package com.rollingcode.test.interview.controller.form;

public class QuestionInfo {

	private String categoryNameSelected;
	private String seniorityNameSelected;
	private String question;
	private String answer;
	
	public String getCategoryNameSelected() {
		return categoryNameSelected;
	}
	public void setCategoryNameSelected(String categoryNameSelected) {
		this.categoryNameSelected = categoryNameSelected;
	}
	public String getSeniorityNameSelected() {
		return seniorityNameSelected;
	}
	public void setSeniorityNameSelected(String seniorityNameSelected) {
		this.seniorityNameSelected = seniorityNameSelected;
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
}
