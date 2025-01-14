package com.telefonica.ssnn.qg.informeExcel.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.telefonica.ssnn.qg.util.QGInformeConstants;
import com.telefonica.ssnn.qg.util.QGInformeUtils;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;

public class QGInformeConvivenciaDTO {
	
	private static final Logger logger = Logger.getLogger(QGInformeConvivenciaDTO.class);	
	
	private QGListMovimientoTotalDTO listTotales;
	
	private QGListMovimientoFijoDTO listFijos;
	
	private QGListMovimientoMovilDTO listMovil;
	
	private QGListMovimientoPrepagoDTO listPrepago;	
	
	private QGReinyeccionDTO reinyeccion ;
	/**
	 * Fecha en la que se ha solicitado el informe (necesaria para la reinyeccion)
	 */
	private String fechaSolicitud;
	/**
	 * Indica si hay reinyeccion
	 */
	private boolean hayReinyeccion;
	/**
	 * Fecha del dia de la reinyeccion
	 */
	private String fechaReinyeccion;
	/**
	 * Dia que ha habido reinyeccion en base 0 (0 a 6)
	 */
	private int diaReinyeccion;
	
	/**
	 * Los errores se almacenan en un mapa para poder saber a ciencia cierta si
	 * ya existe dicho error para tan solo actualizarle el ultimo valor
	 */
	private Map listErrores;
	
	/**
	 * Los errores de reinyeccion tambien, pero aparte ya que son un poco diferentes
	 * ademas solo se trataran los del día donde haya reinyeccion siendo este el ultimo dia
	 * por si hay varios.
	 */
	private Map listErroresReinyeccion;
	
	/**
	 * Clase que dado un listado de errores nos permitira realizar todos los filtrados necesarios
	 * para los listados de errores para todos los tipos, para asi evitar tener que hacer las diferenciaciones
	 * en javascript
	 */
	private QGDecoratorErrorDTO decoratorError;
	
	/**
	 * Fechas del informe, se corresponden con el listado
	 */
	private List fechasInforme;
	/**
	 * Numero de dias con datos
	 */
	private Integer numDias;
		
	
	public QGInformeConvivenciaDTO() {
		
		logger.info("******---QGInformeConvivenciaDTO de QGInformeConvivenciaDTO---******");		
		
		listTotales = new QGListMovimientoTotalDTO();
		listFijos = new QGListMovimientoFijoDTO();
		listMovil = new QGListMovimientoMovilDTO();
		listPrepago = new QGListMovimientoPrepagoDTO();		
		listErrores = new HashMap();
		
		reinyeccion = new QGReinyeccionDTO();
		fechasInforme = new ArrayList();
		
		decoratorError = new QGDecoratorErrorDTO();
		
		numDias = new Integer(0);
		diaReinyeccion = 0;
		
		logger.info("******---FINAL QGInformeConvivenciaDTO de QGInformeConvivenciaDTO---******");		
	}
	
	public QGInformeConvivenciaDTO(String fecha) {
		
		logger.info("******---QGInformeConvivenciaDTO pasando fecha de QGInformeConvivenciaDTO---******");		
		
		listTotales = new QGListMovimientoTotalDTO();
		listFijos = new QGListMovimientoFijoDTO();
		listMovil = new QGListMovimientoMovilDTO();
		listPrepago = new QGListMovimientoPrepagoDTO();		
		listErrores = new HashMap();
		
		fechaSolicitud = fecha;
		
		reinyeccion = new QGReinyeccionDTO();
		fechasInforme = new ArrayList();
		
		decoratorError = new QGDecoratorErrorDTO();
		
		numDias = new Integer(0);
		diaReinyeccion = 0;
		
		logger.info("******---FINAL QGInformeConvivenciaDTO pasando fecha de QGInformeConvivenciaDTO---******");		
	}

