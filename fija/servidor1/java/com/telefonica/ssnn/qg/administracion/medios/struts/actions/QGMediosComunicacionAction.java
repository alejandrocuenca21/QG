/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.medios.struts.actions;

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

import com.telefonica.ssnn.qg.administracion.medios.dto.QGMediosComunicacionDto;
import com.telefonica.ssnn.qg.administracion.medios.servicio.interfaz.QGMediosServicio;
import com.telefonica.ssnn.qg.administracion.medios.struts.forms.QGMediosComunicacionForm;
import com.telefonica.ssnn.qg.auditoria.servicio.interfaz.QGAuditorServicio;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.struts.QGBaseAction;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * Action de Medios de Comunicacion.
 * 
 * @author cnunezba
 *
 * @struts.action name="QGMediosComunicacionForm" path="/MediosComunicacion"
 *                input="/vMediosComunicacion.do" scope="request"
 *                redirect="true" validate="false" parameter="buscar, modificar,
 *                alta, baja, exportarPDF"
 *                type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward name="success" path="/vMediosComunicacion.do"
 */
public class QGMediosComunicacionAction extends QGBaseAction {

	private static final Logger logger = Logger.getLogger(QGMediosComunicacionAction.class);

	private QGMediosServicio mediosServicio;

	private QGGenerarInformeServicio generarInformeServicio;

	private QGAuditorServicio auditorServicio;

	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		QGUsuario usuarioLogado = (QGUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		QGMediosComunicacionForm comunicacionForm = (QGMediosComunicacionForm) form;

		comunicacionForm.setUsuarioLogado(usuarioLogado.getUsername());

		return mapping.findForward(SUCCESS);
	}

	public void buscar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// obtenemos los datos en el dto
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		// Seteamos los datos del usuario
		// setDatosUsuario(request, listaMediosComunicacionDto);

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

	public void modificar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// casting del formulario
		QGMediosComunicacionForm mediosComunicacionForm = (QGMediosComunicacionForm) form;

		// Seteamos los datos del usuario
		// setDatosUsuario(request, listaMediosComunicacionDto);

		// Obtenemos la cadena JSON
		JSONObject jsonObject = JSONObject.fromObject(mediosComunicacionForm.getMedioComunicacionJSON());

		// Convertir la cadena JSON en el DTO
		QGMediosComunicacionDto mediosComunicacionDto = (QGMediosComunicacionDto) JSONObject.toBean(jsonObject,
				QGMediosComunicacionDto.class);

		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		try {
			QGCGlobalDto = this.getMediosServicio().modificarMediosComunicacion(mediosComunicacionDto);
			List listaMensajes = new ArrayList();
			// Si todo ha ido bien mostramos un mensaje de guardado en la
			// pantalla
			listaMensajes.add("La operaci&oacute;n se ha realizado correctamente");
			QGCGlobalDto.setlistaMensajes(listaMensajes);

			String resultadoNav = "No se ha podido modificar el medio de comunicación.";
			String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
			if (mediosComunicacionDto != null) {
				resultadoNav = mediosComunicacionDto.getCodigo();
				resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
			}
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_MEDIO_MODIF, resultado, resultadoNav);
		} catch (Exception e) {
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_MEDIO_MODIF,
					QGConstantes.DS_RESULTADO_OPERA_ERROR, e.getMessage());
			tratarMensajesExcepcion(response, e);
		}

		if (mediosComunicacionDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(QGCGlobalDto, response);
		}

	}

	public void alta(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// casting del formulario
		QGMediosComunicacionForm mediosComunicacionForm = (QGMediosComunicacionForm) form;

		// Seteamos los datos del usuario
		// setDatosUsuario(request, listaMediosComunicacionDto);

		// Obtenemos la cadena JSON
		JSONObject jsonObject = JSONObject.fromObject(mediosComunicacionForm.getMedioComunicacionJSON());

		// Convertir la cadena JSON en el DTO
		QGMediosComunicacionDto mediosComunicacionDto = (QGMediosComunicacionDto) JSONObject.toBean(jsonObject,
				QGMediosComunicacionDto.class);

		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		try {
			QGCGlobalDto = this.getMediosServicio().altaMedioComunicacion(mediosComunicacionDto);
			List listaMensajes = new ArrayList();
			// Si todo ha ido bien mostramos un mensaje de guardado en la
			// pantalla
			listaMensajes.add("La operaci&oacute;n se ha realizado correctamente");
			QGCGlobalDto.setlistaMensajes(listaMensajes);

			String resultadoNav = "No se ha dado de alta el medio de comunicación.";
			String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
			if (mediosComunicacionDto != null) {
				resultadoNav = mediosComunicacionDto.getCodigo();
				resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
			}
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_MEDIO_COMUN, resultado, resultadoNav);

		} catch (Exception e) {
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_MEDIO_COMUN,
					QGConstantes.DS_RESULTADO_OPERA_ERROR, e.getMessage());
			tratarMensajesExcepcion(response, e);
		}

		if (mediosComunicacionDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(QGCGlobalDto, response);
		}

	}

	public void baja(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// casting del formulario
		QGMediosComunicacionForm mediosComunicacionForm = (QGMediosComunicacionForm) form;

		// Seteamos los datos del usuario
		// setDatosUsuario(request, listaMediosComunicacionDto);

		// Obtenemos la cadena JSON
		JSONObject jsonObject = JSONObject.fromObject(mediosComunicacionForm.getMedioComunicacionJSON());

		// Convertir la cadena JSON en el DTO
		QGMediosComunicacionDto mediosComunicacionDto = (QGMediosComunicacionDto) JSONObject.toBean(jsonObject,
				QGMediosComunicacionDto.class);

		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		try {
			QGCGlobalDto = this.getMediosServicio().bajaMedioComunicacion(mediosComunicacionDto);

		} catch (Exception e) {
			tratarMensajesExcepcion(response, e);
		}

		if (mediosComunicacionDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(QGCGlobalDto, response);
		}

	}

	public void exportarPDF(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// obtenemos los datos en el dto
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		// Seteamos los datos del usuario
		// setDatosUsuario(request, listaMediosComunicacionDto);

		try {

			// obtenemos el resultado de la llamada al servicio
			QGCGlobalDto = this.getMediosServicio().obtenerListaMediosComunicacion();

			if (QGCGlobalDto != null) {

				ByteArrayOutputStream baos = generarInformeServicio
						.generarInformeMediosComunicacionPDF(QGCGlobalDto.getlistaDatos());

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

	public QGMediosServicio getMediosServicio() {
		return mediosServicio;
	}

	public void setMediosServicio(QGMediosServicio mediosServicio) {
		this.mediosServicio = mediosServicio;
	}

	public QGGenerarInformeServicio getGenerarInformeServicio() {
		return generarInformeServicio;
	}

	public void setGenerarInformeServicio(QGGenerarInformeServicio generarInformeServicio) {
		this.generarInformeServicio = generarInformeServicio;
	}

	public QGAuditorServicio getAuditorServicio() {
		return auditorServicio;
	}

	public void setAuditorServicio(QGAuditorServicio auditorServicio) {
		this.auditorServicio = auditorServicio;
	}

}
