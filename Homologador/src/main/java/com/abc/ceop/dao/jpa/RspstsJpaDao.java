package com.abc.ceop.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.RspstsDao;
import com.abc.ceop.model.entities.Rspsts;

@Repository
public class RspstsJpaDao implements RspstsDao{
	
	
	@PersistenceContext(unitName="puwebcati")
	private EntityManager em;
//TOdo Corregir el nombre de los dos parametros que le pasamos a la query
	@Override
	public List<Rspsts> valuesWebSurvey (int surveyNumber, String campaignNumber ) {
		TypedQuery<Rspsts> typedQuery= em.createQuery("FROM Rspsts as rs where numeroDeEncuesta = :surveyNumber and id_trabajo = :campaignNumber" , Rspsts.class);
		typedQuery.setParameter("surveyNumber", surveyNumber);
		typedQuery.setParameter("campaignNumber", campaignNumber);

		List<Rspsts> values = typedQuery.getResultList();
		if (values != null && values.size() > 0) {
			return values;
		}
			return null;
	}


	
}
