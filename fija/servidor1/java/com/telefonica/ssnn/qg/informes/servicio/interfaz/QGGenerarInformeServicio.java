/**
 * 
 */
package com.telefonica.ssnn.qg.informes.servicio.interfaz;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.telefonica.ssnn.qg.informeExcel.dto.QGInformeConciliacionDTO;
import com.telefonica.ssnn.qg.informeExcel.dto.QGInformeConvivenciaDTO;
import com.telefonica.ssnn.qg.informes.dto.QGInformeDto;


/**
 * @author vsierra
 *
 */
public interface QGGenerarInformeServicio {

	ByteArrayOutputStream generarInformeClientes(QGInformeDto informeDto);
	
	ByteArrayOutputStream generarInformeErrores(QGInformeDto informeDto);
	
	ByteArrayOutputStream generarInformeClientesPDF(QGInformeDto informeDto);
	
	ByteArrayOutputStream generarInformeErroresPDF(QGInformeDto informeDto);
	
	ByteArrayOutputStream generarInformeTiposUbicacionPDF(List datos);
	
	ByteArrayOutputStream generarInformeCampaniasPDF(List datos);
	
	ByteArrayOutputStream generarInformeMediosComunicacionPDF(List datos);
	
	ByteArrayOutputStream generarInformeConsentimientoPDF(List datos);
	
	ByteArrayOutputStream generarInformeBuscadorClientesPDF(List datos);
	
	ByteArrayOutputStream generarInformeMensajesErrorPDF(List datos);
	
	ByteArrayOutputStream generarInformeAutorizacionesPDF(List datos);
	
	ByteArrayOutputStream generarInformeSistemasExternosPDF(List datos);
	
	ByteArrayOutputStream generarInformeServiciosNAPDF(List datos);	
	
	ByteArrayOutputStream generarInformeCreatividadNAPDF(List datos);
	
	ByteArrayOutputStream generarInformeEncartesNAPDF(List datos);
	
	ByteArrayOutputStream generarInformeConciliacionPDF(QGInformeConciliacionDTO informe);
	
	ByteArrayOutputStream generarInformeConvivenciaPDF(QGInformeConvivenciaDTO informe);
	/**
	 * Informe para las segmentaciones
	 * @param getlistaDatos
	 * @return Array de bytes
	 */
	ByteArrayOutputStream generarInformeSegmentacionPDF(List getlistaDatos);
	
	/**
	 * Genera un informe usando una determinada plantilla
	 * @param getlistaDatos
	 * @param plantilla
	 * @return
	 */
	ByteArrayOutputStream generarInformePDF(
			List getlistaDatos, String plantilla);
	
	ByteArrayOutputStream generarInformeExcelConvivencia(QGInformeConvivenciaDTO informeDto);
	
	ByteArrayOutputStream generarInformeExcelConciliacion(QGInformeConciliacionDTO informeDto);
	
	ByteArrayOutputStream generarInformeExcelPresegmentacion(List datos);

	ByteArrayOutputStream generarInformePresegmentacionPDF(List datos);

	ByteArrayOutputStream generarInformeReglasPDF(List datos);

	ByteArrayOutputStream generarInformeContratosPDF(List datos);
	
}
