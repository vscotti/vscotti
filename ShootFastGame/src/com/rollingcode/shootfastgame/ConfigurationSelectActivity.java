package com.rollingcode.shootfastgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class ConfigurationSelectActivity extends Activity {

	private boolean espanolSelected;
	private boolean sounfEffectOn;
	
	private Constants.difficult difficultSelected;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.configurationselect);
		
		difficultSelected = Constants.difficult.EASY_DIFFICULT;
		sounfEffectOn = true;
		espanolSelected = getIntent().getBooleanExtra("espanolSelected", true);
		
		int easyResourse = R.drawable.easy_difficult_english_selected;
		int mediumResourse = R.drawable.medium_difficult_english;
		int hardResourse = R.drawable.hard_difficult_english;
		if(espanolSelected) {
			easyResourse = R.drawable.easy_difficult_espanol_selected;
			mediumResourse = R.drawable.medium_difficult_espanol;
			hardResourse = R.drawable.hard_difficult_espanol;
		}
		((ImageView)findViewById(R.id.easy_action_button)).setImageResource(easyResourse);
		((ImageView)findViewById(R.id.medium_action_button)).setImageResource(mediumResourse);
		((ImageView)findViewById(R.id.hard_action_button)).setImageResource(hardResourse);
		((ImageView)findViewById(R.id.sound_effect_action_button)).setImageResource(R.drawable.sound_on);

		findViewById(R.id.easy_action_button).setOnTouchListener(new SelectDifficultOnTouchListenerImagen(Constants.difficult.EASY_DIFFICULT));
		findViewById(R.id.medium_action_button).setOnTouchListener(new SelectDifficultOnTouchListenerImagen(Constants.difficult.MEDIUM_DIFFICULT));
		findViewById(R.id.hard_action_button).setOnTouchListener(new SelectDifficultOnTouchListenerImagen(Constants.difficult.HARD_DIFFICULT));
		findViewById(R.id.sound_effect_action_button).setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				sounfEffectOn = !sounfEffectOn;
				((ImageView)findViewById(R.id.sound_effect_action_button)).setImageResource(R.drawable.sound_off);
				if(sounfEffectOn) {
					((ImageView)findViewById(R.id.sound_effect_action_button)).setImageResource(R.drawable.sound_on);
				}
				return false;
		}});
		findViewById(R.id.start_game_action_button).setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Intent intent = new Intent(getApplicationContext(),GameMainActivity.class);
				if(((Spinner)findViewById(R.id.layout_values)).getSelectedItemPosition() == Constants.theme.CUSTOM_THEME.ordinal()) {
					intent = new Intent(getApplicationContext(),CameraActivity.class);
				}
				intent.putExtra("espanolSelected", espanolSelected);
				intent.putExtra("sounfEffectOn", sounfEffectOn);
				intent.putExtra("difficultSelected", difficultSelected);
				intent.putExtra("themeSelected", ((Spinner)findViewById(R.id.layout_values)).getSelectedItemPosition());
				startActivity(intent);
				return false;
		}});
		
		((TextView)findViewById(R.id.layout_title)).setText("Theme:");
		((TextView)findViewById(R.id.sound_title)).setText("Sound:");
		((TextView)findViewById(R.id.difficult_title)).setText("Difficult:");
		if(espanolSelected) {
			((TextView)findViewById(R.id.layout_title)).setText("Tema:");
			((TextView)findViewById(R.id.sound_title)).setText("Sonido:");
			((TextView)findViewById(R.id.difficult_title)).setText("Dificultad:");
		}
		String[] values = getResources().getStringArray(R.array.layout_values_english);
		if(espanolSelected) {
			values = getResources().getStringArray(R.array.layout_values_espanol);
		}
		Spinner mySpinner = (Spinner)findViewById(R.id.layout_values);
		mySpinner.setAdapter(new MyCustomAdapter(this, R.layout.row, values));
	}

	private class MyCustomAdapter extends ArrayAdapter<String>{
		private String[] objects;
		public MyCustomAdapter(Context context, 
							   int textViewResourceId,
							   String[] objects) {
			super(context, textViewResourceId, objects);
			this.objects = objects;
		}

		@Override
		public View getDropDownView(int position, 
									View convertView,
									ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.row, parent, false);
			TextView label = (TextView)row.findViewById(R.id.rowDescription);
			label.setText(objects[position]);
			ImageView icon = (ImageView)row.findViewById(R.id.imageRow);
			if(position == Constants.theme.FLOWERS_THEME.ordinal()) {
				icon.setImageResource(R.drawable.flowers_spinner_icon);
			} else if(position == Constants.theme.FOOTBALL_THEME.ordinal()) {
				icon.setImageResource(R.drawable.pasto_spinner_icon);
			} else if(position == Constants.theme.TENNIS_THEME.ordinal()) {
				icon.setImageResource(R.drawable.tenis_spinner_icon);
			} else if(position == Constants.theme.LAKE_THEME.ordinal()) {
				icon.setImageResource(R.drawable.lake_spinner_icon);
			} else if(position == Constants.theme.CUSTOM_THEME.ordinal()) {
				icon.setImageResource(R.drawable.custom_spinner_icon);
			}
			return row;
		}
	}

	private class SelectDifficultOnTouchListenerImagen implements OnTouchListener {
		
		private Constants.difficult difficult;
		
		public SelectDifficultOnTouchListenerImagen(Constants.difficult difficult) {
			this.difficult = difficult;
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
	        final int action = event.getAction();
			
	        switch (action & MotionEvent.ACTION_MASK) {
		        case MotionEvent.ACTION_DOWN: {
		        	if(Constants.difficult.EASY_DIFFICULT == difficult) {
		        		difficultSelected = Constants.difficult.EASY_DIFFICULT;
		        		int easyResourse = R.drawable.easy_difficult_english_selected;
		        		int mediumResourse = R.drawable.medium_difficult_english;
		        		int hardResourse = R.drawable.hard_difficult_english;
		        		if(espanolSelected) {
		        			easyResourse = R.drawable.easy_difficult_espanol_selected;
		        			mediumResourse = R.drawable.medium_difficult_espanol;
		        			hardResourse = R.drawable.hard_difficult_espanol;
		        		}
		        		((ImageView)findViewById(R.id.easy_action_button)).setImageResource(easyResourse);
		        		((ImageView)findViewById(R.id.medium_action_button)).setImageResource(mediumResourse);
		        		((ImageView)findViewById(R.id.hard_action_button)).setImageResource(hardResourse);
		        	} else if(Constants.difficult.MEDIUM_DIFFICULT == difficult) {
		        		difficultSelected = Constants.difficult.MEDIUM_DIFFICULT;
		        		int easyResourse = R.drawable.easy_difficult_english;
		        		int mediumResourse = R.drawable.medium_difficult_english_selected;
		        		int hardResourse = R.drawable.hard_difficult_english;
		        		if(espanolSelected) {
		        			easyResourse = R.drawable.easy_difficult_espanol;
		        			mediumResourse = R.drawable.medium_difficult_espanol_selected;
		        			hardResourse = R.drawable.hard_difficult_espanol;
		        		}
		        		((ImageView)findViewById(R.id.easy_action_button)).setImageResource(easyResourse);
		        		((ImageView)findViewById(R.id.medium_action_button)).setImageResource(mediumResourse);
		        		((ImageView)findViewById(R.id.hard_action_button)).setImageResource(hardResourse);
		        	} else if(Constants.difficult.HARD_DIFFICULT == difficult) {
		        		difficultSelected = Constants.difficult.HARD_DIFFICULT;
		        		int easyResourse = R.drawable.easy_difficult_english;
		        		int mediumResourse = R.drawable.medium_difficult_english;
		        		int hardResourse = R.drawable.hard_difficult_english_selected;
		        		if(espanolSelected) {
		        			easyResourse = R.drawable.easy_difficult_espanol;
		        			mediumResourse = R.drawable.medium_difficult_espanol;
		        			hardResourse = R.drawable.hard_difficult_espanol_selected;
		        		}
		        		((ImageView)findViewById(R.id.easy_action_button)).setImageResource(easyResourse);
		        		((ImageView)findViewById(R.id.medium_action_button)).setImageResource(mediumResourse);
		        		((ImageView)findViewById(R.id.hard_action_button)).setImageResource(hardResourse);
		        	}
					break;
		        }
	        }
	        
			return false;
		}
	}
}