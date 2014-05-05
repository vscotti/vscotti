package com.abc.ceop.common.service;

import java.util.Date;
import java.util.List;

import com.abc.ceop.model.entities.RecordDetail;

public interface RecordDetailService {
	
	RecordDetail getRecordDetailByPhoneId(String phoneId);
	
//	boolean existsRecordDetail(String phoneId, Date date, String countryCampaing);
	
	List<RecordDetail> getRecordDetails(Date dateFrom, Date dateTo, String countryCampaing);
			
	Date getLastDateForCountry(String countryCampaign);

//	void saveRecordDetail  (String suscriberId, int contacted);

	RecordDetail getRecordDetailBySuscriberID(String suscriberId);

//	List<RecordDetail> getRecordDetailUncontacted(Date dateFrom, Date dateTo, String countryCampaing, int contacted);
}


