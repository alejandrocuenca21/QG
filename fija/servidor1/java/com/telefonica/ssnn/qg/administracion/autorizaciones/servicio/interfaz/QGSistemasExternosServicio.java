package com.telefonica.ssnn.qg.administracion.autorizaciones.servicio.interfaz;

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
public interface QGSistemasExternosServicio {
	
	//Método que devuelve la lista de lineas de negocio para sistemas externos almacenada en la tabla QGLNEGTD
	QGCGlobalDto buscadorLineasNegocio();
	//Método que devuelve la lista de sistemas externos almacenada en la tabla QGSIEXTD
	QGCGlobalPagingDto buscadorSistemasExternos(HashMap lineaNegocio,QGSistemasExternosBusquedaDto busqueda); //servicio QGF0121
	
	//Método que carga el combo Sistema Externos con la lista al completo.
	QGCGlobalDto cargarComboSistemaExternos(HashMap lineaNegocio); //servicio QGF0121
	
	//Método que gestiona los sistemas externos de error (altas/bajas/modific)
	QGCGlobalDto gestionarSistemasExternos(QGSistemasExternosDto sistemasExternos);//servicio QGxxxx y QGxxxx
	
}
