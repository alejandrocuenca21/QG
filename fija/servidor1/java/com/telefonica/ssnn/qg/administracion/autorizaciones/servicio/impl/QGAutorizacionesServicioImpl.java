package com.telefonica.ssnn.qg.administracion.autorizaciones.servicio.impl;

import com.telefonica.ssnn.qg.administracion.autorizaciones.dao.interfaz.QGAutorizacionesDao;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGAutorizacionesBusquedaDto;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGAutorizacionesDto;
import com.telefonica.ssnn.qg.administracion.autorizaciones.servicio.interfaz.QGAutorizacionesServicio;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
/**
 * Clase interfaz de servicios para AUTORIZACIONES 
 * @author jacastano
 *
 */
public class QGAutorizacionesServicioImpl implements QGAutorizacionesServicio{

	private QGAutorizacionesDao autorizaciones;

	public QGCGlobalPagingDto buscadorAutorizaciones(QGAutorizacionesBusquedaDto busqueda) {
		return this.autorizaciones.buscadorAutorizaciones(busqueda);
	}

	public QGCGlobalDto gestionarAutorizaciones(
			QGAutorizacionesDto autorizaciones) {
		return this.autorizaciones.gestionarAutorizaciones(autorizaciones);
	}

	public QGAutorizacionesDao getAutorizaciones() {
		return autorizaciones;
	}

	public void setAutorizaciones(QGAutorizacionesDao autorizaciones) {
		this.autorizaciones = autorizaciones;
	}
}
