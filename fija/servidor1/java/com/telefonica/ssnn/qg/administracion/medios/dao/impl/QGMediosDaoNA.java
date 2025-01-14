/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.medios.dao.impl;

import java.util.ArrayList;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGAutorizacionesBusquedaDto;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGAutorizacionesDto;
import com.telefonica.ssnn.qg.administracion.medios.dao.interfaz.QGMediosDao;
import com.telefonica.ssnn.qg.administracion.medios.dto.QGMediosComunicacionDto;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;
import com.telefonica.ssnn.qg.util.QGUtilidadesNegocioUtils;

import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGDetalleCDDto;
import com.telefonica.ssnn.qg.exceptions.QGApplicationException;

/**
 * @author vsierra
 *
 */
public class QGMediosDaoNA extends QGBaseDao implements QGMediosDao {

	public QGCGlobalDto modificarMediosComunicacion(
			QGMediosComunicacionDto mediosComunicacionDto) {
		
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		try {
			servicio = new QGNpaNA ("QGF0071");
			
			//Seteamos la copy de entrada
			setearEntradaServicioQGF0071 (servicio, mediosComunicacionDto);
			
			//Ejecutamos la transaccion del servicio.
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

	public QGCGlobalDto obtenerListaMediosComunicacion() {
		
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		ArrayList listaMensajes = new ArrayList ();
		ArrayList listaDatos = new ArrayList ();
		
		try {
			servicio = new QGNpaNA ("QGF0070");
			
			//Seteamos la copy de entrada
			setearEntradaServicioQGF0070 (servicio);
			
			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();
			
			QGMediosComunicacionDto mediosComunicacionDto = null;
			
			for (int i=1; i<= servicio.getValorAsInt("OCU-NU").intValue(); i++){
				mediosComunicacionDto = new QGMediosComunicacionDto();
				
				mediosComunicacionDto.setCodigo(servicio.getValorAsString("CO-MEDIO-COMUNICA",i));
				mediosComunicacionDto.setDescripcion(servicio.getValorAsString("DS-MEDIO-COMUNICA",i));				
				mediosComunicacionDto.setFechaIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-INI-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				mediosComunicacionDto.setFechaFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-FIN-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				mediosComunicacionDto.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT",i));
				mediosComunicacionDto.setUsuarioMod(servicio.getValorAsString("CO-USUARIO-MOD",i));
				mediosComunicacionDto.setNombrePrograma(servicio.getValorAsString("NO-PROG-MOD",i));
				
				listaDatos.add(mediosComunicacionDto); 
            }   
			
			QGCGlobalDto.setlistaDatos(listaDatos);
    		QGCGlobalDto.setlistaMensajes(listaMensajes);
    		
		} catch (NAWRException e) {
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}finally {
            if (servicio != null) {
                servicio.unload();
            }
            
        }
		
		return QGCGlobalDto;		
	}
	
	
	public QGCGlobalDto buscadorMediosCosenDer(QGDetalleCDDto busqueda) {
		//Implementación servicio QGF0008
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		ArrayList listaDatos = new ArrayList ();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_MEDIOS_CONSEN_DER, obtenerUsuarioLogado());

			servicio.setValor("IN-ACS-INF-COX", busqueda.getInAscInfCox());
			servicio.setValor("CO-DRH-UNF-LOPD", busqueda.getCodDerechoLOPD());

			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();
			
			QGMediosComunicacionDto mediosComunicacionDto = null;
			
			for (int i=1; i<= servicio.getValorAsInt("OCU-NU").intValue(); i++){
				mediosComunicacionDto = new QGMediosComunicacionDto();
				
				mediosComunicacionDto.setCodigo(servicio.getValorAsString("CO-MEDIO-COMUNICA",i));
				mediosComunicacionDto.setDescripcion(servicio.getValorAsString("DS-MEDIO-COMUNICA",i));
				
				listaDatos.add(mediosComunicacionDto); 
            }   
			
			QGCGlobalDto.setlistaDatos(listaDatos);
    		
			
		} catch (NAWRException e) {
			String mensajeErrorServicio = e.getCampo("CACV_VARMSG");
			if(QGUtilidadesNegocioUtils.getCodigoError(e.getCampo("CACV_VARMSG")).equals(QGUtilidadesNegocioUtils.CODIGO_ERROR_NO_EXISTEN_REGISTROS)){					
				mensajeErrorServicio = "La búsqueda de medios de comunicación ha fallado";
			}
			throw new QGApplicationException(mensajeErrorServicio);
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		return QGCGlobalDto;
	}

	public QGCGlobalDto obtenerMedioComunicacion(String codigo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*Seteo de la entrada al servicio de medios de comunicacion (alta/baja/mod)*/
	private void setearEntradaServicioQGF0071(QGNpaNA servicio, QGMediosComunicacionDto filtro) throws NAWRException {
		//Cabecera convergente
		// TODO: a la espera de estos datos.
		
		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		
		servicio.setValor("NACabAnagramaLlamante", "QG");
		servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());
		
		//Campos de entrada
		servicio.setValor("IN-ACTUACION", filtro.getCodActuacion());	//M: Modificacion, A: Alta, B:Baja
		servicio.setValor("CO-MEDIO-COMUNICA", filtro.getCodigo(),true);
		servicio.setValor("DS-MEDIO-COMUNICA", filtro.getDescripcion(),true);
		
		servicio.setValor("FX_INI_VIGENCIA",
				QGUtilidadesFechasUtils.formatearFechaParaCopy(filtro.getFechaIniVigencia(),
				QGUtilidadesFechasUtils.FORMATO_FECHA_1));
		servicio.setValor("FX_FIN_VIGENCIA",
				QGUtilidadesFechasUtils.formatearFechaParaCopy(filtro.getFechaFinVigencia(),
				QGUtilidadesFechasUtils.FORMATO_FECHA_1));
		
		if(filtro.getCodActuacion().equals(QGConstantes.CODIGO_ALTA)){
			servicio.setValor("CO-USUARIO-ALT", usuarioLogado.getUsername());
		}else{
			servicio.setValor("CO-USUARIO-MOD", usuarioLogado.getUsername());
		}
	}
	
	/*Seteo de la entrada al servicio de medios de comunicacion (consulta)*/
	private void setearEntradaServicioQGF0070(QGNpaNA servicio) throws NAWRException {
		
		//Cabecera convergente

		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		
		servicio.setValor("NACabAnagramaLlamante", "QG");
		servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());		
		
		//Campos de entrada
		servicio.setValor("IN-ACTUACION", QGConstantes.CODIGO_TODOS);	//T: Todos los medios de comunicacion.
	}

}
