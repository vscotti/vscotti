package com.uade.tesis.services.daos;

import java.util.List;

import com.uade.tesis.domains.Categoria;

public interface CategoriaDAO {
	Categoria getCategoriaById(Long id);
	List<Categoria> getAllCategorias();
}
