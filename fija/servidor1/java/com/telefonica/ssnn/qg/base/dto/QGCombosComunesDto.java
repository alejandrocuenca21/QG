/**
 * 
 */
package com.telefonica.ssnn.qg.base.dto;

import java.util.Date;

/**
 * @author vsierra
 *
 */
public class QGCombosComunesDto {

	private String codigoNivel;
	
	private String descripcionNivel;
	
	private Date fechaInicioVigencia;
	
	private Date fechaFinVigencia;
	
	private String usuarioAlta;
	
	private String usuarioModificacion;
	
	private Date fechaAltaNivel;
	
	private Date fechaUltMod;
	
	private String nombrePrograma;

	public String getCodigoNivel() {
		return codigoNivel;
	}

	public void setCodigoNivel(String codigoNivel) {
		this.codigoNivel = codigoNivel;
	}

	public String getDescripcionNivel() {
		return descripcionNivel;
	}

	public void setDescripcionNivel(String descripcionNivel) {
		this.descripcionNivel = descripcionNivel;
	}

	public Date getFechaInicioVigencia() {
		return fechaInicioVigencia;
	}

	public void setFechaInicioVigencia(Date fechaInicioVigencia) {
		this.fechaInicioVigencia = fechaInicioVigencia;
	}

	public Date getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(Date fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
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

	public Date getFechaAltaNivel() {
		return fechaAltaNivel;
	}

	public void setFechaAltaNivel(Date fechaAltaNivel) {
		this.fechaAltaNivel = fechaAltaNivel;
	}

	public Date getFechaUltMod() {
		return fechaUltMod;
	}

	public void setFechaUltMod(Date fechaUltMod) {
		this.fechaUltMod = fechaUltMod;
	}

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}
}
