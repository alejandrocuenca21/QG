/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.consentimientos.dto;

import java.util.Date;

/**
 * @author vsierra
 *
 */
public class QGCombosDto {

	private String codigo;
	
	private String descripcion;
	
	private Date fxIniVigencia;
	
	private Date fxFinVigencia;
	
	private String codUsuarioAlta;
	
	private String codUsuarioMod;
	
	private String nombrePrograma;

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

	public Date getFxIniVigencia() {
		return fxIniVigencia;
	}

	public void setFxIniVigencia(Date fxIniVigencia) {
		this.fxIniVigencia = fxIniVigencia;
	}

	public Date getFxFinVigencia() {
		return fxFinVigencia;
	}

	public void setFxFinVigencia(Date fxFinVigencia) {
		this.fxFinVigencia = fxFinVigencia;
	}

	public String getCodUsuarioAlta() {
		return codUsuarioAlta;
	}

	public void setCodUsuarioAlta(String codUsuarioAlta) {
		this.codUsuarioAlta = codUsuarioAlta;
	}

	public String getCodUsuarioMod() {
		return codUsuarioMod;
	}

	public void setCodUsuarioMod(String codUsuarioMod) {
		this.codUsuarioMod = codUsuarioMod;
	}

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}
}
