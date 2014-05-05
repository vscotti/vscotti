package com.abc.ceop.common.service;

import java.util.List;

import com.abc.ceop.model.entities.Campaign;
import com.abc.ceop.model.entities.Thresholds;

public interface ConditionService {

	List<Thresholds> getConditionsByDestinationCampaign(String destination, Campaign campaign);

	List<Thresholds> getConditionsByDestination(String destination);
	
	Integer getMinPhoneCountValue(Campaign campaign);
	
	Integer getTimeWithOutDataValue(Campaign campaign);
}
