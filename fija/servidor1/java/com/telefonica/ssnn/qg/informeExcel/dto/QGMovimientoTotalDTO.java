package com.telefonica.ssnn.qg.informeExcel.dto;

import java.util.ArrayList;
import java.util.List;

import com.telefonica.ssnn.qg.util.QGInformeUtils;

public class QGMovimientoTotalDTO {

	/**
	 * Indica si el movimiento de totales
	 */
	private boolean total = false;
	/**
	 * Fecha a la que hace referencia el movimiento total
	 */
	private String fecha;

	private Integer tratadosFechaFija;
	private Integer pendienteFechaFija;
	private Integer errorFechaFija;

	private Integer tratadosPosteriorFija;
	private Integer pendientePosteriorFija;
	private Integer errorPosteriorFija;

	private Integer tratadosFechaMovil;
	private Integer pendienteFechaMovil;
	private Integer errorFechaMovil;

	private Integer tratadosPosteriorMovil;
	private Integer pendientePosteriorMovil;
	private Integer errorPosteriorMovil;

	private Integer movOnline;
	
	private Integer tratadosFechaPrepago;
	private Integer pendienteFechaPrepago;
	private Integer errorFechaPrepago;

	private Integer tratadosPosteriorPrepago;
	private Integer pendientePosteriorPrepago;
	private Integer errorPosteriorPrepago;

	private Integer movOnlinePrepago;	
	
	/**
	 * Es una lista que muestra en el orden de las tablas los valores que se van a mostrar
	 */
	private List listaValoresMostrar;
	/**
	 * Lista para los tantos por ciento
	 */
	private List listaValoresPorCiento;
	
	/**
	 * Suma de errores ABS (solo para la columna de totales de movimiento total)
	 */
	private Integer absErrorSuma;
	/**
	 * Suma de pendientes ABS (solo para la columna de totales de movimiento total)
	 */
	private Integer absPendienteSuma;
	
	/*
	 * Quedarian los atributos que indican sumas, tantos por ciento...
	 */

	public QGMovimientoTotalDTO() {
		listaValoresMostrar = new ArrayList();
		listaValoresPorCiento = new ArrayList();		
		
		tratadosFechaFija = new Integer(0);
		pendienteFechaFija = new Integer(0);
		errorFechaFija = new Integer(0);

		tratadosPosteriorFija = new Integer(0);
		pendientePosteriorFija = new Integer(0);
		errorPosteriorFija = new Integer(0);

		tratadosFechaMovil = new Integer(0);
		pendienteFechaMovil = new Integer(0);
		errorFechaMovil = new Integer(0);

		tratadosPosteriorMovil = new Integer(0);
		pendientePosteriorMovil = new Integer(0);
		errorPosteriorMovil = new Integer(0);
		
		absErrorSuma = new Integer(0);
		absPendienteSuma = new Integer(0);

		movOnline = new Integer(0);
		
		tratadosFechaPrepago = new Integer(0);
		pendienteFechaPrepago = new Integer(0);
		errorFechaPrepago = new Integer(0);

		tratadosPosteriorPrepago = new Integer(0);
		pendientePosteriorPrepago = new Integer(0);
		errorPosteriorPrepago = new Integer(0);
		
		movOnlinePrepago = new Integer(0);		
		
	}
	
