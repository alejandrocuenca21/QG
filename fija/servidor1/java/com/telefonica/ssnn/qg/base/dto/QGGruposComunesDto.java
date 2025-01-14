/**
 * 
 */
package com.telefonica.ssnn.qg.base.dto;


/**
 * @author vsierra
 *
 */
public class QGGruposComunesDto {

	private Boolean marcado;
	
	private String codigo;
	
	private String descripcion;

	public Boolean getMarcado() {
		return marcado;
	}

	public void setMarcado(Boolean marcado) {
		this.marcado = marcado;
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
