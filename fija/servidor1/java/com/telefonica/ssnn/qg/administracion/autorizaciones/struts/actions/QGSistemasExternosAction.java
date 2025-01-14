package com.telefonica.ssnn.qg.administracion.autorizaciones.struts.actions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.security.context.SecurityContextHolder;

import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGSistemasExternosBusquedaDto;
import com.telefonica.ssnn.qg.administracion.autorizaciones.dto.QGSistemasExternosDto;
import com.telefonica.ssnn.qg.administracion.autorizaciones.servicio.interfaz.QGSistemasExternosServicio;
import com.telefonica.ssnn.qg.administracion.autorizaciones.struts.forms.QGSistemasExternosForm;
import com.telefonica.ssnn.qg.auditoria.servicio.interfaz.QGAuditorServicio;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.struts.QGBaseAction;

/**
 * Action de Sistemas Externos.
 * 
 * @author jacastano  
 *
 * @struts.action 
 *  name="QGSistemasExternosForm" 
 *  path="/SistemasExternos" 
 *  input="/vSistemasExternos.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="buscarLinNegocio, buscar, gestionar, exportarPDF, obtenerUsuario, anterior, siguiente,buscarParaModific"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vSistemasExternos.do"
 */
public class QGSistemasExternosAction extends QGBaseAction {

	private static final Logger logger = Logger.getLogger(QGAutorizacionesAction.class);
	
	private QGSistemasExternosServicio sistemasExternosServicio;
	
	private QGGenerarInformeServicio generarInformeServicio;
	
	private QGAuditorServicio auditorServicio;
	
	private static HashMap lineaNegocio  = null;
	

	private static HashMap metadatos = null;
	
    public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	QGUsuario usuarioLogado = (QGUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	QGSistemasExternosForm sistemasExternosForm = (QGSistemasExternosForm) form;
    	
    	sistemasExternosForm.setUsuarioLogado(usuarioLogado.getUsername());
    	
        return mapping.findForward(SUCCESS);
    }
    
    
 
