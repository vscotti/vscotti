package com.uade.tesis.utils;

import java.awt.Image;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
* <P><FONT COLOR="red">
*<B>FSearchImage:</B> Gets the images in a BD similars to the input image.<BR>
*<B>Description</B><BR>
*<FONT COLOR="blue">
*Calculates the histograms of the input image and compares it to the ones in the
*image BD (got from the FCalcHistoBD). Using the distance that the user indicates
*with the parameter, the most similar image in the BD is returned and, 
*depending of the perc parameter, some additional images
*can be returned. It does not process an image, but the complete sequence (but only
*the first image is processed). The sequence returned is ordered by distance: 
*first, the image with the less distance, then the next one
* and so on until the percentage parameter is reached.<BR>
*Use: FSearchImage<BR>
*<ul><B>Input parameters:</B><BR>
*<li>img: Input sequence to process<BR>
*<li>imageBD: file with the histogram BD.<BR>
*<li>disc: Discretization (number of bins)<BR>
*<li>perc: percentage (variation with respect the less distance)<BR>
*<li>type: Type of the image<BR>
*<li>algorithm: Distance to use<BR>
*</ul>
<ul><B>Output parameters:</B><BR>
*<li>The output sequence is the most similar images in the BD.<BR>
*<li>distance: minimum distance.<BR><BR>
*</ul>
*/

public class FSearchImage extends JIPFunction {
	@SuppressWarnings("unchecked")
	public FSearchImage() {
		name = "FSearchImage";
		description = "Gets the images in a BD similars to the input image";
		groupFunc = JIPFunctionList.IMAGEBD;

		JIPParameter p1 = new JIPParameter("imageBD", JIP.pFILE, false);
		p1.setDescription("File with the histogram BD");
		p1.setDefault("out");
		JIPParameter p2 = new JIPParameter("disc", JIP.pINT, false);
		p2.setDefault(20);
		p2.setDescription("Discretization (number of bins)");
		JIPParameter p3 = new JIPParameter("perc", JIP.pREAL, false);
		p3.setDefault(0.1f);
		p3.setDescription("Percentage 0.1=10%");
		JIPParameter p4 = new JIPParameter("type", JIP.pLIST, false);
		String []paux = new String[3];
		paux[0]="RGB";
		paux[1]="YCbCr";
		paux[2]="HSI";
		p4.setDefault(paux);
		p4.setDescription("Type of representation");
		JIPParameter p5 = new JIPParameter("algorithm", JIP.pLIST, false);
		String []palg = new String[4];
		palg[0]="L1";
		palg[1]="L2";
		palg[2]="Jeffrey-divergence";
		palg[3]="Kullback-Leibler divergence";
		p5.setDefault(palg);
		p5.setDescription("Type of distance to use");
		
		JIPParameter posearch = new JIPParameter("distance", JIP.pREAL, false);
		posearch.setDescription("Result of the calculation");
		results.addElement(posearch);
		

		params.addElement(p1);
		params.addElement(p2);
		params.addElement(p3);
		params.addElement(p4);
		params.addElement(p5);
	}

	public JIPImage processImg(JIPImage img) {
		error = "FSearchImage must be applied to the complete sequence";
		return img;
	}
		

	@SuppressWarnings({ "unchecked", "unchecked" })
	public JIPSequence processSeq(JIPSequence seq) {

		// First, it gets the first frame and checks if it is of color type
		JIPImage img = seq.getFrame(0);
		if (img == null || img.getType() != JIP.tCOLOR) {
			error = "FSearchImage: image must be of color type";
			return seq;
		}
				
		int disc = getParamValueInt("disc");
		String type = getParamValueStr("type");
		// Processes the input image and gets the histogram
		FCalcHistoColor chc = new FCalcHistoColor();
		chc.setParamValue("disc", disc);
		chc.setParamValue("type", type);
		chc.processImg(img);
		if (chc.isError()) {
			error = "SearchImage error: "+chc.getError();
			return seq;
		}
		if (chc.isWarning()) {
			warning = "SearchImage warning: "+chc.getWarning();
			return seq;
		}
		if (chc.isInfo()) {
			info = "SearchImage info: "+chc.getInfo();
			return seq;
		}
		
		float [][][]histoImage =(float[][][])chc.getResultValueObj("histo");
		
		// Now, gets the image histogram BD 
		FileInputStream fos=null;
		ObjectInputStream oos=null;
		ArrayList names = new ArrayList();
		ArrayList histograms = new ArrayList();
		try {
			fos = new FileInputStream(getParamValueStr("imageBD"));
			oos = new ObjectInputStream(fos);
			while (true) {
				names.add(oos.readUTF());
				histograms.add((float[][][])oos.readObject());
				
			}
			
		}
		catch (EOFException e) {} // This is just to reach the end of file
		catch (ClassNotFoundException e) {
			System.out.println(e);
		} 
		catch (FileNotFoundException e) {
			error = "SearchImage: file "+getParamValueStr("imageBD")+" not found";
			return seq;
		}
		catch (IOException e) {
			e.printStackTrace();
			error = "SearchImage: a Input/Output exception ocurred";
			return seq;
		}
		// If we are here, we have reached the end of the file
		try {
			if (oos != null && fos != null) {
				oos.close();
				fos.close();
			}
		}
		catch (Exception e) {System.out.println(e);}

		// And now, we have to search for images with low distances
		// but we have to know the algorithm to use
		double min=Double.MAX_VALUE;
		int index=-1;
		double []distances = new double[names.size()];
		String algo = getParamValueStr("algorithm");
		
		for (int i=0; i<names.size(); i++) {
			if(algo.equals("L1"))
				distances[i] = HistoDistances.calcL1(histoImage, (float[][][])histograms.get(i));
			else if(algo.equals("L2"))
				distances[i] = HistoDistances.calcL2(histoImage, (float[][][])histograms.get(i));
			else if(algo.equals("Jeffrey-divergence"))
				distances[i] = HistoDistances.Jeffrey(histoImage, (float[][][])histograms.get(i));
			else if(algo.equals("Kullback-Leibler divergence"))
				distances[i] = HistoDistances.KullbackLeibler(histoImage, (float[][][])histograms.get(i));
		 	
			if (distances[i] < min) {
				min = distances[i];
				index=i;
			}
		 } 
		
		// Returns the most similar image
		float perc = getParamValueReal("perc");
		Image imgAWT = JIPToolkit.getAWTImage((String)names.get(index));
		if (imgAWT != null)
			img=JIPToolkit.getColorImage(imgAWT);
		seq.setFrame(img,0);
		int []indexes = new int[names.size()];
		indexes[0]=index;
		int max=1;
		// And now, stores images with a distance less than the minimum increased
		// by the percentage parameter
		for (int i=0; i<distances.length; i++)
			if (index != i) {
				if (distances[i] <= min*(1+perc)) {
					imgAWT = JIPToolkit.getAWTImage((String)names.get(i));
					if (imgAWT != null) img=JIPToolkit.getColorImage(imgAWT);
					else continue;
					indexes[max]=-1;
					for (int j=1; j<max; j++) {
						if (distances[i] < distances[indexes[j]]) {
							seq.insertFrame(img,j);
							for (int k=max; k>j; k--) 
								indexes[k] = indexes[k-1];
							indexes[j] = i;
							break;
						}
					}
					if (indexes[max] == -1) {
						seq.addFrame (img);
						indexes[max] = i;
					}
					max++;
				}
			}
		
		// Stores the minimun distance for later calculations 
		setResultValue("distance",(float)min);
		return seq;
	}
}
