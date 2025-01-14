/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.ubicacion.dao.interfaz;

import com.telefonica.ssnn.qg.administracion.ubicacion.dto.QGTiposUbicacionDto;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author vsierra
 *
 */
public interface QGUbicacionDao {

	QGCGlobalDto obtenerTiposUbicacion(String codigo);
	
	QGCGlobalDto obtenerListaTiposUbicacion();
	
	QGCGlobalDto modificarTiposUbicacion(QGTiposUbicacionDto tiposUbicacionDto);
}
