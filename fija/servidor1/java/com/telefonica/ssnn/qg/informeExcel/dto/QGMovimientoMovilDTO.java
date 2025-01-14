package com.telefonica.ssnn.qg.informeExcel.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.telefonica.ssnn.qg.informeExcel.dto.base.QGMovimientoMovilBaseDTO;
import com.telefonica.ssnn.qg.util.QGInformeUtils;


public class QGMovimientoMovilDTO {

	/**
	 * Indica si el movimiento de totales (columna que almacena todos los totales)
	 */
	private boolean total = false;
	
	private QGMovimientoMovilBaseDTO movTratado;
	private QGMovimientoMovilBaseDTO movPendiente;
	private QGMovimientoMovilBaseDTO movWarning;
	private QGMovimientoMovilBaseDTO movErrores;
	
	/**
	 * Es una lista que muestra en el orden de las tablas los valores que se van a mostrar
	 */
	private List listaValoresMostrar;
	/**
	 * Lista para los tantos por ciento
	 */
	private List listaValoresPorCiento;

	
	public QGMovimientoMovilDTO() {
		movTratado = new QGMovimientoMovilBaseDTO();
		movPendiente = new QGMovimientoMovilBaseDTO();
		movWarning = new QGMovimientoMovilBaseDTO();
		movErrores = new QGMovimientoMovilBaseDTO();
		
		listaValoresMostrar = new ArrayList();
		listaValoresPorCiento = new ArrayList();
	}
	
