package com.abc.ceop.dao.jpa;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.LocationDao;
import com.abc.ceop.model.entities.Country;
import com.abc.ceop.model.entities.Location;

@Repository
public class LocationJpaDao implements LocationDao {

	private static final Class<Location> clazz = Location.class;
	private final Logger logger = LoggerFactory.getLogger(LocationJpaDao.class);

	@PersistenceContext(unitName = "pu")
	private EntityManager em;

	@Cacheable("getLocation")
	@Override
	public Location getLocation(String smallCity, String largeCity,
			String state, String country, Map<String, Boolean> searchLocationMap) {

		StringBuilder sb = new StringBuilder("from Location where");

		if (searchLocationMap.get("smallCity") == true) {
			if (smallCity != null) {
				sb.append(" smallCity like :smallCity");
			} else {
				sb.append(" smallCity is null");
			}
		}
		else {
			sb.append(" smallCity is null");
		}

		if (searchLocationMap.get("largeCity") == true) {
			if (largeCity != null) {
				sb.append(" and largeCity like :largeCity");
			} else {
				sb.append(" and largeCity is null");
			}
		}

		if (searchLocationMap.get("state") == true) {
			if (state != null) {
				sb.append(" and state like :state");
			} else {
				sb.append(" and state is null");
			}
		}
		
		if (searchLocationMap.get("country") == true) {
			if (country != null) {
				sb.append(" and country.name like :country");
			} else {
				sb.append(" and country.name is null");
			}
		}

		String queryString = sb.toString();
		TypedQuery<Location> typedQuery = em.createQuery(queryString, clazz);
		
		if (searchLocationMap.get("smallCity") == true) {
			if (smallCity != null) {
				typedQuery.setParameter("smallCity", smallCity);
			}
		}
		
		if (searchLocationMap.get("largeCity") == true) {
			if (largeCity != null) {
				typedQuery.setParameter("largeCity", largeCity);
			}
		}
		
		if (searchLocationMap.get("state") == true) {
			if (state != null) {
				typedQuery.setParameter("state", state);
			}
		}

		if (searchLocationMap.get("country") == true) {
			if (country != null) {
				typedQuery.setParameter("country", country);
			}
		}

		Location location = null;
		try {
			location = typedQuery.getSingleResult();
		} catch (RuntimeException runtimeException) {
			logger.trace(
					"Could not find a location for smallCity {}, largeCity {}, state {}, country {}",
					new String[] { smallCity, largeCity, state, country });
		}
		return location;
	}

	@Override
	public List<Location> getAll() {
		TypedQuery<Location> typedQuery = em.createQuery(
				"FROM Location as Location", Location.class);
		return typedQuery.getResultList();
	}

	@Override
	public List<Location> getAllForCountry(Country country) {
		TypedQuery<Location> typedQuery = em.createQuery(
				"FROM Location as Location where country.id = :countryid",
				Location.class);
		typedQuery.setParameter("countryid", country.getId());
		return typedQuery.getResultList();
	}

	@Override
	public Boolean existAnyNationalCode(Country country,
			List<String> nationalCode) {
		TypedQuery<Long> query = em
				.createQuery(
						"Select count(*) FROM Location as Location where nationalCode in (?1) and country.id = :countryid",
						Long.class);
		query.setParameter(1, nationalCode);
		query.setParameter("countryid", country.getId());
		Long count = query.getSingleResult();
		return count > 0;
	}

	@Override
	public Integer getMaxNationalCodeLenght(Country country) {
		TypedQuery<Location> typedQuery = em
				.createQuery(
						"FROM Location as Location where country.id = :countryid order by LEN(nationalCode) desc",
						Location.class);
		typedQuery.setParameter("countryid", country.getId());
		List<Location> list = typedQuery.getResultList();
		if (list != null && list.size() > 0) {
			Location loc = list.get(0);
			return loc.getNationalCode().length();
		}
		return 0;
	}

}
