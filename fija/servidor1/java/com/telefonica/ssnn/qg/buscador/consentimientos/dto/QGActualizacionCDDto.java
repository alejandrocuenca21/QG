/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.consentimientos.dto;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author vsierra
 *
 */
public class QGActualizacionCDDto {

	private String codigoLOPD;
	
	private ArrayList situacionActual;
	
	private String codigoCliente;
	
	private String codOperacion;
	
	private String codEstadoCD;
	
	private String codGestion;
	
	private String valorExplotacion;
	
	private Date fxCambioEstado;
	
	private ArrayList situacionNueva;
	
	private String descEmail;
	
	private String indIncidencia;
	
	private String descObservacion;
	
	private String codCampaniaCD;
	
	private String codMedioComunicacion;
	
	private String codTipoUbicacion;
	
	private String descUbicacion;
	
	private Date fxIniVigencia;
	
	private Date fxFinVigencia;
	
	private String codUsuario;

	public String getCodigoLOPD() {
		return codigoLOPD;
	}

	public void setCodigoLOPD(String codigoLOPD) {
		this.codigoLOPD = codigoLOPD;
	}

	public ArrayList getSituacionActual() {
		return situacionActual;
	}

	public void setSituacionActual(ArrayList situacionActual) {
		this.situacionActual = situacionActual;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getCodOperacion() {
		return codOperacion;
	}

	public void setCodOperacion(String codOperacion) {
		this.codOperacion = codOperacion;
	}

	public String getCodEstadoCD() {
		return codEstadoCD;
	}

	public void setCodEstadoCD(String codEstadoCD) {
		this.codEstadoCD = codEstadoCD;
	}

	public String getCodGestion() {
		return codGestion;
	}

	public void setCodGestion(String codGestion) {
		this.codGestion = codGestion;
	}

	public String getValorExplotacion() {
		return valorExplotacion;
	}

	public void setValorExplotacion(String valorExplotacion) {
		this.valorExplotacion = valorExplotacion;
	}

	public Date getFxCambioEstado() {
		return fxCambioEstado;
	}

	public void setFxCambioEstado(Date fxCambioEstado) {
		this.fxCambioEstado = fxCambioEstado;
	}

	public ArrayList getSituacionNueva() {
		return situacionNueva;
	}

	public void setSituacionNueva(ArrayList situacionNueva) {
		this.situacionNueva = situacionNueva;
	}

	public String getDescEmail() {
		return descEmail;
	}

	public void setDescEmail(String descEmail) {
		this.descEmail = descEmail;
	}

	public String getIndIncidencia() {
		return indIncidencia;
	}

	public void setIndIncidencia(String indIncidencia) {
		this.indIncidencia = indIncidencia;
	}

	public String getDescObservacion() {
		return descObservacion;
	}

	public void setDescObservacion(String descObservacion) {
		this.descObservacion = descObservacion;
	}

	public String getCodCampaniaCD() {
		return codCampaniaCD;
	}

	public void setCodCampaniaCD(String codCampaniaCD) {
		this.codCampaniaCD = codCampaniaCD;
	}

	public String getCodMedioComunicacion() {
		return codMedioComunicacion;
	}

	public void setCodMedioComunicacion(String codMedioComunicacion) {
		this.codMedioComunicacion = codMedioComunicacion;
	}

	public String getCodTipoUbicacion() {
		return codTipoUbicacion;
	}

	public void setCodTipoUbicacion(String codTipoUbicacion) {
		this.codTipoUbicacion = codTipoUbicacion;
	}

	public String getDescUbicacion() {
		return descUbicacion;
	}

	public void setDescUbicacion(String descUbicacion) {
		this.descUbicacion = descUbicacion;
	}

	public Date getFxIniVigencia() {
		return fxIniVigencia;
	}

	public void setFxIniVigencia(Date fxIniVigencia) {
		this.fxIniVigencia = fxIniVigencia;
	}

	public Date getFxFinVigencia() {
		return fxFinVigencia;
	}

	public void setFxFinVigencia(Date fxFinVigencia) {
		this.fxFinVigencia = fxFinVigencia;
	}

	public String getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}
}
