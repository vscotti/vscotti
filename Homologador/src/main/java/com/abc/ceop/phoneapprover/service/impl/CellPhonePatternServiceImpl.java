package com.abc.ceop.phoneapprover.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.abc.ceop.dao.CellPhonePatternDao;
import com.abc.ceop.model.entities.CellPhonePattern;
import com.abc.ceop.model.entities.Location;
import com.abc.ceop.phoneapprover.service.CellPhonePatternService;

@Service
public class CellPhonePatternServiceImpl implements CellPhonePatternService {

	private final CellPhonePatternDao cellPhonePatternDao;
	
	@Autowired
	public CellPhonePatternServiceImpl(CellPhonePatternDao cellPhonePatternDao) {
		this.cellPhonePatternDao = cellPhonePatternDao;
	}
	
	@Cacheable("getMostSpecificPatternAction")
	@Override
	public List<CellPhonePattern> getMostSpecificPatternAction(Location location) {
		return cellPhonePatternDao.getMostSpecificPatternAction(location);
	}

}
