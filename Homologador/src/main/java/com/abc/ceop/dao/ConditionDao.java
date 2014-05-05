package com.abc.ceop.dao;

import java.util.List;

import com.abc.ceop.model.entities.Campaign;
import com.abc.ceop.model.entities.Thresholds;

public interface ConditionDao {
	
	List<Thresholds> getConditionsByDestinationCampaign(String destination, Campaign campaign);

	List<Thresholds> getConditionsByDestination(String destination);
}
