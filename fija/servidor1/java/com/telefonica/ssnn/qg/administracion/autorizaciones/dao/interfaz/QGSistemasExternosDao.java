package com.telefonica.ssnn.qg.administracion.autorizaciones.dao.interfaz;

import java.util.HashMap;

import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGSistemasExternosBusquedaDto;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGSistemasExternosDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;

/**
 * Clase interfaz de servicios para SISTEMAS EXTERNOS 
 * @author jacastano 
 *
 */
public interface QGSistemasExternosDao {
	
	//Método que devuelve la lista de lineas de negocio para sistemas externos almacenada en la tabla QGLNEGTD
	QGCGlobalDto buscadorLineasNegocio(String inActuacion);
	//Método que devuelve la lista de sistemas externos almacenada en la tabla QGSIEXTD
	QGCGlobalPagingDto buscadorSistemasExternos(String inActuacion,HashMap lineaNegocio,QGSistemasExternosBusquedaDto busqueda); //servicio QGF0121

	//Método que devuelve la lista de sistemas externos POR COMPLETO  almacenada en la tabla QGSIEXTD
	QGCGlobalDto cargarComboSistemaExternos(String inActuacion,HashMap lineaNegocio); //servicio QGF0121
	
	//Método que gestiona los sistemas externos de error (altas/bajas/modific)
	QGCGlobalDto gestionarSistemasExternos(QGSistemasExternosDto sistemasExternos);//servicio QGF0121
	
}
