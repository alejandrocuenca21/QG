package com.telefonica.ssnn.qg.administracion.autorizaciones.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dao.interfaz.QGSistemasExternosDao;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGSistemasExternosBusquedaDto;
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
 * Clase implementación de servicios para SISTEMAS EXTERNOS
 * @author jacastano 
 *
 */
public class QGSistemasExternosDaoNA extends QGBaseDao implements QGSistemasExternosDao{

	
	private static final Logger logger = Logger.getLogger(QGSistemasExternosDaoNA.class);
	
	
	public QGCGlobalDto buscadorLineasNegocio(String inActuacion) {
		//Implementación servicio QG0120
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		ArrayList listaDatos = new ArrayList ();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_LINEASNEGOCIO, obtenerUsuarioLogado());

			servicio.setValor("IN-ACTUACION", inActuacion);

			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();

			QGSistemasExternosDto sistemasExternos = null;

			for (int i=1; i<= servicio.getValorAsInt("OCU-NU").intValue(); i++){
				sistemasExternos = new QGSistemasExternosDto();

				sistemasExternos.setDescripcion(servicio.getValorAsString("DS-LIN-NGC",i));
				sistemasExternos.setNombre(servicio.getValorAsString("CO-LIN-NGC",i));
				sistemasExternos.setFxIniVigencia(servicio.getValorAsString("FX-INI-VIGENCIA",i));
				sistemasExternos.setFxFinVigencia(servicio.getValorAsString("FX-FIN-VIGENCIA",i));
				sistemasExternos.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT",i));
				sistemasExternos.setFxUsuarioAlta(servicio.getValorAsString("IT-ALTA",i));
				sistemasExternos.setUsuarioModif(servicio.getValorAsString("CO-USUARIO-MOD",i));
				sistemasExternos.setFxUsuarioAlta(servicio.getValorAsString("IT-ULT-MOD",i));
				
				listaDatos.add(sistemasExternos);
			}
			QGCGlobalDto.setlistaDatos(listaDatos);
		} catch (NAWRException e) {
			String mensajeSisExternos = e.getCampo("CACV_VARMSG");
			if(QGUtilidadesNegocioUtils.getCodigoError(e.getCampo("CACV_VARMSG")).equals(QGUtilidadesNegocioUtils.CODIGO_ERROR_NO_EXISTEN_REGISTROS)){					
				mensajeSisExternos = "La búsqueda de Líneas de Negocio para Sistemas Externos no devuelve resultados";
			}
			throw new QGApplicationException(mensajeSisExternos);
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return QGCGlobalDto;
	}	
	
	
	public QGCGlobalPagingDto buscadorSistemasExternos(String inActuacion,HashMap lineaNegocio,QGSistemasExternosBusquedaDto busqueda) {
		//Implementación servicio QG0121
		QGNpaNA servicio = null;
		QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto();

		ArrayList listaDatos = new ArrayList ();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_SISTEMASEX, obtenerUsuarioLogado());

			servicio.setValor("ENTRADA.IN-ACTUACION", inActuacion);

			servicio.setValor("ENTRADA.OCU-NU","");
			servicio.setValor("ENTRADA.PGN-TX", busqueda.getPgnTx());

			//Ejecutamos la transaccion del servicio.
			logger.info("Ejecutamos Servicio "+ QGIdentificadoresDescriptores.DESCRIPTOR_SISTEMASEX);
			servicio.ejecutarTransaccion();
			logger.info("Servicio  "+ QGIdentificadoresDescriptores.DESCRIPTOR_SISTEMASEX  + " Ejecutado.");
			
			QGCGlobalPagingDto.setIndPgnIn(servicio.getValorAsString("IND-PGN-IN"));
			QGCGlobalPagingDto.setPgnTx(servicio.getValorAsString("SALIDA.PGN-TX"));
			
			QGSistemasExternosDto sistemasExternos = null;

			for (int i=1; i<= servicio.getValorAsInt("SALIDA.OCU-NU").intValue(); i++){
				sistemasExternos = new QGSistemasExternosDto();

				sistemasExternos.setAnagrama(servicio.getValorAsString("CO-MASI-SIS-EXT",i));
				sistemasExternos.setDescripcion(servicio.getValorAsString("DS-SISTEMA-EXTERNO",i));
				sistemasExternos.setNombre(servicio.getValorAsString("NO-SISTEMA-EXTERNO",i));
				sistemasExternos.setLineaNegocio(servicio.getValorAsString("CO-LIN-NGC",i));
				if (lineaNegocio != null){
					sistemasExternos.setDesLineaNegocio((String) lineaNegocio.get(sistemasExternos.getLineaNegocio()));
				}
				sistemasExternos.setFxIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-INI-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				sistemasExternos.setFxFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-FIN-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));				
				sistemasExternos.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT",i));
				sistemasExternos.setFxUsuarioAlta(servicio.getValorAsString("IT-ALTA",i));
				sistemasExternos.setUsuarioModif(servicio.getValorAsString("CO-USUARIO-MOD",i));
				sistemasExternos.setFxUsuarioAlta(servicio.getValorAsString("IT-ULT-MOD",i));

				
				listaDatos.add(sistemasExternos);
			}	
			
		
			QGCGlobalPagingDto.setListaDatos(listaDatos);
			
		} catch (NAWRException e) {
			String mensajeSisExternos = e.getCampo("CACV_VARMSG");
			if(QGUtilidadesNegocioUtils.getCodigoError(e.getCampo("CACV_VARMSG")).equals(QGUtilidadesNegocioUtils.CODIGO_ERROR_NO_EXISTEN_REGISTROS)){					
				mensajeSisExternos = "La búsqueda de Sistemas Externos no devuelve resultados";
			}
			throw new QGApplicationException(mensajeSisExternos);
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return QGCGlobalPagingDto;
	}

	/**
	 * Método encargado de llamar tantas veces como sea necesario al servicio QG0121
	 * hasta que indique que no hay mas datos.
	 * 
	 * Rellenamos el combo (Sistemas Externos).
	 *
	 */
	public QGCGlobalDto cargarComboSistemaExternos(String inActuacion, HashMap lineaNegocio) {
		//Implementacion servicio QG0121 
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		
		ArrayList listaDatos = new ArrayList ();
		
		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_SISTEMASEX, obtenerUsuarioLogado());
			
			servicio.setValor("ENTRADA.IN-ACTUACION", inActuacion);
			
			servicio.setValor("ENTRADA.OCU-NU","");
			boolean continuar = true;
			
			QGSistemasExternosDto sistemasExternos = null;
			

			while (continuar){
				//llamamos al servicio
				logger.info("Ejecutamos Servicio "+ QGIdentificadoresDescriptores.DESCRIPTOR_SISTEMASEX);
				servicio.ejecutarTransaccion();
				logger.info("Servicio  "+ QGIdentificadoresDescriptores.DESCRIPTOR_SISTEMASEX  + " Ejecutado.");
				
				//comprobamos la condicion del bucle. 
				if("N".equalsIgnoreCase(servicio.getValorAsString("IND-PGN-IN")) 
						|| servicio.getValorAsInt("SALIDA.OCU-NU").intValue() == 0 ){
					continuar = false;
				}
				
				for (int i=1; i<= servicio.getValorAsInt("SALIDA.OCU-NU").intValue(); i++){
					sistemasExternos = new QGSistemasExternosDto();

					sistemasExternos.setAnagrama(servicio.getValorAsString("CO-MASI-SIS-EXT",i));
					sistemasExternos.setDescripcion(servicio.getValorAsString("DS-SISTEMA-EXTERNO",i));
					sistemasExternos.setNombre(servicio.getValorAsString("NO-SISTEMA-EXTERNO",i));
					sistemasExternos.setLineaNegocio(servicio.getValorAsString("CO-LIN-NGC",i));
					if (lineaNegocio != null){
						sistemasExternos.setDesLineaNegocio((String) lineaNegocio.get(sistemasExternos.getLineaNegocio()));
					}
					sistemasExternos.setFxIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-INI-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
					sistemasExternos.setFxFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-FIN-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));				
					sistemasExternos.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT",i));
					sistemasExternos.setFxUsuarioAlta(servicio.getValorAsString("IT-ALTA",i));
					sistemasExternos.setUsuarioModif(servicio.getValorAsString("CO-USUARIO-MOD",i));
					sistemasExternos.setFxUsuarioAlta(servicio.getValorAsString("IT-ULT-MOD",i));
					
					listaDatos.add(sistemasExternos);
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

	public QGCGlobalDto gestionarSistemasExternos(
			QGSistemasExternosDto sistemasExternos) {
		//Implementación servicio QG0121
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_SISTEMASEX, obtenerUsuarioLogado());


			servicio.setValor("ENTRADA.CO-MASI-SIS-EXT", sistemasExternos.getAnagrama(),true);
			servicio.setValor("ENTRADA.DS-SISTEMA-EXTERNO", sistemasExternos.getDescripcion(),true);	
			servicio.setValor("ENTRADA.CO-LIN-NGC", sistemasExternos.getLineaNegocio());
			servicio.setValor("ENTRADA.NO-SISTEMA-EXTERNO", sistemasExternos.getNombre(),true);
			servicio.setValor("ENTRADA.FX_INI_VIGENCIA",
							QGUtilidadesFechasUtils.formatearFechaParaCopy(sistemasExternos.getFxIniVigencia(),
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			servicio.setValor("ENTRADA.FX_FIN_VIGENCIA",
							QGUtilidadesFechasUtils.formatearFechaParaCopy(sistemasExternos.getFxFinVigencia(),
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			
			servicio.setValor("ENTRADA.IN-ACTUACION", sistemasExternos.getAccion());
			
			logger.info("Ejecutamos Servicio "+ QGIdentificadoresDescriptores.DESCRIPTOR_SISTEMASEX);
			servicio.ejecutarTransaccion();
			logger.info("Servicio  "+ QGIdentificadoresDescriptores.DESCRIPTOR_SISTEMASEX  + " Ejecutado.");

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
