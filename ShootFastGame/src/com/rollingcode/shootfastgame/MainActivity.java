package com.rollingcode.shootfastgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private boolean espanolSelected;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);
		
		espanolSelected = false;
		
		findViewById(R.id.espanol_action_button).setOnTouchListener(new SelectLanguageOnTouchListenerImagen(Constants.LANGUAGE_ESPANOL));
		findViewById(R.id.english_action_button).setOnTouchListener(new SelectLanguageOnTouchListenerImagen(Constants.LANGUAGE_ENGLISH));
		findViewById(R.id.startgame_action_button).setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Intent intent = new Intent(getApplicationContext(),ConfigurationSelectActivity.class);
				intent.putExtra("espanolSelected", espanolSelected);
				startActivity(intent);
				return false;
			}
		});
		findViewById(R.id.startgame_action_button).setVisibility(View.INVISIBLE);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent returnIntent = new Intent();
		setResult(RESULT_CANCELED, returnIntent);        
		finish();
	}
	
	private class SelectLanguageOnTouchListenerImagen implements OnTouchListener {
		
		private String identification;
		
		public SelectLanguageOnTouchListenerImagen(String identification) {
			this.identification = identification;
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
	        final int action = event.getAction();
			
	        switch (action & MotionEvent.ACTION_MASK) {
		        case MotionEvent.ACTION_DOWN: {
		        	if(Constants.LANGUAGE_ESPANOL.equals(identification)) {
		        		((ImageView)findViewById(R.id.espanol_action_button)).setImageResource(R.drawable.espanol_selected);
		        		((ImageView)findViewById(R.id.english_action_button)).setImageResource(R.drawable.english);
		        		espanolSelected = true;
		        		findViewById(R.id.startgame_action_button).setVisibility(View.VISIBLE);
		        	} else if(Constants.LANGUAGE_ENGLISH.equals(identification)) {
		        		((ImageView)findViewById(R.id.espanol_action_button)).setImageResource(R.drawable.espanol);
		        		((ImageView)findViewById(R.id.english_action_button)).setImageResource(R.drawable.english_selected);
		        		espanolSelected = false;
		        		findViewById(R.id.startgame_action_button).setVisibility(View.VISIBLE);
		        	}
					break;
		        }
	        }
	        
			return false;
		}
	}
}