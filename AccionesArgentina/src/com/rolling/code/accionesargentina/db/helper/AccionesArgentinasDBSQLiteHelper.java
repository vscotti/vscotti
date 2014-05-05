package com.rolling.code.accionesargentina.db.helper;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rolling.code.accionesargentina.db.entity.Indice;
import com.rolling.code.accionesargentina.db.helper.tables.AccionesSQLiteHelper;
import com.rolling.code.accionesargentina.db.helper.tables.AccionesSeguimientoSQLiteHelper;
import com.rolling.code.accionesargentina.db.helper.tables.BonosSQLiteHelper;
import com.rolling.code.accionesargentina.db.helper.tables.BonosSeguimientoSQLiteHelper;
import com.rolling.code.accionesargentina.db.helper.tables.IndiceSQLiteHelper;

public class AccionesArgentinasDBSQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_ACCIONSEGUIMIENTO = "accionseguimiento";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_ACCIONID = "accionid";

	private static final String DATABASE_NAME = "accion.db";
	private static final int DATABASE_VERSION = 1;

	private AccionesSQLiteHelper accionesHelper;
	private BonosSQLiteHelper bonosHelper;
	private AccionesSeguimientoSQLiteHelper accionesSeguimientoHelper;
	private BonosSeguimientoSQLiteHelper bonosSeguimientoHelper;
	private IndiceSQLiteHelper indiceSQLiteHelper;
	
	public AccionesArgentinasDBSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		accionesHelper = new AccionesSQLiteHelper();
		bonosHelper = new BonosSQLiteHelper();
		accionesSeguimientoHelper = new AccionesSeguimientoSQLiteHelper();
		bonosSeguimientoHelper = new BonosSeguimientoSQLiteHelper();
		indiceSQLiteHelper = new IndiceSQLiteHelper();
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(accionesHelper.returnTableCreationStatement());
		database.execSQL(bonosHelper.returnTableCreationStatement());
		database.execSQL(accionesSeguimientoHelper.returnTableCreationStatement());
		database.execSQL(bonosSeguimientoHelper.returnTableCreationStatement());
		database.execSQL(indiceSQLiteHelper.returnTableCreationStatement());
		
		List<Indice> list = indiceSQLiteHelper.initialData();
		for (Indice indice : list) {
			ContentValues values = new ContentValues();
			values.put(IndiceSQLiteHelper.COLUMN_ID, indice.getId());
			values.put(IndiceSQLiteHelper.COLUMN_NOMBRE, indice.getNombre());
			values.put(IndiceSQLiteHelper.COLUMN_TIPO, indice.getTipo());
			database.insert(IndiceSQLiteHelper.TABLE_INDICE, null, values);
		}
		
		Log.i(this.getClass().getSimpleName(), "onCreate");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		Log.i(this.getClass().getSimpleName(), "onUpgrade");
	}
}
