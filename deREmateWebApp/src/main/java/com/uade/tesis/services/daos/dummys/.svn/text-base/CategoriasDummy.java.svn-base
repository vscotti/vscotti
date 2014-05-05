package com.uade.tesis.services.daos.dummys;

import java.util.ArrayList;
import java.util.List;

import com.uade.tesis.domains.Categoria;

public class CategoriasDummy {

	private List<Categoria> list;
	
	public CategoriasDummy() {
		list = new ArrayList<Categoria>();
		
		Categoria cat1;
		Categoria cat2;
		Categoria cat3;
		Categoria cat4;
		Categoria cat5;
		Categoria cat6;
		Categoria cat7;
		Categoria cat8;
		Categoria cat9;
		Categoria cat10;
		
		cat1 = new Categoria(1L,"Notebooks");
		cat2 = new Categoria(2L,"Zapatillas");
		cat3 = new Categoria(3L,"Camaras Digitales");
		cat4 = new Categoria(4L,"Computacion");
		cat5 = new Categoria(5L,"Indumentaria");
		cat6 = new Categoria(6L,"Camaras");
		cat7 = new Categoria(7L,"Camaras No Digitales");
		cat8 = new Categoria(8L,"Computadoras de Escritorio");
		cat9 = new Categoria(9L,"Accesorios");
		cat10 = new Categoria(10L,"Remeras");
		
		cat4.getSubCategorias().add(cat1);
		cat4.getSubCategorias().add(cat8);
		cat4.getSubCategorias().add(cat9);
		
		cat5.getSubCategorias().add(cat2);
		cat5.getSubCategorias().add(cat10);
		
		cat6.getSubCategorias().add(cat3);
		cat6.getSubCategorias().add(cat7);
		
		list.add(cat1);
		list.add(cat2);
		list.add(cat3);
		list.add(cat4);
		list.add(cat5);
		list.add(cat6);
		list.add(cat7);
		list.add(cat8);
		list.add(cat9);
		list.add(cat10);
	}
	
	public List<Categoria> getCategorias() {
		return list;
	}
}
