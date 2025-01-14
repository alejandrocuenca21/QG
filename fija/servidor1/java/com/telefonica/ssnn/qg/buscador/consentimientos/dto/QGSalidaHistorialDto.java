/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.consentimientos.dto;

import java.util.Date;

/**
 * @author vsierra
 *
 */
public class QGSalidaHistorialDto {

	private String codigoOperacion;
	
	private String descOperacion;
	
	private Date fxOperacion;
	
	private String descObjetoCD;

	public String getCodigoOperacion() {
		return codigoOperacion;
	}

	public void setCodigoOperacion(String codigoOperacion) {
		this.codigoOperacion = codigoOperacion;
	}

	public String getDescOperacion() {
		return descOperacion;
	}

	public void setDescOperacion(String descOperacion) {
		this.descOperacion = descOperacion;
	}

	public Date getFxOperacion() {
		return fxOperacion;
	}

	public void setFxOperacion(Date fxOperacion) {
		this.fxOperacion = fxOperacion;
	}

	public String getDescObjetoCD() {
		return descObjetoCD;
	}

	public void setDescObjetoCD(String descObjetoCD) {
		this.descObjetoCD = descObjetoCD;
	}
}
