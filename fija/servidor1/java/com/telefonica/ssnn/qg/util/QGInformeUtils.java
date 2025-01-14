package com.telefonica.ssnn.qg.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.telefonica.ssnn.qg.informeExcel.dto.QGErrorDTO;
import com.telefonica.ssnn.qg.informeExcel.dto.QGErrorReinyeccionDTO;
import com.telefonica.ssnn.qg.informeExcel.dto.base.QGErrorBaseDTO;

public class QGInformeUtils {

	/**
	 * Convierte un String en un Interger, si el string es vacio devuelve 0
	 * @param numOcurr
	 * @return
	 */
	public static Integer toInteger(String numOcurr) {
		Integer res = new Integer(0);
		
		try{
			if (StringUtils.isNotBlank(numOcurr)) {
				res = Integer.valueOf(numOcurr);
			}
		}catch (Exception e) {
			res = new Integer(0);
		}
		
		return res;
	}
	/**
	 * Metodo que devuelve el identificador único según el error para
	 * localizarlo en la plantilla.
	 * 
	 * @param error
	 * @return
	 */
	public static String getIdTableByTypeError(QGErrorBaseDTO error) {
		String idTable = "";

		if (error.isErrorReinyeccion()) {
			// Fijos, Moviles y Prepago se encuentran en la misma linea
			if (QGInformeConstants.TYPE_ERROR.equals(error.getTipo())) {

				// Por lo tanto las relaciones se agrupan
				if (QGInformeConstants.RELATION_ERROR_F.equals(error.getRelacion())
				|| 	QGInformeConstants.RELATION_ERROR_RA.equals(error.getRelacion())
				|| 	QGInformeConstants.RELATION_ERROR_GA.equals(error.getRelacion())) {

					idTable = QGInformeConstants.MODALITY_REINYECCION + QGInformeConstants.ID_TABLE_EA;

				} else if (QGInformeConstants.RELATION_ERROR_S.equals(error.getRelacion())
						|| QGInformeConstants.RELATION_ERROR_MA.equals(error.getRelacion())
						|| QGInformeConstants.RELATION_ERROR_GM.equals(error.getRelacion())) {

					idTable = QGInformeConstants.MODALITY_REINYECCION + QGInformeConstants.ID_TABLE_EM;
					
				} else if (QGInformeConstants.RELATION_ERROR_GB.equals(error.getRelacion())) {

					idTable = QGInformeConstants.MODALITY_REINYECCION + QGInformeConstants.ID_TABLE_EB;
				}

			} else if (QGInformeConstants.TYPE_WARNING.equals(error.getTipo())) {

				// Por lo tanto las relaciones se agrupan
				if (QGInformeConstants.RELATION_WARNING_F.equals(error.getRelacion())
				||  QGInformeConstants.RELATION_WARNING_RA.equals(error.getRelacion())
				||  QGInformeConstants.RELATION_WARNING_GA.equals(error.getRelacion())) {

					idTable = QGInformeConstants.MODALITY_REINYECCION + QGInformeConstants.ID_TABLE_WA;

				} else if (QGInformeConstants.RELATION_WARNING_S.equals(error.getRelacion())
						|| QGInformeConstants.RELATION_WARNING_MA.equals(error.getRelacion())
						|| QGInformeConstants.RELATION_WARNING_GM.equals(error.getRelacion())) {

					idTable = QGInformeConstants.MODALITY_REINYECCION + QGInformeConstants.ID_TABLE_WM;
					
				} else if (QGInformeConstants.RELATION_WARNING_GB.equals(error.getRelacion())) {

					idTable = QGInformeConstants.MODALITY_REINYECCION + QGInformeConstants.ID_TABLE_WB;
				}
			}
		} else if (error.isModalidadFijo()) {

			if (QGInformeConstants.TYPE_ERROR.equals(error.getTipo())) {

				return QGInformeConstants.MODALITY_FIJO + QGInformeConstants.TYPE_ERROR + error.getRelacion();

			} else if (QGInformeConstants.TYPE_WARNING.equals(error.getTipo())) {

				return QGInformeConstants.MODALITY_FIJO + QGInformeConstants.TYPE_WARNING + error.getRelacion();
			}

		} else if (error.isModalidadMovil()) {

			if (QGInformeConstants.TYPE_ERROR.equals(error.getTipo())) {

				return QGInformeConstants.MODALITY_MOVIL + QGInformeConstants.TYPE_ERROR + error.getRelacion();

			} else if (QGInformeConstants.TYPE_WARNING.equals(error.getTipo())) {

				return QGInformeConstants.MODALITY_MOVIL + QGInformeConstants.TYPE_WARNING + error.getRelacion();
			}

		} else if (error.isModalidadPrepago()) {

			if (QGInformeConstants.TYPE_ERROR.equals(error.getTipo())) {

				return QGInformeConstants.MODALITY_PREPAGO + QGInformeConstants.TYPE_ERROR + error.getRelacion();

			} else if (QGInformeConstants.TYPE_WARNING.equals(error.getTipo())) {

				return QGInformeConstants.MODALITY_PREPAGO + QGInformeConstants.TYPE_WARNING + error.getRelacion();
			}

		}

		return idTable;
	}

