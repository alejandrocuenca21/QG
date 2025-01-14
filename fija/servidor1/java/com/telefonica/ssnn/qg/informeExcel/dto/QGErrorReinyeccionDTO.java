package com.telefonica.ssnn.qg.informeExcel.dto;

import java.util.ArrayList;
import java.util.List;

import com.telefonica.ssnn.qg.informeExcel.dto.base.QGErrorBaseDTO;
import com.telefonica.ssnn.qg.util.QGInformeConstants;
import com.telefonica.ssnn.qg.util.QGInformeUtils;

public class QGErrorReinyeccionDTO extends QGErrorBaseDTO{
	
	private Integer valorFijo;
	private Integer valorMovil;
	private Integer valorPrepago;	
	private Integer sumaValores;
	
	public QGErrorReinyeccionDTO() {
				
		valorFijo = new Integer(0);
		valorMovil = new Integer(0);
		valorPrepago = new Integer(0);		
	}
	
	

	public QGErrorReinyeccionDTO(String cadError) {
		
		valorFijo = new Integer(0);
		valorMovil = new Integer(0);
		valorPrepago = new Integer(0);		
		
		this.codigo = QGInformeUtils.obtenerCodigoError(cadError);
		this.tipo = QGInformeUtils.obtenerTipoError(cadError);
		this.relacion = QGInformeUtils.obtenerRelacionError(cadError);
		String modalidad = QGInformeUtils.obtenerModalidadError(cadError);
		String numOcurr = QGInformeUtils.obtenerOccurError(cadError);
		
	
		if(QGInformeConstants.MODALITY_FIJO.equals(modalidad)){
			
			this.valorFijo = QGInformeUtils.toInteger(numOcurr);
			
		}else if(QGInformeConstants.MODALITY_MOVIL.equals(modalidad)){
			
			this.valorMovil = QGInformeUtils.toInteger(numOcurr);
			
		}else if(QGInformeConstants.MODALITY_PREPAGO.equals(modalidad)){
			
			this.valorPrepago = QGInformeUtils.toInteger(numOcurr);
			
		}
		
	}
	
	
	/**
	 * No diferenciamos entre fijo, movil y prepago
	 * @return
	 */
	public String getKey() {
		return codigo + "-" + tipo + "-" + relacion;
	}

	public Integer getSumaValores() {
		sumaValores = QGInformeUtils.sumar(this.valorFijo, this.valorMovil);
		sumaValores = QGInformeUtils.sumar(sumaValores, this.valorPrepago);
		return sumaValores;
	}


	public void setSumaValores(Integer sumaValores) {
		this.sumaValores = sumaValores;
	}


	public void setValorFijo(Integer valorFijo) {
		this.valorFijo = valorFijo;
	}


	public void setValorMovil(Integer valorMovil) {
		this.valorMovil = valorMovil;
	}

	public void setValorPrepago(Integer valorPrepago) {
		this.valorPrepago = valorPrepago;
	}

	public Integer getValorFijo() {
		return valorFijo;
	}

	public Integer getValorMovil() {
		return valorMovil;
	}

	public Integer getValorPrepago() {
		return valorPrepago;
	}	

	public String getIdTable(){
		return QGInformeUtils.getIdTableByTypeError(this);
	}
	
	/**
	 * Ofrece el error en formato listado de strings para facilitar el pintado sobre las tablas
	 */
	public List getColumnas(){
		
		List columnas = new ArrayList();
		//Fija
		columnas.add(this.codigo);
		columnas.add(this.valorFijo);
		columnas.add(QGInformeConstants.CELDA_VACIA);
		
		columnas.add(QGInformeConstants.CELDA_VACIA);
		
		//Movil
		columnas.add(this.codigo);
		columnas.add(this.valorMovil);
		columnas.add(QGInformeConstants.CELDA_VACIA);
		
		columnas.add(QGInformeConstants.CELDA_VACIA);
		
		//Prepago
		columnas.add(this.codigo);
		columnas.add(this.valorPrepago);
		columnas.add(QGInformeConstants.CELDA_VACIA);
		
		columnas.add(QGInformeConstants.CELDA_VACIA);		
		
		//Suma
		columnas.add(this.codigo);
		columnas.add(this.getSumaValores());
		columnas.add(QGInformeConstants.CELDA_VACIA);
		return columnas;
	}
}
