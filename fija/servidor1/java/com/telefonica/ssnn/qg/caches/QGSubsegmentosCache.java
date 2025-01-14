package com.telefonica.ssnn.qg.caches;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.telefonica.ssnn.qg.util.QGCatalogoDto;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;

/**
 * 
 * Clase singleton para cachear los subsegmentos que pertenecen a un segmento.
 * los datos se guardan en un hasmap donde la key es el codigo del segmento y el
 * value es el listado de los subsegmentos.
 * 
 * @author rgsimon
 * 
 */
public class QGSubsegmentosCache {

	private HashMap mapaArraysFijos = new HashMap();

	private HashMap mapaDescripcionesFijos = new HashMap();

	private HashMap mapaArraysMovil = new HashMap();

	private HashMap mapaDescripcionesMovil = new HashMap();

	private static QGSubsegmentosCache subsegmentosCache;
	/**
	 * Fecha de inicializacion de la caché
	 */
	private static int fechaInicializacion;

	/**
	 * Constructor privado. crea la instancia de la clase y guarda la fecha en
	 * la que se ha creado. en formato yyyymmdd para comparar cuando se requiera
	 * una instancia.
	 */
	private QGSubsegmentosCache() {

		// guardamos la fecha de creación como un entero en formato yyyymmdd
		fechaInicializacion = Integer.parseInt(QGUtilidadesFechasUtils
				.fromDateToString(new Date(),
						QGUtilidadesFechasUtils.FORMATO_FECHA_3));

	}

	/**
	 * Devuelve la instancia de la clase comprobando si la fecha en la que se
	 * llama es mayor a la guardada para así crear una nueva instancia.
	 * 
	 * @return instancia de la clase
	 */
	public static QGSubsegmentosCache getInstance() {

		// comprobamos si existe la instancia y si se creo antes de la fecha de
		// hoy
		// si no existe o se creo antes de hoy, creamos una nueva instancia.
		if (subsegmentosCache == null
				|| Integer.parseInt(QGUtilidadesFechasUtils.fromDateToString(
						new Date(), QGUtilidadesFechasUtils.FORMATO_FECHA_3)) > fechaInicializacion) {
			subsegmentosCache = new QGSubsegmentosCache();
		}
		return subsegmentosCache;
	}

	/**
	 * Método que devuelve el listado de subsegmentos en funcion del codigo de
	 * segmento introducido si existe en el mapa utilizado para caché.
	 * 
	 * @param codigoSegmento
	 *            Codigo de segmento utilizado para obtener los subsegmentos.
	 * @return subsegmentos del segmento si existen, si no existen, null
	 */
	public ArrayList getSubSegmentos(String codigoSegmento, Boolean fijos) {
		if (fijos.equals(Boolean.TRUE)) {
			return (ArrayList) mapaArraysFijos.get(codigoSegmento);
		} else {
			return (ArrayList) mapaArraysMovil.get(codigoSegmento);
		}
	}

	/**
	 * Método que guarda un elemento del mapa de cache donde la key es el codigo
	 * del segmento y el resultado es el listado de subsegmentos.
	 * 
	 * @param codigoSegmento
	 *            Codigo del segmento
	 * @param listadoSubsegmentos
	 *            Listado de subsegmentos del segmento
	 */
	public void setSubsegmentos(String codigoSegmento,
			List listadoSubsegmentos, Boolean fijos) {
		if (fijos.equals(Boolean.TRUE)) {
			mapaArraysFijos.put(codigoSegmento, listadoSubsegmentos);
			for (int i = 0; i < listadoSubsegmentos.size(); i++) {
				mapaDescripcionesFijos.put(codigoSegmento
						+ "-"
						+ ((QGCatalogoDto) listadoSubsegmentos.get(i))
								.getCodigo(),
						((QGCatalogoDto) listadoSubsegmentos.get(i))
								.getDescripcion());
			}
		} else {
			mapaArraysMovil.put(codigoSegmento, listadoSubsegmentos);
			for (int i = 0; i < listadoSubsegmentos.size(); i++) {
				mapaDescripcionesMovil.put(codigoSegmento
						+ "-"
						+ ((QGCatalogoDto) listadoSubsegmentos.get(i))
								.getCodigo(),
						((QGCatalogoDto) listadoSubsegmentos.get(i))
								.getDescripcion());
			}
		}

	}

	/**
	 * Devuelve la descripcion asociado
	 * 
	 * @param codigoSegmento
	 * @param codigoSubSegmento
	 * @return
	 */
	public String getDescripcion(String codigoSegmento,
			String codigoSubSegmento, Boolean fijos) {
		if (fijos.equals(Boolean.TRUE)) {
			return (String) mapaDescripcionesFijos.get(codigoSegmento + "-"
					+ codigoSubSegmento);
		} else {
			return (String) mapaDescripcionesMovil.get(codigoSegmento + "-"
					+ codigoSubSegmento);
		}
	}

	/**
	 * Devuelve la descripcion asociado para un FIJO
	 * 
	 * @param codigoSegmento
	 * @param codigoSubSegmento
	 * @return
	 */
	public String getDescripcion(String codigoSegmento, String codigoSubSegmento) {
		return (String) mapaDescripcionesFijos.get(codigoSegmento + "-"
				+ codigoSubSegmento);

	}
}