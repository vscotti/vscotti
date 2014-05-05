package com.uade.tesis.web.formbeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.uade.tesis.domains.Usuario;

public class PostingFormBean {

	private String titulo;
	private String descripcion;
	private String categoriaDesc;

	private Long precio;
	private Long categoria;

	private int consultas;
	private int ventas;
	private int cantProductos;
	private int tipoProducto;
	
	private boolean formatoDescripcion;
	
	private Date creacion;
	private Date finalizacion;
	
	private Usuario usuarioLogeado;
	
	private List<String> imagesList;
	
	public PostingFormBean() {
		imagesList = new ArrayList<String>();
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
	public boolean isFormatoDescripcion() {
		return formatoDescripcion;
	}
	public void setFormatoDescripcion(boolean formatoDescripcion) {
		this.formatoDescripcion = formatoDescripcion;
	}
	public int getConsultas() {
		return consultas;
	}
	public void setConsultas(int consultas) {
		this.consultas = consultas;
	}
	public int getVentas() {
		return ventas;
	}
	public void setVentas(int ventas) {
		this.ventas = ventas;
	}
	public Date getCreacion() {
		return creacion;
	}
	public void setCreacion(Date creacion) {
		this.creacion = creacion;
	}
	public Date getFinalizacion() {
		return finalizacion;
	}
	public void setFinalizacion(Date finalizacion) {
		this.finalizacion = finalizacion;
	}
	public String getCategoriaDesc() {
		return categoriaDesc;
	}
	public void setCategoriaDesc(String categoriaDesc) {
		this.categoriaDesc = categoriaDesc;
	}
	public Usuario getUsuarioLogeado() {
		return usuarioLogeado;
	}
	public void setUsuarioLogeado(Usuario usuarioLogeado) {
		this.usuarioLogeado = usuarioLogeado;
	}
	public Long getCategoria() {
		return categoria;
	}
	public void setCategoria(Long categoria) {
		this.categoria = categoria;
	}
	public List<String> getImagesList() {
		return imagesList;
	}
	public void setImagesList(List<String> imagesList) {
		this.imagesList = imagesList;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getPrecio() {
		return precio;
	}
	public void setPrecio(Long precio) {
		this.precio = precio;
	}
}
