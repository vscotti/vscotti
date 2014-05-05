package com.rolling.code.accionesargentina;

import java.util.ArrayList;
import java.util.Date;
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
import android.widget.TextView;

import com.rolling.code.accionesargentina.db.dao.BonoDAO;
import com.rolling.code.accionesargentina.db.entity.Bono;
import com.rolling.code.accionesargentina.db.entity.Indice;
import com.rolling.code.accionesargentina.parsers.HtmlParser;
import com.rolling.code.accionesargentina.utils.AccionBonoViewGenerator;
import com.rolling.code.accionesargentina.utils.AccionesBonosConstant;
import com.rolling.code.accionesargentina.utils.AccionesBonosDBUtils;

public class BonosActivity extends Activity {

	private BonoDAO bonoDao;

	private AccionBonoViewGenerator abG;

	private List<Float> colWidths;
	private List<String> colNames;
	
	private HtmlParser parser;

	private Activity currentAcitvity;

	private AnimationDrawable frameAnimation;
	
	private String indexName = "BONOS";
	
	private Long indiceId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		colWidths = AccionesBonosConstant.BONOS_COL_WIDTHS;
		colNames = AccionesBonosConstant.BONOS_COL_NAMES;
		
		parser = new HtmlParser();
		currentAcitvity = this;

		abG = new AccionBonoViewGenerator();
		bonoDao = new BonoDAO(this);
		bonoDao.open();
		
		indiceId = AccionesBonosConstant.BONOS_NACIONALES_INDICE_ID;
		Indice indice = new Indice();
		indice.setId(indiceId);
		List<Bono> bonos = bonoDao.getAllBonosByInidice(indice);
		List<List<String>> results = new ArrayList<List<String>>();
		Date fechaUltimaActualizacion = new Date();
		for (Bono bono : bonos) {
			List<String> aux = new ArrayList<String>();
			aux.add(bono.getNombre());
			aux.add(bono.getTicket());
			aux.add(bono.getMoneda());
			aux.add(bono.getValor());
			aux.add(bono.getVarPercentage());
			results.add(aux);
			fechaUltimaActualizacion = new Date(bono.getFecha());
		}

		abG.generateView(colWidths, colNames, results, currentAcitvity, fechaUltimaActualizacion, true, false);

		TextView title = (TextView)findViewById(R.id.title_index);
		title.setText(indexName);

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
		BonosAsyncTask initTask = new BonosAsyncTask();
		initTask.execute(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected class BonosAsyncTask extends AsyncTask<Context, Integer, String> {

		@Override
		protected String doInBackground(Context... params) {
			try {
				parser.parseBonos("1");
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

//			List<List<String>> results = new ArrayList<List<String>>();
//			for (String key : parser.getResultados().keySet()) {
//				List<String> list = parser.getResultados().get(key);
//				List<String> aux = new ArrayList<String>();
//				aux.add(list.get(0));
//				aux.add(list.get(2));
//				aux.add(list.get(1));
//				aux.add(list.get(7));
//				aux.add(list.get(8));
//				results.add(aux);
//			}
			
			List<List<String>> results = parser.generateResultList(true);

			if(!results.isEmpty()) {
				Date date = new Date();
				abG.generateView(colWidths, colNames, results, currentAcitvity, date, true, false);
	
				AccionesBonosDBUtils.addBonos(bonoDao, results, indiceId, date);
				
//				Indice indice = new Indice();
//				indice.setId(indiceId);R
//				
//				for (List<String> list : results) {
//					Bono bono = bonoDao.getBonoByInidiceTicker(indice, list.get(1));
//					if(bono == null) {
//						bono = new Bono();
//						bono.setNombre(list.get(0));
//						bono.setTicket(list.get(1));
//						bono.setMoneda(list.get(2));
//						bono.setValor(list.get(3));
//						bono.setVarPercentage(list.get(4));
//						bono.setFecha(date.getTime());
//						bono.setIndiceid(indiceId);
//						bono = bonoDao.addBono(bono);
//						Log.i(BonosActivity.class.getSimpleName(), "Bono agregado a la Base: id: " + bono.getId() + " Nombre: " + bono.getNombre());
//					} else {
//						bono.setValor(list.get(3));
//						bono.setVarPercentage(list.get(4));
//						bono.setFecha(date.getTime());
//						bono = bonoDao.updateBono(bono);
//						Log.i(BonosActivity.class.getSimpleName(), "Accion fue actualizada. id: " + bono.getId() + " Nombre: " + bono.getNombre());
//					}
//				}
//				
//				bonoDao.deleteBonosByDistinctDate(date.getTime());
			}
			
			frameAnimation.stop();
		}
	}

}