	/**
	 * Dada la información en infoDay creamos el objeto QGMovimientoTotalDTO
	 * @param infoDay
	 * @param diaMovil 
	 * @param diaFijo
	 * @param diaPrepago 
	 */
	public void crearDia(List infoDay, QGMovimientoFijoDTO diaFijo, QGMovimientoMovilDTO diaMovil, QGMovimientoPrepagoDTO diaPrepago) {
		
		if(infoDay != null && infoDay.size() > 0){
			//Tratariamos toda la informacion referente a dicho movimiento total para el dia.
			this.setTratadosFechaFija(QGInformeUtils.toInteger((String) infoDay.get(0)));
			this.setPendienteFechaFija(QGInformeUtils.toInteger((String) infoDay.get(1)));
			this.setErrorFechaFija(QGInformeUtils.toInteger((String)infoDay.get(2)));
			
			this.setTratadosPosteriorFija(QGInformeUtils.abs(QGInformeUtils.restar(this.getTratadosFechaFija(), diaFijo.getMovTratado().getTotal())));
			this.setPendientePosteriorFija(QGInformeUtils.abs(QGInformeUtils.restar(this.getPendienteFechaFija(), diaFijo.getMovPendiente().getTotal())));
			this.setErrorPosteriorFija(QGInformeUtils.abs(QGInformeUtils.restar(this.getErrorFechaFija(), diaFijo.getMovErrores().getTotal())));
			
			this.setMovOnline(QGInformeUtils.toInteger((String)infoDay.get(3)));
			
			this.setTratadosFechaMovil(QGInformeUtils.toInteger((String) infoDay.get(4)));
			this.setPendienteFechaMovil(QGInformeUtils.toInteger((String) infoDay.get(5)));
			this.setErrorFechaMovil(QGInformeUtils.toInteger((String) infoDay.get(6)));
			
			this.setTratadosPosteriorMovil(QGInformeUtils.abs(QGInformeUtils.restar(this.getTratadosFechaMovil(), diaMovil.getMovTratado().getTotal())));
			this.setPendientePosteriorMovil(QGInformeUtils.abs(QGInformeUtils.restar(this.getPendienteFechaMovil(), diaMovil.getMovPendiente().getTotal())));
			this.setErrorPosteriorMovil(QGInformeUtils.abs(QGInformeUtils.restar(this.getErrorFechaMovil(), diaMovil.getMovErrores().getTotal())));
			
			this.setMovOnlinePrepago(QGInformeUtils.toInteger((String)infoDay.get(7)));
			
			this.setTratadosFechaPrepago(QGInformeUtils.toInteger((String) infoDay.get(8)));
			this.setPendienteFechaPrepago(QGInformeUtils.toInteger((String) infoDay.get(9)));
			this.setErrorFechaPrepago(QGInformeUtils.toInteger((String) infoDay.get(10)));
			
			this.setTratadosPosteriorPrepago(QGInformeUtils.abs(QGInformeUtils.restar(this.getTratadosFechaPrepago(), diaPrepago.getMovTratado().getTotal())));
			this.setPendientePosteriorPrepago(QGInformeUtils.abs(QGInformeUtils.restar(this.getPendienteFechaPrepago(), diaPrepago.getMovPendiente().getTotal())));
			this.setErrorPosteriorPrepago(QGInformeUtils.abs(QGInformeUtils.restar(this.getErrorFechaPrepago(), diaPrepago.getMovErrores().getTotal())));			
		}
		
	}
	
