/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.servicio.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.telefonica.ssnn.qg.administracion.segmentacion.dao.interfaz.QGSegmentacionesTraduccionDao;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSegmentacionesTraduccionDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.servicio.interfaz.QGSegmentacionesTraduccionServicio;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.comun.dao.interfaz.QGSegmentosDao;
import com.telefonica.ssnn.qg.util.QGCatalogoDto;

/**
 * @author mgvinuesa
 *
 */
public class QGSegmentacionesTraduccionServicioImpl implements QGSegmentacionesTraduccionServicio {

	private static final Logger logger = Logger
	.getLogger(QGSegmentacionesTraduccionServicioImpl.class);
	
	private QGSegmentacionesTraduccionDao segmentacionDAO;
	private QGSegmentosDao segmentosDao;
	
	public QGCGlobalDto altaSegmentacion(
			QGSegmentacionesTraduccionDto segmentacion) {
		segmentacion.setCodActuacion(QGConstantes.CODIGO_ALTA);
		return this.getSegmentacionDAO().gestionarTraduccion(segmentacion);
	}

	public QGCGlobalDto bajaSegmentacion(
			QGSegmentacionesTraduccionDto segmentacion) {
		segmentacion.setCodActuacion(QGConstantes.CODIGO_BAJA);
		return this.getSegmentacionDAO().gestionarTraduccion(segmentacion);
	}
	
	public QGCGlobalDto modificarSegmentacion(
			QGSegmentacionesTraduccionDto segmentacion) {
		segmentacion.setCodActuacion(QGConstantes.CODIGO_MODIFICAR);
		return this.getSegmentacionDAO().gestionarTraduccion(segmentacion);
	}
	
	public QGCGlobalDto obtenerHistorico(
			QGSegmentacionesTraduccionDto busquedaSegmentacion) {
		busquedaSegmentacion.setCodActuacion(QGConstantes.LISTADO_HISTORICO);
		return this.getSegmentacionDAO().gestionarTraduccion(busquedaSegmentacion);
	}

	public QGCGlobalDto obtenerListaSegmentaciones(
			QGSegmentacionesTraduccionDto busquedaSegmentacion) {
		busquedaSegmentacion.setCodActuacion(QGConstantes.LISTADO_CONSULTA);
		return this.getSegmentacionDAO().gestionarTraduccion(busquedaSegmentacion);
	}
	/**
	 * Obtiene los segmentos fijos
	 */
	public QGCGlobalDto obtenerCodigosSegmentoFijo() {
		return this.getSegmentosDao().obtenerSegmentos();
	}
	/**
	 * Obtiene los segmentos moviles
	 */
	public QGCGlobalDto obtenerCodigosSegmentoMovil() {
		return this.getSegmentosDao().obtenerSegmentosMovil();
	}
	/**
	 * Obtiene los codigos de subsegmento fijos
	 */
	public QGCGlobalDto obtenerCodigosSubSegmentoFijo(String valorCombo) {
		return this.getSegmentosDao().obtenerSubSegmentos(valorCombo);
	}


	/**
	 * Obtiene los codigos de subsegmento moviles
	 */
	public QGCGlobalDto obtenerCodigosSubSegmentoMovil(String valorCombo) {
		return this.getSegmentosDao().obtenerSubSegmentosMovil(valorCombo);
	}



	public QGSegmentacionesTraduccionDao getSegmentacionDAO() {
		return segmentacionDAO;
	}

	public void setSegmentacionDAO(QGSegmentacionesTraduccionDao segmentacionDAO) {
		this.segmentacionDAO = segmentacionDAO;
	}

	public QGSegmentosDao getSegmentosDao() {
		return segmentosDao;
	}

	public void setSegmentosDao(QGSegmentosDao segmentosDao) {
		this.segmentosDao = segmentosDao;
	}


	
	
	
	private QGCGlobalDto dummyComboFijo() {
		logger.info("DUMMY COMBO FIJO!!!!!!!!!!!!!!!!");
		QGCGlobalDto listaDummy = new QGCGlobalDto();
		List comboFijo = new ArrayList();
		
		for (int i = 1; i <= 5; i++) {
			QGCatalogoDto descripcionesDto = new QGCatalogoDto();
			
			descripcionesDto.setCodigo("CF - " + String.valueOf(i));
			descripcionesDto.setDescripcion("CF - Desc: " + String.valueOf(i));

			comboFijo.add(descripcionesDto);
		}
		
		listaDummy.setlistaDatos(comboFijo);
		return listaDummy;
	}

	private QGCGlobalDto dummyComboMovil() {
		logger.info("DUMMY COMBO MOVIL!!!!!!!!!!!!!!!!");
		QGCGlobalDto listaDummy = new QGCGlobalDto();
		List comboFijo = new ArrayList();
		
		for (int i = 1; i <= 5; i++) {
			QGCatalogoDto descripcionesDto = new QGCatalogoDto();
			
			descripcionesDto.setCodigo("CM - " + String.valueOf(i));
			descripcionesDto.setDescripcion("CM - Desc: " + String.valueOf(i));

			comboFijo.add(descripcionesDto);
		}
		
		listaDummy.setlistaDatos(comboFijo);
		return listaDummy;
	}
	
	private QGCGlobalDto dummyComboSubFijo(String valorCombo) {
		QGCGlobalDto listaDummy = new QGCGlobalDto();
		List comboFijo = new ArrayList();

		for (int i = 1; i <= 5; i++) {
			QGCatalogoDto descripcionesDto = new QGCatalogoDto();
			
			descripcionesDto.setCodigo(valorCombo + "SCF - " + String.valueOf(i));
			descripcionesDto.setDescripcion(valorCombo + "SCF - Desc: " + String.valueOf(i));

			comboFijo.add(descripcionesDto);
		}
		
		listaDummy.setlistaDatos(comboFijo);
		return listaDummy;
		
	}
	
	private QGCGlobalDto dummyComboSubMovil(String valorCombo) {
		QGCGlobalDto listaDummy = new QGCGlobalDto();
		List comboFijo = new ArrayList();

		for (int i = 1; i <= 5; i++) {
			QGCatalogoDto descripcionesDto = new QGCatalogoDto();
			
			descripcionesDto.setCodigo(valorCombo + "SCM - " + String.valueOf(i));
			descripcionesDto.setDescripcion(valorCombo + "SCM - Desc: " + String.valueOf(i));

			comboFijo.add(descripcionesDto);
		}
		
		listaDummy.setlistaDatos(comboFijo);
		return listaDummy;
		
	}

	private QGCGlobalDto dummyListado() {
		QGCGlobalDto listaDummy = new QGCGlobalDto();
		List comboFijo = new ArrayList();

		for (int i = 1; i <= 15; i++) {
			QGSegmentacionesTraduccionDto elemento = crearElemento(i);
			comboFijo.add(elemento);
		}
		
		listaDummy.setlistaDatos(comboFijo);
		return listaDummy;
	}

	private QGSegmentacionesTraduccionDto crearElemento(int i) {
		QGSegmentacionesTraduccionDto elemento = new QGSegmentacionesTraduccionDto("codActuacion"+i,
				"AU", "Autonomos",
				"GC", "GRANDES CLIENTES",
				"AU", "Autonomos",
				"GC","GRANDES CLIENTES",
				"20.03.2011", "24.03.2011","",
				"20.03.2011","","Manuel"+i,
				"Manuel"+i,"Manuel"+i,"planCuentas"+i);
				
		
		return elemento;
	}
	
	
}