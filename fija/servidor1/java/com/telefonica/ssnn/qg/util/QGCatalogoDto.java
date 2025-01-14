package com.telefonica.ssnn.qg.util;


/**
 * Dto Gen�rico para mover datos de tipo codigo/descripci�n como cargas de combos.
 * 
 * @author rgsimon
 *
 */
public class QGCatalogoDto {
	/**
	 * C�digo
	 */
	private String codigo;

	/**
	 * Descripci�n.
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
