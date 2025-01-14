/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.consentimientos.dao.interfaz;

import com.telefonica.ssnn.qg.administracion.consentimientos.dto.QGDetalleConsentimientosDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGTextoLegalDto;

/**
 * @author vsierra 
 *
 */
public interface QGConsentimientosDao {

	QGCGlobalDto obtenerDetalleConsentimiento(String codigo);
	
	QGCGlobalDto obtenerListaConsentimientos();
	
	QGCGlobalDto modificarConsentimiento(QGDetalleConsentimientosDto detalleConsentimientosDto);
	
	QGCGlobalDto obtenerTextoLegal(String codigo, Integer secuencial);
	
	QGCGlobalDto obtenerTipoObjeto(String codigo);

	QGCGlobalDto obtenerListaTipoObjetos();
	
	QGCGlobalDto obtenerListaTipoConsentimientos();
	
	QGCGlobalDto obtenerAmbito(String codigo);

	QGCGlobalDto obtenerListaAmbitos();

	QGCGlobalDto obtenerNivelAplicacion(String codigo);
	
	QGCGlobalDto obtenerListaNivelAplicacion();
	
	QGCGlobalDto altaTextoLegal(QGTextoLegalDto QGTextoLegalDto);
}
