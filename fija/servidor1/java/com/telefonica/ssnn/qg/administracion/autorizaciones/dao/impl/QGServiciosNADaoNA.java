package com.telefonica.ssnn.qg.administracion.autorizaciones.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dao.interfaz.QGServiciosNADao;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGServiciosNADto;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGSistemasExternosDto;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
import com.telefonica.ssnn.qg.exceptions.QGApplicationException;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;
import com.telefonica.ssnn.qg.util.QGUtilidadesNegocioUtils;
/**
 * Clase implementación de servicios para SERVICIOSNA
 * @author jacastano
 *
 */
public class QGServiciosNADaoNA extends QGBaseDao implements QGServiciosNADao{

	public QGCGlobalPagingDto buscadorServiciosNA(String inActuacion, String pgnTx) {
		//Implementación servicio QGF0122
		QGNpaNA servicio = null;
		QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto();
		ArrayList listaDatos = new ArrayList ();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_SERVICIOSNA, obtenerUsuarioLogado());

			servicio.setValor("ENTRADA.IN-ACTUACION", inActuacion);
			servicio.setValor("ENTRADA.PGN-TX",pgnTx);
			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();
			QGCGlobalPagingDto.setIndPgnIn(servicio.getValorAsString("IND-PGN-IN"));
			QGCGlobalPagingDto.setPgnTx(servicio.getValorAsString("SALIDA.PGN-TX"));
			
			QGServiciosNADto serviciosNA = null;

			for (int i=1; i<= Integer.parseInt(servicio.getValorAsString("SALIDA.OCU-NU")); i++){
				serviciosNA = new QGServiciosNADto();

				serviciosNA.setCodigo(servicio.getValorAsString("CO-SER-NA-CLI-GBL",i));
				serviciosNA.setDescripcion(servicio.getValorAsString("DS-SER-CLI-GBL",i));
				serviciosNA.setDisplay(servicio.getValorAsString("IN-DISPLAY",i));
				serviciosNA.setFuncion(servicio.getValorAsString("CO-FUNCION-CLI-GBL",i));
				serviciosNA.setHabilitado(servicio.getValorAsString("IN-HABILITADO",i));
				serviciosNA.setLogError(servicio.getValorAsString("IN-LOG-ERROR",i));
				serviciosNA.setOnlineBatch(servicio.getValorAsString("IN-ONLINE-BATCH",i));
				serviciosNA.setTipo(servicio.getValorAsString("IN-TIPO-SERVICIO",i));			
				serviciosNA.setFxIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-INI-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				serviciosNA.setFxFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-FIN-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				serviciosNA.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT",i));
				serviciosNA.setFxUsuarioAlta(servicio.getValorAsString("IT-ALTA",i));
				serviciosNA.setUsuarioModif(servicio.getValorAsString("CO-USUARIO-MOD",i));
				serviciosNA.setFxUsuarioModif(servicio.getValorAsString("IT-ULT-MOD",i));
				
				listaDatos.add(serviciosNA);
			}
			
