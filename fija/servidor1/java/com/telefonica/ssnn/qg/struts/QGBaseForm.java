package com.telefonica.ssnn.qg.struts;

import org.apache.struts.action.ActionForm;

/**
 * Clase base para los formularios de struts.
 * @author jacangas
 *
 */
public class QGBaseForm extends ActionForm {
	
	private static final long serialVersionUID = -6697885824745962494L;
	
	private String usuario;
	
	private String perfil;


	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}


}
