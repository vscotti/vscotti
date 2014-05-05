package com.abc.ceop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.ceop.common.service.CsvFileConfigurationService;
import com.abc.ceop.model.dto.Record;
import com.abc.ceop.model.entities.CsvFileConfiguration;
import com.abc.ceop.phoneapprover.service.PhonesCsvFileReader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring/model-context.xml" })
public class PhonesCsvFileReaderImplTest {

	@Resource
	private PhonesCsvFileReader target;	
	@Resource
	private CsvFileConfigurationService csvFileConfigurationService;
	
	@Test
	public void testWithCSVFile() {
		CsvFileConfiguration config = csvFileConfigurationService.getCsvFileConfiguration("ARG");
		List<Record> records = target.readFile("src/test/resources/ARG20110801IBS10.txt", config);
		Assert.assertEquals(623, records.size());
	}
	
}
