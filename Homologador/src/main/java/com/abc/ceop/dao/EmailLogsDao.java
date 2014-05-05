package com.abc.ceop.dao;

import java.util.Date;
import java.util.List;

import com.abc.ceop.model.entities.Campaign;
import com.abc.ceop.model.entities.EmailLogs;

public interface EmailLogsDao {
	
		
	List<EmailLogs> getEmailLogs(Date lastSentMail, Campaign campaign);

	Date getLastSentMailForCountry(Campaign campaign);
	
}