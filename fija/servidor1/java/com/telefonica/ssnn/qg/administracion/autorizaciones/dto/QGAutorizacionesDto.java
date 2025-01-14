package com.telefonica.ssnn.qg.administracion.autorizaciones.dto;
/**
 * Clase objeto para AUTORIZACIONES 
 * @author jacastano
 *
 */
public class QGAutorizacionesDto {
	
	private String sistema;
	
	private String servicioNA;
	
	private String lineaNegocio;
	
	private String fxIniVigencia;
	
	private String fxFinVigencia;
	
	private String usuarioAlta;
	
	private String usuarioModif;
	
	private String fxUsuarioAlta;
	
	private String fxUsuarioModif;

	private String accion;
	
	private String pgnTx;
	
	private String nejecucion;
	
	public String getPgnTx() {
		return pgnTx;
	}

	public void setPgnTx(String pgnTx) {
		this.pgnTx = pgnTx;
	}

	public String getNejecucion() {
		return nejecucion;
	}

	public void setNejecucion(String nejecucion) {
		this.nejecucion = nejecucion;
	}

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getServicioNA() {
		return servicioNA;
	}

	public void setServicioNA(String servicioNA) {
		this.servicioNA = servicioNA;
	}

	public String getLineaNegocio() {
		return lineaNegocio;
	}

	public void setLineaNegocio(String lineaNegocio) {
		this.lineaNegocio = lineaNegocio;
	}

	public String getFxIniVigencia() {
		return fxIniVigencia;
	}

	public void setFxIniVigencia(String fxIniVigencia) {
		this.fxIniVigencia = fxIniVigencia;
	}

	public String getFxFinVigencia() {
		return fxFinVigencia;
	}

	public void setFxFinVigencia(String fxFinVigencia) {
		this.fxFinVigencia = fxFinVigencia;
	}

	public String getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public String getUsuarioModif() {
		return usuarioModif;
	}

	public void setUsuarioModif(String usuarioModif) {
		this.usuarioModif = usuarioModif;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getFxUsuarioAlta() {
		return fxUsuarioAlta;
	}

	public void setFxUsuarioAlta(String fxUsuarioAlta) {
		this.fxUsuarioAlta = fxUsuarioAlta;
	}

	public String getFxUsuarioModif() {
		return fxUsuarioModif;
	}

	public void setFxUsuarioModif(String fxUsuarioModif) {
		this.fxUsuarioModif = fxUsuarioModif;
	}
	
	
}
