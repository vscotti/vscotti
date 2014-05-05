package com.rollingcode.test.interview.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rollingcode.test.interview.dao.CategoryDao;
import com.rollingcode.test.interview.dao.ExamDao;
import com.rollingcode.test.interview.dao.QuestionDao;
import com.rollingcode.test.interview.dao.SeniorityDao;
import com.rollingcode.test.interview.entity.Category;
import com.rollingcode.test.interview.entity.Question;
import com.rollingcode.test.interview.entity.Seniority;
import com.rollingcode.test.interview.service.TestInterviewBusinessDelegate;

@Service
public class TestInterviewBusinessDelegateImpl implements TestInterviewBusinessDelegate {
	
	@Resource
	private ExamDao examenDao;
	
	@Resource
	private CategoryDao categoriaDao;

	@Resource
	private SeniorityDao seniorityDao;

	@Resource
	private QuestionDao preguntaDao;

	@Override
	public List<Category> getCategoriesActives() {
		return categoriaDao.getAllActive();
	}

	@Override
	public List<Seniority> getSenioritiesActives() {
		return seniorityDao.getAll();
	}

	@Override
	public List<Question> getQuestions(List<String> categories, 
									   String seniority,
									   Integer amountQuestions) {
		List<Question> preguntas = new ArrayList<Question>();
		if(amountQuestions == null ||
				amountQuestions <= 0) {
			if(categories != null &&
					!categories.contains(-1)) {
				if(seniority == null) {
		    		return preguntaDao.getByCategoryList(categories);
		    	} else {
		    		return preguntaDao.getByCategoryIdSeniorityId(categories, seniority);
		    	} 
			} else {
		    	if(seniority == null) {
		    		return preguntaDao.getAll();
		    	} else {
		    		return preguntaDao.getBySeniorirtyId(seniority);
				}
			}
		}
		
		return preguntas;
	}

	@Override
	public void saveCategories(List<Category> categories) {
		categoriaDao.save(categories);
	}

	@Override
	public void saveSeniorities(List<Seniority> seniorities) {
		seniorityDao.save(seniorities);
	}
	
	@Override
	public void saveQuestion(Question question) {
		preguntaDao.save(question);
	}

	@Override
	public void saveCategory(Category category) {
		categoriaDao.save(category);
	}

	@Override
	public void saveSeniority(Seniority seniority) {
		seniorityDao.save(seniority);
	}

	@Override
	public Category getCategoryByName(String name) {
		return categoriaDao.getCategoryByName(name);
	}

	@Override
	public List<Category> getAllCategories() {
		return categoriaDao.getAll();
	}

	@Override
	public void updateCategory(Category category) {
		categoriaDao.update(category);
	}

//	@Override
//	public List<ExamenBO> generarRandomExamenes(List<Long> categorias, 
//												Long seniority,
//												Integer numeroPreguntas,
//												String propuesta) {
//		// TODO Auto-generated method stub
//		List<ExamenBO> list = new ArrayList<ExamenBO>();
//		list.add(new ExamenBO(1000L,10,10,"cacacacac","cacacacac"));
//		list.add(new ExamenBO(1000L,10,10,"cacacacac","cacacacac"));
//		list.add(new ExamenBO(1000L,10,10,"cacacacac","cacacacac"));
//		list.add(new ExamenBO(1000L,10,10,"cacacacac","cacacacac"));
//		list.add(new ExamenBO(1000L,10,10,"cacacacac","cacacacac"));
//		list.add(new ExamenBO(1000L,10,10,"cacacacac","cacacacac"));
//		return list;
//	}
//
//	@Override
//	public ExamenBO guardarExamen(ExamenVO examenVO) {
//		Date fecha = new Date();
//		StringBuffer categorias = new StringBuffer();
//		int count = 0;
//		
//		for (Pregunta pregunta : examenVO.getPreguntas()) {
//			Examen examen = new Examen();
//			examen.setFechaCreacion(fecha);
//			examen.setPreguntas(pregunta);
//			examen.setNumeroExamen(fecha.getTime());
//			examen.setPropuesta(examenVO.getPropuesta());
//			examenDao.save(examen);
//			
//			if(!categorias.toString().contains(pregunta.getCategoria().getNombre())) {
//				count++;
//				categorias.append(pregunta.getCategoria().getNombre());
//				categorias.append(", ");
//			}
//		}
//		
//		ExamenBO examenBO = new ExamenBO();
//		examenBO.setNumeroIdentificador(fecha.getTime());
//		examenBO.setCantidadPreguntas(examenVO.getPreguntas().size());
//		examenBO.setCantidadCategorias(count);
//		examenBO.setPropuesta(examenVO.getPropuesta());
//		examenBO.setCategorias(categorias.toString());
//		
//		return examenBO;
//	}
}
 