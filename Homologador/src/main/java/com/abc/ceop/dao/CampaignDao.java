package com.abc.ceop.dao;

import java.util.List;

import com.abc.ceop.model.entities.Campaign;

public interface CampaignDao {
	
	Campaign getCampaign(String country);

	List<Campaign> getCampaignByCampaignId(String campaign);

	List<Campaign> getAll();
}
