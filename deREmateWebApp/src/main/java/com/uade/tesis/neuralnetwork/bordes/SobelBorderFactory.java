package com.uade.tesis.neuralnetwork.bordes;

import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

import javax.imageio.ImageIO;

public class SobelBorderFactory {
	
	public static File processImage(File file, String path, boolean withThreshold){

		try {
			
			// MAX = 255 (Negro) and Min = 0 (Blanco)
			int threshold = 30;
			int width = 0;
			int height = 0;
	        FileInputStream fis = new FileInputStream(file);
	        if (fis.read() != 255 || fis.read() != 216)
                throw new RuntimeException("SOI (Start Of Image) marker 0xff 0xd8 missing");
	        while (fis.read() == 255) {
	        	int marker = fis.read();
	        	int len = fis.read() << 8 | fis.read();
	        	if (marker == 192) {
	        		fis.skip(1);
	        		int h = fis.read() << 8 | fis.read();
	        		int w = fis.read() << 8 | fis.read();
	        		width = w;
	        		height = h;
	        		break;
	        	}
	        	fis.skip(len - 2);
	        }
	        fis.close();
			
		    BufferedImage input = ImageIO.read(file);
			int[] orig = new int[width*height];
			PixelGrabber grabber = new PixelGrabber(input, 0, 0, width, height, orig, 0, width);
			grabber.grabPixels();
			Sobel edgedetector = new Sobel();
			edgedetector.init(orig,width,height);
			int[] res = edgedetector.process();
			if(withThreshold) 
				res = threshold(res, threshold);
			Frame f = new Frame();
			Image output = f.createImage(new MemoryImageSource(width, height, res, 0, width));
			BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);;
			Graphics2D ig = bimage.createGraphics();
			ig.drawImage(output, 0, 0, f);
			File f2 = new File(path + new Date().getTime() + ".jpg");
		    ImageIO.write(bimage, "jpg", f2);
		    return f2;
		} catch(Exception e2) {
			e2.printStackTrace();
		}
		return null;
	}

	private static int[] threshold(int[] original, int value) {
		for(int x=0; x<original.length; x++) {
			if((original[x] & 0xff)>=value)
				original[x]=0xffffffff;
			else
				original[x]=0xff000000;
		}
		return original;
	}
}
