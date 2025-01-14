<%@ page language="java" contentType="text/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

var CGLOBAL = function (){	
	/*Plantillas*/
	var tplDom;
	var tplDomLNM;
	var tplConsent;
	var tplOneRow;
	var tplConsDat
	var tplHeadConsent;
	var tplDetConsent;
	var tplNuevaConsent;
	var filtro;
	var myMask;
	
	var datosLConsent;
	var datosTipoObjeto;
	var datosEstadoGest;
	var datosHistorialConsent;
	var datosDomicilios;
	var datosGenerales;
	var combosDetalleConsent;
	var collectionDatosDetCons = new Ext.util.MixedCollection();
	var collectionDatosListadoCons = new Ext.util.MixedCollection();
	var modoNuevaCom = false;
	var modoAltaCom = false;
    
    var esVigente = true;
    
	var iniciarPestanias = function (){
		mostrarDatosGenerales();
	}
	
	var crearPlantillas = function (){
		/*Plantilla para los domicilios geograficos de Domicilios*/
		var arrTplDom = [];
		arrTplDom.push('<table class="{sep}"><colgroup><col width="120"/><col width="200"/><col width="115"/><col width="100"/><col width="100"/></colgroup>');
        arrTplDom.push('<tbody><tr class="sep">');
        arrTplDom.push('<td class="pLeft20"><div class="colA"><label>PROVINCIA</label><br/><span>{descProvincia}</span></div></td><td class="pLeft20"><div class="colB"><label>LOCALIDAD</label><br/><span>{descLocalidad}</span></div></td>');
        arrTplDom.push('<td><label>C&Oacute;DIGO POSTAL</label><br/><span>{codigoPostal}</span></td>');
        arrTplDom.push('<td><label>PA&Iacute;S</label><br/><span>{descPais}</span></td>');
        arrTplDom.push('<td><label>TIPO DOMICILIO</label><br/><span>{descTipoDireccion}</span></td></tr>');
        arrTplDom.push('<tr class="sep">');
        arrTplDom.push('<td class="pLeft20"><div class="colA"><label>TIPO V&Iacute;A</label><br/><span>{descTipoVia}</span></div></td><td class="pLeft20"><div class="colB"><label>DIRECCI&Oacute;N</label><br/><span>{descCalle}</span></div></td>');
        arrTplDom.push('<td><label>Nº</label><br/><span>{finca}</span>');
        arrTplDom.push('<td><label>BIS DUPLICADO</label><br/><span>{bisDuplic}</span>');
        arrTplDom.push('<td><label>BLOQUE</label><br/><span>{bloque}</span>');
        arrTplDom.push('<td width="200" ><label>PORTAL</label><br/><span>{portal}</span></td></tr><tr>');
        arrTplDom.push('<td class="pLeft20"><div class="colA"><label>ESCALERA</label><br/><span>{escalera}</span></div></td><td class="pLeft20"><div class="colB"><label>PLANTA</label><br/><span>{planta}</span></div></td>');
        arrTplDom.push('<td><label>LETRA</label><br/><span>{letra}</span></td>');
        arrTplDom.push('<td width="120"><label>ACLARADOR DE FINCA</label><br/><span>{aclaradorFinca}</span></td>');
        arrTplDom.push('</tr></tbody></table>');
		tplDom = new Ext.XTemplate(arrTplDom.join(''));
		
		/*Plantilla para los domicilios geofraficos LNM*/
		var arrTplDomLNM = [];
		arrTplDomLNM.push('<table class="{sep}"><colgroup><col width="120"/><col width="200"/><col width="115"/><col width="100"/><col width="*"/></colgroup>');
        arrTplDomLNM.push('<tbody><tr class="sep">');
        arrTplDomLNM.push('<td class="pLeft20"><div class="colA"><label>PROVINCIA</label><br/><span>{descProvincia}</span></div></td><td class="pLeft20"><div class="colB"><label>LOCALIDAD</label><br/><span>{descLocalidad}</span></div></td>');
        arrTplDomLNM.push('<td><label>MUNICIPIO</label><br/><span>{municipio}</span></td>');
        arrTplDomLNM.push('<td><label>C&Oacute;DIGO POSTAL</label><br/><span>{codPostal}</span></td>');
        arrTplDomLNM.push('<td><label>TIPO DOMICILIO</label><br/><span>{tipDomicilio}</span></td></tr>');
        arrTplDomLNM.push('<tr class="sep">');
        arrTplDomLNM.push('<td class="pLeft20"><div class="colA"><label>TIPO V&Iacute;A</label><br/><span>{nomTipoVia}</span></div></td><td class="pLeft20" width="150"><div class="colB"><label>DIRECCI&Oacute;N</label><br/><span>{nombCalle}</span></div></td>');
        arrTplDomLNM.push('<td><label>NOMBRE DE ATENCI&Oacute;N</label><br/><span>{nomAte}</span></td></tr>');
        arrTplDomLNM.push('<tr class="sep">');
        arrTplDomLNM.push('<td colspan="2" class="pLeft20" width="320"><div class="colA"><label>C&Oacute;DIGO INE DE LA ENTIDAD DE POBLACI&Oacute;N</label><br/><span>{codLocalidad}</span></div></td><td width="180"><div class="colB"><label>C&Oacute;DIGO INE DE LA CALLE</label><br/><span>{codCalle}</span></div></td>');
        
        arrTplDomLNM.push('</tr></tbody></table>');
		tplDomLNM = new Ext.XTemplate(arrTplDomLNM.join(''));
		
		/*Plantilla para el resto de datos de Domicilios*/
		var arrTplOneRow = [];
		arrTplOneRow.push('<table class="{sep}"><tbody><tr><td class="oneRow pLeft20"><label>{label}</label><br/><span>{descTipoDireccion}</span></td></tr>');
        arrTplOneRow.push('<tr><td class="oneRow pLeft20"><label>{label}</label><br/><span>{descDirLogica}</span></td></tr>');
        arrTplOneRow.push('</tbody></table>');
		tplOneRow = new Ext.XTemplate(arrTplOneRow.join(''));
		
		/*Plantilla para los consentimientos*/
		var arrTplConsHead = [];
		arrTplConsHead.push('<div class="cabDatCons"><b class="headTitL"></b><b class="headTitR"></b>');
		arrTplConsHead.push('<div class="titCons"><b class="icoVerde"></b><span class="codCons">[{lblCodD}]</span><span class="descCons">{lblDescD}</span></div>');
		arrTplConsHead.push('<div class="divInfoCons"><div class="infoCons"><b class="{icoExplotacion}"></b><b class="headInfoR"></b>');
		arrTplConsHead.push('<div class="colA"><span>{lblExplotacionD}</span></div><div class="colB"><span>{lblFxCambioEstado}</span></div></div>');
		arrTplConsHead.push('<div class="infoEst WEstGest"><b class="{icoEstGest}"></b><span class="estadoGestCD">{lblEstGest}</span></div>');
		arrTplConsHead.push('<div class="infoEst WTipoLog tAc"><span class="">{lblTipoLog}</span></div>');
		arrTplConsHead.push('<div class="infoEst WBtHist"><div class="bTit bTitHist botonesHistorial">');    			
		arrTplConsHead.push('<span class="bTitCont"><b class="bTitL"></b><b class="bTitR"></b><b class="ico"></b>Historial</span></div></div></div></div>');
		tplConsent = new Ext.XTemplate(arrTplConsHead.join(''));
		
		/*Plantilla para el historial de consentimientos*/
		tplConsDat = new Ext.XTemplate('<div class="histCons"><span class="fecha">{lblFecha}</span><span class="codigo">{lblCodigo}</span><span class="desc">{lblDescripcion}</span><span>{lblAtrAsoc}</span></div>');
		
		/*Plantilla para la cabecera del historial de consentimientos*/
		var arrTplConsCabHead = [];
		/*arrTplConsCabHead.push('<div class="headCabDatCons">');
		arrTplConsCabHead.push('<div class="cabDatCons">');
		arrTplConsCabHead.push('<b class="headTitL"></b><b class="headTitR"></b>');
		arrTplConsCabHead.push('<div class="titCons">Consentimiento</div>');
		arrTplConsCabHead.push('<div class="divInfoCons"><div class="infoCons">Estado Consentimiento</div>');
		arrTplConsCabHead.push('<div class="infoEst WEstGest"><span class="estadoGestCD">Estado Gesti&oacute;n</span></div>');
		arrTplConsCabHead.push('<div class="infoEst WTipoLog tAc"><span class="logAplic">L&oacute;gica Aplicac</span></div>');
		arrTplConsCabHead.push('<div class="infoEst WBtHist"><div class="bTit bTitHist">');			
		arrTplConsCabHead.push('<span class="bTitCont">Historial</span>');
		arrTplConsCabHead.push('</div></div></div></div></div>');*/
		
		arrTplConsCabHead.push('<div class="headCabDatCons">');
		
		arrTplConsCabHead.push('<b class="headTitL"></b><b class="headTitR"></b>');
		arrTplConsCabHead.push('<div class="titCons">Consentimiento</div>');
		arrTplConsCabHead.push('<div class="divInfoCons"><div class="EstCons">Estado Consentimiento</div>');
		arrTplConsCabHead.push('<div class="WEstGest"><span>Estado Gesti&oacute;n</span></div>');
		arrTplConsCabHead.push('<div class="WTipoLog tAc"><span class="logAplic">L&oacute;gica Aplicac</span></div>');
		arrTplConsCabHead.push('<div class="WBtHist">');			
		arrTplConsCabHead.push('<span>Historial</span>');
		arrTplConsCabHead.push('</div></div></div>');
		
		tplHeadConsent = new Ext.XTemplate(arrTplConsCabHead.join(''));
		
		/*Plantilla para el detalle de consentimientos*/
		var arrTplConsDet = [];
		arrTplConsDet.push('<div class="detHistCons"><table class="TBnoSpace"><tbody>');
		arrTplConsDet.push('<tr><td><label>Tipo de Comunicaci&oacute;n:</label><br/>');
		arrTplConsDet.push('<select class="w282 selTipoComunicacion"></select></td>');
		arrTplConsDet.push('<td><div><label>Estado de la gesti&oacute;n:</label><br/><select class="w150 selEstadoGestion"></select></div>');
		arrTplConsDet.push('<div><label>Estado del derecho:</label><br/><select class="selEstadoDerecho"><option>Si consiente</option><option>No consiente</option></select></div>');
		arrTplConsDet.push('<div><label>Fecha cambio de estado:</label><br/><div class="divCalendar"><input type="text" class="inputCalendar" /><input type="image" class="icoCalendar" src="'+contexto+'/images/iconos/QGicoCalendar.gif"/></div></div>');
		arrTplConsDet.push('</td></tr><tr><td>');
		arrTplConsDet.push('<label>Objeto de aplicaci&oacute;n del consentimiento:</label></td><td>');
		arrTplConsDet.push('<div class="grupoRad"><input type="radio" class="radObservaciones" name="radHConsent" value="observaciones"><label class="radObservaciones">Observaciones</label>');
		arrTplConsDet.push('<input type="radio" class="radIncidencia" name="radHConsent" value="incidencia"><label class="radIncidencia">Incidencia</label></div></td></tr>');
		arrTplConsDet.push('<tr><td class="vTop"><select class="w130 selTipoObjeto"></select><input type="text" class="mLeft5 w138 txtTipoObjeto" value="" /></td>');
		arrTplConsDet.push('<td><textarea class="wTAFC txtArea"></textarea></td></tr><tr><td>');
		arrTplConsDet.push('<div><label>Campa&ntilde;a:</label><br/><select class="w130 selCampanias"></select></div>');
		arrTplConsDet.push('<div><label>Medio comunicaci&oacute;n:</label><br/><select class="w150 selMediosCom"></select></div></td><td>');
		arrTplConsDet.push('<div><label>Tipo de ubicaci&oacute;n:</label><br/><select class="w150 selTipoUbicacion"></select></div>');
		arrTplConsDet.push('<div class="noPad"><label>Ubicaci&oacute;n f&iacute;sica:</label><br/><input type="text" value="" class="w416 txtUbicacionFisica"/></div>');
		arrTplConsDet.push('</td></tr></tbody></table></div>');
		
		tplDetConsent = new Ext.XTemplate(arrTplConsDet.join(''));
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
		/*Plantilla para los botones al pulsar nueva comunicacion*/
		var arrTplConsNueva = [];
		arrTplConsNueva.push('<div class="divUsMod"><label>Usuario modificaci&oacute;n: </label><span class="ususarioMod">{usuarioMod}</span>');
		arrTplConsNueva.push('<div class="divBtn">');
		arrTplConsNueva.push('<input type="image" class="btn btnAceptar" src="'+contexto+'/images/botones/QGbtAceptar.gif" alt="Aceptar" onclick="return false;"/>');
		arrTplConsNueva.push('<input type="image" class="btn btnCancelar" src="'+contexto+'/images/botones/QGbtCancelar.gif" alt="Cancelar" onclick="return false;" />');
		arrTplConsNueva.push('</div></div>');
		
		tplNuevaConsent = new Ext.XTemplate(arrTplConsNueva.join(''));
		</sec:authorize>
	}
	
	/*=====================================================*/
	/*====================DATOS GENERALES==================*/
	/*=====================================================*/
	var mostrarDatosGenerales = function (){
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
		Ext.get('btnNuevaComunicacion').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('bhBotones').child('.bhSep').setVisibilityMode(Ext.Element.DISPLAY).hide();
		</sec:authorize>
		Ext.get('divDatosGenerales').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('divConsentimientos').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divDomicilios').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		Ext.get('pesDatGen').addClass('activa');
		Ext.get('pesConsentimientos').removeClass('activa');
		Ext.get('pesDomicilios').removeClass('activa');
		
		if(!datosGenerales || datosGenerales.total == 0){
			obtenerDatosGeneralesCons();
		}		
	}
	
	var obtenerDatosGeneralesCons = function (){
		myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
		myMask.show();
		
		var datGen = {
			codigoCliente:Ext.get('FCHistorialCodCliente').dom.value
		};
		
		Ext.Ajax.request({
    		url:contexto + 'DatosGenerales.do',
    		params:{
    			buscar:'',
    			datosGeneralesJSON:Ext.encode(datGen)
    		},
    		callback:function (options, success, response){
    			myMask.hide();
    			if (success){
    				datosGenerales = Ext.util.JSON.decode (response.responseText);
					if (datosGenerales.success){
   						//Pueden llegar mensajes informativos.
    					if (datosGenerales.message && datosGenerales.message.length>0){
	    					Ext.each(datosGenerales.message,function(message){
		    					Ext.Msg.show({
								   title:'Información',
								   cls:'cgMsgBox',
								   msg: '<span>'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
		    				});
	    				}
	    				
	    				//Convertimos las fechas de cliente Fijo
	    				datosGenerales.datos[0].clienteFijoDto[0].fxAltaCliente = convertDateFromJSONLib (datosGenerales.datos[0].clienteFijoDto[0].fxAltaCliente);
	    				datosGenerales.datos[0].clienteFijoDto[0].fxAltaRegistro = convertDateFromJSONLib (datosGenerales.datos[0].clienteFijoDto[0].fxAltaRegistro);
	    				datosGenerales.datos[0].clienteFijoDto[0].fxFinVigencia = convertDateFromJSONLib (datosGenerales.datos[0].clienteFijoDto[0].fxFinVigencia);
	    				datosGenerales.datos[0].clienteFijoDto[0].fxIniVigencia = convertDateFromJSONLib (datosGenerales.datos[0].clienteFijoDto[0].fxIniVigencia);
	    				datosGenerales.datos[0].clienteFijoDto[0].fxUltCambioEstado = convertDateFromJSONLib (datosGenerales.datos[0].clienteFijoDto[0].fxUltCambioEstado);
	    				datosGenerales.datos[0].clienteFijoDto[0].fxUltCambioSubSegmento = convertDateFromJSONLib (datosGenerales.datos[0].clienteFijoDto[0].fxUltCambioSubSegmento);
	    				datosGenerales.datos[0].clienteFijoDto[0].fxUltModRegistro = convertDateFromJSONLib (datosGenerales.datos[0].clienteFijoDto[0].fxUltModRegistro);
	    				
	    				//Convertimos las fechas de cliente Movil Contrato   				
	    				datosGenerales.datos[0].clienteMovilCtoDto[0].fxAltaCliente = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilCtoDto[0].fxAltaCliente);
	    				datosGenerales.datos[0].clienteMovilCtoDto[0].fxAltaRegistro = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilCtoDto[0].fxAltaRegistro);
	    				datosGenerales.datos[0].clienteMovilCtoDto[0].fxFinVigencia = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilCtoDto[0].fxFinVigencia);
	    				datosGenerales.datos[0].clienteMovilCtoDto[0].fxIniVigencia = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilCtoDto[0].fxIniVigencia);
	    				datosGenerales.datos[0].clienteMovilCtoDto[0].fxUltCambioEstado = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilCtoDto[0].fxUltCambioEstado);
	    				datosGenerales.datos[0].clienteMovilCtoDto[0].fxCambioSegmento = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilCtoDto[0].fxCambioSegmento);
	    				datosGenerales.datos[0].clienteMovilCtoDto[0].fxUltModificacion = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilCtoDto[0].fxUltModificacion);
	    				
	    				
	    				//Convertimos las fechas de cliente Movil Prepago
	    				datosGenerales.datos[0].clienteMovilPreDto[0].fxCambioEstado = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilPreDto[0].fxCambioEstado);
	    				datosGenerales.datos[0].clienteMovilPreDto[0].fxNacimiento = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilPreDto[0].fxNacimiento);
	    				datosGenerales.datos[0].clienteMovilPreDto[0].fxCambioSegmento = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilPreDto[0].fxCambioSegmento);
	    				datosGenerales.datos[0].clienteMovilPreDto[0].fxIniVigencia = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilPreDto[0].fxIniVigencia);
	    				datosGenerales.datos[0].clienteMovilPreDto[0].fxFinVigencia = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilPreDto[0].fxFinVigencia);
	    				datosGenerales.datos[0].clienteMovilPreDto[0].fxAltaRegistro = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilPreDto[0].fxAltaRegistro);
	    				datosGenerales.datos[0].clienteMovilPreDto[0].fxUltModificacion = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilPreDto[0].fxUltModificacion);
	    				
	    				//Convertimos las fechas de cliente Movil Prepago Representante
	    				datosGenerales.datos[0].clienteMovilRPSDto[0].fxIniVigencia = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilRPSDto[0].fxIniVigencia);
	    				datosGenerales.datos[0].clienteMovilRPSDto[0].fxFinVigencia = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilRPSDto[0].fxFinVigencia);
	    				datosGenerales.datos[0].clienteMovilRPSDto[0].fxAltaRegistro = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilRPSDto[0].fxAltaRegistro);
	    				datosGenerales.datos[0].clienteMovilRPSDto[0].fxUltModificacion = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilRPSDto[0].fxUltModificacion);
	    				
	    				//Convertimos las fechas de convergentes
	    				datosGenerales.datos[0].datosClienteDto[0].fxAltaRegistro = convertDateFromJSONLib (datosGenerales.datos[0].datosClienteDto[0].fxAltaRegistro);
	    				datosGenerales.datos[0].datosClienteDto[0].fxFinVigencia = convertDateFromJSONLib (datosGenerales.datos[0].datosClienteDto[0].fxFinVigencia);
	    				datosGenerales.datos[0].datosClienteDto[0].fxIniVigencia = convertDateFromJSONLib (datosGenerales.datos[0].datosClienteDto[0].fxIniVigencia);
	    				datosGenerales.datos[0].datosClienteDto[0].fxUltEstadoCli = convertDateFromJSONLib (datosGenerales.datos[0].datosClienteDto[0].fxUltEstadoCli);
	    				datosGenerales.datos[0].datosClienteDto[0].fxUltModificacion = convertDateFromJSONLib (datosGenerales.datos[0].datosClienteDto[0].fxUltModificacion);
	    				datosGenerales.datos[0].datosClienteDto[0].fxUltReactivacion = convertDateFromJSONLib (datosGenerales.datos[0].datosClienteDto[0].fxUltReactivacion);
	    				datosGenerales.datos[0].datosClienteDto[0].fxUltSubSegmento = convertDateFromJSONLib (datosGenerales.datos[0].datosClienteDto[0].fxUltSubSegmento);
	    					    				
	    				cargarDatosGenerales();
	    				
    				}else{
    					myMask.hide();
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span>'+datosGenerales.message+'</span><br/>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
    				}
    			}
    		}
    	});
	}
	
	var cargarDatosGenerales = function (){
		if(datosGenerales.datos[0].datosClienteDto[0].inUnidadNegocio == 'A'){
			Ext.get('btnDatosLNF').setVisibilityMode(Ext.Element.DISPLAY).show();
			if(datosGenerales.datos[0].datosClienteDto[0].inModalidadMovil == 'A'){
				Ext.get('btnDatosLNMC').setVisibilityMode(Ext.Element.DISPLAY).show();
				Ext.get('btnDatosLNMP').setVisibilityMode(Ext.Element.DISPLAY).show();				
			}else if(datosGenerales.datos[0].datosClienteDto[0].inModalidadMovil == 'C'){
				Ext.get('btnDatosLNMC').setVisibilityMode(Ext.Element.DISPLAY).show();
				Ext.get('btnDatosLNMP').setVisibilityMode(Ext.Element.DISPLAY).hide();				
			}else{
				Ext.get('btnDatosLNMC').setVisibilityMode(Ext.Element.DISPLAY).hide();
				Ext.get('btnDatosLNMP').setVisibilityMode(Ext.Element.DISPLAY).show();				
			}
		}else if(datosGenerales.datos[0].datosClienteDto[0].inUnidadNegocio == 'F'){
			Ext.get('btnDatosLNF').setVisibilityMode(Ext.Element.DISPLAY).show();
			Ext.get('btnDatosLNMC').setVisibilityMode(Ext.Element.DISPLAY).hide();
			Ext.get('btnDatosLNMP').setVisibilityMode(Ext.Element.DISPLAY).hide();			
		}else{
			Ext.get('btnDatosLNF').setVisibilityMode(Ext.Element.DISPLAY).hide();
			if(datosGenerales.datos[0].datosClienteDto[0].inModalidadMovil == 'A'){
				Ext.get('btnDatosLNMC').setVisibilityMode(Ext.Element.DISPLAY).show();
				Ext.get('btnDatosLNMP').setVisibilityMode(Ext.Element.DISPLAY).show();				
			}else if(datosGenerales.datos[0].datosClienteDto[0].inModalidadMovil == 'C'){
				Ext.get('btnDatosLNMC').setVisibilityMode(Ext.Element.DISPLAY).show();
				Ext.get('btnDatosLNMP').setVisibilityMode(Ext.Element.DISPLAY).hide();				
			}else{
				Ext.get('btnDatosLNMC').setVisibilityMode(Ext.Element.DISPLAY).hide();
				Ext.get('btnDatosLNMP').setVisibilityMode(Ext.Element.DISPLAY).show();				
			}			
		}
		cargarDatosConvergentes();
		cargarDatosAdicionales();		
	}
	//BTON Datos CONVERGENTES.
	var cargarDatosConvergentes = function (){
		//Ocultamos los datos de Representante	
		Ext.get('colDatosRepresentante').setVisibilityMode(Ext.Element.DISPLAY).hide();	
		Ext.get('etiqCodCliente').dom.childNodes[0].data = 'Código cliente Global:'
		Ext.get('lblDGCodCliente').update (Ext.get('FCHistorialCodCliente').dom.value);
		Ext.get('lblDGTipoDoc').update (datosGenerales.datos[0].datosClienteDto[0].dsTipoDocumento);
		Ext.get('lblDGNDoc').update (datosGenerales.datos[0].datosClienteDto[0].nuDocumento);
		if(datosGenerales.datos[0].datosClienteDto[0].dsRazonSocial.length == 0){
			Ext.get('lblDGNombreRazon').update (datosGenerales.datos[0].datosClienteDto[0].dsNombre);
			Ext.get('lblDGApellido1').update (datosGenerales.datos[0].datosClienteDto[0].dsApellido1);
			Ext.get('lblDGApellido2').update (datosGenerales.datos[0].datosClienteDto[0].dsApellido2);
		}else{
			Ext.get('lblDGNombreRazon').update (datosGenerales.datos[0].datosClienteDto[0].dsRazonSocial);
		}
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].datosClienteDto[0].dsSegmento == null || datosGenerales.datos[0].datosClienteDto[0].dsSegmento == ""){
			Ext.get('lblDGSegmento').update (datosGenerales.datos[0].datosClienteDto[0].coSegmento);
		}
		else{
			Ext.get('lblDGSegmento').update (datosGenerales.datos[0].datosClienteDto[0].dsSegmento);		
		}
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].datosClienteDto[0].dsSubSegmento == null || datosGenerales.datos[0].datosClienteDto[0].dsSubSegmento == ""){
			Ext.get('lblDGSubsegmento').update (datosGenerales.datos[0].datosClienteDto[0].coSubSegmento);
		}
		else{
			Ext.get('lblDGSubsegmento').update (datosGenerales.datos[0].datosClienteDto[0].dsSubSegmento);		
		}
		
		if(datosGenerales.datos[0].datosClienteDto[0].fxUltSubSegmento != null)
			Ext.get('lblDGFechaCambioSeg').update (modificarFechaAnio(datosGenerales.datos[0].datosClienteDto[0].fxUltSubSegmento));
		else	
			Ext.get('lblDGFechaCambioSeg').update ("");
		Ext.get('lblDGEstado').update (datosGenerales.datos[0].datosClienteDto[0].dsEstadoCliente);
		
		if(datosGenerales.datos[0].datosClienteDto[0].fxUltEstadoCli != null)
			Ext.get('lblDGFechaCambioEstado').update (modificarFechaAnio(datosGenerales.datos[0].datosClienteDto[0].fxUltEstadoCli));
		else	
			Ext.get('lblDGFechaCambioEstado').update ("");
		Ext.get('lblDGMotivoEstado').update (datosGenerales.datos[0].datosClienteDto[0].dsMotivoEstado);
		Ext.get('lblDGObservaciones').update (datosGenerales.datos[0].datosClienteDto[0].dsComentariosCliente);
		
		//pintar plantilla DatosConvergentes
		limpiarPlantillas ();
        crearPlantillas ();
        cargarDomicilios();
        
        
	
	}
	
	var cargarDatosAdicionales = function (){

		//Mostramos la descripcion indicada segun codigo
		if (datosGenerales.datos[0].datosClienteDto[0].inCaracterInternacional == "S"){
			Ext.get('lblDGdaClienteInter').update ("Es cliente de car&aacute;cter internacional");	
		}
		else{
			Ext.get('lblDGdaClienteInter').update (datosGenerales.datos[0].datosClienteDto[0].inCaracterInternacional);
		}
		
		Ext.get('lblDGdaPais').update (datosGenerales.datos[0].datosClienteDto[0].dsPais);
		
		if(datosGenerales.datos[0].datosClienteDto[0].fxUltReactivacion != null)
			Ext.get('lblDGdaUltimaReactivacion').update (modificarFechaAnio(datosGenerales.datos[0].datosClienteDto[0].fxUltReactivacion));
		else	
			Ext.get('lblDGdaUltimaReactivacion').update ("");
		
		//Mostramos la descripcion indicada segun codigo
		if (datosGenerales.datos[0].datosClienteDto[0].inExentoImpuesto == "N"){
			Ext.get('lblDGdaExencionImpuestos').update ("No exento de impuestos");
		}
		else if (datosGenerales.datos[0].datosClienteDto[0].inExentoImpuesto == "S"){
			Ext.get('lblDGdaExencionImpuestos').update ("Exento de impuestos");
		}
		else {
			Ext.get('lblDGdaExencionImpuestos').update (datosGenerales.datos[0].datosClienteDto[0].inExentoImpuesto);
		}
		
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].datosClienteDto[0].noTipoFacturaMvl == null || datosGenerales.datos[0].datosClienteDto[0].noTipoFacturaMvl == ""){
			Ext.get('lblDGdaTipoImpresion').update (datosGenerales.datos[0].datosClienteDto[0].coTipoFacturaMvl);
		}
		else{
			Ext.get('lblDGdaTipoImpresion').update (datosGenerales.datos[0].datosClienteDto[0].noTipoFacturaMvl);
		}
		
		Ext.get('lblDGdaMoneda').update (datosGenerales.datos[0].datosClienteDto[0].coMoneda);
		
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].datosClienteDto[0].dsNivelConfidencial == null || datosGenerales.datos[0].datosClienteDto[0].dsNivelConfidencial == ""){
			Ext.get('lblDGdaNivelConf').update (datosGenerales.datos[0].datosClienteDto[0].coNivelConfidencial);
		}
		else{
			Ext.get('lblDGdaNivelConf').update (datosGenerales.datos[0].datosClienteDto[0].dsNivelConfidencial);
		}
		
		Ext.get('lblDGdaOficinaAtencion').update (datosGenerales.datos[0].datosClienteDto[0].coUnidadGestion);
		Ext.get('lblDGdaTipoCliente').update (datosGenerales.datos[0].datosClienteDto[0].coTipoCliente);
		
		//Mostramos la descripcion indicada segun codigo
		if (datosGenerales.datos[0].datosClienteDto[0].inCabecera == ""){
			Ext.get('lblDGdaCabecera').update ("No es cabecera");
		}
		else if (datosGenerales.datos[0].datosClienteDto[0].inCabecera == "C"){
			Ext.get('lblDGdaCabecera').update ("Cabecera de grupo");
		}
		else if (datosGenerales.datos[0].datosClienteDto[0].inCabecera == "S"){
			Ext.get('lblDGdaCabecera').update ("Cabecera de subgrupo empresarial de 2º nivel o mayor");
		}
		else{
			Ext.get('lblDGdaCabecera').update (datosGenerales.datos[0].datosClienteDto[0].inCabecera);
		}
		
		Ext.get('lblDGdaSectorEconomico').update (datosGenerales.datos[0].datosClienteDto[0].dsSectorEconomico);
		Ext.get('lblDGdaIdioma').update (datosGenerales.datos[0].datosClienteDto[0].dsIdioma);
		
		//Mostramos la descripcion indicada segun codigo
		if (datosGenerales.datos[0].datosClienteDto[0].coPlanClientes == "01"){
			Ext.get('lblDGdaPlanCuentas').update ("Key account");
		}
		else{
			Ext.get('lblDGdaPlanCuentas').update (datosGenerales.datos[0].datosClienteDto[0].coPlanClientes);
		}
		Ext.get('lblDGdaActividadEconom').update (datosGenerales.datos[0].datosClienteDto[0].dsCNAE);
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].datosClienteDto[0].dsSgmDatawarehou == null || datosGenerales.datos[0].datosClienteDto[0].dsSgmDatawarehou == ""){
			Ext.get('lblDGdaSegmentoSWH').update (datosGenerales.datos[0].datosClienteDto[0].coSgmDatawarehou);
		}
		else{
			Ext.get('lblDGdaSegmentoSWH').update (datosGenerales.datos[0].datosClienteDto[0].dsSgmDatawarehou);
		}
		
		Ext.get('lblDGdaAtencionCliente').update (datosGenerales.datos[0].datosClienteDto[0].coNivelAtencion);
		
		//Mostramos la descripcion indicada segun codigo
		if (datosGenerales.datos[0].datosClienteDto[0].inUnidadNegocio == "F"){
			Ext.get('lblDGdaUnidadNegGlobal').update ("Fija");
		}
		else if (datosGenerales.datos[0].datosClienteDto[0].inUnidadNegocio == "M"){
			Ext.get('lblDGdaUnidadNegGlobal').update ("M&oacute;vil");
		}
		else if (datosGenerales.datos[0].datosClienteDto[0].inUnidadNegocio == "A"){
			Ext.get('lblDGdaUnidadNegGlobal').update ("Convergente");
		}
		else{
			Ext.get('lblDGdaUnidadNegGlobal').update (datosGenerales.datos[0].datosClienteDto[0].inUnidadNegocio);
		}
		
		//Mostramos la descripcion indicada segun codigo
		if (datosGenerales.datos[0].datosClienteDto[0].coIdentidad == "V"){
			Ext.get('lblDGdaIdentificacion').update ("Var&oacute;n");
		}
		else if(datosGenerales.datos[0].datosClienteDto[0].coIdentidad == "M"){
			Ext.get('lblDGdaIdentificacion').update ("Mujer");
		}
		else if(datosGenerales.datos[0].datosClienteDto[0].coIdentidad == "E"){
			Ext.get('lblDGdaIdentificacion').update ("Empresa");
		}
		else if(datosGenerales.datos[0].datosClienteDto[0].coIdentidad == "C"){
			Ext.get('lblDGdaIdentificacion').update ("Telef&oacute;nica");
		}
		else{
			Ext.get('lblDGdaIdentificacion').update (datosGenerales.datos[0].datosClienteDto[0].coIdentidad);
		}
		
		if(datosGenerales.datos[0].datosClienteDto[0].fxIniVigencia != null)
			Ext.get('lblDGdaFInicioVigenciaCGBL').update (modificarFechaAnio(datosGenerales.datos[0].datosClienteDto[0].fxIniVigencia));
		else
			Ext.get('lblDGdaFInicioVigenciaCGBL').update ("");
			
		if(datosGenerales.datos[0].datosClienteDto[0].fxFinVigencia != null)
			Ext.get('lblDGdaFinVigenciaCGBL').update (modificarFechaAnio(datosGenerales.datos[0].datosClienteDto[0].fxFinVigencia));
		else
			Ext.get('lblDGdaFinVigenciaCGBL').update ("");
			
		Ext.get('lblDGdaClientePadre').update (datosGenerales.datos[0].datosClienteDto[0].codigoCliente);
		
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].datosClienteDto[0].dsCaracterInternacional == null || datosGenerales.datos[0].datosClienteDto[0].dsCaracterInternacional == ""){
			Ext.get('lblDGdaTipoCarInter').update (datosGenerales.datos[0].datosClienteDto[0].coCaracterInternacional);
		}
		else{
			Ext.get('lblDGdaTipoCarInter').update (datosGenerales.datos[0].datosClienteDto[0].dsCaracterInternacional);		
		}
		Ext.get('lblDGdaNombreCorto').update (datosGenerales.datos[0].datosClienteDto[0].noComercial);
		
		//Mostramos la descripcion indicada segun inClienteCabecera
		if (datosGenerales.datos[0].clienteFijoDto[0].inClienteCabecera == "N"){
			Ext.get('lblDGdaCabLNF').update ("No es cabecera");
		}
		else if (datosGenerales.datos[0].clienteFijoDto[0].inClienteCabecera == "C"){
			Ext.get('lblDGdaCabLNF').update ("Cabecera de grupo");
		}
		else if (datosGenerales.datos[0].clienteFijoDto[0].inClienteCabecera == "S"){
			Ext.get('lblDGdaCabLNF').update ("Cabecera de subgrupo empresarial de 2º nivel o mayor");
		}
		else{
			Ext.get('lblDGdaCabLNF').update (datosGenerales.datos[0].clienteFijoDto[0].inClienteCabecera);
		}
		
		if(datosGenerales.datos[0].clienteFijoDto[0].fxIniVigencia != null)
			Ext.get('lblDGdaFInicioVigenciaLNF').update (modificarFechaAnio(datosGenerales.datos[0].clienteFijoDto[0].fxIniVigencia));
		else
			Ext.get('lblDGdaFInicioVigenciaLNF').update ("");
			
		if(datosGenerales.datos[0].clienteFijoDto[0].fxFinVigencia != null)
			Ext.get('lblDGdaFinVigenciaLNF').update (modificarFechaAnio(datosGenerales.datos[0].clienteFijoDto[0].fxFinVigencia));
		else
			Ext.get('lblDGdaFinVigenciaLNF').update ("");
		
		Ext.get('lblDGdaNombreRazonLNM').update (datosGenerales.datos[0].clienteMovilCtoDto[0].dsNombre);
		Ext.get('lblDGdaApellido1LNM').update (datosGenerales.datos[0].clienteMovilCtoDto[0].dsApellido1);
		Ext.get('lblDGdaApellido2LNM').update (datosGenerales.datos[0].clienteMovilCtoDto[0].dsApellido2);
		
		//Mostramos la descripcion indicada segun coTipoDocumento
		if (datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoDocumento == null || datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoDocumento == "")
			Ext.get('lblDGdaTipoDocLNM').update (datosGenerales.datos[0].clienteMovilCtoDto[0].coTipoDocumento);
		else		
			Ext.get('lblDGdaTipoDocLNM').update (datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoDocumento);
					
		//Si la descripcion es vacía o nula pondremos el codigo		
		if (datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoClienteFSC == null || datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoClienteFSC == ""){
			Ext.get('lblDGdaTipoClienteFis').update (datosGenerales.datos[0].clienteMovilCtoDto[0].coTipoClienteFSC);
		}
		else{
			Ext.get('lblDGdaTipoClienteFis').update (datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoClienteFSC);
		}
		
		//Si la descripcion es vacía o nula pondremos el codigo		
		if (datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoSegmento == null || datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoSegmento == ""){
			Ext.get('lblDGdaTipoSeg').update (datosGenerales.datos[0].clienteMovilCtoDto[0].coTipoSegmento);
		}
		else{
			Ext.get('lblDGdaTipoSeg').update (datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoSegmento);
		}

		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoSegmento == null || datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoSegmento == ""){
			Ext.get('lblDGdaTipoSeg').update (datosGenerales.datos[0].clienteMovilCtoDto[0].coTipoSegmento);
		}
		else{
			Ext.get('lblDGdaTipoSeg').update (datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoSegmento);
		}
		
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].clienteMovilCtoDto[0].dsMotivoSegmento == null || datosGenerales.datos[0].clienteMovilCtoDto[0].dsMotivoSegmento == ""){
			Ext.get('lblDGdaMotivoSeg').update (datosGenerales.datos[0].clienteMovilCtoDto[0].coMotivoSegmento);
		}
		else{
			Ext.get('lblDGdaMotivoSeg').update (datosGenerales.datos[0].clienteMovilCtoDto[0].dsMotivoSegmento);
		}				
		
		//Mostramos la descripcion indicada segun codigo
		if (datosGenerales.datos[0].clienteMovilCtoDto[0].inCabeceraGrupo == ""){
			Ext.get('lblDGdaCabLNM').update ("No es cabecera");
		}
		else if (datosGenerales.datos[0].clienteMovilCtoDto[0].inCabeceraGrupo == "C"){
			Ext.get('lblDGdaCabLNM').update ("Cabecera de grupo");
		}
		else if (datosGenerales.datos[0].clienteMovilCtoDto[0].inCabeceraGrupo == "S"){
			Ext.get('lblDGdaCabLNM').update ("Cabecera de subgrupo empresarial de 2º nivel o mayor");
		}
		else{
			Ext.get('lblDGdaCabLNM').update (datosGenerales.datos[0].clienteMovilCtoDto[0].inCabeceraGrupo);
		}

		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].datosClienteDto[0].dsPais == null || datosGenerales.datos[0].datosClienteDto[0].dsPais == ""){
			Ext.get('lblDGdaPaisLNM').update (datosGenerales.datos[0].clienteMovilCtoDto[0].coPais);
		}
		else{
			Ext.get('lblDGdaPaisLNM').update (datosGenerales.datos[0].datosClienteDto[0].dsPais);
		}
		
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoCliente == null || datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoCliente == ""){
			Ext.get('lblDGdaTipoClienteLNM').update (datosGenerales.datos[0].clienteMovilCtoDto[0].coTipoCliente);
		}
		else{
			Ext.get('lblDGdaTipoClienteLNM').update (datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoCliente);
		}
		
		if(datosGenerales.datos[0].clienteMovilCtoDto[0].fxAltaCliente != null)
			Ext.get('lblDGdaAltaLNM').update (modificarFechaAnio(datosGenerales.datos[0].clienteMovilCtoDto[0].fxAltaCliente));
		else
			Ext.get('lblDGdaAltaLNM').update ("");
					
		if(datosGenerales.datos[0].clienteMovilCtoDto[0].fxIniVigencia != null)
			Ext.get('lblDGdaFInicioVigenciaLNM').update (modificarFechaAnio(datosGenerales.datos[0].clienteMovilCtoDto[0].fxIniVigencia));
		else
			Ext.get('lblDGdaFInicioVigenciaLNM').update ("");
				
		if(datosGenerales.datos[0].clienteMovilCtoDto[0].fxFinVigencia != null)
			Ext.get('lblDGdaFinVigenciaLNM').update (modificarFechaAnio(datosGenerales.datos[0].clienteMovilCtoDto[0].fxFinVigencia));
		else
			Ext.get('lblDGdaFinVigenciaLNM').update ("");					
	}
	//BTON Datos LNF.
	var cargarDatosLNF = function (){
		Ext.get('etiqCodCliente').dom.childNodes[0].data = 'Código cliente:';
		Ext.get('lblDGCodCliente').update (datosGenerales.datos[0].clienteFijoDto[0].coClienteFijo);
		Ext.get('lblDGTipoDoc').update (datosGenerales.datos[0].datosClienteDto[0].dsTipoDocumento);
		Ext.get('lblDGNDoc').update (datosGenerales.datos[0].datosClienteDto[0].nuDocumento);
		if(datosGenerales.datos[0].datosClienteDto[0].dsRazonSocial.length == 0){
			Ext.get('lblDGNombreRazon').update (datosGenerales.datos[0].datosClienteDto[0].dsNombre);
			Ext.get('lblDGApellido1').update (datosGenerales.datos[0].datosClienteDto[0].dsApellido1);
			Ext.get('lblDGApellido2').update (datosGenerales.datos[0].datosClienteDto[0].dsApellido2);
		}else{
			Ext.get('lblDGNombreRazon').update (datosGenerales.datos[0].datosClienteDto[0].dsRazonSocial);
		}
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].clienteFijoDto[0].dsSegmento == null || datosGenerales.datos[0].clienteFijoDto[0].dsSegmento == ""){
			Ext.get('lblDGSegmento').update (datosGenerales.datos[0].clienteFijoDto[0].coSegmento);
		}
		else{
			Ext.get('lblDGSegmento').update (datosGenerales.datos[0].clienteFijoDto[0].dsSegmento);
		}
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].clienteFijoDto[0].coSubSegmento == null || datosGenerales.datos[0].clienteFijoDto[0].coSubSegmento == ""){
			Ext.get('lblDGSubsegmento').update (datosGenerales.datos[0].clienteFijoDto[0].coSubSegmento);
		}
		else{
			Ext.get('lblDGSubsegmento').update (datosGenerales.datos[0].clienteFijoDto[0].dsSubSegmento);
		}
		
		if(datosGenerales.datos[0].clienteFijoDto[0].fxUltCambioSubSegmento != null)
			Ext.get('lblDGFechaCambioSeg').update (modificarFechaAnio(datosGenerales.datos[0].clienteFijoDto[0].fxUltCambioSubSegmento));
		else
			Ext.get('lblDGFechaCambioSeg').update ("");	
		
		Ext.get('lblDGEstado').update (datosGenerales.datos[0].clienteFijoDto[0].dsEstadoCliente);
		
		if(datosGenerales.datos[0].clienteFijoDto[0].fxUltCambioEstado != null)
			Ext.get('lblDGFechaCambioEstado').update (modificarFechaAnio(datosGenerales.datos[0].clienteFijoDto[0].fxUltCambioEstado));
		else
			Ext.get('lblDGFechaCambioEstado').update ("");	
		
		Ext.get('lblDGMotivoEstado').update (datosGenerales.datos[0].clienteFijoDto[0].dsMotivoEstado);
		Ext.get('lblDGObservaciones').update (datosGenerales.datos[0].datosClienteDto[0].dsComentariosCliente);
		
		//pintar plantilla Datos LNF.
		limpiarPlantillas ();
		crearPlantillas ();
		cargarDatosLNFDomicilios ();
		
		
	}
    
    //Limpia la estructura de las plantillas.
    var limpiarPlantillas = function (){
    
        var arrayNewClientesDto = new Array();
        var arrTplDom = [];
        tplDom = new Ext.XTemplate(arrTplDom.join(''));
        //limpiamos domicilios LNF
        tplDom.overwrite('domGeograficos', arrayNewClientesDto);
    
        var arrTplDomLNM = [];
        tplDomLNM = new Ext.XTemplate(arrTplDomLNM.join(''));
        //limpiamos domicilios LNM
        tplDomLNM.overwrite('domGeograficosLNM', arrayNewClientesDto);
    
        var arrTplOneRow = [];  
        tplOneRow = new Ext.XTemplate(arrTplOneRow.join(''));
        //limpiamos domicilios logicos.
        tplOneRow.overwrite('domDirLogicas', arrayNewClientesDto);
    }
    
    
   

	var cargarDatosLNFDomicilios = function (){
	
	    var contLogicas = 0;
        var contLNM = 0;
        var contLNF = 0;
        myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
        myMask.show();
    
        var domicilios = {
            codigoCliente:Ext.get('FCHistorialCodCliente').dom.value
        };
        
    
        Ext.Ajax.request({
            url:contexto + 'DomiciliosCliente.do',
            params:{
                buscar:'',
                domiciliosJSON:Ext.encode(domicilios)
            },
            callback:function (options, success, response){
                myMask.hide();
                if (success){
                    var respuesta = Ext.util.JSON.decode (response.responseText);
                    
                    if (respuesta.success){
                        //Pueden llegar mensajes informativos.
                        if (respuesta.message && respuesta.message.length>0){
                            Ext.each(respuesta.message,function(message){
                                Ext.Msg.show({
                                   title:'Información',
                                   cls:'cgMsgBox',
                                   msg: '<span>'+message+'</span><br/>',
                                   buttons: Ext.Msg.OK,
                                   icon: Ext.MessageBox.INFO
                                });
                            });
                        }
                        
                        datosDomicilios = respuesta.datos[0];
                        
                        //Pintamos la plantilla Domicilios Linea fija.
                        var i = 0;
                        Ext.each(datosDomicilios.direccionesClienteDto,function(clienteDto){
                            if (i>0){
                                clienteDto.sep = 'sepDom';
                            }
                            if(clienteDto.inOriDir == 'F'){
                                tplDom.append('domGeograficos', clienteDto);
                                contLNF++;
                            }
                            i++;
                        });

                        //Pintamos todas las plantillas direcciones Logicas
                        var i = 0;
                        Ext.each(datosDomicilios.direccioneClienteLogicas,function(clienteLogicasdto){
                            if (i>0){
                                clienteLogicasdto.sep = 'sepDom';
                            }
                            
                            if(clienteLogicasdto.inOriDirLog == 'A' || clienteLogicasdto.inOriDirLog == 'F' ){
                               tplOneRow.append('domDirLogicas', clienteLogicasdto);
                               contLogicas++;
                            }
                            i++;
                        });

                        iniciarAcordeon (contLNF,contLNM,contLogicas);
                        
                        if(datosDomicilios.direccionesClienteDto.length == 0
                            && datosDomicilios.direccionesClienteDtoLNM.length == 0){
                            Ext.Msg.show({
                               title:'Información',
                               cls:'cgMsgBox',
                               msg: '<span>No se han encontrado domicilios para el cliente '+Ext.get('FCHistorialCodCliente').dom.value+'</span>',
                               buttons: Ext.Msg.OK,
                               icon: Ext.MessageBox.INFO
                            });
                        }
                    }else{
                        //Ha saltado una excepcion y viene un objeto con un mensaje de error.
                        Ext.Msg.show({
                           title:'Error',
                           cls:'cgMsgBox',
                           msg: '<span>'+respuesta.message+'</span><br/>',
                           buttons: Ext.Msg.OK,
                           icon: Ext.MessageBox.ERROR
                        });
                    }
                }
            }
        });
	
	
	}
	
	   var cargarDatosLNMContrato = function (){
    
        var contLogicas = 0;
        var contLNM = 0;
        var contLNF = 0;
        myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
        myMask.show();
    
        var domicilios = {
            codigoCliente:Ext.get('FCHistorialCodCliente').dom.value
        };
        
    
        Ext.Ajax.request({
            url:contexto + 'DomiciliosCliente.do',
            params:{
                buscar:'',
                domiciliosJSON:Ext.encode(domicilios)
            },
            callback:function (options, success, response){
                myMask.hide();
                if (success){
                    var respuesta = Ext.util.JSON.decode (response.responseText);
                    
                    if (respuesta.success){
                        //Pueden llegar mensajes informativos.
                        if (respuesta.message && respuesta.message.length>0){
                            Ext.each(respuesta.message,function(message){
                                Ext.Msg.show({
                                   title:'Información',
                                   cls:'cgMsgBox',
                                   msg: '<span>'+message+'</span><br/>',
                                   buttons: Ext.Msg.OK,
                                   icon: Ext.MessageBox.INFO
                                });
                            });
                        }
                        
                        datosDomicilios = respuesta.datos[0];
                        
                        //Pintamos todas las plantillas LNM
                        var i = 0;
                        Ext.each(datosDomicilios.direccionesClienteLNMDto,function(clienteLMDto){
                            if (i>0)
                                clienteLMDto.sep = 'sepDom';
                            
                            contLNM++;
                            tplDomLNM.append('domGeograficosLNM', clienteLMDto);
                            i++;
                        });

                        //Pintamos todas las plantillas direcciones Logicas
                        
                        var i = 0;
                        Ext.each(datosDomicilios.direccioneClienteLogicas,function(clienteLogicasdto){
                            if (i>0){
                                clienteLogicasdto.sep = 'sepDom';
                            }
                            
                            if(clienteLogicasdto.inOriDirLog == 'M' || clienteLogicasdto.inOriDirLog == 'A' ){
                               tplOneRow.append('domDirLogicas', clienteLogicasdto);
                               contLogicas++;
                            }
                            i++;
                        });

                        
                        iniciarAcordeon (contLNF,contLNM,contLogicas);
                        
                        if(datosDomicilios.direccionesClienteDto.length == 0
                            && datosDomicilios.direccionesClienteDtoLNM.length == 0){
                            Ext.Msg.show({
                               title:'Información',
                               cls:'cgMsgBox',
                               msg: '<span>No se han encontrado domicilios para el cliente '+Ext.get('FCHistorialCodCliente').dom.value+'</span>',
                               buttons: Ext.Msg.OK,
                               icon: Ext.MessageBox.INFO
                            });
                        }
                    }else{
                        //Ha saltado una excepcion y viene un objeto con un mensaje de error.
                        Ext.Msg.show({
                           title:'Error',
                           cls:'cgMsgBox',
                           msg: '<span>'+respuesta.message+'</span><br/>',
                           buttons: Ext.Msg.OK,
                           icon: Ext.MessageBox.ERROR
                        });
                    }
                }
            }
        });
    
    
    }
	//BTON Datos LNM/Contrato.
	var cargarDatosLNMC = function (){
		Ext.get('etiqCodCliente').dom.childNodes[0].data = 'Código cliente:'
		Ext.get('lblDGCodCliente').update (datosGenerales.datos[0].clienteMovilCtoDto[0].coClienteMovilCto);		
		if (datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoDocumento == null || datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoDocumento == "")
			Ext.get('lblDGTipoDoc').update (datosGenerales.datos[0].clienteMovilCtoDto[0].coTipoDocumento);
		else
			Ext.get('lblDGTipoDoc').update (datosGenerales.datos[0].clienteMovilCtoDto[0].dsTipoDocumento);				
		Ext.get('lblDGNDoc').update (datosGenerales.datos[0].datosClienteDto[0].nuDocumento);
		
		Ext.get('lblDGNombreRazon').update (datosGenerales.datos[0].clienteMovilCtoDto[0].dsNombre);
		Ext.get('lblDGApellido1').update (datosGenerales.datos[0].clienteMovilCtoDto[0].dsApellido1);
		Ext.get('lblDGApellido2').update (datosGenerales.datos[0].clienteMovilCtoDto[0].dsApellido2);
		
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].clienteMovilCtoDto[0].dsSegmento == null || datosGenerales.datos[0].clienteMovilCtoDto[0].dsSegmento == ""){
			Ext.get('lblDGSegmento').update (datosGenerales.datos[0].clienteMovilCtoDto[0].coSegmento);
		}
		else{
			Ext.get('lblDGSegmento').update (datosGenerales.datos[0].clienteMovilCtoDto[0].dsSegmento);
		}
		
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].clienteMovilCtoDto[0].dsSubSegmento == null || datosGenerales.datos[0].clienteMovilCtoDto[0].dsSubSegmento == ""){
			Ext.get('lblDGSubsegmento').update (datosGenerales.datos[0].clienteMovilCtoDto[0].coSubSegmento);
		}
		else{
			Ext.get('lblDGSubsegmento').update (datosGenerales.datos[0].clienteMovilCtoDto[0].dsSubSegmento);
		}
		
		if(datosGenerales.datos[0].clienteMovilCtoDto[0].fxCambioSegmento != null)
			Ext.get('lblDGFechaCambioSeg').update (modificarFechaAnio(datosGenerales.datos[0].clienteMovilCtoDto[0].fxCambioSegmento));
		else
			Ext.get('lblDGFechaCambioSeg').update ("");	
			
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].clienteMovilCtoDto[0].dsEstadoCliente == null || datosGenerales.datos[0].clienteMovilCtoDto[0].dsEstadoCliente == ""){
			Ext.get('lblDGEstado').update (datosGenerales.datos[0].clienteMovilCtoDto[0].coEstadoCliente);
		}
		else{
			Ext.get('lblDGEstado').update (datosGenerales.datos[0].clienteMovilCtoDto[0].dsEstadoCliente);
		}
		
		if(datosGenerales.datos[0].clienteMovilCtoDto[0].fxUltCambioEstado != null)
			Ext.get('lblDGFechaCambioEstado').update (modificarFechaAnio(datosGenerales.datos[0].clienteMovilCtoDto[0].fxUltCambioEstado));
		else
			Ext.get('lblDGFechaCambioEstado').update ("");	
		
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].clienteMovilCtoDto[0].dsSituacionEstado == null || datosGenerales.datos[0].clienteMovilCtoDto[0].dsSituacionEstado == ""){
			Ext.get('lblDGMotivoEstado').update (datosGenerales.datos[0].clienteMovilCtoDto[0].coSituacionEstado);
		}
		else{
			Ext.get('lblDGMotivoEstado').update (datosGenerales.datos[0].clienteMovilCtoDto[0].dsSituacionEstado);		
		}
		Ext.get('lblDGObservaciones').update (datosGenerales.datos[0].datosClienteDto[0].dsComentariosCliente);
		
		//pintar plantilla LNMContrato.
		limpiarPlantillas ();
		crearPlantillas ();
		cargarDatosLNMContrato ();
	}
	
	var cargarDatosLNMP = function (){
		Ext.get('etiqCodCliente').dom.childNodes[0].data = 'Código cliente:'
		Ext.get('lblDGCodCliente').update (datosGenerales.datos[0].clienteMovilPreDto[0].coIdentificador);
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].clienteMovilPreDto[0].dsTipoDocumento == null || datosGenerales.datos[0].clienteMovilPreDto[0].dsTipoDocumento == "")
			Ext.get('lblDGTipoDoc').update (datosGenerales.datos[0].clienteMovilPreDto[0].coTipoDocumento);
		else
			Ext.get('lblDGTipoDoc').update (datosGenerales.datos[0].clienteMovilPreDto[0].dsTipoDocumento);
		Ext.get('lblDGNDoc').update (datosGenerales.datos[0].clienteMovilPreDto[0].nuDocumento);
		
		Ext.get('lblDGNombreRazon').update (datosGenerales.datos[0].clienteMovilPreDto[0].dsNombre);
		Ext.get('lblDGApellido1').update (datosGenerales.datos[0].clienteMovilPreDto[0].dsApellido1);
		Ext.get('lblDGApellido2').update (datosGenerales.datos[0].clienteMovilPreDto[0].dsApellido2);
		
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].clienteMovilPreDto[0].dsSegmento == null || datosGenerales.datos[0].clienteMovilPreDto[0].dsSegmento == "")
			Ext.get('lblDGSegmento').update (datosGenerales.datos[0].clienteMovilPreDto[0].coSegmento);
		else
			Ext.get('lblDGSegmento').update (datosGenerales.datos[0].clienteMovilPreDto[0].dsSegmento);
				
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].clienteMovilPreDto[0].dsSubSegmento == null || datosGenerales.datos[0].clienteMovilPreDto[0].dsSubSegmento == "")
			Ext.get('lblDGSubsegmento').update (datosGenerales.datos[0].clienteMovilPreDto[0].coSubSegmento);
		else
			Ext.get('lblDGSubsegmento').update (datosGenerales.datos[0].clienteMovilPreDto[0].dsSubSegmento);
		
		if(datosGenerales.datos[0].clienteMovilPreDto[0].fxCambioSegmento != null)
			Ext.get('lblDGFechaCambioSeg').update (modificarFechaAnio(datosGenerales.datos[0].clienteMovilPreDto[0].fxCambioSegmento));
		else
			Ext.get('lblDGFechaCambioSeg').update ("");	
			
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].clienteMovilPreDto[0].dsEstado == null || datosGenerales.datos[0].clienteMovilPreDto[0].dsEstado == "")
			Ext.get('lblDGEstado').update (datosGenerales.datos[0].clienteMovilPreDto[0].coEstado);
		else
			Ext.get('lblDGEstado').update (datosGenerales.datos[0].clienteMovilPreDto[0].dsEstado);
		
		if(datosGenerales.datos[0].clienteMovilPreDto[0].fxCambioEstado != null)
			Ext.get('lblDGFechaCambioEstado').update (modificarFechaAnio(datosGenerales.datos[0].clienteMovilPreDto[0].fxCambioEstado));
		else
			Ext.get('lblDGFechaCambioEstado').update ("");	
		
		//Si la descripcion es vacía o nula pondremos el codigo
		if (datosGenerales.datos[0].clienteMovilPreDto[0].dsMotivoEstado == null || datosGenerales.datos[0].clienteMovilPreDto[0].dsMotivoEstado == "")
			Ext.get('lblDGMotivoEstado').update (datosGenerales.datos[0].clienteMovilPreDto[0].coMotivoEstado);
		else
			Ext.get('lblDGMotivoEstado').update (datosGenerales.datos[0].clienteMovilPreDto[0].dsMotivoEstado);		

		Ext.get('lblDGObservaciones').update ("");	
		
		if (datosGenerales.datos[0].clienteMovilRPSDto[0].dsTipoDocumento == null || datosGenerales.datos[0].clienteMovilRPSDto[0].dsTipoDocumento == "")
			Ext.get('lblDGrpsTipoDoc').update (datosGenerales.datos[0].clienteMovilRPSDto[0].coTipoDocumento);
		else	
			Ext.get('lblDGrpsTipoDoc').update (datosGenerales.datos[0].clienteMovilRPSDto[0].dsTipoDocumento);
		Ext.get('lblDGrpsNumDocRPS').update (datosGenerales.datos[0].clienteMovilRPSDto[0].nuDocumento);
		if (datosGenerales.datos[0].clienteMovilRPSDto[0].dsPais == null || datosGenerales.datos[0].clienteMovilRPSDto[0].dsPais == "")
			Ext.get('lblDGrpsPais').update (datosGenerales.datos[0].clienteMovilRPSDto[0].coPais);
		else	
			Ext.get('lblDGrpsPais').update (datosGenerales.datos[0].clienteMovilRPSDto[0].dsPais);		
		Ext.get('lblDGrpsNombreRazon').update (datosGenerales.datos[0].clienteMovilRPSDto[0].dsNombre);
		Ext.get('lblDGrpsApellido1').update (datosGenerales.datos[0].clienteMovilRPSDto[0].dsApellido1);
		Ext.get('lblDGrpsApellido2').update (datosGenerales.datos[0].clienteMovilRPSDto[0].dsApellido2);
		Ext.get('lblDGrpsCoPostal').update (datosGenerales.datos[0].clienteMovilRPSDto[0].coPostal);
		if (datosGenerales.datos[0].clienteMovilRPSDto[0].fxAltaRegistro != null)
			Ext.get('lblDGrpsAlta').update (modificarFechaAnio(datosGenerales.datos[0].clienteMovilRPSDto[0].fxAltaRegistro));
		else
			Ext.get('lblDGrpsAlta').update ("");			
		if (datosGenerales.datos[0].clienteMovilRPSDto[0].fxIniVigencia != null)
			Ext.get('lblDGrpsFInicioVigencia').update (modificarFechaAnio(datosGenerales.datos[0].clienteMovilRPSDto[0].fxIniVigencia));
		else
			Ext.get('lblDGrpsFInicioVigencia').update ("");
		if (datosGenerales.datos[0].clienteMovilRPSDto[0].fxFinVigencia != null)
			Ext.get('lblDGrpsFinVigencia').update (modificarFechaAnio(datosGenerales.datos[0].clienteMovilRPSDto[0].fxFinVigencia));
		else
			Ext.get('lblDGrpsFinVigencia').update ("");
			
			
		mostrarConsentimientos ();				
	}	
	
	var mostrarDatosConvergentes = function (){
		//Mostramos la pestaña de domicilios por si venimos de Prepago
		Ext.get('pesDomicilios').setVisibilityMode(Ext.Element.DISPLAY).show();
		//Mostramos los Datos Acicionales
		Ext.get('colDatosAdicionales').setVisibilityMode(Ext.Element.DISPLAY).show();	
		Ext.get('btnDatosConvergentes').addClass('activo');
		Ext.get('btnDatosLNF').removeClass('activo');
		Ext.get('btnDatosLNMC').removeClass('activo');
		Ext.get('btnDatosLNMP').removeClass('activo');
		
		cargarDatosConvergentes();
	}
	
	var mostrarDatosLNF = function (){
		//Mostramos la pestaña de domicilios por si venimos de Prepago
		Ext.get('pesDomicilios').setVisibilityMode(Ext.Element.DISPLAY).show();	
		//Mostramos los Datos Acicionales
		Ext.get('colDatosAdicionales').setVisibilityMode(Ext.Element.DISPLAY).show();
		//Ocultamos los datos de Representante	
		Ext.get('colDatosRepresentante').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('btnDatosLNF').addClass('activo');
		Ext.get('btnDatosConvergentes').removeClass('activo');
		Ext.get('btnDatosLNMC').removeClass('activo');
		Ext.get('btnDatosLNMP').removeClass('activo');
		
		cargarDatosLNF();
	}
	
	var mostrarDatosLNMC = function (){
		//Mostramos la pestaña de domicilios por si venimos de Prepago
		Ext.get('pesDomicilios').setVisibilityMode(Ext.Element.DISPLAY).show();	
		//Mostramos los Datos Acicionales
		Ext.get('colDatosAdicionales').setVisibilityMode(Ext.Element.DISPLAY).show();
		//Ocultamos los datos de Representante	
		Ext.get('colDatosRepresentante').setVisibilityMode(Ext.Element.DISPLAY).hide();				
		Ext.get('btnDatosLNMC').addClass('activo');
		Ext.get('btnDatosLNMP').removeClass('activo');
		Ext.get('btnDatosLNF').removeClass('activo');
		Ext.get('btnDatosConvergentes').removeClass('activo');
		
		cargarDatosLNMC();
	}
	
	var mostrarDatosLNMP = function (){
		//Ocultamos la pestaña de domicilios
		Ext.get('pesDomicilios').setVisibilityMode(Ext.Element.DISPLAY).hide();	
		//Ocultamos los Datos Acicionales
		Ext.get('colDatosAdicionales').setVisibilityMode(Ext.Element.DISPLAY).hide();	
		//Mostramos los datos de Representante	
		Ext.get('colDatosRepresentante').setVisibilityMode(Ext.Element.DISPLAY).show();			
		Ext.get('btnDatosLNMP').addClass('activo');
		Ext.get('btnDatosLNMC').removeClass('activo');
		Ext.get('btnDatosLNF').removeClass('activo');
		Ext.get('btnDatosConvergentes').removeClass('activo');
		
		cargarDatosLNMP();
	}	
	
	/*=====================================================*/
	/*====================CONSENTIMIENTOS==================*/
	/*=====================================================*/
	var mostrarConsentimientos = function (){
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
		Ext.get('btnNuevaComunicacion').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('bhBotones').child('.bhSep').setVisibilityMode(Ext.Element.DISPLAY).show();
		</sec:authorize>
		Ext.get('divDatosGenerales').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divConsentimientos').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('divDomicilios').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		Ext.get('pesDatGen').removeClass('activa');
		Ext.get('pesConsentimientos').addClass('activa');
		Ext.get('pesDomicilios').removeClass('activa');
		
		//Cargamos los datos de la pestaña si no estan cargados
		if (!datosTipoObjeto){
			Ext.get('divExpandirColCons').setVisibilityMode(Ext.Element.DISPLAY).hide();
			myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
			myMask.show();
			cargarComboTipoObjeto();
		}						
	}
	
	var cargarComboTipoObjeto = function (){
		Ext.Ajax.request({
    		url:contexto + 'ConsentimientosCliente.do',
    		params:{
    			tipoObjeto:''
    		},
    		callback:function (options, success, response){
    			if (success){
    				datosTipoObjeto = Ext.util.JSON.decode (response.responseText);
					if (datosTipoObjeto.success){
   						//Pueden llegar mensajes informativos.
    					if (datosTipoObjeto.message && datosTipoObjeto.message.length>0){
	    					Ext.each(datosTipoObjeto.message,function(message){
		    					Ext.Msg.show({
								   title:'Información',
								   cls:'cgMsgBox',
								   msg: '<span>'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
		    				});
	    				}
	    				
	    				var i = 0;
	    				//CARGAR COMBO DE TIPO OBJETO
	    				var arrayCombo = new Array();
	    				arrayCombo.push({texto: 'Todos', valor: ''});
	    				Ext.each(datosTipoObjeto.datos,function(dato){
	    					arrayCombo.push({texto: dato.descripcionNivel, valor: dato.codigoNivel});
	    				});
						agregarValoresCombo('selObjeto', arrayCombo, true);
						
						//CARGAMOS EL COMBO ESTADO GESTION 
						cargarComboEstadoGestion();
    				}else{
    					myMask.hide();
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span>'+datosTipoObjeto.message+'</span><br/>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
    				}
    			}
    		}
    	});
	}
	
	var cargarComboEstadoGestion = function (){
	
		Ext.Ajax.request({
    		url:contexto + 'ConsentimientosCliente.do',
    		params:{
    			estadoGestion:''
    		},
    		callback:function (options, success, response){
    			if (success){
    				datosEstadoGest = Ext.util.JSON.decode (response.responseText);
    				
    				if (datosEstadoGest.success){
    					//Pueden llegar mensajes informativos.
    					if (datosEstadoGest.message && datosEstadoGest.message.length>0){
	    					Ext.each(datosEstadoGest.message,function(message){
		    					Ext.Msg.show({
								   title:'Información',
								   cls:'cgMsgBox',
								   msg: '<span>'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
		    				});
	    				}
	    				
	    				var i = 0;
	    				//CARGAR COMBO DE ESTADO DE LA GESTION
	    				var arrayCombo = new Array();
	    				arrayCombo.push({texto: 'Todos', valor: ''});
	    				Ext.each(datosEstadoGest.datos,function(dato){
	    					if (dato.descripcion != "CANCELADO"){
	    						arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});
	    					}
	    				});
						agregarValoresCombo('selEstGestion', arrayCombo, true);
						
						if(!combosDetalleConsent)
							obtenerCombosDetalleConsentimiento();	
    				}else{
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span>'+datosEstadoGest.message+'</span><br/>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
    				}
    			}
    		}
    	});
	}
	
	
	var ocultarHistorialConsent = function (id){
		Ext.each(Ext.query('.histCons', id), function(div) {
			Ext.get(div).setVisibilityMode(Ext.Element.DISPLAY).hide();
		});
	}
	
	var filtrarCosentimientos = function (){
		
		myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
		myMask.show();
		
		Ext.Ajax.request({
    		url:contexto + 'ConsentimientosCliente.do',
    		params:{
    			buscar:'',
    			consentimientosClientesJSON:Ext.encode(filtro)
    		},
    		callback:function (options, success, response){
    			myMask.hide();
    			if (success){
    				datosLConsent = Ext.util.JSON.decode (response.responseText);
    				
    				if (datosLConsent.success){
    					//Pueden llegar mensajes informativos.
    					if (datosLConsent.message && datosLConsent.message.length>0){
	    					Ext.each(datosLConsent.message,function(message){
		    					Ext.Msg.show({
								   title:'Información',
								   cls:'cgMsgBox',
								   msg: '<span>'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
		    				});
	    				}
	    				
	    				if(datosLConsent.datos.length == 0){
	    					Ext.Msg.show({
								   title:'Información',
								   cls:'cgMsgBox',
								   msg: '<span>'+'No se encontraron resultados'+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
	    				}
	    				
	    				var resFiltro = Ext.get('divConsentimientos').child('.resFiltro');
	    				Ext.get('divExpandirColCons').setVisibilityMode(Ext.Element.DISPLAY).show();
	    				
	    				//Borramos el contenido para una nueva busqueda
	    				Ext.get(resFiltro.id).update();
	    				
	    				tplHeadConsent.overwrite(Ext.get('divCabeceraConsent'));
							
	    				Ext.each(datosLConsent.datos,function(dato){
	    				
	    					collectionDatosListadoCons.add(dato.codDerecho,dato);
	    					
	    					Ext.DomHelper.append(resFiltro.id, '<div id="'+dato.codDerecho+'" class="containerCons"></div>');
	    					
	    					var fechaEstado = null;
	    					
	    					if(dato.fxCambioEstado != null)
	    						fechaEstado = modificarFechaAnio(convertDateFromJSONLib (dato.fxCambioEstado));
	    					
	    					var valorExplot = '';
	    					var icoExplot = '';
	    					if(dato.valorExplotacion == "N"){
	    						valorExplot = 'No Consiente';
	    						icoExplot = 'icoNoCons';	    						
	    					}else{
	    						valorExplot = 'Consiente';
	    						icoExplot = 'icoCons';
	    					}
	    					
	    					var tacitoExpreso = '';
	    					if(dato.tipoLogica == "T"){
	    						tacitoExpreso = 'Tácito';
	    					}else{
	    						tacitoExpreso = 'Expreso';
	    					}
	    					
	    					
	    					var descripcion = dato.descDerecho;
	    					if (!Ext.isEmpty(descripcion) && descripcion.length > 55) {
								descripcion = cortarCadena(descripcion,55);
							}
	    					
	    					var estadoGestCD = '';
	    					var icoEstadoGestCD = '';
	    					if(dato.estadoGestionCD == "NO"){
	    						estadoGestCD = 'No Contactado';
	    						icoEstadoGestCD = 'icoEstCDNO'
	    					}else if(dato.estadoGestionCD == "SC"){
	    						estadoGestCD = 'Si Consiente';
	    						icoEstadoGestCD = 'icoEstCDSC'
	    					}else if(dato.estadoGestionCD == "PE"){
	    						estadoGestCD = 'Pendiente';
	    						icoEstadoGestCD = 'icoEstCDPE'
	    					}else if(dato.estadoGestionCD == "NC"){
	    						estadoGestCD = 'No Consiente';
	    						icoEstadoGestCD = 'icoEstCDNC'
	    					}else if(dato.estadoGestionCD == "CA"){
	    						estadoGestCD = 'Cancelado';
	    						icoEstadoGestCD = 'icoEstCDCA'
	    					}else{
	    						estadoGestCD = dato.descEstadoGestion;
	    					}
	    					
	    					tplConsent.append(Ext.get(dato.codDerecho), {
	    						lblCodD:dato.codDerecho,
	    						lblDescD:descripcion,
	    						icoExplotacion:icoExplot,
	    						lblExplotacionD:valorExplot,
	    						lblFxCambioEstado:fechaEstado,
	    						icoEstGest:icoEstadoGestCD,
	    						lblEstGest:estadoGestCD,
	    						lblTipoLog:tacitoExpreso
							});
							
							if (!Ext.isEmpty(descripcion)) {
								if(dato.descDerecho.length > 55){
									Ext.get(dato.codDerecho).child('.cabDatCons .descCons').dom.setAttribute('ext:qtip', dato.descDerecho);
								}
							}
							
							//Creamos un div formDatos para cada cabecera y lo ocultamos
							Ext.DomHelper.append(dato.codDerecho, '<div class="formDatos"></div>');
							Ext.get(dato.codDerecho).child('.formDatos').setVisibilityMode(Ext.Element.DISPLAY).hide();
							
							
	    				});
	    				
	    				//Añadimos funcionalidad al boton Historial
	    				Ext.each(Ext.query('.botonesHistorial', '.resFiltro'), function(input) {	
	    					var idPadre = Ext.fly(input).parent('.containerCons').id;    					
	    					var contParent = Ext.fly(input).parent('.containerCons').child('.formDatos');	    					
	    					
							Ext.get(input).on("click", function(e){
								e.stopEvent();
								//if(collectionDatosListadoCons.get(idPadre).valorExplotacion != "N"){
									<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
									if(!modoNuevaCom){
									</sec:authorize>
										asignarFuncionalidadHistorial(idPadre, contParent);
									<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
									}else{
										Ext.Msg.show({
										   title:'Se perderán los datos',
										   msg: '<span>Se van a perder los datos introducidos, ¿Desea continuar?</span>',
										   buttons: Ext.Msg.YESNO,
										   icon: Ext.MessageBox.WARNING,
										   fn:function(respuesta){
										   		if (respuesta == 'yes'){
												
													if(modoAltaCom == false){
														var idAux = Ext.query('.detNuevo', '.resFiltro')[0].id;
														if(comprobarValoresDetalleCons(idAux)){
															cargarFormularioDetalleConsentimiento(idAux,collectionDatosDetCons.get(idAux));
														}
														deshabilitarDetalleConsentimiento(idAux);
													}else{
														Ext.get(Ext.query('.detNuevo', '.resFiltro')[0].id).child('.formDatos').child('.detHistCons').remove();
														Ext.get(Ext.query('.detNuevo', '.resFiltro')[0].id).child('.formDatos').child('.divUsMod').remove();
														modoAltaCom = false;
														Ext.get(Ext.query('.detNuevo', '.resFiltro')[0].id).child('.formDatos').setVisibilityMode(Ext.Element.DISPLAY).hide();
														Ext.fly(Ext.query('.detNuevo', '.resFiltro')[0]).removeClass('cabConsentSel');
														Ext.fly(Ext.query('.detNuevo', '.resFiltro')[0]).removeClass('detExpand');
													}
										   			asignarFuncionalidadHistorial(idPadre, contParent);
										   			modoNuevaCom = false;
										   			Ext.fly(Ext.query('.detNuevo', '.resFiltro')[0]).removeClass('detNuevo');
										   		}
										   }
										});
									}
									</sec:authorize>
								//}
								
								
							});
						});	
						
						
						//Añadimos funcionalidad a la cabecera de consentimientos para acceder al detalle
	    				Ext.each(Ext.query('.containerCons .cabDatCons', '.resFiltro'), function(cabecera) {
	    					
							Ext.get(cabecera).on("click", function(e){
								if(collectionDatosListadoCons.get(Ext.get(cabecera.id).parent('.containerCons').id).valorExplotacion != "N"){
									<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
									if(!modoNuevaCom){
									</sec:authorize>
										asignarFuncionalidadCabecera(cabecera);
									<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
									}else{
										if(Ext.query('.detNuevo', '.resFiltro')[0] && 
											Ext.query('.detNuevo', '.resFiltro')[0].id != Ext.get(cabecera.id).parent('.containerCons').id){
											Ext.Msg.show({
											   title:'Se perderán los datos',
											   msg: '<span>Se van a perder los datos introducidos, ¿Desea continuar?</span>',
											   buttons: Ext.Msg.YESNO,
											   icon: Ext.MessageBox.WARNING,
											   fn:function(respuesta){
											   		if (respuesta == 'yes'){
													
														if(modoAltaCom == false){
															var idAux = Ext.query('.detNuevo', '.resFiltro')[0].id;
															if(comprobarValoresDetalleCons(idAux)){
																cargarFormularioDetalleConsentimiento(idAux,collectionDatosDetCons.get(idAux));
															}
															deshabilitarDetalleConsentimiento(idAux);
														}else{
															Ext.get(Ext.query('.detNuevo', '.resFiltro')[0].id).child('.formDatos').child('.detHistCons').remove();
															Ext.get(Ext.query('.detNuevo', '.resFiltro')[0].id).child('.formDatos').child('.divUsMod').remove();
															modoAltaCom = false;
															Ext.get(Ext.query('.detNuevo', '.resFiltro')[0].id).child('.formDatos').setVisibilityMode(Ext.Element.DISPLAY).hide();
															Ext.fly(Ext.query('.detNuevo', '.resFiltro')[0]).removeClass('cabConsentSel');
															Ext.fly(Ext.query('.detNuevo', '.resFiltro')[0]).removeClass('detExpand');
														}
													
											   			asignarFuncionalidadCabecera(cabecera);
											   			modoNuevaCom = false;
											   			Ext.fly(Ext.query('.detNuevo', '.resFiltro')[0]).removeClass('detNuevo');
											   		}
											   }
											});
										}else{
											asignarFuncionalidadCabecera(cabecera);
										}
									}
									</sec:authorize>
								}else{
									//Solo podriamos acceder a nueva comunicacion
									<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
									if(!modoNuevaCom){
									</sec:authorize>
										asignarFuncionalidadCabecera(cabecera);
									<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
									}else{
										if(Ext.query('.detNuevo', '.resFiltro')[0] && 
											Ext.query('.detNuevo', '.resFiltro')[0].id != Ext.get(cabecera.id).parent('.containerCons').id){
											Ext.Msg.show({
											   title:'Se perderán los datos',
											   msg: '<span>Se van a perder los datos introducidos, ¿Desea continuar?</span>',
											   buttons: Ext.Msg.YESNO,
											   icon: Ext.MessageBox.WARNING,
											   fn:function(respuesta){
											   		if (respuesta == 'yes'){
														if(modoAltaCom){
															Ext.get(Ext.query('.detNuevo', '.resFiltro')[0].id).child('.formDatos').child('.detHistCons').remove();
														 }else{
															var idAux = Ext.query('.detNuevo', '.resFiltro')[0].id;
															if(comprobarValoresDetalleCons(idAux)){
																cargarFormularioDetalleConsentimiento(idAux,collectionDatosDetCons.get(idAux));
															}
															deshabilitarDetalleConsentimiento(idAux);
														 }
														Ext.get(Ext.query('.detNuevo', '.resFiltro')[0].id).child('.formDatos').child('.divUsMod').remove();
											   			asignarFuncionalidadCabecera(cabecera);
											   			modoNuevaCom = false;
											   			Ext.fly(Ext.query('.detNuevo', '.resFiltro')[0]).removeClass('detNuevo');
											   		}
											   }
											});
										}else{
											asignarFuncionalidadCabecera(cabecera);
										}
									}
									</sec:authorize>
								}
							});
						});
    				}else{
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span>'+datosLConsent.message+'</span><br/>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
    				}
    			}
    		}
    	});
	}
	
	var asignarFuncionalidadHistorial = function (idPadre, contParent){
		ocultarTodosDivDetalle();
		mostrarTodosDivHistoriales();
		
		//Ocultamos todos los desplegables menos el que se ha pulsado
		Ext.each(Ext.query('.divConsentimientos .formDatos', '.resFiltro'), function(formDatos) {
			var idSup = Ext.get(formDatos.id).parent('.containerCons').id;
			if(idSup!= idPadre && !Ext.get(idSup).hasClass('histExpand')){
				Ext.get(formDatos.id).setVisibilityMode(Ext.Element.DISPLAY).hide();
			}
			Ext.get(idSup).removeClass('detExpand');
			Ext.get(idSup).removeClass('cabConsentSel');
		});	
		
		if(Ext.get(contParent.id).child('.divHistoriales') == null){
			buscarHistorial(idPadre);
		}else{
			
			if(Ext.get(idPadre).hasClass('histExpand')){
				Ext.get(contParent.id).setVisibilityMode(Ext.Element.DISPLAY).hide();
				Ext.get(idPadre).removeClass('histExpand');
			}else{
				Ext.get(contParent.id).setVisibilityMode(Ext.Element.DISPLAY).show();
				Ext.get(idPadre).addClass('histExpand');
			}
		}
	}
	
	var asignarFuncionalidadCabecera = function (cabecera){
		var idConsent = Ext.get(cabecera.id).parent('.containerCons');
		ocultarTodosDivHistoriales();
		
		//Ocultamos todos los desplegables menos el que se ha pulsado
		Ext.each(Ext.query('.divConsentimientos .formDatos', '.resFiltro'), function(formDatos) {
			var idPadre = Ext.get(formDatos.id).parent('.containerCons').id;
			if(idPadre!= idConsent.id && Ext.get(formDatos.id).isVisible()){
				Ext.get(formDatos.id).setVisibilityMode(Ext.Element.DISPLAY).hide();
			}
			Ext.get(idPadre).removeClass('histExpand');
			Ext.get(idPadre).removeClass('detExpand');
			Ext.get(idPadre).removeClass('cabConsentSel');
		});	
		
		if(Ext.get(idConsent.id).child('.formDatos').child('.detHistCons') == null){
			cargarDetalleConsentimiento(idConsent.id);
		
		}
		
		if(!Ext.get(idConsent.id).child('.formDatos').isVisible()){
			Ext.get(idConsent.id).child('.formDatos').setVisibilityMode(Ext.Element.DISPLAY).show();
			
			Ext.get(idConsent.id).child('.formDatos').child('.detHistCons').setVisibilityMode(Ext.Element.DISPLAY).show();
		
			Ext.get(idConsent.id).addClass('detExpand');
		}else{
			if(Ext.get(idConsent.id).child('.formDatos').child('.detHistCons').isVisible()){
				Ext.get(idConsent.id).child('.formDatos').setVisibilityMode(Ext.Element.DISPLAY).hide();
			}else{
				Ext.get(idConsent.id).child('.formDatos').child('.detHistCons').setVisibilityMode(Ext.Element.DISPLAY).show();
			
				Ext.get(idConsent.id).addClass('detExpand');
			}
		}

	
	}
	var ocultarHistorialConsent = function (id){
		Ext.each(Ext.query('.histCons', id), function(div) {
			Ext.get(div).setVisibilityMode(Ext.Element.DISPLAY).hide();
		});
	}
	
	var buscarConsentimientos = function (){
		filtrarCosentimientos();
	}
	
	var buscarHistorial = function (id){
		
		myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
		myMask.show();
	
		var historial = {
			codDerechoLOPD:collectionDatosListadoCons.get(id).codDerecho,
			secTipoObjeto:collectionDatosListadoCons.get(id).secTipoObjeto,
			codTipoObjeto:collectionDatosListadoCons.get(id).codTipoObjeto,
			descObjetoCD:collectionDatosListadoCons.get(id).objetoCD,
			codCliente:Ext.get('FCHistorialCodCliente').dom.value
		};
		
	
		Ext.Ajax.request({
    		url:contexto + 'HistorialConsentimientos.do',
    		params:{
    			buscar:'',
    			historialJSON:Ext.encode(historial)
    		},
    		callback:function (options, success, response){
    			myMask.hide();
    			if (success){
    				datosHistorialConsent = Ext.util.JSON.decode (response.responseText);
    				
    				if (datosHistorialConsent.success){
    					//Pueden llegar mensajes informativos.
    					if (datosHistorialConsent.message && datosHistorialConsent.message.length>0){
	    					Ext.each(datosHistorialConsent.message,function(message){
		    					Ext.Msg.show({
								   title:'Información',
								   cls:'cgMsgBox',
								   msg: '<span>'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
		    				});
	    				}
	    				
	    				//CARGAR EL HISTORIAL DEL CONSENTIMIENTOS		
						if (datosHistorialConsent.datos.length==0){
	    					Ext.Msg.show({
							   title:'Información',
							   cls:'cgMsgBox',
							   msg: '<span>'+ "No se encontraron datos en el historial" +'</span><br/>',
							   buttons: Ext.Msg.OK,
							   icon: Ext.MessageBox.INFO
							});
	    				}else{
							var divPadre = Ext.get(id);
					
	    					Ext.DomHelper.append(divPadre.child('.formDatos').id, '<div class="divHistoriales"></div>');
		    				
		    				Ext.each(datosHistorialConsent.datos,function(dato){
		    					var fecha = "";
		    					if(dato.fxOperacion != null){
		    						fecha = modificarFechaAnio(convertDateFromJSONLib (dato.fxOperacion));
		    					}
		    					tplConsDat.append(Ext.get(id).child('.formDatos').child('.divHistoriales').id, {
		    						lblFecha:fecha,
		    						lblCodigo:dato.codigoOperacion,
		    						lblDescripcion:dato.descOperacion.substring(0,19),
		    						lblAtrAsoc:dato.descObjetoCD
								});
		    				});
	    				
		    				Ext.get(divPadre.child('.formDatos')).setVisibilityMode(Ext.Element.DISPLAY).show();
		    				Ext.get(id).addClass('histExpand');
		    			}
    				}else{
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span>'+datosHistorialConsent.message+'</span><br/>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
    				}
    			}
    		}
    	});
	}
	
	var cargarDetalleConsentimiento = function (id){
		
		myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
		myMask.show();
		
		var indiceSeleccionado;
		
		var codigoConsent = collectionDatosListadoCons.get(id).codDerecho;
		
		var detalleCD = {
			codDerechoLOPD:collectionDatosListadoCons.get(id).codDerecho,
			secObjeto:collectionDatosListadoCons.get(id).secTipoObjeto,
			secEvento:collectionDatosListadoCons.get(id).secEvento,
			codTipoObjeto:collectionDatosListadoCons.get(id).codTipoObjeto
		};
		
		tplDetConsent.append(Ext.get(id).child('.formDatos'), {});
		Ext.get(id).child('.formDatos').child('.detHistCons').setVisibilityMode(Ext.Element.DISPLAY).hide();
		deshabilitarDetalleConsentimiento(id);
		
		Ext.Ajax.request({
    		url:contexto + 'DetalleConsentimiento.do',
    		params:{
    			buscar:'',
    			detalleConsentimientoJSON:Ext.encode(detalleCD)
    		},
    		callback:function (options, success, response){
    			myMask.hide();
    			if (success){
    				var datosDetalleConsent = Ext.util.JSON.decode (response.responseText);
    				
    				if (datosDetalleConsent.success){
    					//Pueden llegar mensajes informativos.
    					if (datosDetalleConsent.message && datosDetalleConsent.message.length>0){
	    					Ext.each(datosDetalleConsent.message,function(message){
		    					Ext.Msg.show({
								   title:'Información',
								   cls:'cgMsgBox',
								   msg: '<span>'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
		    				});
	    				}
	    				
	    				collectionDatosDetCons.add(id,datosDetalleConsent);
	    				cargarComboMediosCom(codigoConsent,id,datosDetalleConsent);
	    				cargarCombosDetalleConsentimiento(id);
	    				cargarFormularioDetalleConsentimiento(id,datosDetalleConsent);
    				}else{
    					Ext.get(id).child('.formDatos').setVisibilityMode(Ext.Element.DISPLAY).hide();
    					Ext.get(id).child('.formDatos').child('.detHistCons').remove();
    					Ext.get(id).removeClass('detExpand');
    					
    					myMask.hide();
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span>'+datosDetalleConsent.message+'</span><br/>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
    				}
    			}
    		}
    	});
	}
	
	var cargarFormularioDetalleConsentimiento = function (id,datosDetalleConsent){
		var selTipoComunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoComunicacion');
		var estadoGestion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoGestion');
		var estadoDerecho = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoDerecho');
		var divCalendar = Ext.get(id).child('.formDatos').child('.detHistCons').child('.divCalendar');
		var txtFechaCambioEstado = Ext.get(id).child('.formDatos').child('.detHistCons').child('.inputCalendar');
		var btnFechaCambioEstado = Ext.get(id).child('.formDatos').child('.detHistCons').child('.icoCalendar');
		var selTipoObjeto = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoObjeto');
		var txtTipoObjeto = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtTipoObjeto');
		var radObservaciones = Ext.get(id).child('.formDatos').child('.detHistCons').child('.radObservaciones');
		var radIncidencia = Ext.get(id).child('.formDatos').child('.detHistCons').child('.radIncidencia');
		var txtArea = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtArea');
		var selCodigoCampania = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selCampanias');
		var selMedioComnunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selMediosCom');
		var selTipoUbicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoUbicacion');
		var txtUbicacionFisica = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtUbicacionFisica');
		
		//Cargamos los datos recibidos en el formulario
		
		Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtUbicacionFisica').dom.value = Texto_HTML(datosDetalleConsent.datos[0].descUbicacion);
 				
 				
 				
		//SELECCIONAMOS EN LOS COMBOS LA OPCION QUE NOS LLEGA DEL SERVICIO
		//COMBO TIPO DE COMUNICACION
 				var i = 0;
		Ext.each(selTipoComunicacion.dom.options, function(option) {
			//Obtengo el indice en el que esta el codigo que tiene que ir seleccionado por defecto
		    if (option.value == datosDetalleConsent.datos[0].codTipoComunicacion){
		    	indiceSeleccionado = i;
		    }
		    i++;
		});
		//Seleccionamos en el combo
		selTipoComunicacion.dom.selectedIndex = indiceSeleccionado;
		
		//COMBO ESTADO GESTION
		//Averiguamos cual es la descripcion del estado de la gestion
		var descripcionEstGestion;
		Ext.each(datosLConsent.datos, function(dato) {
			
		    if (dato.codDerecho == id){
		    	descripcionEstGestion = dato.descEstadoGestion;
		    }
		});
		//Lo seleccionamos en el combo
 				i = 0;
		Ext.each(estadoGestion.dom.options, function(option) {
			//Obtengo el indice en el que esta el codigo que tiene que ir seleccionado por defecto
		    if (option.innerHTML == descripcionEstGestion){
		    	indiceSeleccionado = i;
		    }
		    i++;
		});
		//Seleccionamos en el combo
		estadoGestion.dom.selectedIndex = indiceSeleccionado;
		
		
		//COMBO ESTADO DERECHO
		//Averiguamos cual es el estado del derecho
		var indicadorExplotacion;
		Ext.each(datosLConsent.datos, function(dato) {
			
		    if (dato.codDerecho == id){
		    	indicadorExplotacion = dato.valorExplotacion;
		    }
		});
		if(indicadorExplotacion == "S"){
			estadoDerecho.dom.selectedIndex = 0;
		}else{
			estadoDerecho.dom.selectedIndex = 1;
		}
		
		//FECHA CAMBIO ESTADO
		//Averiguamos cual es la fecha de cambio de estado
		Ext.each(datosLConsent.datos, function(dato) {
			
		    if (dato.codDerecho == id){
		    	if(dato.fxCambioEstado != null){
		    		txtFechaCambioEstado.dom.value = modificarFechaAnio(convertDateFromJSONLib(dato.fxCambioEstado));	
		    	}
		    }
		});
		
		
		//COMBO OBJETO APLICACION
		//Seleccionamos en el combo
		txtTipoObjeto.dom.value = Texto_HTML(datosDetalleConsent.datos[0].descTipoObjeto);
		
		var i = 0;
		Ext.each(selTipoObjeto.dom.options, function(option) {
			//Obtengo el indice en el que esta el codigo que tiene que ir seleccionado por defecto
		    if (option.value == datosDetalleConsent.datos[0].codTipoObjeto){
		    	selTipoObjeto.dom.selectedIndex = i;
		    }
		    i++;
		});
		
		
		//RADIOS OBSERVACIONES O INCIDENCIA
		if(datosDetalleConsent.datos[0].indIncidencia == "S"){
			radIncidencia.dom.checked = true;
		}else{
			radObservaciones.dom.checked = true;
		}
		
		//AREA OBSERVACIONES
		txtArea.dom.value = Texto_HTML(datosDetalleConsent.datos[0].observacionCD);
		
		//COMBO CAMPANIA
 				var i = 0;
		Ext.each(selCodigoCampania.dom.options, function(option) {
			//Obtengo el indice en el que esta el codigo que tiene que ir seleccionado por defecto
		    if (option.value == datosDetalleConsent.datos[0].codCampaniaCD){
		    	indiceSeleccionado = i;
		    }
		    i++;
		});
		//Seleccionamos en el combo
		selCodigoCampania.dom.selectedIndex = indiceSeleccionado;
		
		//COMBO MEDIO COMUNICACION
 				var i = 0;
		Ext.each(selMedioComnunicacion.dom.options, function(option) {
			//Obtengo el indice en el que esta el codigo que tiene que ir seleccionado por defecto
		    if (option.value == datosDetalleConsent.datos[0].codMedioComunicacion){
		    	indiceSeleccionado = i;
		    }
		    i++;
		});
		//Seleccionamos en el combo
		selMedioComnunicacion.dom.selectedIndex = indiceSeleccionado;
		
		//COMBO TIPO DE UBICACION
 				var i = 0;
		Ext.each(selTipoUbicacion.dom.options, function(option) {
			//Obtengo el indice en el que esta el codigo que tiene que ir seleccionado por defecto
		    if (option.value == datosDetalleConsent.datos[0].codTipoUbicacion){
		    	indiceSeleccionado = i;
		    }
		    i++;
		});
		//Seleccionamos en el combo
		selTipoUbicacion.dom.selectedIndex = indiceSeleccionado;
		
		deshabilitarDetalleConsentimiento(id);
	}
	
	var deshabilitarDetalleConsentimiento = function(id){
		var selTipoComunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoComunicacion');
		var estadoGestion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoGestion');
		var estadoDerecho = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoDerecho');
		var divCalendar = Ext.get(id).child('.formDatos').child('.detHistCons').child('.divCalendar');
		var txtFechaCambioEstado = Ext.get(id).child('.formDatos').child('.detHistCons').child('.inputCalendar');
		var btnFechaCambioEstado = Ext.get(id).child('.formDatos').child('.detHistCons').child('.icoCalendar');
		var selTipoObjeto = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoObjeto');
		var txtTipoObjeto = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtTipoObjeto');
		var radObservaciones = Ext.get(id).child('.formDatos').child('.detHistCons').child('.radObservaciones');
		var radIncidencia = Ext.get(id).child('.formDatos').child('.detHistCons').child('.radIncidencia');
		var txtArea = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtArea');
		var selCodigoCampania = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selCampanias');
		var selMedioComnunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selMediosCom');
		var selTipoUbicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoUbicacion');
		var txtUbicacionFisica = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtUbicacionFisica');
		
		selTipoComunicacion.dom.disabled = true;
		estadoGestion.dom.disabled = true;
		estadoDerecho.dom.disabled = true;
		
		divCalendar.addClass('calendarDis')
		txtFechaCambioEstado.dom.readOnly = true;
		btnFechaCambioEstado.dom.disabled = true;
		
		selTipoObjeto.dom.disabled = true
		
		txtTipoObjeto.addClass('calendarDis')
		txtTipoObjeto.dom.readOnly = true;
		
		radObservaciones.dom.disabled = true
		radIncidencia.dom.disabled = true
		
		txtArea.addClass('dis')
		txtArea.dom.readOnly = true;
		
		selCodigoCampania.dom.disabled = true
		selMedioComnunicacion.dom.disabled = true
		selTipoUbicacion.dom.disabled = true
		
		txtUbicacionFisica.addClass('dis')
		txtUbicacionFisica.dom.readOnly = true;
	}
	
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
	var habilitarDetalleConsentimiento = function(id){
		var selTipoComunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoComunicacion');
		var estadoGestion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoGestion');
		var estadoDerecho = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoDerecho');
		var divCalendar = Ext.get(id).child('.formDatos').child('.detHistCons').child('.divCalendar');
		var txtFechaCambioEstado = Ext.get(id).child('.formDatos').child('.detHistCons').child('.inputCalendar');
		var btnFechaCambioEstado = Ext.get(id).child('.formDatos').child('.detHistCons').child('.icoCalendar');
		var selTipoObjeto = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoObjeto');
		var txtTipoObjeto = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtTipoObjeto');
		var radObservaciones = Ext.get(id).child('.formDatos').child('.detHistCons').child('.radObservaciones');
		var radIncidencia = Ext.get(id).child('.formDatos').child('.detHistCons').child('.radIncidencia');
		var txtArea = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtArea');
		var selCodigoCampania = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selCampanias');
		var selMedioComnunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selMediosCom');
		var selTipoUbicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoUbicacion');
		var txtUbicacionFisica = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtUbicacionFisica');
		
		selTipoComunicacion.dom.disabled = false;
		estadoGestion.dom.disabled = false;
		estadoDerecho.dom.disabled = false;
		
		divCalendar.removeClass('calendarDis')
		txtFechaCambioEstado.dom.readOnly = false;
		btnFechaCambioEstado.dom.disabled = false;
		
		txtTipoObjeto.removeClass('calendarDis')
		txtTipoObjeto.dom.readOnly = false;
		
		radObservaciones.dom.disabled = false
		radIncidencia.dom.disabled = false
		
		txtArea.removeClass('dis')
		txtArea.dom.readOnly = false;
		
		selCodigoCampania.dom.disabled = false
		selMedioComnunicacion.dom.disabled = false
		selTipoUbicacion.dom.disabled = false
		
		txtUbicacionFisica.removeClass('dis')
		txtUbicacionFisica.dom.readOnly = false;
	}
	
	var limpiarCamposConsentimiento = function(id){
		var selTipoComunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoComunicacion');
		var estadoGestion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoGestion');
		var estadoDerecho = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoDerecho');
		var divCalendar = Ext.get(id).child('.formDatos').child('.detHistCons').child('.divCalendar');
		var txtFechaCambioEstado = Ext.get(id).child('.formDatos').child('.detHistCons').child('.inputCalendar');
		var selTipoObjeto = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoObjeto');
		var txtTipoObjeto = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtTipoObjeto');
		var radObservaciones = Ext.get(id).child('.formDatos').child('.detHistCons').child('.radObservaciones');
		var radIncidencia = Ext.get(id).child('.formDatos').child('.detHistCons').child('.radIncidencia');
		var txtArea = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtArea');
		var selCodigoCampania = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selCampanias');
		var selMedioComnunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selMediosCom');
		var selTipoUbicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoUbicacion');
		var txtUbicacionFisica = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtUbicacionFisica');
		
		selTipoComunicacion.dom.selectedIndex = 0 ;
		estadoGestion.dom.selectedIndex = 0 ;
		estadoDerecho.dom.selectedIndex = 0 ;
		divCalendar.dom.value = "";
		txtFechaCambioEstado.dom.value = "";
		txtTipoObjeto.dom.value = "";
		radObservaciones.dom.checked = false;
		radIncidencia.dom.checked = false;
		txtArea.dom.value = "";
		selCodigoCampania.dom.selectedIndex = 0 ;
		selMedioComnunicacion.dom.selectedIndex = 0 ;
		selTipoUbicacion.dom.selectedIndex = 0 ;
		txtUbicacionFisica.dom.value = "";				
	}
	
	var nuevaComunicacion = function(){

		if(Ext.query('.detExpand', '.resFiltro')[0]){
			var div = Ext.fly(Ext.query('.detExpand', '.resFiltro')[0]);
			div.addClass('detNuevo');
			var id = Ext.query('.detExpand', '.resFiltro')[0].id;
			modoNuevaCom = true;
			habilitarDetalleConsentimiento(id);
			//Modificación para que limpie los campos
			limpiarCamposConsentimiento(id);
				
			if(!div.child('.formDatos').child('.divUsMod')){
				tplNuevaConsent.append(Ext.get(id).child('.formDatos'), {
					usuarioMod: collectionDatosDetCons.get(id).datos[0].codUsuario
				});
				
				//Asignamos funcionalidad al calendario
				Calendar.setup({
					inputField: Ext.get(id).child('.formDatos').child('.detHistCons').child('.inputCalendar').id,
					dateFormat: "%d.%m.%Y",
					min:fxIniMax,
					max:fxFinMax,
					trigger: Ext.get(id).child('.formDatos').child('.detHistCons').child('.icoCalendar').id,
					bottomBar: false,
					align:'Tl///T/l',
					onSelect: function() {
						this.hide();
					}
				});
				
				
				//Asignamos funcionalidad al boton cancelar
				Ext.get(id).child('.formDatos').child('.divUsMod').child('.divBtn').child('.btnCancelar').on('click', function() {
					cancelarNuevaComunicacion(id);
				});

				//Asignamos funcionalidad al boton aceptar
				Ext.get(id).child('.formDatos').child('.divUsMod').child('.divBtn').child('.btnAceptar').on('click', function() {
					aceptarModificarComunicacion(id);
				});
			}else{
				div.child('.formDatos').child('.divUsMod').setVisibilityMode(Ext.Element.DISPLAY).show();
			}
		}else if(Ext.query('.cabConsentSel', '.resFiltro')[0]){
			var id = Ext.query('.cabConsentSel', '.resFiltro')[0].id;
			
			//Nueva comunicacion para un consentimiento con estado No Consiente
			if(Ext.get(id).child('.formDatos').child('.detHistCons') == null){
				tplDetConsent.append(Ext.get(id).child('.formDatos'), {});
				cargarCombosDetalleConsentimiento(id);
				Ext.get(id).addClass('detExpand');
			}
			
			//Recuperamos el tipo de objeto
			var selTipoObjeto = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoObjeto');
			var codigoTipoObjeto = 0;
			Ext.each(datosLConsent.datos, function(option){
				if(option.codDerecho == id){
					codigoTipoObjeto = option.codTipoObjeto;
				}
			});
			
			var i = 0;			
			Ext.each(selTipoObjeto.dom.options, function(option) {
				//Obtengo el indice en el que esta el codigo que tiene que ir seleccionado por defecto
			    if (option.value == codigoTipoObjeto){
			    	selTipoObjeto.dom.selectedIndex = i;
			    }
			    i++;
			});
			
			selTipoObjeto.dom.disabled = true;
			//Fin recuperar tipo objeto
			if(!Ext.get(id).child('.formDatos').isVisible()){
				Ext.get(id).child('.formDatos').setVisibilityMode(Ext.Element.DISPLAY).show();
				Ext.get(id).child('.formDatos').child('.detHistCons').setVisibilityMode(Ext.Element.DISPLAY).show();
			}else{
				if(Ext.get(id).child('.formDatos').child('.detHistCons').isVisible()){
					Ext.get(id).child('.formDatos').setVisibilityMode(Ext.Element.DISPLAY).hide();
				}else{
					Ext.get(id).child('.formDatos').child('.detHistCons').setVisibilityMode(Ext.Element.DISPLAY).show();
				
					Ext.get(id).addClass('detExpand');
				}
			}
			
			var div = Ext.fly(Ext.query('.cabConsentSel', '.resFiltro')[0]);
			div.addClass('detNuevo');
			var id = Ext.query('.cabConsentSel', '.resFiltro')[0].id;
			modoNuevaCom = true;
			modoAltaCom = true;
			if(!div.child('.formDatos').child('.divUsMod')){
				tplNuevaConsent.append(Ext.get(id).child('.formDatos'), {
					usuarioMod: Ext.get('idUsuario').dom.innerHTML
				});
				
				//Asignamos funcionalidad al calendario
				Calendar.setup({
					inputField: Ext.get(id).child('.formDatos').child('.detHistCons').child('.inputCalendar').id,
					dateFormat: "%d.%m.%Y",
					min:fxIniMax,
					max:fxFinMax,
					trigger: Ext.get(id).child('.formDatos').child('.detHistCons').child('.icoCalendar').id,
					bottomBar: false,
					align:'Tl///T/l',
					onSelect: function() {
						this.hide();
					}
				});
				
				
				//Asignamos funcionalidad al boton cancelar
				Ext.get(id).child('.formDatos').child('.divUsMod').child('.divBtn').child('.btnCancelar').on('click', function() {
					cancelarAnadirNuevaCom(id);
				});

				//Asignamos funcionalidad al boton aceptar
				Ext.get(id).child('.formDatos').child('.divUsMod').child('.divBtn').child('.btnAceptar').on('click', function() {
					aceptarNuevaComunicacion(id);
				});
			}else{
				div.child('.formDatos').child('.divUsMod').setVisibilityMode(Ext.Element.DISPLAY).show();
			}
		}
		if(id){
			var estadoDerecho = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoDerecho');
			estadoDerecho.dom.disabled = true;
			//Ext.get(id).child('.formDatos').child('.divUsMod').child('.divBtn').child('.btnAceptar').on('click', function()
			Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoGestion').on('change', function() {
				mostrarEstadoConsentSeleccionado(id);
			});
			
			Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoGestion').on('keyup', function() {
				mostrarEstadoConsentSeleccionado(id);
			});
		}
	}
	
	var mostrarEstadoConsentSeleccionado = function (id){
	
		var estadoGestion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoGestion');
		var estadoDerecho = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoDerecho');
	
		if(estadoGestion.dom.value == "NO"){
			estadoDerecho.dom.selectedIndex = 1
		}else if(estadoGestion.dom.value == "SC"){
			estadoDerecho.dom.selectedIndex = 0
		}else if(estadoGestion.dom.value == "PE"){
			estadoDerecho.dom.selectedIndex = 1
		}else if(estadoGestion.dom.value == "NC"){
			estadoDerecho.dom.selectedIndex = 1
		}else if(estadoGestion.dom.value == "CA"){
			estadoDerecho.dom.selectedIndex = 1
		}
	}
	
	var cancelarAnadirNuevaCom = function(id){
		if(comprobarValoresNuevaCom(id)){
			Ext.Msg.show({
			   title:'Se perderán los datos',
			   msg: '<span>Se van a perder los datos introducidos, ¿Desea continuar?</span>',
			   buttons: Ext.Msg.YESNO,
			   icon: Ext.MessageBox.WARNING,
			   fn:function(respuesta){
			   		if (respuesta == 'yes'){
			   			limpiarFormularioNuevaCom(id);
			   		}
			   }
			});
		}
	}
	
	var comprobarValoresNuevaCom = function(id){
		var selTipoComunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoComunicacion');
		var estadoGestion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoGestion');
		var estadoDerecho = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoDerecho');
		var txtFechaCambioEstado = Ext.get(id).child('.formDatos').child('.detHistCons').child('.inputCalendar');
		var selTipoObjeto = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoObjeto');
		var txtTipoObjeto = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtTipoObjeto');
		var radObservaciones = Ext.get(id).child('.formDatos').child('.detHistCons').child('.radObservaciones');
		var radIncidencia = Ext.get(id).child('.formDatos').child('.detHistCons').child('.radIncidencia');
		var txtArea = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtArea');
		var selCodigoCampania = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selCampanias');
		var selMedioComnunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selMediosCom');
		var selTipoUbicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoUbicacion');
		var txtUbicacionFisica = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtUbicacionFisica');
		
		if(selTipoComunicacion.dom.selectedIndex != 0 || estadoGestion.dom.selectedIndex != 0 || estadoDerecho.dom.selectedIndex != 0 || 
			txtFechaCambioEstado.dom.value.trim().length > 0 || selTipoObjeto.dom.selectedIndex != 0 || 
			txtTipoObjeto.dom.value.trim().length > 0 || radObservaciones.dom.checked || radIncidencia.dom.checked || 
			txtArea.dom.value.trim().length > 0 || selCodigoCampania.dom.selectedIndex != 0 || 
			selMedioComnunicacion.dom.selectedIndex != 0 || selTipoUbicacion.dom.selectedIndex != 0 ||
			txtUbicacionFisica.dom.value.trim().length > 0){
			//Se ha modificado algun valor
			return true
		}else{
			return false;
		}
	}
	
	var limpiarFormularioNuevaCom = function(id){
		Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoComunicacion').dom.selectedIndex = 0;
		Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoGestion').dom.selectedIndex = 0;
		Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoDerecho').dom.selectedIndex = 0;
		Ext.get(id).child('.formDatos').child('.detHistCons').child('.inputCalendar').dom.value = '';
		Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoObjeto').dom.selectedIndex = 0;
		Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtTipoObjeto').dom.value = '';
		Ext.get(id).child('.formDatos').child('.detHistCons').child('.radObservaciones').dom.checked = false;
		Ext.get(id).child('.formDatos').child('.detHistCons').child('.radIncidencia').dom.checked = false;
		Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtArea').dom.value = '';
		Ext.get(id).child('.formDatos').child('.detHistCons').child('.selCampanias').dom.selectedIndex = 0;
		Ext.get(id).child('.formDatos').child('.detHistCons').child('.selMediosCom').dom.selectedIndex = 0;
		Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoUbicacion').dom.selectedIndex = 0;
		Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtUbicacionFisica').dom.value = '';
	}
	
	var cancelarNuevaComunicacion = function(id){
		
		if(comprobarValoresDetalleCons(id)){
			Ext.Msg.show({
			   title:'Se perderán los datos',
			   msg: '<span>Se van a perder los datos introducidos, ¿Desea continuar?</span>',
			   buttons: Ext.Msg.YESNO,
			   icon: Ext.MessageBox.WARNING,
			   fn:function(respuesta){
			   		if (respuesta == 'yes'){
			   			cargarFormularioDetalleConsentimiento(id,collectionDatosDetCons.get(id));
			   		}
			   }
			});
		}
	}
	
	var aceptarNuevaComunicacion = function(id){
		if(comprobarObligatorios(id)){
			actualizarConsentimientoDerecho(id);
		}
	}
	
	var aceptarModificarComunicacion = function(id){
		if(comprobarObligatorios(id) && comprobarValoresDetalleCons(id)){
			actualizarConsentimientoDerecho(id);
		}
	}
	
	var comprobarObligatorios = function(id) {
	
		var selTipoComunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoComunicacion');
		var estadoGestion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoGestion');
		var txtFechaCambioEstado = Ext.get(id).child('.formDatos').child('.detHistCons').child('.inputCalendar');
		var selMedioComnunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selMediosCom');
		var selTipoUbicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoUbicacion');
		var msgErrorCampos = '';
		
		if(selTipoComunicacion.dom.value==""){
			 msgErrorCampos += '<span class="oblig">Es obligatorio seleccionar un Tipo de Comunicación.</span>';	
		}
		if(estadoGestion.dom.value==""){
			 msgErrorCampos += '<span class="oblig">Es obligatorio seleccionar un Estado de la Gestión.</span>';	
		}
		if(txtFechaCambioEstado.dom.value==""){
			 msgErrorCampos += '<span class="oblig">Es obligatorio seleccionar una Fecha cambio de Estado.</span>';	
		}
		if(selTipoUbicacion.dom.value==""){
			 msgErrorCampos += '<span class="oblig">Es obligatorio seleccionar un Tipo de Ubicación.</span>';	
		}
		if(selMedioComnunicacion.dom.value==""){
			 msgErrorCampos += '<span class="oblig">Es obligatorio seleccionar un Medio de Comunicación.</span>';	
		}
		
		 if(msgErrorCampos.length>0){
			Ext.Msg.show({
			title:'Quedan campos obligatorios sin informar',
			msg: '<span>No se han rellenado todos los campos obligatorios:</span><br/>'+msgErrorCampos,
			buttons: Ext.Msg.OK,
			width: 450,
			icon: Ext.MessageBox.ERROR
			});
			
			return false;
		}else{
			return true;
		}
	}
	
	var actualizarConsentimientoDerecho = function(id){
	
		var selTipoComunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoComunicacion');
			var estadoGestion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoGestion');
			var estadoDerecho = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoDerecho');
			var txtFechaCambioEstado = Ext.get(id).child('.formDatos').child('.detHistCons').child('.inputCalendar');
			var selTipoObjeto = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoObjeto');
			var txtTipoObjeto = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtTipoObjeto');
			var radObservaciones = Ext.get(id).child('.formDatos').child('.detHistCons').child('.radObservaciones');
			var radIncidencia = Ext.get(id).child('.formDatos').child('.detHistCons').child('.radIncidencia');
			var txtArea = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtArea');
			var selCodigoCampania = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selCampanias');
			var selMedioComnunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selMediosCom');
			var selTipoUbicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoUbicacion');
			var txtUbicacionFisica = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtUbicacionFisica');
			
			var fechaEstado = null;
			
			if(esFechaValida(txtFechaCambioEstado.dom.value), "Fecha cambio de estado")
				fechaEstado = Date.parseDate(txtFechaCambioEstado.dom.value, 'd.m.Y');
			
			//Obtenemos la situacion actual
			var arraySituacionActual = new Array();
			 
			Ext.each(datosTipoObjeto.datos, function(dato) {
			
			    if (dato.codigoNivel == collectionDatosListadoCons.get(id).codTipoObjeto){
			    	arraySituacionActual.push({secuencial:collectionDatosListadoCons.get(id).secTipoObjeto, 
			    		codigo:dato.codigoNivel, descripcion:dato.descripcionNivel});
			    }
			});
			
			//Obtenemos la situacion nueva
			
			var arraySituacionNueva = new Array();
			
			Ext.each(combosDetalleConsent.tiposObjetos.listaDatos,function(dato){
				if (dato.codigoNivel == selTipoObjeto.dom.value){
			    	arraySituacionNueva.push({codigoNivel:dato.codigoNivel, descripcionNivel:dato.descripcionNivel,
			    		fechaInicioVigencia:dato.fechaInicioVigencia, fechaFinVigencia:dato.fechaFinVigencia,
			    		usuarioAlta:dato.usuarioAlta, usuarioModificacion:dato.usuarioModificacion, 
			    		fechaAltaNivel:dato.fechaAltaNivel, fechaUltMod:dato.fechaUltMod, nombrePrograma:dato.nombrePrograma});
				}
			});
			
			
			var actualizacion = {
			
				codigoLOPD:collectionDatosListadoCons.get(id).codDerecho,
				situacionActual:arraySituacionActual,
				codigoCliente:Ext.get('FCHistorialCodCliente').dom.value,
				codEstadoCD:estadoDerecho.dom.value,
				codGestion:estadoGestion.dom.value,
				valorExplotacion:estadoDerecho.dom.value,
				codOperacion:selTipoComunicacion.dom.value,
				fxCambioEstado:fechaEstado,
				situacionNueva:arraySituacionNueva,
				descEmail:collectionDatosListadoCons.get(id).descEmail,
				indIncidencia:radIncidencia.dom.checked,
				descObservacion:HTML_Texto(txtArea.dom.value),
				codCampaniaCD:selCodigoCampania.dom.value,
				codMedioComunicacion:selMedioComnunicacion.dom.value,
				codTipoUbicacion:selTipoUbicacion.dom.value,
				descUbicacion:HTML_Texto(txtUbicacionFisica.dom.value),
				fxIniVigencia:collectionDatosListadoCons.get(id).fxIniVigencia,
				fxFinVigencia:collectionDatosListadoCons.get(id).fxFinVigencia,
				codUsuario:collectionDatosListadoCons.get(id).codUsuario
			};			
			
			myMask = new Ext.LoadMask('marco', {msg:"Guardando datos..."});
			myMask.show();
			
			Ext.Ajax.request({
	    		url:contexto + 'ActualizacionConsentimiento.do',
	    		params:{
	    			actualizar:'',
	    			actualizacionClienteJSON:Ext.encode(actualizacion)
	    		},
    			callback:function (options, success, response){
    				myMask.hide();
    				var respueta = Ext.util.JSON.decode (response.responseText);
    				
    				if (respueta.success){
    					//Pueden llegar mensajes informativos.
    					if (respueta.message && respueta.message.length>0){
	    					Ext.each(respueta.message,function(message){
		    					Ext.Msg.show({
								   title:'Información',
								   cls:'cgMsgBox',
								   msg: '<span>'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
		    				});
	    				}
	    				modoNuevaCom = false;
    					filtrarCosentimientos();
    				}else{
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span>'+respueta.message+'</span><br/>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
    				}
    			}
			});
		
	}
	
	var comprobarValoresDetalleCons = function(id){
		var selTipoComunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoComunicacion');
		var estadoGestion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoGestion');
		var estadoDerecho = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoDerecho');
		var divCalendar = Ext.get(id).child('.formDatos').child('.detHistCons').child('.divCalendar');
		var txtFechaCambioEstado = Ext.get(id).child('.formDatos').child('.detHistCons').child('.inputCalendar');
		var btnFechaCambioEstado = Ext.get(id).child('.formDatos').child('.detHistCons').child('.icoCalendar');
		var selTipoObjeto = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoObjeto');
		var txtTipoObjeto = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtTipoObjeto');
		var radObservaciones = Ext.get(id).child('.formDatos').child('.detHistCons').child('.radObservaciones');
		var radIncidencia = Ext.get(id).child('.formDatos').child('.detHistCons').child('.radIncidencia');
		var txtArea = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtArea');
		var selCodigoCampania = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selCampanias');
		var selMedioComnunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selMediosCom');
		var selTipoUbicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoUbicacion');
		var txtUbicacionFisica = Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtUbicacionFisica');
		
		
		//COMBO ESTADO GESTION
		//Averiguamos cual es la descripcion del estado de la gestion
		var descripcionEstGestion;
		Ext.each(datosLConsent.datos, function(dato) {
			
		    if (dato.codDerecho == id){
		    	descripcionEstGestion = dato.descEstadoGestion;
		    }
		});
		
		
		//COMBO ESTADO DERECHO
		//Averiguamos cual es el estado del derecho
		var indicadorExplotacion;
		var booleanEstadoDerecho;
		Ext.each(datosLConsent.datos, function(dato) {
			
		    if (dato.codDerecho == id){
		    	indicadorExplotacion = dato.valorExplotacion;
		    }
		});
		if(indicadorExplotacion == "S"){
			if(estadoDerecho.dom.selectedIndex == 0)
				booleanEstadoDerecho = true;
			else
				booleanEstadoDerecho = false
		}else{
			if(estadoDerecho.dom.selectedIndex == 0)
				booleanEstadoDerecho = false;
			else
				booleanEstadoDerecho = true
		}
		
		
		//FECHA CAMBIO ESTADO
		//Averiguamos cual es la fecha de cambio de estado
		var fechaCambio;
		Ext.each(datosLConsent.datos, function(dato) {
			
		    if (dato.codDerecho == id){
		    	if(dato.fxCambioEstado != null)
		    		fechaCambio = modificarFechaAnio(convertDateFromJSONLib (dato.fxCambioEstado));
		    }
		});
		
		var booleanIncidencia = false;
		if(radIncidencia.dom.checked && collectionDatosDetCons.get(id).datos[0].indIncidencia == "S"){
			booleanIncidencia = true;
		}
		
		if(radObservaciones.dom.checked && collectionDatosDetCons.get(id).datos[0].indIncidencia != "S"){
			booleanIncidencia = true;
		}
		
		
		if(selTipoComunicacion.dom.value != collectionDatosDetCons.get(id).datos[0].codTipoComunicacion ||
			/*estadoGestion.dom[estadoGestion.dom.selectedIndex].innerHTML != descripcionEstGestion  ||
			!booleanEstadoDerecho ||*/
			txtFechaCambioEstado.dom.value != fechaCambio ||
			selTipoObjeto.dom.selectedIndex != 3 || 
			txtTipoObjeto.dom.value != Texto_HTML(collectionDatosDetCons.get(id).datos[0].descTipoObjeto) ||
			!booleanIncidencia || txtArea.dom.value != Texto_HTML(collectionDatosDetCons.get(id).datos[0].observacionCD) ||
			selCodigoCampania.dom.value != collectionDatosDetCons.get(id).datos[0].codCampaniaCD ||
			selMedioComnunicacion.dom.value != collectionDatosDetCons.get(id).datos[0].codMedioComunicacion || 
			 selTipoUbicacion.dom.value != collectionDatosDetCons.get(id).datos[0].codTipoUbicacion ||
			 txtUbicacionFisica.dom.value != Texto_HTML(collectionDatosDetCons.get(id).datos[0].descUbicacion)){
			
			return true
		}else{
			return false;
		}
	}
	</sec:authorize>
	var obtenerCombosDetalleConsentimiento = function(){
		Ext.Ajax.request({
    		url:contexto + 'DetalleConsentimiento.do',
    		params:{
    			cargarCombosConsentimientos:''
    		},
    		callback:function (options, success, response){
    			myMask.hide();
    			if (success){
    				combosDetalleConsent = Ext.util.JSON.decode (response.responseText);
    				
    				if (combosDetalleConsent.success){
    					//Pueden llegar mensajes informativos.
    					if (combosDetalleConsent.message && combosDetalleConsent.message.length>0){
	    					Ext.each(combosDetalleConsent.message,function(message){
		    					Ext.Msg.show({
								   title:'Información',
								   cls:'cgMsgBox',
								   msg: '<span>'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
		    				});
	    				}
    				}else{
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span>'+combosDetalleConsent.message+'</span><br/>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
    				}
    			}
    		}
    	});
	}
	
	var cargarCombosDetalleConsentimiento = function(id){
		var selTipoComunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoComunicacion');
		var estadoGestion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoGestion');
		var estadoDerecho = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selEstadoDerecho');
		var selTipoObjeto = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoObjeto');
		var selCodigoCampania = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selCampanias');
		var selTipoUbicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoUbicacion');
		
		
		
		var arrayCombo = new Array();
		arrayCombo.push({texto: '', valor: ''});
		//CARGAR COMBO TIPO COMUNICACION
		Ext.each(combosDetalleConsent.tipoComunicacion.listaDatos,function(dato){
			if (dato.codigo != "MM" && dato.codigo != "PE" && dato.codigo != "BD" && (dato.fxIniVigencia.time <= fechaHoy().getTime() && fechaHoy().getTime() <= dato.fxFinVigencia.time)){
							arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});
			}
		});
		agregarValoresCombo(selTipoComunicacion, arrayCombo, true);
			
		
		arrayCombo = new Array();
		arrayCombo.push({texto: '', valor: ''});
		//CARGAR COMBO ESTADO GESTION
	    Ext.each(combosDetalleConsent.estadoGestion.listaDatos,function(dato){
	    	if (dato.codigo != "CA" && dato.codigo != "NO" && (dato.fxIniVigencia.time <= fechaHoy().getTime() && fechaHoy().getTime() <= dato.fxFinVigencia.time)){
	    		arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});
	    	}
	    });
	    agregarValoresCombo(estadoGestion, arrayCombo, true);
			
		
		arrayCombo = new Array();
		arrayCombo.push({texto: '', valor: ''});
		
		//CARGAR COMBO TIPO UBICACION
		Ext.each(combosDetalleConsent.tipoUbicacion.listaDatos,function(dato){
			var fechaIni = Date.parseDate(dato.fechaIniVigencia,'d.m.Y');
			var fechaFin = Date.parseDate(dato.fechaFinVigencia,'d.m.Y');
			if (fechaIni.getTime() <= fechaHoy().getTime() && fechaHoy().getTime() <= fechaFin.getTime()){
				arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});
			}
		});
		agregarValoresCombo(selTipoUbicacion, arrayCombo, true);
		
		arrayCombo = new Array();
		arrayCombo.push({texto: '', valor: ''});
		
		//CARGAR COMBO TIPO OBJETO
		Ext.each(combosDetalleConsent.tiposObjetos.listaDatos,function(dato){
			arrayCombo.push({texto: dato.descripcionNivel, valor: dato.codigoNivel});
		});
		agregarValoresCombo(selTipoObjeto, arrayCombo, true);
		
		arrayCombo = new Array();
		arrayCombo.push({texto: '', valor: ''});
		
		//CARGAR COMBO CAMPANIAS
		Ext.each(combosDetalleConsent.campanias.listaDatos,function(dato){
			var fechaIni = Date.parseDate(dato.fechaIniVigencia,'d.m.Y');
			var fechaFin = Date.parseDate(dato.fechaFinVigencia,'d.m.Y');
			if (fechaIni.getTime() <= fechaHoy().getTime() && fechaHoy().getTime() <= fechaFin.getTime()){
				arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});
			}	
		});
		agregarValoresCombo(selCodigoCampania, arrayCombo, true);
	}
	
	var cargarComboMediosCom = function(codigoConsent,id,datosDetalleConsent){
	
		var detalleCD = {
			codDerechoLOPD:codigoConsent
		};
	
		Ext.Ajax.request({
   		url:contexto + 'DetalleConsentimiento.do',
   		params:{
   			cargarComboMedios:'',
   			detalleConsentimientoJSON:Ext.encode(detalleCD)
   		},
   		callback:function (options, success, response){
   			myMask.hide();
   			if (success){
   				combosMediosCom = Ext.util.JSON.decode (response.responseText);
				
				if (combosMediosCom != null && combosMediosCom.success != false && Ext.get(id).child('.formDatos').child('.detHistCons') != null){
								
					var selMedioComunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selMediosCom');
				
					var arrayCombo = new Array();
					arrayCombo.push({texto: '', valor: ''});
	   				
	   				Ext.each(combosMediosCom.mediosComunicacion.listaDatos,function(dato){
	   					if ((dato.fechaIniVigencia != null && dato.fechaIniVigencia != "") && (dato.fechaFinVigencia != null && dato.fechaFinVigencia != "")){
	   						var fechaIni = Date.parseDate(dato.fechaIniVigencia,'d.m.Y');
							var fechaFin = Date.parseDate(dato.fechaFinVigencia,'d.m.Y');
							if (fechaIni.getTime() <= fechaHoy().getTime() && fechaHoy().getTime() <= fechaFin.getTime()){
								arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});
							}
						}
						else{
							arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});
						}
					});
					agregarValoresCombo(selMedioComunicacion, arrayCombo, true); 
					
					//COMBO MEDIO COMUNICACION
 					var i = 0;
					Ext.each(selMedioComunicacion.dom.options, function(option) {
						//Obtengo el indice en el que esta el codigo que tiene que ir seleccionado por defecto
					    if (option.value == datosDetalleConsent.datos[0].codMedioComunicacion){
					    	indiceSeleccionado = i;
					    }
					    i++;
					});
					//Seleccionamos en el combo
					selMedioComunicacion.dom.selectedIndex = indiceSeleccionado;
					  
				}					
   			}
   		}
   	});
	}
		
	var expandirTodosHistoriales = function(){
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
		if(!modoNuevaCom){
		</sec:authorize>
			ocultarTodosDivDetalle();
			mostrarTodosDivHistoriales();
			Ext.each(Ext.query('.divConsentimientos .formDatos', '.resFiltro'), function(formDatos) {
			
				var idPadre = Ext.get(formDatos.id).parent('.containerCons').id;
				
				if(Ext.get(formDatos.id).child('.divHistoriales') == null && collectionDatosListadoCons.get(idPadre).valorExplotacion != "N"){
					buscarHistorial(idPadre);
				}else{
					if(!Ext.get(formDatos.id).isVisible() && collectionDatosListadoCons.get(idPadre).valorExplotacion != "N"){
						Ext.get(formDatos.id).setVisibilityMode(Ext.Element.DISPLAY).show();
					}
				}
				
				if(collectionDatosListadoCons.get(idPadre).valorExplotacion != "N"){
					Ext.get(idPadre).addClass('histExpand');
					Ext.get(idPadre).removeClass('detExpand');
				}
			});	
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
		}else{
		
			Ext.Msg.show({
			   title:'Se perderán los datos',
			   msg: '<span>Se van a perder los datos introducidos, ¿Desea continuar?</span>',
			   buttons: Ext.Msg.YESNO,
			   icon: Ext.MessageBox.WARNING,
			   fn:function(respuesta){
			   		if (respuesta == 'yes'){
						if(modoAltaCom == false){
							var idAux = Ext.query('.detNuevo', '.resFiltro')[0].id;
							if(comprobarValoresDetalleCons(idAux)){
								cargarFormularioDetalleConsentimiento(idAux,collectionDatosDetCons.get(idAux));
							}
							deshabilitarDetalleConsentimiento(idAux);
						}else{
							Ext.get(Ext.query('.detNuevo', '.resFiltro')[0].id).child('.formDatos').child('.detHistCons').remove();
							Ext.get(Ext.query('.detNuevo', '.resFiltro')[0].id).child('.formDatos').child('.divUsMod').remove();
							modoAltaCom = false;
							Ext.get(Ext.query('.detNuevo', '.resFiltro')[0].id).child('.formDatos').setVisibilityMode(Ext.Element.DISPLAY).hide();
							Ext.fly(Ext.query('.detNuevo', '.resFiltro')[0]).removeClass('cabConsentSel');
							Ext.fly(Ext.query('.detNuevo', '.resFiltro')[0]).removeClass('detExpand');
						}
			   			
			   			modoNuevaCom = false;
			   			Ext.fly(Ext.query('.detNuevo', '.resFiltro')[0]).removeClass('detNuevo');
			   			
			   			ocultarTodosDivDetalle();
						mostrarTodosDivHistoriales();
						Ext.each(Ext.query('.divConsentimientos .formDatos', '.resFiltro'), function(formDatos) {
						
							var idPadre = Ext.get(formDatos.id).parent('.containerCons').id;
							
							if(Ext.get(formDatos.id).child('.divHistoriales') == null && collectionDatosListadoCons.get(idPadre).valorExplotacion != "N"){
								buscarHistorial(idPadre);
							}else{
								if(!Ext.get(formDatos.id).isVisible() && collectionDatosListadoCons.get(idPadre).valorExplotacion != "N"){
									Ext.get(formDatos.id).setVisibilityMode(Ext.Element.DISPLAY).show();
								}
							}
							
							if(collectionDatosListadoCons.get(idPadre).valorExplotacion != "N"){
								Ext.get(idPadre).addClass('histExpand');
								Ext.get(idPadre).removeClass('detExpand');
							}
						});	
			   			
			   		}
			   }
			});
		}
		</sec:authorize>
	}
	
	var colapsarTodosHistoriales = function(){
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
		if(!modoNuevaCom){
		</sec:authorize>
			ocultarTodosDivDetalle();
			mostrarTodosDivHistoriales();
			Ext.each(Ext.query('.divConsentimientos .formDatos', '.resFiltro'), function(formDatos) {
				var idPadre = Ext.get(formDatos.id).parent('.containerCons').id;
				
				if(Ext.get(formDatos.id).isVisible()){
					Ext.get(formDatos.id).setVisibilityMode(Ext.Element.DISPLAY).hide();
				}
				
				Ext.get(idPadre).removeClass('histExpand');
				Ext.get(idPadre).removeClass('detExpand');
			});	
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
		}else{
			
			Ext.Msg.show({
			   title:'Se perderán los datos',
			   msg: '<span>Se van a perder los datos introducidos, ¿Desea continuar?</span>',
			   buttons: Ext.Msg.YESNO,
			   icon: Ext.MessageBox.WARNING,
			   fn:function(respuesta){
			   		if (respuesta == 'yes'){
					
						if(modoAltaCom == false){
							var idAux = Ext.query('.detNuevo', '.resFiltro')[0].id;
							if(comprobarValoresDetalleCons(idAux)){  
								cargarFormularioDetalleConsentimiento(idAux,collectionDatosDetCons.get(idAux));
							}
							deshabilitarDetalleConsentimiento(idAux);
						}else{
							Ext.get(Ext.query('.detNuevo', '.resFiltro')[0].id).child('.formDatos').child('.detHistCons').remove();
							Ext.get(Ext.query('.detNuevo', '.resFiltro')[0].id).child('.formDatos').child('.divUsMod').remove();
							modoAltaCom = false;
							Ext.get(Ext.query('.detNuevo', '.resFiltro')[0].id).child('.formDatos').setVisibilityMode(Ext.Element.DISPLAY).hide();
							Ext.fly(Ext.query('.detNuevo', '.resFiltro')[0]).removeClass('cabConsentSel');
							Ext.fly(Ext.query('.detNuevo', '.resFiltro')[0]).removeClass('detExpand');
						}
						
						ocultarTodosDivDetalle();
						mostrarTodosDivHistoriales();
			   			
			   			modoNuevaCom = false;
			   			Ext.fly(Ext.query('.detNuevo', '.resFiltro')[0]).removeClass('detNuevo');
			   			
			   			Ext.each(Ext.query('.divConsentimientos .formDatos', '.resFiltro'), function(formDatos) {
							var idPadre = Ext.get(formDatos.id).parent('.containerCons').id;
							
							if(Ext.get(formDatos.id).isVisible()){
								Ext.get(formDatos.id).setVisibilityMode(Ext.Element.DISPLAY).hide();
							}
							
							Ext.get(idPadre).removeClass('histExpand');
							Ext.get(idPadre).removeClass('detExpand');
						});	
			   			
			   		}
			   }
			});	
		}
		</sec:authorize>
	}
	
	var ocultarTodosDivHistoriales = function(){
		Ext.each(Ext.query('.divHistoriales', '.resFiltro'), function(divHistorial) {
			Ext.get(divHistorial).setVisibilityMode(Ext.Element.DISPLAY).hide();
		});	
	}
	
	var mostrarTodosDivHistoriales = function(){
		Ext.each(Ext.query('.divHistoriales', '.resFiltro'), function(divHistorial) {
			Ext.get(divHistorial).setVisibilityMode(Ext.Element.DISPLAY).show();
		});	
	}
	
	var ocultarTodosDivDetalle = function(){
		Ext.each(Ext.query('.detHistCons', '.resFiltro'), function(divDetalle) {
			Ext.get(divDetalle).setVisibilityMode(Ext.Element.DISPLAY).hide();
		});	
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
		Ext.each(Ext.query('.divUsMod', '.resFiltro'), function(divUsMod) {
			Ext.get(divUsMod).setVisibilityMode(Ext.Element.DISPLAY).hide();
		});
		</sec:authorize>
	}
		
	/*=====================================================*/
	/*=======================DOMICILIOS====================*/
	/*=====================================================*/
	var mostrarDomicilios = function (){
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
		Ext.get('btnNuevaComunicacion').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('bhBotones').child('.bhSep').setVisibilityMode(Ext.Element.DISPLAY).hide();
		</sec:authorize>		
		
		Ext.get('divDatosGenerales').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divConsentimientos').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divDomicilios').setVisibilityMode(Ext.Element.DISPLAY).show();
		
		Ext.get('pesDatGen').removeClass('activa');
		Ext.get('pesConsentimientos').removeClass('activa');
		Ext.get('pesDomicilios').addClass('activa');
		
		if(!datosDomicilios || datosDomicilios.length == 0){
			contraerTodo();
			cargarDomicilios();
		}
	}
	
	//Acordeon Domicilios
	var iniciarAcordeon = function (contLNF,contLNM,contLogicas){
		contraerTodo();
		
		
		
		if (!datosDomicilios || datosDomicilios.direccionesClienteDto.length == 0){
			Ext.get('titDom').addClass('dis');
		}else{
			Ext.get('contDom').dom.innerHTML = '[ '+contLNF+' ]';
		
		}
		
		if (!datosDomicilios || datosDomicilios.direccionesClienteLNMDto.length == 0){
            Ext.get('titDomLNM').addClass('dis');
        }else{
            Ext.get('contDomLNM').dom.innerHTML = '[ '+contLNM+' ]';
        
        }
		
		if (!datosDomicilios || datosDomicilios.direccioneClienteLogicas.length == 0){
            Ext.get('titDomLog').addClass('dis');
        }else{
            Ext.get('contDomLog').dom.innerHTML = '[ '+contLogicas+' ]';
        
        }
		
	}
	
	var expandirTodo = function (){
		if ((!datosDomicilios || datosDomicilios.direccionesClienteDto.length > 0) && 
			!Ext.get('divDomGeo').child('.formDatos').isVisible())
			expandirContraerDomicilios('divDomGeo');
			
		if ((!datosDomicilios || datosDomicilios.direccionesClienteLNMDto.length > 0) && 
            !Ext.get('divDomGeoLNM').child('.formDatos').isVisible())
            expandirContraerDomicilios('divDomGeoLNM');	
			
		if ((!datosDomicilios || datosDomicilios.direccioneClienteLogicas.length > 0) && 
            !Ext.get('divDomLogicas').child('.formDatos').isVisible())
            expandirContraerDomicilios('divDomLogicas'); 	
	
	}
	
	var contraerTodo = function (){
		if (Ext.get('divDomGeo').child('.formDatos').isVisible())
			expandirContraerDomicilios('divDomGeo');
			
		if (Ext.get('divDomGeoLNM').child('.formDatos').isVisible())
            expandirContraerDomicilios('divDomGeoLNM');
            
        if (Ext.get('divDomLogicas').child('.formDatos').isVisible())
            expandirContraerDomicilios('divDomLogicas');    
			
	}
	
	var expandirContraerDomicilios = function (idDiv){
		var formDatos = Ext.get(idDiv).child('.formDatos');
		var cabecera = Ext.get(idDiv).child('.cabDatDom');
		
		if (!formDatos.isVisible()){
			Ext.get(idDiv).addClass('expandido');
			formDatos.setVisibilityMode(Ext.Element.DISPLAY).show();
			cabecera.select('.expandCol span').update('colapsar');
		}else{
			Ext.get(idDiv).removeClass('expandido');
			formDatos.setVisibilityMode(Ext.Element.DISPLAY).hide();
			cabecera.select('.expandCol span').update('expandir');
		}
		
	}
	
	var cargarDomicilios = function (){
	
	    var contLogicas = 0;
        var contLNM = 0;
        var contLNF = 0;
		myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
		myMask.show();
	
		var domicilios = {
			codigoCliente:Ext.get('FCHistorialCodCliente').dom.value
		};
		
	
		Ext.Ajax.request({
    		url:contexto + 'DomiciliosCliente.do',
    		params:{
    			buscar:'',
    			domiciliosJSON:Ext.encode(domicilios)
    		},
    		callback:function (options, success, response){
    			myMask.hide();
    			if (success){
    				var respuesta = Ext.util.JSON.decode (response.responseText);
    				
    				if (respuesta.success){
    					//Pueden llegar mensajes informativos.
    					if (respuesta.message && respuesta.message.length>0){
	    					Ext.each(respuesta.message,function(message){
		    					Ext.Msg.show({
								   title:'Información',
								   cls:'cgMsgBox',
								   msg: '<span>'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
		    				});
	    				}
	    				
	    				datosDomicilios = respuesta.datos[0];
	    				
	    				//Pintamos todas las plantillas
	    				var i = 0;
	    				Ext.each(datosDomicilios.direccionesClienteDto,function(clienteDto){
	    					if (i>0){
	    						clienteDto.sep = 'sepDom';
                            }
                            contLNF++;
							tplDom.append('domGeograficos', clienteDto);
							i++;
		    			});
		    			
		    			//Pintamos todas las plantillas LNM
                        var i = 0;
                        Ext.each(datosDomicilios.direccionesClienteLNMDto,function(clienteLMDto){
                            if (i>0){
                                clienteLMDto.sep = 'sepDom';
                            }
                           
                            contLNM++;
                            tplDomLNM.append('domGeograficosLNM', clienteLMDto);
                            i++;
                        });
		    			
		    			
		    			//Pintamos todas las plantillas direcciones Logicas
                        var i = 0;
                        Ext.each(datosDomicilios.direccioneClienteLogicas,function(clienteLogicasdto){
                            if (i>0){
                                clienteLogicasdto.sep = 'sepDom';
                            }
                                
                            contLogicas++;
                            tplOneRow.append('domDirLogicas', clienteLogicasdto);
                            i++;
                        });
                        
		    			
		    			
		    			
		    			iniciarAcordeon (contLNF,contLNM,contLogicas);
		    			
		    			if(datosDomicilios.direccionesClienteDto.length == 0
			    			&& datosDomicilios.direccionesClienteDtoLNM.length == 0){
			    			Ext.Msg.show({
							   title:'Información',
							   cls:'cgMsgBox',
							   msg: '<span>No se han encontrado domicilios para el cliente '+Ext.get('FCHistorialCodCliente').dom.value+'</span>',
							   buttons: Ext.Msg.OK,
							   icon: Ext.MessageBox.INFO
							});
						}
    				}else{
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span>'+respuesta.message+'</span><br/>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
    				}
    			}
    		}
    	});
	}
	
	var controlEventos = function() {
		/*---------------------------
			EVENTOS SOBRE PESTAÑAS			
		---------------------------*/
		// evento click Pestania Datos Generales
		Ext.get('pesDatGen').on('click', function() {
			mostrarDatosGenerales();
		});
		
		// evento click Pestania Consentimientos
		Ext.get('pesConsentimientos').on('click', function() {
			mostrarConsentimientos();
		});
		
		// evento click Pestania Domicilios
		Ext.get('pesDomicilios').on('click', function() {
			mostrarDomicilios();
		});
		
		// evento click boton cerrar
		Ext.get('btnCerrar').on('click', function() {
			cerrarFichaCliente();
		});
		
		/*---------------------------
			EVENTOS DATOS GENERALES			
		---------------------------*/
		// evento click Boton Datos Convergentes
		Ext.get('btnDatosConvergentes').on('click', function() {
			mostrarDatosConvergentes();
		});
		
		// evento click Boton Datos LNF
		Ext.get('btnDatosLNF').on('click', function() {
			mostrarDatosLNF();
		});
		
		// evento click Boton Datos LNMC
		Ext.get('btnDatosLNMC').on('click', function() {
			mostrarDatosLNMC();
		});
		
		// evento click Boton Datos LNMP
		Ext.get('btnDatosLNMP').on('click', function() {
			mostrarDatosLNMP();
		});		
		
		/*-----------------------------------
			EVENTOS SOBRE CONSENTIMIENTOS			
		-----------------------------------*/
		Ext.get('btnFiltrar').on('click', function() {
			var fechaInicio = Ext.get('txtFechaInicio').dom.value;
			var fechaFin = Ext.get('txtFechaFin').dom.value;
			var dFechaInicio = null;
			var dFechaFin = null;
			
			
			if(fechaInicio.trim().length == 0){
				fechaInicio = '01.01.1900';
			}
			
			if(fechaFin.trim().length == 0){
				fechaFin = '31.12.2500';
			}
			
			dFechaInicio = Date.parseDate(fechaInicio, 'd.m.Y');
			dFechaFin = Date.parseDate(fechaFin, 'd.m.Y');
			
			if(esFechaValida(fechaInicio, "Fecha inicio de cambio de estado") && esFechaValida(fechaFin, "Fecha fin de cambio de estado")){
				if (dFechaInicio < dFechaFin){
					filtro = {
						codCliente:Ext.get('FCHistorialCodCliente').dom.value,
						codTipoObjeto:Ext.get('selObjeto').dom.value,
						estadoGestion:Ext.get('selEstGestion').dom.value,
						estadoCD:Ext.get('selEstadoCD').dom.value,
						fxIniCambio:convertDateToJSONLib(dFechaInicio),
						fxFinCambio:convertDateToJSONLib(dFechaFin)
					};
					
					filtrarCosentimientos();
				}else{
					Ext.Msg.show({
					   title:'Fecha incorrecta',
					   msg: '<span>La fecha de fin vigencia debe ser mayor a la fecha de inicio vigencia.</span>',
					   buttons: Ext.Msg.OK,
					   icon: Ext.MessageBox.ERROR
					});
				}
			}
		});
		
		Ext.get('btnVerTodos').on('click', function() {
			filtro = {
				codCliente:Ext.get('FCHistorialCodCliente').dom.value
			};
			
			buscarConsentimientos();
		});
		
		Ext.get('expandAllCons').on('click', function() {
			expandirTodosHistoriales();
		});
		
		Ext.get('colAllCon').on('click', function() {
			colapsarTodosHistoriales();
		});
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
		Ext.get('btnNuevaComunicacion').on('click', function() {
			nuevaComunicacion();
		});
		</sec:authorize>
		
		// evento click Boton Datos LNM
		Ext.get('selEstGestion').on('change', function() {
			mostrarEstadoConsent();
		});
		
		Ext.get('selEstGestion').on('keyup', function() {
			mostrarEstadoConsent();
		});
		
		/*---------------------------
			EVENTOS SOBRE DOMICILIOS			
		---------------------------*/
		Ext.get('expandAll').on('click', function() {
			expandirTodo();
		});
		
		Ext.get('colAll').on('click', function() {			
			contraerTodo();
		});
		
		Ext.get('expDom').on('click', function() {
			if (!datosDomicilios || datosDomicilios.direccionesClienteDto.length > 0)
				expandirContraerDomicilios('divDomGeo');
		});
		
		Ext.get('expDomLNM').on('click', function() {
            if (!datosDomicilios || datosDomicilios.direccionesClienteLNMDto.length > 0)
                expandirContraerDomicilios('divDomGeoLNM');
        });
        
        Ext.get('expDomLogicas').on('click', function() {
            if (!datosDomicilios || datosDomicilios.direccioneClienteLogicas.length > 0)
                expandirContraerDomicilios('divDomLogicas');
        });
		
	}	
	
	var mostrarEstadoConsent = function (){
		if(Ext.get('selEstGestion').dom.value == "NO"){
			Ext.get('selEstadoCD').dom.selectedIndex = 2
		}else if(Ext.get('selEstGestion').dom.value == "SC"){
			Ext.get('selEstadoCD').dom.selectedIndex = 1
		}else if(Ext.get('selEstGestion').dom.value == "PE"){
			Ext.get('selEstadoCD').dom.selectedIndex = 2
		}else if(Ext.get('selEstGestion').dom.value == "NC"){
			Ext.get('selEstadoCD').dom.selectedIndex = 2
		}else if(Ext.get('selEstGestion').dom.value == "CA"){
			Ext.get('selEstadoCD').dom.selectedIndex = 2
		}else{
			Ext.get('selEstadoCD').dom.selectedIndex = 0
		}
	}
	
	var iniciarCalendarios = function (){
	
		Calendar.setup({
			inputField: "txtFechaInicio",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaInicio",
			min:fxIniMax,
			max:fxFinMax,
			bottomBar: false,
			align:'Tr///T/l',
			onSelect: function() {
				this.hide();
			}
		});
		
		Calendar.setup({
			inputField: "txtFechaFin",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaFin",
			min:fxIniMax,
			max:fxFinMax,
			bottomBar: false,
			align:'Tr///T/r',
			onSelect: function() {
				this.hide();
			}
		});
	}
	
	var cerrarFichaCliente = function (){
		Ext.Msg.show({
		   title:'Cerrar',
		   msg: '<span>Se cerrará la ventana actual, ¿Desea continuar?</span>',
		   buttons: Ext.Msg.YESNO,
		   icon: Ext.MessageBox.INFO,
		   fn:function(respuesta){
		   		if (respuesta == 'yes'){
		   			//Volvemos a la busqueda de clientes
		   			
		   			Ext.get('hiddenVuelta').dom.value = "accedido";
		   			Ext.get('QGListaClientesForm').dom.submit();
		   		}
		   }
		});
		
	}
	
	var iniciarDatosCabecera = function (){
		Ext.get('lblOrigen').update(Ext.get('FCHistorialOrigen').dom.value);
		Ext.get('lblModalidad').update(Ext.get('FCHistorialModalidad').dom.value);		
		Ext.get('lblCodCliente').update(Ext.get('FCHistorialCodCliente').dom.value);
		Ext.get('lblTipoDoc').update(Ext.get('FCHistorialTipoDoc').dom.value);
		Ext.get('lblNumDoc').update(Ext.get('FCHistorialDocIdentif').dom.value);
		
		if (Ext.get('FCHistorialRazonSocial').dom.value.length>0){
			Ext.get('lblNombreRazon').update(Ext.get('FCHistorialRazonSocial').dom.value);
		}else{
			Ext.get('lblNombreRazon').update(Ext.get('FCHistorialNombre').dom.value + ' ' + Ext.get('FCHistorialPrimerApellido').dom.value + ' ' + Ext.get('FCHistorialSegundoApellido').dom.value);
		}
		
		Ext.get('lblSegmento').update(Ext.get('FCHistorialSegmento').dom.value);
		Ext.get('lblSubseg').update(Ext.get('FCHistorialSubsegmento').dom.value);
		Ext.get('lblEstado').update(Ext.get('FCHistorialEstado').dom.value);
	}
	
	return {
		init: function (){
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			Ext.QuickTips.init();
			iniciarDatosCabecera();
			
			iniciarPestanias();
			
			crearPlantillas();
			
			controlEventos();
			
			iniciarCalendarios ();
		}
	}
	
}();

Ext.onReady (CGLOBAL.init, CGLOBAL, true);