package com.telefonica.ssnn.qg.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

public class QGUtilidadesFechasUtils {
	/**
	 * yyyy-MM-dd
	 */
	private static final String FORMATO_TIMESTAMP_COPY = "yyyy-MM-dd";
	/**
	 * "dd.MM.yyyy"
	 */
	public static String FORMATO_FECHA_1 = "dd.MM.yyyy";
	/**
	 * "yyyy-MM-dd-HH.mm.ss.S"
	 */
	public static String FORMATO_FECHA_2 = "yyyy-MM-dd-HH.mm.ss.S";
	/**
	 * "yyyyMMdd"
	 */
	public static String FORMATO_FECHA_3 = "yyyyMMdd";
	/**
	 * "yyyyMMdd"
	 */
	private static String FORMATO_FECHA_COPY = "yyyyMMdd";
	/**
	 * yyyy-MM-dd
	 */
	public static String FORMATO_FECHA_4 = "yyyy-MM-dd";
	/**
	 * dd/MM/yyyy
	 */
	public static String FORMATO_FECHA_5 = "dd/MM/yyyy";

	/**
	 * Método para comparar fechas
	 * 
	 * @param fecha1
	 *            primera fecha
	 * @param fecha2
	 *            segunda fecha
	 * @param formato
	 *            formato en el que están las fechas
	 * @return resultado -1:fecha1>fecha2, 0:fecha1=fecha2, 1:fecha1<fecha2
	 */
	public static int compararFechas(String fecha1, String fecha2,
			String formato) {

		SimpleDateFormat sdf = new SimpleDateFormat(formato, Locale
				.getDefault());
		int resultado = -100;

		try {
			long date1 = sdf.parse(fecha1).getTime();
			long date2 = sdf.parse(fecha2).getTime();
			if (date1 > date2) {
				resultado = -1;
			} else if (date1 == date2) {
				resultado = 0;
			} else {
				resultado = 1;
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return resultado;

	}

	/**
	 * Método que valida si el formato de una fecha es el esperado
	 * 
	 * @param fecha
	 *            Fecha a comprobar
	 * @param formato
	 *            Formato esperado
	 * @return booleano con el resultado true:formato correcto, false:formato
	 *         incorrecto
	 */
	public static boolean validarFormatoFecha(String fecha, String formato) {

		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		sdf.setLenient(false);
		boolean resultado = true;
		if (fecha.length() != formato.length()) {
			resultado = false;
		}
		try {
			sdf.parse(fecha);
		} catch (ParseException e) {
			resultado = false;
		}
		return resultado;
	}

	/**
	 * Método que formatea una fecha del formato de la copy al que entra por
	 * parametro
	 * 
	 * @param fecha
	 *            Fecha a formatear
	 * @param formato
	 *            Formato de salida
	 * @return String Fecha formateada
	 */
	public static String formatearFechaDesdeCopy(String fecha, String formato) {

		String res = null;
		SimpleDateFormat sdfCopy = new SimpleDateFormat(FORMATO_FECHA_COPY);

		SimpleDateFormat sdfSalida = new SimpleDateFormat(formato);
		Date date = null;

		try {
			date = sdfCopy.parse(fecha);
			res = sdfSalida.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	/**
	 * Método que formatea una fecha del formato que le entra por parametro al
	 * formato de la copy
	 * 
	 * @param fecha
	 *            fecha a formatear
	 * @param formato
	 *            Formato en que viene el parametro fecha
	 * @return Fecha en el formato de la copy
	 */
	public static String formatearFechaParaCopy(String fecha, String formato) {

		if (StringUtils.isNotBlank(fecha)) {
			SimpleDateFormat sdfCopy = new SimpleDateFormat(FORMATO_FECHA_COPY);
			SimpleDateFormat sdfEntrada = new SimpleDateFormat(formato);
			Date date = null;
			try {
				date = sdfEntrada.parse(fecha);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return sdfCopy.format(date);
		} else {
			return null;
		}
	}

	/**
	 * Convierte la fechade un Date a un String con el formato introducido.
	 * 
	 * @param fecha
	 *            Fecha a convertir
	 * @param formato
	 *            Formato de salida
	 * @return fecha formateada
	 */

	public static String fromDateToString(Date fecha, String formato) {

		SimpleDateFormat sdfSalida = new SimpleDateFormat(formato);

		return sdfSalida.format(fecha);
	}

	/**
	 * Convierte una cadena fecha en el date.
	 * 
	 * @param fecha
	 * @param formato
	 * @return
	 */
	public static Date fromStringToDate(String fecha, String formato) {
		try {
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat(formato);
			return formatoDeFecha.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	/**
	 * 
	 * @param valorAsString
	 * @param formato_fecha_12
	 * @return
	 */
	public static String formatearTimeStampDesdeCopy(String fecha,
			String formato) {
		String res = null;
		SimpleDateFormat sdfCopy = new SimpleDateFormat(FORMATO_TIMESTAMP_COPY);

		SimpleDateFormat sdfSalida = new SimpleDateFormat(formato);
		Date date = null;

		try {
			date = sdfCopy.parse(fecha);
			res = sdfSalida.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return res;
	}
	/**
	 * Obtiene el Date del timestamp que devuelve la copy.
	 * @param fecha
	 * @return
	 */
	public static Date obtenerTimeStampDesdeCopy(String fecha) {
		SimpleDateFormat sdfCopy = new SimpleDateFormat(FORMATO_FECHA_2);
		Date date = null;
		try {
			date = sdfCopy.parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * Restamos un día a una fecha
	 */
	public static String restarDias (String fecha,int dias){
		
		DateFormat df = new SimpleDateFormat(FORMATO_FECHA_3); 
		
		String salida = "";
		
		try {
			Date d = df.parse(fecha);
			d.setTime( d.getTime() - dias*1000*60*60*numHorasDia(fecha) );
			DateFormat formatter = new SimpleDateFormat(FORMATO_FECHA_3);
			salida = formatter.format(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return salida;
	}
	
	/**
	 * Sumamos un día a una fecha
	 */
	public static String sumarDias (String fecha,int dias){
		
		DateFormat df = new SimpleDateFormat(FORMATO_FECHA_1); 
		
		String salida = "";
		
		try {
			Date d = df.parse(fecha);
			d.setTime( d.getTime() + dias*1000*60*60*numHorasDia(fecha) );
			DateFormat formatter = new SimpleDateFormat(FORMATO_FECHA_1);
			salida = formatter.format(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return salida;
	}
	
	/**
	 * Calculamos el numero de horas del día, dependiendo de si hay cambio horario o no
	 */
	public static int numHorasDia(String fecha){

        Calendar fec = convertStringToCalendar(fecha);
        
        int resultado=24; //Por defecto

        try{
            //con el año de la fecha de entrada saco los 2 dias de cambio horario
        	int anyo=fec.get(Calendar.YEAR);
            int mesVerano=Calendar.MARCH;
            int mesInvierno=Calendar.OCTOBER;
            int horaInvierno=2;
            int horaVerano=3;
            int cero=0;
            int diaCandidato=31;
            Calendar cambioInvierno=Calendar.getInstance();
            Calendar cambioVerano=Calendar.getInstance();
            

            cambioInvierno.set(anyo, mesInvierno, diaCandidato, horaInvierno, cero, cero);
            cambioInvierno.set(Calendar.MILLISECOND, cero);

            cambioVerano.set(anyo, mesVerano, diaCandidato, horaVerano, cero, cero);
            cambioVerano.set(Calendar.MILLISECOND, cero);

            //Se obtiene la fecha de cambio de hora invierno exacta
          while(cambioInvierno.get(Calendar.DAY_OF_WEEK)!=Calendar.SUNDAY)
          {
          	diaCandidato--;
              cambioInvierno.set(Calendar.DAY_OF_MONTH,diaCandidato);
          }

          //Se obtiene la fecha de cambio de hora verano exacta
          diaCandidato=31;

          while(cambioVerano.get(Calendar.DAY_OF_WEEK)!=Calendar.SUNDAY)
          {
          	diaCandidato--;
              cambioVerano.set(Calendar.DAY_OF_MONTH,diaCandidato);
          }

          //Se comprueba el cambio de hora de invierno es igual a la fecha de entrada
          if(convertCalendarToString(fec).equals(convertCalendarToString(cambioInvierno)))
          		resultado=25;
          //Se comprueba el cambio de hora de verano es igual a la fecha de entrada
          if(convertCalendarToString(fec).equals(convertCalendarToString(cambioVerano)))
          		resultado=23;

        }
        catch(Throwable e){
      	  e.printStackTrace();
        }
        return resultado;
	}
	/**
	 * Convierte de String a Calendar 
	 */
	public static Calendar convertStringToCalendar(String fecha){
		
		Calendar cal=Calendar.getInstance();
		
		try {  
			DateFormat formatter ; 
			Date date ; 
			formatter = new SimpleDateFormat(FORMATO_FECHA_1);
			date = (Date)formatter.parse(fecha); 
	 
			cal.setTime(date);
		 	
		 }catch (ParseException e){
			 e.printStackTrace();
		 }
		return cal; 
	} 

	public static String convertCalendarToString(Calendar fecha){
		
		String strdate = null;
	
		SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA_1);

		if (fecha != null) {
			strdate = sdf.format(fecha.getTime());
		}
		return strdate;
	}	

}