	/**
	 * Metodo para el movimiento total que engloba la suma de los movimientos totales,
	 * en dicha funcion se ira sumando la informacion y calculando los valores conforme se vayan añadiendo datos
	 * @param diaNuevo
	 */
	public void sumarDiaMovimientoTotal(QGMovimientoTotalDTO diaNuevo) {
		//Tratariamos toda la informacion referente a dicho movimiento total para el dia.
		this.setTratadosFechaFija(QGInformeUtils.sumar(this.getTratadosFechaFija(),diaNuevo.getTratadosFechaFija()));
		this.setPendienteFechaFija(QGInformeUtils.sumar(this.getPendienteFechaFija(),diaNuevo.getPendienteFechaFija()));
		this.setErrorFechaFija(QGInformeUtils.sumar(this.getErrorFechaFija(),diaNuevo.getErrorFechaFija()));
		
		this.setTratadosPosteriorFija(QGInformeUtils.sumar(this.getTratadosPosteriorFija(),diaNuevo.getTratadosPosteriorFija()));
		this.setPendientePosteriorFija(QGInformeUtils.sumar(this.getPendientePosteriorFija(),diaNuevo.getPendientePosteriorFija()));
		this.setErrorPosteriorFija(QGInformeUtils.sumar(this.getErrorPosteriorFija(),diaNuevo.getErrorPosteriorFija()));
		
		this.setMovOnline(QGInformeUtils.sumar(this.getMovOnline(),diaNuevo.getMovOnline()));
		
		this.setTratadosFechaMovil(QGInformeUtils.sumar(this.getTratadosFechaMovil(),diaNuevo.getTratadosFechaMovil()));
		this.setPendienteFechaMovil(QGInformeUtils.sumar(this.getPendienteFechaMovil(),diaNuevo.getPendienteFechaMovil()));
		this.setErrorFechaMovil(QGInformeUtils.sumar(this.getErrorFechaMovil(),diaNuevo.getErrorFechaMovil()));
		
		this.setTratadosPosteriorMovil(QGInformeUtils.sumar(this.getTratadosPosteriorMovil(),diaNuevo.getTratadosPosteriorMovil()));
		this.setPendientePosteriorMovil(QGInformeUtils.sumar(this.getPendientePosteriorMovil(),diaNuevo.getPendientePosteriorMovil()));
		this.setErrorPosteriorMovil(QGInformeUtils.sumar(this.getErrorPosteriorMovil(),diaNuevo.getErrorPosteriorMovil()));
		
		//Error ABS Suma
		this.setAbsErrorSuma(QGInformeUtils.sumar(this.getAbsErrorSuma(),diaNuevo.getErrorABS()));
		
		//Pendiente ABS suma
		this.setAbsPendienteSuma(QGInformeUtils.sumar(this.getAbsPendienteSuma(),diaNuevo.getPendientesABS()));
		
		this.setMovOnlinePrepago(QGInformeUtils.sumar(this.getMovOnlinePrepago(),diaNuevo.getMovOnlinePrepago()));
		
		this.setTratadosFechaPrepago(QGInformeUtils.sumar(this.getTratadosFechaPrepago(),diaNuevo.getTratadosFechaPrepago()));
		this.setPendienteFechaPrepago(QGInformeUtils.sumar(this.getPendienteFechaPrepago(),diaNuevo.getPendienteFechaPrepago()));
		this.setErrorFechaPrepago(QGInformeUtils.sumar(this.getErrorFechaPrepago(),diaNuevo.getErrorFechaPrepago()));
		
		this.setTratadosPosteriorPrepago(QGInformeUtils.sumar(this.getTratadosPosteriorPrepago(),diaNuevo.getTratadosPosteriorPrepago()));
		this.setPendientePosteriorPrepago(QGInformeUtils.sumar(this.getPendientePosteriorPrepago(),diaNuevo.getPendientePosteriorPrepago()));
		this.setErrorPosteriorPrepago(QGInformeUtils.sumar(this.getErrorPosteriorPrepago(),diaNuevo.getErrorPosteriorPrepago()));					
	}
	
	public boolean isTotal() {
		return total;
	}