			QGCGlobalPagingDto.setListaDatos(listaDatos);
		} catch (NAWRException e) {
			String mensajeErrorServicio = e.getCampo("CACV_VARMSG");
			if(QGUtilidadesNegocioUtils.getCodigoError(e.getCampo("CACV_VARMSG")).equals(QGUtilidadesNegocioUtils.CODIGO_ERROR_NO_EXISTEN_REGISTROS)){					
				mensajeErrorServicio = "La búsqueda de serviciosNA no devuelve resultados";
			}
			throw new QGApplicationException(mensajeErrorServicio);
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return QGCGlobalPagingDto;
	}

	public QGCGlobalDto gestionarServiciosNA(
			QGServiciosNADto serviciosNA) {
		//Implementación servicio QGF0122
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_SERVICIOSNA, obtenerUsuarioLogado());

			servicio.setValor("ENTRADA.CO-SER-NA-CLI-GBL", serviciosNA.getCodigo(),true);
			servicio.setValor("ENTRADA.DS-SER-CLI-GBL", serviciosNA.getDescripcion(),true);	
			servicio.setValor("ENTRADA.IN-DISPLAY", serviciosNA.getDisplay());
			servicio.setValor("ENTRADA.CO-FUNCION-CLI-GBL", serviciosNA.getFuncion());
			servicio.setValor("ENTRADA.IN-HABILITADO", serviciosNA.getHabilitado());
			servicio.setValor("ENTRADA.IN-LOG-ERROR", serviciosNA.getLogError());
			servicio.setValor("ENTRADA.IN-ONLINE-BATCH", serviciosNA.getOnlineBatch());
			servicio.setValor("ENTRADA.IN-TIPO-SERVICIO", serviciosNA.getTipo());
			servicio.setValor("ENTRADA.FX_INI_VIGENCIA",
							QGUtilidadesFechasUtils.formatearFechaParaCopy(serviciosNA.getFxIniVigencia(),
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			servicio.setValor("ENTRADA.FX_FIN_VIGENCIA",
							QGUtilidadesFechasUtils.formatearFechaParaCopy(serviciosNA.getFxFinVigencia(),
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));

			servicio.setValor("ENTRADA.IN-ACTUACION", serviciosNA.getAccion());
			
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
	public QGCGlobalDto cargarComboServiciosNA(String inActuacion, HashMap lineaNegocio) {
		//Implementacion servicio QG0122
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		
		ArrayList listaDatos = new ArrayList ();
		
		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_SERVICIOSNA, obtenerUsuarioLogado());
			
			servicio.setValor("ENTRADA.IN-ACTUACION", inActuacion);
			
			servicio.setValor("ENTRADA.OCU-NU","");
			boolean continuar = true;
			
			QGServiciosNADto serviciosNA = null;
			

			while (continuar){
				//llamamos al servicio

				servicio.ejecutarTransaccion();
				
				//comprobamos la condicion del bucle. 
				if("N".equalsIgnoreCase(servicio.getValorAsString("IND-PGN-IN")) 
						|| Integer.parseInt(servicio.getValorAsString("SALIDA.OCU-NU")) == 0 ){
					continuar = false;
				}
				
				for (int i=1; i<= servicio.getValorAsInt("SALIDA.OCU-NU").intValue(); i++){
					serviciosNA = new QGServiciosNADto();
	
					serviciosNA.setCodigo(servicio.getValorAsString("CO-SER-NA-CLI-GBL",i));
					serviciosNA.setDescripcion(servicio.getValorAsString("DS-SER-CLI-GBL",i));
					serviciosNA.setDisplay(servicio.getValorAsString("IN-DISPLAY",i));
					serviciosNA.setFuncion(servicio.getValorAsString("CO-FUNCION-CLI-GBL",i));
					serviciosNA.setHabilitado(servicio.getValorAsString("IN-HABILITADO",i));
					serviciosNA.setLogError(servicio.getValorAsString("IN-LOG-ERROR",i));
					serviciosNA.setOnlineBatch(servicio.getValorAsString("IN-ONLINE-BATCH",i));
					serviciosNA.setTipo(servicio.getValorAsString("IN-TIPO-SERVICIO",i));			
					serviciosNA.setFxIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-INI-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
					serviciosNA.setFxFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-FIN-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
					serviciosNA.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT",i));
					serviciosNA.setFxUsuarioAlta(servicio.getValorAsString("IT-ALTA",i));
					serviciosNA.setUsuarioModif(servicio.getValorAsString("CO-USUARIO-MOD",i));
					serviciosNA.setFxUsuarioModif(servicio.getValorAsString("IT-ULT-MOD",i));
					
					listaDatos.add(serviciosNA);
				}
			}
			
			QGCGlobalDto.setlistaDatos(listaDatos);
			
		} catch (NAWRException e) {
			throw new QGApplicationException(e);
		}finally{
			if(servicio != null)
				servicio.unload();
		}	
		
		return QGCGlobalDto;
	}


}
