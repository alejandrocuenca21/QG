package com.telefonica.ssnn.qg.administracion.segmentacion.dto;

/** Entrada para los servicios que cargan y guarda la Administracion en Presegmentaciones
 * 
 * @author jacastano
 *
 */

public class QGEntradaPresegmentacionAdminDto{

	private String inActuacion;
	private String codParametro;
	private String desParametro;
	private String tipoValor;
	private String valorAlfa;
	private String valorNum;
	
	public String getInActuacion() {
		return inActuacion;
	}
	public void setInActuacion(String inActuacion) {
		this.inActuacion = inActuacion;
	}
	public String getCodParametro() {
		return codParametro;
	}
	public void setCodParametro(String codParametro) {
		this.codParametro = codParametro;
	}
	public String getDesParametro() {
		return desParametro;
	}
	public void setDesParametro(String desParametro) {
		this.desParametro = desParametro;
	}
	public String getTipoValor() {
		return tipoValor;
	}
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}
	public String getValorAlfa() {
		return valorAlfa;
	}
	public void setValorAlfa(String valorAlfa) {
		this.valorAlfa = valorAlfa;
	}
	public String getValorNum() {
		return valorNum;
	}
	public void setValorNum(String valorNum) {
		this.valorNum = valorNum;
	}
	
	
}