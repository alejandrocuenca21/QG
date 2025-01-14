/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.servicio.interfaz;

import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSegmentacionesBusquedaDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSegmentacionesDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author mgvinuesa
 *
 */
public interface QGSegmentacionesServicio {
	
	/**
	 * Busqueda las segmentaciones por criterio
	 * @param busquedaSegmentacion criterios de busqueda
	 * @return Listado de segmentaciones obtenidas
	 */
	public QGCGlobalDto obtenerListaSegmentaciones(QGSegmentacionesBusquedaDto busquedaSegmentacion);

	/**
	 * Da de alta una segmentacion
	 * @param segmentacion datos de la segmentacion a crear
	 * @return resultado del alta.
	 */
	public QGCGlobalDto altaSegmentacion(QGSegmentacionesDto segmentacion);
	
	/**
	 * Da de baja una segmentacion
	 * @param segmentacion datos de la segmentacion a eliminar
	 * @return resultado de la baja
	 */
	public QGCGlobalDto bajaSegmentacion(QGSegmentacionesDto segmentacion);
	
	/**
	 * Obtiene el listado de codigos de segmento
	 * @return listado de segmentos
	 */
	public QGCGlobalDto obtenerCodigosSegmento(String unidad);
	
	/**
	 * Obtiene el listado de codigos del subsegmento
	 * @param valorCombo codigo del segmento para el filtrado
	 * @return listado de subsegmentos
	 */
	public QGCGlobalDto obtenerCodigosSubSegmento(String unidad,String valorCombo);

	/**
	 * Obtiene el historico asociado a las segmentaciones
	 * @param busquedaSegmentacion
	 * @return
	 */
	public QGCGlobalDto obtenerHistorico(
			QGSegmentacionesBusquedaDto busquedaSegmentacion);
}