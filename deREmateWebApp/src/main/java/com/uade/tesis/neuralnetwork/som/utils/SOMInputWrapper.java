package com.uade.tesis.neuralnetwork.som.utils;

import java.awt.Point;

import org.yccheok.SOMInput;

public class SOMInputWrapper {
    
    public SOMInputWrapper(SOMInput somInput, 
    		HistogramAlgorithm algorithm, 
            ThumbnailImage thumbnailImage) {
        this.somInput = somInput;
        this.algorithm = algorithm;
        this.thumbnailImage = thumbnailImage;
        this.point = null;
    }
    
    public SOMInput getSOMInput() {
        return this.somInput;
    }
    
    public HistogramAlgorithm getHistogramAlgoritm() {
        return this.algorithm;
    }
    
    public ThumbnailImage getThumbnailImage() {
        return thumbnailImage;
    }
    
    public Point getPoint() {
        return point;
    }
    
    public void setPoint(Point point) {
        this.point = point;
    }
    
    private SOMInput somInput;
    private HistogramAlgorithm algorithm;
    private ThumbnailImage thumbnailImage;
    private Point point;
}
