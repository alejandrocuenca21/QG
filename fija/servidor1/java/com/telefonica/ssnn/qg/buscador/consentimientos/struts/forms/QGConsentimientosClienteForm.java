/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.consentimientos.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * @author vsierra
 *
 *@struts.form name="QGConsentimientosClienteForm"
 */
public class QGConsentimientosClienteForm extends QGBaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8742691191567543192L;
	
	private String consentimientosClientesJSON;
	
	public String getConsentimientosClientesJSON() {
		return consentimientosClientesJSON;
	}

	public void setConsentimientosClientesJSON(String consentimientosClientesJSON) {
		this.consentimientosClientesJSON = consentimientosClientesJSON;
	}
}
