package com.telefonica.ssnn.qg.comun.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.comun.dao.interfaz.QGDerechosDao;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.util.QGDerechosDto;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;
import com.telefonica.ssnn.qg.util.QGUtilidadesNegocioUtils;

public class QGDerechosDaoNA extends QGBaseDao implements QGDerechosDao {
	
	private static final Logger logger = Logger
	.getLogger(QGDerechosDaoNA.class);

	public QGCGlobalDto gestionarDerechos(QGDerechosDto derecho) {
		
		QGNpaNA servicio = null;
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		ArrayList listaDatos = new ArrayList();

		try {
			servicio = QGNpaNA.obtenerServicio(
					QGIdentificadoresDescriptores.DESCRIPTOR_LISTADO_DERECHOS,
					this.obtenerUsuarioLogado());
			
			servicio.setValor("ENTRADA.IN-ACTUACION", derecho.getCodActuacion());
			servicio.setValor("ENTRADA.CO-DRH-UNF-LOPD", derecho.getCodDerecho());
			servicio.setValor("ENTRADA.CO-LIN-NGC", derecho.getCodLineaNegocio());
			servicio.setValor("ENTRADA.FX-INI-VIGENCIA",
					QGUtilidadesFechasUtils.formatearFechaParaCopy(derecho
							.getFecIniVigencia(),
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));

			servicio.setValor("ENTRADA.FX-FIN-VIGENCIA",
					QGUtilidadesFechasUtils.formatearFechaParaCopy(derecho
							.getFecFinVigencia(),
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));
	
			// Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();

			// Si es una consulta o historico...
			if (derecho.getCodActuacion() != null
					&& (derecho.getCodActuacion().equals(
							QGConstantes.LISTADO_CONSULTA) || derecho
							.getCodActuacion().equals(QGConstantes.LISTADO_HISTORICO))) {

				for (int i = 1; i <= servicio.getValorAsInt("SALIDA.OCU-NU")
						.intValue(); i++) {
					derecho = new QGDerechosDto();
					
					derecho.setCodDerecho(servicio.getValorAsString(
							"CO-DRH-UNF-LOPD", i));
					derecho.setDescDerecho(servicio.getValorAsString(
							"DS-CSM-DRH-INTERNO", i));
					derecho.setCodLineaNegocio(servicio.getValorAsString(
							"CO-LIN-NGC", i));
					
					derecho.setFechaAlta(QGUtilidadesFechasUtils
							.formatearTimeStampDesdeCopy(servicio
									.getValorAsString("IT-ALTA", i),
									QGUtilidadesFechasUtils.FORMATO_FECHA_1));

					derecho.setFechaMod(QGUtilidadesFechasUtils
							.formatearTimeStampDesdeCopy(servicio
									.getValorAsString("IT-ULT-MOD", i),
									QGUtilidadesFechasUtils.FORMATO_FECHA_1));

					derecho.setUsuarioAlta(servicio.getValorAsString(
							"CO-USUARIO-ALT", i));
					derecho.setUsuarioMod(servicio.getValorAsString(
							"CO-USUARIO-MOD", i));

					derecho.setFecIniVigencia(QGUtilidadesFechasUtils
							.formatearFechaDesdeCopy(servicio.getValorAsString(
									"FX-INI-VIGENCIA", i),
									QGUtilidadesFechasUtils.FORMATO_FECHA_1));
					derecho.setFecIniVigencia(QGUtilidadesFechasUtils
							.formatearFechaDesdeCopy(servicio.getValorAsString(
									"FX-FIN-VIGENCIA", i),
									QGUtilidadesFechasUtils.FORMATO_FECHA_1));

					listaDatos.add(derecho);
							
				}
			}
			qGCGlobalDto.setlistaDatos(listaDatos);
		} catch (NAWRException e) {
			logger.error(e.getMessage());
			QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e,
					QGIdentificadoresDescriptores.DESCRIPTOR_LISTADO_DERECHOS);

		} finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return qGCGlobalDto;

	}

}
