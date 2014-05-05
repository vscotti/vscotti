package com.abc.ceop.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.CsvFileConfigurationDao;
import com.abc.ceop.model.entities.CsvFileConfiguration;
import com.google.common.collect.Iterables;

@Repository
public class CsvFileConfigurationJpaDao implements CsvFileConfigurationDao {

	private final Logger logger = LoggerFactory.getLogger(CsvFileConfigurationJpaDao.class);
	
	@PersistenceContext(unitName="pu")
	private EntityManager em;
	
	@Override
	public CsvFileConfiguration getCsvFileConfiguration(String code) {
		TypedQuery<CsvFileConfiguration> typedQuery = em.createQuery("from CsvFileConfiguration where code like :code", CsvFileConfiguration.class);
		typedQuery.setParameter("code", code);
		try {
			return getSingleResultOrNull(typedQuery);
		} catch (NoResultException noResultException) {
			logger.error("No se encontro configuracion CSV para el codigo {}", code);
			throw noResultException; 
		}
	}

	private CsvFileConfiguration getSingleResultOrNull(final TypedQuery<CsvFileConfiguration> query) {
	    return Iterables.getOnlyElement(query.getResultList(), null); 
	}

	
		
}
