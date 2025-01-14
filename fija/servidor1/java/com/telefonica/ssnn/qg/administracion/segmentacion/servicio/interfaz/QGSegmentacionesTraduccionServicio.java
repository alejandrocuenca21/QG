/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.servicio.interfaz;

import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSegmentacionesTraduccionDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author mgvinuesa
 *
 */
public interface QGSegmentacionesTraduccionServicio {
	
	/**
	 * Busqueda las segmentaciones por criterio
	 * @param busquedaSegmentacion criterios de busqueda
	 * @return Listado de segmentaciones obtenidas
	 */
	public QGCGlobalDto obtenerListaSegmentaciones(QGSegmentacionesTraduccionDto busquedaSegmentacion);

	/**
	 * Da de alta una segmentacion
	 * @param segmentacion datos de la segmentacion a crear
	 * @return resultado del alta.
	 */
	public QGCGlobalDto altaSegmentacion(QGSegmentacionesTraduccionDto segmentacion);
	
	/**
	 * Da de baja una segmentacion
	 * @param segmentacion datos de la segmentacion a eliminar
	 * @return resultado de la baja
	 */
	public QGCGlobalDto bajaSegmentacion(QGSegmentacionesTraduccionDto segmentacion);
	
	/**
	 * Modifica una segmentacion
	 * @param segmentacion datos de la segmentacion a eliminar
	 * @return resultado de la baja
	 */
	public QGCGlobalDto modificarSegmentacion(QGSegmentacionesTraduccionDto segmentacion);
	
	/**
	 * Obtiene el listado de codigos de segmentos moviles
	 * @return listado de segmentos
	 */
	public QGCGlobalDto obtenerCodigosSegmentoMovil();
	
	/**
	 * Obtiene el listado de codigos del subsegmento moviles
	 * @param valorCombo codigo del segmento para el filtrado
	 * @return listado de subsegmentos
	 */
	public QGCGlobalDto obtenerCodigosSubSegmentoMovil(String valorCombo);
	/**
	 * Obtiene el listado de codigos de segmento fijos
	 * @return listado de segmentos
	 */
	public QGCGlobalDto obtenerCodigosSegmentoFijo();
	
	/**
	 * Obtiene el listado de codigos del subsegmento fijos
	 * @param valorCombo codigo del segmento para el filtrado
	 * @return listado de subsegmentos
	 */
	public QGCGlobalDto obtenerCodigosSubSegmentoFijo(String valorCombo);

	/**
	 * Obtiene el historico asociado a las segmentaciones
	 * @param busquedaSegmentacion
	 * @return
	 */
	public QGCGlobalDto obtenerHistorico(
			QGSegmentacionesTraduccionDto busquedaSegmentacion);
}