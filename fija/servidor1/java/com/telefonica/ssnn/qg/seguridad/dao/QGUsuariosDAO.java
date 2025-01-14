package com.telefonica.ssnn.qg.seguridad.dao;

import com.telefonica.ssnn.qg.seguridad.dto.QGUsuarioDTO;

public interface QGUsuariosDAO {
	
	QGUsuarioDTO obtenerUsuario(String nombre);

}
