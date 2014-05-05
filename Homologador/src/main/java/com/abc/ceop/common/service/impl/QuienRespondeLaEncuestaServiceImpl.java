package com.abc.ceop.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.QuienRespondeLaEncuestaService;
import com.abc.ceop.dao.QuienRespondeLaEncuestaDao;
import com.abc.ceop.model.entities.QuienRespondeLaEncuesta;


@Service
public class QuienRespondeLaEncuestaServiceImpl implements QuienRespondeLaEncuestaService {

	private final QuienRespondeLaEncuestaDao quienRespondeLaEncuestaDao;
	@Autowired
	public QuienRespondeLaEncuestaServiceImpl (QuienRespondeLaEncuestaDao quienRespondeLaEncuestaDao){
		this.quienRespondeLaEncuestaDao = quienRespondeLaEncuestaDao;
	}
	
	@Override
	public List<QuienRespondeLaEncuesta> getSurveyComplete(String surveyNumberWebCati) {
		return quienRespondeLaEncuestaDao.getSurveyComplete(surveyNumberWebCati);
	}

	@Override
	public List<QuienRespondeLaEncuesta> getSurveyCompleteNdays(String surveyNumberWebCati, int cantidadDeDiasARestar, List <String> suscribers) {
		return quienRespondeLaEncuestaDao.getSurveyCompleteNdays(surveyNumberWebCati, cantidadDeDiasARestar, suscribers);
	}

	}

	

