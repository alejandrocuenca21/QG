/**
 * 
 */
package com.telefonica.ssnn.qg.seguridad.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de acceso.
 * 
 * @author ACF
 * 
 * @struts.form name="QGElegirAccesoForm" 
 *
 */
public class QGElegirAccesoForm extends QGBaseForm {

	private static final long serialVersionUID = 3834952047321588082L;

	public String accesoSeleccionado;

	public String getAccesoSeleccionado() {
		return accesoSeleccionado;
	}
	public void setAccesoSeleccionado(String accesoSeleccionado) {
		this.accesoSeleccionado = accesoSeleccionado;
	}
	
	

}
