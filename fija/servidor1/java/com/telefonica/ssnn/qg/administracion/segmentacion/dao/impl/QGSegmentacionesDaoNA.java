/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.dao.impl;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.administracion.segmentacion.dao.interfaz.QGSegmentacionesDao;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSegmentacionesBusquedaDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSegmentacionesDto;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.caches.QGSegmentosCache;
import com.telefonica.ssnn.qg.caches.QGSubsegmentosCache;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;
import com.telefonica.ssnn.qg.util.QGUtilidadesNegocioUtils;

/**
 * @author mgvinuesa
 * 
 */
public class QGSegmentacionesDaoNA extends QGBaseDao implements
QGSegmentacionesDao {

	private static final Logger logger = Logger
	.getLogger(QGSegmentacionesDaoNA.class);


	/**
	 * Busqueda las segmentaciones por criterio
	 * @param busquedaSegmentacion criterios de busqueda
	 * @return Listado de segmentaciones obtenidas
	 */
	public QGCGlobalDto obtenerListaSegmentaciones(
			QGSegmentacionesBusquedaDto busquedaSegmentacion) {

		 
		logger.debug("Obteniendo lista de segmentaciones");
		QGNpaNA servicio = null;
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		ArrayList listaDatos = new ArrayList();

		try {

			// Se especifica el servicio al que se va a llamar
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_LISTA_SEGMENTACIONES,obtenerUsuarioLogado());

			// Seteamos la copy de entrada
			servicio.setValor("ENTRADA.CO-SGM-UNF-UNM", busquedaSegmentacion.getCodigoSegmentoOrigen());
			servicio.setValor("ENTRADA.CO-SBG-UNF-UNM", busquedaSegmentacion.getCodigoSubSegmentoOrigen());
			servicio.setValor("ENTRADA.IN-OPERACION", busquedaSegmentacion.getOperacion());
			servicio.setValor("ENTRADA.CO-LIN-NGC", busquedaSegmentacion.getUnidad());
			servicio.setValor("IN-ACTUACION", busquedaSegmentacion.getActuacionBusqueda());
			
			
			// Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();

			QGSegmentacionesDto segmentacionesDto = null;
			for (int i = 1; i <= servicio.getValorAsInt("OCU-NU").intValue(); i++) {
				segmentacionesDto = new QGSegmentacionesDto();

				segmentacionesDto.setFechaIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-INI-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				segmentacionesDto.setFechaFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-FIN-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				
				segmentacionesDto.setOperacion(servicio.getValorAsString("IN-OPERACION",i));
				if(segmentacionesDto.getOperacion().equals("A")){
					segmentacionesDto.setDescOperacion(QGConstantes.DESCRIPCION_OPERACION_A);
				}else {
					segmentacionesDto.setDescOperacion(QGConstantes.DESCRIPCION_OPERACION_B);
				}							
				segmentacionesDto.setSegmentoDestino(servicio.getValorAsString("CO-SGM-UNF-UNM",i));
				segmentacionesDto.setSegmentoOrigen(servicio.getValorAsString("CO-SGM-UNF-UNM",i));
				segmentacionesDto.setSubSegmentoDestino(servicio.getValorAsString("CO-SBG-DST",i));
				segmentacionesDto.setSubSegmentoOrigen(servicio.getValorAsString("CO-SBG-UNF-UNM",i));
				
				segmentacionesDto.setUnidad(servicio.getValorAsString("CO-LIN-NGC",i));				
				if(segmentacionesDto.getUnidad().equals("01")){
					segmentacionesDto.setDescUnidad(QGConstantes.DESCRIPCION_UNIDAD_01);
				}else {
					segmentacionesDto.setDescUnidad(QGConstantes.DESCRIPCION_UNIDAD_02);
				}	
				
				segmentacionesDto.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT",i));
				segmentacionesDto.setFechaBaja(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-BAJA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				segmentacionesDto.setUsuarioBaja(servicio.getValorAsString("CO-USUARIO-MOD",i));
				//Campos descripcion los traemos de la cache de segmentos
				segmentacionesDto.setSegmentoOrigenDescripcion(QGSegmentosCache.getInstance().getDescripcion(segmentacionesDto.getSegmentoOrigen()));
				segmentacionesDto.setSegmentoDestinoDescripcion(QGSegmentosCache.getInstance().getDescripcion(segmentacionesDto.getSegmentoDestino()));
				
				//Si viene informados dichos campos obviamos la cache
				String descSubSegOrigen = servicio.getValorAsString("DS-SBG-UNF-UNM",i);
				if(StringUtils.isEmpty(descSubSegOrigen)){
					descSubSegOrigen = QGSubsegmentosCache.getInstance().getDescripcion(segmentacionesDto.getSegmentoOrigen(), segmentacionesDto.getSubSegmentoOrigen());
				}
				segmentacionesDto.setSubSegmentoOrigenDescripcion(descSubSegOrigen);
				
				//Descripcion del Subsegmento destino
				String descSubSegDest = servicio.getValorAsString("DS-SBG-UNF-UNM-DST",i);
				if(StringUtils.isEmpty(descSubSegDest)){
					descSubSegDest = QGSubsegmentosCache.getInstance().getDescripcion(segmentacionesDto.getSegmentoDestino(), segmentacionesDto.getSubSegmentoDestino());
				}
				segmentacionesDto.setSubSegmentoDestinoDescripcion(descSubSegDest);

				listaDatos.add(segmentacionesDto);
			}
			qGCGlobalDto.setlistaDatos(listaDatos);

		} catch (NAWRException e) {
			logger.error(e.getMessage());
			QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e, QGIdentificadoresDescriptores.DESCRIPTOR_LISTA_SEGMENTACIONES);
		} finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return qGCGlobalDto;
	}

	/**
	 * Gestiona una segmentacion ya sea darla de alta, modificarla o darla de baja
	 * depende del codigo de actuacion que tenga la segmentacion
	 * @param segmentacion datos de la segmentacion
	 * @return resultado de la accion
	 */
	public QGCGlobalDto modificarSegmentacion(QGSegmentacionesDto segmentacion) {

		QGNpaNA servicio = null;
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_MODIFICAR_SEGMENTACION, this.obtenerUsuarioLogado()); 			
			// Seteamos la copy de entrada
			//Campos de entrada
			servicio.setValor("IN-ACTUACION", segmentacion.getCodActuacion());	//M: Modificacion, A: Alta, B:Baja

			servicio.setValor("CO-LIN-NGC", segmentacion.getUnidad());
			servicio.setValor("IN-OPERACION", segmentacion.getOperacion());
			servicio.setValor("CO-SGM-UNF-UNM", segmentacion.getSegmentoOrigen());
			servicio.setValor("CO-SBG-UNF-UNM", segmentacion.getSubSegmentoOrigen());
			//servicio.setValor("", segmentacion.getSegmentoDestino());
			servicio.setValor("CO-SBG-DST", segmentacion.getSubSegmentoDestino());
			servicio.setValor("FX-INI-VIGENCIA", QGUtilidadesFechasUtils.formatearFechaParaCopy(segmentacion.getFechaIniVigencia(), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			servicio.setValor("FX-FIN-VIGENCIA", QGUtilidadesFechasUtils.formatearFechaParaCopy(segmentacion.getFechaFinVigencia(), QGUtilidadesFechasUtils.FORMATO_FECHA_1));

			//			if(segmentacion.getCodActuacion().equals(QGConstantes.CODIGO_ALTA)){
			servicio.setValor("CO-USUARIO-ALT", this.obtenerUsuarioLogado().getUsername());
			//			}else if(segmentacion.getCodActuacion().equals(QGConstantes.CODIGO_BAJA)){
			//				servicio.setValor("CO-USUARIO-BAJA", this.obtenerUsuarioLogado().getUsername());
			//			}

			// Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();

		} catch (NAWRException e) {
			logger.error(e.getMessage());			
			QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e,QGIdentificadoresDescriptores.DESCRIPTOR_MODIFICAR_SEGMENTACION );
			
		} finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return qGCGlobalDto;
	}
}