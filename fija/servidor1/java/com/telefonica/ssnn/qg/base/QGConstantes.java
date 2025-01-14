package com.telefonica.ssnn.qg.base;

import java.util.HashMap;


public class QGConstantes {

	//MENSAJES 
	public static final String ERROR_GENERICO_MSG = "Se ha producido un error.";
	
	//CONSTANTES GLOBALES
	public static final String CODIGO_ALTA = "A";
	public static final String CODIGO_BAJA = "B";
	public static final String CODIGO_MODIFICAR = "M";
	public static final String CODIGO_TODOS = "T";
	public static final String CODIGO_UNO = "U";
	public static final String CODIGO_E = "E";
	public static final String CODIGO_S = "S"; 
	public static final String CODIGO_N = "N";
	public static final String CODIGO_T = "T";
	public static final String DESCRIPCION_T = "TACITO";
	public static final String DESCRIPCION_E = "EXPRESO";
	
	//CONSTANTES PARA INFORMES
	public static final String INDICADOR_FIJO = "Fijo";
	public static final String CODIGO_FIJO = "F";
	public static final String LITERAL_FIJO = "LNF";
	public static final String INDICADOR_MOVIL = "Móvil";
	public static final String CODIGO_MOVIL = "M";
	public static final String LITERAL_MOVIL = "LNM";
	public static final String LITERAL_CONVERGENTE = "Convergente";
	public static final String CODIGO_PREPAGO = "P";
	public static final String CODIGO_AMBAS = "A";	
	public static final String MODALIDAD_MOVIL = "Contrato";	
	public static final String MODALIDAD_PREPAGO = "Prepago";	
	public static final String MODALIDAD_AMBAS = "Ambas";	
	public static final String CODIGO_DUPLICADO = "D";
	public static final String CODIGO_ERROR = "E";
	
	//CONSTANTES NOMBRE COMBOS
	public static final String TEXTO_LEGAL = "textoLegal";
	public static final String MEDIOS = "medios";
	public static final String SEGMENTOS = "segmentos";
	public static final String AMBITOS = "ambitos";
	public static final String TIPOS_CONSENTIMIENTO = "tiposConsentimiento";
	public static final String TIPOS_OBJETOS = "tiposObjetos";
	public static final String NIVELES_APLICACION = "nivelesAplicacion";
	public static final String GRUPO_MEDIOS = "grupoMediosComunicacion";
	public static final String GRUPO_SEGMENTOS = "grupoSegmentos";
	public static final String ESTADO_GESTION = "estadoGestion";
	public static final String TIPOS_COMUNICACION = "tipoComunicacion";
	public static final String CAMPANIAS = "campanias";
	public static final String MEDIOS_COMUNICACION = "mediosComunicacion";
	public static final String TIPO_UBICACION = "tipoUbicacion";
	public static final String GRUPO_SITUACION_ACTUAL = "situacionActual";
	public static final String GRUPO_SITUACION_NUEVA = "situacionNueva";
	
	//CONSTANTES AUDITORIA
	public static final String DS_RESULTADO_OPERA_OK = "RESULTADO CORRECTO";
	public static final String DS_RESULTADO_OPERA_NOK = "SIN RESULTADOS";
	public static final String DS_RESULTADO_OPERA_ERROR = "ERROR";
	public static final String CO_TP_PARAM_GBL = "023";
	
