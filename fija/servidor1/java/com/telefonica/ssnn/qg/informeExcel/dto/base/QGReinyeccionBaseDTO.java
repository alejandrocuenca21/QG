package com.telefonica.ssnn.qg.informeExcel.dto.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.telefonica.ssnn.qg.informeExcel.dto.QGErrorDTO;
import com.telefonica.ssnn.qg.informeExcel.dto.QGErrorReinyeccionDTO;
import com.telefonica.ssnn.qg.util.QGInformeUtils;


public class QGReinyeccionBaseDTO {

	private Integer altaTratados;
	private Integer modTratados;
	private Integer bajaTratados;	

	private Integer altaWarning;
	private Integer modWarning;
	private Integer bajaWarning;	

	private Integer altaPendiente;
	private Integer modPendiente;
	private Integer bajaPendiente;	

	private Integer altaErrores;
	private Integer modErrores;
	private Integer bajaErrores;	
	
	/**
	 * Es una lista que muestra en el orden de las tablas los valores que se van a mostrar
	 */
	private List listaValoresMostrar;
	/**
	 * Lista para los tantos por ciento
	 */
	private List listaValoresPorCiento;
	
	
	public QGReinyeccionBaseDTO() {
		altaTratados = new Integer(0);
		modTratados = new Integer(0);
		bajaTratados = new Integer(0);		

		altaWarning = new Integer(0);
		modWarning = new Integer(0);
		bajaWarning = new Integer(0);		

		altaPendiente = new Integer(0);
		modPendiente = new Integer(0);
		bajaPendiente = new Integer(0);		

		altaErrores = new Integer(0);
		modErrores = new Integer(0);
		bajaErrores = new Integer(0);		
		
		listaValoresMostrar = new ArrayList();
		listaValoresPorCiento = new ArrayList();
	}
	
