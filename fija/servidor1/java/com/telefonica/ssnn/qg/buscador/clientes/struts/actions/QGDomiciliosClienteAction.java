/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.clientes.struts.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.telefonica.ssnn.qg.buscador.clientes.dto.QGDatosClienteDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGDireccionesClienteDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGDireccionesClienteLNMDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGDireccionesClienteLogicasDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGDomiciliosClienteDto;
import com.telefonica.ssnn.qg.buscador.clientes.servicio.interfaz.QGBuscadorClientesServicio;
import com.telefonica.ssnn.qg.buscador.clientes.struts.forms.QGDomiciliosClienteForm;
import com.telefonica.ssnn.qg.struts.QGBaseAction;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * Action de Domicilios Cliente.
 * 
 * @author cnunezba
 *
 * @struts.action 
 *  name="QGDomiciliosClienteForm" 
 *  path="/DomiciliosCliente" 
 *  input="/vDomiciliosCliente.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="buscar"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vDomiciliosCliente.do"
 */
public class QGDomiciliosClienteAction extends QGBaseAction {
	QGBuscadorClientesServicio buscadorClientesServicio;
	
	public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return mapping.findForward(SUCCESS);
    }
    
    public void buscar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	QGDomiciliosClienteForm domiciliosClienteForm = (QGDomiciliosClienteForm) form;
        
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();
        
        //Seteamos los datos del usuario
        //setDatosUsuario(request, consentimientosDto);
        
        JSONObject jsonObject = JSONObject.fromObject(domiciliosClienteForm.getDomiciliosJSON());
        
        QGDatosClienteDto datosClienteDto = (QGDatosClienteDto) JSONObject.toBean(jsonObject, QGDatosClienteDto.class);
        ArrayList listaDatos = new ArrayList();
        try {
            // obtenemos el resultado de la llamada al servicio
        	 QGCGlobalDto = this.getBuscadorClientesServicio().obtenerDireccionesCliente(datosClienteDto.getCodigoCliente());
            //QGDomiciliosClienteDto domiciliosClienteDto = (QGDomiciliosClienteDto) QGCGlobalDto.getlistaDatos().get(0);

        	QGDomiciliosClienteDto domiciliosClienteDto = new QGDomiciliosClienteDto();

            
            
            
        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }
        
        
        if (QGCGlobalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        	
        }
    }

	public QGBuscadorClientesServicio getBuscadorClientesServicio() {
		return buscadorClientesServicio;
	}

	public void setBuscadorClientesServicio(
			QGBuscadorClientesServicio buscadorClientesServicio) {
		this.buscadorClientesServicio = buscadorClientesServicio;
	}
}
