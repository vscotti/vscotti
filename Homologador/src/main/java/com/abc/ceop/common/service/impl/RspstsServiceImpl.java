package com.abc.ceop.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.RspstsService;
import com.abc.ceop.dao.RspstsDao;
import com.abc.ceop.model.entities.Rspsts;

@Service
public class RspstsServiceImpl implements RspstsService{
	
	private final RspstsDao rspstsDao;
	
	@Autowired
	public RspstsServiceImpl (RspstsDao rspstsDao){
		this.rspstsDao = rspstsDao;
	}
	
		@Override
		public List<Rspsts> valuesWebSurvey (int surveyNumber, String campaignNumber) {
			return rspstsDao.valuesWebSurvey(surveyNumber, campaignNumber);
		
	
	}
}
