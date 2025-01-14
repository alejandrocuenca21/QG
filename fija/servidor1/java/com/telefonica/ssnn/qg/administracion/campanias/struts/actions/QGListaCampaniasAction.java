/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.campanias.struts.actions;

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

import com.telefonica.ssnn.qg.administracion.campanias.dto.QGCampaniasDto;
import com.telefonica.ssnn.qg.administracion.campanias.servicio.interfaz.QGCampaniasServicio;
import com.telefonica.ssnn.qg.administracion.campanias.struts.forms.QGCampaniasForm;
import com.telefonica.ssnn.qg.administracion.ubicacion.struts.actions.QGTiposUbicacionAction;
import com.telefonica.ssnn.qg.auditoria.servicio.interfaz.QGAuditorServicio;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.struts.QGBaseAction;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * Action de Lista Campanias.
 * 
 * @author cnunezba
 *
 * @struts.action 
 *  name="QGCampaniasForm" 
 *  path="/Campanias" 
 *  input="/vCampanias.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="buscar, modificar, alta, baja, exportarPDF"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vCampanias.do"
 */
public class QGListaCampaniasAction extends QGBaseAction{
	
	private static final Logger logger = Logger.getLogger(QGTiposUbicacionAction.class);
		
	private QGCampaniasServicio campaniasServicio;
	
	private QGGenerarInformeServicio generarInformeServicio;
	
	private QGAuditorServicio auditorServicio;
	
	 public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

		QGUsuario usuarioLogado = (QGUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		QGCampaniasForm campaniasForm = (QGCampaniasForm) form;
		
		campaniasForm.setUsuarioLogado(usuarioLogado.getUsername());
		
        return mapping.findForward(SUCCESS);
    }
	 
	 public void buscar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
	        
    	//obtenemos los datos en el dto
        QGCGlobalDto cGlobalDto = new QGCGlobalDto ();
        
        //Seteamos los datos del usuario
        //setDatosUsuario(request, campaniasDto);
        
        try {

            // obtenemos el resultado de la llamada al servicio
        	cGlobalDto = this.getCampaniasServicio().obtenerListaCampanias();
                    

        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (cGlobalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(cGlobalDto, response);
        }
    }
	 
	 public void modificar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	 // casting del formulario
		 QGCampaniasForm campaniasForm = (QGCampaniasForm) form;    	
    	
    	 //Seteamos los datos del usuario
        //setDatosUsuario(request, campaniasDto);
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(campaniasForm.getCampaniasJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGCampaniasDto campaniasDto = (QGCampaniasDto) JSONObject.toBean(jsonObject, QGCampaniasDto.class);
    	
    	QGCGlobalDto cGlobalDto = new  QGCGlobalDto();
        
    	try{
    		cGlobalDto = this.getCampaniasServicio().modificarListaCampanias(campaniasDto);
    		List listaMensajes = new ArrayList();
    		// Si todo ha ido bien mostramos un mensaje de guardado en la
			// pantalla
			listaMensajes
					.add("La operaci&oacute;n se ha realizado correctamente");
			cGlobalDto.setlistaMensajes(listaMensajes);
			
			String resultadoNav ="No se ha podido modificar la campaña.";
    		String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
        	if (campaniasDto != null) {
    			resultadoNav = campaniasDto.getCodigo();
        		resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
            }
        	this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_CAMPANAS_MOD,resultado, resultadoNav);
    	}catch (Exception e) {
    		this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_CAMPANAS_MOD,
					QGConstantes.DS_RESULTADO_OPERA_ERROR, e.getMessage());
    		tratarMensajesExcepcion(response, e);
		}
    	
    	if (campaniasDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(cGlobalDto, response);
        }
    	
    }
	 
	 public void alta(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	 // casting del formulario
		 QGCampaniasForm campaniasForm = (QGCampaniasForm) form;
    	
    	 //Seteamos los datos del usuario
        //setDatosUsuario(request, campaniasDto);
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(campaniasForm.getCampaniasJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGCampaniasDto campaniasDto = (QGCampaniasDto) JSONObject.toBean(jsonObject, QGCampaniasDto.class);    	
    	
    	QGCGlobalDto cGlobalDto = new  QGCGlobalDto();
        
    	try{
    		cGlobalDto = this.getCampaniasServicio().altaCampania(campaniasDto);
    		List listaMensajes = new ArrayList();
    		// Si todo ha ido bien mostramos un mensaje de guardado en la
			// pantalla
			listaMensajes
					.add("La operaci&oacute;n se ha realizado correctamente");
			cGlobalDto.setlistaMensajes(listaMensajes);
			
    		String resultadoNav ="No se ha dado de alta la campaña.";
    		String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
        	if (campaniasDto != null) {
    			resultadoNav = campaniasDto.getCodigo();
        		resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
            }
        	this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_CAMPANAS,resultado, resultadoNav);
    	}catch (Exception e) {
    		this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_CAMPANAS,
					QGConstantes.DS_RESULTADO_OPERA_ERROR, e.getMessage());
    		tratarMensajesExcepcion(response, e);
		}
    	
    	if (campaniasDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(cGlobalDto, response);
        }
    	
    }
	 
	 public void baja(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	 // casting del formulario
		 QGCampaniasForm campaniasForm = (QGCampaniasForm) form;
    	
    	
    	 //Seteamos los datos del usuario
        //setDatosUsuario(request, campaniasDto);
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(campaniasForm.getCampaniasJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGCampaniasDto campaniasDto = (QGCampaniasDto) JSONObject.toBean(jsonObject, QGCampaniasDto.class);
    	
    	
    	QGCGlobalDto cGlobalDto = new  QGCGlobalDto();
        
    	try{
    		cGlobalDto = this.getCampaniasServicio().bajaCampania(campaniasDto);
    		
    	}catch (Exception e) {
    		tratarMensajesExcepcion(response, e);
		}
    	
    	if (campaniasDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(cGlobalDto, response);
        }
    	
    }
	 
	 public void exportarPDF(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws Exception {
		        
	    	//obtenemos los datos en el dto
	        QGCGlobalDto cGlobalDto = null;;
	        
	        
	        try {

	            // obtenemos el resultado de la llamada al servicio
	        	cGlobalDto = this.getCampaniasServicio().obtenerListaCampanias();
	        	
	        	if (cGlobalDto != null) {
	        		ByteArrayOutputStream baos = generarInformeServicio.generarInformeCampaniasPDF(cGlobalDto.getlistaDatos());
	        		
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

	public QGCampaniasServicio getCampaniasServicio() {
		return campaniasServicio;
	}

	public void setCampaniasServicio(QGCampaniasServicio campaniasServicio) {
		this.campaniasServicio = campaniasServicio;
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
