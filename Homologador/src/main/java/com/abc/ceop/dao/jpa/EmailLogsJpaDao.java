package com.abc.ceop.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;


import com.abc.ceop.dao.EmailLogsDao;
import com.abc.ceop.model.entities.Campaign;
import com.abc.ceop.model.entities.EmailLogs;

@Repository
public class EmailLogsJpaDao implements EmailLogsDao {
	
	@PersistenceContext(unitName="pu")
	private EntityManager em;


	@Override
	public List<EmailLogs> getEmailLogs(Date lastSentMail, Campaign campaign) {
		TypedQuery<EmailLogs> typedQuery = em.createQuery("FROM EmailLogs as rd WHERE date = :date and campaign = :campaign", EmailLogs.class);
		typedQuery.setParameter("date", lastSentMail);
		typedQuery.setParameter("campaign", campaign);
		List<EmailLogs> emailLogs = typedQuery.getResultList();
		if (emailLogs != null && emailLogs.size() > 0) {
			return emailLogs;
		}
		return null;
	}

	@Override
	public Date getLastSentMailForCountry(Campaign campaign) {

		Query query = em.createNativeQuery("Select top 1 * FROM EmailLogs as rd where campaign_id = :campaign order by date desc", EmailLogs.class);
		query.setParameter("campaign", campaign.getId());
		try {
			EmailLogs date = (EmailLogs) query.getSingleResult();
			return date != null? date.getDate(): null;
		} catch(NoResultException e) {}
		return null;
	}
	
	}
