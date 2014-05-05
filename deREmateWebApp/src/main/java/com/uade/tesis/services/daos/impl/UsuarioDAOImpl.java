package com.uade.tesis.services.daos.impl;

import java.util.List;

import com.uade.tesis.domains.Usuario;
import com.uade.tesis.services.daos.UsuarioDAO;
import com.uade.tesis.services.daos.dummys.UsuariosDummy;

public class UsuarioDAOImpl implements UsuarioDAO {

	private List<Usuario> list;

	public UsuarioDAOImpl() {
		UsuariosDummy dummy = new UsuariosDummy();
		list = dummy.getUsersList();
	}
	
	public Usuario getUsuarioById(Long id) {
		for(Usuario element : list) {
			if(element.getId().equals(id)) {
				return element;
			}
		}
		return null;
	}
	
	public List<Usuario> getAllUsuarios() {
		return list;
	}
}
