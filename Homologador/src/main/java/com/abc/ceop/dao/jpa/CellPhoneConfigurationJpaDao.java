package com.abc.ceop.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.CellPhoneConfigurationDao;
import com.abc.ceop.model.entities.CellPhoneConfiguration;
import com.abc.ceop.model.entities.Country;

@Repository
public class CellPhoneConfigurationJpaDao implements CellPhoneConfigurationDao {
	
	@PersistenceContext(unitName="pu")
	private EntityManager em;

	@Override
	public List<CellPhoneConfiguration> getAll() {
		TypedQuery<CellPhoneConfiguration> typedQuery = em.createQuery("from CellPhoneConfiguration", CellPhoneConfiguration.class);
		return typedQuery.getResultList();
	}

	@Override
	public List<CellPhoneConfiguration> getCellPhoneConfigurationByCountrry(
			Country country) {
		TypedQuery<CellPhoneConfiguration> typedQuery = em.createQuery("from CellPhoneConfiguration as CellPhoneConfiguration where country.id = :countryid", CellPhoneConfiguration.class);
		typedQuery.setParameter("countryid", country.getId());
		return typedQuery.getResultList();
	}

}