/**
 * 
 */
package com.telefonica.ssnn.qg.informes.servicio.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGBuscadorClientesDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGInformeBuscadorClientesDto;
import com.telefonica.ssnn.qg.creadorPdf.clasesRenderer.QGCreadorPdf;
import com.telefonica.ssnn.qg.exceptions.QGApplicationException;
import com.telefonica.ssnn.qg.informeExcel.dto.QGErrorDTO;
import com.telefonica.ssnn.qg.informeExcel.dto.QGErrorReinyeccionDTO;
import com.telefonica.ssnn.qg.informeExcel.dto.QGInformeConciliacionDTO;
import com.telefonica.ssnn.qg.informeExcel.dto.QGInformeConvivenciaDTO;
import com.telefonica.ssnn.qg.informeExcel.dto.QGMovimientoFijoDTO;
import com.telefonica.ssnn.qg.informeExcel.dto.QGMovimientoMovilDTO;
import com.telefonica.ssnn.qg.informeExcel.dto.QGMovimientoPrepagoDTO;
import com.telefonica.ssnn.qg.informeExcel.dto.QGMovimientoTotalDTO;
import com.telefonica.ssnn.qg.informes.dto.QGInformeDto;
import com.telefonica.ssnn.qg.informes.servicio.interfaz.QGGenerarInformeServicio;
import com.telefonica.ssnn.qg.util.QGInformeUtils;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;

/**
 * @author jacastano
 * 
 */
public class QGGenerarInformeServicioImpl implements QGGenerarInformeServicio {

	private QGGenerarInformeServicio generarInformeServicio;

	/**
	 * logger
	 */
	private static final Logger logger = Logger
			.getLogger(QGGenerarInformeServicioImpl.class);

	public ByteArrayOutputStream generarInformeClientes(QGInformeDto informeDto) {
		Map beans = new HashMap();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		beans.put("datos", informeDto.getListaClientes());
		beans.put("estadistica", informeDto.getEstadisticasDto());
		beans.put("dateFormat", dateFormat);
		XLSTransformer transformer = new XLSTransformer();
		HSSFWorkbook libro = transformer.transformXLS(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(
						"plantillas/plantillaDuplicados.xls"), beans);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			libro.write(baos);
		} catch (IOException e) {
			throw new QGApplicationException(e);
		}

