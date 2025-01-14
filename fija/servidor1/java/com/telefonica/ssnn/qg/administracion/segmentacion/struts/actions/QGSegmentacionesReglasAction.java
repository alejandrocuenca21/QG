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

import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaSegmentacionesReglasDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGSalidaSegmentacionesReglasDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.servicio.interfaz.QGSegmentacionesReglasServicio;
import com.telefonica.ssnn.qg.administracion.segmentacion.servicio.interfaz.QGSegmentacionesServicio;
import com.telefonica.ssnn.qg.administracion.segmentacion.struts.forms.QGSegmentacionesReglasForm;
import com.telefonica.ssnn.qg.auditoria.servicio.interfaz.QGAuditorServicio;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.struts.QGBaseAction;
import com.telefonica.ssnn.qg.util.QGCatalogoDto;

/**
 * Action de Reglas
 * 
 * @struts.action 
 * name="QGSegmentacionesReglasForm" 
 * path="/Reglas"
 * input="/vReglas.do" 
 * scope="request" 
 * redirect="true"
 * validate="false" 
 * parameter="irReglas, obtenerUsuario, cargarComboReglas, cargarReglas, cargarReglasSeg, cargarReglasTotal, exportarPDF, operarReglas"
 * type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 * name="success" 
 * path="/vSegmentacionesReglas.do"
 * 
 *  @struts.action-forward 
 *  name="irReglas" 
 *  path="/vReglas.do"
 */
 
public class QGSegmentacionesReglasAction extends QGBaseAction {

	/**
	 * logger
	 */
	private static final Logger logger = Logger
			.getLogger(QGSegmentacionesReglasAction.class);
	
	//Atributo para llamar a métodos de Reglas
	private QGSegmentacionesReglasServicio segmentacionesServicio;
	//Atributo para llamar a métodos de Segmentaciones
	private QGSegmentacionesServicio segmentacionesSer;
	//Atributo para llamar a métodos de GenerarInforme
	private QGGenerarInformeServicio generarInformeServicio;
	//Atributo para llamar a métodos de Auditor
	private QGAuditorServicio auditorServicio;


