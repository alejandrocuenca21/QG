/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.ubicacion.struts.actions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.security.context.SecurityContextHolder;

import com.telefonica.ssnn.qg.administracion.ubicacion.dto.QGTiposUbicacionDto;
import com.telefonica.ssnn.qg.administracion.ubicacion.servicio.interfaz.QGUbicacionServicio;
import com.telefonica.ssnn.qg.administracion.ubicacion.struts.forms.QGTiposUbicacionForm;
import com.telefonica.ssnn.qg.auditoria.servicio.interfaz.QGAuditorServicio;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.struts.QGBaseAction;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * Action de Tipos de Ubicacion.
 * 
 * @author cnunezba
 *
 * @struts.action 
 *  name="QGTiposUbicacionForm" 
 *  path="/TiposUbicacion" 
 *  input="/vTiposUbicacion.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="buscar, modificar, alta, baja, exportarPDF"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vTiposUbicacion.do"
 */
public class QGTiposUbicacionAction extends QGBaseAction{
	
	private static final Logger logger = Logger.getLogger(QGTiposUbicacionAction.class);
	
	private QGUbicacionServicio ubicacionServicio;
	
	private QGGenerarInformeServicio generarInformeServicio;
	
	private QGAuditorServicio auditorServicio;
	
	
    public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	QGUsuario usuarioLogado = (QGUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
    	QGTiposUbicacionForm tiposUbicacionForm = (QGTiposUbicacionForm) form;
		
    	tiposUbicacionForm.setUsuarioLogado(usuarioLogado.getUsername());
        return mapping.findForward(SUCCESS);
    }
    
    public void buscar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	
        
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();
        
        //Seteamos los datos del usuario
        //setDatosUsuario(request, TiposUbicacionDto);
        
        try {
            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getUbicacionServicio().obtenerListaTiposUbicacion();
        } catch (Exception ex) {
        	tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
    }
    
    public void modificar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	 // casting del formulario
    	QGTiposUbicacionForm tiposUbicacionForm = (QGTiposUbicacionForm) form;
    	
