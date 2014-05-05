package com.abc.ceop.phonepoll.service;

import java.util.List;

import com.abc.ceop.model.dto.DialedOption;

public interface PollQuestionMatcherService {

	Long getCallIdByCode(String code);
	
	List<DialedOption> getAllQuestionsByCampaing(String campaign);

	String getCodeByCallId(Long callId);
	
}
