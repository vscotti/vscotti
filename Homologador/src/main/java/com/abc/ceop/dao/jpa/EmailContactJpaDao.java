package com.abc.ceop.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.EmailContactDao;
import com.abc.ceop.model.entities.EmailContact;

@Repository
public class EmailContactJpaDao implements EmailContactDao {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(EmailContactJpaDao.class);
	
	@PersistenceContext(unitName="pu")
	private EntityManager em;
	
	private List<EmailContact> getEmailsByType(String type) {
		TypedQuery<EmailContact> typedQuery = em.createQuery("FROM EmailContact as EmailContact where type = :type", EmailContact.class);
		typedQuery.setParameter("type", "ERRORALERTS");
		return typedQuery.getResultList();
	}
	
	@Override
	public List<EmailContact> getErrorStatsEmailContacts() {
		return getEmailsByType("ERRORALERTS");
	}

	@Override
	public List<EmailContact> getEmailContacts(String campaign, String type) {
		TypedQuery<EmailContact> typedQuery = em.createQuery("FROM EmailContact as EmailContact where campaign = :campaign and type = :type", EmailContact.class);
		typedQuery.setParameter("campaign", campaign);
		typedQuery.setParameter("type", type);
		return typedQuery.getResultList();
	}
	
}
