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
import com.telefonica.ssnn.qg.informes.struts.forms.QGMovimientosTratadosForm;
import com.telefonica.ssnn.qg.struts.QGBaseAction;

import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * Action de Movimientos Tratados.
 * 
 * @author cnunezba
 *
 * @struts.action 
 *  name="QGMovimientosTratadosForm" 
 *  path="/MovimientosTratados" 
 *  input="/vMovimientosTratados.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="buscar, exportar, exportarPDF"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vMovimientosTratados.do"
 */
public class QGMovimientosTratadosAction extends QGBaseAction{
	
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
		QGMovimientosTratadosForm movimientosTratadosForm = (QGMovimientosTratadosForm) form;
    	
    	//Obtenemos la cadena JSON
    	JSONObject jsonObject = JSONObject.fromObject(movimientosTratadosForm.getMovimientosTratadosJSON());
    	
    	//Convertir la cadena JSON en el DTO
    	QGEstadisticasDto estadisticasDto = (QGEstadisticasDto) JSONObject.toBean(jsonObject, QGEstadisticasDto.class);
    	
    	QGCGlobalDto qGCGlobalDto = null;
        
    	try{
    		estadisticasDto.setTipoInforme(QGConstantes.CODIGO_ERROR);
    		qGCGlobalDto = this.getInformesConvivenciaServicio().buscarErroresEstadisticas(estadisticasDto);
    	}catch (Exception e) {
    		tratarMensajesExcepcion(response, e);
		}
    	
    	if (estadisticasDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(qGCGlobalDto, response);
        }
    }
	
	public void exportar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {


        
	   	 // casting del formulario
		QGMovimientosTratadosForm movimientosTratadosForm = (QGMovimientosTratadosForm) form;
	   	
	   	 //Seteamos los datos del usuario
	       //setDatosUsuario(request, TiposUbicacionDto);
	   	
	   	//Obtenemos la cadena JSON
	   	JSONObject jsonObject = JSONObject.fromObject(movimientosTratadosForm.getMovimientosTratadosJSON());
	   	
	   	//Convertir la cadena JSON en el DTO
	   	QGEstadisticasDto estadisticasDto = (QGEstadisticasDto) JSONObject.toBean(jsonObject, QGEstadisticasDto.class);
	   	
	   	QGCGlobalDto qGCGlobalDto = new  QGCGlobalDto();
	       
	   	try{
	   		QGInformeDto informeDto = new QGInformeDto();
	   		QGBuscadorDto buscadorDto = new QGBuscadorDto();
	   		
//	   		buscadorDto.setFxInicio(estadisticasDto.getFxInicio());
//	   		buscadorDto.setFxFin(estadisticasDto.getFxFin());
	   		
	   		buscadorDto.setFxBusqueda(estadisticasDto.getFxBusqueda());
	   		
			qGCGlobalDto = this.getInformesConvivenciaServicio().buscadorErrores(buscadorDto);
	   		
			informeDto.setListaClientes(qGCGlobalDto.getlistaDatos());		
			
			estadisticasDto.setTipoInforme(QGConstantes.CODIGO_ERROR);
			
			qGCGlobalDto = getInformesConvivenciaServicio().obtenerEstadisticas(estadisticasDto);
	   		
	   		informeDto.setEstadisticasDto((QGEstadisticasDto)qGCGlobalDto.getlistaDatos().get(0));
	   		
	   		ByteArrayOutputStream baos = this.getGenerarInformeServicio().generarInformeErrores(informeDto);
	   		
	   		response.setContentType("application/excel");
	   		
	   		response.addHeader("Content-Disposition", "attachment; filename=\"informe.xls\"");
	   		
	   		response.setContentLength(baos.toByteArray().length);
	   		
	   		response.getOutputStream().write(baos.toByteArray());
	   		
	   		response.getOutputStream().flush();
	   		
	   	}catch (Exception e) {
	   		
		}
	   	
    }
	
	public void exportarPDF(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {


        
		QGMovimientosTratadosForm movimientosTratadosForm = (QGMovimientosTratadosForm) form;
	   	
	   	 //Seteamos los datos del usuario
	       //setDatosUsuario(request, TiposUbicacionDto);
	   	
	   	//Obtenemos la cadena JSON
	   	JSONObject jsonObject = JSONObject.fromObject(movimientosTratadosForm.getMovimientosTratadosJSON());
	   	
	   	//Convertir la cadena JSON en el DTO
	   	QGEstadisticasDto estadisticasDto = (QGEstadisticasDto) JSONObject.toBean(jsonObject, QGEstadisticasDto.class);
	   	
	   	QGCGlobalDto qGCGlobalDto = new  QGCGlobalDto();
	       
	   	try{
	   		QGInformeDto informeDto = new QGInformeDto();
	   		QGBuscadorDto buscadorDto = new QGBuscadorDto();
	   		
//	   		buscadorDto.setFxInicio(estadisticasDto.getFxInicio());
//	   		buscadorDto.setFxFin(estadisticasDto.getFxFin());
	   		
	   		buscadorDto.setFxBusqueda(estadisticasDto.getFxBusqueda());
	   		
			qGCGlobalDto = this.getInformesConvivenciaServicio().buscadorErrores(buscadorDto);
	   		
			informeDto.setListaClientes(qGCGlobalDto.getlistaDatos());		
			
			estadisticasDto.setTipoInforme(QGConstantes.CODIGO_ERROR);
			
			qGCGlobalDto = getInformesConvivenciaServicio().obtenerEstadisticas(estadisticasDto);
	   		
	   		informeDto.setEstadisticasDto((QGEstadisticasDto)qGCGlobalDto.getlistaDatos().get(0));
	   		
	   		ByteArrayOutputStream baos = this.getGenerarInformeServicio().generarInformeErroresPDF(informeDto);
	   		
	   		response.setContentType("application/pdf");
	   		
	   		response.addHeader("Content-Disposition", "attachment; filename=\"informe.pdf\"");
	   		
	   		response.setContentLength(baos.toByteArray().length);
	   		
	   		response.getOutputStream().write(baos.toByteArray());
	   		
	   		response.getOutputStream().flush();
	   		
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
