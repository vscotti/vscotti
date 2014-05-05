package com.abc.ceop.common.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.EmailSuscriberWebService;
import com.abc.ceop.dao.EmailSuscriberWebDao;

@Service
public class EmailSuscriberWebServiceImpl implements EmailSuscriberWebService {

	private final EmailSuscriberWebDao emailSuscriberWebDao;

	@Autowired
	public EmailSuscriberWebServiceImpl(EmailSuscriberWebDao emailSuscriberWebDao) {
		
		this.emailSuscriberWebDao = emailSuscriberWebDao;
	}

	@Override
	public void updateSWebs(int id_entrevistado) {
		emailSuscriberWebDao.updateSWebs(id_entrevistado);
	}

	@Override
	public List<String> getCompletedSurvey(int websurveyCompleted, String country,  int cantidadDeDiasARestar, Date dateFrom) {
		return emailSuscriberWebDao.getCompletedSurvey(websurveyCompleted,country, cantidadDeDiasARestar, dateFrom );
	}

}
