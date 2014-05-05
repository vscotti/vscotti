package com.abc.ceop.common.service;

import java.util.Date;
import java.util.List;

import com.abc.ceop.model.entities.Campaign;
import com.abc.ceop.model.entities.EmailLogs;

public interface EmailLogsService {
	
		
	List<EmailLogs> getEmailLogs (Date lastSentMail, Campaign campaign);
	
	Date getLastSentMail (Campaign campaing);

}
