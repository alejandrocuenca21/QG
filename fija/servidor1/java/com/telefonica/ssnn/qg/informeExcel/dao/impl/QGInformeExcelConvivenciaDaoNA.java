package com.telefonica.ssnn.qg.informeExcel.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.exceptions.QGApplicationException;
import com.telefonica.ssnn.qg.informeExcel.dao.interfaz.QGInformeExcelConvivenciaDao;
import com.telefonica.ssnn.qg.informeExcel.dto.QGEntradaInformeDto;
import com.telefonica.ssnn.qg.informeExcel.dto.QGInformeConciliacionDTO;
import com.telefonica.ssnn.qg.informeExcel.dto.QGInformeConvivenciaDTO;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;
import com.telefonica.ssnn.qg.util.QGUtilidadesNegocioUtils;


/**
 * @author jacastano
 *
 */
public class QGInformeExcelConvivenciaDaoNA extends QGBaseDao implements QGInformeExcelConvivenciaDao {
	
	private static final Logger logger = Logger.getLogger(QGInformeExcelConvivenciaDaoNA.class);	

	public QGCGlobalDto buscarInforme(QGEntradaInformeDto entrada) {		
		
		logger.info("******---buscarInforme de QGInformeExcelConvivenciaDaoNA---******");
		
		QGCGlobalDto globalDto = new QGCGlobalDto();
					
		QGInformeConvivenciaDTO informeConvDTO = new QGInformeConvivenciaDTO(QGUtilidadesFechasUtils.formatearFechaParaCopy(entrada.getFecha(),QGUtilidadesFechasUtils.FORMATO_FECHA_1));
		QGInformeConciliacionDTO informeConcDTO = new QGInformeConciliacionDTO();
		
		QGNpaNA servicio = null;
		
		//Implementación servicio QGF0133
		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_INFORMES_CONVIVENCIA, obtenerUsuarioLogado());

