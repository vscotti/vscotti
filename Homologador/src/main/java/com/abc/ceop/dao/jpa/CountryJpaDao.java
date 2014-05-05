package com.abc.ceop.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.CountryDao;
import com.abc.ceop.model.entities.Country;

@Repository
public class CountryJpaDao implements CountryDao {
	
	@PersistenceContext(unitName="pu")
	private EntityManager em;

	public Country getCountryByName(String name) {
		try {
			TypedQuery<Country> typedQuery = em.createQuery("FROM Country as Country where name = :name", Country.class);
			typedQuery.setParameter("name", name);
			return typedQuery.getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

	@Override
	public Country getCountryById(Long id) {
		return em.find(Country.class, id);
	}
}
