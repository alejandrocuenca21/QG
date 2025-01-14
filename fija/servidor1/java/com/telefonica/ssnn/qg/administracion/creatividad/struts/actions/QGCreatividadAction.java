package com.telefonica.ssnn.qg.administracion.creatividad.struts.actions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.security.context.SecurityContextHolder;

import com.telefonica.ssnn.qg.administracion.creatividad.dto.QGCreatividadDto;
import com.telefonica.ssnn.qg.administracion.creatividad.servicio.interfaz.QGCreatividadServicio;
import com.telefonica.ssnn.qg.administracion.creatividad.struts.forms.QGCreatividadForm;
import com.telefonica.ssnn.qg.auditoria.servicio.interfaz.QGAuditorServicio;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.struts.QGBaseAction;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;


/**
 * Action de Creatividad.
 * 
 * @author rgsimon
 *
 * @struts.action 
 *  name="QGCreatividadForm" 
 *  path="/Creatividad" 
 *  input="/vCreatividad.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="buscar, baja, guardar, obtenerUsuario, obtenerSegmentos, obtenerDerechos, exportarPDF"  
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vCreatividad.do"
 */


public class QGCreatividadAction extends QGBaseAction{

	/**
	 * logger
	 */
	private static final Logger logger = Logger
			.getLogger(QGCreatividadAction.class);
	
	private QGCreatividadServicio creatividadServicio;
	
	private QGGenerarInformeServicio generarInformeServicio;
	
	private QGAuditorServicio auditorServicio;
	
	/**
	 * Accion que se ejecuta instantaneamente al lanzar el action
	 */
	public ActionForward doExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Lanzando accion por defecto");
		
		QGUsuario usuarioLogado = (QGUsuario) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		QGCreatividadForm creatividadForm = (QGCreatividadForm) form;
		
		creatividadForm.setUsuarioLogado(usuarioLogado.getUsername());

