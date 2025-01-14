/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.clientes.dto;

/**
 * @author vsierra
 *
 */
public class QGDescripcionesDto {

	private String linea;
	
	private String modalidad;
	
	private String codigo;
	
	private String descripcion;
	
	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}
	
	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
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
