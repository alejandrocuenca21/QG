package com.telefonica.ssnn.qg.buscador.clientes.dto;

import java.util.Date;

public class QGHistorialClienteDto {

	private String codCliente;
	
	private String nombre;
	
	private Date fechaConsulta;
	
	private String origen;
	
	private String modalidad;	
	
	private String tipoDocumento;
	
	private String numDocumento;
	
	private String codSegmento;
	
	private String codSubSegmento;
	
	private String estado;

	public String getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaConsulta() {
		return fechaConsulta;
	}

	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}
	
	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
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

	public String getCodSegmento() {
		return codSegmento;
	}

	public void setCodSegmento(String codSegmento) {
		this.codSegmento = codSegmento;
	}

	public String getCodSubSegmento() {
		return codSubSegmento;
	}

	public void setCodSubSegmento(String codSubSegmento) {
		this.codSubSegmento = codSubSegmento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
