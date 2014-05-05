package com.abc.ceop.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.QuienRespondeLaEncuestaDao;
import com.abc.ceop.model.entities.QuienRespondeLaEncuesta;



@Repository
public class QuienRespondeLaEncuestaJpaDao implements QuienRespondeLaEncuestaDao{
	
	@PersistenceContext(unitName="puwebcati")
	private EntityManager em;


	@Override
	public List<QuienRespondeLaEncuesta> getSurveyComplete(String surveyNumberWebCati) {
		
		TypedQuery<QuienRespondeLaEncuesta> typedQuery= em.createQuery("FROM QuienRespondeLaEncuesta as rs where id_trabajo = :id_trabajo  and  encuestaCompleta  = :encuestaCompleta" ,
																		QuienRespondeLaEncuesta.class);
		typedQuery.setParameter("id_trabajo", surveyNumberWebCati);
		typedQuery.setParameter("encuestaCompleta", 1 );
		List<QuienRespondeLaEncuesta> surveyComplete = typedQuery.getResultList();
		if (surveyComplete != null && surveyComplete.size() > 0) {
			return surveyComplete;
		}
		return null;
	}
	
	@Override
	public List <QuienRespondeLaEncuesta> getSurveyCompleteNdays (String surveyNumberWebCati,  int cantidadDeDiasARestar, List <String> suscribers) {
		
		Query query= em.createNativeQuery("select * from F2QuienRespondeLaEncuesta where convert(datetime,replicate('0',4-len(convert(varchar,RespAno)))+" +
																		"convert(varchar,RespAno)+replicate('0',2-len(convert(varchar,RespMes)))+convert(varchar,RespMes)+replicate('0',2-len(convert(varchar,RespDia)))+" +
																		"convert(varchar,RespDia)) >= getdate() - :cantidadDeDiasARestar and id_trabajo = :id_trabajo  and  " +
																		"encuestaCompleta  = :encuestaCompleta  and id_entrevistado in(:suscribers)" ,
																		QuienRespondeLaEncuesta.class);
						
		query.setParameter("id_trabajo", surveyNumberWebCati);
		query.setParameter("encuestaCompleta", 1 );
		query.setParameter("cantidadDeDiasARestar", cantidadDeDiasARestar);
		query.setParameter("suscribers", suscribers);
		@SuppressWarnings("unchecked")
		List <QuienRespondeLaEncuesta> surveyComplete =  query.getResultList();
		
		if (surveyComplete != null && surveyComplete.size() > 0) {
			return surveyComplete;
		}
		return null;
	}

}
	
