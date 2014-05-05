package com.rolling.code.accionesargentina.db.helper.tables;

public class AccionesSeguimientoSQLiteHelper {

	public static final String TABLE_ACCIONSEGUIMIENTO = "accionseguimiento";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_ACCIONID = "accionid";
	
	public static final String FOREIGNKEY_COLUMNNAME = AccionesSQLiteHelper.COLUMN_ID;
	public static final String FOREIGNKEY_TABLENAME = AccionesSQLiteHelper.TABLE_ACCION;

	private static final String TABLE_CREATE = "create table "
			+ TABLE_ACCIONSEGUIMIENTO + "(" + COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_ACCIONID + " integer not null,"
			+ " FOREIGN KEY (" + COLUMN_ID + ") REFERENCES "+ FOREIGNKEY_TABLENAME + " (" + FOREIGNKEY_COLUMNNAME + "));";

	public String returnTableCreationStatement() {
		return TABLE_CREATE;
	}
}
