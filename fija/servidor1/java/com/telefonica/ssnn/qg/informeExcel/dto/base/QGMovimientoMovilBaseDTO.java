package com.telefonica.ssnn.qg.informeExcel.dto.base;

import java.util.ArrayList;
import java.util.List;

import com.telefonica.ssnn.qg.util.QGInformeUtils;


public class QGMovimientoMovilBaseDTO {

	private Integer altaDirecciones;
	private Integer altaCliente;
	private Integer altaPreCliente;
	private Integer bajaDirElectronicas;
	private Integer cancelacion;
	private Integer cambioEstado;
	private Integer modificacionDir;
	private Integer modificacionDatos;
	private Integer modificacionEstab;
	private Integer modificacionImpresion;
	private Integer migracionPre;
	private Integer modificacionSegmentacion;
	private Integer asignacionSegmentacion;
	
	
	public QGMovimientoMovilBaseDTO() {
		altaDirecciones = new Integer(0);
		altaCliente = new Integer(0);
		altaPreCliente = new Integer(0);
		bajaDirElectronicas = new Integer(0);
		cancelacion = new Integer(0);
		cambioEstado = new Integer(0);
		modificacionDir = new Integer(0);
		modificacionDatos = new Integer(0);
		modificacionEstab = new Integer(0);
		modificacionImpresion = new Integer(0);
		migracionPre = new Integer(0);
		modificacionSegmentacion = new Integer(0);
		asignacionSegmentacion = new Integer(0);
	}


	/**
	 * Suma todos los valores
	 */
	public Integer getTotal(){
		
		List listaValores = new ArrayList();
		listaValores.add(this.altaCliente);
		listaValores.add(this.altaDirecciones);
		listaValores.add(this.altaPreCliente);
		listaValores.add(this.asignacionSegmentacion);
		listaValores.add(this.bajaDirElectronicas);
		listaValores.add(this.cambioEstado);
		listaValores.add(this.cancelacion);
		listaValores.add(this.migracionPre);
		listaValores.add(this.modificacionDatos);
		listaValores.add(this.modificacionDir);
		listaValores.add(this.modificacionEstab);
		listaValores.add(this.modificacionImpresion);
		listaValores.add(this.modificacionSegmentacion);
				
		return QGInformeUtils.sumar(listaValores);
		
	}





	public Integer getAltaDirecciones() {
		return altaDirecciones;
	}





	public void setAltaDirecciones(Integer altaDirecciones) {
		this.altaDirecciones = altaDirecciones;
	}





	public Integer getAltaCliente() {
		return altaCliente;
	}





	public void setAltaCliente(Integer altaCliente) {
		this.altaCliente = altaCliente;
	}





	public Integer getAltaPreCliente() {
		return altaPreCliente;
	}





	public void setAltaPreCliente(Integer altaPreCliente) {
		this.altaPreCliente = altaPreCliente;
	}





	public Integer getBajaDirElectronicas() {
		return bajaDirElectronicas;
	}





	public void setBajaDirElectronicas(Integer bajaDirElectronicas) {
		this.bajaDirElectronicas = bajaDirElectronicas;
	}





	public Integer getCancelacion() {
		return cancelacion;
	}





	public void setCancelacion(Integer cancelacion) {
		this.cancelacion = cancelacion;
	}





	public Integer getCambioEstado() {
		return cambioEstado;
	}





	public void setCambioEstado(Integer cambioEstado) {
		this.cambioEstado = cambioEstado;
	}





	public Integer getModificacionDir() {
		return modificacionDir;
	}





	public void setModificacionDir(Integer modificacionDir) {
		this.modificacionDir = modificacionDir;
	}





	public Integer getModificacionDatos() {
		return modificacionDatos;
	}





	public void setModificacionDatos(Integer modificacionDatos) {
		this.modificacionDatos = modificacionDatos;
	}





	public Integer getModificacionEstab() {
		return modificacionEstab;
	}





	public void setModificacionEstab(Integer modificacionEstab) {
		this.modificacionEstab = modificacionEstab;
	}





	public Integer getModificacionImpresion() {
		return modificacionImpresion;
	}





	public void setModificacionImpresion(Integer modificacionImpresion) {
		this.modificacionImpresion = modificacionImpresion;
	}





	public Integer getMigracionPre() {
		return migracionPre;
	}





	public void setMigracionPre(Integer migracionPre) {
		this.migracionPre = migracionPre;
	}





	public Integer getModificacionSegmentacion() {
		return modificacionSegmentacion;
	}





	public void setModificacionSegmentacion(Integer modificacionSegmentacion) {
		this.modificacionSegmentacion = modificacionSegmentacion;
	}





	public Integer getAsignacionSegmentacion() {
		return asignacionSegmentacion;
	}





	public void setAsignacionSegmentacion(Integer asignacionSegmentacion) {
		this.asignacionSegmentacion = asignacionSegmentacion;
	}

	
	
}
