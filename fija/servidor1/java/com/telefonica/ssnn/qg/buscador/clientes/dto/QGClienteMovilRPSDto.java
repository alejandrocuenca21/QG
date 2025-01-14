/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.clientes.dto;

import java.util.Date;


/**
 * @author pmpalomo
 *
 */
public class QGClienteMovilRPSDto {
			
	private String dsTipoDocumento;      
	private String coTipoDocumento;        
	private String nuDocumento;               
	private String dsPais;      
	private String coPais;    				
	private String dsNombre;				
	private String dsApellido1;      				
	private String dsApellido2;				
	private String coPostal;         
	private Date fxIniVigencia;        
	private Date fxFinVigencia;   
	private String coUsuarioAlta;
	private Date fxAltaRegistro;       
	private String coUsuarioModificacion;
	private Date fxUltModificacion;    
	private String nombrePrograma;			
	
	public String getDsTipoDocumento() {
		return dsTipoDocumento;
	}

	public void setDsTipoDocumento(String dsTipoDocumento) {
		this.dsTipoDocumento = dsTipoDocumento;
	}	

	public String getCoTipoDocumento() {
		return coTipoDocumento;
	}

	public void setCoTipoDocumento(String coTipoDocumento) {
		this.coTipoDocumento = coTipoDocumento;
	}
	
	public String getNuDocumento() {
		return nuDocumento;
	}

	public void setNuDocumento(String nuDocumento) {
		this.nuDocumento = nuDocumento;
	}
	
	public String getDsPais() {
		return dsPais;
	}

	public void setDsPais(String dsPais) {
		this.dsPais = dsPais;
	}
	
	public String getCoPais() {
		return coPais;
	}

	public void setCoPais(String coPais) {
		this.coPais = coPais;
	}	

	public String getDsNombre() {
		return dsNombre;
	}

	public void setDsNombre(String dsNombre) {
		this.dsNombre = dsNombre;
	}

	public String getDsApellido1() {
		return dsApellido1;
	}

	public void setDsApellido1(String dsApellido1) {
		this.dsApellido1 = dsApellido1;
	}

	public String getDsApellido2() {
		return dsApellido2;
	}

	public void setDsApellido2(String dsApellido2) {
		this.dsApellido2 = dsApellido2;
	}
	
	public String getCoPostal() {
		return coPostal;
	}

	public void setCoPostal(String coPostal) {
		this.coPostal = coPostal;
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
	
	public String getCoUsuarioAlta() {
		return coUsuarioAlta;
	}

	public void setCoUsuarioAlta(String coUsuarioAlta) {
		this.coUsuarioAlta = coUsuarioAlta;
	}	
	
	public Date getFxAltaRegistro() {
		return fxAltaRegistro;
	}

	public void setFxAltaRegistro(Date fxAltaRegistro) {
		this.fxAltaRegistro = fxAltaRegistro;
	}	
	
	public String getCoUsuarioModificacion() {
		return coUsuarioModificacion;
	}

	public void setCoUsuarioModificacion(String coUsuarioModificacion) {
		this.coUsuarioModificacion = coUsuarioModificacion;
	}	
	
	public Date getFxUltModificacion() {
		return fxUltModificacion;
	}

	public void setFxUltModificacion(Date fxUltModificacion) {
		this.fxUltModificacion = fxUltModificacion;
	}

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}
}
