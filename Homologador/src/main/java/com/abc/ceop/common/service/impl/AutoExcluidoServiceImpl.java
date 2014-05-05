package com.abc.ceop.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.AutoExcluidoService;
import com.abc.ceop.dao.AutoExcluidoDao;
import com.abc.ceop.model.entities.AutoExcluido;


@Service
public class AutoExcluidoServiceImpl implements AutoExcluidoService {

	private final AutoExcluidoDao autoExcluidoDao;
	
	@Autowired
	public AutoExcluidoServiceImpl (AutoExcluidoDao autoExcluidoDao) {
		this.autoExcluidoDao = autoExcluidoDao;
	}
	
	@Override
	public List<AutoExcluido> outOfTheWebSurvey(int notContactFlag, String countryCampaing) {
	return autoExcluidoDao.outOfTheWebSurvey(notContactFlag, countryCampaing);
	}

}
