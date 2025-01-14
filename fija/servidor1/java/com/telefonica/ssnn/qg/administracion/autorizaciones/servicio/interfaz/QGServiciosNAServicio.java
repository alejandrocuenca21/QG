package com.telefonica.ssnn.qg.administracion.autorizaciones.servicio.interfaz;

import java.util.HashMap;

import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGServiciosNADto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
/**
 * Clase interfaz de servicios para SERVICIOS NA
 * @author jacastano
 *
 */
public interface QGServiciosNAServicio {
	
	//Método que devuelve la lista de servicios NA almacenada en la tabla QGSERVTD
	QGCGlobalPagingDto buscadorServiciosNA(String PgnTx); //servicio QGxxxx
	//Método que gestiona los servicios NA de error (altas/bajas/modific)
	QGCGlobalDto gestionarServiciosNA(QGServiciosNADto serviciosNA);//servicio QGxxxx y QGxxxx
	
	QGCGlobalDto cargarComboServiciosNA(HashMap lineaNegocio);
}
