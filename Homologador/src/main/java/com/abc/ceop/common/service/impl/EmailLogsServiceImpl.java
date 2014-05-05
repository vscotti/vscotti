package com.abc.ceop.common.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.EmailLogsService;
import com.abc.ceop.dao.EmailLogsDao;
import com.abc.ceop.model.entities.Campaign;
import com.abc.ceop.model.entities.EmailLogs;


@Service
public class EmailLogsServiceImpl implements EmailLogsService {

	private final EmailLogsDao emailLogsDao;

	@Autowired
	public EmailLogsServiceImpl(EmailLogsDao emailLogsDao) {
		this.emailLogsDao = emailLogsDao;
	}

	@Override
	public List<EmailLogs> getEmailLogs(Date lastSentMail, Campaign campaign) {
		return emailLogsDao.getEmailLogs(lastSentMail, campaign);
	}

	@Override
	public Date getLastSentMail(Campaign campaign) {
		return emailLogsDao.getLastSentMailForCountry(campaign);
	}
	
}