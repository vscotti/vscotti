package com.abc.ceop.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.ceop.common.service.CampaignService;
import com.abc.ceop.common.service.ConditionService;
import com.abc.ceop.model.dto.SecondProcessCommonData;
import com.abc.ceop.model.entities.Configuration.SecondProcessConfiguration;
import com.abc.ceop.phonepoll.service.DialRecordExcelFileCreator;
import com.abc.ceop.phonepoll.service.DialRecordExcelFileReader;
import com.abc.ceop.phonepoll.service.PollQuestionMatcherService;
import com.abc.ceop.phonepoll.service.SecondProcessConfigurationService;
import com.abc.ceop.util.CampaignUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring/model-context.xml" })
public class DialRecordExcelFileCreatorImplTest {
	
	@Resource
	private DialRecordExcelFileCreator creator;
	@Resource
	private DialRecordExcelFileReader reader;
	@Resource
	private CampaignService campaignService;
	@Resource
	private SecondProcessConfigurationService configurationService;
	@Resource
	private PollQuestionMatcherService pollQuestionMatcherService;
	@Resource
	private ConditionService conditionService;
	
	private String fileName = "S907-20111221__22-00-00.xls";
	private String remoteFileName = "src/test/resources/" + fileName;

	private SecondProcessCommonData loadSecondProcessCommonData() {
		SecondProcessCommonData cd = new SecondProcessCommonData();
		cd.setDateNoSuscriber(configurationService.getConfigurationValue(SecondProcessConfiguration.NO_SUSCRIBER_TIME));
		cd.setTempPath(configurationService.getConfigurationValue(SecondProcessConfiguration.TEMP_PATH));
		cd.setCurrentTime(new SimpleDateFormat("hhmmss-SSS").format(new Date()));
		cd.setCampaignIdTrabajo(CampaignUtils.getCampaignIdTrabajo(remoteFileName));
		cd.setCampaigndate(CampaignUtils.getCampaignDate(remoteFileName));
		cd.setCampaign(campaignService.getCampaignByCampaignId(CampaignUtils.getCampaignIdTrabajo(remoteFileName)));
		cd.setDialedRecords(reader.readExcel(remoteFileName));
		if(cd.getDialedRecords() != null &&
				cd.getDialedRecords().size() > 0) {
			cd.setIdQuestionYear(String.valueOf(pollQuestionMatcherService.getCallIdByCode("year")));
			cd.setIdQuestionMonth(String.valueOf(pollQuestionMatcherService.getCallIdByCode("month")));
			cd.setIdQuestionDay(String.valueOf(pollQuestionMatcherService.getCallIdByCode("day")));
			cd.setFlagEfectivoCondition(conditionService.getConditionsByDestinationCampaign("FLAGEFECTIVO", cd.getCampaign()));
			cd.setFlagSOSCondition(conditionService.getConditionsByDestinationCampaign("FLAGSOS", cd.getCampaign()));
			cd.setCutConditions(conditionService.getConditionsByDestination("CUTTINGCONDITION"));
		}
		return cd;
	}

	@Test
	public void test() {
		SecondProcessCommonData cd = loadSecondProcessCommonData();
		String createdFilepath = creator.createFile(cd);
		Assert.assertTrue(createdFilepath != null);
	}			
}
