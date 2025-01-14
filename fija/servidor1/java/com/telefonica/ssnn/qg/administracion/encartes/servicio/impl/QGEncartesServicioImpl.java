package com.telefonica.ssnn.qg.administracion.encartes.servicio.impl;

import java.util.ArrayList;
import java.util.List;

import com.telefonica.ssnn.qg.administracion.encartes.dao.interfaz.QGEncartesDao;
import com.telefonica.ssnn.qg.administracion.encartes.dto.QGPOEncartesDto;
import com.telefonica.ssnn.qg.administracion.encartes.servicio.interfaz.QGEncartesServicio;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.comun.dao.interfaz.QGDerechosDao;
import com.telefonica.ssnn.qg.comun.dao.interfaz.QGSegmentosDao;
import com.telefonica.ssnn.qg.util.QGCatalogoDto;
import com.telefonica.ssnn.qg.util.QGDerechosDto;

/**
 * Implementacion del servicio para encartes.
 * 
 * @author rgsimon
 * 
 */
public class QGEncartesServicioImpl implements QGEncartesServicio {

	/**
	 * Interfaz del dao de derechos
	 */
	private QGDerechosDao derechosDao;

	private QGEncartesDao encartesDao;

	private QGSegmentosDao segmentosDao;

	/**
	 * Consulta el historico de encartes
	 * 
	 * @return
	 */
	public QGCGlobalDto consultarHistoricoPoEncartes(QGPOEncartesDto encartesDto) {
		// Codigo de actuacion H
		encartesDto.setAccion(QGConstantes.LISTADO_HISTORICO);
		return this.getEncartesDao().gestionEncartes(encartesDto);

	}

	/**
	 * Consulta los encartes
	 */
	public QGCGlobalDto consultarPoEncartes() {
		QGPOEncartesDto encartes = new QGPOEncartesDto();
		// Codigo de actuacion C
		encartes.setAccion(QGConstantes.LISTADO_CONSULTA);
		return this.getEncartesDao().gestionEncartes(encartes);
	}

	/**
	 * Modificacion / Baja / Alta de encarte
	 */
	public void gestionarPoEncartes(QGPOEncartesDto encartesDto) {
		this.getEncartesDao().gestionEncartes(encartesDto);
	}

	/**
	 * Obtencion de derechos
	 */
	public QGCGlobalDto obtenerDerechos(String lineaNegocio) {
		QGDerechosDto derecho = new QGDerechosDto();
		derecho.setCodActuacion(QGConstantes.LISTADO_CONSULTA);
		derecho.setCodLineaNegocio(lineaNegocio);
		return this.getDerechosDao().gestionarDerechos(derecho );

	}

	/**
	 * Obtenecion de segmentos
	 */
	public QGCGlobalDto obtenerSegmentos() {

		/*
		 * Se llamará al QGF0066 para los segmentos (para el caso del código GP,
		 * la web mostrará GP/RES pero a la hora del alta al servicio se enviará
		 * GP, no se mostrarán los códigos TE ni NT)
		 */

		QGCGlobalDto resultado = this.getSegmentosDao().obtenerSegmentos();
		List list = resultado.getlistaDatos();
		List listaSalida = new ArrayList();
		for (int i = 0; i < list.size(); i++) {

			String codigo = ((QGCatalogoDto) list.get(i)).getCodigo().trim();

			if (!QGConstantes.CODIGOS_DE_SEGMENTOS_NO_MOSTRADOS
					.containsKey(codigo)) {
				if (codigo.equals(QGConstantes.CODIGO_SEGMENTO_GP_INICIAL)) {
					((QGCatalogoDto) list.get(i))
							.setCodigo(QGConstantes.CODIGO_SEGMENTO_GP_SUSTITUTO);
				}
				listaSalida.add(list.get(i));
			}

		}
		resultado.setlistaDatos(listaSalida);
		return resultado;
	}

	public QGEncartesDao getEncartesDao() {
		return encartesDao;
	}

	public void setEncartesDao(QGEncartesDao encartesDao) {
		this.encartesDao = encartesDao;
	}

	public QGSegmentosDao getSegmentosDao() {
		return segmentosDao;
	}

	public void setSegmentosDao(QGSegmentosDao segmentosDao) {
		this.segmentosDao = segmentosDao;
	}

	public QGDerechosDao getDerechosDao() {
		return derechosDao;
	}

	public void setDerechosDao(QGDerechosDao derechosDao) {
		this.derechosDao = derechosDao;
	}
}
