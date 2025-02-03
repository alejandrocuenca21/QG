/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.clientes.struts.forms;

import org.apache.log4j.Logger;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de Lista Clientes.
 * 
 * @author cnunezba
 * 
 * @struts.form name="QGListaClientesForm" 
 *
 */
public class TWListaClientesForm extends QGBaseForm{

	private static final long serialVersionUID = -9186910770236330952L;
	private static final Logger logger = Logger.getLogger(QGListaClientesForm.class);
	private String listaClientesJSON;
	private String busquedaClientesJSON;
	private String busquedaTipoDocJSON;	
	
	private String vuelta;

	public String getVuelta() {
		return vuelta;
	}

	public void setVuelta(String vuelta) {
		this.vuelta = vuelta;
	}

	public String getListaClientesJSON() {
		return listaClientesJSON;
	}

	public void setListaClientesJSON(String listaClientesJSON) {
		this.listaClientesJSON = listaClientesJSON;
	}

	public String getBusquedaClientesJSON() {
		return busquedaClientesJSON;
	}

	public void setBusquedaClientesJSON(String busquedaClientesJSON) {
		this.busquedaClientesJSON = busquedaClientesJSON;
	}
	public String getBusquedaTipoDocJSON() {
		logger.info("******---getBusquedaTipoDocJSON de QGListaClientesForm---******");
		return busquedaTipoDocJSON;
	}

	public void setBusquedaTipoDocJSON(String busquedaTipoDocJSON) {
		this.busquedaTipoDocJSON = busquedaTipoDocJSON;
	}	
	
}
