package com.telefonica.ssnn.qg.administracion.autorizaciones.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;
/**
 * 
 * Formulario de Autorizaciones
 * 
 * 
 * @struts.form name="QGAutorizacionesForm" 
 *
 */
public class QGAutorizacionesForm extends QGBaseForm{

	private static final long serialVersionUID = -7362834678755496600L;

	private String autorizacionesJSON;
	
	private String busquedaJSON;
	
	private String usuarioLogado;

	public String getAutorizacionesJSON() {
		return autorizacionesJSON;
	}

	public void setAutorizacionesJSON(String autorizacionesJSON) {
		this.autorizacionesJSON = autorizacionesJSON;
	}

	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getBusquedaJSON() {
		return busquedaJSON;
	}

	public void setBusquedaJSON(String busquedaJSON) {
		this.busquedaJSON = busquedaJSON;
	}

}
