/**
 * 
 */
package com.telefonica.ssnn.qg.base.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ACF
 *
 */
public class TWGlobalDto {
		
	private static final long serialVersionUID = 884005053278943686L;

	/**
	 * listaDatos devueltos por el host.
	 */
	private List listaDatos;
	
	/**
	 * listaMensajes informativos devueltos por el host.
	 */
	private List listaMensajes;
	
	/**
	 * Constructor por defecto.
	 */
	public TWGlobalDto() {
		listaDatos = new ArrayList();
		listaMensajes = new ArrayList();
	}
	
	/**
	 * Constructor con listaDatos y listaMensajes.
	 * @param listaDatos - listaDatos.
	 * @param listaMensajes - listaMensajes.
	 */
	public TWGlobalDto(List listaDatos, List listaMensajes) {
		this.listaDatos = listaDatos;
		this.listaMensajes = listaMensajes;
	}
	

	public List getlistaDatos() {
		return listaDatos;
	}

	public void setlistaDatos(List listaDatos) {
		this.listaDatos = listaDatos;
	}

	public List getlistaMensajes() {
		return listaMensajes;
	}

	public void setlistaMensajes(List listaMensajes) {
		this.listaMensajes = listaMensajes;
	}		
}