	public boolean crearReinyeccion(List infoDay, String reinyeccion, List erroresReinyeccion, Map listErroresQGCXXX, int countDay) {
		
		boolean hayReinyeccion = false;
		
		if(reinyeccion.equals("F")){			
			//int i = 47;
			int i = 57;			
			altaTratados = QGInformeUtils.toInteger((String)infoDay.get(i++));
			modTratados = QGInformeUtils.toInteger((String)infoDay.get(i++));
			altaPendiente = QGInformeUtils.toInteger((String)infoDay.get(i++));
			modPendiente = QGInformeUtils.toInteger((String)infoDay.get(i++));
			
		}else if (reinyeccion.equals("M")) {
			
			altaTratados = QGInformeUtils.toInteger((String)infoDay.get(61));
			modTratados = QGInformeUtils.toInteger((String)infoDay.get(62));
			altaPendiente =QGInformeUtils.toInteger((String)infoDay.get(65));
			modPendiente = QGInformeUtils.toInteger((String)infoDay.get(66));
			
		}else if (reinyeccion.equals("P")) {
			
			int i = 69;
			altaTratados = QGInformeUtils.toInteger((String)infoDay.get(i++));
			bajaTratados = QGInformeUtils.toInteger((String)infoDay.get(i++));			
			modTratados = QGInformeUtils.toInteger((String)infoDay.get(i++));			
			altaPendiente =QGInformeUtils.toInteger((String)infoDay.get(i++));
			bajaPendiente = QGInformeUtils.toInteger((String)infoDay.get(i++));
			modPendiente = QGInformeUtils.toInteger((String)infoDay.get(i++));			
			
		}
		
		
		//Si hay reinyeccion buscamos los errores
		if((altaTratados != null && altaTratados.intValue() != 0) ||
		   (modTratados != null && modTratados.intValue() != 0) ||
		   (bajaTratados != null && bajaTratados.intValue() != 0) ||		   
		   (altaPendiente != null && altaPendiente.intValue() != 0)	||
		   (modPendiente != null && modPendiente.intValue() != 0)	||		   
		   (bajaPendiente != null && bajaPendiente.intValue() != 0) ){
			
			hayReinyeccion = true;
		
		
			//Calculamos los errores y los warnings
			
			//Tratamos los errores para ir añadiendolos
			if(erroresReinyeccion != null && erroresReinyeccion.size() > 0){
				Iterator ite = erroresReinyeccion.iterator();
				
				while(ite.hasNext()){
					QGErrorReinyeccionDTO errorReinyeccion = (QGErrorReinyeccionDTO)ite.next();
					
					//Cogemos el valor que venga en el error y dependiendo de si es fija, movil o prepago
					Integer valor = null;
					if(reinyeccion.equals("F")){
						valor = errorReinyeccion.getValorFijo();
					}else if (reinyeccion.equals("M")){
						valor = errorReinyeccion.getValorMovil();
					}else if (reinyeccion.equals("P")){
						valor = errorReinyeccion.getValorPrepago();
					}
					//Errores y Warnings
					if(errorReinyeccion.isTipoError()){
						
						if(reinyeccion.equals("F") && errorReinyeccion.isErrorReinyeccionFijaAlta()){
							this.altaErrores = QGInformeUtils.sumar(this.altaErrores,valor);
						}else if(reinyeccion.equals("M") && errorReinyeccion.isErrorReinyeccionMovilAlta()){
							this.altaErrores = QGInformeUtils.sumar(this.altaErrores, valor);
						}else if(reinyeccion.equals("P") && errorReinyeccion.isErrorReinyeccionPrepagoAlta()){
							this.altaErrores = QGInformeUtils.sumar(this.altaErrores, valor);							
						}else if(reinyeccion.equals("F") && errorReinyeccion.isErrorReinyeccionFijaMod()){
							this.modErrores = QGInformeUtils.sumar(this.modErrores, valor);
						}else if(reinyeccion.equals("M") && errorReinyeccion.isErrorReinyeccionMovilMod()){
							this.modErrores = QGInformeUtils.sumar(this.modErrores, valor);
						}else if(reinyeccion.equals("P") && errorReinyeccion.isErrorReinyeccionPrepagoMod()){
							this.modErrores = QGInformeUtils.sumar(this.modErrores, valor);
						}else if(reinyeccion.equals("P") && errorReinyeccion.isErrorReinyeccionPrepagoBaja()){
							this.bajaErrores = QGInformeUtils.sumar(this.bajaErrores, valor);
						}												
						
					}else if(errorReinyeccion.isTipoWarning()){
						
						if(reinyeccion.equals("F") && errorReinyeccion.isErrorReinyeccionFijaAlta()){
							this.altaWarning = QGInformeUtils.sumar(this.altaWarning, valor);
						}else if(reinyeccion.equals("M") && errorReinyeccion.isErrorReinyeccionMovilAlta()){
							this.altaWarning = QGInformeUtils.sumar(this.altaWarning, valor);
						}else if(reinyeccion.equals("P") && errorReinyeccion.isErrorReinyeccionPrepagoAlta()){
							this.altaWarning = QGInformeUtils.sumar(this.altaWarning, valor);							
						}else if(reinyeccion.equals("F") && errorReinyeccion.isErrorReinyeccionFijaMod()){
							this.modWarning = QGInformeUtils.sumar(this.modWarning, valor);
						}else if(reinyeccion.equals("M") && errorReinyeccion.isErrorReinyeccionMovilMod()){
							this.modWarning = QGInformeUtils.sumar(this.modWarning, valor);
						}else if(reinyeccion.equals("P") && errorReinyeccion.isErrorReinyeccionPrepagoMod()){
							this.modWarning = QGInformeUtils.sumar(this.modWarning, valor);
						}else if(reinyeccion.equals("P") && errorReinyeccion.isErrorReinyeccionPrepagoBaja()){
							this.bajaWarning = QGInformeUtils.sumar(this.bajaWarning, valor);
						}
						
					}
				
				}
				
			}
			//Tratamos los QGCXXX
			
			List erroresQGCXXX = QGInformeUtils.obtenerErroresReinyeccion(listErroresQGCXXX);
			
			if(erroresQGCXXX != null && erroresQGCXXX.size()> 0){
				
				Iterator ite = erroresQGCXXX.iterator();
				
				while(ite.hasNext()){

					QGErrorDTO errorQGCXXX = (QGErrorDTO) ite.next();
					
					if(reinyeccion.equals("F") && errorQGCXXX.isErrorReinyeccionFijaAlta()){
						this.altaTratados = QGInformeUtils.sumar(this.altaTratados, (Integer)errorQGCXXX.getValoresPorFecha().get(0));
					}else if(reinyeccion.equals("M") && errorQGCXXX.isErrorReinyeccionMovilAlta()){
						this.altaTratados = QGInformeUtils.sumar(this.altaTratados, (Integer)errorQGCXXX.getValoresPorFecha().get(0));
					}else if(reinyeccion.equals("P") && errorQGCXXX.isErrorReinyeccionPrepagoAlta()){
						this.altaTratados = QGInformeUtils.sumar(this.altaTratados, (Integer)errorQGCXXX.getValoresPorFecha().get(0));						
					}else if(reinyeccion.equals("F") && errorQGCXXX.isErrorReinyeccionFijaMod()){
						this.modTratados = QGInformeUtils.sumar(this.modTratados, (Integer)errorQGCXXX.getValoresPorFecha().get(0));
					}else if(reinyeccion.equals("M") && errorQGCXXX.isErrorReinyeccionMovilMod()){
						this.modTratados = QGInformeUtils.sumar(this.modTratados, (Integer)errorQGCXXX.getValoresPorFecha().get(0));
					}else if(reinyeccion.equals("P") && errorQGCXXX.isErrorReinyeccionPrepagoMod()){
						this.modTratados = QGInformeUtils.sumar(this.modTratados, (Integer)errorQGCXXX.getValoresPorFecha().get(0));
					}else if(reinyeccion.equals("P") && errorQGCXXX.isErrorReinyeccionPrepagoBaja()){
						this.bajaTratados = QGInformeUtils.sumar(this.bajaTratados, (Integer)errorQGCXXX.getValoresPorFecha().get(0));
					}
				}
			}
		}
		
		return hayReinyeccion;
	}

