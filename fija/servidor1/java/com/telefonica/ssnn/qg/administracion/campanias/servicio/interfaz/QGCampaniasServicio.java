/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.campanias.servicio.interfaz;

import com.telefonica.ssnn.qg.administracion.campanias.dto.QGCampaniasDto;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author vsierra
 *
 */
public interface QGCampaniasServicio {

	QGCGlobalDto obtenerCampania(String codigo);
	
	QGCGlobalDto obtenerListaCampanias();
	
	QGCGlobalDto modificarListaCampanias(QGCampaniasDto campaniasDto);
	
	QGCGlobalDto altaCampania(QGCampaniasDto campaniasDto);
	
	QGCGlobalDto bajaCampania(QGCampaniasDto campaniasDto);
}
