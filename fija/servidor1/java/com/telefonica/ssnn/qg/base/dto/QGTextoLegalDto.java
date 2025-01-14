/**
 * 
 */
package com.telefonica.ssnn.qg.base.dto;

import java.util.Date;

/**
 * @author vsierra
 *
 */
public class QGTextoLegalDto {

	private String codActuacion;
	
	private String codigoDerecho;
	
	private Integer secuencial;
	
	private String textoLegal;
	
	private Date fecInicioVigencia;
	
	private Date fecFinVigencia;
	
	private String usuarioAlta;
	
	private String usuarioModificacion;
	
	private Date fechaAlta;
	
	private Date fechaUltModificacion;
	
	private String nombrePrograma;

	public String getCodActuacion() {
		return codActuacion;
	}

	public void setCodActuacion(String codActuacion) {
		this.codActuacion = codActuacion;
	}

	public String getCodigoDerecho() {
		return codigoDerecho;
	}

	public void setCodigoDerecho(String codigoDerecho) {
		this.codigoDerecho = codigoDerecho;
	}

	public Integer getSecuencial() {
		return secuencial;
	}

	public void setSecuencial(Integer secuencial) {
		this.secuencial = secuencial;
	}

	public String getTextoLegal() {
		return textoLegal;
	}

	public void setTextoLegal(String textoLegal) {
		this.textoLegal = textoLegal;
	}

	public Date getFecInicioVigencia() {
		return fecInicioVigencia;
	}

	public void setFecInicioVigencia(Date fecInicioVigencia) {
		this.fecInicioVigencia = fecInicioVigencia;
	}

	public Date getFecFinVigencia() {
		return fecFinVigencia;
	}

	public void setFecFinVigencia(Date fecFinVigencia) {
		this.fecFinVigencia = fecFinVigencia;
	}

	public String getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaUltModificacion() {
		return fechaUltModificacion;
	}

	public void setFechaUltModificacion(Date fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}

}
