package com.abc.ceop.phonepoll.service;

import com.abc.ceop.model.dto.SecondProcessCommonData;

public interface DialRecordTxtFileCreator {

	String createFile(SecondProcessCommonData secondProcessCommonData, boolean lastHourcheck);
	
	
}
