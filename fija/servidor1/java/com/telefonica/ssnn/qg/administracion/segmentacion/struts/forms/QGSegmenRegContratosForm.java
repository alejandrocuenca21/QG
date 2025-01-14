/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de Segmentaciones para Reglas - Contratos
 * 
 * 
 * @struts.form name="QGSegmenRegContratosForm" 
 *
 */
public class QGSegmenRegContratosForm extends QGBaseForm{

	private static final long serialVersionUID = 5463389299288949920L;

	private String usuarioLogado;

	private String segmenRegContratosJSON;
	
	private String origen;
	
	private String inActuacion;

	
	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getSegmenRegContratosJSON() {
		return segmenRegContratosJSON;
	}

	public void setSegmenRegContratosJSON(String segmenRegContratosJSON) {
		this.segmenRegContratosJSON = segmenRegContratosJSON;
	}

	public String getInActuacion() {
		return inActuacion;
	}

	public void setInActuacion(String inActuacion) {
		this.inActuacion = inActuacion;
	}


}
