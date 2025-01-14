package com.telefonica.ssnn.qg.base.dto;

import java.util.List;

public class QGCGlobalPagingDto {

	  private List listaDatos;	  
	  
	  private List listaMensajes;
	  
	  private String indPgnIn;
		
	  private String pgnTx;

	public List getListaDatos() {
		return listaDatos;
	}

	public void setListaDatos(List listaDatos) {
		this.listaDatos = listaDatos;
	}

	public List getListaMensajes() {
		return listaMensajes;
	}

	public void setListaMensajes(List listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

	public String getIndPgnIn() {
		return indPgnIn;
	}

	public void setIndPgnIn(String indPgnIn) {
		this.indPgnIn = indPgnIn;
	}

	public String getPgnTx() {
		return pgnTx;
	}

	public void setPgnTx(String pgnTx) {
		this.pgnTx = pgnTx;
	}
	  
	  
	
}
