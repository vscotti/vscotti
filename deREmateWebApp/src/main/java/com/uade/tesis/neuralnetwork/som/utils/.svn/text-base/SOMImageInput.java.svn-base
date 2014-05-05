/*
 * SOMImageInput.java
 *
 * Created on January 15, 2006, 1:15 AM
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Copyright (C) 2006 Cheok YanCheng <yccheok@yahoo.com>
 *
 * $Id: SOMImageInput.java,v 1.2 2006/05/21 15:11:06 yccheok Exp $
 */

package com.uade.tesis.neuralnetwork.som.utils;

import org.yccheok.*;
import org.yccheok.numrecognition.*;

/**
 *
 * @author yccheok
 */
public class SOMImageInput {
    
    /** Creates a new instance of SOMImageInput */
    public SOMImageInput(String fileName, double scale) {
        this.scale = scale;
        processor = new NumberImageProcessor(fileName);
        processor.scale(scale);
    }

    public SOMInput getHistogramSOMInput(int sampleSizePerChannel) {
        if(sampleSizePerChannel > 256 || sampleSizePerChannel < 0) throw new java.lang.IllegalArgumentException(""+sampleSizePerChannel);

        int[][] histogram = processor.getHistogram();
        int numOfSamplePerChannel = histogram[0].length / sampleSizePerChannel;
        int lastSampleSizePerChannel = sampleSizePerChannel + (histogram[0].length - (numOfSamplePerChannel * sampleSizePerChannel));
        
        SOMInput somInput = new SOMInput(histogram.length * numOfSamplePerChannel);
       
        for(int channel=0; channel<histogram.length; channel++) {
            for(int i=0; i<(numOfSamplePerChannel-1); i++) {
                int index = i*sampleSizePerChannel;
                int value = 0;
                for(int j=index; j<(index+sampleSizePerChannel); j++) {
                    value = value + histogram[channel][j];
                }

                somInput.setValue(value, 
                        i + (channel*numOfSamplePerChannel));
            }
            
            int index = (numOfSamplePerChannel-1)*sampleSizePerChannel;
            int value = 0;
            for(int j=index; j<(index+lastSampleSizePerChannel); j++) {
                value = value + histogram[channel][j];
            }
                
            somInput.setValue(value, 
                (numOfSamplePerChannel-1) + (channel*numOfSamplePerChannel));            
        }
    
        return somInput;
    }
    
    public SOMInput getHSBHistogramSOMInput(int sampleSizePerChannel) {
        if(sampleSizePerChannel > 256 || sampleSizePerChannel < 0) throw new java.lang.IllegalArgumentException(""+sampleSizePerChannel);

        int[][] histogram = processor.getHSBHistogram();
        int numOfSamplePerChannel = histogram[0].length / sampleSizePerChannel;
        int lastSampleSizePerChannel = sampleSizePerChannel + (histogram[0].length - (numOfSamplePerChannel * sampleSizePerChannel));
        
        SOMInput somInput = new SOMInput(histogram.length * numOfSamplePerChannel);
       
        for(int channel=0; channel<histogram.length; channel++) {
            for(int i=0; i<(numOfSamplePerChannel-1); i++) {
                int index = i*sampleSizePerChannel;
                int value = 0;
                for(int j=index; j<(index+sampleSizePerChannel); j++) {
                    value = value + histogram[channel][j];
                }

                somInput.setValue(value, 
                        i + (channel*numOfSamplePerChannel));
            }
            
            int index = (numOfSamplePerChannel-1)*sampleSizePerChannel;
            int value = 0;
            for(int j=index; j<(index+lastSampleSizePerChannel); j++) {
                value = value + histogram[channel][j];
            }
                
            somInput.setValue(value, 
                (numOfSamplePerChannel-1) + (channel*numOfSamplePerChannel));            
        }
    
        return somInput;
    }
    
    public double getScale() {
        return scale;
    }
    
    public int getImageWidth() {
        return processor.getWidth();
    }
    
    public int getImageHeight() {
        return processor.getHeight();
    }
 
    public void saveImage(String fileName) {
        processor.save(fileName);
    }
    
    private NumberImageProcessor processor;    
    private double scale;
}
