package com.telefonica.ssnn.qg.administracion.segmentacion.dto;

/** Entrada para los servicios que cargan los combos en Presegmentaciones
 * 
 * @author jacastano
 *
 */

public class QGSalidaPresegmentacionDto{

	private String coUoVenta;
	private String coNemoTandem;
	
	public String getCoUoVenta() {
		return coUoVenta;
	}
	public void setCoUoVenta(String coUoVenta) {
		this.coUoVenta = coUoVenta;
	}
	public String getCoNemoTandem() {
		return coNemoTandem;
	}
	public void setCoNemoTandem(String coNemoTandem) {
		this.coNemoTandem = coNemoTandem;
	}
}