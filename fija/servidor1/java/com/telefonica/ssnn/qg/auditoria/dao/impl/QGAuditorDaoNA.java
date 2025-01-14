package com.telefonica.ssnn.qg.auditoria.dao.impl;

import java.util.Date;

import org.apache.log4j.Logger;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.auditoria.dao.interfaz.QGAuditorDao;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;

public class QGAuditorDaoNA extends QGBaseDao implements QGAuditorDao {

	private Logger logger = Logger.getLogger(QGAuditorDaoNA.class);

	public void altaAuditoria(String operacionDetalle, String resultadoOperacion, String resultadoNavegación) {
		logger.info("******---altaAuditoria de QGAuditorDaoNA---******");
		
		QGNpaNA servicio = null;
		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		try {

			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_MONITORIZACION_SEGURIDAD_WEB, usuarioLogado);
		
			String fechaAlta = QGUtilidadesFechasUtils.fromDateToString(new Date(), QGUtilidadesFechasUtils.FORMATO_FECHA_2);
			
//			logger.info("CO-PRF-USR: " + usuarioLogado.getPerfil());
//			logger.info("CO-OP-COMERC: " + QGConstantes.CO_TP_PARAM_GBL);
//			logger.info("CO-USUARIO: " + usuarioLogado.getUsername());
//			logger.info("DS-OPERACION: " + operacionDetalle);
//			logger.info("IT-ALTA: " + fechaAlta);
//			logger.info("DS-RSL-OPR: " + resultadoOperacion);
//			logger.info("DS-RSL-NAV: " + manageStringSize(resultadoNavegación,200));
			
			servicio.setValor("ENTRADA.CO-PRF-USR", usuarioLogado.getPerfil());
			servicio.setValor("ENTRADA.CO-OP-COMERC", QGConstantes.CO_TP_PARAM_GBL);
			servicio.setValor("ENTRADA.CO-USUARIO", usuarioLogado.getUsername());
			servicio.setValor("ENTRADA.DS-OPERACION", operacionDetalle);
			servicio.setValor("ENTRADA.IT-ALTA", QGUtilidadesFechasUtils.fromDateToString(new Date(), QGUtilidadesFechasUtils.FORMATO_FECHA_2));
			servicio.setValor("ENTRADA.DS-RSL-OPR", resultadoOperacion);
			servicio.setValor("ENTRADA.DS-RSL-NAV", manageStringSize(resultadoNavegación,200));

			servicio.ejecutarTransaccion();
			

			
		} catch (NAWRException e) {
			logger.info("error dao --> " + e.getMessage(), e);

			//throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		} finally {
			if (servicio != null)
				servicio.unload();
		}

		logger.info("******---FIN altaAuditoria de QGAuditorDaoNA---******");	
	}
	
	private String manageStringSize (String string,int size) {
		if(string.length()>size) {
			return string.substring(0, size);
		}
		
		return string;
	}

}
