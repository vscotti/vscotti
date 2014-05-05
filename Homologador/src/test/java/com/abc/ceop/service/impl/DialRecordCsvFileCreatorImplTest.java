package com.abc.ceop.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.ceop.common.service.CampaignService;
import com.abc.ceop.common.service.ConditionService;
import com.abc.ceop.model.dto.DialedOption;
import com.abc.ceop.model.dto.SecondProcessCommonData;
import com.abc.ceop.model.entities.Configuration.SecondProcessConfiguration;
import com.abc.ceop.phonepoll.service.DialRecordCsvFileCreator;
import com.abc.ceop.phonepoll.service.DialRecordExcelFileReader;
import com.abc.ceop.phonepoll.service.PollQuestionMatcherService;
import com.abc.ceop.phonepoll.service.SecondProcessConfigurationService;
import com.abc.ceop.phonepoll.service.impl.DialedRecordUtils;
import com.abc.ceop.util.CampaignUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring/model-context.xml" })
public class DialRecordCsvFileCreatorImplTest {

	@Resource
	private DialRecordCsvFileCreator dialRecordCsvFileCreator;
	@Resource
	private DialRecordExcelFileReader dialRecordExcelFileReader;
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
		cd.setDialedRecords(dialRecordExcelFileReader.readExcel(remoteFileName));
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
	public void testWriteCsvFile_smallFile() {
		SecondProcessCommonData cd = loadSecondProcessCommonData();
		String filepath = dialRecordCsvFileCreator.createFile(cd);
		Assert.assertTrue(filepath.contains(cd.getCampaignIdTrabajo()));
	}
	
	@Test
	public void testSortHeader() {
		Set<DialedOption> dialedOptions = new LinkedHashSet<DialedOption>();
		dialedOptions.add(new DialedOption(3, "P1"));
		dialedOptions.add(new DialedOption(3, "P46"));
		dialedOptions.add(new DialedOption(3, "P3"));
		dialedOptions.add(new DialedOption(3, "P2"));
		
		List<DialedOption> sortedDialedOptions = DialedRecordUtils.sortHeader(dialedOptions);
		Assert.assertEquals("P1", sortedDialedOptions.get(0).getColumn());
		Assert.assertEquals("P2", sortedDialedOptions.get(1).getColumn());
		Assert.assertEquals("P3", sortedDialedOptions.get(2).getColumn());
		Assert.assertEquals("P46", sortedDialedOptions.get(3).getColumn());
	}
}
