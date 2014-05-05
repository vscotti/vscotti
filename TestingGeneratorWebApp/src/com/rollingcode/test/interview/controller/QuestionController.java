package com.rollingcode.test.interview.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rollingcode.test.interview.controller.form.QuestionInfo;
import com.rollingcode.test.interview.entity.Category;
import com.rollingcode.test.interview.entity.Question;
import com.rollingcode.test.interview.entity.Seniority;
import com.rollingcode.test.interview.service.TestInterviewBusinessDelegate;

@Controller
public class QuestionController {

	@Resource  
	private TestInterviewBusinessDelegate testEntrevistaBusinessDelegate;  

	private List<Category> categories;
	private List<Seniority> seniorities;

	@RequestMapping("/addQuestion")
	public ModelAndView addQuestion() {
		System.out.println("addQuestion");
		this.categories = testEntrevistaBusinessDelegate.getCategoriesActives();
		this.seniorities = testEntrevistaBusinessDelegate.getSenioritiesActives();
		ModelAndView mav = new ModelAndView("question");
		return mav;
	}
	
	@RequestMapping(value="/getCategories", method=RequestMethod.GET)
	public @ResponseBody List<Category> getCategories() {
		System.out.println("getCategories");
		return this.categories;
	}

	@RequestMapping(value="/getSeniorities", method=RequestMethod.GET)
	public @ResponseBody List<Seniority> getSeniorities() {
		System.out.println("getSeniorities");
		return this.seniorities;
	}
	
	@RequestMapping(value = "/saveQuestionAjax", method = RequestMethod.POST)
	public @ResponseBody void saveQuestionAjax(@RequestBody QuestionInfo preguntaInfo) {
		System.out.println("saveQuestion");

		String question = preguntaInfo.getQuestion();
		String answer = preguntaInfo.getAnswer();
		String categoryName = preguntaInfo.getCategoryNameSelected();
		String seniorityName = preguntaInfo.getSeniorityNameSelected();
		
		System.out.println(question);
		System.out.println(answer);
		System.out.println(categoryName);
		System.out.println(seniorityName);
		
		Question quest = new Question();
		quest.setQuestion(question);
		quest.setAnswer(answer);
		quest.setCategory(getCategory(categoryName));
		quest.setSeniority(getSeniority(seniorityName));
		quest.setActive("Y");
		
		testEntrevistaBusinessDelegate.saveQuestion(quest);
	}
	
	private Category getCategory(String name) {
		for (Category categoria : categories) {
			if(categoria != null &&
					categoria.getName() != null &&
					categoria.getName().equals(name)) {
				return categoria;
			}
		}
		return null;
	}
	
	private Seniority getSeniority(String name) {
		for (Seniority seniority : seniorities) {
			if(seniority != null &&
					seniority.getName() != null &&
					seniority.getName().equals(name)) {
				return seniority;
			}
		}
		return null;
	}
}
