package com.rolling.code.accionesargentina.db.entity;


public class Accion {

	private long id;
	private String nombre;
	private String ticket;
	private String valor;
	private String varPercentage;
	private long indiceid;
	private Long fecha;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getVarPercentage() {
		return varPercentage;
	}
	public void setVarPercentage(String varPercentage) {
		this.varPercentage = varPercentage;
	}
	public Long getFecha() {
		return fecha;
	}
	public void setFecha(Long fecha) {
		this.fecha = fecha;
	}
	public long getIndiceid() {
		return indiceid;
	}
	public void setIndiceid(long indiceid) {
		this.indiceid = indiceid;
	}

	@Override
	public boolean equals(Object o) {
		return getId() == ((Accion)o).getId();
	}
}
