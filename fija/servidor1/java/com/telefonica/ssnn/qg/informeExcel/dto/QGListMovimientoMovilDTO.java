package com.telefonica.ssnn.qg.informeExcel.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QGListMovimientoMovilDTO {

	/**
	 * Lista de movimientos según el día
	 */
	private List listaMovimientos;
	
	/**
	 * Movimientos totales DTO, suma de todos los que tiene dentro
	 */
	private QGMovimientoMovilDTO movimientoTotal;
	
	/**
	 * Fechas del informe, se corresponden con el listado
	 */
	private List fechasInforme;
	
	public QGListMovimientoMovilDTO() {
		listaMovimientos = new ArrayList();
		
		movimientoTotal = new QGMovimientoMovilDTO();
		movimientoTotal.setTotal(true);
		
		fechasInforme = new ArrayList();		
	}
	
	
	public QGMovimientoMovilDTO addDay(String fechaDia, List infoDay, Map listErrores, Map listErroresQGCXXX) {
		fechasInforme.add(fechaDia);
		
		//Creamos el movimiento total con toda la informacion
		QGMovimientoMovilDTO diaNuevo = new QGMovimientoMovilDTO();
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

	public List getFechasInforme() {
		return fechasInforme;
	}

	public void setFechasInforme(List fechasInforme) {
		this.fechasInforme = fechasInforme;
	}

	public QGMovimientoMovilDTO getMovimientoTotal() {
		return movimientoTotal;
	}

	public void setMovimientoTotal(QGMovimientoMovilDTO movimientoTotal) {
		this.movimientoTotal = movimientoTotal;
	}




	
	
}
