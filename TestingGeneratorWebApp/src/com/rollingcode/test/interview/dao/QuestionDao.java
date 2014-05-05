package com.rollingcode.test.interview.dao;

import java.util.List;

import com.rollingcode.test.interview.entity.Question;

public interface QuestionDao {

	void save(Question question);
	
	List<Question> getAll();

	List<Question> getByCategoryList(List<String> categories);

	List<Question> getBySeniorirtyId(String seniorityid);

	List<Question> getByCategoryIdSeniorityId(List<String> categories, String seniorityid);
}
