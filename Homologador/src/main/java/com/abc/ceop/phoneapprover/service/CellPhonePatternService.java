package com.abc.ceop.phoneapprover.service;

import java.util.List;

import com.abc.ceop.model.entities.CellPhonePattern;
import com.abc.ceop.model.entities.Location;

public interface CellPhonePatternService {

	List<CellPhonePattern> getMostSpecificPatternAction(Location location);
	
}
