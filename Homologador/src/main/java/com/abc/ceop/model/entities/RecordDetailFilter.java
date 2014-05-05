package com.abc.ceop.model.entities;

import javax.annotation.concurrent.Immutable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Immutable
public class RecordDetailFilter {

    @SuppressWarnings("unused")
    @Id
    @GeneratedValue
    private Long id;
    private String Country;
    private boolean setOn;
    private int columnTofilter;
    @Column(nullable = false)
    private String pattern;

    public RecordDetailFilter(Long id, String country, boolean setOn, int columnToFilter, String pattern) {
        this.id = id;
        Country = country;
        this.setOn = setOn;
        this.pattern = pattern;
        this.columnTofilter = columnToFilter;
    }

    public RecordDetailFilter() {
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public boolean isSetOn() {
        return setOn;
    }

    public void setSetOn(boolean setOn) {
        this.setOn = setOn;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public int getColumnTofilter() {
        return columnTofilter;
    }

    public void setColumnTofilter(int columnTofilter) {
        this.columnTofilter = columnTofilter;
    }

}
