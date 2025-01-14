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

import com.telefonica.ssnn.qg.administracion.campanias.servicio.interfaz.QGCampaniasServicio;
import com.telefonica.ssnn.qg.administracion.consentimientos.servicio.interfaz.QGConsentimientosServicio;
import com.telefonica.ssnn.qg.administracion.medios.servicio.interfaz.QGMediosServicio;
import com.telefonica.ssnn.qg.administracion.ubicacion.servicio.interfaz.QGUbicacionServicio;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGDetalleCDDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.servicio.interfaz.QGConsentimientosClientesServicio;
import com.telefonica.ssnn.qg.buscador.consentimientos.struts.forms.QGDetalleConsentimientoForm;
import com.telefonica.ssnn.qg.struts.QGBaseAction;

import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * Action de Buscador de Clientes.
 * 
 * @author vsierra
 *
 * @struts.action 
 *  name="QGDetalleConsentimientoForm" 
 *  path="/DetalleConsentimiento" 
 *  input="/vDetalleConsentimiento.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="buscar, cargarCombosConsentimientos, cargarComboMedios"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vDetalleConsentimiento.do"
 */
public class QGDetalleConsentimientoAction extends QGBaseAction {
	QGConsentimientosClientesServicio consentimientosServicio;
	QGConsentimientosServicio consentimientosServicioAdmin;
	QGCampaniasServicio campaniasServicio;
	QGMediosServicio mediosServicio;
	QGUbicacionServicio ubicacionServicio;
	
	public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return mapping.findForward(SUCCESS);
    }
    
    public void buscar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	QGDetalleConsentimientoForm detalleConsentimientoForm = (QGDetalleConsentimientoForm) form; 
        
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();
        
        JSONObject jsonObject = JSONObject.fromObject(detalleConsentimientoForm.getDetalleConsentimientoJSON());

        QGDetalleCDDto detalleCDDto = (QGDetalleCDDto) JSONObject.toBean(jsonObject, QGDetalleCDDto.class);
        
        try {

            // obtenemos el resultado de la llamada al servicio
        	QGCGlobalDto = this.getConsentimientosServicio().obtenerDetalleCliente(detalleCDDto);
                    

        } catch (Exception ex) {
            tratarMensajesExcepcion(response, ex);
        }

        if (QGCGlobalDto != null) {
        	// enviamos los datos al js
        	escribirRespuestaJson(QGCGlobalDto, response);
        }
    }
    
    public void cargarCombosConsentimientos(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	HashMap datos = new HashMap();
      
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = null;
    	
    	  try {

    		//Obtenemos la lista de Tipo de comunicacion
        	QGCGlobalDto = this.getConsentimientosServicio().obtenerListaTipoOperacion();
        	//Añadimos la lista de Tipo de Objeto a la respuesta JSON
        	datos.put(QGConstantes.TIPOS_COMUNICACION, QGCGlobalDto);
            	
    		//Obtenemos la lista de estado de la gestion
          	QGCGlobalDto = this.getConsentimientosServicio().obtenerListaEstadoGestion();
          	//Añadimos los niveles de aplicacion a la respuesta JSON
          	datos.put(QGConstantes.ESTADO_GESTION, QGCGlobalDto);
          	
          	//Obtenemos la lista de Tipo de Objeto
          	QGCGlobalDto = this.getConsentimientosServicioAdmin().obtenerListaTipoObjetos();
          	//Añadimos la lista de Tipo de Objeto a la respuesta JSON
          	datos.put(QGConstantes.TIPOS_OBJETOS, QGCGlobalDto);

          	//Obtenemos la lista de Tipo Ubicacion
          	QGCGlobalDto = this.getUbicacionServicio().obtenerListaTiposUbicacion();
          	//Añadimos la lista de Tipo de Objeto a la respuesta JSON
          	datos.put(QGConstantes.TIPO_UBICACION, QGCGlobalDto);

          	//Obtenemos la lista de Medio de comunicacion         	
          	//Obtenemos la lista de Campanias
          	QGCGlobalDto = this.getCampaniasServicio().obtenerListaCampanias();
          	//Añadimos la lista de Tipo de Objeto a la respuesta JSON
          	datos.put(QGConstantes.CAMPANIAS, QGCGlobalDto);
          	
          	
        	datos.put(SUCCESS, Boolean.TRUE);
          	//Enviamos el objeto por JSON
          	enviarObjetoJSON(datos,response);
          	
          } catch (Exception ex) {
        	  datos.put(SUCCESS, Boolean.FALSE);
        	  //Enviamos el objeto por JSON
          	  enviarObjetoJSON(datos,response);
              tratarMensajesExcepcion(response, ex);
          }
    	
    }

    public void cargarComboMedios(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    		throws Exception {
    	
    	QGDetalleConsentimientoForm detalleConsentimientoForm = (QGDetalleConsentimientoForm) form; 
        
    	//obtenemos los datos en el dto
        QGCGlobalDto QGCGlobalDto = new QGCGlobalDto ();
        
        JSONObject jsonObject = JSONObject.fromObject(detalleConsentimientoForm.getDetalleConsentimientoJSON());

        QGDetalleCDDto detalleCDDto = (QGDetalleCDDto) JSONObject.toBean(jsonObject, QGDetalleCDDto.class);
    	
        HashMap datos = new HashMap();
        
    	try {
    		QGCGlobalDto = this.getMediosServicio().buscadorMediosCosenDer(detalleCDDto);
          	if (QGCGlobalDto.getlistaDatos().size() == 0){
          		QGCGlobalDto = this.getMediosServicio().obtenerListaMediosComunicacion();
          	}
	
          	//Añadimos la lista de Tipo de Objeto a la respuesta JSON
          	datos.put(QGConstantes.MEDIOS_COMUNICACION, QGCGlobalDto);
          	//Enviamos el objeto por JSON
          	enviarObjetoJSON(datos,response);
		} catch (Exception ex) {
			datos.put(SUCCESS, Boolean.FALSE);
      	  //Enviamos el objeto por JSON
        	  enviarObjetoJSON(datos,response);
            tratarMensajesExcepcion(response, ex);
		}
    }
    
	public QGConsentimientosClientesServicio getConsentimientosServicio() {
		return consentimientosServicio;
	}

	public void setConsentimientosServicio(
			QGConsentimientosClientesServicio consentimientosServicio) {
		this.consentimientosServicio = consentimientosServicio;
	}

	public QGCampaniasServicio getCampaniasServicio() {
		return campaniasServicio;
	}

	public void setCampaniasServicio(QGCampaniasServicio campaniasServicio) {
		this.campaniasServicio = campaniasServicio;
	}

	public QGMediosServicio getMediosServicio() {
		return mediosServicio;
	}

	public void setMediosServicio(QGMediosServicio mediosServicio) {
		this.mediosServicio = mediosServicio;
	}

	public QGUbicacionServicio getUbicacionServicio() {
		return ubicacionServicio;
	}

	public void setUbicacionServicio(QGUbicacionServicio ubicacionServicio) {
		this.ubicacionServicio = ubicacionServicio;
	}

	public QGConsentimientosServicio getConsentimientosServicioAdmin() {
		return consentimientosServicioAdmin;
	}

	public void setConsentimientosServicioAdmin(
			QGConsentimientosServicio consentimientosServicioAdmin) {
		this.consentimientosServicioAdmin = consentimientosServicioAdmin;
	}
}
