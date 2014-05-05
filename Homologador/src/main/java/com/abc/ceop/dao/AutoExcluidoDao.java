package com.abc.ceop.dao;

import java.util.List;

import com.abc.ceop.model.entities.AutoExcluido;

public interface AutoExcluidoDao {
	
	List <AutoExcluido> outOfTheWebSurvey (int notContactFlag, String contryCampaing);

}
