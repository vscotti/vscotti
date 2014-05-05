package com.uade.tesis.domains;

import java.util.Date;
import java.util.List;

public class Posting {

	private Long id;
	private Long precio;
	
	private Date fechaPosting;
	private Date fechafinalizacion;
	
	private String descripcion;
	private String titulo;
	
	private boolean htmlDescrition;
	
	private int cantConsultas;
	private int cantVentas;
	private int cantProductos;
	private int tipoProducto;
	
	private List<String> fileImages;
	
	private Usuario dueno;
	
	private Categoria categoria;
	
	public Posting() {
	}
	
	public Posting(Long id, 
			String titulo,
			Long precio,
			Categoria categoria, 
			List<String> fileImages) {
		this.id = id;
		this.titulo = titulo;
		this.precio = precio;
		this.categoria = categoria;
		this.fileImages = fileImages;
	}
	
	public String getImagenPrincipal() {
		if(fileImages != null &&
				fileImages.size() > 0) {
			return fileImages.get(0);
		}
		return null;
	}
	public int getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(int tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	public int getCantProductos() {
		return cantProductos;
	}
	public void setCantProductos(int cantProductos) {
		this.cantProductos = cantProductos;
	}
	public boolean isHtmlDescrition() {
		return htmlDescrition;
	}
	public void setHtmlDescrition(boolean htmlDescrition) {
		this.htmlDescrition = htmlDescrition;
	}
	public int getCantVentas() {
		return cantVentas;
	}
	public void setCantVentas(int cantVentas) {
		this.cantVentas = cantVentas;
	}
	public Date getFechafinalizacion() {
		return fechafinalizacion;
	}
	public void setFechafinalizacion(Date fechafinalizacion) {
		this.fechafinalizacion = fechafinalizacion;
	}
	public List<String> getFileImages() {
		return fileImages;
	}
	public void setFileImages(List<String> fileImages) {
		this.fileImages = fileImages;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFechaPosting() {
		return fechaPosting;
	}
	public void setFechaPosting(Date fechaPosting) {
		this.fechaPosting = fechaPosting;
	}
	public Usuario getDueno() {
		return dueno;
	}
	public void setDueno(Usuario dueno) {
		this.dueno = dueno;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public Long getPrecio() {
		return precio;
	}
	public void setPrecio(Long precio) {
		this.precio = precio;
	}
	public int getCantConsultas() {
		return cantConsultas;
	}
	public void setCantConsultas(int cantConsultas) {
		this.cantConsultas = cantConsultas;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
