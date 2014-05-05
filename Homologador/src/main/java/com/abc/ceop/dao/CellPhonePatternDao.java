package com.abc.ceop.dao;

import java.util.List;

import com.abc.ceop.model.entities.CellPhonePattern;
import com.abc.ceop.model.entities.Location;

public interface CellPhonePatternDao {
	
	List<CellPhonePattern> getMostSpecificPatternAction(Location location);

	List<CellPhonePattern> findAll();
}
