package com.rollingcode.shootfastgame;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;

public class ThingView extends View {
	
	private Bitmap mImage;

	private Random random;
	
	private float currentXPosition, currentYPosition;
	private float vxPosition, vyPosition;
	private float velocity;

	private int displayWidth;
	private int displayHeight;
	private int deathCounts;
	
	private int themeSelected;
	
	private boolean isAlive;
	
	public ThingView(Context context) {
		super(context);
	}
	
	public ThingView(Context context, 
				   Random random, 
				   int displayWidth, 
				   int displayHeight,
				   int themeSelected,
				   float velocity) {
		super(context);

		this.random = random;

		this.isAlive = true;
		
		this.deathCounts = 0;
		
		this.displayWidth = displayWidth;
		this.displayHeight = displayHeight;
		this.themeSelected = themeSelected;
				
		this.velocity = velocity;
		
		int directionx = random.nextInt(3) - 1;
		int directiony = random.nextInt(3) - 1;
		if(directionx == 0 &&
				directiony == 0) {
			int direction = random.nextInt(2);
			if(direction == 0) {
				direction = random.nextInt(2);
				directionx = direction==0?-1:1;
			} else {
				direction = random.nextInt(2);
				directiony = direction==0?-1:1;
			}
		}
		this.vxPosition = directionx * 2.0f + velocity;
		this.vyPosition = directiony * 2.0f + velocity;
		
		this.mImage = ThingBitmapFactory.getBitmap(getResources(), themeSelected, vxPosition, vyPosition);
		this.currentXPosition = random.nextInt(displayWidth - getImageWidth() - 10);
		this.currentYPosition = random.nextInt(displayHeight - getImageHeight() - 10);
	}

	public int getImageWidth() {
		if(mImage != null) {
			return mImage.getWidth();
		} else {
			return 0;
		}
	}
	public int getImageHeight() {
		if(mImage != null) {
			return mImage.getHeight();
		} else {
			return 0;
		}
	}
	public float getCurrentXPosition() {
		return currentXPosition;
	}
	public float getCurrentYPosition() {
		return currentYPosition;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public Bitmap getImage() {
		return mImage;
	}

	public void updatePosition() {
		if(isAlive()) {
			if(checkCollisions()) {
				if(themeSelected == Constants.theme.FLOWERS_THEME.ordinal() ||
						themeSelected == Constants.theme.LAKE_THEME.ordinal() ||
						themeSelected == Constants.theme.CUSTOM_THEME.ordinal()) {
					this.mImage = ThingBitmapFactory.getBitmap(getResources(), themeSelected, vxPosition, vyPosition);
				}
			}
			currentXPosition += vxPosition;
			currentYPosition += vyPosition;
			
		} else {
			deathCounts++;
			if(deathCounts >= 10) {
				mImage = null;
				deathCounts = 0;
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int widthBee = mImage.getWidth();
		int heightBee = mImage.getHeight();
		
    	float xTouch = event.getX();
    	float yTouch = event.getY();
		
		if(xTouch >= this.currentXPosition && xTouch <= (this.currentXPosition + widthBee) &&
				yTouch >= this.currentYPosition && yTouch <= (this.currentYPosition + heightBee)) {
    		isAlive = false;
			this.mImage = ThingBitmapFactory.getKillBitmap(getResources(), themeSelected, vxPosition, vyPosition);
		}
        return true; 
	}

	private boolean checkCollisions() {
		boolean collition = false;
		if(currentXPosition + vxPosition < 0) {
			vxPosition = Math.abs(vxPosition);
			int directiony = random.nextInt(3) - 1;
			vyPosition = directiony * 5.0f + velocity;
			collition = true;
		} else if(currentXPosition + vxPosition > displayWidth - getImageWidth()) {
			vxPosition = -Math.abs(vxPosition);
			int directiony = random.nextInt(3) - 1;
			vyPosition = directiony * 5.0f + velocity;
			collition = true;
		}
		if(currentYPosition + vyPosition < 0) {
			vyPosition = Math.abs(vyPosition);
			int directionx = random.nextInt(3) - 1;
			vxPosition = directionx * 5.0f + velocity;
			collition = true;
		} else if(currentYPosition + vyPosition > displayHeight - getImageHeight()) {
			vyPosition = -Math.abs(vyPosition);
			int directionx = random.nextInt(3) - 1;
			vxPosition = directionx * 5.0f + velocity;
			collition = true;
		}
		return collition;
	}
}