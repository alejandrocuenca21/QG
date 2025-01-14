package com.telefonica.ssnn.qg.administracion.autorizaciones.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;
/**
 * 
 * Formulario de Sistemas Externos
 * 
 * 
 * @struts.form name="QGSistemasExternosForm"  
 *
 */
public class QGSistemasExternosForm extends QGBaseForm{

	private static final long serialVersionUID = -7362834678755496600L;

	private String sistemasExternosJSON;
	
	private String usuarioLogado;
	
	private String busquedaJSON;

	public String getSistemasExternosJSON() {
		return sistemasExternosJSON;
	}

	public void setSistemasExternosJSON(String sistemasExternosJSON) {
		this.sistemasExternosJSON = sistemasExternosJSON;
	}

	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getBusquedaJSON() {
		return this.busquedaJSON;
	}

	public void setBusquedaJSON(String busquedaJSON) {
		this.busquedaJSON = busquedaJSON;
	}
}
