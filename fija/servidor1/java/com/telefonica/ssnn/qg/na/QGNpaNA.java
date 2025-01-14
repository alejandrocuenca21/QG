//<!--$Id: NpaNA.java, v1.3 01/12/2009 11:57:24 pjpepe Tran $-->
//<!--$Codigo: PAAT000054(I08060) - Se han agregado cambios pedidos por el cliente $-->
package com.telefonica.ssnn.qg.na;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.telefonica.na.NACampo;
import com.telefonica.na.NAServicio;
import com.telefonica.na.NAWRException;

import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.utils.QGAssert;
import com.telefonica.ssnn.qg.utils.QGStringHelper;



/**
 * Wrapper de servicios NA. Implementa metodos utiles para la creacion de
 * servicios NA.
 * 
 * @author jacangas
 * 
 */
public class QGNpaNA {
	
	
	 

	private static final String NOMBRE_OBLIGATORIO = "El nombre del campo es obligatorio";

	private static final String RANGO_INDICE_ERRONEO = "El indice debe ser mayor o igual a 1";

	private static final SimpleDateFormat NA_FORMAT_8 = new SimpleDateFormat("yyyyMMdd");

	private static final SimpleDateFormat NA_FORMAT_10 = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final SimpleDateFormat NA_FORMAT_23 = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.S");

	/**
	 * Servicio NA.
	 */
	private NAServicio servicio;

	/**
	 * Crea un servicio NA indicando un nombre de transaccion.
	 * Se debe usar la funcion obtener servicio.
	 * 
	 * @param sNombreTransaccion - nombre de la transaccion.
	 * @throws NAWRException - NAWRException
	 * @deprecated
	 * Se debe usar la funcion obtener servicio.
	 */
	
	public QGNpaNA(String sNombreTransaccion) throws NAWRException {

		servicio = new NAServicio(sNombreTransaccion);

	}

	

	/**
	 * devuelve una instancia de un servicio
	 * @param descriptor
	 * @param usuario
	 * @return
	 * @throws NAWRException 
	 */
	public static QGNpaNA obtenerServicio(String descriptor,QGUsuario usuario) throws NAWRException{
		 
		QGNpaNA servicio = new QGNpaNA (descriptor);
		servicio.setValor("NACabAnagramaLlamante", "QG");
		servicio.setValor("CACV_USUARIO_SESION", usuario.getUsername());

		return servicio;
				
	}
	
	/**
	 * devuelve una instancia de un servicio de GF
	 * @param descriptor
	 * @param usuario
	 * @return
	 * @throws NAWRException 
	 */
	public static QGNpaNA obtenerServicioGF(String descriptor) throws NAWRException{
		
		QGNpaNA servicio = new QGNpaNA (descriptor);
		servicio.setValor("NACabAnagramaLlamante", "QG");
		
		return servicio;		
	}
	
