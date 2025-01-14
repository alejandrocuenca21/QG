package com.telefonica.ssnn.qg.administracion.menserror.dao.impl;

import java.util.ArrayList;
import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.administracion.menserror.dao.interfaz.QGMensajeErrorDao;
import com.telefonica.ssnn.qg.administracion.menserror.dto.QGMensajeErrorDto;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
import com.telefonica.ssnn.qg.exceptions.QGApplicationException;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;
import com.telefonica.ssnn.qg.util.QGUtilidadesNegocioUtils;
/**
 * Clase implementación de servicios para ADMINISTRACIÓN MENSAJES ERROR 
 * @author jacastano
 *
 */
public class QGMensajeErrorDaoNA extends QGBaseDao implements QGMensajeErrorDao {

	public QGCGlobalPagingDto buscadorMensajesError(QGMensajeErrorDto mensajeError) {
		//Implementación servicio QGF0124
		QGNpaNA servicio = null;
		QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto();

		ArrayList listaDatos = new ArrayList ();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_MENSAJES_ERROR, obtenerUsuarioLogado());

			servicio.setValor("ENTRADA.IN-ACTUACION", mensajeError.getAccion());
			servicio.setValor("ENTRADA.PGN-TX", mensajeError.getPgnTx());
		
			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();

			
			QGCGlobalPagingDto.setIndPgnIn(servicio.getValorAsString("SALIDA.IND-PGN-IN"));
			QGCGlobalPagingDto.setPgnTx(servicio.getValorAsString("SALIDA.PGN-TX"));
			
			QGMensajeErrorDto mensajeErrorDto = null;

			for (int i=1; i<= servicio.getValorAsInt("SALIDA.OCU-NU").intValue(); i++){
				mensajeErrorDto = new QGMensajeErrorDto();

				mensajeErrorDto.setCodigo(servicio.getValorAsString("ERR-MEN-GR.CO-MENSAJE-ERROR",i));
				mensajeErrorDto.setDescripcion(servicio.getValorAsString("ERR-MEN-GR.DS-MENSAJE-ERROR",i));
				mensajeErrorDto.setGrupoResponsable(servicio.getValorAsString("ERR-MEN-GR.IN-GRU-RES-ERR",i));
				mensajeErrorDto.setFxIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("ERR-MEN-GR.FX-INI-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				mensajeErrorDto.setFxFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("ERR-MEN-GR.FX-FIN-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				mensajeErrorDto.setUsuarioAlta(servicio.getValorAsString("ERR-MEN-GR.CO-USUARIO-ALT",i));
				mensajeErrorDto.setFxUsuarioAlta(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("ERR-MEN-GR.IT-ALTA",i),QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				mensajeErrorDto.setUsuarioModif(servicio.getValorAsString("ERR-MEN-GR.CO-USUARIO-MOD",i));
				mensajeErrorDto.setFxUsuarioModif(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("ERR-MEN-GR.IT-ULT-MOD",i),QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				
				listaDatos.add(mensajeErrorDto);
			}
			QGCGlobalPagingDto.setListaDatos(listaDatos);
			
		} catch (NAWRException e) {
			String mensajeErrorServicio = e.getCampo("CACV_VARMSG");
			if(QGUtilidadesNegocioUtils.getCodigoError(e.getCampo("CACV_VARMSG")).equals(QGUtilidadesNegocioUtils.CODIGO_ERROR_NO_EXISTEN_REGISTROS)){					
				mensajeErrorServicio = "La búsqueda de mensajes de error no devuelve resultados";
			}
			throw new QGApplicationException(mensajeErrorServicio);
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return QGCGlobalPagingDto;
	}

	public QGCGlobalDto gestionarMensajesError(
			QGMensajeErrorDto mensajeError) {
		//Implementación servicio QGF0124
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		try {

			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_MENSAJES_ERROR, obtenerUsuarioLogado());			

			servicio.setValor("ENTRADA.CO-MENSAJE-ERROR", mensajeError.getCodigo(),true);
			servicio.setValor("ENTRADA.DS-MENSAJE-ERROR", mensajeError.getDescripcion(),true);	
			servicio.setValor("ENTRADA.IN-GRU-RES-ERR", mensajeError.getGrupoResponsable(),true);	
			servicio.setValor("ENTRADA.FX-INI-VIGENCIA",
							QGUtilidadesFechasUtils.formatearFechaParaCopy(mensajeError.getFxIniVigencia(),
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			servicio.setValor("ENTRADA.FX_FIN_VIGENCIA",
							QGUtilidadesFechasUtils.formatearFechaParaCopy(mensajeError.getFxFinVigencia(),
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			servicio.setValor("ENTRADA.IN-ACTUACION", mensajeError.getAccion());
			
			servicio.ejecutarTransaccion();

		} catch (NAWRException e) {
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return QGCGlobalDto;
	}
}
