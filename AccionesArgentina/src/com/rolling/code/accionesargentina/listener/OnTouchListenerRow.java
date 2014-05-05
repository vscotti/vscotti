package com.rolling.code.accionesargentina.listener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnTouchListenerRow implements OnTouchListener {

	private Point p1;
	private Point p2;
    
	private Class<? extends Activity> destActivity;
	private Activity origActivity;
	private String accionBonoTicker;
	private String accionBonoDesc;
	private String accionBonoValue;
	private String accionBonoVar;
	private String bonoMoneda;
	
	public OnTouchListenerRow(Class<? extends Activity> destActivity, 
							  Activity origActivity, 
							  String accionBonoDesc, 
							  String accionBonoTicker, 
							  String accionBonoValue, 
							  String accionBonoVar,
							  String bonoMoneda) {
		this.destActivity = destActivity;
		this.origActivity = origActivity;
		this.accionBonoTicker = accionBonoTicker;
		this.accionBonoDesc = accionBonoDesc;
		this.accionBonoValue = accionBonoValue;
		this.accionBonoVar = accionBonoValue;
		this.bonoMoneda = bonoMoneda;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
        	p1 = new Point((int) event.getX(), (int) event.getY());
        } else if(event.getAction() == MotionEvent.ACTION_UP) {
    		p2 = new Point((int) event.getX(), (int) event.getY());
			if(Math.sqrt(Math.pow(p1.x-p2.x, 2) + Math.pow(p1.y-p2.y, 2)) <= 7.0) {
	        	v.playSoundEffect(SoundEffectConstants.CLICK);
				Intent intent = new Intent(origActivity,destActivity);
				intent.putExtra("accionBonoDesc", accionBonoDesc);
				intent.putExtra("accionBonoTicker", accionBonoTicker);
				intent.putExtra("accionBonoValue", accionBonoValue);
				intent.putExtra("accionBonoVar", accionBonoVar);
				if(bonoMoneda != null) {
					intent.putExtra("bonoMoneda", bonoMoneda);
				}
				origActivity.startActivity(intent);
    		}
        }
		return true;
	}
}
