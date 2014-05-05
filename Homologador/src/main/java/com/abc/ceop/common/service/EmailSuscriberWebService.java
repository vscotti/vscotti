package com.abc.ceop.common.service;

import java.util.Date;
import java.util.List;

public interface EmailSuscriberWebService {

	void updateSWebs(int id_entrevistado);
	
	List<String> getCompletedSurvey(int websurveyCompleted, String country, int cantidadDeDiasARestar, Date dateFrom);


}