	/**
	 * Prepara la decoracion de los errores para mostrarlos.
	 */
	public void prepararDecoratorErrores(){

		logger.info("******---prepararDecoratorErrores de QGInformeConvivenciaDTO---******");
		
		this.decoratorError.setMapErrors(listErrores);
		this.decoratorError.setMapReinyeccionErrors(listErroresReinyeccion);
		
		logger.info("******---FINAL prepararDecoratorErrores de QGInformeConvivenciaDTO---******");		
	}
	
	//Crea los demas dias de manera virtual
	public void completarDias() {
		logger.info("******---completarDias de QGInformeConvivenciaDTO---******");		
		
		String fechaVirtual = "Dia No Generado";
		for(int i = this.numDias.intValue(); i < 7 ; i++){
			
			fechasInforme.add(fechaVirtual);
			
			QGMovimientoFijoDTO diaFijo = listFijos.addDay(fechaVirtual,new ArrayList(),null,null);
			QGMovimientoMovilDTO diaMovil = listMovil.addDay(fechaVirtual,new ArrayList(),null,null);
			QGMovimientoPrepagoDTO diaPrepago = listPrepago.addDay(fechaVirtual,new ArrayList(),null,null);			
			
			listTotales.addDay(fechaVirtual,new ArrayList(),diaFijo,diaMovil,diaPrepago);
		
		}
		logger.info("******---FINAL completarDias de QGInformeConvivenciaDTO---******");		
	}
	
	public List getFechasInformeFormateadas() {
		
		logger.info("******---getFechasInformeFormateadas de QGInformeConvivenciaDTO---******");
		
		List fechas = new ArrayList();
		if(fechasInforme != null && fechasInforme.size() > 0){
			
			//Las fechas de los dias insertados si se formatean
			for(int i = 0 ; i < this.numDias.intValue() ; i ++){
				fechas.add(QGUtilidadesFechasUtils.formatearFechaDesdeCopy((String)fechasInforme.get(i), QGUtilidadesFechasUtils.FORMATO_FECHA_5));
			}
			
			//las demas se introducen tal y como se metieron
			for(int i = this.numDias.intValue(); i < 7; i ++){
				fechas.add((String)fechasInforme.get(i));
			}
		}
		logger.info("******---FINAL getFechasInformeFormateadas de QGInformeConvivenciaDTO---******");		
		return fechas;
	}

	public String getIntervaloInforme(){
		
		logger.info("******---getIntervaloInforme de QGInformeConvivenciaDTO---******");		
		
		String intervalo = "";
		if(fechasInforme != null && fechasInforme.size() > 0){
			//Las fechas de los dias insertados si se formatean
			for(int i = 0 ; i < this.numDias.intValue() ; i ++){
				if(i == 0){
					intervalo = "Total  \n" + "( " + QGUtilidadesFechasUtils.formatearFechaDesdeCopy((String)fechasInforme.get(i),QGUtilidadesFechasUtils.FORMATO_FECHA_5) + " al ";
				}
				if(i == this.numDias.intValue()-1){
					intervalo = intervalo + QGUtilidadesFechasUtils.formatearFechaDesdeCopy((String)fechasInforme.get(i),QGUtilidadesFechasUtils.FORMATO_FECHA_5) + ")";
				}
			}
		}
		
		logger.info("******---FINAL getIntervaloInforme de QGInformeConvivenciaDTO---******");		
		
		return intervalo;
	}
	
