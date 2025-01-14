package com.telefonica.ssnn.qg.administracion.encartes.struts.actions;

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

import com.telefonica.ssnn.qg.administracion.encartes.dto.QGPOEncartesDto;
import com.telefonica.ssnn.qg.administracion.encartes.servicio.interfaz.QGEncartesServicio;
import com.telefonica.ssnn.qg.administracion.encartes.struts.forms.QGEncartesForm;
import com.telefonica.ssnn.qg.auditoria.servicio.interfaz.QGAuditorServicio;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.struts.QGBaseAction;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;

/**
 * Action de Encartes.
 * 
 * @author jacastano
 *
 * @struts.action 
 *  name="QGEncartesForm" 
 *  path="/POEncartes" 
 *  input="/vPOEncartes.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="buscar, obtenerHistorico, baja, obtenerSegmentos, obtenerDerechos, obtenerUsuario, guardar, exportarPDF"  
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vPOEncartes.do"
 */

public class QGEncartesAction extends QGBaseAction {

	/**
	 * servicio utilizado.
	 */
	private QGEncartesServicio encartesServicio;

	private QGGenerarInformeServicio generarInformeServicio;
	
	private QGAuditorServicio auditorServicio;
	/**
	 * logger
	 */
	private static final Logger logger = Logger
			.getLogger(QGEncartesAction.class);

	/**
	 * Accion que se ejecuta instantaneamente al lanzar el action
	 */
	public ActionForward doExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Lanzando accion por defecto");
		QGUsuario usuarioLogado = (QGUsuario) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		QGEncartesForm encartesForm = (QGEncartesForm) form;

