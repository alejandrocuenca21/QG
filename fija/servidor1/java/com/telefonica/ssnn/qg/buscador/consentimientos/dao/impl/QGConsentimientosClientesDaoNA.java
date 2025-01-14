/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.consentimientos.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.buscador.consentimientos.dao.interfaz.QGConsentimientosClientesDao;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGActualizacionCDDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGCombosDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGDetalleCDDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGEntradaHistorialDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGEntradaListaCDDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGSalidaHistorialDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGSalidaListaCDDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGSituacionesDto;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;

import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCombosComunesDto;

import com.telefonica.ssnn.qg.exceptions.QGApplicationException;

/**
 * @author vsierra
 *
 */
public class QGConsentimientosClientesDaoNA extends QGBaseDao implements QGConsentimientosClientesDao {

	/**
	 * Llamará al servicio QGF0079
	 */
	public QGCGlobalDto actualizacionCliente(QGActualizacionCDDto actualizacionCDDto) {
		
		QGNpaNA servicio = null;
		QGCGlobalDto globalDto = new QGCGlobalDto();
		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		ArrayList listaDatos = new ArrayList();
		ArrayList listaMensajes = new ArrayList();
		
		
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.S");
		
		try {
			servicio = new QGNpaNA("QGF0079");
			
			servicio.setValor("NACabAnagramaLlamante", "QG");
			servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());
			
			servicio.setValor("CO-DRH-UNF-LOPD", actualizacionCDDto.getCodigoLOPD());
			
			for(int i=0; i < actualizacionCDDto.getSituacionActual().size(); i++){
				if(actualizacionCDDto.getSituacionActual().get(i) != null){
					QGSituacionesDto situacion = (QGSituacionesDto)actualizacionCDDto.getSituacionActual().get(i);
					
					servicio.setValor("NU-SEC-TIPO-OBJETO", situacion.getSecuencial());
					servicio.setValor("ATL-CMC-DAT-GR.CO-TIPO-OBJETO", situacion.getCodigo());
					servicio.setValor("ATL-CMC-DAT-GR.DS-DAT-OJT-APL-PFE", situacion.getDescripcion());
				}
			}			
			servicio.setValor("CO-CLIENTE-GBL", actualizacionCDDto.getCodigoCliente());
			servicio.setValor("CO-OPR-TP-COMUNICA", actualizacionCDDto.getCodOperacion());
			servicio.setValor("CO-EST-CSM-DRH", actualizacionCDDto.getCodGestion());
			if(QGConstantes.SI_CONSIENTE.equals(actualizacionCDDto.getCodGestion()))
				servicio.setValor("IN-VAL-EPL-DRH", QGConstantes.CODIGO_S);
			else
				servicio.setValor("IN-VAL-EPL-DRH", QGConstantes.CODIGO_N);
			
			servicio.setValor("IT-CAMBIO-ESTADO", timeStamp.format(actualizacionCDDto.getFxCambioEstado()) + "000");
			
			for(int i=0; i < actualizacionCDDto.getSituacionNueva().size(); i++){
				if(actualizacionCDDto.getSituacionNueva().get(i) != null){
					QGCombosComunesDto situacion = (QGCombosComunesDto)actualizacionCDDto.getSituacionNueva().get(i);
					
					servicio.setValor("NVO-CMC-DAT-GR.CO-TIPO-OBJETO", situacion.getCodigoNivel());
					servicio.setValor("NVO-CMC-DAT-GR.DS-DAT-OJT-APL-PFE", situacion.getDescripcionNivel());
				}
			}	
			servicio.setValor("DS-EMAIL-PREFERENT", actualizacionCDDto.getDescEmail());
			servicio.setValor("IN-INCIDENCIA", actualizacionCDDto.getIndIncidencia());
			servicio.setValor("DS-OBS-CSM-DRH", actualizacionCDDto.getDescObservacion());
			servicio.setValor("CO-CPN-CMC-CSM-DRH", actualizacionCDDto.getCodCampaniaCD());
			servicio.setValor("CO-MEDIO-COMUNICA", actualizacionCDDto.getCodMedioComunicacion());
			servicio.setValor("CO-TP-UBICACION", actualizacionCDDto.getCodTipoUbicacion());
			servicio.setValor("DS-UBICACION", actualizacionCDDto.getDescUbicacion());
			if(actualizacionCDDto.getFxIniVigencia() != null)
				servicio.setValor("FX-INI-VIGENCIA", formato.format(actualizacionCDDto.getFxIniVigencia()));
			if(actualizacionCDDto.getFxFinVigencia() != null)
				servicio.setValor("FX-FIN-VIGENCIA", formato.format(actualizacionCDDto.getFxFinVigencia()));
			servicio.setValor("CO-USUARIO", usuarioLogado.getUsername());			
			
			servicio.ejecutarTransaccion();
			
			
		} catch (NAWRException e) {
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}finally{
			if(servicio != null)
				servicio.unload();
		}
		