	public Integer getAltaTratados() {
		return altaTratados;
	}

	public void setAltaTratados(Integer altaTratados) {
		this.altaTratados = altaTratados;
	}

	public Integer getModTratados() {
		return modTratados;
	}

	public void setModTratados(Integer modTratados) {
		this.modTratados = modTratados;
	}
	
	public Integer getBajaTratados() {
		return bajaTratados;
	}

	public void setBajaTratados(Integer bajaTratados) {
		this.bajaTratados = bajaTratados;
	}	

	public Integer getAltaWarning() {
		return altaWarning;
	}

	public void setAltaWarning(Integer altaWarning) {
		this.altaWarning = altaWarning;
	}

	public Integer getModWarning() {
		return modWarning;
	}

	public void setModWarning(Integer modWarning) {
		this.modWarning = modWarning;
	}
	
	public Integer getBajaWarning() {
		return bajaWarning;
	}

	public void setBajaWarning(Integer bajaWarning) {
		this.bajaWarning = bajaWarning;
	}	

	public Integer getAltaPendiente() {
		return altaPendiente;
	}

	public void setAltaPendiente(Integer altaPendiente) {
		this.altaPendiente = altaPendiente;
	}

	public Integer getModPendiente() {
		return modPendiente;
	}

	public void setModPendiente(Integer modPendiente) {
		this.modPendiente = modPendiente;
	}
	
	public Integer getBajaPendiente() {
		return bajaPendiente;
	}

	public void setBajaPendiente(Integer bajaPendiente) {
		this.bajaPendiente = bajaPendiente;
	}	

	public Integer getAltaErrores() {
		return altaErrores;
	}

	public void setAltaErrores(Integer altaErrores) {
		this.altaErrores = altaErrores;
	}

	public Integer getModErrores() {
		return modErrores;
	}

	public void setModErrores(Integer modErrores) {
		this.modErrores = modErrores;
	}
	
	public Integer getBajaErrores() {
		return bajaErrores;
	}

	public void setBajaErrores(Integer bajaErrores) {
		this.bajaErrores = bajaErrores;
	}	
	
	public Integer getTotalTratados(){
		return QGInformeUtils.sumar((QGInformeUtils.sumar(this.altaTratados, this.modTratados)),this.bajaTratados);
	}
	
	public Integer getTotalPendientes(){
		return QGInformeUtils.sumar((QGInformeUtils.sumar(this.altaPendiente, this.modPendiente)),this.bajaPendiente);
	}
	
