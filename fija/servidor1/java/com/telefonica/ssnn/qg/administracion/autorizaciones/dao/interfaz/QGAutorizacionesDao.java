package com.telefonica.ssnn.qg.administracion.autorizaciones.dao.interfaz;

import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGAutorizacionesBusquedaDto;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGAutorizacionesDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;


/**
 * Clase interfaz de servicios para AUTORIZACIONES 
 * @author jacastano
 *
 */
public interface QGAutorizacionesDao {
	
	//Método que devuelve la lista de autorizaciones almacenada en la tabla QGSIBDTD
	QGCGlobalPagingDto buscadorAutorizaciones(QGAutorizacionesBusquedaDto busqueda); //servicio QGF0123
	//Método que gestiona los autorizaciones de error (altas/bajas/modific)
	QGCGlobalDto gestionarAutorizaciones(QGAutorizacionesDto autorizaciones);//servicio QGF0123
	
}
