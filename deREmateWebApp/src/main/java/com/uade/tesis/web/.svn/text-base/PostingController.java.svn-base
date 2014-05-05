package com.uade.tesis.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class PostingController extends SimpleFormController {

	@Override
	protected ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response, BindException errors, Map controlModel)
			throws Exception {
		controlModel = new HashMap<String, String>();
		controlModel.put("categorias", new String[]{"Cuadrados", "Circulos", "Rectangulos"});
		return super.showForm(request, response, errors, controlModel);
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		// TODO Auto-generated method stub
		return super.onSubmit(request, response, command, errors);
	}

	@Override
	protected void doSubmitAction(Object command) throws Exception {
		super.doSubmitAction(command);
	}
}
