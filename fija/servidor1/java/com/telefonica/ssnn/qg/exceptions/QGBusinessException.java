package com.telefonica.ssnn.qg.exceptions;

/**
 * Exception generica de negocio.
 * @author jacangas
 *
 */
public class QGBusinessException extends RuntimeException {
	
	private static final long serialVersionUID = -309577513491793310L;
	
	/**
	 * Constructor por defecto.
	 */
	public QGBusinessException() {
		
	}
	
	/**
	 * Constructor con mensaje.
	 * @param message mensaje.
	 */
	public QGBusinessException(String message) {
		super(message);
	}
	
	/**
	 * Constructor con causa.
	 * @param cause causa
	 */
	public QGBusinessException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor con mensaje y causa.
	 * @param message mensaje
	 * @param cause causa
	 */
	public QGBusinessException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
