/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.dao.interfaz;

import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSegmentacionesTraduccionDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author mgvinuesa
 *
 */
public interface QGSegmentacionesTraduccionDao {
	
	/**
	 * Busqueda las segmentaciones por criterio
	 * @param busquedaSegmentacion criterios de busqueda
	 * @return Listado de segmentaciones obtenidas
	 */
	public QGCGlobalDto gestionarTraduccion(QGSegmentacionesTraduccionDto segmentacion);



}