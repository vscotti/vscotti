package com.abc.ceop.dao;

import java.util.List;

import com.abc.ceop.model.entities.PollQuestionMatcher;

public interface PollQuestionMatcherDao {
	
	Long getCallIdByCode(String code);
	
	String getCodeByCallId(Long callId);
	
	List<String> getAllCodes();
	
	List<PollQuestionMatcher> getAllCodes(List<Long> restrictions);
	
}
