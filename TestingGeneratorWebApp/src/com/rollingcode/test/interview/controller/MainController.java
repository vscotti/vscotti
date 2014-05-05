package com.rollingcode.test.interview.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rollingcode.test.interview.controller.form.SearchInfo;
import com.rollingcode.test.interview.entity.Category;
import com.rollingcode.test.interview.entity.Seniority;
import com.rollingcode.test.interview.service.TestInterviewBusinessDelegate;

@Controller
public class MainController {

	@Resource  
	private TestInterviewBusinessDelegate testEntrevistaBusinessDelegate;  

	private List<Category> categorias;
	private List<Seniority> seniorities;
//	private List<Question> preguntas;
	
	private SearchInfo searchInfo;
//	private ResultInfo resultInfo;
//	private ExamInfo examenesInfo;

	@RequestMapping("/mainPage")
	public ModelAndView goToMainPage() {
		this.categorias = new ArrayList<Category>();
		this.categorias.addAll(testEntrevistaBusinessDelegate.getCategoriesActives());

		this.seniorities = new ArrayList<Seniority>();
		this.seniorities.addAll(testEntrevistaBusinessDelegate.getSenioritiesActives());

		this.searchInfo = new SearchInfo();

		ModelAndView mav = new ModelAndView("mainPageView");
		mav.addObject("searchInfo", searchInfo);
		mav.addObject("categorias", categorias);  
		mav.addObject("seniorities", seniorities);  
		return mav;
	}
//
//	@RequestMapping("/processSearchInfo")
//	public ModelAndView searchForResults(@ModelAttribute SearchInfo searchInfo) {
//		List<Long> categoria = searchInfo.getIdCategoriaSeleccionada();
//		Long seniority = searchInfo.getIdSenioritySeleccionado();
//		Integer numeroPreguntas = searchInfo.getPreguntasPorConsulta();
//		if(searchInfo.getPreguntasPorCategoria() != null &&
//				searchInfo.getPreguntasPorCategoria() > 0) {
//			numeroPreguntas = searchInfo.getPreguntasPorCategoria();
//		}
//		
//		this.preguntas = testEntrevistaBusinessDelegate.getPreguntas(categoria, seniority, numeroPreguntas);
//		this.searchInfo = searchInfo;
//		this.resultInfo = new ResultInfo();
//
//		List<PreguntaVO> list = new ArrayList<PreguntaVO>();
//		for (Pregunta pregunta : this.preguntas) {
//			list.add(new PreguntaVO(pregunta));
//		}
//		
//		this.resultInfo.setPreguntas(list);
//		
//		if(list.isEmpty()) {
//			this.searchInfo.setMensaje("No hay valores para mostrar. Intente otr consulta.");
//		}
//		
//		ModelAndView mav = new ModelAndView("mainPageView");
//		mav.addObject("searchInfo", this.searchInfo);
//		mav.addObject("resultInfo", this.resultInfo);  
//		mav.addObject("categorias", this.categorias);  
//		mav.addObject("seniorities", this.seniorities);  
//		return mav;
//	}
//
//	@RequestMapping("/generarExamen")
//	public ModelAndView generarExamenes(@ModelAttribute SearchInfo searchInfo) {
//		List<Long> categoria = searchInfo.getIdCategoriaSeleccionada();
//		Long seniority = searchInfo.getIdSenioritySeleccionado();
//		Integer numeroPreguntas = searchInfo.getPreguntasPorConsulta();
//		String propuesta = searchInfo.getPropuestaExamen();
//		if(searchInfo.getPreguntasPorCategoria() != null &&
//				searchInfo.getPreguntasPorCategoria() > 0) {
//			numeroPreguntas = searchInfo.getPreguntasPorCategoria();
//		}
//
//		this.searchInfo = searchInfo;
//		this.examenesInfo = new ExamenesInfo();
//		this.examenesInfo.setExamenes(testEntrevistaBusinessDelegate.generarRandomExamenes(categoria, seniority, numeroPreguntas, propuesta));
//		
//		ModelAndView mav = new ModelAndView("examenes");
//		mav.addObject("examenesInfo", examenesInfo);
//		return mav;
//	}
//
//	@RequestMapping("/recargarPagina")
//	public ModelAndView reloadPage() {
//		ModelAndView mav = new ModelAndView("mainPageView");
//		mav.addObject("searchInfo", this.searchInfo);
//		mav.addObject("resultInfo", this.resultInfo);  
//		mav.addObject("categorias", this.categorias);  
//		mav.addObject("seniorities", this.seniorities);  
//		return mav;
//	}
//	
//	@RequestMapping("/grabarSeleccionExamenes")
//	public ModelAndView grabarSeleccionExamenes(@ModelAttribute ResultInfo resultInfo) {
//		this.searchInfo.setMensaje("Examen Guardado con Existo");
//		this.resultInfo = resultInfo;
//		
//		List<Pregunta> pregtas = new ArrayList<Pregunta>();
//		for (PreguntaVO preguntaVO : resultInfo.getPreguntas()) {
//			if(preguntaVO.isSeleccionado()) {
//				pregtas.add(buscarPregunta(preguntaVO.getPregunta().getId()));
//			}
//		}
//		
//		testEntrevistaBusinessDelegate.guardarExamen(new ExamenVO(pregtas, resultInfo.getPropuestaExamen()));
//		
//		ModelAndView mav = new ModelAndView("mainPageView");
//		mav.addObject("searchInfo", this.searchInfo);
//		mav.addObject("resultInfo", this.resultInfo);  
//		mav.addObject("categorias", this.categorias);  
//		mav.addObject("seniorities", this.seniorities);  
//		return mav;
//	}
//
//	@RequestMapping("/grabarExamenes")
//	public ModelAndView grabarExamenes(@ModelAttribute ResultInfo resultInfo) {
//		this.searchInfo.setMensaje("Examen Guardado con Existo");
//		this.resultInfo = resultInfo;
//		
//		List<Pregunta> pregtas = new ArrayList<Pregunta>();
//		for (PreguntaVO preguntaVO : resultInfo.getPreguntas()) {
//			pregtas.add(buscarPregunta(preguntaVO.getPregunta().getId()));
//		}
//		
//		testEntrevistaBusinessDelegate.guardarExamen(new ExamenVO(pregtas, resultInfo.getPropuestaExamen()));
//		
//		ModelAndView mav = new ModelAndView("mainPageView");
//		mav.addObject("searchInfo", this.searchInfo);
//		mav.addObject("resultInfo", this.resultInfo);  
//		mav.addObject("categorias", this.categorias);  
//		mav.addObject("seniorities", this.seniorities);  
//		return mav;
//	}
//	
//	private Pregunta buscarPregunta(Long id) {
//		if(preguntas != null) {
//			for (Pregunta pregunta : preguntas) {
//				if(pregunta.getId().equals(id)) {
//					return pregunta;
//				}
//			}
//		}
//		return null;
//	}
}
