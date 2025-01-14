/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.ubicacion.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.administracion.ubicacion.dao.interfaz.QGUbicacionDao;
import com.telefonica.ssnn.qg.administracion.ubicacion.dto.QGTiposUbicacionDto;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;

import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.exceptions.QGApplicationException;

/**
 * @author vsierra
 *
 */
public class QGUbicacionDaoNA extends QGBaseDao implements QGUbicacionDao {

	private static final Logger logger = Logger.getLogger(QGUbicacionDaoNA.class);
	
	public QGCGlobalDto modificarTiposUbicacion(
			QGTiposUbicacionDto tiposUbicacionDto) {

		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		try {
			servicio = new QGNpaNA ("QGF0075");
			
			//Seteamos la copy de entrada
			setearEntradaServicioQGF0075 (servicio, tiposUbicacionDto);
			
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

	public QGCGlobalDto obtenerListaTiposUbicacion() {
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		ArrayList listaDatos = new ArrayList ();
		
		try {
			servicio = new QGNpaNA ("QGF0074");
			
			//Seteamos la copy de entrada
			setearEntradaServicioQGF0074 (servicio);

			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();

			QGTiposUbicacionDto tiposUbicacionDto = null;
			for (int i=1; i <= servicio.getValorAsInt("OCU-NU").intValue(); i++){
				tiposUbicacionDto = new QGTiposUbicacionDto();
				
				tiposUbicacionDto.setCodigo(servicio.getValorAsString("CO-TP-UBICACION", i));
				tiposUbicacionDto.setDescripcion(servicio.getValorAsString("DS-TP-UBICACION", i));
				tiposUbicacionDto.setFechaIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-INI-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				tiposUbicacionDto.setFechaFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-FIN-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				tiposUbicacionDto.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT", i));
				tiposUbicacionDto.setUsuarioMod(servicio.getValorAsString("CO-USUARIO-MOD", i));
				tiposUbicacionDto.setNombrePrograma(servicio.getValorAsString("NO-PROG-MOD", i));
				
				listaDatos.add(tiposUbicacionDto); 
            }   
			QGCGlobalDto.setlistaDatos(listaDatos);
		} catch (NAWRException e) {
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}finally {
            if (servicio != null) {
                servicio.unload();
            }
        }
		
		return QGCGlobalDto;
	}

	public QGCGlobalDto obtenerTiposUbicacion(String codigo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*Seteo de la entrada al servicio de tipos de ubicacion (alta/baja/mod)*/
	private void setearEntradaServicioQGF0075(QGNpaNA servicio, QGTiposUbicacionDto filtro) throws NAWRException {
		//Cabecera convergente
		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		servicio.setValor("NACabAnagramaLlamante", "QG");
		servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());
		
		//Campos de entrada
		servicio.setValor("IN-ACTUACION", filtro.getCodActuacion());	//M: Modificacion, A: Alta, B:Baja
		servicio.setValor("CO-TP-UBICACION", filtro.getCodigo(),true);
		servicio.setValor("DS-TP-UBICACION", filtro.getDescripcion(),true);
		
		servicio.setValor("ENTRADA.FX_INI_VIGENCIA",
				QGUtilidadesFechasUtils.formatearFechaParaCopy(filtro.getFechaIniVigencia(),
				QGUtilidadesFechasUtils.FORMATO_FECHA_1));
		servicio.setValor("ENTRADA.FX_FIN_VIGENCIA",
				QGUtilidadesFechasUtils.formatearFechaParaCopy(filtro.getFechaFinVigencia(),
				QGUtilidadesFechasUtils.FORMATO_FECHA_1));	
		
		if(filtro.getCodActuacion().equals(QGConstantes.CODIGO_ALTA)){
			servicio.setValor("CO-USUARIO-ALT", usuarioLogado.getUsername());
		}else{
			servicio.setValor("CO-USUARIO-MOD", usuarioLogado.getUsername());
		}
	}
	
	/*Seteo de la entrada al servicio de tipos de ubicacion (consulta)*/
	private void setearEntradaServicioQGF0074(QGNpaNA servicio) throws NAWRException {
		//Cabecera convergente
		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		
		logger.info("seteamos valores en el servicio");
		servicio.setValor("NACabAnagramaLlamante", "QG");
		servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());		
		
		//Campos de entrada
		servicio.setValor("IN-ACTUACION", QGConstantes.CODIGO_TODOS);	//T: Todos los tipos de ubicacion.
		
	}

}
