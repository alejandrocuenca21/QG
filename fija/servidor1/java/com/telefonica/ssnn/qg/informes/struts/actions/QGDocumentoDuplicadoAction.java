/**
 * 
 */
package com.telefonica.ssnn.qg.informes.struts.actions;


import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.telefonica.ssnn.qg.informes.dto.QGBuscadorDto;
import com.telefonica.ssnn.qg.informes.dto.QGEstadisticasDto;
import com.telefonica.ssnn.qg.informes.dto.QGInformeDto;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGInformesConvivenciaServicio;
import com.telefonica.ssnn.qg.informes.struts.forms.QGDocumentoDuplicadoForm;
import com.telefonica.ssnn.qg.struts.QGBaseAction;

import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * Action de Documento Duplicado.
 * 
 * @author cnunezba
 *
 * @struts.action 
 *  name="QGDocumentoDuplicadoForm" 
 *  path="/DocumentoDuplicado" 
 *  input="/vDocumentoDuplicado.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="buscar, exportar, exportarPDF"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vDocumentoDuplicado.do"
 */
public class QGDocumentoDuplicadoAction extends QGBaseAction {
	
	private QGInformesConvivenciaServicio informesConvivenciaServicio;
	private QGGenerarInformeServicio generarInformeServicio;
	
	private static final Logger logger = Logger.getLogger(QGDocumentoDuplicadoAction.class);
	
