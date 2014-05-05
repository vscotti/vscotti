package com.rolling.code.accionesargentina;

import com.rolling.code.accionesargentina.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class IntroActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.intro);
        
		new SleepAsyncTask().execute(this);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
	    	 finish();
		}
	}
		
	private class SleepAsyncTask extends AsyncTask<Context, Integer, String> {
		
		@Override
		protected String doInBackground(Context... params) {
			try {
				Thread.sleep(3000);
			} catch( Exception e ) {
				e.printStackTrace();
			}
			return "COMPLETE!";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Intent intent = new Intent(getApplicationContext(),MainActivity.class);
			startActivityForResult(intent, 1);
		}
	}
}
