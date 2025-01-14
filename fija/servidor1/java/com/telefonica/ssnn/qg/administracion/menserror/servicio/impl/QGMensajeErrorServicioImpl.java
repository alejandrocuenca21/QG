package com.telefonica.ssnn.qg.administracion.menserror.servicio.impl;

import com.telefonica.ssnn.qg.administracion.menserror.dao.interfaz.QGMensajeErrorDao;
import com.telefonica.ssnn.qg.administracion.menserror.dto.QGMensajeErrorDto;
import com.telefonica.ssnn.qg.administracion.menserror.servicio.interfaz.QGMensajeErrorServicio;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
/**
 * Clase implementación de servicios para ADMINISTRACIÓN MENSAJES ERROR 
 * @author jacastano
 *
 */
public class QGMensajeErrorServicioImpl implements QGMensajeErrorServicio{

	private QGMensajeErrorDao mensajeError;

	public QGCGlobalPagingDto buscadorMensajesError(QGMensajeErrorDto mensajeError) {
		return getMensajeError().buscadorMensajesError(mensajeError);
	}

	public QGCGlobalDto gestionarMensajesError(
			QGMensajeErrorDto mensajeError) {
		return getMensajeError().gestionarMensajesError(mensajeError);
	}

	public QGMensajeErrorDao getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(QGMensajeErrorDao mensajeError) {
		this.mensajeError = mensajeError;
	}


}
