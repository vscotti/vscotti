package com.rollingcode.test.interview.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rollingcode.test.interview.entity.Category;
import com.rollingcode.test.interview.entity.Seniority;
import com.rollingcode.test.interview.service.TestInterviewBusinessDelegate;

@Controller
public class InsertDataController {

	@Resource  
	private TestInterviewBusinessDelegate testEntrevistaBusinessDelegate;  

	@RequestMapping("/insertInitialData")
	public ModelAndView goToMainPage() {
		
		List<Category> categories = new ArrayList<Category>();
		categories.add(new Category("Java","Y"));
		categories.add(new Category("MYSQL","Y"));
		categories.add(new Category("JDBC","Y"));
		categories.add(new Category("SAP","N"));

		List<Seniority> seniorities = new ArrayList<Seniority>();
		seniorities.add(new Seniority("SSR"));
		seniorities.add(new Seniority("SR"));
		seniorities.add(new Seniority("JR"));
		
		testEntrevistaBusinessDelegate.saveCategories(categories);
		testEntrevistaBusinessDelegate.saveSeniorities(seniorities);
		
		ModelAndView mav = new ModelAndView("mainPage");
		return mav;
	}
}
