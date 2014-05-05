package com.rollingcode.test.interview.dao.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.rollingcode.test.interview.dao.CategoryDao;
import com.rollingcode.test.interview.entity.Category;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	public static final String COLLECTION_NAME = "category";

	@Override
	public void save(Category category) {
		if (!mongoTemplate.collectionExists(Category.class)) {
			mongoTemplate.createCollection(Category.class);
		}       
		category.setId(UUID.randomUUID().toString());
		mongoTemplate.insert(category, COLLECTION_NAME);
	}

	@Override
	public void save(List<Category> categories) {
		if (!mongoTemplate.collectionExists(Category.class)) {
			mongoTemplate.createCollection(Category.class);
		}       
		mongoTemplate.insertAll(categories);
	}

	@Override
	public List<Category> getAll() {
		return mongoTemplate.findAll(Category.class, COLLECTION_NAME);
	}

	@Override
	public List<Category> getAllActive() {
		return mongoTemplate.find(new Query(Criteria.where("active").is("Y")), Category.class);
	}

	@Override
	public Category getCategoryByName(String name) {
		List<Category> list = mongoTemplate.find(new Query(Criteria.where("name").is(name)), Category.class);
		if(list != null &&
				list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void update(Category category) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(category.getName()));
 
		Update update = new Update();
		update.set("description", category.getDescription());
		update.set("active", category.getActive());
 
		mongoTemplate.updateFirst(query, update, Category.class);
	}
}
