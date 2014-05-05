package com.abc.ceop.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.abc.ceop.dao.PollQuestionMatcherDao;
import com.abc.ceop.model.entities.PollQuestionMatcher;

@Service
public class PollQuestionMatcherJpaDao implements PollQuestionMatcherDao {
	
	@PersistenceContext(unitName="pu")
	private EntityManager em;
	
	@Cacheable("getCallIdByCode")
	@Override
	public Long getCallIdByCode(String code) {
		PollQuestionMatcher pollQuestionMatcher = em.find(PollQuestionMatcher.class, code);
		if(pollQuestionMatcher != null) {
			return pollQuestionMatcher.getCallId();
		}
		return null;
	}
//TOdo Revisar Query !!!!
	
//	@Cacheable("getCallIdByCode")
//	@Override
//	public String getCodeByCallId (Long callId) {
//		PollQuestionMatcher pollQuestionMatcher = em.find(PollQuestionMatcher.class, callId);
//		if(pollQuestionMatcher != null) {
//			return pollQuestionMatcher.getCode();
//		}
//		return null;
//	}
	
	@Override
	public String getCodeByCallId(Long callId) {
			TypedQuery<String> typedQuery = em.createQuery("SELECT poll.code FROM PollQuestionMatcher AS poll WHERE callId = :callId", String.class);
			typedQuery.setParameter("callId", callId);
			if (!typedQuery.getResultList().isEmpty() ) {
				return typedQuery.getSingleResult();
			} 
			return null;
		}
	

	@Override
	public List<String> getAllCodes() {
		TypedQuery<String> typedQuery = em.createQuery("SELECT poll.code FROM PollQuestionMatcher AS poll", String.class);
		return typedQuery.getResultList();
	}
	
	@Override
	public List<PollQuestionMatcher> getAllCodes(List<Long> restrictions) {
		TypedQuery<PollQuestionMatcher> typedQuery = em.createQuery("FROM PollQuestionMatcher AS poll where poll.callId not in (?1) order by poll.callId", PollQuestionMatcher.class);
		typedQuery.setParameter(1, restrictions);
		return typedQuery.getResultList();
	}
	
	

}
