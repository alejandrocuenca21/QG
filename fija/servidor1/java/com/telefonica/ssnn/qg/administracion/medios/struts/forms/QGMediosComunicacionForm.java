/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.medios.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de Medios de Comunicacion.
 * 
 * @author cnunezba
 * 
 * @struts.form name="QGMediosComunicacionForm" 
 *
 */
public class QGMediosComunicacionForm extends QGBaseForm{

	private static final long serialVersionUID = -7362834678755496600L;

	private String medioComunicacionJSON;
	
	private String usuarioLogado;

	public String getMedioComunicacionJSON() {
		return medioComunicacionJSON;
	}

	public void setMedioComunicacionJSON(String medioComunicacionJSON) {
		this.medioComunicacionJSON = medioComunicacionJSON;
	}

	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
}