		globalDto.setlistaDatos(listaDatos);
		globalDto.setlistaMensajes(listaMensajes);
		return globalDto;
	}

	/**
	 * Llamará al servicio QGF0077
	 */
	public QGCGlobalDto obtenerDetalleCliente(QGDetalleCDDto detalleCDDto) {
		
		QGNpaNA servicio = null;
		QGCGlobalDto globalDto = new QGCGlobalDto();
		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		ArrayList listaDatos = new ArrayList();
		ArrayList listaMensajes = new ArrayList();
		
		try {
			//Creamos una instancia con el servicio a utilizar
			servicio = new QGNpaNA("QGF0077");
			
			servicio.setValor("NACabAnagramaLlamante", "QG");
			servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());
			//Seteamos la copy de entrada
			servicio.setValor("CO-DRH-UNF-LOPD", detalleCDDto.getCodDerechoLOPD());
			servicio.setValor("NU-SEC-TIPO-OBJETO", detalleCDDto.getSecObjeto());
			servicio.setValor("NU-SEC-EVE-CSM-DRH", detalleCDDto.getSecEvento());
			servicio.setValor("CO-TIPO-OBJETO", detalleCDDto.getCodTipoObjeto());
			
			//Ejecutamos el servicio
			servicio.ejecutarTransaccion();
			
			//Recuperamos los datos
			detalleCDDto.setDescTipoObjeto(servicio.getValorAsString("DS-TIPO-OBJETO"));
			detalleCDDto.setDescEmail(servicio.getValorAsString("DS-EMAIL-PREFERENT"));
			detalleCDDto.setCodTipoComunicacion(servicio.getValorAsString("CO-OPR-TP-COMUNICA"));
			detalleCDDto.setDescTipoComunicacion(servicio.getValorAsString("DS-OPR-TP-COMUNICA"));
			detalleCDDto.setIndIncidencia(servicio.getValorAsString("IN-INCIDENCIA"));
			detalleCDDto.setSecObservaciones(servicio.getValorAsLong("NU-SEC-OBS-CSM-DRH"));
			detalleCDDto.setObservacionCD(servicio.getValorAsString("DS-OBS-CSM-DRH"));
			//detalleCDDto.setSecTelefonicaCD(servicio.getValorAsLong("NU-SEC-CMC-DRH"));
			detalleCDDto.setCodCampaniaCD(servicio.getValorAsString("CO-CPN-CMC-CSM-DRH"));
			detalleCDDto.setDescCampaniaCD(servicio.getValorAsString("DS-CPN-CMC-CSM-DRH"));
			detalleCDDto.setCodMedioComunicacion(servicio.getValorAsString("CO-MEDIO-COMUNICA"));
			detalleCDDto.setDescMedioComunicacion(servicio.getValorAsString("DS-MEDIO-COMUNICA"));
			detalleCDDto.setCodTipoUbicacion(servicio.getValorAsString("CO-TP-UBICACION"));
			detalleCDDto.setDescTipoUbicacion(servicio.getValorAsString("DS-TP-UBICACION"));
			detalleCDDto.setDescUbicacion(servicio.getValorAsString("DS-UBICACION"));
			detalleCDDto.setFxIniVigencia(servicio.getValorAsDate("FX-INI-VIGENCIA"));
			detalleCDDto.setFxFinVigencia(servicio.getValorAsDate("FX-FIN-VIGENCIA"));
			detalleCDDto.setCodUsuario(servicio.getValorAsString("CO-USUARIO"));	
			
			listaDatos.add(detalleCDDto);
			
		} catch (NAWRException e) {
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}finally{
			if(servicio != null)
				servicio.unload();
		}
		
		globalDto.setlistaDatos(listaDatos);
		globalDto.setlistaMensajes(listaMensajes);
		
		return globalDto;
	}

	/**
	 * Llamará al servicio QGF0081
	 */
	public QGCGlobalDto obtenerEstadoGestion(String codigo) {
		
		QGNpaNA servicio = null;
		QGCGlobalDto globalDto = new QGCGlobalDto();
		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		ArrayList listaDatos = new ArrayList();
		ArrayList listaMensajes = new ArrayList();
		
		try {
			servicio = new QGNpaNA("QGF0081");
			
			servicio.setValor("NACabAnagramaLlamante", "QG");
			servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());
			
			servicio.setValor("IN-ACTUACION", "U");
			servicio.setValor("CO-EST-CSM-DRH", codigo);	
			
			servicio.ejecutarTransaccion();
			
			listaDatos = tratamientoDatosEstadoGestion(servicio);
			
		} catch (NAWRException e) {
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}finally{
			if(servicio != null)
				servicio.unload();
		}
		
		globalDto.setlistaDatos(listaDatos);
		globalDto.setlistaMensajes(listaMensajes);
		return globalDto;
	}

	/**
	 * Llamará al servicio QGF0078
	 */
	public QGCGlobalDto obtenerHistorialCliente(
			QGEntradaHistorialDto entradaHistorialDto) {
		
		QGNpaNA servicio = null;    
		QGCGlobalDto globalDto = new QGCGlobalDto();
		QGSalidaHistorialDto salidaHistorialDto = null;
		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		ArrayList listaDatos = new ArrayList();
		ArrayList listaMensajes = new ArrayList();
		
		try {
			//Creamos una instancia con el servicio a utilizar
			servicio = new QGNpaNA("QGF0078");
			
			servicio.setValor("NACabAnagramaLlamante", "QG");
			servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());
			
			//Seteamos la copy de entrada
			servicio.setValor("CO-DRH-UNF-LOPD", entradaHistorialDto.getCodDerechoLOPD());
			servicio.setValor("CO-CLIENTE-GBL", entradaHistorialDto.getCodCliente());
			servicio.setValor("CO-TIPO-OBJETO", entradaHistorialDto.getCodTipoObjeto());
			servicio.setValor("DS-DAT-OJT-APL-PFE", entradaHistorialDto.getDescObjetoCD());
			servicio.setValor("NU-SEC-TIPO-OBJETO", entradaHistorialDto.getSecTipoObjeto());
						
			//Ejecutamos el servicio
			servicio.ejecutarTransaccion();
			
			//Recuperamos los datos
			for(int i = 1; i <= servicio.getValorAsInt("OCU-NU").intValue(); i++){
				salidaHistorialDto = new QGSalidaHistorialDto();
			
				salidaHistorialDto.setCodigoOperacion(servicio.getValorAsString("CO_OPR_TP_COMUNICA", i));
				salidaHistorialDto.setDescObjetoCD(servicio.getValorAsString("DS_OPR_TP_COMUNICA", i));
				salidaHistorialDto.setDescOperacion(servicio.getValorAsString("IT_OPERACION", i));
				salidaHistorialDto.setFxOperacion(servicio.getValorAsDate("DS-DAT-OJT-APL-PFE", i));
				
				listaDatos.add(salidaHistorialDto);
			}
			
		} catch (NAWRException e) {
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}finally{
			if(servicio != null)
				servicio.unload();
		}
		
		globalDto.setlistaDatos(listaDatos);
		globalDto.setlistaMensajes(listaMensajes);
		
		return globalDto;
	}

	/**
	 * Llamará al servicio QGF0076
	 */
	public QGCGlobalDto obtenerListaConsentimientos(
			QGEntradaListaCDDto entradaListaCDDto) {
		QGNpaNA servicio = null;
		QGCGlobalDto globalDto = new QGCGlobalDto();
		QGSalidaListaCDDto salidaListaCDDto = null;
		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		ArrayList listaDatos = new ArrayList();
		ArrayList listaMensajes = new ArrayList();
		
		try {
			servicio = new QGNpaNA("QGF0076");
			
			servicio.setValor("NACabAnagramaLlamante", "QG");
			servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());
			
			servicio.setValor("IN-ACTUACION", entradaListaCDDto.getCodActuacion());
			servicio.setValor("CO-CLIENTE-GBL",entradaListaCDDto.getCodCliente());
			servicio.setValor("CO-TIPO-OBJETO",entradaListaCDDto.getCodTipoObjeto());
			servicio.setValor("IN-VAL-EPL-DRH",entradaListaCDDto.getEstadoCD());
			servicio.setValor("CO-EST-CSM-DRH",entradaListaCDDto.getEstadoGestion());
			servicio.setValor("FX-FIN-CMB-EST",entradaListaCDDto.getFxFinCambio());
			servicio.setValor("FX-INI-CMB-EST",entradaListaCDDto.getFxIniCambio());
			
			servicio.ejecutarTransaccion();
			
			for(int i = 1; i <= servicio.getValorAsInt("OCU-NU").intValue(); i++){
				salidaListaCDDto = new QGSalidaListaCDDto();
				
				salidaListaCDDto.setCodDerecho(servicio.getValorAsString("CO-DRH-UNF-LOPD", i));
				salidaListaCDDto.setCodTipoObjeto(servicio.getValorAsString("CO-TIPO-OBJETO", i));
				salidaListaCDDto.setCodUsuario(servicio.getValorAsString("CO-USUARIO", i));
				salidaListaCDDto.setDescDerecho(servicio.getValorAsString("DS-CSM-DRH-INTERNO", i));
				salidaListaCDDto.setDescEstadoGestion(servicio.getValorAsString("DS-EST-CSM-DRH", i));
				salidaListaCDDto.setEstadoGestionCD(servicio.getValorAsString("CO-EST-CSM-DRH", i));
				salidaListaCDDto.setFxCambioEstado(servicio.getValorAsDate("IT-CAMBIO-ESTADO", i));
				salidaListaCDDto.setFxFinVigencia(servicio.getValorAsDate("FX-FIN-VIGENCIA", i));
				salidaListaCDDto.setFxIniVigencia(servicio.getValorAsDate("FX-INI-VIGENCIA", i));
				salidaListaCDDto.setObjetoCD(servicio.getValorAsString("DS-DAT-OJT-APL-PFE", i));
				salidaListaCDDto.setSecEvento(servicio.getValorAsLong("NU-SEC-EVE-CSM-DRH", i));
				salidaListaCDDto.setSecTipoObjeto(servicio.getValorAsLong("NU-SEC-TIPO-OBJETO", i));
				salidaListaCDDto.setTipoLogica(servicio.getValorAsString("CO-TIP-LGC-CSM-DRH", i));
				salidaListaCDDto.setValorExplotacion(servicio.getValorAsString("IN-VAL-EPL-DRH", i));
				
				listaDatos.add(salidaListaCDDto);
			}
			
			
		} catch (NAWRException e) {
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}finally{
			if(servicio != null)
				servicio.unload();
		}
		
		globalDto.setlistaDatos(listaDatos);
		globalDto.setlistaMensajes(listaMensajes);
		
		return globalDto;
	}

	/**
	 * Llamará al servicio QGF0080
	 */
	public QGCGlobalDto obtenerTipoOperacion(String codigo) {
		
		QGNpaNA servicio = null;
		QGCGlobalDto globalDto = new QGCGlobalDto();
		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		ArrayList listaDatos = new ArrayList();
		ArrayList listaMensajes = new ArrayList();
		
		try {
			servicio = new QGNpaNA("QGF0080");
			
			servicio.setValor("NACabAnagramaLlamante", "QG");
			servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());
			
			servicio.setValor("IN-ACTUACION", "U");
			servicio.setValor("CO-OPR-TP-COMUNICA", codigo);	
			
			servicio.ejecutarTransaccion();
			
			listaDatos = tratamientoDatosTipoOperacion(servicio);
			
		} catch (NAWRException e) {
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}finally{
			if(servicio != null)
				servicio.unload();
		}
		
		globalDto.setlistaDatos(listaDatos);
		globalDto.setlistaMensajes(listaMensajes);
		return globalDto;
	}
	
	public QGCGlobalDto obtenerListaEstadoGestion() {
		QGNpaNA servicio = null;
		QGCGlobalDto globalDto = new QGCGlobalDto();
		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		ArrayList listaDatos = new ArrayList();
		ArrayList listaMensajes = new ArrayList();
		
		try {
			servicio = new QGNpaNA("QGF0081");
			
			servicio.setValor("NACabAnagramaLlamante", "QG");
			servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());
			
			servicio.setValor("IN-ACTUACION", "T");
						
			servicio.ejecutarTransaccion();
			
			listaDatos = tratamientoDatosEstadoGestion(servicio);
			
		} catch (NAWRException e) {
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}finally{
			if(servicio != null)
				servicio.unload();
		}
		
		globalDto.setlistaDatos(listaDatos);
		globalDto.setlistaMensajes(listaMensajes);
		
		return globalDto;
	}

	public QGCGlobalDto obtenerListaTipoOperacion() {
		QGNpaNA servicio = null;
		QGCGlobalDto globalDto = new QGCGlobalDto();
		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		ArrayList listaDatos = new ArrayList();
		ArrayList listaMensajes = new ArrayList();
		
		try {
			servicio = new QGNpaNA("QGF0080");
			
			servicio.setValor("NACabAnagramaLlamante", "QG");
			servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());
			
			servicio.setValor("IN-ACTUACION", "T");
						
			servicio.ejecutarTransaccion();
			
			listaDatos = tratamientoDatosTipoOperacion(servicio);
			
		} catch (NAWRException e) {
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}finally{
			if(servicio != null)
				servicio.unload();
		}
		
		globalDto.setlistaDatos(listaDatos);
		globalDto.setlistaMensajes(listaMensajes);
		
		return globalDto;
	}
	
	public QGCGlobalDto obtenerListaTiposComunicacion() {
		// TODO Auto-generated method stub
		return null;
	}

	public QGCGlobalDto obtenerTipoComunicacion(String codigo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList tratamientoDatosEstadoGestion(QGNpaNA servicio){
		
		ArrayList lista = new ArrayList();

		QGCombosDto combo = null;
		try{
			for(int i=1; i <= servicio.getValorAsInt("OCU-NU").intValue(); i++){
				combo = new QGCombosDto();
				
				combo.setCodigo(servicio.getValorAsString("CO-EST-CSM-DRH", i));
				combo.setCodUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT", i));
				combo.setCodUsuarioMod(servicio.getValorAsString("CO-USUARIO-MOD", i));
				combo.setDescripcion(servicio.getValorAsString("DS-EST-CSM-DRH", i));
				combo.setFxFinVigencia(servicio.getValorAsDate("FX-FIN-VIGENCIA", i));
				combo.setFxIniVigencia(servicio.getValorAsDate("FX-INI-VIGENCIA", i));
				combo.setNombrePrograma(servicio.getValorAsString("NO-PROG-MOD", i));
				
				lista.add(combo);
			}
		}catch (NAWRException e) {
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}		
			
		return lista;
	}
	
	public ArrayList tratamientoDatosTipoOperacion(QGNpaNA servicio){
		
		ArrayList lista = new ArrayList();
		
		QGCombosDto combo = null;
		try{
			for(int i=1; i <= servicio.getValorAsInt("OCU-NU").intValue(); i++){
				combo = new QGCombosDto();
				
				combo.setCodigo(servicio.getValorAsString("CO-OPR-TP-COMUNICA", i));
				combo.setCodUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT", i));
				combo.setCodUsuarioMod(servicio.getValorAsString("CO-USUARIO-MOD", i));
				combo.setDescripcion(servicio.getValorAsString("DS-OPR-TP-COMUNICA", i));
				combo.setFxFinVigencia(servicio.getValorAsDate("FX-FIN-VIGENCIA", i));
				combo.setFxIniVigencia(servicio.getValorAsDate("FX-INI-VIGENCIA", i));
				combo.setNombrePrograma(servicio.getValorAsString("NO-PROG-MOD", i));
				
				lista.add(combo);
			}
		}catch (NAWRException e) {
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}		
			
		return lista;
	}
	
	/*public void tratarDatosSituaciones(NpaNA servicio, ArrayList listaSituacionesDto){
		try{	
			for(int i=0; i < listaSituacionesDto.size(); i++){
				if(listaSituacionesDto.get(i) != null){
					SituacionesDto situacion = (SituacionesDto)listaSituacionesDto.get(i);
					
					servicio.setValor("NU-SEC-TIPO-OBJETO", situacion.getSecuencial());
					servicio.setValor("ENTRADA.CO-TIPO-OBJETO", situacion.getCodigo());
					servicio.setValor("DS-DAT-OJT-APL-PFE", situacion.getDescripcion());
				}
			}
		}catch (NAWRException e) {
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}
	}*/

	
	
}
