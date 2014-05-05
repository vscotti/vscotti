package com.abc.ceop.service.impl;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.abc.ceop.phoneapprover.service.FirstProcess;

@Transactional(readOnly = false)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/model-context.xml"})
public class FirstProcessImplTest {

	@Resource
	private FirstProcess mainExecutor;

	@Test
	public void testFirstProcessExecuteCorrection() throws Exception {
		mainExecutor.executeCorrection("src/test/resources/ARG20110801IBS10.txt");
		Assert.assertTrue(true);
	}
	
	@Test
	public void testFirstProcessExecute() throws Exception {
		mainExecutor.execute();
		Assert.assertTrue(true);
	}
	
}
