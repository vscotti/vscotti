package com.abc.ceop.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.EmailSuscriberWebDao;
import com.abc.ceop.model.entities.EmailSuscriberWeb;
@Repository
public class EmailSuscriberWebJpaDao implements EmailSuscriberWebDao {
	
	@PersistenceContext(unitName ="pu")
	private EntityManager em;

	@Override
	public void updateSWebs(int id_entrevistado) {

		
		Query query = em.createNativeQuery("update EmailSuscriberWeb set webSurveyCompleted = 1 where suscriber = :id_entrevistado" , EmailSuscriberWeb.class);
		query.setParameter("id_entrevistado",id_entrevistado);
		query.executeUpdate();
		
	}
	@Override
	public List <String> getCompletedSurvey (int websurveyCompleted, String country, int cantidadDeDiasARestar, Date dateFrom) {
		
		TypedQuery<String> typedQuery = em.createQuery("select suscriber from EmailSuscriberWeb as rd WHERE webSurveyCompleted = :webSurveyCompleted and country = :country and " +
				" date <= :dateFrom and date >= getdate()- :cantidadDeDiasARestar", String.class);
		typedQuery.setParameter("webSurveyCompleted", websurveyCompleted );
		typedQuery.setParameter("cantidadDeDiasARestar", cantidadDeDiasARestar );
		typedQuery.setParameter("country", country);
		typedQuery.setParameter("dateFrom", dateFrom);
		List<String> suscribers = typedQuery.getResultList();
		if (suscribers != null && suscribers.size() > 0) {
			return suscribers;
		}
		return null;
	}
	}


