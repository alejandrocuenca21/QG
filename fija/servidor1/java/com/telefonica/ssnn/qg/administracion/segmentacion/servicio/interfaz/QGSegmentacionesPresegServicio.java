/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.servicio.interfaz;

import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaPresegmentacionAdminDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaPresegmentacionDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaSegmentacionesPresegDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;

/**
 * @author jacastano
 *
 */
public interface QGSegmentacionesPresegServicio {
	
	/**
	 * Busqueda las presegmentaciones por criterio
	 * @param busquedaPresegmentacion criterios de busqueda
	 * @return Listado de presegmentaciones obtenidas
	 */
	public QGCGlobalPagingDto obtenerListaPresegmentaciones(QGEntradaSegmentacionesPresegDto busquedaPresegmentacion);
	public QGCGlobalPagingDto obtenerDatosOfAtencion(QGEntradaPresegmentacionDto entrada);
	public QGCGlobalPagingDto obtenerDatosTandem(QGEntradaPresegmentacionDto entrada);
	public QGCGlobalDto obtenerSubsegmentos(QGEntradaSegmentacionesPresegDto busquedaSegmentacion);
	public QGCGlobalDto gestionAdministracion(QGEntradaPresegmentacionAdminDto entrada);
	public QGCGlobalDto operarPreseg(QGEntradaSegmentacionesPresegDto entrada);

}