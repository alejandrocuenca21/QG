/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.dto;


/**
 * @author mgvinuesa
 *
 */
public class QGSegmentacionesTraduccionDto {
	/**
	 * Codigo de actuacion sobre la segmentacion
	 */
	private String codActuacion;

	private String codigoSegmentoFijo;
	
	private String descSegmentoFijo;
	
	private String codigoSubSegmentoFijo;
	
	private String descSubSegmentoFijo;
	
	private String codigoSegmentoMovil;
	
	private String descSegmentoMovil;
	
	private String codigoSubSegmentoMovil;
	
	private String descSubSegmentoMovil;
	
	/**
	 * Fecha de inicio de vigencia
	 */
	private String fechaIniVigencia;
	/**
	 * Fecha de fin de vigencia
	 */
	private String fechaFinVigencia;
	/**
	 * Nueva fecha de fin de vigencia para las bajas
	 */
	private String fechaFinVigenciaBaja;
	/**
	 * Fecha de alta
	 */
	private String fechaAlta;
	/**
	 * Fecha de modificacion
	 */
	private String fechaMod;
	/**
	 * Usuario alta de la segmentacion
	 */
	private String usuarioAlta;
	/**
	 * Usuario modificacion
	 */
	private String usuarioMod;
	

	/**
	 * Plan de cuentas
	 */
	private String planCuentas;
	
	public QGSegmentacionesTraduccionDto(){
		
	}
		
	public QGSegmentacionesTraduccionDto(String codActuacion,
			String codigoSegmentoFijo, String descSegmentoFijo,
			String codigoSubSegmentoFijo, String descSubSegmentoFijo,
			String codigoSegmentoMovil, String descSegmentoMovil,
			String codigoSubSegmentoMovil, String descSubSegmentoMovil,
			String fechaIniVigencia, String fechaFinVigencia, String fechaBaja,
			String fechaAlta, String fechaMod, String usuarioAlta,
			String usuarioBaja, String usuarioMod, String planCuentas) {
		super();
		this.codActuacion = codActuacion;
		this.codigoSegmentoFijo = codigoSegmentoFijo;
		this.descSegmentoFijo = descSegmentoFijo;
		this.codigoSubSegmentoFijo = codigoSubSegmentoFijo;
		this.descSubSegmentoFijo = descSubSegmentoFijo;
		this.codigoSegmentoMovil = codigoSegmentoMovil;
		this.descSegmentoMovil = descSegmentoMovil;
		this.codigoSubSegmentoMovil = codigoSubSegmentoMovil;
		this.descSubSegmentoMovil = descSubSegmentoMovil;
		this.fechaIniVigencia = fechaIniVigencia;
		this.fechaFinVigencia = fechaFinVigencia;
		this.fechaAlta = fechaAlta;
		this.fechaMod = fechaMod;
		this.usuarioAlta = usuarioAlta;
		this.usuarioMod = usuarioMod;
		this.planCuentas = planCuentas;
	}
	
	public String getCodActuacion() {
		return codActuacion;
	}
	public void setCodActuacion(String codActuacion) {
		this.codActuacion = codActuacion;
	}
	
	public String getFechaIniVigencia() {
		return fechaIniVigencia;
	}
	public void setFechaIniVigencia(String fechaIniVigencia) {
		this.fechaIniVigencia = fechaIniVigencia;
	}
	public String getFechaFinVigencia() {
		return fechaFinVigencia;
	}
	public void setFechaFinVigencia(String fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public String getUsuarioAlta() {
		return usuarioAlta;
	}
	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}
	public String getPlanCuentas() {
		return planCuentas;
	}
	public void setPlanCuentas(String planCuentas) {
		this.planCuentas = planCuentas;
	}
	public String getCodigoSegmentoFijo() {
		return codigoSegmentoFijo;
	}
	public void setCodigoSegmentoFijo(String codigoSegmentoFijo) {
		this.codigoSegmentoFijo = codigoSegmentoFijo;
	}
	public String getDescSegmentoFijo() {
		return descSegmentoFijo;
	}
	public void setDescSegmentoFijo(String descSegmentoFijo) {
		this.descSegmentoFijo = descSegmentoFijo;
	}
	public String getCodigoSubSegmentoFijo() {
		return codigoSubSegmentoFijo;
	}
	public void setCodigoSubSegmentoFijo(String codigoSubSegmentoFijo) {
		this.codigoSubSegmentoFijo = codigoSubSegmentoFijo;
	}
	public String getDescSubSegmentoFijo() {
		return descSubSegmentoFijo;
	}
	public void setDescSubSegmentoFijo(String descSubSegmentoFijo) {
		this.descSubSegmentoFijo = descSubSegmentoFijo;
	}
	public String getCodigoSegmentoMovil() {
		return codigoSegmentoMovil;
	}
	public void setCodigoSegmentoMovil(String codigoSegmentoMovil) {
		this.codigoSegmentoMovil = codigoSegmentoMovil;
	}
	public String getDescSegmentoMovil() {
		return descSegmentoMovil;
	}
	public void setDescSegmentoMovil(String descSegmentoMovil) {
		this.descSegmentoMovil = descSegmentoMovil;
	}
	public String getCodigoSubSegmentoMovil() {
		return codigoSubSegmentoMovil;
	}
	public void setCodigoSubSegmentoMovil(String codigoSubSegmentoMovil) {
		this.codigoSubSegmentoMovil = codigoSubSegmentoMovil;
	}
	public String getDescSubSegmentoMovil() {
		return descSubSegmentoMovil;
	}
	public void setDescSubSegmentoMovil(String descSubSegmentoMovil) {
		this.descSubSegmentoMovil = descSubSegmentoMovil;
	}
	public String getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getFechaMod() {
		return fechaMod;
	}
	public void setFechaMod(String fechaMod) {
		this.fechaMod = fechaMod;
	}
	public String getUsuarioMod() {
		return usuarioMod;
	}
	public void setUsuarioMod(String usuarioMod) {
		this.usuarioMod = usuarioMod;
	}

	public String getFechaFinVigenciaBaja() {
		return fechaFinVigenciaBaja;
	}

	public void setFechaFinVigenciaBaja(String fechaFinVigenciaBaja) {
		this.fechaFinVigenciaBaja = fechaFinVigenciaBaja;
	}
	
	


}
