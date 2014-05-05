package com.rollingcode.shootfastgame;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

	private SurfaceHolder sh;
	private MainView bee;

	private Canvas canvas;

	private boolean run = false;

	public GameThread(SurfaceHolder _holder,MainView _bee) {
		this.sh = _holder;
		this.bee = _bee;
	}

	public void setRunnable(boolean _run) {
		run = _run;
	}

	public void run() {
		while(run) {
			canvas = null;
			try {
				canvas = sh.lockCanvas(null);
				synchronized(sh) {
					this.bee.onDraw(canvas);
				}
			} finally {
				if(canvas != null) {
					sh.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
        synchronized (sh) {
        	return this.bee.killBees(event);
        }
	}
}