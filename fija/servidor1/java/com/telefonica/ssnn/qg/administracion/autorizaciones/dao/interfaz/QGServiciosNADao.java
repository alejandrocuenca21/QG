package com.telefonica.ssnn.qg.administracion.autorizaciones.dao.interfaz;

import java.util.HashMap;

import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGServiciosNADto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;

/**
 * Clase interfaz de servicios para SERVICIOS NA
 * @author jacastano
 *
 */
public interface QGServiciosNADao {
	
	//Método que devuelve la lista de servicios NA almacenada en la tabla QGSERVTD
	QGCGlobalPagingDto buscadorServiciosNA(String inActuacion,String PgnTx); //servicio QGF0122
	//Método que gestiona los servicios NA de error (altas/bajas/modific)
	QGCGlobalDto gestionarServiciosNA(QGServiciosNADto serviciosNA);//servicio QGF0122
	
	QGCGlobalDto cargarComboServiciosNA(String inActuacion,HashMap lineaNegocio);
}
