package com.telefonica.ssnn.qg.administracion.autorizaciones.servicio.interfaz;

import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGAutorizacionesBusquedaDto;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGAutorizacionesDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;

/**
 * Clase interfaz de servicios para AUTORIZACIONES 
 * @author jacastano
 *
 */
public interface QGAutorizacionesServicio {
	
	//Método que devuelve la lista de autorizaciones almacenada en la tabla QGSIBDTD
	QGCGlobalPagingDto buscadorAutorizaciones(QGAutorizacionesBusquedaDto busqueda); //servicio QGxxxx
	//Método que gestiona los autorizaciones de error (altas/bajas/modific)
	QGCGlobalDto gestionarAutorizaciones(QGAutorizacionesDto autorizaciones);//servicio QGxxxx y QGxxxx
	
}
