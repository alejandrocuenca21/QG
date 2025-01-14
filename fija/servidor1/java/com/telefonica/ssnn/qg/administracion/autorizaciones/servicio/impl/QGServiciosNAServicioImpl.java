package com.telefonica.ssnn.qg.administracion.autorizaciones.servicio.impl;

import java.util.HashMap;

import com.telefonica.ssnn.qg.administracion.autorizaciones.dao.interfaz.QGServiciosNADao;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGServiciosNADto;
import com.telefonica.ssnn.qg.administracion.autorizaciones.servicio.interfaz.QGServiciosNAServicio;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
/**
 * Clase interfaz de servicios para SERVICIOS NA 
 * @author jacastano
 *
 */
public class QGServiciosNAServicioImpl implements QGServiciosNAServicio{

	private QGServiciosNADao serviciosNA;

	public QGCGlobalPagingDto buscadorServiciosNA(String pgnTx) {
		String inActuacion = "C";
		return this.serviciosNA.buscadorServiciosNA(inActuacion,pgnTx);
	}

	public QGCGlobalDto cargarComboServiciosNA(HashMap lineaNegocio) {
		String inActuacion = "C";
		return this.serviciosNA.cargarComboServiciosNA(inActuacion, lineaNegocio);
	}
	
	public QGCGlobalDto gestionarServiciosNA(
			QGServiciosNADto serviciosNA) {
		return this.serviciosNA.gestionarServiciosNA(serviciosNA);
	}
	
	public QGServiciosNADao getServiciosNA() {
		return serviciosNA;
	}

	public void setServiciosNA(QGServiciosNADao serviciosNA) {
		this.serviciosNA = serviciosNA;
	}
}
