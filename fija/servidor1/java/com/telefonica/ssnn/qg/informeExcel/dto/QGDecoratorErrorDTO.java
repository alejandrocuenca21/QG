package com.telefonica.ssnn.qg.informeExcel.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class QGDecoratorErrorDTO {
	/**
	 * Todos los errores del informe, a partir de este mapa
	 * se iran realizando todos los filtros.
	 */
	private Map mapErrors;
	
	/**
	 * Errores de reinyeccion
	 * @return
	 */
	private Map mapReinyeccionErrors;
	
	/*
	 * Diferenciaremos todos los tipos de errores con codigos únicos, como se muestra
	 * en la primera columna de los siguientes comentarios. Tendremos todas las funciones
	 * de filtrado para dichos codigos.
	 * 
	 * Se obvia los errores QGCXXX
	 * 
	 * En javascript nada mas que habrá que acceder a dichos metodos: getErrorCodeEA, getErrorCodeEE...
	 * 
	 * -MOVIMIENTOS FIJOS
	 * 
	 * 	         ERRORES
		EA	A - ALTA 
		EB	B - BAJA
		EM	M - MODIFICACION
		EC	C - CAMBIO SEGMENTO
		ED	D - CAMBIO SEGMENTO DIFERIDO
		EE	E - BAJA EN DIFERIDO
		ER	R - REACTIVACION DE CLIENTES
		
			     ERRORES INFORMATIVOS
		WA	A - ALTA 
		WB	B - BAJA
		WM	M - MODIFICACION
		WC	C - CAMBIO SEGMENTO
		WD	D - CAMBIO SEGMENTO DIFERIDO
		WE	E - BAJA EN DIFERIDO
		WR	R - REACTIVACION DE CLIENTES
		
		-MOVIMIENTOS MOVILES
		
			     ERRORES
		EA	A - ALTAS DIRECCIONES
		EAC	AC - ALTA CLIENTE
		EAP	AP- ALTA PRECLIENTE
		EB	B - BAJAS DIRECCIONES ELECTRONICAS
		ECC	CC - CANCELACION
		ECE	CE - CAMBIO DE ESTADO
		EM	M - MODIFICACION DIRECCIONES
		EMC	MC - MODIFICACIÓN DE DATOS VARIOS DEL CLIENTE 
		EME	ME - MOD.ESTABLECIMIENTO
		EMI	MI - MODIFICACION IMPRESIÓN FACTURA
		EMP	MP - MIGRACION DE PRECLIENTE A CLIENTE 
		EMS	MS - MODIFICACION SEGMENTACION
		ENS	NS - ASIGNACION SEGMENTACION
		
			     ERRORES INFORMATIVOS
		WA	A - ALTAS DIRECCIONES
		WAC	AC - ALTA CLIENTE
		WAP	AP- ALTA PRECLIENTE
		WB	B - BAJAS DIRECCIONES ELECTRONICAS
		WCC	CC - CANCELACION
		WCE	CE - CAMBIO DE ESTADO
		WM	M - MODIFICACION DIRECCIONES
		WMC	MC - MODIFICACIÓN DE DATOS VARIOS DEL CLIENTE 
		WME	ME - MOD.ESTABLECIMIENTO
		WMI	MI - MODIFICACION IMPRESIÓN FACTURA
		WMP	MP - MIGRACION DE PRECLIENTE A CLIENTE 
		WMS	MS - MODIFICACION SEGMENTACION
		WNS	NS - ASIGNACION SEGMENTACION
		
		-MOVIMIENTOS PREPAGO
		
			     ERRORES
		EAG	AG - ALTA PREPAGO
		EBG	BG - BAJA PREPAGO
		EMG	MG - MODIFICACIÓN DE DATOS VARIOS DEL CLIENTE
		
			     ERRORES INFORMATIVOS
		WAG	AG - ALTA PREPAGOS
		WBG	BG - BAJA PREPAGO
		WMG	MG - MODIFICACIÓN DE DATOS VARIOS DEL CLIENTE	

		- REINYECCIONES
		
			ERRORES  - FIJO/MOVIL/PREPAGO
		
		EA	F,RA,GA - ALTA 
		EM	S,MA,GM - MODIFICACION
		EB	GB 		- BAJA (PREPAGO)		

			ERRORES INFORMATIVOS - FIJO/MOVIL
			
		WA	F,RA,GA - ALTA 
		WM	S,MA,GM - MODIFICACION
		WB	GB 		- BAJA (PREPAGO)

	 */
	
	public List getErrorCodeEAFijo(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadFijo() && error.isErrorAlta()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeEBFijo(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadFijo() && error.isErrorBaja()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeEMFijo(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadFijo() && error.isErrorModificacion()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeEC(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadFijo() && error.isErrorCambioSeg()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeED(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadFijo() && error.isErrorCambioDif()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeEE(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadFijo() && error.isErrorBajaDif()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeER(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadFijo() && error.isErrorReactivacion()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWAFijo(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadFijo() && error.isErrorAlta()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWBFijo(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadFijo() && error.isErrorBaja()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWMFijo(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadFijo() && error.isErrorModificacion()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWC(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadFijo() && error.isErrorCambioSeg()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWD(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadFijo() && error.isErrorCambioDif()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWE(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadFijo() && error.isErrorBajaDif()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWR(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadFijo() && error.isErrorReactivacion()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeEAMovil(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadMovil() && error.isErrorAltaDirecciones()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeEAC(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					 Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadMovil() && error.isErrorAltaCliente()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeEAP(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadMovil() && error.isErrorAltaPreCliente()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeEBMovil(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadMovil() && error.isErrorBajaDirecciones()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeECC(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadMovil() && error.isErrorCancelacion()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeECE(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadMovil() && error.isErrorCambioEstado()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeEMMovil(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadMovil() && error.isErrorModificacionDir()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeEMC(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadMovil() && error.isErrorModificacionDatos()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeEME(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadMovil() && error.isErrorModificacionEstablecimiento()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeEMI(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadMovil() && error.isErrorModificacionImpresion()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeEMP(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadMovil() && error.isErrorMigracion()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeEMS(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadMovil() && error.isErrorModificacionSegmentacion()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeENS(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadMovil() && error.isErrorAsignacionSegmentacion()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWAMovil(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadMovil() && error.isErrorAltaDirecciones()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeEAG(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadPrepago() && error.isErrorAltaPrepago()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeEBG(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadPrepago() && error.isErrorBajaPrepago()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeEMG(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoError() && error.isModalidadPrepago() && error.isErrorModificacionPrepago()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}	
	public List getErrorCodeWAC(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadMovil() && error.isErrorAltaCliente()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWAP(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadMovil() && error.isErrorAltaPreCliente()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWBMovil(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadMovil() && error.isErrorBajaDirecciones()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWCC(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadMovil() && error.isErrorCancelacion()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWCE(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadMovil() && error.isErrorCambioEstado()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWMMovil(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadMovil() && error.isErrorModificacionDir()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWMC(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadMovil() && error.isErrorModificacionDatos()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWME(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadMovil() && error.isErrorModificacionEstablecimiento()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWMI(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadMovil() && error.isErrorModificacionImpresion()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWMP(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadMovil() && error.isErrorMigracion()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWMS(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadMovil() && error.isErrorModificacionSegmentacion()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	public List getErrorCodeWNS(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadMovil() && error.isErrorAsignacionSegmentacion()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	
	public List getErrorCodeWAG(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadPrepago() && error.isErrorAltaPrepago()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	
	public List getErrorCodeWBG(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadPrepago() && error.isErrorBajaPrepago()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	
	public List getErrorCodeWMG(){
		List list = new ArrayList();
		if(mapErrors != null){
			
			if(mapErrors.entrySet() != null && mapErrors.entrySet().size() > 0){
				
				Iterator iter = mapErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorDTO error = (QGErrorDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if(!error.isQGXXX() && error.isTipoWarning() && error.isModalidadPrepago() && error.isErrorModificacionPrepago()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}	
	
	public List getErrorCodeReinyeccionEA(){
		List list = new ArrayList();
		if(mapReinyeccionErrors != null){
			
			if(mapReinyeccionErrors.entrySet() != null && mapReinyeccionErrors.entrySet().size() > 0){
				
				Iterator iter = mapReinyeccionErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorReinyeccionDTO error = (QGErrorReinyeccionDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if( error.isTipoError() && (error.isErrorReinyeccionFijaAlta() || error.isErrorReinyeccionMovilAlta() || error.isErrorReinyeccionPrepagoAlta())){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	
	public List getErrorCodeReinyeccionEM(){
		List list = new ArrayList();
		if(mapReinyeccionErrors != null){
			
			if(mapReinyeccionErrors.entrySet() != null && mapReinyeccionErrors.entrySet().size() > 0){
				
				Iterator iter = mapReinyeccionErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorReinyeccionDTO error = (QGErrorReinyeccionDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if( error.isTipoError() && (error.isErrorReinyeccionFijaMod() || error.isErrorReinyeccionMovilMod() || error.isErrorReinyeccionPrepagoMod())){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	
	public List getErrorCodeReinyeccionEB(){
		List list = new ArrayList();
		if(mapReinyeccionErrors != null){
			
			if(mapReinyeccionErrors.entrySet() != null && mapReinyeccionErrors.entrySet().size() > 0){
				
				Iterator iter = mapReinyeccionErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorReinyeccionDTO error = (QGErrorReinyeccionDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if( error.isTipoError() && error.isErrorReinyeccionPrepagoBaja()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}	
	
	public List getErrorCodeReinyeccionWA(){
		List list = new ArrayList();
		if(mapReinyeccionErrors != null){
			
			if(mapReinyeccionErrors.entrySet() != null && mapReinyeccionErrors.entrySet().size() > 0){
				
				Iterator iter = mapReinyeccionErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorReinyeccionDTO error = (QGErrorReinyeccionDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if( error.isTipoWarning() && (error.isErrorReinyeccionFijaAlta() || error.isErrorReinyeccionMovilAlta() || error.isErrorReinyeccionPrepagoAlta())){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	
	public List getErrorCodeReinyeccionWM(){
		List list = new ArrayList();
		if(mapReinyeccionErrors != null){
			
			if(mapReinyeccionErrors.entrySet() != null && mapReinyeccionErrors.entrySet().size() > 0){
				
				Iterator iter = mapReinyeccionErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorReinyeccionDTO error = (QGErrorReinyeccionDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if( error.isTipoWarning() && (error.isErrorReinyeccionFijaMod() || error.isErrorReinyeccionMovilMod() || error.isErrorReinyeccionPrepagoMod())){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}
	
	public List getErrorCodeReinyeccionWB(){
		List list = new ArrayList();
		if(mapReinyeccionErrors != null){
			
			if(mapReinyeccionErrors.entrySet() != null && mapReinyeccionErrors.entrySet().size() > 0){
				
				Iterator iter = mapReinyeccionErrors.entrySet().iterator();
				
				while (iter.hasNext()) {
					Entry element = (Entry) iter.next();
					 QGErrorReinyeccionDTO error = (QGErrorReinyeccionDTO)element.getValue();
					//Condicion que decidira si dicho error debe mostrarse
					if( error.isTipoWarning() && error.isErrorReinyeccionPrepagoBaja()){
						list.add(error);
					}
				}
			}
			
		}
		return list;
	}	
	
	public Map getMapErrors() {
		return mapErrors;
	}
	public void setMapErrors(Map mapErrors) {
		this.mapErrors = mapErrors;
	}
	public Map getMapReinyeccionErrors() {
		return mapReinyeccionErrors;
	}
	public void setMapReinyeccionErrors(Map mapReinyeccionErrors) {
		this.mapReinyeccionErrors = mapReinyeccionErrors;
	}

}
