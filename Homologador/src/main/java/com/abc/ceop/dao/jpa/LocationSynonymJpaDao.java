package com.abc.ceop.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.LocationSynonymDao;
import com.abc.ceop.model.entities.LocationSynonym;

@Repository
public class LocationSynonymJpaDao implements LocationSynonymDao {

	private final Logger logger = LoggerFactory.getLogger(LocationSynonymJpaDao.class);
	private static final Class<LocationSynonym> clazz = LocationSynonym.class;
	
	@PersistenceContext(unitName="pu")
	private EntityManager em;
	
	@Cacheable("lookupForWord")
	@Override
	public String lookupForWord(String synonym) {
		String query = "from LocationSynonym where synonym like :synonym";
		
		TypedQuery<LocationSynonym> typedQuery = em.createQuery(query, clazz);
		typedQuery.setParameter("synonym", synonym);
		
		String result = null;
		
		try {
			LocationSynonym locationSynonym = typedQuery.getSingleResult();
			result = locationSynonym.getWord();
		} catch (NoResultException noResultException) {
			logger.trace("No appropiate word for synonym {}", synonym);
		}
		
		return result;
	}

}
