package com.telefonica.ssnn.qg.administracion.autorizaciones.struts.actions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.security.context.SecurityContextHolder;

import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGServiciosNADto;
import com.telefonica.ssnn.qg.administracion.autorizaciones.servicio.interfaz.QGServiciosNAServicio;
import com.telefonica.ssnn.qg.administracion.autorizaciones.struts.forms.QGServiciosNAForm;
import com.telefonica.ssnn.qg.auditoria.servicio.interfaz.QGAuditorServicio;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.struts.QGBaseAction;

/**
 * Action de Servicios NA.
 * 
 * @author jacastano
 *
 * @struts.action 
 *  name="QGServiciosNAForm" 
 *  path="/ServiciosNA" 
 *  input="/vServiciosNA.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="buscar, gestionar, exportarPDF, anteriror, siguiente, obtenerUsuario"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vServiciosNA.do"
 */
public class QGServiciosNAAction extends QGBaseAction {

	private static final Logger logger = Logger.getLogger(QGAutorizacionesAction.class);
	
	private QGServiciosNAServicio serviciosNAServicio;
	
	private QGGenerarInformeServicio generarInformeServicio;
	
	private QGAuditorServicio auditorServicio;

	private static HashMap metadatos = null;
	
    public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	QGUsuario usuarioLogado = (QGUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	QGServiciosNAForm serviciosNAForm = (QGServiciosNAForm) form;
    	
    	serviciosNAForm.setUsuarioLogado(usuarioLogado.getUsername());
    	
        return mapping.findForward(SUCCESS);
    }
    
    public void buscar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
    	//obtenemos los datos en el dto
		QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto();
    
        //Seteamos los datos del usuario
        //setDatosUsuario(request, listaMediosComunicacionDto);
      	QGServiciosNAForm serviciosNAForm = (QGServiciosNAForm) form;
      	
    	JSONObject jsonObject = JSONObject.fromObject(serviciosNAForm.getServiciosNAJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGServiciosNADto serviciosNA = (QGServiciosNADto) JSONObject.toBean(jsonObject, QGServiciosNADto.class);
    	   
        try {

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalPagingDto = this.getServiciosNAServicio().buscadorServiciosNA(serviciosNA.getPgnTx());
        	
        	metadatos = new HashMap();
        	
			metadatos.put("0", QGCGlobalPagingDto);       

        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalPagingDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJsonPagin(QGCGlobalPagingDto, response);
        }
    }
	public void anterior(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
	logger.info("Entramos en el método: **** --anterior-- ****");
	// casting del formulario  se le pasarian los datos de (indPgnIn y pgnTx).
	QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto();
    
    //Seteamos los datos del usuario
    //setDatosUsuario(request, listaMediosComunicacionDto);
  	QGServiciosNAForm serviciosNAForm = (QGServiciosNAForm) form;
  	
	JSONObject jsonObject = JSONObject.fromObject(serviciosNAForm.getServiciosNAJSON());
	
	//Convertir la cadena JSON en el DTO
	QGServiciosNADto serviciosNA = (QGServiciosNADto) JSONObject.toBean(jsonObject, QGServiciosNADto.class);
	try{
		
		if(metadatos.get(serviciosNA.getNejecucion().toString()) != null){
			
			QGCGlobalPagingDto =  (QGCGlobalPagingDto) metadatos.get(serviciosNA.getNejecucion().toString());
		} else{            
		    // obtenemos el resultado de la llamada al servicio
			QGCGlobalPagingDto = this.getServiciosNAServicio().buscadorServiciosNA(serviciosNA.getPgnTx());
			metadatos.put(serviciosNA.getNejecucion().toString(), QGCGlobalPagingDto);
		}

	}catch (Exception ex) {
        tratarMensajesExcepcion(response, ex);
    }

    if (QGCGlobalPagingDto != null) {
    	// enviamos los datos al js
    	escribirRespuestaPagingJson(QGCGlobalPagingDto, response);
    }
    logger.info("Salimos del método: **** --anterior-- ****");
}
	public void siguiente(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
	logger.info("Entramos en el método: **** --siguiente-- ****");
	// casting del formulario  se le pasarian los datos de (indPgnIn y pgnTx).
	QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto();
    
    //Seteamos los datos del usuario
    //setDatosUsuario(request, listaMediosComunicacionDto);
  	QGServiciosNAForm serviciosNAForm = (QGServiciosNAForm) form;
  	
	JSONObject jsonObject = JSONObject.fromObject(serviciosNAForm.getServiciosNAJSON());
	
	//Convertir la cadena JSON en el DTO
	QGServiciosNADto serviciosNA = (QGServiciosNADto) JSONObject.toBean(jsonObject, QGServiciosNADto.class);
	try{
		
		if(metadatos.get(serviciosNA.getNejecucion().toString()) != null){
			
			QGCGlobalPagingDto =  (QGCGlobalPagingDto) metadatos.get(serviciosNA.getNejecucion().toString());
		} else{            
		    // obtenemos el resultado de la llamada al servicio
			QGCGlobalPagingDto = this.getServiciosNAServicio().buscadorServiciosNA(serviciosNA.getPgnTx());
			metadatos.put(serviciosNA.getNejecucion().toString(), QGCGlobalPagingDto);
		}

	}catch (Exception ex) {
        tratarMensajesExcepcion(response, ex);
    }

    if (QGCGlobalPagingDto != null) {
    	// enviamos los datos al js
    	escribirRespuestaPagingJson(QGCGlobalPagingDto, response);
    }
    logger.info("Salimos del método: **** --siguiente-- ****");
}
    public void gestionar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	// casting del formulario
    	QGServiciosNAForm serviciosNAForm = (QGServiciosNAForm) form;
    	
