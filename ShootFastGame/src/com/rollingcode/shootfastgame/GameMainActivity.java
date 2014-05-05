package com.rollingcode.shootfastgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameMainActivity extends Activity {

	private MainView mainView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		System.out.println(getIntent().getIntExtra("themeSelected", -1));
		
		mainView = new MainView(this, 
								getIntent().getIntExtra("difficultSelected", Integer.valueOf(Constants.difficult.EASY_DIFFICULT.ordinal())),
								getIntent().getIntExtra("themeSelected", Integer.valueOf(Constants.theme.FLOWERS_THEME.ordinal())), 
								getIntent().getBooleanExtra("espanolSelected", false), 
								getIntent().getBooleanExtra("sounfEffectOn", true),
								getIntent().getStringExtra("filePath"));
		setContentView(mainView);
	}

	public void callIntent() {
		mainView.surfaceDestroyed(null);
		mainView = null;
		setContentView(null);
		System.out.println("disparando...");
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mainView = null;
		finish();
	}
}