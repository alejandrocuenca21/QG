/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.dto;


/**
 * @author jacastano
 *
 */
public class QGEntradaSegmentacionesReglasDto {

	private String codActuacion;
	
	private String tipoConsulta;

	private String segmentoOrigen;
	
	private String segmento;
	
	private String subSegmento;
	
	private String ofAtencion;
	
	private String tandem;
	
	private String regla;
	
	private String nLinFija;
	
	private String nLinMovil;
	
	private String nTotalLin;
	
	private String nDias;
	
	private String fechaFinVigencia;	
	
	private String pgnTx;
	
	private String nejecucion;
	
	private String ultimaBusquedaTipo;

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


	public String getSegmento() {
		return segmento;
	}

	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}

	public String getSubSegmento() {
		return subSegmento;
	}

	public void setSubSegmento(String subSegmento) {
		this.subSegmento = subSegmento;
	}

	public String getOfAtencion() {
		return ofAtencion;
	}

	public void setOfAtencion(String ofAtencion) {
		this.ofAtencion = ofAtencion;
	}

	public String getTandem() {
		return tandem;
	}

	public void setTandem(String tandem) {
		this.tandem = tandem;
	}

	public String getCodActuacion() {
		return codActuacion;
	}

	public void setCodActuacion(String codActuacion) {
		this.codActuacion = codActuacion;
	}

	public String getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(String tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public String getRegla() {
		return regla;
	}

	public void setRegla(String regla) {
		this.regla = regla;
	}

	public String getNLinFija() {
		return nLinFija;
	}

	public void setNLinFija(String linFija) {
		nLinFija = linFija;
	}

	public String getNLinMovil() {
		return nLinMovil;
	}

	public void setNLinMovil(String linMovil) {
		nLinMovil = linMovil;
	}

	public String getNTotalLin() {
		return nTotalLin;
	}

	public void setNTotalLin(String totalLin) {
		nTotalLin = totalLin;
	}

	public String getNDias() {
		return nDias;
	}

	public void setNDias(String dias) {
		nDias = dias;
	}
	
	public String getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(String fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}	

	public String getSegmentoOrigen() {
		return segmentoOrigen;
	}

	public void setSegmentoOrigen(String segmentoOrigen) {
		this.segmentoOrigen = segmentoOrigen;
	}
	
	public String getultimaBusquedaTipo() {
		return ultimaBusquedaTipo;
	}

	public void setultimaBusquedaTipo(String ultimaBusquedaTipo) {
		this.ultimaBusquedaTipo = ultimaBusquedaTipo;
	}	
}
