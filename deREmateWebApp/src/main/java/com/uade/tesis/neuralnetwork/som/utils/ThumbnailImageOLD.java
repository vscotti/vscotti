package com.uade.tesis.neuralnetwork.som.utils;
/*
 * ThumbnailImage.java
 *
 * Created on January 17, 2006, 11:47 AM
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
 * $Id: ThumbnailImage.java,v 1.5 2006/05/21 15:22:08 yccheok Exp $
 */

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ThumbnailImageOLD extends JLabel {
    
	private static final long serialVersionUID = 1L;
	
	public ThumbnailImageOLD(ThumbnailImageOLD thumbnailImage) {
        this(thumbnailImage.fileName, thumbnailImage.preferredLength);
    }
    
    /** Creates a new instance of ThumbnailImage */
    public ThumbnailImageOLD(String fileName, int preferredLength) {
        super();
        
        if(IMAGE_NOT_FOUND_ICON == null) {
            java.net.URL imageURL = ThumbnailImageOLD.class.getResource("/images/no-image-found.gif");

            if (imageURL != null) {
                IMAGE_NOT_FOUND_ICON = new ImageIcon(imageURL);                
            } 
        }
        
        this.fileName = fileName;
        
        this.preferredLength = preferredLength;
        
        ImageIcon originalImageIcon = new ImageIcon(fileName, "original");
                        
        if((originalImageIcon.getIconWidth() > 0) && (originalImageIcon.getIconHeight() > 0))
        {
        }
        else
        {
            // Image not found.
            //
            originalImageIcon = IMAGE_NOT_FOUND_ICON;
        }
        
        Image image = originalImageIcon.getImage();
        
        double scale = 1.0;
        double tmp1 = preferredLength / (double)image.getWidth(this);
        double tmp2 = preferredLength / (double)image.getHeight(this);
        if(tmp1 < tmp2) scale = tmp1; else scale = tmp2;
        
        Image scaledImage = image.getScaledInstance(
                (int)(scale * (double)image.getWidth(this)),
                (int)(scale * (double)image.getHeight(this)),
                Image.SCALE_FAST
        );      
        
        scaledImageIcon = new ImageIcon(scaledImage, "scaled");
        
        this.setIcon(scaledImageIcon);
        
        this.setHorizontalAlignment(SwingConstants.CENTER);        
    }
    
    public void useScaledImageIcon() {
        this.setIcon(scaledImageIcon);
    }

    public void useOriginalImageIcon() {
        // What if the image file is removed. We should have a better way to
        // handle this.
        //
        ImageIcon originalImageIcon = new ImageIcon(fileName, "original");
        
        if((originalImageIcon.getIconWidth() > 0) && (originalImageIcon.getIconHeight() > 0))
        {
        }
        else
        {
            // Image not found.
            //
            originalImageIcon = IMAGE_NOT_FOUND_ICON;
        }
        
        this.setIcon(originalImageIcon);
    }
    
    public String getFileName() {
        return fileName;
    }
    
    private static ImageIcon IMAGE_NOT_FOUND_ICON = null;
    private ImageIcon scaledImageIcon;
    private String fileName;
    private int preferredLength;
}
