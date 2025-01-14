package com.telefonica.ssnn.qg.informeExcel.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.telefonica.ssnn.qg.informeExcel.dto.base.QGReinyeccionBaseDTO;
import com.telefonica.ssnn.qg.util.QGInformeUtils;

public class QGReinyeccionDTO {
	/**
	 * Reinyeccion para datos fija
	 */
	private QGReinyeccionBaseDTO reinyeccionFija;
	/**
	 * Reinyeccion para datos movil
	 */
	private QGReinyeccionBaseDTO reinyeccionMovil;
	/**
	 * Reinyeccion para datos prepago
	 */
	private QGReinyeccionBaseDTO reinyeccionPrepago;	
	
	
	/**
	 * Es una lista que muestra en el orden de las tablas los valores que se van a mostrar
	 */
	private List listaValoresMostrarSumas;
	/**
	 * Lista para los tantos por ciento
	 */
	private List listaValoresPorCientoSumas;
	
	
	public QGReinyeccionDTO() {
		reinyeccionFija = new QGReinyeccionBaseDTO();
		reinyeccionMovil = new  QGReinyeccionBaseDTO();
		reinyeccionPrepago = new  QGReinyeccionBaseDTO();		
		
		listaValoresMostrarSumas = new ArrayList();
		listaValoresPorCientoSumas = new ArrayList();
		
	}

	public QGReinyeccionBaseDTO getReinyeccionFija() {
		return reinyeccionFija;
	}

	public void setReinyeccionFija(QGReinyeccionBaseDTO reinyeccionFija) {
		this.reinyeccionFija = reinyeccionFija;
	}

	public QGReinyeccionBaseDTO getReinyeccionMovil() {
		return reinyeccionMovil;
	}

	public void setReinyeccionMovil(QGReinyeccionBaseDTO reinyeccionMovil) {
		this.reinyeccionMovil = reinyeccionMovil;
	}
	
	public QGReinyeccionBaseDTO getReinyeccionPrepago() {
		return reinyeccionPrepago;
	}

	public void setReinyeccionPrepago(QGReinyeccionBaseDTO reinyeccionPrepago) {
		this.reinyeccionPrepago = reinyeccionPrepago;
	}	

	/**
	 * Crea la reinyeccion y ademas dice si ha habido
	 * @param infoDay
	 * @param listErrores 
	 * @param listErroresQGCXXX 
	 * @param countDay 
	 * @return
	 */
	public boolean crearReinyeccion(List infoDay, Map listErrores, Map listErroresQGCXXX, int countDay) {
		
		List erroresReinyeccion = QGInformeUtils.obtenerListadoErroresReinyeccion(listErrores);
		
		boolean hayReinyeccionFija = reinyeccionFija.crearReinyeccion(infoDay,"F",erroresReinyeccion,listErroresQGCXXX,countDay);
		boolean hayReinyeccionMovil = reinyeccionMovil.crearReinyeccion(infoDay,"M",erroresReinyeccion,listErroresQGCXXX,countDay);
		boolean hayReinyeccionPrepago = reinyeccionPrepago.crearReinyeccion(infoDay,"P",erroresReinyeccion,listErroresQGCXXX,countDay);		
		return (hayReinyeccionFija || hayReinyeccionMovil || hayReinyeccionPrepago);
	}

