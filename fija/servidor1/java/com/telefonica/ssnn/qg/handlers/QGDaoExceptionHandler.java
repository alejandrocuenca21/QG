package com.telefonica.ssnn.qg.handlers;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.exceptions.QGApplicationException;
import com.telefonica.ssnn.qg.exceptions.QGBusinessException;


/**
 * Manejador de excepciones del DAO.
 * @author jacangas
 *
 */
public class QGDaoExceptionHandler implements MethodInterceptor {

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		
		try {
			
			return methodInvocation.proceed();
			
		} catch (NAWRException nawrEx) {
			throw new QGBusinessException(nawrEx);
		} catch (QGApplicationException e) {
			throw e;
		} catch (Exception e) {
			throw new QGApplicationException(e);
		}
		
	}

}
