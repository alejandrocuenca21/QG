/**
 * 
 */
package com.telefonica.ssnn.qg.informes.dto;

import java.util.Date;

/**
 * @author vsierra
 *
 */
public class QGEstadisticasDto {

	private Long regTratados;
	
	private Long regValidos;
	
	private Long regErroneos;
	
	private Long regValidosFija;
	
	private Long regValidosMovil;
	
	private Long regErrorFija;
	
	private Long regErrorMovil;
	
	private Date fxInicio;
	
	private Date fxFin;
	
	private String fxBusqueda;
	
	private String tipoInforme;
	
	public String getTipoInforme() {
		return tipoInforme;
	}

	public void setTipoInforme(String tipoInforme) {
		this.tipoInforme = tipoInforme;
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

	public Long getRegTratados() {
		return regTratados;
	}

	public void setRegTratados(Long regTratados) {
		this.regTratados = regTratados;
	}

	public Long getRegValidos() {
		return regValidos;
	}

	public void setRegValidos(Long regValidos) {
		this.regValidos = regValidos;
	}

	public Long getRegErroneos() {
		return regErroneos;
	}

	public void setRegErroneos(Long regErroneos) {
		this.regErroneos = regErroneos;
	}

	public Long getRegValidosFija() {
		return regValidosFija;
	}

	public void setRegValidosFija(Long regValidosFija) {
		this.regValidosFija = regValidosFija;
	}

	public Long getRegValidosMovil() {
		return regValidosMovil;
	}

	public void setRegValidosMovil(Long regValidosMovil) {
		this.regValidosMovil = regValidosMovil;
	}

	public Long getRegErrorFija() {
		return regErrorFija;
	}

	public void setRegErrorFija(Long regErrorFija) {
		this.regErrorFija = regErrorFija;
	}

	public Long getRegErrorMovil() {
		return regErrorMovil;
	}

	public void setRegErrorMovil(Long regErrorMovil) {
		this.regErrorMovil = regErrorMovil;
	}

	public String getFxBusqueda() {
		return fxBusqueda;
	}

	public void setFxBusqueda(String fxBusqueda) {
		this.fxBusqueda = fxBusqueda;
	}
	
}
