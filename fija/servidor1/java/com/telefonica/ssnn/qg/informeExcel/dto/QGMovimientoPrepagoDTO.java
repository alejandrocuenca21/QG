package com.telefonica.ssnn.qg.informeExcel.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.telefonica.ssnn.qg.informeExcel.dto.base.QGMovimientoPrepagoBaseDTO;
import com.telefonica.ssnn.qg.util.QGInformeUtils;


public class QGMovimientoPrepagoDTO {

	/**
	 * Indica si el movimiento de totales (columna que almacena todos los totales)
	 */
	private boolean total = false;
	
	private QGMovimientoPrepagoBaseDTO movTratado;
	private QGMovimientoPrepagoBaseDTO movPendiente;
	private QGMovimientoPrepagoBaseDTO movWarning;
	private QGMovimientoPrepagoBaseDTO movErrores;
	
	/**
	 * Es una lista que muestra en el orden de las tablas los valores que se van a mostrar
	 */
	private List listaValoresMostrar;
	/**
	 * Lista para los tantos por ciento
	 */
	private List listaValoresPorCiento;

	
	public QGMovimientoPrepagoDTO() {
		movTratado = new QGMovimientoPrepagoBaseDTO();
		movPendiente = new QGMovimientoPrepagoBaseDTO();
		movWarning = new QGMovimientoPrepagoBaseDTO();
		movErrores = new QGMovimientoPrepagoBaseDTO();
		
		listaValoresMostrar = new ArrayList();
		listaValoresPorCiento = new ArrayList();
	}
	