    public void buscarLinNegocio(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();
        
        lineaNegocio = new HashMap();
        
        try {

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getSistemasExternosServicio().buscadorLineasNegocio();
        	for (Iterator iterator = QGCGlobalDto.getlistaDatos().iterator(); iterator.hasNext();) {
				QGSistemasExternosDto elemento = (QGSistemasExternosDto) iterator.next();
				
				lineaNegocio.put(elemento.getNombre(), elemento.getDescripcion());  
				
			}
 
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
    	logger.info("Entramos en el método: **** --buscar-- ****");
    	// casting del formulario  se le pasarian los datos de (indPgnIn y pgnTx).
    	QGSistemasExternosForm sistemasExternosForm = (QGSistemasExternosForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(sistemasExternosForm.getBusquedaJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGSistemasExternosBusquedaDto busqueda = (QGSistemasExternosBusquedaDto) JSONObject.toBean(jsonObject, QGSistemasExternosBusquedaDto.class);
    	
    	
    	//obtenemos los datos en el dto para la paginacion.
    	QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto ();
       
        
        try {

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalPagingDto = this.getSistemasExternosServicio().buscadorSistemasExternos(lineaNegocio,busqueda);
        	
        	metadatos = new HashMap();
        	
			metadatos.put("0", QGCGlobalPagingDto);
			

        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalPagingDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaPagingJson(QGCGlobalPagingDto, response);
        }
        logger.info("Salimos del método: **** --buscar-- ****");
    }    

    public void gestionar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	logger.info("Entramos en el método: **** --gestionar-- ****");
    	// casting del formulario
    	QGSistemasExternosForm sistemasExternosForm = (QGSistemasExternosForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(sistemasExternosForm.getSistemasExternosJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGSistemasExternosDto sistemasExternos = (QGSistemasExternosDto) JSONObject.toBean(jsonObject, QGSistemasExternosDto.class);
    	
    	QGCGlobalDto QGCGlobalDto = new  QGCGlobalDto();
    	
		String operacionDetalle = "";
		String resultadoNav = "";
		if (sistemasExternos.getAccion().equals("A")) {
			operacionDetalle = QGConstantes.MONIT_AUT_SE_ALTA;
			resultadoNav = "No se ha dado de alta la autorización.";
		} else if (sistemasExternos.getAccion().equals("B")) {
			operacionDetalle = QGConstantes.MONIT_AUT_SE_BAJA;
			resultadoNav = "No se ha podido eliminar la autorización.";
		} else if (sistemasExternos.getAccion().equals("M")) {
			operacionDetalle = QGConstantes.MONIT_AUT_SE_MODI;
			resultadoNav = "No se ha podido modificar la autorización.";
		}
        
    	try{
    		QGCGlobalDto = this.getSistemasExternosServicio().gestionarSistemasExternos(sistemasExternos);
    		List listaMensajes = new ArrayList();
    		List listaDatos = new ArrayList();
    		// Si todo ha ido bien mostramos un mensaje de guardado en la
			// pantalla
			listaMensajes
					.add("La operaci&oacute;n se ha realizado correctamente");
			QGCGlobalDto.setlistaMensajes(listaMensajes);
			QGCGlobalDto.setlistaDatos(listaDatos);
			

			if (sistemasExternos.getAccion().equals("A") || sistemasExternos.getAccion().equals("B")|| sistemasExternos.getAccion().equals("M")) {
				String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
				if (sistemasExternos != null) {
					resultadoNav = "";
					resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
				}
				this.getAuditorServicio().altaAuditoria(operacionDetalle, resultado, resultadoNav);
			}
    	}catch (Exception e) {
			if (sistemasExternos.getAccion().equals("A") || sistemasExternos.getAccion().equals("B")|| sistemasExternos.getAccion().equals("M")) {
				this.getAuditorServicio().altaAuditoria(operacionDetalle,
						QGConstantes.DS_RESULTADO_OPERA_ERROR, e.getMessage());
			}
    		tratarMensajesExcepcion(response, e);
		}
    	
    	if (sistemasExternos != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
    	logger.info("Salimos del método: **** --gestionar-- ****");
    }
    
    public void buscarParaModific(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	logger.info("Entramos en el método: **** --buscarParaModific-- ****");
    	// casting del formulario  se le pasarian los datos de (indPgnIn y pgnTx).
    	QGSistemasExternosForm sistemasExternosForm = (QGSistemasExternosForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(sistemasExternosForm.getBusquedaJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGSistemasExternosBusquedaDto busqueda = (QGSistemasExternosBusquedaDto) JSONObject.toBean(jsonObject, QGSistemasExternosBusquedaDto.class);

		
		//obtenemos los datos en el dto
    	QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto ();
		
		
		try{
			QGCGlobalPagingDto = this.getSistemasExternosServicio().buscadorSistemasExternos(lineaNegocio,busqueda);
		
			metadatos.put(busqueda.getNejecucion().toString(), QGCGlobalPagingDto);

	
		}catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalPagingDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaPagingJson(QGCGlobalPagingDto, response);
        }
        logger.info("Salimos del método: **** --buscarParaModific-- ****");
    	
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
		logger.info("Entramos en el método: **** --exportarPDF-- ****");
    	//obtenemos los datos en el dto 
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();
        try {

            // obtenemos el resultado de la llamada al servicio.
        	QGCGlobalDto = this.getSistemasExternosServicio().cargarComboSistemaExternos(null); 

        	if (QGCGlobalDto != null) {
            	
        		ByteArrayOutputStream baos = generarInformeServicio.generarInformeSistemasExternosPDF(QGCGlobalDto.getlistaDatos());
        		
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
        logger.info("Salimos del método: **** --exportarPDF-- ****");
	}	

	public void siguiente(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws Exception {
		logger.info("Entramos en el método: **** --siguiente-- ****");
    	// casting del formulario  se le pasarian los datos de (indPgnIn y pgnTx).
    	QGSistemasExternosForm sistemasExternosForm = (QGSistemasExternosForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(sistemasExternosForm.getBusquedaJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGSistemasExternosBusquedaDto busqueda = (QGSistemasExternosBusquedaDto) JSONObject.toBean(jsonObject, QGSistemasExternosBusquedaDto.class);

		
		//obtenemos los datos en el dto
    	QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto ();
		
		
		try{
			
			if(metadatos.get(busqueda.getNejecucion().toString()) != null){
				
				QGCGlobalPagingDto =  (QGCGlobalPagingDto) metadatos.get(busqueda.getNejecucion().toString());
			} else{            
			    // obtenemos el resultado de la llamada al servicio
				QGCGlobalPagingDto = this.getSistemasExternosServicio().buscadorSistemasExternos(lineaNegocio,busqueda);
				metadatos.put(busqueda.getNejecucion().toString(), QGCGlobalPagingDto);
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
	
    public void anterior(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    		throws Exception {
    	logger.info("Entramos en el método: **** --anterior-- ****");
    	// casting del formulario  se le pasarian los datos de (indPgnIn y pgnTx).
    	QGSistemasExternosForm sistemasExternosForm = (QGSistemasExternosForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(sistemasExternosForm.getBusquedaJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGSistemasExternosBusquedaDto busqueda = (QGSistemasExternosBusquedaDto) JSONObject.toBean(jsonObject, QGSistemasExternosBusquedaDto.class);

		
		//obtenemos los datos en el dto
    	QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto ();
    	
    	try{
    	  if(metadatos.get(busqueda.getNejecucion().toString()) != null){
    			
    			QGCGlobalPagingDto =  (QGCGlobalPagingDto) metadatos.get(busqueda.getNejecucion().toString());
    		} else{            
    			// obtenemos el resultado de la llamada al servicio
				QGCGlobalPagingDto = this.getSistemasExternosServicio().buscadorSistemasExternos(lineaNegocio,busqueda);
    			metadatos.put(busqueda.getNejecucion().toString(), QGCGlobalPagingDto);
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
	
	public QGSistemasExternosServicio getSistemasExternosServicio() {
		return sistemasExternosServicio;
	}

	public void setSistemasExternosServicio(
			QGSistemasExternosServicio sistemasExternosServicio) {
		this.sistemasExternosServicio = sistemasExternosServicio;
	}

	public QGGenerarInformeServicio getGenerarInformeServicio() {
		return generarInformeServicio;
	}

	public void setGenerarInformeServicio(
			QGGenerarInformeServicio generarInformeServicio) {
		this.generarInformeServicio = generarInformeServicio;
	}

	public static HashMap getLineaNegocio() {
		return lineaNegocio;
	}

	public static void setLineaNegocio(HashMap lineaNegocio) {
		QGSistemasExternosAction.lineaNegocio = lineaNegocio;
	}
	
    /**
	 * @return the metadatos
	 */
	public static HashMap getMetadatos() {
		return QGSistemasExternosAction.metadatos;
	}

	/**
	 * @param metadatos the metadatos to set
	 */
	public static void setMetadatos(HashMap metadatos) {
		QGSistemasExternosAction.metadatos = metadatos;
	}
	
	public QGAuditorServicio getAuditorServicio() {
		return auditorServicio;
	}

	public void setAuditorServicio(QGAuditorServicio auditorServicio) {
		this.auditorServicio = auditorServicio;
	}

}
