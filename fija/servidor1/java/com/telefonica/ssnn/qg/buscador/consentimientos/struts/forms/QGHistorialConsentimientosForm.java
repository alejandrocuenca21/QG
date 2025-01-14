/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.consentimientos.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de Historial Consentimientos
 * 
 * @author cnunezba
 * 
 * @struts.form name="QGHistorialConsentimientosForm" 
 *
 */
public class QGHistorialConsentimientosForm extends QGBaseForm{

	
	private static final long serialVersionUID = -9087838988529097069L;

	private String historialJSON;

	public String getHistorialJSON() {
		return historialJSON;
	}

	public void setHistorialJSON(String historialJSON) {
		this.historialJSON = historialJSON;
	}
}
