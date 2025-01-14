package com.telefonica.ssnn.qg.administracion.segmentacion.dto;
/**
 * Funcion que controlará la carga de los combos dependientes de los segmentos
 * indicando si es de tipo movil o fijo y ademas el codigo por el que se realiza la 
 * busqueda
 * @author mgvinuesa
 *
 */
public class QGTipoComboValor {

	/**
	 * Booleano que indica si el segmento es fijo (true) o movil (false)
	 */
	private Boolean tipoSegmento;
	/**
	 * String que indica el codigo por el que se quiere realizar la busqueda
	 */
	private String valorCombo;
	
	public Boolean getTipoSegmento() {
		return tipoSegmento;
	}
	public void setTipoSegmento(Boolean tipoSegmento) {
		this.tipoSegmento = tipoSegmento;
	}
	public String getValorCombo() {
		return valorCombo;
	}
	public void setValorCombo(String valorCombo) {
		this.valorCombo = valorCombo;
	}
	
	
}
