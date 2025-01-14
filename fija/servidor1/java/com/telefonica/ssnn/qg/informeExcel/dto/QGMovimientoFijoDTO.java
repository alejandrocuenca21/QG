package com.telefonica.ssnn.qg.informeExcel.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.telefonica.ssnn.qg.informeExcel.dto.base.QGMovimientoFijoBaseDTO;
import com.telefonica.ssnn.qg.util.QGInformeUtils;

public class QGMovimientoFijoDTO {

	/**
	 * Indica si el movimiento de totales
	 */
	private boolean total = false;
	
	private QGMovimientoFijoBaseDTO movTratado;
	private QGMovimientoFijoBaseDTO movPendiente;
	private QGMovimientoFijoBaseDTO movWarning;
	private QGMovimientoFijoBaseDTO movErrores;
	
	/**
	 * Es una lista que muestra en el orden de las tablas los valores que se van a mostrar
	 */
	private List listaValoresMostrar;
	/**
	 * Lista para los tantos por ciento
	 */
	private List listaValoresPorCiento;
	
	

	public QGMovimientoFijoDTO() {
		movTratado = new QGMovimientoFijoBaseDTO();
		movPendiente = new QGMovimientoFijoBaseDTO();
		movWarning = new QGMovimientoFijoBaseDTO();
		movErrores = new QGMovimientoFijoBaseDTO();
		
		listaValoresMostrar = new ArrayList();
		listaValoresPorCiento = new ArrayList();
		
	}
	/**
	 * Dada la información en infoDay creamos el objeto QGMovimientoFijoDTO
	 * @param infoDay
	 * @param listErrores 
	 * @param listErroresQGCXXX 
	 * @param dia 
	 */
	public void crearDia(List infoDay, Map listErrores, Map listErroresQGCXXX, int dia) {
		//int i = 7;
		int i = 11;		
		
		if(infoDay != null && infoDay.size() > 0){
		
		this.getMovTratado().setAlta(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setBaja(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setModificacion(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setCambioSeg(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setCambioSegDif(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setBajaDif(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setReactivacion(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		
		this.getMovPendiente().setAlta(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setBaja(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setModificacion(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setCambioSeg(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setCambioSegDif(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setBajaDif(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setReactivacion(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		
		//Inicializamos a 0 los valores de errores y warnings
		this.getMovWarning().setAlta(new Integer(0));
		this.getMovWarning().setBaja(new Integer(0));
		this.getMovWarning().setModificacion(new Integer(0));
		this.getMovWarning().setCambioSeg(new Integer(0));
		this.getMovWarning().setCambioSegDif(new Integer(0));
		this.getMovWarning().setBajaDif(new Integer(0));
		this.getMovWarning().setReactivacion(new Integer(0));
		
		this.getMovErrores().setAlta(new Integer(0));
		this.getMovErrores().setBaja(new Integer(0));
		this.getMovErrores().setModificacion(new Integer(0));
		this.getMovErrores().setCambioSeg(new Integer(0));
		this.getMovErrores().setCambioSegDif(new Integer(0));
		this.getMovErrores().setBajaDif(new Integer(0));
		this.getMovErrores().setReactivacion(new Integer(0));
		
		List erroresFijos = QGInformeUtils.obtenerErroresFijos(listErrores);
		//Tratamos los errores para ir añadiendolos
		if(erroresFijos != null && erroresFijos.size() > 0){
			Iterator ite = erroresFijos.iterator();
			
			while(ite.hasNext()){
				QGErrorDTO errorFijo = (QGErrorDTO)ite.next();
				//Errores y Warnings
				if(errorFijo.isTipoError()){
					if(errorFijo.isErrorAlta()){
						
						this.getMovErrores().setAlta(QGInformeUtils.sumar(this.getMovErrores().getAlta(), (Integer)errorFijo.getValoresPorFecha().get(dia)));
						
					}else if(errorFijo.isErrorBaja()){
						
						this.getMovErrores().setBaja(QGInformeUtils.sumar(this.getMovErrores().getBaja(), (Integer)errorFijo.getValoresPorFecha().get(dia)));
						
					}else if(errorFijo.isErrorModificacion()){
						
						this.getMovErrores().setModificacion(QGInformeUtils.sumar(this.getMovErrores().getModificacion(), (Integer)errorFijo.getValoresPorFecha().get(dia)));

					}else if(errorFijo.isErrorCambioSeg()){
						
						this.getMovErrores().setCambioSeg(QGInformeUtils.sumar(this.getMovErrores().getCambioSeg(), (Integer)errorFijo.getValoresPorFecha().get(dia)));
						
					}else if(errorFijo.isErrorCambioDif()){
						
						this.getMovErrores().setCambioSegDif(QGInformeUtils.sumar(this.getMovErrores().getCambioSegDif(), (Integer)errorFijo.getValoresPorFecha().get(dia)));
						
					}else if(errorFijo.isErrorBajaDif()){
						
						this.getMovErrores().setBajaDif(QGInformeUtils.sumar(this.getMovErrores().getBajaDif(), (Integer)errorFijo.getValoresPorFecha().get(dia)));

					}else if(errorFijo.isErrorReactivacion()){
						
						this.getMovErrores().setReactivacion(QGInformeUtils.sumar(this.getMovErrores().getReactivacion(), (Integer)errorFijo.getValoresPorFecha().get(dia)));

					}
					
				}else if(errorFijo.isTipoWarning()){
					
					if(errorFijo.isErrorAlta()){
						
						this.getMovWarning().setAlta(QGInformeUtils.sumar(this.getMovWarning().getAlta(), (Integer)errorFijo.getValoresPorFecha().get(dia)));
						
					}else if(errorFijo.isErrorBaja()){
						
						this.getMovWarning().setBaja(QGInformeUtils.sumar(this.getMovWarning().getBaja(), (Integer)errorFijo.getValoresPorFecha().get(dia)));
						
					}else if(errorFijo.isErrorModificacion()){
						
						this.getMovWarning().setModificacion(QGInformeUtils.sumar(this.getMovWarning().getModificacion(), (Integer)errorFijo.getValoresPorFecha().get(dia)));

					}else if(errorFijo.isErrorCambioSeg()){
						
						this.getMovWarning().setCambioSeg(QGInformeUtils.sumar(this.getMovWarning().getCambioSeg(), (Integer)errorFijo.getValoresPorFecha().get(dia)));
						
					}else if(errorFijo.isErrorCambioDif()){
						
						this.getMovWarning().setCambioSegDif(QGInformeUtils.sumar(this.getMovWarning().getCambioSegDif(), (Integer)errorFijo.getValoresPorFecha().get(dia)));
						
					}else if(errorFijo.isErrorBajaDif()){
						
						this.getMovWarning().setBajaDif(QGInformeUtils.sumar(this.getMovWarning().getBajaDif(), (Integer)errorFijo.getValoresPorFecha().get(dia)));

					}else if(errorFijo.isErrorReactivacion()){
						
						this.getMovWarning().setReactivacion(QGInformeUtils.sumar(this.getMovWarning().getReactivacion(), (Integer)errorFijo.getValoresPorFecha().get(dia)));

					}
					
				}
				}
				
			
		}
		
		List erroresQGCXXX = QGInformeUtils.obtenerErroresFijos(listErroresQGCXXX);
		
		//Tratamos los QGCXXX
		if(erroresQGCXXX != null && erroresQGCXXX.size()> 0){
			
			Iterator ite = erroresQGCXXX.iterator();
			
			while(ite.hasNext()){
				
				QGErrorDTO errorQGCXXX = (QGErrorDTO) ite.next();
				
				// Error QGCXXX
				/*
				 * En la hoja excel del ejemplo se especifica que para las
				 * pestañas de fija, móvil y reinyección al valor de tratados
				 * hay que añadirle los errores QGCXXX para cada tipo
				 * 
				 * Ademas solo tienen un valor
				 */
				if(errorQGCXXX.isErrorAlta()){
					
					this.getMovTratado().setAlta(QGInformeUtils.sumar(this.getMovTratado().getAlta(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
					
				}else if(errorQGCXXX.isErrorBaja()){
					
					this.getMovTratado().setBaja(QGInformeUtils.sumar(this.getMovTratado().getBaja(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));

				}else if(errorQGCXXX.isErrorModificacion()){

					this.getMovTratado().setModificacion(QGInformeUtils.sumar(this.getMovTratado().getModificacion(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
					
				}else if(errorQGCXXX.isErrorCambioSeg()){
					
					this.getMovTratado().setCambioSeg(QGInformeUtils.sumar(this.getMovTratado().getCambioSeg(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
					
				}else if(errorQGCXXX.isErrorCambioDif()){
					
					this.getMovTratado().setCambioSegDif(QGInformeUtils.sumar(this.getMovTratado().getCambioSegDif(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
					
				}else if(errorQGCXXX.isErrorBajaDif()){
					
					this.getMovTratado().setBajaDif(QGInformeUtils.sumar(this.getMovTratado().getBajaDif(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
					
				}else if(errorQGCXXX.isErrorReactivacion()){
					
					this.getMovTratado().setReactivacion(QGInformeUtils.sumar(this.getMovTratado().getReactivacion(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));

				}
				
				
			}
			
			
		}
		}
	}

	/**
	 * Metodo para el movimiento fijo que engloba la suma de los movimientos fihos,
	 * en dicha funcion se ira sumando la informacion y calculando los valores conforme se vayan añadiendo datos
	 * @param diaNuevo
	 */
	public void sumarDiaMovimientoTotal(QGMovimientoFijoDTO diaNuevo) {
		
		this.getMovTratado().setAlta(QGInformeUtils.sumar(this.getMovTratado().getAlta(),diaNuevo.getMovTratado().getAlta()));
		this.getMovTratado().setBaja(QGInformeUtils.sumar(this.getMovTratado().getBaja(),diaNuevo.getMovTratado().getBaja()));
		this.getMovTratado().setModificacion(QGInformeUtils.sumar(this.getMovTratado().getModificacion(),diaNuevo.getMovTratado().getModificacion()));
		this.getMovTratado().setCambioSeg(QGInformeUtils.sumar(this.getMovTratado().getCambioSeg(),diaNuevo.getMovTratado().getCambioSeg()));
		this.getMovTratado().setCambioSegDif(QGInformeUtils.sumar(this.getMovTratado().getCambioSegDif(),diaNuevo.getMovTratado().getCambioSegDif()));
		this.getMovTratado().setBajaDif(QGInformeUtils.sumar(this.getMovTratado().getBajaDif(),diaNuevo.getMovTratado().getBajaDif()));
		this.getMovTratado().setReactivacion(QGInformeUtils.sumar(this.getMovTratado().getReactivacion(),diaNuevo.getMovTratado().getReactivacion()));
	
		this.getMovPendiente().setAlta(QGInformeUtils.sumar(this.getMovPendiente().getAlta(),diaNuevo.getMovPendiente().getAlta()));
		this.getMovPendiente().setBaja(QGInformeUtils.sumar(this.getMovPendiente().getBaja(),diaNuevo.getMovPendiente().getBaja()));
		this.getMovPendiente().setModificacion(QGInformeUtils.sumar(this.getMovPendiente().getModificacion(),diaNuevo.getMovPendiente().getModificacion()));
		this.getMovPendiente().setCambioSeg(QGInformeUtils.sumar(this.getMovPendiente().getCambioSeg(),diaNuevo.getMovPendiente().getCambioSeg()));
		this.getMovPendiente().setCambioSegDif(QGInformeUtils.sumar(this.getMovPendiente().getCambioSegDif(),diaNuevo.getMovPendiente().getCambioSegDif()));
		this.getMovPendiente().setBajaDif(QGInformeUtils.sumar(this.getMovPendiente().getBajaDif(),diaNuevo.getMovPendiente().getBajaDif()));
		this.getMovPendiente().setReactivacion(QGInformeUtils.sumar(this.getMovPendiente().getReactivacion(),diaNuevo.getMovPendiente().getReactivacion()));
		
		this.getMovErrores().setAlta(QGInformeUtils.sumar(this.getMovErrores().getAlta(),diaNuevo.getMovErrores().getAlta()));
		this.getMovErrores().setBaja(QGInformeUtils.sumar(this.getMovErrores().getBaja(),diaNuevo.getMovErrores().getBaja()));
		this.getMovErrores().setModificacion(QGInformeUtils.sumar(this.getMovErrores().getModificacion(),diaNuevo.getMovErrores().getModificacion()));
		this.getMovErrores().setCambioSeg(QGInformeUtils.sumar(this.getMovErrores().getCambioSeg(),diaNuevo.getMovErrores().getCambioSeg()));
		this.getMovErrores().setCambioSegDif(QGInformeUtils.sumar(this.getMovErrores().getCambioSegDif(),diaNuevo.getMovErrores().getCambioSegDif()));
		this.getMovErrores().setBajaDif(QGInformeUtils.sumar(this.getMovErrores().getBajaDif(),diaNuevo.getMovErrores().getBajaDif()));
		this.getMovErrores().setReactivacion(QGInformeUtils.sumar(this.getMovErrores().getReactivacion(),diaNuevo.getMovErrores().getReactivacion()));
		
		this.getMovWarning().setAlta(QGInformeUtils.sumar(this.getMovWarning().getAlta(),diaNuevo.getMovWarning().getAlta()));
		this.getMovWarning().setBaja(QGInformeUtils.sumar(this.getMovWarning().getBaja(),diaNuevo.getMovWarning().getBaja()));
		this.getMovWarning().setModificacion(QGInformeUtils.sumar(this.getMovWarning().getModificacion(),diaNuevo.getMovWarning().getModificacion()));
		this.getMovWarning().setCambioSeg(QGInformeUtils.sumar(this.getMovWarning().getCambioSeg(),diaNuevo.getMovWarning().getCambioSeg()));
		this.getMovWarning().setCambioSegDif(QGInformeUtils.sumar(this.getMovWarning().getCambioSegDif(),diaNuevo.getMovWarning().getCambioSegDif()));
		this.getMovWarning().setBajaDif(QGInformeUtils.sumar(this.getMovWarning().getBajaDif(),diaNuevo.getMovWarning().getBajaDif()));
		this.getMovWarning().setReactivacion(QGInformeUtils.sumar(this.getMovWarning().getReactivacion(),diaNuevo.getMovWarning().getReactivacion()));
		
	}

	public QGMovimientoFijoBaseDTO getMovTratado() {
		return movTratado;
	}

	public void setMovTratado(QGMovimientoFijoBaseDTO movTratado) {
		this.movTratado = movTratado;
	}

	public QGMovimientoFijoBaseDTO getMovPendiente() {
		return movPendiente;
	}

	public void setMovPendiente(QGMovimientoFijoBaseDTO movPendiente) {
		this.movPendiente = movPendiente;
	}

	public QGMovimientoFijoBaseDTO getMovWarning() {
		return movWarning;
	}

	public void setMovWarning(QGMovimientoFijoBaseDTO movWarning) {
		this.movWarning = movWarning;
	}

	public QGMovimientoFijoBaseDTO getMovErrores() {
		return movErrores;
	}

	public void setMovErrores(QGMovimientoFijoBaseDTO movErrores) {
		this.movErrores = movErrores;
	}
	public boolean isTotal() {
		return total;
	}
	public void setTotal(boolean total) {
		this.total = total;
	}
	public List getListaValoresMostrar() {
		
		List listaValores = new ArrayList();
		listaValores.add(this.getMovTratado().getTotal());
		listaValores.add(this.getMovPendiente().getTotal());
		listaValores.add(this.getMovErrores().getTotal());
		
		Integer totales = QGInformeUtils.sumar(listaValores);
		
		//		 MOVIMIENTOS FIJA
		listaValoresMostrar.add(totales);
		//         TRATADOS
		listaValoresMostrar.add(this.getMovTratado().getTotal());
		//         PENDIENTES
		listaValoresMostrar.add(this.getMovPendiente().getTotal());
		//         ERRORES
		listaValoresMostrar.add(this.getMovErrores().getTotal());
		//
		//         TRATADOS
		listaValoresMostrar.add(this.getMovTratado().getTotal());
		//			A - ALTA 
		listaValoresMostrar.add(this.getMovTratado().getAlta());
		//			B - BAJA
		listaValoresMostrar.add(this.getMovTratado().getBaja());
		//			M - MODIFICACION
		listaValoresMostrar.add(this.getMovTratado().getModificacion());
		//			C - CAMBIO SEGMENTO
		listaValoresMostrar.add(this.getMovTratado().getCambioSeg());
		//			D - CAMBIO SEGMENTO DIFERIDO
		listaValoresMostrar.add(this.getMovTratado().getCambioSegDif());
		//			E - BAJA EN DIFERIDO
		listaValoresMostrar.add(this.getMovTratado().getBajaDif());
		//			R - REACTIVACION DE CLIENTES
		listaValoresMostrar.add(this.getMovTratado().getReactivacion());
		//			
		//			 
		//			         PENDIENTES
		listaValoresMostrar.add(this.getMovPendiente().getTotal());
		//			A - ALTA
		listaValoresMostrar.add(this.getMovPendiente().getAlta());
		//			B - BAJA
		listaValoresMostrar.add(this.getMovPendiente().getBaja());
		//			M - MODIFICACION
		listaValoresMostrar.add(this.getMovPendiente().getModificacion());
		//			C - CAMBIO SEGMENTO
		listaValoresMostrar.add(this.getMovPendiente().getCambioSeg());
		//			D - CAMBIO SEGMENTO DIFERIDO
		listaValoresMostrar.add(this.getMovPendiente().getCambioSegDif());
		//			E - BAJA EN DIFERIDO
		listaValoresMostrar.add(this.getMovPendiente().getBajaDif());
		//			R - REACTIVACION DE CLIENTES
		listaValoresMostrar.add(this.getMovPendiente().getReactivacion());
		//			
		//			         ERRORES
		listaValoresMostrar.add(this.getMovErrores().getTotal());
		//			A - ALTA
		listaValoresMostrar.add(this.getMovErrores().getAlta());
		//			B - BAJA
		listaValoresMostrar.add(this.getMovErrores().getBaja());
		//			M - MODIFICACION
		listaValoresMostrar.add(this.getMovErrores().getModificacion());
		//			C - CAMBIO SEGMENTO
		listaValoresMostrar.add(this.getMovErrores().getCambioSeg());
		//			D - CAMBIO SEGMENTO DIFERIDO
		listaValoresMostrar.add(this.getMovErrores().getCambioSegDif());
		//			E - BAJA EN DIFERIDO
		listaValoresMostrar.add(this.getMovErrores().getBajaDif());
		//			R - REACTIVACION DE CLIENTES
		listaValoresMostrar.add(this.getMovErrores().getReactivacion());
	
		//			         ERRORES INFORMATIVOS
		listaValoresMostrar.add(this.getMovWarning().getTotal());
		//			A - ALTA
		listaValoresMostrar.add(this.getMovWarning().getAlta());
		//			B - BAJA
		listaValoresMostrar.add(this.getMovWarning().getBaja());
		//			M - MODIFICACION
		listaValoresMostrar.add(this.getMovWarning().getModificacion());
		//			C - CAMBIO SEGMENTO
		listaValoresMostrar.add(this.getMovWarning().getCambioSeg());
		//			D - CAMBIO SEGMENTO DIFERIDO
		listaValoresMostrar.add(this.getMovWarning().getCambioSegDif());
		//			E - BAJA EN DIFERIDO
		listaValoresMostrar.add(this.getMovWarning().getBajaDif());
		//			R - REACTIVACION DE CLIENTES
		listaValoresMostrar.add(this.getMovWarning().getReactivacion());

		
		return listaValoresMostrar;
	}
	public void setListaValoresMostrar(List listaValoresMostrar) {
		this.listaValoresMostrar = listaValoresMostrar;
	}
	public List getListaValoresPorCiento() {
		
		List listaValores = new ArrayList();
		listaValores.add(this.getMovTratado().getTotal());
		listaValores.add(this.getMovPendiente().getTotal());
		listaValores.add(this.getMovErrores().getTotal());
		
		Integer totales = QGInformeUtils.sumar(listaValores);
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(totales,totales));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getTotal(),totales));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getTotal(),totales));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getTotal(),totales));
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(this.getMovTratado().getTotal(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getAlta(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getBaja(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getModificacion(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getCambioSeg(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getCambioSegDif(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getBajaDif(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getReactivacion(),this.getMovTratado().getTotal()));
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(this.getMovPendiente().getTotal(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getAlta(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getBaja(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getModificacion(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getCambioSeg(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getCambioSegDif(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getBajaDif(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getReactivacion(),this.getMovPendiente().getTotal()));
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(this.getMovErrores().getTotal(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getAlta(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getBaja(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getModificacion(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getCambioSeg(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getCambioSegDif(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getBajaDif(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getReactivacion(),this.getMovErrores().getTotal()));
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(this.getMovWarning().getTotal(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getAlta(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getBaja(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getModificacion(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getCambioSeg(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getCambioSegDif(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getBajaDif(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getReactivacion(),this.getMovWarning().getTotal()));
		
		return listaValoresPorCiento;
	}
	public void setListaValoresPorCiento(List listaValoresPorCiento) {
		this.listaValoresPorCiento = listaValoresPorCiento;
	}
	
	

	

}
