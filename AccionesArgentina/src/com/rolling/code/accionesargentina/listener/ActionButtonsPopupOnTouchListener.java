package com.rolling.code.accionesargentina.listener;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.rolling.code.accionesargentina.R;
import com.rolling.code.accionesargentina.SeguimientoActivity;
import com.rolling.code.accionesargentina.db.dao.AccionDAO;
import com.rolling.code.accionesargentina.db.dao.AccionSeguimientoDAO;
import com.rolling.code.accionesargentina.db.dao.BonoDAO;
import com.rolling.code.accionesargentina.db.dao.BonoSeguimientoDAO;
import com.rolling.code.accionesargentina.db.dao.IndiceDAO;
import com.rolling.code.accionesargentina.db.entity.Accion;
import com.rolling.code.accionesargentina.db.entity.AccionSeguimiento;
import com.rolling.code.accionesargentina.db.entity.Bono;
import com.rolling.code.accionesargentina.db.entity.BonoSeguimiento;
import com.rolling.code.accionesargentina.db.entity.Indice;
import com.rolling.code.accionesargentina.utils.AccionesBonosConstant;

public 	class ActionButtonsPopupOnTouchListener implements OnClickListener {
	
	private SeguimientoActivity activity;

	private Spinner spinnerPopupIndices;
	private Spinner spinnerPopupAccionesBonos;

	private boolean isBonos;

	private List<Indice> indices;
	private List<Accion> acciones;
	private List<Bono> bonos;
	
	private IndiceDAO indiceDAO;
	private AccionDAO accionDAO;
	private BonoDAO bonoDAO;
	private AccionSeguimientoDAO accionSeguimientoDAO;
	private BonoSeguimientoDAO bonoSeguimientoDAO;

	public ActionButtonsPopupOnTouchListener(SeguimientoActivity activity) {
		this.activity = activity;
		this.isBonos = false;
		this.indiceDAO = new IndiceDAO(activity);
		this.accionDAO = new AccionDAO(activity);
		this.bonoDAO = new BonoDAO(activity);
		this.accionSeguimientoDAO = new AccionSeguimientoDAO(activity);
		this.bonoSeguimientoDAO = new BonoSeguimientoDAO(activity);
	}
	
	
	@Override
	public void onClick(View v) {
		this.isBonos = false;
		if(v.getId() == R.id.agregarBono_button) {
			this.isBonos = true;
		}
		Log.i(this.getClass().getSimpleName(), "onClick");
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		Point p = new Point();
		p.x = location[0];
		p.y = location[1];
		showPopup(p);
	}
	
	private void showPopup(Point p) {
		String text = "Jugadas Guardadas";

		LinearLayout viewGroup = (LinearLayout) activity.findViewById(R.id.popup);
		LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);

		Button close = (Button) layout.findViewById(R.id.popup_close);
		Button select = (Button) layout.findViewById(R.id.popup_select);
		
		indiceDAO.open();
		
		final PopupWindow popup = new PopupWindow(activity);
		popup.setContentView(layout);
		popup.setWidth(LayoutParams.MATCH_PARENT);
		popup.setHeight(LayoutParams.WRAP_CONTENT);
		popup.setFocusable(true);

		int OFFSET_X = popup.getWidth();
		int OFFSET_Y = 300;

		TextView popupTitle = (TextView) layout.findViewById(R.id.popup_title);
		popupTitle.setText(text);
	   
		LinearLayout popupLayout = (LinearLayout) layout.findViewById(R.id.popup_layout);
		popupLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		popupLayout.removeAllViews();
		String type = "ACCION";
		if(isBonos) {
			type = "BONO";
		}
		indices = indiceDAO.getIndicesByTipo(type);
		
