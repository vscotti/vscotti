package com.abc.ceop.dao;

import java.util.Date;
import java.util.List;

public interface EmailSuscriberWebDao {

	void updateSWebs(int id_entrevistado);

	List<String> getCompletedSurvey(int websurveyCompleted, String country, int cantidadDeDiasARestar, Date dateFrom);

}
