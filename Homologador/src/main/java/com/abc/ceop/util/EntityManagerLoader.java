package com.abc.ceop.util;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerLoader {
	
	@PersistenceContext(unitName="pu")
	private EntityManager puEm;
	
	@PersistenceContext(unitName="puwebcati")
	private EntityManager puWebCatiEm;

	public EntityManager getPuEm() {
		return puEm;
	}

	public EntityManager getPuWebCatiEm() {
		return puWebCatiEm;
	}

}