	public static final String MONIT_CLIENTES = "MONIT-CLIENTES";
	public static final String MONIT_DET_CLIENTES = "MONIT-DET-CLIENTES";
	public static final String MONIT_CONSENT_ALTA = "MONIT-CONSENT-ALTA";
	public static final String MONIT_CONSENT_DET = "MONIT-CONSENT-DET";
	public static final String MONIT_MEDIO_COMUN = "MONIT-MEDIO-COMUN";
	public static final String MONIT_CAMPANAS = "MONIT-CAMPANAS";
	public static final String MONIT_TIP_UBICAC = "MONIT-TIP-UBICAC";
	public static final String MONIT_SEG_EV_ALTA = "MONIT-SEG-EV-ALTA";
	public static final String MONIT_SEG_EV_BAJA = "MONIT-SEG-EV-BAJA";
	public static final String MONIT_SEG_TRA_ALTA = "MONIT-SEG-TRA-ALTA";
	public static final String MONIT_SEG_TRA_BAJA = "MONIT-SEG-TRA-BAJA";
	public static final String MONIT_SEG_PRES_ALTA = "MONIT-SEG-PRES-ALTA";
	public static final String MONIT_SEG_PRES_MOD = "MONIT-SEG-PRES-MOD";
	public static final String MONIT_SEG_PRES_BAJA = "MONIT-SEG-PRES-BAJA";
	public static final String MONIT_SEG_EXP = "MONIT-SEG-EXP";
	public static final String MONIT_SEG_REGLAS = "MONIT-SEG-REGLAS";
	public static final String MONIT_PO_ALTA = "MONIT-PO-ALTA";
	public static final String PO_BAJA = "PO-BAJA";
	public static final String MONIT_CE_ALTA = "MONIT-CE-ALTA";
	public static final String MONIT_CE_BAJA = "MONIT-CE-BAJA";
	public static final String MONIT_MENS_ERR_ALTA = "MONIT-MENS-ERR-ALTA";
	public static final String MONIT_MENS_ERR_BAJA = "MONIT-MENS-ERR-BAJA";
	public static final String MONIT_AUT_ALTA = "MONIT-AUT-ALTA";
	public static final String MONIT_AUT_BAJA = "MONIT-AUT-BAJA";
	public static final String MONIT_AUT_SE_ALTA = "MONIT-AUT-SE-ALTA";
	public static final String MONIT_AUT_SE_BAJA = "MONIT-AUT-SE-BAJA";
	public static final String MONIT_AUT_NA_ALTA = "MONIT-AUT-NA-ALTA";
	public static final String MONIT_AUT_NA_BAJA = "MONIT-AUT-NA-BAJA";
	public static final String MONIT_CONSENT_MODI = "MONIT-CONSENT-MODI";
	public static final String MONIT_MEDIO_MODIF = "MONIT-MEDIO-MODIF";
	public static final String MONIT_CAMPANAS_MOD = "MONIT-CAMPANAS-MOD";
	public static final String MONIT_TIP_UBI_MOD = "MONIT-TIP-UBI-MOD";
	public static final String MONIT_PO_MODI = "MONIT-PO-MODI";
	public static final String MONIT_CE_MODI = "MONIT-CE-MODI";
	public static final String MONIT_MENS_ERR_MODI = "MONIT-MENS-ERR-MODI";
	public static final String MONIT_AUT_MODI = "MONIT-AUT-MODI";
	public static final String MONIT_AUT_SE_MODI = "MONIT-AUT-SE-MODI";
	public static final String MONIT_AUT_NA_MODI = "MONIT-AUT-NA-MODI"; 
	
	
	//PLANTILLAS PARA LA GENERACION DE INFORMES
	public static final String PLANTILLA_INFORME_SEG_TRAD = "plantillas/informeSegmentacionTraduccion.jasper";
	public static final String PLANTILLA_INFORME_ENCARTES = "plantillas/informeEncartes.jasper";
	public static final String PLANTILLA_INFORME_CREATIVIDAD = "plantillas/informeCreatividad.jasper";
	public static final String PLANTILLA_INFORME_SEG_EVO = "plantillas/informeSegmentacion.jasper";
	public static final String PLANTILLA_INFORME_CAMPANIAS = "plantillas/informeCampanias.jasper";
	public static final String PLANTILLA_INFORME_MCOM = "plantillas/informeMediosComunicacion.jasper";
	public static final String PLANTILLA_INFORME_CONSENT = "plantillas/informeConsentimientosDerechos.jasper";
	public static final String PLANTILLA_INFORME_TIPOUBI = "plantillas/informeTipoUbicacion.jasper";
	public static final String PLANTILLA_INFORME_MERROR = "plantillas/informeMensajesError.jasper";
	public static final String PLANTILLA_INFORME_AUTORIZ = "plantillas/informeAutorizaciones.jasper";
	public static final String PLANTILLA_INFORME_SISEXT = "plantillas/informeSistemasExternos.jasper";
	public static final String PLANTILLA_INFORME_SERVNA = "plantillas/informeServiciosNA.jasper";
	public static final String PLANTILLA_INFORME_PRESEGMENTACION = "plantillas/informePresegmentaciones.jasper";
	public static final String PLANTILLA_INFORME_REGLAS = "plantillas/informeReglasSegmentacion.jasper";
	public static final String PLANTILLA_INFORME_CONTRATOS = "plantillas/informeContratosSegmentacion.jasper";
	//NAVEGACION ACTIONS
	public static final String IR_ALTA = "irAlta";
	public static final String IR_DETALLE = "irDetalle";
	public static final String IR_CONTRATOS = "irContratos";
	public static final String IR_REGLAS = "irReglas";
	public static final String IR_BAJA = "irBaja";
	
	//DETALLE CLIENTES
	public static final String SI_CONSIENTE = "SC";
	
	/**
	 * descripcion unidad 01 para combo unidad lnf
	 */
	public static final String DESCRIPCION_UNIDAD_01 = "LNF";
	/**
	 * descripcion unidad 02 para combo unidad lnm
	 */
	public static final String DESCRIPCION_UNIDAD_02 ="LNM";
	
	/**
	 * descripcion operacion A para combo operacion Alta
	 */
	public static final String DESCRIPCION_OPERACION_A ="Alta";
	/**
	 * descripcion operacion B para combo operacion Baja
	 */
	public static final String DESCRIPCION_OPERACION_B ="Baja";
	/**
	 * Listado de historicos
	 */
	public static final String LISTADO_HISTORICO = "H";
	/**
	 * Listado de consulta
	 */
	public static final String LISTADO_CONSULTA = "C";
	
	/**
	 * Codigo de segmento AU
	 */
	public static String CODIGO_SEGMENTO_AU = "AU";
	
	//Administracion encartes.
	//para el caso del código de segmento, cuando el servicio nos devuelva GP, la web mostrará GP/RES, 
	public static String CODIGO_SEGMENTO_GP_INICIAL ="GP";
	public static String CODIGO_SEGMENTO_GP_SUSTITUTO="GP/RES";
	/*no se mostrarán los códigos TE ni NT ni PY*/
	
	public static HashMap CODIGOS_DE_SEGMENTOS_NO_MOSTRADOS;
	static 
	{
		//Mapa de contenido estatico
		HashMap myMap = new HashMap();
		myMap.put("TE", "TE");
		myMap.put("PY", "PY");
		myMap.put("GC", "GC");
	 	
		CODIGOS_DE_SEGMENTOS_NO_MOSTRADOS = myMap;
		
	}
	
}
