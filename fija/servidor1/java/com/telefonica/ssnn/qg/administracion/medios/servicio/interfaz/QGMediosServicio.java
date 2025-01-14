/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.medios.servicio.interfaz;

import com.telefonica.ssnn.qg.administracion.medios.dto.QGMediosComunicacionDto;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGDetalleCDDto;

/**
 * @author vsierra
 *
 */
public interface QGMediosServicio {

	QGCGlobalDto obtenerMedioComunicacion(String codigo);
	
	QGCGlobalDto obtenerListaMediosComunicacion();
	
	QGCGlobalDto modificarMediosComunicacion(QGMediosComunicacionDto mediosComunicacionDto);
	
	QGCGlobalDto altaMedioComunicacion(QGMediosComunicacionDto mediosComunicacionDto);
	
	QGCGlobalDto bajaMedioComunicacion(QGMediosComunicacionDto mediosComunicacionDto);
	
	QGCGlobalDto buscadorMediosCosenDer(QGDetalleCDDto busqueda);
}