	/**
	 * Inserta un campo.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param sValor - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, String sValor) throws NAWRException {
		
		setValor(sNombreCampo, sValor, false);	
	}	
	
	/**
	 * Inserta un campo.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param sValor - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, String sValor, boolean allowDownCase) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (sValor != null) {
			if(allowDownCase) {
				servicio.setCampo(sNombreCampo, new NACampo(sValor));
			} else {
				servicio.setCampo(sNombreCampo, new NACampo(sValor.toUpperCase()));
			}
		}	
	}

	
	/**
	 * Inserta un campo.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param iValor - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, Integer iValor) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (iValor != null) {
			servicio.setCampo(sNombreCampo, new NACampo(iValor.intValue()));
		}
	}
	
	

	/**
	 * Inserta un campo.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param dValor - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, Double dValor) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (dValor != null) {
			servicio.setCampo(sNombreCampo, new NACampo(dValor.doubleValue()));
		}
	}

	/**
	 * Inserta un campo.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param fValor - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, Float fValor) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (fValor != null) {
			servicio.setCampo(sNombreCampo, new NACampo(fValor.floatValue()));
		}
	}

	/**
	 * Inserta un campo.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param shrValor - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, Short shrValor) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (shrValor != null) {
			servicio.setCampo(sNombreCampo, new NACampo(shrValor.shortValue()));
		}
	}

	/**
	 * Inserta un campo.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param lValor - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, Long lValor) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (lValor != null) {
			servicio.setCampo(sNombreCampo, new NACampo(lValor.longValue()));
		}
	}
	
	/**
	 * Inserta un campo de tipo fecha con el formato indicado.
	 * @param sNombreCampo - nombre del campo.
	 * @param dValor - valor del campo.
	 * @param formato - formato de la fecha.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, Date dValor, String formato) throws NAWRException {
		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);
		QGAssert.isNotBlank(formato, "Formato obligatorio");
		
		if (dValor != null) {
			SimpleDateFormat df = new SimpleDateFormat(formato);
			
			servicio.setCampo(sNombreCampo, df.format(dValor));
		}
	}

	/**
	 * Inserta un campo.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param sValor - valor del campo.
	 * @param indice - indice.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, String sValor, int indice) throws NAWRException {
		
		setValor(sNombreCampo, sValor, indice, false);
	}
	
	/**
	 * Inserta un campo.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param sValor - valor del campo.
	 * @param indice - indice.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, String sValor, int indice, boolean allowDownCase) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (indice < 1) {
			throw new IllegalArgumentException(RANGO_INDICE_ERRONEO);
		}
		
		if (sValor != null) {
			if (allowDownCase) {
				servicio.setCampo(sNombreCampo, sValor, indice);
			} else {
				servicio.setCampo(sNombreCampo, sValor.toUpperCase(), indice);
			}
		}		
	}
	
	
	/**
	 * Inserta un campo.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param iValor - valor del campo.
	 * @param indice - indice.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, Integer iValor, int indice) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (indice < 1) {
			throw new IllegalArgumentException(RANGO_INDICE_ERRONEO);
		}

		if (iValor != null) {
			servicio.setCampo(sNombreCampo, iValor.intValue(), indice);
		}

	}

	/**
	 * Inserta un campo.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param dValor - valor del campo.
	 * @param indice - indice.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, Double dValor, int indice) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (indice < 1) {
			throw new IllegalArgumentException(RANGO_INDICE_ERRONEO);
		}

		if (dValor != null) {
			servicio.setCampo(sNombreCampo, dValor.doubleValue(), indice);
		}

	}

	/**
	 * Inserta un campo.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param fValor - valor del campo.
	 * @param indice - indice.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, Float fValor, int indice) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (indice < 1) {
			throw new IllegalArgumentException(RANGO_INDICE_ERRONEO);
		}

		if (fValor != null) {
			servicio.setCampo(sNombreCampo, fValor.floatValue(), indice);
		}

	}

	/**
	 * Inserta un campo.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param shrValor - valor del campo.
	 * @param indice - indice.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, Short shrValor, int indice) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (indice < 1) {
			throw new IllegalArgumentException(RANGO_INDICE_ERRONEO);
		}

		if (shrValor != null) {
			servicio.setCampo(sNombreCampo, shrValor.shortValue(), indice);
		}

	}

	/**
	 * Inserta un campo.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param lValor - valor del campo.
	 * @param indice - indice.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, Long lValor, int indice) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (indice < 1) {
			throw new IllegalArgumentException(RANGO_INDICE_ERRONEO);
		}

		if (lValor != null) {
			servicio.setCampo(sNombreCampo, lValor.longValue(), indice);
		}

	}

	/**
	 * Inserta un campo.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param fecha - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, Date fecha) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (fecha != null) {
			servicio.setCampo(sNombreCampo, NA_FORMAT_8.format(fecha));
		}
	}

	/**
	 * Inserta un campo.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param fecha - valor del campo.
	 * @param indice - indice.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, Date fecha, int indice) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (indice < 1) {
			throw new IllegalArgumentException(RANGO_INDICE_ERRONEO);
		}

		if (fecha != null) {
			servicio.setCampo(sNombreCampo, NA_FORMAT_8.format(fecha), indice);
		}
	}
	
	/**
	 * Inserta un campo de tipo fecha con el formato indicado.
	 * @param sNombreCampo - nombre del campo.
	 * @param dValor - valor del campo.
	 * @param formato - formato de la fecha.
	 * @param indice - indice.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, Date dValor, int indice, String formato) throws NAWRException {
		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);
		QGAssert.isNotBlank(formato, "Formato obligatorio");
		
		if (dValor != null) {
			SimpleDateFormat df = new SimpleDateFormat(formato);
			
			servicio.setCampo(sNombreCampo, df.format(dValor), indice);
		}
	}
	
	public void setValor(String sNombreCampo, String sValor, int indice, int indice2) throws NAWRException {

		setValor(sNombreCampo, sValor, indice, indice2, false);

	}
	
	/**
	 * Inserta un campo.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param sValor - valor del campo.
	 * @param indice - indice.
	 * @throws NAWRException - NAWRException.
	 */
	public void setValor(String sNombreCampo, String sValor, int indice, int indice2, boolean allowDownCase) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (indice < 1) {
			throw new IllegalArgumentException(RANGO_INDICE_ERRONEO);
		}

