package com.uade.tesis.services.daos.dummys;

import java.util.ArrayList;
import java.util.List;

import com.uade.tesis.domains.Categoria;
import com.uade.tesis.domains.Posting;

public class PostingsDummy {

	private List<Posting> list;
	
	public PostingsDummy() {
		list = new ArrayList<Posting>();
		
		Categoria cat1 = new Categoria(1L,"Notebooks");
		Categoria cat2 = new Categoria(2L,"Zapatillas");
		Categoria cat3 = new Categoria(3L,"Camaras Digitales");
		Categoria cat4 = new Categoria(7L,"Camaras No Digitales");
		Categoria cat5 = new Categoria(8L,"Computadoras de Escritorio");
		Categoria cat6 = new Categoria(9L,"Accesorios");
		Categoria cat7 = new Categoria(10L,"Remeras");
		
		List<String> l1 = new ArrayList<String>();
		List<String> l2 = new ArrayList<String>();
		List<String> l3 = new ArrayList<String>();
		List<String> l4 = new ArrayList<String>();
		List<String> l5 = new ArrayList<String>();
		List<String> l6 = new ArrayList<String>();
		List<String> l7 = new ArrayList<String>();
		List<String> l8 = new ArrayList<String>();
		
		l1.add("C:/Documents and Settings/Administrador/Mis documentos/workspacetesis/deREmateTesisWebApp/src/main/resources/images/1.JPG");
		l2.add("C:/Documents and Settings/Administrador/Mis documentos/workspacetesis/deREmateTesisWebApp/src/main/resources/images/2.JPG");
		l3.add("C:/Documents and Settings/Administrador/Mis documentos/workspacetesis/deREmateTesisWebApp/src/main/resources/images/3.JPG");
		l4.add("C:/Documents and Settings/Administrador/Mis documentos/workspacetesis/deREmateTesisWebApp/src/main/resources/images/4.JPG");
		l5.add("C:/Documents and Settings/Administrador/Mis documentos/workspacetesis/deREmateTesisWebApp/src/main/resources/images/5.JPG");
		l6.add("C:/Documents and Settings/Administrador/Mis documentos/workspacetesis/deREmateTesisWebApp/src/main/resources/images/6.JPG");
		l7.add("C:/Documents and Settings/Administrador/Mis documentos/workspacetesis/deREmateTesisWebApp/src/main/resources/images/7.JPG");
		l8.add("C:/Documents and Settings/Administrador/Mis documentos/workspacetesis/deREmateTesisWebApp/src/main/resources/images/8.JPG");
		
		list.add(new Posting(1L, "TEST 1", 120L, cat1, l1));
		list.add(new Posting(2L, "TEST 2", 120L, cat2, l2));
		list.add(new Posting(3L, "TEST 3", 120L, cat3, l3));
		list.add(new Posting(4L, "TEST 4", 120L, cat4, l4));
		list.add(new Posting(5L, "TEST 5", 120L, cat5, l5));
		list.add(new Posting(6L, "TEST 6", 120L, cat6, l6));
		list.add(new Posting(7L, "TEST 7", 120L, cat7, l7));
		list.add(new Posting(8L, "TEST 8", 120L, cat1, l8));
	}
	
	public List<Posting> getPostings() {
		return list;
	}

}
