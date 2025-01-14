/**
 * 
 */
package com.telefonica.ssnn.qg.informes.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de Documento Duplicado.
 * 
 * @author cnunezba
 * 
 * @struts.form name="QGDocumentoDuplicadoForm" 
 *
 */
public class QGDocumentoDuplicadoForm extends QGBaseForm {

	private static final long serialVersionUID = 3834952047321588082L;
	private String documentoDuplicadoJSON;
	public String accesoSeleccionado;
	
	public String getDocumentoDuplicadoJSON() {
		return documentoDuplicadoJSON;
	}
	public void setDocumentoDuplicadoJSON(String documentoDuplicadoJSON) {
		this.documentoDuplicadoJSON = documentoDuplicadoJSON;
	}
	public String getAccesoSeleccionado() {
		return accesoSeleccionado;
	}
	public void setAccesoSeleccionado(String accesoSeleccionado) {
		this.accesoSeleccionado = accesoSeleccionado;
	}
	
	

}
