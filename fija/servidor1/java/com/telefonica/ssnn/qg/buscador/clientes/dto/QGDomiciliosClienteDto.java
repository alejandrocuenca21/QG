/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.clientes.dto;

import java.util.ArrayList;

/**
 * @author vsierra
 *
 */
public class QGDomiciliosClienteDto {

	private ArrayList numeroTelfFijo;
	
	private ArrayList numeroTelfMovil;
	
	private ArrayList apartadoCorreos;
	
	private ArrayList correoElectronico;
	
	private ArrayList descripcionWeb;
	
	private ArrayList direccioneClienteLogicas;
	
	private ArrayList direccionesClienteDto;
	
	private ArrayList direccionesClienteLNMDto;

	public ArrayList getNumeroTelfFijo() {
		return numeroTelfFijo;
	}

	public void setNumeroTelfFijo(ArrayList numeroTelfFijo) {
		this.numeroTelfFijo = numeroTelfFijo;
	}

	public ArrayList getNumeroTelfMovil() {
		return numeroTelfMovil;
	}

	public void setNumeroTelfMovil(ArrayList numeroTelfMovil) {
		this.numeroTelfMovil = numeroTelfMovil;
	}

	public ArrayList getApartadoCorreos() {
		return apartadoCorreos;
	}

	public void setApartadoCorreos(ArrayList apartadoCorreos) {
		this.apartadoCorreos = apartadoCorreos;
	}

	public ArrayList getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(ArrayList correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public ArrayList getDescripcionWeb() {
		return descripcionWeb;
	}

	public void setDescripcionWeb(ArrayList descripcionWeb) {
		this.descripcionWeb = descripcionWeb;
	}

	public ArrayList getDireccionesClienteDto() {
		return direccionesClienteDto;
	}

	public void setDireccionesClienteDto(ArrayList direccionesClienteDto) {
		this.direccionesClienteDto = direccionesClienteDto;
	}

	public ArrayList getDireccionesClienteLNMDto() {
		return this.direccionesClienteLNMDto;
	}

	public void setDireccionesClienteLNMDto(ArrayList direccionesClienteLNMDto) {
		this.direccionesClienteLNMDto = direccionesClienteLNMDto;
	}

	public ArrayList getDireccioneClienteLogicas() {
		return this.direccioneClienteLogicas;
	}

	public void setDireccioneClienteLogicas(ArrayList direccioneClienteLogicas) {
		this.direccioneClienteLogicas = direccioneClienteLogicas;
	}
	
}
