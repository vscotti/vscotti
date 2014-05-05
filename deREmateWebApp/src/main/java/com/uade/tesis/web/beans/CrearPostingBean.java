package com.uade.tesis.web.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import com.uade.tesis.domains.Categoria;
import com.uade.tesis.domains.Posting;
import com.uade.tesis.neuralnetwork.services.NeuralNetworkSingletonService;
import com.uade.tesis.services.DeRemateTesisService;
import com.uade.tesis.web.formbeans.CrearPostingFormBean;

public class CrearPostingBean {

	private CrearPostingFormBean form = new CrearPostingFormBean();

	private DeRemateTesisService service;
	private NeuralNetworkSingletonService neuralNetworkService;
	
	private String loggedUser;
	
	public void initializeBean() {
		List<Categoria> list = service.getAllCategorias();
		List<SelectItem> cat = new ArrayList<SelectItem>();
		cat.add(0,new SelectItem(-1L,""));
		for(Categoria item : list) {
			cat.add(new SelectItem(item.getId(),item.getDescripcion()));
		}
		form = new CrearPostingFormBean();
		form.setCategorias(cat);
		form.setPosting(null);
		form.setUsuarioLogeado(service.getUsuarioById(Long.getLong(loggedUser)));
	}
	
	private boolean validate() {
		boolean noerror = true;
		FacesContext context= FacesContext.getCurrentInstance();  
		if(form.getTitulo() == null || "".equals(form.getTitulo().trim())) {
			FacesMessage message = new FacesMessage();  
            message.setDetail("Ingrese un titulo");                      
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, message);  		
			noerror = false;
		}
		if(form.getDescripcion() == null || "".equals(form.getDescripcion().trim())) {
			FacesMessage message = new FacesMessage();  
            message.setDetail("Ingrese una descripcion");                      
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, message);  		
			noerror = false;
		}
		if(form.getPrecio() == null) {
			FacesMessage message = new FacesMessage();  
            message.setDetail("Ingrese un precio de venta");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, message);  		
			noerror = false;
		}
		if(form.getFinalizacion() == null) {
			FacesMessage message = new FacesMessage();  
            message.setDetail("Ingrese una fecha de finalizacion de la venta");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, message);  		
			noerror = false;
		}
		if(form.getFinalizacion() != null &&
				!form.getFinalizacion().after(new Date())) {
			FacesMessage message = new FacesMessage();  
            message.setDetail("La Fecha de finalizacion debe ser mayor que hoy");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, message);  		
			noerror = false;
		}
		if(form.getPrecio() != null && form.getPrecio() <= 0) {
			FacesMessage message = new FacesMessage();  
            message.setDetail("Ingrese un precio mayor que 0");                      
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, message);  		
			noerror = false;
		}
		if(form.getImagesList() == null || form.getImagesList().size() <= 0) {
			FacesMessage message = new FacesMessage();  
            message.setDetail("Ingrese al menos una foto");                      
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, message);  		
			noerror = false;
		}
		if(form.getCategoria() == null || form.getCategoria().equals(-1L)) {
			FacesMessage message = new FacesMessage();  
            message.setDetail("Seleccione una Categoria");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, message);  		
			noerror = false;
		}
		return noerror;
		
	}
	public String crearPosting() throws Exception {
        if(validate()) {
			if(form.getImagesList() != null) {
				Posting posting = new Posting();
				posting.setFileImages(new ArrayList<String>());
				posting.setId(service.getNextPostingId());
				posting.setFechaPosting(new Date());
				posting.setDescripcion(form.getDescripcion());
				posting.setPrecio(form.getPrecio());
				posting.setHtmlDescrition(form.isFormatoDescripcion());
				posting.setFechafinalizacion(form.getFinalizacion());
				posting.setTitulo(form.getTitulo());
				posting.setCategoria(service.getCategoriaById(form.getCategoria()));
				posting.setDueno(form.getUsuarioLogeado());
				
	        	for(String element : form.getImagesList()) {
					String destPath = "C:/Temp2/postingimages/" + element.substring(element.lastIndexOf("/")+1, element.length());
					posting.getFileImages().add(destPath);
					copyfiletoANewPath(element,destPath);
				}
				service.savePosting(posting);
				form.setPosting(posting);
		        return "success";
	        }
        }
        return "failure";
	}

	private void copyfiletoANewPath(String origin, String dest) throws Exception {
    	File desttemp = new File(dest);
    	File origtemp = new File(origin);
    	InputStream origis = new FileInputStream(origtemp);
    	OutputStream destis = new FileOutputStream(desttemp);
        byte[] buf = new byte[1024];
        int len;
        while ((len = origis.read(buf)) > 0){
        	destis.write(buf, 0, len);
        }
        origis.close();
        destis.close();
        origtemp.delete();
	}
	
	public void sugerirCategorias(ActionEvent event) {
		refreshCategoriassugeridas();
	}
	
	public void subirFoto(ActionEvent event) {
		try {
			int index = Integer.valueOf(form.getHiddenIndex()).intValue();
			if(index > 0) {
				String str = form.getImagesList().get(index);
				form.getImagesList().remove(index);
				form.getImagesList().add(index-1, str);
				form.setHiddenIndex(null);
			}
		} catch(Exception e) {}
	}
	
	public void bajarFoto(ActionEvent event) {
		try {
			int index = Integer.valueOf(form.getHiddenIndex()).intValue();
			if(index < form.getImagesList().size() - 1) {
				String str = form.getImagesList().get(index);
				form.getImagesList().remove(index);
				form.getImagesList().add(index+1, str);
				form.setHiddenIndex(null);
			}
		} catch(Exception e) {}
	}
	
	public void eliminarFoto(ActionEvent event) {
		try {
			int index = Integer.valueOf(form.getHiddenIndex()).intValue();
			File ftemp = new File(form.getImagesList().get(index));
			ftemp.delete();
			form.getImagesList().remove(index);
			form.setHiddenIndex(null);
            form.setMaxImages(false);
		} catch(Exception e) {}
	}
	
    public void refreshImage(ValueChangeEvent event) throws IOException  {
        UploadedFile uploadedFile = (UploadedFile) event.getNewValue();
        if(uploadedFile != null) {
        	String str = "C:/Temp2/tempposting/" + String.valueOf(new Date().getTime())+ ".jpg";
        	copyUploadedFileToANewLocation(uploadedFile,str);
            form.getImagesList().add(str);
            form.setMaxImages(form.getImagesList().size() >= 5);
            
            if(form.getImagesList().size() == 1) {
            	refreshCategoriassugeridas();
            }
        }
    }
	
    private void copyUploadedFileToANewLocation(UploadedFile uploadedFile, String newPath) throws IOException {
    	File ftemp = new File(newPath);
    	InputStream in = uploadedFile.getInputStream();
    	OutputStream out = new FileOutputStream(ftemp);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0){
        	out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
    
    private void refreshCategoriassugeridas() {
		try {
			File image = new File(form.getImagesList().get(0));
			form.setCategoriasSugeridas(neuralNetworkService.searchCategoria(image));
			form.setHaySugerencias(form.getCategoriasSugeridas().size() > 0);
		} catch(Exception e) {}
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
	public CrearPostingFormBean getForm() {
		return form;
	}
	public void setForm(CrearPostingFormBean form) {
		this.form = form;
	}
}
