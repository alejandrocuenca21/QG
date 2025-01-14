/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.campanias.servicio.impl;

import com.telefonica.ssnn.qg.administracion.campanias.dao.interfaz.QGCampaniasDao;
import com.telefonica.ssnn.qg.administracion.campanias.dto.QGCampaniasDto;
import com.telefonica.ssnn.qg.administracion.campanias.servicio.interfaz.QGCampaniasServicio;

import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author vsierra
 *
 */
public class QGCampaniasServicioImpl implements QGCampaniasServicio {
	
	private QGCampaniasDao campaniasDao;

	public QGCGlobalDto altaCampania(QGCampaniasDto campaniasDto) {
		campaniasDto.setCodActuacion(QGConstantes.CODIGO_ALTA);
		return this.getCampaniasDao().modificarListaCampanias(campaniasDto);		
	}

	public QGCGlobalDto bajaCampania(QGCampaniasDto campaniasDto) {
		campaniasDto.setCodActuacion(QGConstantes.CODIGO_BAJA);
		return this.getCampaniasDao().modificarListaCampanias(campaniasDto);		
	}

	public QGCGlobalDto modificarListaCampanias(QGCampaniasDto campaniasDto) {
		campaniasDto.setCodActuacion(QGConstantes.CODIGO_MODIFICAR);
		return this.getCampaniasDao().modificarListaCampanias(campaniasDto);		
	}

	public QGCGlobalDto obtenerCampania(String codigo) {
		return this.getCampaniasDao().obtenerCampania(codigo);
	}

	public QGCGlobalDto obtenerListaCampanias() {
		return this.getCampaniasDao().obtenerListaCampanias();
	}

	public QGCampaniasDao getCampaniasDao() {
		return campaniasDao;
	}

	public void setCampaniasDao(QGCampaniasDao campaniasDao) {
		this.campaniasDao = campaniasDao;
	}
}
