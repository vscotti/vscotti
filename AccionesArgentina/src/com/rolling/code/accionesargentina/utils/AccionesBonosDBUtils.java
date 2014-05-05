package com.rolling.code.accionesargentina.utils;

import java.util.Date;
import java.util.List;

import android.util.Log;

import com.rolling.code.accionesargentina.AccionesActivity;
import com.rolling.code.accionesargentina.BonosActivity;
import com.rolling.code.accionesargentina.db.dao.AccionDAO;
import com.rolling.code.accionesargentina.db.dao.BonoDAO;
import com.rolling.code.accionesargentina.db.entity.Accion;
import com.rolling.code.accionesargentina.db.entity.Bono;
import com.rolling.code.accionesargentina.db.entity.Indice;

public class AccionesBonosDBUtils {

	public static void addAcciones(AccionDAO accionDAO, List<List<String>> results, long indiceId, Date date) {
		Indice indice = new Indice();
		indice.setId(indiceId);

		for (List<String> list : results) {
			Accion accion = accionDAO.getAccionByInidiceTicker(indice, list.get(1));
			if(accion == null) {
				accion = new Accion();
				accion.setNombre(list.get(0));
				accion.setTicket(list.get(1));
				accion.setValor(list.get(2));
				accion.setVarPercentage(list.get(3));
				accion.setFecha(date.getTime());
				accion.setIndiceid(indiceId);
				accion = accionDAO.addAccion(accion);
				Log.i(AccionesActivity.class.getSimpleName(), "Accion agregado a la Base: id: " + accion.getId() + " Nombre: " + accion.getNombre());
			} else {
				accion.setValor(list.get(2));
				accion.setVarPercentage(list.get(3));
				accion.setFecha(date.getTime());
				accion = accionDAO.updateAccion(accion);
				Log.i(AccionesActivity.class.getSimpleName(), "Accion fue actualizada. id: " + accion.getId() + " Nombre: " + accion.getNombre());
			}
		}
		
		accionDAO.deleteAccionesByDistinctDate(date.getTime());
	}

	public static void addBonos(BonoDAO bonoDao, List<List<String>> results, Long indiceId, Date date) {
		Indice indice = new Indice();
		indice.setId(indiceId);
		
		for (List<String> list : results) {
			Bono bono = bonoDao.getBonoByInidiceTicker(indice, list.get(1));
			if(bono == null) {
				bono = new Bono();
				bono.setNombre(list.get(0));
				bono.setTicket(list.get(1));
				bono.setMoneda(list.get(2));
				bono.setValor(list.get(3));
				bono.setVarPercentage(list.get(4));
				bono.setFecha(date.getTime());
				bono.setIndiceid(indiceId);
				bono = bonoDao.addBono(bono);
				Log.i(BonosActivity.class.getSimpleName(), "Bono agregado a la Base: id: " + bono.getId() + " Nombre: " + bono.getNombre());
			} else {
				bono.setValor(list.get(3));
				bono.setVarPercentage(list.get(4));
				bono.setFecha(date.getTime());
				bono = bonoDao.updateBono(bono);
				Log.i(BonosActivity.class.getSimpleName(), "Accion fue actualizada. id: " + bono.getId() + " Nombre: " + bono.getNombre());
			}
		}
		
		bonoDao.deleteBonosByDistinctDate(date.getTime());
	}
}
