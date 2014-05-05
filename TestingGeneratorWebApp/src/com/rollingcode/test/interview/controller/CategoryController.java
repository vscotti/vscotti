package com.rollingcode.test.interview.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rollingcode.test.interview.controller.form.CategorySeniorityInfo;
import com.rollingcode.test.interview.entity.Category;
import com.rollingcode.test.interview.service.TestInterviewBusinessDelegate;

@Controller
public class CategoryController {

	@Resource  
	private TestInterviewBusinessDelegate testEntrevistaBusinessDelegate;  

	private Category category = null;
	private List<Category> categories = null;
	
	@RequestMapping("/addCategory")
	public ModelAndView addCategory() {
		System.out.println("addCategory");
		category = null;
		ModelAndView mav = new ModelAndView("category");
		mav.addObject("category", category);
		return mav;
	}
	
	@RequestMapping(value = "/updateCategory/{categoryName}", method = RequestMethod.GET)
	public ModelAndView updateCategory(@PathVariable String categoryName) {
		System.out.println("updateCategory");
		System.out.println(categoryName);
		ModelAndView mav = new ModelAndView("category");
		category = testEntrevistaBusinessDelegate.getCategoryByName(categoryName);
		System.out.println(category);
		mav.addObject("category", category);
		return mav;
	}

	@RequestMapping("/listCagetories")
	public ModelAndView listCagetories() {
		System.out.println("listCagetories");
		if(categories == null) {
			this.categories = testEntrevistaBusinessDelegate.getAllCategories();
		}
		ModelAndView mav = new ModelAndView("categories");
		return mav;
	}

	@RequestMapping(value = "/getAllCategories", method=RequestMethod.GET)
	public @ResponseBody List<Category> getAllCategories() {
		System.out.println("getAllCategories");
		if(categories == null) {
			this.categories = testEntrevistaBusinessDelegate.getAllCategories();
		}
		return categories;
	}
	
	@RequestMapping(value = "/saveCategoryAjax", method = RequestMethod.POST)
	public @ResponseBody String saveCategoryAjax(@RequestBody CategorySeniorityInfo categoriaSeniorityInfo) {
		System.out.println("saveCategoryAjax");

		String name = categoriaSeniorityInfo.getName();
		String description = categoriaSeniorityInfo.getDescription();
		
		if(testEntrevistaBusinessDelegate.getCategoryByName(name) != null) {
			return "FAIL";
		}
		
		Category cat = new Category();
		cat.setName(name);
		cat.setDescription(description);
		cat.setActive("Y");
		
		testEntrevistaBusinessDelegate.saveCategory(cat);
		
		this.categories = testEntrevistaBusinessDelegate.getAllCategories();
		
		return "SUCCESS";
		
	}
	
	@RequestMapping(value = "/updatedCategoriaAjax", method = RequestMethod.POST)
	public @ResponseBody String updatedCategoriaAjax(@RequestBody CategorySeniorityInfo categoriaSeniorityInfo) {
		System.out.println("updatedCategoriaAjax");

		String description = categoriaSeniorityInfo.getDescription();
		
		category.setDescription(description);
//		categoria.setActivo("Y");
			
		testEntrevistaBusinessDelegate.updateCategory(category);
		
		this.categories = testEntrevistaBusinessDelegate.getAllCategories();
		
		return "SUCCESS";
		
	}
}
