package com.telefonica.ssnn.qg.informeExcel.dto;

import java.util.ArrayList;
import java.util.List;

public class QGListMovimientoTotalDTO {
	
	/**
	 * Lista de movimientos según el día
	 */
	private List listaMovimientos;
	
	/**
	 * Movimientos totales DTO
	 */
	private QGMovimientoTotalDTO movimientoTotal;
	
	/**
	 * Fechas del informe, se corresponden con el listado
	 */
	private List fechasInforme;
	
	public QGListMovimientoTotalDTO() {
		movimientoTotal = new QGMovimientoTotalDTO();
		movimientoTotal.setTotal(true);
		
		fechasInforme = new ArrayList();
		listaMovimientos = new ArrayList();
	}
	
	/**
	 * Añade un dia de informe a la lista de movimientos totales (columnas del informe)
	 * @param fechaDia
	 * @param infoDay
	 * @param diaMovil 
	 * @param diaFijo
	 * @param diaPrepago
	 */
	public void addDay(String fechaDia, List infoDay, QGMovimientoFijoDTO diaFijo, QGMovimientoMovilDTO diaMovil, QGMovimientoPrepagoDTO diaPrepago) {
		fechasInforme.add(fechaDia);
		
		//Creamos el movimiento total con toda la informacion
		QGMovimientoTotalDTO diaNuevo = new QGMovimientoTotalDTO();
		diaNuevo.crearDia(infoDay,diaFijo,diaMovil,diaPrepago);
		
		listaMovimientos.add(diaNuevo);
		
		//Sumamos la informacion que tuvieramos
		movimientoTotal.sumarDiaMovimientoTotal(diaNuevo);
		
	}	


	public void setListaMovimientos(List listaMovimientos) {
		this.listaMovimientos = listaMovimientos;
	}

	public List getListaMovimientos() {
		return listaMovimientos;
	}

	public QGMovimientoTotalDTO getMovimientoTotal() {
		return movimientoTotal;
	}

	public void setMovimientoTotal(QGMovimientoTotalDTO movimientoTotal) {
		this.movimientoTotal = movimientoTotal;
	}

	public void setFechasInforme(List fechasInforme) {
		this.fechasInforme = fechasInforme;
	}

	public List getFechasInforme() {
		return fechasInforme;
	}

	
}
