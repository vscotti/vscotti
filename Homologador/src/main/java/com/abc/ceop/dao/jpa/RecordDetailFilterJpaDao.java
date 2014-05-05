package com.abc.ceop.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.RecordDetailFilterDao;
import com.abc.ceop.model.entities.RecordDetailFilter;

@Repository
public class RecordDetailFilterJpaDao implements RecordDetailFilterDao {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    public List<RecordDetailFilter> getRecordDetailFilter(String country) {

        TypedQuery<RecordDetailFilter> typedQuery = em.createQuery("from RecordDetailFilter where Country = :country and SetOn = 1", RecordDetailFilter.class);
        typedQuery.setParameter("country", country);
        List<RecordDetailFilter> recordDetailFilter = typedQuery.getResultList();
        if (recordDetailFilter.size() > 0 && recordDetailFilter != null) {
            return recordDetailFilter;
        } else {

            return null;
        }
    }

}
