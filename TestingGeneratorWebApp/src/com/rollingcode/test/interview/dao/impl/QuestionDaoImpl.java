package com.rollingcode.test.interview.dao.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.rollingcode.test.interview.dao.QuestionDao;
import com.rollingcode.test.interview.entity.Question;

@Repository
public class QuestionDaoImpl implements QuestionDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	public static final String COLLECTION_NAME = "question";

	@Override
	public void save(Question question) {
		if (!mongoTemplate.collectionExists(Question.class)) {
			mongoTemplate.createCollection(Question.class);
		}       
		question.setId(UUID.randomUUID().toString());
		mongoTemplate.insert(question, COLLECTION_NAME);
	}
	
	@Override
	public List<Question> getAll() {
		return mongoTemplate.findAll(Question.class, COLLECTION_NAME);
	}

	@Override
	public List<Question> getByCategoryList(List<String> categories) {
		return mongoTemplate.find(new Query(Criteria.where("category.id").in(categories)), Question.class);
	}

	@Override
	public List<Question> getBySeniorirtyId(String seniorityid) {
		return mongoTemplate.find(new Query(Criteria.where("seniority.id").in(seniorityid)), Question.class);
	}

	@Override
	public List<Question> getByCategoryIdSeniorityId(List<String> categories, String seniorityid) {
		return mongoTemplate.find(new Query(Criteria.where("category.id").in(categories).and("seniority.id").is(seniorityid)), Question.class);
	}
}
