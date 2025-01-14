package com.telefonica.ssnn.qg.administracion.encartes.dto;

import java.util.Date;

import org.apache.commons.lang.StringUtils;


/**
 * Dto de encartes.
 * 
 * @author rgsimon
 *
 */
public class QGPOEncartesDto {

	
	private String accion;

	private boolean modificacion;
	
	private boolean ocultarHistorico = true;
	
	private String descLineaNegocio;
	
	private String codLineaNegocio;
	
	private String codSegmento;
	
	private String descSegmento;
	
	private String codDerecho;
	
	private String descDerecho;
	
	private String fecIniVigencia;
	
	private String fecFinVigencia;
	
	private String usuarioAlta;
	
	private String fecAlta;
	
	private Date fecAltaDate;
	
	private String usuarioMod;
	
	private String fecMod;

	private Date fecModDate;
	
	public boolean getModificacion() {
		return modificacion;
	}

	public void setModificacion(boolean modificacion) {
		this.modificacion = modificacion;
	}

	public String getCodLineaNegocio() {
		return codLineaNegocio;
	}

	public void setCodLineaNegocio(String codLineaNegocio) {
		this.codLineaNegocio = codLineaNegocio;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public boolean isOcultarHistorico() {
		//Ocultar historico sera false si estan informadas tanto la fecha como el usuario de mod
		if(this.fecAltaDate != null && this.fecModDate != null && this.fecModDate.compareTo(this.fecAltaDate) > 0){
			return false;
		}else{
			return true;
		}
	}

	public void setOcultarHistorico(boolean ocultarHistorico) {
		this.ocultarHistorico = ocultarHistorico;
	}

	

	public String getDescLineaNegocio() {
		return descLineaNegocio;
	}

	public void setDescLineaNegocio(String descLineaNegocio) {
		this.descLineaNegocio = descLineaNegocio;
	}

	public String getCodSegmento() {
		return codSegmento;
	}

	public void setCodSegmento(String codSegmento) {
		this.codSegmento = codSegmento;
	}

	public String getDescSegmento() {
		return descSegmento;
	}

	public void setDescSegmento(String descSegmento) {
		this.descSegmento = descSegmento;
	}

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

	public String getFecIniVigencia() {
		return fecIniVigencia;
	}

	public void setFecIniVigencia(String fecIniVigencia) {
		this.fecIniVigencia = fecIniVigencia;
	}

	public String getFecFinVigencia() {
		return fecFinVigencia;
	}

	public void setFecFinVigencia(String fecFinVigencia) {
		this.fecFinVigencia = fecFinVigencia;
	}

	public String getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public String getFecAlta() {
		return fecAlta;
	}

	public void setFecAlta(String fecAlta) {
		this.fecAlta = fecAlta;
	}

	public String getUsuarioMod() {
		return usuarioMod;
	}

	public void setUsuarioMod(String usuarioMod) {
		this.usuarioMod = usuarioMod;
	}

	public String getFecMod() {
		return fecMod;
	}

	public void setFecMod(String fecMod) {
		this.fecMod = fecMod;
	}

	public Date getFecAltaDate() {
		return fecAltaDate;
	}

	public void setFecAltaDate(Date fecAltaDate) {
		this.fecAltaDate = fecAltaDate;
	}

	public Date getFecModDate() {
		return fecModDate;
	}

	public void setFecModDate(Date fecModDate) {
		this.fecModDate = fecModDate;
	}
	
	
	
	
	
	
	
	
}