	public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return mapping.findForward(SUCCESS);
    }
    
    public void buscar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {


        
    	 // casting del formulario
    	QGDocumentoDuplicadoForm documentoDuplicadoForm = (QGDocumentoDuplicadoForm) form;
    	
    	 //Seteamos los datos del usuario
        //setDatosUsuario(request, TiposUbicacionDto);
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(documentoDuplicadoForm.getDocumentoDuplicadoJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGEstadisticasDto estadisticasDto = (QGEstadisticasDto) JSONObject.toBean(jsonObject, QGEstadisticasDto.class);
    	
    	QGCGlobalDto QGCGlobalDto = null;
        
    	try{
    		estadisticasDto.setTipoInforme(QGConstantes.CODIGO_DUPLICADO);
    		QGCGlobalDto = this.getInformesConvivenciaServicio().buscarClientesEstadisticas(estadisticasDto);
    	}catch (Exception e) {
    		tratarMensajesExcepcion(response, e);
		}
    	
    	if (estadisticasDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
    }
    
        
    public void exportar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {


        
	   	 // casting del formulario
	   	QGDocumentoDuplicadoForm documentoDuplicadoForm = (QGDocumentoDuplicadoForm) form;
	   	
	   	 //Seteamos los datos del usuario
	       //setDatosUsuario(request, TiposUbicacionDto);
	   	
	   	//Obtenemos la cadena JSON
	   	JSONObject jsonObject = JSONObject.fromObject(documentoDuplicadoForm.getDocumentoDuplicadoJSON());
	   	
	   	//Convertir la cadena JSON en el DTO
	   	QGEstadisticasDto estadisticasDto = (QGEstadisticasDto) JSONObject.toBean(jsonObject, QGEstadisticasDto.class);
	   	
	   	QGCGlobalDto QGCGlobalDto = new  QGCGlobalDto();
	       
	   	try{
	   		QGInformeDto informeDto = new QGInformeDto();
	   		QGBuscadorDto buscadorDto = new QGBuscadorDto();
	   		
	   		buscadorDto.setFxInicio(estadisticasDto.getFxInicio());
	   		buscadorDto.setFxFin(estadisticasDto.getFxFin());
	   		
	   		QGCGlobalDto = this.getInformesConvivenciaServicio().buscadorClientesDuplicados(buscadorDto);
	   		
			informeDto.setListaClientes(QGCGlobalDto.getlistaDatos());		
			
			estadisticasDto.setTipoInforme(QGConstantes.CODIGO_DUPLICADO);
			
			QGCGlobalDto = getInformesConvivenciaServicio().obtenerEstadisticas(estadisticasDto);
	   		
	   		informeDto.setEstadisticasDto((QGEstadisticasDto)QGCGlobalDto.getlistaDatos().get(0));
	   		
	   		QGCGlobalDto = this.getInformesConvivenciaServicio().buscadorClientesDuplicados(buscadorDto);
	   	
	   		ByteArrayOutputStream baos = this.getGenerarInformeServicio().generarInformeClientes(informeDto);
	   		
	   		response.setContentType("application/ms-excel");
	   		
	   		response.addHeader("Content-Disposition", "attachment; filename=\"informe.xls\"");
	   		response.setHeader("Content-Transfer-Encoding","binary");
	   		
	   		response.setContentLength(baos.toByteArray().length);
	   		
	   		response.getOutputStream().write(baos.toByteArray());
	   		
	   		response.getOutputStream().flush();   	
	   		
	   		response.getOutputStream().close();	   		
	   		
	   	}catch (Exception e) {
	   		logger.info(e);
		}
	   	
    }
    
    
    public void exportarPDF(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {


        
	   	 // casting del formulario
	   	QGDocumentoDuplicadoForm documentoDuplicadoForm = (QGDocumentoDuplicadoForm) form;
	   	
	   	 //Seteamos los datos del usuario
	       //setDatosUsuario(request, TiposUbicacionDto);
	   	
	   	//Obtenemos la cadena JSON
	   	JSONObject jsonObject = JSONObject.fromObject(documentoDuplicadoForm.getDocumentoDuplicadoJSON());
	   	
	   	//Convertir la cadena JSON en el DTO
	   	QGEstadisticasDto estadisticasDto = (QGEstadisticasDto) JSONObject.toBean(jsonObject, QGEstadisticasDto.class);
	   	
	   	QGCGlobalDto QGCGlobalDto = new  QGCGlobalDto();
	       
	   	try{
	   		QGInformeDto informeDto = new QGInformeDto();
	   		QGBuscadorDto buscadorDto = new QGBuscadorDto();
	   		
	   		buscadorDto.setFxInicio(estadisticasDto.getFxInicio());
	   		buscadorDto.setFxFin(estadisticasDto.getFxFin());
	   		
			QGCGlobalDto = this.getInformesConvivenciaServicio().buscadorClientesDuplicados(buscadorDto );
	   		
			informeDto.setListaClientes(QGCGlobalDto.getlistaDatos());
			
			//Tipo informe documento duplicados.
			estadisticasDto.setTipoInforme(QGConstantes.CODIGO_DUPLICADO);
			
			QGCGlobalDto = getInformesConvivenciaServicio().obtenerEstadisticas(estadisticasDto);
	   		
	   		informeDto.setEstadisticasDto((QGEstadisticasDto)QGCGlobalDto.getlistaDatos().get(0));
	   	
	   		ByteArrayOutputStream baos = this.getGenerarInformeServicio().generarInformeClientesPDF(informeDto);
	   		
	   		response.setContentType("application/pdf");
	   		
	   		response.addHeader("Content-Disposition", "attachment; filename=\"informe.pdf\"");
	   		response.setHeader("Content-Transfer-Encoding","binary");
	   		
	   		response.setContentLength(baos.toByteArray().length);
	   		
	   		response.getOutputStream().write(baos.toByteArray());
	   		
	   		response.getOutputStream().flush();   	
	   		
	   		response.getOutputStream().close();	   		
	   		
	   	}catch (Exception e) {
	   		logger.info(e);
		}
	   	
    }
    
    
    
    
    
    
    
    
    
	public QGInformesConvivenciaServicio getInformesConvivenciaServicio() {
		return informesConvivenciaServicio;
	}

	public void setInformesConvivenciaServicio(
			QGInformesConvivenciaServicio informesConvivenciaServicio) {
		this.informesConvivenciaServicio = informesConvivenciaServicio;
	}
	
	public QGGenerarInformeServicio getGenerarInformeServicio() {
		return generarInformeServicio;
	}

	public void setGenerarInformeServicio(
			QGGenerarInformeServicio generarInformeServicio) {
		this.generarInformeServicio = generarInformeServicio;
	}
}
