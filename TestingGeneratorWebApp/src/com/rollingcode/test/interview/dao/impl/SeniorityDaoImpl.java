package com.rollingcode.test.interview.dao.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.rollingcode.test.interview.dao.SeniorityDao;
import com.rollingcode.test.interview.entity.Seniority;

@Repository
public class SeniorityDaoImpl implements SeniorityDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	public static final String COLLECTION_NAME = "seniority";

	@Override
	public void save(Seniority seniority) {
		if (!mongoTemplate.collectionExists(Seniority.class)) {
			mongoTemplate.createCollection(Seniority.class);
		}       
		seniority.setId(UUID.randomUUID().toString());
		mongoTemplate.insert(seniority, COLLECTION_NAME);
	}

	@Override
	public void save(List<Seniority> seniorities) {
		if (!mongoTemplate.collectionExists(Seniority.class)) {
			mongoTemplate.createCollection(Seniority.class);
		}       
		mongoTemplate.insertAll(seniorities);
	}

	@Override
	public List<Seniority> getAll() {
		return mongoTemplate.findAll(Seniority.class);
	}
}
