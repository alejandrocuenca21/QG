/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de Segmentaciones para Presegmentación
 * 
 * 
 * @struts.form name="QGSegmentacionesPresegForm" 
 *
 */
public class QGSegmentacionesPresegForm extends QGBaseForm{

	private static final long serialVersionUID = 5463389299288949920L;

	private String usuarioLogado;

	private String segmentacionPresegJSON;

	
	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getSegmentacionPresegJSON() {
		return segmentacionPresegJSON;
	}

	public void setSegmentacionPresegJSON(String segmentacionPresegJSON) {
		this.segmentacionPresegJSON = segmentacionPresegJSON;
	}
}
