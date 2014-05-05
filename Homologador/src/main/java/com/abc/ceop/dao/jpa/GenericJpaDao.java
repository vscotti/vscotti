package com.abc.ceop.dao.jpa;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abc.ceop.dao.GenericDao;

@Repository
public class GenericJpaDao implements GenericDao {

	@PersistenceContext(unitName="pu")
	private EntityManager em;

	@Override
	@Transactional(readOnly = false)
	public <T> void save(T... entities) {
		CollectionUtils.forAllDo(Arrays.asList(entities), new Closure() {
			@Override
			public void execute(Object input) {
				em.persist(input);
			}
		});
	}
	
	@Override
	@Transactional(readOnly = false)
	public <T> T update(T entity) {
		return em.merge(entity);
	}

	@Override
	public <T> void update(T... entities) {
		CollectionUtils.forAllDo(Arrays.asList(entities), new Closure() {
			@Override
			public void execute(Object input) {
				em.merge(input);
			}
		});
	}

	@Override
	public <T> void delete(T... entities) {
		CollectionUtils.forAllDo(Arrays.asList(entities), new Closure() {
			@Override
			public void execute(Object input) {
				em.remove(input);
			}
		});
	}

	@Override
	public <T> T getById(Class<T> clazz, Long id) {
		return em.find(clazz, id);
	}
	
}
