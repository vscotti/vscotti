package com.uade.tesis.utils;


/**
* <P><FONT COLOR="red">
*<B>FCalcHistoColor:</B> Calculates the histogram of a color image.<BR>
**<B>Description</B><BR>
*<FONT COLOR="blue">
*It calculates the histogram from a color image. The histogram is calculated from the
*RGB image and the discretization parameter indicates the number of bins, 
*It returns a 3 dimensions array (one for each component),
*which is the calculated histogram. <BR>
*Only applicable for COLOR type.<BR>
*Use: FCalcHistoColor<BR>
*<ul><B>Input parameters:</B><BR>
*<li>img: Input image.<BR>
*<li>disc: Discretization (number of bins)<BR>
*<li>type: Type of the image<BR>
*</ul>
<ul><B>Output parameters:</B><BR>
*<li>Returns the input image, without change it.<BR>
*<li>histo: 3 dimensions float array.<BR><BR>
*</ul>
*/

public class FCalcHistoColor extends JIPFunction {
	@SuppressWarnings("unchecked")
	public FCalcHistoColor() {
		name = "FCalcHistoColor";
		description = "Calculates the histogram of a color image";
		groupFunc = JIPFunctionList.IMAGEBD;

		JIPParameter p1 = new JIPParameter("disc", JIP.pINT, false);
		p1.setDefault(20);
		p1.setDescription("Discretization (number of bins)");
		
		JIPParameter p2 = new JIPParameter("type", JIP.pLIST, false);
		String []paux = new String[3];
		paux[0]="RGB";
		paux[1]="YCbCr";
		paux[2]="HSI";
		p2.setDefault(paux);
		p2.setDescription("Type of the image");
		
		params.addElement(p1);
		params.addElement(p2);
		
		// Output parameter
		JIPParameter po = new JIPParameter("histo", JIP.pOBJECT, false);
		po.setDescription("3 dimensions float array");
		results.addElement(po);
	}
	
	public JIPImage processImg(JIPImage img) {
		// As we have 256 values for each band, we divide this value
		// by the discretization parameter (number of bins) to get 
		// the number of values in each bin
		String tipo = getParamValueStr("type");
		int disc = getParamValueInt("disc");
		int size = img.getWidth()*img.getHeight();
		float binSize2 = 1.01f/disc;
		float [][][]acumF = new float[disc][disc][disc];
		JIPImage imagen;

		// Cheks is the img is a color image
		if (img.getType() == JIP.tCOLOR) {
			if(tipo.equals("YCbCr")) {
				float []Y = new float[size];
				float []Cb = new float[size];
				float []Cr = new float[size];
				FRGBToYCbCr ycbcr = new FRGBToYCbCr();

				imagen=ycbcr.processImg(img);
				Y=imagen.getAllPixelFlo(0);
				Cb=imagen.getAllPixelFlo(1);
				Cr=imagen.getAllPixelFlo(2);
			
				// Calculates the histogram
				for (int i = 0; i < size; i++) 
					acumF[(int)(Y[i]/binSize2)][(int)(Cb[i]/binSize2)]
									[(int)(Cr[i]/binSize2)]++;

				// Normalize, so that the sum is 1
		  		for (int i = 0; i < disc; i++) 
		  			for (int j = 0; j < disc; j++)
		  				for (int k = 0; k < disc; k++)
		  					acumF[i][j][k] = acumF[i][j][k] / (float)size;
		  		
		  		// The histogram calculated is stored 
				setResultValue("histo",acumF);
			}
			else
				if(tipo.equals("HSI"))  { 
			  		float []H = new float[size];
			  		float []S = new float[size];	
			  		float []I = new float[size];
			  		FRGBToHSI hsi = new FRGBToHSI();
	
			  		imagen=hsi.processImg(img);
			  		H=imagen.getAllPixelFlo(0);
			  		S=imagen.getAllPixelFlo(1);
			  		I=imagen.getAllPixelFlo(2);
				
			  		// Calculates the histogram
			  		for (int j = 0; j < size; j++) 
			  			acumF[(int)(H[j]/binSize2)][(int)(S[j]/binSize2)]
		  									[(int)(I[j]/binSize2)]++;
			  		
			  		// Normalize, so that the sum is 1
			  		for (int i = 0; i < disc; i++) 
			  			for (int j = 0; j < disc; j++)
			  				for (int k = 0; k < disc; k++)
			  					acumF[i][j][k] = acumF[i][j][k] / (float)size;
	
			  		// The histogram calculated is stored		  		
			  		setResultValue("histo",acumF);
				}
				else {	  	
			  		int[] red = img.getAllPixelRed();
			  		int[] green = img.getAllPixelGreen();
			  		int[] blue = img.getAllPixelBlue();
			  		float binSize = 256.0f/disc;
			  		int [][][]acum = new int[disc][disc][disc];
			  		
			  		// Calculates the histogram
			  		for (int i = 0; i < size; i++) 
			  			acum[(int)(red[i]/binSize)][(int)(green[i]/binSize)]
									[(int)(blue[i]/binSize)]++;
			  		
			  		// Normalize, so that the sum is 1
			  		for (int i = 0; i < disc; i++) 
			  			for (int j = 0; j < disc; j++)
			  				for (int k = 0; k < disc; k++)
			  					acumF[i][j][k] = acum[i][j][k] / (float)size;
			  		
			  		// The histogram calculated is stored
			  		setResultValue("histo",acumF);
			  	}
		}
		else
			error=new String("CalcHistoColor only defined for COLOR images");
		
		return img;
	}
}
