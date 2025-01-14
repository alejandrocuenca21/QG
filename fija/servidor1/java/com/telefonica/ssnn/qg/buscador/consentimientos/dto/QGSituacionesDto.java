/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.consentimientos.dto;

/**
 * @author vsierra
 *
 */
public class QGSituacionesDto {

	private Long secuencial;
	
	private String codigo;
	
	private String descripcion;

	public Long getSecuencial() {
		return secuencial;
	}

	public void setSecuencial(Long secuencial) {
		this.secuencial = secuencial;
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
}
