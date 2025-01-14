/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.consentimientos.servicio.impl;

import com.telefonica.ssnn.qg.buscador.consentimientos.dao.interfaz.QGConsentimientosClientesDao;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGActualizacionCDDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGDetalleCDDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGEntradaHistorialDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.dto.QGEntradaListaCDDto;
import com.telefonica.ssnn.qg.buscador.consentimientos.servicio.interfaz.QGConsentimientosClientesServicio;

import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author vsierra
 *
 */
public class QGConsentimientosClientesServicioImpl implements QGConsentimientosClientesServicio {

	private QGConsentimientosClientesDao consentimientosDao;
	
	/**
	 * Llamará al método del Dao que lleva su mismo nombre
	 */
	public QGCGlobalDto actualizacionCliente(QGActualizacionCDDto actualizacionCDDto) {
		return this.getConsentimientosDao().actualizacionCliente(actualizacionCDDto);
	}

	/**
	 * Llamará al método del Dao que lleva su mismo nombre
	 */
	public QGCGlobalDto obtenerDetalleCliente(QGDetalleCDDto detalleCDDto) {
		return this.getConsentimientosDao().obtenerDetalleCliente(detalleCDDto);
	}

	/**
	 * Llamará al método del Dao que lleva su mismo nombre
	 */
	public QGCGlobalDto obtenerEstadoGestion(String codigo) {
		return this.getConsentimientosDao().obtenerEstadoGestion(codigo);
	}

	/**
	 * Llamará al método del Dao que lleva su mismo nombre
	 */
	public QGCGlobalDto obtenerHistorialCliente(
			QGEntradaHistorialDto entradaHistorialDto) {
		return this.getConsentimientosDao().obtenerHistorialCliente(entradaHistorialDto);
	}

	/**
	 * Llamará al método del Dao obtenerListaConsentimientos
	 */
	public QGCGlobalDto obtenerListaFiltroConsentimientos(
			QGEntradaListaCDDto entradaListaCDDto) {
		entradaListaCDDto.setCodActuacion(QGConstantes.CODIGO_FIJO);
		return this.getConsentimientosDao().obtenerListaConsentimientos(entradaListaCDDto);
	}

	public QGCGlobalDto obtenerListaTodosConsentimientos(
			QGEntradaListaCDDto entradaListaCDDto) {
		entradaListaCDDto.setCodActuacion(QGConstantes.CODIGO_TODOS);
		return this.getConsentimientosDao().obtenerListaConsentimientos(entradaListaCDDto);
	}

	/**
	 * Llamará al método del Dao que lleva su mismo nombre
	 */
	public QGCGlobalDto obtenerTipoOperacion(String codigo) {
		return this.getConsentimientosDao().obtenerTipoOperacion(codigo);
	}

	/**
	 * Llamará al método del Dao que lleva su mismo nombre
	 */
	public QGCGlobalDto obtenerListaEstadoGestion() {
		return this.getConsentimientosDao().obtenerListaEstadoGestion();
	}

	/**
	 * Llamará al método del Dao que lleva su mismo nombre
	 */
	public QGCGlobalDto obtenerListaTipoOperacion() {
		return this.getConsentimientosDao().obtenerListaTipoOperacion();
	}
	
	public QGCGlobalDto obtenerListaTiposComunicacion() {
		// TODO Auto-generated method stub
		return null;
	}

	public QGCGlobalDto obtenerTipoComunicacion(String codigo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public QGConsentimientosClientesDao getConsentimientosDao() {
		return consentimientosDao;
	}

	public void setConsentimientosDao(QGConsentimientosClientesDao consentimientosDao) {
		this.consentimientosDao = consentimientosDao;
	}

	
}
