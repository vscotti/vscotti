package com.abc.ceop.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.CampaignService;
import com.abc.ceop.dao.CampaignDao;
import com.abc.ceop.model.entities.Campaign;

@Service
public class CampaignServiceImpl implements CampaignService {

	private final CampaignDao campaignDao;
	
	@Autowired
	public CampaignServiceImpl(CampaignDao campaignDao) {
		this.campaignDao = campaignDao;
	}
	
	@Override
	public Campaign getCampaignName(String country) {
		return campaignDao.getCampaign(country);
	}

	@Override
	public Campaign getCampaignByCampaignId(String campaign) {
		List<Campaign> list = campaignDao.getCampaignByCampaignId(campaign);
		if(list != null &&
				list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Campaign> getAll() {
		return campaignDao.getAll();
	}

}
