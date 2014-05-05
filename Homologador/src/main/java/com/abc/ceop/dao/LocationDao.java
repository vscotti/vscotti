package com.abc.ceop.dao;

import java.util.List;
import java.util.Map;

import com.abc.ceop.model.entities.Country;
import com.abc.ceop.model.entities.Location;

public interface LocationDao {
		
	Location getLocation (String smallCity, String largeCity, String state, String country, Map <String, Boolean> searchLocationMap);

	List<Location> getAllForCountry(Country country);

	List<Location> getAll();

	Boolean existAnyNationalCode(Country country, List<String> nationalCode);
	
	Integer getMaxNationalCodeLenght(Country country);
}
