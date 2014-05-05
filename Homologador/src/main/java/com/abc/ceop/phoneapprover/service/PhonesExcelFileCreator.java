package com.abc.ceop.phoneapprover.service;

import java.util.List;

import com.abc.ceop.model.dto.Record;

public interface PhonesExcelFileCreator {
	
	String createPhonesExcelFile(String excelFilePath, List<Record> values);

}
