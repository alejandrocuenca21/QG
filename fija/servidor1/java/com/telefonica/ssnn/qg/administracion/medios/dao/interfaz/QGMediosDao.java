/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.medios.dao.interfaz;

import com.telefonica.ssnn.qg.administracion.medios.dto.QGMediosComunicacionDto;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGDetalleCDDto;

/**
 * @author vsierra
 *
 */
public interface QGMediosDao {

	QGCGlobalDto obtenerMedioComunicacion(String codigo);
	
	QGCGlobalDto obtenerListaMediosComunicacion();
	
	QGCGlobalDto modificarMediosComunicacion(QGMediosComunicacionDto mediosComunicacionDto);
	
	QGCGlobalDto buscadorMediosCosenDer(QGDetalleCDDto busqueda);
}
