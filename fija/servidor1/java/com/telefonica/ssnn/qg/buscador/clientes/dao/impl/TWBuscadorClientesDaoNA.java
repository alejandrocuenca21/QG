/**
 *
 */
package com.telefonica.ssnn.qg.buscador.clientes.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.telefonica.na.NAWRException;
import com.telefonica.ssnn.qg.base.dao.QGBaseDao;
import com.telefonica.ssnn.qg.buscador.clientes.dao.interfaz.QGBuscadorClientesDao;
import com.telefonica.ssnn.qg.buscador.clientes.dao.interfaz.TWBuscadorClientesDao;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGBuscadorClientesDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGClienteFijoDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGClienteMovilCtoDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGClienteMovilPreDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGClienteMovilRPSDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGDatosClienteDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGDatosGeneralesDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGDescripcionesDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGDireccionesClienteDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGDireccionesClienteLNMDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGDireccionesClienteLogicasDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGDomiciliosClienteDto;
import com.telefonica.ssnn.qg.buscador.clientes.dto.QGHistorialSegmentacionCliDto;
import com.telefonica.ssnn.qg.na.QGIdentificadoresDescriptores;
import com.telefonica.ssnn.qg.na.QGNpaNA;
import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.util.QGUtilidadesFechasUtils;
import com.telefonica.ssnn.qg.util.QGUtilidadesNegocioUtils;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.exceptions.QGApplicationException;

/**
 * @author ACF
 *
 */
