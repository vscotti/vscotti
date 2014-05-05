package com.rolling.code.accionesargentina.db.helper.tables;

public class AccionesSQLiteHelper {

	public static final String TABLE_ACCION = "accion";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NOMBRE = "nombre";
	public static final String COLUMN_TICKER = "ticker";
	public static final String COLUMN_VALOR = "valor";
	public static final String COLUMN_VAR = "varPercentage";
	public static final String COLUMN_FECHA = "fecha";
	public static final String COLUMN_INDICEID = "indiceid";

	public static final String FOREIGNKEY_COLUMNNAME = IndiceSQLiteHelper.COLUMN_ID;
	public static final String FOREIGNKEY_TABLENAME = IndiceSQLiteHelper.TABLE_INDICE;

	private static final String TABLE_CREATE = "create table "
			+ TABLE_ACCION + "(" + COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_NOMBRE + " text not null,"
			+ COLUMN_TICKER + " text not null,"
			+ COLUMN_VALOR + " text not null,"
			+ COLUMN_VAR + " text not null,"
			+ COLUMN_FECHA + " integer not null,"
			+ COLUMN_INDICEID + " integer not null,"
			+ " FOREIGN KEY (" + COLUMN_INDICEID + ") REFERENCES "+ FOREIGNKEY_TABLENAME + " (" + FOREIGNKEY_COLUMNNAME + "));";

	public String returnTableCreationStatement() {
		return TABLE_CREATE;
	}
}
