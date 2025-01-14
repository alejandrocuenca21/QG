/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.struts.actions;

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

import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaPresegmentacionAdminDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaPresegmentacionDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaSegmentacionesPresegDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSalidaSegmentacionesPresegDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSegmentacionesDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.servicio.interfaz.QGSegmentacionesPresegServicio;
import com.telefonica.ssnn.qg.administracion.segmentacion.servicio.interfaz.QGSegmentacionesServicio;
import com.telefonica.ssnn.qg.administracion.segmentacion.struts.forms.QGSegmentacionesPresegForm;
import com.telefonica.ssnn.qg.auditoria.servicio.interfaz.QGAuditorServicio;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.struts.QGBaseAction;
import com.telefonica.ssnn.qg.util.QGCatalogoDto;

/**
 * Action de Presegmentaciones
 * 
 * @struts.action name="QGSegmentacionesPresegForm" path="/Presegmentacion"
 *                input="/vPresegmentacion.do" scope="request" redirect="true"
 *                validate="false" parameter="obtenerUsuario,
 *                cargarCodigosSegmento, cargarPresegmentaciones,
 *                cargarCodigosOfAtencion, cargarCodigosTandem, anterior,
 *                siguiente, exportarExcel, exportarPDF,
 *                cargarDescripcionSubsegmento, cargarDatosAdmin, operarPreseg"
 *                type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward name="success" path="/vSegmentacionesPreseg.do"
 */
public class QGSegmentacionesPresegAction extends QGBaseAction {

	/**
	 * Codigo de segmento de mediana empresa
	 */
	private static String CODIGO_MEDIANA_EMPRESA = "ME";
	/**
	 * Código de segmento pequeña empresa
	 */
	private static String CODIGO_PEQUENIA_EMPRESA = "PE";
	/**
	 * Código de segmento autónomo
	 */
	private static String CODIGO_AUTONOMO = "AU";
	/**
	 * Código de segmento Negocio trafico telefonico
	 */
	private static String CODIGO_RESIDENCIAL_NEGOCIO_PRF = "GP";

	/**
	 * logger
	 */
	private static final Logger logger = Logger.getLogger(QGSegmentacionesPresegAction.class);

	// Atributo para llamar a métodos de Presegmentaciones
	private QGSegmentacionesPresegServicio segmentacionesServicio;
	// Atributo para llamar a métodos de Segmentaciones
	private QGSegmentacionesServicio segmentacionesSer;
	// Atributo para llamar a métodos de GenerarInforme
	private QGGenerarInformeServicio generarInformeServicio;
	// Atributo para llamar a métodos de Auditor
	private QGAuditorServicio auditorServicio;

	private static HashMap metadatos = null;

	/**
	 * Accion que se ejecuta instantaneamente al lanzar el action
	 */
	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Lanzando accion por defecto");
		QGUsuario usuarioLogado = (QGUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		QGSegmentacionesPresegForm segmentacionesPresegForm = (QGSegmentacionesPresegForm) form;

		segmentacionesPresegForm.setUsuarioLogado(usuarioLogado.getUsername());
		return mapping.findForward(SUCCESS);
	}

	/**
	 * Carga las presegmentaciones por pantalla
	 */
	public void cargarPresegmentaciones(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("Buscando segun criterio");
		QGCGlobalPagingDto qGCGlobalPagingDto = new QGCGlobalPagingDto();

		metadatos = new HashMap();

		QGSegmentacionesPresegForm segmentacionesForm = (QGSegmentacionesPresegForm) form;

		JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm.getSegmentacionPresegJSON());

		QGEntradaSegmentacionesPresegDto busquedaSegmentacion = (QGEntradaSegmentacionesPresegDto) JSONObject
				.toBean(jsonObject, QGEntradaSegmentacionesPresegDto.class);

