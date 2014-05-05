package com.uade.tesis.services.daos.impl;

import java.util.List;

import com.uade.tesis.domains.Categoria;
import com.uade.tesis.services.daos.CategoriaDAO;
import com.uade.tesis.services.daos.dummys.CategoriasDummy;

public class CategoriaDAOImpl implements CategoriaDAO {

	private List<Categoria> categorias;
	
	public CategoriaDAOImpl() {
		CategoriasDummy dummy = new CategoriasDummy();
		categorias = dummy.getCategorias();
	}
	
	public Categoria getCategoriaById(Long id) {
		for(Categoria element : categorias) {
			if(element.getId().equals(id)) {
				return element;
			}
		}
		return null;
	}
	
	public List<Categoria> getAllCategorias() {
		return categorias;
	}
}
