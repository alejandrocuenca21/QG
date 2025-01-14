/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.struts.actions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.security.context.SecurityContextHolder;

import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSegmentacionesTraduccionDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGTipoComboValor;
import com.telefonica.ssnn.qg.administracion.segmentacion.servicio.interfaz.QGSegmentacionesTraduccionServicio;
import com.telefonica.ssnn.qg.administracion.segmentacion.struts.forms.QGSegmentacionesForm;
import com.telefonica.ssnn.qg.administracion.segmentacion.struts.forms.QGSegmentacionesTraduccionForm;
import com.telefonica.ssnn.qg.auditoria.servicio.interfaz.QGAuditorServicio;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.struts.QGBaseAction;
import com.telefonica.ssnn.qg.util.QGCatalogoDto;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;

/**
 * Action de Segmentaciones Traduccion.
 * 
 * @struts.action name="QGSegmentacionesTraduccionForm" path="/Traduccion"
 *                input="/vTraduccion.do" scope="request" redirect="true"
 *                validate="false" parameter="cargarCodigosSegmento,cargarCodigosSubSegmento, buscar, obtenerUsuario, alta, baja, exportarPDF,obtenerHistorico"
 *                type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward name="success" path="/vSegmentacionesTraduccion.do"
 */
public class QGSegmentacionesTraduccionAction extends QGBaseAction {

	/**
	 * Código de segmento autónomo
	 */
	private static String CODIGO_AUTONOMO = "AU";

	/**
	 * logger
	 */
	private static final Logger logger = Logger
			.getLogger(QGSegmentacionesTraduccionAction.class);

	private QGSegmentacionesTraduccionServicio segmentacionesServicio;

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
	 * Carga el combo de codigos de segmento movil o fijo
	 */
	public void cargarCodigosSegmento(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Cargando el combo de codigos de segmento");

		// Obtenemos el valor del combo padre
		QGSegmentacionesTraduccionForm segmentacionesForm = (QGSegmentacionesTraduccionForm) form;
		
		JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm
				.getTipoComboValorJSON());

