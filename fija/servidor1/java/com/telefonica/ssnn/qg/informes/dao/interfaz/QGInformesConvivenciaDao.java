/**
 * 
 */
package com.telefonica.ssnn.qg.informes.dao.interfaz;

import java.util.List;

import com.telefonica.ssnn.qg.informes.dto.QGBuscadorDto;
import com.telefonica.ssnn.qg.informes.dto.QGEstadisticasDto;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author vsierra
 *
 */
public interface QGInformesConvivenciaDao {

	QGCGlobalDto buscadorClientesDuplicados(QGBuscadorDto buscadorDto);
	
	QGCGlobalDto actualizarDuplicados(List buscadorDto);
	
	QGCGlobalDto buscadorErrores(QGBuscadorDto buscadorDto);
	
	QGCGlobalDto obtenerEstadisticas(QGEstadisticasDto estadisticasDto);
}
