package com.telefonica.ssnn.qg.exceptions;


/**
 * Excepcion de aplicacion generica.
 * @author jacangas
 *
 */
public class QGApplicationException extends RuntimeException {
	
	private static final long serialVersionUID = -309577513491793310L;
	
	
	
	/**
	 * Constructor por defecto.
	 */
	public QGApplicationException() {
		
	}
	
	/**
	 * Constructor con mensaje.
	 * @param message mensaje.
	 */
	public QGApplicationException(String message) {
		super(message);
	}
	
	/**
	 * Constructor con causa.
	 * @param cause causa
	 */
	public QGApplicationException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor con mensaje y causa.
	 * @param message mensaje
	 * @param cause causa
	 */
	public QGApplicationException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