	/**
	 * Prepara el valor quitando los posibles 0's que tenga delante
	 * 
	 * @param string
	 * @return
	 */
	public static String prepararValorNumerico(String cadNumerica) {
		if (StringUtils.isNotBlank(cadNumerica)) {
			try {
				Integer num = Integer.valueOf(cadNumerica);
				// Al convertirlo a numerico le hemos quitado los 0.
				return String.valueOf(num);
			} catch (Exception e) {
				e.printStackTrace();
				return "0";
			}
		} else {
			return "0";
		}
	}

	/**
	 * Suma los dos valores y lo devuelve
	 * 
	 * @param cad1
	 * @param cad2
	 * @return
	 */
	public static String sumarValoresInforme(String cad1, String cad2) {
		if (StringUtils.isBlank(cad1)) {
			cad1 = "0";
		}
		if (StringUtils.isBlank(cad2)) {
			cad2 = "0";
		}

		try {
			Integer num1 = Integer.valueOf(cad1);
			Integer num2 = Integer.valueOf(cad2);

			int res = num1.intValue() + num2.intValue();
			// Al convertirlo a numerico le hemos quitado los 0.
			return String.valueOf(res);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}

	}

	/**
	 * Suma los dos valores y lo devuelve
	 * 
	 * @param cad1
	 * @param cad2
	 * @return
	 */
	public static String restarValoresInforme(String cad1, String cad2) {
		if (StringUtils.isBlank(cad1)) {
			cad1 = "0";
		}
		if (StringUtils.isBlank(cad2)) {
			cad2 = "0";
		}

		try {
			Integer num1 = Integer.valueOf(cad1);
			Integer num2 = Integer.valueOf(cad2);

			long res = num1.intValue() - num2.intValue();
			// Al convertirlo a numerico le hemos quitado los 0.
			return String.valueOf(res);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}

	}
	
	/**
	 * Funcion para restar dos integer, si alguno no viene informado se considerara 0
	 * @param op1
	 * @param op2
	 * @return
	 */
	public static Integer restar(Integer op1, Integer op2) {
		if(op1 == null){
			op1 = new Integer(0);
		}
		if(op2 == null){
			op2 = new Integer(0);
		}
		
		return new Integer(op1.intValue() - op2.intValue());
	}

	/**
	 * Devuelve el valor absoluto de un valor tipo cadena
	 * 
	 * @param cad
	 * @return
	 */
	public static Integer abs(Integer value) {

		if (value == null) {
			value = new Integer(0);
		}
		try {
			
			int res = Math.abs(value.intValue());

			return new Integer(res);

		} catch (Exception e) {
			e.printStackTrace();
			return new Integer(0);
		}

	}

	/**
	 * Suma una lista de strings que son numericos, si no son numericos no fallara
	 * pero devolvera un resultado erroneo
	 * @param values
	 * @return
	 */
	public static String sumarStrings(List values) {
		if (values != null && values.size() > 0) {
			String res = "0";
			for (int i = 0; i < values.size(); i++) {
				res = sumarValoresInforme(res, (String) values.get(i));
			}
			return res;
		}
		return "0";
	}
	
	/**
	 * Suma un listado de integers
	 * @param values
	 * @return
	 */
	public static Integer sumar(List values) {
		if (values != null && values.size() > 0) {
			Integer res = new Integer(0);
			for (int i = 0; i < values.size(); i++) {
				res = sumar(res, (Integer) values.get(i));
			}
			return res;
		}
		return new Integer(0);
	}
	/**
	 * Suma tres integers
	 * @param op1
	 * @param op2
	 * @param op3
	 * @return
	 */
	public static Integer sumar(Integer op1, Integer op2) {
		if(op1 == null){
			op1 = new Integer(0);
		}
		if(op2 == null){
			op2 = new Integer(0);
		}	
		
		return new Integer(op1.intValue() + op2.intValue());
	}

