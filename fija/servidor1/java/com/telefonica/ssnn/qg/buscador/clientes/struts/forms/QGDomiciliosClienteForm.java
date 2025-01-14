/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.clientes.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de Tipos de Ubicacion.
 * 
 * @author cnunezba
 * 
 * @struts.form name="QGDomiciliosClienteForm" 
 *
 */
public class QGDomiciliosClienteForm extends QGBaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2954726774945613469L;

	private String domiciliosJSON;

	public String getDomiciliosJSON() {
		return domiciliosJSON;
	}

	public void setDomiciliosJSON(String domiciliosJSON) {
		this.domiciliosJSON = domiciliosJSON;
	}
}
