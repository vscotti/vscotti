package com.abc.ceop.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.abc.ceop.common.service.CsvFileConfigurationService;
import com.abc.ceop.model.dto.Record;
import com.abc.ceop.model.entities.CsvFileConfiguration;
import com.abc.ceop.phoneapprover.service.PhonesCsvFileReader;
import com.abc.ceop.phoneapprover.service.PhonesExcelFileCreator;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring/model-context.xml" })
public class PhonesExcelFileCreatorImplTest {

	@Resource
	private PhonesExcelFileCreator writer;
	@Resource
	private PhonesCsvFileReader reader;
	@Resource
	private CsvFileConfigurationService csvFileConfigurationService;
	
	@Test
	public void testCreatePhonesExcelFile() throws IOException {
		List<String> phoneValues = Arrays.asList("23132198", "49231712", "321731268"); // type inference OK
		Record record = new Record(null, null, phoneValues, "mirror", null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		List<Record> values = Arrays.<Record> asList(record); // type inference shit
		String createFilepath = writer.createPhonesExcelFile("temp/test.xls", values);
		Assert.assertNotNull(createFilepath);
	}
	
	@Test
	public void testReadAndCreateExcelFile() throws IOException {
		String filePath = "src/test/resources/ARG20110801IBS10.txt";
		CsvFileConfiguration config = csvFileConfigurationService.getCsvFileConfiguration("ARG");
		List<Record> values = reader.readFile(filePath, config);
		String createFilepath = writer.createPhonesExcelFile("temp/test.xls", values);
		Assert.assertNotNull(createFilepath);
	}
	
}
