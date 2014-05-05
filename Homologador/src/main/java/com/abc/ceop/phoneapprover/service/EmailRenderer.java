package com.abc.ceop.phoneapprover.service;

import com.abc.ceop.model.dto.FirstProcessResultInfo;
import com.abc.ceop.model.dto.SecondProcessResultInfo;

public interface EmailRenderer {

	String render(String title, FirstProcessResultInfo info, boolean isSecondEmail) throws Exception;
	
	String render(String title,SecondProcessResultInfo info) throws Exception;
	
	String render (String title, String encrypterSubscriber )throws Exception;


}
