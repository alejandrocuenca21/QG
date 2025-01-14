/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.consentimientos.servicio.impl;

import com.telefonica.ssnn.qg.administracion.consentimientos.dao.interfaz.QGConsentimientosDao;
import com.telefonica.ssnn.qg.administracion.consentimientos.dto.QGDetalleConsentimientosDto;
import com.telefonica.ssnn.qg.administracion.consentimientos.servicio.interfaz.QGConsentimientosServicio;

import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGTextoLegalDto;
import com.telefonica.ssnn.qg.comun.dao.interfaz.QGSegmentosDao;

/**
 * @author vsierra
 * 
 */
public class QGConsentimientosServicioImpl implements QGConsentimientosServicio {

	private QGConsentimientosDao consentimientosDao;
	private QGSegmentosDao segmentosDao;
	
	public QGCGlobalDto obtenerAmbito(String codigo) {
		return this.getConsentimientosDao().obtenerAmbito(codigo);
	}

	public QGCGlobalDto obtenerDetalleConsentimiento(String codigo) {
		return this.getConsentimientosDao().obtenerDetalleConsentimiento(codigo);
	}

	public QGCGlobalDto obtenerListaAmbitos() {
		return this.getConsentimientosDao().obtenerListaAmbitos();
	}

	public QGCGlobalDto obtenerListaConsentimientos() {
		return this.getConsentimientosDao().obtenerListaConsentimientos();
	}

	public QGCGlobalDto obtenerListaNivelAplicacion() {
		return this.getConsentimientosDao().obtenerListaNivelAplicacion();
	}

	public QGCGlobalDto obtenerListaSegmentos() {
		return this.getSegmentosDao().obtenerSegmentos();
	}

	public QGCGlobalDto obtenerListaTipoObjetos() {
		return this.getConsentimientosDao().obtenerListaTipoObjetos();
	}
	
	public QGCGlobalDto obtenerListaTipoConsentimientos() {
		return this.getConsentimientosDao().obtenerListaTipoConsentimientos();
	}

	public QGCGlobalDto obtenerNivelAplicacion(String codigo) {
		return this.getConsentimientosDao().obtenerNivelAplicacion(codigo);
	}

	public QGCGlobalDto obtenerTextoLegal(String codigo, Integer secuencial) {
		return this.getConsentimientosDao().obtenerTextoLegal(codigo, secuencial);
	}

	public QGCGlobalDto obtenerTipoObjeto(String codigo) {
		return this.getConsentimientosDao().obtenerTipoObjeto(codigo);
	}

	public QGCGlobalDto altaConsentimiento(QGDetalleConsentimientosDto detalleConsentimientosDto) {
		detalleConsentimientosDto.setActuacion(QGConstantes.CODIGO_ALTA);
		return this.getConsentimientosDao().modificarConsentimiento(detalleConsentimientosDto);
	}

	public QGCGlobalDto altaTextoLegal(QGTextoLegalDto QGTextoLegalDto) {
		QGTextoLegalDto.setCodActuacion(QGConstantes.CODIGO_ALTA);
		return this.getConsentimientosDao().altaTextoLegal(QGTextoLegalDto);
	}
	
	public QGCGlobalDto modificarTextoLegal(QGTextoLegalDto QGTextoLegalDto) {
		QGTextoLegalDto.setCodActuacion(QGConstantes.CODIGO_MODIFICAR);
		return this.getConsentimientosDao().altaTextoLegal(QGTextoLegalDto);
	}

	public QGCGlobalDto bajaConsentimiento(QGDetalleConsentimientosDto detalleConsentimientosDto) {
		detalleConsentimientosDto.setActuacion(QGConstantes.CODIGO_BAJA);
		return this.getConsentimientosDao().modificarConsentimiento(detalleConsentimientosDto);
	}

	public QGCGlobalDto modificarConsentimiento(
			QGDetalleConsentimientosDto detalleConsentimientosDto) {
		detalleConsentimientosDto.setActuacion(QGConstantes.CODIGO_MODIFICAR);
		return this.getConsentimientosDao().modificarConsentimiento(detalleConsentimientosDto);
	}

	public QGConsentimientosDao getConsentimientosDao() {
		return consentimientosDao;
	}

	public void setConsentimientosDao(QGConsentimientosDao consentimientosDao) {
		this.consentimientosDao = consentimientosDao;
	}

	public QGSegmentosDao getSegmentosDao() {
		return segmentosDao;
	}

	public void setSegmentosDao(QGSegmentosDao segmentosDao) {
		this.segmentosDao = segmentosDao;
	}
	
	
}
