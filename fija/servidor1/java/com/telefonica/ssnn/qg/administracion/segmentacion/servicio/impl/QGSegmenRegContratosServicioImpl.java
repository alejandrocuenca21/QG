package com.telefonica.ssnn.qg.administracion.segmentacion.servicio.impl;

import com.telefonica.ssnn.qg.administracion.segmentacion.dao.interfaz.QGSegmenRegContratosDao;
import com.telefonica.ssnn.qg.administracion.segmentacion.servicio.interfaz.QGSegmenRegContratosServicio;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.comun.dao.interfaz.QGSegmentosDao;

/**
 * @author jacastano
 *
 */
public class QGSegmenRegContratosServicioImpl implements QGSegmenRegContratosServicio {

	private QGSegmenRegContratosDao segmentacionDAO;
	private QGSegmentosDao segmentosDao;
	
	//Métodos
	public QGCGlobalDto obtenerContratos(String inActuacion) {
		// TODO Auto-generated method stub
		return this.getSegmentacionDAO().obtenerContratos(inActuacion);
	}

	//Getters & Setters

	public QGSegmentosDao getSegmentosDao() {
		return segmentosDao;
	}
	public void setSegmentosDao(QGSegmentosDao segmentosDao) {
		this.segmentosDao = segmentosDao;
	}
	public QGSegmenRegContratosDao getSegmentacionDAO() {
		return segmentacionDAO;
	}
	public void setSegmentacionDAO(QGSegmenRegContratosDao segmentacionDAO) {
		this.segmentacionDAO = segmentacionDAO;
	}
}
