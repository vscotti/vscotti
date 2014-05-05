package com.abc.ceop.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.LocationSearchConfigDao;
import com.abc.ceop.model.entities.LocationSearchConfig;
import com.google.common.collect.Iterables;
@Repository
public class LocationSearchConfigJpaDao implements LocationSearchConfigDao {
	@PersistenceContext(unitName="pu")
	private EntityManager em;
		
	@Override
	public  LocationSearchConfig  getLocationSearchConfig (String countryCampaign ){
			
		TypedQuery<LocationSearchConfig > query = em.createQuery(" FROM LocationSearchConfig WHERE countryCampaign = :countryCampaign", LocationSearchConfig.class);
			 query.setParameter("countryCampaign", countryCampaign);
			 return getSingleResultOrNull(query);
		}
	
	
	
	private LocationSearchConfig getSingleResultOrNull(final TypedQuery<LocationSearchConfig> query) {
	    return Iterables.getOnlyElement(query.getResultList(), null); 
	}




	
	
}
