package com.abc.ceop.phoneapprover.service;

import com.abc.ceop.model.dto.SecondProcessResultInfo;

public interface SecondProcessEmailService {

	void sendEmail(SecondProcessResultInfo info);
	
}
