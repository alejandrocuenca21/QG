/**
 * 
 */
package com.telefonica.ssnn.qg.buscador.clientes.dto;

import org.apache.log4j.Logger;

/**
 * @author pmpalomo
 *
 */
public class QGEntradaBusquedaTipoDocDto {
	private static final Logger logger = Logger.getLogger(QGEntradaBusquedaTipoDocDto.class);	

	private String codigoLinea;	
	private String codigoModalidad;	
	private String codigoDocumento;

	public String getCodigoLinea() {
		if (codigoLinea==null) codigoLinea="";
		logger.info("******---getCodigoLinea de QGEntradaBusquedaTipoDocDto---******");
		return codigoLinea;
	}

	public void setCodigoLinea(String codigoLinea) {
		this.codigoLinea = codigoLinea;
	}
	
	public String getCodigoModalidad() {
		if (codigoModalidad==null) codigoModalidad="";
		logger.info("******---getCodigoModalidad de QGEntradaBusquedaTipoDocDto---******");		
		return codigoModalidad;
	}

	public void setCodigoModalidad(String codigoModalidad) {
		this.codigoModalidad = codigoModalidad;
	}
	
	public String getCodigoDocumento() {
		if (codigoDocumento==null) codigoDocumento="";
		logger.info("******---getCodigoDocumento de QGEntradaBusquedaTipoDocDto---******");		
		return codigoDocumento;
	}

	public void setCodigoDocumento(String codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
	}		
}
