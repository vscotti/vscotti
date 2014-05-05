package com.abc.ceop.common.service;

import java.util.List;

import com.abc.ceop.model.entities.Campaign;

public interface CampaignService {

	Campaign getCampaignName(String country);
	
	Campaign getCampaignByCampaignId(String campaign);
	
	List<Campaign> getAll();
}
