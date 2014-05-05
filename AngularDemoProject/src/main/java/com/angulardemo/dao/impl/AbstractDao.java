package com.angulardemo.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unchecked")
@Transactional
public abstract class AbstractDao<T> {

	@PersistenceContext
	private EntityManager entityManager;
	
    private Class<T> persistentClass = figureOutPersistentClass();

	public AbstractDao() {
	}

    private Class<T> figureOutPersistentClass() {
        Class<T> clazz = (Class<T>)((ParameterizedType) (getClass().getGenericSuperclass())).getActualTypeArguments()[0];
        return clazz;
    }

	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

	public void create(T entity) {
		this.entityManager.persist(entity);
	}

	public void edit(T entity) {
		this.entityManager.merge(entity);
	}

	public void remove(T entity) {
		this.entityManager.remove(entity);
	}

	public T find(Long primaryKey) {
		return (T) this.entityManager.find(persistentClass, primaryKey);
	}

	public List<T> findAll() {
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(persistentClass);
        Root<T> rootEntry = cq.from(persistentClass);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = this.entityManager.createQuery(all);
        return allQuery.getResultList();
	}
	
	public List<T> findAll(Map<String, Object> equalValues) {
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(persistentClass);
		for (String value : equalValues.keySet()) {
			criteria.add(Restrictions.eq(value, equalValues.get(value)));
		}
		return (List<T>) criteria.list();
	}
	
	public T find(Map<String, Object> equalValues) {
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(persistentClass);
		for (String value : equalValues.keySet()) {
			criteria.add(Restrictions.eq(value, equalValues.get(value)));
		}
		return (T) criteria.uniqueResult();
	}
}