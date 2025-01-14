/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.campanias.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de Campanias.
 * 
 * @author cnunezba
 * 
 * @struts.form name="QGCampaniasForm" 
 *
 */
public class QGCampaniasForm extends QGBaseForm{

	private static final long serialVersionUID = 5245613174736897193L;

	private String campaniasJSON;

	private String usuarioLogado;
	
	public String getCampaniasJSON() {
		return campaniasJSON;
	}

	public void setCampaniasJSON(String campaniasJSON) {
		this.campaniasJSON = campaniasJSON;
	}

	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
}
