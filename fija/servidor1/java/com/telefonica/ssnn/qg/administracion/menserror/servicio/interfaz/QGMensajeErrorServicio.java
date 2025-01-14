package com.telefonica.ssnn.qg.administracion.menserror.servicio.interfaz;

import com.telefonica.ssnn.qg.administracion.menserror.dto.QGMensajeErrorDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
/**
 * Clase interfaz de servicios para ADMINISTRACI�N MENSAJES ERROR 
 * @author jacastano
 *
 */
public interface QGMensajeErrorServicio {
	
	//M�todo que devuelve la lista de errores almacenada en la tabla QGMENSTD
	QGCGlobalPagingDto buscadorMensajesError(QGMensajeErrorDto mensajeError); //servicio QGxxxx
	//M�todo que gestiona los mensajes de error (altas/bajas/modific)
	QGCGlobalDto gestionarMensajesError(QGMensajeErrorDto mensajeError);//servicios QGxxxx y QGxxxx
}
