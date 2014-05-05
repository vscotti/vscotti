package com.abc.ceop.common.service;

import java.util.List;

import com.abc.ceop.model.entities.QuienRespondeLaEncuesta;


public interface QuienRespondeLaEncuestaService {
	
	List<QuienRespondeLaEncuesta> getSurveyComplete (String surveyNumberWebCati);
	
	List<QuienRespondeLaEncuesta> getSurveyCompleteNdays(String surveyNumberWebCati, int cantidadDeDiasARestar, List <String> suscribers);
		
}
