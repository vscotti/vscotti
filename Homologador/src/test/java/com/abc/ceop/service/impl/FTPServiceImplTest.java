package com.abc.ceop.service.impl;

import java.io.File;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.abc.ceop.common.service.FTPService;
import com.abc.ceop.model.dto.FTPCredentials;
import com.abc.ceop.model.entities.Configuration;
import com.abc.ceop.phoneapprover.service.FirstProcessConfigurationService;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring/model-context.xml" })
public class FTPServiceImplTest {

	@Resource
	private FTPService ftpService;
	@Resource
	private FirstProcessConfigurationService configurationService;
	
	@Test
	public void testCopyLocalAndRemove() throws Exception {
		String ftpPath = configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.INPUT_FTP_PATH);
		String tempPath = configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.TEMP_PATH);
		FTPCredentials ftpCredentials = new FTPCredentials(configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.INPUT_FTP_HOST),
				configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.INPUT_FTP_USERNAME),
				configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.INPUT_FTP_PASSWORD));
		String fileName = null;
		while ((fileName = ftpService.copyLocalAndRemove(ftpPath, tempPath, ftpCredentials)) != null) {
			File file = new File(fileName);
			Assert.assertTrue(file.exists());
		}
		Assert.assertTrue(fileName == null);
	}
	
	@Test
	public void testUpload() throws Exception {
		String ftpPath = configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.INPUT_FTP_PATH);
		String tempPath = configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.TEMP_PATH);
		FTPCredentials ftpCredentials = new FTPCredentials(configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.INPUT_FTP_HOST),
				configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.INPUT_FTP_USERNAME),
				configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.INPUT_FTP_PASSWORD));
		String fileName = ftpService.copyLocalAndRemove(ftpPath, tempPath, ftpCredentials);
		
		String ftpPath2 = configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.OUTPUT_FTP_PATH);
		FTPCredentials ftpCredentials2 = new FTPCredentials(configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.OUTPUT_FTP_HOST),
				configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.OUTPUT_FTP_USERNAME),
				configurationService.getConfigurationValue(Configuration.FirstProcessConfiguration.OUTPUT_FTP_PASSWORD));
		boolean couldUpload = ftpService.upload(fileName, ftpPath2, ftpCredentials2);
		Assert.assertTrue(couldUpload);
	}
	
}
