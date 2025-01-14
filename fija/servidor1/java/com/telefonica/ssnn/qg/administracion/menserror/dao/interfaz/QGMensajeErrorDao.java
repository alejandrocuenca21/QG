package com.telefonica.ssnn.qg.administracion.menserror.dao.interfaz;

import com.telefonica.ssnn.qg.administracion.menserror.dto.QGMensajeErrorDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;

/**
 * Clase interfaz de servicios para ADMINISTRACI�N MENSAJES ERROR 
 * @author jacastano
 *
 */
public interface QGMensajeErrorDao {
	
	//M�todo que devuelve la lista de errores almacenada en la tabla QGMENSTD
	QGCGlobalPagingDto buscadorMensajesError(QGMensajeErrorDto mensajeError); //servicio QGF0124
	//M�todo que gestiona los mensajes de error (altas/bajas/modific)
	QGCGlobalDto gestionarMensajesError(QGMensajeErrorDto mensajeError);//servicio QGF0124
}