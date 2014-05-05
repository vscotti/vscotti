package com.abc.ceop.service.impl;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.abc.ceop.phonepoll.service.SecondProcess;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( {"classpath:/spring/model-context.xml"} )
public class SecondProcessImplTest {
	
	@Resource
	private SecondProcess mainExecutor;
	
	@Test
	public void testSecondProcess() throws Exception {
		mainExecutor.execute();
		Assert.assertTrue(true);
	}
	
}