    	 //Seteamos los datos del usuario
        //setDatosUsuario(request, TiposUbicacionDto);
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(tiposUbicacionForm.getTiposUbicacionJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGTiposUbicacionDto tiposUbicacionDto = (QGTiposUbicacionDto) JSONObject.toBean(jsonObject, QGTiposUbicacionDto.class);
    	
    	QGCGlobalDto QGCGlobalDto = new  QGCGlobalDto();
        
    	try{
    		QGCGlobalDto = this.getUbicacionServicio().modificarTiposUbicacion(tiposUbicacionDto);
    		List listaMensajes = new ArrayList();
    		// Si todo ha ido bien mostramos un mensaje de guardado en la
			// pantalla
			listaMensajes
					.add("La operaci&oacute;n se ha realizado correctamente");
			QGCGlobalDto.setlistaMensajes(listaMensajes);

			String resultadoNav ="No se ha podido modificar el tipo de ubicación.";
    		String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
        	if (tiposUbicacionDto != null) {
    			resultadoNav = tiposUbicacionDto.getCodigo();
        		resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
            }
        	this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_TIP_UBI_MOD,resultado, resultadoNav);
    	}catch (Exception e) {
    		this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_TIP_UBI_MOD,
					QGConstantes.DS_RESULTADO_OPERA_ERROR, e.getMessage());
    		tratarMensajesExcepcion(response, e);
		}
    	
    	if (tiposUbicacionDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
    	
    }
    
    public void alta(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	 // casting del formulario
    	QGTiposUbicacionForm tiposUbicacionForm = (QGTiposUbicacionForm) form;    	
    	
    	 //Seteamos los datos del usuario
        //setDatosUsuario(request, TiposUbicacionDto);
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(tiposUbicacionForm.getTiposUbicacionJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGTiposUbicacionDto tiposUbicacionDto = (QGTiposUbicacionDto) JSONObject.toBean(jsonObject, QGTiposUbicacionDto.class);
    	
    	QGCGlobalDto QGCGlobalDto = new  QGCGlobalDto();
        
    	try{
    		QGCGlobalDto = this.getUbicacionServicio().altaTipoUbicacion(tiposUbicacionDto);
    		List listaMensajes = new ArrayList();
    		// Si todo ha ido bien mostramos un mensaje de guardado en la
			// pantalla
			listaMensajes
					.add("La operaci&oacute;n se ha realizado correctamente");
			QGCGlobalDto.setlistaMensajes(listaMensajes);
			
    		String resultadoNav ="No se ha dado de alta el tipo de ubicación.";
    		String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
        	if (tiposUbicacionDto != null) {
    			resultadoNav = tiposUbicacionDto.getCodigo();
        		resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
            }
        	this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_TIP_UBICAC,resultado, resultadoNav);
    	}catch (Exception e) {
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_TIP_UBICAC,
					QGConstantes.DS_RESULTADO_OPERA_ERROR, e.getMessage());
    		tratarMensajesExcepcion(response, e);
		}
    	
    	if (tiposUbicacionDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
    	
    }
    
    public void baja(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	 // casting del formulario
    	QGTiposUbicacionForm tiposUbicacionForm = (QGTiposUbicacionForm) form;
    	
    	 //Seteamos los datos del usuario
        //setDatosUsuario(request, listaMediosComunicacionDto);
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(tiposUbicacionForm.getTiposUbicacionJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGTiposUbicacionDto tiposUbicacionDto = (QGTiposUbicacionDto) JSONObject.toBean(jsonObject, QGTiposUbicacionDto.class);
    	
    	QGCGlobalDto QGCGlobalDto = new  QGCGlobalDto();
        
    	try{
    		QGCGlobalDto = this.getUbicacionServicio().bajaTipoUbicacion(tiposUbicacionDto);
    		
    	}catch (Exception e) {
    		tratarMensajesExcepcion(response, e);
		}
    	
    	if (tiposUbicacionDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
    	
    }
    
    public void exportarPDF(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = null;
        
        //Seteamos los datos del usuario
        //setDatosUsuario(request, TiposUbicacionDto);
        
        try {
            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getUbicacionServicio().obtenerListaTiposUbicacion();
        	
        	if (QGCGlobalDto != null) {
        		ByteArrayOutputStream baos = generarInformeServicio.generarInformeTiposUbicacionPDF(QGCGlobalDto.getlistaDatos());
        		
        		response.setContentType("application/pdf");
    	   		
    	   		response.addHeader("Content-Disposition", "attachment; filename=\"informe.pdf\"");
    	   		response.setHeader("Content-Transfer-Encoding","binary");
    	   		
    	   		response.setContentLength(baos.toByteArray().length);
    	   		
    	   		response.getOutputStream().write(baos.toByteArray());
    	   		
    	   		response.getOutputStream().flush();   	
    	   		
    	   		response.getOutputStream().close();	   
        	}
        	
        } catch (Exception ex) {
            logger.info(ex);
        }

       
    	
    }

	public QGUbicacionServicio getUbicacionServicio() {
		return ubicacionServicio;
	}

	public void setUbicacionServicio(QGUbicacionServicio ubicacionServicio) {
		this.ubicacionServicio = ubicacionServicio;
	}

	public QGGenerarInformeServicio getGenerarInformeServicio() {
		return generarInformeServicio;
	}

	public void setGenerarInformeServicio(
			QGGenerarInformeServicio generarInformeServicio) {
		this.generarInformeServicio = generarInformeServicio;
	}

	public QGAuditorServicio getAuditorServicio() {
		return auditorServicio;
	}

	public void setAuditorServicio(QGAuditorServicio auditorServicio) {
		this.auditorServicio = auditorServicio;
	}
	
	
}
