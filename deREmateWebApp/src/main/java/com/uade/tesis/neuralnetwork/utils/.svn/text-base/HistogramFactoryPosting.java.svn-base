package com.uade.tesis.neuralnetwork.utils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.yccheok.SOMInput;

import com.uade.tesis.neuralnetwork.som.utils.HistogramAlgorithm;
import com.uade.tesis.neuralnetwork.som.utils.SOMImageInput;
import com.uade.tesis.neuralnetwork.som.utils.SOMInputWrapper;
import com.uade.tesis.neuralnetwork.som.utils.ThumbnailImage;

public class HistogramFactoryPosting {

	private static final double SCALE_DOWN_DEFAUL_VALUE = 1;
	private static final double SCALE_DOWN_MIN_VALUE = 1;
	private static final double SCALE_DOWN_MAX_VALUE = 10;
	private static final int HISTOGRAM_BIN_DEFAUL_VALUE = 1;
	private static final int HISTOGRAM_BIN_MIN_VALUE = 1;
	private static final int HISTOGRAM_BIN_MAX_VALUE = 16;
	private static final String ALGORITM_TYPE_HSB = "HSB";
	private static final String ALGORITM_TYPE_RGB = "RGB";
	private static final String ALGORITM_TYPE_DEFAUL_VALUE = ALGORITM_TYPE_RGB;
	
    private Map<FileInfo ,SOMInputWrapper> histogramWrapperMap;
	private ImageDataBasePosting imageDataBase;
	
	private double scaleSelected = SCALE_DOWN_DEFAUL_VALUE;
	private int sizePerChannelSelected = HISTOGRAM_BIN_DEFAUL_VALUE;
	private String algorithmType = ALGORITM_TYPE_DEFAUL_VALUE;
	
    public HistogramFactoryPosting(ImageDataBasePosting imageDataBase) {
    	this.imageDataBase = imageDataBase;
    	this.histogramWrapperMap = new HashMap<FileInfo, SOMInputWrapper>();
    }
    
    public HistogramFactoryPosting(ImageDataBasePosting imageDataBase,
    						double scaleSelected, 
    						int sizePerChannelSelected,
    						String algorithmType) {
    	this.imageDataBase = imageDataBase;
    	this.histogramWrapperMap = new HashMap<FileInfo, SOMInputWrapper>();
    	this.scaleSelected = SCALE_DOWN_DEFAUL_VALUE;
    	this.sizePerChannelSelected = HISTOGRAM_BIN_DEFAUL_VALUE;
    	this.algorithmType = ALGORITM_TYPE_DEFAUL_VALUE;
    	if(scaleSelected >= SCALE_DOWN_MIN_VALUE && scaleSelected <= SCALE_DOWN_MAX_VALUE) {
    		this.scaleSelected = scaleSelected;
    	}
    	if(sizePerChannelSelected >= HISTOGRAM_BIN_MIN_VALUE && sizePerChannelSelected <= HISTOGRAM_BIN_MAX_VALUE) {
    		this.sizePerChannelSelected = sizePerChannelSelected;
    	}
    	if(algorithmType != null &&
    			(algorithmType.equals(ALGORITM_TYPE_HSB) || 
    					algorithmType.equals(ALGORITM_TYPE_RGB))) {
    		this.scaleSelected = scaleSelected;
    	}
    }
    
	public void doHistogramWork() {
		histogramWrapperMap.clear();
		Iterator<Entry<FileInfo, ThumbnailImage>> it = imageDataBase.getThumbnailImageMap().entrySet().iterator();
		while(it.hasNext()) {
			FileInfo info = it.next().getKey();
			File f = info.getProcessedFile();
			try {
				double scale = 1.0 / (double)scaleSelected;
				String fileName = f.getCanonicalPath();
				SOMImageInput somImageInput = new SOMImageInput(fileName, scale);
				int sampleSizePerChannel = sizePerChannelSelected*16;
				if(algorithmType.equals(ALGORITM_TYPE_HSB))  {
					SOMInput somInput = somImageInput.getHSBHistogramSOMInput(sampleSizePerChannel);
					ThumbnailImage thumbnailImage = (ThumbnailImage)(imageDataBase.getThumbnailImageMap()).get(info);
					SOMInputWrapper somInputWrapper  = new SOMInputWrapper(somInput, HistogramAlgorithm.HSB, 
                            new ThumbnailImage(thumbnailImage));
					histogramWrapperMap.put(info, somInputWrapper);
				} else if(algorithmType.equals(ALGORITM_TYPE_RGB)) {
					SOMInput somInput = somImageInput.getHistogramSOMInput(sampleSizePerChannel);
					ThumbnailImage thumbnailImage = (ThumbnailImage)(imageDataBase.getThumbnailImageMap()).get(info);
					SOMInputWrapper somInputWrapper  = new SOMInputWrapper(somInput, HistogramAlgorithm.RGB, 
                            new ThumbnailImage(thumbnailImage));
					histogramWrapperMap.put(info, somInputWrapper);
				} else {
					assert(false);
				}                        
			} catch(IOException exp) {
				System.err.println(exp);
            } catch(Exception exp) {
            	System.err.println(exp);
            }
		}
    }

    public int getSOMHistogramInputSize() {
        return histogramWrapperMap.size();
    }

    public SOMInput getSOMHistogramInput(int index) {
        Collection<SOMInputWrapper> c = histogramWrapperMap.values();
        Object[] o = c.toArray();
        SOMInputWrapper somInputWrapper = (SOMInputWrapper)o[index];
        return somInputWrapper.getSOMInput();
    }

    public SOMInputWrapper getSOMHistogramInputWrapper(int index) {
    	Collection<SOMInputWrapper> c = histogramWrapperMap.values();
        Object[] o = c.toArray();
        SOMInputWrapper somInputWrapper = (SOMInputWrapper)o[index];
        return somInputWrapper;
    }
    
    public SOMInputWrapper getSOMInputWrapper(String fileName) {
        SOMInputWrapper somInputWrapper = null;
		double scale = 1.0 / (double)scaleSelected;
		SOMImageInput somImageInput = new SOMImageInput(fileName, scale);
		int sampleSizePerChannel = sizePerChannelSelected*16;
        if(algorithmType.equals(ALGORITM_TYPE_HSB)) {
            SOMInput somInput = somImageInput.getHSBHistogramSOMInput(sampleSizePerChannel);
            somInputWrapper  = new SOMInputWrapper(somInput, HistogramAlgorithm.HSB, 
                new ThumbnailImage(fileName));
        } else if(algorithmType.equals(ALGORITM_TYPE_RGB)) {
            SOMInput somInput = somImageInput.getHistogramSOMInput(sampleSizePerChannel);
            somInputWrapper  = new SOMInputWrapper(somInput, HistogramAlgorithm.RGB, 
                new ThumbnailImage(fileName));
        }
        return somInputWrapper;
    }

    public boolean isNeedBorderProcessing() {
        return imageDataBase.isNeedBorderProcessing();
    }
    
    public File getRealImage(String processedFileName) {
    	return imageDataBase.getRealImage(processedFileName);
    }
}