		return mapping.findForward(SUCCESS);
	}
	
	/**
	 * Metodo que realiza la busqueda de Creatividad.	 
	 */
	public void buscar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("Realizando busqueda de creatividad para encartes.");
		 
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		
		try {
			// obtenemos el resultado de la llamada al servicio
			qGCGlobalDto = this.getCreatividadServicio().consultarCreatividadEncartes();
			
			List list = qGCGlobalDto.getlistaDatos();
			List listaSalida = new ArrayList();
			for (int i = 0; i < list.size(); i++) {

				String codigo = ((QGCreatividadDto)list.get(i)).getCodSegmento().trim();

				if(codigo.equals(QGConstantes.CODIGO_SEGMENTO_GP_INICIAL)){
						((QGCreatividadDto)list.get(i)).setCodSegmento(QGConstantes.CODIGO_SEGMENTO_GP_SUSTITUTO);
				}							
				listaSalida.add(list.get(i));	
			}				
			qGCGlobalDto.setlistaDatos(listaSalida);
			

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			tratarMensajesExcepcion(response, ex);
		}
		if (qGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(qGCGlobalDto, response);
		}
		
	}
	
	/**
	 * Guarda una creatividad
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void guardar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("Guardando una creatividad");
		// casting del formulario
		QGCreatividadForm creatividadForm = (QGCreatividadForm) form;

		// Obtenemos la cadena JSON
		JSONObject jsonObject = JSONObject.fromObject(creatividadForm
				.getCreatividadJSON());

		// Convertir la cadena JSON en el DTO
		QGCreatividadDto creatividadDto = (QGCreatividadDto) JSONObject
				.toBean(jsonObject, QGCreatividadDto.class);

		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();

		
			
			
			String operacionDetalle = "";
			String resultadoNav = "";
			//Vemos si es alta o modificacion y cargamos los valores correspondientes
			if(creatividadDto.getModificacion() != null && creatividadDto.getModificacion().equals(Boolean.TRUE)){
				logger.info("Es una modificacion");
				creatividadDto.setAccion(QGConstantes.CODIGO_MODIFICAR);
				creatividadDto.setUsuarioMod(this.getUsuarioLogado().getUsername());
				creatividadDto.setFecMod(QGUtilidadesFechasUtils.fromDateToString(new Date(),QGUtilidadesFechasUtils.FORMATO_FECHA_3));
				operacionDetalle = QGConstantes.MONIT_CE_MODI;
				resultadoNav ="No se ha podido modificar la creatividad.";
				
			}else if(creatividadDto.getModificacion() != null && creatividadDto.getModificacion().equals(Boolean.FALSE)){
				logger.info("Es un alta");
				creatividadDto.setAccion(QGConstantes.CODIGO_ALTA);
				creatividadDto.setUsuarioAlta(this.getUsuarioLogado().getUsername());
				creatividadDto.setFecAlta(QGUtilidadesFechasUtils.fromDateToString(new Date(),QGUtilidadesFechasUtils.FORMATO_FECHA_3));
				operacionDetalle = QGConstantes.MONIT_CE_ALTA;
				resultadoNav ="No se ha dado de alta la creatividad.";
				
			}
			
		try {
			//Cambiamos el codigo de segmento en caso de ser GP/RES
			String codigo = creatividadDto.getCodSegmento().trim();

			if(codigo.equals(QGConstantes.CODIGO_SEGMENTO_GP_SUSTITUTO)){
				creatividadDto.setCodSegmento(QGConstantes.CODIGO_SEGMENTO_GP_INICIAL);
			}	
			
			// Llamada al servicio para dar de baja la segmentacion elegida
			this.getCreatividadServicio().modificarCreatividad(creatividadDto);
			List listaMensajes = new ArrayList();
			listaMensajes
					.add("El registro ha sido guardado correctamente");
			qGCGlobalDto.setlistaMensajes(listaMensajes);
			
    		String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
        	if (qGCGlobalDto != null) {
    			resultadoNav = "";
        		resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
            }
        	this.getAuditorServicio().altaAuditoria(operacionDetalle,resultado, resultadoNav);
		} catch (Exception e) {
			this.getAuditorServicio().altaAuditoria(operacionDetalle,
					QGConstantes.DS_RESULTADO_OPERA_ERROR, e.getMessage());
			logger.error(e.getMessage());
			tratarMensajesExcepcion(response, e);
		}

		if (qGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(qGCGlobalDto, response);
		}			
	}
	
	
	/**
	 * Da de baja la creatividad
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void baja(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("Dando de baja una creatividad");
		// casting del formulario
		QGCreatividadForm creatividadForm = (QGCreatividadForm) form;

		// Obtenemos la cadena JSON
		JSONObject jsonObject = JSONObject.fromObject(creatividadForm
				.getCreatividadJSON());

		// Convertir la cadena JSON en el DTO
		QGCreatividadDto creatividadDto = (QGCreatividadDto) JSONObject
				.toBean(jsonObject, QGCreatividadDto.class);

		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();

		try {
			creatividadDto.setAccion(QGConstantes.CODIGO_BAJA);
			
			// Llamada al servicio para dar de baja la segmentacion elegida
			this.getCreatividadServicio().modificarCreatividad(creatividadDto);
			List listaMensajes = new ArrayList();
			listaMensajes
					.add("El registro ha sido dado de baja correctamente");
			qGCGlobalDto.setlistaMensajes(listaMensajes);
			
    		String resultadoNav ="No se ha podido dar de baja la creatividad.";
    		String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
        	if (qGCGlobalDto != null) {
    			resultadoNav = "";
        		resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
            }
        	this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_CE_BAJA,resultado, resultadoNav);
		} catch (Exception e) {
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_CE_BAJA,
					QGConstantes.DS_RESULTADO_OPERA_ERROR, e.getMessage());
			logger.error(e.getMessage());
			tratarMensajesExcepcion(response, e);
		}

		if (qGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(qGCGlobalDto, response);
		}			
	}
	/**
	 * Genera un informe de las creatividades
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void exportarPDF(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	    	//obtenemos los datos en el dto
	        QGCGlobalDto qGCGlobalDto = new QGCGlobalDto ();
	        
	        try {

	            // obtenemos el resultado de la llamada al servicio
	        	qGCGlobalDto = this.getCreatividadServicio().consultarCreatividadEncartes();

	        	if (qGCGlobalDto != null) {

	        		ByteArrayOutputStream baos = generarInformeServicio.generarInformeCreatividadNAPDF(qGCGlobalDto.getlistaDatos());
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
	
	/**
	 * Metodo que obtiene los segmentos para el combo de segmentos.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void obtenerSegmentos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("Cargando el combo de codigos de segmento");
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		try {
			qGCGlobalDto = this.getCreatividadServicio().obtenerSegmentos();
			
		} catch (Exception ex) {
			tratarMensajesExcepcion(response, ex);
		}
		if (qGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(qGCGlobalDto, response);
		}			
	}
	
	public void obtenerDerechos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Cargando el combo de codigos de derechos");
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		
		// casting del formulario
		QGCreatividadForm creatividadForm = (QGCreatividadForm) form;
		
	
		try {
			qGCGlobalDto = this.getCreatividadServicio().obtenerDerechos(creatividadForm.getLineaNegocio());
			
		} catch (Exception ex) {
			tratarMensajesExcepcion(response, ex);
		}
		if (qGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(qGCGlobalDto, response);
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

	public QGUsuario getUsuarioLogado() {
	    	return (QGUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	 
	public QGCreatividadServicio getCreatividadServicio() {
		return creatividadServicio;
	}

	public void setCreatividadServicio(QGCreatividadServicio creatividadService) {
		this.creatividadServicio = creatividadService;
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
