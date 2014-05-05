package com.abc.ceop.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.LocationService;
import com.abc.ceop.dao.LocationDao;
import com.abc.ceop.dao.LocationSynonymDao;
import com.abc.ceop.model.entities.Country;
import com.abc.ceop.model.entities.Location;

@Service
public class LocationServiceImpl implements LocationService {

	private final LocationDao locationDao;
	private final LocationSynonymDao synonymDao;
	
	@Autowired
	public LocationServiceImpl(LocationDao locationDao, LocationSynonymDao synonymDao) {
		this.locationDao = locationDao;
		this.synonymDao = synonymDao;
	}
	
	@Cacheable("lookupLocationLike")
	@Override
	public Location lookupLocationLike(Location location, Map <String, Boolean> searchLocationMap) {
		String country = location.getCountry().getName();
		String state = location.getState();
		String largeCity = location.getLargeCity();
		String smallCity = location.getSmallCity();
		
		if (location.getCountry() != null) {
			String countryLookup = synonymDao.lookupForWord(location.getCountry().getName());
			if (countryLookup != null) {
				country = countryLookup;
			}
		}
		if (location.getState() != null) {
			String stateLookup = synonymDao.lookupForWord(location.getState());
			if (stateLookup != null) {
				state = stateLookup;
			}
		}
		if (location.getLargeCity() != null) {
			String largeCityLookup = synonymDao.lookupForWord(location.getLargeCity());
			if (largeCityLookup != null) {
				largeCity = largeCityLookup;
			}
		}
		if (location.getSmallCity() != null) {
			String smallCityLookup = synonymDao.lookupForWord(location.getSmallCity());
			if (smallCityLookup != null) {
				smallCity = smallCityLookup;
			}
		}
		
		return locationDao.getLocation(smallCity, largeCity, state, country, searchLocationMap);
	}

	@Override
	public List<Location> getAll() {
		return locationDao.getAll();
	}

	@Override
	public List<Location> getAllForCountry(Country country) {
		return locationDao.getAllForCountry(country);
	}

	@Override
	public Boolean existAnyNationalCode(Country country, List<String> nationalCode) {
		return locationDao.existAnyNationalCode(country, nationalCode);
	}

	@Override
	public Integer getMaxNationalCodeLenght(Country country) {
		return locationDao.getMaxNationalCodeLenght(country);
	}
	
}
