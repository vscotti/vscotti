package com.rolling.code.accionesargentina;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rolling.code.accionesargentina.R;
import com.rolling.code.accionesargentina.parsers.HtmlParser;

public class AlertasActivity extends Activity {

	private HtmlParser parser;

	private Context context;

	private AnimationDrawable frameAnimation;
	
	private String indexName = "MERVAL";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		parser = new HtmlParser();
		context = this;

		TextView title = (TextView)findViewById(R.id.title_index);
		title.setText(indexName);

		ImageView img = (ImageView)findViewById(R.id.imageView1);
		img.setBackgroundResource(R.drawable.spin_animation);
		img.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				frameAnimation.start();
				
				v.playSoundEffect(SoundEffectConstants.CLICK);
				AccionesAsyncTask initTask = new AccionesAsyncTask();
				initTask.execute(context);
				return false;
			}
		});
		frameAnimation = (AnimationDrawable) img.getBackground();
		frameAnimation.start();
		
		AccionesAsyncTask initTask = new AccionesAsyncTask();
		initTask.execute(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected class AccionesAsyncTask extends AsyncTask<Context, Integer, String> {
		@Override
		protected String doInBackground(Context... params) {
			try {
//				parser.parseAcciones("INDICE_MERVAL");
				parser.parseAcciones("1");
			} catch( Exception e ) {
				e.printStackTrace();
			}
			return "COMPLETE!";
		}

		@Override
		protected void onPreExecute() {
			Log.i( "makemachine", "onPreExecute()" );
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			Log.i( "makemachine", "onProgressUpdate(): " +  String.valueOf( values[0] ) );
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			Log.i( "makemachine", "onCancelled()" );
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			//TextView title = (TextView)findViewById(R.id.title_index);
			TableLayout tl = (TableLayout)findViewById(R.id.accionesTableLayout);
			tl.removeAllViews();
			
			//title.setText("MERVAL");

			for (String key : parser.getResultados().keySet()) {
				TableRow tr = new TableRow(context);
				List<String> list = parser.getResultados().get(key);
				TextView empty1 = new TextView(context);
				empty1.setText("  ");
				TextView empty2 = new TextView(context);
				empty2.setText("  ");
				TextView empty3 = new TextView(context);
				empty3.setText("  ");
				TextView col1 = new TextView(context);
				col1.setText(key);
				TextView col2 = new TextView(context);
				col2.setText(list.get(1));
				TextView col3 = new TextView(context);
				col3.setText(list.get(2));
				String value = "nochanges";
				if(list.get(2).startsWith("-")) {
					value = "downchanges";
				} else if(!isZero(list.get(2))) {
					value = "upchanges";
				}
				int id = 0;
				id = context.getResources().getIdentifier(value, "drawable", getPackageName());
				ImageView imageView = new ImageView(context);
				if(id != 0) {
					imageView.setBackgroundResource(id);
				}
				tr.addView(col1);
				tr.addView(empty1);
				tr.addView(col2);
				tr.addView(empty2);
				tr.addView(col3);
				tr.addView(empty3);
				tr.addView(imageView);
				tl.addView(tr);
				
				frameAnimation.stop();
			}
		}

		private boolean isZero(String number) {
			for (int i = 0 ; i < number.length() ; i++) {
				if(Character.isDigit(number.charAt(i)) &&
						Character.getNumericValue(number.charAt(i)) != 0) {
					return false;
				}
			}
			return true;
		}
	}

}
