package com.telefonica.ssnn.qg.administracion.creatividad.dao.interfaz;

import com.telefonica.ssnn.qg.administracion.creatividad.dto.QGCreatividadDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * Interfaz para creatividad
 * @author mgvinuesa
 *
 */
public interface QGCreatividadDao {
	
	/**
	 * Modifica una creatividad
	 * @param creatividadDto
	 */
	public QGCGlobalDto gestionarCreatividad(QGCreatividadDto creatividadDto);
}
