/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.campanias.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.administracion.campanias.dao.interfaz.QGCampaniasDao;
import com.telefonica.ssnn.qg.administracion.campanias.dto.QGCampaniasDto;
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
public class QGCampaniasDaoNA extends QGBaseDao implements QGCampaniasDao {

	public QGCGlobalDto modificarListaCampanias(QGCampaniasDto campaniasDto) {
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		
		try {
			servicio = new QGNpaNA ("QGF0073");
			
			//Seteamos la copy de entrada
			setearEntradaServicioQGF0073 (servicio, campaniasDto);
			
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

	public QGCGlobalDto obtenerCampania(String codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	public QGCGlobalDto obtenerListaCampanias() {
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		ArrayList listaDatos = new ArrayList ();
		
		try {
			servicio = new QGNpaNA ("QGF0072");
			
			//Seteamos la copy de entrada
			setearEntradaServicioQGF0072 (servicio);
			
			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();
			
			QGCampaniasDto campaniasDto = null;
			
			for (int i=1; i<= servicio.getValorAsInt("OCU-NU").intValue(); i++){
				campaniasDto = new QGCampaniasDto();
				
				campaniasDto.setCodigo(servicio.getValorAsString("CO-CPN-CMC-CSM-DRH", i));
				campaniasDto.setDescripcion(servicio.getValorAsString("DS-CPN-CMC-CSM-DRH", i));
				campaniasDto.setObservaciones(servicio.getValorAsString("DS-OBS-CPN-CSM-DRH", i));

				campaniasDto.setFechaIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-INI-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				campaniasDto.setFechaFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-FIN-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				
				campaniasDto.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT", i));
				campaniasDto.setUsuarioMod(servicio.getValorAsString("CO-USUARIO-MOD", i));
				
				listaDatos.add(campaniasDto); 
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
	
	/*Seteo de la entrada al servicio de campanias (alta/baja/mod)*/
	private void setearEntradaServicioQGF0073(QGNpaNA servicio,
			QGCampaniasDto filtro) throws NAWRException {
		//Cabecera convergente
		QGUsuario usuarioLogado = obtenerUsuarioLogado();

		servicio.setValor("NACabAnagramaLlamante", "QG");
		servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());
		
		//Campos de entrada
		servicio.setValor("IN-ACTUACION", filtro.getCodActuacion());	//M: Modificacion, A: Alta, B:Baja
		servicio.setValor("CO-CPN-CMC-CSM-DRH", filtro.getCodigo(),true);
		servicio.setValor("DS-CPN-CMC-CSM-DRH", filtro.getDescripcion(),true);
		servicio.setValor("DS-OBS-CPN-CSM-DRH", filtro.getObservaciones(),true);
		
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
	
	/*Seteo de la entrada al servicio de campanias (consulta)*/
	private void setearEntradaServicioQGF0072(QGNpaNA servicio) throws NAWRException {
		//Cabecera convergente
		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		servicio.setValor("NACabAnagramaLlamante", "QG");
		servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());
		
		
		//Campos de entrada
		servicio.setValor("IN-ACTUACION", QGConstantes.CODIGO_TODOS);	//T: Todas las campanias.
	}
	
}
