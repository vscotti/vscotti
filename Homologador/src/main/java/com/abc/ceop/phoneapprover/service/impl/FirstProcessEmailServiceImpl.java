package com.abc.ceop.phoneapprover.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.model.dto.FirstProcessResultInfo;
import com.abc.ceop.model.entities.EmailContact;
import com.abc.ceop.phoneapprover.service.EmailContactService;
import com.abc.ceop.phoneapprover.service.EmailRenderer;
import com.abc.ceop.phoneapprover.service.EmailSenderService;
import com.abc.ceop.phoneapprover.service.FirstProcessEmailService;

@Service
public class FirstProcessEmailServiceImpl implements FirstProcessEmailService {

	private final Logger logger = LoggerFactory.getLogger(FirstProcessEmailServiceImpl.class);
	
	private final EmailContactService emailContactService;
	private final EmailSenderService emailSenderService;
	private final EmailRenderer emailRenderer;
	
	@Autowired
	public FirstProcessEmailServiceImpl(
			EmailContactService emailContactService,
			EmailSenderService emailSenderService,
			EmailRenderer emailRenderer) {
		this.emailContactService = emailContactService;
		this.emailSenderService = emailSenderService;
		this.emailRenderer = emailRenderer;
	}
	
	@Override
	public void sendEmail(FirstProcessResultInfo info, boolean hasNoDB, boolean hasBelowAverager) {
		try {
			boolean isSecondEmail = hasNoDB || hasBelowAverager;
			String title = !isSecondEmail? "CEOP: Homologador de telefonos: Alarma Fields Services" : 
										   "CEOP: Homologador de telefonos: " + (hasNoDB? "Alerta por no envío de base" : "") + (hasNoDB && hasBelowAverager? " / " : "") + (hasBelowAverager? "Alerta por poca cantidad de teléfonos en la base." : "");
			String processedTemplate = emailRenderer.render(title, info, isSecondEmail);
			emailSenderService.postMail(getEmails(info, isSecondEmail), title, processedTemplate, null);
		} catch (Exception exception) {
			logger.error("Error enviando email: {} / {}" ,exception.getClass().toString() ,exception.getMessage());
		}
	}
	
	private String[] getEmails(FirstProcessResultInfo info, boolean isSecondEmail) {
		List<EmailContact> emailContacts = new ArrayList<EmailContact>();
		if(!isSecondEmail) {
			emailContacts = emailContactService.getErrorStatsEmailContacts();
		} else {
			if(info.getCampaignCountry() != null &&
					info.getCampaignCountry().getCampaign() != null) {
				emailContacts = emailContactService.getEmailContacts(info.getCampaignCountry().getCampaign(), "UMBRALESALERT");
			}
		}
		String[] emails = new String[emailContacts.size()];
		int i = 0;
		for (EmailContact emailContact : emailContacts) {
			emails[i++] = emailContact.getEmail();
		}
		return emails;
	}

}
