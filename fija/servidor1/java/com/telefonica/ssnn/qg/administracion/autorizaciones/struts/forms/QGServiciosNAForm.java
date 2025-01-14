package com.telefonica.ssnn.qg.administracion.autorizaciones.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;
/**
 * 
 * Formulario de Servicios NA
 * 
 * 
 * @struts.form name="QGServiciosNAForm" 
 *
 */
public class QGServiciosNAForm extends QGBaseForm{

	private static final long serialVersionUID = -7362834678755496600L;

	private String serviciosNAJSON;
	
	private String usuarioLogado;

	public String getServiciosNAJSON() {
		return serviciosNAJSON;
	}

	public void setServiciosNAJSON(String serviciosNAJSON) {
		this.serviciosNAJSON = serviciosNAJSON;
	}

	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
