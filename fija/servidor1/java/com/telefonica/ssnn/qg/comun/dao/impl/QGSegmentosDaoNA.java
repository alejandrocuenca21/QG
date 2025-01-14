package com.telefonica.ssnn.qg.comun.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.caches.QGSegmentosCache;
import com.telefonica.ssnn.qg.caches.QGSubsegmentosCache;
import com.telefonica.ssnn.qg.comun.dao.interfaz.QGSegmentosDao;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.util.QGCatalogoDto;
import com.telefonica.ssnn.qg.util.QGComboSegmentosDto;
import com.telefonica.ssnn.qg.util.QGUtilidadesNegocioUtils;

public class QGSegmentosDaoNA extends QGBaseDao implements QGSegmentosDao {

	
	private static final Logger logger = Logger
	.getLogger(QGSegmentosDaoNA.class);
	/**
	 * identifica los segmentos fijos
	 */
	public static final String ID_FIJOS = "01";
	/**
	 * identifica los segmentos moviles.
	 */
	public static final String ID_MOVILES = "02";
	/**
	 * identifica todos
	 */
	public static final String ID_TODOS = "00";
	
	/**
	 * Obtiene los segmentos fijos
	 */
	public QGCGlobalDto obtenerSegmentos() {
		rellenarSegmentos();
		return obtenerSegmentos(Boolean.TRUE);
	}
	
	/**
	 * Obtiene los segmentos moviles
	 */
	public QGCGlobalDto obtenerSegmentosMovil() {
		rellenarSegmentos();
		return obtenerSegmentos(Boolean.FALSE);
	}
	
