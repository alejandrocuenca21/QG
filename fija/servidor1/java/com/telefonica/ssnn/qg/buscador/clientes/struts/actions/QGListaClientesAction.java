/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.clientes.struts.actions;

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

import com.telefonica.ssnn.qg.buscador.clientes.dto.QGBuscadorClientesDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGEntradaBusquedaTipoDocDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGHistorialClienteDto;
import com.telefonica.ssnn.qg.buscador.clientes.servicio.interfaz.QGBuscadorClientesServicio;
import com.telefonica.ssnn.qg.buscador.clientes.struts.forms.QGListaClientesForm;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.struts.QGBaseAction;
import com.telefonica.ssnn.qg.auditoria.servicio.interfaz.QGAuditorServicio;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * Action de Buscador de Clientes.
 * 
 * @author jacastano
 *
 * @struts.action name="QGListaClientesForm" path="/ListaClientes"
 *                input="/vListaClientes.do" scope="request" redirect="true"
 *                validate="false" parameter="buscar, cargarTipoDocumento,
 *                exportarPDF, actualizarHistorial, borrarHistorial,
 *                buscarClienteHistorial, buscarVueltaAtras, obtenerHistorico"
 *                type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward name="success" path="/vListaClientes.do"
 */
public class QGListaClientesAction extends QGBaseAction {
	QGBuscadorClientesServicio buscadorClientesServicio;

	private static final Logger logger = Logger.getLogger(QGListaClientesAction.class);
	private QGGenerarInformeServicio generarInformeServicio;

	private QGAuditorServicio auditorServicio;

