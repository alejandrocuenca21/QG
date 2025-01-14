/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.consentimientos.dto;

import java.util.Date;

/**
 * @author vsierra
 *
 */
public class QGSalidaListaCDDto {

	private String codDerecho;
	
	private String descDerecho;
	
	private String tipoLogica;
	
	private String codTipoObjeto;
	
	private String objetoCD;
	
	private Long secTipoObjeto;
	
	private Long secEvento;
	
	private String estadoGestionCD;
	
	private String descEstadoGestion;
	
	private String valorExplotacion;
	
	private Date fxCambioEstado;
	
	private Date fxIniVigencia;
	
	private Date fxFinVigencia;
	
	private String codUsuario;

	public String getCodDerecho() {
		return codDerecho;
	}

	public void setCodDerecho(String codDerecho) {
		this.codDerecho = codDerecho;
	}

	public String getDescDerecho() {
		return descDerecho;
	}

	public void setDescDerecho(String descDerecho) {
		this.descDerecho = descDerecho;
	}

	public String getTipoLogica() {
		return tipoLogica;
	}

	public void setTipoLogica(String tipoLogica) {
		this.tipoLogica = tipoLogica;
	}

	public String getCodTipoObjeto() {
		return codTipoObjeto;
	}

	public void setCodTipoObjeto(String codTipoObjeto) {
		this.codTipoObjeto = codTipoObjeto;
	}

	public String getObjetoCD() {
		return objetoCD;
	}

	public void setObjetoCD(String objetoCD) {
		this.objetoCD = objetoCD;
	}

	public Long getSecTipoObjeto() {
		return secTipoObjeto;
	}

	public void setSecTipoObjeto(Long secTipoObjeto) {
		this.secTipoObjeto = secTipoObjeto;
	}

	public Long getSecEvento() {
		return secEvento;
	}

	public void setSecEvento(Long secEvento) {
		this.secEvento = secEvento;
	}

	public String getEstadoGestionCD() {
		return estadoGestionCD;
	}

	public void setEstadoGestionCD(String estadoGestionCD) {
		this.estadoGestionCD = estadoGestionCD;
	}

	public String getDescEstadoGestion() {
		return descEstadoGestion;
	}

	public void setDescEstadoGestion(String descEstadoGestion) {
		this.descEstadoGestion = descEstadoGestion;
	}

	public String getValorExplotacion() {
		return valorExplotacion;
	}

	public void setValorExplotacion(String valorExplotacion) {
		this.valorExplotacion = valorExplotacion;
	}

	public Date getFxCambioEstado() {
		return fxCambioEstado;
	}

	public void setFxCambioEstado(Date fxCambioEstado) {
		this.fxCambioEstado = fxCambioEstado;
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

	public String getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}
}
