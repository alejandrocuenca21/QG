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

import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGAutorizacionesBusquedaDto;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGAutorizacionesDto;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGServiciosNADto;
import com.telefonica.ssnn.qg.administracion.autorizaciones.servicio.interfaz.QGAutorizacionesServicio;
import com.telefonica.ssnn.qg.administracion.autorizaciones.servicio.interfaz.QGServiciosNAServicio;
import com.telefonica.ssnn.qg.administracion.autorizaciones.servicio.interfaz.QGSistemasExternosServicio;
import com.telefonica.ssnn.qg.administracion.autorizaciones.struts.forms.QGAutorizacionesForm;
import com.telefonica.ssnn.qg.administracion.autorizaciones.struts.forms.QGServiciosNAForm;
import com.telefonica.ssnn.qg.auditoria.servicio.interfaz.QGAuditorServicio;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.struts.QGBaseAction;
/**
 * Action de Autorizaciones.
 * 
 * @author jacastano
 *
 * @struts.action 
 *  name="QGAutorizacionesForm" 
 *  path="/Autorizaciones" 
 *  input="/vAutorizaciones.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="cargarComboSisEx, cargarComboSerNA, buscar, gestionar, exportarPDF, obtenerUsuario, anterior, siguiente, buscarParaModific"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vAutorizaciones.do"
 */
public class QGAutorizacionesAction  extends QGBaseAction{
	
	private static final Logger logger = Logger.getLogger(QGAutorizacionesAction.class);
	
	private QGAutorizacionesServicio autorizacionesServicio;
	private QGSistemasExternosServicio sistemasExternosServicio;
	private QGServiciosNAServicio serviciosNAServicio;
	private QGAuditorServicio auditorServicio;
	
	private QGGenerarInformeServicio generarInformeServicio;
	
	private static HashMap metadatos  = null;
	private static HashMap metadatosHist  = null;
	
    public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	QGUsuario usuarioLogado = (QGUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	QGAutorizacionesForm autorizacionesForm = (QGAutorizacionesForm) form;
    	
    	autorizacionesForm.setUsuarioLogado(usuarioLogado.getUsername());
    	
        return mapping.findForward(SUCCESS);
    }
    
    public void cargarComboSisEx(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();

        try {

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getSistemasExternosServicio().cargarComboSistemaExternos(null); 
                    

        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }

    }

    public void cargarComboSerNA(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
    	QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
     
        //Seteamos los datos del usuario
        //setDatosUsuario(request, listaMediosComunicacionDto);

        
        try {

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getServiciosNAServicio().cargarComboServiciosNA(null);
                    

        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }

    }    
    