	public void setTotal(boolean total) {
		this.total = total;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * Devuelve el valor que posteriormente debe ser sumado en la columna de totales
	 */
	public Integer getPendientesABS(){
		
		//Sino es total se hacen unas operaciones con ABS
		Integer sumaPendienteFecha = QGInformeUtils.sumar(this.pendienteFechaFija, this.pendienteFechaMovil);
		sumaPendienteFecha = QGInformeUtils.sumar(sumaPendienteFecha, this.pendienteFechaPrepago);		
		Integer sumaPendientePosterior = QGInformeUtils.sumar(this.pendientePosteriorFija, this.pendientePosteriorMovil);
		sumaPendientePosterior = QGInformeUtils.sumar(sumaPendientePosterior, this.pendientePosteriorPrepago);		
		
	    return QGInformeUtils.abs(QGInformeUtils.restar(
				sumaPendienteFecha,
				sumaPendientePosterior));
		
	}
	/**
	 * Devuelve el valor que posteriormente debe ser sumado en la columna de totales
	 */
	public Integer getErrorABS(){
		Integer sumaErrorFecha = QGInformeUtils.sumar(this.errorFechaFija, this.errorFechaMovil);
		sumaErrorFecha = QGInformeUtils.sumar(sumaErrorFecha, this.errorFechaPrepago);		
		Integer sumaErrorPosterior = QGInformeUtils.sumar(this.errorPosteriorFija, this.errorPosteriorMovil);
		sumaErrorPosterior = QGInformeUtils.sumar(sumaErrorPosterior, this.errorPosteriorPrepago);		
		
		return QGInformeUtils.abs(QGInformeUtils.restar(
				sumaErrorFecha,
				sumaErrorPosterior));
	}
	
	
	/**
	 * Monta todos los valores en el orden en el que se muestran, facilitando el volcado a las tablas JSP,
	 * 
	 * AQUI SE APLICARÍAN LAS FORMULAS
	 * @return
	 */
	public List getListaValoresMostrar() {
		
//      TRATADOS
		List listaValores = new ArrayList();
		listaValores.add(this.tratadosFechaFija);
		listaValores.add(this.tratadosFechaMovil);
		listaValores.add(this.tratadosFechaPrepago);		
		listaValores.add(this.tratadosPosteriorFija);
		listaValores.add(this.tratadosPosteriorMovil);
		listaValores.add(this.tratadosPosteriorPrepago);		
				
		Integer tratados = QGInformeUtils.sumar(listaValores);
		
		Integer sumaPendienteFecha = QGInformeUtils.sumar(this.pendienteFechaFija, this.pendienteFechaMovil);
		sumaPendienteFecha = QGInformeUtils.sumar(sumaPendienteFecha, this.pendienteFechaPrepago);
		Integer sumaPendientePosterior = QGInformeUtils.sumar(this.pendientePosteriorFija, this.pendientePosteriorMovil);
		sumaPendientePosterior = QGInformeUtils.sumar(sumaPendientePosterior, this.pendientePosteriorPrepago);
		Integer absPendienteSuma = QGInformeUtils.abs(QGInformeUtils.restar(
				sumaPendienteFecha,
				sumaPendientePosterior));
		
		Integer sumaErrorFecha = QGInformeUtils.sumar(this.errorFechaFija, this.errorFechaMovil);
		sumaErrorFecha = QGInformeUtils.sumar(sumaErrorFecha, this.errorFechaPrepago);
		Integer sumaErrorPosterior = QGInformeUtils.sumar(this.errorPosteriorFija, this.errorPosteriorMovil);
		sumaErrorPosterior = QGInformeUtils.sumar(sumaErrorPosterior, this.errorPosteriorPrepago);
		Integer absErrorSuma = QGInformeUtils.abs(QGInformeUtils.restar(
				sumaErrorFecha,
				sumaErrorPosterior));
	
		Integer total = new Integer(0);
		
		if(!this.total){
			
//			MOVIMIENTOS TOTAL (ON LINE + BATCH)
			listaValores = new ArrayList();
			listaValores.add(absPendienteSuma);
			listaValores.add(tratados);
			listaValores.add(absErrorSuma);

			total = QGInformeUtils.sumar(listaValores);
			
		}else{
			
			listaValores = new ArrayList();
			listaValores.add(this.getAbsErrorSuma());
			listaValores.add(tratados);
			listaValores.add(this.getAbsPendienteSuma());
			
			total = QGInformeUtils.sumar(listaValores);
			
		}
		listaValoresMostrar.add(total);
		
		listaValoresMostrar.add(tratados);
		//              Tratados en Fecha
		listaValoresMostrar.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.tratadosFechaFija, this.tratadosFechaMovil)),this.tratadosFechaPrepago));
		//              Tratados Posterior
		listaValoresMostrar.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.tratadosPosteriorFija, this.tratadosPosteriorMovil)),this.tratadosPosteriorPrepago));
		
		if(!this.total){
			//Sino es total se hacen unas operaciones con ABS
		
			listaValoresMostrar.add(absPendienteSuma);
			//                Pendientes en Fecha
			listaValoresMostrar.add(sumaPendienteFecha);
			//                Pendientes Tratados Posterior
			listaValoresMostrar.add(sumaPendientePosterior);
			
			listaValoresMostrar.add(absErrorSuma);
			//                Errores en Fecha
			listaValoresMostrar.add(sumaErrorFecha);
			//                Errores Tratados Posterior
			listaValoresMostrar.add(sumaErrorPosterior);
		
		}else {
			listaValoresMostrar.add(this.getAbsPendienteSuma());
			//                Pendientes en Fecha
			listaValoresMostrar.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.pendienteFechaFija, this.pendienteFechaMovil)),this.pendienteFechaPrepago));
			//                Pendientes Tratados Posterior
			listaValoresMostrar.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.pendientePosteriorFija, this.pendientePosteriorMovil)),this.pendientePosteriorPrepago));
			
			listaValoresMostrar.add(this.getAbsErrorSuma());
			//                Errores en Fecha
			listaValoresMostrar.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.errorFechaFija, this.errorFechaMovil)),this.errorFechaPrepago));
			//                Errores Tratados Posterior
			listaValoresMostrar.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.errorPosteriorFija, this.errorPosteriorMovil)),this.errorPosteriorPrepago));
		}
		
		//MOVIMIENTOS FIJA (BATCH)
		//=SUMA(C17+C18+ABS(SUMA(C19-C20))+ABS(SUMA(C21-C22)))
		
		listaValores = new ArrayList();
		listaValores.add(this.tratadosFechaFija);
		listaValores.add(this.tratadosPosteriorFija);
		listaValores.add(QGInformeUtils.abs(QGInformeUtils.restar(this.errorFechaFija, this.errorPosteriorFija)));
		listaValores.add(QGInformeUtils.abs(QGInformeUtils.restar(this.pendienteFechaFija, this.pendientePosteriorFija)));
	

		listaValoresMostrar.add(QGInformeUtils.sumar(listaValores));
		//        TRATADOS EN FECHA
		listaValoresMostrar.add(this.tratadosFechaFija);
		//        TRATADOS Posterior
		listaValoresMostrar.add(this.tratadosPosteriorFija);
		//        PENDIENTES EN FECHA
		listaValoresMostrar.add(this.pendienteFechaFija);
		//        Pendientes Tratados Posterior
		listaValoresMostrar.add(this.pendientePosteriorFija);
		//        ERRORES EN FECHA
		listaValoresMostrar.add(this.errorFechaFija);
		//        Erroneos Tratados Posterior
		listaValoresMostrar.add(this.errorPosteriorFija);
		//MOVIMIENTOS MOVIL (BATCH)
		//=C25+C26+ABS(SUMA(C27-C28))+ABS(SUMA(C29-C30))-C24
		
		listaValores = new ArrayList();
		listaValores.add(this.tratadosFechaMovil);
		listaValores.add(this.tratadosPosteriorMovil);
		listaValores.add(QGInformeUtils.abs(QGInformeUtils.restar(this.errorFechaMovil, this.errorPosteriorMovil)));
		listaValores.add(QGInformeUtils.abs(QGInformeUtils.restar(this.pendienteFechaMovil, this.pendientePosteriorMovil)));
		
		listaValoresMostrar.add(QGInformeUtils.restar(QGInformeUtils.sumar(listaValores), this.movOnline));
		
		//MOVIMIENTOS MOVIL (ON LINE)
		listaValoresMostrar.add(this.movOnline);
		//        TRATADOS EN FECHA
		listaValoresMostrar.add(this.tratadosFechaMovil);
		//        Tratados Posterior
		listaValoresMostrar.add(this.tratadosPosteriorMovil);
		//        PENDIENTES EN FECHA
		listaValoresMostrar.add(this.pendienteFechaMovil);
		//        Pendientes Tratados Posterior
		listaValoresMostrar.add(this.pendientePosteriorMovil);
		//        ERRORES EN FECHA
		listaValoresMostrar.add(this.errorFechaMovil);
		//        Erroneos tratados Posterior
		listaValoresMostrar.add(this.errorPosteriorMovil);

		listaValores = new ArrayList();
		listaValores.add(this.tratadosFechaPrepago);
		listaValores.add(this.tratadosPosteriorPrepago);
		listaValores.add(QGInformeUtils.abs(QGInformeUtils.restar(this.errorFechaPrepago, this.errorPosteriorPrepago)));
		listaValores.add(QGInformeUtils.abs(QGInformeUtils.restar(this.pendienteFechaPrepago, this.pendientePosteriorPrepago)));
		
		listaValoresMostrar.add(QGInformeUtils.restar(QGInformeUtils.sumar(listaValores), this.movOnlinePrepago));
		
		//MOVIMIENTOS PREPAGO (ON LINE)
		listaValoresMostrar.add(this.movOnlinePrepago);
		//        TRATADOS EN FECHA
		listaValoresMostrar.add(this.tratadosFechaPrepago);
		//        Tratados Posterior
		listaValoresMostrar.add(this.tratadosPosteriorPrepago);
		//        PENDIENTES EN FECHA
		listaValoresMostrar.add(this.pendienteFechaPrepago);
		//        Pendientes Tratados Posterior
		listaValoresMostrar.add(this.pendientePosteriorPrepago);
		//        ERRORES EN FECHA
		listaValoresMostrar.add(this.errorFechaPrepago);
		//        Erroneos tratados Posterior
		listaValoresMostrar.add(this.errorPosteriorPrepago);		
		
		return listaValoresMostrar;
	}

	public void setListaValoresMostrar(List listaValoresMostrar) {
		this.listaValoresMostrar = listaValoresMostrar;
	}

	public List getListaValoresPorCiento() {
		
//      TRATADOS
		List listaValores = new ArrayList();
		listaValores.add(this.tratadosFechaFija);
		listaValores.add(this.tratadosFechaMovil);
		listaValores.add(this.tratadosFechaPrepago);		
		listaValores.add(this.tratadosPosteriorFija);
		listaValores.add(this.tratadosPosteriorMovil);
		listaValores.add(this.tratadosPosteriorPrepago);		
				
		Integer tratados = QGInformeUtils.sumar(listaValores);
			
		//Sino es total se hacen unas operaciones con ABS
		Integer sumaPendienteFecha = QGInformeUtils.sumar(this.pendienteFechaFija, this.pendienteFechaMovil);
		sumaPendienteFecha = QGInformeUtils.sumar(sumaPendienteFecha, this.pendienteFechaPrepago);
		Integer sumaPendientePosterior = QGInformeUtils.sumar(this.pendientePosteriorFija, this.pendientePosteriorMovil);
		sumaPendientePosterior = QGInformeUtils.sumar(sumaPendientePosterior, this.pendientePosteriorPrepago);
		
		Integer absSumaPendientes = QGInformeUtils.abs(QGInformeUtils.restar(
				sumaPendienteFecha,
				sumaPendientePosterior));
					
		Integer sumaErrorFecha = QGInformeUtils.sumar(this.errorFechaFija, this.errorFechaMovil);
		sumaErrorFecha = QGInformeUtils.sumar(sumaErrorFecha, this.errorFechaPrepago);
		Integer sumaErrorPosterior = QGInformeUtils.sumar(this.errorPosteriorFija, this.errorPosteriorMovil);
		sumaErrorPosterior = QGInformeUtils.sumar(sumaErrorPosterior, this.errorPosteriorPrepago);
		
		Integer absSumaErrores = QGInformeUtils.abs(QGInformeUtils.restar(
				sumaErrorFecha,
				sumaErrorPosterior));
		
		Integer total = new Integer(0);
		
		if(!this.total){
			
			//		MOVIMIENTOS TOTAL (ON LINE + BATCH)
			listaValores = new ArrayList();
			listaValores.add(absSumaPendientes);
			listaValores.add(tratados);
			listaValores.add(absSumaErrores);
			
			total = QGInformeUtils.sumar(listaValores);
			
		}else{
			
			listaValores = new ArrayList();
			listaValores.add(this.getAbsErrorSuma());
			listaValores.add(tratados);
			listaValores.add(this.getAbsPendienteSuma());
			
			total = QGInformeUtils.sumar(listaValores);
			
		}
		
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(total,total));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(tratados,total));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.tratadosFechaFija, this.tratadosFechaMovil)),this.tratadosFechaPrepago),total));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.tratadosPosteriorFija, this.tratadosPosteriorMovil)),this.tratadosPosteriorPrepago),total));
		
		if(!this.total){
			
			listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(absSumaPendientes,total));
			listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(sumaPendienteFecha,total));
			listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(sumaPendientePosterior,total));
			
			listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(absSumaErrores,total));
			listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(sumaErrorFecha,total));
			listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(sumaErrorPosterior,total));
		}else{
			
			listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getAbsPendienteSuma(),total));
			listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.pendienteFechaFija, this.pendienteFechaMovil)),this.pendienteFechaPrepago),total));
			listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.pendientePosteriorFija, this.pendientePosteriorMovil)),this.pendientePosteriorPrepago),total));
			
			listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getAbsErrorSuma(),total));
			listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.errorFechaFija, this.errorFechaMovil)),this.errorFechaPrepago),total));
			listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.errorPosteriorFija, this.errorPosteriorMovil)),this.errorPosteriorPrepago),total));
				
		}
		listaValores = new ArrayList();
		listaValores.add(this.tratadosFechaFija);
		listaValores.add(this.tratadosPosteriorFija);
		listaValores.add(QGInformeUtils.abs(QGInformeUtils.restar(this.errorFechaFija, this.errorPosteriorFija)));
		listaValores.add(QGInformeUtils.abs(QGInformeUtils.restar(this.pendienteFechaFija, this.pendientePosteriorFija)));
	
		Integer sumaValores = QGInformeUtils.sumar(listaValores);

		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(sumaValores,sumaValores));
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.tratadosFechaFija,sumaValores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.tratadosPosteriorFija,sumaValores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.pendienteFechaFija,sumaValores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.pendientePosteriorFija,sumaValores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.errorFechaFija,sumaValores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.errorPosteriorFija,sumaValores));
		
		
		listaValores = new ArrayList();
		listaValores.add(this.tratadosFechaMovil);
		listaValores.add(this.tratadosPosteriorMovil);
		listaValores.add(QGInformeUtils.abs(QGInformeUtils.restar(this.errorFechaMovil, this.errorPosteriorMovil)));
		listaValores.add(QGInformeUtils.abs(QGInformeUtils.restar(this.pendienteFechaMovil, this.pendientePosteriorMovil)));
		
		sumaValores = QGInformeUtils.sumar(listaValores);
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(sumaValores, sumaValores));
		
		//Esta linea corresponde con movimientos online que no se muestra
		listaValoresPorCiento.add(new Integer(0));
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.tratadosFechaMovil,sumaValores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.tratadosPosteriorMovil,sumaValores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.pendienteFechaMovil,sumaValores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.pendientePosteriorMovil,sumaValores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.errorFechaMovil,sumaValores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.errorPosteriorMovil,sumaValores));
		
		listaValores = new ArrayList();
		listaValores.add(this.tratadosFechaPrepago);
		listaValores.add(this.tratadosPosteriorPrepago);
		listaValores.add(QGInformeUtils.abs(QGInformeUtils.restar(this.errorFechaPrepago, this.errorPosteriorPrepago)));
		listaValores.add(QGInformeUtils.abs(QGInformeUtils.restar(this.pendienteFechaPrepago, this.pendientePosteriorPrepago)));
		
		sumaValores = QGInformeUtils.sumar(listaValores);
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(sumaValores, sumaValores));
		
		//Esta linea corresponde con movimientos online que no se muestra
		listaValoresPorCiento.add(new Integer(0));
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.tratadosFechaPrepago,sumaValores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.tratadosPosteriorPrepago,sumaValores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.pendienteFechaPrepago,sumaValores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.pendientePosteriorPrepago,sumaValores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.errorFechaPrepago,sumaValores));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.errorPosteriorPrepago,sumaValores));		
		
		return listaValoresPorCiento;
	}

	public void setListaValoresPorCiento(List listaValoresPorCiento) {
		this.listaValoresPorCiento = listaValoresPorCiento;
	}

	public Integer getTratadosFechaFija() {
		return tratadosFechaFija;
	}

	public void setTratadosFechaFija(Integer tratadosFechaFija) {
		this.tratadosFechaFija = tratadosFechaFija;
	}

	public Integer getPendienteFechaFija() {
		return pendienteFechaFija;
	}

	public void setPendienteFechaFija(Integer pendienteFechaFija) {
		this.pendienteFechaFija = pendienteFechaFija;
	}

	public Integer getErrorFechaFija() {
		return errorFechaFija;
	}

	public void setErrorFechaFija(Integer errorFechaFija) {
		this.errorFechaFija = errorFechaFija;
	}

	public Integer getTratadosPosteriorFija() {
		return tratadosPosteriorFija;
	}

	public void setTratadosPosteriorFija(Integer tratadosPosteriorFija) {
		this.tratadosPosteriorFija = tratadosPosteriorFija;
	}

	public Integer getPendientePosteriorFija() {
		return pendientePosteriorFija;
	}

	public void setPendientePosteriorFija(Integer pendientePosteriorFija) {
		this.pendientePosteriorFija = pendientePosteriorFija;
	}

	public Integer getErrorPosteriorFija() {
		return errorPosteriorFija;
	}

	public void setErrorPosteriorFija(Integer errorPosteriorFija) {
		this.errorPosteriorFija = errorPosteriorFija;
	}

	public Integer getTratadosFechaMovil() {
		return tratadosFechaMovil;
	}

	public void setTratadosFechaMovil(Integer tratadosFechaMovil) {
		this.tratadosFechaMovil = tratadosFechaMovil;
	}

	public Integer getPendienteFechaMovil() {
		return pendienteFechaMovil;
	}

	public void setPendienteFechaMovil(Integer pendienteFechaMovil) {
		this.pendienteFechaMovil = pendienteFechaMovil;
	}

	public Integer getErrorFechaMovil() {
		return errorFechaMovil;
	}

	public void setErrorFechaMovil(Integer errorFechaMovil) {
		this.errorFechaMovil = errorFechaMovil;
	}

	public Integer getTratadosPosteriorMovil() {
		return tratadosPosteriorMovil;
	}

	public void setTratadosPosteriorMovil(Integer tratadosPosteriorMovil) {
		this.tratadosPosteriorMovil = tratadosPosteriorMovil;
	}

	public Integer getPendientePosteriorMovil() {
		return pendientePosteriorMovil;
	}

	public void setPendientePosteriorMovil(Integer pendientePosteriorMovil) {
		this.pendientePosteriorMovil = pendientePosteriorMovil;
	}

	public Integer getErrorPosteriorMovil() {
		return errorPosteriorMovil;
	}

	public void setErrorPosteriorMovil(Integer errorPosteriorMovil) {
		this.errorPosteriorMovil = errorPosteriorMovil;
	}

	public Integer getMovOnline() {
		return movOnline;
	}

	public void setMovOnline(Integer movOnline) {
		this.movOnline = movOnline;
	}
	
	public Integer getTratadosFechaPrepago() {
		return tratadosFechaPrepago;
	}

	public void setTratadosFechaPrepago(Integer tratadosFechaPrepago) {
		this.tratadosFechaPrepago = tratadosFechaPrepago;
	}

	public Integer getPendienteFechaPrepago() {
		return pendienteFechaPrepago;
	}

	public void setPendienteFechaPrepago(Integer pendienteFechaPrepago) {
		this.pendienteFechaPrepago = pendienteFechaPrepago;
	}

	public Integer getErrorFechaPrepago() {
		return errorFechaPrepago;
	}

	public void setErrorFechaPrepago(Integer errorFechaPrepago) {
		this.errorFechaPrepago = errorFechaPrepago;
	}

	public Integer getTratadosPosteriorPrepago() {
		return tratadosPosteriorPrepago;
	}

	public void setTratadosPosteriorPrepago(Integer tratadosPosteriorPrepago) {
		this.tratadosPosteriorPrepago = tratadosPosteriorPrepago;
	}

	public Integer getPendientePosteriorPrepago() {
		return pendientePosteriorPrepago;
	}

	public void setPendientePosteriorPrepago(Integer pendientePosteriorPrepago) {
		this.pendientePosteriorPrepago = pendientePosteriorPrepago;
	}

	public Integer getErrorPosteriorPrepago() {
		return errorPosteriorPrepago;
	}

	public void setErrorPosteriorPrepago(Integer errorPosteriorPrepago) {
		this.errorPosteriorPrepago = errorPosteriorPrepago;
	}

	public Integer getMovOnlinePrepago() {
		return movOnlinePrepago;
	}

	public void setMovOnlinePrepago(Integer movOnlinePrepago) {
		this.movOnlinePrepago = movOnlinePrepago;
	}	

	public Integer getAbsErrorSuma() {
		return absErrorSuma;
	}

	public void setAbsErrorSuma(Integer absErrorSuma) {
		this.absErrorSuma = absErrorSuma;
	}

	public Integer getAbsPendienteSuma() {
		return absPendienteSuma;
	}

	public void setAbsPendienteSuma(Integer absPendienteSuma) {
		this.absPendienteSuma = absPendienteSuma;
	}	
}
