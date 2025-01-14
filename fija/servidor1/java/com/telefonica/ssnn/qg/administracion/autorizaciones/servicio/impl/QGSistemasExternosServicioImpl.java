package com.telefonica.ssnn.qg.administracion.autorizaciones.servicio.impl;

import java.util.HashMap;

import com.telefonica.ssnn.qg.administracion.autorizaciones.dao.interfaz.QGSistemasExternosDao;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGSistemasExternosBusquedaDto;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGSistemasExternosDto;
import com.telefonica.ssnn.qg.administracion.autorizaciones.servicio.interfaz.QGSistemasExternosServicio;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
/**
 * Clase interfaz de servicios para SISTEMAS EXTERNOS 
 * @author jacastano
 *
 */
public class QGSistemasExternosServicioImpl implements QGSistemasExternosServicio{

	private QGSistemasExternosDao sistemasExternos;

	
	public QGCGlobalDto buscadorLineasNegocio(){
		String inActuacion = "C";
		return this.sistemasExternos.buscadorLineasNegocio(inActuacion);
	}
	
	public QGCGlobalPagingDto buscadorSistemasExternos(HashMap lineaNegocio,QGSistemasExternosBusquedaDto busqueda) {
		String inActuacion = "C";
		return this.sistemasExternos.buscadorSistemasExternos(inActuacion,lineaNegocio,busqueda);
	}

	public QGCGlobalDto cargarComboSistemaExternos(HashMap lineaNegocio) {
		String inActuacion = "C";
		return this.sistemasExternos.cargarComboSistemaExternos(inActuacion, lineaNegocio);
	}
	
	
	public QGCGlobalDto gestionarSistemasExternos(
			QGSistemasExternosDto sistemasExternos) {
		return this.sistemasExternos.gestionarSistemasExternos(sistemasExternos);
	}
	
	public QGSistemasExternosDao getSistemasExternos() {
		return sistemasExternos;
	}

	public void setSistemasExternos(QGSistemasExternosDao sistemasExternos) {
		this.sistemasExternos = sistemasExternos;
	}
}
