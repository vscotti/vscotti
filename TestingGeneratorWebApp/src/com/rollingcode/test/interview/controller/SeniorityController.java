package com.rollingcode.test.interview.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rollingcode.test.interview.controller.form.CategorySeniorityInfo;
import com.rollingcode.test.interview.entity.Seniority;
import com.rollingcode.test.interview.service.TestInterviewBusinessDelegate;

@Controller
public class SeniorityController {

	@Resource  
	private TestInterviewBusinessDelegate testEntrevistaBusinessDelegate;  

	@RequestMapping("/addSeniority")
	public ModelAndView addSeniority() {
		System.out.println("addCategoria");
		ModelAndView mav = new ModelAndView("seniority");
		return mav;
	}
	
	@RequestMapping(value = "/saveSeniorityAjax", method = RequestMethod.POST)
	public @ResponseBody void saveSeniorityAjax(@RequestBody CategorySeniorityInfo categoriaSeniorityInfo) {
		System.out.println("saveSeniorityAjax");

		String name = categoriaSeniorityInfo.getName();
		
		Seniority sen = new Seniority();
		sen.setName(name);
		
		testEntrevistaBusinessDelegate.saveSeniority(sen);
	}
}
