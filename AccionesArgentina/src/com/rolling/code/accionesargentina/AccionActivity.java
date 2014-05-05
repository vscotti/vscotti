package com.rolling.code.accionesargentina;

import com.rolling.code.accionesargentina.R;

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
import android.widget.TextView;

public class AccionActivity extends Activity {

	private AnimationDrawable frameAnimation;

	private String titleName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		titleName = getIntent().getStringExtra("accionBonoDesc");

		TextView title = (TextView)findViewById(R.id.title_index);
		title.setText(titleName);

		ImageView img = (ImageView)findViewById(R.id.imageView1);
		img.setBackgroundResource(R.drawable.spin_animation);
		img.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				frameAnimation.start();
				v.playSoundEffect(SoundEffectConstants.CLICK);
				callAsyncTask();
				return false;
			}
		});
		frameAnimation = (AnimationDrawable) img.getBackground();
		frameAnimation.start();

		callAsyncTask();
	}

	private void callAsyncTask() {
		AccionAsyncTask initTask = new AccionAsyncTask(this);
		initTask.execute(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected class AccionAsyncTask extends AsyncTask<Context, Integer, String> {

		private Activity currentActivity;

		public AccionAsyncTask(Activity currentActivity) {
			this.currentActivity = currentActivity;
		}

		@Override
		protected String doInBackground(Context... params) {
			try {
				//				parser.parseAcciones("INDICE_MERVAL");
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

			//			List<Float> colWidths = new ArrayList<Float>();
			//			colWidths.add(0.02f);
			//			colWidths.add(0.4f);
			//			colWidths.add(0.02f);
			//			colWidths.add(0.16f);
			//			colWidths.add(0.02f);
			//			colWidths.add(0.12f);
			//			colWidths.add(0.02f);
			//			colWidths.add(0.12f);
			//			colWidths.add(0.02f);
			//			colWidths.add(0.08f);
			//			colWidths.add(0.02f);
			//			
			//			TableRow tr = new TableRow(context);
			//			TableLayout tl_header = (TableLayout)findViewById(R.id.table_header);
			//			TableLayout tl = (TableLayout)findViewById(R.id.accionesTableLayout);
			//			tl.removeAllViews();
			//			tl_header.removeAllViews();
			//			
			//			TextView col1 = new TextView(context);
			//			col1.setText("HOLA");
			//			TextView col2 = new TextView(context);
			//			col2.setText("HOLA");
			//			TextView col3 = new TextView(context);
			//			col3.setText("HOLA");
			//			TextView col4 = new TextView(context);
			//			col4.setText("HOLA");
			//			TextView col5 = new TextView(context);
			//
			//			TextView empty1 = new TextView(context);
			//			TextView empty2 = new TextView(context);
			//			TextView empty3 = new TextView(context);
			//			TextView empty4 = new TextView(context);
			//			TextView empty5 = new TextView(context);
			//			TextView empty6 = new TextView(context);
			//			
			//			tr.addView(empty1);
			//			tr.addView(col1);
			//			tr.addView(empty2);
			//			tr.addView(col2);
			//			tr.addView(empty3);
			//			tr.addView(col3);
			//			tr.addView(empty4);
			//			tr.addView(col4);
			//			tr.addView(empty5);
			//			tr.addView(col5);
			//			tr.addView(empty6);
			//			
			//			tl_header.addView(tr);
			//
			//			tr.setGravity(Gravity.CENTER_VERTICAL);
			//			
			//	        for ( int cellnum = 0 ; cellnum < tr.getChildCount() ; cellnum++ ) {
			//	            View cell = tr.getChildAt(cellnum);
			//	            TableRow.LayoutParams params = (TableRow.LayoutParams)cell.getLayoutParams();
			//	            params.width = 0;
			//	            params.weight = colWidths.get(cellnum);
			//	        }
			//	        TableLayout.LayoutParams param = (TableLayout.LayoutParams)tr.getLayoutParams();
			//	        param.height = 300;
			//	        
			//			for (String key : parser.getResultados().keySet()) {
			//				tr = new TableRow(context);
			//				List<String> list = parser.getResultados().get(key);
			//				empty1 = new TextView(context);
			//				empty1.setText("  ");
			//				empty2 = new TextView(context);
			//				empty2.setText("  ");
			//				empty3 = new TextView(context);
			//				empty3.setText("  ");
			//				empty4 = new TextView(context);
			//				empty4.setText("  ");
			//				empty5 = new TextView(context);
			//				empty5.setText("  ");
			//				empty6 = new TextView(context);
			//				empty6.setText("  ");
			//				col1 = new TextView(context);
			//				col1.setText(list.get(0));
			//				col2 = new TextView(context);
			//				col2.setText(list.get(1));
			//				col3 = new TextView(context);
			//				col3.setText(list.get(2));
			//				col4 = new TextView(context);
			//				col4.setText(list.get(3));
			//				String value = "nochanges";
			//				if(list.get(3).startsWith("-")) {
			//					value = "downchanges";
			//				} else if(!isZero(list.get(3))) {
			//					value = "upchanges";
			//				}
			//				int id = 0;
			//				id = context.getResources().getIdentifier(value, "drawable", getPackageName());
			//				ImageView imageView = new ImageView(context);
			//				if(id != 0) {
			//					imageView.setBackgroundResource(id);
			//				}
			//				tr.setGravity(Gravity.CENTER_VERTICAL);
			//				tr.addView(empty1);
			//				tr.addView(col1);
			//				tr.addView(empty2);
			//				tr.addView(col2);
			//				tr.addView(empty3);
			//				tr.addView(col3);
			//				tr.addView(empty4);
			//				tr.addView(col4);
			//				tr.addView(empty5);
			//				tr.addView(imageView);
			//				tr.addView(empty6);
			//				tl.addView(tr);
			//
			//				tr.setOnTouchListener(new OnTouchListenerRow(null, currentActivity, list.get(1)));
			//				
			//		        for ( int cellnum = 0 ; cellnum < tr.getChildCount() ; cellnum++ ) {
			//		            View cell = tr.getChildAt(cellnum);
			//		            TableRow.LayoutParams params = (TableRow.LayoutParams)cell.getLayoutParams();
			//		            params.width = 0;
			//		            params.weight = colWidths.get(cellnum);
			//		        }
			//
			frameAnimation.stop();
			//			}
		}

		//		private boolean isZero(String number) {
		//			for (int i = 0 ; i < number.length() ; i++) {
		//				if(Character.isDigit(number.charAt(i)) &&
		//						Character.getNumericValue(number.charAt(i)) != 0) {
		//					return false;
		//				}
		//			}
		//			return true;
		//		}
	}

}
