package com.abc.ceop.model.entities;

import java.sql.Date;

import javax.annotation.concurrent.Immutable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="F2QuienRespondeLaEncuesta", uniqueConstraints=@UniqueConstraint(columnNames={"id_trabajo","id_encuesta"}))
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Immutable
public class QuienRespondeLaEncuesta {

	@SuppressWarnings("unused")
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = true)
	private Integer id_Trabajo;
	@Column(nullable = true)
	private Integer id_encuesta;
	@Column(nullable = true)
	private String pr;

	@Column(columnDefinition = "VARCHAR(500)", nullable = true)
	private String estudio;

	@Column(columnDefinition = "VARCHAR(500)", nullable = true)
	private String division;
	@Column(nullable = true)
	private Integer id_entrevistado;
	@Column(nullable = true)
	private String pin;
	@Column(nullable = true)
	private String legajo;

	@Column(columnDefinition = "VARCHAR(500)", nullable = true)
	private String nombreCompleto;
	@Column(nullable = true)
	private String turno;
	@Column(nullable = true)
	private String modalidad;
	@Column(nullable = true)
	private Date fechaDeIngreso;
	@Column(nullable = true)
	private String fechaDeEgreso;
	@Column(nullable = true)
	private Integer id_telefonoUtilizado;

	@Column(columnDefinition = "VARCHAR(500)", nullable = true)
	private String telefono;
	@Column(nullable = true)
	private Integer id_prefijo;

	@Column(columnDefinition = "VARCHAR(100)", nullable = true)
	private String nombreDelPrefijo;

	@Column(columnDefinition = "VARCHAR(500)", nullable = true)
	private String nombreDeLaBase;
	@Column(nullable = true)
	private Date momentoDeProvision;
	@Column(nullable = true)
	private Integer momDia;
	@Column(nullable = true)
	private Integer momMes;
	@Column(nullable = true)
	private Integer momAno;
	@Column(nullable = true)
	private Integer momHora;
	@Column(nullable = true)
	private Integer momMinutos;
	@Column(nullable = true)
	private Integer momSegundos;
	@Column(nullable = true)
	private Date fecha;
	@Column(nullable = true)
	private Integer respDia;
	@Column(nullable = true)
	private Integer respMes;
	@Column(nullable = true)
	private Integer respAno;
	@Column(nullable = true)
	private Integer respHora;
	@Column(nullable = true)
	private Integer respMinutos;
	@Column(nullable = true)
	private Integer respSegundos;
	@Column(nullable = true)
	private Integer incidencia;

	@Column(columnDefinition = "VARCHAR(500)", nullable = true)
	private String textoDeLaIncidencia;
	@Column(nullable = true)
	private Integer incidenciaAgrupada;
	@Column(nullable = true)
	private Integer pregCuota1;
	@Column(nullable = true)
	private Integer pregCuota2;
	@Column(nullable = true)
	private Integer pregCuota3;
	@Column(nullable = true)
	private Integer pregCuota4;
	@Column(nullable = true)
	private Integer pregCuota5;
	@Column(nullable = true)
	private Integer respCuota1;
	@Column(nullable = true)
	private Integer respCuota2;
	@Column(nullable = true)
	private Integer respCuota3;
	@Column(nullable = true)
	private Integer respCuota4;
	@Column(nullable = true)
	private Integer respCuota5;
	@Column(nullable = true)
	private Integer diaDeLaSemana;
	@Column(nullable = true)
	private Integer horarioLaboral;
	@Column(nullable = true)
	private Integer encuestaCompleta;
	@Column(nullable = true)
	private float duracion;
	@Column(nullable = true)
	private float duracionEnHoras;

	@Column(columnDefinition = "bit", nullable = true)
	private boolean grabadoDesdeFinDeEncuesta;
	@Column(nullable = true)
	private String formatoDePresentacion;
	@Column(nullable = true)
	private String observaciones;

	public QuienRespondeLaEncuesta() {
	}

	public QuienRespondeLaEncuesta(int id_Trabajo, String pr, String estudio,
			String division, int id_encuesta, int id_entrevistado, String pin,
			String legajo, String nombreCompleto, String turno,
			String modalidad, Date fechaDeIngreso, String fechaDeEgreso,
			int id_telefonoUtilizado, String telefono, int id_prefijo,
			String nombreDelPrefijo, String nombreDeLaBase,
			Date momentoDeProvision, int momDia, int momMes, int momAno,
			int momHora, int momMinutos, int momSegundos, Date fecha,
			int respDia, int respMes, int respAno, int respHora,
			int respMinutos, int respSegundos, int incidencia,
			String textoDeLaIncidencia, int incidenciaAgrupada, int pregCuota1,
			int pregCuota2, int pregCuota3, int pregCuota4, int pregCuota5,
			int respCuota1, int respCuota2, int respCuota3, int respCuota4,
			int respCuota5, int diaDeLaSemana, int horarioLaboral,
			int encuestaCompleta, float duracion, float duracionEnHoras,
			boolean grabadoDesdeFinDeEncuesta, String formatoDePresentacion,
			String observaciones) {
		super();
		this.id_Trabajo = id_Trabajo;
		this.pr = pr;
		this.estudio = estudio;
		this.division = division;
		this.id_encuesta = id_encuesta;
		this.id_entrevistado = id_entrevistado;
		this.pin = pin;
		this.legajo = legajo;
		this.nombreCompleto = nombreCompleto;
		this.turno = turno;
		this.modalidad = modalidad;
		this.fechaDeIngreso = fechaDeIngreso;
		this.fechaDeEgreso = fechaDeEgreso;
		this.id_telefonoUtilizado = id_telefonoUtilizado;
		this.telefono = telefono;
		this.id_prefijo = id_prefijo;
		this.nombreDelPrefijo = nombreDelPrefijo;
		this.nombreDeLaBase = nombreDeLaBase;
		this.momentoDeProvision = momentoDeProvision;
		this.momDia = momDia;
		this.momMes = momMes;
		this.momAno = momAno;
		this.momHora = momHora;
		this.momMinutos = momMinutos;
		this.momSegundos = momSegundos;
		this.fecha = fecha;
		this.respDia = respDia;
		this.respMes = respMes;
		this.respAno = respAno;
		this.respHora = respHora;
		this.respMinutos = respMinutos;
		this.respSegundos = respSegundos;
		this.incidencia = incidencia;
		this.textoDeLaIncidencia = textoDeLaIncidencia;
		this.incidenciaAgrupada = incidenciaAgrupada;
		this.pregCuota1 = pregCuota1;
		this.pregCuota2 = pregCuota2;
		this.pregCuota3 = pregCuota3;
		this.pregCuota4 = pregCuota4;
		this.pregCuota5 = pregCuota5;
		this.respCuota1 = respCuota1;
		this.respCuota2 = respCuota2;
		this.respCuota3 = respCuota3;
		this.respCuota4 = respCuota4;
		this.respCuota5 = respCuota5;
		this.diaDeLaSemana = diaDeLaSemana;
		this.horarioLaboral = horarioLaboral;
		this.encuestaCompleta = encuestaCompleta;
		this.duracion = duracion;
		this.duracionEnHoras = duracionEnHoras;
		this.grabadoDesdeFinDeEncuesta = grabadoDesdeFinDeEncuesta;
		this.formatoDePresentacion = formatoDePresentacion;
		this.observaciones = observaciones;
	}

	public int getId_Trabajo() {
		return id_Trabajo;
	}

	public void setId_Trabajo(int id_Trabajo) {
		this.id_Trabajo = id_Trabajo;
	}

	public String getPr() {
		return pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
	}

	public String getEstudio() {
		return estudio;
	}

	public void setEstudio(String estudio) {
		this.estudio = estudio;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public int getId_encuesta() {
		return id_encuesta;
	}

	public void setId_encuesta(int id_encuesta) {
		this.id_encuesta = id_encuesta;
	}

	public int getId_entrevistado() {
		return id_entrevistado;
	}

	public void setId_entrevistado(int id_entrevistado) {
		this.id_entrevistado = id_entrevistado;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getLegajo() {
		return legajo;
	}

	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public Date getFechaDeIngreso() {
		return fechaDeIngreso;
	}

	public void setFechaDeIngreso(Date fechaDeIngreso) {
		this.fechaDeIngreso = fechaDeIngreso;
	}

	public String getFechaDeEgreso() {
		return fechaDeEgreso;
	}

	public void setFechaDeEgreso(String fechaDeEgreso) {
		this.fechaDeEgreso = fechaDeEgreso;
	}

	public int getId_telefonoUtilizado() {
		return id_telefonoUtilizado;
	}

	public void setId_telefonoUtilizado(int id_telefonoUtilizado) {
		this.id_telefonoUtilizado = id_telefonoUtilizado;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getId_prefijo() {
		return id_prefijo;
	}

	public void setId_prefijo(int id_prefijo) {
		this.id_prefijo = id_prefijo;
	}

	public String getNombreDelPrefijo() {
		return nombreDelPrefijo;
	}

	public void setNombreDelPrefijo(String nombreDelPrefijo) {
		this.nombreDelPrefijo = nombreDelPrefijo;
	}

	public String getNombreDeLaBase() {
		return nombreDeLaBase;
	}

	public void setNombreDeLaBase(String nombreDeLaBase) {
		this.nombreDeLaBase = nombreDeLaBase;
	}

	public Date getMomentoDeProvision() {
		return momentoDeProvision;
	}

	public void setMomentoDeProvision(Date momentoDeProvision) {
		this.momentoDeProvision = momentoDeProvision;
	}

	public int getMomDia() {
		return momDia;
	}

	public void setMomDia(int momDia) {
		this.momDia = momDia;
	}

	public int getMomMes() {
		return momMes;
	}

	public void setMomMes(int momMes) {
		this.momMes = momMes;
	}

	public int getMomAno() {
		return momAno;
	}

	public void setMomAno(int momAno) {
		this.momAno = momAno;
	}

	public int getMomHora() {
		return momHora;
	}

	public void setMomHora(int momHora) {
		this.momHora = momHora;
	}

	public int getMomMinutos() {
		return momMinutos;
	}

	public void setMomMinutos(int momMinutos) {
		this.momMinutos = momMinutos;
	}

	public int getMomSegundos() {
		return momSegundos;
	}

	public void setMomSegundos(int momSegundos) {
		this.momSegundos = momSegundos;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getRespDia() {
		return respDia;
	}

	public void setRespDia(int respDia) {
		this.respDia = respDia;
	}

	public int getRespMes() {
		return respMes;
	}

	public void setRespMes(int respMes) {
		this.respMes = respMes;
	}

	public int getRespAno() {
		return respAno;
	}

	public void setRespAno(int respAno) {
		this.respAno = respAno;
	}

	public int getRespHora() {
		return respHora;
	}

	public void setRespHora(int respHora) {
		this.respHora = respHora;
	}

	public int getRespMinutos() {
		return respMinutos;
	}

	public void setRespMinutos(int respMinutos) {
		this.respMinutos = respMinutos;
	}

	public int getRespSegundos() {
		return respSegundos;
	}

	public void setRespSegundos(int respSegundos) {
		this.respSegundos = respSegundos;
	}

	public int getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(int incidencia) {
		this.incidencia = incidencia;
	}

	public String getTextoDeLaIncidencia() {
		return textoDeLaIncidencia;
	}

	public void setTextoDeLaIncidencia(String textoDeLaIncidencia) {
		this.textoDeLaIncidencia = textoDeLaIncidencia;
	}

	public int getIncidenciaAgrupada() {
		return incidenciaAgrupada;
	}

	public void setIncidenciaAgrupada(int incidenciaAgrupada) {
		this.incidenciaAgrupada = incidenciaAgrupada;
	}

	public int getPregCuota1() {
		return pregCuota1;
	}

	public void setPregCuota1(int pregCuota1) {
		this.pregCuota1 = pregCuota1;
	}

	public int getPregCuota2() {
		return pregCuota2;
	}

	public void setPregCuota2(int pregCuota2) {
		this.pregCuota2 = pregCuota2;
	}

	public int getPregCuota3() {
		return pregCuota3;
	}

	public void setPregCuota3(int pregCuota3) {
		this.pregCuota3 = pregCuota3;
	}

	public int getPregCuota4() {
		return pregCuota4;
	}

	public void setPregCuota4(int pregCuota4) {
		this.pregCuota4 = pregCuota4;
	}

	public int getPregCuota5() {
		return pregCuota5;
	}

	public void setPregCuota5(int pregCuota5) {
		this.pregCuota5 = pregCuota5;
	}

	public int getRespCuota1() {
		return respCuota1;
	}

	public void setRespCuota1(int respCuota1) {
		this.respCuota1 = respCuota1;
	}

	public int getRespCuota2() {
		return respCuota2;
	}

	public void setRespCuota2(int respCuota2) {
		this.respCuota2 = respCuota2;
	}

	public int getRespCuota3() {
		return respCuota3;
	}

	public void setRespCuota3(int respCuota3) {
		this.respCuota3 = respCuota3;
	}

	public int getRespCuota4() {
		return respCuota4;
	}

	public void setRespCuota4(int respCuota4) {
		this.respCuota4 = respCuota4;
	}

	public int getRespCuota5() {
		return respCuota5;
	}

	public void setRespCuota5(int respCuota5) {
		this.respCuota5 = respCuota5;
	}

	public int getDiaDeLaSemana() {
		return diaDeLaSemana;
	}

	public void setDiaDeLaSemana(Integer diaDeLaSemana) {
		this.diaDeLaSemana = diaDeLaSemana;
	}

	public int getHorarioLaboral() {
		return horarioLaboral;
	}

	public void setHorarioLaboral(int horarioLaboral) {
		this.horarioLaboral = horarioLaboral;
	}

	public int getEncuestaCompleta() {
		return encuestaCompleta;
	}

	public void setEncuestaCompleta(int encuestaCompleta) {
		this.encuestaCompleta = encuestaCompleta;
	}

	public float getDuracion() {
		return duracion;
	}

	public void setDuracion(float duracion) {
		this.duracion = duracion;
	}

	public float getDuracionEnHoras() {
		return duracionEnHoras;
	}

	public void setDuracionEnHoras(float duracionEnHoras) {
		this.duracionEnHoras = duracionEnHoras;
	}

	public boolean isGrabadoDesdeFinDeEncuesta() {
		return grabadoDesdeFinDeEncuesta;
	}

	public void setGrabadoDesdeFinDeEncuesta(boolean grabadoDesdeFinDeEncuesta) {
		this.grabadoDesdeFinDeEncuesta = grabadoDesdeFinDeEncuesta;
	}

	public String getFormatoDePresentacion() {
		return formatoDePresentacion;
	}

	public void setFormatoDePresentacion(String formatoDePresentacion) {
		this.formatoDePresentacion = formatoDePresentacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

}
