package com.telefonica.ssnn.qg.informeExcel.dto.base;

import java.util.ArrayList;
import java.util.List;

import com.telefonica.ssnn.qg.util.QGInformeUtils;


public class QGMovimientoPrepagoBaseDTO {

	private Integer altaPrepago;
	private Integer bajaPrepago;
	private Integer modificacionPrepago;	
	
	public QGMovimientoPrepagoBaseDTO() {
		altaPrepago = new Integer(0);
		bajaPrepago = new Integer(0);
		modificacionPrepago = new Integer(0);
	}

	/**
	 * Suma todos los valores
	 */
	public Integer getTotal(){
		
		List listaValores = new ArrayList();
		listaValores.add(this.altaPrepago);
		listaValores.add(this.bajaPrepago);
		listaValores.add(this.modificacionPrepago);
				
		return QGInformeUtils.sumar(listaValores);		
	}

	public Integer getAltaPrepago() {
		return altaPrepago;
	}

	public void setAltaPrepago(Integer altaPrepago) {
		this.altaPrepago = altaPrepago;
	}

	public Integer getBajaPrepago() {
		return bajaPrepago;
	}

	public void setBajaPrepago(Integer bajaPrepago) {
		this.bajaPrepago = bajaPrepago;
	}
	
	public Integer getModificacionPrepago() {
		return modificacionPrepago;
	}

	public void setModificacionPrepago(Integer modificacionPrepago) {
		this.modificacionPrepago = modificacionPrepago;
	}	
}
