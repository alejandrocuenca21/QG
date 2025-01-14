/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.servicio.impl;


import com.telefonica.ssnn.qg.administracion.segmentacion.dao.interfaz.QGSegmentacionesPresegDao;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaPresegmentacionAdminDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaPresegmentacionDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaSegmentacionesPresegDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.servicio.interfaz.QGSegmentacionesPresegServicio;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
import com.telefonica.ssnn.qg.comun.dao.interfaz.QGSegmentosDao;

/**
 * @author jacastano
 *
 */
public class QGSegmentacionesPresegServicioImpl implements QGSegmentacionesPresegServicio {

	private QGSegmentacionesPresegDao segmentacionDAO;
	private QGSegmentosDao segmentosDao;
	
	//Métodos
	
	public QGCGlobalPagingDto obtenerListaPresegmentaciones(
			QGEntradaSegmentacionesPresegDto busquedaPresegmentacion) {
		return this.getSegmentacionDAO().gestionarPresegmentacion(busquedaPresegmentacion);
	}
	
	public QGCGlobalPagingDto obtenerDatosOfAtencion(QGEntradaPresegmentacionDto entrada){
		return this.getSegmentacionDAO().obtenerDatosOfAtencion(entrada);
	}
	
	public QGCGlobalPagingDto obtenerDatosTandem(QGEntradaPresegmentacionDto entrada) {
		return this.getSegmentacionDAO().obtenerTandem(entrada);
	}
	
	public QGCGlobalDto gestionAdministracion(QGEntradaPresegmentacionAdminDto entrada) {
		return this.getSegmentacionDAO().gestionAdministracion(entrada);
	}
	
	public QGCGlobalDto obtenerSubsegmentos(QGEntradaSegmentacionesPresegDto busquedaSegmentacion){
		return this.getSegmentacionDAO().obtenerSubsegmentos(busquedaSegmentacion);
	}
	
	public QGCGlobalDto operarPreseg(QGEntradaSegmentacionesPresegDto entrada){
		return this.getSegmentacionDAO().operarPreseg(entrada);
	}

	//Getters & Setters
	public QGSegmentacionesPresegDao getSegmentacionDAO() {
		return segmentacionDAO;
	}
	public void setSegmentacionDAO(QGSegmentacionesPresegDao segmentacionDAO) {
		this.segmentacionDAO = segmentacionDAO;
	}
	public QGSegmentosDao getSegmentosDao() {
		return segmentosDao;
	}
	public void setSegmentosDao(QGSegmentosDao segmentosDao) {
		this.segmentosDao = segmentosDao;
	}
}
