package com.abc.ceop.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.ConfigurationVariableDao;
import com.abc.ceop.model.entities.Configuration;
import com.abc.ceop.model.entities.Configuration.FirstProcessConfiguration;
import com.abc.ceop.model.entities.Configuration.SecondProcessConfiguration;
import com.abc.ceop.model.entities.ConfigurationVariable;
import com.abc.ceop.model.entities.FirstProcessConfigurationVariable;
import com.abc.ceop.model.entities.SecondProcessConfigurationVariable;

@Repository
public class ConfigurationVariableJpaDao implements ConfigurationVariableDao {
	
	private final Logger logger = LoggerFactory.getLogger(ConfigurationVariableJpaDao.class);
	
	@PersistenceContext(unitName="pu")
	private EntityManager em;

	private <T extends ConfigurationVariable<E>, E extends Configuration> T get(E name, Class<T> clazz) {
		String query = "from " + clazz.getSimpleName() + " where name = :name";

		TypedQuery<T> typedQuery = em.createQuery(query, clazz);
		typedQuery.setParameter("name", name);

		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException noResultException) {
			logger.error("No se encontro configuracion asociada a {} para la entidad {}", new Object[] {name, clazz.getSimpleName()});
			throw noResultException;
		}
	}
	
	private <T extends ConfigurationVariable<E>, E extends Configuration> List<T> gets(E name, Class<T> clazz) {
		String query = "from " + clazz.getSimpleName() + " where name = :name";

		TypedQuery<T> typedQuery = em.createQuery(query, clazz);
		typedQuery.setParameter("name", name);

		try {
			return typedQuery.getResultList();
		} catch (NoResultException noResultException) {
			logger.error("No se encontro configuracion asociada a {} para la entidad {}", new Object[] {name, clazz.getSimpleName()});
			throw noResultException;
		}
	}

	@Override
	public FirstProcessConfigurationVariable get(FirstProcessConfiguration name) {
		return get(name, FirstProcessConfigurationVariable.class);
	}
	
	@Override
	public SecondProcessConfigurationVariable get(SecondProcessConfiguration name) {
		return get(name, SecondProcessConfigurationVariable.class);
	}

	@Override
	public List<FirstProcessConfigurationVariable> gets(FirstProcessConfiguration name) {
		return gets(name, FirstProcessConfigurationVariable.class);
	}

}
