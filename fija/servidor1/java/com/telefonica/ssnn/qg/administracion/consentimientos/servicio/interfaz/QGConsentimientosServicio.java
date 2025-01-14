/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.consentimientos.servicio.interfaz;

import com.telefonica.ssnn.qg.administracion.consentimientos.dto.QGDetalleConsentimientosDto;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGTextoLegalDto;

/**
 * @author vsierra
 * 
 */
public interface QGConsentimientosServicio {

	QGCGlobalDto obtenerDetalleConsentimiento(String codigo);
	
	QGCGlobalDto obtenerListaConsentimientos();
	
	QGCGlobalDto modificarConsentimiento(QGDetalleConsentimientosDto detalleConsentimientosDto);
	
	QGCGlobalDto altaConsentimiento(QGDetalleConsentimientosDto detalleConsentimientosDto);
	
	QGCGlobalDto bajaConsentimiento(QGDetalleConsentimientosDto detalleConsentimientosDto);
	
	QGCGlobalDto obtenerTextoLegal(String codigo, Integer secuencial);
	
	QGCGlobalDto obtenerTipoObjeto(String codigo);

	QGCGlobalDto obtenerListaTipoObjetos();

	QGCGlobalDto obtenerListaSegmentos();
	
	QGCGlobalDto obtenerAmbito(String codigo);

	QGCGlobalDto obtenerListaAmbitos();

	QGCGlobalDto obtenerListaTipoConsentimientos();

	QGCGlobalDto obtenerNivelAplicacion(String codigo);
	
	QGCGlobalDto obtenerListaNivelAplicacion();
	
	QGCGlobalDto altaTextoLegal(QGTextoLegalDto QGTextoLegalDto);
	
	QGCGlobalDto modificarTextoLegal(QGTextoLegalDto QGTextoLegalDto);
}
