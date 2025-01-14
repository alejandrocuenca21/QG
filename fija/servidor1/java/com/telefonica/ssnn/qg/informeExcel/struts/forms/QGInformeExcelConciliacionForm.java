package com.telefonica.ssnn.qg.informeExcel.struts.forms;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de Informes Convivencia.
 * 
 * @author jacastano
 * 
 * @struts.form name="QGInformeExcelConciliacionForm" 
 *
 */
public class QGInformeExcelConciliacionForm extends QGBaseForm {

	private static final long serialVersionUID = 4080244120509371329L;
	
	private String busquedaJSON;

	public String getBusquedaJSON() {
		return busquedaJSON;
	}

	public void setBusquedaJSON(String busquedaJSON) {
		this.busquedaJSON = busquedaJSON;
	}



}