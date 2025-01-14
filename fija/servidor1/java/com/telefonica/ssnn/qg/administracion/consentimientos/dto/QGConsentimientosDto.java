/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.consentimientos.dto;

import java.util.ArrayList;
import java.util.Date;


/**
 * @author vsierra 
 *
 */
public class QGConsentimientosDto {
	
	private String codActuacion;
	
	private String codigoLOPD;
	
	private String codNegocioFija;
	
	private String codNegocioMovil;
	
	private String ambitoAplicacion;
	
	private String descDerecho;
	
	private String descAmbito;
	
	private String tipoLogica;
	
	private String fxIniVigencia;
	
	private String fxFinVigencia;
	
	private String usuarioAlta;
	
	private String usuarioMod;
	
	private Date fxAlta;
	
	private Date fxUltModificacion;
	
	private String nombrePrograma;
	
	private ArrayList codLineaNegocio;
	
	private String codigoTDE;
	
	private String codigoTME;

	private String codigoConsentimiento;
	
	private String descConsentimiento;

	public String getCodActuacion() {
		return codActuacion;
	}

	public void setCodActuacion(String codActuacion) {
		this.codActuacion = codActuacion;
	}

	public String getCodigoLOPD() {
		return codigoLOPD;
	}

	public void setCodigoLOPD(String codigoLOPD) {
		this.codigoLOPD = codigoLOPD;
	}

	public String getCodNegocioFija() {
		return codNegocioFija;
	}

	public void setCodNegocioFija(String codNegocioFija) {
		this.codNegocioFija = codNegocioFija;
	}

	public String getCodNegocioMovil() {
		return codNegocioMovil;
	}

	public void setCodNegocioMovil(String codNegocioMovil) {
		this.codNegocioMovil = codNegocioMovil;
	}

	public String getAmbitoAplicacion() {
		return ambitoAplicacion;
	}

	public void setAmbitoAplicacion(String ambitoAplicacion) {
		this.ambitoAplicacion = ambitoAplicacion;
	}

	public String getDescDerecho() {
		return descDerecho;
	}

	public void setDescDerecho(String descDerecho) {
		this.descDerecho = descDerecho;
	}

	public String getDescAmbito() {
		return descAmbito;
	}

	public void setDescAmbito(String descAmbito) {
		this.descAmbito = descAmbito;
	}

	public String getTipoLogica() {
		return tipoLogica;
	}

	public void setTipoLogica(String tipoLogica) {
		this.tipoLogica = tipoLogica;
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

	public Date getFxAlta() {
		return fxAlta;
	}

	public void setFxAlta(Date fxAlta) {
		this.fxAlta = fxAlta;
	}

	public Date getFxUltModificacion() {
		return fxUltModificacion;
	}

	public void setFxUltModificacion(Date fxUltModificacion) {
		this.fxUltModificacion = fxUltModificacion;
	}

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}

	public ArrayList getCodLineaNegocio() {
		return codLineaNegocio;
	}

	public void setCodLineaNegocio(ArrayList codLineaNegocio) {
		this.codLineaNegocio = codLineaNegocio;
	}

	public String getCodigoTDE() {
		return codigoTDE;
	}

	public void setCodigoTDE(String codigoTDE) {
		this.codigoTDE = codigoTDE;
	}

	public String getCodigoTME() {
		return codigoTME;
	}

	public void setCodigoTME(String codigoTME) {
		this.codigoTME = codigoTME;
	}

	/**
	 * @return the codigoConsentimiento
	 */
	public String getCodigoConsentimiento() {
		return this.codigoConsentimiento;
	}

	/**
	 * @param codigoConsentimiento the codigoConsentimiento to set
	 */
	public void setCodigoConsentimiento(String codigoConsentimiento) {
		this.codigoConsentimiento = codigoConsentimiento;
	}

	/**
	 * @return the descConsentimiento
	 */
	public String getDescConsentimiento() {
		return this.descConsentimiento;
	}

	/**
	 * @param descConsentimiento the descConsentimiento to set
	 */
	public void setDescConsentimiento(String descConsentimiento) {
		this.descConsentimiento = descConsentimiento;
	}
}
