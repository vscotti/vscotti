package com.abc.ceop.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.ceop.common.service.RecordDetailFilterService;
import com.abc.ceop.dao.RecordDetailFilterDao;
import com.abc.ceop.model.entities.RecordDetailFilter;

@Service
public class RecordDetailFilterServiceImpl implements RecordDetailFilterService{

    private final RecordDetailFilterDao recordRetailFilterDao;
    
    @Autowired
    public RecordDetailFilterServiceImpl (RecordDetailFilterDao recordRetailFilterDao) {
    this.recordRetailFilterDao = recordRetailFilterDao;
    }

    
    @Override
    public List<RecordDetailFilter> getRecordDetailFilter(String country) {
         return recordRetailFilterDao.getRecordDetailFilter(country);
        
    }
    
    

}
