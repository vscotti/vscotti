package com.rolling.code.accionesargentina.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.rolling.code.accionesargentina.db.entity.Accion;
import com.rolling.code.accionesargentina.db.entity.Indice;
import com.rolling.code.accionesargentina.db.helper.AccionesArgentinasDBSQLiteHelper;
import com.rolling.code.accionesargentina.db.helper.tables.AccionesSQLiteHelper;

public class AccionDAO {

	private SQLiteDatabase database;
	private AccionesArgentinasDBSQLiteHelper dbHelper;
	private String[] allColumns = { AccionesSQLiteHelper.COLUMN_ID,
									AccionesSQLiteHelper.COLUMN_NOMBRE,
									AccionesSQLiteHelper.COLUMN_TICKER,
									AccionesSQLiteHelper.COLUMN_VALOR,
									AccionesSQLiteHelper.COLUMN_VAR,
									AccionesSQLiteHelper.COLUMN_FECHA,
									AccionesSQLiteHelper.COLUMN_INDICEID };

	public AccionDAO(Context context) {
		dbHelper = new AccionesArgentinasDBSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Accion addAccion(Accion newAccion) {
		ContentValues values = new ContentValues();
		values.put(AccionesSQLiteHelper.COLUMN_NOMBRE, newAccion.getNombre());
		values.put(AccionesSQLiteHelper.COLUMN_TICKER, newAccion.getTicket());
		values.put(AccionesSQLiteHelper.COLUMN_VALOR, newAccion.getValor());
		values.put(AccionesSQLiteHelper.COLUMN_VAR, newAccion.getVarPercentage());
		values.put(AccionesSQLiteHelper.COLUMN_FECHA, newAccion.getFecha());
		values.put(AccionesSQLiteHelper.COLUMN_INDICEID, newAccion.getIndiceid());
		long insertId = database.insert(AccionesSQLiteHelper.TABLE_ACCION, null,
				values);
		Cursor cursor = database.query(AccionesSQLiteHelper.TABLE_ACCION,
				allColumns, AccionesSQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Accion newAccionDB = cursorToAccion(cursor);
		cursor.close();
		return newAccionDB;
	}

	public Accion updateAccion(Accion newAccion) {
		ContentValues values = new ContentValues();
		values.put(AccionesSQLiteHelper.COLUMN_NOMBRE, newAccion.getNombre());
		values.put(AccionesSQLiteHelper.COLUMN_TICKER, newAccion.getTicket());
		values.put(AccionesSQLiteHelper.COLUMN_VALOR, newAccion.getValor());
		values.put(AccionesSQLiteHelper.COLUMN_VAR, newAccion.getVarPercentage());
		values.put(AccionesSQLiteHelper.COLUMN_FECHA, newAccion.getFecha());
		values.put(AccionesSQLiteHelper.COLUMN_INDICEID, newAccion.getIndiceid());
		database.update(AccionesSQLiteHelper.TABLE_ACCION, values, AccionesSQLiteHelper.COLUMN_ID + " = " + newAccion.getId(), null);
		Cursor cursor = database.query(AccionesSQLiteHelper.TABLE_ACCION,
				allColumns, AccionesSQLiteHelper.COLUMN_ID + " = " + newAccion.getId(), null,
				null, null, null);
		cursor.moveToFirst();
		Accion newAccionDB = cursorToAccion(cursor);
		cursor.close();
		return newAccionDB;
	}

	public void deleteAllAccionesByInidice(Indice indice) {
		database.delete(AccionesSQLiteHelper.TABLE_ACCION, AccionesSQLiteHelper.COLUMN_INDICEID + " = " + indice.getId(), null);
	}

	public void deleteAccionesByDistinctDate(Long date) {
		database.delete(AccionesSQLiteHelper.TABLE_ACCION, AccionesSQLiteHelper.COLUMN_FECHA + " <> " + date, null);
	}
	
	public void deleteAccion(Accion accion) {
		long id = accion.getId();
		database.delete(AccionesSQLiteHelper.TABLE_ACCION, AccionesSQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public Accion getAccionByInidiceTicker(Indice indice, String ticker) {
		Cursor cursor = database.query(AccionesSQLiteHelper.TABLE_ACCION,
				allColumns, AccionesSQLiteHelper.COLUMN_INDICEID + " = " + indice.getId() + " and " + AccionesSQLiteHelper.COLUMN_TICKER + " = '" + ticker + "'", null,
				null, null, null);
		cursor.moveToFirst();
		Accion newAccionDB = cursorToAccion(cursor);
		cursor.close();
		return newAccionDB;
	}
	
	public List<Accion> getAllAccionesByInidice(Indice indice) {
		List<Accion> acciones = new ArrayList<Accion>();

		Cursor cursor = database.query(AccionesSQLiteHelper.TABLE_ACCION,
				allColumns, AccionesSQLiteHelper.COLUMN_INDICEID + " = " + indice.getId(), null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Accion accion = cursorToAccion(cursor);
			acciones.add(accion);
			cursor.moveToNext();
		}
		cursor.close();
		return acciones;
	}

	private Accion cursorToAccion(Cursor cursor) {
		Accion accion = new Accion();
		accion.setId(cursor.getLong(0));
		accion.setNombre(cursor.getString(1));
		accion.setTicket(cursor.getString(2));
		accion.setValor(cursor.getString(3));
		accion.setVarPercentage(cursor.getString(4));
		accion.setFecha(cursor.getLong(5));
		accion.setIndiceid(cursor.getLong(6));
		return accion;
	}
}
