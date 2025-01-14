package com.telefonica.ssnn.qg.informeExcel.dto.base;

import java.util.ArrayList;
import java.util.List;

import com.telefonica.ssnn.qg.util.QGInformeUtils;


/**
 * Movimiento Fijo Base. Contiene todos los atributos necesarios para el
 * guardado de movimientos fijos
 * 
 * @author mgvinuesa
 * 
 */
public class QGMovimientoFijoBaseDTO {
	
	private Integer alta;
	private Integer baja;
	private Integer modificacion;
	private Integer cambioSeg;
	private Integer cambioSegDif;
	private Integer bajaDif;
	private Integer reactivacion;
	
	public QGMovimientoFijoBaseDTO() {
		alta = new Integer(0);
		baja = new Integer(0);
		modificacion = new Integer(0);
		cambioSeg = new Integer(0);
		cambioSegDif = new Integer(0);
		bajaDif = new Integer(0);
		reactivacion = new Integer(0);
	}

	/**
	 * Suma todos los valores
	 */
	public Integer getTotal(){
		
		List listaValores = new ArrayList();
		listaValores.add(this.alta);
		listaValores.add(this.baja);
		listaValores.add(this.bajaDif);
		listaValores.add(this.cambioSeg);
		listaValores.add(this.cambioSegDif);
		listaValores.add(this.modificacion);
		listaValores.add(this.reactivacion);
				
		return QGInformeUtils.sumar(listaValores);
		
	}



	public Integer getAlta() {
		return alta;
	}



	public void setAlta(Integer alta) {
		this.alta = alta;
	}



	public Integer getBaja() {
		return baja;
	}



	public void setBaja(Integer baja) {
		this.baja = baja;
	}



	public Integer getModificacion() {
		return modificacion;
	}



	public void setModificacion(Integer modificacion) {
		this.modificacion = modificacion;
	}



	public Integer getCambioSeg() {
		return cambioSeg;
	}



	public void setCambioSeg(Integer cambioSeg) {
		this.cambioSeg = cambioSeg;
	}



	public Integer getCambioSegDif() {
		return cambioSegDif;
	}



	public void setCambioSegDif(Integer cambioSegDif) {
		this.cambioSegDif = cambioSegDif;
	}



	public Integer getBajaDif() {
		return bajaDif;
	}



	public void setBajaDif(Integer bajaDif) {
		this.bajaDif = bajaDif;
	}



	public Integer getReactivacion() {
		return reactivacion;
	}



	public void setReactivacion(Integer reactivacion) {
		this.reactivacion = reactivacion;
	}



	
}
