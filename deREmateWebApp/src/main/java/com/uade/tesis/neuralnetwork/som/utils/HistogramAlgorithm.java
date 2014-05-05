/*
 * HistogramAlgorithm.java
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
 * $Id: HistogramAlgorithm.java,v 1.1 2006/05/03 17:07:29 yccheok Exp $
 */

package com.uade.tesis.neuralnetwork.som.utils;

/**
 *
 * @author yccheok
 */
public class HistogramAlgorithm {

    public static final HistogramAlgorithm RGB = new HistogramAlgorithm("RGB");
    public static final HistogramAlgorithm HSB = new HistogramAlgorithm("HSB");
    
    /** Creates a new instance of HistogramAlgorithm */
    private HistogramAlgorithm(String name) {
        this.name = name;
    }
    
    public String toString() {
        return name;
    }
    
    private String name;
}
