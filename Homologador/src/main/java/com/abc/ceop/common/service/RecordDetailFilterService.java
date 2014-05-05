package com.abc.ceop.common.service;

import java.util.List;

import com.abc.ceop.model.entities.RecordDetailFilter;

public interface RecordDetailFilterService {
    
    List<RecordDetailFilter> getRecordDetailFilter (String country);

}
