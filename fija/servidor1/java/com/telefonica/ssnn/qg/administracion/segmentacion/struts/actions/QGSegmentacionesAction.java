/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.struts.actions;

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

import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSegmentacionesBusquedaDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSegmentacionesDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.servicio.interfaz.QGSegmentacionesServicio;
import com.telefonica.ssnn.qg.administracion.segmentacion.struts.forms.QGSegmentacionesForm;
import com.telefonica.ssnn.qg.auditoria.servicio.interfaz.QGAuditorServicio;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.struts.QGBaseAction;
import com.telefonica.ssnn.qg.util.QGCatalogoDto;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;

/**
 * Action de Segmentaciones.
 * 
 * @struts.action name="QGSegmentacionesForm" path="/Segmentaciones"
 *                input="/vSegmentaciones.do" scope="request" redirect="true"
 *                validate="false" parameter="cargarCodigosSegmento,cargarCodigosSubSegmento, buscar, obtenerUsuario, alta, baja, exportarPDF,obtenerHistorico"
 *                type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward name="success" path="/vSegmentaciones.do"
 */
public class QGSegmentacionesAction extends QGBaseAction {

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
	private static String CODIGO_NEGOCIO_TRAF_TELEF = "NT";

	/**
	 * logger
	 */
	private static final Logger logger = Logger
			.getLogger(QGSegmentacionesAction.class);

	private QGSegmentacionesServicio segmentacionesServicio;

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

		QGSegmentacionesForm segmentacionesForm = (QGSegmentacionesForm) form;

