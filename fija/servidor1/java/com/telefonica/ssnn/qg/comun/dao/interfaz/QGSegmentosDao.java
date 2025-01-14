package com.telefonica.ssnn.qg.comun.dao.interfaz;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

public interface QGSegmentosDao {
	/**
	 * Obtiene los segmentos fijos para:
	 * 	- encartes
	 * 	- segmentacion evolucion
	 * 	- segmentacion traduccion
	 * @return QGGlobalDto
	 */
	public QGCGlobalDto obtenerSegmentos();
	
	/**
	 * Obtiene los subSegmentos fijos para:
	 * 	- encartes
	 * 	- segmentacion evolucion
	 * 	- segmentacion traduccion
	 * @return QGGlobalDto
	 */
	public QGCGlobalDto obtenerSubSegmentos(String codigoSegmento);
	
	/**
	 * Obtiene los Segmentos moviles para:
	 * 	- segmentacion traduccion
	 * @return QGGlobalDto
	 */
	public QGCGlobalDto obtenerSegmentosMovil();

	/**
	 * Obtiene los Subsegmentos moviles para:
	 * 	- segmentacion traduccion
	 * @return QGGlobalDto
	 */
	public QGCGlobalDto obtenerSubSegmentosMovil(String codigoSegmento);
	
	
}
