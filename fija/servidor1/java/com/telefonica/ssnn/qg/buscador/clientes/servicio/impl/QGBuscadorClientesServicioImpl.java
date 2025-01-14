/**
 *
 */
package com.telefonica.ssnn.qg.buscador.clientes.servicio.impl;

import com.telefonica.ssnn.qg.buscador.clientes.dao.interfaz.QGBuscadorClientesDao;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGBuscadorClientesDto;
import com.telefonica.ssnn.qg.buscador.clientes.servicio.interfaz.QGBuscadorClientesServicio;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author jacastano
 *
 */
public class QGBuscadorClientesServicioImpl implements
		QGBuscadorClientesServicio {

	QGBuscadorClientesDao buscadorClientesDao;

	public QGCGlobalDto obtenerDatosCliente(String codigoCliente) {
		return buscadorClientesDao.obtenerDatosCliente(codigoCliente);
	}

	public QGCGlobalDto obtenerDireccionesCliente(String codigoCliente) {
		return buscadorClientesDao.obtenerDireccionesCliente(codigoCliente);
	}

	public QGCGlobalDto obtenerListadoClientes(
			QGBuscadorClientesDto buscadorClientesDto) {
		return buscadorClientesDao.obtenerListadoClientes(buscadorClientesDto);
	}

	public QGCGlobalDto obtenerTipoDocumento(String codigoLinea, String codigoModalidad, String codigoDocumento) {
		return buscadorClientesDao.obtenerTipoDocumento(codigoLinea, codigoModalidad, codigoDocumento);
	}
	
	public QGCGlobalDto obtenerHistoricoSegmentacion(QGBuscadorClientesDto buscadorClientesDto) {
		return buscadorClientesDao.obtenerHistoricoSegmentacion(buscadorClientesDto);
	}

	public QGBuscadorClientesDao getBuscadorClientesDao() {
		return buscadorClientesDao;
	}

	public void setBuscadorClientesDao(QGBuscadorClientesDao buscadorClientesDao) {
		this.buscadorClientesDao = buscadorClientesDao;
	}
}
