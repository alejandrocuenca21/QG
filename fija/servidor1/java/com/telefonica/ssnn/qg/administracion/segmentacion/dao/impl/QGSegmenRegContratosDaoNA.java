package com.telefonica.ssnn.qg.administracion.segmentacion.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;


import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.administracion.segmentacion.dao.interfaz.QGSegmenRegContratosDao;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSalidaContratoDto;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;
import com.telefonica.ssnn.qg.util.QGUtilidadesNegocioUtils;

/**
 * @author jacastano
 * 
 */
public class QGSegmenRegContratosDaoNA extends QGBaseDao implements
		QGSegmenRegContratosDao {

	private static final Logger logger = Logger
			.getLogger(QGSegmenRegContratosDaoNA.class);

	public QGCGlobalDto obtenerContratos(String inActuacion) {
		logger.debug("Obteniendo listado de Contratos.");
		
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		ArrayList listaDatos = new ArrayList();
/*		QGNpaNA servicio = null;
		
		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_LISTA_SUB_SEGMENTOS, obtenerUsuarioLogado());

			//ENTRADA
			servicio.setValor("ENTRADA.IN-ACTUACION",inActuacion);
			
			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();
			//SALIDA
			//mapeamos la salida del servidio NA
			for (int i = 1; i <= servicio.getValorAsInt("SALIDA.OCU-NU").intValue(); i++) {
				QGSalidaContratoDto nuevo = new QGSalidaContratoDto();
				nuevo.setCodigo(servicio.getValorAsString("SALIDA.sss.codigo,i"));
				nuevo.setDescripcion(servicio.getValorAsString("SALIDA.sss.descrip",i));
				nuevo.setFxIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("SALIDA.sss.FX-INI-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				nuevo.setFxFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("SALIDA.sss.FX-FIN-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				nuevo.setUsuarioAlta(servicio.getValorAsString("SALIDA.sss.CO-USUARIO-ALT",i));
				nuevo.setUsuarioMod(servicio.getValorAsString("SALIDA.sss.CO-USUARIO-MOD",i));
				
				listaDatos.add(nuevo);
			}
		} catch (NAWRException e) {
			logger.error(e.getMessage());
			QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e,QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_LISTA_SUB_SEGMENTOS);
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
*/		
		
		for (int i = 1; i <= 20; i++) {
			QGSalidaContratoDto nuevo = new QGSalidaContratoDto();
			nuevo.setCodigo("cod"+i);
			nuevo.setDescripcion("des"+i);
			nuevo.setFxIniVigencia("01.01.2001");
			nuevo.setFxFinVigencia("01.01.2050");
			nuevo.setUsuarioAlta("usuAlta"+i);
			nuevo.setUsuarioMod("usuMod"+i);
			
			listaDatos.add(nuevo);
		}
		
		QGCGlobalDto.setlistaDatos(listaDatos);
		return QGCGlobalDto;
	}

}