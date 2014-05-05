package com.uade.tesis.utils;


/**
*<P><FONT COLOR="red">
*<B>FRGBToHSI: </B>Converts a RGB image into a HSI one.<BR>
*<B>Description</B><BR>
*<FONT COLOR="blue">
*This function converts from a RGB image into a HSI one. 
*<ul><B>Input parameters:</B><BR>
*<li>img: RGB image to convert to HSI type.<BR><BR> 
*</ul>
*<ul><B>Output parameters:</B><BR>
*<li>HSI image.<BR>
*</ul>
*/

public class FRGBToHSI extends JIPFunction {
	public FRGBToHSI() {
		name = "FRGBToHSI";
		description = "Converts RGB into HSI color format";
		groupFunc = JIPFunctionList.TRANSFORM;
	}
	
	public JIPImage processImg(JIPImage img) {	
	 	int size = img.getWidth()*img.getHeight();
		JIPImage img2 = new JIPImage (3,img.getWidth(),img.getHeight(),JIP.tREAL);
		float []H = new float[size];
		float []S = new float[size];
		float []I = new float[size];
		float []sigma = new float[size];
		float aux,aux2;
	
		if(img != null)  {
			if (img.getType() == JIP.tCOLOR) {
				int[] red = img.getAllPixelRed();
				int[] green = img.getAllPixelGreen();
				int[] blue = img.getAllPixelBlue();
				for (int i=0; i<size; i++) {
					I[i]=(red[i]+green[i]+blue[i])/765.0f; //Normalizing [0..1]
					S[i]=1.0f-((3.0f/(red[i]+green[i]+blue[i]))*Math.min(red[i],Math.min(green[i],blue[i])));
					aux=(1/2.0f)*((red[i]-green[i])+(red[i]-blue[i]));
					aux2=(float)Math.sqrt(((red[i]-green[i])*(red[i]-green[i]))+((red[i]-blue[i])*(green[i]-blue[i])));
					sigma[i]=(float)Math.acos(aux/aux2);
				
					if(blue[i]<=green[i])
						H[i]=(float)(sigma[i]/(2.0*Math.PI));
					else
						H[i]=(float)((2.0*Math.PI-sigma[i])/(2.0*Math.PI));
				}
			}
			// Puts the correct values to the band respectively
			img2.setAllPixel(0,H);
			img2.setAllPixel(1,S);
			img2.setAllPixel(2,I);
		}
		return img2;
	}
}
