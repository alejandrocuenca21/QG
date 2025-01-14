/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.consentimientos.dto;

/**
 * @author vsierra
 *
 */
public class QGEntradaHistorialDto {

	private String codDerechoLOPD;
	
	private Long secTipoObjeto;
	
	private String codTipoObjeto;
	
	private String descObjetoCD;
	
	private String codCliente;

	public String getCodDerechoLOPD() {
		return codDerechoLOPD;
	}

	public void setCodDerechoLOPD(String codDerechoLOPD) {
		this.codDerechoLOPD = codDerechoLOPD;
	}

	public Long getSecTipoObjeto() {
		return secTipoObjeto;
	}

	public void setSecTipoObjeto(Long secTipoObjeto) {
		this.secTipoObjeto = secTipoObjeto;
	}

	public String getCodTipoObjeto() {
		return codTipoObjeto;
	}

	public void setCodTipoObjeto(String codTipoObjeto) {
		this.codTipoObjeto = codTipoObjeto;
	}

	public String getDescObjetoCD() {
		return descObjetoCD;
	}

	public void setDescObjetoCD(String descObjetoCD) {
		this.descObjetoCD = descObjetoCD;
	}

	public String getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}
}
