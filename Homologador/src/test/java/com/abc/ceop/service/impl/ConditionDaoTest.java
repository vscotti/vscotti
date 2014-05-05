package com.abc.ceop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.abc.ceop.common.service.CampaignService;
import com.abc.ceop.dao.ConditionDao;
import com.abc.ceop.model.entities.Campaign;
import com.abc.ceop.model.entities.Thresholds;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring/model-context.xml" })
public class ConditionDaoTest {

	@Resource
	private ConditionDao conditionDao;
	@Resource
	private CampaignService campaignService;
	
	@Test
	public void testConditionDao() throws Exception {
		Campaign campaign = campaignService.getCampaignName("ARG");
		List<Thresholds> list = conditionDao.getConditionsByDestinationCampaign("FLAGEFECTIVO", campaign);
		Assert.assertTrue(list != null);
	}
	
}