	/**
	 * Añade un nuevo dia con toda la estructura
	 * 
	 * @param fechaDia
	 * @param infoDay
	 * @param infoErrores
	 * @param countDay - dia en el que nos encontramos de la lista
	 */
	public void addDay(String fechaDia, List infoDay, List infoErrors, int countDay){
		
		logger.info("******---addDay de QGInformeConvivenciaDTO---******");		
		
		//Añade el nuevo dia
		fechasInforme.add(fechaDia);
		
		//Para cada dia tendremos un listado de QGCXXX errores
		Map listErroresQGCXXX = new HashMap();
		Map listErroresReinyeccionAux = new HashMap();
		
		//Creamos los errores y los vamos metiendo en el mapa
		if(infoErrors != null && infoErrors.size() > 0){
			Iterator ite = infoErrors.iterator();
			while (ite.hasNext()) {
				String cadError = (String) ite.next();
				
				String keyError = QGInformeUtils.obtenerClaveError(cadError);
				String numOcurr = QGInformeUtils.obtenerOccurError(cadError);
				
				
				//Vemos si el error es QGCXXX ya que no se inserta en el mapa
				if(keyError != null && keyError.indexOf(QGInformeConstants.QGCXXX) > -1){
					
					QGErrorDTO errorNuevo = new QGErrorDTO(cadError,countDay);
					listErroresQGCXXX.put(errorNuevo.getKey(), errorNuevo);
				
				//Si es de tipo reinyeccion tiene un tratamiento aparte
				}else if(QGInformeUtils.isErrorReinyeccion(cadError)){
					
					String keyErrorReinyeccion = QGInformeUtils.obtenerClaveErrorReinyeccion(cadError);
					
					QGErrorReinyeccionDTO errorReinyeccion = (QGErrorReinyeccionDTO)listErroresReinyeccionAux.get(keyErrorReinyeccion);
					
					if(errorReinyeccion != null){
						//Hay que actualizar o el fijo o el movil o el prepago de los valores
						String modalidad = QGInformeUtils.obtenerModalidadError(cadError);
						
						if(QGInformeConstants.MODALITY_FIJO.equals(modalidad)){
							
							errorReinyeccion.setValorFijo(QGInformeUtils.toInteger(numOcurr));
							
						}else if(QGInformeConstants.MODALITY_MOVIL.equals(modalidad)){
							
							errorReinyeccion.setValorMovil(QGInformeUtils.toInteger(numOcurr));
							
						}else if(QGInformeConstants.MODALITY_PREPAGO.equals(modalidad)){
							
							errorReinyeccion.setValorPrepago(QGInformeUtils.toInteger(numOcurr));
							
						}
						
					}else{
						QGErrorReinyeccionDTO errorNuevo = new QGErrorReinyeccionDTO(cadError);
						listErroresReinyeccionAux.put(errorNuevo.getKey(), errorNuevo);
					}
					
				}else {	
					
					//Vemos si esta en el mapa, para añadirlo o actualizarlo
					
					QGErrorDTO errorMapa = (QGErrorDTO) listErrores.get(keyError);
					if(errorMapa != null){
						//Insertamos el ultimo valor que hubieramos metido
						errorMapa.insertarValor(numOcurr,countDay);
					}else{
						QGErrorDTO errorNuevo = new QGErrorDTO(cadError,countDay);
						listErrores.put(errorNuevo.getKey(), errorNuevo);
						
					}
				}
			}
			
		}
		
		//Le pasamos los errores para que los cuente y los trate
		QGMovimientoFijoDTO diaFijo = listFijos.addDay(fechaDia,infoDay,listErrores,listErroresQGCXXX);
		QGMovimientoMovilDTO diaMovil = listMovil.addDay(fechaDia,infoDay,listErrores,listErroresQGCXXX);
		QGMovimientoPrepagoDTO diaPrepago = listPrepago.addDay(fechaDia,infoDay,listErrores,listErroresQGCXXX);		
				
		//A totales le influye la información que se haya obtenido para cada día
		listTotales.addDay(fechaDia,infoDay,diaFijo,diaMovil,diaPrepago);
				
		this.aumentarDia();
		
		//Usamos una reinyeccion aux
		QGReinyeccionDTO reinyeccionAux = new QGReinyeccionDTO();
		boolean hayReinyeccionAux = reinyeccionAux.crearReinyeccion(infoDay,listErroresReinyeccionAux,listErroresQGCXXX,countDay);
		
		String fechaSalida = null;
		if (fechaSolicitud != null){
			fechaSalida = QGUtilidadesFechasUtils.restarDias(fechaSolicitud,1);
		}
		
		if((hayReinyeccionAux && fechaDia.equals(fechaSalida))){
		//if(hayReinyeccionAux){
			hayReinyeccion = hayReinyeccionAux;
			reinyeccion = reinyeccionAux;
			fechaReinyeccion = QGUtilidadesFechasUtils.formatearFechaDesdeCopy(fechaDia, QGUtilidadesFechasUtils.FORMATO_FECHA_5);
			diaReinyeccion = this.numDias.intValue();
			//Seteamos los nuevos errores encontrados
			listErroresReinyeccion = listErroresReinyeccionAux;
		}
		logger.info("******---FINAL addDay de QGInformeConvivenciaDTO---******");
	}
	
