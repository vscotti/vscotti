package com.rolling.code.accionesargentina;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.rolling.code.accionesargentina.db.dao.AccionDAO;
import com.rolling.code.accionesargentina.db.dao.AccionSeguimientoDAO;
import com.rolling.code.accionesargentina.db.dao.BonoDAO;
import com.rolling.code.accionesargentina.db.dao.BonoSeguimientoDAO;
import com.rolling.code.accionesargentina.db.entity.AccionSeguimiento;
import com.rolling.code.accionesargentina.db.entity.BonoSeguimiento;
import com.rolling.code.accionesargentina.listener.ActionButtonsPopupOnTouchListener;
import com.rolling.code.accionesargentina.parsers.HtmlParser;
import com.rolling.code.accionesargentina.utils.AccionBonoViewGenerator;
import com.rolling.code.accionesargentina.utils.AccionesBonosConstant;
import com.rolling.code.accionesargentina.utils.AccionesBonosDBUtils;

public class SeguimientoActivity extends Activity {

	private AccionSeguimientoDAO accionSeguimientoDAO;
	private AccionDAO accionDAO;
	private BonoSeguimientoDAO bonoSeguimientoDAO;
	private BonoDAO bonoDAO;

	private String indexName = "Seguimiento";

	private HtmlParser parser;

	private AnimationDrawable frameAnimation;
	
	private List<AccionSeguimiento> accionesSeguimiento;
	private List<BonoSeguimiento> bonosSeguimiento;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seguimiento_main);
		
		parser = new HtmlParser();

		accionSeguimientoDAO = new AccionSeguimientoDAO(this);
		bonoSeguimientoDAO = new BonoSeguimientoDAO(this);
		accionDAO = new AccionDAO(this);
		bonoDAO = new BonoDAO(this);
		
		accionSeguimientoDAO.open();
		bonoSeguimientoDAO.open();
		accionDAO.open();
		bonoDAO.open();
		
		TextView title = (TextView)findViewById(R.id.title_index);
		title.setText(indexName);

		Button agregarAccion = (Button) findViewById(R.id.agregarAccion_button);
		Button agregarBono = (Button) findViewById(R.id.agregarBono_button);
		
		ActionButtonsPopupOnTouchListener ab = new ActionButtonsPopupOnTouchListener(this);
		
		agregarAccion.setOnClickListener(ab);
		agregarBono.setOnClickListener(ab);
		
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
		
		refreshScreen();
		
		callAsyncTask();
	}

	public List<AccionSeguimiento> getAccionesSeguimiento() {
		return accionesSeguimiento;
	}
	public List<BonoSeguimiento> getBonosSeguimiento() {
		return bonosSeguimiento;
	}
	
	public void refreshScreen() {
		accionesSeguimiento = accionSeguimientoDAO.getAccionesSeguimiento();
		bonosSeguimiento = bonoSeguimientoDAO.getBonosSeguimiento();
		Log.i("SeguimientoActivity", "Hay " + accionesSeguimiento.size() + " acciones en seguimiento.");
		Log.i("SeguimientoActivity", "Hay " + bonosSeguimiento.size() + " bonos en seguimiento.");
		generateScreen();
	}
	
	private void callAsyncTask() {
		SeguimientoAsyncTask initTask = new SeguimientoAsyncTask();
		initTask.execute(this);
	}
	
	private void generateScreen() {
		ScrollView sv = (ScrollView)findViewById(R.id.scrollView1);
		sv.removeAllViews();
		if((accionesSeguimiento == null || accionesSeguimiento.isEmpty()) &&
				(bonosSeguimiento == null || bonosSeguimiento.isEmpty())) {
			TextView text = new TextView(this);
			text.setTypeface(null, Typeface.BOLD);
			text.setText("No hay informacion para mostrar. Agrege Bonos o Acciones atravez de los botones para agregar o desde la misma Accion o Bono.");
			text.setPadding(15, 15, 15, 0);
			sv.addView(text);
		} else {
			LinearLayout main = new LinearLayout(this);
			main.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			main.setOrientation(LinearLayout.VERTICAL);
			if(accionesSeguimiento != null && !accionesSeguimiento.isEmpty()) {
				TextView text = new TextView(this);
				text.setTypeface(null, Typeface.BOLD);
				text.setText("ACCIONES");
				text.setTextSize(20);
				text.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				text.setGravity(Gravity.CENTER);
				text.setPadding(5, 5, 5, 5);
				main.addView(text);
				
				List<List<String>> results = new ArrayList<List<String>>();
				for(AccionSeguimiento accionSeguimiento : accionesSeguimiento) {
					List<String> list = new ArrayList<String>();
					list.add(String.valueOf(accionSeguimiento.getId()));
					list.add(accionSeguimiento.getAccion().getNombre());
					list.add(accionSeguimiento.getAccion().getTicket());
					list.add(accionSeguimiento.getAccion().getValor());
					list.add(accionSeguimiento.getAccion().getVarPercentage());
					results.add(list);
				}

				main.addView(generateTable(AccionesBonosConstant.ACCIONES_COL_NAMES, AccionesBonosConstant.ACCIONES_COL_WIDTHS, results, false));
			}
			if(bonosSeguimiento != null && !bonosSeguimiento.isEmpty()) {
				TextView text = new TextView(this);
				text.setTypeface(null, Typeface.BOLD);
				text.setText("BONOS");
				text.setTextSize(20);
				text.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				text.setGravity(Gravity.CENTER);
				text.setPadding(5, 5, 5, 5);
				main.addView(text);
				
				List<List<String>> results = new ArrayList<List<String>>();
				for (BonoSeguimiento bonoSeguimiento : bonosSeguimiento) {
					List<String> list = new ArrayList<String>();
					list.add(String.valueOf(bonoSeguimiento.getId()));
					list.add(bonoSeguimiento.getBono().getNombre());
					list.add(bonoSeguimiento.getBono().getTicket());
					list.add(bonoSeguimiento.getBono().getMoneda());
					list.add(bonoSeguimiento.getBono().getValor());
					list.add(bonoSeguimiento.getBono().getVarPercentage());
					results.add(list);
				}

				main.addView(generateTable(AccionesBonosConstant.BONOS_COL_NAMES, AccionesBonosConstant.BONOS_COL_WIDTHS, results, true));
			}
			sv.addView(main);
		}
	}
	
	private TableLayout generateTable(List<String> colNames, List<Float> colWidths, List<List<String>> results, boolean isBono) {
		TableLayout tl = new TableLayout(this);
		AccionBonoViewGenerator ag = new AccionBonoViewGenerator();
		ag.generateTableContent(tl, tl, colWidths, colNames, results, this, isBono, true);
		return tl;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected class SeguimientoAsyncTask extends AsyncTask<Context, Integer, String> {
		
		@Override
		protected String doInBackground(Context... params) {
			try {
				Date date = new Date();
				
				parser.parseAcciones("INDICE_MERVAL");
				List<List<String>> results = parser.generateResultList(false);
				AccionesBonosDBUtils.addAcciones(accionDAO, results, 1L, date);
				
				parser.parseBonos("1");
				results = parser.generateResultList(true);
				AccionesBonosDBUtils.addBonos(bonoDAO, results, 3L, date);
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
			refreshScreen();
			frameAnimation.stop();
		}
	}

}
