/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de Segmentaciones para traduccion
 * 
 * 
 * @struts.form name="QGSegmentacionesTraduccionForm" 
 *
 */
public class QGSegmentacionesTraduccionForm extends QGBaseForm{

	private static final long serialVersionUID = 5463389299288949920L;

	private String usuarioLogado;

	private String segmentacionTraduccionJSON;
	/**
	 * Objeto JSON que se compondrá de el tipo de combo (fijo o movil) y el codigo seleccionado
	 */
	private String tipoComboValorJSON;
	
	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getSegmentacionTraduccionJSON() {
		return segmentacionTraduccionJSON;
	}

	public void setSegmentacionTraduccionJSON(String segmentacionTraduccionJSON) {
		this.segmentacionTraduccionJSON = segmentacionTraduccionJSON;
	}

	public String getTipoComboValorJSON() {
		return tipoComboValorJSON;
	}

	public void setTipoComboValorJSON(String tipoComboValorJSON) {
		this.tipoComboValorJSON = tipoComboValorJSON;
	}

	

	




}
