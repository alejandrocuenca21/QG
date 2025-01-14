/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.dao.interfaz;

import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaPresegmentacionAdminDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaPresegmentacionDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaSegmentacionesPresegDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;

/**
 * @author jacastano
 *
 */
public interface QGSegmentacionesPresegDao {
	
	/**
	 * Busqueda las segmentaciones por criterio
	 * @param busquedaSegmentacion criterios de busqueda
	 * @return Listado de segmentaciones obtenidas
	 */
	public QGCGlobalPagingDto gestionarPresegmentacion (QGEntradaSegmentacionesPresegDto segmentacion);

	public QGCGlobalPagingDto obtenerDatosOfAtencion(QGEntradaPresegmentacionDto entrada);

	public QGCGlobalPagingDto obtenerTandem(QGEntradaPresegmentacionDto entrada);

	public QGCGlobalDto obtenerSubsegmentos(QGEntradaSegmentacionesPresegDto busquedaSegmentacion);

	public QGCGlobalDto gestionAdministracion(QGEntradaPresegmentacionAdminDto entrada);

	public QGCGlobalDto operarPreseg(QGEntradaSegmentacionesPresegDto entrada);
}