		return baos;
	}

	public ByteArrayOutputStream generarInformeErrores(QGInformeDto informeDto) {
		Map beans = new HashMap();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		beans.put("datos", informeDto.getListaClientes());
		beans.put("estadistica", informeDto.getEstadisticasDto());
		beans.put("dateFormat", dateFormat);
		XLSTransformer transformer = new XLSTransformer();
		HSSFWorkbook libro = transformer.transformXLS(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(
						"plantillas/plantillaErrores.xls"), beans);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			libro.write(baos);
		} catch (IOException e) {
			throw new QGApplicationException(e);
		}

		return baos;
	}

	public ByteArrayOutputStream generarInformeClientesPDF(
			QGInformeDto informeDto) {

		try {

			ClassLoader cLoader = Thread.currentThread()
					.getContextClassLoader();

			InputStream isInformeClientePDF = cLoader
					.getResourceAsStream("plantillas/informeDocumentoDuplicado.jasper");

			HashMap parametros = new HashMap();

			parametros.put("PROCESADOS", informeDto.getEstadisticasDto()
					.getRegTratados());
			parametros.put("ACEPTADOS", informeDto.getEstadisticasDto()
					.getRegValidos());
			parametros.put("RECHAZADOS", informeDto.getEstadisticasDto()
					.getRegErroneos());
			parametros.put("AFIJO", informeDto.getEstadisticasDto()
					.getRegValidosFija());
			parametros.put("AMOVIL", informeDto.getEstadisticasDto()
					.getRegValidosMovil());
			parametros.put("RFIJO", informeDto.getEstadisticasDto()
					.getRegErrorFija());
			parametros.put("RMOVIL", informeDto.getEstadisticasDto()
					.getRegErrorMovil());

			parametros.put("ICOACEPTADOS",
					"/plantillas/imagenes/icoAceptados.gif");
			parametros.put("ICORECHAZADOS",
					"/plantillas/imagenes/icoRechazados.gif");
			parametros.put("ICOAFIJO", "/plantillas/imagenes/icoTelFijo.gif");
			parametros.put("ICOAMOVIL", "/plantillas/imagenes/icoTelMovil.gif");
			parametros.put("ICORFIJO", "/plantillas/imagenes/icoTelFijo.gif");
			parametros.put("ICORMOVIL", "/plantillas/imagenes/icoTelMovil.gif");

			JRBeanCollectionDataSource colDataSource = new JRBeanCollectionDataSource(
					informeDto.getListaClientes());

			JasperPrint jasperPrint = JasperFillManager.fillReport(
					isInformeClientePDF, parametros, colDataSource);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);

			return baos;

		} catch (JRException e) {

			throw new QGApplicationException(e);
		}

	}

	public ByteArrayOutputStream generarInformeErroresPDF(
			QGInformeDto informeDto) {

		try {

			ClassLoader cLoader = Thread.currentThread()
					.getContextClassLoader();

			InputStream isInformeClientePDF = cLoader
					.getResourceAsStream("plantillas/informeMovimientosTratados.jasper");

			HashMap parametros = new HashMap();

			parametros.put("PROCESADOS", informeDto.getEstadisticasDto()
					.getRegTratados());
			parametros.put("ACEPTADOS", informeDto.getEstadisticasDto()
					.getRegValidos());
			parametros.put("RECHAZADOS", informeDto.getEstadisticasDto()
					.getRegErroneos());
			parametros.put("AFIJO", informeDto.getEstadisticasDto()
					.getRegValidosFija());
			parametros.put("AMOVIL", informeDto.getEstadisticasDto()
					.getRegValidosMovil());
			parametros.put("RFIJO", informeDto.getEstadisticasDto()
					.getRegErrorFija());
			parametros.put("RMOVIL", informeDto.getEstadisticasDto()
					.getRegErrorMovil());

			parametros.put("ICOACEPTADOS",
					"/plantillas/imagenes/icoAceptados.gif");
			parametros.put("ICORECHAZADOS",
					"/plantillas/imagenes/icoRechazados.gif");
			parametros.put("ICOAFIJO", "/plantillas/imagenes/icoTelFijo.gif");
			parametros.put("ICOAMOVIL", "/plantillas/imagenes/icoTelMovil.gif");
			parametros.put("ICORFIJO", "/plantillas/imagenes/icoTelFijo.gif");
			parametros.put("ICORMOVIL", "/plantillas/imagenes/icoTelMovil.gif");

			JRBeanCollectionDataSource colDataSource = new JRBeanCollectionDataSource(
					informeDto.getListaClientes());

			JasperPrint jasperPrint = JasperFillManager.fillReport(
					isInformeClientePDF, parametros, colDataSource);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);

			return baos;

		} catch (JRException e) {

			throw new QGApplicationException(e);
		}

	}

	public ByteArrayOutputStream generarInformeTiposUbicacionPDF(List datos) {
			return this.generarInformePDF(datos, QGConstantes.PLANTILLA_INFORME_TIPOUBI);	
	}

	/**
	 * Genera la pantalla de informes de segmentaciones obtenidas por criterio
	 */
	public ByteArrayOutputStream generarInformeSegmentacionPDF(
			List getlistaDatos) {
			//Llamamos a la funcion generica de generacion de informes
			return this.generarInformePDF(getlistaDatos, QGConstantes.PLANTILLA_INFORME_SEG_EVO);		
	}
	/**
	 * Genera los informes de campañas.
	 */
	public ByteArrayOutputStream generarInformeCampaniasPDF(List datos) {
			//Llamamos a la funcion generica de generacion de informes
			return this.generarInformePDF(datos, QGConstantes.PLANTILLA_INFORME_CAMPANIAS);				
	}

	public ByteArrayOutputStream generarInformeMediosComunicacionPDF(List datos) {
			//Llamamos a la funcion generica de generacion de informes
			return this.generarInformePDF(datos, QGConstantes.PLANTILLA_INFORME_MCOM);			
			
	}

	public ByteArrayOutputStream generarInformeConsentimientoPDF(List datos) {
			
			//Llamamos a la funcion generica de generacion de informes
			return this.generarInformePDF(datos, QGConstantes.PLANTILLA_INFORME_CONSENT);	
	}
	
	public ByteArrayOutputStream generarInformeMensajesErrorPDF(List datos) {
		//Llamamos a la funcion generica de generacion de informes
		return this.generarInformePDF(datos, QGConstantes.PLANTILLA_INFORME_MERROR);			
		
	}
	
	public ByteArrayOutputStream generarInformeAutorizacionesPDF(List datos) {
		//Llamamos a la funcion generica de generacion de informes
		return this.generarInformePDF(datos, QGConstantes.PLANTILLA_INFORME_AUTORIZ);			
		
	}	

	public ByteArrayOutputStream generarInformeSistemasExternosPDF(List datos) {
		//Llamamos a la funcion generica de generacion de informes
		return this.generarInformePDF(datos, QGConstantes.PLANTILLA_INFORME_SISEXT);			
		
	}	
	
	public ByteArrayOutputStream generarInformeServiciosNAPDF(List datos) {
		//Llamamos a la funcion generica de generacion de informes
		return this.generarInformePDF(datos, QGConstantes.PLANTILLA_INFORME_SERVNA);			
		
	}
	
	public ByteArrayOutputStream generarInformeCreatividadNAPDF(List datos) {
		//Llamamos a la funcion generica de generacion de informes
		return this.generarInformePDF(datos, QGConstantes.PLANTILLA_INFORME_CREATIVIDAD);			
		
	}
	public ByteArrayOutputStream generarInformeEncartesNAPDF(List datos) {
		//Llamamos a la funcion generica de generacion de informes
		return this.generarInformePDF(datos, QGConstantes.PLANTILLA_INFORME_ENCARTES);			
		
	}
	public ByteArrayOutputStream generarInformeConciliacionPDF(QGInformeConciliacionDTO informe) {
		
		logger.info("******---generarInformeConciliacionPDF de QGGenerarInformeServicioImpl---******");		
			
		HashMap datos = new HashMap();
		
		//Informe		
		//Depuración Datos Clientes
		datos.put("listaDatosTW_DDC", QGInformeUtils.formatoLista(informe.getDepuracionDatosClientes().getTW().getListaDatos(),2));
		datos.put("listaDatosQGTW_DDC", QGInformeUtils.formatoLista(informe.getDepuracionDatosClientes().getQGTW().getListaDatos(),2));
		datos.put("listaDatosNSCO_DDC", QGInformeUtils.formatoLista(informe.getDepuracionDatosClientes().getNSCO().getListaDatos(),2));
		datos.put("listaDatosQGNSCO_DDC", QGInformeUtils.formatoLista(informe.getDepuracionDatosClientes().getQGNSCO().getListaDatos(),2));
		datos.put("listaDatosPREPAGO_DDC", QGInformeUtils.formatoLista(informe.getDepuracionDatosClientes().getPREPAGO().getListaDatos(),2));
		datos.put("listaDatosQGPREPAGO_DDC", QGInformeUtils.formatoLista(informe.getDepuracionDatosClientes().getQGPREPAGO().getListaDatos(),2));		
		
		datos.put("listaPorcentajesTW_DDC", QGInformeUtils.formatoLista(informe.getDepuracionDatosClientes().getTW().getListaPorcentajes(),2));
		datos.put("listaPorcentajesQGTW_DDC", QGInformeUtils.formatoLista(informe.getDepuracionDatosClientes().getQGTW().getListaPorcentajes(),2));
		datos.put("listaPorcentajesNSCO_DDC", QGInformeUtils.formatoLista(informe.getDepuracionDatosClientes().getNSCO().getListaPorcentajes(),2));
		datos.put("listaPorcentajesQGNSCO_DDC", QGInformeUtils.formatoLista(informe.getDepuracionDatosClientes().getQGNSCO().getListaPorcentajes(),2));
		datos.put("listaPorcentajesPREPAGO_DDC", QGInformeUtils.formatoLista(informe.getDepuracionDatosClientes().getPREPAGO().getListaPorcentajes(),2));
		datos.put("listaPorcentajesQGPREPAGO_DDC", QGInformeUtils.formatoLista(informe.getDepuracionDatosClientes().getQGPREPAGO().getListaPorcentajes(),2));		
		
		//Clientes Conciliados
		datos.put("listaDatosTW_DCC", QGInformeUtils.formatoLista(informe.getClientesConciliados().getTW().getListaDatos(),2));
		datos.put("listaDatosQGTW_DCC", QGInformeUtils.formatoLista(informe.getClientesConciliados().getQGTW().getListaDatos(),2));
		datos.put("listaDatosNSCO_DCC", QGInformeUtils.formatoLista(informe.getClientesConciliados().getNSCO().getListaDatos(),2));
		datos.put("listaDatosQGNSCO_DCC", QGInformeUtils.formatoLista(informe.getClientesConciliados().getQGNSCO().getListaDatos(),2));
		datos.put("listaDatosPREPAGO_DCC", QGInformeUtils.formatoLista(informe.getClientesConciliados().getPREPAGO().getListaDatos(),2));
		datos.put("listaDatosQGPREPAGO_DCC", QGInformeUtils.formatoLista(informe.getClientesConciliados().getQGPREPAGO().getListaDatos(),2));		
		
		datos.put("listaPorcentajesTW_DCC", QGInformeUtils.formatoLista(informe.getClientesConciliados().getTW().getListaPorcentajes(),2));
		datos.put("listaPorcentajesNSCO_DCC", QGInformeUtils.formatoLista(informe.getClientesConciliados().getNSCO().getListaPorcentajes(),2));
		datos.put("listaPorcentajesPREPAGO_DCC", QGInformeUtils.formatoLista(informe.getClientesConciliados().getPREPAGO().getListaPorcentajes(),2));		
		
		//Conciliación Direcciones
		datos.put("listaDatosNSCO_DCD", QGInformeUtils.formatoLista(informe.getConciliacionDirecciones().getNSCO().getListaDatos(),2));
		datos.put("listaDatosQG_DCD", QGInformeUtils.formatoLista(informe.getConciliacionDirecciones().getQG().getListaDatos(),2));
		
		datos.put("listaPorcentajesNSCO_DCD", QGInformeUtils.formatoLista(informe.getConciliacionDirecciones().getNSCO().getListaPorcentajes(),2));
		datos.put("listaPorcentajesQG_DCD", QGInformeUtils.formatoLista(informe.getConciliacionDirecciones().getQG().getListaPorcentajes(),2));
		
		datos.put("fechaSolic", informe.getFechaSolicitud());
		
		//NSCO
		datos.put("NSCO",QGInformeUtils.formatoLista(informe.getNSCO(),2));
		
		//TW
		datos.put("TW",QGInformeUtils.formatoLista(informe.getTW(),2));
		
		//PREPAGO
		datos.put("PREPAGO",QGInformeUtils.formatoLista(informe.getPREPAGO(),2));		
		
		ByteArrayOutputStream salida = null;
		try {
			InputStream isInforme = Thread.currentThread()
			.getContextClassLoader().getResourceAsStream(
					"plantillas/informeConciliacion.xhtml");

			salida = QGCreadorPdf.crearPdf(isInforme, datos, "", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("******---FINAL generarInformeConciliacionPDF de QGGenerarInformeServicioImpl---******");
		
		return salida;			
	}	
	
	private List formatearFechas(List fechas,String formato){
		
		logger.info("******---formatearFechas de QGGenerarInformeServicioImpl---******");		
		
		List salida = new ArrayList();
		for (int i=0;i<fechas.size();i++){
			String fecha = QGUtilidadesFechasUtils.formatearFechaDesdeCopy((String)fechas.get(i), formato);
			salida.add(fecha);
		}
		
		logger.info("******---FINAL formatearFechas de QGGenerarInformeServicioImpl---******");		
		
		return salida;
	}
	
	public ByteArrayOutputStream generarInformeConvivenciaPDF(QGInformeConvivenciaDTO informe) {
		
		logger.info("******---generarInformeConvivenciaPDF de QGGenerarInformeServicioImpl---******");		
		
		HashMap datos = new HashMap();
		
		//Informe		
		//Movimientos Totales
		datos.put("listaFechasMov", formatearFechas(informe.getListTotales().getFechasInforme(),QGUtilidadesFechasUtils.FORMATO_FECHA_5));
		for (int i=0;i < 7;i++){
			if (i < informe.getNumDias().intValue()){
				datos.put("listaDatosMov"+i, QGInformeUtils.formatoLista(((QGMovimientoTotalDTO) informe.getListTotales().getListaMovimientos().get(i)).getListaValoresMostrar(),3));
				datos.put("listaPorcentajesMov"+i,QGInformeUtils.formatoLista(((QGMovimientoTotalDTO) informe.getListTotales().getListaMovimientos().get(i)).getListaValoresPorCiento(),3));			
				datos.put("noMostrarMov"+i, "");
				datos.put("cierreDiv"+i,"");
			}
			else{
				datos.put("noMostrarMov"+i, "<div style=\"display:none;\">");
				datos.put("cierreDiv"+i,"</div>");
			}
		}

		datos.put("listaDatosMovTotal",QGInformeUtils.formatoLista(((QGMovimientoTotalDTO) informe.getListTotales().getMovimientoTotal()).getListaValoresMostrar(),3));
		datos.put("listaPorcentajesMovTotal",QGInformeUtils.formatoLista(((QGMovimientoTotalDTO) informe.getListTotales().getMovimientoTotal()).getListaValoresPorCiento(),3));
		
		String espacios = "<h1 style=\"page-break-after: always;\"></h1>";
		datos.put("espaciosMov", espacios);

		//Movimientos Fijos
		datos.put("listaFechasFij",formatearFechas(informe.getListFijos().getFechasInforme(),QGUtilidadesFechasUtils.FORMATO_FECHA_5));
		
		for (int i=0;i < 7;i++){
			if (i < informe.getNumDias().intValue()){
				datos.put("listaDatosFij"+i,QGInformeUtils.formatoLista(((QGMovimientoFijoDTO) informe.getListFijos().getListaMovimientos().get(i)).getListaValoresMostrar(),3));
				datos.put("listaPorcentajesFij"+i,QGInformeUtils.formatoLista(((QGMovimientoFijoDTO) informe.getListFijos().getListaMovimientos().get(i)).getListaValoresPorCiento(),3));			
				datos.put("noMostrarFij"+i, "");
				datos.put("cierreDivF"+i,"");
			}
			else{
				datos.put("noMostrarFij"+i, "<div style=\"display:none;\">");
				datos.put("cierreDivF"+i,"</div>");
			}
		}

		datos.put("listaDatosFijTotal",QGInformeUtils.formatoLista(((QGMovimientoFijoDTO) informe.getListFijos().getMovimientoTotal()).getListaValoresMostrar(),3));
		datos.put("listaPorcentajesFijTotal",QGInformeUtils.formatoLista(((QGMovimientoFijoDTO) informe.getListFijos().getMovimientoTotal()).getListaValoresPorCiento(),3));

		
		espacios = "<h1 style=\"page-break-after: always;\"></h1>";
		datos.put("espaciosFij", espacios);
		
		//Movimientos Movil
		datos.put("listaFechasMovil", formatearFechas(informe.getListMovil().getFechasInforme(),QGUtilidadesFechasUtils.FORMATO_FECHA_5));
		
		for (int i=0;i < 7;i++){
			if (i < informe.getNumDias().intValue()){
				datos.put("listaDatosMovil"+i,QGInformeUtils.formatoLista(((QGMovimientoMovilDTO) informe.getListMovil().getListaMovimientos().get(i)).getListaValoresMostrar(),3));
				datos.put("listaPorcentajesMovil"+i,QGInformeUtils.formatoLista(((QGMovimientoMovilDTO) informe.getListMovil().getListaMovimientos().get(i)).getListaValoresPorCiento(),3));			
				datos.put("noMostrarMovil"+i, "");
				datos.put("cierreDivM","");
			}
			else{
				datos.put("noMostrarMovil"+i, "<div style=\"display:none;\">");
				datos.put("cierreDivM"+i,"</div>");
			}
		}

		datos.put("listaDatosMovilTotal",QGInformeUtils.formatoLista(((QGMovimientoMovilDTO) informe.getListMovil().getMovimientoTotal()).getListaValoresMostrar(),3));
		datos.put("listaPorcentajesMovilTotal",QGInformeUtils.formatoLista(((QGMovimientoMovilDTO) informe.getListMovil().getMovimientoTotal()).getListaValoresPorCiento(),3));

		espacios = "<h1 style=\"page-break-after: always;\"></h1>";
		datos.put("espaciosMovil", espacios);
		
		//Movimientos Prepago
		datos.put("listaFechasPrepago", formatearFechas(informe.getListPrepago().getFechasInforme(),QGUtilidadesFechasUtils.FORMATO_FECHA_5));
		
		for (int i=0;i < 7;i++){
			if (i < informe.getNumDias().intValue()){
				datos.put("listaDatosPrepago"+i,QGInformeUtils.formatoLista(((QGMovimientoPrepagoDTO) informe.getListPrepago().getListaMovimientos().get(i)).getListaValoresMostrar(),3));
				datos.put("listaPorcentajesPrepago"+i,QGInformeUtils.formatoLista(((QGMovimientoPrepagoDTO) informe.getListPrepago().getListaMovimientos().get(i)).getListaValoresPorCiento(),3));			
				datos.put("noMostrarPrepago"+i, "");
				datos.put("cierreDivP","");
			}
			else{
				datos.put("noMostrarPrepago"+i, "<div style=\"display:none;\">");
				datos.put("cierreDivP"+i,"</div>");
			}
		}

		datos.put("listaDatosPrepagoTotal",QGInformeUtils.formatoLista(((QGMovimientoPrepagoDTO) informe.getListPrepago().getMovimientoTotal()).getListaValoresMostrar(),3));
		datos.put("listaPorcentajesPrepagoTotal",QGInformeUtils.formatoLista(((QGMovimientoPrepagoDTO) informe.getListPrepago().getMovimientoTotal()).getListaValoresPorCiento(),3));

		espacios = "<h1 style=\"page-break-after: always;\"></h1>";
		datos.put("espaciosPrepago", espacios);		
		
		//Reinyeccion
		if (informe.getFechaReinyeccion() != null){
			datos.put("mostrarReinye","");
			datos.put("finMostrarReinye","");
			
			espacios = "<h1 style=\"page-break-after: always;\"></h1>";
			datos.put("espaciosReinye", espacios);
		}
		else{	
			datos.put("mostrarReinye","<div style=\"display:none;\">");
			datos.put("finMostrarReinye","</div>");
			datos.put("espaciosReinye", "");
		}
		
		datos.put("fechaReinyeccion", informe.getFechaReinyeccion());

		datos.put("listaDatosReinyeFija",QGInformeUtils.formatoLista((informe.getReinyeccion().getReinyeccionFija().getListaValoresMostrar()),3));
		datos.put("listaPorcentajesReinyeFija",QGInformeUtils.formatoLista((informe.getReinyeccion().getReinyeccionFija().getListaValoresPorCiento()),3));

		datos.put("listaDatosReinyeMovil",QGInformeUtils.formatoLista((informe.getReinyeccion().getReinyeccionMovil().getListaValoresMostrar()),3));
		datos.put("listaPorcentajesReinyeMovil",QGInformeUtils.formatoLista((informe.getReinyeccion().getReinyeccionMovil().getListaValoresPorCiento()),3));
		
		datos.put("listaDatosReinyePrepago",QGInformeUtils.formatoLista((informe.getReinyeccion().getReinyeccionPrepago().getListaValoresMostrar()),3));
		datos.put("listaPorcentajesReinyePrepago",QGInformeUtils.formatoLista((informe.getReinyeccion().getReinyeccionPrepago().getListaValoresPorCiento()),3));		
		
		datos.put("listaDatosReinyeSumas",QGInformeUtils.formatoLista((informe.getReinyeccion().getListaValoresMostrarSumas()),3));
		datos.put("listaPorcentajesReinyeSumas",QGInformeUtils.formatoLista((informe.getReinyeccion().getListaValoresPorCientoSumas()),3));
		
		
		//Errores
		//Lista errores Movimientos Fija
		String[] erroresFijos = {"FEA", "FEB", "FEM", "FEC", "FED", "FEE", "FER"};
		String[] erroresFijosInf = {"FWA", "FWB", "FWM", "FWC", "FWD", "FWE", "FWR"};
		datos = pintarListadoErrores(informe.getErroresFijos(),erroresFijos,informe.getNumDias().intValue(),datos);
		datos = pintarListadoErrores(informe.getErroresFijos(),erroresFijosInf,informe.getNumDias().intValue(),datos);
		//Si excemos este número de errores coloco los informativos en otra página
		if (informe.getErroresFijos().size() > 12){
			espacios = "<h1 style=\"page-break-after: always;\"></h1>";
			datos.put("espaciosErrFijInf", espacios);
		}
		else{
			espacios = "";
			datos.put("espaciosErrFijInf", espacios);
		}
		
		//Lista errores Movimientos Movil
		String[] erroresMovil = {"MEA", "MEAC", "MEAP", "MEB", "MECC", "MECE", "MEM", "MEMC", "MEME", "MEMI", "MEMP", "MEMS", "MENS"};
		String[] erroresMovilInf = {"MWA", "MWAC", "MWAP", "MWB", "MWCC", "MWCE", "MWM", "MWMC", "MWME", "MWMI", "MWMP", "MWMS", "MWNS"};
		datos = pintarListadoErrores(informe.getErroresMovil(),erroresMovil,informe.getNumDias().intValue(),datos);
		datos = pintarListadoErrores(informe.getErroresMovil(),erroresMovilInf,informe.getNumDias().intValue(),datos);
		
		//Si excemos este número de días coloco los informativos en otra página
		if (informe.getNumDias().intValue() > 6){
			espacios = "<h1 style=\"page-break-after: always;\"></h1>";
			datos.put("espaciosErrMovil", espacios);
			espacios = "";
			datos.put("espaciosErrInfMovil", espacios);
		}
		else{
			espacios = "";
			datos.put("espaciosErrMovil", espacios);
			espacios = "<h1 style=\"page-break-after: always;\"></h1>";
			datos.put("espaciosErrInfMovil", espacios);
		}
		
		//Lista errores Movimientos Prepago
		String[] erroresPrepago = {"PEAG", "PEBG", "PEMG"};
		String[] erroresPrepagoInf = {"PWAG", "PWBG", "PWMG"};
		datos = pintarListadoErrores(informe.getErroresPrepago(),erroresPrepago,informe.getNumDias().intValue(),datos);
		datos = pintarListadoErrores(informe.getErroresPrepago(),erroresPrepagoInf,informe.getNumDias().intValue(),datos);
		
		//Si excemos este número de días coloco los informativos en otra página
		if (informe.getNumDias().intValue() > 6){
			espacios = "<h1 style=\"page-break-after: always;\"></h1>";
			datos.put("espaciosErrPrepago", espacios);
			espacios = "";
			datos.put("espaciosErrInfPrepago", espacios);
		}
		else{
			espacios = "";
			datos.put("espaciosErrPrepago", espacios);
			espacios = "<h1 style=\"page-break-after: always;\"></h1>";
			datos.put("espaciosErrInfPrepago", espacios);
		}		
				
		//Lista errores Reinyección
		String[] erroresReinye = {"REA", "REM", "REB", "RWA", "RWM", "RWB" };
		datos = pintarListadoErroresReinyeccion(informe.getErroresReinyeccion(),erroresReinye,datos);
		
		
		//Dias que se muestran, se resta 1 porque los valores van desde 0 a numDias -1
		datos.put("numDias", (QGInformeUtils.restar(informe.getNumDias(),new Integer(1))).toString());
		
		ByteArrayOutputStream salida = null;
		try {
			InputStream isInforme = Thread.currentThread()
			.getContextClassLoader().getResourceAsStream(
					"plantillas/informeConvivencia.xhtml");

			salida = QGCreadorPdf.crearPdf(isInforme, datos, "", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("******---FINAL generarInformeConvivenciaPDF de QGGenerarInformeServicioImpl---******");		
		
		return salida;			
	}		
	
	private HashMap pintarListadoErrores (List errores, String[] lista,int dias, HashMap datos){
		
		logger.info("******---pintarListadoErrores de QGGenerarInformeServicioImpl---******");		
		
		int totalCeldas = (dias * 2);
		String simbolPer = null;
		String stringTD = "";
		String stringTR = "";
		
		for (int i=0;i < lista.length;i++){
			stringTR = "";
			for (int j=0;j < errores.size();j++){
				if (lista[i].equals(((QGErrorDTO) errores.get(j)).getIdTable())){
					stringTD += "<td style=\"text-align:right; background-color:rgb(255,255,255);font-family: Arial,Helvetica,sans-serif;font-size: 4pt;\">"+((QGErrorDTO) errores.get(j)).getColumnas().get(0)+"</td>";
					for (int k=1;k <= totalCeldas;k++){
						if(k % 2 == 0){
					 		simbolPer = "%";
					 	}
						else{
							simbolPer = "";
						}
						stringTD += "<td style=\"text-align:right; background-color:rgb(255,255,255);font-family: Arial,Helvetica,sans-serif;font-size: 4pt;\">"+QGInformeUtils.formatoElemento(((QGErrorDTO) errores.get(j)).getColumnas().get(k),3)+simbolPer + "</td>";
					}
					//Columna Totales
					stringTD += "<td style=\"text-align:right; background-color:rgb(255,255,255);font-family: Arial,Helvetica,sans-serif;font-size: 4pt;\">"+QGInformeUtils.formatoElemento(((QGErrorDTO) errores.get(j)).getColumnas().get(15),3)+"</td>";
					stringTD += "<td style=\"text-align:right; background-color:rgb(255,255,255);font-family: Arial,Helvetica,sans-serif;font-size: 4pt;\">"+QGInformeUtils.formatoElemento(((QGErrorDTO) errores.get(j)).getColumnas().get(16),3)+"%" + "</td>";
					
					stringTR += "<tr>"+stringTD+"</tr>";
					datos.put("error"+lista[i],stringTR);
					stringTD = "";
				}
			}
		}
		
		logger.info("******---FNAL pintarListadoErrores de QGGenerarInformeServicioImpl---******");		
		
		return datos;
	}

	private HashMap pintarListadoErroresReinyeccion (List errores, String[] lista, HashMap datos){
		
		logger.info("******---pintarListadoErroresReinyeccion de QGGenerarInformeServicioImpl---******");		

		String stringTR = "";
		
		String stringTD_F = "";
		String stringTD_M = "";
		String stringTD_P = "";		
		String stringTD_T = "";
		
		for (int i=0;i < lista.length;i++){
			stringTR = "";
			for (int j=0;j < errores.size();j++){
				if (lista[i].equals(((QGErrorReinyeccionDTO) errores.get(j)).getIdTable())){

					//Errores para Fija
					//Columna izquierda
					stringTD_F +=
					"<td style=\"text-align:right; background-color:rgb(255,255,255);font-family: Arial,Helvetica,sans-serif;font-size: 4pt;\">"
					+((QGErrorReinyeccionDTO) errores.get(j)).getColumnas().get(0)+"</td>"+
					//Columna derecha
					"<td style=\"text-align:right; background-color:rgb(255,255,255);font-family: Arial,Helvetica,sans-serif;font-size: 4pt;\">"
					+QGInformeUtils.formatoElemento(((QGErrorReinyeccionDTO) errores.get(j)).getColumnas().get(1),3)+"</td>"+
					"<td style=\"text-align:right; background-color:rgb(255,255,255);font-family: Arial,Helvetica,sans-serif;font-size: 4pt;\">"
					+QGInformeUtils.formatoElemento(((QGErrorReinyeccionDTO) errores.get(j)).getColumnas().get(2),3)+"</td>"+"<td>   </td><td>   </td>";
					
					//Errores para Movil
					//Columna izquierda
					stringTD_M +=
					"<td style=\"text-align:right; background-color:rgb(255,255,255);font-family: Arial,Helvetica,sans-serif;font-size: 4pt;\">"
					+((QGErrorReinyeccionDTO) errores.get(j)).getColumnas().get(4)+"</td>"+
					//Columna derecha
					"<td style=\"text-align:right; background-color:rgb(255,255,255);font-family: Arial,Helvetica,sans-serif;font-size: 4pt;\">"
					+QGInformeUtils.formatoElemento(((QGErrorReinyeccionDTO) errores.get(j)).getColumnas().get(5),3)+"</td>"+
					"<td style=\"text-align:right; background-color:rgb(255,255,255);font-family: Arial,Helvetica,sans-serif;font-size: 4pt;\">"
					+QGInformeUtils.formatoElemento(((QGErrorReinyeccionDTO) errores.get(j)).getColumnas().get(6),3)+"</td>"+"<td>   </td><td>   </td>";
					
					//Errores para Prepago
					//Columna izquierda
					stringTD_P +=
					"<td style=\"text-align:right; background-color:rgb(255,255,255);font-family: Arial,Helvetica,sans-serif;font-size: 4pt;\">"
					+((QGErrorReinyeccionDTO) errores.get(j)).getColumnas().get(8)+"</td>"+
					//Columna derecha
					"<td style=\"text-align:right; background-color:rgb(255,255,255);font-family: Arial,Helvetica,sans-serif;font-size: 4pt;\">"
					+QGInformeUtils.formatoElemento(((QGErrorReinyeccionDTO) errores.get(j)).getColumnas().get(9),3)+"</td>"+
					"<td style=\"text-align:right; background-color:rgb(255,255,255);font-family: Arial,Helvetica,sans-serif;font-size: 4pt;\">"
					+QGInformeUtils.formatoElemento(((QGErrorReinyeccionDTO) errores.get(j)).getColumnas().get(10),3)+"</td>"+"<td>   </td><td>   </td>";					
					
					//Errores para Totales
					stringTD_T +=
					"<td style=\"text-align:right; background-color:rgb(255,255,255);font-family: Arial,Helvetica,sans-serif;font-size: 4pt;\">"
					+((QGErrorReinyeccionDTO) errores.get(j)).getColumnas().get(12)+"</td>"+
					//Columna derecha
					"<td style=\"text-align:right; background-color:rgb(255,255,255);font-family: Arial,Helvetica,sans-serif;font-size: 4pt;\">"
					+QGInformeUtils.formatoElemento(((QGErrorReinyeccionDTO) errores.get(j)).getColumnas().get(13),3)+"</td>"+
					"<td style=\"text-align:right; background-color:rgb(255,255,255);font-family: Arial,Helvetica,sans-serif;font-size: 4pt;\">"
					+QGInformeUtils.formatoElemento(((QGErrorReinyeccionDTO) errores.get(j)).getColumnas().get(14),3)+"</td>";
				
					stringTR+= "<tr>"+stringTD_F + stringTD_M + stringTD_P + stringTD_T+"</tr>";
					
					stringTD_F = "";
					stringTD_M = "";
					stringTD_P = "";					
					stringTD_T = "";
				}
			}
			datos.put("errores"+lista[i], stringTR);
		}
		
		logger.info("******---FINAL pintarListadoErroresReinyeccion de QGGenerarInformeServicioImpl---******");		
		
		return datos;
	}
		
	public ByteArrayOutputStream generarInformeBuscadorClientesPDF(List datos) {

		try {			
			InputStream isInforme = Thread.currentThread()
					.getContextClassLoader().getResourceAsStream(
							"plantillas/informeBuscadorClientes.jasper");

			HashMap parametros = new HashMap();
			QGBuscadorClientesDto buscadorClientesDto = null;
			QGInformeBuscadorClientesDto informeBuscadorClientesDto = null;
			String nombre;
			ArrayList listaDatosInforme = new ArrayList();

			for (int i = 0; i < datos.size(); i++) {
				informeBuscadorClientesDto = new QGInformeBuscadorClientesDto();
				buscadorClientesDto = new QGBuscadorClientesDto();

				buscadorClientesDto = (QGBuscadorClientesDto) datos.get(i);
				nombre = "";

				if (buscadorClientesDto.getInUniNgcCliGbl().equals("A"))
					informeBuscadorClientesDto.setOrigen ("Convergente");
				else if (buscadorClientesDto.getInUniNgcCliGbl().equals("F"))
					informeBuscadorClientesDto.setOrigen ("LNF");
				else if (buscadorClientesDto.getInUniNgcCliGbl().equals("M"))
					informeBuscadorClientesDto.setOrigen ("LNM");
				else
					informeBuscadorClientesDto.setOrigen ("");
				if (buscadorClientesDto.getInModalidad().equals("A"))
					informeBuscadorClientesDto.setModalidad("Ambas");
				else if (buscadorClientesDto.getInModalidad().equals("C"))
					informeBuscadorClientesDto.setModalidad("Contrato");
				else if (buscadorClientesDto.getInModalidad().equals("P"))
					informeBuscadorClientesDto.setModalidad("Prepago");
				else
					informeBuscadorClientesDto.setModalidad("");		
				informeBuscadorClientesDto.setCodCliente(buscadorClientesDto.getCodigoCliente());			
				informeBuscadorClientesDto.setTipoDoc(buscadorClientesDto.getDsTipoDocumento());			
				informeBuscadorClientesDto.setDocIdent(buscadorClientesDto.getNumeroDocumento());

				if (buscadorClientesDto.getRazonSocial().trim().length() > 0) {
					nombre = buscadorClientesDto.getRazonSocial();
				} else {
					nombre = buscadorClientesDto.getNombreCliente() + " "
							+ buscadorClientesDto.getPrimerApellido() + " "
							+ buscadorClientesDto.getSegundoApellido();
				}

				informeBuscadorClientesDto.setNombre(nombre);

				informeBuscadorClientesDto.setEstado(buscadorClientesDto
						.getEstadoCliente());

				informeBuscadorClientesDto
						.setSegmentoSubseg(buscadorClientesDto
								.getCodigoSegmento()
								+ "/"
								+ buscadorClientesDto.getCodigoSubsegmento());
				if (buscadorClientesDto.getCodigoSegmento().equals("")
						&& buscadorClientesDto.getCodigoSubsegmento()
								.equals(""))
					informeBuscadorClientesDto.setSegmentoSubseg("");

				listaDatosInforme.add(informeBuscadorClientesDto);
			}

			JRBeanCollectionDataSource colDataSource = new JRBeanCollectionDataSource(listaDatosInforme);
			JasperPrint jasperPrint = JasperFillManager.fillReport(isInforme, parametros, colDataSource);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
			return baos;

		} catch (JRException e) {

			throw new QGApplicationException(e);
		}
	}	
	
	/**
	 * Genera un informe segun una plantilla y una lista de datos.
	 */
	public ByteArrayOutputStream generarInformePDF(List getlistaDatos,
			String plantilla) {
		try {
			
			logger.info("******---generarInformePDF de QGGenerarInformeServicioImpl---******");			

			InputStream isInforme = Thread.currentThread()
					.getContextClassLoader().getResourceAsStream(
							plantilla);
			
			HashMap parametros = new HashMap();

			JRBeanCollectionDataSource colDataSource = new JRBeanCollectionDataSource(
					getlistaDatos);

			JasperPrint jasperPrint = JasperFillManager.fillReport(isInforme,
					parametros, colDataSource);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
			
			logger.info("******---FINAL generarInformePDF de QGGenerarInformeServicioImpl---******");			

			return baos;

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new QGApplicationException(e);
		}
	}
	
	public ByteArrayOutputStream generarInformeExcelConvivencia(QGInformeConvivenciaDTO informeDto) {
		
		logger.info("******---generarInformeExcelConvivencia de QGGenerarInformeServicioImpl---******");
		
		Map beans = new HashMap();		
		
		beans.put("movimientosTotales", informeDto.getListTotales().getListaMovimientos());
		beans.put("movimientosFijos", informeDto.getListFijos().getListaMovimientos());
		beans.put("movimientosMovil", informeDto.getListMovil().getListaMovimientos());
		beans.put("movimientosPrepago", informeDto.getListPrepago().getListaMovimientos());		
		
		beans.put("intervalo", informeDto.getIntervaloInforme());
		beans.put("fechasInforme", informeDto.getFechasInformeFormateadas());
		beans.put("fechaReinyeccion", informeDto.getFechaReinyeccion());
		
		beans.put("errorFijaAlta", informeDto.getDecoratorError().getErrorCodeEAFijo());
		beans.put("errorFijaBaja", informeDto.getDecoratorError().getErrorCodeEBFijo());
		beans.put("errorFijaMod", informeDto.getDecoratorError().getErrorCodeEMFijo());
		beans.put("errorFijaCambioSeg", informeDto.getDecoratorError().getErrorCodeEC());
		beans.put("errorFijaCambioSegDif", informeDto.getDecoratorError().getErrorCodeED());
		beans.put("errorFijaBajaDif", informeDto.getDecoratorError().getErrorCodeEE());
		beans.put("errorFijaReactivacion", informeDto.getDecoratorError().getErrorCodeER());
		
		beans.put("errorInfoFijaAlta", informeDto.getDecoratorError().getErrorCodeWAFijo());
		beans.put("errorInfoFijaBaja", informeDto.getDecoratorError().getErrorCodeWBFijo());
		beans.put("errorInfoFijaMod", informeDto.getDecoratorError().getErrorCodeWMFijo());
		beans.put("errorInfoFijaCambioSeg", informeDto.getDecoratorError().getErrorCodeWC());
		beans.put("errorInfoFijaCambioSegDif", informeDto.getDecoratorError().getErrorCodeWD());
		beans.put("errorInfoFijaBajaDif", informeDto.getDecoratorError().getErrorCodeWE());
		beans.put("errorInfoFijaReactivacion", informeDto.getDecoratorError().getErrorCodeWR());
		
		
		beans.put("errorMovilAltaDirecciones", informeDto.getDecoratorError().getErrorCodeEAMovil());
		beans.put("errorMovilAltaCliente", informeDto.getDecoratorError().getErrorCodeEAC());
		beans.put("errorMovilAltaPreCliente", informeDto.getDecoratorError().getErrorCodeEAP());
		beans.put("errorMovilBajaDirElectronicas", informeDto.getDecoratorError().getErrorCodeEBMovil());
		beans.put("errorMovilCancelacion", informeDto.getDecoratorError().getErrorCodeECC());
		beans.put("errorMovilCambioEstado", informeDto.getDecoratorError().getErrorCodeECE());
		beans.put("errorMovilModificacionDir", informeDto.getDecoratorError().getErrorCodeEMMovil());
		beans.put("errorMovilModificacionDatos", informeDto.getDecoratorError().getErrorCodeEMC());
		beans.put("errorMovilModificacionEstab", informeDto.getDecoratorError().getErrorCodeEME());
		beans.put("errorMovilModificacionImpresion", informeDto.getDecoratorError().getErrorCodeEMI());
		beans.put("errorMovilMigracionPre", informeDto.getDecoratorError().getErrorCodeEMP());
		beans.put("errorMovilModificacionSegmentacion", informeDto.getDecoratorError().getErrorCodeEMS());
		beans.put("errorMovilAsignacionSegmentacion", informeDto.getDecoratorError().getErrorCodeENS());
		
		beans.put("errorInfoMovilAltaDirecciones", informeDto.getDecoratorError().getErrorCodeWAMovil());
		beans.put("errorInfoMovilAltaCliente", informeDto.getDecoratorError().getErrorCodeWAC());
		beans.put("errorInfoMovilAltaPreCliente", informeDto.getDecoratorError().getErrorCodeWAP());
		beans.put("errorInfoMovilBajaDirElectronicas", informeDto.getDecoratorError().getErrorCodeWBMovil());
		beans.put("errorInfoMovilCancelacion", informeDto.getDecoratorError().getErrorCodeWCC());
		beans.put("errorInfoMovilCambioEstado", informeDto.getDecoratorError().getErrorCodeWCE());
		beans.put("errorInfoMovilModificacionDir", informeDto.getDecoratorError().getErrorCodeWMMovil());
		beans.put("errorInfoMovilModificacionDatos", informeDto.getDecoratorError().getErrorCodeWMC());
		beans.put("errorInfoMovilModificacionEstab", informeDto.getDecoratorError().getErrorCodeWME());
		beans.put("errorInfoMovilModificacionImpresion", informeDto.getDecoratorError().getErrorCodeWMI());
		beans.put("errorInfoMovilMigracionPre", informeDto.getDecoratorError().getErrorCodeWMP());
		beans.put("errorInfoMovilModificacionSegmentacion", informeDto.getDecoratorError().getErrorCodeWMS());
		beans.put("errorInfoMovilAsignacionSegmentacion", informeDto.getDecoratorError().getErrorCodeWNS());
		
		beans.put("errorPrepagoAltaPrepago", informeDto.getDecoratorError().getErrorCodeEAG());
		beans.put("errorPrepagoBajaPrepago", informeDto.getDecoratorError().getErrorCodeEBG());
		beans.put("errorPrepagoModificacionPrepago", informeDto.getDecoratorError().getErrorCodeEMG());
		
		beans.put("errorInfoPrepagoAltaPrepago", informeDto.getDecoratorError().getErrorCodeWAG());
		beans.put("errorInfoPrepagoBajaPrepago", informeDto.getDecoratorError().getErrorCodeWBG());
		beans.put("errorInfoPrepagoModificacionPrepago", informeDto.getDecoratorError().getErrorCodeWMG());		
		
		beans.put("errorReinyeccionEA",informeDto.getDecoratorError().getErrorCodeReinyeccionEA());
		beans.put("errorReinyeccionEM",informeDto.getDecoratorError().getErrorCodeReinyeccionEM());
		beans.put("errorReinyeccionEB",informeDto.getDecoratorError().getErrorCodeReinyeccionEB());		
		beans.put("errorInfoReinyeccionWA",informeDto.getDecoratorError().getErrorCodeReinyeccionWA());
		beans.put("errorInfoReinyeccionWM",informeDto.getDecoratorError().getErrorCodeReinyeccionWM());
		beans.put("errorInfoReinyeccionWB",informeDto.getDecoratorError().getErrorCodeReinyeccionWB());		
		
		beans.put("reinyeccionFija",informeDto.getReinyeccion().getReinyeccionFija());
		beans.put("reinyeccionMovil",informeDto.getReinyeccion().getReinyeccionMovil());
		beans.put("reinyeccionPrepago",informeDto.getReinyeccion().getReinyeccionPrepago());		
		
		XLSTransformer transformer = new XLSTransformer();
		HSSFWorkbook libro = transformer.transformXLS(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(
						"plantillas/informes/CLGL_INFORME_CONVIVENCIA.xls"), beans);

		//Regeneramos todas las formulas
		QGInformeUtils.recalcularLibro(libro);
		
		//Ocultamos las columnas que no tienen informacion relevante
		//segun los dias que se vayan a mostrar
		QGInformeUtils.ocultarColumnas(libro,informeDto.getNumDias());
		
		//Si no hay reinyeccion habrá que ocultar la hoja de reinyeccion
		if(!informeDto.isHayReinyeccion()){
			QGInformeUtils.ocultarHoja(libro,4);
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			libro.write(baos);
		} catch (IOException e) {
			throw new QGApplicationException(e);
		}
		
		logger.info("******---FINAL generarInformeExcelConvivencia de QGGenerarInformeServicioImpl---******");		

		return baos;
	}
	
	public ByteArrayOutputStream generarInformeExcelConciliacion(QGInformeConciliacionDTO informeDto){
		
		logger.info("******---generarInformeExcelConciliacion de QGGenerarInformeServicioImpl---******");		
		
		Map beans = new HashMap();

		beans.put("NSCO", informeDto.getNSCO());
		beans.put("TW", informeDto.getTW());
		beans.put("PREPAGO", informeDto.getPREPAGO());		

		XLSTransformer transformer = new XLSTransformer();
		HSSFWorkbook libro = transformer.transformXLS(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(
						"plantillas/informes/CLGL_INFORME_CONCILIACION.xls"), beans);
		
		libro.setSheetName(libro.getSheetIndex("INFORME"), informeDto.getFechaSolicitud());
		
		//Regeneramos todas las formulas
		QGInformeUtils.recalcularLibro(libro);
			
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			libro.write(baos);
		} catch (IOException e) {
			throw new QGApplicationException(e);
		}
		
		logger.info("******---FINAL generarInformeExcelConciliacion de QGGenerarInformeServicioImpl---******");		

		return baos;		
	}
	
	public ByteArrayOutputStream generarInformeExcelPresegmentacion(List datos){
			
		Map beans = new HashMap();

		beans.put("datos", datos);

		XLSTransformer transformer = new XLSTransformer();
		HSSFWorkbook libro = transformer.transformXLS(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(
						"plantillas/plantillaPresegmentacion.xls"), beans);
				
		//Regeneramos todas las formulas
		QGInformeUtils.recalcularLibro(libro);
			
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			libro.write(baos);
		} catch (IOException e) {
			throw new QGApplicationException(e);
		}

		return baos;	
	}
	
	public ByteArrayOutputStream generarInformePresegmentacionPDF(List datos){
		//Llamamos a la funcion generica de generacion de informes
		return this.generarInformePDF(datos, QGConstantes.PLANTILLA_INFORME_PRESEGMENTACION);	
	}
	
	public ByteArrayOutputStream generarInformeReglasPDF(List datos){
		//Llamamos a la funcion generica de generacion de informes
		return this.generarInformePDF(datos, QGConstantes.PLANTILLA_INFORME_REGLAS);
	}
	
	public ByteArrayOutputStream generarInformeContratosPDF(List datos){
		//Llamamos a la funcion generica de generacion de informes
		return this.generarInformePDF(datos, QGConstantes.PLANTILLA_INFORME_CONTRATOS);
	}
	

	public QGGenerarInformeServicio getGenerarInformeServicio() {
		return generarInformeServicio;
	}

	public void setGenerarInformeServicio(
			QGGenerarInformeServicio generarInformeServicio) {
		this.generarInformeServicio = generarInformeServicio;
	}

}
