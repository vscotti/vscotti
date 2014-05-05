package com.rollingcode.test.interview.dao;

import java.util.List;

import com.rollingcode.test.interview.entity.Category;

public interface CategoryDao {

	List<Category> getAll();

	List<Category> getAllActive();
	
	Category getCategoryByName(String name);
	
	void save(Category category);
	
	void save(List<Category> categories);
	
	void update(Category category);
}
