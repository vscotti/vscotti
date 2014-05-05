package com.abc.ceop.model.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class OutputRepresentation {
	
	private String idTrabajo;
	private String idEncuesta;
	private String idPregunta;
	private String nsubresp;
	private String idOpcion;
	private String valor;
	private List<String> ponds = new ArrayList<String>();

	public String getIdTrabajo() {
		return idTrabajo;
	}

	public void setIdTrabajo(String idTrabajo) {
		this.idTrabajo = idTrabajo;
	}

	public String getIdEncuesta() {
		return idEncuesta;
	}

	public void setIdEncuesta(String idEncuesta) {
		this.idEncuesta = idEncuesta;
	}

	public String getIdPregunta() {
		return idPregunta;
	}

	public void setIdPregunta(String idPregunta) {
		this.idPregunta = idPregunta;
	}

	public String getNsubresp() {
		return nsubresp;
	}

	public void setNsubresp(String nsubresp) {
		this.nsubresp = nsubresp;
	}

	public String getIdOpcion() {
		return idOpcion;
	}

	public void setIdOpcion(String idOpcion) {
		this.idOpcion = idOpcion;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public List<String> getPonds() {
		return ponds;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
