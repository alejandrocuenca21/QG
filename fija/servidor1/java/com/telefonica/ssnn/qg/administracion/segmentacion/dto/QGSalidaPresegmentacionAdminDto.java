package com.telefonica.ssnn.qg.administracion.segmentacion.dto;

/** Salida para los servicios que cargan y guarda la Administracion en Presegmentaciones
 * 
 * @author jacastano
 *
 */

public class QGSalidaPresegmentacionAdminDto{

	private String codTipoParametro;
	private String nomTipoParametro;
	private String desTipoParametro;	
	private String desValorAlfa;
	private String desValorNum;
	private String fxIniVigencia;
	private String fxFinVigencia;
	private String fxUltimaMod;
	
	public String getDesValorAlfa() {
		return desValorAlfa;
	}
	public void setDesValorAlfa(String desValorAlfa) {
		this.desValorAlfa = desValorAlfa;
	}
	public String getDesValorNum() {
		return desValorNum;
	}
	public void setDesValorNum(String desValorNum) {
		this.desValorNum = desValorNum;
	}
	public String getFxIniVigencia() {
		return fxIniVigencia;
	}
	public void setFxIniVigencia(String fxIniVigencia) {
		this.fxIniVigencia = fxIniVigencia;
	}
	public String getFxFinVigencia() {
		return fxFinVigencia;
	}
	public void setFxFinVigencia(String fxFinVigencia) {
		this.fxFinVigencia = fxFinVigencia;
	}
	public String getFxUltimaMod() {
		return fxUltimaMod;
	}
	public void setFxUltimaMod(String fxUltimaMod) {
		this.fxUltimaMod = fxUltimaMod;
	}
	public String getCodTipoParametro() {
		return codTipoParametro;
	}
	public void setCodTipoParametro(String codTipoParametro) {
		this.codTipoParametro = codTipoParametro;
	}
	public String getNomTipoParametro() {
		return nomTipoParametro;
	}
	public void setNomTipoParametro(String nomTipoParametro) {
		this.nomTipoParametro = nomTipoParametro;
	}
	public String getDesTipoParametro() {
		return desTipoParametro;
	}
	public void setDesTipoParametro(String desTipoParametro) {
		this.desTipoParametro = desTipoParametro;
	}
}