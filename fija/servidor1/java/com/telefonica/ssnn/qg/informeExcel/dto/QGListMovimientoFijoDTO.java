package com.telefonica.ssnn.qg.informeExcel.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QGListMovimientoFijoDTO {

	/**
	 * Lista de movimientos según el día
	 */
	private List listaMovimientos;
	/**
	 * Movimientos totales DTO
	 */
	private QGMovimientoFijoDTO movimientoTotal;
	
	/**
	 * Fechas del informe, se corresponden con el listado
	 */
	private List fechasInforme;
	
	
	public QGListMovimientoFijoDTO() {
		listaMovimientos = new ArrayList();
		
		movimientoTotal = new QGMovimientoFijoDTO();
		movimientoTotal.setTotal(true);
		
		fechasInforme = new ArrayList();		
	}
	
	/**
	 * Añade un dia de informe a la lista de movimientos fijos (columnas del informe), lo devuelve para obtener informacion
	 * @param fechaDia
	 * @param infoDay
	 * @param listErrores 
	 * @param listErroresQGCXXX 
	 */
	public QGMovimientoFijoDTO addDay(String fechaDia, List infoDay, Map listErrores, Map listErroresQGCXXX) {
		fechasInforme.add(fechaDia);
		
		//Creamos el movimiento total con toda la informacion
		QGMovimientoFijoDTO diaNuevo = new QGMovimientoFijoDTO();
		//el tamaño de la lista indicara el dia que estamos tratando
		diaNuevo.crearDia(infoDay,listErrores,listErroresQGCXXX,listaMovimientos.size());
		
		listaMovimientos.add(diaNuevo);
		
		//Sumamos la informacion que tuvieramos
		movimientoTotal.sumarDiaMovimientoTotal(diaNuevo);
		
		//Devolvemos el dia para obtener informacion en totales
		return diaNuevo;
		
	}

	
	

	public List getListaMovimientos() {
		return listaMovimientos;
	}

	public void setListaMovimientos(List listaMovimientos) {
		this.listaMovimientos = listaMovimientos;
	}

	public QGMovimientoFijoDTO getMovimientoTotal() {
		return movimientoTotal;
	}

	public void setMovimientoTotal(QGMovimientoFijoDTO movimientoTotal) {
		this.movimientoTotal = movimientoTotal;
	}

	public List getFechasInforme() {
		return fechasInforme;
	}

	public void setFechasInforme(List fechasInforme) {
		this.fechasInforme = fechasInforme;
	}

	
	
	
}
