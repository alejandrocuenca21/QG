package com.telefonica.ssnn.qg.informeExcel.dto;

import java.util.List;

public class QGListadoDTO {

	//Columnas de las tablas de la pestaña Informe
	private List listaDatos;
	
	private List listaPorcentajes;

	public List getListaDatos() {
		return listaDatos;
	}

	public void setListaDatos(List listaDatos) {
		this.listaDatos = listaDatos;
	}

	public List getListaPorcentajes() {
		return listaPorcentajes;
	}

	public void setListaPorcentajes(List listaPorcentajes) {
		this.listaPorcentajes = listaPorcentajes;
	}
	
	
}
