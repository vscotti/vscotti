package com.uade.tesis.web.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.faces.event.ValueChangeEvent;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import com.uade.tesis.neuralnetwork.services.NeuralNetworkSingletonService;
import com.uade.tesis.services.DeRemateTesisService;
import com.uade.tesis.web.formbeans.SearchFormBean;

public class SearchBean {

	private SearchFormBean form = new SearchFormBean();

	private DeRemateTesisService service;
	private NeuralNetworkSingletonService neuralNetworkService;
	
	private String searchesDir;
	private String loggedUser;
	
	public void initializeBean() {
		form = new SearchFormBean();
		form.setUsuarioLogeado(service.getUsuarioById(Long.getLong(loggedUser)));
	}
	
	public void searchPosting() {
		if(form.getImagesList() != null) {
			try {
				File image = new File(form.getImagesList());
				form.setPostings(neuralNetworkService.searchPosting(image));
    		} catch(Exception e) {}
		}
	}

    public void refreshImage(ValueChangeEvent event) throws IOException  {
        UploadedFile uploadedFile = (UploadedFile) event.getNewValue();
        if(uploadedFile != null) {
        	if(form.getImagesList() != null) {
        		try {
	        		File ftempprev = new File(form.getImagesList());
	        		ftempprev.delete();
        		} catch(Exception e) {}
        	}
        	String str = String.valueOf(new Date().getTime());
        	File ftemp = new File(searchesDir + str + ".jpg");
        	InputStream in = uploadedFile.getInputStream();
        	OutputStream out = new FileOutputStream(ftemp);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0){
            	out.write(buf, 0, len);
            }
            in.close();
            out.close();
            
            form.setImagesList(searchesDir + str + ".jpg");
        }
    }
	
	public String getLoggedUser() {
		return loggedUser;
	}
	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}
	public String getSearchesDir() {
		return searchesDir;
	}
	public void setSearchesDir(String searchesDir) {
		this.searchesDir = searchesDir;
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
	public SearchFormBean getForm() {
		return form;
	}
	public void setForm(SearchFormBean form) {
		this.form = form;
	}
}
