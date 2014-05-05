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

import com.rolling.code.accionesargentina.db.dao.AccionDAO;
import com.rolling.code.accionesargentina.db.entity.Accion;
import com.rolling.code.accionesargentina.db.entity.Indice;
import com.rolling.code.accionesargentina.parsers.HtmlParser;
import com.rolling.code.accionesargentina.utils.AccionBonoViewGenerator;
import com.rolling.code.accionesargentina.utils.AccionesBonosConstant;
import com.rolling.code.accionesargentina.utils.AccionesBonosDBUtils;

public class AccionesActivity extends Activity {

	private AccionDAO accionDAO;

	private AccionBonoViewGenerator abG;

	private List<Float> colWidths;
	private List<String> colNames;
	
	private HtmlParser parser;

	private Activity currentAcitvity;

	private AnimationDrawable frameAnimation;
	
	private String indexName = "MERVAL";
	
	private Long indiceId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		colWidths = AccionesBonosConstant.ACCIONES_COL_WIDTHS;
		colNames = AccionesBonosConstant.ACCIONES_COL_NAMES;

		abG = new AccionBonoViewGenerator();
		accionDAO = new AccionDAO(this);
		accionDAO.open();
		
		indiceId = AccionesBonosConstant.MERVAL_INDICE_ID;
		Indice indice = new Indice();
		indice.setId(indiceId);
		List<Accion> acciones = accionDAO.getAllAccionesByInidice(indice);
		List<List<String>> results = new ArrayList<List<String>>();
		Date fechaUltimaActualizacion = new Date();
		for (Accion accion : acciones) {
			List<String> aux = new ArrayList<String>();
			aux.add(accion.getNombre());
			aux.add(accion.getTicket());
			aux.add(accion.getValor());
			aux.add(accion.getVarPercentage());
			results.add(aux);
			fechaUltimaActualizacion = new Date(accion.getFecha());
		}
		
		parser = new HtmlParser();
		currentAcitvity = this;

		abG.generateView(colWidths, colNames, results, currentAcitvity, fechaUltimaActualizacion, false, false);
		
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
				parser.parseAcciones("INDICE_MERVAL");
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
//				aux.add(list.get(1));
//				aux.add(list.get(2));
//				aux.add(list.get(3));
//				results.add(aux);
//			}

			List<List<String>> results = parser.generateResultList(false);

			if(!results.isEmpty()) {
				Date date = new Date();
				abG.generateView(colWidths, colNames, results, currentAcitvity, date, false, false);
				
				AccionesBonosDBUtils.addAcciones(accionDAO, results, indiceId, date);
				
//				Indice indice = new Indice();
//				indice.setId(indiceId);
//
//				for (List<String> list : results) {
//					Accion accion = accionDAO.getAccionByInidiceTicker(indice, list.get(1));
//					if(accion == null) {
//						accion = new Accion();
//						accion.setNombre(list.get(0));
//						accion.setTicket(list.get(1));
//						accion.setValor(list.get(2));
//						accion.setVarPercentage(list.get(3));
//						accion.setFecha(date.getTime());
//						accion.setIndiceid(indiceId);
//						accion = accionDAO.addAccion(accion);
//						Log.i(AccionesActivity.class.getSimpleName(), "Accion agregado a la Base: id: " + accion.getId() + " Nombre: " + accion.getNombre());
//					} else {
//						accion.setValor(list.get(2));
//						accion.setVarPercentage(list.get(3));
//						accion.setFecha(date.getTime());
//						accion = accionDAO.updateAccion(accion);
//						Log.i(AccionesActivity.class.getSimpleName(), "Accion fue actualizada. id: " + accion.getId() + " Nombre: " + accion.getNombre());
//					}
//				}
//				
//				accionDAO.deleteAccionesByDistinctDate(date.getTime());
			}
			
			frameAnimation.stop();
		}
	}

}
