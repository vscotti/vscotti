package com.abc.ceop.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.CellPhonePatternDao;
import com.abc.ceop.model.entities.CellPhonePattern;
import com.abc.ceop.model.entities.Location;

@Repository
public class CellPhonePatternJpaDao implements CellPhonePatternDao {

	private final Logger logger = LoggerFactory.getLogger(CellPhonePatternJpaDao.class);
	
	private final Class<CellPhonePattern> clazz = CellPhonePattern.class;
	@PersistenceContext(unitName="pu")
	private EntityManager em;
	
	@Override
	public List<CellPhonePattern> getMostSpecificPatternAction(Location location) {
		TypedQuery<CellPhonePattern> typedQuery = em.createQuery("from CellPhonePattern where location = :location", clazz);
		typedQuery.setParameter("location", location);
		try {
			return typedQuery.getResultList();
		} catch (RuntimeException runtimeException) {
			logger.warn("No se pudo encontrar un pattern action para la Location {}.", location);
			throw runtimeException;
		}
	}
	
	@Override
	public List<CellPhonePattern> findAll() {
		TypedQuery<CellPhonePattern> typedQuery = em.createQuery("from CellPhonePattern", clazz);
		try {
			return typedQuery.getResultList();
		} catch (RuntimeException runtimeException) {
			logger.error("Ha ocurrido un error en CellPhonePatternJpaDao / findAll: {} / {}", runtimeException.getClass().toString(), runtimeException.getMessage());
			throw runtimeException;
		}
	}
	
}
