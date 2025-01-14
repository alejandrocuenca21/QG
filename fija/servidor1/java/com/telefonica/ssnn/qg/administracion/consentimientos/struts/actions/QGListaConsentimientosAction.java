/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.consentimientos.struts.actions;

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
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.User;

import com.telefonica.ssnn.qg.administracion.consentimientos.dto.QGConsentimientosDto;
import com.telefonica.ssnn.qg.administracion.consentimientos.dto.QGDetalleConsentimientosDto;
import com.telefonica.ssnn.qg.administracion.consentimientos.servicio.interfaz.QGConsentimientosServicio;
import com.telefonica.ssnn.qg.administracion.consentimientos.struts.forms.QGConsentimientosDerechosForm;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.struts.QGBaseAction;

import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * Action de Consentimientos.
 * 
 * @author cnunezba
 *
 * @struts.action 
 *  name="QGConsentimientosDerechosForm" 
 *  path="/ConsentimientosDerechos" 
 *  input="/vConsentimientosDerechos.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="buscar, baja, exportarPDF"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vConsentimientosDerechos.do"
 */
public class QGListaConsentimientosAction extends QGBaseAction{
	
	private static final Logger logger = Logger.getLogger(QGListaConsentimientosAction.class);
	
	private QGConsentimientosServicio consentimientosServicio;
	
	private QGGenerarInformeServicio generarInformeServicio;
	
    public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return mapping.findForward(SUCCESS);
    }
    
    public void buscar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();
        
        //Seteamos los datos del usuario
        //setDatosUsuario(request, consentimientosDto);
        
        try {

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getConsentimientosServicio().obtenerListaConsentimientos();
                    

        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
    }
    
    public void baja(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	 // casting del formulario
    	QGConsentimientosDerechosForm consentimientosDerechosForm = (QGConsentimientosDerechosForm) form;
    	
    	 //Seteamos los datos del usuario
        //setDatosUsuario(request, listaMediosComunicacionDto);
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(consentimientosDerechosForm.getConsentimientosDerechosJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGDetalleConsentimientosDto detalleConsentimientosDto = (QGDetalleConsentimientosDto) JSONObject.toBean(jsonObject, QGDetalleConsentimientosDto.class);
    	
    	QGCGlobalDto QGCGlobalDto = new  QGCGlobalDto();
        
    	try{
			//Recuperamos el detalle del consentimiento a eliminar
    		QGCGlobalDto = this.getConsentimientosServicio().obtenerDetalleConsentimiento(detalleConsentimientosDto.getCodigoDerecho());
    		detalleConsentimientosDto = (QGDetalleConsentimientosDto)QGCGlobalDto.getlistaDatos().get(0);
    		//lanzamos la baja
    		QGCGlobalDto = this.getConsentimientosServicio().bajaConsentimiento(detalleConsentimientosDto);
    		
    	}catch (Exception e) {
    		tratarMensajesExcepcion(response, e);
		}
    	
    	if (detalleConsentimientosDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
    	
    }
    
    public void exportarPDF(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();
        
        //Seteamos los datos del usuario
        //setDatosUsuario(request, consentimientosDto);
        
        try {

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getConsentimientosServicio().obtenerListaConsentimientos();
             if (QGCGlobalDto != null) {
            	
            	 ArrayList listadoNuevo = new ArrayList();	
        		for(int i = 0; i < QGCGlobalDto.getlistaDatos().size(); i++){
        			
        			QGConsentimientosDto consentimiento = (QGConsentimientosDto)QGCGlobalDto.getlistaDatos().get(i); 
        			if(consentimiento.getTipoLogica().equals(QGConstantes.CODIGO_E)){
        				consentimiento.setTipoLogica(QGConstantes.DESCRIPCION_E);
        			}else{
        				consentimiento.setTipoLogica(QGConstantes.DESCRIPCION_T);
        			}
        			listadoNuevo.add(consentimiento);
        		}
        		QGCGlobalDto.setlistaDatos(listadoNuevo);
        		 
        		ByteArrayOutputStream baos = generarInformeServicio.generarInformeConsentimientoPDF(QGCGlobalDto.getlistaDatos());
	        		
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

	public QGConsentimientosServicio getConsentimientosServicio() {
		return consentimientosServicio;
	}

	public void setConsentimientosServicio(
			QGConsentimientosServicio consentimientosServicio) {
		this.consentimientosServicio = consentimientosServicio;
	}

	public QGGenerarInformeServicio getGenerarInformeServicio() {
		return generarInformeServicio;
	}

	public void setGenerarInformeServicio(
			QGGenerarInformeServicio generarInformeServicio) {
		this.generarInformeServicio = generarInformeServicio;
	}
}
