/**
 * 
 */
package com.telefonica.ssnn.qg.informes.servicio.interfaz;

import java.util.List;

import com.telefonica.ssnn.qg.informes.dto.QGBuscadorDto;
import com.telefonica.ssnn.qg.informes.dto.QGEstadisticasDto;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author vsierra
 *
 */
public interface QGInformesConvivenciaServicio {

	QGCGlobalDto buscadorClientesDuplicados(QGBuscadorDto buscadorDto);
	
	QGCGlobalDto buscarClientesEstadisticas(QGEstadisticasDto estadisticasDto);
	
	QGCGlobalDto actualizarDuplicados(List buscadorDto);
	
	QGCGlobalDto buscadorErrores(QGBuscadorDto buscadorDto);
	
	QGCGlobalDto buscarErroresEstadisticas(QGEstadisticasDto estadisticasDto);
	
	QGCGlobalDto obtenerEstadisticas(QGEstadisticasDto estadisticasDto);
}
