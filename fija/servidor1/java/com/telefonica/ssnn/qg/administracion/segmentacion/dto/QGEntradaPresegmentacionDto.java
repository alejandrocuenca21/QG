package com.telefonica.ssnn.qg.administracion.segmentacion.dto;

/** Entrada para los servicios que cargan los combos en Presegmentaciones
 * 
 * @author jacastano
 *
 */

public class QGEntradaPresegmentacionDto{

	private String codigoSegmento;
	private String codigoSubsegmento;
	private String ofAtencion;
	
	public String getCodigoSegmento() {
		return codigoSegmento;
	}
	public void setCodigoSegmento(String codigoSegmento) {
		this.codigoSegmento = codigoSegmento;
	}
	public String getCodigoSubsegmento() {
		return codigoSubsegmento;
	}
	public void setCodigoSubsegmento(String codigoSubsegmento) {
		this.codigoSubsegmento = codigoSubsegmento;
	}
	public String getOfAtencion() {
		return ofAtencion;
	}
	public void setOfAtencion(String ofAtencion) {
		this.ofAtencion = ofAtencion;
	}
}