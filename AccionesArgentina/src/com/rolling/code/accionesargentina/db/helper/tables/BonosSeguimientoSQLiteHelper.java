package com.rolling.code.accionesargentina.db.helper.tables;

public class BonosSeguimientoSQLiteHelper {

	public static final String TABLE_BONOSEGUIMIENTO = "bonoseguimiento";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_BONOID = "bonoid";
	
	public static final String FOREIGNKEY_COLUMNNAME = BonosSQLiteHelper.COLUMN_ID;
	public static final String FOREIGNKEY_TABLENAME = BonosSQLiteHelper.TABLE_BONO;

	private static final String TABLE_CREATE = "create table "
			+ TABLE_BONOSEGUIMIENTO + "(" + COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_BONOID + " integer not null,"
			+ " FOREIGN KEY (" + COLUMN_ID + ") REFERENCES "+ FOREIGNKEY_TABLENAME + " (" + FOREIGNKEY_COLUMNNAME + "));";

	public String returnTableCreationStatement() {
		return TABLE_CREATE;
	}
}
