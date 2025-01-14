/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.campanias.dao.interfaz;

import com.telefonica.ssnn.qg.administracion.campanias.dto.QGCampaniasDto;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author vsierra
 *
 */
public interface QGCampaniasDao {

	QGCGlobalDto obtenerCampania(String codigo);
	
	QGCGlobalDto obtenerListaCampanias();
	
	QGCGlobalDto modificarListaCampanias(QGCampaniasDto campaniasDto);
}
