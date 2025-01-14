package com.telefonica.ssnn.qg.administracion.encartes.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;


/**
 * 
 * Formulario de Segmentaciones
 * 
 * 
 * @struts.form name="QGEncartesForm" 
 *
 */

public class QGEncartesForm extends QGBaseForm{

	

	private String usuarioLogado;

	private String encartesJSON;
	
	
	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getEncartesJSON() {
		return encartesJSON;
	}

	public void setEncartesJSON(String encartesJSON) {
		this.encartesJSON = encartesJSON;
	}

	 

}