	private static HashMap metadatos = null;

	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward(SUCCESS);
	}

	public void buscar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("******---buscar de QGListaClientesAction---******");

		QGListaClientesForm listaClientesForm = (QGListaClientesForm) form;

		// obtenemos los datos en el dto
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		// Seteamos los datos del usuario
		// setDatosUsuario(request, consentimientosDto);

		JSONObject jsonObject = JSONObject.fromObject(listaClientesForm.getListaClientesJSON());

		QGBuscadorClientesDto buscadorClientesDto = (QGBuscadorClientesDto) JSONObject.toBean(jsonObject,
				QGBuscadorClientesDto.class);
		
		try {

			// obtenemos el resultado de la llamada al servicio
			QGCGlobalDto = this.getBuscadorClientesServicio().obtenerListadoClientes(buscadorClientesDto);
			String resultado=QGConstantes.DS_RESULTADO_OPERA_NOK;
			String resultadoNav="No se han encontrado clientes que coincidan con los criterios de búsqueda introducidos.";
			// Rellenamos los datos para NombreRazonSocial y SegmentoSubsegmento
			if (QGCGlobalDto != null) {

				for (int i = 0; i < QGCGlobalDto.getlistaDatos().size(); i++) {
					QGBuscadorClientesDto elem = (QGBuscadorClientesDto) QGCGlobalDto.getlistaDatos().get(i);
					if (elem.getRazonSocial().length() > 0) {
						elem.setNombreRazonSocial(elem.getRazonSocial());
					} else {
						elem.setNombreRazonSocial(elem.getNombreCliente() + " " + elem.getPrimerApellido() + " "
								+ elem.getSegundoApellido());
					}
					if (elem.getCodigoSubsegmento().length() == 0) {
						elem.setSegmentoSubsegmento(elem.getCodigoSegmento());
					} else if (elem.getCodigoSegmento().length() == 0) {
						elem.setSegmentoSubsegmento(elem.getCodigoSubsegmento());
					} else {
						elem.setSegmentoSubsegmento(elem.getCodigoSegmento() + "/" + elem.getCodigoSubsegmento());
					}
					QGCGlobalDto.getlistaDatos().set(i, elem);

					if (i==0) {
						resultadoNav = elem.getCodigoCliente();
					} else {
						resultadoNav = resultadoNav + ";" + elem.getCodigoCliente();
					}
				}
		
				if(QGCGlobalDto.getlistaDatos().size()>0) {
					resultado= QGConstantes.DS_RESULTADO_OPERA_OK;
				}
				
			}

			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_CLIENTES,resultado, resultadoNav);
		} catch (Exception ex) {
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_CLIENTES,
					QGConstantes.DS_RESULTADO_OPERA_ERROR, ex.getMessage());
			tratarMensajesExcepcion(response, ex);
		}

		if (QGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(QGCGlobalDto, response);

		}

		// auditorServicio.altaAuditoria(QGConstantes.MONIT_CLIENTES,
		// QGConstantes.DS_RESULTADO_OPERA_OK, "Búsqueda por DNI");
		logger.info("******---FIN buscar de QGListaClientesAction---******");
	}

	public void buscarVueltaAtras(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("******---buscarVueltaAtras de QGListaClientesAction---******");

		// obtenemos los datos en el dto
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		if (metadatos != null) {
			QGBuscadorClientesDto buscadorClientesDto = (QGBuscadorClientesDto) metadatos.get("0");
			List listaDatos = new ArrayList();
			listaDatos.add(buscadorClientesDto);
			QGCGlobalDto.setlistaDatos(listaDatos);
			if (QGCGlobalDto != null) {
				// enviamos los datos al js
				escribirRespuestaJson(QGCGlobalDto, response);
			}
		}
		logger.info("******---FIN buscarVueltaAtras de QGListaClientesAction---******");
	}

	public void cargarTipoDocumento(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("******---cargarTipoDocumento de QGListaClientesAction---******");

		// obtenemos los datos en el dto
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		try {
			// casting del formulario
			QGListaClientesForm listaClientesForm = (QGListaClientesForm) form;
			// Obtenemos la cadena JSON
			JSONObject jsonObject = JSONObject.fromObject(listaClientesForm.getBusquedaTipoDocJSON());
			// Convertir la cadena JSON en el DTO
			QGEntradaBusquedaTipoDocDto busquedaTipoDoc = (QGEntradaBusquedaTipoDocDto) JSONObject.toBean(jsonObject,
					QGEntradaBusquedaTipoDocDto.class);

			// Seteamos los datos del usuario
			// setDatosUsuario(request, TiposUbicacionDto);

			// obtenemos el resultado de la llamada al servicio
			logger.info("******---Linea:" + busquedaTipoDoc.getCodigoLinea().toString() + "-Modalidad:"
					+ busquedaTipoDoc.getCodigoModalidad().toString() + "-Documento:"
					+ busquedaTipoDoc.getCodigoDocumento().toString() + "---******");
			QGCGlobalDto = this.getBuscadorClientesServicio().obtenerTipoDocumento(busquedaTipoDoc.getCodigoLinea(),
					busquedaTipoDoc.getCodigoModalidad(), busquedaTipoDoc.getCodigoDocumento());
		} catch (Exception ex) {
			logger.info("******---excepcion cargarTipoDocumento de QGListaClientesAction -->", ex);
			tratarMensajesExcepcion(response, ex);
		}

		if (QGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(QGCGlobalDto, response);
		}
		logger.info("******---FIN cargarTipoDocumento de QGListaClientesAction---******");
	}

	public void actualizarHistorial(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		metadatos = new HashMap();
		logger.info("******---actualizarHistorial de QGListaClientesAction---******");

		QGListaClientesForm listaClientesForm = (QGListaClientesForm) form;

		JSONObject jsonObject = JSONObject.fromObject(listaClientesForm.getListaClientesJSON());

		QGHistorialClienteDto historialClienteDto = (QGHistorialClienteDto) JSONObject.toBean(jsonObject,
				QGHistorialClienteDto.class);

		JSONObject jsonObjectBus = JSONObject.fromObject(listaClientesForm.getBusquedaClientesJSON());

		QGBuscadorClientesDto buscadorClientesDto = (QGBuscadorClientesDto) JSONObject.toBean(jsonObjectBus,
				QGBuscadorClientesDto.class);

		if (buscadorClientesDto != null) {
			if (!buscadorClientesDto.getCodigoCliente().equals("")
					|| !buscadorClientesDto.getNumeroDocumento().equals("")
					|| (!buscadorClientesDto.getNombreCliente().equals("")
							&& !buscadorClientesDto.getPrimerApellido().equals(""))
					|| !buscadorClientesDto.getRazonSocial().equals("")) {
				QGListaClientesAction.setMetadatos(metadatos);
				metadatos.put("0", buscadorClientesDto);
			} else {
				metadatos = null;
			}
		}
		QGHistorialClienteDto[] arrayHistorial = (QGHistorialClienteDto[]) request.getSession()
				.getAttribute("historial");
		QGHistorialClienteDto[] arrayAuxiliar = new QGHistorialClienteDto[5];
		if (arrayHistorial == null) {
			arrayHistorial = new QGHistorialClienteDto[5];
		}

		arrayAuxiliar[0] = historialClienteDto;
		for (int i = 1; i < 5; i++) {
			arrayAuxiliar[i] = arrayHistorial[i - 1];
		}

		request.getSession().setAttribute("historial", arrayAuxiliar);
		logger.info("******---FIN actualizarHistorial de QGListaClientesAction---******");
	}

	public void buscarClienteHistorial(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("******---buscarClienteHistorial de QGListaClientesAction---******");

		QGListaClientesForm listaClientesForm = (QGListaClientesForm) form;

		// obtenemos los datos en el dto
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		// Seteamos los datos del usuario
		// setDatosUsuario(request, consentimientosDto);

		JSONObject jsonObject = JSONObject.fromObject(listaClientesForm.getListaClientesJSON());

		QGHistorialClienteDto historialClienteDto = (QGHistorialClienteDto) JSONObject.toBean(jsonObject,
				QGHistorialClienteDto.class);

		try {

			QGHistorialClienteDto[] arrayHistorial = (QGHistorialClienteDto[]) request.getSession()
					.getAttribute("historial");

			for (int i = 0; i < 5 && arrayHistorial[i] != null; i++) {
				QGHistorialClienteDto clienteDto = arrayHistorial[i];

				if (clienteDto.getCodCliente().equals(historialClienteDto.getCodCliente())) {
					QGCGlobalDto.getlistaDatos().add(clienteDto);

				}
			}
		} catch (Exception ex) {
			tratarMensajesExcepcion(response, ex);
		}

		if (QGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(QGCGlobalDto, response);
		}
		logger.info("******---FIN buscarClienteHistorial de QGListaClientesAction---******");
	}

	public void borrarHistorial(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("******---borrarHistorial de QGListaClientesAction---******");
		request.getSession().setAttribute("historial", null);
		logger.info("******---FIN borrarHistorial de QGListaClientesAction---******");
	}

	public void exportarPDF(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("******---exportarPDF de QGListaClientesAction---******");
		QGListaClientesForm listaClientesForm = (QGListaClientesForm) form;

		// obtenemos los datos en el dto
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		// Seteamos los datos del usuario
		// setDatosUsuario(request, consentimientosDto);

		JSONObject jsonObject = JSONObject.fromObject(listaClientesForm.getListaClientesJSON());

		QGBuscadorClientesDto buscadorClientesDto = (QGBuscadorClientesDto) JSONObject.toBean(jsonObject,
				QGBuscadorClientesDto.class);

		try {
			// obtenemos el resultado de la llamada al servicio
			QGCGlobalDto = this.getBuscadorClientesServicio().obtenerListadoClientes(buscadorClientesDto);

			if (QGCGlobalDto != null) {
				/*
				 * ArrayList listadoNuevo = new ArrayList(); for(int i = 0; i <
				 * QGCGlobalDto.getlistaDatos().size(); i++){
				 * 
				 * QGBuscadorClientesDto clientes =
				 * (QGBuscadorClientesDto)QGCGlobalDto.getlistaDatos().get(i);
				 * if(buscadorClientesDto.getUnidadNegocio().equals(QGConstantes.CODIGO_FIJO)){
				 * clientes.setUnidadNegocio(QGConstantes.LITERAL_FIJO); }else
				 * if(buscadorClientesDto.getUnidadNegocio().equals(QGConstantes.CODIGO_MOVIL)){
				 * clientes.setUnidadNegocio(QGConstantes.LITERAL_MOVIL); }else{
				 * clientes.setUnidadNegocio(QGConstantes.LITERAL_CONVERGENTE); } if
				 * (buscadorClientesDto.getModalidadMovil().equals(QGConstantes.CODIGO_MOVIL)){
				 * clientes.setModalidadMovil(QGConstantes.MODALIDAD_MOVIL); }else if
				 * (buscadorClientesDto.getModalidadMovil().equals(QGConstantes.CODIGO_PREPAGO))
				 * { clientes.setModalidadMovil(QGConstantes.MODALIDAD_PREPAGO); }else if
				 * (buscadorClientesDto.getModalidadMovil().equals(QGConstantes.CODIGO_AMBAS)){
				 * clientes.setModalidadMovil(QGConstantes.MODALIDAD_AMBAS); }
				 * listadoNuevo.add(clientes); }
				 */
				ByteArrayOutputStream baos = generarInformeServicio
						.generarInformeBuscadorClientesPDF(QGCGlobalDto.getlistaDatos());

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
		logger.info("******---FIN exportarPDF de QGListaClientesAction---******");
	}

	public void obtenerHistorico(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("******---obtenerHistorico de QGListaClientesAction---******");
		QGListaClientesForm listaClientesForm = (QGListaClientesForm) form;

		// obtenemos los datos en el dto
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		JSONObject jsonObject = JSONObject.fromObject(listaClientesForm.getListaClientesJSON());

		QGBuscadorClientesDto buscadorClientesDto = (QGBuscadorClientesDto) JSONObject.toBean(jsonObject,
				QGBuscadorClientesDto.class);

		try {

			// obtenemos el resultado de la llamada al servicio
			QGCGlobalDto = this.getBuscadorClientesServicio().obtenerHistoricoSegmentacion(buscadorClientesDto);

		} catch (Exception ex) {
			tratarMensajesExcepcion(response, ex);
		}

		if (QGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(QGCGlobalDto, response);
		}
		logger.info("******---FIN obtenerHistorico de QGListaClientesAction---******");
	}

	public QGBuscadorClientesServicio getBuscadorClientesServicio() {
		return buscadorClientesServicio;
	}

	public void setBuscadorClientesServicio(QGBuscadorClientesServicio buscadorClientesServicio) {
		this.buscadorClientesServicio = buscadorClientesServicio;
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
		QGListaClientesAction.metadatos = metadatos;
	}

	public QGAuditorServicio getAuditorServicio() {
		return auditorServicio;
	}

	public void setAuditorServicio(QGAuditorServicio auditorServicio) {
		this.auditorServicio = auditorServicio;
	}
}
