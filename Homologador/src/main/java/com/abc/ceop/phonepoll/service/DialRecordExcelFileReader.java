package com.abc.ceop.phonepoll.service;

import java.util.List;

import com.abc.ceop.model.dto.DialedRecord;

public interface DialRecordExcelFileReader {

	List<DialedRecord> readExcel(String filepath);
	
}
