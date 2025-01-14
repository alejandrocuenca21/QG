package com.telefonica.ssnn.qg.base.dao;

import org.springframework.security.context.SecurityContextHolder;

import com.telefonica.ssnn.qg.seguridad.QGUsuario;


public class QGBaseDao {

	protected QGUsuario obtenerUsuarioLogado(){
		return (QGUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
