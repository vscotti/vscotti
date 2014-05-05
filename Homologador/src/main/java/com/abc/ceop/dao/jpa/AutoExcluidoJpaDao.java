package com.abc.ceop.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.abc.ceop.dao.AutoExcluidoDao;
import com.abc.ceop.model.entities.AutoExcluido;


@Repository
public class AutoExcluidoJpaDao implements AutoExcluidoDao {

	@PersistenceContext(unitName="puwebcati")
	private EntityManager em;
	
	@Override
	public List<AutoExcluido> outOfTheWebSurvey(int notContactFlag, String countryCampaing) {
	TypedQuery<AutoExcluido> typedQuery= em.createQuery("FROM AutoExcluido as rs where id_contacto = :id_contacto and countryCampaing = :countryCampaing" , AutoExcluido.class);
	typedQuery.setParameter("id_contacto", notContactFlag);
	typedQuery.setParameter("countryCampaing", countryCampaing);
	
	List<AutoExcluido> values = typedQuery.getResultList();
	if (values != null && values.size() > 0) {
		return values;
	}
		return null;
}
	
	
	
	

}
