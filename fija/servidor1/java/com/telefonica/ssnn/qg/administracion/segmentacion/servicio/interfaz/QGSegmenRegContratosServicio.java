package com.telefonica.ssnn.qg.administracion.segmentacion.servicio.interfaz;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author jacastano
 *
 */
public interface QGSegmenRegContratosServicio {

	QGCGlobalDto obtenerContratos(String inActuacion);
}