		encartesForm.setUsuarioLogado(usuarioLogado.getUsername());
		return mapping.findForward(SUCCESS);
	}

	/**
	 * Metodo que realiza la busqueda de POEncartes.
	 */
	public void buscar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Realizando busqueda de Publico Objetivo para encartes.");

		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();

		// QGEncartesForm encartesForm = (QGEncartesForm) form;
		try {
			// obtenemos el resultado de la llamada al servicio
			qGCGlobalDto = this.getEncartesServicio().consultarPoEncartes();
			
			List list = qGCGlobalDto.getlistaDatos();
			List listaSalida = new ArrayList();
			for (int i = 0; i < list.size(); i++) {

				String codigo = ((QGPOEncartesDto)list.get(i)).getCodSegmento().trim();

				if(codigo.equals(QGConstantes.CODIGO_SEGMENTO_GP_INICIAL)){
						((QGPOEncartesDto)list.get(i)).setCodSegmento(QGConstantes.CODIGO_SEGMENTO_GP_SUSTITUTO);
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
	 * Método que obtiene el histórico segun una linea de negocio
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void obtenerHistorico(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Obteniendo histórico");

		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		QGEncartesForm encartesForm = (QGEncartesForm) form;
		try {

			JSONObject jsonObject = JSONObject.fromObject(encartesForm
					.getEncartesJSON());

			// Convertir la cadena JSON en el DTO
			QGPOEncartesDto encartesDto = (QGPOEncartesDto) JSONObject.toBean(
					jsonObject, QGPOEncartesDto.class);

			// obtenemos el resultado de la llamada al servicio, no hay busqueda
			// por criterio
			qGCGlobalDto = this.getEncartesServicio()
					.consultarHistoricoPoEncartes(encartesDto);

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
	 * Metodo que obtiene los segmentos para el combo de segmentos.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void obtenerSegmentos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("Realizando busqueda de Publico Objetivo para encartes.");

		logger.info("Cargando el combo de codigos de segmento");
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		try {
			qGCGlobalDto = this.getEncartesServicio().obtenerSegmentos();

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

		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		QGEncartesForm encartesForm = (QGEncartesForm) form;
		try {
			qGCGlobalDto = this.getEncartesServicio().obtenerDerechos(
					encartesForm.getEncartesJSON());
		} catch (Exception ex) {
			tratarMensajesExcepcion(response, ex);
		}
		if (qGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(qGCGlobalDto, response);
		}
	}

	public void guardar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Guardando");
		// casting del formulario
		QGEncartesForm encartesForm = (QGEncartesForm) form;

		// Obtenemos la cadena JSON
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		

			JSONObject jsonObject = JSONObject.fromObject(encartesForm
					.getEncartesJSON());

			// Convertir la cadena JSON en el DTO
			QGPOEncartesDto encartesDto = (QGPOEncartesDto) JSONObject.toBean(
					jsonObject, QGPOEncartesDto.class);

			String operacionDetalle = "";
			String resultadoNav = "";
			if (encartesDto.getModificacion()) {
				encartesDto
						.setUsuarioMod(this.getUsuarioLogado().getUsername());
				encartesDto.setFecMod(QGUtilidadesFechasUtils.fromDateToString(
						new Date(), QGUtilidadesFechasUtils.FORMATO_FECHA_3));
				encartesDto.setAccion(QGConstantes.CODIGO_MODIFICAR);
				operacionDetalle = QGConstantes.MONIT_PO_MODI;
				resultadoNav = "No se ha podido modificar el encarte.";
			} else {
				encartesDto.setUsuarioAlta(this.getUsuarioLogado()
						.getUsername());
				encartesDto.setFecAlta(QGUtilidadesFechasUtils
						.fromDateToString(new Date(),
								QGUtilidadesFechasUtils.FORMATO_FECHA_3));
				encartesDto.setAccion(QGConstantes.CODIGO_ALTA);
				operacionDetalle = QGConstantes.MONIT_PO_ALTA;
				resultadoNav = "No se ha dado de alta el encarte.";
			}
			
		try {
			
			//Cambiamos el codigo de segmento en caso de ser GP/RES
			String codigo = encartesDto.getCodSegmento().trim();

			if(codigo.equals(QGConstantes.CODIGO_SEGMENTO_GP_SUSTITUTO)){
					encartesDto.setCodSegmento(QGConstantes.CODIGO_SEGMENTO_GP_INICIAL);
			}	

			// Llamada al servicio para dar de baja la segmentacion elegida
			this.getEncartesServicio().gestionarPoEncartes(encartesDto);
			List listaMensajes = new ArrayList();
			listaMensajes.add("El registro, ha sido guardado correctamente");
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

	public void baja(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("Dando de baja un po encarte");
		// casting del formulario
		QGEncartesForm encartesForm = (QGEncartesForm) form;

		// Obtenemos la cadena JSON
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		try {

			JSONObject jsonObject = JSONObject.fromObject(encartesForm
					.getEncartesJSON());

			// Convertir la cadena JSON en el DTO
			QGPOEncartesDto encartesDto = (QGPOEncartesDto) JSONObject.toBean(
					jsonObject, QGPOEncartesDto.class);

			encartesDto.setAccion(QGConstantes.CODIGO_BAJA);

			// Llamada al servicio para dar de baja la segmentacion elegida
			this.getEncartesServicio().gestionarPoEncartes(encartesDto);
			List listaMensajes = new ArrayList();
			listaMensajes
					.add("El registro, ha sido dado de baja correctamente");
			qGCGlobalDto.setlistaMensajes(listaMensajes);
			
    		String resultadoNav ="No se ha podido dar de baja el encarte.";
    		String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
        	if (qGCGlobalDto != null) {
    			resultadoNav = "";
        		resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
            }
        	this.getAuditorServicio().altaAuditoria(QGConstantes.PO_BAJA,resultado, resultadoNav);
		} catch (Exception e) {
			this.getAuditorServicio().altaAuditoria(QGConstantes.PO_BAJA,
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
	        	qGCGlobalDto = this.getEncartesServicio().consultarPoEncartes();
	        	if (qGCGlobalDto != null) {
	        		ByteArrayOutputStream baos = generarInformeServicio.generarInformeEncartesNAPDF(qGCGlobalDto.getlistaDatos());
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

	public QGEncartesServicio getEncartesServicio() {
		return encartesServicio;
	}

	public void setEncartesServicio(QGEncartesServicio encartesServicio) {
		this.encartesServicio = encartesServicio;
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
