package com.abc.ceop.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.ConditionDao;
import com.abc.ceop.model.entities.Campaign;
import com.abc.ceop.model.entities.Thresholds;

@Repository
public class ConditionJpaDao implements ConditionDao {
	
	@PersistenceContext(unitName="pu")
	private EntityManager em;

	@Override
	public List<Thresholds> getConditionsByDestinationCampaign(String destination, Campaign campaign) {
		String query = "from Thresholds where destination = :destination and campaign.id = :campaign";
		TypedQuery<Thresholds> typedQuery = em.createQuery(query, Thresholds.class);
		typedQuery.setParameter("destination", destination);
		typedQuery.setParameter("campaign", campaign.getId());
		return typedQuery.getResultList();
	}

	@Override
	public List<Thresholds> getConditionsByDestination(String destination) {
		String query = "from Thresholds where destination = :destination";
		TypedQuery<Thresholds> typedQuery = em.createQuery(query, Thresholds.class);
		typedQuery.setParameter("destination", destination);
		return typedQuery.getResultList();
	}

}