		if (sValor != null) {
			if(allowDownCase){
				servicio.setCampo(sNombreCampo, sValor, indice, indice2);
			} else {
				servicio.setCampo(sNombreCampo, sValor.toUpperCase(), indice, indice2);
			}
		}
				
	}
		

	/**
	 * Obtiene el valor de un campo como cadena de texto.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @return String - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public String getValorAsString(String sNombreCampo) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo);

		return naCampo.toString().trim();

	}
	
	/**
	 * Obtiene el valor de un campo como cadena de texto, sin utilizar el trim al final de la cadena.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @return String - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public String getValorAsStringNoTrim(String sNombreCampo) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo);

		return naCampo.toString();

	}

    /**
     * Obtiene el valor de un campo como cadena de texto, sin utilizar el trim al final de la cadena.
     * 
     * @param sNombreCampo - nombre del campo.
     * @param indice - indice del campo.
     * @return String - valor del campo.
     * @throws NAWRException - NAWRException.
     */
    public String getValorAsStringNoTrim(String sNombreCampo, int indice) throws NAWRException {

        QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

        if (indice < 1) {
            throw new IllegalArgumentException(RANGO_INDICE_ERRONEO);
        }

        NACampo naCampo = servicio.getCampo(sNombreCampo, indice);

        return naCampo.toString();

    }
	
	/**
	 * Obtiene el valor de un campo como cadena de texto.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice - indice del campo.
	 * @return String - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public String getValorAsString(String sNombreCampo, int indice) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (indice < 1) {
			throw new IllegalArgumentException(RANGO_INDICE_ERRONEO);
		}

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice);

		return naCampo.toString().trim();

	}

	/**
	 * Obtiene el valor de un campo como cadena de texto.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice1 - indice del campo.
	 * @param indice2 - indice del campo.
	 * @return String - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public String getValorAsString(String sNombreCampo, int indice1, int indice2) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (indice1 < 1 || indice2 < 1) {
			throw new IllegalArgumentException(RANGO_INDICE_ERRONEO);
		}

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice1, indice2);

		return naCampo.toString().trim();

	}

	/**
	 * Obtiene el valor de un campo como cadena de texto.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice1 - indice del campo.
	 * @param indice2 - indice del campo.
	 * @param indice3 - indice del campo.
	 * @return String - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public String getValorAsString(String sNombreCampo, int indice1, int indice2, int indice3) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		if (indice1 < 1 || indice2 < 1 || indice3 < 1) {
			throw new IllegalArgumentException(RANGO_INDICE_ERRONEO);
		}

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice1, indice2, indice3);

		return naCampo.toString().trim();

	}

	/**
	 * Obtiene el valor de un campo como int.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @return Integer - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Integer getValorAsInt(String sNombreCampo) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Integer(naCampo.intValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como int.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice - indice del campo.
	 * @return int - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Integer getValorAsInt(String sNombreCampo, int indice) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Integer(naCampo.intValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como int.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice1 - indice del campo.
	 * @param indice2 - indice del campo.
	 * @return int - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Integer getValorAsInt(String sNombreCampo, int indice1, int indice2) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice1, indice2);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Integer(naCampo.intValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como int.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice1 - indice del campo.
	 * @param indice2 - indice del campo.
	 * @param indice3 - indice del campo.
	 * @return int - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Integer getValorAsInt(String sNombreCampo, int indice1, int indice2, int indice3) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice1, indice2, indice3);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Integer(naCampo.intValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como float.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @return float - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Float getValorAsFloat(String sNombreCampo) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Float(naCampo.floatValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como float.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice - indice del campo.
	 * @return float - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Float getValorAsFloat(String sNombreCampo, int indice) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Float(naCampo.floatValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como float.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice1 - indice del campo.
	 * @param indice2 - indice del campo.
	 * @return float - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Float getValorAsFloat(String sNombreCampo, int indice1, int indice2) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice1, indice2);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Float(naCampo.floatValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como float.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice1 - indice del campo.
	 * @param indice2 - indice del campo.
	 * @param indice3 - indice del campo.
	 * @return float - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Float getValorAsFloat(String sNombreCampo, int indice1, int indice2, int indice3) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice1, indice2, indice3);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Float(naCampo.floatValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como long.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @return long - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Long getValorAsLong(String sNombreCampo) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Long(naCampo.longValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como long.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice - indice del campo.
	 * @return long - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Long getValorAsLong(String sNombreCampo, int indice) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Long(naCampo.longValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como long.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice1 - indice del campo.
	 * @param indice2 - indice del campo.
	 * @return long - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Long getValorAsLong(String sNombreCampo, int indice1, int indice2) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice1, indice2);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Long(naCampo.longValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como long.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice1 - indice del campo.
	 * @param indice2 - indice del campo.
	 * @param indice3 - indice del campo.
	 * @return long - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Long getValorAsLong(String sNombreCampo, int indice1, int indice2, int indice3) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice1, indice2, indice3);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Long(naCampo.longValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como double.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @return double - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Double getValorAsDouble(String sNombreCampo) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Double(naCampo.doubleValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como double.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice - indice del campo.
	 * @return double - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Double getValorAsDouble(String sNombreCampo, int indice) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Double(naCampo.doubleValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como double.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice1 - indice del campo.
	 * @param indice2 - indice del campo.
	 * @return double - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Double getValorAsDouble(String sNombreCampo, int indice1, int indice2) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice1, indice2);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Double(naCampo.doubleValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como double.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice1 - indice del campo.
	 * @param indice2 - indice del campo.
	 * @param indice3 - indice del campo.
	 * @return double - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Double getValorAsDouble(String sNombreCampo, int indice1, int indice2, int indice3) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice1, indice2, indice3);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Double(naCampo.doubleValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como short.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @return short - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Short getValorAsShort(String sNombreCampo) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Short(naCampo.shortValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como short.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice - indice del campo.
	 * @return short - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Short getValorAsShort(String sNombreCampo, int indice) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Short(naCampo.shortValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como short.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice1 - indice del campo.
	 * @param indice2 - indice del campo.
	 * @return short - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Short getValorAsShort(String sNombreCampo, int indice1, int indice2) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice1, indice2);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Short(naCampo.shortValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como short.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice1 - indice del campo.
	 * @param indice2 - indice del campo.
	 * @param indice3 - indice del campo.
	 * @return short - valor del campo.
	 * @throws NAWRException - NAWRException.
	 */
	public Short getValorAsShort(String sNombreCampo, int indice1, int indice2, int indice3) throws NAWRException {

		QGAssert.isNotBlank(sNombreCampo, NOMBRE_OBLIGATORIO);

		NACampo naCampo = servicio.getCampo(sNombreCampo, indice1, indice2, indice3);

		return ((QGStringHelper.isNotBlank(naCampo.toString())) ? new Short(naCampo.shortValue()) : null);

	}

	/**
	 * Obtiene el valor de un campo como Date.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @return Date - valor del campo.
	 * @throws NAWRException - NAWRException.
	 * 
	 */
	public Date getValorAsDate(String sNombreCampo) throws NAWRException {

		String valor = getValorAsString(sNombreCampo);

		try {

			Date fecha = null;

			if (valor.length() == 8) {
				fecha = NA_FORMAT_8.parse(valor);
			} else if (valor.length() == 10) {
				fecha = NA_FORMAT_10.parse(valor);
			}else if(valor.length() == 26){
				fecha = NA_FORMAT_23.parse(valor.substring(0, 23));
			}

			return fecha;

		} catch (ParseException pEx) {
			return null;
		}

	}

	/**
	 * Obtiene el valor de un campo como Date.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @return Date - valor del campo.
	 * @throws NAWRException - NAWRException.
	 * 
	 */
	public Date getValorAsDate(String sNombreCampo, String tipoFormato) throws NAWRException {

		String valor = getValorAsString(sNombreCampo);

		try {

			Date fecha = null;
			SimpleDateFormat formatoFecha = new SimpleDateFormat(tipoFormato);
			fecha = formatoFecha.parse(valor);

			return fecha;

		} catch (ParseException pEx) {
			return null;
		}

	}

	/**
	 * Obtiene el valor de un campo como Date.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @return Date - valor del campo.
	 * @throws NAWRException - NAWRException.
	 * 
	 */
	public Date getValorAsDate(String sNombreCampo, int indice, String tipoFormato) throws NAWRException {

		String valor = getValorAsString(sNombreCampo, indice);

		try {

			Date fecha = null;
			SimpleDateFormat formatoFecha = new SimpleDateFormat(tipoFormato);
			fecha = formatoFecha.parse(valor);

			return fecha;

		} catch (ParseException pEx) {
			return null;
		}

	}

	/**
	 * Obtiene el valor de un campo como Date.
	 * 
	 * @param sNombreCampo - nombre del campo.
	 * @param indice - indice.
	 * @return Date - valor del campo.
	 * @throws NAWRException - NAWRException.
	 * 
	 */
	public Date getValorAsDate(String sNombreCampo, int indice) throws NAWRException {

		String valor = getValorAsString(sNombreCampo, indice);

		try {

			Date fecha = null;

			if (valor.length() == 8) {
				fecha = NA_FORMAT_8.parse(valor);
			} else if (valor.length() == 10) {
				fecha = NA_FORMAT_10.parse(valor);
			}

			return fecha;

		} catch (ParseException pEx) {
			return null;
		}

	}

	/**
	 * Ejecuta la transaccion.
	 * 
	 * @throws NAWRException - NAWRException.
	 */
	public void ejecutarTransaccion() throws NAWRException {
		servicio.ejecutar();
	}

	/**
	 * Ejecuta la transaccion.
	 * 
	 * @param sCodigoRuteo - codigo de ruteo necesario para ejecutar servicios
	 * zonales.
	 * @throws NAWRException - NAWRException.
	 */
	public void ejecutarTransaccion(String sCodigoRuteo) throws NAWRException {
		servicio.ejecutar(sCodigoRuteo);
	}

	/**
	 * Ejecuta la transaccion.
	 * 
	 * @param sUsername - usuario.
	 * @param sPassword - contraseña.
	 * @throws NAWRException - NAWRException.
	 */
	public void ejecutarTransaccion(String sUsername, String sPassword) throws NAWRException {
		servicio.ejecutar(sUsername, sPassword);
	}

	/**
	 * Ejecuta la transaccion.
	 * 
	 * @param sUsername - usuario.
	 * @param sPassword - contraseña.
	 * @param sCodigoRuteo - codigo de ruteo necesario para ejecutar servicios
	 * zonales.
	 * @throws NAWRException - NAWRException.
	 */
	public void ejecutarTransaccion(String sUsername, String sPassword, String sCodigoRuteo) throws NAWRException {
		servicio.ejecutar(sUsername, sPassword, sCodigoRuteo);
	}

	/**
	 * Libera los recursos del servicio. Debe llamarse explicitamente al
	 * terminar de usarse el servicio.
	 * 
	 */
	public void unload() {
		servicio.unload();
	}
	
	/**
	 * Convierte a texto HTML los nombres porque al mostralos
	 * de las listas se ven bien pero en campos no 
	 * 
	 */	
	public String convertirHTML (String descripcion)
	{
		if (descripcion != "")
		{	
			descripcion = descripcion.replaceAll("ñ","&ntilde;");
			descripcion = descripcion.replaceAll("Ñ","&Ntilde;");
			descripcion = descripcion.replaceAll("á","&aacute;");
			descripcion = descripcion.replaceAll("à","&agrave;");			
			descripcion = descripcion.replaceAll("Á","&Aacute;");
			descripcion = descripcion.replaceAll("À","&Agrave;");			
			descripcion = descripcion.replaceAll("é","&eacute;");
			descripcion = descripcion.replaceAll("è","&egrave;");			
			descripcion = descripcion.replaceAll("É","&Eacute;");
			descripcion = descripcion.replaceAll("È","&Egrave;");
			descripcion = descripcion.replaceAll("í","&iacute;");
			descripcion = descripcion.replaceAll("ï","&iuml;");			
			descripcion = descripcion.replaceAll("Í","&Iacute;");
			descripcion = descripcion.replaceAll("Ï","&Iuml;");			
			descripcion = descripcion.replaceAll("ó","&oacute;");
			descripcion = descripcion.replaceAll("ò","&ograve;");			
			descripcion = descripcion.replaceAll("Ó","&Oacute;");
			descripcion = descripcion.replaceAll("Ò","&Ograve;");
			descripcion = descripcion.replaceAll("ú","&uacute;");
			descripcion = descripcion.replaceAll("ü","&uuml;");			
			descripcion = descripcion.replaceAll("Ú","&Uacute;");			
			descripcion = descripcion.replaceAll("Ü","&Uuml;");
			descripcion = descripcion.replaceAll("ç","&ccedil;");
			descripcion = descripcion.replaceAll("Ç","&Ccedil;");			
		}
		return descripcion.toString().trim();		
	}
}