			servicio.setValor("ENTRADA.FX-INFORME",
				QGUtilidadesFechasUtils.formatearFechaParaCopy(entrada.getFecha(),
				QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			servicio.setValor("ENTRADA.CO-TIPO-INFORME",entrada.getInforme());

			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();
			
			//Recogemos los datos para las fechas que devuelve la fecha seleccionada
			for(int j=1; j <= servicio.getValorAsInt("SALIDA.OCU-NU").intValue(); j++){

				String fecha = QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("SALIDA.INF-FEC-GR.FX-INFORME",j), QGUtilidadesFechasUtils.FORMATO_FECHA_3);

				//Recogemos los datos para los "Datos Fijos"
				List listaDatos = new ArrayList();
				
				//Primero tenemos que saber si hay Prepago porque los datos vienen
				//en ocurrencias distintas
/*				
				logger.info("******---Lista del dia: " + j + " NATESTER");
				for (int m=1; m<servicio.getValorAsInt("DGN-OCU-NU",j).intValue();m++)
					logger.info(servicio.getValorAsString("DS-DGN",j,m));				
*/				
				for(int i=1; i <= servicio.getValorAsInt("DGN-OCU-NU",j).intValue(); i++){
					if (entrada.getInforme().equals("1"))
					{				
						//NO Prepago 
						if (servicio.getValorAsInt("DGN-OCU-NU",j).intValue() == 59)
						{			
							listaDatos.add(servicio.getValorAsString("DS-DGN",j,i));
							//Metemos en la lista los 4 valores de totales prepago a 0
							if (i==7)
							{
								for (int l=1; l<5; l++)
									listaDatos.add("0");
							}
							//Metemos en la lista los 6 valores de prepago a 0
							if (i==47)
							{
								for (int l=1; l<7; l++)
									listaDatos.add("0");
							}
							//Metemos en la lista los 6 valores de reinyeccion prepago a 0
							if (i==59)
							{
								for (int l=1; l<7; l++)
									listaDatos.add("0");
							}							
						}
						else
						{
							if (i==48)
								listaDatos.set(7, servicio.getValorAsString("DS-DGN",j,i));
							else if (i==49)
								listaDatos.set(8, servicio.getValorAsString("DS-DGN",j,i));
							else if (i==50)
								listaDatos.set(9, servicio.getValorAsString("DS-DGN",j,i));
							else if (i==51)
								listaDatos.set(10, servicio.getValorAsString("DS-DGN",j,i));
							else
								listaDatos.add(servicio.getValorAsString("DS-DGN",j,i));
							//Metemos en la lista los 4 valores de totales prepago a 0
							//y los sustituimos luego por su valor en la ocurrencia 48, 49, 50 y 51
							if (i==7)
							{
								for (int l=1; l<5; l++)
									listaDatos.add("0");
							}
						}
					}
					else
						listaDatos.add(servicio.getValorAsString("DS-DGN",j,i));
				}
//				logger.info("******---"+servicio.getValorAsInt("DGN-OCU-NU",j)+"---******");
				if (entrada.getInforme().equals("3"))
				{
					if (servicio.getValorAsInt("DGN-OCU-NU",j).intValue() == 33)
					{					
						for (int l=listaDatos.size(); l<45; l++)
							listaDatos.add("00000000");
					}
				}
//				logger.info("******---Lista del dia: " + j + " ADAPTADA");
				for (int m=0; m<listaDatos.size();m++)
					logger.info(listaDatos.get(m));
				
				//Recogemos los datos para los "Errores"
				List listaErrores = new ArrayList();
				
				for(int i=1; i <= servicio.getValorAsInt("ERR-OCU-NU",j).intValue(); i++){

					listaErrores.add(servicio.getValorAsString("DS-ERROR-VALOR",j,i));				
				}
								
				
				//---------------------------------------------------
				if (entrada.getInforme().equals("1")){ //Pasamos lo obtenido para el Informe de Convivencia
					informeConvDTO.addDay(fecha,listaDatos,listaErrores,j);
				}
				else if (entrada.getInforme().equals("3")){ //Pasamos lo obtenido para el Informe de Conciliación
					informeConcDTO.setFechaSolicitud(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(fecha,
							QGUtilidadesFechasUtils.FORMATO_FECHA_1));
					
					informeConcDTO.rellenarListadosNSCOTWPREPAGO(listaDatos);
					
					informeConcDTO.aniadirDatosClientesConciliados();
					informeConcDTO.aniadirDatosConciliacionDirecciones();
					informeConcDTO.aniadirDatosDepuracionDatosClientes();
				}
			}
			if (entrada.getInforme().equals("1")){
				//Al finalizar los informes siempre preparamos el decorator
				informeConvDTO.prepararDecoratorErrores();
				//Funcion que crea hasta 7 dias, para completar siempre el informe
				//evita complicaciones en la excel
				informeConvDTO.completarDias();
				
				List listaDatosTotal = new ArrayList ();
				listaDatosTotal.add(informeConvDTO);
				globalDto.setlistaDatos(listaDatosTotal);
				
				List listaMensajes = new ArrayList ();
				globalDto.setlistaMensajes(listaMensajes);
			}
			else if (entrada.getInforme().equals("3")){
				//Finalizamos el informe de conciliación
				List listaDatosTotal = new ArrayList ();
				listaDatosTotal.add(informeConcDTO);
				globalDto.setlistaDatos(listaDatosTotal);
				
				List listaMensajes = new ArrayList ();
				globalDto.setlistaMensajes(listaMensajes);
			}
			
			logger.info("******---FINAL buscarInforme de QGInformeExcelConvivenciaDaoNA---******");			
		
		} catch (NAWRException e) {
			String mensajeErrorServicio = e.getCampo("CACV_VARMSG");
			if(QGUtilidadesNegocioUtils.getCodigoError(e.getCampo("CACV_VARMSG")).equals(QGUtilidadesNegocioUtils.CODIGO_ERROR_NO_EXISTEN_REGISTROS)){					
				mensajeErrorServicio = "La búsqueda de Datos del Informe de convivencia no devuelve resultados";
			}
			throw new QGApplicationException(mensajeErrorServicio);
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return globalDto;
	}

}
