package com.rollingcode.shootfastgame;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class MainView extends SurfaceView implements SurfaceHolder.Callback {

	private GameThread gameThread = null;

	private Bitmap mBackgroundImage;

	private List<ThingView> things;
	
	private Context context;
	
	private Display display;
	
	private int viewCount;
	private int dificult;
	private int themeSelected;
	private int iteractions;
	
//	private long countThnigs;
	private long diffDate;
	
	private float velocity;

	private Date startDate;
	
	private Random random;
	
	public MainView(Context context, 
					int dificult, 
					int themeSelected,
					boolean isSpanishLanguage, 
					boolean soundEffectOn,
					String customImagePath) {
		super(context);
		
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		
		this.context = context;
		this.display = wm.getDefaultDisplay();
		
		this.dificult = Constants.difficult.HARD_DIFFICULT.ordinal();
		this.themeSelected = themeSelected;
		
		this.velocity = 0.0f;
		this.viewCount = 1;
//		this.countThnigs = 1;
		this.diffDate = 0;
		this.iteractions = Constants.ITERACTIONS;
		
		this.random = new Random();
		this.things = new ArrayList<ThingView>();
		this.things.add(new ThingView(context, 
								  	  random, 
								  	  display.getWidth(), 
								  	  display.getHeight(), 
								  	  themeSelected,
								  	  velocity));
		
		this.mBackgroundImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.flowers), display.getWidth(), display.getHeight(), false);
		if(themeSelected == Constants.theme.FOOTBALL_THEME.ordinal()) {
			this.mBackgroundImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pasto), display.getWidth(), display.getHeight(), false);
		} else if(themeSelected == Constants.theme.TENNIS_THEME.ordinal()) {
			this.mBackgroundImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tenis), display.getWidth(), display.getHeight(), false);
		} else if(themeSelected == Constants.theme.LAKE_THEME.ordinal()) {
			this.mBackgroundImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lake), display.getWidth(), display.getHeight(), false);
		} else if(themeSelected == Constants.theme.CUSTOM_THEME.ordinal()) {
			this.mBackgroundImage = BitmapFactory.decodeFile(customImagePath);
		}
		this.mBackgroundImage = Bitmap.createScaledBitmap(mBackgroundImage, display.getWidth(), display.getHeight(), false);

		this.getHolder().addCallback(this);
		
		this.gameThread = new GameThread(getHolder(), this);
		this.startDate = new Date();
	}

	protected void onDraw(Canvas canvas) {
		if(canvas != null) {
			canvas.drawBitmap(mBackgroundImage, 0, 0, null);
			List<ThingView> beesAux = new ArrayList<ThingView>();
			for (ThingView view : things) {
				view.updatePosition();
				if(view.getImage() != null) {
					Matrix matrixBall = new Matrix();
					matrixBall.postTranslate(view.getCurrentXPosition(), view.getCurrentYPosition());
					canvas.drawBitmap(view.getImage(), matrixBall, null);
					beesAux.add(view);
				}
			}
			things = new ArrayList<ThingView>();
			things.addAll(beesAux);
			if(things.isEmpty()) {
				iteractions--;
				if(iteractions >= 0) {
					viewCount++;
//					countThnigs += viewCount;
					for (int i = 0 ; i < viewCount ; i++) {
						if(dificult == Constants.difficult.MEDIUM_DIFFICULT.ordinal()) {
							if(viewCount % 2 == 0) {
								velocity *= 2;
							}
						} else if(dificult == Constants.difficult.MEDIUM_DIFFICULT.ordinal()) {
							if(viewCount % 2 == 0) {
								velocity *= 4;
							}
						}
						things.add(new ThingView(context, 
								  			 	 random, 
								  			 	 display.getWidth(), 
								  			 	 display.getHeight(), 
								  			 	 themeSelected,
								  			 	 velocity));
					}
					startDate = new Date();
				} else {
					System.out.println("llego aca");
					((GameMainActivity)context).callIntent();
				}
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		gameThread.setRunnable(true);
		gameThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		gameThread.setRunnable(false);
		while(retry) {
			try {
				gameThread.join();
				retry = false;
			} catch(InterruptedException ie) {
			}
			break;
		}
		gameThread = null;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gameThread.onTouchEvent(event);
	}

	public boolean killBees(MotionEvent event) {
		boolean value = true;
		boolean isAlive = false;
		for (ThingView view : things) {
			value &= view.onTouchEvent(event);
			isAlive |= view.isAlive();
		}
		if(!isAlive) {
			diffDate = diffDate + (new Date().getTime() - startDate.getTime());
		}
		return value;
	}
	
}