    	 //Seteamos los datos del usuario
        //setDatosUsuario(request, listaMediosComunicacionDto);
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(serviciosNAForm.getServiciosNAJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGServiciosNADto serviciosNA = (QGServiciosNADto) JSONObject.toBean(jsonObject, QGServiciosNADto.class);
    	
    	QGCGlobalDto QGCGlobalDto = new  QGCGlobalDto();
    	
		String operacionDetalle = "";
		String resultadoNav = "";
		if (serviciosNA.getAccion().equals("A")) {
			operacionDetalle = QGConstantes.MONIT_AUT_NA_ALTA;
			resultadoNav = "No se ha dado de alta el servicio NA.";
		} else if (serviciosNA.getAccion().equals("B")) {
			operacionDetalle = QGConstantes.MONIT_AUT_NA_BAJA;
			resultadoNav = "No se ha podido eliminar el servicio NA.";
		}else if (serviciosNA.getAccion().equals("M")) {
			operacionDetalle = QGConstantes.MONIT_AUT_NA_MODI;
			resultadoNav = "No se ha podido modificar el servicio NA.";
		}
        
    	try{
    		QGCGlobalDto = this.getServiciosNAServicio().gestionarServiciosNA(serviciosNA);
    		List listaMensajes = new ArrayList();
    		List listaDatos = new ArrayList();
    		// Si todo ha ido bien mostramos un mensaje de guardado en la
			// pantalla
			listaMensajes.add("La operaci&oacute;n se ha realizado correctamente");
			QGCGlobalDto.setlistaMensajes(listaMensajes);
			QGCGlobalDto.setlistaDatos(listaDatos);
			
			
			if (serviciosNA.getAccion().equals("A") || serviciosNA.getAccion().equals("B")|| serviciosNA.getAccion().equals("M")) {
				String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
				if (QGCGlobalDto != null) {
					resultadoNav = serviciosNA.getCodigo();
					resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
				}
				this.getAuditorServicio().altaAuditoria(operacionDetalle, resultado, resultadoNav);
			}
    	}catch (Exception e) {
			if (serviciosNA.getAccion().equals("A") || serviciosNA.getAccion().equals("B")|| serviciosNA.getAccion().equals("M")) {
				this.getAuditorServicio().altaAuditoria(operacionDetalle,
						QGConstantes.DS_RESULTADO_OPERA_ERROR, e.getMessage());
			}
    		tratarMensajesExcepcion(response, e);
		}
    	
    	if (serviciosNA != null) {
        	// enviamos los datos al js
    		escribirRespuestaJson(QGCGlobalDto, response);
        }    
    }

    public void obtenerUsuario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Buscando usuario logado");
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();

		try {

			List listaDatos = new ArrayList();
			listaDatos.add(this.getUsuarioLogado().getUsername());
			qGCGlobalDto.setlistaDatos(listaDatos);

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			tratarMensajesExcepcion(response, ex);
		}
		if (qGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(qGCGlobalDto, response);
		}

	}
	
	public void exportarPDF(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	//obtenemos los datos en el dto
		  QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();

        try {

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getServiciosNAServicio().cargarComboServiciosNA(null);
        	
        	if (QGCGlobalDto != null) {
            	
        		ByteArrayOutputStream baos = generarInformeServicio.generarInformeServiciosNAPDF(QGCGlobalDto.getlistaDatos());
        		
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

	public QGServiciosNAServicio getServiciosNAServicio() {
		return serviciosNAServicio;
	}

	public void setServiciosNAServicio(QGServiciosNAServicio serviciosNAServicio) {
		this.serviciosNAServicio = serviciosNAServicio;
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
