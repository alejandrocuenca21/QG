/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.consentimientos.struts.actions;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.telefonica.ssnn.qg.administracion.consentimientos.dto.QGDetalleConsentimientosDto;
import com.telefonica.ssnn.qg.administracion.consentimientos.servicio.interfaz.QGConsentimientosServicio;
import com.telefonica.ssnn.qg.administracion.consentimientos.struts.forms.QGConsentimientosDerechosForm;
import com.telefonica.ssnn.qg.administracion.medios.servicio.interfaz.QGMediosServicio;
import com.telefonica.ssnn.qg.auditoria.servicio.interfaz.QGAuditorServicio;
import com.telefonica.ssnn.qg.struts.QGBaseAction;

import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGGruposComunesDto;
import com.telefonica.ssnn.qg.base.dto.QGTextoLegalDto;

/**
 * Action de Consentimientos.
 * 
 * @author cnunezba
 * 
 * @struts.action 
 *  name="QGConsentimientosDerechosForm" 
 *  path="/DetalleConsentimientosDerechos" 
 *  input="/vDetalleConsentimientosDerechos.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="irAlta, irDetalle, cargarAmbitos, obtenerSegmentos, 
 *  			obtenerMediosComunicacion, cargarNivelAplicacion, cargarTipoObjeto, alta, obtenerDatosDetalle,cargarDatosConsentimientos,
 *  			modificar"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vDetalleConsentimientosDerechos.do"
 *  
 *  @struts.action-forward 
 *  name="irAlta" 
 *  path="/vAltaConsentimientosDerechos.do"
 *  
 *  @struts.action-forward 
 *  name="irDetalle" 
 *  path="/vDetalleConsentimientosDerechos.do"
 */
public class QGDetalleAltaConsentimientosAction extends QGBaseAction{
	
	private QGConsentimientosServicio consentimientosServicio;
	
	private QGMediosServicio mediosServicio;
	
	private QGAuditorServicio auditorServicio;
	
