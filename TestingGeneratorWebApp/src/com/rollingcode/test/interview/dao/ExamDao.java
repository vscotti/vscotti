package com.rollingcode.test.interview.dao;

import java.util.List;

import com.rollingcode.test.interview.entity.Exam;

public interface ExamDao {

	List<Exam> getByIdentificator(String identificator);
	
	void save(Exam examen);
}
