package com.telefonica.ssnn.qg.administracion.creatividad.servicio.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.telefonica.ssnn.qg.administracion.creatividad.dao.interfaz.QGCreatividadDao;
import com.telefonica.ssnn.qg.administracion.creatividad.dto.QGCreatividadDto;
import com.telefonica.ssnn.qg.administracion.creatividad.servicio.interfaz.QGCreatividadServicio;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.comun.dao.interfaz.QGDerechosDao;
import com.telefonica.ssnn.qg.comun.dao.interfaz.QGSegmentosDao;
import com.telefonica.ssnn.qg.util.QGCatalogoDto;
import com.telefonica.ssnn.qg.util.QGDerechosDto;

public class QGCreatividadServicioImpl implements QGCreatividadServicio {

	/**
	 * Interfaz del dao de creatividad
	 */
	private QGCreatividadDao creatividadDao;
	
	/**
	 * Interfaz del dao de derechos
	 */
	private QGDerechosDao derechosDao;
	
	/**
	 * Interfaz de segmentos
	 */
	private QGSegmentosDao segmentosDao;
	/**
	 * Consulta la lista de creatividad encartes
	 */
	public QGCGlobalDto consultarCreatividadEncartes() {
		QGCreatividadDto creatividad = new QGCreatividadDto();
		// Codigo de actuacion C
		creatividad.setAccion(QGConstantes.LISTADO_CONSULTA);
		return this.getCreatividadDao().gestionarCreatividad(creatividad);
	}
	/**
	 * Gestion de creatividad
	 */
	public void modificarCreatividad(QGCreatividadDto creatividadDto) {
		this.getCreatividadDao().gestionarCreatividad(creatividadDto);	
	}
	/**
	 * Obtiene los derechos
	 */
	public QGCGlobalDto obtenerDerechos(String lineaNegocio) {
		QGDerechosDto derecho = new QGDerechosDto();
		derecho.setCodActuacion(QGConstantes.LISTADO_CONSULTA);
		derecho.setCodLineaNegocio(lineaNegocio);
		return this.getDerechosDao().gestionarDerechos(derecho);
	}
	/**
	 * Obtiene los segmentos
	 * @return
	 */
	public QGCGlobalDto obtenerSegmentos() {

		/*Se llamará al QGF0066 para los segmentos 
		 * (para el caso del código GP, la web mostrará GP/RES 
		 * pero a la hora del alta al servicio se enviará GP, no se mostrarán los códigos TE ni NT) */

		QGCGlobalDto resultado = this.getSegmentosDao().obtenerSegmentos();
		List list = resultado.getlistaDatos();
		List listaSalida = new ArrayList();
		for (int i = 0; i < list.size(); i++) {

			String codigo = ((QGCatalogoDto)list.get(i)).getCodigo().trim();

			if(!((HashMap)QGConstantes.CODIGOS_DE_SEGMENTOS_NO_MOSTRADOS).containsKey(codigo)){
				if(codigo.equals(QGConstantes.CODIGO_SEGMENTO_GP_INICIAL)){
					((QGCatalogoDto)list.get(i)).setCodigo(QGConstantes.CODIGO_SEGMENTO_GP_SUSTITUTO);
				}							
				listaSalida.add(list.get(i));	
			}
		}				
		resultado.setlistaDatos(listaSalida);
		return resultado;
	}
	

	public QGCreatividadDao getCreatividadDao() {
		return creatividadDao;
	}

	public void setCreatividadDao(QGCreatividadDao creatividadDao) {
		this.creatividadDao = creatividadDao;
	}
	public QGDerechosDao getDerechosDao() {
		return derechosDao;
	}
	public void setDerechosDao(QGDerechosDao derechosDao) {
		this.derechosDao = derechosDao;
	}
	public QGSegmentosDao getSegmentosDao() {
		return segmentosDao;
	}
	public void setSegmentosDao(QGSegmentosDao segmentosDao) {
		this.segmentosDao = segmentosDao;
	}



	

	
}
