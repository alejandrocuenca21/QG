/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.ubicacion.servicio.impl;

import com.telefonica.ssnn.qg.administracion.ubicacion.dao.interfaz.QGUbicacionDao;
import com.telefonica.ssnn.qg.administracion.ubicacion.dto.QGTiposUbicacionDto;
import com.telefonica.ssnn.qg.administracion.ubicacion.servicio.interfaz.QGUbicacionServicio;

import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author vsierra
 *
 */
public class QGUbicacionServicioImpl implements QGUbicacionServicio {
	
	private QGUbicacionDao ubicacionDao;

	public QGCGlobalDto altaTipoUbicacion(QGTiposUbicacionDto tiposUbicacionDto) {
		tiposUbicacionDto.setCodActuacion(QGConstantes.CODIGO_ALTA);
		return this.getUbicacionDao().modificarTiposUbicacion(tiposUbicacionDto);
		
	}

	public QGCGlobalDto bajaTipoUbicacion(QGTiposUbicacionDto tiposUbicacionDto) {
		tiposUbicacionDto.setCodActuacion(QGConstantes.CODIGO_BAJA);
		return this.getUbicacionDao().modificarTiposUbicacion(tiposUbicacionDto);
	}

	public QGCGlobalDto modificarTiposUbicacion(
			QGTiposUbicacionDto tiposUbicacionDto) {
		tiposUbicacionDto.setCodActuacion(QGConstantes.CODIGO_MODIFICAR);
		return this.getUbicacionDao().modificarTiposUbicacion(tiposUbicacionDto);
		
	}

	public QGCGlobalDto obtenerListaTiposUbicacion() {
		return this.getUbicacionDao().obtenerListaTiposUbicacion();
		}

	public QGCGlobalDto obtenerTiposUbicacion(String codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	public QGUbicacionDao getUbicacionDao() {
		return ubicacionDao;
	}

	public void setUbicacionDao(QGUbicacionDao ubicacionDao) {
		this.ubicacionDao = ubicacionDao;
	}
}
