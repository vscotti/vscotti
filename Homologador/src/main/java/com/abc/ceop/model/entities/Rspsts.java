package com.abc.ceop.model.entities;

import java.sql.Date;

import javax.annotation.concurrent.Immutable;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name="F2Rspsts", uniqueConstraints=@UniqueConstraint(columnNames={"id_trabajo", "numeroDeEncuesta", "id_pregunta", "nsubresp", "valor"}))
@Cacheable
@Cache (usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Immutable


public class Rspsts {
	
	@Id
	@GeneratedValue
	private Long id;

	private int id_Trabajo;
	private int numeroDeEncuesta;
	private int id_pregunta;
	private int nsubresp;
	private int valor;

	private int id_opcion;
	private Date fecha;
	
	public Rspsts() {}

	public Rspsts(int id_Trabajo, int numeroDeEncuesta, int id_pregunta,
			int id_opcion, int nsubresp, int valor, Date fecha) {
		super();
		this.id_Trabajo = id_Trabajo;
		this.numeroDeEncuesta = numeroDeEncuesta;
		this.id_pregunta = id_pregunta;
		this.id_opcion = id_opcion;
		this.nsubresp = nsubresp;
		this.valor = valor;
		this.fecha = fecha;
	}

	public int getId_Trabajo() {
		return id_Trabajo;
	}

	public void setId_Trabajo(int id_Trabajo) {
		this.id_Trabajo = id_Trabajo;
	}

	public int getNumeroDeEncuesta() {
		return numeroDeEncuesta;
	}

	public void setNumeroDeEncuesta(int numeroDeEncuesta) {
		this.numeroDeEncuesta = numeroDeEncuesta;
	}

	public int getId_pregunta() {
		return id_pregunta;
	}

	public void setId_pregunta(int id_pregunta) {
		this.id_pregunta = id_pregunta;
	}

	public int getId_opcion() {
		return id_opcion;
	}

	public void setId_opcion(int id_opcion) {
		this.id_opcion = id_opcion;
	}

	public int getNsubresp() {
		return nsubresp;
	}

	public void setNsubresp(int nsubresp) {
		this.nsubresp = nsubresp;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
	
	

}
