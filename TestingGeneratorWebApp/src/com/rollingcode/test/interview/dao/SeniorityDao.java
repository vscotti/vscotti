package com.rollingcode.test.interview.dao;

import java.util.List;

import com.rollingcode.test.interview.entity.Seniority;

public interface SeniorityDao {

	List<Seniority> getAll();
	
	void save(Seniority seniority);
	
	void save(List<Seniority> seniorities);
}
