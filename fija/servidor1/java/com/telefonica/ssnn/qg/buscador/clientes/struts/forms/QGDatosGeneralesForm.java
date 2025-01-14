/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.clientes.struts.forms;

import org.apache.log4j.Logger;

import com.telefonica.ssnn.qg.struts.QGBaseForm;

/**
 * 
 * Formulario de Datos Generales.
 * 
 * @author cnunezba
 * 
 * @struts.form name="QGDatosGeneralesForm" 
 *
 */
public class QGDatosGeneralesForm extends QGBaseForm{

	private static final long serialVersionUID = 1275280152453767737L;
	
	private static final Logger logger = Logger.getLogger(QGDatosGeneralesForm.class);	
	
	private String origen;
	private String modalidad;	
	private String codCliente;
	private String tipoDoc;
	private String docIdentif;
	private String razonSocial;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String estado;
	private String segmento;
	private String subsegmento;
	

	private String datosGeneralesJSON;

	public String getDatosGeneralesJSON() {
		return datosGeneralesJSON;
	}

	public void setDatosGeneralesJSON(String datosGeneralesJSON) {
		this.datosGeneralesJSON = datosGeneralesJSON;
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

	public String getDocIdentif() {
		return docIdentif;
	}

	public void setDocIdentif(String docIdentif) {
		this.docIdentif = docIdentif;
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

	public String getSegmento() {
		return segmento;
	}

	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}

	public String getSubsegmento() {
		return subsegmento;
	}

	public void setSubsegmento(String subsegmento) {
		this.subsegmento = subsegmento;
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

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
}
