package com.telefonica.ssnn.qg.administracion.segmentacion.dto;
/**
 * Clase para realizar la busqueda de segmentaciones por criterio
 * @author mgvinuesa
 *
 */
public class QGSegmentacionesBusquedaDto {
	
	private String unidad;
	
	private String operacion;
	
	private String codigoSegmentoOrigen;
	
	private String descSegmentoOrigen;
	
	private String codigoSubSegmentoOrigen;
	
	private String descSubSegmentoOrigen;
	
	private String actuacionBusqueda;

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getCodigoSegmentoOrigen() {
		return codigoSegmentoOrigen;
	}

	public void setCodigoSegmentoOrigen(String codigoSegmentoOrigen) {
		this.codigoSegmentoOrigen = codigoSegmentoOrigen;
	}

	public String getDescSegmentoOrigen() {
		return descSegmentoOrigen;
	}

	public void setDescSegmentoOrigen(String descSegmentoOrigen) {
		this.descSegmentoOrigen = descSegmentoOrigen;
	}

	public String getCodigoSubSegmentoOrigen() {
		return codigoSubSegmentoOrigen;
	}

	public void setCodigoSubSegmentoOrigen(String codigoSubSegmentoOrigen) {
		this.codigoSubSegmentoOrigen = codigoSubSegmentoOrigen;
	}

	public String getDescSubSegmentoOrigen() {
		return descSubSegmentoOrigen;
	}

	public void setDescSubSegmentoOrigen(String descSubSegmentoOrigen) {
		this.descSubSegmentoOrigen = descSubSegmentoOrigen;
	}

	public String getActuacionBusqueda() {
		return actuacionBusqueda;
	}

	public void setActuacionBusqueda(String actuacionBusqueda) {
		this.actuacionBusqueda = actuacionBusqueda;
	}
	
	
}
