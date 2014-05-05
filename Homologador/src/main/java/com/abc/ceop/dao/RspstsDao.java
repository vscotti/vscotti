package com.abc.ceop.dao;

import java.util.List;

import com.abc.ceop.model.entities.Rspsts;


public interface RspstsDao {
	
	List <Rspsts> valuesWebSurvey (int surveyNumber, String campaignNumber);
	
	

}
