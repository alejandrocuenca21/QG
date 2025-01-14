package com.telefonica.ssnn.qg.util;

import java.util.Properties;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Clase que permite acceder a las keys del propertie de errores de servicios
 * 
 * @author mgvinuesa, lgtello y rgsimon :)
 * 
 */
public class QGControlExcepcionesUtils {

	private static final Logger logger = Logger
			.getLogger(QGControlExcepcionesUtils.class);

	private static final String NOMBRE_FICHERO_PROPIEDADES = "QGexcepciones.properties";
	/**
	 * Instacia de la clase
	 */
	private static QGControlExcepcionesUtils instance;
	/**
	 * Fichero de propertie
	 */
	private static Properties ficheroProperties;

	/**
	 * Constructor
	 * 
	 * @throws NotificaBusinessException
	 * 
	 */
	private QGControlExcepcionesUtils() {
		// Si no esta inicializado se inicializa
		if (ficheroProperties == null) {

			ficheroProperties = new Properties();
			try {
				ficheroProperties.load(Thread.currentThread()
						.getContextClassLoader().getResource(
								NOMBRE_FICHERO_PROPIEDADES).openStream());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Retorna la instancia del singleton
	 * 
	 * @return instancia
	 */
	public static QGControlExcepcionesUtils getInstace() {
		if (instance == null) {
			instance = new QGControlExcepcionesUtils();
		}

		return instance;
	}

	/**
	 * Obtiene el mensaje de error asociado a la key.
	 * 
	 * @param codigoDescriptor
	 * @param codigoError
	 */
	public String getProperty(String codigoDescriptor, String codigoError) {
		try {
			String mensaje = ficheroProperties.getProperty(codigoDescriptor
					+ "." + codigoError);
			// Se le pasa el filtro para que se muestre en Html
			if (StringUtils.isNotBlank(mensaje)) {
				mensaje = StringEscapeUtils.escapeHtml(mensaje);
			}
			return mensaje;
		} catch (Exception e) {
			logger.error("No se ha encontrado un mensaje asociado a la key");
			return null;
		}

	}

}
