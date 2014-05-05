package com.abc.ceop.phoneapprover.service;

import java.util.List;

import com.abc.ceop.model.dto.Record;
import com.abc.ceop.model.entities.CsvFileConfiguration;

public interface PhonesCsvFileReader {

	List<Record> readFile(String filePath, CsvFileConfiguration config);
	
}
