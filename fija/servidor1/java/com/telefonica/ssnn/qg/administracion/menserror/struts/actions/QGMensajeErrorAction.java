package com.telefonica.ssnn.qg.administracion.menserror.struts.actions;

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

import com.telefonica.ssnn.qg.administracion.menserror.dto.QGMensajeErrorDto;
import com.telefonica.ssnn.qg.administracion.menserror.servicio.interfaz.QGMensajeErrorServicio;
import com.telefonica.ssnn.qg.administracion.menserror.struts.forms.QGMensajeErrorForm;
import com.telefonica.ssnn.qg.auditoria.servicio.interfaz.QGAuditorServicio;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.struts.QGBaseAction;

/**
 * Action de Mensajes de Error.
 * 
 * @author jacastano
 *
 * @struts.action name="QGMensajeErrorForm" path="/MensError"
 *                input="/vMensError.do" scope="request" redirect="true"
 *                validate="false" parameter="buscar, gestionar, exportarPDF,
 *                obtenerUsuario, anterior, siguiente, buscarParaModific"
 *                type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward name="success" path="/vMensError.do"
 */
public class QGMensajeErrorAction extends QGBaseAction {

	private static final Logger logger = Logger.getLogger(QGMensajeErrorAction.class);

	private QGMensajeErrorServicio menserrorServicio;

	private QGGenerarInformeServicio generarInformeServicio;
	
	private QGAuditorServicio auditorServicio;

	private static HashMap metadatos = null;

	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		QGUsuario usuarioLogado = (QGUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		QGMensajeErrorForm menserrorForm = (QGMensajeErrorForm) form;

		menserrorForm.setUsuarioLogado(usuarioLogado.getUsername());

		return mapping.findForward(SUCCESS);
	}

	public void buscar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// casting del formulario
		QGMensajeErrorForm mensErrorForm = (QGMensajeErrorForm) form;

		// Obtenemos la cadena JSON
		JSONObject jsonObject = JSONObject.fromObject(mensErrorForm.getMenserrorJSON());

		// Convertir la cadena JSON en el DTO
		QGMensajeErrorDto mensajeError = (QGMensajeErrorDto) JSONObject.toBean(jsonObject, QGMensajeErrorDto.class);

		QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto();

		metadatos = new HashMap();

		try {
			// obtenemos el resultado de la llamada al servicio
			QGCGlobalPagingDto = this.getMenserrorServicio().buscadorMensajesError(mensajeError);

			QGMensajeErrorAction.setMetadatos(metadatos);
			metadatos.put("0", QGCGlobalPagingDto);
		} catch (Exception ex) {
			tratarMensajesExcepcion(response, ex);
		}

