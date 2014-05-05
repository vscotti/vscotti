package com.uade.tesis.web.formbeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import com.uade.tesis.domains.Categoria;
import com.uade.tesis.domains.Posting;
import com.uade.tesis.domains.Usuario;

public class CrearPostingFormBean {

	private Posting posting;
	
	private String titulo;
	private String descripcion;
	private String hiddenIndex;

	private Long precio;
	private Long categoria;

	private Date finalizacion;
	
	private Usuario usuarioLogeado;
	
	private UploadedFile uploadedFile;
	
	private List<SelectItem> categorias;
	
	private List<String> imagesList;
	
	private List<Categoria> categoriasSugeridas;
	
	private boolean maxImages;
	private boolean haySugerencias;
	private boolean formatoDescripcion;
	
	public CrearPostingFormBean() {
		categorias = new ArrayList<SelectItem>();
		imagesList = new ArrayList<String>();
	}
	
	public boolean isFormatoDescripcion() {
		return formatoDescripcion;
	}
	public void setFormatoDescripcion(boolean formatoDescripcion) {
		this.formatoDescripcion = formatoDescripcion;
	}
	public Date getFinalizacion() {
		return finalizacion;
	}
	public void setFinalizacion(Date finalizacion) {
		this.finalizacion = finalizacion;
	}
	public Posting getPosting() {
		return posting;
	}
	public void setPosting(Posting posting) {
		this.posting = posting;
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
	public boolean isHaySugerencias() {
		return haySugerencias;
	}
	public void setHaySugerencias(boolean haySugerencias) {
		this.haySugerencias = haySugerencias;
	}
	public List<Categoria> getCategoriasSugeridas() {
		return categoriasSugeridas;
	}
	public void setCategoriasSugeridas(List<Categoria> categoriasSugeridas) {
		this.categoriasSugeridas = categoriasSugeridas;
	}
	public boolean isMaxImages() {
		return maxImages;
	}
	public void setMaxImages(boolean maxImages) {
		this.maxImages = maxImages;
	}
	public String getHiddenIndex() {
		return hiddenIndex;
	}
	public void setHiddenIndex(String hiddenIndex) {
		this.hiddenIndex = hiddenIndex;
	}
	public List<String> getImagesList() {
		return imagesList;
	}
	public void setImagesList(List<String> imagesList) {
		this.imagesList = imagesList;
	}
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}
	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	public List<SelectItem> getCategorias() {
		return categorias;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void setCategorias(List<SelectItem> categorias) {
		this.categorias = categorias;
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