	/**
	 * Dada la información en infoDay creamos el objeto QGMovimientoMovilDTO
	 * @param infoDay
	 * @param dia 
	 * @param listErrores 
	 * @param listErroresQGCXXX 
	 */
	public void crearDia(List infoDay, Map listErrores, Map listErroresQGCXXX, int dia) {
		//int i = 21;
		int i = 25;		
		
		if(infoDay != null && infoDay.size() > 0){
		this.getMovTratado().setAltaDirecciones(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setAltaCliente(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setAltaPreCliente(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setBajaDirElectronicas(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setCancelacion(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setCambioEstado(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setModificacionDir(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setModificacionDatos(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setModificacionEstab(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setModificacionImpresion(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setMigracionPre(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setModificacionSegmentacion(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setAsignacionSegmentacion(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		
		this.getMovPendiente().setAltaDirecciones(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setAltaCliente(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setAltaPreCliente(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setBajaDirElectronicas(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setCancelacion(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setCambioEstado(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setModificacionDir(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setModificacionDatos(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setModificacionEstab(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setModificacionImpresion(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setMigracionPre(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setModificacionSegmentacion(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setAsignacionSegmentacion(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		
		//Inicializamos a 0 los valores de errores y warnings
		this.getMovErrores().setAltaDirecciones(new Integer(0));
		this.getMovErrores().setAltaCliente(new Integer(0));
		this.getMovErrores().setAltaPreCliente(new Integer(0));
		this.getMovErrores().setBajaDirElectronicas(new Integer(0));
		this.getMovErrores().setCancelacion(new Integer(0));
		this.getMovErrores().setCambioEstado(new Integer(0));
		this.getMovErrores().setModificacionDir(new Integer(0));
		this.getMovErrores().setModificacionDatos(new Integer(0));
		this.getMovErrores().setModificacionEstab(new Integer(0));
		this.getMovErrores().setModificacionImpresion(new Integer(0));
		this.getMovErrores().setMigracionPre(new Integer(0));
		this.getMovErrores().setModificacionSegmentacion(new Integer(0));
		this.getMovErrores().setAsignacionSegmentacion(new Integer(0));
		
		this.getMovWarning().setAltaDirecciones(new Integer(0));
		this.getMovWarning().setAltaCliente(new Integer(0));
		this.getMovWarning().setAltaPreCliente(new Integer(0));
		this.getMovWarning().setBajaDirElectronicas(new Integer(0));
		this.getMovWarning().setCancelacion(new Integer(0));
		this.getMovWarning().setCambioEstado(new Integer(0));
		this.getMovWarning().setModificacionDir(new Integer(0));
		this.getMovWarning().setModificacionDatos(new Integer(0));
		this.getMovWarning().setModificacionEstab(new Integer(0));
		this.getMovWarning().setModificacionImpresion(new Integer(0));
		this.getMovWarning().setMigracionPre(new Integer(0));
		this.getMovWarning().setModificacionSegmentacion(new Integer(0));
		this.getMovWarning().setAsignacionSegmentacion(new Integer(0));
		
		List erroresMoviles = QGInformeUtils.obtenerErroresMovil(listErrores);
		
		//Tratamos los errores para ir añadiendolos
		if(erroresMoviles != null && erroresMoviles.size() > 0){
			Iterator ite = erroresMoviles.iterator();
			
			while(ite.hasNext()){
				QGErrorDTO errorMovil = (QGErrorDTO)ite.next();
				
				
				//Errores y Warnings
				if(errorMovil.isTipoError()){
					if(errorMovil.isErrorAltaDirecciones()){
						this.getMovErrores().setAltaDirecciones(QGInformeUtils.sumar(this.getMovErrores().getAltaDirecciones(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorAltaCliente()){
						this.getMovErrores().setAltaCliente(QGInformeUtils.sumar(this.getMovErrores().getAltaCliente(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorAltaPreCliente()){
						this.getMovErrores().setAltaPreCliente(QGInformeUtils.sumar(this.getMovErrores().getAltaPreCliente(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorBajaDirecciones()){
						this.getMovErrores().setBajaDirElectronicas(QGInformeUtils.sumar(this.getMovErrores().getAltaDirecciones(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorCancelacion()){
						this.getMovErrores().setCancelacion(QGInformeUtils.sumar(this.getMovErrores().getCancelacion(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorCambioEstado()){
						this.getMovErrores().setCambioEstado(QGInformeUtils.sumar(this.getMovErrores().getCambioEstado(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorModificacionDir()){
						this.getMovErrores().setModificacionDir(QGInformeUtils.sumar(this.getMovErrores().getModificacionDir(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorModificacionDatos()){
						this.getMovErrores().setModificacionDatos(QGInformeUtils.sumar(this.getMovErrores().getModificacionDatos(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorModificacionEstablecimiento()){
						this.getMovErrores().setModificacionEstab(QGInformeUtils.sumar(this.getMovErrores().getModificacionEstab(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorModificacionImpresion()){
						this.getMovErrores().setModificacionImpresion(QGInformeUtils.sumar(this.getMovErrores().getModificacionImpresion(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorMigracion()){
						this.getMovErrores().setMigracionPre(QGInformeUtils.sumar(this.getMovErrores().getMigracionPre(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorModificacionSegmentacion()){
						this.getMovErrores().setModificacionSegmentacion(QGInformeUtils.sumar(this.getMovErrores().getModificacionSegmentacion(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorAsignacionSegmentacion()){
						this.getMovErrores().setAsignacionSegmentacion(QGInformeUtils.sumar(this.getMovErrores().getAsignacionSegmentacion(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}
					
				}else if(errorMovil.isTipoWarning()){
					if(errorMovil.isErrorAltaDirecciones()){
						this.getMovWarning().setAltaDirecciones(QGInformeUtils.sumar(this.getMovWarning().getAltaDirecciones(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorAltaCliente()){
						this.getMovWarning().setAltaCliente(QGInformeUtils.sumar(this.getMovWarning().getAltaCliente(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorAltaPreCliente()){
						this.getMovWarning().setAltaPreCliente(QGInformeUtils.sumar(this.getMovWarning().getAltaPreCliente(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorBajaDirecciones()){
						this.getMovWarning().setBajaDirElectronicas(QGInformeUtils.sumar(this.getMovWarning().getAltaDirecciones(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorCancelacion()){
						this.getMovWarning().setCancelacion(QGInformeUtils.sumar(this.getMovWarning().getCancelacion(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorCambioEstado()){
						this.getMovWarning().setCambioEstado(QGInformeUtils.sumar(this.getMovWarning().getCambioEstado(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorModificacionDir()){
						this.getMovWarning().setModificacionDir(QGInformeUtils.sumar(this.getMovWarning().getModificacionDir(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorModificacionDatos()){
						this.getMovWarning().setModificacionDatos(QGInformeUtils.sumar(this.getMovWarning().getModificacionDatos(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorModificacionEstablecimiento()){
						this.getMovWarning().setModificacionEstab(QGInformeUtils.sumar(this.getMovWarning().getModificacionEstab(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorModificacionImpresion()){
						this.getMovWarning().setModificacionImpresion(QGInformeUtils.sumar(this.getMovWarning().getModificacionImpresion(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorMigracion()){
						this.getMovWarning().setMigracionPre(QGInformeUtils.sumar(this.getMovWarning().getMigracionPre(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorModificacionSegmentacion()){
						this.getMovWarning().setModificacionSegmentacion(QGInformeUtils.sumar(this.getMovWarning().getModificacionSegmentacion(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}else if(errorMovil.isErrorAsignacionSegmentacion()){
						this.getMovWarning().setAsignacionSegmentacion(QGInformeUtils.sumar(this.getMovWarning().getAsignacionSegmentacion(), (Integer)errorMovil.getValoresPorFecha().get(dia)));
					}
				}
				
			}
		}

		List erroresQGCXXX = QGInformeUtils.obtenerErroresMovil(listErroresQGCXXX);
		
		//Tratamos los QGCXXX
		if(erroresQGCXXX != null && erroresQGCXXX.size()> 0){
			
			Iterator ite = erroresQGCXXX.iterator();
			
			while(ite.hasNext()){
				
				QGErrorDTO errorQGCXXX = (QGErrorDTO)ite.next();
				
				// Error QGCXXX
				/*
				 * En la hoja excel del ejemplo se especifica que para 
				 * las pestañas de fija, móvil y reinyección al valor de 
				 * tratados hay que añadirle los errores QGCXXX para cada tipo
				 */
				if(errorQGCXXX.isErrorAltaDirecciones()){
					this.getMovTratado().setAltaDirecciones(QGInformeUtils.sumar(this.getMovTratado().getAltaDirecciones(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
				}else if(errorQGCXXX.isErrorAltaCliente()){
					this.getMovTratado().setAltaCliente(QGInformeUtils.sumar(this.getMovTratado().getAltaCliente(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
				}else if(errorQGCXXX.isErrorAltaPreCliente()){
					this.getMovTratado().setAltaPreCliente(QGInformeUtils.sumar(this.getMovTratado().getAltaPreCliente(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
				}else if(errorQGCXXX.isErrorBajaDirecciones()){
					this.getMovTratado().setBajaDirElectronicas(QGInformeUtils.sumar(this.getMovTratado().getAltaDirecciones(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
				}else if(errorQGCXXX.isErrorCancelacion()){
					this.getMovTratado().setCancelacion(QGInformeUtils.sumar(this.getMovTratado().getCancelacion(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
				}else if(errorQGCXXX.isErrorCambioEstado()){
					this.getMovTratado().setCambioEstado(QGInformeUtils.sumar(this.getMovTratado().getCambioEstado(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
				}else if(errorQGCXXX.isErrorModificacionDir()){
					this.getMovTratado().setModificacionDir(QGInformeUtils.sumar(this.getMovTratado().getModificacionDir(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
				}else if(errorQGCXXX.isErrorModificacionDatos()){
					this.getMovTratado().setModificacionDatos(QGInformeUtils.sumar(this.getMovTratado().getModificacionDatos(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
				}else if(errorQGCXXX.isErrorModificacionEstablecimiento()){
					this.getMovTratado().setModificacionEstab(QGInformeUtils.sumar(this.getMovTratado().getModificacionEstab(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
				}else if(errorQGCXXX.isErrorModificacionImpresion()){
					this.getMovTratado().setModificacionImpresion(QGInformeUtils.sumar(this.getMovTratado().getModificacionImpresion(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
				}else if(errorQGCXXX.isErrorMigracion()){
					this.getMovTratado().setMigracionPre(QGInformeUtils.sumar(this.getMovTratado().getMigracionPre(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
				}else if(errorQGCXXX.isErrorModificacionSegmentacion()){
					this.getMovTratado().setModificacionSegmentacion(QGInformeUtils.sumar(this.getMovTratado().getModificacionSegmentacion(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
				}else if(errorQGCXXX.isErrorAsignacionSegmentacion()){
					this.getMovTratado().setAsignacionSegmentacion(QGInformeUtils.sumar(this.getMovTratado().getAsignacionSegmentacion(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
				}
				
			}
		}
		}
	}

	/**
	 * Metodo para el movimiento movil que engloba la suma de los movimientos fihos,
	 * en dicha funcion se ira sumando la informacion y calculando los valores conforme se vayan añadiendo datos
	 * @param diaNuevo
	 */
	public void sumarDiaMovimientoTotal(QGMovimientoMovilDTO diaNuevo) {
		
		this.getMovTratado().setAltaDirecciones(QGInformeUtils.sumar(this.getMovTratado().getAltaDirecciones(),diaNuevo.getMovTratado().getAltaDirecciones()));
		this.getMovTratado().setAltaCliente(QGInformeUtils.sumar(this.getMovTratado().getAltaCliente(),diaNuevo.getMovTratado().getAltaCliente()));
		this.getMovTratado().setAltaPreCliente(QGInformeUtils.sumar(this.getMovTratado().getAltaPreCliente(),diaNuevo.getMovTratado().getAltaPreCliente()));
		this.getMovTratado().setBajaDirElectronicas(QGInformeUtils.sumar(this.getMovTratado().getBajaDirElectronicas(),diaNuevo.getMovTratado().getBajaDirElectronicas()));
		this.getMovTratado().setCancelacion(QGInformeUtils.sumar(this.getMovTratado().getCancelacion(),diaNuevo.getMovTratado().getCancelacion()));
		this.getMovTratado().setCambioEstado(QGInformeUtils.sumar(this.getMovTratado().getCambioEstado(),diaNuevo.getMovTratado().getCambioEstado()));
		this.getMovTratado().setModificacionDir(QGInformeUtils.sumar(this.getMovTratado().getModificacionDir(),diaNuevo.getMovTratado().getModificacionDir()));
		this.getMovTratado().setModificacionDatos(QGInformeUtils.sumar(this.getMovTratado().getModificacionDatos(),diaNuevo.getMovTratado().getModificacionDatos()));
		this.getMovTratado().setModificacionEstab(QGInformeUtils.sumar(this.getMovTratado().getModificacionEstab(),diaNuevo.getMovTratado().getModificacionEstab()));
		this.getMovTratado().setModificacionImpresion(QGInformeUtils.sumar(this.getMovTratado().getModificacionImpresion(),diaNuevo.getMovTratado().getModificacionImpresion()));
		this.getMovTratado().setMigracionPre(QGInformeUtils.sumar(this.getMovTratado().getMigracionPre(),diaNuevo.getMovTratado().getMigracionPre()));
		this.getMovTratado().setModificacionSegmentacion(QGInformeUtils.sumar(this.getMovTratado().getModificacionSegmentacion(),diaNuevo.getMovTratado().getModificacionSegmentacion()));
		this.getMovTratado().setAsignacionSegmentacion(QGInformeUtils.sumar(this.getMovTratado().getAsignacionSegmentacion(),diaNuevo.getMovTratado().getAsignacionSegmentacion()));
		
		this.getMovPendiente().setAltaDirecciones(QGInformeUtils.sumar(this.getMovPendiente().getAltaDirecciones(),diaNuevo.getMovPendiente().getAltaDirecciones()));
		this.getMovPendiente().setAltaCliente(QGInformeUtils.sumar(this.getMovPendiente().getAltaCliente(),diaNuevo.getMovPendiente().getAltaCliente()));
		this.getMovPendiente().setAltaPreCliente(QGInformeUtils.sumar(this.getMovPendiente().getAltaPreCliente(),diaNuevo.getMovPendiente().getAltaPreCliente()));
		this.getMovPendiente().setBajaDirElectronicas(QGInformeUtils.sumar(this.getMovPendiente().getBajaDirElectronicas(),diaNuevo.getMovPendiente().getBajaDirElectronicas()));
		this.getMovPendiente().setCancelacion(QGInformeUtils.sumar(this.getMovPendiente().getCancelacion(),diaNuevo.getMovPendiente().getCancelacion()));
		this.getMovPendiente().setCambioEstado(QGInformeUtils.sumar(this.getMovPendiente().getCambioEstado(),diaNuevo.getMovPendiente().getCambioEstado()));
		this.getMovPendiente().setModificacionDir(QGInformeUtils.sumar(this.getMovPendiente().getModificacionDir(),diaNuevo.getMovPendiente().getModificacionDir()));
		this.getMovPendiente().setModificacionDatos(QGInformeUtils.sumar(this.getMovPendiente().getModificacionDatos(),diaNuevo.getMovPendiente().getModificacionDatos()));
		this.getMovPendiente().setModificacionEstab(QGInformeUtils.sumar(this.getMovPendiente().getModificacionEstab(),diaNuevo.getMovPendiente().getModificacionEstab()));
		this.getMovPendiente().setModificacionImpresion(QGInformeUtils.sumar(this.getMovPendiente().getModificacionImpresion(),diaNuevo.getMovPendiente().getModificacionImpresion()));
		this.getMovPendiente().setMigracionPre(QGInformeUtils.sumar(this.getMovPendiente().getMigracionPre(),diaNuevo.getMovPendiente().getMigracionPre()));
		this.getMovPendiente().setModificacionSegmentacion(QGInformeUtils.sumar(this.getMovPendiente().getModificacionSegmentacion(),diaNuevo.getMovPendiente().getModificacionSegmentacion()));
		this.getMovPendiente().setAsignacionSegmentacion(QGInformeUtils.sumar(this.getMovPendiente().getAsignacionSegmentacion(),diaNuevo.getMovPendiente().getAsignacionSegmentacion()));
		
		this.getMovErrores().setAltaDirecciones(QGInformeUtils.sumar(this.getMovErrores().getAltaDirecciones(),diaNuevo.getMovErrores().getAltaDirecciones()));
		this.getMovErrores().setAltaCliente(QGInformeUtils.sumar(this.getMovErrores().getAltaCliente(),diaNuevo.getMovErrores().getAltaCliente()));
		this.getMovErrores().setAltaPreCliente(QGInformeUtils.sumar(this.getMovErrores().getAltaPreCliente(),diaNuevo.getMovErrores().getAltaPreCliente()));
		this.getMovErrores().setBajaDirElectronicas(QGInformeUtils.sumar(this.getMovErrores().getBajaDirElectronicas(),diaNuevo.getMovErrores().getBajaDirElectronicas()));
		this.getMovErrores().setCancelacion(QGInformeUtils.sumar(this.getMovErrores().getCancelacion(),diaNuevo.getMovErrores().getCancelacion()));
		this.getMovErrores().setCambioEstado(QGInformeUtils.sumar(this.getMovErrores().getCambioEstado(),diaNuevo.getMovErrores().getCambioEstado()));
		this.getMovErrores().setModificacionDir(QGInformeUtils.sumar(this.getMovErrores().getModificacionDir(),diaNuevo.getMovErrores().getModificacionDir()));
		this.getMovErrores().setModificacionDatos(QGInformeUtils.sumar(this.getMovErrores().getModificacionDatos(),diaNuevo.getMovErrores().getModificacionDatos()));
		this.getMovErrores().setModificacionEstab(QGInformeUtils.sumar(this.getMovErrores().getModificacionEstab(),diaNuevo.getMovErrores().getModificacionEstab()));
		this.getMovErrores().setModificacionImpresion(QGInformeUtils.sumar(this.getMovErrores().getModificacionImpresion(),diaNuevo.getMovErrores().getModificacionImpresion()));
		this.getMovErrores().setMigracionPre(QGInformeUtils.sumar(this.getMovErrores().getMigracionPre(),diaNuevo.getMovErrores().getMigracionPre()));
		this.getMovErrores().setModificacionSegmentacion(QGInformeUtils.sumar(this.getMovErrores().getModificacionSegmentacion(),diaNuevo.getMovErrores().getModificacionSegmentacion()));
		this.getMovErrores().setAsignacionSegmentacion(QGInformeUtils.sumar(this.getMovErrores().getAsignacionSegmentacion(),diaNuevo.getMovErrores().getAsignacionSegmentacion()));
		
		this.getMovWarning().setAltaDirecciones(QGInformeUtils.sumar(this.getMovWarning().getAltaDirecciones(),diaNuevo.getMovWarning().getAltaDirecciones()));
		this.getMovWarning().setAltaCliente(QGInformeUtils.sumar(this.getMovWarning().getAltaCliente(),diaNuevo.getMovWarning().getAltaCliente()));
		this.getMovWarning().setAltaPreCliente(QGInformeUtils.sumar(this.getMovWarning().getAltaPreCliente(),diaNuevo.getMovWarning().getAltaPreCliente()));
		this.getMovWarning().setBajaDirElectronicas(QGInformeUtils.sumar(this.getMovWarning().getBajaDirElectronicas(),diaNuevo.getMovWarning().getBajaDirElectronicas()));
		this.getMovWarning().setCancelacion(QGInformeUtils.sumar(this.getMovWarning().getCancelacion(),diaNuevo.getMovWarning().getCancelacion()));
		this.getMovWarning().setCambioEstado(QGInformeUtils.sumar(this.getMovWarning().getCambioEstado(),diaNuevo.getMovWarning().getCambioEstado()));
		this.getMovWarning().setModificacionDir(QGInformeUtils.sumar(this.getMovWarning().getModificacionDir(),diaNuevo.getMovWarning().getModificacionDir()));
		this.getMovWarning().setModificacionDatos(QGInformeUtils.sumar(this.getMovWarning().getModificacionDatos(),diaNuevo.getMovWarning().getModificacionDatos()));
		this.getMovWarning().setModificacionEstab(QGInformeUtils.sumar(this.getMovWarning().getModificacionEstab(),diaNuevo.getMovWarning().getModificacionEstab()));
		this.getMovWarning().setModificacionImpresion(QGInformeUtils.sumar(this.getMovWarning().getModificacionImpresion(),diaNuevo.getMovWarning().getModificacionImpresion()));
		this.getMovWarning().setMigracionPre(QGInformeUtils.sumar(this.getMovWarning().getMigracionPre(),diaNuevo.getMovWarning().getMigracionPre()));
		this.getMovWarning().setModificacionSegmentacion(QGInformeUtils.sumar(this.getMovWarning().getModificacionSegmentacion(),diaNuevo.getMovWarning().getModificacionSegmentacion()));
		this.getMovWarning().setAsignacionSegmentacion(QGInformeUtils.sumar(this.getMovWarning().getAsignacionSegmentacion(),diaNuevo.getMovWarning().getAsignacionSegmentacion()));
		
	}

	public QGMovimientoMovilBaseDTO getMovTratado() {
		return movTratado;
	}

	public void setMovTratado(QGMovimientoMovilBaseDTO movTratado) {
		this.movTratado = movTratado;
	}

	public QGMovimientoMovilBaseDTO getMovPendiente() {
		return movPendiente;
	}

	public void setMovPendiente(QGMovimientoMovilBaseDTO movPendiente) {
		this.movPendiente = movPendiente;
	}

	public QGMovimientoMovilBaseDTO getMovWarning() {
		return movWarning;
	}

	public void setMovWarning(QGMovimientoMovilBaseDTO movWarning) {
		this.movWarning = movWarning;
	}

	public QGMovimientoMovilBaseDTO getMovErrores() {
		return movErrores;
	}

	public void setMovErrores(QGMovimientoMovilBaseDTO movErrores) {
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
		
			// MOVIMIENTOS MOVIL
		listaValoresMostrar.add(totales);
		// TRATADOS
		listaValoresMostrar.add(this.getMovTratado().getTotal());
		// PENDIENTES
		listaValoresMostrar.add(this.getMovPendiente().getTotal());
		// ERRORES
		listaValoresMostrar.add(this.getMovErrores().getTotal());
		//
		// TRATADOS
		listaValoresMostrar.add(this.getMovTratado().getTotal());
		// A - ALTAS DIRECCIONES
		listaValoresMostrar.add(this.getMovTratado().getAltaDirecciones());
		// AC - ALTA CLIENTE
		listaValoresMostrar.add(this.getMovTratado().getAltaCliente());
		// AP- ALTA PRECLIENTE
		listaValoresMostrar.add(this.getMovTratado().getAltaPreCliente());
		// B - BAJAS DIRECCIONES ELECTRONICAS
		listaValoresMostrar.add(this.getMovTratado().getBajaDirElectronicas());
		// CC - CANCELACION
		listaValoresMostrar.add(this.getMovTratado().getCancelacion());
		// CE - CAMBIO DE ESTADO
		listaValoresMostrar.add(this.getMovTratado().getCambioEstado());
		// M - MODIFICACION DIRECCIONES
		listaValoresMostrar.add(this.getMovTratado().getModificacionDir());
		// MC - MODIFICACIÓN DE DATOS VARIOS DEL CLIENTE
		listaValoresMostrar.add(this.getMovTratado().getModificacionDatos());
		// ME - MODIFICACION ESTABLECIMIENTO
		listaValoresMostrar.add(this.getMovTratado().getModificacionEstab());
		// MI - MODIFICACION IMPRESIÓN FACTURA
		listaValoresMostrar.add(this.getMovTratado().getModificacionImpresion());
		// MP - MIGRACION DE PRECLIENTE A CLIENTE
		listaValoresMostrar.add(this.getMovTratado().getMigracionPre());
		// MS - MODIFICACION SEGMENTACION
		listaValoresMostrar.add(this.getMovTratado().getModificacionSegmentacion());
		// NS - ASIGNACION SEGMENTACION
		listaValoresMostrar.add(this.getMovTratado().getAsignacionSegmentacion());
			//
			//        PENDIENTES
		listaValoresMostrar.add(this.getMovPendiente().getTotal());
		// A - ALTAS DIRECCIONES
		listaValoresMostrar.add(this.getMovPendiente().getAltaDirecciones());
		// AC - ALTA CLIENTE
		listaValoresMostrar.add(this.getMovPendiente().getAltaCliente());
		// AP- ALTA PRECLIENTE
		listaValoresMostrar.add(this.getMovPendiente().getAltaPreCliente());
		// B - BAJAS DIRECCIONES ELECTRONICAS
		listaValoresMostrar.add(this.getMovPendiente().getBajaDirElectronicas());
		// CC - CANCELACION
		listaValoresMostrar.add(this.getMovPendiente().getCancelacion());
		// CE - CAMBIO DE ESTADO
		listaValoresMostrar.add(this.getMovPendiente().getCambioEstado());
		// M - MODIFICACION DIRECCIONES
		listaValoresMostrar.add(this.getMovPendiente().getModificacionDir());
		// MC - MODIFICACIÓN DE DATOS VARIOS DEL CLIENTE
		listaValoresMostrar.add(this.getMovPendiente().getModificacionDatos());
		// ME - MODIFICACION ESTABLECIMIENTO
		listaValoresMostrar.add(this.getMovPendiente().getModificacionEstab());
		// MI - MODIFICACION IMPRESIÓN FACTURA
		listaValoresMostrar.add(this.getMovPendiente().getModificacionImpresion());
		// MP - MIGRACION DE PRECLIENTE A CLIENTE
		listaValoresMostrar.add(this.getMovPendiente().getMigracionPre());
		// MS - MODIFICACION SEGMENTACION
		listaValoresMostrar.add(this.getMovPendiente().getModificacionSegmentacion());
		// NS - ASIGNACION SEGMENTACION
		listaValoresMostrar.add(this.getMovPendiente().getAsignacionSegmentacion());
			//
			//
			//        ERRORES
		listaValoresMostrar.add(this.getMovErrores().getTotal());
		// A - ALTAS DIRECCIONES
		listaValoresMostrar.add(this.getMovErrores().getAltaDirecciones());
		// AC - ALTA CLIENTE
		listaValoresMostrar.add(this.getMovErrores().getAltaCliente());
		// AP- ALTA PRECLIENTE
		listaValoresMostrar.add(this.getMovErrores().getAltaPreCliente());
		// B - BAJAS DIRECCIONES ELECTRONICAS
		listaValoresMostrar.add(this.getMovErrores().getBajaDirElectronicas());
		// CC - CANCELACION
		listaValoresMostrar.add(this.getMovErrores().getCancelacion());
		// CE - CAMBIO DE ESTADO
		listaValoresMostrar.add(this.getMovErrores().getCambioEstado());
		// M - MODIFICACION DIRECCIONES
		listaValoresMostrar.add(this.getMovErrores().getModificacionDir());
		// MC - MODIFICACIÓN DE DATOS VARIOS DEL CLIENTE
		listaValoresMostrar.add(this.getMovErrores().getModificacionDatos());
		// ME - MODIFICACION ESTABLECIMIENTO
		listaValoresMostrar.add(this.getMovErrores().getModificacionEstab());
		// MI - MODIFICACION IMPRESIÓN FACTURA
		listaValoresMostrar.add(this.getMovErrores().getModificacionImpresion());
		// MP - MIGRACION DE PRECLIENTE A CLIENTE
		listaValoresMostrar.add(this.getMovErrores().getMigracionPre());
		// MS - MODIFICACION SEGMENTACION
		listaValoresMostrar.add(this.getMovErrores().getModificacionSegmentacion());
		// NS - ASIGNACION SEGMENTACION
		listaValoresMostrar.add(this.getMovErrores().getAsignacionSegmentacion());
		
		//        ERRORES INFORMATIVOS
		listaValoresMostrar.add(this.getMovWarning().getTotal());
		// A - ALTAS DIRECCIONES
		listaValoresMostrar.add(this.getMovWarning().getAltaDirecciones());
		// AC - ALTA CLIENTE
		listaValoresMostrar.add(this.getMovWarning().getAltaCliente());
		// AP- ALTA PRECLIENTE
		listaValoresMostrar.add(this.getMovWarning().getAltaPreCliente());
		// B - BAJAS DIRECCIONES ELECTRONICAS
		listaValoresMostrar.add(this.getMovWarning().getBajaDirElectronicas());
		// CC - CANCELACION
		listaValoresMostrar.add(this.getMovWarning().getCancelacion());
		// CE - CAMBIO DE ESTADO
		listaValoresMostrar.add(this.getMovWarning().getCambioEstado());
		// M - MODIFICACION DIRECCIONES
		listaValoresMostrar.add(this.getMovWarning().getModificacionDir());
		// MC - MODIFICACIÓN DE DATOS VARIOS DEL CLIENTE
		listaValoresMostrar.add(this.getMovWarning().getModificacionDatos());
		// ME - MODIFICACION ESTABLECIMIENTO
		listaValoresMostrar.add(this.getMovWarning().getModificacionEstab());
		// MI - MODIFICACION IMPRESIÓN FACTURA
		listaValoresMostrar.add(this.getMovWarning().getModificacionImpresion());
		// MP - MIGRACION DE PRECLIENTE A CLIENTE
		listaValoresMostrar.add(this.getMovWarning().getMigracionPre());
		// MS - MODIFICACION SEGMENTACION
		listaValoresMostrar.add(this.getMovWarning().getModificacionSegmentacion());
		// NS - ASIGNACION SEGMENTACION
		listaValoresMostrar.add(this.getMovWarning().getAsignacionSegmentacion());
		
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
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getAltaDirecciones(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getAltaCliente(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getAltaPreCliente(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getBajaDirElectronicas(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getCancelacion(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getCambioEstado(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getModificacionDir(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getModificacionDatos(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getModificacionEstab(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getModificacionImpresion(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getMigracionPre(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getModificacionSegmentacion(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getAsignacionSegmentacion(),this.getMovTratado().getTotal()));
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(this.getMovPendiente().getTotal(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getAltaDirecciones(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getAltaCliente(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getAltaPreCliente(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getBajaDirElectronicas(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getCancelacion(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getCambioEstado(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getModificacionDir(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getModificacionDatos(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getModificacionEstab(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getModificacionImpresion(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getMigracionPre(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getModificacionSegmentacion(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getAsignacionSegmentacion(),this.getMovPendiente().getTotal()));
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(this.getMovErrores().getTotal(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getAltaDirecciones(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getAltaCliente(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getAltaPreCliente(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getBajaDirElectronicas(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getCancelacion(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getCambioEstado(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getModificacionDir(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getModificacionDatos(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getModificacionEstab(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getModificacionImpresion(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getMigracionPre(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getModificacionSegmentacion(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getAsignacionSegmentacion(),this.getMovErrores().getTotal()));
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(this.getMovWarning().getTotal(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getAltaDirecciones(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getAltaCliente(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getAltaPreCliente(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getBajaDirElectronicas(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getCancelacion(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getCambioEstado(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getModificacionDir(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getModificacionDatos(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getModificacionEstab(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getModificacionImpresion(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getMigracionPre(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getModificacionSegmentacion(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getAsignacionSegmentacion(),this.getMovWarning().getTotal()));
		
		return listaValoresPorCiento;
	}

	public void setListaValoresPorCiento(List listaValoresPorCiento) {
		this.listaValoresPorCiento = listaValoresPorCiento;
	}

}
