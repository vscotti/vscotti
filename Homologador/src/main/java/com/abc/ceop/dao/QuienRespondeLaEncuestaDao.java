package com.abc.ceop.dao;

import java.util.List;

import com.abc.ceop.model.entities.QuienRespondeLaEncuesta;

public interface QuienRespondeLaEncuestaDao {

	
	List<QuienRespondeLaEncuesta> getSurveyComplete (String surveyNumberWebCati);

	List<QuienRespondeLaEncuesta> getSurveyCompleteNdays(String surveyNumberWebCati, int cantidadDeDiasARestar, List <String> suscribers);
	

	}