	public Integer getTotalErrores(){
		return QGInformeUtils.sumar((QGInformeUtils.sumar(this.altaErrores, this.modErrores)),this.bajaErrores);
	}
	
	public Integer getTotalWarnings(){
		return  QGInformeUtils.sumar((QGInformeUtils.sumar(this.altaWarning, this.modWarning)),this.bajaWarning);
	}
	
	public Integer getTotal(){
		List listaValores = new ArrayList();
		listaValores.add(getTotalTratados());
		listaValores.add(getTotalPendientes());
		listaValores.add(getTotalErrores());
		
		return QGInformeUtils.sumar(listaValores);
	}

	public List getListaValoresMostrar() {
		
		Integer totalTratados = getTotalTratados();
		Integer totalPendientes = getTotalPendientes();
		Integer totalErrores = getTotalErrores();
		Integer totalWarnings = getTotalWarnings();
				
		Integer totales = getTotal();
		
		listaValoresMostrar.add(totales);
		listaValoresMostrar.add(totalTratados);
		listaValoresMostrar.add(totalPendientes);
		listaValoresMostrar.add(totalErrores);
		
		listaValoresMostrar.add(totalTratados);
		listaValoresMostrar.add(this.altaTratados);
		listaValoresMostrar.add(this.modTratados);
		listaValoresMostrar.add(this.bajaTratados);		
		
		listaValoresMostrar.add(totalPendientes);
		listaValoresMostrar.add(this.altaPendiente);
		listaValoresMostrar.add(this.modPendiente);
		listaValoresMostrar.add(this.bajaPendiente);		
		
		listaValoresMostrar.add(totalErrores);
		listaValoresMostrar.add(this.altaErrores);
		listaValoresMostrar.add(this.modErrores);
		listaValoresMostrar.add(this.bajaErrores);		
		
		listaValoresMostrar.add(totalWarnings);
		listaValoresMostrar.add(this.altaWarning);
		listaValoresMostrar.add(this.modWarning);
		listaValoresMostrar.add(this.bajaWarning);		
		
		return listaValoresMostrar;
	}

	public void setListaValoresMostrar(List listaValoresMostrar) {
		this.listaValoresMostrar = listaValoresMostrar;
	}

	public List getListaValoresPorCiento() {
		
		Integer totalTratados = QGInformeUtils.sumar(this.altaTratados, this.modTratados);
		totalTratados = QGInformeUtils.sumar(totalTratados, this.bajaTratados);
		Integer totalPendientes = QGInformeUtils.sumar(this.altaPendiente, this.modPendiente);
		totalPendientes = QGInformeUtils.sumar(totalPendientes, this.bajaPendiente);
		Integer totalErrores = QGInformeUtils.sumar(this.altaErrores, this.modErrores);
		totalErrores = QGInformeUtils.sumar(totalErrores, this.bajaErrores);
		Integer totalWarnings = QGInformeUtils.sumar(this.altaWarning, this.modWarning);
		totalWarnings = QGInformeUtils.sumar(totalWarnings, this.bajaWarning);
		
		List listaValores = new ArrayList();
		listaValores.add(totalTratados);
		listaValores.add(totalPendientes);
		listaValores.add(totalErrores);
		
		Integer totales = QGInformeUtils.sumar(listaValores);
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(totales,totales));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(totalTratados,totales));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(totalPendientes,totales));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(totalErrores,totales));
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(totalTratados,totalTratados));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.altaTratados,totalTratados));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.modTratados,totalTratados));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.bajaTratados,totalTratados));		
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(totalPendientes,totalPendientes));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.altaPendiente,totalPendientes));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.modPendiente,totalPendientes));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.bajaPendiente,totalPendientes));		
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(totalErrores,totalErrores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.altaErrores,totalErrores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.modErrores,totalErrores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.bajaErrores,totalErrores));		
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(totalWarnings,totalWarnings));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.altaWarning,totalWarnings));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.modWarning,totalWarnings));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.bajaWarning,totalWarnings));		
		
		
		return listaValoresPorCiento;
	}

	public void setListaValoresPorCiento(List listaValoresPorCiento) {
		this.listaValoresPorCiento = listaValoresPorCiento;
	}
}
