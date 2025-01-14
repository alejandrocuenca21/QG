/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.dao.impl;


import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.administracion.segmentacion.dao.interfaz.QGSegmentacionesReglasDao;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaSegmentacionesReglasDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSalidaSegmentacionesReglasDto;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.util.QGCatalogoDto;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;
import com.telefonica.ssnn.qg.util.QGUtilidadesNegocioUtils;

/**
 * @author jacastano
 * 
 */
public class QGSegmentacionesReglasDaoNA extends QGBaseDao implements QGSegmentacionesReglasDao {

	private static final Logger logger = Logger.getLogger(QGSegmentacionesReglasDaoNA.class);

	public QGCGlobalDto obtenerDatosReglas(String actuacion){
		logger.debug("Obteniendo combo de reglas.");
		
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		ArrayList listaDatos = new ArrayList();
		QGNpaNA servicio = null;
		
		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_REGLAS_TODAS, obtenerUsuarioLogado());

			// Seteamos la copy de entrada
			servicio.setValor("ENTRADA.IN-ACTUACION",actuacion);
			
			// Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();
	
			//SALIDA
			//mapeamos la salida del servicio NA
			for (int i = 1; i <= servicio.getValorAsInt("SALIDA.OCU-NU").intValue(); i++) {
				QGCatalogoDto descripcionesDto = new QGCatalogoDto();
				descripcionesDto.setCodigo(servicio.getValorAsString("SALIDA.REG-SEG-GR.CO-REGLA-SGM",i));
				descripcionesDto.setDescripcion(servicio.getValorAsString("SALIDA.REG-SEG-GR.DS-REGLA-SGM",i));

				listaDatos.add(descripcionesDto);
			}
		} catch (NAWRException e) {
			logger.error(e.getMessage());
			QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e,QGIdentificadoresDescriptores.DESCRIPTOR_REGLAS_TODAS);
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}

		QGCGlobalDto.setlistaDatos(listaDatos);
		return QGCGlobalDto;		
	}
	
	public QGCGlobalDto obtenerListaReglas(QGEntradaSegmentacionesReglasDto busquedaSegmentacion){
		logger.debug("Obteniendo lista buscando por regla.");
		
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		ArrayList listaDatos = new ArrayList();

		QGNpaNA servicio = null;
		
		try {

			// Se especifica el servicio al que se va a llamar
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_REGLAS_REGLAS,obtenerUsuarioLogado());

			// Seteamos la copy de entrada
			servicio.setValor("ENTRADA.IN-TIP-CST-PSG",busquedaSegmentacion.getTipoConsulta());
			servicio.setValor("ENTRADA.CO-REGLA-SGM",busquedaSegmentacion.getRegla());
			servicio.setValor("ENTRADA.CA-LINEAS-UNF",busquedaSegmentacion.getNLinFija());
			servicio.setValor("ENTRADA.CA-LINEAS-UNM",busquedaSegmentacion.getNLinMovil());
			servicio.setValor("ENTRADA.CA-LINEAS-TOTAL",busquedaSegmentacion.getNTotalLin());
			servicio.setValor("ENTRADA.CA-DIAS-PTE-SGM",busquedaSegmentacion.getNDias());
			
			// Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();
			
			for (int i = 1; i <= servicio.getValorAsInt("SALIDA.OCU-NU").intValue(); i++) {
				QGSalidaSegmentacionesReglasDto salidaSegmentacionesReglasDto = new QGSalidaSegmentacionesReglasDto();
				
				salidaSegmentacionesReglasDto.setNDias(servicio.getValorAsString("SALIDA.SGM-RGL-GR.CA-DIAS-PTE-SGM",i));
				salidaSegmentacionesReglasDto.setNTotalLin(servicio.getValorAsString("SALIDA.SGM-RGL-GR.CA-LINEAS-TOTAL",i));
				salidaSegmentacionesReglasDto.setNLinMov(servicio.getValorAsString("SALIDA.SGM-RGL-GR.CA-LINEAS-UNM",i));
				salidaSegmentacionesReglasDto.setNLinFija(servicio.getValorAsString("SALIDA.SGM-RGL-GR.CA-LINEAS-UNF",i));
				
				salidaSegmentacionesReglasDto.setRegla(servicio.getValorAsString("SALIDA.SGM-RGL-GR.CO-REGLA-SGM",i));
				salidaSegmentacionesReglasDto.setReglaDes(servicio.getValorAsString("SALIDA.SGM-RGL-GR.DS-REGLA-SGM",i));
				
				salidaSegmentacionesReglasDto.setFechaIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("SALIDA.SGM-RGL-GR.FX-INI-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				salidaSegmentacionesReglasDto.setFechaFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("SALIDA.SGM-RGL-GR.FX-FIN-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));				
				salidaSegmentacionesReglasDto.setFechaModRegla(QGUtilidadesFechasUtils.formatearTimeStampDesdeCopy(servicio.getValorAsString("SALIDA.SGM-RGL-GR.IT-ULT-MOD",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				
				salidaSegmentacionesReglasDto.setUsuModRegla(servicio.getValorAsString("SALIDA.SGM-RGL-GR.CO-USUARIO-MOD",i));
				
				salidaSegmentacionesReglasDto.setUsuAlta(servicio.getValorAsString("SALIDA.SGM-RGL-GR.CO-USUARIO-ALT",i));

				if (servicio.getValorAsInt("SALIDA.SGM-RGL-GR.SGM-OCU-NU",i).intValue() > 0)
				{				
					for (int j = 1; j <= servicio.getValorAsInt("SALIDA.SGM-RGL-GR.SGM-OCU-NU",i).intValue(); j++) {
						salidaSegmentacionesReglasDto.setSegmentoOrigen(servicio.getValorAsString("SALIDA.SGM-RGL-GR.SGM-ASO-GR.CO-SEGMENTO",i,j));
						salidaSegmentacionesReglasDto.setSegmento(servicio.getValorAsString("SALIDA.SGM-RGL-GR.SGM-ASO-GR.CO-SEGMENTO-DST",i,j));
						salidaSegmentacionesReglasDto.setSubSegmento(servicio.getValorAsString("SALIDA.SGM-RGL-GR.SGM-ASO-GR.CO-SUBSEGMENTO-DST",i,j));
						salidaSegmentacionesReglasDto.setSegmentoDes(servicio.getValorAsString("SALIDA.SGM-RGL-GR.SGM-ASO-GR.DS-SEGMENTO-UNM",i,j));
						salidaSegmentacionesReglasDto.setSubSegmentoDes(servicio.getValorAsString("SALIDA.SGM-RGL-GR.SGM-ASO-GR.DS-SUBSEGMENTO-UNM",i,j));
						
						salidaSegmentacionesReglasDto.setOfAtencion(servicio.getValorAsString("SALIDA.SGM-RGL-GR.SGM-ASO-GR.CO-OFICINA-AT",i,j));
						salidaSegmentacionesReglasDto.setTandem(servicio.getValorAsString("SALIDA.SGM-RGL-GR.SGM-ASO-GR.CO-NEMO-TANDEM",i,j));
										
						salidaSegmentacionesReglasDto.setFechaModSegmento(QGUtilidadesFechasUtils.formatearTimeStampDesdeCopy(servicio.getValorAsString("SALIDA.SGM-RGL-GR.SGM-ASO-GR.IT-ULT-MOD-RGL",i,j), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
						salidaSegmentacionesReglasDto.setUsuModSegmento(servicio.getValorAsString("SALIDA.SGM-RGL-GR.SGM-ASO-GR.CO-USUARIO-MOD-RGL",i,j));
						
						listaDatos.add(salidaSegmentacionesReglasDto);
					}
				}
				else
				{
					salidaSegmentacionesReglasDto.setSegmentoOrigen("");
					salidaSegmentacionesReglasDto.setSegmento("");
					salidaSegmentacionesReglasDto.setSubSegmento("");
					salidaSegmentacionesReglasDto.setSegmentoDes("");
					salidaSegmentacionesReglasDto.setSubSegmentoDes("");
					
					salidaSegmentacionesReglasDto.setOfAtencion("");
					salidaSegmentacionesReglasDto.setTandem("");
									
					salidaSegmentacionesReglasDto.setFechaModSegmento("");
					salidaSegmentacionesReglasDto.setUsuModSegmento("");
					
					listaDatos.add(salidaSegmentacionesReglasDto);					
				}					
			}
			QGCGlobalDto.setlistaDatos(listaDatos);

		} catch (NAWRException e) {
			logger.error(e.getMessage());
			QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e, QGIdentificadoresDescriptores.DESCRIPTOR_REGLAS_REGLAS);
		} finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return QGCGlobalDto;
		
	}
	public QGCGlobalDto obtenerListaReglasSeg(QGEntradaSegmentacionesReglasDto busquedaSegmentacion){
		logger.debug("Obteniendo lista buscando por segmento.");
		
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		ArrayList listaDatos = new ArrayList();		

		QGNpaNA servicio = null;
		
		try {

			// Se especifica el servicio al que se va a llamar
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_REGLAS_SEGMEN,obtenerUsuarioLogado());

			// Seteamos la copy de entrada
			servicio.setValor("ENTRADA.IN-TIP-CST-PSG",busquedaSegmentacion.getTipoConsulta());
			servicio.setValor("ENTRADA.CO-SEGMENTO-DST",busquedaSegmentacion.getSegmento());
			servicio.setValor("ENTRADA.CO-SUBSEGMENTO-DST",busquedaSegmentacion.getSubSegmento());
			servicio.setValor("ENTRADA.CO-OFICINA-AT",busquedaSegmentacion.getOfAtencion());
			servicio.setValor("ENTRADA.CO-NEMO-TANDEM",busquedaSegmentacion.getTandem());
			
			// Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();
			
			for (int i = 1; i <= servicio.getValorAsInt("SALIDA.OCU-NU").intValue(); i++) {
				QGSalidaSegmentacionesReglasDto salidaSegmentacionesReglasDto = new QGSalidaSegmentacionesReglasDto();
				
				salidaSegmentacionesReglasDto.setNDias(servicio.getValorAsString("SALIDA.REG-SEG-GR.CA-DIAS-PTE-SGM",i));
				salidaSegmentacionesReglasDto.setNTotalLin(servicio.getValorAsString("SALIDA.REG-SEG-GR.CA-LINEAS-TOTAL",i));
				salidaSegmentacionesReglasDto.setNLinMov(servicio.getValorAsString("SALIDA.REG-SEG-GR.CA-LINEAS-UNM",i));
				salidaSegmentacionesReglasDto.setNLinFija(servicio.getValorAsString("SALIDA.REG-SEG-GR.CA-LINEAS-UNF",i));
				
				salidaSegmentacionesReglasDto.setRegla(servicio.getValorAsString("SALIDA.REG-SEG-GR.CO-REGLA-SGM",i));
				salidaSegmentacionesReglasDto.setReglaDes(servicio.getValorAsString("SALIDA.REG-SEG-GR.DS-REGLA-SGM",i));
				
				salidaSegmentacionesReglasDto.setFechaIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("SALIDA.REG-SEG-GR.FX-INI-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				salidaSegmentacionesReglasDto.setFechaFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("SALIDA.REG-SEG-GR.FX-FIN-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));				
				salidaSegmentacionesReglasDto.setFechaModRegla(QGUtilidadesFechasUtils.formatearTimeStampDesdeCopy(servicio.getValorAsString("SALIDA.REG-SEG-GR.IT-ULT-MOD",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				
				salidaSegmentacionesReglasDto.setUsuModRegla(servicio.getValorAsString("SALIDA.REG-SEG-GR.CO-USUARIO-MOD",i));
				
				salidaSegmentacionesReglasDto.setUsuAlta(servicio.getValorAsString("SALIDA.REG-SEG-GR.CO-USUARIO-ALT",i));

				if (servicio.getValorAsInt("SALIDA.REG-SEG-GR.SGM-OCU-NU",i).intValue() > 0)
				{
					for (int j = 1; j <= servicio.getValorAsInt("SALIDA.REG-SEG-GR.SGM-OCU-NU",i).intValue(); j++) {
						salidaSegmentacionesReglasDto.setSegmentoOrigen(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.CO-SEGMENTO",i,j));
						salidaSegmentacionesReglasDto.setSegmento(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.CO-SEGMENTO-DST",i,j));
						salidaSegmentacionesReglasDto.setSubSegmento(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.CO-SUBSEGMENTO-DST",i,j));
						salidaSegmentacionesReglasDto.setSegmentoDes(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.DS-SEGMENTO-UNM",i,j));
						salidaSegmentacionesReglasDto.setSubSegmentoDes(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.DS-SUBSEGMENTO-UNM",i,j));
						
						salidaSegmentacionesReglasDto.setOfAtencion(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.CO-OFICINA-AT",i,j));
						salidaSegmentacionesReglasDto.setTandem(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.CO-NEMO-TANDEM",i,j));
										
						salidaSegmentacionesReglasDto.setFechaModSegmento(QGUtilidadesFechasUtils.formatearTimeStampDesdeCopy(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.IT-ULT-MOD-RGL",i,j), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
						salidaSegmentacionesReglasDto.setUsuModSegmento(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.CO-USUARIO-MOD-RGL",i,j));
						
						listaDatos.add(salidaSegmentacionesReglasDto);
					}
				}
				else
				{
					salidaSegmentacionesReglasDto.setSegmentoOrigen("");
					salidaSegmentacionesReglasDto.setSegmento("");
					salidaSegmentacionesReglasDto.setSubSegmento("");
					salidaSegmentacionesReglasDto.setSegmentoDes("");
					salidaSegmentacionesReglasDto.setSubSegmentoDes("");
					
					salidaSegmentacionesReglasDto.setOfAtencion("");
					salidaSegmentacionesReglasDto.setTandem("");
									
					salidaSegmentacionesReglasDto.setFechaModSegmento("");
					salidaSegmentacionesReglasDto.setUsuModSegmento("");
					
					listaDatos.add(salidaSegmentacionesReglasDto);					
				}
			}
			QGCGlobalDto.setlistaDatos(listaDatos);

		} catch (NAWRException e) {
			logger.error(e.getMessage());
			QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e, QGIdentificadoresDescriptores.DESCRIPTOR_REGLAS_SEGMEN);
		} finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return QGCGlobalDto;		
	}
	public QGCGlobalDto obtenerListaReglasTotal(QGEntradaSegmentacionesReglasDto busquedaSegmentacion){
		logger.debug("Obteniendo lista total.");
		
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		ArrayList listaDatos = new ArrayList();
				
		QGNpaNA servicio = null;
		
		try {

			// Se especifica el servicio al que se va a llamar
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_REGLAS_TODAS,obtenerUsuarioLogado());

			// Seteamos la copy de entrada
			servicio.setValor("ENTRADA.IN-ACTUACION",busquedaSegmentacion.getCodActuacion());
		
			// Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();

			for (int i = 1; i <= servicio.getValorAsInt("SALIDA.OCU-NU").intValue(); i++) {
				QGSalidaSegmentacionesReglasDto salidaSegmentacionesReglasDto = new QGSalidaSegmentacionesReglasDto();
				
				salidaSegmentacionesReglasDto.setNDias(servicio.getValorAsString("SALIDA.REG-SEG-GR.CA-DIAS-PTE-SGM",i));
				salidaSegmentacionesReglasDto.setNTotalLin(servicio.getValorAsString("SALIDA.REG-SEG-GR.CA-LINEAS-TOTAL",i));
				salidaSegmentacionesReglasDto.setNLinMov(servicio.getValorAsString("SALIDA.REG-SEG-GR.CA-LINEAS-UNM",i));
				salidaSegmentacionesReglasDto.setNLinFija(servicio.getValorAsString("SALIDA.REG-SEG-GR.CA-LINEAS-UNF",i));
				
				salidaSegmentacionesReglasDto.setRegla(servicio.getValorAsString("SALIDA.REG-SEG-GR.CO-REGLA-SGM",i));
				salidaSegmentacionesReglasDto.setReglaDes(servicio.getValorAsString("SALIDA.REG-SEG-GR.DS-REGLA-SGM",i));
				
				salidaSegmentacionesReglasDto.setFechaIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("SALIDA.REG-SEG-GR.FX-INI-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				salidaSegmentacionesReglasDto.setFechaFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("SALIDA.REG-SEG-GR.FX-FIN-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));				
				salidaSegmentacionesReglasDto.setFechaModRegla(QGUtilidadesFechasUtils.formatearTimeStampDesdeCopy(servicio.getValorAsString("SALIDA.REG-SEG-GR.IT-ULT-MOD",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				
				salidaSegmentacionesReglasDto.setUsuModRegla(servicio.getValorAsString("SALIDA.REG-SEG-GR.CO-USUARIO-MOD",i));
				
				salidaSegmentacionesReglasDto.setUsuAlta(servicio.getValorAsString("SALIDA.REG-SEG-GR.CO-USUARIO-ALT",i));
				
				if (servicio.getValorAsInt("SALIDA.REG-SEG-GR.SGM-OCU-NU",i).intValue() > 0)
				{
					for (int j = 1; j <= servicio.getValorAsInt("SALIDA.REG-SEG-GR.SGM-OCU-NU",i).intValue(); j++) {
						salidaSegmentacionesReglasDto.setSegmentoOrigen(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.CO-SEGMENTO",i,j));
						salidaSegmentacionesReglasDto.setSegmento(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.CO-SEGMENTO-DST",i,j));
						salidaSegmentacionesReglasDto.setSubSegmento(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.CO-SUBSEGMENTO-DST",i,j));
						salidaSegmentacionesReglasDto.setSegmentoDes(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.DS-SEGMENTO-UNM",i,j));
						salidaSegmentacionesReglasDto.setSubSegmentoDes(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.DS-SUBSEGMENTO-UNM",i,j));
						
						salidaSegmentacionesReglasDto.setOfAtencion(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.CO-OFICINA-AT",i,j));
						salidaSegmentacionesReglasDto.setTandem(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.CO-NEMO-TANDEM",i,j));
											
						salidaSegmentacionesReglasDto.setFechaModSegmento(QGUtilidadesFechasUtils.formatearTimeStampDesdeCopy(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.IT-ULT-MOD-RGL",i,j), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
						salidaSegmentacionesReglasDto.setUsuModSegmento(servicio.getValorAsString("SALIDA.REG-SEG-GR.SGM-ASO-GR.CO-USUARIO-MOD-RGL",i,j));
						
						listaDatos.add(salidaSegmentacionesReglasDto);
					}
				}
				else
				{
					salidaSegmentacionesReglasDto.setSegmentoOrigen("");
					salidaSegmentacionesReglasDto.setSegmento("");
					salidaSegmentacionesReglasDto.setSubSegmento("");
					salidaSegmentacionesReglasDto.setSegmentoDes("");
					salidaSegmentacionesReglasDto.setSubSegmentoDes("");
					
					salidaSegmentacionesReglasDto.setOfAtencion("");
					salidaSegmentacionesReglasDto.setTandem("");
										
					salidaSegmentacionesReglasDto.setFechaModSegmento("");
					salidaSegmentacionesReglasDto.setUsuModSegmento("");
					
					listaDatos.add(salidaSegmentacionesReglasDto);					
				}
			}
			QGCGlobalDto.setlistaDatos(listaDatos);

		} catch (NAWRException e) {
			logger.error(e.getMessage());
			QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e, QGIdentificadoresDescriptores.DESCRIPTOR_REGLAS_TODAS);
		} finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		
		return QGCGlobalDto;		
	}
	
	public QGCGlobalDto operarReglas(QGEntradaSegmentacionesReglasDto entrada){
			QGNpaNA servicio = null;
			QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

			try {
				servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_OPERAC_REGLAS, obtenerUsuarioLogado());			
			
				servicio.setValor("ENTRADA.CO-SEGMENTO", entrada.getSegmentoOrigen(),true);					
				servicio.setValor("ENTRADA.CO-SEGMENTO-DST", entrada.getSegmento(),true);
				servicio.setValor("ENTRADA.CO-SUBSEGMENTO-DST", entrada.getSubSegmento(),true);	
				servicio.setValor("ENTRADA.CO-OFICINA-AT", entrada.getOfAtencion());
				servicio.setValor("ENTRADA.CO-NEMO-TANDEM", entrada.getTandem());			
				servicio.setValor("ENTRADA.CO-REGLA-SGM", entrada.getRegla());
				servicio.setValor("ENTRADA.CA-LINEAS-UNF", entrada.getNLinFija());
				servicio.setValor("ENTRADA.CA-LINEAS-UNM", entrada.getNLinMovil());			
				servicio.setValor("ENTRADA.CA-LINEAS-TOTAL", entrada.getNTotalLin());			
				servicio.setValor("ENTRADA.CA-DIAS-PTE-SGM", entrada.getNDias());
				servicio.setValor("ENTRADA.FX-FIN-VIGENCIA", QGUtilidadesFechasUtils.formatearFechaParaCopy(entrada.getFechaFinVigencia(),QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				
				servicio.ejecutarTransaccion();

			} catch (NAWRException e) {
				logger.error(e.getMessage());
				QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e, QGIdentificadoresDescriptores.DESCRIPTOR_OPERAC_REGLAS);				
			}finally {
				if (servicio != null) {
					servicio.unload();
				}
			}
			return QGCGlobalDto;
	}
	
}
