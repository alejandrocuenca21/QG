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

import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGEntradaHistorialDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.servicio.interfaz.QGConsentimientosClientesServicio;
import com.telefonica.ssnn.qg.buscador.consentimientos.struts.forms.QGHistorialConsentimientosForm;
import com.telefonica.ssnn.qg.struts.QGBaseAction;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * Action de Buscador de Clientes.
 * 
 * @author vsierra
 *
 * @struts.action 
 *  name="QGHistorialConsentimientosForm" 
 *  path="/HistorialConsentimientos" 
 *  input="/vHistorialConsentimientos.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="buscar"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vAdministracionClientes.do"
 */
public class QGHistorialConsentimientosAction extends QGBaseAction{
	private QGConsentimientosClientesServicio consentimientosClientesServicio;
	
	public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return mapping.findForward(SUCCESS);
    }
    
    public void buscar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	QGHistorialConsentimientosForm historialConsentimientosForm = (QGHistorialConsentimientosForm) form;
        
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();
        
        //Seteamos los datos del usuario
        //setDatosUsuario(request, consentimientosDto);
        
        JSONObject jsonObject = JSONObject.fromObject(historialConsentimientosForm.getHistorialJSON());
        
        QGEntradaHistorialDto entradaHistorialDto = (QGEntradaHistorialDto) JSONObject.toBean(jsonObject, QGEntradaHistorialDto.class);
        
        try {

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getConsentimientosClientesServicio().obtenerHistorialCliente(entradaHistorialDto );                    

        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
    }

	public QGConsentimientosClientesServicio getConsentimientosClientesServicio() {
		return consentimientosClientesServicio;
	}

	public void setConsentimientosClientesServicio(
			QGConsentimientosClientesServicio consentimientosClientesServicio) {
		this.consentimientosClientesServicio = consentimientosClientesServicio;
	}
}
