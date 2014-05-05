package com.abc.ceop.model.entities;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Immutable
@Table(uniqueConstraints = {
	@UniqueConstraint(name = "callId", columnNames = {"callId"}),
	@UniqueConstraint(name = "both", columnNames = {"code", "callId"})
	})
public class PollQuestionMatcher {
		
	@Id
	private String code;
	private Long callId;
	
	public PollQuestionMatcher(Long callId, 
							   String code) {
		this.callId = callId;
		this.code = code;
	}
	
	public PollQuestionMatcher() {
		
	}

	public Long getCallId() {
		return callId;
	}
	public String getCode() {
		return code;
	}
	
}
