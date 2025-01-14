/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.administracion.segmentacion.dao.interfaz.QGSegmentacionesTraduccionDao;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSegmentacionesTraduccionDto;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;
import com.telefonica.ssnn.qg.util.QGUtilidadesNegocioUtils;

/**
 * @author mgvinuesa
 * 
 */
public class QGSegmentacionesTraduccionDaoNA extends QGBaseDao implements
		QGSegmentacionesTraduccionDao {

	private static final Logger logger = Logger
			.getLogger(QGSegmentacionesTraduccionDaoNA.class);

	public QGCGlobalDto gestionarTraduccion(
			QGSegmentacionesTraduccionDto segmentacion) {
		QGNpaNA servicio = null;
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		ArrayList listaDatos = new ArrayList();

		try {
			servicio = QGNpaNA
					.obtenerServicio(
							QGIdentificadoresDescriptores.DESCRIPTOR_SEGMENTACIONES_TRAD,
							this.obtenerUsuarioLogado());
			// Seteamos la copy de entrada

			servicio.setValor("ENTRADA.IN-ACTUACION", segmentacion
					.getCodActuacion());

			// Seteamos la copy de entrada
			servicio.setValor("ENTRADA.CO_SEGMENTO", segmentacion
					.getCodigoSegmentoFijo());
			servicio.setValor("ENTRADA.CO_SUBSEGMENTO", segmentacion
					.getCodigoSubSegmentoFijo());
			servicio.setValor("ENTRADA.CO_SEGMENTO_UNM", segmentacion
					.getCodigoSegmentoMovil());
			servicio.setValor("ENTRADA.CO_SUBSEGMENTO_UNM", segmentacion
					.getCodigoSubSegmentoMovil());

			servicio.setValor("ENTRADA.IN-SEGM-VALOR", segmentacion
					.getPlanCuentas());
			servicio.setValor("ENTRADA.FX_INI_VIGENCIA",
					QGUtilidadesFechasUtils.formatearFechaParaCopy(segmentacion
							.getFechaIniVigencia(),
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			servicio.setValor("ENTRADA.FX_FIN_VIGENCIA",
					QGUtilidadesFechasUtils.formatearFechaParaCopy(segmentacion
							.getFechaFinVigencia(),
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			servicio.setValor("ENTRADA.FX_BAJA", QGUtilidadesFechasUtils
					.formatearFechaParaCopy(segmentacion
							.getFechaFinVigenciaBaja(),
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));

			boolean continuar = true;
			while (continuar) {

				// Ejecutamos la transaccion del servicio.
				servicio.ejecutarTransaccion();

				// Si es una consulta o historico...
				if (segmentacion.getCodActuacion() != null
						&& (segmentacion.getCodActuacion().equals(
								QGConstantes.LISTADO_CONSULTA) || segmentacion
								.getCodActuacion().equals(
										QGConstantes.LISTADO_HISTORICO))) {

					// Hay que comprobar la paginacion

					// comprobamos la condicion del bucle.
					if ("N".equalsIgnoreCase(servicio
							.getValorAsString("IND-PGN-IN"))
							|| servicio.getValorAsInt("SALIDA.OCU-NU")
									.intValue() == 0) {
						continuar = false;
					}

					// Recuperamos del servicio
					QGSegmentacionesTraduccionDto segmentacionesDto = null;
					for (int i = 1; i <= servicio.getValorAsInt("SALIDA.OCU-NU")
							.intValue(); i++) {
						segmentacionesDto = new QGSegmentacionesTraduccionDto();

						segmentacionesDto
								.setFechaIniVigencia(QGUtilidadesFechasUtils
										.formatearFechaDesdeCopy(
												servicio.getValorAsString(
														"FX-INI-VIGENCIA", i),
												QGUtilidadesFechasUtils.FORMATO_FECHA_1));
						segmentacionesDto
								.setFechaFinVigencia(QGUtilidadesFechasUtils
										.formatearFechaDesdeCopy(
												servicio.getValorAsString(
														"FX-FIN-VIGENCIA", i),
												QGUtilidadesFechasUtils.FORMATO_FECHA_1));

						segmentacionesDto.setCodigoSegmentoFijo(servicio
								.getValorAsString("CO-SEGMENTO", i));
						segmentacionesDto.setCodigoSegmentoMovil(servicio
								.getValorAsString("CO-SEGMENTO-UNM", i));
						segmentacionesDto.setCodigoSubSegmentoFijo(servicio
								.getValorAsString("CO-SUBSEGMENTO", i));
						segmentacionesDto.setCodigoSubSegmentoMovil(servicio
								.getValorAsString("CO-SUBSEGMENTO-UNM", i));

						segmentacionesDto.setDescSegmentoFijo(servicio
								.getValorAsString("DS-SEGMENTO", i));
						segmentacionesDto.setDescSegmentoMovil(servicio
								.getValorAsString("DS-SEGMENTO-UNM", i));
						segmentacionesDto.setDescSubSegmentoFijo(servicio
								.getValorAsString("DS-SUBSEGMENTO", i));
						segmentacionesDto.setDescSubSegmentoMovil(servicio
								.getValorAsString("DS-SUBSEGMENTO-UNM", i));

						segmentacionesDto
								.setFechaAlta(QGUtilidadesFechasUtils
										.formatearTimeStampDesdeCopy(
												servicio.getValorAsString(
														"IT-ALTA", i),
												QGUtilidadesFechasUtils.FORMATO_FECHA_1));
						segmentacionesDto
								.setFechaMod(QGUtilidadesFechasUtils
										.formatearTimeStampDesdeCopy(
												servicio.getValorAsString(
														"IT-ULT-MOD", i),
												QGUtilidadesFechasUtils.FORMATO_FECHA_1));

						segmentacionesDto.setPlanCuentas(servicio
								.getValorAsString("IN-SEGM-VALOR", i));
						segmentacionesDto.setUsuarioAlta(servicio
								.getValorAsString("CO-USUARIO-ALT", i));
						segmentacionesDto.setUsuarioMod(servicio
								.getValorAsString("CO-USUARIO-MOD", i));

						listaDatos.add(segmentacionesDto);
					}
					// cabecera de la siguiente llamada.
					servicio.setValor("ENTRADA.PGN-TX", servicio
							.getValorAsString("SALIDA.PGN-TX"));

				}else{
					continuar = false;
				}

			}
			qGCGlobalDto.setlistaDatos(listaDatos);
		} catch (NAWRException e) {
			logger.error(e.getMessage());
			QGUtilidadesNegocioUtils
					.tratarExcepcionesServicio(
							e,
							QGIdentificadoresDescriptores.DESCRIPTOR_SEGMENTACIONES_TRAD);

		} finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return qGCGlobalDto;

	}

}