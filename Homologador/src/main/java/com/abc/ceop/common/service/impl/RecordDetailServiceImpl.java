package com.abc.ceop.common.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.RecordDetailService;
//import com.abc.ceop.dao.GenericDao;
import com.abc.ceop.dao.RecordDetailDao;
import com.abc.ceop.model.entities.RecordDetail;

@Service
public class RecordDetailServiceImpl implements RecordDetailService {
	
	private final RecordDetailDao recordDetailDao;
//	private final GenericDao genericJpaDAO;
	
	@Autowired
//	public RecordDetailServiceImpl(RecordDetailDao recordDetailDao, @Qualifier("genericJpaDao")GenericDao genericJpaDao) {
	public RecordDetailServiceImpl (RecordDetailDao recordDetailDao) {
	this.recordDetailDao = recordDetailDao;
//		this.genericJpaDAO = genericJpaDao;
	}
	
	@Override
	public RecordDetail getRecordDetailByPhoneId(String phoneId) {
		return recordDetailDao.getRecordDetailByPhoneId(phoneId);
	}

	@Override
	public List<RecordDetail> getRecordDetails(Date dateFrom, Date dateTo, String countryCampaing) {
		return recordDetailDao.getRecordDetails(dateFrom, dateTo, countryCampaing);
	}

	@Override
	public Date getLastDateForCountry(String countryCampaign) {
		return recordDetailDao.getLastDateForCountry(countryCampaign);
	}
	@Override
	public RecordDetail getRecordDetailBySuscriberID (String suscriberId) {
		return recordDetailDao.getRecordDetailBySuscriberId (suscriberId);
	}
	
//	@Override
//	public void saveRecordDetail  (String suscriberId, int contacted) {
//		
//		RecordDetail updateRecordDetail = new RecordDetail();
//		updateRecordDetail = recordDetailDao.getRecordDetailBySuscriberId(suscriberId);
//		updateRecordDetail.setContacted(contacted);
//		this.genericJpaDAO.update(updateRecordDetail);
//			} 
	
//	@Override
//	public List<RecordDetail> getRecordDetailUncontacted (Date dateFrom, Date dateTo, String countryCampaing, int contacted) {
//		return recordDetailDao.getRecordDetailUncontacted(dateFrom, dateTo, countryCampaing, contacted);
//	}
//	
	
//	@Override
//	public boolean existsRecordDetail(String phoneId, Date date, String countryCampaing) {
//		return recordDetailDao.existsRecordDetail(phoneId, date, countryCampaing);
//	}

}
