package com.abc.ceop.phonepoll.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.dao.PollQuestionMatcherDao;
import com.abc.ceop.dao.QuestionnaireDao;
import com.abc.ceop.model.dto.DialedOption;
import com.abc.ceop.model.entities.PollQuestionMatcher;
import com.abc.ceop.model.entities.Questionnaire;
import com.abc.ceop.phonepoll.service.PollQuestionMatcherService;

@Service
public class PollQuestionMatcherServiceImpl implements PollQuestionMatcherService {

	private final PollQuestionMatcherDao pollQuestionMatcherDao;
	private final QuestionnaireDao questionnaireDao;
	
	@Autowired
	public PollQuestionMatcherServiceImpl (PollQuestionMatcherDao pollQuestionMatcherDao,
										   QuestionnaireDao questionnaireDao) {
		super();
		this.pollQuestionMatcherDao = pollQuestionMatcherDao;
		this.questionnaireDao = questionnaireDao;
	}
	
	@Override
	public List<DialedOption> getAllQuestionsByCampaing(String campaign) {
		List<Long> restrictions = new ArrayList<Long>();
		restrictions.add(9000L);
		restrictions.add(9010L);
		restrictions.add(9020L);
		List<DialedOption> dialedOptions = new ArrayList<DialedOption>();
		List<PollQuestionMatcher> list = pollQuestionMatcherDao.getAllCodes(restrictions);
		for (PollQuestionMatcher pollQuestionMatcher : list) {
			Questionnaire columnSynonym = questionnaireDao.getQuestionSynonymByCampaignQuestionMatcher(campaign, pollQuestionMatcher);
			String alternativeName = null;
			if(columnSynonym != null &&
					StringUtils.isNotBlank(columnSynonym.getValue())) {
				alternativeName = columnSynonym.getValue();
			}
			DialedOption dialedOption = new DialedOption(pollQuestionMatcher.getCallId().intValue(),
														 pollQuestionMatcher.getCode(),
														 alternativeName);
			dialedOptions.add(dialedOption);
		}
		return dialedOptions;
	}

	@Override
	public Long getCallIdByCode(String code) {
		return pollQuestionMatcherDao.getCallIdByCode(code);
	}

	@Override
	public String getCodeByCallId(Long callId) {
		return pollQuestionMatcherDao.getCodeByCallId(callId);
	}

}
