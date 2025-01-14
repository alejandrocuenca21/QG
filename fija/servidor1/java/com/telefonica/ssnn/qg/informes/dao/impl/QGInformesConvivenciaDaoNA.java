/**
 * 
 */
package com.telefonica.ssnn.qg.informes.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.exceptions.QGApplicationException;
import com.telefonica.ssnn.qg.informes.dao.interfaz.QGInformesConvivenciaDao;
import com.telefonica.ssnn.qg.informes.dto.QGBuscadorDto;
import com.telefonica.ssnn.qg.informes.dto.QGEstadisticasDto;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;

/**
 * @author vsierra
 *
 */
public class QGInformesConvivenciaDaoNA extends QGBaseDao implements QGInformesConvivenciaDao {
	
	private static final Logger logger = Logger
	.getLogger(QGInformesConvivenciaDaoNA.class);

	/**
	 * Se llamará al servicio de negocio QGF0058
	 */
	public QGCGlobalDto actualizarDuplicados(List buscadorDto) {
		QGNpaNA servicio = null;
		QGCGlobalDto globalDto = new QGCGlobalDto();
		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_ACTUALIZAR_DUPLICADOS, usuarioLogado);

			QGBuscadorDto buscador = null;

			for(int i=0; i < buscadorDto.size(); i++){
				buscador = new QGBuscadorDto();

				buscador = (QGBuscadorDto)buscadorDto.get(i);

				servicio.setValor("IT-ALT-REG-GLB", buscador.getFxMovimiento());
				servicio.setValor("IN-UNI-NGC-CLI-GBL", buscador.getIndicadorNegocio());
				servicio.setValor("CO-CLIENTE-NSCO", buscador.getClienteNSCO());
				servicio.setValor("IN-TUZ-TAB", buscador.getIndActuacion());
				servicio.setValor("IN-TUZ-BAS-DAT", buscador.getIndActuacion());

				servicio.ejecutarTransaccion();
			}

		} catch (NAWRException e) {
			throw new QGApplicationException(e);
		}finally{
			if(servicio != null)
				servicio.unload();
		}		
		return globalDto;
	}

	/**
	 * Se llamará al servicio de negocio QGF0057
	 */
	public QGCGlobalDto buscadorClientesDuplicados(QGBuscadorDto buscadorDto) {
		QGNpaNA servicio = null;
		QGCGlobalDto globalDto = new QGCGlobalDto();

		ArrayList listaDatos = new ArrayList();
		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_LISTA_CLIENTES_DUPLICADOS, obtenerUsuarioLogado());			 

			servicio.setValor("FX-INI-VIGENCIA", buscadorDto.getFxInicio());
			servicio.setValor("FX-FIN-VIGENCIA", buscadorDto.getFxFin());

			servicio.ejecutarTransaccion();

			for(int i=1; i<=servicio.getValorAsInt("OCU-NU").intValue(); i++){
				buscadorDto = new QGBuscadorDto();

				buscadorDto.setClienteNSCO(servicio.getValorAsString("CO-CLIENTE-NSCO", i));
				buscadorDto.setFxMovimiento(servicio.getValorAsDate("IT-ALT-REG-GLB", i));
				buscadorDto.setIndicadorNegocio(QGConstantes.INDICADOR_MOVIL);
				if(servicio.getValorAsString("IN-UNI-NGC-CLI-GBL", i).equalsIgnoreCase(QGConstantes.CODIGO_FIJO))
					buscadorDto.setIndicadorNegocio(QGConstantes.INDICADOR_FIJO);

				buscadorDto.setNumDocumento(servicio.getValorAsString("NU-DOCUMENTO", i));
				buscadorDto.setRazonSocial(servicio.getValorAsString("DS-RAZON-SOCIAL", i));
				buscadorDto.setTipoDocumento(servicio.getValorAsString("DS-TIPO-DOCUMENTO", i));

				listaDatos.add(buscadorDto);							
			}

			globalDto.setlistaDatos(listaDatos);
		} catch (NAWRException e) {
			throw new QGApplicationException(e);
		}finally{
			if(servicio != null)
				servicio.unload();
		}		

		return globalDto;

	}

	/**
	 * Se llamará al servicio de negocio QGF0059
	 */
	public QGCGlobalDto buscadorErrores(QGBuscadorDto buscadorDto) {
		
		
		//metemos 0 para que devuelva el maximo
		String numeroElementosPorPagina = "0";
		
		logger.info("Buscando errores por criterio");
		QGNpaNA servicio = null;
		QGCGlobalDto globalDto = new QGCGlobalDto();

		ArrayList listaDatos = new ArrayList();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_LISTA_ERRORES, obtenerUsuarioLogado());

			servicio.setValor("FX-INI-VIGENCIA",QGUtilidadesFechasUtils.formatearFechaParaCopy(buscadorDto.getFxBusqueda(),QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			servicio.setValor("FX-FIN-VIGENCIA",QGUtilidadesFechasUtils.formatearFechaParaCopy(buscadorDto.getFxBusqueda(),QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			servicio.setValor("ENTRADA.OCU-NU", numeroElementosPorPagina);
			
			boolean continuar = true;
			while (continuar) {
				//llamamos al servicio
				servicio.ejecutarTransaccion();
				//comprobamos la condicion del bucle.
				if("N".equalsIgnoreCase(servicio.getValorAsString("IND-PGN-IN")) 
						|| servicio.getValorAsInt("SALIDA.OCU-NU").intValue() == 0 ){
					continuar = false;
				}
				//recorremos los datos de la respuesta.
				for(int i=1; i<=servicio.getValorAsInt("SALIDA.OCU-NU").intValue(); i++){
					buscadorDto = new QGBuscadorDto();
					buscadorDto.setClienteNSCO(servicio.getValorAsString("CO-CLIENTE-NSCO", i));
					buscadorDto.setFxMovimiento(servicio.getValorAsDate("IT-ALT-REG-GLB", i));

					buscadorDto.setIndicadorNegocio(QGConstantes.INDICADOR_MOVIL);
					if(servicio.getValorAsString("IN-UNI-NGC-CLI-GBL", i).equalsIgnoreCase(QGConstantes.CODIGO_FIJO)){
						buscadorDto.setIndicadorNegocio(QGConstantes.INDICADOR_FIJO);
					}

					buscadorDto.setNumDocumento(servicio.getValorAsString("NU-DOCUMENTO", i));
					buscadorDto.setRazonSocial(servicio.getValorAsString("DS-RAZON-SOCIAL", i));
					buscadorDto.setTipoDocumento(servicio.getValorAsString("DS-TIPO-DOCUMENTO", i));
					buscadorDto.setCodigoError(servicio.getValorAsString("CO-ERROR", i));		
					
					listaDatos.add(buscadorDto);
				}	
				// cabecera de la siguiente llamada.							
				servicio.setValor("ENTRADA.PGN-TX",servicio.getValorAsString("SALIDA.PGN-TX")); 
										
			}

			globalDto.setlistaDatos(listaDatos);
		} catch (NAWRException e) {
			throw new QGApplicationException(e);
		}finally{
			if(servicio != null)
				servicio.unload();
		}

		return globalDto;
	}

	/**
	 * Se llamará al servicio de negocio QGF0060
	 */
	public QGCGlobalDto obtenerEstadisticas(QGEstadisticasDto estadisticasDto) {
		QGNpaNA servicio = null;
		QGCGlobalDto globalDto = new QGCGlobalDto();

		ArrayList listaDatos = new ArrayList();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_LISTA_ESTADISTICAS, obtenerUsuarioLogado());



			servicio.setValor("FX-INI-VIGENCIA", QGUtilidadesFechasUtils.formatearFechaParaCopy(estadisticasDto.getFxBusqueda(),QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			servicio.setValor("FX-FIN-VIGENCIA", QGUtilidadesFechasUtils.formatearFechaParaCopy(estadisticasDto.getFxBusqueda(),QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			servicio.setValor("IN-RCU-ESD", estadisticasDto.getTipoInforme());

			servicio.ejecutarTransaccion();

			//Inicializamos todos los valores a 0 para no hacer validaciones abajo
			estadisticasDto.setRegErroneos(new Long(0));
			estadisticasDto.setRegErrorFija(new Long(0));
			estadisticasDto.setRegErrorMovil(new Long(0));
			estadisticasDto.setRegTratados(new Long(0));
			estadisticasDto.setRegValidos(new Long(0));
			estadisticasDto.setRegValidosFija(new Long(0));
			estadisticasDto.setRegValidosMovil(new Long(0));

			for(int i=1; i<=servicio.getValorAsInt("OCU-NU").intValue(); i++){
				estadisticasDto.setRegErroneos(new Long(estadisticasDto.getRegErroneos().intValue() + servicio.getValorAsLong("CA-REG-REC", i).intValue()));
				estadisticasDto.setRegErrorFija(new Long(estadisticasDto.getRegErrorFija().intValue() + servicio.getValorAsLong("CA-REG-REC-UNF", i).intValue()));
				estadisticasDto.setRegErrorMovil(new Long(estadisticasDto.getRegErrorMovil().intValue() + servicio.getValorAsLong("CA-REG-REC-UNM", i).intValue()));
				estadisticasDto.setRegTratados(new Long(estadisticasDto.getRegTratados().intValue() + servicio.getValorAsLong("CA-REG-TTD", i).intValue()));
				estadisticasDto.setRegValidos(new Long(estadisticasDto.getRegValidos().intValue() + servicio.getValorAsLong("CA-REG-ACE", i).intValue()));
				estadisticasDto.setRegValidosFija(new Long(estadisticasDto.getRegValidosFija().intValue() + servicio.getValorAsLong("CA-REG-ACE-UNF", i).intValue()));
				estadisticasDto.setRegValidosMovil(new Long(estadisticasDto.getRegValidosMovil().intValue() + servicio.getValorAsLong("CA-REG-ACE-UNM", i).intValue()));
			}
			listaDatos.add(estadisticasDto);
		} catch (NAWRException e) {
			throw new QGApplicationException(e);
		}finally{
			if(servicio != null)
				servicio.unload();
		}

		globalDto.setlistaDatos(listaDatos);
		return globalDto;

	}

}
