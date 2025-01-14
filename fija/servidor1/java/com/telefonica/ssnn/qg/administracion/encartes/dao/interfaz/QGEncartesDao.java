package com.telefonica.ssnn.qg.administracion.encartes.dao.interfaz;

import com.telefonica.ssnn.qg.administracion.encartes.dto.QGPOEncartesDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;


/**
 * Interfaz para el dao de encartes.
 * 
 * @author rgsimon
 *
 */
public interface QGEncartesDao {
	/**
	 * Funcion para la gestion de encartes
	 * @param encarteDto
	 * @return
	 */
	public QGCGlobalDto gestionEncartes(QGPOEncartesDto encarteDto);


} 