	/**
	 * Dada la información en infoDay creamos el objeto QGMovimientoPrepagoDTO
	 * @param infoDay
	 * @param dia 
	 * @param listErrores 
	 * @param listErroresQGCXXX 
	 */
	public void crearDia(List infoDay, Map listErrores, Map listErroresQGCXXX, int dia) 
	{
		int i = 51;
		
		if(infoDay != null && infoDay.size() > 0){
		this.getMovTratado().setAltaPrepago(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setBajaPrepago(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovTratado().setModificacionPrepago(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		
		this.getMovPendiente().setAltaPrepago(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setBajaPrepago(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		this.getMovPendiente().setModificacionPrepago(QGInformeUtils.toInteger((String)infoDay.get(i++)));
		
		//Inicializamos a 0 los valores de errores y warnings
		this.getMovErrores().setAltaPrepago(new Integer(0));
		this.getMovErrores().setBajaPrepago(new Integer(0));
		this.getMovErrores().setModificacionPrepago(new Integer(0));
		
		this.getMovWarning().setAltaPrepago(new Integer(0));
		this.getMovWarning().setBajaPrepago(new Integer(0));
		this.getMovWarning().setModificacionPrepago(new Integer(0));
		
		List erroresPrepagos = QGInformeUtils.obtenerErroresPrepago(listErrores);
		
		//Tratamos los errores para ir añadiendolos
		if(erroresPrepagos != null && erroresPrepagos.size() > 0)
		{
			Iterator ite = erroresPrepagos.iterator();
			
			while(ite.hasNext())
			{
				QGErrorDTO errorPrepago = (QGErrorDTO)ite.next();				
				
				//Errores y Warnings
				if(errorPrepago.isTipoError())
				{
					if(errorPrepago.isErrorAltaPrepago())
					{
						this.getMovErrores().setAltaPrepago(QGInformeUtils.sumar(this.getMovErrores().getAltaPrepago(), (Integer)errorPrepago.getValoresPorFecha().get(dia)));
					}
					else if(errorPrepago.isErrorBajaPrepago())
					{
						this.getMovErrores().setBajaPrepago(QGInformeUtils.sumar(this.getMovErrores().getBajaPrepago(), (Integer)errorPrepago.getValoresPorFecha().get(dia)));
					}
					else if(errorPrepago.isErrorModificacionPrepago())
					{
						this.getMovErrores().setModificacionPrepago(QGInformeUtils.sumar(this.getMovErrores().getModificacionPrepago(), (Integer)errorPrepago.getValoresPorFecha().get(dia)));
					}
				}
				else if(errorPrepago.isTipoWarning())
				{
					if(errorPrepago.isErrorAltaPrepago())
					{
						this.getMovWarning().setAltaPrepago(QGInformeUtils.sumar(this.getMovWarning().getAltaPrepago(), (Integer)errorPrepago.getValoresPorFecha().get(dia)));
					}
					else if(errorPrepago.isErrorBajaPrepago())
					{
						this.getMovWarning().setBajaPrepago(QGInformeUtils.sumar(this.getMovWarning().getBajaPrepago(), (Integer)errorPrepago.getValoresPorFecha().get(dia)));
					}
					else if(errorPrepago.isErrorModificacionPrepago())
					{
						this.getMovWarning().setModificacionPrepago(QGInformeUtils.sumar(this.getMovWarning().getModificacionPrepago(), (Integer)errorPrepago.getValoresPorFecha().get(dia)));
					}
				}				
			}
		}

		List erroresQGCXXX = QGInformeUtils.obtenerErroresPrepago(listErroresQGCXXX);
		
		//Tratamos los QGCXXX
		if(erroresQGCXXX != null && erroresQGCXXX.size()> 0)
		{			
			Iterator ite = erroresQGCXXX.iterator();
			
			while(ite.hasNext())
			{				
				QGErrorDTO errorQGCXXX = (QGErrorDTO)ite.next();
				
				// Error QGCXXX
				/*
				 * En la hoja excel del ejemplo se especifica que para 
				 * las pestañas de fija, móvil, prepago y reinyección al valor de 
				 * tratados hay que añadirle los errores QGCXXX para cada tipo
				 */
				if(errorQGCXXX.isErrorAltaPrepago())
				{
					this.getMovTratado().setAltaPrepago(QGInformeUtils.sumar(this.getMovTratado().getAltaPrepago(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
				}
				else if(errorQGCXXX.isErrorBajaPrepago())
				{
					this.getMovTratado().setBajaPrepago(QGInformeUtils.sumar(this.getMovTratado().getBajaPrepago(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
				}
				else if(errorQGCXXX.isErrorModificacionPrepago())
				{
					this.getMovTratado().setModificacionPrepago(QGInformeUtils.sumar(this.getMovTratado().getModificacionPrepago(), (Integer)errorQGCXXX.getValoresPorFecha().get(0)));
				}
			}
		}
		}
	}

	/**
	 * Metodo para el movimiento Prepago que engloba la suma de los movimientos fihos,
	 * en dicha funcion se ira sumando la informacion y calculando los valores conforme se vayan añadiendo datos
	 * @param diaNuevo
	 */
	public void sumarDiaMovimientoTotal(QGMovimientoPrepagoDTO diaNuevo) {
		
		this.getMovTratado().setAltaPrepago(QGInformeUtils.sumar(this.getMovTratado().getAltaPrepago(),diaNuevo.getMovTratado().getAltaPrepago()));
		this.getMovTratado().setBajaPrepago(QGInformeUtils.sumar(this.getMovTratado().getBajaPrepago(),diaNuevo.getMovTratado().getBajaPrepago()));
		this.getMovTratado().setModificacionPrepago(QGInformeUtils.sumar(this.getMovTratado().getModificacionPrepago(),diaNuevo.getMovTratado().getModificacionPrepago()));
		
		this.getMovPendiente().setAltaPrepago(QGInformeUtils.sumar(this.getMovPendiente().getAltaPrepago(),diaNuevo.getMovPendiente().getAltaPrepago()));
		this.getMovPendiente().setBajaPrepago(QGInformeUtils.sumar(this.getMovPendiente().getBajaPrepago(),diaNuevo.getMovPendiente().getBajaPrepago()));
		this.getMovPendiente().setModificacionPrepago(QGInformeUtils.sumar(this.getMovPendiente().getModificacionPrepago(),diaNuevo.getMovPendiente().getModificacionPrepago()));
		
		this.getMovErrores().setAltaPrepago(QGInformeUtils.sumar(this.getMovErrores().getAltaPrepago(),diaNuevo.getMovErrores().getAltaPrepago()));
		this.getMovErrores().setBajaPrepago(QGInformeUtils.sumar(this.getMovErrores().getBajaPrepago(),diaNuevo.getMovErrores().getBajaPrepago()));
		this.getMovErrores().setModificacionPrepago(QGInformeUtils.sumar(this.getMovErrores().getModificacionPrepago(),diaNuevo.getMovErrores().getModificacionPrepago()));
		
		this.getMovWarning().setAltaPrepago(QGInformeUtils.sumar(this.getMovWarning().getAltaPrepago(),diaNuevo.getMovWarning().getAltaPrepago()));
		this.getMovWarning().setBajaPrepago(QGInformeUtils.sumar(this.getMovWarning().getBajaPrepago(),diaNuevo.getMovWarning().getBajaPrepago()));
		this.getMovWarning().setModificacionPrepago(QGInformeUtils.sumar(this.getMovWarning().getModificacionPrepago(),diaNuevo.getMovWarning().getModificacionPrepago()));		
	}

	public QGMovimientoPrepagoBaseDTO getMovTratado() {
		return movTratado;
	}

	public void setMovTratado(QGMovimientoPrepagoBaseDTO movTratado) {
		this.movTratado = movTratado;
	}

	public QGMovimientoPrepagoBaseDTO getMovPendiente() {
		return movPendiente;
	}

	public void setMovPendiente(QGMovimientoPrepagoBaseDTO movPendiente) {
		this.movPendiente = movPendiente;
	}

	public QGMovimientoPrepagoBaseDTO getMovWarning() {
		return movWarning;
	}

	public void setMovWarning(QGMovimientoPrepagoBaseDTO movWarning) {
		this.movWarning = movWarning;
	}

	public QGMovimientoPrepagoBaseDTO getMovErrores() {
		return movErrores;
	}

	public void setMovErrores(QGMovimientoPrepagoBaseDTO movErrores) {
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
		
			// MOVIMIENTOS Prepago
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
		// AG - ALTA CLIENTE
		listaValoresMostrar.add(this.getMovTratado().getAltaPrepago());
		// BG - BAJA CLIENTE
		listaValoresMostrar.add(this.getMovTratado().getBajaPrepago());
		// MG - MODIFICACIÓN DE DATOS VARIOS DEL CLIENTE
		listaValoresMostrar.add(this.getMovTratado().getModificacionPrepago());
			//
			//        PENDIENTES
		listaValoresMostrar.add(this.getMovPendiente().getTotal());
		// AG - ALTA CLIENTE
		listaValoresMostrar.add(this.getMovPendiente().getAltaPrepago());
		// BG - BAJA CLIENTE
		listaValoresMostrar.add(this.getMovPendiente().getBajaPrepago());
		// MG - MODIFICACIÓN DE DATOS VARIOS DEL CLIENTE
		listaValoresMostrar.add(this.getMovPendiente().getModificacionPrepago());
			//
			//
			//        ERRORES
		listaValoresMostrar.add(this.getMovErrores().getTotal());
		// AG - ALTA CLIENTE
		listaValoresMostrar.add(this.getMovErrores().getAltaPrepago());
		// BG - BAJA CLIENTE
		listaValoresMostrar.add(this.getMovErrores().getBajaPrepago());
		// MG - MODIFICACIÓN DE DATOS VARIOS DEL CLIENTE
		listaValoresMostrar.add(this.getMovErrores().getModificacionPrepago());
		
		//        ERRORES INFORMATIVOS
		listaValoresMostrar.add(this.getMovWarning().getTotal());
		// AG - ALTA CLIENTE
		listaValoresMostrar.add(this.getMovWarning().getAltaPrepago());
		// BG - BAJA CLIENTE
		listaValoresMostrar.add(this.getMovWarning().getBajaPrepago());
		// MG - MODIFICACIÓN DE DATOS VARIOS DEL CLIENTE
		listaValoresMostrar.add(this.getMovWarning().getModificacionPrepago());
		
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
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getAltaPrepago(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getBajaPrepago(),this.getMovTratado().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovTratado().getModificacionPrepago(),this.getMovTratado().getTotal()));
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(this.getMovPendiente().getTotal(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getAltaPrepago(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getBajaPrepago(),this.getMovPendiente().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovPendiente().getModificacionPrepago(),this.getMovPendiente().getTotal()));
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(this.getMovErrores().getTotal(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getAltaPrepago(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getBajaPrepago(),this.getMovErrores().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovErrores().getModificacionPrepago(),this.getMovErrores().getTotal()));
		
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCiento(this.getMovWarning().getTotal(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getAltaPrepago(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getBajaPrepago(),this.getMovWarning().getTotal()));
		listaValoresPorCiento.add(QGInformeUtils.tantoPorCientoBase0(this.getMovWarning().getModificacionPrepago(),this.getMovWarning().getTotal()));
		
		return listaValoresPorCiento;
	}

	public void setListaValoresPorCiento(List listaValoresPorCiento) {
		this.listaValoresPorCiento = listaValoresPorCiento;
	}

}
