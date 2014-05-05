package com.rolling.code.accionesargentina.utils;

import java.util.ArrayList;
import java.util.List;

public class AccionesBonosConstant {

	public static final Long MERVAL_INDICE_ID = 1L;
	public static final Long MERVAL25_INDICE_ID = 2L;
	public static final Long BONOS_NACIONALES_INDICE_ID = 3L;
	
	public static final int SPINNER_ACCIONESBONOS_ID = 15301;
	
	public static final List<Float> ACCIONES_COL_WIDTHS = new ArrayList<Float>();
	public static final List<Float> BONOS_COL_WIDTHS = new ArrayList<Float>();
	
	public static final List<String> ACCIONES_COL_NAMES = new ArrayList<String>();
	public static final List<String> BONOS_COL_NAMES = new ArrayList<String>();
	
	static {
		ACCIONES_COL_NAMES.add("Nombre");
		ACCIONES_COL_NAMES.add("Ticker");
		ACCIONES_COL_NAMES.add("Valor");
		ACCIONES_COL_NAMES.add("VAR%");
		
		ACCIONES_COL_WIDTHS.add(0.02f);
		ACCIONES_COL_WIDTHS.add(0.4f);
		ACCIONES_COL_WIDTHS.add(0.02f);
		ACCIONES_COL_WIDTHS.add(0.18f);
		ACCIONES_COL_WIDTHS.add(0.02f);
		ACCIONES_COL_WIDTHS.add(0.1f);
		ACCIONES_COL_WIDTHS.add(0.02f);
		ACCIONES_COL_WIDTHS.add(0.12f);
//		ACCIONES_COL_WIDTHS.add(0.02f);
//		ACCIONES_COL_WIDTHS.add(0.08f);
		ACCIONES_COL_WIDTHS.add(0.02f);

		BONOS_COL_NAMES.add("Nombre");
		BONOS_COL_NAMES.add("Ticker");
		BONOS_COL_NAMES.add("MON");
		BONOS_COL_NAMES.add("Valor");
		BONOS_COL_NAMES.add("VAR%");
		
		BONOS_COL_WIDTHS.add(0.02f);
		BONOS_COL_WIDTHS.add(0.3f);
		BONOS_COL_WIDTHS.add(0.01f);
		BONOS_COL_WIDTHS.add(0.16f);
		BONOS_COL_WIDTHS.add(0.01f);
		BONOS_COL_WIDTHS.add(0.1f);
		BONOS_COL_WIDTHS.add(0.02f);
		BONOS_COL_WIDTHS.add(0.14f);
		BONOS_COL_WIDTHS.add(0.02f);
		BONOS_COL_WIDTHS.add(0.12f);
//		BONOS_COL_WIDTHS.add(0.02f);
//		BONOS_COL_WIDTHS.add(0.08f);
		BONOS_COL_WIDTHS.add(0.02f);
		
	}
}
