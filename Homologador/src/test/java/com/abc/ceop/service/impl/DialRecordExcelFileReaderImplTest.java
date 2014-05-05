package com.abc.ceop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.ceop.model.dto.DialedRecord;
import com.abc.ceop.phonepoll.service.DialRecordExcelFileReader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring/model-context.xml" })
public class DialRecordExcelFileReaderImplTest {
	
	@Resource
	private DialRecordExcelFileReader dialRecordExcelFileReader;

	@Test
	public void testReadExcel() {
		List<DialedRecord> dialedRecords = dialRecordExcelFileReader.readExcel("src/test/resources/S907-20120110__15-28-00.xls");
		Assert.assertEquals(4473, dialedRecords.size());
	}
	
}
