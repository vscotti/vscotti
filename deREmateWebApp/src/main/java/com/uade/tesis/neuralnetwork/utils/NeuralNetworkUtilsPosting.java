package com.uade.tesis.neuralnetwork.utils;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yccheok.SOM2D;
import org.yccheok.SOMInput;

import com.uade.tesis.domains.Posting;
import com.uade.tesis.neuralnetwork.bordes.SobelBorderFactory;
import com.uade.tesis.neuralnetwork.som.utils.SOMInputWrapper;

public class NeuralNetworkUtilsPosting {
	
	private static final double LEARNING_RATE_DEFAUL_VALUE = 0.2;
	private static final double LEARNING_RATE_MIN_VALUE = 0.0;
	private static final double LEARNING_RATE_MAX_VALUE = 1.0;
	private static final int WIDTH_DEFAUL_VALUE = 3;
	private static final int HEIGHT_DEFAUL_VALUE = 3;
	private static final int ITERATIONS_DEFAUL_VALUE = 600;
	
    private int somWidth = WIDTH_DEFAUL_VALUE;
    private int somHeight = HEIGHT_DEFAUL_VALUE;
    private int iteration = ITERATIONS_DEFAUL_VALUE;
    private double learningRate = LEARNING_RATE_DEFAUL_VALUE;
	
    private HistogramFactoryPosting histogramFactory;
    
    private Map<Point,Set<SOMInputWrapper>> somInputWrapperMap;
    
    private SOM2D som2D;
    
    public NeuralNetworkUtilsPosting(HistogramFactoryPosting histogramFactory) {
    	this.histogramFactory = histogramFactory;
    	this.somInputWrapperMap = new HashMap<Point, Set<SOMInputWrapper>>();
    }
    
    public NeuralNetworkUtilsPosting(HistogramFactoryPosting histogramFactory,
    					 int somWidth,
    					 int somHeight, 
    					 int iteration,
    					 double learningRate) {
    	this.histogramFactory = histogramFactory;
    	this.somWidth = WIDTH_DEFAUL_VALUE;
    	this.somHeight = HEIGHT_DEFAUL_VALUE;
    	this.iteration = ITERATIONS_DEFAUL_VALUE;
    	this.learningRate = LEARNING_RATE_DEFAUL_VALUE;
    	this.somInputWrapperMap = new HashMap<Point, Set<SOMInputWrapper>>();
        if(somWidth >= 1) {
        	this.somWidth = somWidth;
        }
        if(somHeight >= 1) {
        	this.somHeight = somHeight;
        }
        if(iteration >= 1) {
        	this.iteration = iteration;
        }
        if(learningRate > LEARNING_RATE_MIN_VALUE && learningRate <= LEARNING_RATE_MAX_VALUE) {
        	this.learningRate = learningRate;
        }       
    }
    
    public void learn() {
        int numOfInput = histogramFactory.getSOMHistogramInput(0).getNumOfInput();
        int upperBound = 0;
        for(int i = 0 ; i < histogramFactory.getSOMHistogramInputSize() ; i++) {
            for(int j = 0 ; j < numOfInput ; j++) {
                int tmp = (int)histogramFactory.getSOMHistogramInput(i).getValue(j);
                
                if(tmp > upperBound) upperBound = tmp;
            }
        }        
        som2D = new SOM2D(numOfInput, somWidth, somHeight);
        som2D.init(iteration, learningRate, 0.0, (double)upperBound);
        doLearnWork();
    }
  
	private void doLearnWork() {        
        int count = som2D.getNumOfIteration();
        somInputWrapperMap.clear();
        for(int i = 0 ; i < count ; i++) {
            
            int index = i % histogramFactory.getSOMHistogramInputSize();
            SOMInput somInput = histogramFactory.getSOMHistogramInput(index);
            SOMInputWrapper somInputWrapper = histogramFactory.getSOMHistogramInputWrapper(index);
            
            som2D.train(somInput);
            
            Point point = som2D.getBestMatchingUnit(somInput);
            Point prevPoint = somInputWrapper.getPoint();
            if(prevPoint != null) {
                Set<SOMInputWrapper> s = (Set<SOMInputWrapper>)somInputWrapperMap.get(prevPoint);
                if(s != null) {
                    s.remove(somInputWrapper);
                }
            }
            
            somInputWrapper.setPoint(point);
            
            if(somInputWrapperMap.containsKey(point) == false) {
                somInputWrapperMap.put(point, new HashSet<SOMInputWrapper>());                    
            }
            
            Set<SOMInputWrapper> s = (Set<SOMInputWrapper>)somInputWrapperMap.get(point);
            s.add(somInputWrapper);                                
            
        }
    }

	public List<Posting> searchImage(File file) {
       	SOMInputWrapper somInputWrapper;
       	List<Posting> list = new ArrayList<Posting>();
		try {
			if(histogramFactory.isNeedBorderProcessing()) {
				somInputWrapper = histogramFactory.getSOMInputWrapper(SobelBorderFactory.processImage(file, "C:/Documents and Settings/Administrador/Escritorio/bordes/", true).getCanonicalPath());
			} else {
				somInputWrapper = histogramFactory.getSOMInputWrapper(file.getCanonicalPath());
			}
	        Point point = som2D.getBestMatchingUnit(somInputWrapper.getSOMInput());
	        point.toString();
	        int size = getGroupSize(point);
	        
	        for(int i = 0 ; i < size ; i++) {
	            SOMInputWrapper resultSOMInputWrapper = getSOMInputWrapper(point, i);
	            list.add(resultSOMInputWrapper.getThumbnailImage().getPosting());
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

    private int getGroupSize(Point point) {
        Set<SOMInputWrapper> s = (Set<SOMInputWrapper>)somInputWrapperMap.get(point);
        if(s != null) {
            return s.size();
        }
        return 0;
    }

    public SOMInputWrapper getSOMInputWrapper(Point point, int index) {
        Set<SOMInputWrapper> s = (Set<SOMInputWrapper>)somInputWrapperMap.get(point);
        if(s != null) {
            Object[] o = s.toArray();
            return (SOMInputWrapper)o[index];
        }
        return null;
    }
}