	/**
	 * Rellena las caches de segmentos con la llamada generica
	 * @return
	 */
	private void rellenarSegmentos(){
		logger.debug("Obtenidendo todo el listado");
		ArrayList listaDatos = new ArrayList();
		QGNpaNA servicio = null;
		try {

			if(QGSegmentosCache.getInstance().getSegmentos(Boolean.TRUE) == null && QGSegmentosCache.getInstance().getSegmentos(Boolean.FALSE)==null){
			
				servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_LISTA_SEGMENTOS, obtenerUsuarioLogado());
				//Segun el parametro fijos, buscaremos unos segmentos u otros.
				String parametroTipoSegmento = ID_TODOS;
					
				//Realizamos la llamada
				listaDatos = llamadaServicioSegmentos(servicio,
						parametroTipoSegmento);
	
				ArrayList listaDatosFijo = new ArrayList();
				ArrayList listaDatosMovil = new ArrayList();
				
				//Recorremos la lista generica y diferenciamos entre F y M
				Iterator iterator = listaDatos.iterator();
				while(iterator.hasNext()){
					QGComboSegmentosDto elemento = (QGComboSegmentosDto)iterator.next();
					if(QGConstantes.CODIGO_FIJO.equals(elemento.getLineaNegocio())){
						listaDatosFijo.add(elemento);
					}else if(QGConstantes.CODIGO_MOVIL.equals(elemento.getLineaNegocio())){
						listaDatosMovil.add(elemento);
					}
				}
				
				//cacheamos el listado.
				QGSegmentosCache.getInstance().setSegmentos(listaDatosFijo,Boolean.TRUE);
				QGSegmentosCache.getInstance().setSegmentos(listaDatosMovil,Boolean.FALSE);
			}else{
				this.obtenerSegmentos(Boolean.TRUE);
				this.obtenerSegmentos(Boolean.FALSE);
			}
		} catch (NAWRException e) {
			logger.error(e.getMessage());
			QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e,QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_LISTA_SEGMENTOS);
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}			
	}
	

	/**
	 * Obtiene los segmentos (Fijos / Moviles) según un parámetro.
	 * @param fijos - TRUE si tiene que traer los fijos
	 * 						 - FALSE si tiene que traer los moviles.
	 * 
	 * @return QGCGlobalDto
	 */
	private QGCGlobalDto obtenerSegmentos(Boolean fijos) {
		logger.debug("Obtenidendo listado de segmentos fijos");
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		//obtenemos el listado de la cache y si no existe, lo obtenemos del servicio.
		ArrayList listaDatos = QGSegmentosCache.getInstance().getSegmentos(fijos);

		
		if(listaDatos == null){
			QGNpaNA servicio = null;
			listaDatos = new ArrayList();
			try {

				servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_LISTA_SEGMENTOS, obtenerUsuarioLogado());
				//Segun el parametro fijos, buscaremos unos segmentos u otros.
				String parametroTipoSegmento = "";
				if(fijos.equals(Boolean.FALSE)){
					parametroTipoSegmento = ID_MOVILES;
				}else{
					parametroTipoSegmento = ID_FIJOS;
				}
				
				//Realizamos la llamada
				listaDatos = llamadaServicioSegmentos(servicio,
						parametroTipoSegmento);
	
				
				//cacheamos el listado.
				QGSegmentosCache.getInstance().setSegmentos(listaDatos,fijos);
			
			} catch (NAWRException e) {
				logger.error(e.getMessage());
				QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e,QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_LISTA_SEGMENTOS);
			}finally {
				if (servicio != null) {
					servicio.unload();
				}
			}			
		}else{
			logger.info("Segmentos recuperados de la caché");
		}
		QGCGlobalDto.setlistaDatos(listaDatos);
	return QGCGlobalDto;
	}

	/**
	 * Llamada al servicio de segmentos.
	 * @param servicio
	 * @param parametroTipoSegmento
	 * @return
	 * @throws NAWRException
	 */
	private ArrayList llamadaServicioSegmentos(QGNpaNA servicio, String parametroTipoSegmento)
			throws NAWRException {
		
		ArrayList listaDatos = new ArrayList();
		
		servicio.setValor("ENTRADA.CO-LIN-NGC",parametroTipoSegmento);
		
		//Ejecutamos la transaccion del servicio.
		servicio.ejecutarTransaccion();

		for (int i = 1; i <= servicio.getValorAsInt("OCU-NU").intValue(); i++) {
			QGComboSegmentosDto descripcionesDto = new QGComboSegmentosDto();
			descripcionesDto.setCodigo(servicio.getValorAsString("CO-SGM-UNF-UNM",i));
			descripcionesDto.setDescripcion(servicio.getValorAsString("DS-SEGMENTO-UNM",i));
			descripcionesDto.setLineaNegocio(servicio.getValorAsString("IN-UNI-NGC-CLI-GBL",i));
			listaDatos.add(descripcionesDto);
		}
		
		return listaDatos;
	}
	
	/**
	 * Obtiene los subsegmentos fijos por codigo segmento
	 */
	public QGCGlobalDto obtenerSubSegmentos(String codigoSegmento) {
		return obtenerSubSegmentos(codigoSegmento,Boolean.TRUE);
	}
	/**
	 * Obtiene los subsegmentos moviles por codigo segmento
	 */
	public QGCGlobalDto obtenerSubSegmentosMovil(String codigoSegmento) {
		return obtenerSubSegmentos(codigoSegmento,Boolean.FALSE);
	}


	/**
	 * Obtiene los subsegmentos asociados a un codigo de segmento, discerniendo entre fijos y moviles segun un parametro.
	 * @param codigoSegmento - codigo de segmento por el que filtrar
	 * @param fijos - indica si tienen que ser los fijos o no.
	 * @return
	 */
	private QGCGlobalDto obtenerSubSegmentos(String codigoSegmento,
			Boolean fijos) {
		logger.debug("Obteniendo listado de subsegmentos.");
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		//cogemos la lista de la caché y si no existe, vamos al sevicio a buscarla 
		ArrayList listaDatos = QGSubsegmentosCache.getInstance().getSubSegmentos(codigoSegmento,fijos);

		if(listaDatos == null){			  
			listaDatos = new ArrayList();
			QGNpaNA servicio = null;
			try {
				servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_LISTA_SUB_SEGMENTOS, obtenerUsuarioLogado());

				//ENTRADA
				servicio.setValor("CO-SGM-UNF-UNM", codigoSegmento);
				
				String parametroTipoSegmento = "";
				if(fijos.equals(Boolean.FALSE)){
					parametroTipoSegmento = ID_MOVILES;
				}else{
					parametroTipoSegmento = ID_FIJOS;
				}
				servicio.setValor("ENTRADA.CO-LIN-NGC",parametroTipoSegmento);
				
				//Ejecutamos la transaccion del servicio.
				servicio.ejecutarTransaccion();
				//SALIDA
				//mapeamos la salida del servidio NA
				for (int i = 1; i <= servicio.getValorAsInt("OCU-NU").intValue(); i++) {
					QGCatalogoDto descripcionesDto = new QGCatalogoDto();
					descripcionesDto.setCodigo(servicio.getValorAsString("CO-SBG-UNF-UNM",i));
					descripcionesDto.setDescripcion(servicio.getValorAsString("DS-SUBSEGMENTO-UNM",i));

					listaDatos.add(descripcionesDto);
				}
				//cacheamos la lista
				QGSubsegmentosCache.getInstance().setSubsegmentos(codigoSegmento, listaDatos,fijos);
				
			} catch (NAWRException e) {
				logger.error(e.getMessage());
				QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e,QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_LISTA_SUB_SEGMENTOS);
			}finally {
				if (servicio != null) {
					servicio.unload();
				}
			}
			
		}else{
			logger.info("Subsegmentos recuperados de la caché");
		}
		QGCGlobalDto.setlistaDatos(listaDatos);
		return QGCGlobalDto;
	}

	
}
