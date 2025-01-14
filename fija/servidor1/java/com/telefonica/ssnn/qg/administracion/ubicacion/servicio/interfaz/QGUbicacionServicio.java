/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.ubicacion.servicio.interfaz;

import com.telefonica.ssnn.qg.administracion.ubicacion.dto.QGTiposUbicacionDto;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author vsierra
 *
 */
public interface QGUbicacionServicio {

	QGCGlobalDto obtenerTiposUbicacion(String codigo);
	
	QGCGlobalDto obtenerListaTiposUbicacion();
	
	QGCGlobalDto modificarTiposUbicacion(QGTiposUbicacionDto tiposUbicacionDto);
	
	QGCGlobalDto altaTipoUbicacion(QGTiposUbicacionDto tiposUbicacionDto);
	
	QGCGlobalDto bajaTipoUbicacion(QGTiposUbicacionDto tiposUbicacionDto);
}
