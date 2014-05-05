package com.uade.tesis.utils;


/**
*<P><FONT COLOR="red">
*<B>FRGBToHSI: </B>Converts a RGB image into a YCbCr one.<BR>
*<B>Description</B><BR>
*<FONT COLOR="blue">
*This function converts from a RGB image into a YCbCr one. 
*<ul><B>Input parameters:</B><BR>
*<li>img: RGB image to convert to YCbCr type.<BR><BR> 
*</ul>
*<ul><B>Output parameters:</B><BR>
*<li>YCbCr image.<BR>
*</ul>
*/

public class FRGBToYCbCr extends JIPFunction {
	public FRGBToYCbCr() {
		name = "FRGBToYCbCr";
		description = "Converts RGB into YCbCr color format";
		groupFunc = JIPFunctionList.TRANSFORM;
	}
	
	public JIPImage processImg(JIPImage img) {	
		int size = img.getWidth()*img.getHeight();
		JIPImage img2 = new JIPImage (3,img.getWidth(),img.getHeight(),JIP.tREAL);
		float []Y = new float[size];
		float []Cb = new float[size];
		float []Cr = new float[size];
		// Float arrays standardize 
		float []Yn = new float[size];
		float []Cbn = new float[size];
		float []Crn = new float[size];

		if(img != null) {
			if (img.getType() == JIP.tCOLOR) {
				int[] red = img.getAllPixelRed();
				int[] green = img.getAllPixelGreen();
				int[] blue = img.getAllPixelBlue();
			
				for (int i=0;i<size;i++) {
					Y[i]=16+(65.481f*red[i]+128.553f*green[i]+24.966f*blue[i]);
					Cb[i]=128+(-37.797f*red[i]+-74.203f*green[i]+112.0f*blue[i]);
					Cr[i]=128+(112.0f*red[i]+-93.786f*green[i]+-18.214f*blue[i]);
					Yn[i]=(Y[i]-16)/55845.0f;
					Cbn[i]=(Cb[i]+28432)/57120.0f;
					Crn[i]=(Cr[i]+28432)/57120.0f;		
				}	
			}
			// Puts the correct values to the band respectively
			img2.setAllPixel(0,Yn);
			img2.setAllPixel(1,Cbn);
			img2.setAllPixel(2,Crn);
		}
		return img2;
	}
}
