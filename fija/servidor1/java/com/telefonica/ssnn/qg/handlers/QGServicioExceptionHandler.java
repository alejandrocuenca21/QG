package com.telefonica.ssnn.qg.handlers;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import com.telefonica.ssnn.qg.exceptions.QGApplicationException;
import com.telefonica.ssnn.qg.exceptions.QGBusinessException;


/**
 * Manejador de excepciones del Servicio.
 * @author jacangas
 *
 */
public class QGServicioExceptionHandler implements MethodInterceptor {

	//private static final Logger logger = Logger.getLogger(ServicioExceptionHandler.class);
	
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
	
		try {
			
			return methodInvocation.proceed();
			
		} catch (QGBusinessException bEx) {
			//logger.debug(bEx);
			throw bEx;
			
		} catch (QGApplicationException aEx) {
			//logger.debug(aEx);
			throw aEx;
			
		} catch (Exception e) {
			//logger.debug(e);
			throw new QGApplicationException(e);
		}
		
	}

}
