package com.uade.tesis.utils;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
* <P><FONT COLOR="red">
*<B>FCalcHistoBD:</B> Calculates the histograms of images in a directory<BR>
*<B>Description</B><BR>
*<FONT COLOR="blue">
*Calculates the histograms of images in a directory. It gets the directory
*name and gets all the subdirectories in it. Every directory is a cluster
*containing more images (all of them in the same group). It uses FCalcHistoColor
*to get the histogram of each image<BR>
*Use: FCalcHistoBD<BR>
*<ul><B>Input parameters:</B><BR>
*<li>img: Input image<BR>
*<li>dir: Directory containing the BD.<BR>
*<li>disc: Discretization (number of bins)<BR>
*<li>fileBD: File to save the results.<BR>
*<li>type: Type of the image.<BR>
*</ul>
<ul><B>Output parameters:</B><BR>
*<li>The input image remains the same.<BR><BR>
*</ul>
*/

public class FCalcHistoBD extends JIPFunction {

	@SuppressWarnings("unchecked")
	public FCalcHistoBD() {
		name = "FCalcHistoBD";
		description = "Calculates the histograms of images in a directory";
		groupFunc = JIPFunctionList.IMAGEBD;

		JIPParameter p1 = new JIPParameter("dir", JIP.pDIR, false);
		p1.setDescription("Directory to process");
		JIPParameter p2 = new JIPParameter("disc", JIP.pINT, false);
		p2.setDefault(20);
		p2.setDescription("Discretization (number of bins)");
		JIPParameter p3 = new JIPParameter("fileBD", JIP.pSTRING, false);
		p3.setDefault("out");
		p3.setDescription("File to save the results");
		JIPParameter p4 = new JIPParameter("type", JIP.pLIST, false);
		String []paux = new String[3];
		paux[0]="RGB";
		paux[1]="YCbCr";
		paux[2]="HSI";
		p4.setDefault(paux);
		p4.setDescription("Type of the image");

		params.addElement(p1);
		params.addElement(p2);
		params.addElement(p3);
		params.addElement(p4);
	}

	public JIPImage processImg(JIPImage img) {
		// First, open the file to save the histograms
		String fileBD = getParamValueStr("fileBD");
		FileOutputStream fos=null;
		ObjectOutputStream oos=null;
		try {
			fos = new FileOutputStream(fileBD);
			oos = new ObjectOutputStream(fos);
			int disc = getParamValueInt("disc");
			String type = getParamValueStr("type");
			String dir = getParamValueStr("dir");
			File f = new File(dir);
			// Get the names of files and directories in the current directory
			String []clusters = f.list();
			String []images;
			JIPImage imgAux=null;
			FCalcHistoColor chc = new FCalcHistoColor();
			chc.setParamValue("disc", disc);
			chc.setParamValue("type",type);
			for (int i=0; i<clusters.length; i++) {
				String group=dir+File.separator+clusters[i];
				File f2 = new File(group);
				// Only processes the directories
				if (f2.isDirectory()) {
					images=f2.list();
					// Processes all the images in the directory
					for (int pic=0; pic<images.length; pic++) {
						String fileImg=group+File.separator+images[pic];
						Image imgAWT = JIPToolkit.getAWTImage(fileImg);
						if (imgAWT != null) 
							imgAux=JIPToolkit.getColorImage(imgAWT);
						else continue;
						// Do not process files which are not images
						if (imgAux != null) {
							chc.processImg(imgAux);
							if (chc.isError()) {
								error = "CalcHistoColor error: "+chc.getError();
								continue;
							}
							if (chc.isWarning()) {
								warning = "CalcHistoColor warning: "+chc.getWarning();
								continue;
							}
							if (chc.isInfo()) {
								info = "CalcHistoColor info: "+chc.getInfo();
								continue;
							}
							float [][][]acumF =(float[][][])chc.getResultValueObj("histo");
							// Stores the filename and the histogram
							oos.writeUTF(fileImg);
							oos.writeObject(acumF);
						}
						else warning = new String("FCalcHistoBD: some files are not images (JPEG, GIF)");
					}
				}
				else //The images are directly in the folder
				{
					//Processes all the images in the directory
					Image imgAWT = JIPToolkit.getAWTImage(group);
					if (imgAWT != null)
						imgAux=JIPToolkit.getColorImage(imgAWT);
					else continue;
					// Do not process files which are not images
					if (imgAux != null) {
						chc.processImg(imgAux);
						if (chc.isError()) {
							error = "CalcHistoColor error: "+chc.getError();
							continue;
						}
						if (chc.isWarning()) {
							warning = "CalcHistoColor warning: "+chc.getWarning();
							continue;
						}
						if (chc.isInfo()) {
							info = "CalcHistoColor info: "+chc.getInfo();
							continue;
						}
						float [][][]acumF =(float[][][])chc.getResultValueObj("histo");
						// Stores the filename and the histogram
						oos.writeUTF(group);
						oos.writeObject(acumF);
					}
					else warning = new String("FCalcHistoBD: some files are not images (JPEG, GIF)");
				}
			}
		}
		catch (Exception e) {
			System.out.println(e); 
			error=new String("FCalcHistBD: error opening or writing in file "+fileBD);
			return img;
		}		
		finally {
			try {
				if (oos != null && fos != null) {
					oos.close();
					fos.close();
				}
			}
			catch (Exception e) {System.out.println(e);} 				
		}
		return img;
	}
}
