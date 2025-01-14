/**
 * 
 */
package com.telefonica.ssnn.qg.administracion.segmentacion.dto;


/**
 * @author mgvinuesa
 *
 */
public class QGSegmentacionesDto {
	/**
	 * Codigo de actuacion sobre la segmentacion
	 */
	private String codActuacion;
	/**
	 * Unidad
	 */
	private String unidad;
	/**
	 * Descripcion de la unidad
	 */
	private String descUnidad;
	
	/**
	 * Operacion (alta o baja)
	 */
	private String operacion;
	/**
	 * Descripcion de la operacion
	 */
	private String descOperacion;
	
	/**
	 * Codigo de segmento origen
	 */
	private String segmentoOrigen;
	/**
	 * Codigo de subsegmento origen
	 */
	private String subSegmentoOrigen;
	/**
	 * Codigo de segmento destino
	 */
	private String segmentoDestino;
	/**
	 * Codigo de subsegmento destino
	 */
	private String subSegmentoDestino;
	/**
	 * Fecha de inicio de vigencia
	 */
	private String fechaIniVigencia;
	/**
	 * Fecha de fin de vigencia
	 */
	private String fechaFinVigencia;
	/**
	 * Fecha de baja de la segmentacion
	 */
	private String fechaBaja;
	/**
	 * Usuario alta de la segmentacion
	 */
	private String usuarioAlta;
	/**
	 * Usuario baja de la segmentacion
	 */
	private String usuarioBaja;
	/**
	 * Obtiene la clave primaria de la segmentacion, concatenando los atributos principales
	 *  - unidad + operacion + segmento origen + subsegmento origen
	 */
	private String keySegmentacion;
	/**
	 * Descripcion del segmento origen
	 */
	private String segmentoOrigenDescripcion;
	/**
	 * Descripcion del subsegmento origen
	 */
	private String subSegmentoOrigenDescripcion;
	/**
	 * Descripcion del segmento destino
	 */
	private String segmentoDestinoDescripcion;
	/**
	 * Descripcion del subsegmento destino
	 */
	private String subSegmentoDestinoDescripcion;


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

	public String getSegmentoOrigen() {
		return segmentoOrigen;
	}

	public void setSegmentoOrigen(String segmentoOrigen) {
		this.segmentoOrigen = segmentoOrigen;
	}
	

	public String getDescUnidad() {
		return descUnidad;
	}

	public void setDescUnidad(String descUnidad) {
		this.descUnidad = descUnidad;
	}

	public String getDescOperacion() {
		return descOperacion;
	}

	public void setDescOperacion(String descOperacion) {
		this.descOperacion = descOperacion;
	}

	public String getSubSegmentoOrigen() {
		return subSegmentoOrigen;
	}

	public void setSubSegmentoOrigen(String subSegmentoOrigen) {
		this.subSegmentoOrigen = subSegmentoOrigen;
	}

	public String getSegmentoDestino() {
		return segmentoDestino;
	}

	public void setSegmentoDestino(String segmentoDestino) {
		this.segmentoDestino = segmentoDestino;
	}

	public String getSubSegmentoDestino() {
		return subSegmentoDestino;
	}

	public void setSubSegmentoDestino(String subSegmentoDestino) {
		this.subSegmentoDestino = subSegmentoDestino;
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

	public String getUsuarioBaja() {
		return usuarioBaja;
	}

	public void setUsuarioBaja(String usuarioBaja) {
		this.usuarioBaja = usuarioBaja;
	}

	public String getCodActuacion() {
		return codActuacion;
	}

	public void setCodActuacion(String codActuacion) {
		this.codActuacion = codActuacion;
	}

	/**
	 * Concatena los valores de clave primaria para posteriomente comprobar la duplicidad
	 * de datos
	 * @return
	 */
	public String getKeySegmentacion(){
		//Concatena los valores princiaples
		return this.getUnidad()+this.getOperacion()+this.getSegmentoOrigen()+this.getSubSegmentoOrigen();
	}

	public String getSegmentoOrigenDescripcion() {
		return segmentoOrigenDescripcion;
	}

	public void setSegmentoOrigenDescripcion(String segmentoOrigenDescripcion) {
		this.segmentoOrigenDescripcion = segmentoOrigenDescripcion;
	}

	public String getSubSegmentoOrigenDescripcion() {
		return subSegmentoOrigenDescripcion;
	}

	public void setSubSegmentoOrigenDescripcion(String subSegmentoOrigenDescripcion) {
		this.subSegmentoOrigenDescripcion = subSegmentoOrigenDescripcion;
	}

	public String getSegmentoDestinoDescripcion() {
		return segmentoDestinoDescripcion;
	}

	public void setSegmentoDestinoDescripcion(String segmentoDestinoDescripcion) {
		this.segmentoDestinoDescripcion = segmentoDestinoDescripcion;
	}

	public String getSubSegmentoDestinoDescripcion() {
		return subSegmentoDestinoDescripcion;
	}

	public void setSubSegmentoDestinoDescripcion(
			String subSegmentoDestinoDescripcion) {
		this.subSegmentoDestinoDescripcion = subSegmentoDestinoDescripcion;
	}

	public String getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}


}