		if(indices.isEmpty()) {
			TextView tv = new TextView(activity);
			tv.setText("No Posee Sorteos Grabados");
			popupLayout.addView(tv);
			select.setVisibility(Button.INVISIBLE);
		} else {
			List<String> list = new ArrayList<String>();
			for (Indice value : indices) {
				list.add(value.getNombre());
			}
			ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(activity,
					android.R.layout.simple_spinner_item,
					list.toArray(new String[]{}));
			spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerPopupIndices = new Spinner(activity);
			spinnerPopupIndices.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			spinnerPopupIndices.setAdapter(spinnerAdapter);
			spinnerPopupIndices.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View v, int i, long l) {
					loadDataSpinner(i);
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});
			popupLayout.addView(spinnerPopupIndices);
			
			spinnerPopupAccionesBonos = new Spinner(activity);
			spinnerPopupAccionesBonos.setId(AccionesBonosConstant.SPINNER_ACCIONESBONOS_ID);
			spinnerPopupAccionesBonos.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			popupLayout.addView(spinnerPopupAccionesBonos);
						
			loadDataSpinner(0);
			
			select.setVisibility(Button.VISIBLE);
		}
		
		popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popup.dismiss();
			}
		});
		select.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(spinnerPopupAccionesBonos.getSelectedItemPosition() >= 0) {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
		            builder1.setMessage("Esta seguro que desea agregar " + spinnerPopupAccionesBonos.getSelectedItem() + " al seguimiento?");
		            builder1.setCancelable(true);
		            builder1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int id) {
		                	if(!isBonos) {
		                		AccionSeguimiento newAccion = new AccionSeguimiento();
		                		newAccion.setAccion(acciones.get(spinnerPopupAccionesBonos.getSelectedItemPosition()));
		                		accionSeguimientoDAO.open();
		                		newAccion = accionSeguimientoDAO.addAccionSeguimiento(newAccion);
		                		accionSeguimientoDAO.close();
		                		Log.i("ActionButtonsOnTouchListener", "La accion: " + newAccion.getId() + " fue guardado con exito.");
		                	} else {
		                		BonoSeguimiento newBono = new BonoSeguimiento();
		                		newBono.setBono(bonos.get(spinnerPopupAccionesBonos.getSelectedItemPosition()));
		                		bonoSeguimientoDAO.open();
		                		newBono = bonoSeguimientoDAO.addBonoSeguimiento(newBono);
		                		bonoSeguimientoDAO.close();
		                		Log.i("ActionButtonsOnTouchListener", "El Bono: " + newBono.getId() + " fue guardado con exito.");
		                	}
		                    dialog.cancel();
		                    popup.dismiss();
		                    ((SeguimientoActivity) activity).refreshScreen();
		                }
		            });
		            builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int id) {
		                    dialog.cancel();
		                }
		            });
		            AlertDialog alert11 = builder1.create();
		            alert11.show();					
				} else {
					popup.dismiss();
				}
			}
		});
		
		indiceDAO.close();

	}

	private void loadDataSpinner(int index) {
		accionDAO.open();
		bonoDAO.open();
		List<String> list = new ArrayList<String>();
		if(!isBonos) {
			acciones = accionDAO.getAllAccionesByInidice(indices.get(index));
			for (Accion accion : acciones) {
				if(!accionAlreadySeguimiento(accion)){
					list.add(accion.getNombre() + " / " + accion.getTicket());
				}
			}
		} else {
			bonos = bonoDAO.getAllBonosByInidice(indices.get(index));
			for (Bono bono : bonos) {
				if(!bonoAlreadySeguimiento(bono)) {
					list.add(bono.getNombre() + " / " + bono.getTicket());
				}
			}
		}
		
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(activity,
				android.R.layout.simple_spinner_item,
				list.toArray(new String[]{}));
		
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPopupAccionesBonos.setAdapter(spinnerAdapter);
		accionDAO.close();
		bonoDAO.close();
	}
	
	private boolean accionAlreadySeguimiento(Accion accion) {
		for (AccionSeguimiento accionSeguimiento : activity.getAccionesSeguimiento()) {
			if(accionSeguimiento.getAccion().getId() == accion.getId()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean bonoAlreadySeguimiento(Bono bono) {
		for (BonoSeguimiento bonoSeguimiento : activity.getBonosSeguimiento()) {
			if(bonoSeguimiento.getBono().getId() == bono.getId()) {
				return true;
			}
		}
		return false;
	}
}
