/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.clientes.servicio.interfaz;

import com.telefonica.ssnn.qg.buscador.clientes.dto.QGBuscadorClientesDto;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;



/**
 * @author ACF
 *
 */
public interface TWBuscadorClientesServicio {
	
	QGCGlobalDto obtenerListadoClientes(QGBuscadorClientesDto buscadorClientesDto);
	
	QGCGlobalDto obtenerDatosCliente(String codigoCliente);
	
	QGCGlobalDto obtenerDireccionesCliente(String codigoCliente);
	
	QGCGlobalDto obtenerTipoDocumento(String codigoLinea, String codigoModalidad, String codigoDocumento);

	QGCGlobalDto obtenerHistoricoSegmentacion(QGBuscadorClientesDto buscadorClientesDto);
}