public class TWBuscadorClientesDaoNA extends QGBaseDao implements
		TWBuscadorClientesDao {
	private Logger logger = Logger.getLogger(TWBuscadorClientesDaoNA.class);
	/**
	 * Se llamará al servicio de negocio QGF0012
	 */

	public QGCGlobalDto obtenerDatosCliente(String codigoCliente) {
		logger.info("******---obtenerDatosCliente de TWBuscadorClientesDaoNA---******");		
		QGNpaNA servicio = null;
		QGCGlobalDto globalDto = new QGCGlobalDto();
		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		ArrayList listaDatos = new ArrayList();
		ArrayList listaMensajes = new ArrayList();
		
		try {
			servicio = new QGNpaNA("QGF0012");
			
			servicio.setValor("NACabAnagramaLlamante", "QG");
			servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());
			
			servicio.setValor("CO-CLIENTE-GBL", codigoCliente);
			
			servicio.ejecutarTransaccion();
			
			QGDatosGeneralesDto datosGenerales = new QGDatosGeneralesDto();
			
			QGDatosClienteDto datosCliente = null;			
			ArrayList listaDatosCliente = new ArrayList();
			
			datosCliente = new QGDatosClienteDto();
			
			datosCliente.setCodigoCliente(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-CLIENTE-GBL-PDR"));// ,i));
			datosCliente.setDsRazonSocial(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-RAZON-SOCIAL"));// ,i));
			datosCliente.setDsNombre(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-NOMBRE"));// ,i));
			datosCliente.setDsApellido1(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-APELLIDO-1"));// ,i));
			datosCliente.setDsApellido2(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-APELLIDO-2"));// ,i));
			datosCliente.setNoComercial(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.NO-COMERCIAL"));// ,i));
			datosCliente.setDsTipoDocumento(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-TIPO-DOCUMENTO"));// ,i));
			datosCliente.setCoTipoDocumento(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-TIPO-DOCUMENTO"));// ,i));
			datosCliente.setNuDocumento(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.NU-DOCUMENTO"));// ,i));
			datosCliente.setNuSecDuplicado(servicio.getValorAsInt("SALIDA.GBL-CLI-DAT-GR.NU-SEC-DUPLICADO"));// ,i));
			datosCliente.setDsDocExtranjero(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-NU-DOC-EXTR"));// ,i));
			datosCliente.setInCaracterInternacional(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.IN-CARACTER-INT"));// ,i));
			datosCliente.setCoCaracterInternacional(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-TP-CARACTER-INT"));// ,i));
			datosCliente.setDsCaracterInternacional(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-TP-CARACTER-INT"));
			datosCliente.setDsEstadoCliente(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-ESTADO-CLIENTE"));// ,i));
			datosCliente.setCoEstadoCliente(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-ESTADO-CLIENTE"));// ,i));
			datosCliente.setDsMotivoEstado(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-MOTIVO-ESTADO"));// ,i));
			datosCliente.setCoMotivoEstado(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-MOTIVO-ESTADO"));// ,i));
			datosCliente.setFxUltEstadoCli(servicio.getValorAsDate("SALIDA.GBL-CLI-DAT-GR.IT-CMB-EST-CLI-GLB"));// ,i));
			datosCliente.setDsPais(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-PAIS"));// ,i));
			datosCliente.setCoPais(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-PAIS"));// ,i));
			datosCliente.setFxUltReactivacion(servicio.getValorAsDate("SALIDA.GBL-CLI-DAT-GR.FX-ULT-REACTIVAC"));// ,i));
			datosCliente.setInExentoImpuesto(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.IN-EXENTO-IMPUESTO"));// ,i));
			datosCliente.setCoTipoFacturaMvl(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-TIPO-IPR-FAC"));// ,i));
			datosCliente.setNoTipoFacturaMvl(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.NO-TIPO-IPR-FAC"));
			datosCliente.setCoMoneda(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-MONEDA"));// ,i));
			datosCliente.setDsNivelConfidencial(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-NIVEL-CONFIDEN"));// ,i));
			datosCliente.setCoNivelConfidencial(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-NIVEL-CONFIDEN"));// ,i));
			datosCliente.setCoUnidadGestion(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-OFICINA-AT"));// ,i));
			datosCliente.setCoTipoCliente(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-TIPO-CLIENTE"));// ,i));
			datosCliente.setInCabecera(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.IN-CAB-GRU-EMP"));// ,i));
			datosCliente.setDsSectorEconomico(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-SECTOR-ECO"));// ,i));
			datosCliente.setCoSectorEconomico(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-SECTOR-ECO"));// ,i));
			datosCliente.setDsIdioma(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-IDIOMA"));// ,i));
			datosCliente.setCoIdioma(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-IDIOMA"));// ,i));
			datosCliente.setCoPlanClientes(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-VAL-PLN-CUE"));// ,i));
			datosCliente.setCoCNAE(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-CNAE"));// ,i));
			datosCliente.setDsCNAE(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-CNAE"));// ,i));
			datosCliente.setCoSegmento(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-SEGMENTO"));// ,i));
			datosCliente.setDsSegmento(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-SEGMENTO"));
			datosCliente.setCoSubSegmento(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-SUBSEGMENTO"));// ,i));
			datosCliente.setDsSubSegmento(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-SUBSEGMENTO"));
			datosCliente.setFxUltSubSegmento(servicio.getValorAsDate("SALIDA.GBL-CLI-DAT-GR.IT-CMB-SBG-CLI-GLB"));// ,i));
			datosCliente.setCoSgmDatawarehou(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-SGM-DWH-CLI"));// ,i));
			datosCliente.setDsSgmDatawarehou(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-SGM-DWH-CLI"));
			datosCliente.setDsAnagrama(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-ANAGRAMA-GF"));// ,i));
			datosCliente.setDsAnagramaPe1(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-ANAGR-APE1"));// ,i));
			datosCliente.setDsAnagramaPe2(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-ANAGR-APE2"));// ,i));
			datosCliente.setDsAnagramaNombre(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-ANAGR-NOMB"));// ,i));
			datosCliente.setDsAnagramaRsocial(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-ANAGR-RSOC"));// ,i));
			datosCliente.setDsClaveConsonantes(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.DS-CLV-DAT-CLI"));// ,i));
			datosCliente.setCoNivelAtencion(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-NIV-ATENC-CRC"));// ,i));
			datosCliente.setInUnidadNegocio(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.IN-UNI-NGC-CLI-GBL"));// ,i));
			datosCliente.setInModalidadMovil(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.IN-MODALIDAD-UNM"));// ,i));
			datosCliente.setCoIdentidad(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-IDENTIDAD"));// ,i));
			datosCliente.setDsComentariosCliente(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.OB-CLI-CVG"));// ,i));
			datosCliente.setFxIniVigencia(servicio.getValorAsDate("SALIDA.GBL-CLI-DAT-GR.FX-INI-VIGENCIA"));// ,i));
			datosCliente.setFxFinVigencia(servicio.getValorAsDate("SALIDA.GBL-CLI-DAT-GR.FX-FIN-VIGENCIA"));// ,i));
			datosCliente.setCoUsuarioAlta(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-USR-ALT"));// ,i));
			datosCliente.setFxAltaRegistro(servicio.getValorAsDate("SALIDA.GBL-CLI-DAT-GR.IT-ALT-REG-GLB"));// ,i));
			datosCliente.setCoUsuarioMod(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.CO-USR-MOD"));// ,i));
			datosCliente.setFxUltModificacion(servicio.getValorAsDate("SALIDA.GBL-CLI-DAT-GR.IT-ULT-MOD"));// ,i));
			datosCliente.setNombrePrograma(servicio.getValorAsString("SALIDA.GBL-CLI-DAT-GR.NO-PGM-MOD"));// ,i));
			
			listaDatosCliente.add(datosCliente);
			datosGenerales.setDatosClienteDto(listaDatosCliente);
			QGClienteFijoDto clienteFijoDto = null;
			ArrayList listaClienteFijo = new ArrayList();
			
			clienteFijoDto = new QGClienteFijoDto();
			
			clienteFijoDto.setCoClienteFijo(servicio.getValorAsString("SALIDA.UNF-CLI-DAT-GR.CO-CLIENTE-TW"));
			clienteFijoDto.setInClienteCabecera(servicio.getValorAsString("SALIDA.UNF-CLI-DAT-GR.IN-CLIENTE-CAB"));// ,i));
			clienteFijoDto.setFxAltaCliente(servicio.getValorAsDate("SALIDA.UNF-CLI-DAT-GR.FX-ALT-CLI-UNF"));// ,i));
			clienteFijoDto.setDsEstadoCliente(servicio.getValorAsString("SALIDA.UNF-CLI-DAT-GR.DS-ESTADO-CLIENTE"));// ,i));
			clienteFijoDto.setCoEstadoCliente(servicio.getValorAsString("SALIDA.UNF-CLI-DAT-GR.CO-ESTADO-CLIENTE"));// ,i));
			clienteFijoDto.setDsMotivoEstado(servicio.getValorAsString("SALIDA.UNF-CLI-DAT-GR.DS-MOTIVO-ESTADO"));// ,i));
			clienteFijoDto.setCoMotivoEstado(servicio.getValorAsString("SALIDA.UNF-CLI-DAT-GR.CO-MOTIVO-ESTADO"));// ,i));
			clienteFijoDto.setFxUltCambioEstado(servicio.getValorAsDate("SALIDA.UNF-CLI-DAT-GR.IT-ULT-CMB-EST-UNF"));// ,i));
			clienteFijoDto.setCoSegmento(servicio.getValorAsString("SALIDA.UNF-CLI-DAT-GR.CO-SEGMENTO"));// ,i));
			clienteFijoDto.setDsSegmento(servicio.getValorAsString("SALIDA.UNF-CLI-DAT-GR.DS-SEGMENTO"));
			clienteFijoDto.setCoSubSegmento(servicio.getValorAsString("SALIDA.UNF-CLI-DAT-GR.CO-SUBSEGMENTO"));// ,i));
			clienteFijoDto.setDsSubSegmento(servicio.getValorAsString("SALIDA.UNF-CLI-DAT-GR.DS-SUBSEGMENTO"));
			clienteFijoDto.setFxUltCambioSubSegmento(servicio.getValorAsDate("SALIDA.UNF-CLI-DAT-GR.IT-CMB-SBG-CLI"));// ,i));
			clienteFijoDto.setFxIniVigencia(servicio.getValorAsDate("SALIDA.UNF-CLI-DAT-GR.FX-INI-VIG-CLI-UNF"));// ,i));
			clienteFijoDto.setFxFinVigencia(servicio.getValorAsDate("SALIDA.UNF-CLI-DAT-GR.FX-FIN-VIG-CLI-UNF"));// ,i));
			clienteFijoDto.setCoUsuarioAlta(servicio.getValorAsString("SALIDA.UNF-CLI-DAT-GR.CO-USR-ALT-UNF"));// ,i));
			clienteFijoDto.setFxAltaRegistro(servicio.getValorAsDate("SALIDA.UNF-CLI-DAT-GR.IT-ALT-REG-UNF"));// ,i));
			clienteFijoDto.setCoUsuarioModificacion(servicio.getValorAsString("SALIDA.UNF-CLI-DAT-GR.CO-USR-MOD-UNF"));// ,i));
			clienteFijoDto.setFxUltModRegistro(servicio.getValorAsDate("SALIDA.UNF-CLI-DAT-GR.IT-ULT-MOD-UNF"));// ,i));
			clienteFijoDto.setNombrePrograma(servicio.getValorAsString("SALIDA.UNF-CLI-DAT-GR.NO-PGM-MOD-UNF"));// ,i));
			
			listaClienteFijo.add(clienteFijoDto);
			datosGenerales.setClienteFijoDto(listaClienteFijo);
					
			QGClienteMovilCtoDto clienteMovilCtoDto = null;
			ArrayList listaClienteMovilCtoDto = new ArrayList();
			
			clienteMovilCtoDto = new QGClienteMovilCtoDto();

			
			clienteMovilCtoDto.setCoClienteMovilCto(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.CO-CLIENTE-NSCO"));
			clienteMovilCtoDto.setDsNombre(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.DS-NOMBRE-UNM"));// ,i));
			clienteMovilCtoDto.setDsApellido1(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.DS-APELLIDO-1-UNM"));// ,i));
			clienteMovilCtoDto.setDsApellido2(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.DS-APELLIDO-2-UNM"));// ,i));
			clienteMovilCtoDto.setDsTipoDocumento(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.DS-TIPO-DOC-UNM"));// ,i));
			clienteMovilCtoDto.setCoTipoDocumento(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.CO-TIPO-DOC-UNM"));// ,i));
			clienteMovilCtoDto.setFxAltaCliente(servicio.getValorAsDate("SALIDA.UNM-CLI-DAT-GR.FX-ALT-CLI-UNM"));// ,i));
			clienteMovilCtoDto.setCoPais(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.CO-PAIS-UNM"));// ,i));
			clienteMovilCtoDto.setInCabeceraGrupo(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.IN-CAB-GRU-EMP-UNM"));// ,i));
			clienteMovilCtoDto.setCoSituacionEstado(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.CO-SIT-ESTADO-UNM"));// ,i));
			clienteMovilCtoDto.setDsSituacionEstado(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.DS-SIT-ESTADO-UNM"));
			clienteMovilCtoDto.setCoEstadoCliente(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.CO-EST-CLI-UNM"));// ,i));
			clienteMovilCtoDto.setDsEstadoCliente(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.DS-EST-CLI-UNM"));
			clienteMovilCtoDto.setFxUltCambioEstado(servicio.getValorAsDate("SALIDA.UNM-CLI-DAT-GR.IT-ULT-CMB-EST-UNM"));// ,i));
			clienteMovilCtoDto.setCoTipoCliente(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.CO-TIPO-CLI-UNM"));// ,i));
			clienteMovilCtoDto.setDsTipoCliente(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.DS-TIPO-CLI-UNM"));
			clienteMovilCtoDto.setCoTipoClienteFSC(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.CO-TIP-CLI-FSC-UNM"));// ,i));
			clienteMovilCtoDto.setDsTipoClienteFSC(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.DS-TIP-CLI-FSC-UNM"));
			clienteMovilCtoDto.setCoSegmento(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.CO-SEGMENTO-UNM"));// ,i));
			clienteMovilCtoDto.setDsSegmento(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.DS-SEGMENTO-UNM"));
			clienteMovilCtoDto.setCoSubSegmento(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.CO-SUBSEGMENTO-UNM"));// ,i));
			clienteMovilCtoDto.setDsSubSegmento(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.DS-SUBSEGMENTO-UNM"));
			clienteMovilCtoDto.setCoTipoSegmento(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.CO-TP-SEGMENTO-UNM"));// ,i));
			clienteMovilCtoDto.setDsTipoSegmento(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.DS-TP-SEGMENTO-UNM"));
			clienteMovilCtoDto.setCoMotivoSegmento(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.CO-MOT-SGM-UNM"));// ,i));
			clienteMovilCtoDto.setDsMotivoSegmento(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.DS-MOT-SGM-UNM"));
			clienteMovilCtoDto.setFxCambioSegmento(servicio.getValorAsDate("SALIDA.UNM-CLI-DAT-GR.IT-CMB-SGM-UNM"));// ,i));
			clienteMovilCtoDto.setFxIniVigencia(servicio.getValorAsDate("SALIDA.UNM-CLI-DAT-GR.FX-INI-VIG-CLI-UNM"));// ,i));
			clienteMovilCtoDto.setFxFinVigencia(servicio.getValorAsDate("SALIDA.UNM-CLI-DAT-GR.FX-FIN-VIG-CLI-UNM"));// ,i));
			clienteMovilCtoDto.setCoUsuarioAlta(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.CO-USR-ALT-UNM"));// ,i));
			clienteMovilCtoDto.setFxAltaRegistro(servicio.getValorAsDate("SALIDA.UNM-CLI-DAT-GR.IT-ALT-REG-UNM"));// ,i));
			clienteMovilCtoDto.setCoUsuarioModificacion(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.CO-USR-MOD-UNM"));// ,i));
			clienteMovilCtoDto.setFxUltModificacion(servicio.getValorAsDate("SALIDA.UNM-CLI-DAT-GR.IT-ULT-MOD-UNM"));// ,i));
			clienteMovilCtoDto.setNombrePrograma(servicio.getValorAsString("SALIDA.UNM-CLI-DAT-GR.NO-PGM-MOD-UNM"));// ,i));

			listaClienteMovilCtoDto.add(clienteMovilCtoDto);						
			datosGenerales.setClienteMovilCtoDto(listaClienteMovilCtoDto);
			
			QGClienteMovilPreDto clienteMovilPreDto = null;
			ArrayList listaClienteMovilPreDto = new ArrayList();
			
			clienteMovilPreDto = new QGClienteMovilPreDto();
						
			clienteMovilPreDto.setCoIdentificador(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-IDF-PGO-NSCO"));
			clienteMovilPreDto.setDsEstado(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.DS-EST-PSN-PGO"));// ,i));
			clienteMovilPreDto.setCoEstado(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-EST-PSN-PGO"));// ,i));
			clienteMovilPreDto.setDsMotivoEstado(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.DS-MOT-EST-PGO"));// ,i));
			clienteMovilPreDto.setCoMotivoEstado(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-MOT-EST-PGO"));// ,i));
			clienteMovilPreDto.setDsTipoDocumento(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.DS-TIPO-DOC-PGO"));// ,i));
			clienteMovilPreDto.setCoTipoDocumento(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-TIPO-DOC-PGO"));// ,i));
			clienteMovilPreDto.setNuDocumento(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.NU-DOCUMENTO-PGO"));// ,i));
			clienteMovilPreDto.setDsPais(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.DS-PAIS-PGO"));// ,i));
			clienteMovilPreDto.setCoPais(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-PAIS-PGO"));// ,i));
			clienteMovilPreDto.setDsNombre(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.DS-NOMBRE-PGO"));
			clienteMovilPreDto.setDsApellido1(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.DS-APELLIDO-1-PGO"));// ,i));
			clienteMovilPreDto.setDsApellido2(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.DS-APELLIDO-2-PGO"));
			clienteMovilPreDto.setCoPostal(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-POSTAL-PGO"));// ,i));
			clienteMovilPreDto.setDsActivo(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.DS-ACTIVO-PGO"));// ,i));
			clienteMovilPreDto.setCoActivo(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-ACTIVO-PGO"));
			clienteMovilPreDto.setFxCambioEstado(servicio.getValorAsDate("SALIDA.PRE-CLI-DAT-GR.IT-CMB-EST-PGO"));// ,i));
			clienteMovilPreDto.setCoCanal(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-CANAL-REG"));
			clienteMovilPreDto.setFxNacimiento(servicio.getValorAsDate("SALIDA.PRE-CLI-DAT-GR.FX-NAC-MENOR"));// ,i));
			clienteMovilPreDto.setCoClienteMovilPre(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-CLIENTE-GBL-PGO"));
			clienteMovilPreDto.setCoSegmento(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-SEGMENTO-PGO"));// ,i));
			clienteMovilPreDto.setDsSegmento(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.DS-SEGMENTO-PGO"));
			clienteMovilPreDto.setCoSubSegmento(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-SUBSEGMENTO-PGO"));// ,i));
			clienteMovilPreDto.setDsSubSegmento(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.DS-SUBSEGMENTO-PGO"));
			clienteMovilPreDto.setCoMotivoSegmento(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-MOT-SGM-PGO"));// ,i));
			clienteMovilPreDto.setDsMotivoSegmento(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.DS-MOT-SGM-PGO"));
			clienteMovilPreDto.setFxCambioSegmento(servicio.getValorAsDate("SALIDA.PRE-CLI-DAT-GR.IT-ULT-CMB-SGM-PGO"));// ,i));
			clienteMovilPreDto.setFxIniVigencia(servicio.getValorAsDate("SALIDA.PRE-CLI-DAT-GR.FX-INI-VIG-PGO"));// ,i));
			clienteMovilPreDto.setFxFinVigencia(servicio.getValorAsDate("SALIDA.PRE-CLI-DAT-GR.FX-FIN-VIG-PGO"));// ,i));
			clienteMovilPreDto.setCoUsuarioAlta(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-USR-ALT-PGO"));// ,i));
			clienteMovilPreDto.setFxAltaRegistro(servicio.getValorAsDate("SALIDA.PRE-CLI-DAT-GR.IT-ALT-REG-PGO"));// ,i));
			clienteMovilPreDto.setCoUsuarioModificacion(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-USR-MOD-PGO"));// ,i));
			clienteMovilPreDto.setFxUltModificacion(servicio.getValorAsDate("SALIDA.PRE-CLI-DAT-GR.IT-ULT-MOD-PGO"));// ,i));
			clienteMovilPreDto.setNombrePrograma(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.NO-PGM-MOD-PGO"));// ,i));
			
			listaClienteMovilPreDto.add(clienteMovilPreDto);				
			datosGenerales.setClienteMovilPreDto(listaClienteMovilPreDto);
			
			QGClienteMovilRPSDto clienteMovilRPSDto = null;
			ArrayList listaClienteMovilRPSDto = new ArrayList();
			
			clienteMovilRPSDto = new QGClienteMovilRPSDto();			
						
			clienteMovilRPSDto.setDsTipoDocumento(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.DS-TIPO-DOC-RPS"));// ,i));
			clienteMovilRPSDto.setCoTipoDocumento(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-TIPO-DOC-RPS"));// ,i));
			clienteMovilRPSDto.setNuDocumento(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.NU-DOCUMENTO-RPS"));// ,i));
			clienteMovilRPSDto.setDsPais(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.DS-PAIS-RPS"));// ,i));
			clienteMovilRPSDto.setCoPais(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-PAIS-RPS"));// ,i));
			clienteMovilRPSDto.setDsNombre(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.DS-NOMBRE-RPS"));// ,i));
			clienteMovilRPSDto.setDsApellido1(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.DS-APE1-RPS"));// ,i));
			clienteMovilRPSDto.setDsApellido2(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.DS-APE2-RPS"));// ,i));
			clienteMovilRPSDto.setCoPostal(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-POSTAL-RPS"));// ,i));
			clienteMovilRPSDto.setFxIniVigencia(servicio.getValorAsDate("SALIDA.PRE-CLI-DAT-GR.FX-INI-VIG-RPS"));// ,i));
			clienteMovilRPSDto.setFxFinVigencia(servicio.getValorAsDate("SALIDA.PRE-CLI-DAT-GR.FX-FIN-VIG-RPS"));// ,i));
			clienteMovilRPSDto.setCoUsuarioAlta(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-USR-ALT-RPS"));// ,i));
			clienteMovilRPSDto.setFxAltaRegistro(servicio.getValorAsDate("SALIDA.PRE-CLI-DAT-GR.IT-ALT-REG-RPS"));// ,i));
			clienteMovilRPSDto.setCoUsuarioModificacion(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.CO-USR-MOD-RPS"));// ,i));
			clienteMovilRPSDto.setFxUltModificacion(servicio.getValorAsDate("SALIDA.PRE-CLI-DAT-GR.IT-ULT-MOD-RPS"));// ,i));
			clienteMovilRPSDto.setNombrePrograma(servicio.getValorAsString("SALIDA.PRE-CLI-DAT-GR.NO-PGM-MOD-RPS"));// ,i));
			
			listaClienteMovilRPSDto.add(clienteMovilRPSDto);							
			datosGenerales.setClienteMovilRPSDto(listaClienteMovilRPSDto);								
			
			listaDatos.add(datosGenerales);
			
		} catch (NAWRException e) {
			logger.info("error dao --> " + e.getMessage(),e);
			
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));			
		}finally{
			if(servicio != null)
				servicio.unload();
		}
		
		globalDto.setlistaDatos(listaDatos);
		globalDto.setlistaMensajes(listaMensajes);
		logger.info("******---FIN obtenerDatosCliente de TWBuscadorClientesDaoNA---******");		
		return globalDto;
	}	
	
	/**
	 * Se llamará al servicio de negocio QGF0023
	 */

	public QGCGlobalDto obtenerDireccionesCliente(String codigoCliente) {
		logger.info("******---obtenerDireccionesCliente de TWBuscadorClientesDaoNA---******");		
		QGNpaNA servicio = null;
		QGCGlobalDto globalDto = new QGCGlobalDto();
		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		ArrayList listaDatos = new ArrayList();
		ArrayList listaMensajes = new ArrayList();
		
		try {
			servicio = new QGNpaNA("QGF0023");
			
			servicio.setValor("NACabAnagramaLlamante", "QG");
			servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());
			
			servicio.setValor("CO-CLIENTE-GBL", codigoCliente);
			servicio.ejecutarTransaccion();
			
			QGDomiciliosClienteDto domiciliosClienteDto = new QGDomiciliosClienteDto();
			
		    ArrayList lista = new ArrayList();

		    
		    //direcciones Logicas.
			lista = new ArrayList();
			QGDireccionesClienteLogicasDto clienteLogicoDto = null;
			for(int i=1; i <=servicio.getValorAsInt("DIR-LOG-OCU-NU").intValue(); i++){
				clienteLogicoDto = new QGDireccionesClienteLogicasDto();
				clienteLogicoDto.setInOriDirLog(servicio.getValorAsString("IN-ORI-DIR-LOG", i));
				clienteLogicoDto.setDescDirLogica(servicio.getValorAsString("DS-DIR-LOG", i));
				clienteLogicoDto.setDescTipoDireccion(servicio.getValorAsString("DIR-LOG-GR.DS-TIPO-DIRE", i));
				lista.add(clienteLogicoDto);
			}
			domiciliosClienteDto.setDireccioneClienteLogicas(lista);
			
		    
		    
		    
			lista = new ArrayList();
			QGDireccionesClienteDto clienteDto = null;
			for(int i=1; i <=servicio.getValorAsInt("DIR-LNF-OCU-NU").intValue(); i++){
				clienteDto = new QGDireccionesClienteDto();
				clienteDto.setInOriDir(servicio.getValorAsString("IN-ORI-DIR", i));
				clienteDto.setCodigoProvincia(servicio.getValorAsString("CO-PROVINCIA", i));
				clienteDto.setDescProvincia(servicio.getValorAsString("DIR-LNF-GR.DS-PROVINCIA", i));
				clienteDto.setDescLocalidad(servicio.getValorAsString("DS-ENTIDAD-POBLAC", i));
				clienteDto.setCodigoPostal(servicio.getValorAsString("NU-CODIGO-POSTAL", i));
				clienteDto.setDescPais(servicio.getValorAsString("DS-PAIS", i));
				clienteDto.setCodTipoDireccion(servicio.getValorAsString("DIR-LNF-GR.CO-TIPO-DIRECCION", i));
				clienteDto.setDescTipoDireccion(servicio.getValorAsString("DIR-LNF-GR.DS-TIPO-DIRE", i));
				clienteDto.setCodTipoVia(servicio.getValorAsString("CO-TIPO-VIA", i));
				clienteDto.setDescTipoVia(servicio.getValorAsString("DS-TIPO-VIA", i));
				clienteDto.setDescCalle(servicio.getValorAsString("DS-CALLE", i));
				
				clienteDto.setFinca(servicio.getValorAsString("NU-FINCA", i));
				clienteDto.setBisDuplic(servicio.getValorAsString("DS-BIS-DUPLIC", i));
				clienteDto.setBloque(servicio.getValorAsString("DS-BLOQUE", i));
				clienteDto.setPortal(servicio.getValorAsString("DS-PORTAL", i));
				clienteDto.setEscalera(servicio.getValorAsString("DS-ESCALERA", i));
				clienteDto.setPlanta(servicio.getValorAsString("DS-PLANTA", i));
				clienteDto.setLetra(servicio.getValorAsString("DS-LETRA", i));
				clienteDto.setAclaradorFinca(servicio.getValorAsString("DS-ACLARADOR-FINCA", i));											
				
				lista.add(clienteDto);
			}
			domiciliosClienteDto.setDireccionesClienteDto(lista);			
			
			
			lista = new ArrayList();
			QGDireccionesClienteLNMDto clienteLNMDto = null;
			for(int i=1; i <=servicio.getValorAsInt("DIR-OCU-NU").intValue(); i++){
				clienteLNMDto = new QGDireccionesClienteLNMDto();
				
				clienteLNMDto.setTipDomicilio(servicio.getValorAsString("GEO-DIR-LNM-GR.DS-TIPO-DIRE", i));
				clienteLNMDto.setCodigoProvincia(servicio.getValorAsString("CO-PROVINCIA-UNM", i));
				clienteLNMDto.setDescProvincia(servicio.getValorAsString("GEO-DIR-LNM-GR.DS-PROVINCIA", i));
				clienteLNMDto.setDescLocalidad(servicio.getValorAsString("DS-ENT-POBLAC-UNM", i));
				clienteLNMDto.setCodLocalidad(servicio.getValorAsString("CO-INE-ENT-POB-UNM", i));
				clienteLNMDto.setMunicipio(servicio.getValorAsString("NO-MUNICIPIO-UNM", i));
				clienteLNMDto.setCodPostal(servicio.getValorAsString("CO-POSTAL-UNM", i));
				clienteLNMDto.setCodTipoVia(servicio.getValorAsString("CO-TIP-VIA-UNM", i));
				clienteLNMDto.setNomTipoVia(servicio.getValorAsString("NO-TIP-VIA-UNM", i));
				clienteLNMDto.setNombCalle(servicio.getValorAsString("NO-CALLE-UNM", i));
				clienteLNMDto.setCodCalle(servicio.getValorAsString("CO-CALLE-INE-UNM", i));
				clienteLNMDto.setNomAte(servicio.getValorAsString("DS-NOM-ATE-UNM", i));
								
				lista.add(clienteLNMDto);
			
			}
			domiciliosClienteDto.setDireccionesClienteLNMDto(lista);
			
			// agregar lista de datos
			listaDatos.add(domiciliosClienteDto);
		} catch (NAWRException e) {
			logger.info("error dao --> " + e.getMessage(),e);			
			
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}finally{
			if(servicio != null)
				servicio.unload();
		}
		
		globalDto.setlistaDatos(listaDatos);
		globalDto.setlistaMensajes(listaMensajes);
		logger.info("******---FIN obtenerDireccionesCliente de QGBuscadorClientesDaoNA---******");		
		return globalDto;
	}	
	
	/**
	 * Se llamará al servicio de negocio QGF0013
	 */

	public QGCGlobalDto obtenerListadoClientes(QGBuscadorClientesDto buscadorClientesDto) {
		logger.info("******---obtenerListadoClientes de TWBuscadorClientesDaoNA---******");
		boolean bPrepago = false;
		QGNpaNA servicio = null;
		QGCGlobalDto globalDto = new QGCGlobalDto();
		QGUsuario usuarioLogado = obtenerUsuarioLogado();
		ArrayList listaDatos = new ArrayList();
		ArrayList listaMensajes = new ArrayList();
		
		try {
			servicio = new QGNpaNA("QGF0013");
			
			servicio.setValor("NACabAnagramaLlamante", "QG");
			servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());
			
			servicio.setValor("IN-UNI-NGC-CLI-GBL", buscadorClientesDto.getUnidadNegocio());
			servicio.setValor("IN-MODALIDAD-UNM", buscadorClientesDto.getModalidadMovil());		
			servicio.setValor("CO-CLIENTE-NSCO", buscadorClientesDto.getCodigoCliente());
			servicio.setValor("CO-TIPO-DOCUMENTO", buscadorClientesDto.getTipoDocumento());
			servicio.setValor("NU-DOCUMENTO", buscadorClientesDto.getNumeroDocumento());
			servicio.setValor("DS-RAZON-SOCIAL", buscadorClientesDto.getRazonSocial());
			servicio.setValor("DS-NOMBRE", buscadorClientesDto.getNombreCliente());
			servicio.setValor("DS-APELLIDO-1", buscadorClientesDto.getPrimerApellido());
			servicio.setValor("DS-APELLIDO-2", buscadorClientesDto.getSegundoApellido());
			servicio.setValor("PGN-TX", buscadorClientesDto.getTextoPaginacion());
			servicio.setValor("OCU-NU", buscadorClientesDto.getNumeroOcurrencias());
			
			servicio.ejecutarTransaccion();
			
			QGBuscadorClientesDto resultados = new QGBuscadorClientesDto();
			buscadorClientesDto.setIndicadorPaginacion(servicio.getValorAsString("SALIDA.IND-PGN-IN"));
			buscadorClientesDto.setTextoPaginacion(servicio.getValorAsString("SALIDA.PGN-TX"));  
			
			
			for(int i=1; i <= servicio.getValorAsInt("SALIDA.OCU-NU").intValue(); i++){
				resultados.setInUniNgcCliGbl(servicio.getValorAsString("IN-UNI-NGC-CLI-GBL", i));
				resultados.setInModalidad(servicio.getValorAsString("IN-MODALIDAD-UNM", i));			
				resultados.setCodigoCliente(servicio.getValorAsString("CO-CLIENTE-GBL", i));          
				resultados.setTipoDocumento(servicio.getValorAsString("CO-TIPO-DOCUMENTO", i));    
				resultados.setNumeroDocumento(servicio.getValorAsString("NU-DOCUMENTO", i));  
				resultados.setRazonSocial(servicio.convertirHTML(servicio.getValorAsString("DS-RAZON-SOCIAL", i)));      
				resultados.setNombreCliente(servicio.convertirHTML(servicio.getValorAsString("DS-NOMBRE", i)));    
				resultados.setPrimerApellido(servicio.convertirHTML(servicio.getValorAsString("DS-APELLIDO-1", i)));			
				resultados.setSegundoApellido(servicio.convertirHTML(servicio.getValorAsString("DS-APELLIDO-2", i)));  
				resultados.setEstadoCliente(servicio.getValorAsString("DS-ESTADO-CLIENTE", i));    
				resultados.setCodigoSegmento(servicio.getValorAsString("CO-SEGMENTO", i));   
				resultados.setCodigoSubsegmento(servicio.getValorAsString("CO-SUBSEGMENTO", i));								
				resultados.setCoTipoDocumento(servicio.getValorAsString("CO-TIPO-DOCUMENTO", i));		
				resultados.setDsTipoDocumento(servicio.getValorAsString("DS-TIPO-DOCUMENTO", i));   
				resultados.setDsSegmento(servicio.getValorAsString("DS-SEGMENTO", i));   
				resultados.setDsSubsegmento(servicio.getValorAsString("DS-SUBSEGMENTO", i));
				
				listaDatos.add(resultados);
				resultados = new QGBuscadorClientesDto();
			}
			globalDto.setlistaDatos(listaDatos);
			globalDto.setlistaMensajes(listaMensajes);
		} catch (NAWRException e) {
			logger.info("error dao --> " + e.getMessage(),e);
			
			throw new QGApplicationException(e.getCampo("CACV_VARMSG"));
		}finally{
			if(servicio != null) 
				servicio.unload();
		}

		logger.info("******---FINAL obtenerListadoClientes de QGBuscadorClientesDaoNA---******");
		return globalDto;
	}	
	
	/**
	 * Se llamará al servicio de negocio QGF0014
	 */

	public QGCGlobalDto obtenerTipoDocumento(String codigoLinea, String codigoModalidad, String codigoDocumento) {
		  logger.info(">> obtenerTipoDocumento() linea={}, modalidad={}");

	        QGNpaNA servicio = null;
	        QGUsuario usuarioLogado = obtenerUsuarioLogado();
	        QGCGlobalDto dto = new QGCGlobalDto();
				ArrayList lista = new ArrayList();
	        try {
	            // 1) Instanciamos el servicio NA, ajusta el descriptor a tu caso real:
	            servicio = new QGNpaNA("TWF0001");  
	            // O el que uses en la app TW para “tipos de documento”

	            // 2) Cabecera estándar
	            servicio.setValor("NACabAnagramaLlamante", "TW");
//				servicio.setValor("CACV_USUARIO_SESION", usuarioLogado.getUsername());

	            // 3) Seteamos parámetros de entrada (nombres de campo reales de tu descriptor NA)
//	            servicio.setValor("CAMPO_LINEA", codigoLinea);
//	            servicio.setValor("CAMPO_MODALIDAD", codigoModalidad);

	            // 4) Ejecutamos
	            servicio.ejecutarTransaccion();

	            // 5) Parseamos la salida. Ej: su descriptor devuelva 1..N filas con
	            //    "DOC-CODIGO" / "DOC-DESCRIPCION" (ajusta según tus nombres).
	            for (int i = 1; i <= 200; i++) {
	                String cod = servicio.getValorAsString("DOC-CODIGO", i);
	                if (cod == null) {
	                    break; // no hay más
	                }
	                String desc = servicio.getValorAsString("DOC-DESCRIPCION", i);

	                // Creamos DTO
	              
	               
	            }

	          
	            return dto;

	        } catch (NAWRException e) {
	        	logger.error(e.getMessage());
				QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e,
						QGIdentificadoresDescriptores.DESCRIPTOR_GESTION_ENCARTES);

	        } finally {
	            if (servicio != null) {
	                servicio.unload(); 
	            }
	        }
			return dto;
	    }

	public QGCGlobalDto obtenerHistoricoSegmentacion(QGBuscadorClientesDto buscadorClientesDto) {
		logger.info("******---obtenerHistoricoSegmentacion de QGBuscadorClientesDaoNA---******");
		QGNpaNA servicio = null;
		QGCGlobalDto qGCGlobalDto = new QGCGlobalDto();
		ArrayList listaDatos = new ArrayList();

		try {
			servicio = QGNpaNA.obtenerServicio(QGIdentificadoresDescriptores.DESCRIPTOR_HIST_SEGMENTACION,this.obtenerUsuarioLogado());

			// Seteamos la copy de entrada
			
			servicio.setValor("ENTRADA.CO-CLIENTE-GBL", buscadorClientesDto.getCodigoCliente());
			
			// Ejecutamos la transaccion del servicio.
			servicio.ejecutarTransaccion();

			// Si es una consulta o historico...
			for (int i = 1; i <= servicio.getValorAsInt("SALIDA.OCU-NU").intValue(); i++) {
				QGHistorialSegmentacionCliDto histSeg = new QGHistorialSegmentacionCliDto();

				histSeg.setCodSegmento(servicio.getValorAsString("CO-SEGMENTO-GBL", i));
				histSeg.setDesSegmento(servicio.getValorAsString("DS-SEGMENTO-UNM", i));
				histSeg.setCodSubSegmento(servicio.getValorAsString("CO-SUBSEGMENTO-GBL", i));
				histSeg.setDesSubSegmento(servicio.getValorAsString("DS-SUBSEGMENTO-UNM", i));
				histSeg.setFecMod(QGUtilidadesFechasUtils.formatearTimeStampDesdeCopy(servicio.getValorAsString("IT-ULT-MOD", i),
								  QGUtilidadesFechasUtils.FORMATO_FECHA_1));
				
				listaDatos.add(histSeg);
				}
				
			qGCGlobalDto.setlistaDatos(listaDatos);
		} catch (NAWRException e) {
			logger.error(e.getMessage());
			QGUtilidadesNegocioUtils.tratarExcepcionesServicio(e,
					QGIdentificadoresDescriptores.DESCRIPTOR_GESTION_ENCARTES);

		} finally {
			if (servicio != null) {
				servicio.unload();
			}
		}
		logger.info("******---FIN obtenerHistoricoSegmentacion de QGBuscadorClientesDaoNA---******");				
		return qGCGlobalDto;
	}
}
