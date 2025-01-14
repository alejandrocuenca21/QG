package com.telefonica.ssnn.qg.administracion.segmentacion.dao.interfaz;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author jacastano
 *
 */
public interface QGSegmenRegContratosDao {

	QGCGlobalDto obtenerContratos(String inActuacion);
	
}