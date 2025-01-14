/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.clientes.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.telefonica.ssnn.qg.buscador.clientes.dto.QGBuscadorClientesDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGDatosClienteDto;
import com.telefonica.ssnn.qg.buscador.clientes.servicio.interfaz.QGBuscadorClientesServicio;
import com.telefonica.ssnn.qg.buscador.clientes.struts.forms.QGDatosGeneralesForm;
import com.telefonica.ssnn.qg.struts.QGBaseAction;
import com.telefonica.ssnn.qg.auditoria.servicio.interfaz.QGAuditorServicio;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * Action de Datos Generales.
 * 
 * @author vsierra
 *
 * @struts.action 
 *  name="QGDatosGeneralesForm" 
 *  path="/DatosGenerales" 
 *  input="/vDatosGenerales.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="buscar, irDetalle"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vDatosGenerales.do"
 *  
 *  @struts.action-forward 
 *  name="irDetalle"
 *  path="/vFichaCliente.do"
 */
public class QGDatosGeneralesAction extends QGBaseAction {
	
	private Logger logger = Logger.getLogger(QGDatosGeneralesAction.class);	
	
	private QGBuscadorClientesServicio buscadorClientesServicio;
	
	private QGAuditorServicio auditorServicio;
	
	public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return mapping.findForward(SUCCESS);
    }
    
    public void buscar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	logger.info("******---buscar de QGDatosGeneralesAction---******");

    	QGDatosGeneralesForm datosGeneralesForm = (QGDatosGeneralesForm) form;   	
        
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();              
        
        //Seteamos los datos del usuario
        //setDatosUsuario(request, consentimientosDto);
        
        JSONObject jsonObject = JSONObject.fromObject(datosGeneralesForm.getDatosGeneralesJSON());                
        
        QGDatosClienteDto datosClienteDto  = (QGDatosClienteDto) JSONObject.toBean(jsonObject, QGDatosClienteDto.class);                
                try {        	        	

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getBuscadorClientesServicio().obtenerDatosCliente(datosClienteDto.getCodigoCliente());        	        	        	        	
        	
			String resultado=QGConstantes.DS_RESULTADO_OPERA_NOK;
			String resultadoNav = "No se ha encontrado el cliente.";
			if(QGCGlobalDto.getlistaDatos().size()>0) {
				resultado= QGConstantes.DS_RESULTADO_OPERA_OK;
				resultadoNav=datosClienteDto.getCodigoCliente();
			}
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_DET_CLIENTES,resultado, resultadoNav);
        } catch (Exception ex) {       	
			this.getAuditorServicio().altaAuditoria(QGConstantes.MONIT_DET_CLIENTES,
					QGConstantes.DS_RESULTADO_OPERA_ERROR, ex.getMessage());
            tratarMensajesExcepcion(response, ex);           
        }
        if (QGCGlobalDto != null) {   
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);       	
        }
    	logger.info("******---FIN buscar de QGDatosGeneralesAction---******");        
    }
    
    public ActionForward irDetalle(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {


    	return mapping.findForward(QGConstantes.IR_DETALLE);
    	
    }

	public QGBuscadorClientesServicio getBuscadorClientesServicio() {
		return buscadorClientesServicio;
	}

	public void setBuscadorClientesServicio(
			QGBuscadorClientesServicio buscadorClientesServicio) {
		this.buscadorClientesServicio = buscadorClientesServicio;
	}

	public QGAuditorServicio getAuditorServicio() {
		return auditorServicio;
	}

	public void setAuditorServicio(QGAuditorServicio auditorServicio) {
		this.auditorServicio = auditorServicio;
	}
	
	
}
