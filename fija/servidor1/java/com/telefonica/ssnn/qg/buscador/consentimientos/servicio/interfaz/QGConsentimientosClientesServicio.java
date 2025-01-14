/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.consentimientos.servicio.interfaz;

import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGActualizacionCDDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGDetalleCDDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGEntradaHistorialDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGEntradaListaCDDto;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author vsierra
 *
 */
public interface QGConsentimientosClientesServicio {

	QGCGlobalDto obtenerListaTodosConsentimientos(QGEntradaListaCDDto entradaListaCDDto);
	
	QGCGlobalDto obtenerListaFiltroConsentimientos(QGEntradaListaCDDto entradaListaCDDto);
	
	QGCGlobalDto obtenerDetalleCliente(QGDetalleCDDto detalleCDDto);
	
	QGCGlobalDto obtenerHistorialCliente(QGEntradaHistorialDto entradaHistorialDto);
	
	QGCGlobalDto actualizacionCliente(QGActualizacionCDDto actualizacionCDDto);
	
	QGCGlobalDto obtenerTipoOperacion(String codigo);
	
	QGCGlobalDto obtenerListaTipoOperacion();
	
	QGCGlobalDto obtenerEstadoGestion(String codigo);
	
	QGCGlobalDto obtenerListaEstadoGestion();
	
	QGCGlobalDto obtenerTipoComunicacion(String codigo);
	
	QGCGlobalDto obtenerListaTiposComunicacion();
}