    public void buscar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	// casting del formulario
    	QGAutorizacionesForm autorizacionesForm = (QGAutorizacionesForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(autorizacionesForm.getBusquedaJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGAutorizacionesBusquedaDto busqueda = (QGAutorizacionesBusquedaDto) JSONObject.toBean(jsonObject, QGAutorizacionesBusquedaDto.class);

    	//obtenemos los datos en el dto
    	QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto ();
        
    	if (busqueda.getInActuacion().equals("C")){
    		metadatos = new HashMap();
    	}
    	else{
    		metadatosHist = new HashMap();
    	}
    	
        try {

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalPagingDto = this.getAutorizacionesServicio().buscadorAutorizaciones(busqueda);
			
			if (busqueda.getInActuacion().equals("C")){
				QGAutorizacionesAction.setMetadatos(metadatos);
				metadatos.put("0", QGCGlobalPagingDto);
			}
			else{
				QGAutorizacionesAction.setMetadatosHist(metadatosHist);
				metadatosHist.put("0", QGCGlobalPagingDto);
			}

        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalPagingDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaPagingJson(QGCGlobalPagingDto, response);
        }
    } 
	
    public void buscarParaModific(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	// casting del formulario
    	QGAutorizacionesForm autorizacionesForm = (QGAutorizacionesForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(autorizacionesForm.getBusquedaJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGAutorizacionesBusquedaDto busqueda = (QGAutorizacionesBusquedaDto) JSONObject.toBean(jsonObject, QGAutorizacionesBusquedaDto.class);

    	//obtenemos los datos en el dto
    	QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto ();
        
    	try{
			// obtenemos el resultado de la llamada al servicio
			QGCGlobalPagingDto = this.getAutorizacionesServicio().buscadorAutorizaciones(busqueda);
			if (busqueda.getInActuacion().equals("C")){
				metadatos.put(busqueda.getNejecucion().toString(), QGCGlobalPagingDto);	
			}
			else{
				metadatosHist.put(busqueda.getNejecucion().toString(), QGCGlobalPagingDto);	
			}
        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalPagingDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaPagingJson(QGCGlobalPagingDto, response);
        }

    }
        
    public void gestionar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	// casting del formulario
    	QGAutorizacionesForm autorizacionesForm = (QGAutorizacionesForm) form;
    	
    	 //Seteamos los datos del usuario
        //setDatosUsuario(request, listaMediosComunicacionDto);
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(autorizacionesForm.getAutorizacionesJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGAutorizacionesDto autorizaciones = (QGAutorizacionesDto) JSONObject.toBean(jsonObject, QGAutorizacionesDto.class);
    	
    	QGCGlobalDto QGCGlobalDto = new  QGCGlobalDto();
    	
		String operacionDetalle = "";
		String resultadoNav = "";
		if (autorizaciones.getAccion().equals("A")) {
			operacionDetalle = QGConstantes.MONIT_AUT_ALTA;
			resultadoNav = "No se ha dado de alta la autorización.";
		} else if (autorizaciones.getAccion().equals("B")) {
			operacionDetalle = QGConstantes.MONIT_AUT_BAJA;
			resultadoNav = "No se ha podido eliminar la autorización.";
		}else if (autorizaciones.getAccion().equals("M")) {
			operacionDetalle = QGConstantes.MONIT_AUT_MODI;
			resultadoNav = "No se ha podido modificar la autorización.";
		}
        
    	try{
    		QGCGlobalDto = this.getAutorizacionesServicio().gestionarAutorizaciones(autorizaciones);
    		List listaMensajes = new ArrayList();
    		List listaDatos = new ArrayList();
    		// Si todo ha ido bien mostramos un mensaje de guardado en la
			// pantalla
			listaMensajes
					.add("La operaci&oacute;n se ha realizado correctamente");
			QGCGlobalDto.setlistaMensajes(listaMensajes);
			QGCGlobalDto.setlistaDatos(listaDatos);
    		
			if (autorizaciones.getAccion().equals("A") || autorizaciones.getAccion().equals("B")|| autorizaciones.getAccion().equals("M")) {
				String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
				if (QGCGlobalDto != null) {
					resultadoNav = "";
					resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
				}
				this.getAuditorServicio().altaAuditoria(operacionDetalle, resultado, resultadoNav);
			}
    	}catch (Exception e) {
			if (autorizaciones.getAccion().equals("A") || autorizaciones.getAccion().equals("B")|| autorizaciones.getAccion().equals("M")) {
				this.getAuditorServicio().altaAuditoria(operacionDetalle,
						QGConstantes.DS_RESULTADO_OPERA_ERROR, e.getMessage());
			}
    		tratarMensajesExcepcion(response, e);
		}
    	
    	if (QGCGlobalDto != null) {
        	// enviamos los datos al js
    		escribirRespuestaJson(QGCGlobalDto, response);
        }
    }
    
	public void exportarPDF(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	// casting del formulario
    	QGAutorizacionesForm autorizacionesForm = (QGAutorizacionesForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(autorizacionesForm.getBusquedaJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGAutorizacionesBusquedaDto busqueda = (QGAutorizacionesBusquedaDto) JSONObject.toBean(jsonObject, QGAutorizacionesBusquedaDto.class);

    	//obtenemos los datos en el dto
    	QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto ();
        
        try {

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalPagingDto = this.getAutorizacionesServicio().buscadorAutorizaciones(busqueda);
        	
        	if (QGCGlobalPagingDto != null) {
            	
        		ByteArrayOutputStream baos = generarInformeServicio.generarInformeAutorizacionesPDF(QGCGlobalPagingDto.getListaDatos());
        		
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

    public void siguiente(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	// casting del formulario
    	QGAutorizacionesForm autorizacionesForm = (QGAutorizacionesForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(autorizacionesForm.getBusquedaJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGAutorizacionesBusquedaDto busqueda = (QGAutorizacionesBusquedaDto) JSONObject.toBean(jsonObject, QGAutorizacionesBusquedaDto.class);

    	//obtenemos los datos en el dto
    	QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto ();
        
    	try{
    		if (busqueda.getInActuacion().equals("C")){
				if (metadatos.get(busqueda.getNejecucion().toString()) != null){
					QGCGlobalPagingDto =  (QGCGlobalPagingDto) metadatos.get(busqueda.getNejecucion().toString());
				} else{            
					// obtenemos el resultado de la llamada al servicio
					QGCGlobalPagingDto = this.getAutorizacionesServicio().buscadorAutorizaciones(busqueda);
					metadatos.put(busqueda.getNejecucion().toString(), QGCGlobalPagingDto);
				}
    		}
    		else{
    			if (metadatosHist.get(busqueda.getNejecucion().toString()) != null){
					QGCGlobalPagingDto =  (QGCGlobalPagingDto) metadatosHist.get(busqueda.getNejecucion().toString());
				} else{            
					// obtenemos el resultado de la llamada al servicio
					QGCGlobalPagingDto = this.getAutorizacionesServicio().buscadorAutorizaciones(busqueda);
					metadatosHist.put(busqueda.getNejecucion().toString(), QGCGlobalPagingDto);
				}
    		}
    		
        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalPagingDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaPagingJson(QGCGlobalPagingDto, response);
        }

    }
    
    public void anterior(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    		throws Exception {
    	// casting del formulario
    	QGAutorizacionesForm autorizacionesForm = (QGAutorizacionesForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(autorizacionesForm.getBusquedaJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGAutorizacionesBusquedaDto busqueda = (QGAutorizacionesBusquedaDto) JSONObject.toBean(jsonObject, QGAutorizacionesBusquedaDto.class);

    	//obtenemos los datos en el dto
    	QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto ();
        
    	try{
    		if (busqueda.getInActuacion().equals("C")){
				if (metadatos.get(busqueda.getNejecucion().toString()) != null){
					QGCGlobalPagingDto =  (QGCGlobalPagingDto) metadatos.get(busqueda.getNejecucion().toString());
				} else{            
					// obtenemos el resultado de la llamada al servicio
					QGCGlobalPagingDto = this.getAutorizacionesServicio().buscadorAutorizaciones(busqueda);
					metadatos.put(busqueda.getNejecucion().toString(), QGCGlobalPagingDto);
				}
    		}
    		else{
    			if (metadatosHist.get(busqueda.getNejecucion().toString()) != null){
					QGCGlobalPagingDto =  (QGCGlobalPagingDto) metadatosHist.get(busqueda.getNejecucion().toString());
				} else{            
					// obtenemos el resultado de la llamada al servicio
					QGCGlobalPagingDto = this.getAutorizacionesServicio().buscadorAutorizaciones(busqueda);
					metadatosHist.put(busqueda.getNejecucion().toString(), QGCGlobalPagingDto);
				}
    		}
        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalPagingDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaPagingJson(QGCGlobalPagingDto, response);
        }

    }

	
	public QGAutorizacionesServicio getAutorizacionesServicio() {
		return autorizacionesServicio;
	}

	public void setAutorizacionesServicio(
			QGAutorizacionesServicio autorizacionesServicio) {
		this.autorizacionesServicio = autorizacionesServicio;
	}

	public QGSistemasExternosServicio getSistemasExternosServicio() {
		return sistemasExternosServicio;
	}

	public void setSistemasExternosServicio(
			QGSistemasExternosServicio sistemasExternosServicio) {
		this.sistemasExternosServicio = sistemasExternosServicio;
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

	public static HashMap getMetadatos() {
		return metadatos;
	}

	public static void setMetadatos(HashMap metadatos) {
		QGAutorizacionesAction.metadatos = metadatos;
	}

	public static HashMap getMetadatosHist() {
		return metadatosHist;
	}

	public static void setMetadatosHist(HashMap metadatosHist) {
		QGAutorizacionesAction.metadatosHist = metadatosHist;
	}
	
	public QGAuditorServicio getAuditorServicio() {
		return auditorServicio;
	}

	public void setAuditorServicio(QGAuditorServicio auditorServicio) {
		this.auditorServicio = auditorServicio;
	}
	

}
