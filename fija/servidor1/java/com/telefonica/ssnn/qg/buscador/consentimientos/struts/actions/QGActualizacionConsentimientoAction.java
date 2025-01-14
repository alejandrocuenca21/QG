/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.consentimientos.struts.actions;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGActualizacionCDDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGSituacionesDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.servicio.interfaz.QGConsentimientosClientesServicio;
import com.telefonica.ssnn.qg.buscador.consentimientos.struts.forms.QGActualizacionConsentiemientoForm;
import com.telefonica.ssnn.qg.struts.QGBaseAction;

import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCombosComunesDto;

/**
 * Action de Actualizacion de Clientes.
 * 
 * @author vsierra
 *
 * @struts.action 
 *  name="QGActualizacionConsentiemientoForm" 
 *  path="/ActualizacionConsentimiento" 
 *  input="/vActualizacionConsentimiento.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="actualizar"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vActualizacionConsentimiento.do"
 */
public class QGActualizacionConsentimientoAction extends QGBaseAction {
	QGConsentimientosClientesServicio consentimientosServicio;
	
	public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return mapping.findForward(SUCCESS);
    }
    
    public void actualizar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	QGActualizacionConsentiemientoForm actualizacionConsentiemientoForm = (QGActualizacionConsentiemientoForm) form;
        
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();
        
        //Seteamos los datos del usuario
        //setDatosUsuario(request, consentimientosDto);
        
        JSONObject jsonObject = JSONObject.fromObject(actualizacionConsentiemientoForm.getActualizacionClienteJSON());
        
        HashMap mapa = new HashMap();
    	mapa.put(QGConstantes.GRUPO_SITUACION_ACTUAL, QGSituacionesDto.class);
    	mapa.put(QGConstantes.GRUPO_SITUACION_NUEVA, QGCombosComunesDto.class);
        
        QGActualizacionCDDto actualizacionCDDto = (QGActualizacionCDDto) JSONObject.toBean(jsonObject, QGActualizacionCDDto.class, mapa);
        
        try {
            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getConsentimientosServicio().actualizacionCliente(actualizacionCDDto);
        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
    }

	public QGConsentimientosClientesServicio getConsentimientosServicio() {
		return consentimientosServicio;
	}

	public void setConsentimientosServicio(
			QGConsentimientosClientesServicio consentimientosServicio) {
		this.consentimientosServicio = consentimientosServicio;
	}
}
