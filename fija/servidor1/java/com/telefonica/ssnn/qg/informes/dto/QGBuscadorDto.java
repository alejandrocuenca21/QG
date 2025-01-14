/**
 * 
 */
package com.telefonica.ssnn.qg.informes.dto;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author vsierra
 *
 */
public class QGBuscadorDto {

	private Date fxMovimiento;
	
	private String indicadorNegocio;
	
	private String clienteNSCO;
	
	private String tipoDocumento;
	
	private String numDocumento;
	
	private String razonSocial;
	
	private String indActuacion;
	
	private Date fxInicio;
	
	private Date fxFin;
	
	private String fxBusqueda;
	
	private String codigoError;
	
	private ArrayList listaDatos;

	
	public String getFxBusqueda() {
		return fxBusqueda;
	}

	public void setFxBusqueda(String fxBusqueda) {
		this.fxBusqueda = fxBusqueda;
	}
	
	public Date getFxMovimiento() {
		return fxMovimiento;
	}

	public void setFxMovimiento(Date fxMovimiento) {
		this.fxMovimiento = fxMovimiento;
	}

	public String getIndicadorNegocio() {
		return indicadorNegocio;
	}

	public void setIndicadorNegocio(String indicadorNegocio) {
		this.indicadorNegocio = indicadorNegocio;
	}

	public String getClienteNSCO() {
		return clienteNSCO;
	}

	public void setClienteNSCO(String clienteNSCO) {
		this.clienteNSCO = clienteNSCO;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumDocumento() {
		return numDocumento;
	}

	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getIndActuacion() {
		return indActuacion;
	}

	public void setIndActuacion(String indActuacion) {
		this.indActuacion = indActuacion;
	}

	public Date getFxInicio() {
		return fxInicio;
	}

	public void setFxInicio(Date fxInicio) {
		this.fxInicio = fxInicio;
	}

	public Date getFxFin() {
		return fxFin;
	}

	public void setFxFin(Date fxFin) {
		this.fxFin = fxFin;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public ArrayList getListaDatos() {
		return listaDatos;
	}

	public void setListaDatos(ArrayList listaDatos) {
		this.listaDatos = listaDatos;
	}
}
