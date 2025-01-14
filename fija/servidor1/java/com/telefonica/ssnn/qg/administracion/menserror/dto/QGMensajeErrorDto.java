package com.telefonica.ssnn.qg.administracion.menserror.dto;
/**
 * Clase objeto para ADMINISTRACIÓN MENSAJES ERROR 
 * @author jacastano
 *
 */
public class QGMensajeErrorDto {

	private String codigo;
	
	private String descripcion;
	
	private String grupoResponsable;
	
	private String fxIniVigencia;
	
	private String fxFinVigencia;
	
	private String usuarioAlta;
	
	private String fxUsuarioAlta;
	
	private String usuarioModif;

	private String fxUsuarioModif;	
	
	private String accion;
	
	private String pgnTx;
	
	private String nejecucion;
	
	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public String getGrupoResponsable() {
		return grupoResponsable;
	}

	public void setGrupoResponsable(String grupoResponsable) {
		this.grupoResponsable = grupoResponsable;
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
	
}