    public ActionForward irReglas(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {


    	return mapping.findForward(QGConstantes.IR_REGLAS);
    	
    }
	
	
	public void cargarComboReglas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("Cargando el combo de reglas");

		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		
		QGSegmentacionesReglasForm segmentacionesForm = (QGSegmentacionesReglasForm) form;

		try {
			
			qGCGlobalDto = this.getSegmentacionesServicio().obtenerDatosReglas(segmentacionesForm.getInActuacion());

			// buscamos en la lista para descartar los que no necesitamos
			List listaFinal = new ArrayList();
			int i = 0;
			while (i < qGCGlobalDto.getlistaDatos().size()) {
				QGCatalogoDto catalogoDto = (QGCatalogoDto) qGCGlobalDto
						.getlistaDatos().get(i);
				String codigo = catalogoDto.getCodigo();
				if (!codigo.equals("555") && !codigo.equals("SDM")) {
					listaFinal.add(catalogoDto);
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
	
	public void cargarReglas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("Buscando segun criterio reglas");
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		
		QGSegmentacionesReglasForm segmentacionesForm = (QGSegmentacionesReglasForm) form;
		
			JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm
					.getSegmentacionReglasJSON());

			QGEntradaSegmentacionesReglasDto busquedaSegmentacion = (QGEntradaSegmentacionesReglasDto) JSONObject
					.toBean(jsonObject,
							QGEntradaSegmentacionesReglasDto.class);

		try {	
			// obtenemos el resultado de la llamada al servicio
			QGCGlobalDto = this.getSegmentacionesServicio().obtenerListaReglas(busquedaSegmentacion);

			// buscamos en la lista para descartar los que no necesitamos
			List listaFinal = new ArrayList();
			int i = 0;
			while (i < QGCGlobalDto.getlistaDatos().size()) {
				QGSalidaSegmentacionesReglasDto salidaSegmentacionesReglasDto = (QGSalidaSegmentacionesReglasDto) QGCGlobalDto.getlistaDatos().get(i);
				String codigo = salidaSegmentacionesReglasDto.getRegla();
				if (!codigo.equals("555") && !codigo.equals("SDM")) {
					listaFinal.add(salidaSegmentacionesReglasDto);
				}
				i++;
			}
			QGCGlobalDto.setlistaDatos(listaFinal);

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			tratarMensajesExcepcion(response, ex);
		}
		if (QGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(QGCGlobalDto, response);
		}
	}
	
	public void cargarReglasSeg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("Buscando segun criterio segmentos");
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();
		
		QGSegmentacionesReglasForm segmentacionesForm = (QGSegmentacionesReglasForm) form;
		
			JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm
					.getSegmentacionReglasJSON());

			QGEntradaSegmentacionesReglasDto busquedaSegmentacion = (QGEntradaSegmentacionesReglasDto) JSONObject
					.toBean(jsonObject,
							QGEntradaSegmentacionesReglasDto.class);

		try {	
			// obtenemos el resultado de la llamada al servicio
			QGCGlobalDto = this.getSegmentacionesServicio().obtenerListaReglasSeg(busquedaSegmentacion);
			
			// buscamos en la lista para descartar los que no necesitamos
			List listaFinal = new ArrayList();
			int i = 0;
			while (i < QGCGlobalDto.getlistaDatos().size()) {
				QGSalidaSegmentacionesReglasDto salidaSegmentacionesReglasDto = (QGSalidaSegmentacionesReglasDto) QGCGlobalDto.getlistaDatos().get(i);
				String codigo = salidaSegmentacionesReglasDto.getRegla();
				if (!codigo.equals("555") && !codigo.equals("SDM")) {
					listaFinal.add(salidaSegmentacionesReglasDto);
				}
				i++;
			}
			QGCGlobalDto.setlistaDatos(listaFinal);
			
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			tratarMensajesExcepcion(response, ex);
		}
		if (QGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(QGCGlobalDto, response);
		}
	}
	
	public void cargarReglasTotal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("Buscando sin criterio");
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		QGSegmentacionesReglasForm segmentacionesForm = (QGSegmentacionesReglasForm) form;
		
			JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm
					.getSegmentacionReglasJSON());

			QGEntradaSegmentacionesReglasDto busquedaSegmentacion = (QGEntradaSegmentacionesReglasDto) JSONObject
					.toBean(jsonObject,
							QGEntradaSegmentacionesReglasDto.class);

		try {	
			// obtenemos el resultado de la llamada al servicio
			QGCGlobalDto = this.getSegmentacionesServicio().obtenerListaReglasTotal(busquedaSegmentacion);
			
			// buscamos en la lista para descartar los que no necesitamos
			List listaFinal = new ArrayList();
			int i = 0;
			while (i < QGCGlobalDto.getlistaDatos().size()) {
				QGSalidaSegmentacionesReglasDto salidaSegmentacionesReglasDto = (QGSalidaSegmentacionesReglasDto) QGCGlobalDto.getlistaDatos().get(i);
				String codigo = salidaSegmentacionesReglasDto.getRegla();
				if (!codigo.equals("555") && !codigo.equals("SDM")) {
					listaFinal.add(salidaSegmentacionesReglasDto);
				}
				i++;
			}
			QGCGlobalDto.setlistaDatos(listaFinal);
			
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			tratarMensajesExcepcion(response, ex);
		}
		if (QGCGlobalDto != null) {
			// enviamos los datos al js
			escribirRespuestaJson(QGCGlobalDto, response);
		}
	}
	
	public void exportarPDF(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

		logger.info("Buscando segun criterio para exportar a pdf");
		QGCGlobalDto QGCGlobalDto = new QGCGlobalDto();

		QGSegmentacionesReglasForm segmentacionesForm = (QGSegmentacionesReglasForm) form;
		
		JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm
				.getSegmentacionReglasJSON());

		QGEntradaSegmentacionesReglasDto busquedaSegmentacion = (QGEntradaSegmentacionesReglasDto) JSONObject
				.toBean(jsonObject,
						QGEntradaSegmentacionesReglasDto.class);

		try {	
			// obtenemos el resultado de la llamada al servicio
			if (busquedaSegmentacion.getultimaBusquedaTipo().equals("reglas"))
			{
				QGCGlobalDto = this.getSegmentacionesServicio().obtenerListaReglas(busquedaSegmentacion);
			}				
			else if (busquedaSegmentacion.getultimaBusquedaTipo().equals("segmentos"))
			{
				QGCGlobalDto = this.getSegmentacionesServicio().obtenerListaReglasSeg(busquedaSegmentacion);
			}
			else
			{
				QGCGlobalDto = this.getSegmentacionesServicio().obtenerListaReglasTotal(busquedaSegmentacion);			
			}

        	if (QGCGlobalDto != null) {
            	
        		ByteArrayOutputStream baos = generarInformeServicio.generarInformeReglasPDF(QGCGlobalDto.getlistaDatos());
        		
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
	 * Llamada Operar Reglas
	 */
	public void operarReglas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		try {
			
			QGSegmentacionesReglasForm segmentacionesForm = (QGSegmentacionesReglasForm) form;
			
			JSONObject jsonObject = JSONObject.fromObject(segmentacionesForm
					.getSegmentacionReglasJSON());

			QGEntradaSegmentacionesReglasDto entrada = (QGEntradaSegmentacionesReglasDto) JSONObject
					.toBean(jsonObject,
							QGEntradaSegmentacionesReglasDto.class);
			
			qGCGlobalDto = this.getSegmentacionesServicio().operarReglas(entrada);
			
			List listaMensajes = new ArrayList();
			// Si todo ha ido bien mostramos un mensaje de guardado en la
			// pantalla
			listaMensajes 
					.add("La Regla ha sido modificada correctamente");
			qGCGlobalDto.setlistaMensajes(listaMensajes);
			
    		String resultadoNav ="No se ha podido modificar la regla.";
    		String resultado = QGConstantes.DS_RESULTADO_OPERA_NOK;
        	if (qGCGlobalDto != null) {
    			resultadoNav = entrada.getRegla();
        		resultado = QGConstantes.DS_RESULTADO_OPERA_OK;
            }
        	this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_SEG_REGLAS,resultado, resultadoNav);
		} catch (Exception ex) {
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_SEG_REGLAS,
					QGConstantes.DS_RESULTADO_OPERA_ERROR, ex.getMessage());
			logger.error(ex.getMessage());
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
	
	/**
	 * GETTERS & SETTERS
	 */
	public QGGenerarInformeServicio getGenerarInformeServicio() {
		return generarInformeServicio;
	}
	public void setGenerarInformeServicio(
			QGGenerarInformeServicio generarInformeServicio) {
		this.generarInformeServicio = generarInformeServicio;
	}

	public QGSegmentacionesServicio getSegmentacionesSer() {
		return segmentacionesSer;
	}

	public void setSegmentacionesSer(QGSegmentacionesServicio segmentacionesSer) {
		this.segmentacionesSer = segmentacionesSer;
	}

	public QGSegmentacionesReglasServicio getSegmentacionesServicio() {
		return segmentacionesServicio;
	}

	public void setSegmentacionesServicio(
			QGSegmentacionesReglasServicio segmentacionesServicio) {
		this.segmentacionesServicio = segmentacionesServicio;
	}	
	
	public QGAuditorServicio getAuditorServicio() {
		return auditorServicio;
	}

	public void setAuditorServicio(QGAuditorServicio auditorServicio) {
		this.auditorServicio = auditorServicio;
	}
}
