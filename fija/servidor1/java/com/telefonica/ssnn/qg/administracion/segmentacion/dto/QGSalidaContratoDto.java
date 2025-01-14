package com.telefonica.ssnn.qg.administracion.segmentacion.dto;

/** Salida para los Contratos
 * 
 * @author jacastano
 *
 */

public class QGSalidaContratoDto{

	private String codigo;
	private String descripcion;
	private String fxIniVigencia;
	private String fxFinVigencia;
	private String usuarioAlta;
	private String usuarioMod;
	
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
	public String getUsuarioMod() {
		return usuarioMod;
	}
	public void setUsuarioMod(String usuarioMod) {
		this.usuarioMod = usuarioMod;
	}

}