    public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return mapping.findForward(SUCCESS);
    }
    
    public ActionForward irAlta(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {


    	return mapping.findForward(QGConstantes.IR_ALTA);
    	
    }
    
    public ActionForward irDetalle(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {


    	return mapping.findForward(QGConstantes.IR_DETALLE);
    	
    }
    
    public void cargarAmbitos(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();
        
        try {

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getConsentimientosServicio().obtenerListaAmbitos();                    

        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
	}
    
    
    
    public void obtenerSegmentos(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();
        
        try {

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getConsentimientosServicio().obtenerListaSegmentos();
                    

        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
	}
    
    public void obtenerMediosComunicacion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();
        
        try {

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getMediosServicio().obtenerListaMediosComunicacion();
                    

        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
	}
    
    public void cargarNivelAplicacion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();
        
        try {

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getConsentimientosServicio().obtenerListaNivelAplicacion();
                    

        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
	}
    
    public void cargarTipoObjeto(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();
        
        try {

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getConsentimientosServicio().obtenerListaTipoObjetos();
                    

        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
	}
    
    public void alta(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	 // casting del formulario
    	QGConsentimientosDerechosForm consentimientosDerechosForm = (QGConsentimientosDerechosForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(consentimientosDerechosForm.getConsentimientosDerechosJSON());
    	
    	HashMap mapa = new HashMap();
    	mapa.put(QGConstantes.GRUPO_MEDIOS, QGGruposComunesDto.class);
    	mapa.put(QGConstantes.GRUPO_SEGMENTOS, QGGruposComunesDto.class);
    	//Convertir la cadena JSON en el DTO
    	QGDetalleConsentimientosDto detalleConsentimientosDto = (QGDetalleConsentimientosDto) JSONObject.toBean(jsonObject, QGDetalleConsentimientosDto.class, mapa);    	
    	
    	jsonObject = JSONObject.fromObject(consentimientosDerechosForm.getTextoLegalJSON());
    	QGTextoLegalDto QGTextoLegalDto = (QGTextoLegalDto) JSONObject.toBean(jsonObject, QGTextoLegalDto.class);
    	
    	QGCGlobalDto QGCGlobalDto = new  QGCGlobalDto();
        
    	try{
    		QGCGlobalDto = getConsentimientosServicio().altaConsentimiento(detalleConsentimientosDto);
    		QGCGlobalDto = getConsentimientosServicio().altaTextoLegal(QGTextoLegalDto);
    		
    		String resultadoNav ="No se ha dado de alta el consentimiento.";
    		String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
        	if (detalleConsentimientosDto != null) {
    			resultadoNav = detalleConsentimientosDto.getCodigoDerecho();
        		resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
            }
        	this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_CONSENT_ALTA,resultado, resultadoNav);
    	}catch (Exception e) {
    		this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_CONSENT_ALTA,
					QGConstantes.DS_RESULTADO_OPERA_ERROR, e.getMessage());
    		tratarMensajesExcepcion(response, e);
		}
    	
    	if (detalleConsentimientosDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
    	
    }
    
    public void modificar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	 // casting del formulario
    	QGConsentimientosDerechosForm consentimientosDerechosForm = (QGConsentimientosDerechosForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(consentimientosDerechosForm.getConsentimientosDerechosJSON());
    	
    	HashMap mapa = new HashMap();
    	mapa.put(QGConstantes.GRUPO_MEDIOS, QGGruposComunesDto.class);
    	mapa.put(QGConstantes.GRUPO_SEGMENTOS, QGGruposComunesDto.class);
    	//Convertir la cadena JSON en el DTO
    	QGDetalleConsentimientosDto detalleConsentimientosDto = (QGDetalleConsentimientosDto) JSONObject.toBean(jsonObject, QGDetalleConsentimientosDto.class, mapa);    	
    	
    	jsonObject = JSONObject.fromObject(consentimientosDerechosForm.getTextoLegalJSON());
    	QGTextoLegalDto QGTextoLegalDto = (QGTextoLegalDto) JSONObject.toBean(jsonObject, QGTextoLegalDto.class);
    	
    	QGCGlobalDto QGCGlobalDto = new  QGCGlobalDto();
        
    	try{
    		QGCGlobalDto = getConsentimientosServicio().modificarConsentimiento(detalleConsentimientosDto);
    		QGCGlobalDto = getConsentimientosServicio().modificarTextoLegal(QGTextoLegalDto);
    		
    		String resultadoNav ="No se ha podido modificar el consentimiento.";
    		String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
        	if (detalleConsentimientosDto != null) {
    			resultadoNav = detalleConsentimientosDto.getCodigoDerecho();
        		resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
            }
        	this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_CONSENT_MODI,resultado, resultadoNav);
    	}catch (Exception e) {
    		this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_CONSENT_MODI,
					QGConstantes.DS_RESULTADO_OPERA_ERROR, e.getMessage());
    		tratarMensajesExcepcion(response, e);
		}
    	
    	if (detalleConsentimientosDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
    	
    }
    
    public void obtenerDatosDetalle(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	 // casting del formulario
    	QGConsentimientosDerechosForm consentimientosDerechosForm = (QGConsentimientosDerechosForm) form;
    	
    	JSONObject jsonObject = JSONObject.fromObject(consentimientosDerechosForm.getConsentimientosDerechosJSON());
    	
    	QGDetalleConsentimientosDto detalleConsentimientosDto = (QGDetalleConsentimientosDto) JSONObject.toBean(jsonObject, QGDetalleConsentimientosDto.class);
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();
        QGCGlobalDto cGlobalTextoLegal = new QGCGlobalDto ();
        
        try {
        	// obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getConsentimientosServicio().obtenerDetalleConsentimiento(detalleConsentimientosDto.getCodigoDerecho());
        	cGlobalTextoLegal = this.getConsentimientosServicio().obtenerTextoLegal(detalleConsentimientosDto.getCodigoDerecho(), new Integer(1));
        	QGCGlobalDto.getlistaDatos().add(1, cGlobalTextoLegal);
        	
			String resultado=QGConstantes.DS_RESULTADO_OPERA_OK;
			String resultadoNav=detalleConsentimientosDto.getCodigoDerecho();
			if(QGCGlobalDto.getlistaDatos().size()==0) {
				resultado= QGConstantes.DS_RESULTADO_OPERA_NOK;
				resultadoNav="No se ha encontrado el consentimiento.";
			}
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_CONSENT_DET,resultado, resultadoNav);
        } catch (Exception ex) {
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_CONSENT_DET,
					QGConstantes.DS_RESULTADO_OPERA_ERROR, ex.getMessage());
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
	}
    
    public void cargarDatosConsentimientos(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	HashMap datos = new HashMap();
    	QGCGlobalDto QGCGlobalDto = null;
    	
    	  try {

    		//Obtenemos la lista de nivel de aplicacion
          	QGCGlobalDto = this.getConsentimientosServicio().obtenerListaNivelAplicacion();
          	//Añadimos los niveles de aplicacion a la respuesta JSON
          	datos.put(QGConstantes.NIVELES_APLICACION, QGCGlobalDto);

          	//Obtenemos la lista de Tipo de Objeto
          	QGCGlobalDto = this.getConsentimientosServicio().obtenerListaTipoObjetos();
          	//Añadimos la lista de Tipo de Objeto a la respuesta JSON
          	datos.put(QGConstantes.TIPOS_OBJETOS, QGCGlobalDto);
          	
          	//Obtenemos la lista de Ambitos
          	QGCGlobalDto = this.getConsentimientosServicio().obtenerListaAmbitos();
          	//Añadimos la lista de Ambitos
          	datos.put(QGConstantes.AMBITOS, QGCGlobalDto);
          	
          	//Obtenemos la lista de Tipos de consentimiento
          	QGCGlobalDto = this.getConsentimientosServicio().obtenerListaTipoConsentimientos();
          	//Añadimos la lista de Tipos de consentimiento
          	datos.put(QGConstantes.TIPOS_CONSENTIMIENTO, QGCGlobalDto);
          	
          	//Obtenemos la lista de Segmentos
          	QGCGlobalDto = this.getConsentimientosServicio().obtenerListaSegmentos();
          	//Añadimos la lista de Segmentos
          	datos.put(QGConstantes.SEGMENTOS, QGCGlobalDto);
          	
          	//Obtenemos la lista de Medios de comunicacion
          	QGCGlobalDto = this.getMediosServicio().obtenerListaMediosComunicacion();
          	//Añadimos la lista de Medios de comunicacion
          	datos.put(QGConstantes.MEDIOS, QGCGlobalDto);
          	
          	//Recuperamos el Dto para pasar al servicio el codigo del derecho
          	QGConsentimientosDerechosForm consentimientosDerechosForm = (QGConsentimientosDerechosForm) form;
        	
        	JSONObject jsonObject = JSONObject.fromObject(consentimientosDerechosForm.getConsentimientosDerechosJSON());
        	
        	QGDetalleConsentimientosDto detalleConsentimientosDto = (QGDetalleConsentimientosDto) JSONObject.toBean(jsonObject, QGDetalleConsentimientosDto.class);
          	
        	if(detalleConsentimientosDto != null){
	        	//Llamamos al servicio que nos devuelve el texto legal
	          	QGCGlobalDto = this.getConsentimientosServicio().obtenerTextoLegal(detalleConsentimientosDto.getCodigoDerecho(), new Integer(1));
	          	//Añadimos a la lista el textLegal
	          	datos.put(QGConstantes.TEXTO_LEGAL, QGCGlobalDto);
        	}
        	
        	datos.put(SUCCESS, Boolean.TRUE);
          	//Enviamos el objeto por JSON
          	enviarObjetoJSON(datos,response);
          	
          } catch (Exception ex) {
        	  datos.put(SUCCESS, Boolean.FALSE);
        	  //Enviamos el objeto por JSON
          	  enviarObjetoJSON(datos,response);
              tratarMensajesExcepcion(response, ex);
          }
    	
    }
    
    public QGConsentimientosServicio getConsentimientosServicio() {
		return consentimientosServicio;
	}

	public void setConsentimientosServicio(
			QGConsentimientosServicio consentimientosServicio) {
		this.consentimientosServicio = consentimientosServicio;
	}

	public QGMediosServicio getMediosServicio() {
		return mediosServicio;
	}

	public void setMediosServicio(QGMediosServicio mediosServicio) {
		this.mediosServicio = mediosServicio;
	}

	public QGAuditorServicio getAuditorServicio() {
		return auditorServicio;
	}

	public void setAuditorServicio(QGAuditorServicio auditorServicio) {
		this.auditorServicio = auditorServicio;
	}
	
	
}
