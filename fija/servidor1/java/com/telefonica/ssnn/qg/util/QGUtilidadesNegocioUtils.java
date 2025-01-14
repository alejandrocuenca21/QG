package com.telefonica.ssnn.qg.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.base.QGConstantes;
import com.telefonica.ssnn.qg.exceptions.QGApplicationException;

public class QGUtilidadesNegocioUtils {
	/**
	 * Campo de la excepcion donde viene el error.
	 */
	private static final String EXCEPCION_CAMPO = "CACV_VARMSG";

	private static final Logger logger = Logger
	.getLogger(QGUtilidadesNegocioUtils.class); 
	
	private static String SEPARADOR_MENSAJE_ERROR = ":";
	
	/**
	 * -803
	 */
	public static String CODIGO_ERROR_REGISTRO_EXISTE = "-803";
	/**
	 * +100
	 */
	public static String CODIGO_ERROR_NO_EXISTEN_REGISTROS = "+100"; 
	
	
	
	/**
	 * Metodo que retorna el codigo del error
	 * 
	 * -803 --> registro existe al insertar
	 * +100 --> busqueda, no existen registros
	 * 
	 * @return codigo del error que ha dado el servicio
	 */	
	public static String getCodigoError(String error){
		try{
			if(StringUtils.isNotBlank(error)){
				return error.split(SEPARADOR_MENSAJE_ERROR)[3].substring(0,4);
			}
		}catch (Exception e) {
			logger.error("El mensaje no se puede parsear por codigo");
		}
		return error;
	}
	
	/**
	 * Funcion que controla el tratamiento de excepciones para los servicios, como muchos servicios pueden tener el mismo codigo de error
	 * se le pasa el descriptor del servicio que ha lanzado la excepcion
	 * @param e
	 * @param mensaje
	 */
	public static void tratarExcepcionesServicio(NAWRException e, String codDescriptor) {
		
		//Mensaje que se asocia a la nueva excepcion
		String mensajeErrorServicio;
		//Si el campo de mensaje viene informado se intenta parsear.
		if(StringUtils.isNotBlank(e.getCampo(EXCEPCION_CAMPO))){
			String codigoError = QGUtilidadesNegocioUtils.getCodigoError(e.getCampo(EXCEPCION_CAMPO));
			
			//Con el descriptor del servicio y el codigo de error obtenemos el mensaje controlado
			if( StringUtils.isNotBlank(codDescriptor) && StringUtils.isNotBlank(codigoError) ){
				mensajeErrorServicio = QGControlExcepcionesUtils.getInstace().getProperty(codDescriptor,codigoError);
				
				//Si no se consigue mensaje
				if( StringUtils.isEmpty(mensajeErrorServicio)){
					mensajeErrorServicio = e.getCampo(EXCEPCION_CAMPO);
				}
			}else{
				//Como no se puede controlar metemos directamente el mensaje que tiene el campo del servicio
				mensajeErrorServicio = e.getCampo(EXCEPCION_CAMPO);
			}
			
		}else{
				mensajeErrorServicio = "Se ha producido un error no controlado en la capa de servicio";
		}
		
		
				
		
		
		throw new QGApplicationException(mensajeErrorServicio);
	}

	/**
	 * Método que aniade espacios al principio de un String
	 * @param valor String a modificar
	 * @param tamanioTotal tamanio final del String
	 * @return string modificado
	 */
	public static String rellenarEspaciosIzquierda(String valor, int tamanioTotal) {
		if(StringUtils.isNotEmpty(valor)){
			
			valor = StringUtils.leftPad(valor, tamanioTotal, " ");
		}
		return valor;
		
	}
	/**
	 * Si codigo 01 desc = LNF si codigo 02 desc = LNM sino devuelve lo que tenia
	 * @param codLineaNegocio
	 * @return
	 */
	public static String obtenerLineaNegocio(String codLineaNegocio) {
		if(StringUtils.isNotBlank(codLineaNegocio)){
			if(codLineaNegocio.compareTo("01") == 0){
				return QGConstantes.DESCRIPCION_UNIDAD_01;
			}else if(codLineaNegocio.compareTo("02") == 0){
				return QGConstantes.DESCRIPCION_UNIDAD_02;
			}else{
				return codLineaNegocio;
			}
		}else{
			return codLineaNegocio;
		}
	}
	
	
}
