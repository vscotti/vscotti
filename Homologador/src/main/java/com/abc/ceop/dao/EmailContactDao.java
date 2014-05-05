package com.abc.ceop.dao;

import java.util.List;

import com.abc.ceop.model.entities.EmailContact;

public interface EmailContactDao {
	
	List<EmailContact> getErrorStatsEmailContacts();

	List<EmailContact> getEmailContacts(String campaign, String type);
}
