package com.abc.ceop.phoneapprover.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.dao.EmailContactDao;
import com.abc.ceop.model.entities.EmailContact;
import com.abc.ceop.phoneapprover.service.EmailContactService;

@Service
public class EmailContactServiceImpl implements EmailContactService {
		
	private EmailContactDao emailContactDao;
	
	@Autowired
	public EmailContactServiceImpl(EmailContactDao emailContactDao) {
		this.emailContactDao = emailContactDao;
	}
	
	@Override
	public List<EmailContact> getErrorStatsEmailContacts() {
		return emailContactDao.getErrorStatsEmailContacts();
	}

	@Override
	public List<EmailContact> getEmailContacts(String campaign, String type) {
		return emailContactDao.getEmailContacts(campaign, type);
	}

}
