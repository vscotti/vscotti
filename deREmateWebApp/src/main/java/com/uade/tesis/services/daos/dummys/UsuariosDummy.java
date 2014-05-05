package com.uade.tesis.services.daos.dummys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.uade.tesis.domains.Usuario;

public class UsuariosDummy {
	
	private List<Usuario> list;
	
	@SuppressWarnings("deprecation")
	public UsuariosDummy() {
		list = new ArrayList<Usuario>();
		
		Usuario us = new Usuario();
		us.setApellido("Scotti");
		us.setApodo("oscotti");
		us.setDomicilio("Cordoba 567");
		us.setEmail("oscar.scotti@yahoo.com.ar");
		us.setFechaNacimiento(new Date(1982,11,23));
		us.setId(1L);
		us.setUbicacion("CAPITAL FEDERAL");
		us.setTelefono("4545-4545");
		us.setNombre("Oscar");
		list.add(us);
		
		us = new Usuario();
		us.setApellido("Suarez");
		us.setApodo("msuarez");
		us.setDomicilio("Santa Fe 1234");
		us.setEmail("msuarez@hotmail.com");
		us.setFechaNacimiento(new Date(1980,1,2));
		us.setId(1L);
		us.setUbicacion("BUENOS AIRES");
		us.setTelefono("3535-3535");
		us.setNombre("Marcos");
		list.add(us);
		
		us = new Usuario();
		us.setApellido("Lopez");
		us.setApodo("carlitoslopez");
		us.setDomicilio("Reconquista 1234");
		us.setEmail("carlos.lopez@gmail.com");
		us.setFechaNacimiento(new Date(1967,5,7));
		us.setId(1L);
		us.setUbicacion("CORDOBA");
		us.setTelefono("0254-3535-3535");
		us.setNombre("Carlos");
		list.add(us);
	}
	
	public List<Usuario> getUsersList() {
		return list;
	}
}
