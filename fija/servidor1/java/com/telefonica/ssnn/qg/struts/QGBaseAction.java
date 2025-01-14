package com.telefonica.ssnn.qg.struts;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.dozer.util.mapping.MapperIF;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.EventDispatchAction;
import org.springframework.security.context.SecurityContextHolder;

import com.telefonica.ssnn.qg.seguridad.QGUsuario;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalPagingDto;
import com.telefonica.ssnn.qg.exceptions.QGApplicationException;

/**
 * Clase base para las actions de struts.
 * @author jacangas
 *
 */
public class QGBaseAction extends EventDispatchAction {
	
	private Logger logger = Logger.getLogger(QGBaseAction.class);	
	
	/**
     * Forward de exito.
     */
    protected static final String SUCCESS = "success";
    
    /**
     * Forward para retorno.
     */
    protected static final String BACK = "back";

    /**
     * Forward para paginas detalladas.
     */
    protected static final String DETAIL = "detail";
    /**
     * BeanMapper.
     */
    protected MapperIF beanMapper;
    /**
     * Metodo ejecutado por struts cuando no sabe a que metodo redirigir.
     * 
     * @param mapping - 
     *              ActionMapping
     * @param form -
     *              ActionForm
     * @param request -
     *              HttpServletRequest
     * @param response -
     *              HttpServletResponse
     * @return ActionForward -
     *              forward a la visual
     * @throws Exception -
     *              exception
     */

    protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        return doExecute(mapping, form, request, response);
    }
    
    /**
     * Metodo a sobreescribir por cada action.
     * 
     * @param mapping - 
     *              HttpServletResponse
     * @param form -
     *              ActionForm
     * @param request -
     *              HttpServletRequest
     * @param response -
     *              HttpServletResponse
     * @return ActionForward -
     *              forward a la visual
     * @throws Exception - 
     *              exception
     */
    protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        return null;
    }
    
    /**
     * Metodo a principal de la action.
     * 
     * @param mapping -
     *               HttpServletResponse
     * @param form - 
     *              ActionForm
     * @param request - 
     *              HttpServletRequest
     * @param response -
     *              HttpServletResponse
     * @return ActionForward -
     *              forward a la visual 
     * @throws Exception
     *              exception
     */

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
    	if (form instanceof QGBaseForm) {
    		
    		QGBaseForm baseForm = (QGBaseForm) form;
    		
    		QGUsuario usuarioLogado = getUsuarioLogado();
    		
    		baseForm.setUsuario(usuarioLogado.getUsername());
    		
    		String rol = usuarioLogado.getAuthorities()[0].getAuthority();
    		
    		baseForm.setPerfil(rol.substring(rol.length() - 2));
    	}    	    	    	    	    	    	
    	
    	return super.execute(mapping, form, request, response);    	
    }
    
    /**
     * Escribe los datos en formato json.
     * @param datos
     * @param response
     */
    protected void escribirRespuestaJson(QGCGlobalDto datos, HttpServletResponse response) {
    	
    	logger.info("******---escribirRespuestaJson de QGBaseAction---******");
    	
    	HashMap mapa = new HashMap();
    	   	
    	mapa.put("datos", datos.getlistaDatos());
    	mapa.put("message", datos.getlistaMensajes());
    	mapa.put("total", new Integer(datos.getlistaDatos().size()));
    	mapa.put("success", Boolean.TRUE);
    	   	
    	JSONObject jsonObject = JSONObject.fromObject(mapa);
    	    	
    	String strJson = jsonObject.toString();
    	    	
    	response.setContentType("text/html;charset=ISO-8859-15");
    	response.setContentLength(strJson.length());
    	    	
    	try {
			response.getWriter().print(strJson);
			response.getWriter().flush();			
		} catch (IOException e) {			
			throw new QGApplicationException(e);
		}    	   
    	logger.info("******---FIN escribirRespuestaJson de QGBaseAction---******");		
    }
    protected void escribirRespuestaJsonPagin(QGCGlobalPagingDto datos, HttpServletResponse response) {
    	
    	logger.info("******---escribirRespuestaJson de QGBaseAction---******");
    	
    	HashMap mapa = new HashMap();
    	   	
    	mapa.put("datos", datos);
    	mapa.put("message", datos.getListaMensajes());
    	mapa.put("total", new Integer(datos.getListaDatos().size()));
    	mapa.put("success", Boolean.TRUE);
    	   	
    	JSONObject jsonObject = JSONObject.fromObject(mapa);
    	    	
    	String strJson = jsonObject.toString();
    	    	
    	response.setContentType("text/html;charset=ISO-8859-15");
    	response.setContentLength(strJson.length());
    	    	
    	try {
			response.getWriter().print(strJson);
			response.getWriter().flush();			
		} catch (IOException e) {			
			throw new QGApplicationException(e);
		}    	   
    	logger.info("******---FIN escribirRespuestaJson de QGBaseAction---******");		
    }
    
    /**
     * Escribe los datos en formato json.
     * @param datos
     * @param response
     */
    protected void escribirRespuestaPagingJson(QGCGlobalPagingDto datos, HttpServletResponse response) {
    	
    	logger.info("******---escribirRespuestaPagingJson de QGBaseAction---******");    	
    	
    	HashMap mapa = new HashMap();
    	
    	mapa.put("datos", datos.getListaDatos());
    	mapa.put("message", datos.getListaMensajes());
    	mapa.put("total", new Integer(datos.getListaDatos().size()));
    	mapa.put("pgnTx", datos.getPgnTx());
    	mapa.put("indPgnIn", datos.getIndPgnIn());
    	mapa.put("success", Boolean.TRUE);    	    	
    	
    	JSONObject jsonObject = JSONObject.fromObject(mapa);
    	
    	String strJson = jsonObject.toString();
    	
    	response.setContentType("text/html;charset=ISO-8859-15");
    	response.setContentLength(strJson.length());
    	
    	try {
			response.getWriter().print(strJson);
			response.getWriter().flush();
			
		} catch (IOException e) {			
			throw new QGApplicationException(e);			
		}    	    	
    	logger.info("******---FIN escribirRespuestaPagingJson de QGBaseAction---******");		
    }        
    
    protected void tratarMensajesExcepcion(HttpServletResponse response, Exception ex) {
    	
    	logger.info("******---tratarMensajesExcepcion de QGBaseAction---******");
    	
    	HashMap mapa = new HashMap();
    	
    	mapa.put("success", Boolean.FALSE);
    	mapa.put("message", ex.getMessage());
    	
    	JSONObject jsonObject = JSONObject.fromObject(mapa);
    	
    	String strJson = jsonObject.toString();
    	
    	response.setContentType("text/html;charset=ISO-8859-15");
    	response.setContentLength(strJson.length());
    	
    	try {
			response.getWriter().print(strJson);
			response.getWriter().flush();
			
		} catch (IOException e) {			
			throw new QGApplicationException(e);			
		}
    	logger.info("******---FIN tratarMensajesExcepcion de QGBaseAction---******");		
    }
    
    protected void enviarObjetoJSON(HashMap datos, HttpServletResponse response) {
    	
    	logger.info("******---enviarObjetoJSON de QGBaseAction---******");
    	
    	JSONObject jsonObject = JSONObject.fromObject(datos);
    	
    	String strJson = jsonObject.toString();
    	
    	response.setContentType("text/html;charset=ISO-8859-15");
    	response.setContentLength(strJson.length());
    	
    	try {
			response.getWriter().print(strJson);
			response.getWriter().flush();
			
		} catch (IOException e) {			
			throw new QGApplicationException(e);			
		}
    	logger.info("******---FIN enviarObjetoJSON de QGBaseAction---******");		
    }
    
    public QGUsuario getUsuarioLogado() {
    	return (QGUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

	public MapperIF getBeanMapper() {
		return beanMapper;
	}

	public void setBeanMapper(MapperIF beanMapper) {
		this.beanMapper = beanMapper;
	}

}
