/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.medios.servicio.impl;

import com.telefonica.ssnn.qg.administracion.medios.dao.interfaz.QGMediosDao;
import com.telefonica.ssnn.qg.administracion.medios.dto.QGMediosComunicacionDto;
import com.telefonica.ssnn.qg.administracion.medios.servicio.interfaz.QGMediosServicio;

import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGDetalleCDDto;

/**
 * @author vsierra
 *
 */
public class QGMediosServicioImpl implements QGMediosServicio {
	
	private QGMediosDao mediosDao;

	public QGCGlobalDto altaMedioComunicacion(
			QGMediosComunicacionDto mediosComunicacionDto) {
		mediosComunicacionDto.setCodActuacion(QGConstantes.CODIGO_ALTA);
		return this.getMediosDao().modificarMediosComunicacion(mediosComunicacionDto);
		
	}

	public QGCGlobalDto bajaMedioComunicacion(
			QGMediosComunicacionDto mediosComunicacionDto) {
		mediosComunicacionDto.setCodActuacion(QGConstantes.CODIGO_BAJA);
		return this.getMediosDao().modificarMediosComunicacion(mediosComunicacionDto);
		
	}

	public QGCGlobalDto modificarMediosComunicacion(
			QGMediosComunicacionDto mediosComunicacionDto) {
		mediosComunicacionDto.setCodActuacion(QGConstantes.CODIGO_MODIFICAR);
		return this.getMediosDao().modificarMediosComunicacion(mediosComunicacionDto);
		
	}
	
	public QGCGlobalDto buscadorMediosCosenDer(QGDetalleCDDto busqueda) {
		busqueda.setInAscInfCox("C");
		return this.getMediosDao().buscadorMediosCosenDer(busqueda);
	}

	public QGCGlobalDto obtenerListaMediosComunicacion() {
		return this.getMediosDao().obtenerListaMediosComunicacion();
		}

	public QGCGlobalDto obtenerMedioComunicacion(String codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	public QGMediosDao getMediosDao() {
		return mediosDao;
	}

	public void setMediosDao(QGMediosDao mediosDao) {
		this.mediosDao = mediosDao;
	}
}
