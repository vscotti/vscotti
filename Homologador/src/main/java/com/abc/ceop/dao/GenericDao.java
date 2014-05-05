package com.abc.ceop.dao;

public interface GenericDao {
	
	<T> void save(T... entities);
	
	<T> T update(T entity);

	<T> void update(T... entities);
	
	<T> void delete(T... entities);
	
	<T> T getById(Class<T> clazz, Long id);
	
}
