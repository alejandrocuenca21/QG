/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de Segmentaciones para Reglas
 * 
 * 
 * @struts.form name="QGSegmentacionesReglasForm" 
 *
 */
public class QGSegmentacionesReglasForm extends QGBaseForm{

	private static final long serialVersionUID = 5463389299288949920L;

	private String usuarioLogado;

	private String segmentacionReglasJSON;
	
	private String origen;

	private String inActuacion;
	
	public String getInActuacion() {
		return inActuacion;
	}

	public void setInActuacion(String inActuacion) {
		this.inActuacion = inActuacion;
	}

	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getSegmentacionReglasJSON() {
		return segmentacionReglasJSON;
	}

	public void setSegmentacionReglasJSON(String segmentacionReglasJSON) {
		this.segmentacionReglasJSON = segmentacionReglasJSON;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}
}
