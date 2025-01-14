package com.telefonica.ssnn.qg.administracion.encartes.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.administracion.encartes.dao.interfaz.QGEncartesDao;
import com.telefonica.ssnn.qg.administracion.encartes.dto.QGPOEncartesDto;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;
import com.telefonica.ssnn.qg.util.QGUtilidadesNegocioUtils;

/**
 * Dao para la administracion de encartes
 * 
 * @author rgsimon
 * 
 */
public class QGEncartesDaoNA extends QGBaseDao implements QGEncartesDao {

	private static final Logger logger = Logger
			.getLogger(QGEncartesDaoNA.class);

	/**
	 * Funcion que gestion los encartes
	 */
	public QGCGlobalDto gestionEncartes(QGPOEncartesDto encarteDto) {
		QGNpaNA servicio = null;
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		ArrayList listaDatos = new ArrayList();

		try {
			servicio = QGNpaNA.obtenerServicio(
					QGIdentificadoresDescriptores.DESCRIPTOR_GESTION_ENCARTES,
					this.obtenerUsuarioLogado());

			// Seteamos la copy de entrada
			servicio.setValor("ENTRADA.IN-ACTUACION", encarteDto.getAccion());
			servicio.setValor("ENTRADA.CO-DRH-UNF-LOPD", encarteDto
					.getCodDerecho());
			servicio.setValor("ENTRADA.CO-LIN-NGC", encarteDto
					.getCodLineaNegocio());
			servicio.setValor("ENTRADA.CO-SEGMENTO", encarteDto
					.getCodSegmento());

			servicio.setValor("ENTRADA.FX-INI-VIGENCIA",
					QGUtilidadesFechasUtils.formatearFechaParaCopy(encarteDto
							.getFecIniVigencia(),
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));

			servicio.setValor("ENTRADA.FX-FIN-VIGENCIA",
					QGUtilidadesFechasUtils.formatearFechaParaCopy(encarteDto
							.getFecFinVigencia(),
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));

			// Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();

			// Si es una consulta o historico...
			if (encarteDto.getAccion() != null
					&& (encarteDto.getAccion().equals(
							QGConstantes.LISTADO_CONSULTA) || encarteDto
							.getAccion().equals(QGConstantes.LISTADO_HISTORICO))) {

				for (int i = 1; i <= servicio.getValorAsInt("SALIDA.OCU-NU")
						.intValue(); i++) {
					encarteDto = new QGPOEncartesDto();

					encarteDto.setCodDerecho(servicio.getValorAsString(
							"CO-DRH-UNF-LOPD", i));
					encarteDto.setDescDerecho(servicio.getValorAsString(
							"DS-CSM-DRH-INTERNO", i));
					encarteDto.setCodLineaNegocio(servicio.getValorAsString(
							"CO-LIN-NGC", i));
					
					//Obtenemos la descripcion segun el codigo (estatico)
					encarteDto.setDescLineaNegocio(QGUtilidadesNegocioUtils.obtenerLineaNegocio(encarteDto.getCodLineaNegocio()));
				
					
					
					encarteDto.setCodSegmento(servicio.getValorAsString(
							"CO-SEGMENTO", i));
					encarteDto.setDescSegmento(servicio.getValorAsString(
							"DS-SEGMENTO-UNM", i));

					encarteDto.setFecAlta(QGUtilidadesFechasUtils
							.formatearTimeStampDesdeCopy(servicio
									.getValorAsString("IT-ALTA", i),
									QGUtilidadesFechasUtils.FORMATO_FECHA_1));
					
					encarteDto.setFecAltaDate(QGUtilidadesFechasUtils
							.obtenerTimeStampDesdeCopy(servicio
									.getValorAsString("IT-ALTA", i)));

					encarteDto.setFecMod(QGUtilidadesFechasUtils
							.formatearTimeStampDesdeCopy(servicio
									.getValorAsString("IT-ULT-MOD", i),
									QGUtilidadesFechasUtils.FORMATO_FECHA_1));
					
					encarteDto.setFecModDate(QGUtilidadesFechasUtils
							.obtenerTimeStampDesdeCopy(servicio
									.getValorAsString("IT-ULT-MOD", i)));

					encarteDto.setUsuarioAlta(servicio.getValorAsString(
							"CO-USUARIO-ALT", i));
					encarteDto.setUsuarioMod(servicio.getValorAsString(
							"CO-USUARIO-MOD", i));

					encarteDto.setFecIniVigencia(QGUtilidadesFechasUtils
							.formatearFechaDesdeCopy(servicio.getValorAsString(
									"FX-INI-VIGENCIA", i),
									QGUtilidadesFechasUtils.FORMATO_FECHA_1));
					encarteDto.setFecFinVigencia(QGUtilidadesFechasUtils
							.formatearFechaDesdeCopy(servicio.getValorAsString(
									"FX-FIN-VIGENCIA", i),
									QGUtilidadesFechasUtils.FORMATO_FECHA_1));

					listaDatos.add(encarteDto);
				}
				
			}
			qGCGlobalDto.setlistaDatos(listaDatos);
		} catch (NAWRException e) {
			logger.error(e.getMessage());
			QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e,
					QGIdentificadoresDescriptores.DESCRIPTOR_GESTION_ENCARTES);

		} finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return qGCGlobalDto;

	}

}
