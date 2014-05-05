package com.rollingcode.shootfastgame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

public class CameraActivity extends Activity implements PictureCallback {
    private CameraPreview mPreview;
    private Camera mCamera;
    private Context mContext = this;
    private PictureCallback mPicture = this;
    private ImageView mImage = null;

    private Bitmap mBitmapOrigin;
    
    private String filePath;
    
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	private int numberOfCameras;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.pictureselection);
 
		mPreview = new CameraPreview(this);
		mImage = null;  
		
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);
		
        numberOfCameras = Camera.getNumberOfCameras();
 
        System.out.println("numero de camaras: " + numberOfCameras);
        
   		Button backButton = (Button) findViewById(R.id.button_back);
   		backButton.setVisibility(Button.INVISIBLE);
   		backButton.setOnClickListener(
			    new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
			        	v.setVisibility(Button.INVISIBLE);

			       		Button clearButton = (Button) findViewById(R.id.button_clear);
			       		clearButton.setVisibility(Button.INVISIBLE);

			       		Button captureButton = (Button) findViewById(R.id.button_capture);
			    		captureButton.setVisibility(Button.VISIBLE);
			        	
			       		Button saveButton = (Button) findViewById(R.id.button_save);
			       		saveButton.setVisibility(Button.VISIBLE);
			       		
			        	onResume();
			        	
			    		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
			    		preview.addView(mPreview);
			        	
			    		mImage = null;
			        }
			    }
			);
   		
   		Button clearButton = (Button) findViewById(R.id.button_clear);
   		clearButton.setVisibility(Button.INVISIBLE);
   		clearButton.setOnClickListener(
			    new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
			        	mImage.setImageBitmap(mBitmapOrigin);
			        }
			    }
			);

   		Button saveButton = (Button) findViewById(R.id.button_save);
   		saveButton.setVisibility(Button.INVISIBLE);
   		saveButton.setOnClickListener(
			    new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
						Intent intent = new Intent(getApplicationContext(),GameMainActivity.class);
						intent.putExtra("espanolSelected", true);
						intent.putExtra("sounfEffectOn", true);
						intent.putExtra("difficultSelected", 0);
						intent.putExtra("themeSelected", Constants.theme.CUSTOM_THEME.ordinal());
						intent.putExtra("filePath", filePath);
						startActivity(intent);
			        }
			    }
			);

   		Button captureButton = (Button) findViewById(R.id.button_capture);
		captureButton.setOnClickListener(
		    new View.OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	System.out.println("a punto de tomar la foto");
		            mCamera.takePicture(null, null, mPicture);
		            System.out.println("foto tomada");
		        }
		    }
		);
            
    }
 
    @Override
    protected void onResume() {
        super.onResume();
 
        mImage = null;       
        mCamera = Camera.open(0);
        Parameters parameters = mCamera.getParameters();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
        	parameters.set("orientation", "portrait");
        	parameters.set("rotation",90);
        	mCamera.setParameters(parameters);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){                               
        	parameters.set("orientation", "landscape");          
        	parameters.set("rotation", 90);
        	mCamera.setParameters(parameters);
        }
        mPreview.setCamera(mCamera);
    }
 
    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera != null) {
            mCamera = null;
            mImage = null;
        }
    }
 
    @Override
	public void onPictureTaken(byte[] data, Camera camera) {
		File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
		if (pictureFile == null){
			return;
		}
		
//		onPause();
//

//		Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = 0;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
           	rotation = 90;
        }
        
        System.out.println("rotation: " + rotation);
        
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inDither = true;
		opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bitmapAux = BitmapFactory.decodeByteArray(data, 0, data.length, opt);
		int w = bitmapAux.getWidth();
		int h = bitmapAux.getHeight();
		// Setting post rotate to 90
		Matrix mtx = new Matrix();
		mtx.postRotate(rotation);
		// Rotating Bitmap
		Bitmap bitmap = Bitmap.createBitmap(bitmapAux, 0, 0, w, h, mtx, true).copy(Bitmap.Config.ARGB_8888, true);

        mBitmapOrigin = bitmap;
        
		ImageView imageView = new ImageView(mContext);
		imageView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		imageView.setImageBitmap(bitmap);
		imageView.setDrawingCacheEnabled(true);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.removeAllViews();
		preview.addView(imageView);
		
		mImage = imageView;
		
   		Button backButton = (Button) findViewById(R.id.button_back);
   		backButton.setVisibility(Button.VISIBLE);
   		
   		Button clearButton = (Button) findViewById(R.id.button_clear);
   		clearButton.setVisibility(Button.VISIBLE);

   		Button captureButton = (Button) findViewById(R.id.button_capture);
		captureButton.setVisibility(Button.INVISIBLE);

   		Button saveButton = (Button) findViewById(R.id.button_save);
   		saveButton.setVisibility(Button.VISIBLE);
   		
		try {
			filePath = pictureFile.getPath();
			FileOutputStream fos = new FileOutputStream(pictureFile);
			
			int size = bitmap.getRowBytes() * bitmap.getHeight();
			ByteBuffer b = ByteBuffer.allocate(size);
			bitmap.copyPixelsToBuffer(b);
			byte[] bytes = new byte[size];
			try {
				fos.write(b.get(bytes, 0, bytes.length).array());
			} catch (BufferUnderflowException e) {
			}			
			fos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    private File getOutputMediaFile(int type){
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mydir = mContext.getDir("Pictures", Context.MODE_PRIVATE); //Creating an internal dir;
		File mediaStorageDir = new File(mydir, "MyCameraApp");
		
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (! mediaStorageDir.exists()){
			if (! mediaStorageDir.mkdirs()){
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE){
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +
					"IMG_"+ timeStamp + ".jpg");
		} else if(type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +
					"VID_"+ timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}	
}