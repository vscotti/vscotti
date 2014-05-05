package com.abc.ceop.phoneapprover.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.model.dto.SecondProcessResultInfo;
import com.abc.ceop.model.entities.EmailContact;
import com.abc.ceop.phoneapprover.service.EmailContactService;
import com.abc.ceop.phoneapprover.service.EmailRenderer;
import com.abc.ceop.phoneapprover.service.EmailSenderService;
import com.abc.ceop.phoneapprover.service.SecondProcessEmailService;

@Service
public class SecondProcessEmailServiceImpl implements SecondProcessEmailService {

	private final Logger logger = LoggerFactory.getLogger(SecondProcessEmailServiceImpl.class);
	
	private final EmailContactService emailContactService;
	private final EmailSenderService emailSenderService;
	private final EmailRenderer emailRenderer;
	
	@Autowired
	public SecondProcessEmailServiceImpl(
			EmailContactService emailContactService,
			EmailSenderService emailSenderService,
			EmailRenderer emailRenderer) {
		this.emailContactService = emailContactService;
		this.emailSenderService = emailSenderService;
		this.emailRenderer = emailRenderer;
	}
	
	@Override
	public void sendEmail(SecondProcessResultInfo info) {
		try {
			String title = "CEOP: Homologador de telefonos: Alerta por Detractores";
			String processedTemplate = emailRenderer.render(title, info);
			emailSenderService.postMail(getEmails(info), title, processedTemplate, info.getFileName());
		} catch (Exception exception) {
			logger.error("Error enviando email. {} / {}" ,exception.getClass().toString() ,exception.getMessage());
		}
	}
	
	private String[] getEmails(SecondProcessResultInfo info) {
		List<EmailContact> emailContacts = emailContactService.getEmailContacts(info.getCampaign(), "SOSALERT");
		String[] emails = new String[emailContacts.size()];
		int i = 0;
		for (EmailContact emailContact : emailContacts) {
			emails[i++] = emailContact.getEmail();
		}
		return emails;
	}

}
