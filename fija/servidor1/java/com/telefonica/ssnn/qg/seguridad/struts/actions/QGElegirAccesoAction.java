/**
 * 
 */
package com.telefonica.ssnn.qg.seguridad.struts.actions;


import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGInformesConvivenciaServicio;
import com.telefonica.ssnn.qg.seguridad.servicio.impl.QGLdapUserDetailsService;
import com.telefonica.ssnn.qg.struts.QGBaseAction;



/**
 * Action de Documento Duplicado.
 * 
 * @author action
 *
 * @struts.action 
 *  name="QGElegirAccesoForm" 
 *  path="/QGElegirAcceso" 
 *  input="/vQGElegirAcceso.do" 
 *  scope="request" 
 *  redirect="true"
 *  validate="false" 
 *  parameter="seleccionar"
 *  type="org.springframework.web.struts.DelegatingActionProxy"
 * 
 * @struts.action-forward 
 *  name="success" 
 *  path="/vQGElegirAcceso.do"
 */
public class QGElegirAccesoAction extends QGBaseAction {
	
	private static final Logger logger = Logger.getLogger(QGElegirAccesoAction.class);
	
	
	
	public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return mapping.findForward(SUCCESS);
    }
    
    public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	logger.info("Test");
    	logger.info("Test");
    	logger.info("Test");

    	  return mapping.findForward(SUCCESS);
    }
    
    public ActionForward accesoSeleccionado(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	logger.info("Test");
    	logger.info("Test");
    	logger.info("Test");
   
    	  return mapping.findForward(SUCCESS);

    }
    

}
