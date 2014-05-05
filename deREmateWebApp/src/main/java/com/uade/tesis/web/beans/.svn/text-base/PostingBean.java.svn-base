package com.uade.tesis.web.beans;

import com.uade.tesis.domains.Posting;
import com.uade.tesis.neuralnetwork.services.NeuralNetworkSingletonService;
import com.uade.tesis.services.DeRemateTesisService;
import com.uade.tesis.web.formbeans.PostingFormBean;

public class PostingBean {

	private PostingFormBean form = new PostingFormBean();

	private DeRemateTesisService service;
	private NeuralNetworkSingletonService neuralNetworkService;
	
	private String loggedUser;
	private String posting_id;
	
	public String initializeBean() {
		if(posting_id != null) {
			Posting p = service.getPostingById(Long.valueOf(posting_id));
			if(p != null) {
				form = new PostingFormBean();
				form.setUsuarioLogeado(service.getUsuarioById(Long.getLong(loggedUser)));
				form.setCategoria(p.getCategoria().getId());
				form.setCantProductos(p.getCantProductos());
				form.setCategoriaDesc(p.getCategoria().getDescripcion());
				form.setDescripcion(p.getDescripcion());
				form.setPrecio(p.getPrecio());
				form.setTitulo(p.getTitulo());
				form.setConsultas(p.getCantConsultas());
				form.setVentas(p.getCantVentas());
				form.setCreacion(p.getFechaPosting());
				form.setTipoProducto(p.getTipoProducto());
				form.setFinalizacion(p.getFechafinalizacion());
				form.setImagesList(p.getFileImages());
			} else {
				return "failure";
			}
		} else {
			return "failure";
		}
		return "success";
	}
	
	public String getPosting_id() {
		return posting_id;
	}
	public void setPosting_id(String posting_id) {
		this.posting_id = posting_id;
	}
	public String getLoggedUser() {
		return loggedUser;
	}
	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}
	public NeuralNetworkSingletonService getNeuralNetworkService() {
		return neuralNetworkService;
	}
	public void setNeuralNetworkService(
			NeuralNetworkSingletonService neuralNetworkService) {
		this.neuralNetworkService = neuralNetworkService;
	}
	public DeRemateTesisService getService() {
		return service;
	}
	public void setService(DeRemateTesisService service) {
		this.service = service;
	}
	public PostingFormBean getForm() {
		return form;
	}
	public void setForm(PostingFormBean form) {
		this.form = form;
	}
}
