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

import com.telefonica.ssnn.qg.administracion.encartes.dto.QGPOEncartesDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaSegmentacionesPresegDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaSegmentacionesReglasDto;
import com.telefonica.ssnn.qg.administracion.segmentacion.servicio.interfaz.QGSegmenRegContratosServicio;
import com.telefonica.ssnn.qg.administracion.segmentacion.servicio.interfaz.QGSegmentacionesServicio;
import com.telefonica.ssnn.qg.administracion.segmentacion.struts.forms.QGSegmenRegContratosForm;
import com.telefonica.ssnn.qg.administracion.segmentacion.struts.forms.QGSegmentacionesPresegForm;
import com.telefonica.ssnn.qg.administracion.segmentacion.struts.forms.QGSegmentacionesReglasForm;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.struts.QGBaseAction;


/**
 * Action de Contratos
 * 
 * @struts.action 
 * name="QGSegmenRegContratosForm" 
 * path="/Contratos"
 * input="/vContratos.do" 
 * scope="request" 
 * redirect="true"
 * validate="false" 
 * parameter="irContratos,obtenerUsuario, buscar, exportarPDF"
 * type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 * name="success" 
 * path="/vContratos.do"

*  @struts.action-forward 
*  name="irContratos" 
*  path="/vContratos.do"
*/

public class QGSegmenRegContratosAction extends QGBaseAction {

	/**
	 * logger
	 */
	private static final Logger logger = Logger
			.getLogger(QGSegmenRegContratosAction.class);
	
	//Atributo para llamar a métodos de Reglas
	private QGSegmenRegContratosServicio segmentacionesServicio;
	//Atributo para llamar a métodos de Segmentaciones
	private QGSegmentacionesServicio segmentacionesSer;
	//Atributo para llamar a métodos de GenerarInforme
	private QGGenerarInformeServicio generarInformeServicio;
	

    public ActionForward irContratos(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {


    	return mapping.findForward(QGConstantes.IR_CONTRATOS);
    	
    }
	
	
	public void buscar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Realizando busqueda de Contratos");

		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();

		QGSegmenRegContratosForm segmentacionesForm = (QGSegmenRegContratosForm) form;
		
		try {
			// obtenemos el resultado de la llamada al servicio
			qGCGlobalDto = this.getSegmentacionesServicio().obtenerContratos(segmentacionesForm.getInActuacion());
		
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

		logger.info("Realizando busqueda de Contratos");

		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();

		QGSegmenRegContratosForm segmentacionesForm = (QGSegmenRegContratosForm) form;
		
		try {
			// obtenemos el resultado de la llamada al servicio
			qGCGlobalDto = this.getSegmentacionesServicio().obtenerContratos(segmentacionesForm.getInActuacion());

        	if (qGCGlobalDto != null) {
            	
        		ByteArrayOutputStream baos = generarInformeServicio.generarInformeContratosPDF(qGCGlobalDto.getlistaDatos());
        		
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


	public QGSegmenRegContratosServicio getSegmentacionesServicio() {
		return segmentacionesServicio;
	}

	public void setSegmentacionesServicio(
			QGSegmenRegContratosServicio segmentacionesServicio) {
		this.segmentacionesServicio = segmentacionesServicio;
	}

}
