package com.telefonica.ssnn.qg.administracion.creatividad.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;


/**
 * 
 * Formulario de Creatividad
 * 
 * 
 * @struts.form name="QGCreatividadForm" 
 *
 */

public class QGCreatividadForm extends QGBaseForm{

	private String usuarioLogado;

	private String creatividadJSON;
	
	private String lineaNegocio;

	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getCreatividadJSON() {
		return creatividadJSON;
	}

	public void setCreatividadJSON(String creatividadJSON) {
		this.creatividadJSON = creatividadJSON;
	}

	public String getLineaNegocio() {
		return lineaNegocio;
	}

	public void setLineaNegocio(String lineaNegocio) {
		this.lineaNegocio = lineaNegocio;
	}


	 

}
