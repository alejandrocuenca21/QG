package com.telefonica.ssnn.qg.utils;


/**
 * Aserciones.
 * @author jacangas
 *
 */
public class QGAssert {

	/**
	 * Comprueba que la cadena es nula o vacia.
	 * @param str cadena a comparar.
	 */
	public static void isBlank(String str) {
		isBlank(str, null);
	}
	
	/**
	 * Comprueba que la cadena es nula o vacia.
	 * @param str cadena a comparar.
	 * @param msg mensaje de la excepcion.
	 */
	public static void isBlank(String str, String msg) {
		
		if (QGStringHelper.isNotBlank(str)) {
			
			if (QGStringHelper.isBlank(msg)) {
				throw new IllegalArgumentException();
			} else {
				throw new IllegalArgumentException(msg);
			}
			
		}
		
	}
	
	/**
	 * Comprueba que la cadena no es nula o vacia.
	 * @param str cadena a comparar.
	 */
	public static void isNotBlank(String str) {
		isNotBlank(str, null);
	}
	
	/**
	 * Comprueba que la cadena no es nula o vacia.
	 * @param str cadena a comparar.
	 * @param msg mensaje de la excepcion.
	 */
	public static void isNotBlank(String str, String msg) {
		
		if (QGStringHelper.isBlank(str)) {
			
			if (QGStringHelper.isBlank(msg)) {
				throw new IllegalArgumentException();
			} else {
				throw new IllegalArgumentException(msg);
			}
			
		}
		
	}
	
}
