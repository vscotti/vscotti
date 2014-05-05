package com.rolling.code.accionesargentina.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.rolling.code.accionesargentina.db.entity.Bono;
import com.rolling.code.accionesargentina.db.entity.Indice;
import com.rolling.code.accionesargentina.db.helper.AccionesArgentinasDBSQLiteHelper;
import com.rolling.code.accionesargentina.db.helper.tables.BonosSQLiteHelper;

public class BonoDAO {

	private SQLiteDatabase database;
	private AccionesArgentinasDBSQLiteHelper dbHelper;
	private String[] allColumns = { BonosSQLiteHelper.COLUMN_ID,
									BonosSQLiteHelper.COLUMN_NOMBRE,
									BonosSQLiteHelper.COLUMN_TICKER,
									BonosSQLiteHelper.COLUMN_MONEDA,
									BonosSQLiteHelper.COLUMN_VALOR,
									BonosSQLiteHelper.COLUMN_VAR,
									BonosSQLiteHelper.COLUMN_FECHA,
									BonosSQLiteHelper.COLUMN_INDICEID };

	public BonoDAO(Context context) {
		dbHelper = new AccionesArgentinasDBSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Bono addBono(Bono newBono) {
		ContentValues values = new ContentValues();
		values.put(BonosSQLiteHelper.COLUMN_NOMBRE, newBono.getNombre());
		values.put(BonosSQLiteHelper.COLUMN_TICKER, newBono.getTicket());
		values.put(BonosSQLiteHelper.COLUMN_MONEDA, newBono.getMoneda());
		values.put(BonosSQLiteHelper.COLUMN_VALOR, newBono.getValor());
		values.put(BonosSQLiteHelper.COLUMN_VAR, newBono.getVarPercentage());
		values.put(BonosSQLiteHelper.COLUMN_FECHA, newBono.getFecha());
		values.put(BonosSQLiteHelper.COLUMN_INDICEID, newBono.getIndiceid());
		long insertId = database.insert(BonosSQLiteHelper.TABLE_BONO, null,
				values);
		Cursor cursor = database.query(BonosSQLiteHelper.TABLE_BONO,
				allColumns, BonosSQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Bono newBonoDB = cursorToBono(cursor);
		cursor.close();
		return newBonoDB;
	}

	public Bono updateBono(Bono newBono) {
		ContentValues values = new ContentValues();
		values.put(BonosSQLiteHelper.COLUMN_NOMBRE, newBono.getNombre());
		values.put(BonosSQLiteHelper.COLUMN_TICKER, newBono.getTicket());
		values.put(BonosSQLiteHelper.COLUMN_MONEDA, newBono.getTicket());
		values.put(BonosSQLiteHelper.COLUMN_VALOR, newBono.getValor());
		values.put(BonosSQLiteHelper.COLUMN_VAR, newBono.getVarPercentage());
		values.put(BonosSQLiteHelper.COLUMN_FECHA, newBono.getFecha());
		values.put(BonosSQLiteHelper.COLUMN_INDICEID, newBono.getIndiceid());
		database.update(BonosSQLiteHelper.TABLE_BONO, values, BonosSQLiteHelper.COLUMN_ID + " = " + newBono.getId(), null);
		Cursor cursor = database.query(BonosSQLiteHelper.TABLE_BONO,
				allColumns, BonosSQLiteHelper.COLUMN_ID + " = " + newBono.getId(), null,
				null, null, null);
		cursor.moveToFirst();
		Bono newBonoDB = cursorToBono(cursor);
		cursor.close();
		return newBonoDB;
	}

	public void deleteAllBonosByInidice(Indice indice) {
		database.delete(BonosSQLiteHelper.TABLE_BONO, BonosSQLiteHelper.COLUMN_INDICEID + " = " + indice.getId(), null);
	}
	

	public void deleteBonosByDistinctDate(Long date) {
		database.delete(BonosSQLiteHelper.TABLE_BONO, BonosSQLiteHelper.COLUMN_FECHA + " <> " + date, null);
	}
	
	public void deleteBono(Bono bono) {
		long id = bono.getId();
		database.delete(BonosSQLiteHelper.TABLE_BONO, BonosSQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public Bono getBonoByInidiceTicker(Indice indice, String ticker) {
		Cursor cursor = database.query(BonosSQLiteHelper.TABLE_BONO,
				allColumns, BonosSQLiteHelper.COLUMN_INDICEID + " = " + indice.getId() + " and " + BonosSQLiteHelper.COLUMN_TICKER + " = '" + ticker + "'", null,
				null, null, null);
		cursor.moveToFirst();
		Bono bonoDB = cursorToBono(cursor);
		cursor.close();
		return bonoDB;
	}
	
	public List<Bono> getAllBonosByInidice(Indice indice) {
		List<Bono> bonos = new ArrayList<Bono>();

		Cursor cursor = database.query(BonosSQLiteHelper.TABLE_BONO,
				allColumns, BonosSQLiteHelper.COLUMN_INDICEID + " = " + indice.getId(), null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Bono bono = cursorToBono(cursor);
			bonos.add(bono);
			cursor.moveToNext();
		}
		cursor.close();
		return bonos;
	}

	private Bono cursorToBono(Cursor cursor) {
		Bono bono = new Bono();
		bono.setId(cursor.getLong(0));
		bono.setNombre(cursor.getString(1));
		bono.setTicket(cursor.getString(2));
		bono.setMoneda(cursor.getString(3));
		bono.setValor(cursor.getString(4));
		bono.setVarPercentage(cursor.getString(5));
		bono.setFecha(cursor.getLong(6));
		bono.setIndiceid(cursor.getLong(7));
		return bono;
	}
}
