package com.rollingcode.shootfastgame;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder mSurfaceHolder;
	private Camera mCamera;
	private Context context;

	private boolean isPreviewRunning;
	
	//Constructor that obtains context and camera
	public CameraPreview(Context context) {
		super(context);

		this.context = context;
		this.mSurfaceHolder = this.getHolder();
		this.mSurfaceHolder.addCallback(this); // we get notified when underlying surface is created and destroyed
		this.mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); //this is a deprecated method, is not requierd after 3.0
	}

    public void setCamera(Camera camera) {
        mCamera = camera;
    }

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		try {
			mCamera.setPreviewDisplay(surfaceHolder);
			mCamera.startPreview();
			isPreviewRunning = true;
		} catch (IOException e) {
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		mCamera.setPreviewCallback(null);
		mCamera.stopPreview();
		mCamera.release();
		isPreviewRunning = false;
		mCamera = null;
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int format,
			int width, int height) {
		try {
	        if (isPreviewRunning)
	        {
	            mCamera.stopPreview();
	        }

	        Parameters parameters = mCamera.getParameters();
	        Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

	        if(display.getRotation() == Surface.ROTATION_0)
	        {
	            parameters.setPreviewSize(height, width);                           
	            mCamera.setDisplayOrientation(90);
	        }

	        if(display.getRotation() == Surface.ROTATION_90)
	        {
	            parameters.setPreviewSize(width, height);                           
	        }

	        if(display.getRotation() == Surface.ROTATION_180)
	        {
	            parameters.setPreviewSize(height, width);               
	        }

	        if(display.getRotation() == Surface.ROTATION_270)
	        {
	            parameters.setPreviewSize(width, height);
	            mCamera.setDisplayOrientation(180);
	        }

	        mCamera.setParameters(parameters);
			mCamera.setPreviewDisplay(surfaceHolder);
			mCamera.startPreview();
			isPreviewRunning = true;
		} catch (Exception e) {
		}
	}

}