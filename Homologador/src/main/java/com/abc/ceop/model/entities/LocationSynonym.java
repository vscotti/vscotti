package com.abc.ceop.model.entities;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"synonym"}))
public class LocationSynonym {

	@SuppressWarnings("unused")
	@Id
	@GeneratedValue
	private Long id;

	private String synonym;
	private String word;
	
	public LocationSynonym() {
		
	}
	
	public LocationSynonym(String synonym, String word) {
		this.synonym = synonym;
		this.word = word;
	}
	
	public String getSynonym() {
		return synonym;
	}

	public String getWord() {
		return word;
	}
	
}
