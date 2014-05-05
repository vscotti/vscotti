package com.uade.tesis.neuralnetwork.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.uade.tesis.domains.Categoria;
import com.uade.tesis.domains.Posting;
import com.uade.tesis.neuralnetwork.utils.older.NeuralNetworkUtils;

public class NeuralNetworkSingletonServiceImpl implements NeuralNetworkSingletonService {
	
//    private ImageDataBase imageDataBase = null;
//    private HistogramFactory histogramFactory = null;
    private NeuralNetworkUtils neuralNetworkUtils = null;

    private String tempPath = "C:/Documents and Settings/Administrador/Escritorio/TESIS/somimagegui_0.01/SOMImageGUI/images/train1";
    private Boolean needBorderProcessing = true;

    public NeuralNetworkSingletonServiceImpl() {
//    	imageDataBase = new ImageDataBase(tempPath, needBorderProcessing);
//    	System.out.println("imageDataBase loaded...");
//    	histogramFactory = new HistogramFactory(imageDataBase);
//    	histogramFactory.doHistogramWork();
//    	System.out.println("histogramFactory loaded...");
//    	neuralNetworkUtils = new NeuralNetworkUtils(histogramFactory);
//    	System.out.println("neuralNetworkUtils loaded...");
//    	neuralNetworkUtils.learn();
//    	System.out.println("neuralNetworkUtils learned...");
    }
    
    public List<File> search(File image) {
//    	neuralNetworkUtils.searchImage(image);
    	return new ArrayList<File>();
    }

    public List<Posting> searchPosting(File image) {
//    	neuralNetworkUtils.searchImage(image);
    	return new ArrayList<Posting>();
    }

    public List<Categoria> searchCategoria(File image) {
    	neuralNetworkUtils.searchImage(image);
    	return new ArrayList<Categoria>();
    }
    
	public String getTempPath() {
		return tempPath;
	}
	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
	public Boolean getNeedBorderProcessing() {
		return needBorderProcessing;
	}
	public void setNeedBorderProcessing(Boolean needBorderProcessing) {
		this.needBorderProcessing = needBorderProcessing;
	}
}
