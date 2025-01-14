/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.consentimientos.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de Detalle Consentimiento.
 * 
 * @author cnunezba
 * 
 * @struts.form name="QGDetalleConsentimientoForm" 
 *
 */
public class QGDetalleConsentimientoForm extends QGBaseForm{


	private static final long serialVersionUID = -299612613894941893L;
	
	private String detalleConsentimientoJSON;
	
	private String acceso;

	public String getDetalleConsentimientoJSON() {
		return detalleConsentimientoJSON;
	}

	public void setDetalleConsentimientoJSON(String detalleConsentimientoJSON) {
		this.detalleConsentimientoJSON = detalleConsentimientoJSON;
	}

	public String getAcceso() {
		return acceso;
	}

	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}

	
}