		try {
			// obtenemos el resultado de la llamada al servicio
			qGCGlobalPagingDto = this.getSegmentacionesServicio().obtenerListaPresegmentaciones(busquedaSegmentacion);

			QGSegmentacionesPresegAction.setMetadatos(metadatos);
			metadatos.put("0", qGCGlobalPagingDto);

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			tratarMensajesExcepcion(response, ex);
		}
		if (qGCGlobalPagingDto != null) {
			// enviamos los datos al js
			escribirRespuestaPagingJson(qGCGlobalPagingDto, response);
		}
	}

	public void siguiente(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("Buscando segun criterio");
		QGCGlobalPagingDto qGCGlobalPagingDto = new QGCGlobalPagingDto();

		QGSegmentacionesPresegForm segmentacionesForm = (QGSegmentacionesPresegForm) form;

		JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm.getSegmentacionPresegJSON());

		QGEntradaSegmentacionesPresegDto busquedaSegmentacion = (QGEntradaSegmentacionesPresegDto) JSONObject
				.toBean(jsonObject, QGEntradaSegmentacionesPresegDto.class);

		try {
			if (metadatos.get(busquedaSegmentacion.getNejecucion().toString()) != null) {
				qGCGlobalPagingDto = (QGCGlobalPagingDto) metadatos
						.get(busquedaSegmentacion.getNejecucion().toString());
			} else {
				// obtenemos el resultado de la llamada al servicio
				qGCGlobalPagingDto = this.getSegmentacionesServicio()
						.obtenerListaPresegmentaciones(busquedaSegmentacion);
				metadatos.put(busquedaSegmentacion.getNejecucion().toString(), qGCGlobalPagingDto);
			}
		} catch (Exception ex) {
			tratarMensajesExcepcion(response, ex);
		}

		if (qGCGlobalPagingDto != null) {
			// enviamos los datos al js
			escribirRespuestaPagingJson(qGCGlobalPagingDto, response);
		}
	}

	public void anterior(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Buscando segun criterio");
		QGCGlobalPagingDto qGCGlobalPagingDto = new QGCGlobalPagingDto();

		QGSegmentacionesPresegForm segmentacionesForm = (QGSegmentacionesPresegForm) form;

		JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm.getSegmentacionPresegJSON());

		QGEntradaSegmentacionesPresegDto busquedaSegmentacion = (QGEntradaSegmentacionesPresegDto) JSONObject
				.toBean(jsonObject, QGEntradaSegmentacionesPresegDto.class);

		try {
			if (metadatos.get(busquedaSegmentacion.getNejecucion().toString()) != null) {
				qGCGlobalPagingDto = (QGCGlobalPagingDto) metadatos
						.get(busquedaSegmentacion.getNejecucion().toString());
			} else {
				// obtenemos el resultado de la llamada al servicio
				qGCGlobalPagingDto = this.getSegmentacionesServicio()
						.obtenerListaPresegmentaciones(busquedaSegmentacion);
				metadatos.put(busquedaSegmentacion.getNejecucion().toString(), qGCGlobalPagingDto);
			}
		} catch (Exception ex) {
			tratarMensajesExcepcion(response, ex);
		}

		if (qGCGlobalPagingDto != null) {
			// enviamos los datos al js
			escribirRespuestaPagingJson(qGCGlobalPagingDto, response);
		}
	}

	/**
	 * Carga la descripcion del subsegmento
	 */
	public void cargarDescripcionSubsegmento(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();

		QGSegmentacionesPresegForm segmentacionesForm = (QGSegmentacionesPresegForm) form;

		JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm.getSegmentacionPresegJSON());

		QGEntradaSegmentacionesPresegDto busquedaSegmentacion = (QGEntradaSegmentacionesPresegDto) JSONObject
				.toBean(jsonObject, QGEntradaSegmentacionesPresegDto.class);

		try {
			// obtenemos el resultado de la llamada al servicio
			qGCGlobalDto = this.getSegmentacionesServicio().obtenerSubsegmentos(busquedaSegmentacion);

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
	 * Carga el combo de codigos de segmento
	 */
	public void cargarCodigosSegmento(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Cargando el combo de codigos de segmento");

		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		try {

			// casting del formulario
			QGSegmentacionesPresegForm presegmentacionesForm = (QGSegmentacionesPresegForm) form;

			// Obtenemos la cadena JSON
			JSONObject jsonObject = JSONObject.fromObject(presegmentacionesForm.getSegmentacionPresegJSON());

			// Convertir la cadena JSON en el DTO
			QGSegmentacionesDto segmentacion = (QGSegmentacionesDto) JSONObject.toBean(jsonObject,
					QGSegmentacionesDto.class);

			qGCGlobalDto = this.getSegmentacionesSer().obtenerCodigosSegmento(segmentacion.getUnidad());

			// solo hay que cargar en el combo ME,PE,AU,GP
			// recorremos la lista para buscar los 4 que necesitamos
			List listaFinal = new ArrayList();
			int i = 0;
			int encontrados = 0;
			while (encontrados < 4 && i < qGCGlobalDto.getlistaDatos().size()) {
				QGCatalogoDto catalogoDto = (QGCatalogoDto) qGCGlobalDto.getlistaDatos().get(i);
				String codigo = catalogoDto.getCodigo();
				if (codigo.equals(CODIGO_AUTONOMO) || codigo.equals(CODIGO_MEDIANA_EMPRESA)
						|| codigo.equals(CODIGO_RESIDENCIAL_NEGOCIO_PRF) || codigo.equals(CODIGO_PEQUENIA_EMPRESA)) {
					listaFinal.add(catalogoDto);
					encontrados++;
				}
				i++;
			}
			qGCGlobalDto.setlistaDatos(listaFinal);
		} catch (Exception ex) {
			tratarMensajesExcepcion(response, ex);
		}
		if (qGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(qGCGlobalDto, response);
		}
	}

	/**
	 * Carga el combo de codigos de Of. Atención
	 */
	public void cargarCodigosOfAtencion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject jsonObject = null;
		QGSegmentacionesPresegForm segmentacionesForm = null;
		QGEntradaPresegmentacionDto entrada = null;
		QGCGlobalPagingDto qGCGlobalPagingDto = null;

		try {

			segmentacionesForm = (QGSegmentacionesPresegForm) form;

			jsonObject = JSONObject.fromObject(segmentacionesForm.getSegmentacionPresegJSON());

			entrada = (QGEntradaPresegmentacionDto) JSONObject.toBean(jsonObject, QGEntradaPresegmentacionDto.class);

			qGCGlobalPagingDto = this.getSegmentacionesServicio().obtenerDatosOfAtencion(entrada);

			escribirRespuestaPagingJson(qGCGlobalPagingDto, response);

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			tratarMensajesExcepcion(response, ex);
		}
	}

	/**
	 * Carga el combo de codigos de Tandem
	 */
	public void cargarCodigosTandem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		QGCGlobalPagingDto qGCGlobalPagingDto = new QGCGlobalPagingDto();
		QGSegmentacionesPresegForm segmentacionesForm = null;
		QGEntradaPresegmentacionDto entrada = null;
		JSONObject jsonObject;

		try {

			segmentacionesForm = (QGSegmentacionesPresegForm) form;

			jsonObject = JSONObject.fromObject(segmentacionesForm.getSegmentacionPresegJSON());

			entrada = (QGEntradaPresegmentacionDto) JSONObject.toBean(jsonObject, QGEntradaPresegmentacionDto.class);

			qGCGlobalPagingDto = this.getSegmentacionesServicio().obtenerDatosTandem(entrada);

			escribirRespuestaPagingJson(qGCGlobalPagingDto, response);

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			tratarMensajesExcepcion(response, ex);
		}
	}

	/**
	 * Llamada parte Administraciones
	 */
	public void cargarDatosAdmin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		try {

			QGSegmentacionesPresegForm segmentacionesForm = (QGSegmentacionesPresegForm) form;
			JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm.getSegmentacionPresegJSON());

			QGEntradaPresegmentacionAdminDto entrada = (QGEntradaPresegmentacionAdminDto) JSONObject.toBean(jsonObject,
					QGEntradaPresegmentacionAdminDto.class);
			qGCGlobalDto = this.getSegmentacionesServicio().gestionAdministracion(entrada);

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
	 * Llamada Operar Presegmentaciones
	 */
	public void operarPreseg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();

		QGSegmentacionesPresegForm segmentacionesForm = (QGSegmentacionesPresegForm) form;
		JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm.getSegmentacionPresegJSON());

		QGEntradaSegmentacionesPresegDto entrada = (QGEntradaSegmentacionesPresegDto) JSONObject.toBean(jsonObject,
				QGEntradaSegmentacionesPresegDto.class);

		
		String operacionDetalle = "";
		String resultadoNav = "";
		List listaMensajes = new ArrayList();
		if (entrada.getCodActuacion().equals("A")) {
			operacionDetalle = QGConstantes.MONIT_SEG_PRES_ALTA;
			resultadoNav = "No se ha dado de alta la presegmentación.";
			listaMensajes.add("La Presegmentació&oacute;n ha sido creada correctamente");
		} else if (entrada.getCodActuacion().equals("M")) {
			operacionDetalle = QGConstantes.MONIT_SEG_PRES_MOD;
			resultadoNav = "No se ha podido modificar la presegmentación.";
			listaMensajes.add("La Presegmentaci&oacute;n ha sido modificada correctamente");
		} else if (entrada.getCodActuacion().equals("B")) {
			operacionDetalle = QGConstantes.MONIT_SEG_PRES_BAJA;
			resultadoNav = "No se ha podido eliminar la presegmentación.";
			listaMensajes.add("La Presegmentaci&oacute;n ha sido borrada correctamente");
		}

		try {
			qGCGlobalDto = this.getSegmentacionesServicio().operarPreseg(entrada);
			// Si todo ha ido bien mostramos un mensaje de guardado en la
			// pantalla
			qGCGlobalDto.setlistaMensajes(listaMensajes);

			
			if (entrada.getCodActuacion().equals("A") || entrada.getCodActuacion().equals("B")
					|| entrada.getCodActuacion().equals("M")) {
				String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
				if (qGCGlobalDto != null) {
					resultadoNav = "";
					resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
				}
				this.getAuditorServicio().altaAuditoria(operacionDetalle, resultado, resultadoNav);
			}
		} catch (Exception ex) {
			if (entrada.getCodActuacion().equals("A") || entrada.getCodActuacion().equals("B")
					|| entrada.getCodActuacion().equals("M")) {
				this.getAuditorServicio().altaAuditoria(operacionDetalle, QGConstantes.DS_RESULTADO_OPERA_ERROR,
						ex.getMessage());
			}
			logger.error(ex.getMessage());
			tratarMensajesExcepcion(response, ex);
		}
		if (qGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(qGCGlobalDto, response);
		}
	}

	public void exportarExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("Buscando segun criterio de busqueda inicial");
		QGCGlobalPagingDto qGCGlobalPagingDto = new QGCGlobalPagingDto();
		ArrayList todosPresegmentados = new ArrayList();
		// metadatos = new HashMap();
		QGSegmentacionesPresegForm segmentacionesForm = (QGSegmentacionesPresegForm) form;
		JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm.getSegmentacionPresegJSON());
		QGEntradaSegmentacionesPresegDto busquedaSegmentacion = (QGEntradaSegmentacionesPresegDto) JSONObject
				.toBean(jsonObject, QGEntradaSegmentacionesPresegDto.class);
		busquedaSegmentacion.setPgnTx("");
		try {
			do {
				// obtenemos el resultado de la llamada al servicio
				qGCGlobalPagingDto = this.getSegmentacionesServicio()
						.obtenerListaPresegmentaciones(busquedaSegmentacion);
				busquedaSegmentacion.setPgnTx(qGCGlobalPagingDto.getPgnTx());
				for (int i = 0; i < qGCGlobalPagingDto.getListaDatos().size(); i++) {
					QGSalidaSegmentacionesPresegDto var = (QGSalidaSegmentacionesPresegDto) qGCGlobalPagingDto
							.getListaDatos().get(i);
					if (!var.getFechaFinVigencia().equals("31.12.2500")) {
						var.setUsuarioBaja(var.getUsuarioModif());
					}
					// Se modifica tambien el tipo de documento
					if (var.getTipoDocumento().equals("D")) {
						var.setTipoDocumento("DNI/NIF");
					} else if (var.getTipoDocumento().equals("C")) {
						var.setTipoDocumento("OTROS");
					} else if (var.getTipoDocumento().equals("L")) {
						var.setTipoDocumento("L.Fiscal");
					} else if (var.getTipoDocumento().equals("N")) {
						var.setTipoDocumento("DOCUMENTO NULO");
					} else if (var.getTipoDocumento().equals("P")) {
						var.setTipoDocumento("Pasaporte");
					} else if (var.getTipoDocumento().equals("R")) {
						var.setTipoDocumento("T.Residen.");
					}

					todosPresegmentados.add(qGCGlobalPagingDto.getListaDatos().set(i, var));
				}
				// todosPresegmentados.add(qGCGlobalPagingDto.getListaDatos());
			} while (qGCGlobalPagingDto.getIndPgnIn().equals("S"));

			ByteArrayOutputStream baos = this.getGenerarInformeServicio()
					.generarInformeExcelPresegmentacion(todosPresegmentados);
			response.setContentType("application/excel");
			response.addHeader("Content-Disposition", "attachment; filename=\"informe.xls\"");
			response.setContentLength(baos.toByteArray().length);
			response.getOutputStream().write(baos.toByteArray());
			response.getOutputStream().flush();
			
			String resultadoNav = "No se han podido exportar los datos.";
			String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
			if (qGCGlobalPagingDto != null) {
				resultadoNav = "";
				resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
			}
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_SEG_EXP, resultado, resultadoNav);
		} catch (Exception e) {
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_SEG_EXP, QGConstantes.DS_RESULTADO_OPERA_ERROR,
					e.getMessage());
			logger.info("error dao --> " + e.getMessage());
		}

	}

	public void exportarPDF(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("Buscando segun criterio");
		QGCGlobalPagingDto qGCGlobalPagingDto = new QGCGlobalPagingDto();

		metadatos = new HashMap();

		QGSegmentacionesPresegForm segmentacionesForm = (QGSegmentacionesPresegForm) form;

		JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm.getSegmentacionPresegJSON());

		QGEntradaSegmentacionesPresegDto busquedaSegmentacion = (QGEntradaSegmentacionesPresegDto) JSONObject
				.toBean(jsonObject, QGEntradaSegmentacionesPresegDto.class);

		try {
			// obtenemos el resultado de la llamada al servicio
			qGCGlobalPagingDto = this.getSegmentacionesServicio().obtenerListaPresegmentaciones(busquedaSegmentacion);

			for (int i = 0; i < qGCGlobalPagingDto.getListaDatos().size(); i++) {
				QGSalidaSegmentacionesPresegDto var = (QGSalidaSegmentacionesPresegDto) qGCGlobalPagingDto
						.getListaDatos().get(i);
				if (!var.getFechaFinVigencia().equals("31.12.2500")) {
					var.setUsuarioBaja(var.getUsuarioModif());
				}
				qGCGlobalPagingDto.getListaDatos().set(i, var);
			}

			if (qGCGlobalPagingDto != null) {

				ByteArrayOutputStream baos = generarInformeServicio
						.generarInformePresegmentacionPDF(qGCGlobalPagingDto.getListaDatos());

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

	/**
	 * GETTERS & SETTERS
	 */
	public QGSegmentacionesPresegServicio getSegmentacionesServicio() {
		return segmentacionesServicio;
	}

	public void setSegmentacionesServicio(QGSegmentacionesPresegServicio segmentacionesServicio) {
		this.segmentacionesServicio = segmentacionesServicio;
	}

	public QGGenerarInformeServicio getGenerarInformeServicio() {
		return generarInformeServicio;
	}

	public void setGenerarInformeServicio(QGGenerarInformeServicio generarInformeServicio) {
		this.generarInformeServicio = generarInformeServicio;
	}

	public QGSegmentacionesServicio getSegmentacionesSer() {
		return segmentacionesSer;
	}

	public void setSegmentacionesSer(QGSegmentacionesServicio segmentacionesSer) {
		this.segmentacionesSer = segmentacionesSer;
	}

	public static HashMap getMetadatos() {
		return metadatos;
	}

	public static void setMetadatos(HashMap metadatos) {
		QGSegmentacionesPresegAction.metadatos = metadatos;
	}

	public QGAuditorServicio getAuditorServicio() {
		return auditorServicio;
	}

	public void setAuditorServicio(QGAuditorServicio auditorServicio) {
		this.auditorServicio = auditorServicio;
	}

}
