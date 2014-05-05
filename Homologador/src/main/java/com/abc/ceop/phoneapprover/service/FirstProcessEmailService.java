package com.abc.ceop.phoneapprover.service;

import com.abc.ceop.model.dto.FirstProcessResultInfo;

public interface FirstProcessEmailService {

	void sendEmail(FirstProcessResultInfo info, boolean hasNoDB, boolean hasBelowAverager);
	
}
