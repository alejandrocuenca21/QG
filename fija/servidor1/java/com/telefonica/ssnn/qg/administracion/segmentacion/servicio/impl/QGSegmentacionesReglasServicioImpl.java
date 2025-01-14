package com.telefonica.ssnn.qg.administracion.segmentacion.servicio.impl;


import com.telefonica.ssnn.qg.administracion.segmentacion.dao.interfaz.QGSegmentacionesReglasDao;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaSegmentacionesReglasDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.servicio.interfaz.QGSegmentacionesReglasServicio;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.comun.dao.interfaz.QGSegmentosDao;

/**
 * @author jacastano
 *
 */
public class QGSegmentacionesReglasServicioImpl implements QGSegmentacionesReglasServicio {

	private QGSegmentacionesReglasDao segmentacionDAO;
	private QGSegmentosDao segmentosDao;
	
	//Métodos

	public QGCGlobalDto obtenerDatosReglas(String actuacion){
		return this.getSegmentacionDAO().obtenerDatosReglas(actuacion);
	}
	
	public QGCGlobalDto obtenerListaReglas(QGEntradaSegmentacionesReglasDto busquedaSegmentacion){
		return this.getSegmentacionDAO().obtenerListaReglas(busquedaSegmentacion);
	}
	
	public QGCGlobalDto obtenerListaReglasSeg(QGEntradaSegmentacionesReglasDto busquedaSegmentacion) {
		return this.getSegmentacionDAO().obtenerListaReglasSeg(busquedaSegmentacion);	
	}

	public QGCGlobalDto obtenerListaReglasTotal(QGEntradaSegmentacionesReglasDto busquedaSegmentacion) {
		return this.getSegmentacionDAO().obtenerListaReglasTotal(busquedaSegmentacion);	
	}	
	
	public QGCGlobalDto operarReglas(QGEntradaSegmentacionesReglasDto entrada){
		return this.getSegmentacionDAO().operarReglas(entrada);	
	}
	
	//Getters & Setters

	public QGSegmentosDao getSegmentosDao() {
		return segmentosDao;
	}
	public void setSegmentosDao(QGSegmentosDao segmentosDao) {
		this.segmentosDao = segmentosDao;
	}

	public QGSegmentacionesReglasDao getSegmentacionDAO() {
		return segmentacionDAO;
	}

	public void setSegmentacionDAO(QGSegmentacionesReglasDao segmentacionDAO) {
		this.segmentacionDAO = segmentacionDAO;
	}
}
