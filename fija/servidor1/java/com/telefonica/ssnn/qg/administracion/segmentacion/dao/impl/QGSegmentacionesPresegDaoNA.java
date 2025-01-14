/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.dao.impl;


import java.util.ArrayList;

import org.apache.log4j.Logger;


import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.administracion.segmentacion.dao.interfaz.QGSegmentacionesPresegDao;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaPresegmentacionAdminDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaPresegmentacionDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaSegmentacionesPresegDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSalidaPresegmentacionAdminDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSalidaPresegmentacionDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSalidaSegmentacionesPresegDto;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
import com.telefonica.ssnn.qg.exceptions.QGApplicationException;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.util.QGCatalogoDto;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;
import com.telefonica.ssnn.qg.util.QGUtilidadesNegocioUtils;


/**
 * @author jacastano
 * 
 */
public class QGSegmentacionesPresegDaoNA extends QGBaseDao implements
		QGSegmentacionesPresegDao {

	private static final Logger logger = Logger
			.getLogger(QGSegmentacionesTraduccionDaoNA.class);
	
	private static final String IND_PG_OK = "S";

	public QGCGlobalPagingDto gestionarPresegmentacion(
				QGEntradaSegmentacionesPresegDto presegmentacion) {
		logger.debug("Obteniendo lista de presegmentaciones");
		
		QGCGlobalPagingDto qGCGlobalPagingDto = new QGCGlobalPagingDto();
		ArrayList listaDatos = new ArrayList();

		QGNpaNA servicio = null;
		
		try {

			// Se especifica el servicio al que se va a llamar
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_PRESEG,obtenerUsuarioLogado());

			// Seteamos la copy de entrada
			servicio.setValor("ENTRADA.IN-ACTUACION",presegmentacion.getCodActuacion());
			servicio.setValor("ENTRADA.IN-TIP-CST-PSG",presegmentacion.getTipoConsulta());
			servicio.setValor("ENTRADA.IN-TIP-VIG-PSG",presegmentacion.getInVigencia());
			servicio.setValor("ENTRADA.CO-TIPO-DOCUMENTO",presegmentacion.getTipoDocumento());
			servicio.setValor("ENTRADA.NU-DOCUMENTO",presegmentacion.getNumDocumento());
			servicio.setValor("ENTRADA.CO-SEGMENTO",presegmentacion.getSegmento());
			servicio.setValor("ENTRADA.CO-SUBSEGMENTO",presegmentacion.getSubSegmento());
			servicio.setValor("ENTRADA.CO-OFICINA-AT",presegmentacion.getOfAtencion());
			servicio.setValor("ENTRADA.CO-NEMO-TANDEM",presegmentacion.getTandem());
			
			servicio.setValor("ENTRADA.CO-OFICINA-AT",presegmentacion.getOfAtencion());
			servicio.setValor("ENTRADA.CO-NEMO-TANDEM",presegmentacion.getTandem());
			
			servicio.setValor("ENTRADA.PGN-TX",presegmentacion.getPgnTx());
			servicio.setValor("ENTRADA.OCU-NU",presegmentacion.getOcuNu());
			
			// Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();

			String pgnTx = servicio.getValorAsString("SALIDA.PGN-TX");
			String indPgnIn = servicio.getValorAsString("SALIDA.IND-PGN-IN");
			
			for (int i = 1; i <= servicio.getValorAsInt("SALIDA.OCU-NU").intValue(); i++) {
				QGSalidaSegmentacionesPresegDto salidaSegmentacionesPresegDto = new QGSalidaSegmentacionesPresegDto();
				
				salidaSegmentacionesPresegDto.setNuSegPsg(servicio.getValorAsString("SALIDA.PSG-DAT-GR.NU-SEC-PSG",i));
				salidaSegmentacionesPresegDto.setTipoDocumento(servicio.getValorAsString("SALIDA.PSG-DAT-GR.CO-TIPO-DOCUMENTO",i));
				salidaSegmentacionesPresegDto.setNumDocumento(servicio.getValorAsString("SALIDA.PSG-DAT-GR.NU-DOCUMENTO",i));
				salidaSegmentacionesPresegDto.setSegmento(servicio.getValorAsString("SALIDA.PSG-DAT-GR.CO-SEGMENTO",i));
				salidaSegmentacionesPresegDto.setSubSegmento(servicio.getValorAsString("SALIDA.PSG-DAT-GR.CO-SUBSEGMENTO",i));
				
				salidaSegmentacionesPresegDto.setSegmentoDes(servicio.getValorAsString("SALIDA.PSG-DAT-GR.DS-SEGMENTO-UNM",i));
				salidaSegmentacionesPresegDto.setSubSegmentoDes(servicio.getValorAsString("SALIDA.PSG-DAT-GR.DS-SUBSEGMENTO-UNM",i));
				
				salidaSegmentacionesPresegDto.setOfAtencion(servicio.getValorAsString("SALIDA.PSG-DAT-GR.CO-OFICINA-AT",i));
				salidaSegmentacionesPresegDto.setTandem(servicio.getValorAsString("SALIDA.PSG-DAT-GR.CO-NEMO-TANDEM",i));
				
				salidaSegmentacionesPresegDto.setCodOrigPresegmentacion(servicio.getValorAsString("SALIDA.PSG-DAT-GR.CO-ORI-PSG",i));
				salidaSegmentacionesPresegDto.setDesOrigPresegmentacion(servicio.getValorAsString("SALIDA.PSG-DAT-GR.DS-ORI-PSG",i));
				
				salidaSegmentacionesPresegDto.setInPtePreseg(servicio.getValorAsString("SALIDA.PSG-DAT-GR.IN-PTE-PSG",i));
				
				salidaSegmentacionesPresegDto.setFechaIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("SALIDA.PSG-DAT-GR.FX-INI-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				salidaSegmentacionesPresegDto.setFechaFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("SALIDA.PSG-DAT-GR.FX-FIN-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				
				salidaSegmentacionesPresegDto.setUsuarioAlta(servicio.getValorAsString("SALIDA.PSG-DAT-GR.CO-USUARIO-ALT",i));
				salidaSegmentacionesPresegDto.setFechaModif(QGUtilidadesFechasUtils.formatearTimeStampDesdeCopy(servicio.getValorAsString("SALIDA.PSG-DAT-GR.IT-ULT-MOD",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				salidaSegmentacionesPresegDto.setUsuarioModif(servicio.getValorAsString("SALIDA.PSG-DAT-GR.CO-USUARIO-MOD",i));
				salidaSegmentacionesPresegDto.setCodMotivoBaja(servicio.getValorAsString("SALIDA.PSG-DAT-GR.CO-MOT-BAJ-PSG",i));
				
				salidaSegmentacionesPresegDto.setDesMotivoBaja(servicio.getValorAsString("SALIDA.PSG-DAT-GR.DS-MOT-BAJ-PSG",i));
				
				listaDatos.add(salidaSegmentacionesPresegDto);
			}
			qGCGlobalPagingDto.setListaDatos(listaDatos);
			qGCGlobalPagingDto.setIndPgnIn(indPgnIn);
			qGCGlobalPagingDto.setPgnTx(pgnTx);

		} catch (NAWRException e) {
			logger.error(e.getMessage());
			QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e, QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_PRESEG);
		} finally {
			if (servicio != null) {
				servicio.unload();
			}
		}

		return qGCGlobalPagingDto;
	}
		
	public QGCGlobalPagingDto obtenerDatosOfAtencion(QGEntradaPresegmentacionDto entrada){
		
		ArrayList listaDatos = new ArrayList();
		QGCGlobalPagingDto qGCGlobalPagingDto = new QGCGlobalPagingDto();
		logger.debug("Obteniendo lista de of. atencion");
		
		
		QGNpaNA servicio = null;
		try {

			// Se especifica el servicio al que se va a llamar
			servicio = QGNpaNA.obtenerServicioGF(QGIdentificadoresDescriptores.DESCRIPTOR_OF_ATENCION);
			
			String pgnTx = null;
			String indPgnIn = null;
			do {
			    
    			// Seteamos la copy de entrada
    			servicio.setValor("ENTRADA.CO-SEGMENTO", entrada.getCodigoSegmento());
    			servicio.setValor("ENTRADA.CO-SUBSEGMENTO", entrada.getCodigoSubsegmento());
    			
    			 if (null != pgnTx) {
    			     servicio.setValor("ENTRADA.PGN-TX", pgnTx);
    			 }
    			
    			// Ejecutamos la transaccion del servicio.
    			servicio.ejecutarTransaccion();
    
    			pgnTx = servicio.getValorAsString("SALIDA.PGN-TX");
    			indPgnIn = servicio.getValorAsString("SALIDA.IND-PGN-IN");
    			int ocuNu = servicio.getValorAsInt("SALIDA.OCU-NU").intValue();
    			
    			for (int i = 1; i <= ocuNu; i++) {
    				String coOficinaAt = servicio.getValorAsString("SALIDA.CO-OFICINA-AT",i);
    				listaDatos.add(coOficinaAt);
    			}
    			qGCGlobalPagingDto.setIndPgnIn(indPgnIn);
    			qGCGlobalPagingDto.setPgnTx(pgnTx);
    			qGCGlobalPagingDto.setListaDatos(listaDatos);
    			
			} while(IND_PG_OK.equals(indPgnIn.toString()));

		} catch (NAWRException e) {
			logger.error(e.getCampo("PARAMETROS_ERROR"));
			throw new QGApplicationException(e.getCampo("PARAMETROS_ERROR"));
		} finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return qGCGlobalPagingDto;	
	}
	
	public QGCGlobalPagingDto obtenerTandem(QGEntradaPresegmentacionDto entrada){
		ArrayList listaDatos = new ArrayList();
		QGCGlobalPagingDto qGCGlobalPagingDto = new QGCGlobalPagingDto();
		logger.debug("Obteniendo lista de tandem");
		
		
		QGNpaNA servicio = null;
		try {			
			// Se especifica el servicio al que se va a llamar
			servicio = QGNpaNA.obtenerServicioGF(QGIdentificadoresDescriptores.DESCRIPTOR_TANDEM);
			
			String pgnTx = null;
            String indPgnIn = null;
            
            do {
                
    			// Seteamos la copy de entrada
    			servicio.setValor("ENTRADA.CO-SEGMENTO", entrada.getCodigoSegmento());
    			servicio.setValor("ENTRADA.CO-SUBSEGMENTO", entrada.getCodigoSubsegmento());
    			servicio.setValor("ENTRADA.CO-OFICINA-AT", entrada.getOfAtencion());
    			
    			if (null != pgnTx) {
                    servicio.setValor("ENTRADA.PGN-TX", pgnTx);
                }
    			
    			// Ejecutamos la transaccion del servicio.
    			servicio.ejecutarTransaccion();
    
    			pgnTx = servicio.getValorAsString("SALIDA.PGN-TX");
    			indPgnIn = servicio.getValorAsString("SALIDA.IND-PGN-IN");
    			int ocuNu = servicio.getValorAsInt("SALIDA.OCU-NU").intValue();
    			
    			for (int i = 1; i <= ocuNu; i++) {
    				QGSalidaPresegmentacionDto nuevo = new QGSalidaPresegmentacionDto();
    				nuevo.setCoNemoTandem(servicio.getValorAsString("SALIDA.TDM-OFE-LST-GR.CO-NEMO-TANDEM",i));
    				nuevo.setCoUoVenta(servicio.getValorAsString("SALIDA.TDM-OFE-LST-GR.CO-UO-VENTA",i));
    				
    				listaDatos.add(nuevo);
    			} 
    			qGCGlobalPagingDto.setIndPgnIn(indPgnIn);
    			qGCGlobalPagingDto.setPgnTx(pgnTx);
    			qGCGlobalPagingDto.setListaDatos(listaDatos);
    			
            }while(IND_PG_OK.equals(indPgnIn.toString()));

		} catch (NAWRException e) {
			logger.error(e.getCampo("PARAMETROS_ERROR"));
			throw new QGApplicationException(e.getCampo("PARAMETROS_ERROR"));
		} finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return qGCGlobalPagingDto;	
	}

	public QGCGlobalDto obtenerSubsegmentos(QGEntradaSegmentacionesPresegDto busquedaSegmentacion){
		logger.debug("Obteniendo listado de subsegmentos.");
		
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		ArrayList listaDatos = new ArrayList();
		QGNpaNA servicio = null;
		
		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_LISTA_SUB_SEGMENTOS, obtenerUsuarioLogado());

			//ENTRADA
			servicio.setValor("ENTRADA.CO-SGM-UNF-UNM", busquedaSegmentacion.getSegmento());
			servicio.setValor("ENTRADA.CO-SBG-UNF-UNM", busquedaSegmentacion.getSubSegmento());
			servicio.setValor("ENTRADA.CO-LIN-NGC",busquedaSegmentacion.getTipoConsulta());
			
			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();
			//SALIDA
			//mapeamos la salida del servidio NA
			for (int i = 1; i <= servicio.getValorAsInt("SALIDA.OCU-NU").intValue(); i++) {
				QGCatalogoDto descripcionesDto = new QGCatalogoDto();
				descripcionesDto.setCodigo(servicio.getValorAsString("SALIDA.UNM-UNF-SBG-GR.CO-SBG-UNF-UNM",i));
				descripcionesDto.setDescripcion(servicio.getValorAsString("SALIDA.UNM-UNF-SBG-GR.DS-SUBSEGMENTO-UNM",i));

				listaDatos.add(descripcionesDto);
			}
		} catch (NAWRException e) {
			logger.error(e.getMessage());
			QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e,QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_LISTA_SUB_SEGMENTOS);
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		
		QGCGlobalDto.setlistaDatos(listaDatos);
		return QGCGlobalDto;
	}

	public QGCGlobalDto gestionAdministracion(QGEntradaPresegmentacionAdminDto entrada){
		
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		ArrayList listaDatos = new ArrayList();
		QGNpaNA servicio = null;
		
		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_GESTION_ADMIN, obtenerUsuarioLogado());

			//ENTRADA
			servicio.setValor("ENTRADA.CO-TP-PARAM-GBL", entrada.getCodParametro());
			servicio.setValor("ENTRADA.NO-PARAMETRO-GBL", entrada.getDesParametro());
			servicio.setValor("ENTRADA.IN-ACTUACION",entrada.getInActuacion());
			servicio.setValor("ENTRADA.IN-TIPO-VALOR", entrada.getTipoValor());
			servicio.setValor("ENTRADA.DS-VALOR-ALFA", entrada.getValorAlfa());
			servicio.setValor("ENTRADA.NU-VALOR-PARAM",entrada.getValorNum());
			
			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();
			//SALIDA
			//mapeamos la salida del servicio NA
			
			QGSalidaPresegmentacionAdminDto salida = new QGSalidaPresegmentacionAdminDto();
			salida.setCodTipoParametro(servicio.getValorAsString("SALIDA.CO-TP-PARAM-GBL"));
			salida.setNomTipoParametro(servicio.getValorAsString("SALIDA.NO-PARAMETRO-GBL"));
			salida.setDesTipoParametro(servicio.getValorAsString("SALIDA.DS-PARAMETRO-GBL"));	
			salida.setDesValorAlfa(servicio.getValorAsString("SALIDA.DS-VALOR-ALFA"));
			salida.setDesValorNum(servicio.getValorAsString("SALIDA.NU-VALOR-PARAM"));
			salida.setFxFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("SALIDA.FX-FIN-VIGENCIA"), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			salida.setFxIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("SALIDA.FX-INI-VIGENCIA"), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			salida.setFxUltimaMod(QGUtilidadesFechasUtils.formatearTimeStampDesdeCopy(servicio.getValorAsString("SALIDA.IT-ULT-MOD"), QGUtilidadesFechasUtils.FORMATO_FECHA_1));	
			
			listaDatos.add(salida);
			
		} catch (NAWRException e) {
			logger.error(e.getMessage());
			QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e,QGIdentificadoresDescriptores.DESCRIPTOR_GESTION_ADMIN);
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}

		QGCGlobalDto.setlistaDatos(listaDatos);
		
		return QGCGlobalDto;
		
	}
	
	public QGCGlobalDto operarPreseg(QGEntradaSegmentacionesPresegDto entrada){
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_OPERAC_PRESEG, obtenerUsuarioLogado());			

			servicio.setValor("ENTRADA.IN-ACTUACION", entrada.getCodActuacion(),true);
			servicio.setValor("ENTRADA.NU-SEC-PSG", entrada.getNuSegPsg(),true);	
			servicio.setValor("ENTRADA.CO-TIPO-DOCUMENTO", entrada.getTipoDocumento());
			servicio.setValor("ENTRADA.NU-DOCUMENTO", entrada.getNumDocumento().trim());
			servicio.setValor("ENTRADA.CO-SEGMENTO", entrada.getSegmento(),true);
			servicio.setValor("ENTRADA.CO-SUBSEGMENTO", entrada.getSubSegmento(),true);	
			servicio.setValor("ENTRADA.CO-OFICINA-AT", entrada.getOfAtencion());
			servicio.setValor("ENTRADA.CO-NEMO-TANDEM", entrada.getTandem());
			
			servicio.ejecutarTransaccion();

		} catch (NAWRException e) {
			//throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
			logger.error(e.getMessage());
			QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e, QGIdentificadoresDescriptores.DESCRIPTOR_OPERAC_PRESEG);			
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return QGCGlobalDto;
		
	}
}
