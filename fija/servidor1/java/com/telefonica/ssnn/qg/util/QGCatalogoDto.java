package com.telefonica.ssnn.qg.util;


/**
 * Dto Genérico para mover datos de tipo codigo/descripción como cargas de combos.
 * 
 * @author rgsimon
 *
 */
public class QGCatalogoDto {
	/**
	 * Código
	 */
	private String codigo;

	/**
	 * Descripción.
	 */
	private String descripcion;

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
