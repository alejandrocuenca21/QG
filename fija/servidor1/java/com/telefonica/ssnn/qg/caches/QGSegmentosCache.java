package com.telefonica.ssnn.qg.caches;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.telefonica.ssnn.qg.util.QGCatalogoDto;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;


/**
 * 
 * Clase singleton para cachear los Segmentos 
 * los datos se guardan en un ArrayList
 * @author rgsimon
 *
 */
public class QGSegmentosCache {
	
	
	private static final Logger logger = Logger
	.getLogger(QGSegmentosCache.class);

	private static QGSegmentosCache segmentosCache;
	
	/**
	 * Listado de segmentos cacheado fijos
	 */
	private ArrayList segmentosFijos = null;
	
	/**
	 * Mapa de codigo / descripcion fijos
	 */
	private HashMap mapaSegmentosFijos;
	
	/**
	 * Listado de segmentos cacheado fijos
	 */
	private ArrayList segmentosMovil = null;
	
	/**
	 * Mapa de codigo / descripcion fijos
	 */
	private HashMap mapaSegmentosMovil;
	
	/**
	 * Fecha de inicializacion de la caché
	 */
	private static int fechaInicializacion;
	
	/**
	 * Constructor privado.
	 * crea la instancia de la clase y guarda la fecha en la que se ha creado. en formato yyyymmdd
	 * para comparar cuando se requiera una instancia.
	 */
	private QGSegmentosCache (){
				 		
		//guardamos la fecha de creación como un entero en formato yyyymmdd
		fechaInicializacion = Integer.parseInt(
				QGUtilidadesFechasUtils.fromDateToString(
					new Date(), 
					QGUtilidadesFechasUtils.FORMATO_FECHA_3));			
	}
	
	/**
	 * Devuelve la instancia de la clase comprobando si la fecha en la que se llama es mayor a la guardada
	 * para así crear una nueva instancia. 
	 * @return instancia de la clase
	 */
	public static QGSegmentosCache getInstance(){		
		//comprobamos si existe la instancia y si se creo antes de la fecha de hoy 
		//si no existe o se creo antes de hoy, creamos una nueva instancia.
		if(segmentosCache == null || Integer.parseInt(QGUtilidadesFechasUtils.fromDateToString(
				new Date(), 
				QGUtilidadesFechasUtils.FORMATO_FECHA_3))> fechaInicializacion){
			segmentosCache = new QGSegmentosCache();			 		
		}
		return segmentosCache;
	}
	
	/**
	 * Devuelve el listado de segmentos cacheados.
	 * @param fijos - true devuelve los fijos , false los moviles
	 * @return listado de segmentos cacheados.
	 */
	public ArrayList getSegmentos(Boolean fijos){
		if(fijos.equals(Boolean.TRUE)){
			return segmentosFijos;
		}else{
			return segmentosMovil;
		}
		
	}
	
	/**
	 * Setea la caché de segmentos
	 * @param segmentos Listado de segmentos a cachear.
	 * @param fijos - true devuelve los fijos , false los moviles
	 */
	public void setSegmentos(ArrayList segmentos, Boolean fijos){
		//Comprobamos que venga algun elemento sino, no se crea nada
		if(segmentos != null && segmentos.get(0) != null){
			if(fijos.equals(Boolean.TRUE)){
				this.segmentosFijos = segmentos; 
				this.mapaSegmentosFijos = new HashMap();
				for(int i=0; i<segmentos.size();i++){
					this.mapaSegmentosFijos.put(((QGCatalogoDto)segmentos.get(i)).getCodigo(), ((QGCatalogoDto)segmentos.get(i)).getDescripcion());
				}
			}else{
				this.segmentosMovil = segmentos; 
				this.mapaSegmentosMovil = new HashMap();
				for(int i=0; i<segmentos.size();i++){
					this.mapaSegmentosMovil.put(((QGCatalogoDto)segmentos.get(i)).getCodigo(), ((QGCatalogoDto)segmentos.get(i)).getDescripcion());
				}
			}
			
		}
			
		}
	/**
	 * Devuelve la descripcion asociada al codigo dado
	 * @param codigo
	 * @param fijos - true devuelve los fijos , false los moviles
	 * @return String
	 */
	public String getDescripcion(String codigo, Boolean fijos){ 
		if(fijos.equals(Boolean.TRUE)){
			if(this.mapaSegmentosFijos != null){
				return (String)this.mapaSegmentosFijos.get(codigo);
			}else{
				logger.error("No estan inicializadas las caches -- el servicio de carga de combos ha debido fallar");
				return "";
			}
		}else{
			if(this.mapaSegmentosMovil != null){
				return (String)this.mapaSegmentosMovil.get(codigo);
			}else{
				logger.error("No estan inicializadas las caches -- el servicio de carga de combos ha debido fallar");
				return "";
			}
		}
	}

	/**
	 * Devuelve la descripcion asociada al codigo dado para un segmento FIJO
	 * 
	 * (*) No eliminamos esta funcion ya que el desarrollo de segmentos moviles fue posterior y no se pueden modificar
	 *   las funciones que ya la llamaban.
	 *   
	 * @param codigo
	 * @return String
	 */
	public String getDescripcion(String codigo){ 
		if(this.mapaSegmentosFijos != null){
				return (String)this.mapaSegmentosFijos.get(codigo);
			}else{
				logger.error("No estan inicializadas las caches -- el servicio de carga de combos ha debido fallar");
				return "";
			}
	}
	
	/**
	 * Rellena las caches para fijo y movil, ya que el listado esta completamente lleno.
	 * @param listaDatos
	 */
	public void setRellenarCache(ArrayList listaDatos) {
		// TODO Auto-generated method stub
		
	}

	
	public ArrayList getSegmentosFijos() {
		return segmentosFijos;
	}

	public void setSegmentosFijos(ArrayList segmentosFijos) {
		this.segmentosFijos = segmentosFijos;
	}

	public HashMap getMapaSegmentosFijos() {
		return mapaSegmentosFijos;
	}

	public void setMapaSegmentosFijos(HashMap mapaSegmentosFijos) {
		this.mapaSegmentosFijos = mapaSegmentosFijos;
	}

	public ArrayList getSegmentosMovil() {
		return segmentosMovil;
	}

	public void setSegmentosMovil(ArrayList segmentosMovil) {
		this.segmentosMovil = segmentosMovil;
	}

	public HashMap getMapaSegmentosMovil() {
		return mapaSegmentosMovil;
	}

	public void setMapaSegmentosMovil(HashMap mapaSegmentosMovil) {
		this.mapaSegmentosMovil = mapaSegmentosMovil;
	}


	
	
}
