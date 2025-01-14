/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.medios.dto;


/**
 * @author jacastano
 *
 */
public class QGMediosComunicacionDto {

	private String codActuacion;
	
	private String codigo;
	
	private String descripcion;
	
	private String fechaIniVigencia;
	
	private String fechaFinVigencia;
	
	private String usuarioAlta;
	
	private String usuarioMod;
	
	private String nombrePrograma;

	public String getCodActuacion() {
		return codActuacion;
	}

	public void setCodActuacion(String codActuacion) {
		this.codActuacion = codActuacion;
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

	public String getFechaIniVigencia() {
		return fechaIniVigencia;
	}

	public void setFechaIniVigencia(String fechaIniVigencia) {
		this.fechaIniVigencia = fechaIniVigencia;
	}

	public String getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(String fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
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

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}
}
