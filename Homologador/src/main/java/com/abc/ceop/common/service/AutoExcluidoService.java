package com.abc.ceop.common.service;

import java.util.List;

import com.abc.ceop.model.entities.AutoExcluido;

public interface AutoExcluidoService {
	
	List <AutoExcluido> outOfTheWebSurvey (int notContactFlag, String countryCampaing);


}
