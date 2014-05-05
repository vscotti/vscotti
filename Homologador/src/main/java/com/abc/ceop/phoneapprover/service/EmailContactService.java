package com.abc.ceop.phoneapprover.service;

import java.util.List;

import com.abc.ceop.model.entities.EmailContact;

public interface EmailContactService {
	
	List<EmailContact> getEmailContacts(String campaign, String type);

	List<EmailContact> getErrorStatsEmailContacts();

}
