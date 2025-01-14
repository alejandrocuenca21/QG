/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.consentimientos.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de Consentimientos/Derechos.
 * 
 * @author cnunezba
 * 
 * @struts.form name="QGConsentimientosDerechosForm" 
 *
 */
public class QGConsentimientosDerechosForm extends QGBaseForm{

	private static final long serialVersionUID = 1277770140736423728L;

	private String consentimientosDerechosJSON;
	
	private String textoLegalJSON;
	
	private String codDerecho;

	public String getCodDerecho() {
		return codDerecho;
	}

	public void setCodDerecho(String codDerecho) {
		this.codDerecho = codDerecho;
	}

	public String getConsentimientosDerechosJSON() {
		return consentimientosDerechosJSON;
	}

	public void setConsentimientosDerechosJSON(String consentimientosDerechosJSON) {
		this.consentimientosDerechosJSON = consentimientosDerechosJSON;
	}

	public String getTextoLegalJSON() {
		return textoLegalJSON;
	}

	public void setTextoLegalJSON(String textoLegalJSON) {
		this.textoLegalJSON = textoLegalJSON;
	}
}
