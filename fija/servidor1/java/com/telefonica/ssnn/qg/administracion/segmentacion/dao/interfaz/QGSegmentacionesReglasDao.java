package com.telefonica.ssnn.qg.administracion.segmentacion.dao.interfaz;

import com.telefonica.ssnn.qg.administracion.segmentacion.dto.QGEntradaSegmentacionesReglasDto;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

/**
 * @author jacastano
 *
 */
public interface QGSegmentacionesReglasDao {
	public QGCGlobalDto obtenerDatosReglas(String actuacion);

	public QGCGlobalDto obtenerListaReglas(QGEntradaSegmentacionesReglasDto busquedaSegmentacion);

	public QGCGlobalDto obtenerListaReglasSeg(QGEntradaSegmentacionesReglasDto busquedaSegmentacion);

	public QGCGlobalDto obtenerListaReglasTotal(QGEntradaSegmentacionesReglasDto busquedaSegmentacion);

	public QGCGlobalDto operarReglas(QGEntradaSegmentacionesReglasDto entrada);
}