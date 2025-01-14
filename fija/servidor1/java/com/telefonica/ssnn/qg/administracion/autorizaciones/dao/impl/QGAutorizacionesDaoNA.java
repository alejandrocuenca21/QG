package com.telefonica.ssnn.qg.administracion.autorizaciones.dao.impl;

import java.util.ArrayList;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dao.interfaz.QGAutorizacionesDao;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGAutorizacionesBusquedaDto;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGAutorizacionesDto;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
import com.telefonica.ssnn.qg.exceptions.QGApplicationException;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;
import com.telefonica.ssnn.qg.util.QGUtilidadesNegocioUtils;
/**
 * Clase implementación de servicios para AUTORIZACIONES
 * @author jacastano
 *
 */
public class QGAutorizacionesDaoNA extends QGBaseDao implements QGAutorizacionesDao{

	public QGCGlobalPagingDto buscadorAutorizaciones(QGAutorizacionesBusquedaDto busqueda) {
		//Implementación servicio QGF0123
		QGNpaNA servicio = null;
		QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto();

		ArrayList listaDatos = new ArrayList ();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_AUTORIZACIONES, obtenerUsuarioLogado());

			servicio.setValor("ENTRADA.CO-MASI-SIS-EXT", busqueda.getCodSistemaExterno(),true);
			servicio.setValor("ENTRADA.CO-SER-NA-CLI-GBL", busqueda.getCodServicioNA(),true);
			servicio.setValor("ENTRADA.IN-ACTUACION", busqueda.getInActuacion());
			servicio.setValor("ENTRADA.PGN-TX", busqueda.getPgnTx());
			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();
			
			QGCGlobalPagingDto.setIndPgnIn(servicio.getValorAsString("SALIDA.IND-PGN-IN"));
			QGCGlobalPagingDto.setPgnTx(servicio.getValorAsString("SALIDA.PGN-TX"));
			
			QGAutorizacionesDto autorizaciones = null;

			for (int i=1; i<= servicio.getValorAsInt("SALIDA.OCU-NU").intValue(); i++){
				autorizaciones = new QGAutorizacionesDto();

				autorizaciones.setSistema(servicio.getValorAsString("GBL-CLI-SER-GR.CO-MASI-SIS-EXT",i));
				autorizaciones.setServicioNA(servicio.getValorAsString("GBL-CLI-SER-GR.CO-SER-NA-CLI-GBL",i));
				autorizaciones.setLineaNegocio(servicio.getValorAsString("GBL-CLI-SER-GR.IN-ACS-INF-COX",i));
				autorizaciones.setFxIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("GBL-CLI-SER-GR.FX-INI-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				autorizaciones.setFxFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("GBL-CLI-SER-GR.FX-FIN-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				autorizaciones.setUsuarioAlta(servicio.getValorAsString("GBL-CLI-SER-GR.CO-USUARIO-ALT",i));
				autorizaciones.setFxUsuarioAlta(servicio.getValorAsString("GBL-CLI-SER-GR.IT-ALTA",i));
				autorizaciones.setUsuarioModif(servicio.getValorAsString("GBL-CLI-SER-GR.CO-USUARIO-MOD",i));
				autorizaciones.setFxUsuarioModif(servicio.getValorAsString("GBL-CLI-SER-GR.IT-ULT-MOD",i));
				
				listaDatos.add(autorizaciones);
			}
				
			QGCGlobalPagingDto.setListaDatos(listaDatos);
			
		} catch (NAWRException e) {
			String mensajeErrorServicio = e.getCampo("CACV_VARMSG");
			if(QGUtilidadesNegocioUtils.getCodigoError(e.getCampo("CACV_VARMSG")).equals(QGUtilidadesNegocioUtils.CODIGO_ERROR_NO_EXISTEN_REGISTROS)){					
				mensajeErrorServicio = "La búsqueda de autorizaciones no devuelve resultados";
			}
			throw new QGApplicationException(mensajeErrorServicio);
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return QGCGlobalPagingDto;
	}

	public QGCGlobalDto gestionarAutorizaciones(
			QGAutorizacionesDto autorizaciones) {
		//Implementación servicio QGF0123
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_AUTORIZACIONES, obtenerUsuarioLogado());			


			servicio.setValor("ENTRADA.CO-MASI-SIS-EXT", autorizaciones.getSistema(),true);
			servicio.setValor("ENTRADA.CO-SER-NA-CLI-GBL", autorizaciones.getServicioNA(),true);	
			servicio.setValor("ENTRADA.IN-ACS-INF-COX", autorizaciones.getLineaNegocio());
			servicio.setValor("ENTRADA.FX_INI_VIGENCIA",
							QGUtilidadesFechasUtils.formatearFechaParaCopy(autorizaciones.getFxIniVigencia(),
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			servicio.setValor("ENTRADA.FX_FIN_VIGENCIA",
							QGUtilidadesFechasUtils.formatearFechaParaCopy(autorizaciones.getFxFinVigencia(),
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			servicio.setValor("ENTRADA.IN-ACTUACION", autorizaciones.getAccion());
			
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