	public List getListaValoresPorCientoSumas() {
		
		Integer total = QGInformeUtils.sumar(this.getReinyeccionFija().getTotal(), this.getReinyeccionMovil().getTotal());
		total = QGInformeUtils.sumar(total, this.getReinyeccionPrepago().getTotal());
		Integer totalTratados = QGInformeUtils.sumar(this.getReinyeccionFija().getTotalTratados(), this.getReinyeccionMovil().getTotalTratados());
		totalTratados = QGInformeUtils.sumar(totalTratados, this.getReinyeccionPrepago().getTotalTratados());
		Integer totalPendientes = QGInformeUtils.sumar(this.getReinyeccionFija().getTotalPendientes(), this.getReinyeccionMovil().getTotalPendientes());
		totalPendientes = QGInformeUtils.sumar(totalPendientes, this.getReinyeccionPrepago().getTotalPendientes());
		Integer totalErrores = QGInformeUtils.sumar(this.getReinyeccionFija().getTotalErrores(), this.getReinyeccionMovil().getTotalErrores());
		totalErrores = QGInformeUtils.sumar(totalErrores, this.getReinyeccionPrepago().getTotalErrores());
		Integer totalWarnings = QGInformeUtils.sumar(this.getReinyeccionFija().getTotalWarnings(), this.getReinyeccionMovil().getTotalWarnings());
		totalWarnings = QGInformeUtils.sumar(totalWarnings, this.getReinyeccionPrepago().getTotalWarnings());
			
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCiento(total, total));
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCientoBase0(totalTratados, total));
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCientoBase0(totalPendientes, total));
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCientoBase0(totalErrores, total));
		
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCiento(totalTratados, totalTratados));
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getAltaTratados(), this.getReinyeccionMovil().getAltaTratados())),this.getReinyeccionPrepago().getAltaTratados()), totalTratados));
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getModTratados(), this.getReinyeccionMovil().getModTratados())),this.getReinyeccionPrepago().getModTratados()), totalTratados));
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getBajaTratados(), this.getReinyeccionMovil().getBajaTratados())),this.getReinyeccionPrepago().getBajaTratados()), totalTratados));		

		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCiento(totalPendientes, totalPendientes));
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getAltaPendiente(), this.getReinyeccionMovil().getAltaPendiente())),this.getReinyeccionPrepago().getAltaPendiente()), totalPendientes));
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getModPendiente(), this.getReinyeccionMovil().getModPendiente())),this.getReinyeccionPrepago().getModPendiente()), totalPendientes));
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getBajaPendiente(), this.getReinyeccionMovil().getBajaPendiente())),this.getReinyeccionPrepago().getBajaPendiente()), totalPendientes));		
		
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCiento(totalErrores, totalErrores));
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getAltaErrores(), this.getReinyeccionMovil().getAltaErrores())),this.getReinyeccionPrepago().getAltaErrores()), totalErrores));
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getModErrores(), this.getReinyeccionMovil().getModErrores())),this.getReinyeccionPrepago().getModErrores()), totalErrores));
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getBajaErrores(), this.getReinyeccionMovil().getBajaErrores())),this.getReinyeccionPrepago().getBajaErrores()), totalErrores));		
		
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCiento(totalWarnings, totalWarnings));
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getAltaWarning(), this.getReinyeccionMovil().getAltaWarning())),this.getReinyeccionPrepago().getAltaWarning()), totalWarnings));
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getModWarning(), this.getReinyeccionMovil().getModWarning())),this.getReinyeccionPrepago().getModWarning()), totalWarnings));
		listaValoresPorCientoSumas.add(QGInformeUtils.tantoPorCientoBase0(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getBajaWarning(), this.getReinyeccionMovil().getBajaWarning())),this.getReinyeccionPrepago().getBajaWarning()), totalWarnings));		
		
		return listaValoresPorCientoSumas;
	}

	public void setListaValoresPorCientoSumas(List listaValoresPorCientoSumas) {
		this.listaValoresPorCientoSumas = listaValoresPorCientoSumas;
	}

	public List getListaValoresMostrarSumas() {
				
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getTotal(), this.getReinyeccionMovil().getTotal())),this.getReinyeccionPrepago().getTotal()));
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getTotalTratados(), this.getReinyeccionMovil().getTotalTratados())),this.getReinyeccionPrepago().getTotalTratados()));
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getTotalPendientes(), this.getReinyeccionMovil().getTotalPendientes())),this.getReinyeccionPrepago().getTotalPendientes()));
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getTotalErrores(), this.getReinyeccionMovil().getTotalErrores())),this.getReinyeccionPrepago().getTotalErrores()));
		
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getTotalTratados(), this.getReinyeccionMovil().getTotalTratados())),this.getReinyeccionPrepago().getTotalTratados()));
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getAltaTratados(), this.getReinyeccionMovil().getAltaTratados())),this.getReinyeccionPrepago().getAltaTratados()));
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getModTratados(), this.getReinyeccionMovil().getModTratados())),this.getReinyeccionPrepago().getModTratados()));
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getBajaTratados(), this.getReinyeccionMovil().getBajaTratados())),this.getReinyeccionPrepago().getBajaTratados()));		
		
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getTotalPendientes(), this.getReinyeccionMovil().getTotalPendientes())),this.getReinyeccionPrepago().getTotalPendientes()));
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getAltaPendiente(), this.getReinyeccionMovil().getAltaPendiente())),this.getReinyeccionPrepago().getAltaPendiente()));
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getModPendiente(), this.getReinyeccionMovil().getModPendiente())),this.getReinyeccionPrepago().getModPendiente()));
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getBajaPendiente(), this.getReinyeccionMovil().getBajaPendiente())),this.getReinyeccionPrepago().getBajaPendiente()));		
		
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getTotalErrores(), this.getReinyeccionMovil().getTotalErrores())),this.getReinyeccionPrepago().getTotalErrores()));
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getAltaErrores(), this.getReinyeccionMovil().getAltaErrores())),this.getReinyeccionPrepago().getAltaErrores()));
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getModErrores(), this.getReinyeccionMovil().getModErrores())),this.getReinyeccionPrepago().getModErrores()));
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getBajaErrores(), this.getReinyeccionMovil().getBajaErrores())),this.getReinyeccionPrepago().getBajaErrores()));		
		
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getTotalWarnings(), this.getReinyeccionMovil().getTotalWarnings())),this.getReinyeccionPrepago().getTotalWarnings()));
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getAltaWarning(), this.getReinyeccionMovil().getAltaWarning())),this.getReinyeccionPrepago().getAltaWarning()));
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getModWarning(), this.getReinyeccionMovil().getModWarning())),this.getReinyeccionPrepago().getModWarning()));
		listaValoresMostrarSumas.add(QGInformeUtils.sumar((QGInformeUtils.sumar(this.getReinyeccionFija().getBajaWarning(), this.getReinyeccionMovil().getBajaWarning())),this.getReinyeccionPrepago().getBajaWarning()));		
		
		
		return listaValoresMostrarSumas;
	}

	public void setListaValoresMostrarSumas(List listaValoresMostrarSumas) {
		this.listaValoresMostrarSumas = listaValoresMostrarSumas;
	}
	
	
	
}
