package com.abc.ceop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.abc.ceop.model.dto.DialedOption;
import com.abc.ceop.phonepoll.service.PollQuestionMatcherService;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/model-context.xml"})
public class PollquestionMatcherServiceImplTest {

	@Resource
	private PollQuestionMatcherService pollQuestionMatcherService;
	
	@Test
	public void test_corrected_Argentina() {
		List<DialedOption> list = pollQuestionMatcherService.getAllQuestionsByCampaing("906");
		Assert.assertTrue(list != null);
	}
	@Test
	public void test_get_Code_By_CallId() {
		String nd = pollQuestionMatcherService.getCodeByCallId((long) 2);
		Assert.assertTrue(nd != null);
	}
	@Test
	public void test_get_Code_By_CallIdFalse() {
		String nd = pollQuestionMatcherService.getCodeByCallId((long) 98);
		Assert.assertTrue(nd == null);
	}
}
