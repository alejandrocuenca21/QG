/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.consentimientos.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * @author vsierra
 *
 *@struts.form name="QGActualizacionConsentiemientoForm" 
 */
public class QGActualizacionConsentiemientoForm extends QGBaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1497074899415025195L;

	private String actualizacionClienteJSON;

	public String getActualizacionClienteJSON() {
		return actualizacionClienteJSON;
	}

	public void setActualizacionClienteJSON(String actualizacionClienteJSON) {
		this.actualizacionClienteJSON = actualizacionClienteJSON;
	}
}