		/*
		 * Obtenemos el valor y el tipo de segmento que se necesita cargar, en este caso el codigo vendra a vacio
		 */
		QGTipoComboValor tipoComboValor = (QGTipoComboValor) JSONObject
				.toBean(jsonObject,
						QGTipoComboValor.class);

		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		try {
			// Si hay que traer los moviles...
			if (tipoComboValor.getTipoSegmento() != null
					&& tipoComboValor.getTipoSegmento().equals(Boolean.FALSE)) {
				logger.info("Segmentos móviles");
				qGCGlobalDto = this.getSegmentacionesServicio()
						.obtenerCodigosSegmentoMovil();

				// Los fijos
			} else if (tipoComboValor.getTipoSegmento() != null
					&& tipoComboValor.getTipoSegmento().equals(Boolean.TRUE)) {

				logger.info("Segmentos fijos");
				qGCGlobalDto = this.getSegmentacionesServicio()
						.obtenerCodigosSegmentoFijo();

			}
			
			// solo hay que cargar en el combo AU tanto en fijos como en moviles
			// recorremos la lista para buscar los que necesitamos
			List listaFinal = new ArrayList();
			int i = 0;
			int encontrados = 0;
			while (encontrados < 4
					&& i < qGCGlobalDto.getlistaDatos().size()) {
				QGCatalogoDto catalogoDto = (QGCatalogoDto) qGCGlobalDto
						.getlistaDatos().get(i);
				String codigo = catalogoDto.getCodigo();
				if (codigo.equals(CODIGO_AUTONOMO)) {
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
	 * Carga el combo de codigos de subsegmento fijo
	 */
	public void cargarCodigosSubSegmento(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Cargando el combo de codigos de subsegmento");
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		// Obtenemos el valor del combo padre
		QGSegmentacionesTraduccionForm segmentacionesForm = (QGSegmentacionesTraduccionForm) form;
		
		JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm
				.getTipoComboValorJSON());

		/*
		 * Obtenemos el valor y el tipo de segmento que se necesita cargar, en este caso el codigo vendra a vacio
		 */
		QGTipoComboValor tipoComboValor = (QGTipoComboValor) JSONObject
				.toBean(jsonObject,
						QGTipoComboValor.class);

		try {
			// Valor del combo seleccionado
			String valorCombo = tipoComboValor.getValorCombo();
			
			//Si es el valor nulo, se devolvera un qGCGlobalDto sin datos
			if(valorCombo.equals("") || valorCombo.equals("Todos")){
				qGCGlobalDto = new QGCGlobalDto();
			}else{
	
				// Si hay que traer los moviles...
				if (tipoComboValor.getTipoSegmento() != null
						&& tipoComboValor.getTipoSegmento().equals(Boolean.FALSE)) {
					logger.info("Segmentos móviles");
					// Le pasamos al servicio el codigo por el que tendra que buscar
					qGCGlobalDto = this.getSegmentacionesServicio()
							.obtenerCodigosSubSegmentoMovil(valorCombo);
	
					// Los fijos
				} else if (tipoComboValor.getTipoSegmento() != null
						&& tipoComboValor.getTipoSegmento().equals(Boolean.TRUE)) {
					logger.info("Segmentos fijos");
					qGCGlobalDto = this.getSegmentacionesServicio()
							.obtenerCodigosSubSegmentoFijo(valorCombo);
				}
			}

		} catch (Exception ex) {
			tratarMensajesExcepcion(response, ex);
		}
		if (qGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(qGCGlobalDto, response);
		}
	}

	
	/**
	 * Funcion que busca el historico de segmentaciones traduccion
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

		try {
			// obtenemos el resultado de la llamada al servicio, no hay busqueda
			// por criterio
			qGCGlobalDto = this.getSegmentacionesServicio().obtenerHistorico(
					new QGSegmentacionesTraduccionDto());
			
			this.filtrarListaResultado(qGCGlobalDto.getlistaDatos());

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

		QGSegmentacionesTraduccionForm segmentacionesForm = (QGSegmentacionesTraduccionForm) form;
		try {
			JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm
					.getSegmentacionTraduccionJSON());

			QGSegmentacionesTraduccionDto busquedaSegmentacion = (QGSegmentacionesTraduccionDto) JSONObject
					.toBean(jsonObject,
							QGSegmentacionesTraduccionDto.class);

			// obtenemos el resultado de la llamada al servicio
			qGCGlobalDto = this.getSegmentacionesServicio()
					.obtenerListaSegmentaciones(busquedaSegmentacion);
			
			this.filtrarListaResultado(qGCGlobalDto.getlistaDatos());

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
	 * Filtra la lista de datos al codigo de segmento fijo y movil AU
	 * @param getlistaDatos
	 * @return
	 */
	private void filtrarListaResultado(List listaDatos) {
		if(listaDatos != null){
			Iterator iter = listaDatos.iterator();
			while(iter.hasNext()){
				QGSegmentacionesTraduccionDto traduccion = (QGSegmentacionesTraduccionDto)iter.next();
				//Si alguno de los dos codigos no es AU no se mostrara.
				if(!traduccion.getCodigoSegmentoFijo().equals(CODIGO_AUTONOMO)|| !traduccion.getCodigoSegmentoMovil().equals(CODIGO_AUTONOMO)){
					iter.remove();
				}
			}
			
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
		logger.info("Dando de alta una traduccion");
		// casting del formulario
		QGSegmentacionesTraduccionForm segmentacionesForm = (QGSegmentacionesTraduccionForm) form;

		// Obtenemos la cadena JSON
		JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm
				.getSegmentacionTraduccionJSON());
		
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		try {
			// Convertir la cadena JSON en el DTO
			QGSegmentacionesTraduccionDto traduccionAlta = (QGSegmentacionesTraduccionDto) JSONObject
					.toBean(jsonObject, QGSegmentacionesTraduccionDto.class);
		
			/*
			 * HAY QUE VALIDAR LOS DATOS. - FORMATO CORRECTO EN LAS FECHAS DE
			 * VIGENCIA - FECHA DE INICIO DE VIGENCIA MENOR QUE FECHA FIN - LOS
			 * SUBSEGMENTOS DE ORIGEN Y DESTINO DEBEN SER DISTINTOS
			 */

			String error = null;
			boolean errores = false;
			if(StringUtils.isBlank(traduccionAlta.getCodigoSubSegmentoFijo())){
				errores = true;
				error = "El código de subsegmento móvil es un valor obligatorio";
			}else if(StringUtils.isBlank(traduccionAlta.getCodigoSubSegmentoFijo())){
				errores = true;
				error = "El código de subsegmento fijo es un valor obligatorio";
			}else if(StringUtils.isBlank(traduccionAlta.getCodigoSegmentoFijo())){
				errores = true;
				error = "El código de segmento fijo es un valor obligatorio";
			}else if(StringUtils.isBlank(traduccionAlta.getCodigoSegmentoMovil())){
				errores = true;
				error = "El código de segmento móvil es un valor obligatorio";
			}else if (!QGUtilidadesFechasUtils.validarFormatoFecha(traduccionAlta
					.getFechaIniVigencia(),
					QGUtilidadesFechasUtils.FORMATO_FECHA_1)) {
				errores = true;
				error = "La Fecha de inicio de vigencia, no tiene un formato correcto (dd.mm.yyyy)";
			} else if (!QGUtilidadesFechasUtils.validarFormatoFecha(
					traduccionAlta.getFechaFinVigencia(),
					QGUtilidadesFechasUtils.FORMATO_FECHA_1)) {
				errores = true;
				error = "La Fecha de fin de vigencia, no tiene un formato correcto (dd.mm.yyyy)";
			} else if (-1 == QGUtilidadesFechasUtils.compararFechas(
					traduccionAlta.getFechaIniVigencia(), traduccionAlta
							.getFechaFinVigencia(),
					QGUtilidadesFechasUtils.FORMATO_FECHA_1)) {
				errores = true;
				error = "La Fecha de inicio de vigencia debe ser menor que la de fin.";
			}
			if (errores) {// si hay errores, metemos la lista de los mensajes en
				// la respuesta.
				logger.error(error);
				throw new Exception(error);
			}

			if (!errores) {// si no hay errores, continuamos con el alta
				List listaMensajes = new ArrayList();
				
				//Le asignamos la fecha de alta
				traduccionAlta.setFechaAlta(QGUtilidadesFechasUtils.fromDateToString(new Date(), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				
				qGCGlobalDto = this.getSegmentacionesServicio()
						.altaSegmentacion(traduccionAlta);
				// Si todo ha ido bien mostramos un mensaje de guardado en la
				// pantalla
				listaMensajes
						.add("La traducci&oacute;n de la segmentaci&oacute;n ha sido creada correctamente");
				qGCGlobalDto.setlistaMensajes(listaMensajes);
			}
			
    		String resultadoNav ="No se ha dado de alta la traducción.";
    		String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
        	if (qGCGlobalDto != null) {
    			resultadoNav = "";
        		resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
            }
        	this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_SEG_TRA_ALTA,resultado, resultadoNav);
			
		} catch (Exception e) {
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_SEG_TRA_ALTA,
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
		QGSegmentacionesTraduccionForm segmentacionesForm = (QGSegmentacionesTraduccionForm) form;

		// Obtenemos la cadena JSON
		JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm
				.getSegmentacionTraduccionJSON());

		// Convertir la cadena JSON en el DTO
		QGSegmentacionesTraduccionDto segmentacionBaja = (QGSegmentacionesTraduccionDto) JSONObject
				.toBean(jsonObject, QGSegmentacionesTraduccionDto.class);

		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();

		try {
			// Llamada al servicio para dar de baja la segmentacion elegida
			
			segmentacionBaja.setFechaMod(QGUtilidadesFechasUtils.fromDateToString(new Date(), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
			
			//No se pone la fecha del dia si no viene informada.
//			if(segmentacionBaja.getFechaFinVigenciaBaja() == null || "".equals(segmentacionBaja.getFechaFinVigenciaBaja() )){
//				segmentacionBaja.setFechaFinVigenciaBaja(QGUtilidadesFechasUtils.fromDateToString(new Date(), QGUtilidadesFechasUtils.FORMATO_FECHA_1));
//			}
			
			//Si la fecha no viene informada (fecha fin para baja)se da de baja sino, se modifica
			if(segmentacionBaja.getFechaFinVigenciaBaja() == null || "".equals(segmentacionBaja.getFechaFinVigenciaBaja() )){
				this.getSegmentacionesServicio().bajaSegmentacion(segmentacionBaja);
			}else{
				this.getSegmentacionesServicio().modificarSegmentacion(segmentacionBaja);
			}
			List listaMensajes = new ArrayList();
			listaMensajes
					.add("La segmentación traducción ha sido dada de baja correctamente");
			qGCGlobalDto.setlistaMensajes(listaMensajes);
			
			
			String resultadoNav ="No se ha podido efectuar la baja de la traducción.";
    		String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
        	if (qGCGlobalDto != null) {
    			resultadoNav = "";
        		resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
            }
        	this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_SEG_TRA_BAJA,resultado, resultadoNav);	
		} catch (Exception e) {
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_SEG_TRA_BAJA,
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
				.info("Generando el informe de segmentaciones traduccion segun el criterio informado");
		// obtenemos los datos en el dto
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		QGSegmentacionesTraduccionForm segmentacionesForm = (QGSegmentacionesTraduccionForm) form;
		JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm
				.getSegmentacionTraduccionJSON());

		QGSegmentacionesTraduccionDto busquedaSegmentacion = (QGSegmentacionesTraduccionDto) JSONObject
				.toBean(jsonObject, QGSegmentacionesTraduccionDto.class);
		try {
			// obtenemos el resultado de la llamada al servicio
			logger
					.info("Obteniendo el listado de las segmentaciones traduccion a exportar a PDF");
			qGCGlobalDto = this.getSegmentacionesServicio()
					.obtenerListaSegmentaciones(busquedaSegmentacion);
			
			this.filtrarListaResultado(qGCGlobalDto.getlistaDatos());			

			if (qGCGlobalDto != null) {
				logger.info("Generando el informe");
				ByteArrayOutputStream baos = generarInformeServicio
						.generarInformePDF(qGCGlobalDto.getlistaDatos(),
								QGConstantes.PLANTILLA_INFORME_SEG_TRAD);

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

	public QGSegmentacionesTraduccionServicio getSegmentacionesServicio() {
		return segmentacionesServicio;
	}

	public void setSegmentacionesServicio(
			QGSegmentacionesTraduccionServicio segmentacionesServicio) {
		this.segmentacionesServicio = segmentacionesServicio;
	}

	public QGAuditorServicio getAuditorServicio() {
		return auditorServicio;
	}

	public void setAuditorServicio(QGAuditorServicio auditorServicio) {
		this.auditorServicio = auditorServicio;
	}
	
}
