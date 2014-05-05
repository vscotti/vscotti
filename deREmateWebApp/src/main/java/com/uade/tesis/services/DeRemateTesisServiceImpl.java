package com.uade.tesis.services;

import java.util.List;

import com.uade.tesis.domains.Categoria;
import com.uade.tesis.domains.Posting;
import com.uade.tesis.domains.Usuario;
import com.uade.tesis.services.daos.CategoriaDAO;
import com.uade.tesis.services.daos.PostingDAO;
import com.uade.tesis.services.daos.UsuarioDAO;

public class DeRemateTesisServiceImpl implements DeRemateTesisService {

	private PostingDAO postingDAO;
	private CategoriaDAO categoriaDAO;
	private UsuarioDAO usuarioDAO;
	
	public Posting getPostingById(Long id) {
		return postingDAO.getPostingById(id);
	}
	
	public Long getNextPostingId() {
		return postingDAO.getNextPostingId();
	}
	
	public void savePosting(Posting posting) {
		postingDAO.savePosting(posting);
	}
	
	public List<Posting> getAllPostings() {
		return postingDAO.getAllPostings();
	}
	
	public Usuario getUsuarioById(Long id) {
		return usuarioDAO.getUsuarioById(id);
	}
	
	public Categoria getCategoriaById(Long id) {
		return categoriaDAO.getCategoriaById(id);
	}
	
	public List<Categoria> getAllCategorias() {
		return categoriaDAO.getAllCategorias();
	}
	
	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}
	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
	public PostingDAO getPostingDAO() {
		return postingDAO;
	}
	public void setPostingDAO(PostingDAO postingDAO) {
		this.postingDAO = postingDAO;
	}
	public CategoriaDAO getCategoriaDAO() {
		return categoriaDAO;
	}
	public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
		this.categoriaDAO = categoriaDAO;
	}
}
