package com.abc.ceop.dao;

import java.util.List;

import com.abc.ceop.model.entities.PollQuestionMatcher;
import com.abc.ceop.model.entities.Questionnaire;


public interface QuestionnaireDao {

	Questionnaire getQuestionSynonymByCampaignQuestionMatcher(String campaign, PollQuestionMatcher pollQuestionMatcher);
	
	List<Questionnaire> getQuestionnaires (String campaing );
}
