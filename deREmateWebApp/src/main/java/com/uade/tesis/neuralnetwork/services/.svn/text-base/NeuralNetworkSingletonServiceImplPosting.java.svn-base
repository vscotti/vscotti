package com.uade.tesis.neuralnetwork.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.uade.tesis.domains.Categoria;
import com.uade.tesis.domains.Posting;
import com.uade.tesis.neuralnetwork.utils.HistogramFactoryPosting;
import com.uade.tesis.neuralnetwork.utils.ImageDataBasePosting;
import com.uade.tesis.neuralnetwork.utils.NeuralNetworkUtilsPosting;
import com.uade.tesis.services.DeRemateTesisService;

public class NeuralNetworkSingletonServiceImplPosting implements NeuralNetworkSingletonService {
	
    private ImageDataBasePosting imageDataBase = null;
    private HistogramFactoryPosting histogramFactory = null;
    private NeuralNetworkUtilsPosting neuralNetworkUtils = null;

    private Boolean needBorderProcessing = true;

    private DeRemateTesisService deRemateTesisService;

    public NeuralNetworkSingletonServiceImplPosting() {
    }
    
    public void learnNetwork() {
    	imageDataBase = new ImageDataBasePosting(deRemateTesisService.getAllPostings(), needBorderProcessing);
    	System.out.println("ImageDataBasePosting loaded...");
    	histogramFactory = new HistogramFactoryPosting(imageDataBase);
    	histogramFactory.doHistogramWork();
    	System.out.println("histogramFactoryPosting loaded...");
    	neuralNetworkUtils = new NeuralNetworkUtilsPosting(histogramFactory);
    	System.out.println("neuralNetworkUtilsPosting loaded...");
    	neuralNetworkUtils.learn();
    	System.out.println("neuralNetworkUtilsPosting learned...");
    }
    
    public 	List<File> search(File image) {
    	return null;
    }

    public List<Posting> searchPosting(File image) {
    	return neuralNetworkUtils.searchImage(image);
    }

    public List<Categoria> searchCategoria(File image) {
    	List<Posting> postings = neuralNetworkUtils.searchImage(image);
    	List<Categoria> cats = new ArrayList<Categoria>();
    	for(Posting posting : postings) {
    		cats.add(posting.getCategoria());
		}
    	return cats;
    }
    
	public Boolean getNeedBorderProcessing() {
		return needBorderProcessing;
	}
	public void setNeedBorderProcessing(Boolean needBorderProcessing) {
		this.needBorderProcessing = needBorderProcessing;
	}
	public DeRemateTesisService getDeRemateTesisService() {
		return deRemateTesisService;
	}
	public void setDeRemateTesisService(DeRemateTesisService deRemateTesisService) {
		this.deRemateTesisService = deRemateTesisService;
	}
}
