package com.telefonica.ssnn.qg.creadorPdf.clasesRenderer;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import com.telefonica.ssnn.qg.creadorPdf.exception.QGCreadorPdfException;

public class QGCreadorPdf {

	
	 /**
	  * 
	  * @param rutaPlantilla ruta de la plantilla .xhtml que se usará para crear el pdf
	  * @param parametros parametros a sustituir en la plantilla $P{}
	  * @param baseURL ?? ("")
	  * @param fontDir directorio de fuentes del sistema operativo, si es nulo, se toma el de por defecto del sistema operativo que 
	  * se esté utilizando.
	  * @return ByteArrayOutputStream con el fichero creado.
	  * @throws Exception
	  */
	 public static ByteArrayOutputStream crearPdf(InputStream isPlantilla, Map parametros, String baseURL,String fontDir) throws Exception{
		
		QGReportUtilities reportUtilities = new QGReportUtilities();
		try {
			return reportUtilities.obtenerInforme(isPlantilla, parametros, baseURL, fontDir);
		} catch (QGCreadorPdfException e) {
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Ha ocurrido un error creando el pdf.");
		}		
	}
}
