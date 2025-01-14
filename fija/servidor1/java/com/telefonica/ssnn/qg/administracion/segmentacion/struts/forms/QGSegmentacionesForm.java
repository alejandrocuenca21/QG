/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de Segmentaciones
 * 
 * 
 * @struts.form name="QGSegmentacionesForm" 
 *
 */
public class QGSegmentacionesForm extends QGBaseForm{

	private static final long serialVersionUID = 5463389299288949920L;

	private String usuarioLogado;

	private String segmentacionJSON;
	/**
	 * Variable donde se almacenara el codigo seleccionado
	 */
	private String codSegmentoSeleccionado;

	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getSegmentacionJSON() {
		return segmentacionJSON;
	}

	public void setSegmentacionJSON(String segmentacionJSON) {
		this.segmentacionJSON = segmentacionJSON;
	}

	public String getCodSegmentoSeleccionado() {
		return codSegmentoSeleccionado;
	}

	public void setCodSegmentoSeleccionado(String codSegmentoSeleccionado) {
		this.codSegmentoSeleccionado = codSegmentoSeleccionado;
	}




}
