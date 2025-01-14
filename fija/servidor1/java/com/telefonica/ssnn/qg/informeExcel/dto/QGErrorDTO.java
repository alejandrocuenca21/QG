package com.telefonica.ssnn.qg.informeExcel.dto;

import java.util.ArrayList;
import java.util.List;

import com.telefonica.ssnn.qg.informeExcel.dto.base.QGErrorBaseDTO;
import com.telefonica.ssnn.qg.util.QGInformeConstants;
import com.telefonica.ssnn.qg.util.QGInformeUtils;

public class QGErrorDTO extends QGErrorBaseDTO{

	

	/**
	 * Indica todos los valores que va a tener ese error por cada fecha
	 * si un error no tiene valor para una fecha se le mete la constante VALOR_VACIO
	 * para mantener siempre la misma estructura
	 */
	private List valoresPorFecha;
	
	public QGErrorDTO() {
		valoresPorFecha = new ArrayList();
		especial = false;
	}
	
	/**
	 * Forma el error dada la cadena obtenida del servicio. El contador indica que dia estamos tratando (1 a 7)
	 * para rellenar de 0 los anteriores
	 * @param cadenaError
	 */
	public QGErrorDTO(String cadenaError, int cont){
		
		valoresPorFecha = new ArrayList();
		//La lista de valores ya tendra 7 0's
		QGInformeUtils.rellenarListaValoresError(valoresPorFecha);
		
		//Se trata la cadena para formar el objeto correctamente, el valor (numero de ocurrencias se inserta al final segun el cont)

		this.codigo = QGInformeUtils.obtenerCodigoError(cadenaError);
		this.modalidad = QGInformeUtils.obtenerModalidadError(cadenaError);
		this.tipo = QGInformeUtils.obtenerTipoError(cadenaError);
		this.relacion = QGInformeUtils.obtenerRelacionError(cadenaError);
		
		String numOcurr = QGInformeUtils.obtenerOccurError(cadenaError);
		
		
		//Si es especial no se trata como el resto de valores
		if(QGInformeConstants.QGCXXX.equals(this.codigo)){
			
			this.especial = true;
			valoresPorFecha.clear();
			valoresPorFecha.add(QGInformeUtils.toInteger(numOcurr));
			
		}else{
			this.especial = false;
				
			this.insertarValor(numOcurr,cont);
		}
	}

	/**
	 * Para un error que ya fue creado (la key existe en el mapa de errores) tan solo añade el valor
	 * a la lista de valores por fecha.
	 */
	public void insertarValor(String valor, int countDay) {
		//Se inserta y sustituye por el valor que diga count day
		this.valoresPorFecha.set(countDay-1, QGInformeUtils.toInteger(valor));
		
		this.acumulado = QGInformeUtils.sumar(valoresPorFecha);
	}
	
	public String getIdTable(){
		return QGInformeUtils.getIdTableByTypeError(this);
	}
	
	/**
	 * Forma la KEY que tendrá el mapa que lo contiene, para que sea único
	 * @return
	 */
	public String getKey(){
		return codigo + "-" + modalidad + "-" + tipo + "-" + relacion;
	}
	
		
	public List getValoresPorFecha() {
		return valoresPorFecha;
	}

	public void setValoresPorFecha(List valoresPorFecha) {
		this.valoresPorFecha = valoresPorFecha;
	}

	public boolean isQGXXX(){
		if(this.codigo != null && QGInformeConstants.QGCXXX.equals(this.codigo)){
			return true;
		}
		return false;
	}
	
	/**
	 * Ofrece el error en formato listado de strings para facilitar el pintado sobre las tablas
	 */
	public List getColumnas(){
		
		//Los errores QGCXXX no se pintan y ademas solo tienen un valor
		
		List columnas = new ArrayList();
		List valores = new ArrayList();
		
		if(!this.isQGXXX()){
		
			columnas.add(this.codigo);
			columnas.add(this.getValoresPorFecha().get(0));
			valores.add(this.getValoresPorFecha().get(0));
			columnas.add(QGInformeConstants.CELDA_VACIA);
			columnas.add(this.getValoresPorFecha().get(1));
			valores.add(this.getValoresPorFecha().get(1));
			columnas.add(QGInformeConstants.CELDA_VACIA);
			columnas.add(this.getValoresPorFecha().get(2));
			valores.add(this.getValoresPorFecha().get(2));
			columnas.add(QGInformeConstants.CELDA_VACIA);
			columnas.add(this.getValoresPorFecha().get(3));
			valores.add(this.getValoresPorFecha().get(3));
			columnas.add(QGInformeConstants.CELDA_VACIA);
			columnas.add(this.getValoresPorFecha().get(4));
			valores.add(this.getValoresPorFecha().get(4));
			columnas.add(QGInformeConstants.CELDA_VACIA);
			columnas.add(this.getValoresPorFecha().get(5));
			valores.add(this.getValoresPorFecha().get(5));
			columnas.add(QGInformeConstants.CELDA_VACIA);
			columnas.add(this.getValoresPorFecha().get(6));
			valores.add(this.getValoresPorFecha().get(6));
			columnas.add(QGInformeConstants.CELDA_VACIA);
			
			//Suma, las cadenas vacias cuentan como 0
			columnas.add(QGInformeUtils.sumar(valores));
			columnas.add(QGInformeConstants.CELDA_VACIA);
		
		}
		return columnas;
		
	}
	
	
	
	
	
	
	
}