		if (QGCGlobalPagingDto != null) {
			// enviamos los datos al js
			escribirRespuestaPagingJson(QGCGlobalPagingDto, response);
		}
	}

	public void buscarParaModific(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		QGMensajeErrorForm mensErrorForm = (QGMensajeErrorForm) form;

		// Obtenemos la cadena JSON
		JSONObject jsonObject = JSONObject.fromObject(mensErrorForm.getMenserrorJSON());

		// Convertir la cadena JSON en el DTO
		QGMensajeErrorDto mensajeError = (QGMensajeErrorDto) JSONObject.toBean(jsonObject, QGMensajeErrorDto.class);

		QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto();

		try {
			// obtenemos el resultado de la llamada al servicio
			QGCGlobalPagingDto = this.getMenserrorServicio().buscadorMensajesError(mensajeError);
			metadatos.put(mensajeError.getNejecucion().toString(), QGCGlobalPagingDto);
		} catch (Exception ex) {
			tratarMensajesExcepcion(response, ex);
		}

		if (QGCGlobalPagingDto != null) {
			// enviamos los datos al js
			escribirRespuestaPagingJson(QGCGlobalPagingDto, response);
		}
	}

	public void gestionar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// casting del formulario
		QGMensajeErrorForm mensErrorForm = (QGMensajeErrorForm) form;

		// Obtenemos la cadena JSON
		JSONObject jsonObject = JSONObject.fromObject(mensErrorForm.getMenserrorJSON());

		// Convertir la cadena JSON en el DTO
		QGMensajeErrorDto mensajeError = (QGMensajeErrorDto) JSONObject.toBean(jsonObject, QGMensajeErrorDto.class);

		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		String operacionDetalle = "";
		String resultadoNav = "";
		if (mensajeError.getAccion().equals("A")) {
			operacionDetalle = QGConstantes.MONIT_MENS_ERR_ALTA;
			resultadoNav = "No se ha dado de alta el mensaje de error.";
		} else if (mensajeError.getAccion().equals("B")) {
			operacionDetalle = QGConstantes.MONIT_MENS_ERR_BAJA;
			resultadoNav = "No se ha podido eliminar el mensaje de error.";
		} else if (mensajeError.getAccion().equals("M")) {
			operacionDetalle = QGConstantes.MONIT_MENS_ERR_MODI;
			resultadoNav = "No se ha podido modificar el mensaje de error.";
		}

		try {
			QGCGlobalDto = this.getMenserrorServicio().gestionarMensajesError(mensajeError);
			List listaMensajes = new ArrayList();
			List listaDatos = new ArrayList();
			// Si todo ha ido bien mostramos un mensaje de guardado en la
			// pantalla
			listaMensajes.add("La operaci&oacute;n se ha realizado correctamente");
			QGCGlobalDto.setlistaMensajes(listaMensajes);
			QGCGlobalDto.setlistaDatos(listaDatos);

			if (mensajeError.getAccion().equals("A") || mensajeError.getAccion().equals("B")|| mensajeError.getAccion().equals("M")) {
				String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
				if (QGCGlobalDto != null) {
					resultadoNav = mensajeError.getCodigo();
					resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
				}
				this.getAuditorServicio().altaAuditoria(operacionDetalle, resultado, resultadoNav);
			}
		} catch (Exception e) {
			if (mensajeError.getAccion().equals("A") || mensajeError.getAccion().equals("B")|| mensajeError.getAccion().equals("M")) {
				this.getAuditorServicio().altaAuditoria(operacionDetalle,
						QGConstantes.DS_RESULTADO_OPERA_ERROR, e.getMessage());
			}
			tratarMensajesExcepcion(response, e);
		}

		if (mensajeError != null) {
			// enviamos los datos al js
			escribirRespuestaJson(QGCGlobalDto, response);
		}

	}

	public void siguiente(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// casting del formulario
		QGMensajeErrorForm mensErrorForm = (QGMensajeErrorForm) form;

		// Obtenemos la cadena JSON
		JSONObject jsonObject = JSONObject.fromObject(mensErrorForm.getMenserrorJSON());

		// Convertir la cadena JSON en el DTO
		QGMensajeErrorDto mensajeError = (QGMensajeErrorDto) JSONObject.toBean(jsonObject, QGMensajeErrorDto.class);

		QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto();

		try {
			if (metadatos.get(mensajeError.getNejecucion().toString()) != null) {
				QGCGlobalPagingDto = (QGCGlobalPagingDto) metadatos.get(mensajeError.getNejecucion().toString());
			} else {
				// obtenemos el resultado de la llamada al servicio
				QGCGlobalPagingDto = this.getMenserrorServicio().buscadorMensajesError(mensajeError);
				metadatos.put(mensajeError.getNejecucion().toString(), QGCGlobalPagingDto);
			}
		} catch (Exception ex) {
			tratarMensajesExcepcion(response, ex);
		}

		if (QGCGlobalPagingDto != null) {
			// enviamos los datos al js
			escribirRespuestaPagingJson(QGCGlobalPagingDto, response);
		}

	}

	public void anterior(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// casting del formulario
		QGMensajeErrorForm mensErrorForm = (QGMensajeErrorForm) form;

		// Obtenemos la cadena JSON
		JSONObject jsonObject = JSONObject.fromObject(mensErrorForm.getMenserrorJSON());

		// Convertir la cadena JSON en el DTO
		QGMensajeErrorDto mensajeError = (QGMensajeErrorDto) JSONObject.toBean(jsonObject, QGMensajeErrorDto.class);

		QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto();

		try {
			if (metadatos.get(mensajeError.getNejecucion().toString()) != null) {
				QGCGlobalPagingDto = (QGCGlobalPagingDto) metadatos.get(mensajeError.getNejecucion().toString());
			} else {
				// obtenemos el resultado de la llamada al servicio
				QGCGlobalPagingDto = this.getMenserrorServicio().buscadorMensajesError(mensajeError);
				metadatos.put(mensajeError.getNejecucion().toString(), QGCGlobalPagingDto);
			}
		} catch (Exception ex) {
			tratarMensajesExcepcion(response, ex);
		}

		if (QGCGlobalPagingDto != null) {
			// enviamos los datos al js
			escribirRespuestaPagingJson(QGCGlobalPagingDto, response);
		}

	}

	public void exportarPDF(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// casting del formulario
		QGMensajeErrorForm mensErrorForm = (QGMensajeErrorForm) form;

		// Seteamos los datos del usuario
		// setDatosUsuario(request, listaMediosComunicacionDto);

		// Obtenemos la cadena JSON
		JSONObject jsonObject = JSONObject.fromObject(mensErrorForm.getMenserrorJSON());

		// Convertir la cadena JSON en el DTO
		QGMensajeErrorDto mensajeError = (QGMensajeErrorDto) JSONObject.toBean(jsonObject, QGMensajeErrorDto.class);

		QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto();

		try {

			// obtenemos el resultado de la llamada al servicio
			QGCGlobalPagingDto = this.getMenserrorServicio().buscadorMensajesError(mensajeError);

			if (QGCGlobalPagingDto != null) {

				ByteArrayOutputStream baos = generarInformeServicio
						.generarInformeMensajesErrorPDF(QGCGlobalPagingDto.getListaDatos());

				response.setContentType("application/pdf");

				response.addHeader("Content-Disposition", "attachment; filename=\"informe.pdf\"");
				response.setHeader("Content-Transfer-Encoding", "binary");

				response.setContentLength(baos.toByteArray().length);

				response.getOutputStream().write(baos.toByteArray());

				response.getOutputStream().flush();

				response.getOutputStream().close();
			}
		} catch (Exception ex) {
			logger.info(ex);
		}
	}

	public void obtenerUsuario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Buscando usuario logado");
		QGCGlobalPagingDto QGCGlobalPagingDto = new QGCGlobalPagingDto();

		try {

			List listaDatos = new ArrayList();
			listaDatos.add(this.getUsuarioLogado().getUsername());
			QGCGlobalPagingDto.setListaDatos(listaDatos);

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			tratarMensajesExcepcion(response, ex);
		}
		if (QGCGlobalPagingDto != null) {
			// enviamos los datos al js
			escribirRespuestaPagingJson(QGCGlobalPagingDto, response);
		}

	}

	public QGMensajeErrorServicio getMenserrorServicio() {
		return menserrorServicio;
	}

	public void setMenserrorServicio(QGMensajeErrorServicio menserrorServicio) {
		this.menserrorServicio = menserrorServicio;
	}

	public QGGenerarInformeServicio getGenerarInformeServicio() {
		return generarInformeServicio;
	}

	public void setGenerarInformeServicio(QGGenerarInformeServicio generarInformeServicio) {
		this.generarInformeServicio = generarInformeServicio;
	}

	public static HashMap getMetadatos() {
		return metadatos;
	}

	public static void setMetadatos(HashMap metadatos) {
		QGMensajeErrorAction.metadatos = metadatos;
	}
	
	public QGAuditorServicio getAuditorServicio() {
		return auditorServicio;
	}

	public void setAuditorServicio(QGAuditorServicio auditorServicio) {
		this.auditorServicio = auditorServicio;
	}
}
