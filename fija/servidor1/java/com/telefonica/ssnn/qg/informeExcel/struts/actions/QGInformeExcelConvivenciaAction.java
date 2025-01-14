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
import com.telefonica.ssnn.qg.informeExcel.dto.QGInformeConvivenciaDTO;
import com.telefonica.ssnn.qg.informeExcel.servicio.interfaz.QGInformeExcelServicio;
import com.telefonica.ssnn.qg.informeExcel.struts.forms.QGInformeExcelConvivenciaForm;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.struts.QGBaseAction;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;

/**
 * Action de Informes de convivencia.
 * 
 * @author jacastano
 *
 * @struts.action 
 *  name="QGInformeExcelConvivenciaForm" 
 *  path="/Convivencia" 
 *  input="/vConvivencia.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="buscar, exportar, exportarPDF"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vConvivencia.do"
 */
public class QGInformeExcelConvivenciaAction extends QGBaseAction{

	private static final Logger logger = Logger.getLogger(QGInformeExcelConvivenciaAction.class);
	
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
		
		logger.info("******---buscar de QGInformeExcelConvivenciaAction---******");		

    	 //casting del formulario
		QGInformeExcelConvivenciaForm informesConvivenciaExcelForm = (QGInformeExcelConvivenciaForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(informesConvivenciaExcelForm.getBusquedaJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGEntradaInformeDto entrada = (QGEntradaInformeDto) JSONObject.toBean(jsonObject, QGEntradaInformeDto.class);
    	
    	QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
        
    	try{
    		if (entrada.getInforme().equals("1")){	
    			entrada.setFecha(QGUtilidadesFechasUtils.sumarDias(entrada.getFecha(), 1));
    		}
    		qGCGlobalDto = this.getInformesExcelServicio().buscarInforme(entrada);
    		    		    		
    	}catch (Exception e) {
    		tratarMensajesExcepcion(response, e);
		}    	
        if (qGCGlobalDto != null) {
        	 //enviamos los datos al js       	
    		escribirRespuestaJson(qGCGlobalDto, response);
        }
        
        logger.info("******---FINAL buscar de QGInformeExcelConvivenciaAction---******");        
    }
	
	public void exportarPDF(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		
		logger.info("******---exportarPDF de QGInformeExcelConvivenciaAction---******");		
		
		 //casting del formulario
		QGInformeExcelConvivenciaForm informesConvivenciaExcelForm = (QGInformeExcelConvivenciaForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(informesConvivenciaExcelForm.getBusquedaJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGEntradaInformeDto entrada = (QGEntradaInformeDto) JSONObject.toBean(jsonObject, QGEntradaInformeDto.class);
    	
    	QGCGlobalDto qGCGlobalDto = null;
        
        try {
        	entrada.setFecha(QGUtilidadesFechasUtils.sumarDias(entrada.getFecha(), 1));
            // obtenemos el resultado de la llamada al servicio
        	qGCGlobalDto =  this.getInformesExcelServicio().buscarInforme(entrada);
        	
        	QGInformeConvivenciaDTO informe = (QGInformeConvivenciaDTO) qGCGlobalDto.getlistaDatos().get(0);
        	
        	if (qGCGlobalDto != null) {
        		ByteArrayOutputStream baos = generarInformeServicio.generarInformeConvivenciaPDF(informe);
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

		logger.info("******---FINAL exportarPDF de QGInformeExcelConvivenciaAction---******");	   	
    }
	
	public void exportar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

		logger.info("******---exportar de QGInformeExcelConvivenciaAction---******");		
		
		 //casting del formulario
		QGInformeExcelConvivenciaForm informesConvivenciaExcelForm = (QGInformeExcelConvivenciaForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(informesConvivenciaExcelForm.getBusquedaJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGEntradaInformeDto entrada = (QGEntradaInformeDto) JSONObject.toBean(jsonObject, QGEntradaInformeDto.class);
    	
    	QGCGlobalDto qGCGlobalDto = null;
        
    	try{
        	entrada.setFecha(QGUtilidadesFechasUtils.sumarDias(entrada.getFecha(), 1));
    		qGCGlobalDto =  this.getInformesExcelServicio().buscarInforme(entrada);
    		
    		QGInformeConvivenciaDTO informe = (QGInformeConvivenciaDTO) qGCGlobalDto.getlistaDatos().get(0);
    		
			ByteArrayOutputStream baos = this.getGenerarInformeServicio().generarInformeExcelConvivencia(informe);
	   		
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
		logger.info("******---FINAL exportar de QGInformeExcelConvivenciaAction---******");		
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

