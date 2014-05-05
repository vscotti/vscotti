package com.abc.ceop.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.RecordDetailDao;
import com.abc.ceop.model.entities.RecordDetail;



@Repository
public class RecordDetailJpaDao implements RecordDetailDao {

	@PersistenceContext(unitName="pu")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public RecordDetail getRecordDetailByPhoneId(String phoneId) {
		Query qery = em.createNativeQuery("SELECT * FROM RecordDetail WHERE CAST(phone as numeric(19,0)) = :phoneId order by date desc", RecordDetail.class);
		
		Long phonenumber = 0L;
		if(phoneId != null &&
				StringUtils.isNumeric(phoneId)) {
			phonenumber = Long.parseLong(phoneId);
		}
		
		qery.setParameter("phoneId", phonenumber);
		List<RecordDetail> records = qery.getResultList();
		if (records != null &&
				records.size() > 0) {
			return records.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public RecordDetail getRecordDetailBySuscriberId(String suscriberId) {
		 Query query = em.createNativeQuery("SELECT  * FROM RecordDetail WHERE suscriberId = :suscriberId", RecordDetail.class);
		  query.setParameter("suscriberId", suscriberId);
		  List<RecordDetail> suscribersID = query.getResultList();
		  if (suscribersID != null && suscribersID.size() > 0){
			  return suscribersID.get(0);
		  }
		  		return null;
	}
	
	
	  @Override
	      public  void  saveRecordDetail (String suscriberId, int contacted) {
		  Query query = em.createNativeQuery("UPDATE RecordDetail set contacted= :contacted where suscriberId = :suscriberId", RecordDetail.class);
		  query.setParameter("suscriberId", suscriberId);
		  query.setParameter("contacted", contacted);
		  query.executeUpdate();
		  
	  }

	  @Override
	  public List<RecordDetail> getRecordDetailUncontacted (Date dateFrom, Date dateTo, String countryCampaing, int contacted) {
		  TypedQuery<RecordDetail> typedQuery = em.createQuery("FROM RecordDetail as rd WHERE date >= :dateFrom and date <= :dateTo and countryCampaing = :countryCampaing and contacted =:contacted", RecordDetail.class);
		  typedQuery.setParameter("dateFrom", dateFrom);
		  typedQuery.setParameter("dateTo", dateTo);
		  typedQuery.setParameter("contacted", contacted);
		  typedQuery.setParameter("countryCampaing", countryCampaing);
		  List<RecordDetail> uncontactedPhones = typedQuery.getResultList();
		  if (uncontactedPhones != null && uncontactedPhones.size() > 0) {
			  return uncontactedPhones;
		  }
		  return null;
	  }

	@Override
	public List<RecordDetail> getRecordDetails(Date dateFrom, Date dateTo, String countryCampaing) {
		TypedQuery<RecordDetail> typedQuery = em.createQuery("FROM RecordDetail as rd WHERE date >= :dateFrom and date <= :dateTo and countryCampaing = :countryCampaing", RecordDetail.class);
		typedQuery.setParameter("dateFrom", dateFrom);
		typedQuery.setParameter("dateTo", dateTo);
		typedQuery.setParameter("countryCampaing", countryCampaing);
		List<RecordDetail> recordDetails = typedQuery.getResultList();
		if (recordDetails != null && recordDetails.size() > 0) {
			return recordDetails;
		}
		return null;
	}

	@Override
	public Date getLastDateForCountry(String countryCampaign) {
		Query query = em.createNativeQuery("Select top 1 * FROM RecordDetail as rd where countryCampaing = :countryCampaign order by date desc", RecordDetail.class);
		query.setParameter("countryCampaign", countryCampaign);
		try {
			RecordDetail date = (RecordDetail) query.getSingleResult();
			return date != null? date.getDate(): null;
		} catch(NoResultException e) {}
		return null;
	}
	
}
