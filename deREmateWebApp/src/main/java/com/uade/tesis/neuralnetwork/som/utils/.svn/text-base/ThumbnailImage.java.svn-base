package com.uade.tesis.neuralnetwork.som.utils;

import com.uade.tesis.domains.Posting;

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


public class ThumbnailImage {
    
	private static final long serialVersionUID = 1L;
	
	
	public ThumbnailImage(ThumbnailImage thumbnailImage) {
        this(thumbnailImage.fileName, thumbnailImage.posting);
    }
    
    public ThumbnailImage(String fileName) {
        super();
        this.fileName = fileName;
    }
    
    public ThumbnailImage(String fileName, Posting posting) {
        super();
        this.fileName = fileName;
        this.posting = posting;
    }
    
    public String getFileName() {
        return fileName;
    }
	public Posting getPosting() {
		return posting;
	}

	private Posting posting;
	
    private String fileName;
}
