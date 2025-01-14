/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.ubicacion.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de Tipos de Ubicacion.
 * 
 * @author cnunezba
 * 
 * @struts.form name="QGTiposUbicacionForm" 
 *
 */
public class QGTiposUbicacionForm extends QGBaseForm{

	private static final long serialVersionUID = 5463389299288949920L;
	
	private String tiposUbicacionJSON;

	private String usuarioLogado;
	
	public String getTiposUbicacionJSON() {
		return tiposUbicacionJSON;
	}

	public void setTiposUbicacionJSON(String tiposUbicacionJSON) {
		this.tiposUbicacionJSON = tiposUbicacionJSON;
	}

	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	
}
