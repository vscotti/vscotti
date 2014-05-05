package com.abc.ceop.dao;

import java.util.List;

import com.abc.ceop.model.entities.RecordDetailFilter;

public interface RecordDetailFilterDao {
    
    List<RecordDetailFilter> getRecordDetailFilter (String country);

}
