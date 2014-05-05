package com.rollingcode.test.interview.service;

import java.util.List;

import com.rollingcode.test.interview.entity.Category;
import com.rollingcode.test.interview.entity.Question;
import com.rollingcode.test.interview.entity.Seniority;

public interface TestInterviewBusinessDelegate {

	List<Category> getCategoriesActives();

	List<Category> getAllCategories();

	List<Seniority> getSenioritiesActives();

	List<Question> getQuestions(List<String> category, String senoirity, Integer amountQuestions);
	
	Category getCategoryByName(String name);

	void saveCategories(List<Category> categories);
	
	void saveCategory(Category category);
	
	void updateCategory(Category category);

	void saveSeniority(Seniority seniority);

	void saveSeniorities(List<Seniority> seniorities);
	
	void saveQuestion(Question question);
	
//	ExamenBO guardarExamen(ExamenVO examenVO);
//	
//	List<ExamenBO> generarRandomExamenes(List<Long> categorias, Long seniority, Integer numeroPreguntas, String propuesta);

}
