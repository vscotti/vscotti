package com.abc.ceop.phoneapprover.service;

import java.util.List;

public interface FirstProcess {
	
	void execute();
	
	List<String> executeCorrection(String filepath);
}
