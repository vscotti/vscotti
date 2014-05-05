package com.rolling.code.accionesargentina.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.rolling.code.accionesargentina.db.entity.Accion;
import com.rolling.code.accionesargentina.db.entity.AccionSeguimiento;
import com.rolling.code.accionesargentina.db.helper.AccionesArgentinasDBSQLiteHelper;
import com.rolling.code.accionesargentina.db.helper.tables.AccionesSQLiteHelper;
import com.rolling.code.accionesargentina.db.helper.tables.AccionesSeguimientoSQLiteHelper;

public class AccionSeguimientoDAO {

	private SQLiteDatabase database;
	private AccionesArgentinasDBSQLiteHelper dbHelper;
	private String[] allColumns = { AccionesSeguimientoSQLiteHelper.COLUMN_ID,
									AccionesSeguimientoSQLiteHelper.COLUMN_ACCIONID };

	public AccionSeguimientoDAO(Context context) {
		dbHelper = new AccionesArgentinasDBSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public AccionSeguimiento addAccionSeguimiento(AccionSeguimiento newAccion) {
		ContentValues values = new ContentValues();
		values.put(AccionesSeguimientoSQLiteHelper.COLUMN_ACCIONID, newAccion.getAccion().getId());
		long insertId = database.insert(AccionesSeguimientoSQLiteHelper.TABLE_ACCIONSEGUIMIENTO, null,
				values);
		Cursor cursor = database.query(AccionesSeguimientoSQLiteHelper.TABLE_ACCIONSEGUIMIENTO,
				allColumns, AccionesSQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		AccionSeguimiento newAccionDB = new AccionSeguimiento();
		newAccionDB.setId(cursor.getLong(0));
		Accion accion = new Accion();
		accion.setId(cursor.getLong(1));
		newAccionDB.setAccion(accion);
		cursor.close();
		return newAccionDB;
	}

	public void deleteAllAcciones() {
		database.delete(AccionesSeguimientoSQLiteHelper.TABLE_ACCIONSEGUIMIENTO, null, null);
	}
	
	public void deleteAccion(AccionSeguimiento accion) {
		long id = accion.getId();
		database.delete(AccionesSeguimientoSQLiteHelper.TABLE_ACCIONSEGUIMIENTO, AccionesSeguimientoSQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<AccionSeguimiento> getAccionesSeguimiento() {
		List<AccionSeguimiento> acciones = new ArrayList<AccionSeguimiento>();

		Cursor cursor = database.rawQuery("SELECT a._id, a.nombre, a.ticker, a.valor, a.varPercentage, a.fecha, aes._id from " + AccionesSQLiteHelper.TABLE_ACCION + " a, " + AccionesSeguimientoSQLiteHelper.TABLE_ACCIONSEGUIMIENTO + " aes WHERE a." + AccionesSQLiteHelper.COLUMN_ID + " = aes." + AccionesSeguimientoSQLiteHelper.COLUMN_ACCIONID, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			AccionSeguimiento accion = cursorToAccionSeguimiento(cursor);
			acciones.add(accion);
			cursor.moveToNext();
		}
		cursor.close();

		return acciones;
	}
	
	private AccionSeguimiento cursorToAccionSeguimiento(Cursor cursor) {
		AccionSeguimiento accionSeguimiento = new AccionSeguimiento();
		
		Accion accion = new Accion();
		accion.setId(cursor.getLong(0));
		accion.setNombre(cursor.getString(1));
		accion.setTicket(cursor.getString(2));
		accion.setValor(cursor.getString(3));
		accion.setVarPercentage(cursor.getString(4));
		accion.setFecha(cursor.getLong(5));
		
		accionSeguimiento.setId(cursor.getLong(6));
		accionSeguimiento.setAccion(accion);
		return accionSeguimiento;
	}
}
