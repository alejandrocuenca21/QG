/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.clientes.dto;

/**
 * @author jacastano
 *
 */
public class QGBuscadorClientesDto {
	
	private String unidadNegocio;
	
	private String modalidadMovil;
	
	private String codigoCliente;
		
	private String tipoDocumento;
	
	private String numeroDocumento;
	
	private String razonSocial;
	
	private String nombreCliente;
	
	private String primerApellido;
	
	private String segundoApellido;
	
	private String textoPaginacion;
	
	private Integer numeroOcurrencias;
	
	private String estadoCliente;
	
	private String codigoSegmento;
	
	private String codigoSubsegmento;
	
	private String indicadorPaginacion;
	
	private String nombreRazonSocial;
	
	private String segmentoSubsegmento;

	private String inUniNgcCliGbl;
	
	private String inModalidad;	
	
	private String coTipoDocumento;	
	
	private String dsTipoDocumento;
	
	private String dsSegmento;
	
	private String dsSubsegmento;
	
	private boolean ocultarHistorico = true;
	
	public boolean isOcultarHistorico() {
		return false;
	}

	public void setOcultarHistorico(boolean ocultarHistorico) {
		this.ocultarHistorico = ocultarHistorico;
	}
	
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}

	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}

	public String getSegmentoSubsegmento() {
		return segmentoSubsegmento;
	}

	public void setSegmentoSubsegmento(String segmentoSubsegmento) {
		this.segmentoSubsegmento = segmentoSubsegmento;
	}

	public String getUnidadNegocio() {
		return unidadNegocio;
	}

	public void setUnidadNegocio(String unidadNegocio) {
		this.unidadNegocio = unidadNegocio;
	}
	
	public String getModalidadMovil() {
		return modalidadMovil;
	}

	public void setModalidadMovil(String modalidadMovil) {
		this.modalidadMovil = modalidadMovil;
	}	

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getTextoPaginacion() {
		return textoPaginacion;
	}

	public void setTextoPaginacion(String textoPaginacion) {
		this.textoPaginacion = textoPaginacion;
	}

	public Integer getNumeroOcurrencias() {
		return numeroOcurrencias;
	}

	public void setNumeroOcurrencias(Integer numeroOcurrencias) {
		this.numeroOcurrencias = numeroOcurrencias;
	}

	public String getEstadoCliente() {
		return estadoCliente;
	}

	public void setEstadoCliente(String estadoCliente) {
		this.estadoCliente = estadoCliente;
	}

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

	public String getIndicadorPaginacion() {
		return indicadorPaginacion;
	}

	public void setIndicadorPaginacion(String indicadorPaginacion) {
		this.indicadorPaginacion = indicadorPaginacion;
	}

	public String getInUniNgcCliGbl() {
		return inUniNgcCliGbl;
	}

	public void setInUniNgcCliGbl(String inUniNgcCliGbl) {
		this.inUniNgcCliGbl = inUniNgcCliGbl;
	}
	
	public String getInModalidad() {
		return inModalidad;
	}	

	public void setInModalidad(String inModalidad) {
		this.inModalidad = inModalidad;		
	}
	
	public String getCoTipoDocumento() {
		return coTipoDocumento;
	}

	public void setCoTipoDocumento(String coTipoDocumento) {
		this.coTipoDocumento = coTipoDocumento;
	}	

	public String getDsTipoDocumento() {
		return dsTipoDocumento;
	}

	public void setDsTipoDocumento(String dsTipoDocumento) {
		this.dsTipoDocumento = dsTipoDocumento;
	}

	public String getDsSegmento() {
		return dsSegmento;
	}

	public void setDsSegmento(String dsSegmento) {
		this.dsSegmento = dsSegmento;
	}

	public String getDsSubsegmento() {
		return dsSubsegmento;
	}

	public void setDsSubsegmento(String dsSubsegmento) {
		this.dsSubsegmento = dsSubsegmento;
	}
}
