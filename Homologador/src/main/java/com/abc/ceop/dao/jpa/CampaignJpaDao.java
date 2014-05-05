package com.abc.ceop.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.CampaignDao;
import com.abc.ceop.model.entities.Campaign;
import com.google.common.collect.Iterables;

@Repository
public class CampaignJpaDao implements CampaignDao {
	
	@PersistenceContext(unitName="pu")
	private EntityManager em;

	@Override
	public Campaign getCampaign(String country) {
		String query = "from Campaign where country = :country";

		TypedQuery<Campaign> typedQuery = em.createQuery(query, Campaign.class);
		typedQuery.setParameter("country", country);

		return getSingleResultOrNull(typedQuery);
	}

	@Override
	public List<Campaign> getCampaignByCampaignId(String campaign) {
		String query = "from Campaign where campaign = :campaign";

		TypedQuery<Campaign> typedQuery = em.createQuery(query, Campaign.class);
		typedQuery.setParameter("campaign", campaign);

		return typedQuery.getResultList();
	}

	@Override
	public List<Campaign> getAll() {
		TypedQuery<Campaign> typedQuery = em.createQuery("from Campaign", Campaign.class);
		return typedQuery.getResultList();
	}

	private Campaign getSingleResultOrNull(final TypedQuery<Campaign> query) {
	    return Iterables.getOnlyElement(query.getResultList(), null); 
	}

}