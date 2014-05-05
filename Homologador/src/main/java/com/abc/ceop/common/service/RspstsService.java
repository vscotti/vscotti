package com.abc.ceop.common.service;

import java.util.List;

import com.abc.ceop.model.entities.Rspsts;

public interface RspstsService {

	List<Rspsts> valuesWebSurvey(int surveyNumber, String campaignNumber);

}
