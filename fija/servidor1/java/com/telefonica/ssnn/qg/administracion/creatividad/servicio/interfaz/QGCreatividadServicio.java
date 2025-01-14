package com.telefonica.ssnn.qg.administracion.creatividad.servicio.interfaz;

import com.telefonica.ssnn.qg.administracion.creatividad.dto.QGCreatividadDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;



/**
 * Interfaz para el servicio de creatividad.
 * 
 * @author mgvinuesa
 *
 */
public interface QGCreatividadServicio {

	/**
	 * Consulta la lista de creatividad encartes
	 * @return
	 */
	public QGCGlobalDto consultarCreatividadEncartes();
	/**
	 * Gestion de una creatividad
	 * @param creatividadDto
	 */
	public void modificarCreatividad(QGCreatividadDto creatividadDto);
	
	/**
	 * Obtiene los segmentos para creatividad
	 * @return
	 */
	public QGCGlobalDto obtenerSegmentos();
	/**
	 * Obtiene los derechos para creatividad
	 * @param lineaNegocio
	 * @return
	 */
	public QGCGlobalDto obtenerDerechos(String lineaNegocio);
		
}
