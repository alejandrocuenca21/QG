/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.consentimientos.dto;

import java.util.Date;

/**
 * @author vsierra
 *
 */
public class QGEntradaListaCDDto {

	private String codCliente;
	
	private String codActuacion;
	
	private String codTipoObjeto;
	
	private String estadoGestion;
	
	private String estadoCD;
	
	private Date fxIniCambio;
	
	private Date fxFinCambio;

	public String getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}

	public String getCodActuacion() {
		return codActuacion;
	}

	public void setCodActuacion(String codActuacion) {
		this.codActuacion = codActuacion;
	}

	public String getCodTipoObjeto() {
		return codTipoObjeto;
	}

	public void setCodTipoObjeto(String codTipoObjeto) {
		this.codTipoObjeto = codTipoObjeto;
	}

	public String getEstadoGestion() {
		return estadoGestion;
	}

	public void setEstadoGestion(String estadoGestion) {
		this.estadoGestion = estadoGestion;
	}

	public String getEstadoCD() {
		return estadoCD;
	}

	public void setEstadoCD(String estadoCD) {
		this.estadoCD = estadoCD;
	}

	public Date getFxIniCambio() {
		return fxIniCambio;
	}

	public void setFxIniCambio(Date fxIniCambio) {
		this.fxIniCambio = fxIniCambio;
	}

	public Date getFxFinCambio() {
		return fxFinCambio;
	}

	public void setFxFinCambio(Date fxFinCambio) {
		this.fxFinCambio = fxFinCambio;
	}
}
