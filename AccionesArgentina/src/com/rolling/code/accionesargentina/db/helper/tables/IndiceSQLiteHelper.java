package com.rolling.code.accionesargentina.db.helper.tables;

import java.util.ArrayList;
import java.util.List;

import com.rolling.code.accionesargentina.db.entity.Indice;

public class IndiceSQLiteHelper {

	public static final String TABLE_INDICE = "indice";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NOMBRE = "nombre";
	public static final String COLUMN_TIPO = "tipo";

	private static final String TABLE_CREATE = "create table "
			+ TABLE_INDICE + "(" + COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_NOMBRE + " text not null,"
			+ COLUMN_TIPO + " text not null);";

	public String returnTableCreationStatement() {
		return TABLE_CREATE;
	}
	
	public List<Indice> initialData() {
		List<Indice> list = new ArrayList<Indice>();
		Indice i = new Indice();
		i.setId(1);
		i.setNombre("Merval");
		i.setTipo("ACCION");
		list.add(i);
		i = new Indice();
		i.setId(2);
		i.setNombre("Merval 25");
		i.setTipo("ACCION");
		list.add(i);
		i = new Indice();
		i.setId(3);
		i.setNombre("Bonos Nacionales");
		i.setTipo("BONO");
		list.add(i);
		return list;
	}
}
