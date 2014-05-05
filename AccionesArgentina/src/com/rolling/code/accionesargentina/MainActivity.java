package com.rolling.code.accionesargentina;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button b1 = (Button) findViewById(R.id.acciones_action_button);
		Button b2 = (Button) findViewById(R.id.bonos_action_button);
		Button b3 = (Button) findViewById(R.id.alertas_action_button);
		
		b1.setOnTouchListener(new OnTouchListenerImagen(AccionesActivity.class));
		b2.setOnTouchListener(new OnTouchListenerImagen(BonosActivity.class));
		b3.setOnTouchListener(new OnTouchListenerImagen(SeguimientoActivity.class));
	}

	protected class OnTouchListenerImagen implements OnTouchListener {
		
		private Class<? extends Activity> activity;
		
		public OnTouchListenerImagen(Class<? extends Activity> activity) {
			this.activity = activity;
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
	        final int action = event.getAction();
			
	        switch (action & MotionEvent.ACTION_MASK) {
		        case MotionEvent.ACTION_DOWN: {
		        	v.playSoundEffect(SoundEffectConstants.CLICK);
					Intent intent = new Intent(getApplicationContext(),activity);
					startActivity(intent);
					break;
		        }
	        }
			return false;
		}
	}
}
