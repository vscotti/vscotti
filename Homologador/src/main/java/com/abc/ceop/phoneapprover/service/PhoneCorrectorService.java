package com.abc.ceop.phoneapprover.service;

import com.abc.ceop.model.dto.FirstProcessCommonData;
import com.abc.ceop.model.entities.Country;
import com.abc.ceop.model.entities.Location;

public interface PhoneCorrectorService {

	boolean isCorrectable(String phone, Location location);
	
	String correct(String phone, Location location, FirstProcessCommonData commonData, boolean isMobile);
	
	String easyCorrect(String phone, Country country, FirstProcessCommonData commonData, boolean isMobile);
}
