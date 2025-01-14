package com.telefonica.ssnn.qg.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Clase de utilidades con Strings.
 * @author jacangas
 *
 */
public class QGStringHelper extends StringUtils {

	/**
	 * Compara de manera segura (null safe) si dos cadenas son iguales.
	 * @param strOr cadena a comparar
	 * @param strDest cadena a comparar
	 */
	public static boolean isEquals(String strOr, String strDest) {
		
		if (strOr == strDest) {
			return true;
		}
		
		if ((strOr == null && strDest != null)
				|| (strOr != null && strDest == null)) {
			return false;
		}
		
		return strOr.equals(strDest);
		
	}
	
	/**
	 * Compara de manera segura (null safe) si dos cadenas son iguales,
	 * ignorando mayusculas y minusculas.
	 * @param strOr cadena a comparar
	 * @param strDest cadena a comparar
	 */
	public static boolean isEqualsIgnoreCase(String strOr, String strDest) {
		
		if (strOr == strDest) {
			return true;
		}
		
		if ((strOr == null && strDest != null)
				|| (strOr != null && strDest == null)) {
			return false;
		}
		
		return strOr.equalsIgnoreCase(strDest);
		
	}
	
}
