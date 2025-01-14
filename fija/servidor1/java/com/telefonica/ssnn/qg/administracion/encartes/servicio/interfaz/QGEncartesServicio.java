package com.telefonica.ssnn.qg.administracion.encartes.servicio.interfaz;

import com.telefonica.ssnn.qg.administracion.encartes.dto.QGPOEncartesDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;


/**
 * Interfaz para el servicio de encartes.
 * 
 * @author rgsimon
 *
 */
public interface QGEncartesServicio {

	
	/**
	 * Metodo que realiza la consulta de todos los registros de  publico objetivo para encartes
	 * 
	 * @return listado de resultados.
	 */
	public QGCGlobalDto consultarPoEncartes();
	/**
	 * Realiza la busqueda del historico de un encarte
	 * @param encartesDto
	 * @return
	 */
	public QGCGlobalDto consultarHistoricoPoEncartes(QGPOEncartesDto encartesDto);
	
	public void gestionarPoEncartes(QGPOEncartesDto encartesDto);
	
	public QGCGlobalDto obtenerSegmentos();
	
	public QGCGlobalDto obtenerDerechos(String lineaNegocio);
	
		
}