	/**
	 * Rellena la lista que indicara los valores de los errores con siete ceros
	 * @param valoresPorFecha
	 */
	public static void rellenarListaValoresError(List valoresPorFecha) {
		if (valoresPorFecha == null) {
			valoresPorFecha = new ArrayList();
		}
		for (int i = 0; i < 7; i++) {
			valoresPorFecha.add(new Integer(0));
		}

	}

	/**
	 * Obtiene la KEY para el mapa
	 * 
	 * @param cadError
	 * @return
	 */
	public static String obtenerClaveError(String cadError) {
		if (cadError != null) {
			String codigo = obtenerCodigoError(cadError);
			String modalidad = obtenerModalidadError(cadError);
			String tipo = obtenerTipoError(cadError);
			String relacion = obtenerRelacionError(cadError);

			return codigo + "-" + modalidad + "-" + tipo + "-" + relacion;
		}
		return null;
	}
	
	/**
	 * Obtiene la KEY para el mapa para un error de reinyeccion, no diferencia entre fijo y movil
	 * 
	 * @param cadError
	 * @return
	 */
	public static String obtenerClaveErrorReinyeccion(String cadError) {
		if (cadError != null) {
			String codigo = obtenerCodigoError(cadError);
			String tipo = obtenerTipoError(cadError);
			String relacion = obtenerRelacionError(cadError);

			return codigo + "-" +  tipo + "-" + relacion;
		}
		return null;
	}
	
	/**
	 * Te dice si una cadena que representa a un error es de reinyeccion o no
	 * @param cadError
	 * @return
	 */
	public static boolean isErrorReinyeccion(String cadError){
				
		String relacion = obtenerRelacionError(cadError);
		//Segun la relacion sera de reinyeccion o no
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
	/**
	 * Obtiene de la cadena completa que representa un error el codigo identificativo

	 * @param cadError
	 * @return
	 */
	public static String obtenerCodigoError(String cadError) {
		if (cadError != null) {
			return cadError.substring(0, 6);
		}
		return null;
	}
	/**
	 * Obtiene de la cadena completa que representa un error el numero de ocurrencias

	 * @param cadError
	 * @return
	 */
	public static String obtenerOccurError(String cadError) {
		if (cadError != null) {
			String[] cads = cadError.split(" ");
			String occur = "0";
			if (cads.length == 3) {
				occur = cads[2];
			} else if (cads.length == 4) {
				occur = cads[3];
			}
			return occur.trim();
		}
		return null;
	}
	/**
	 * Obtiene de la cadena completa que representa un error la modalidad (fija o movil)

	 * @param cadError
	 * @return
	 */
	public static String obtenerModalidadError(String cadenaError) {
		if (cadenaError != null) {
			return cadenaError.substring(6, 7);
		}
		return null;
	}
	/**
	 * Obtiene de la cadena completa que representa un error el tipo (error o warning)

	 * @param cadError
	 * @return
	 */
	public static String obtenerTipoError(String cadenaError) {
		if (cadenaError != null) {
			return cadenaError.substring(7, 8);
		}
		return null;
	}

	/**
	 * Obtiene de la cadena completa que representa un error la relacion o descripcion del error

	 * @param cadError
	 * @return
	 */
	public static String obtenerRelacionError(String cadenaError) {
		if (cadenaError != null) {
			String[] cads = cadenaError.split(" ");
			String relacion = cads[1];
			return relacion.trim();
		}
		return null;
	}

	/**
	 * Devuelve el listado con los errores fijos
	 * 
	 * @param mapErrores
	 * @return
	 */
	public static List obtenerErroresFijos(Map mapErrores) {
		List erroresFijos = new ArrayList();
		if (mapErrores != null) {
			Set setKey = mapErrores.keySet();
			if (setKey != null && setKey.size() > 0) {
				Iterator it = setKey.iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					String[] info = key.split("-");
					if (info != null && "F".equals(info[1])) {
						erroresFijos.add(mapErrores.get(key));
					}
				}
			}
		}
		return erroresFijos;
	}

