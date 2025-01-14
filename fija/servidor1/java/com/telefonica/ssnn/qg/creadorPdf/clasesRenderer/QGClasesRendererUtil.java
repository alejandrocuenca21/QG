package com.telefonica.ssnn.qg.creadorPdf.clasesRenderer;

public class QGClasesRendererUtil {

	/**
	 * Devuelve 1 si es un sistema windows y un 2 si es un unix.
	 */
	public static int obtenerSistemaOperativo(){
		String OS = System.getProperty("os.name").toLowerCase();
		  // System.out.println(OS);
		if ( (OS.indexOf("nt") > -1)
				|| (OS.indexOf("windows") > -1 )) {
			return 1;
			//  System.out.println("windows");
		} else {
			return 2;
		}
		 
	}
}
