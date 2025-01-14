/**
 * 
 */
package com.telefonica.ssnn.qg.informes.dto;

import java.util.List;

/**
 * @author vsierra
 *
 */
public class QGInformeDto {

	private QGEstadisticasDto estadisticasDto;
	
	private List listaClientes;

	public QGEstadisticasDto getEstadisticasDto() {
		return estadisticasDto;
	}

	public void setEstadisticasDto(QGEstadisticasDto estadisticasDto) {
		this.estadisticasDto = estadisticasDto;
	}

	public List getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List listaClientes) {
		this.listaClientes = listaClientes;
	}
}