	/**
	 * Devuelve el listado con los errores moviles
	 * 
	 * @param mapErrores
	 * @return
	 */
	public static List obtenerErroresMovil(Map mapErrores) {
		List erroresFijos = new ArrayList();
		if (mapErrores != null) {
			Set setKey = mapErrores.keySet();
			if (setKey != null && setKey.size() > 0) {
				Iterator it = setKey.iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					String[] info = key.split("-");
					if (info != null && "M".equals(info[1])) {
						erroresFijos.add(mapErrores.get(key));
					}
				}
			}
		}
		return erroresFijos;
	}
	
	/**
	 * Devuelve el listado con los errores prepago
	 * 
	 * @param mapErrores
	 * @return
	 */
	public static List obtenerErroresPrepago(Map mapErrores) {
		List erroresFijos = new ArrayList();
		if (mapErrores != null) {
			Set setKey = mapErrores.keySet();
			if (setKey != null && setKey.size() > 0) {
				Iterator it = setKey.iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					String[] info = key.split("-");
					if (info != null && "P".equals(info[1])) {
						erroresFijos.add(mapErrores.get(key));
					}
				}
			}
		}
		return erroresFijos;
	}	

	/**
	 * Recupera los errores de reinyeccion que pudiera haber en el mapa
	 * Hace CASTING a QGErrorDTO y solo se usa para los QGCXXX que son de reinyeccion
	 * @param listErrores
	 * @return
	 */
	public static List obtenerErroresReinyeccion(Map listErrores) {
		List erroresFijosReinyeccion = new ArrayList();
		if (listErrores != null) {
			Set setKey = listErrores.keySet();
			if (setKey != null && setKey.size() > 0) {
				Iterator it = setKey.iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					String[] info = key.split("-");
					if (info != null
							&& ("F".equals(info[1]) || "M".equals(info[1]) || "P".equals(info[1]))) {

						QGErrorDTO error = (QGErrorDTO) listErrores.get(key);
						if (error.isErrorReinyeccion()) {
							erroresFijosReinyeccion.add(error);
						}
					}
				}
			}
		}
		return erroresFijosReinyeccion;
	}
	/**
	 * Recupera los errores de reinyeccion especificos
	 * Hace CASTING a QGErrorReinyeccionDTO
	 * @param listErrores
	 * @return
	 */
	public static List obtenerListadoErroresReinyeccion(Map listErrores) {
		List erroresFijosReinyeccion = new ArrayList();
		if (listErrores != null) {
			Set setKey = listErrores.keySet();
			if (setKey != null && setKey.size() > 0) {
				Iterator it = setKey.iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					

						QGErrorReinyeccionDTO error = (QGErrorReinyeccionDTO) listErrores.get(key);
						if (error.isErrorReinyeccion()) {
							erroresFijosReinyeccion.add(error);
						}
					
				}
			}
		}
		return erroresFijosReinyeccion;
	}

	/**
	 * Trata un string para mostrarlo correctamente
	 * @param campo
	 * @return
	 */
	public static String mostrarCampo(String campo) {
		if (StringUtils.isEmpty(campo)) {
			return "0";
		}

		return campo.trim();
	}

	/**
	 * Relanza todas las formulas de una excel
	 * @param libro
	 */
	public static void recalcularLibro(HSSFWorkbook libro) {
		if (libro != null) {
			for (int i = 0; i < libro.getNumberOfSheets(); i++) {
				HSSFSheet sheet = libro.getSheetAt(i);
				sheet.setForceFormulaRecalculation(true);
			}
		}
		
	}
	
	/**
	 * Oculta las columnas de las hojas del libro segun los dias que haya informados en el informe
	 * @param libro
	 * @param numDias
	 */
	public static void ocultarColumnas(HSSFWorkbook libro, Integer numDias) {
		//Las hojas 0,1,2 y 3
		if (libro != null) {
			int inicioOcultar = numDias.intValue() * 2 + 2;
			for (int i = 0; i < 4; i++) {
				// Ocultamos el sheet una serie de columnas
				HSSFSheet sheet = libro.getSheetAt(i);
				for (int j = inicioOcultar; j < 16; j++) {
					sheet.setColumnHidden(j, true);
				}

			}
		}
	}
	/**
	 * Oculta una hoja dado el indice que la representa (base-0)
	 * @param libro
	 * @param i
	 */
	public static void ocultarHoja(HSSFWorkbook libro, int i) {
		if (libro != null) {
			libro.setSheetHidden(i, true);
		}
	}
	/**
	 * Calcula el tanto por ciento entre dos numeros. 
	 *  - Si dividendo no esta informado devuelve 0
	 *  - Si divisor no esta informado o es 0 devuelve 100
	 *  
	 *  Se usa en los tantos por ciento de cabecera que aunque haya cero elementos hay que mostrar 100%
	 *  
	 * @param dividendo
	 * @param divisor
	 * @return
	 */
	public static Double tantoPorCiento(Integer dividendo, Integer divisor) {
		
		if(dividendo == null){
			dividendo = new Integer(0);
		}
		if(divisor == null || divisor.intValue() == 0){
			return new Double(100);
		}
		
		double res = dividendo.doubleValue() * 100 / divisor.doubleValue();
		
		//Redondeo 3 decimales
		BigDecimal resRound = new BigDecimal(res);
		resRound = resRound.setScale(3,BigDecimal.	ROUND_HALF_UP );
		
		return new Double(resRound.doubleValue());
	}
	
	/**
	 * Calcula el tanto por ciento entre dos numeros. 
	 *  - Si dividendo no esta informado devuelve 0
	 *  - Si dividendo es 0 devuelve 0
	 *  - Si divisor no esta informado o es 0 devuelve 100
	 *  
	 *  Se usa en los tantos por ciento que no son de cabecera ya que si hay cero elementos es un 0%
	 *  
	 * @param dividendo
	 * @param divisor
	 * @return
	 */
	public static Double tantoPorCientoBase0(Integer dividendo, Integer divisor) {
		
		if(dividendo == null){
			dividendo = new Integer(0);
		}
		if(dividendo.intValue() == 0){
			return new Double(0);
		}
		if(divisor == null || divisor.intValue() == 0){
			return new Double(100);
		}
		
		double res = dividendo.doubleValue() * 100 / divisor.doubleValue();
		
		//Redondeo 3 decimales
		BigDecimal resRound = new BigDecimal(res);
		resRound = resRound.setScale(3,BigDecimal.	ROUND_HALF_UP );
		
		return new Double(resRound.doubleValue());
	}
	/**
	 * Idem. que la anterior pero con redondeo a 2 decimales
	 *  
	 * @param dividendo
	 * @param divisor
	 * @return
	 */
	public static Double tantoPorCientoBase0Round2(Integer dividendo, Integer divisor) {
		
		if(dividendo == null){
			dividendo = new Integer(0);
		}
		if(dividendo.intValue() == 0){
			return new Double(0);
		}
		if(divisor == null || divisor.intValue() == 0){
			return new Double(100);
		}
		
		double res = dividendo.doubleValue() * 100 / divisor.doubleValue();
		
		//Redondeo 2 decimales
		BigDecimal resRound = new BigDecimal(res);
		resRound = resRound.setScale(2,BigDecimal.	ROUND_HALF_UP );
		
		return new Double(resRound.doubleValue());
	}
	/**
	 * FORMATEO DE UN ELEMENTO
	 *  
	 * @param datos
	 * @param decimales
	 * @return
	 */
	public static String formatoElemento(Object dato,int decimales){
		
		String salida = null;

		if (dato != null || dato != ""){
		
			if(dato instanceof Integer){
				Integer numero = (Integer) dato;
				
				NumberFormat numberFormatter;
	
				numberFormatter =  NumberFormat.getInstance(new Locale("es_ES"));
				
				salida = numberFormatter.format(numero);

				
			}
			else if(dato instanceof Double){
				Double numero = (Double) dato;
				
				DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
				simbolos.setDecimalSeparator(',');
				DecimalFormat formateador;
				
				if (decimales == 2)
					formateador = new DecimalFormat("0.00",simbolos);
				else
					formateador = new DecimalFormat("0.000",simbolos);
				
				salida = formateador.format(numero);
			}
			else{
				salida = "";
			}
		}
		else{
			salida = "";
		}
	    return salida;
	}
	
	
	/**
	 * FORMATEO DE ELEMENTOS DE LA LISTA
	 *  
	 * @param datos
	 * @param decimales
	 * @return
	 */
	public static List formatoLista(List datos,int decimales){
	    
		List salida = new ArrayList();
		for (int i=0;i<datos.size();i++){

			if (datos.get(i) != null || datos.get(i) != ""){
			
				if(datos.get(i) instanceof Integer){
					Integer numero = (Integer) datos.get(i);
					
					NumberFormat numberFormatter;
					String amountOut;

					numberFormatter =  NumberFormat.getInstance(new Locale("es_ES"));
					
					amountOut = numberFormatter.format(numero);
					
					salida.add(amountOut);
					
				}
				else if(datos.get(i) instanceof Double){
					Double numero = (Double) datos.get(i);
					
					DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
					simbolos.setDecimalSeparator(',');
					DecimalFormat formateador;
					
					if (decimales == 2)
						formateador = new DecimalFormat("0.00",simbolos);
					else
						formateador = new DecimalFormat("0.000",simbolos);
					
					salida.add(formateador.format(numero));
				}
				else{
					salida.add(null);
				}
				
			}
			else{
				salida.add(null);
			}
		}
	    return salida;
	}	
}
