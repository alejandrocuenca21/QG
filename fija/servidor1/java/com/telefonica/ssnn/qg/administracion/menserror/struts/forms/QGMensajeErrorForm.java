package com.telefonica.ssnn.qg.administracion.menserror.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;
/**
 * 
 * Formulario de Medios de Comunicacion.
 * 
 * @author jacastano
 * 
 * @struts.form name="QGMensajeErrorForm" 
 *
 */
public class QGMensajeErrorForm  extends QGBaseForm{

	private static final long serialVersionUID = -7362834678755496600L;

	private String menserrorJSON;
	
	private String usuarioLogado;

	public String getMenserrorJSON() {
		return menserrorJSON;
	}

	public void setMenserrorJSON(String menserrorJSON) {
		this.menserrorJSON = menserrorJSON;
	}

	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
}
