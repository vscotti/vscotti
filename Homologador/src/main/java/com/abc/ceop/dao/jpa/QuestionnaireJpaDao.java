package com.abc.ceop.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.QuestionnaireDao;
import com.abc.ceop.model.entities.PollQuestionMatcher;
import com.abc.ceop.model.entities.Questionnaire;

//@Service
@Repository
public class QuestionnaireJpaDao implements QuestionnaireDao {
	
	@PersistenceContext(unitName="pu")
	private EntityManager em;
	
	@Cacheable("getQuestionSynonym")
	@Override
	public Questionnaire getQuestionSynonymByCampaignQuestionMatcher(String campaign, PollQuestionMatcher pollQuestionMatcher) {
		if (StringUtils.isNotBlank(campaign) &&
				pollQuestionMatcher != null) {
			TypedQuery<Questionnaire> typedQuery = em.createQuery("FROM Questionnaire AS questionnaire WHERE campaign = :campaign and question = :pollQuestionMatcher", Questionnaire.class);
			typedQuery.setParameter("campaign", campaign);
			typedQuery.setParameter("pollQuestionMatcher", pollQuestionMatcher);
			List<Questionnaire> list = typedQuery.getResultList();
			if(list != null &&
					list.size() > 0) {
				return list.get(0);
			}
			return null;
		} else {
			return null;
		}
				
		
	}

	@Override
	public List<Questionnaire> getQuestionnaires(String campaing) {
		TypedQuery<Questionnaire> typedQuery = em.createQuery("FROM Questionnaire as rd WHERE campaign =:campaign ", Questionnaire.class);
		 
		  typedQuery.setParameter("campaign", campaing);
		  List<Questionnaire> listQuestionnaires = typedQuery.getResultList();
		  if (listQuestionnaires != null && listQuestionnaires.size() > 0) {
			  return listQuestionnaires ;
		  }
		  return null;
		
		
	}

}
