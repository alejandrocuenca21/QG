package com.telefonica.ssnn.qg.seguridad.dto;

import java.io.Serializable;

public class AccesosDTO implements Serializable {
	
	private static final long serialVersionUID = 6445851006889247317L;
	
	private String aplicacion;
	
	private String[] perfiles;

	public String getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	public String[] getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(String[] perfiles) {
		this.perfiles = perfiles;
	}
}
