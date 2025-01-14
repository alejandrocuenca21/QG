package com.telefonica.ssnn.qg.seguridad.dto;

import java.io.Serializable;

public class QGUsuarioDTO implements Serializable {
	
	private static final long serialVersionUID = 6445851006889247317L;
	
	private String nombre;
	
	private String dni;
	
	private String password;
	
	private String empresa;
	
	private AccesosDTO[] accesos;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}


	public AccesosDTO[] getAccesos() {
		return accesos;
	}

	public void setAccesos(AccesosDTO[] accesos) {
		this.accesos = accesos;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

}
