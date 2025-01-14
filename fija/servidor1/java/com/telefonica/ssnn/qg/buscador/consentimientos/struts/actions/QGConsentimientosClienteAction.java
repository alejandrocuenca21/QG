/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.consentimientos.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.telefonica.ssnn.qg.administracion.consentimientos.servicio.interfaz.QGConsentimientosServicio;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGEntradaListaCDDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.servicio.interfaz.QGConsentimientosClientesServicio;
import com.telefonica.ssnn.qg.buscador.consentimientos.struts.forms.QGConsentimientosClienteForm;
import com.telefonica.ssnn.qg.struts.QGBaseAction;

import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * Action de Buscador de Clientes.
 * 
 * @author vsierra
 *
 * @struts.action 
 *  name="QGConsentimientosClienteForm" 
 *  path="/ConsentimientosCliente" 
 *  input="/vConsentimientosCliente.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="tipoObjeto,estadoGestion,buscar,irFiltrado"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vConsentimientosCliente.do"
 *  
 *  @struts.action-forward 
 *  name="irFiltrado"
 *  path="/vConsentimientosCliente.do"
 */
public class QGConsentimientosClienteAction extends QGBaseAction {

	QGConsentimientosClientesServicio consentimientosServicio;
	QGConsentimientosServicio servicio;
	public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return mapping.findForward(SUCCESS);
    }
    
    public void buscar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	QGConsentimientosClienteForm consentimientosClienteForm = (QGConsentimientosClienteForm) form;
    	
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();
        
        JSONObject jsonObject = JSONObject.fromObject(consentimientosClienteForm.getConsentimientosClientesJSON());
        
        QGEntradaListaCDDto entradaListaCDDto  = (QGEntradaListaCDDto) JSONObject.toBean(jsonObject, QGEntradaListaCDDto.class);
        
        try {

            // obtenemos el resultado de la llamada al servicio
        	if(entradaListaCDDto.getFxIniCambio() == null)
        		QGCGlobalDto = this.getConsentimientosServicio().obtenerListaTodosConsentimientos(entradaListaCDDto);
        	else
        		QGCGlobalDto = this.getConsentimientosServicio().obtenerListaFiltroConsentimientos(entradaListaCDDto);
                    

        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
    }
    
    public void tipoObjeto(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	QGCGlobalDto globalDto = null;
    	
    	try{
    		globalDto = this.getServicio().obtenerListaTipoObjetos();
    	} catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (globalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(globalDto, response);
        }
    }
    
    public void estadoGestion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	QGCGlobalDto globalDto = null;
    	
    	try{
    		globalDto = this.getConsentimientosServicio().obtenerListaEstadoGestion();
    	} catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (globalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(globalDto, response);
        }
    }
    
    public ActionForward irFiltrado(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {


    	return mapping.findForward(QGConstantes.IR_DETALLE);
    	
    }

	public QGConsentimientosClientesServicio getConsentimientosServicio() {
		return consentimientosServicio;
	}

	public void setConsentimientosServicio(
			QGConsentimientosClientesServicio consentimientosServicio) {
		this.consentimientosServicio = consentimientosServicio;
	}

	public com.telefonica.ssnn.qg.administracion.consentimientos.servicio.interfaz.QGConsentimientosServicio getServicio() {
		return servicio;
	}

	public void setServicio(
			com.telefonica.ssnn.qg.administracion.consentimientos.servicio.interfaz.QGConsentimientosServicio servicio) {
		this.servicio = servicio;
	}
}
