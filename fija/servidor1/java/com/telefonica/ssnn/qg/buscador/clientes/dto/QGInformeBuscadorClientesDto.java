package com.telefonica.ssnn.qg.buscador.clientes.dto;

public class QGInformeBuscadorClientesDto {
	
	private String origen;
	private String modalidad;	
	private String codCliente;
	private String tipoDoc;
	private String docIdent;
	private String nombre;
	private String estado;
	private String segmentoSubseg;
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
	public String getCodCliente() {
		return codCliente;
	}
	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}
	public String getTipoDoc() {
		return tipoDoc;
	}
	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	public String getDocIdent() {
		return docIdent;
	}
	public void setDocIdent(String docIdent) {
		this.docIdent = docIdent;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getSegmentoSubseg() {
		return segmentoSubseg;
	}
	public void setSegmentoSubseg(String segmentoSubseg) {
		this.segmentoSubseg = segmentoSubseg;
	}

}
