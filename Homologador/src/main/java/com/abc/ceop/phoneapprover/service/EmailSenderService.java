package com.abc.ceop.phoneapprover.service;

import javax.mail.MessagingException;

public interface EmailSenderService {
	
	void postMail(String[] recipients, String subject, String message, String attachment) throws MessagingException;

	void postSingleMail(String email, String subject, String message, String attachment, String fromAddress)throws MessagingException;
	
}
