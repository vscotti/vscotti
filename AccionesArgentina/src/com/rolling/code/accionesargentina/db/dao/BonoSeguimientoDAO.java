package com.rolling.code.accionesargentina.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.rolling.code.accionesargentina.db.entity.Bono;
import com.rolling.code.accionesargentina.db.entity.BonoSeguimiento;
import com.rolling.code.accionesargentina.db.helper.AccionesArgentinasDBSQLiteHelper;
import com.rolling.code.accionesargentina.db.helper.tables.BonosSQLiteHelper;
import com.rolling.code.accionesargentina.db.helper.tables.BonosSeguimientoSQLiteHelper;

public class BonoSeguimientoDAO {

	private SQLiteDatabase database;
	private AccionesArgentinasDBSQLiteHelper dbHelper;
	private String[] allColumns = { BonosSeguimientoSQLiteHelper.COLUMN_ID,
									BonosSeguimientoSQLiteHelper.COLUMN_BONOID };

	public BonoSeguimientoDAO(Context context) {
		dbHelper = new AccionesArgentinasDBSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public BonoSeguimiento addBonoSeguimiento(BonoSeguimiento newBono) {
		ContentValues values = new ContentValues();
		values.put(BonosSeguimientoSQLiteHelper.COLUMN_BONOID, newBono.getBono().getId());
		long insertId = database.insert(BonosSeguimientoSQLiteHelper.TABLE_BONOSEGUIMIENTO, null,
				values);
		Cursor cursor = database.query(BonosSeguimientoSQLiteHelper.TABLE_BONOSEGUIMIENTO,
				allColumns, BonosSQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		BonoSeguimiento newBonoDB = new BonoSeguimiento();
		newBonoDB.setId(cursor.getLong(0));
		Bono bono = new Bono();
		bono.setId(cursor.getLong(1));
		newBonoDB.setBono(bono);
		cursor.close();
		return newBonoDB;
	}

	public void deleteAllBonos() {
		database.delete(BonosSeguimientoSQLiteHelper.TABLE_BONOSEGUIMIENTO, null, null);
	}
	
	public void deleteBono(BonoSeguimiento accion) {
		long id = accion.getId();
		database.delete(BonosSeguimientoSQLiteHelper.TABLE_BONOSEGUIMIENTO, BonosSeguimientoSQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<BonoSeguimiento> getBonosSeguimiento() {
		List<BonoSeguimiento> acciones = new ArrayList<BonoSeguimiento>();

		Cursor cursor = database.rawQuery("SELECT a._id, a.nombre, a.ticker, a.moneda, a.valor, a.varPercentage, a.fecha, aes._id from " + BonosSQLiteHelper.TABLE_BONO + " a, " + BonosSeguimientoSQLiteHelper.TABLE_BONOSEGUIMIENTO + " aes WHERE a." + BonosSQLiteHelper.COLUMN_ID + " = aes." + BonosSeguimientoSQLiteHelper.COLUMN_BONOID, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			BonoSeguimiento accion = cursorToBonoSeguimiento(cursor);
			acciones.add(accion);
			cursor.moveToNext();
		}
		cursor.close();

		return acciones;
	}
	
	private BonoSeguimiento cursorToBonoSeguimiento(Cursor cursor) {
		BonoSeguimiento bonoSeguimiento = new BonoSeguimiento();
		Bono bono = new Bono();
		bono.setId(cursor.getLong(0));
		bono.setNombre(cursor.getString(1));
		bono.setTicket(cursor.getString(2));
		bono.setMoneda(cursor.getString(3));
		bono.setValor(cursor.getString(4));
		bono.setVarPercentage(cursor.getString(5));
		bono.setFecha(cursor.getLong(6));
		
		bonoSeguimiento.setId(cursor.getLong(7));
		bonoSeguimiento.setBono(bono);
		
		return bonoSeguimiento;
	}
}
