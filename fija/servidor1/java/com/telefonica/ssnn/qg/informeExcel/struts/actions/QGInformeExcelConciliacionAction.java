package com.telefonica.ssnn.qg.informeExcel.struts.actions;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.informeExcel.dto.QGEntradaInformeDto;
import com.telefonica.ssnn.qg.informeExcel.dto.QGInformeConciliacionDTO;
import com.telefonica.ssnn.qg.informeExcel.servicio.interfaz.QGInformeExcelServicio;
import com.telefonica.ssnn.qg.informeExcel.struts.forms.QGInformeExcelConciliacionForm;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.struts.QGBaseAction;

/**
 * Action de Informes de conciliacion.
 * 
 * @author jacastano
 *
 * @struts.action 
 *  name="QGInformeExcelConciliacionForm" 
 *  path="/Conciliacion" 
 *  input="/vConciliacion.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="buscar, exportarConciliacion, exportarConciliacionPDF"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vConciliacion.do"
 */
public class QGInformeExcelConciliacionAction extends QGBaseAction{

	private static final Logger logger = Logger.getLogger(QGInformeExcelConciliacionAction.class);
	
	private QGInformeExcelServicio informesExcelServicio; 
	private QGGenerarInformeServicio generarInformeServicio;

	public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return mapping.findForward(SUCCESS);
    }
	
	//métodos públicos
	public void buscar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		
		logger.info("******---buscar de QGInformeExcelConciliacionAction---******");		

    	 //casting del formulario
		QGInformeExcelConciliacionForm informesConciliacionExcelForm = (QGInformeExcelConciliacionForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(informesConciliacionExcelForm.getBusquedaJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGEntradaInformeDto entrada = (QGEntradaInformeDto) JSONObject.toBean(jsonObject, QGEntradaInformeDto.class);
    	
    	QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
        
    	try{
    		qGCGlobalDto = this.getInformesExcelServicio().buscarInforme(entrada);    		
    	}catch (Exception e) {
    		tratarMensajesExcepcion(response, e);
		}
    	
        if (qGCGlobalDto != null) {
        	 //enviamos los datos al js       	
    		escribirRespuestaJson(qGCGlobalDto, response);
        }
        
		logger.info("******---FINAL buscar de QGInformeExcelConciliacionAction---******");        
    }
	
	public void exportarConciliacionPDF(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		
		logger.info("******---exportarConciliacionPDF de QGInformeExcelConciliacionAction---******");		
		
		 //casting del formulario
		QGInformeExcelConciliacionForm informesConciliacionExcelForm = (QGInformeExcelConciliacionForm) form;
		
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(informesConciliacionExcelForm.getBusquedaJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGEntradaInformeDto entrada = (QGEntradaInformeDto) JSONObject.toBean(jsonObject, QGEntradaInformeDto.class);
    	
    	QGCGlobalDto qGCGlobalDto = null;
        
        try {
            // obtenemos el resultado de la llamada al servicio
        	qGCGlobalDto =  this.getInformesExcelServicio().buscarInforme(entrada);
        	
        	QGInformeConciliacionDTO informe = (QGInformeConciliacionDTO) qGCGlobalDto.getlistaDatos().get(0);
        	
        	if (qGCGlobalDto != null) {
        		ByteArrayOutputStream baos = generarInformeServicio.generarInformeConciliacionPDF(informe);
        		response.setContentType("application/pdf");
    	   		response.addHeader("Content-Disposition", "attachment; filename=\"informe.pdf\"");
    	   		response.setHeader("Content-Transfer-Encoding","binary");
    	   		response.setContentLength(baos.toByteArray().length);
    	   		response.getOutputStream().write(baos.toByteArray());
    	   		response.getOutputStream().flush();   	
    	   		response.getOutputStream().close();
            }
        	
    		logger.info("******---FINAL exportarConciliacionPDF de QGInformeExcelConciliacionAction---******");        	
        } catch (Exception ex) {
            logger.info(ex);
        }	   
    }
	
	public void exportarConciliacion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		
		logger.info("******---exportarConciliacion de QGInformeExcelConciliacionAction---******");		

		 //casting del formulario
		QGInformeExcelConciliacionForm informesConciliacionExcelForm = (QGInformeExcelConciliacionForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(informesConciliacionExcelForm.getBusquedaJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGEntradaInformeDto entrada = (QGEntradaInformeDto) JSONObject.toBean(jsonObject, QGEntradaInformeDto.class);
    	
    	QGCGlobalDto qGCGlobalDto = null;
        
    	try{
    		qGCGlobalDto =  this.getInformesExcelServicio().buscarInforme(entrada);
    		
    		QGInformeConciliacionDTO informe = (QGInformeConciliacionDTO) qGCGlobalDto.getlistaDatos().get(0);
    		
			ByteArrayOutputStream baos = this.getGenerarInformeServicio().generarInformeExcelConciliacion(informe);
	   		
	   		response.setContentType("application/excel");
	   		
	   		response.addHeader("Content-Disposition", "attachment; filename=\"informe.xls\"");
	   		
	   		response.setContentLength(baos.toByteArray().length);
	   		
	   		response.getOutputStream().write(baos.toByteArray());
	   		
	   		response.getOutputStream().flush();    		
    	}catch (Exception e) {
    		tratarMensajesExcepcion(response, e);
		}
    	
        if (qGCGlobalDto != null) {
        	 //enviamos los datos al js       	
    		escribirRespuestaJson(qGCGlobalDto, response);
        }
		logger.info("******---FINAL exportarConciliacion de QGInformeExcelConciliacionAction---******");		
    }
	
	public QGInformeExcelServicio getInformesExcelServicio() {
		return informesExcelServicio;
	}

	public void setInformesExcelServicio(
			QGInformeExcelServicio informesExcelServicio) {
		this.informesExcelServicio = informesExcelServicio;
	}

	public QGGenerarInformeServicio getGenerarInformeServicio() {
		return generarInformeServicio;
	}

	public void setGenerarInformeServicio(
			QGGenerarInformeServicio generarInformeServicio) {
		this.generarInformeServicio = generarInformeServicio;
	}
	
	
}

