package com.telefonica.ssnn.qg.administracion.autorizaciones.dto;

public class QGAutorizacionesBusquedaDto {

	private String codSistemaExterno;
	private String codServicioNA;
	private String inActuacion;
	private String pgnTx;
	private String nejecucion;
	
	public String getCodSistemaExterno() {
		return codSistemaExterno;
	}
	public void setCodSistemaExterno(String codSistemaExterno) {
		this.codSistemaExterno = codSistemaExterno;
	}
	public String getCodServicioNA() {
		return codServicioNA;
	}
	public void setCodServicioNA(String codServicioNA) {
		this.codServicioNA = codServicioNA;
	}
	public String getInActuacion() {
		return inActuacion;
	}
	public void setInActuacion(String inActuacion) {
		this.inActuacion = inActuacion;
	}
	public String getPgnTx() {
		return pgnTx;
	}
	public void setPgnTx(String pgnTx) {
		this.pgnTx = pgnTx;
	}
	public String getNejecucion() {
		return nejecucion;
	}
	public void setNejecucion(String nejecucion) {
		this.nejecucion = nejecucion;
	}
	
	
	
}
