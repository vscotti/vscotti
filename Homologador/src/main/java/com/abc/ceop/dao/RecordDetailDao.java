package com.abc.ceop.dao;

import java.util.Date;
import java.util.List;

import com.abc.ceop.model.entities.RecordDetail;

public interface RecordDetailDao {
	
	RecordDetail getRecordDetailByPhoneId(String phoneId);
	
	void saveRecordDetail  (String suscriberId, int contacted);
	
	List<RecordDetail> getRecordDetails(Date dateFrom, Date dateTo, String countryCampaing);

//	boolean existsRecordDetail(String phoneId, Date date, String countryCampaing);

	Date getLastDateForCountry(String countryCampaign);
	
	RecordDetail getRecordDetailBySuscriberId (String SuscriberId);

	List<RecordDetail> getRecordDetailUncontacted(Date dateFrom, Date dateTo, String countryCampaign, int contacted);
}
