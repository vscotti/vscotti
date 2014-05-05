package com.abc.ceop.service.impl;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.abc.ceop.phoneapprover.service.EmailSenderService;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring/model-context.xml" })
public class EmailServiceImplTest {

	@Resource
	private EmailSenderService emailService;

	@Test
	public void test_postMail_success() throws MessagingException {
		String[] recipients = {"leandro.contigiani@abcconsulting.com.ar"};
		String subject = "Testing Email Service";
		String message = "Hello, have your received my email?";
		emailService.postMail(recipients, subject, message, null);
	}
//	
//	@Test(expected = MessagingException.class)
//	public void test_postMail_exception() throws MessagingException {
//		String[] recipients = {"incorrectomail"};
//		String subject = "Testing Email Service";
//		String message = "Hello, have your received my email?";
//		emailService.postMail(recipients, subject, message, null);
//	}
	@Test
	public void test_postSingleMail_success() throws MessagingException {
		String recipient = "leandro.contigiani@abcconsulting.com.ar";
		String subject = "Testing Email Service";
		String message = "Hello, have your received my email?";
		String fromAddress = "testRemintent@test.com";
		emailService.postSingleMail(recipient, subject, message, null, fromAddress);
	}
}
