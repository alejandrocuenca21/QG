/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.servicio.impl;

import com.telefonica.ssnn.qg.administracion.segmentacion.dao.interfaz.QGSegmentacionesDao;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSegmentacionesBusquedaDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSegmentacionesDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.servicio.interfaz.QGSegmentacionesServicio;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.comun.dao.interfaz.QGSegmentosDao;

/**
 * @author mgvinuesa
 *
 */
public class QGSegmentacionesServicioImpl implements QGSegmentacionesServicio {

	private QGSegmentacionesDao segmentacionDAO;
	private QGSegmentosDao segmentosDao;
	/**
	 * Busqueda las segmentaciones por criterio
	 * @param busquedaSegmentacion criterios de busqueda
	 * @return Listado de segmentaciones obtenidas
	 */
	public QGCGlobalDto obtenerListaSegmentaciones(QGSegmentacionesBusquedaDto busquedaSegmentacion) {
		//Llama al dao para obtener la lista de segmentaciones
		busquedaSegmentacion.setActuacionBusqueda("A");
		return this.getSegmentacionDAO().obtenerListaSegmentaciones(busquedaSegmentacion);
	}

	/**
	 * Da de alta una segmentacion
	 * @param segmentacion datos de la segmentacion a crear
	 * @return resultado del alta.
	 */
	public QGCGlobalDto altaSegmentacion(QGSegmentacionesDto segmentacion) {
		segmentacion.setCodActuacion(QGConstantes.CODIGO_ALTA);
		return this.getSegmentacionDAO().modificarSegmentacion(segmentacion);
	}

	/**
	 * Da de baja una segmentacion
	 * @param segmentacion datos de la segmentacion a eliminar
	 * @return resultado de la baja
	 */
	public QGCGlobalDto bajaSegmentacion(QGSegmentacionesDto segmentacion) {
		segmentacion.setCodActuacion(QGConstantes.CODIGO_BAJA);
		return this.getSegmentacionDAO().modificarSegmentacion(segmentacion);
	}

	/**
	 * Obtiene el listado de codigos de segmento
	 * @return listado de segmentos
	 */
	public QGCGlobalDto obtenerCodigosSegmento(String unidad) {
		
		if (unidad.equals("01")){
			return this.getSegmentosDao().obtenerSegmentos();
		}
		else if (unidad.equals("02")){
			return this.getSegmentosDao().obtenerSegmentosMovil();
		}
		else{
			QGCGlobalDto fijos = this.getSegmentosDao().obtenerSegmentos();
			QGCGlobalDto moviles = this.getSegmentosDao().obtenerSegmentosMovil();
			QGCGlobalDto global = new QGCGlobalDto();
			
			for (int i=0;i<fijos.getlistaDatos().size();i++){
				global.getlistaDatos().add(fijos.getlistaDatos().get(i));
			}
			for (int i=0;i<moviles.getlistaDatos().size();i++){
				global.getlistaDatos().add(moviles.getlistaDatos().get(i));
			}
			for (int i=0;i<fijos.getlistaMensajes().size();i++){
				global.getlistaDatos().add(fijos.getlistaMensajes().get(i));
			}
			for (int i=0;i<moviles.getlistaMensajes().size();i++){
				global.getlistaDatos().add(moviles.getlistaMensajes().get(i));
			}

			return global;
		}
		
				 
	}

	/**
	 * Obtiene el listado de codigos del subsegmento
	 * @param valorCombo codigo del segmento para el filtrado
	 * @return listado de subsegmentos
	 */
	public QGCGlobalDto obtenerCodigosSubSegmento(String unidad,String valorCombo) {
		
		if (unidad.equals("01")){
			return this.getSegmentosDao().obtenerSubSegmentos(valorCombo);
		}
		else if (unidad.equals("02")){
			return this.getSegmentosDao().obtenerSubSegmentosMovil(valorCombo);
		}
		else{
			QGCGlobalDto fijos = this.getSegmentosDao().obtenerSubSegmentos(valorCombo);
			QGCGlobalDto moviles = this.getSegmentosDao().obtenerSubSegmentosMovil(valorCombo);
			QGCGlobalDto global = new QGCGlobalDto();
			
			for (int i=0;i<fijos.getlistaDatos().size();i++){
				global.getlistaDatos().add(fijos.getlistaDatos().get(i));
			}
			for (int i=0;i<moviles.getlistaDatos().size();i++){
				global.getlistaDatos().add(moviles.getlistaDatos().get(i));
			}
			for (int i=0;i<fijos.getlistaMensajes().size();i++){
				global.getlistaDatos().add(fijos.getlistaMensajes().get(i));
			}
			for (int i=0;i<moviles.getlistaMensajes().size();i++){
				global.getlistaDatos().add(moviles.getlistaMensajes().get(i));
			}

			return global;
		}	
	}
	
	/**
	 * Obtiene el listado de segmentaciones que pertenecen al historico
	 */
	public QGCGlobalDto obtenerHistorico(
			QGSegmentacionesBusquedaDto busquedaSegmentacion) {
		//Llama al dao para obtener la lista de segmentaciones del historico
		//Actuacion historico
		busquedaSegmentacion.setActuacionBusqueda("H");
		return this.getSegmentacionDAO().obtenerListaSegmentaciones(busquedaSegmentacion);
	}

	public QGSegmentacionesDao getSegmentacionDAO() {
		return segmentacionDAO;
	}

	public void setSegmentacionDAO(QGSegmentacionesDao segmentacionDAO) {
		this.segmentacionDAO = segmentacionDAO;
	}

	public QGSegmentosDao getSegmentosDao() {
		return segmentosDao;
	}

	public void setSegmentosDao(QGSegmentosDao segmentosDao) {
		this.segmentosDao = segmentosDao;
	}

	
	
}