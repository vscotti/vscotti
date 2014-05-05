package com.abc.ceop.common.service;

import java.util.List;
import java.util.Map;

import com.abc.ceop.model.entities.Country;
import com.abc.ceop.model.entities.Location;

public interface LocationService {

	Location lookupLocationLike(Location location, Map <String, Boolean> searchLocationMap);
	
	List<Location> getAllForCountry(Country country);
	
	List<Location> getAll();
	
	Boolean existAnyNationalCode(Country country, List<String> nationalCode);
	
	Integer getMaxNationalCodeLenght(Country country);
}