		segmentacionesForm.setUsuarioLogado(usuarioLogado.getUsername());
		return mapping.findForward(SUCCESS);
	}

	/**
	 * Carga el combo de codigos de segmento
	 */
	public void cargarCodigosSegmento(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Cargando el combo de codigos de segmento");

		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		try {
			
			// casting del formulario
			QGSegmentacionesForm segmentacionesForm = (QGSegmentacionesForm) form;

			// Obtenemos la cadena JSON
			JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm
					.getSegmentacionJSON());

			// Convertir la cadena JSON en el DTO
			QGSegmentacionesDto segmentacion = (QGSegmentacionesDto) JSONObject
					.toBean(jsonObject, QGSegmentacionesDto.class);
			
			qGCGlobalDto = this.getSegmentacionesServicio()
			.obtenerCodigosSegmento(segmentacion.getUnidad());

			// solo hay que cargar en el combo ME,PE,AU,NT
			// recorremos la lista para buscar los 4 que necesitamos
			List listaFinal = new ArrayList();
			int i = 0;
			int encontrados = 0;
			while (encontrados < 4 && i < qGCGlobalDto.getlistaDatos().size()) {
				QGCatalogoDto catalogoDto = (QGCatalogoDto) qGCGlobalDto
						.getlistaDatos().get(i);
				String codigo = catalogoDto.getCodigo();
				if (codigo.equals(CODIGO_AUTONOMO)
						|| codigo.equals(CODIGO_MEDIANA_EMPRESA)
						|| codigo.equals(CODIGO_NEGOCIO_TRAF_TELEF)
						|| codigo.equals(CODIGO_PEQUENIA_EMPRESA)) {
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
	 * Carga el combo de codigos de segmento
	 */
	public void cargarCodigosSubSegmento(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Cargando el combo de codigos de subsegmento");
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		// Obtenemos el valor del combo padre
		try {
			
			// casting del formulario
			QGSegmentacionesForm segmentacionesForm = (QGSegmentacionesForm) form;

			// Obtenemos la cadena JSON
			JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm
					.getSegmentacionJSON());

			// Convertir la cadena JSON en el DTO
			QGSegmentacionesDto segmentacion = (QGSegmentacionesDto) JSONObject
					.toBean(jsonObject, QGSegmentacionesDto.class);
			
			// Valor de la unidad seleccionada en el combo
			String unidad = segmentacion.getUnidad();
			// Valor del combo seleccionado
			String valorCombo = segmentacion.getSegmentoOrigen();
			// Le pasamos al servicio el codigo por el que tendra que buscar
			qGCGlobalDto = this.getSegmentacionesServicio()
					.obtenerCodigosSubSegmento(unidad,valorCombo);

		} catch (Exception ex) {
			tratarMensajesExcepcion(response, ex);
		}
		if (qGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(qGCGlobalDto, response);
		}
	}
	/**
	 * Funcion que busca el historico de segmentaciones
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void obtenerHistorico(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Obteniendo histórico");
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();

		try {
			// obtenemos el resultado de la llamada al servicio, no hay busqueda por criterio
			qGCGlobalDto = this.getSegmentacionesServicio()
					.obtenerHistorico(new QGSegmentacionesBusquedaDto());
			
		}catch (Exception ex) {
			logger.error(ex.getMessage());
			tratarMensajesExcepcion(response, ex);
		}
		if (qGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(qGCGlobalDto, response);
		}
	}

	/**
	 * Funcion que busca todas las segmentaciones
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void buscar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Buscando segun criterio");
		
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();

		QGSegmentacionesForm segmentacionesForm = (QGSegmentacionesForm) form;
		try {
			JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm
					.getSegmentacionJSON());
			QGSegmentacionesBusquedaDto busquedaSegmentacion = (QGSegmentacionesBusquedaDto) JSONObject
					.toBean(jsonObject, QGSegmentacionesBusquedaDto.class);
			// obtenemos el resultado de la llamada al servicio
			qGCGlobalDto = this.getSegmentacionesServicio()
					.obtenerListaSegmentaciones(busquedaSegmentacion);

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
	 * Da de alta una nueva segmentacion creada
	 * 
	 * @param mapping
	 *            - mapeador
	 * @param form
	 *            - form que lanza el action
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void alta(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Dando de alta una segmentacion");
		// casting del formulario
		QGSegmentacionesForm segmentacionesForm = (QGSegmentacionesForm) form;

		// Obtenemos la cadena JSON
		JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm
				.getSegmentacionJSON());

		// Convertir la cadena JSON en el DTO
		QGSegmentacionesDto segmentacionAlta = (QGSegmentacionesDto) JSONObject
				.toBean(jsonObject, QGSegmentacionesDto.class);

		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		try {
			/*
			 * HAY QUE VALIDAR LOS DATOS. - FORMATO CORRECTO EN LAS FECHAS DE
			 * VIGENCIA - FECHA DE INICIO DE VIGENCIA MENOR QUE FECHA FIN - LOS
			 * SUBSEGMENTOS DE ORIGEN Y DESTINO DEBEN SER DISTINTOS
			 */

			String error = null;
			boolean errores = false;

			if (!QGUtilidadesFechasUtils.validarFormatoFecha(segmentacionAlta
					.getFechaIniVigencia(),
					QGUtilidadesFechasUtils.FORMATO_FECHA_1)) {
				errores = true;
				error = "La Fecha de inicio de vigencia, no tiene un formato correcto (dd.mm.yyyy)";
			} else if (!QGUtilidadesFechasUtils.validarFormatoFecha(
					segmentacionAlta.getFechaFinVigencia(),
					QGUtilidadesFechasUtils.FORMATO_FECHA_1)) {
				errores = true;
				error = "La Fecha de fin de vigencia, no tiene un formato correcto (dd.mm.yyyy)";
			} else if (-1 == QGUtilidadesFechasUtils.compararFechas(
					segmentacionAlta.getFechaIniVigencia(), segmentacionAlta
							.getFechaFinVigencia(),
					QGUtilidadesFechasUtils.FORMATO_FECHA_1)) {
				errores = true;
				error = "La Fecha de inicio de vigencia debe ser menor que la de fin.";
			} else if (segmentacionAlta.getSubSegmentoOrigen().equals(
					segmentacionAlta.getSubSegmentoDestino())) {
				errores = true;
				error = "El subsegmento de origen y el subsegmento de destino, deben ser distintos.";
			}

			if (errores) {// si hay errores, metemos la lista de los mensajes en
							// la respuesta.
				throw new Exception(error);
			}

			if (!errores) {// si no hay errores, continuamos con el alta
				List listaMensajes = new ArrayList();
				qGCGlobalDto = this.getSegmentacionesServicio()
						.altaSegmentacion(segmentacionAlta);
				// Si todo ha ido bien mostramos un mensaje de guardado en la
				// pantalla
				listaMensajes
						.add("La segmentaci&oacute;n ha sido creada correctamente");
				qGCGlobalDto.setlistaMensajes(listaMensajes);
			}

    		String resultadoNav ="No se ha dado de alta la evolucion.";
    		String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
        	if (qGCGlobalDto != null) {
    			resultadoNav = "";
        		resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
            }
        	this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_SEG_EV_ALTA,resultado, resultadoNav);
		} catch (Exception e) {
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_SEG_EV_ALTA,
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
	 * Da de baja una nueva segmentacion creada
	 * 
	 * @param mapping
	 *            - mapeador
	 * @param form
	 *            - form que lanza el action
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void baja(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Dando de baja una segmentacion");
		// casting del formulario
		QGSegmentacionesForm segmentacionesForm = (QGSegmentacionesForm) form;

		// Obtenemos la cadena JSON
		JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm
				.getSegmentacionJSON());

		// Convertir la cadena JSON en el DTO
		QGSegmentacionesDto segmentacionBaja = (QGSegmentacionesDto) JSONObject
				.toBean(jsonObject, QGSegmentacionesDto.class);

		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();

		try {
			// Llamada al servicio para dar de baja la segmentacion elegida
			this.getSegmentacionesServicio().bajaSegmentacion(segmentacionBaja);
			List listaMensajes = new ArrayList();
			listaMensajes
					.add("La segmentación ha sido dada de baja correctamente");
			qGCGlobalDto.setlistaMensajes(listaMensajes);
			
    		String resultadoNav ="No se ha podido efectuar la baja de la evolucion.";
    		String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
        	if (qGCGlobalDto != null) {
    			resultadoNav = "";
        		resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
            }
        	this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_SEG_EV_BAJA,resultado, resultadoNav);			
		} catch (Exception e) {
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_SEG_EV_BAJA,
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
	 * Genera los informes de segmentaciones
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void exportarPDF(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger
				.info("Generando el informe de segmentaciones segun el criterio informado");
		// obtenemos los datos en el dto
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		QGSegmentacionesForm segmentacionesForm = (QGSegmentacionesForm) form;
		JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm
				.getSegmentacionJSON());
		QGSegmentacionesBusquedaDto busquedaSegmentacion = (QGSegmentacionesBusquedaDto) JSONObject
				.toBean(jsonObject, QGSegmentacionesBusquedaDto.class);
		try {
			// obtenemos el resultado de la llamada al servicio
			logger
					.info("Obteniendo el listado de las segmentaciones a exportar a PDF");
			qGCGlobalDto = this.getSegmentacionesServicio()
					.obtenerListaSegmentaciones(busquedaSegmentacion);
			if (qGCGlobalDto != null) {
				logger.info("Generando el informe");
				ByteArrayOutputStream baos = generarInformeServicio
						.generarInformeSegmentacionPDF(qGCGlobalDto
								.getlistaDatos());
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition",
						"attachment; filename=\"informe.pdf\"");
				response.setHeader("Content-Transfer-Encoding", "binary");
				response.setContentLength(baos.toByteArray().length);
				response.getOutputStream().write(baos.toByteArray());
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		} catch (Exception ex) {
			logger.error(ex);
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

	public QGGenerarInformeServicio getGenerarInformeServicio() {
		return generarInformeServicio;
	}

	public void setGenerarInformeServicio(
			QGGenerarInformeServicio generarInformeServicio) {
		this.generarInformeServicio = generarInformeServicio;
	}

	public QGSegmentacionesServicio getSegmentacionesServicio() {
		return segmentacionesServicio;
	}

	public void setSegmentacionesServicio(
			QGSegmentacionesServicio segmentacionesServicio) {
		this.segmentacionesServicio = segmentacionesServicio;
	}

	public QGAuditorServicio getAuditorServicio() {
		return auditorServicio;
	}

	public void setAuditorServicio(QGAuditorServicio auditorServicio) {
		this.auditorServicio = auditorServicio;
	}
	
	
}
