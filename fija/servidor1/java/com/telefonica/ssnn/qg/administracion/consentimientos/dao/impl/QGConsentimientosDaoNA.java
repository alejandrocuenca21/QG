/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.consentimientos.dao.impl;

import java.util.ArrayList;
import java.util.Date;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.administracion.consentimientos.dao.interfaz.QGConsentimientosDao;
import com.telefonica.ssnn.qg.administracion.consentimientos.dto.QGConsentimientosDto;
import com.telefonica.ssnn.qg.administracion.consentimientos.dto.QGDetalleConsentimientosDto;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCombosComunesDto;
import com.telefonica.ssnn.qg.base.dto.QGGruposComunesDto;
import com.telefonica.ssnn.qg.base.dto.QGTextoLegalDto;
import com.telefonica.ssnn.qg.exceptions.QGApplicationException;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;
import com.telefonica.ssnn.qg.util.QGUtilidadesNegocioUtils;

/**
 * @author vsierra 
 *
 */
public class QGConsentimientosDaoNA extends QGBaseDao implements QGConsentimientosDao {

	public QGCGlobalDto altaTextoLegal(QGTextoLegalDto textoLegalDto) {
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		QGUsuario usuarioLogado = obtenerUsuarioLogado();

		QGTextoLegalDto textoAntiguo = null;

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_ALTA_TEXTO_LEGAL, usuarioLogado);			

			servicio.setValor("IN-ACTUACION", textoLegalDto.getCodActuacion());
			servicio.setValor("CO-DRH-UNF-LOPD", textoLegalDto.getCodigoDerecho());
			servicio.setValor("FX-INI-VIGENCIA", QGUtilidadesFechasUtils.fromDateToString(textoLegalDto.getFecInicioVigencia(), QGUtilidadesFechasUtils.FORMATO_FECHA_3));

			if(textoLegalDto.getFecFinVigencia() != null)
				servicio.setValor("FX-FIN-VIGENCIA", QGUtilidadesFechasUtils.fromDateToString(textoLegalDto.getFecFinVigencia(), QGUtilidadesFechasUtils.FORMATO_FECHA_3));

			if(textoLegalDto.getCodActuacion().equals(QGConstantes.CODIGO_ALTA)){
				servicio.setValor("IT-ALTA", QGUtilidadesFechasUtils.fromDateToString(new Date(), QGUtilidadesFechasUtils.FORMATO_FECHA_3));
				servicio.setValor("CO-USUARIO-ALT", usuarioLogado.getUsername());
			}else{
				servicio.setValor("IT-ULT-MOD", QGUtilidadesFechasUtils.fromDateToString(new Date(), QGUtilidadesFechasUtils.FORMATO_FECHA_3));
				servicio.setValor("CO-USUARIO-MOD", usuarioLogado.getUsername());
			}

			int longitudAntiguo = 1;

			//VEMOS SI QUEREMOS UNA MODIFICACIÓN
			if(textoLegalDto.getCodActuacion().equals(QGConstantes.CODIGO_MODIFICAR)){
				QGCGlobalDto = obtenerTextoLegal(textoLegalDto.getCodigoDerecho(), new Integer(1));

				textoAntiguo = (QGTextoLegalDto)QGCGlobalDto.getlistaDatos().get(0);
				//AVERIGUAMOS LA LONGITUD DEL TEXTO ANTIGUO
				if(textoAntiguo.getTextoLegal().length() > 3000){
					longitudAntiguo = textoAntiguo.getTextoLegal().length()/3000;
				}
			}
			int longitudTexto = 0;

			if(textoLegalDto.getTextoLegal().length() > 3000){
				longitudTexto = textoLegalDto.getTextoLegal().length()/3000;
				if(textoLegalDto.getTextoLegal().length()%3000 > 0)
					longitudTexto++;
				int beginIndex = 0;
				int endIndex = 2999;


				//INSERTAMOS LOS DATOS NUEVOS EN BASE DE DATOS
				for(int i=1; i <= longitudTexto; i++){					
					if(i == longitudTexto && longitudAntiguo < longitudTexto){
						servicio.setValor("NU-SEC-TXT-LEG-CSM", "");
						servicio.setValor("DS-TXT-LEG-CSM-DRH", textoLegalDto.getTextoLegal().substring(beginIndex, textoLegalDto.getTextoLegal().length()-1),true);
					}else{
						servicio.setValor("NU-SEC-TXT-LEG-CSM", new Integer(i));
						servicio.setValor("DS-TXT-LEG-CSM-DRH", textoLegalDto.getTextoLegal().substring(beginIndex, endIndex),true);
					}
					servicio.ejecutarTransaccion();
					beginIndex = endIndex;
					endIndex = endIndex + 3000;
				}
			}else{
				servicio.setValor("NU-SEC-TXT-LEG-CSM", new Integer(1));
				servicio.setValor("DS-TXT-LEG-CSM-DRH", textoLegalDto.getTextoLegal(),true);
				servicio.ejecutarTransaccion();
				longitudTexto = 1;				
			}

