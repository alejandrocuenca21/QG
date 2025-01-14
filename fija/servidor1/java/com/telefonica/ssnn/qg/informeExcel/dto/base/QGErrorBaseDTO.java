package com.telefonica.ssnn.qg.informeExcel.dto.base;

import com.telefonica.ssnn.qg.util.QGInformeConstants;

public class QGErrorBaseDTO {

	/**
	 * Identificador del error p.e: QGC002
	 */
	protected String codigo;
	/**
	 * modalidad error: F:FIJO M:MOVIL P:PREPAGO
	 */
	protected String modalidad;
	/**
	 * Indica el tipo de error o error informativo
	 */
	protected String tipo;
	/**
	 * Indica de que relacion es el error (S,M,AC...), la relacion indicara ademas si es de REINYECCION
	 */
	protected String relacion;
	/**
	 * Indica si es QGXXX, se pone a true en el constructor
	 */
	protected boolean especial;
	
	/**
	 * Valor de la suma acumulada de valoresPorFecha
	 */
	protected Integer acumulado;
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRelacion() {
		return relacion;
	}

	public void setRelacion(String relacion) {
		this.relacion = relacion;
	}
	
	public boolean isEspecial() {
		return especial;
	}

	public void setEspecial(boolean especial) {
		this.especial = especial;
	}
	
	/**
	 * Indica si el error es de reinyeccion ya que tendra un tratamiento especial
	 */
	public boolean isErrorReinyeccion(){
		if(relacion != null && (relacion.equals(QGInformeConstants.RELATION_ERROR_F) || relacion.equals(QGInformeConstants.RELATION_ERROR_S))){
			//Reinyeccions FIJAS
			return true;
		}
		if(relacion != null && (relacion.equals(QGInformeConstants.RELATION_ERROR_RA) || relacion.equals(QGInformeConstants.RELATION_ERROR_MA))){
			//Reinyeccion MOVIL
			return true;
		}
		
		if(relacion != null && (relacion.equals(QGInformeConstants.RELATION_ERROR_GA) || relacion.equals(QGInformeConstants.RELATION_ERROR_GB) || relacion.equals(QGInformeConstants.RELATION_ERROR_GM))){
			//Reinyeccion PREPAGO
			return true;
		}		
		
		return false;
	}
	
	public boolean isModalidadFijo(){
		if(!this.isErrorReinyeccion()){
			if(this.modalidad != null && this.modalidad.equals("F")){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	public boolean isModalidadMovil(){
		if(!this.isErrorReinyeccion()){
			if(this.modalidad != null && this.modalidad.equals("M")){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	public boolean isModalidadPrepago(){
		if(!this.isErrorReinyeccion()){
			if(this.modalidad != null && this.modalidad.equals("P")){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}	
	
	public boolean isErrorAlta(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_ALTA.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorBaja(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_BAJA.equals(relacion)){
			return true;
		}
		return false;
	}

	public boolean isErrorModificacion(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_MODIFICACION.equals(relacion)){
			return true;
		}
		return false;
	}


	public boolean isErrorCambioSeg(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_CAMBIO_SEGMENTO.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorCambioDif(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_CAMBIO_SEG_DIF.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorBajaDif(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_BAJA_DIF.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorReactivacion(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_REACTIVACION.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorAltaDirecciones(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_ALTAS_DIR.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorAltaCliente(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_ALTA_CLIENTE.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorAltaPreCliente(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_ALTA_PRECLIENTE.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorBajaDirecciones(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_BAJAS_DIRECCIONES.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorCancelacion(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_CANCELACION.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorCambioEstado(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_CAMBIO_ESTADO.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorModificacionDir(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_MODIFICACION_DIR.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorModificacionDatos(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_MODIFICACION_DATOS.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorModificacionEstablecimiento(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_MODIFICACION_ESTABLECIMIENTO.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorModificacionImpresion(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_MODIFICACION_IMPRESION.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorMigracion(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_MIGRACION.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorModificacionSegmentacion(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_MODIFICACION_SEGMENTACION.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorAsignacionSegmentacion(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_ASIGNACION_SEGMENTACION.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorAltaPrepago(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_ALTA_PREPAGO.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorBajaPrepago(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_BAJA_PREPAGO.equals(relacion)){
			return true;
		}
		return false;
	}
	
	public boolean isErrorModificacionPrepago(){
		if(relacion != null && QGInformeConstants.RELATION_ERROR_MODIFICACION_PREPAGO.equals(relacion)){
			return true;
		}
		return false;
	}	
	
	public boolean isErrorReinyeccionFijaAlta(){
		if(relacion != null && (relacion.equals(QGInformeConstants.RELATION_ERROR_F))){
			//Reinyeccions FIJAS
			return true;
		}
		return false;
	}
	
	public boolean isErrorReinyeccionFijaMod(){
		if(relacion != null && (relacion.equals(QGInformeConstants.RELATION_ERROR_S))){
			//Reinyeccions FIJAS
			return true;
		}
		return false;
	}
	
	public boolean isErrorReinyeccionMovilAlta(){
		if(relacion != null && (relacion.equals(QGInformeConstants.RELATION_ERROR_RA))){
			//Reinyeccions FIJAS
			return true;
		}
		return false;
	}
	
	public boolean isErrorReinyeccionMovilMod(){
		if(relacion != null && (relacion.equals(QGInformeConstants.RELATION_ERROR_MA))){
			//Reinyeccions FIJAS
			return true;
		}
		return false;
	}
	
	public boolean isErrorReinyeccionPrepagoAlta(){
		if(relacion != null && (relacion.equals(QGInformeConstants.RELATION_ERROR_GA))){
			//Reinyeccion PREPAGO
			return true;
		}
		return false;
	}
	
	public boolean isErrorReinyeccionPrepagoBaja(){
		if(relacion != null && (relacion.equals(QGInformeConstants.RELATION_ERROR_GB))){
			//Reinyeccion PREPAGO
			return true;
		}
		return false;
	}
	
	public boolean isErrorReinyeccionPrepagoMod(){
		if(relacion != null && (relacion.equals(QGInformeConstants.RELATION_ERROR_GM))){
			//Reinyeccion PREPAGO
			return true;
		}
		return false;
	}	
	
	public boolean isTipoError(){
		if(tipo != null && QGInformeConstants.TYPE_ERROR.equals(tipo)){
			return true;
		}
		return false;
	}
	
	public boolean isTipoWarning(){
		if(tipo != null && QGInformeConstants.TYPE_WARNING.equals(tipo)){
			return true;
		}
		return false;
	}

	public Integer getAcumulado() {
		return acumulado;
	}

	public void setAcumulado(Integer acumulado) {
		this.acumulado = acumulado;
	}
	
}
