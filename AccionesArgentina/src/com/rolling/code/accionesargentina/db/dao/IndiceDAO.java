package com.rolling.code.accionesargentina.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.rolling.code.accionesargentina.db.entity.Indice;
import com.rolling.code.accionesargentina.db.helper.AccionesArgentinasDBSQLiteHelper;
import com.rolling.code.accionesargentina.db.helper.tables.IndiceSQLiteHelper;

public class IndiceDAO {

	private SQLiteDatabase database;
	private AccionesArgentinasDBSQLiteHelper dbHelper;
	private String[] allColumns = { IndiceSQLiteHelper.COLUMN_ID,
									IndiceSQLiteHelper.COLUMN_NOMBRE,
									IndiceSQLiteHelper.COLUMN_TIPO };

	public IndiceDAO(Context context) {
		dbHelper = new AccionesArgentinasDBSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public List<Indice> getIndicesByTipo(String tipo) {
		List<Indice> indices = new ArrayList<Indice>();

		Cursor cursor = database.query(IndiceSQLiteHelper.TABLE_INDICE,
				allColumns, IndiceSQLiteHelper.COLUMN_TIPO + " = '" + tipo + "'", null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Indice indice = cursorToIndice(cursor);
			indices.add(indice);
			cursor.moveToNext();
		}
		cursor.close();
		return indices;
	}

	private Indice cursorToIndice(Cursor cursor) {
		Indice indice = new Indice();
		indice.setId(cursor.getLong(0));
		indice.setNombre(cursor.getString(1));
		indice.setTipo(cursor.getString(2));
		return indice;
	}
}
