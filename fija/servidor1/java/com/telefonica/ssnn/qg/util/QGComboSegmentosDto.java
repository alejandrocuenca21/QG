package com.telefonica.ssnn.qg.util;


/**
 * Dto Segmentos para mover datos de tipo codigo/descripción como cargas de combo
 *
 */
public class QGComboSegmentosDto extends QGCatalogoDto  {
	/**
	 * Indica si el combo es de linea de negocio Fija (F) o Movil (M)
	 */
	private String lineaNegocio;

	public String getLineaNegocio() {
		return lineaNegocio;
	}

	public void setLineaNegocio(String lineaNegocio) {
		this.lineaNegocio = lineaNegocio;
	}
}