			//SI EL TEXTO ANTERIOR ES MAYOR AL NUEVO, LIMPIAMOS EL RESTO DE CAMPOS
			if(textoAntiguo != null && longitudAntiguo > longitudTexto){
				for(int i=longitudTexto; i<= longitudAntiguo; i++){
					servicio.setValor("NU-SEC-TXT-LEG-CSM", "");
					servicio.setValor("DS-TXT-LEG-CSM-DRH", "",true);
					servicio.ejecutarTransaccion();
				}
			}

		} catch (NAWRException e) {
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}

		return QGCGlobalDto;
	}

	public QGCGlobalDto modificarConsentimiento(
			QGDetalleConsentimientosDto detalleConsentimientosDto) {
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		QGUsuario usuarioLogado = obtenerUsuarioLogado();


		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_MODIFICAR_CONSENTIMIENTO, usuarioLogado);

			 
			servicio.setValor("IN-ACTUACION", detalleConsentimientosDto.getActuacion());
			servicio.setValor("CO-DRH-UNF-LOPD", detalleConsentimientosDto.getCodigoDerecho());
			servicio.setValor("DS-CSM-DRH-INTERNO", detalleConsentimientosDto.getDescDerechoInterno(),true);
			servicio.setValor("DS-CSM-DRH-EXTERNO", detalleConsentimientosDto.getDescDerechoExterno(),true);

			servicio.setValor("IN-CSM-DRH-EXL-CLI", QGConstantes.CODIGO_S);
			if(!detalleConsentimientosDto.getExclusividad().booleanValue())
				servicio.setValor("IN-CSM-DRH-EXL-CLI", QGConstantes.CODIGO_N);

			servicio.setValor("IN-EJC-CSM-DRH-ALT", QGConstantes.CODIGO_S);
			if(!detalleConsentimientosDto.getMarcaContratoAlta().booleanValue())
				servicio.setValor("IN-EJC-CSM-DRH-ALT", QGConstantes.CODIGO_N);

			servicio.setValor("CA-DIAS-LIBERACION", detalleConsentimientosDto.getDiasLiberacion());

			servicio.setValor("CA-DIAS-NVA-CMC", detalleConsentimientosDto.getDiasNuevaComunicacion());

			servicio.setValor("CO-TIP-LGC-CSM-DRH", QGConstantes.CODIGO_TODOS);
			if(!detalleConsentimientosDto.getTipoLogica().booleanValue())
				servicio.setValor("CO-TIP-LGC-CSM-DRH", QGConstantes.CODIGO_E);

			servicio.setValor("CO-DRH-LOPD-FIJ", detalleConsentimientosDto.getCodigoTDE());
			servicio.setValor("CO-DRH-LOPD-MVL", detalleConsentimientosDto.getCodigoTME());
			servicio.setValor("CO-LIN-NGC", detalleConsentimientosDto.getCodLineaNegocio());
			
			servicio.setValor("CO-NIV-APL-CSM-DRH", detalleConsentimientosDto.getCodNivelAplicacion());
			servicio.setValor("CO-TIPO-OBJETO", detalleConsentimientosDto.getCodTipoObjeto());
			servicio.setValor("CO-AMB-APL-CSM-DRH", detalleConsentimientosDto.getCodAmbito());
			if(!"".equals(detalleConsentimientosDto.getDescObservacion())){
				servicio.setValor("DS-OBS-CSM-DRH", detalleConsentimientosDto.getDescObservacion(),true);
			}
			servicio.setValor("IN-APLIC-PUB-CONT", QGConstantes.CODIGO_S);
			if(!detalleConsentimientosDto.getContactadoTdE().booleanValue())
				servicio.setValor("IN-APLIC-PUB-CONT", QGConstantes.CODIGO_N);

			servicio.setValor("SEG-ASO-OCU-NU", new Integer(detalleConsentimientosDto.getGrupoSegmentos().size()));

			QGGruposComunesDto combo = null;
			for(int i=1; i <= detalleConsentimientosDto.getGrupoSegmentos().size(); i++){
				combo = new QGGruposComunesDto();
				combo = (QGGruposComunesDto)detalleConsentimientosDto.getGrupoSegmentos().get(i-1);
				servicio.setValor("IN-DRH-ASO-SGM", QGConstantes.CODIGO_S, i);
				if(!combo.getMarcado().booleanValue())
					servicio.setValor("IN-DRH-ASO-SGM", QGConstantes.CODIGO_N, i);

				servicio.setValor("CO-SGM-UNF-UNM", combo.getCodigo(), i);
			}

			servicio.setValor("CMC-MDO-OCU-NU", new Integer(detalleConsentimientosDto.getGrupoMediosComunicacion().size()));

			for(int i=1; i <= detalleConsentimientosDto.getGrupoMediosComunicacion().size(); i++){
				combo = new QGGruposComunesDto();
				combo = (QGGruposComunesDto)detalleConsentimientosDto.getGrupoMediosComunicacion().get(i-1);
				servicio.setValor("IN-DRH-ASO-MDO-CMC", QGConstantes.CODIGO_S, i);
				if(!combo.getMarcado().booleanValue())
					servicio.setValor("IN-DRH-ASO-MDO-CMC", QGConstantes.CODIGO_N, i);

				servicio.setValor("CO-MEDIO-COMUNICA", combo.getCodigo(), i);
			}

			servicio.setValor("FX-INI-VIGENCIA", QGUtilidadesFechasUtils.fromDateToString(detalleConsentimientosDto.getFechaInicioVigencia(), QGUtilidadesFechasUtils.FORMATO_FECHA_3));
			if(detalleConsentimientosDto.getFechaFinVigencia() != null)
				servicio.setValor("FX-FIN-VIGENCIA", QGUtilidadesFechasUtils.fromDateToString(detalleConsentimientosDto.getFechaFinVigencia(), QGUtilidadesFechasUtils.FORMATO_FECHA_3));

			if(detalleConsentimientosDto.getActuacion().equals(QGConstantes.CODIGO_ALTA)){
				servicio.setValor("CO-USUARIO-ALT", usuarioLogado.getUsername());
				servicio.setValor("IT-ALTA", QGUtilidadesFechasUtils.fromDateToString(new Date(),QGUtilidadesFechasUtils.FORMATO_FECHA_2)+"000");
			}else{
				servicio.setValor("CO-USUARIO-ALT", detalleConsentimientosDto.getUsuarioAlta());
				servicio.setValor("IT-ALTA", QGUtilidadesFechasUtils.fromDateToString(detalleConsentimientosDto.getFechaAlta(),QGUtilidadesFechasUtils.FORMATO_FECHA_2)+"000");
				servicio.setValor("CO-USUARIO-MOD", usuarioLogado.getUsername());
				servicio.setValor("IT-ULT-MOD", QGUtilidadesFechasUtils.fromDateToString(new Date(),QGUtilidadesFechasUtils.FORMATO_FECHA_2)+"000");
			}				

			servicio.setValor("CO-ESP-DRH", detalleConsentimientosDto.getCodigoConsentimiento());
			
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

	public QGCGlobalDto obtenerAmbito(String codigo) {
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		ArrayList listaDatos = new ArrayList ();

	 

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_LISTA_AMBITOS, obtenerUsuarioLogado());

			 //ENTRADA
			servicio.setValor("IN-ACTUACION", QGConstantes.CODIGO_UNO);
			servicio.setValor("CO-AMB-APL-CSM-DRH", codigo);

			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();

			QGConsentimientosDto consentimientosDto = new QGConsentimientosDto();

			consentimientosDto.setAmbitoAplicacion(servicio.getValorAsString("CO-AMB-APL-CSM-DRH"));
			consentimientosDto.setDescAmbito(servicio.getValorAsString("DS-AMB-APL-CSM-DRH"));
			consentimientosDto.setFxIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-INI-VIGENCIA"), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			consentimientosDto.setFxFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-FIN-VIGENCIA"), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			consentimientosDto.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT"));
			consentimientosDto.setUsuarioMod(servicio.getValorAsString("CO-USUARIO-MOD"));
			consentimientosDto.setFxAlta(servicio.getValorAsDate("IT-ALTA"));
			consentimientosDto.setFxUltModificacion(servicio.getValorAsDate("IT-ULT-MOD"));
			consentimientosDto.setNombrePrograma(servicio.getValorAsString("NO-PROG-MOD"));

			listaDatos.add(consentimientosDto);

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

	public QGCGlobalDto obtenerDetalleConsentimiento(String codigo) {
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		ArrayList listaDatos = new ArrayList ();
	 
		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_DETALLE_CONSENTIMIENTO, obtenerUsuarioLogado());

			
			//ENTRADA
			servicio.setValor("CO-DRH-UNF-LOPD", codigo);

			servicio.ejecutarTransaccion();

			QGDetalleConsentimientosDto detalleConsentimientosDto = new QGDetalleConsentimientosDto();

			detalleConsentimientosDto.setCodigoDerecho(codigo);
			detalleConsentimientosDto.setDescDerechoInterno(servicio.getValorAsString("DS-CSM-DRH-INTERNO"));
			detalleConsentimientosDto.setDescDerechoExterno(servicio.getValorAsString("DS-CSM-DRH-EXTERNO"));

			if(servicio.getValorAsString("IN-CSM-DRH-EXL-CLI").equals(QGConstantes.CODIGO_S)){
				detalleConsentimientosDto.setExclusividad(Boolean.TRUE);
			}else{
				detalleConsentimientosDto.setExclusividad(Boolean.FALSE);
			}

			if(servicio.getValorAsString("IN-EJC-CSM-DRH-ALT").equals(QGConstantes.CODIGO_S)){
				detalleConsentimientosDto.setMarcaContratoAlta(Boolean.TRUE);
			}else{
				detalleConsentimientosDto.setMarcaContratoAlta(Boolean.FALSE);
			}

			detalleConsentimientosDto.setDiasLiberacion(servicio.getValorAsInt("CA-DIAS-LIBERACION"));
			detalleConsentimientosDto.setDiasNuevaComunicacion(servicio.getValorAsInt("CA-DIAS-NVA-CMC"));

			if(servicio.getValorAsString("CO-TIP-LGC-CSM-DRH").equals(QGConstantes.CODIGO_TODOS)){
				detalleConsentimientosDto.setTipoLogica(Boolean.TRUE);
			}else{
				detalleConsentimientosDto.setTipoLogica(Boolean.FALSE);
			}

			detalleConsentimientosDto.setCodNivelAplicacion(servicio.getValorAsString("CO-NIV-APL-CSM-DRH"));
			detalleConsentimientosDto.setDescNivelAlpicacion(servicio.getValorAsString("DS-NIV-APL-CSM-DRH"));
			detalleConsentimientosDto.setCodTipoObjeto(servicio.getValorAsString("CO-TIPO-OBJETO"));
			detalleConsentimientosDto.setDescTipoObjeto(servicio.getValorAsString("DS-TIPO-OBJETO"));
			detalleConsentimientosDto.setCodAmbito(servicio.getValorAsString("CO-AMB-APL-CSM-DRH"));
			detalleConsentimientosDto.setDescAmbito(servicio.getValorAsString("DS-AMB-APL-CSM-DRH"));
			detalleConsentimientosDto.setSecObservacion(servicio.getValorAsInt("NU-SEC-OBS-CSM-DRH"));
			detalleConsentimientosDto.setDescObservacion(servicio.getValorAsString("DS-OBS-CSM-DRH"));

			if(servicio.getValorAsString("IN-APLIC-PUB-CONT").equals(QGConstantes.CODIGO_S)){
				detalleConsentimientosDto.setContactadoTdE(Boolean.TRUE);
			}else{
				detalleConsentimientosDto.setContactadoTdE(Boolean.FALSE);
			}

			QGGruposComunesDto grupo = null;
			ArrayList lista = new ArrayList();
			for(int i=1; i <= servicio.getValorAsInt("SEG-ASO-OCU-NU").intValue(); i++){
				grupo = new QGGruposComunesDto();

				if(servicio.getValorAsString("IN-DRH-ASO-SGM", i).equals(QGConstantes.CODIGO_S)){
					grupo.setMarcado(Boolean.TRUE);
				}else{
					grupo.setMarcado(Boolean.FALSE);
				}

				grupo.setCodigo(servicio.getValorAsString("CO-SGM-UNF-UNM", i));
				grupo.setDescripcion(servicio.getValorAsString("DS-SGM-UNF-UNM", i));

				lista.add(grupo);
			}

			detalleConsentimientosDto.setGrupoSegmentos(lista);

			lista = new ArrayList();
			for(int i=1; i <= servicio.getValorAsInt("CMC-MDO-OCU-NU").intValue(); i++){
				grupo = new QGGruposComunesDto();

				if(servicio.getValorAsString("IN-DRH-ASO-MDO-CMC", i).equals(QGConstantes.CODIGO_S)){
					grupo.setMarcado(Boolean.TRUE);
				}else{
					grupo.setMarcado(Boolean.FALSE);
				}

				grupo.setCodigo(servicio.getValorAsString("CO-MEDIO-COMUNICA", i));
				grupo.setDescripcion(servicio.getValorAsString("DS-MEDIO-COMUNICA", i));

				lista.add(grupo);
			}

			detalleConsentimientosDto.setGrupoMediosComunicacion(lista);
			detalleConsentimientosDto.setCodigoTDE(servicio.getValorAsString("CO-DRH-LOPD-FIJ"));
			detalleConsentimientosDto.setCodigoTME(servicio.getValorAsString("CO-DRH-LOPD-MVL"));
			detalleConsentimientosDto.setCodLineaNegocio(servicio.getValorAsString("CO-LIN-NGC"));
			detalleConsentimientosDto.setFechaInicioVigencia(servicio.getValorAsDate("FX-INI-VIGENCIA"));
			detalleConsentimientosDto.setFechaFinVigencia(servicio.getValorAsDate("FX-FIN-VIGENCIA"));
			detalleConsentimientosDto.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT"));
			detalleConsentimientosDto.setUsuarioModificacion(servicio.getValorAsString("CO-USUARIO-MOD"));
			detalleConsentimientosDto.setFechaAlta(servicio.getValorAsDate("IT-ALTA"));
			detalleConsentimientosDto.setFechaUltModificacion(servicio.getValorAsDate("IT-ULT-MOD"));
			detalleConsentimientosDto.setNombrePrograma(servicio.getValorAsString("NO-PROG-MOD"));

			detalleConsentimientosDto.setCodigoTipoConsentimiento(servicio.getValorAsString("CO-ESP-DRH"));
			detalleConsentimientosDto.setDescTipoConsentimiento(servicio.getValorAsString("DS-ESP-DRH"));

			listaDatos.add(detalleConsentimientosDto);

			QGCGlobalDto.setlistaDatos(listaDatos);

		}catch (NAWRException e) {
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}

		return QGCGlobalDto;
	}

	public QGCGlobalDto obtenerListaAmbitos() {
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		ArrayList listaDatos = new ArrayList ();

		//QGUsuario usuarioLogado = obtenerUsuarioLogado();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_LISTA_AMBITOS, obtenerUsuarioLogado());
			
			servicio.setValor("IN-ACTUACION", QGConstantes.CODIGO_TODOS);

			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();

			QGConsentimientosDto consentimientosDto = null;

			for (int i=1; i<= servicio.getValorAsInt("OCU-NU").intValue(); i++){
				consentimientosDto = new QGConsentimientosDto();

				consentimientosDto.setAmbitoAplicacion(servicio.getValorAsString("CO-AMB-APL-CSM-DRH",i));
				consentimientosDto.setDescAmbito(servicio.getValorAsString("DS-AMB-APL-CSM-DRH",i));
				
				consentimientosDto.setFxIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-INI-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				consentimientosDto.setFxFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-FIN-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				
				consentimientosDto.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT",i));
				consentimientosDto.setUsuarioMod(servicio.getValorAsString("CO-USUARIO-MOD",i));
				consentimientosDto.setFxAlta(servicio.getValorAsDate("IT-ALTA",i));
				consentimientosDto.setFxUltModificacion(servicio.getValorAsDate("IT-ULT-MOD",i));
				consentimientosDto.setNombrePrograma(servicio.getValorAsString("NO-PROG-MOD",i));

				listaDatos.add(consentimientosDto);
			}
			QGCGlobalDto.setlistaDatos(listaDatos);
		} catch (NAWRException e) {
			String mensajeErrorServicio = e.getCampo("CACV_VARMSG");
			if(QGUtilidadesNegocioUtils.getCodigoError(e.getCampo("CACV_VARMSG")).equals(QGUtilidadesNegocioUtils.CODIGO_ERROR_NO_EXISTEN_REGISTROS)){					
				mensajeErrorServicio = "La búsqueda de ámbitos no devuelve resultados";
			}
			throw new QGApplicationException(mensajeErrorServicio);
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}

		return QGCGlobalDto;
	}
	
	public QGCGlobalDto obtenerListaTipoConsentimientos() {
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		ArrayList listaDatos = new ArrayList ();
		
		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_LISTA_CONSENTIMIENTOS, obtenerUsuarioLogado());
			
			servicio.setValor("IN-ACTUACION", QGConstantes.CODIGO_TODOS);

			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();

			QGConsentimientosDto consentimientosDto = null;

			for (int i=1; i<= servicio.getValorAsInt("OCU-NU").intValue(); i++){
				consentimientosDto = new QGConsentimientosDto();

				consentimientosDto.setCodigoConsentimiento(servicio.getValorAsString("CO-ESP-DRH",i));
				consentimientosDto.setTipoLogica(servicio.getValorAsString("CO-TIP-LGC-CSM-DRH",i));
				consentimientosDto.setDescConsentimiento(servicio.getValorAsString("DS-ESP-DRH",i));

				listaDatos.add(consentimientosDto);
			}

			QGCGlobalDto.setlistaDatos(listaDatos);
		} catch (NAWRException e) {
			String mensajeErrorServicio = e.getCampo("CACV_VARMSG");
			if(QGUtilidadesNegocioUtils.getCodigoError(e.getCampo("CACV_VARMSG")).equals(QGUtilidadesNegocioUtils.CODIGO_ERROR_NO_EXISTEN_REGISTROS)){					
				mensajeErrorServicio = "La búsqueda de ámbitos no devuelve resultados";
			}
			throw new QGApplicationException(mensajeErrorServicio);
		}finally {
			if (servicio != null) {
				servicio.unload();
			}
		}

		return QGCGlobalDto;
	}

	public QGCGlobalDto obtenerListaConsentimientos() {
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		ArrayList listaDatos = new ArrayList ();

		try {

			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_LISTA_CONSENTIMIENTOS, obtenerUsuarioLogado());
			 
			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();

			QGConsentimientosDto consentimientosDto = null;

			//Seteo de la salida
			for (int i=1; i<= servicio.getValorAsInt("OCU-NU").intValue(); i++){
				consentimientosDto = new QGConsentimientosDto();

				consentimientosDto.setCodigoLOPD(servicio.getValorAsString("CO-DRH-UNF-LOPD", i));
				consentimientosDto.setDescDerecho(servicio.getValorAsString("DS-CSM-DRH-EXTERNO", i));
				consentimientosDto.setCodNegocioFija(servicio.getValorAsString("CO-DRH-LOPD-FIJ", i));
				consentimientosDto.setCodNegocioMovil(servicio.getValorAsString("CO-DRH-LOPD-MVL", i));
				consentimientosDto.setAmbitoAplicacion(servicio.getValorAsString("CO-AMB-APL-CSM-DRH", i));
				consentimientosDto.setDescAmbito(servicio.getValorAsString("DS-AMB-APL-CSM-DRH", i));
				consentimientosDto.setTipoLogica(servicio.getValorAsString("CO-TIP-LGC-CSM-DRH", i));
				consentimientosDto.setFxIniVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-INI-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				consentimientosDto.setFxFinVigencia(QGUtilidadesFechasUtils.formatearFechaDesdeCopy(servicio.getValorAsString("FX-FIN-VIGENCIA",i), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				consentimientosDto.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT", i));
				consentimientosDto.setUsuarioMod(servicio.getValorAsString("CO-USUARIO-MOD", i));
				consentimientosDto.setFxAlta(servicio.getValorAsDate("IT-ALTA", i));
				consentimientosDto.setFxUltModificacion(servicio.getValorAsDate("IT-ULT-MOD", i));

				listaDatos.add(consentimientosDto); 
			}

			QGCGlobalDto.setlistaDatos(listaDatos);

		} catch (NAWRException e) {
			
			String mensajeErrorServicio = e.getCampo("CACV_VARMSG");
			if(QGUtilidadesNegocioUtils.getCodigoError(e.getCampo("CACV_VARMSG")).equals(QGUtilidadesNegocioUtils.CODIGO_ERROR_NO_EXISTEN_REGISTROS)){					
				mensajeErrorServicio = "La búsqueda de consentimientos no devuelve resultados";
			}
			throw new QGApplicationException(mensajeErrorServicio);
		}finally {
			if (servicio != null) {
				servicio.unload();
			}            
		}

		return QGCGlobalDto;
	}

	public QGCGlobalDto obtenerListaNivelAplicacion() {
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		ArrayList listaDatos = new ArrayList ();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_LISTA_NIVEL_APLICACION, obtenerUsuarioLogado());

			//ENTRADA
			servicio.setValor("IN-ACTUACION", QGConstantes.CODIGO_TODOS);

			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();

			QGCombosComunesDto combosComunesDto = null;

			for (int i=1; i<= servicio.getValorAsInt("OCU-NU").intValue(); i++){
				combosComunesDto = new QGCombosComunesDto();

				combosComunesDto.setCodigoNivel(servicio.getValorAsString("CO-NIV-APL-CSM-DRH",i));
				combosComunesDto.setDescripcionNivel(servicio.getValorAsString("DS-NIV-APL-CSM-DRH",i));
				combosComunesDto.setFechaInicioVigencia(servicio.getValorAsDate("FX-INI-VIGENCIA",i));
				combosComunesDto.setFechaFinVigencia(servicio.getValorAsDate("FX-FIN-VIGENCIA",i));
				combosComunesDto.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT",i));
				combosComunesDto.setUsuarioModificacion(servicio.getValorAsString("CO-USUARIO-MOD",i));
				combosComunesDto.setFechaAltaNivel(servicio.getValorAsDate("IT-ALTA",i));
				combosComunesDto.setFechaUltMod(servicio.getValorAsDate("IT-ULT-MOD",i));
				combosComunesDto.setNombrePrograma(servicio.getValorAsString("NO-PROG-MOD",i));

				listaDatos.add(combosComunesDto);
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



	public QGCGlobalDto obtenerListaTipoObjetos() {
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		ArrayList listaDatos = new ArrayList ();
		
		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_LISTA_TIPO_OBJETOS, obtenerUsuarioLogado());

			 //ENTRADA
			servicio.setValor("IN-ACTUACION", QGConstantes.CODIGO_TODOS);			

			servicio.ejecutarTransaccion();

			QGCombosComunesDto combo = null;
			for(int i=1; i <= servicio.getValorAsInt("OCU-NU").intValue(); i++){
				combo = new QGCombosComunesDto();

				combo.setCodigoNivel(servicio.getValorAsString("CO-TIPO-OBJETO", i));
				combo.setDescripcionNivel(servicio.getValorAsString("DS-TIPO-OBJETO", i));
				combo.setFechaAltaNivel(servicio.getValorAsDate("IT-ALTA", i));
				combo.setFechaFinVigencia(servicio.getValorAsDate("FX-FIN-VIGENCIA", i));
				combo.setFechaInicioVigencia(servicio.getValorAsDate("FX-INI-VIGENCIA", i));
				combo.setFechaUltMod(servicio.getValorAsDate("IT-ULT-MOD", i));
				combo.setNombrePrograma(servicio.getValorAsString("NO-PROG-MOD", i));
				combo.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT", i));
				combo.setUsuarioModificacion(servicio.getValorAsString("CO-USUARIO-MOD", i));

				listaDatos.add(combo);
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

	public QGCGlobalDto obtenerNivelAplicacion(String codigo) {
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		ArrayList listaDatos = new ArrayList ();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_LISTA_NIVEL_APLICACION, obtenerUsuarioLogado());

			//ENTRADA			
			servicio.setValor("IN-ACTUACION", QGConstantes.CODIGO_UNO);
			servicio.setValor("CO-NIV-APL-CSM-DRH", codigo);
			//Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();

			QGCombosComunesDto combosComunesDto = new QGCombosComunesDto();

			combosComunesDto.setCodigoNivel(servicio.getValorAsString("CO-NIV-APL-CSM-DRH"));
			combosComunesDto.setDescripcionNivel(servicio.getValorAsString("DS-NIV-APL-CSM-DRH"));
			combosComunesDto.setFechaInicioVigencia(servicio.getValorAsDate("FX-INI-VIGENCIA"));
			combosComunesDto.setFechaFinVigencia(servicio.getValorAsDate("FX-FIN-VIGENCIA"));
			combosComunesDto.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT"));
			combosComunesDto.setUsuarioModificacion(servicio.getValorAsString("CO-USUARIO-MOD"));
			combosComunesDto.setFechaAltaNivel(servicio.getValorAsDate("IT-ALTA"));
			combosComunesDto.setFechaUltMod(servicio.getValorAsDate("IT-ULT-MOD"));
			combosComunesDto.setNombrePrograma(servicio.getValorAsString("NO-PROG-MOD"));

			listaDatos.add(combosComunesDto);

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

	public QGCGlobalDto obtenerTextoLegal(String codigo, Integer secuencial) {
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		ArrayList listaDatos = new ArrayList ();
		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_TEXTO_LEGAL, obtenerUsuarioLogado());

			//ENTRADA
			servicio.setValor("ENTRADA.CO-DRH-UNF-LOPD", codigo);
			servicio.setValor("ENTRADA.NU-SEC-TXT-LEG-CSM", new Integer(1));

			servicio.ejecutarTransaccion();

			QGTextoLegalDto textoLegalDto = new QGTextoLegalDto();

			textoLegalDto.setCodigoDerecho(codigo);
			textoLegalDto.setFecFinVigencia(servicio.getValorAsDate("FX-FIN-VIGENCIA"));
			textoLegalDto.setFechaAlta(servicio.getValorAsDate("IT-ALTA"));
			textoLegalDto.setFechaUltModificacion(servicio.getValorAsDate("IT-ULT-MOD"));
			textoLegalDto.setFecInicioVigencia(servicio.getValorAsDate("FX-INI-VIGENCIA"));
			textoLegalDto.setNombrePrograma(servicio.getValorAsString("NO-PROG-MOD"));
			textoLegalDto.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT"));
			textoLegalDto.setUsuarioModificacion(servicio.getValorAsString("CO-USUARIO-MOD"));
			textoLegalDto.setTextoLegal(servicio.getValorAsString("DS-TXT-LEG-CSM-DRH"));
			textoLegalDto.setSecuencial(servicio.getValorAsInt("SALIDA.NU-SEC-TXT-LEG-CSM"));
			int i = 2;
			while(servicio.getValorAsString("IN-TXT-LEG-CSM").equals(QGConstantes.CODIGO_S)){
				servicio.setValor("ENTRADA.CO-DRH-UNF-LOPD", codigo);
				servicio.setValor("ENTRADA.NU-SEC-TXT-LEG-CSM", new Integer(i));
				servicio.ejecutarTransaccion();
				textoLegalDto.setTextoLegal(textoLegalDto.getTextoLegal() + " " + servicio.getValorAsString("DS-TXT-LEG-CSM-DRH"));
				textoLegalDto.setSecuencial(servicio.getValorAsInt("SALIDA.NU-SEC-TXT-LEG-CSM"));
				i++;
			}

			listaDatos.add(textoLegalDto);

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

	public QGCGlobalDto obtenerTipoObjeto(String codigo) {
		QGNpaNA servicio = null;
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		ArrayList listaDatos = new ArrayList ();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_OBTENER_LISTA_TIPO_OBJETOS, obtenerUsuarioLogado());

			servicio.setValor("IN-ACTUACION", QGConstantes.CODIGO_UNO);
			servicio.setValor("CO-TIPO-OBJETO", codigo);

			servicio.ejecutarTransaccion();

			QGCombosComunesDto combo = null;
			for(int i=0; i < servicio.getValorAsInt("OCU-NU").intValue(); i++){
				combo = new QGCombosComunesDto();

				combo.setCodigoNivel(servicio.getValorAsString("CO-TIPO-OBJETO", i));
				combo.setDescripcionNivel(servicio.getValorAsString("DS-TIPO-OBJETO", i));
				combo.setFechaAltaNivel(servicio.getValorAsDate("IT-ALTA", i));
				combo.setFechaFinVigencia(servicio.getValorAsDate("FX-FIN-VIGENCIA", i));
				combo.setFechaInicioVigencia(servicio.getValorAsDate("FX-INI-VIGENCIA", i));
				combo.setFechaUltMod(servicio.getValorAsDate("IT-ULT-MOD", i));
				combo.setNombrePrograma(servicio.getValorAsString("NO-PROG-MOD", i));
				combo.setUsuarioAlta(servicio.getValorAsString("CO-USUARIO-ALT", i));
				combo.setUsuarioModificacion(servicio.getValorAsString("CO-USUARIO-MOD", i));

				listaDatos.add(combo);
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

}
