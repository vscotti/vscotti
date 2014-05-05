package com.rolling.code.accionesargentina.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;

import com.rolling.code.accionesargentina.SeguimientoActivity;
import com.rolling.code.accionesargentina.db.dao.AccionSeguimientoDAO;
import com.rolling.code.accionesargentina.db.dao.BonoSeguimientoDAO;
import com.rolling.code.accionesargentina.db.entity.AccionSeguimiento;
import com.rolling.code.accionesargentina.db.entity.BonoSeguimiento;

public 	class ActionesBonosRemoveButtonsOnTouchListener implements OnClickListener {
	
	private Activity activity;
	
	private boolean isBonos;

	private String tickerAccionBono;
	
	private Long accionBonoSeguimientoId;
	
	private AccionSeguimientoDAO accionSeguimientoDAO;
	private BonoSeguimientoDAO bonoSeguimientoDAO;

	public ActionesBonosRemoveButtonsOnTouchListener(Activity activity, Long accionBonoSeguimientoId, String tickerAccionBono, boolean isBonos) {
		this.activity = activity;
		this.isBonos = isBonos;
		this.tickerAccionBono = tickerAccionBono;
		this.accionBonoSeguimientoId = accionBonoSeguimientoId;
		this.accionSeguimientoDAO = new AccionSeguimientoDAO(activity);
		this.bonoSeguimientoDAO = new BonoSeguimientoDAO(activity);
	}
	
	
	@Override
	public void onClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("Esta seguro que desea eliminar " + tickerAccionBono + " del seguimiento?");
        builder.setCancelable(true);
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
        		if(!isBonos) {
        			accionSeguimientoDAO.open();
        			AccionSeguimiento as = new AccionSeguimiento();
        			as.setId(accionBonoSeguimientoId);
        			accionSeguimientoDAO.deleteAccion(as);
        			accionSeguimientoDAO.close();
        		} else {
        			bonoSeguimientoDAO.open();
        			BonoSeguimiento bs = new BonoSeguimiento();
        			bs.setId(accionBonoSeguimientoId);
        			bonoSeguimientoDAO.deleteBono(bs);
        			bonoSeguimientoDAO.close();
        		}
                dialog.cancel();
                ((SeguimientoActivity) activity).refreshScreen();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();					
	}
}
