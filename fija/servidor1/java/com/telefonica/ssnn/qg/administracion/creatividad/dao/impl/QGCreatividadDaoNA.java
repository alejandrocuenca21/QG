package com.telefonica.ssnn.qg.administracion.creatividad.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.administracion.creatividad.dao.interfaz.QGCreatividadDao;
import com.telefonica.ssnn.qg.administracion.creatividad.dto.QGCreatividadDto;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;
import com.telefonica.ssnn.qg.util.QGUtilidadesNegocioUtils;

public class QGCreatividadDaoNA extends QGBaseDao implements QGCreatividadDao {

	private static final Logger logger = Logger
			.getLogger(QGCreatividadDaoNA.class);
	

	/**
	 * Funcion que gestion los encartes
	 */
	public QGCGlobalDto gestionarCreatividad(QGCreatividadDto creatividadDto) {
		QGNpaNA servicio = null;
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		ArrayList listaDatos = new ArrayList();

		try {
			servicio = QGNpaNA.obtenerServicio(
					QGIdentificadoresDescriptores.DESCRIPTOR_GESTION_CREATIVIDAD,
					this.obtenerUsuarioLogado());

			// Seteamos la copy de entrada
			servicio.setValor("ENTRADA.IN-ACTUACION", creatividadDto.getAccion());
			servicio.setValor("ENTRADA.CO-DRH-UNF-LOPD", creatividadDto
					.getCodDerecho());
			servicio.setValor("ENTRADA.CO-LIN-NGC", creatividadDto
					.getCodLineaNegocio());
			servicio.setValor("ENTRADA.CO-SEGMENTO", creatividadDto
					.getCodSegmento());
			servicio.setValor("ENTRADA.CO_CREATIVIDAD", creatividadDto
					.getDescCreatividad());

			servicio.setValor("ENTRADA.FX-INI-VIGENCIA",
					QGUtilidadesFechasUtils.formatearFechaParaCopy(creatividadDto
							.getFecIniVigencia(),
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));

			servicio.setValor("ENTRADA.FX-FIN-VIGENCIA",
					QGUtilidadesFechasUtils.formatearFechaParaCopy(creatividadDto
							.getFecFinVigencia(),
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));

			// Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();

			// Si es una consulta o historico...
			if (creatividadDto.getAccion() != null
					&& (creatividadDto.getAccion().equals(
							QGConstantes.LISTADO_CONSULTA) || creatividadDto
							.getAccion().equals(QGConstantes.LISTADO_HISTORICO))) {

				for (int i = 1; i <= servicio.getValorAsInt("SALIDA.OCU-NU")
						.intValue(); i++) {
					creatividadDto = new QGCreatividadDto();

					creatividadDto.setCodDerecho(servicio.getValorAsString(
							"CO-DRH-UNF-LOPD", i));
					creatividadDto.setDescDerecho(servicio.getValorAsString(
							"DS-CSM-DRH-INTERNO", i));
					creatividadDto.setCodLineaNegocio(servicio.getValorAsString(
							"CO-LIN-NGC", i));
					//Obtenemos la descripcion segun el codigo (estatico)
					creatividadDto.setDescLineaNegocio(QGUtilidadesNegocioUtils.obtenerLineaNegocio(creatividadDto.getCodLineaNegocio()));
					creatividadDto.setCodSegmento(servicio.getValorAsString(
							"CO-SEGMENTO", i));
					creatividadDto.setDescSegmento(servicio.getValorAsString(
							"DS-SEGMENTO-UNM", i));
					creatividadDto.setDescCreatividad(servicio.getValorAsString(
							"CO_CREATIVIDAD", i));

					creatividadDto.setFecAlta(QGUtilidadesFechasUtils
							.formatearTimeStampDesdeCopy(servicio
									.getValorAsString("IT-ALTA", i),
									QGUtilidadesFechasUtils.FORMATO_FECHA_1));

					creatividadDto.setFecMod(QGUtilidadesFechasUtils
							.formatearTimeStampDesdeCopy(servicio
									.getValorAsString("IT-ULT-MOD", i),
									QGUtilidadesFechasUtils.FORMATO_FECHA_1));

					creatividadDto.setUsuarioAlta(servicio.getValorAsString(
							"CO-USUARIO-ALT", i));
					creatividadDto.setUsuarioMod(servicio.getValorAsString(
							"CO-USUARIO-MOD", i));

					creatividadDto.setFecIniVigencia(QGUtilidadesFechasUtils
							.formatearFechaDesdeCopy(servicio.getValorAsString(
									"FX-INI-VIGENCIA", i),
									QGUtilidadesFechasUtils.FORMATO_FECHA_1));
					creatividadDto.setFecFinVigencia(QGUtilidadesFechasUtils
							.formatearFechaDesdeCopy(servicio.getValorAsString(
									"FX-FIN-VIGENCIA", i),
									QGUtilidadesFechasUtils.FORMATO_FECHA_1));

					listaDatos.add(creatividadDto);
				}
				
			}
			qGCGlobalDto.setlistaDatos(listaDatos);
		} catch (NAWRException e) {
			logger.error(e.getMessage());
			QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e,
					QGIdentificadoresDescriptores.DESCRIPTOR_GESTION_CREATIVIDAD);

		} finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return qGCGlobalDto;

	}


}
