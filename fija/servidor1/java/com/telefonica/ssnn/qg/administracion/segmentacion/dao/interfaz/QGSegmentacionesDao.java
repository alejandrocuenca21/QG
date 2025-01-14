/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.dao.interfaz;

import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSegmentacionesBusquedaDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSegmentacionesDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author mgvinuesa
 *
 */
public interface QGSegmentacionesDao {
	
	/**
	 * Busqueda las segmentaciones por criterio
	 * @param busquedaSegmentacion criterios de busqueda
	 * @return Listado de segmentaciones obtenidas
	 */
	public QGCGlobalDto obtenerListaSegmentaciones(QGSegmentacionesBusquedaDto busquedaSegmentacion);

	/**
	 * Gestiona una segmentacion ya sea darla de alta, modificarla o darla de baja
	 * depende del codigo de actuacion que tenga la segmentacion
	 * @param segmentacion datos de la segmentacion
	 * @return resultado de la accion
	 */
	public QGCGlobalDto modificarSegmentacion(QGSegmentacionesDto segmentacion);

}