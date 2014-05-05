package com.abc.ceop.common.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.ConditionService;
import com.abc.ceop.dao.ConditionDao;
import com.abc.ceop.model.entities.Campaign;
import com.abc.ceop.model.entities.Thresholds;

@Service
public class ConditionServiceImpl implements ConditionService {

	private final ConditionDao conditionDao;
	
	@Autowired
	public ConditionServiceImpl(ConditionDao conditionDao) {
		this.conditionDao = conditionDao;
	}

	@Override
	public List<Thresholds> getConditionsByDestinationCampaign(String destination, Campaign campaign) {
		return conditionDao.getConditionsByDestinationCampaign(destination, campaign);
	}

	@Override
	public List<Thresholds> getConditionsByDestination(String destination) {
		return conditionDao.getConditionsByDestination(destination);
	}

	@Override
	public Integer getMinPhoneCountValue(Campaign campaign) {
		List<Thresholds> list = getConditionsByDestinationCampaign("MINPHONECOUNT", campaign);
		if(list != null &
				list.size() > 0 &&
				list.get(0) != null &&
				list.get(0).getConditionRule() != null &&
				StringUtils.isNumeric(list.get(0).getConditionRule())) {
			return Integer.parseInt(list.get(0).getConditionRule());
		}
		return null;
	}

	@Override
	public Integer getTimeWithOutDataValue(Campaign campaign) {
		List<Thresholds> list = getConditionsByDestinationCampaign("TIMEWITHOUTDATA", campaign);
		if(list != null &
				list.size() > 0 &&
				list.get(0) != null &&
				list.get(0).getConditionRule() != null &&
				StringUtils.isNumeric(list.get(0).getConditionRule())) {
			return Integer.parseInt(list.get(0).getConditionRule());
		}
		return null;
	}
	
}
