package com.abc.ceop.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.QuestionnaireService;
import com.abc.ceop.dao.QuestionnaireDao;
import com.abc.ceop.model.entities.Questionnaire;
@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

	private QuestionnaireDao questionnaireDao;
	@Autowired
	public QuestionnaireServiceImpl(QuestionnaireDao questionnaireDao) {
		
		this.questionnaireDao = questionnaireDao;
	}

	@Override
	public List<Questionnaire> getQuestionnaires(String campaing) {
		
		return questionnaireDao.getQuestionnaires(campaing);
	}

}
