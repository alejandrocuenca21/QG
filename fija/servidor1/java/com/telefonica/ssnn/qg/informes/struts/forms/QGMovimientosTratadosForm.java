/**
 * 
 */
package com.telefonica.ssnn.qg.informes.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de Movimientos Tratados.
 * 
 * @author cnunezba
 * 
 * @struts.form name="QGMovimientosTratadosForm" 
 *
 */
public class QGMovimientosTratadosForm extends QGBaseForm {

	private static final long serialVersionUID = 4080244120509371329L;
	
	private String movimientosTratadosJSON;

	public String getMovimientosTratadosJSON() {
		return movimientosTratadosJSON;
	}

	public void setMovimientosTratadosJSON(String movimientosTratadosJSON) {
		this.movimientosTratadosJSON = movimientosTratadosJSON;
	}

}
