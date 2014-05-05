package com.rolling.code.accionesargentina.db.entity;


public class AccionSeguimiento {

	private long id;
	private Accion accion;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Accion getAccion() {
		return accion;
	}
	public void setAccion(Accion accion) {
		this.accion = accion;
	}
}