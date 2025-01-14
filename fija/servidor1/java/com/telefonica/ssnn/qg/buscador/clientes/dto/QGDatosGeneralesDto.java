/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.clientes.dto;

import java.util.ArrayList;

/**
 * @author vsierra
 *
 */
public class QGDatosGeneralesDto {

	private ArrayList datosClienteDto;
	
	private ArrayList clienteMovilCtoDto;
	
	private ArrayList clienteMovilPreDto;
	
	private ArrayList clienteMovilRPSDto;	
	
	private ArrayList clienteFijoDto;
	
	private String coClienteMovil;
	
	private String coClienteFijo;

	public ArrayList getDatosClienteDto() {
		return datosClienteDto;
	}

	public void setDatosClienteDto(ArrayList datosClienteDto) {
		this.datosClienteDto = datosClienteDto;
	}

	public ArrayList getClienteMovilCtoDto() {
		return clienteMovilCtoDto;
	}

	public void setClienteMovilCtoDto(ArrayList clienteMovilCtoDto) {
		this.clienteMovilCtoDto = clienteMovilCtoDto;
	}
	
	public ArrayList getClienteMovilPreDto() {
		return clienteMovilPreDto;
	}

	public void setClienteMovilPreDto(ArrayList clienteMovilPreDto) {
		this.clienteMovilPreDto = clienteMovilPreDto;
	}
	
	public ArrayList getClienteMovilRPSDto() {
		return clienteMovilRPSDto;
	}

	public void setClienteMovilRPSDto(ArrayList clienteMovilRPSDto) {
		this.clienteMovilRPSDto = clienteMovilRPSDto;
	}	

	public ArrayList getClienteFijoDto() {
		return clienteFijoDto;
	}

	public void setClienteFijoDto(ArrayList clienteFijoDto) {
		this.clienteFijoDto = clienteFijoDto;
	}

	public String getCoClienteMovil() {
		return coClienteMovil;
	}

	public void setCoClienteMovil(String coClienteMovil) {
		this.coClienteMovil = coClienteMovil;
	}

	public String getCoClienteFijo() {
		return coClienteFijo;
	}

	public void setCoClienteFijo(String coClienteFijo) {
		this.coClienteFijo = coClienteFijo;
	}
}