	private void aumentarDia() {
		
		logger.info("******---aumentarDia de QGInformeConvivenciaDTO---******");		
		
		int dia = this.numDias.intValue();
		dia++;
		this.numDias = new Integer(dia);
		
		logger.info("******---FINAL aumentarDia de QGInformeConvivenciaDTO---******");		
	}
	
	public QGListMovimientoTotalDTO getListTotales() {
		return listTotales;
	}

	public void setListTotales(QGListMovimientoTotalDTO listTotales) {
		this.listTotales = listTotales;
	}

	public QGListMovimientoFijoDTO getListFijos() {
		return listFijos;
	}

	public void setListFijos(QGListMovimientoFijoDTO listFijos) {
		this.listFijos = listFijos;
	}

	public QGListMovimientoMovilDTO getListMovil() {
		return listMovil;
	}

	public void setListMovil(QGListMovimientoMovilDTO listMovil) {
		this.listMovil = listMovil;
	}
	
	public QGListMovimientoPrepagoDTO getListPrepago() {
		return listPrepago;
	}

	public void setListPrepago(QGListMovimientoPrepagoDTO listPrepago) {
		this.listPrepago = listPrepago;
	}	

	public QGReinyeccionDTO getReinyeccion() {
		return reinyeccion;
	}

	public void setReinyeccion(QGReinyeccionDTO reinyeccion) {
		this.reinyeccion = reinyeccion;
	}

	public boolean isHayReinyeccion() {
		return hayReinyeccion;
	}

	public void setHayReinyeccion(boolean hayReinyeccion) {
		this.hayReinyeccion = hayReinyeccion;
	}

	public List getFechasInforme() {
		return fechasInforme;
	}

	public void setFechasInforme(List fechasInforme) {
		this.fechasInforme = fechasInforme;
	}

	public Map getListErrores() {
		return listErrores;
	}

	public void setListErrores(Map listErrores) {
		this.listErrores = listErrores;
	}

	public List getErroresFijos(){
		return QGInformeUtils.obtenerErroresFijos(listErrores);
	}
	
	public List getErroresMovil(){
		return QGInformeUtils.obtenerErroresMovil(listErrores);
	}
	
	public List getErroresPrepago(){
		return QGInformeUtils.obtenerErroresPrepago(listErrores);
	}	
	
	public List getErroresReinyeccion(){
		return QGInformeUtils.obtenerListadoErroresReinyeccion(listErroresReinyeccion);
	}

	public QGDecoratorErrorDTO getDecoratorError() {
		return decoratorError;
	}

	public void setDecoratorError(QGDecoratorErrorDTO decoratorError) {
		this.decoratorError = decoratorError;
	}

	public Integer getNumDias() {
		return numDias;
	}

	public void setNumDias(Integer numDias) {
		this.numDias = numDias;
	}

	public String getFechaReinyeccion() {
		return fechaReinyeccion;
	}

	public void setFechaReinyeccion(String fechaReinyeccion) {
		this.fechaReinyeccion = fechaReinyeccion;
	}

	public int getDiaReinyeccion() {
		return diaReinyeccion;
	}

	public void setDiaReinyeccion(int diaReinyeccion) {
		this.diaReinyeccion = diaReinyeccion;
	}

	public Map getListErroresReinyeccion() {
		return listErroresReinyeccion;
	}

	public void setListErroresReinyeccion(Map listErroresReinyeccion) {
		this.listErroresReinyeccion = listErroresReinyeccion;
	}

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	
	

}
