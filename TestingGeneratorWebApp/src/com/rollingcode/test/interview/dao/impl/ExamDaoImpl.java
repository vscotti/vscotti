package com.rollingcode.test.interview.dao.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rollingcode.test.interview.dao.ExamDao;
import com.rollingcode.test.interview.entity.Exam;

@Transactional 
@Repository
public class ExamDaoImpl implements ExamDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	public static final String COLLECTION_NAME = "exam";

	@Override
	public void save(Exam examen) {
		if (!mongoTemplate.collectionExists(Exam.class)) {
			mongoTemplate.createCollection(Exam.class);
		}       
		examen.setId(UUID.randomUUID().toString());
		mongoTemplate.insert(examen, COLLECTION_NAME);
	}
	
	@Override
	public List<Exam> getByIdentificator(String identificator) {
		return mongoTemplate.find(new Query(Criteria.where("examNumber").is(identificator)), Exam.class);
	}
}
