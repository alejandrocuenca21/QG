var CGLOBAL = function (){	
	/*Plantillas*/
	var tplDom;
	var tplConsent;
	var tplOneRow;
	var tplConsDat
	var tplDetConsent;
	var tplNuevaConsent;
	
	var myMask;
	
	var datosLConsent;
	var datosTipoObjeto;
	var datosEstadoGest;
	var datosHistorialConsent;
	var datosDomicilios;
	var datosGenerales;
	var combosDetalleConsent;
	var collectionDatosDetCons = new Ext.util.MixedCollection();
	var modoNuevaCom = false;
	
	
	var iniciarPestanias = function (){
		mostrarDatosGenerales();
	}
	
	var crearPlantillas = function (){
		/*Plantilla para los domicilios geograficos de Domicilios*/
		var arrTplDom = [];
		arrTplDom.push('<table class="{sep}"><colgroup><col width="120"/><col width="200"/><col width="115"/><col width="100"/><col width="*"/></colgroup>');
		arrTplDom.push('<tbody><tr class="sep">');
		arrTplDom.push('<td class="pLeft20"><label>PROVINCIA</label><br/><span>{descProvincia}</span></td>');
		arrTplDom.push('<td><label>LOCALIDAD</label><br/><span>{descLocalidad}</span></td>');
		arrTplDom.push('<td><label>CÓDIGO POSTAL</label><br/><span>{codigoPostal}</span></td>');
		arrTplDom.push('<td><label>PAÍS</label><br/><span>{descPais}</span></td>');
		arrTplDom.push('<td><label>TIPO DOMICILIO</label><br/><span>{descTipoDireccion}</span></td></tr><tr>');
		arrTplDom.push('<td colspan="2"  class="pLeft20"><div class="colA"><label>TIPO VÍA</label><br/><span>{descTipoVia}</span></div><div class="colB"><label>DIRECCIÓN</label><br/><span>{descCalle}</span></div></td>');
		arrTplDom.push('<td><div class="colA"><label>Nº</label><br/><span>{descNumCalle}</span></div><div class="colB"><label>ESCALERA</label><br/><span>{numEscalera}</span></div></td>');
		arrTplDom.push('<td colspan="2"><div class="colA"><label>PISO</label><br/><span>{numPiso}</span></div><div class="colB"><label>LETRA</label><br/><span>{letra}</span></div></td>');
		arrTplDom.push('</tr></tbody></table>');
		tplDom = new Ext.XTemplate(arrTplDom.join(''));
		
		/*Plantilla para el resto de datos de Domicilios*/
		var arrTplOneRow = [];
		arrTplOneRow.push('<table class="{sep}"><tbody><tr><td class="oneRow pLeft20"><label>{label}</label><br/><span>{dato}</span></td></tr></tbody></table>');
		tplOneRow = new Ext.XTemplate(arrTplOneRow.join(''));
		
		/*Plantilla para los consentimientos*/
		var arrTplConsHead = [];
		arrTplConsHead.push('<div class="cabDatCons"><b class="headTitL"></b><b class="headTitR"></b>');
		arrTplConsHead.push('<div class="titCons"><b class="icoVerde"></b><span class="codCons">[{lblCodD}]</span><span class="descCons">{lblDescD}</span></div>');
		arrTplConsHead.push('<div class="divInfoCons"><div class="infoCons"><b class="icoNoCons"></b><b class="headInfoR"></b>');
		arrTplConsHead.push('<div class="colA"><span>{lblExplotacionD}</span></div><div class="colB"><span>{lblFxCambioEstado}</span></div></div>');
		arrTplConsHead.push('<div class="infoEst"><b class="icoPendiente"></b><span class="">{lblEstGest}</span></div>');
		arrTplConsHead.push('<div class="infoEst"><span class="">{lblTipoLog}</span></div>');
		arrTplConsHead.push('<div class="infoEst"><div class="bTit bTitHist botonesHistorial">');    			
		arrTplConsHead.push('<span class="bTitCont"><b class="bTitL"></b><b class="bTitR"></b><b class="ico"></b>Historial</span></div></div></div></div>');
		tplConsent = new Ext.XTemplate(arrTplConsHead.join(''));
		
		/*Plantilla para el historial de consentimientos*/
		tplConsDat = new Ext.XTemplate('<div class="histCons"><span class="fecha">{lblFecha}</span><span class="codigo">{lblCodigo}</span><span>{lblDescripcion}</span></div>');
		
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
		arrTplConsDet.push('<div><label>C&oacute;digo campa&ntilde;a:</label><br/><select class="w150 selCampanias"></select></div>');
		arrTplConsDet.push('<div><label>Medio comunicaci&oacute;n:</label><br/><select class="w150 selMediosCom"></select></div></td><td>');
		arrTplConsDet.push('<div><label>Tipo de ubicaci&oacute;n:</label><br/><select class="w150 selTipoUbicacion"></select></div>');
		arrTplConsDet.push('<div class="noPad"><label>Ubicaci&oacute;n f&iacute;sica:</label><br/><input type="text" value="" class="w416 txtUbicacionFisica"/></div>');
		arrTplConsDet.push('</td></tr></tbody></table></div>');
		
		tplDetConsent = new Ext.XTemplate(arrTplConsDet.join(''));
		
		/*Plantilla para los botones al pulsar nueva comunicacion*/
		var arrTplConsNueva = [];
		arrTplConsNueva.push('<div class="divUsMod"><label>Usuario modificaci&oacute;n: </label><span class="ususarioMod">{usuarioMod}</span>');
		arrTplConsNueva.push('<div class="divBtn">');
		arrTplConsNueva.push('<input type="image" class="btn btnAceptar" src="'+contexto+'/images/botones/QGbtAceptar.gif" alt="Aceptar" onclick="return false;"/>');
		arrTplConsNueva.push('<input type="image" class="btn btnCancelar" src="'+contexto+'/images/botones/QGbtCancelar.gif" alt="Cancelar" onclick="return false;" />');
		arrTplConsNueva.push('</div></div>');
		
		tplNuevaConsent = new Ext.XTemplate(arrTplConsNueva.join(''));
		
	}
	
	/*=====================================================*/
	/*====================DATOS GENERALES==================*/
	/*=====================================================*/
	var mostrarDatosGenerales = function (){
		Ext.get('btnNuevaComunicacion').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('bhBotones').child('.bhSep').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
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
			codigoCliente:'codCliente'
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
								   msg: '<span class="bold">'+message+'</span><br/>',
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
	    				
	    				//Convertimos las fechas de cliente Movil
	    				datosGenerales.datos[0].clienteMovilDto[0].fxAltaCliente = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilDto[0].fxAltaCliente);
	    				datosGenerales.datos[0].clienteMovilDto[0].fxAltaRegistro = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilDto[0].fxAltaRegistro);
	    				datosGenerales.datos[0].clienteMovilDto[0].fxFinVigencia = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilDto[0].fxFinVigencia);
	    				datosGenerales.datos[0].clienteMovilDto[0].fxIniVigencia = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilDto[0].fxIniVigencia);
	    				datosGenerales.datos[0].clienteMovilDto[0].fxUltCambioEstado = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilDto[0].fxUltCambioEstado);
	    				datosGenerales.datos[0].clienteMovilDto[0].fxCambioSegmento = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilDto[0].fxCambioSegmento);
	    				datosGenerales.datos[0].clienteMovilDto[0].fxUltModificacion = convertDateFromJSONLib (datosGenerales.datos[0].clienteMovilDto[0].fxUltModificacion);
	    				
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
						   msg: '<span class="bold">'+datosGenerales.message+'</span><br/>',
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
			Ext.get('btnDatosLNM').setVisibilityMode(Ext.Element.DISPLAY).show();
		}else if(datosGenerales.datos[0].datosClienteDto[0].inUnidadNegocio == 'F'){
			Ext.get('btnDatosLNF').setVisibilityMode(Ext.Element.DISPLAY).show();
			Ext.get('btnDatosLNM').setVisibilityMode(Ext.Element.DISPLAY).hide();
		}else{
			Ext.get('btnDatosLNF').setVisibilityMode(Ext.Element.DISPLAY).hide();
			Ext.get('btnDatosLNM').setVisibilityMode(Ext.Element.DISPLAY).show();
		}
		cargarDatosConvergentes();
		cargarDatosAdicionales();
		
	}
	
	var cargarDatosConvergentes = function (){
		Ext.get('lblDGTipoDoc').update (datosGenerales.datos[0].datosClienteDto[0].dsTipoDocumento);
		Ext.get('lblDGNDoc').update (datosGenerales.datos[0].datosClienteDto[0].nuDocumento);
		Ext.get('lblDGNombreRazon').update (datosGenerales.datos[0].datosClienteDto[0].dsNombre);
		Ext.get('lblDGApellido1').update (datosGenerales.datos[0].datosClienteDto[0].dsApellido1);
		Ext.get('lblDGApellido2').update (datosGenerales.datos[0].datosClienteDto[0].dsApellido2);
		Ext.get('lblDGSegmento').update (datosGenerales.datos[0].datosClienteDto[0].coSegmento);
		Ext.get('lblDGSubsegmento').update (datosGenerales.datos[0].datosClienteDto[0].coSubSegmento);
		Ext.get('lblDGFechaCambioSeg').update (datosGenerales.datos[0].datosClienteDto[0].fxUltSubSegmento.format('d/m/Y'));
		Ext.get('lblDGEstado').update (datosGenerales.datos[0].datosClienteDto[0].dsEstadoCliente);
		Ext.get('lblDGFechaCambioEstado').update (datosGenerales.datos[0].datosClienteDto[0].fxUltEstadoCli.format('d.m.Y'));
		Ext.get('lblDGMotivoEstado').update (datosGenerales.datos[0].datosClienteDto[0].dsMotivoEstado);
		Ext.get('lblDGObservaciones').update (datosGenerales.datos[0].datosClienteDto[0].dsComentariosCliente);
	}
	
	var cargarDatosAdicionales = function (){
		Ext.get('lblDGdaClienteInter').update (datosGenerales.datos[0].datosClienteDto[0].inCaracterInternacional);
		Ext.get('lblDGdaPais').update (datosGenerales.datos[0].datosClienteDto[0].dsPais);
		Ext.get('lblDGdaUltimaReactivacion').update (datosGenerales.datos[0].datosClienteDto[0].fxUltReactivacion.format('d/m/Y'));
		Ext.get('lblDGdaExencionImpuestos').update (datosGenerales.datos[0].datosClienteDto[0].inExentoImpuesto);
		Ext.get('lblDGdaTipoImpresion').update (datosGenerales.datos[0].datosClienteDto[0].coTipoFacturaMvl);
		Ext.get('lblDGdaMoneda').update (datosGenerales.datos[0].datosClienteDto[0].coMoneda);
		Ext.get('lblDGdaNivelConf').update (datosGenerales.datos[0].datosClienteDto[0].dsNivelConfidencial);
		Ext.get('lblDGdaOficinaAtencion').update (datosGenerales.datos[0].datosClienteDto[0].coUnidadGestion);
		Ext.get('lblDGdaTipoCliente').update (datosGenerales.datos[0].datosClienteDto[0].coTipoCliente);
		Ext.get('lblDGdaCabecera').update (datosGenerales.datos[0].datosClienteDto[0].inCabecera);
		Ext.get('lblDGdaSectorEconomico').update (datosGenerales.datos[0].datosClienteDto[0].dsSectorEconomico);
		Ext.get('lblDGdaIdioma').update (datosGenerales.datos[0].datosClienteDto[0].dsIdioma);
		Ext.get('lblDGdaPlanCuentas').update (datosGenerales.datos[0].datosClienteDto[0].coPlanClientes);
		Ext.get('lblDGdaActividadEconom').update (datosGenerales.datos[0].datosClienteDto[0].dsCNAE);
		Ext.get('lblDGdaSegmentoSWH').update (datosGenerales.datos[0].datosClienteDto[0].coSgmDatawarehou);
		Ext.get('lblDGdaAtencionCliente').update (datosGenerales.datos[0].datosClienteDto[0].coNivelAtencion);
		Ext.get('lblDGdaUnidadNegGlobal').update (datosGenerales.datos[0].datosClienteDto[0].inUnidadNegocio);
		Ext.get('lblDGdaIdentificacion').update (datosGenerales.datos[0].datosClienteDto[0].coIdentidad);
		Ext.get('lblDGdaFInicioVigenciaCGBL').update (datosGenerales.datos[0].datosClienteDto[0].fxIniVigencia.format('d/m/Y'));
		Ext.get('lblDGdaFinVigenciaCGBL').update (datosGenerales.datos[0].datosClienteDto[0].fxFinVigencia.format('d/m/Y'));
		Ext.get('lblDGdaClientePadre').update (datosGenerales.datos[0].datosClienteDto[0].codigoCliente);
		Ext.get('lblDGdaTipoCarInter').update (datosGenerales.datos[0].datosClienteDto[0].coCaracterInternacional);
		Ext.get('lblDGdaNombreCorto').update (datosGenerales.datos[0].datosClienteDto[0].noComercial);
		
		Ext.get('lblDGdaCabLNF').update (datosGenerales.datos[0].clienteFijoDto[0].inClienteCabecera);
		Ext.get('lblDGdaFInicioVigenciaLNF').update (datosGenerales.datos[0].clienteFijoDto[0].fxIniVigencia.format('d/m/Y'));
		Ext.get('lblDGdaFinVigenciaLNF').update (datosGenerales.datos[0].clienteFijoDto[0].fxFinVigencia.format('d/m/Y'));
		
		Ext.get('lblDGdaNombreRazonLNM').update (datosGenerales.datos[0].clienteMovilDto[0].dsNombre);
		Ext.get('lblDGdaApellido1LNM').update (datosGenerales.datos[0].clienteMovilDto[0].dsApellido1);
		Ext.get('lblDGdaApellido2LNM').update (datosGenerales.datos[0].clienteMovilDto[0].dsApellido2);
		Ext.get('lblDGdaTipoDocLNM').update (datosGenerales.datos[0].clienteMovilDto[0].coTipoDocumento);
		Ext.get('lblDGdaTipoClienteFis').update (datosGenerales.datos[0].clienteMovilDto[0].coTipoClienteFSC);
		Ext.get('lblDGdaTipoSeg').update (datosGenerales.datos[0].clienteMovilDto[0].coTipoSegmento);
		Ext.get('lblDGdaMotivoSeg').update (datosGenerales.datos[0].clienteMovilDto[0].coMotivoSegmento);
		Ext.get('lblDGdaCabLNM').update (datosGenerales.datos[0].clienteMovilDto[0].inCabeceraGrupo);
		Ext.get('lblDGdaPaisLNM').update (datosGenerales.datos[0].clienteMovilDto[0].coPais);
		Ext.get('lblDGdaTipoClienteLNM').update (datosGenerales.datos[0].clienteMovilDto[0].coTipoCliente);
		Ext.get('lblDGdaAltaLNM').update (datosGenerales.datos[0].clienteMovilDto[0].fxAltaCliente.format('d/m/Y'));
		Ext.get('lblDGdaFInicioVigenciaLNM').update (datosGenerales.datos[0].clienteMovilDto[0].fxIniVigencia.format('d/m/Y'));
		Ext.get('lblDGdaFinVigenciaLNM').update (datosGenerales.datos[0].clienteMovilDto[0].fxFinVigencia.format('d/m/Y'));
		
		
	}
	
	var cargarDatosLNF = function (){
		Ext.get('lblDGTipoDoc').update (datosGenerales.datos[0].datosClienteDto[0].dsTipoDocumento);
		Ext.get('lblDGNDoc').update (datosGenerales.datos[0].datosClienteDto[0].nuDocumento);
		Ext.get('lblDGNombreRazon').update (datosGenerales.datos[0].datosClienteDto[0].dsNombre);
		Ext.get('lblDGApellido1').update (datosGenerales.datos[0].datosClienteDto[0].dsApellido1);
		Ext.get('lblDGApellido2').update (datosGenerales.datos[0].datosClienteDto[0].dsApellido2);
		Ext.get('lblDGSegmento').update (datosGenerales.datos[0].datosClienteDto[0].coSegmento);
		Ext.get('lblDGSubsegmento').update (datosGenerales.datos[0].datosClienteDto[0].coSubSegmento);
		
		Ext.get('lblDGFechaCambioSeg').update (datosGenerales.datos[0].clienteFijoDto[0].fxUltCambioSubSegmento.format('d/m/Y'));
		
		Ext.get('lblDGEstado').update (datosGenerales.datos[0].datosClienteDto[0].dsEstadoCliente);
		
		Ext.get('lblDGFechaCambioEstado').update (datosGenerales.datos[0].clienteFijoDto[0].fxUltCambioEstado.format('d.m.Y'));
		
		Ext.get('lblDGMotivoEstado').update (datosGenerales.datos[0].datosClienteDto[0].dsMotivoEstado);
		Ext.get('lblDGObservaciones').update (datosGenerales.datos[0].datosClienteDto[0].dsComentariosCliente);
	}
	
	var cargarDatosLNM = function (){
		Ext.get('lblDGTipoDoc').update (datosGenerales.datos[0].datosClienteDto[0].dsTipoDocumento);
		Ext.get('lblDGNDoc').update (datosGenerales.datos[0].datosClienteDto[0].nuDocumento);
		
		Ext.get('lblDGNombreRazon').update (datosGenerales.datos[0].clienteMovilDto[0].dsNombre);
		Ext.get('lblDGApellido1').update (datosGenerales.datos[0].clienteMovilDto[0].dsApellido1);
		Ext.get('lblDGApellido2').update (datosGenerales.datos[0].clienteMovilDto[0].dsApellido2);
		Ext.get('lblDGSegmento').update (datosGenerales.datos[0].clienteMovilDto[0].coSegmento);
		Ext.get('lblDGSubsegmento').update (datosGenerales.datos[0].clienteMovilDto[0].coSubSegmento);
		Ext.get('lblDGFechaCambioSeg').update (datosGenerales.datos[0].clienteMovilDto[0].fxCambioSegmento.format('d/m/Y'));
		Ext.get('lblDGEstado').update (datosGenerales.datos[0].clienteMovilDto[0].coEstadoCliente);
		
		Ext.get('lblDGFechaCambioEstado').update (datosGenerales.datos[0].clienteMovilDto[0].fxUltCambioEstado.format('d.m.Y'));
		Ext.get('lblDGMotivoEstado').update (datosGenerales.datos[0].clienteMovilDto[0].coSituacionEstado);
		
		Ext.get('lblDGObservaciones').update (datosGenerales.datos[0].datosClienteDto[0].dsComentariosCliente);
	}
	
	var mostrarDatosConvergentes = function (){
		Ext.get('btnDatosConvergentes').addClass('activo');
		Ext.get('btnDatosLNF').removeClass('activo');
		Ext.get('btnDatosLNM').removeClass('activo');
		
		cargarDatosConvergentes();
	}
	
	var mostrarDatosLNF = function (){
		Ext.get('btnDatosLNF').addClass('activo');
		Ext.get('btnDatosConvergentes').removeClass('activo');
		Ext.get('btnDatosLNM').removeClass('activo');
		
		cargarDatosLNF();
	}
	
	var mostrarDatosLNM = function (){
		Ext.get('btnDatosLNM').addClass('activo');
		Ext.get('btnDatosLNF').removeClass('activo');
		Ext.get('btnDatosConvergentes').removeClass('activo');
		
		cargarDatosLNM();
	}
	
	/*=====================================================*/
	/*====================CONSENTIMIENTOS==================*/
	/*=====================================================*/
	var mostrarConsentimientos = function (){
		Ext.get('btnNuevaComunicacion').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('bhBotones').child('.bhSep').setVisibilityMode(Ext.Element.DISPLAY).show();
		
		Ext.get('divDatosGenerales').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divConsentimientos').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('divDomicilios').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		Ext.get('pesDatGen').removeClass('activa');
		Ext.get('pesConsentimientos').addClass('activa');
		Ext.get('pesDomicilios').removeClass('activa');
		
		//Cargamos los datos de la pestaña si no estan cargados
		if (!datosTipoObjeto){
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
								   msg: '<span class="bold">'+message+'</span><br/>',
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
						   msg: '<span class="bold">'+datosTipoObjeto.message+'</span><br/>',
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
								   msg: '<span class="bold">'+message+'</span><br/>',
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
	    					arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});
	    				});
						agregarValoresCombo('selEstGestion', arrayCombo, true);
						
						if(!combosDetalleConsent)
							obtenerCombosDetalleConsentimiento();	
    				}else{
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span class="bold">'+datosEstadoGest.message+'</span><br/>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
    				}
    			}
    		}
    	});
	}
	
	var cargarListadoConsentimientos = function (){
		myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
		myMask.show();
			
		Ext.Ajax.request({
    		url:contexto + 'ConsentimientosCliente.do',
    		params:{
    			buscar:''
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
								   msg: '<span class="bold">'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
		    				});
	    				}
	    				
	    				var resFiltro = Ext.get('divConsentimientos').child('.resFiltro');
	    				
	    				//CARGAR LA LISTA DE CONSENTIMIENTOS
	    				Ext.each(datosLConsent.datos,function(dato){
	    					
	    					Ext.DomHelper.append(resFiltro.id, '<div id="'+dato.codDerecho+'" class="containerCons"></div>');
	    					    					
	    					tplConsent.append(Ext.get(dato.codDerecho), {
	    						lblCodD:dato.codDerecho,
	    						lblDescD:dato.descDerecho,
	    						lblExplotacionD:dato.valorExplotacion,
	    						lblFxCambioEstado:convertDateFromJSONLib (dato.fxCambioEstado).format('d.m.Y'),
	    						lblEstGest:dato.descEstadoGestion,
	    						lblTipoLog:dato.tipoLogica
							});
							
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
								
								if(!modoNuevaCom){
									asignarFuncionalidadHistorial(idPadre, contParent);
								}else{
									
										Ext.Msg.show({
										   title:'Se perderán los datos',
										   msg: '<span>Se van a perder los datos introducidos, ¿Desea continuar?</span>',
										   buttons: Ext.Msg.YESNO,
										   icon: Ext.MessageBox.WARNING,
										   fn:function(respuesta){
										   		if (respuesta == 'yes'){
										   			var idAux = Ext.query('.detNuevo', '.resFiltro')[0].id;
										   			if(comprobarValoresDetalleCons(idAux)){
														cargarFormularioDetalleConsentimiento(idAux,collectionDatosDetCons.get(idAux));
													}
										   			deshabilitarDetalleConsentimiento(idAux);
										   			asignarFuncionalidadHistorial(idPadre, contParent);
										   			modoNuevaCom = false;
										   			Ext.fly(Ext.query('.detNuevo', '.resFiltro')[0]).removeClass('detNuevo');
										   		}
										   }
										});
									
								}
								
								
							});
						});	
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						//Añadimos funcionalidad a la cabecera de consentimientos para acceder al detalle
	    				Ext.each(Ext.query('.containerCons .cabDatCons', '.resFiltro'), function(cabecera) {
	    					
							Ext.get(cabecera).on("click", function(e){
								
								if(!modoNuevaCom){
									asignarFuncionalidadCabecera(cabecera);
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
										   			var idAux = Ext.query('.detNuevo', '.resFiltro')[0].id;
										   			if(comprobarValoresDetalleCons(idAux)){
														cargarFormularioDetalleConsentimiento(idAux,collectionDatosDetCons.get(idAux));
													}
										   			deshabilitarDetalleConsentimiento(idAux);
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
							});
						});
							
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
    				}else{
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span class="bold">'+datosLConsent.message+'</span><br/>',
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
	
	var filtrarCosentimientos = function (){
	}
	
	var buscarConsentimientos = function (){
		cargarListadoConsentimientos();
	}
	
	var buscarDetalleConsentimiento = function (){
	}
	
	var buscarHistorial = function (id){
		
		myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
		myMask.show();
	
		var historial = {
			codDerechoLOPD:'TODO',
			secTipoObjeto:'TODO',
			codTipoObjeto:'TODO',
			descObjetoCD:'TODO',
			codCliente:'TODO'
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
								   msg: '<span class="bold">'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
		    				});
	    				}
	    				
	    				//CARGAR EL HISTORIAL DEL CONSENTIMIENTOS		
				
						var divPadre = Ext.get(id);
				
    					Ext.DomHelper.append(divPadre.child('.formDatos').id, '<div class="divHistoriales"></div>');
	    				Ext.each(datosHistorialConsent.datos,function(dato){
	    					tplConsDat.append(Ext.get(id).child('.formDatos').child('.divHistoriales').id, {
	    						lblFecha:convertDateFromJSONLib (dato.fxOperacion).format('d.m.Y h:m'),
	    						lblCodigo:dato.codigoOperacion,
	    						lblDescripcion:dato.descOperacion
							});
	    				});
	    				
	    				Ext.get(divPadre.child('.formDatos')).setVisibilityMode(Ext.Element.DISPLAY).show();
	    				Ext.get(id).addClass('histExpand');
    				}else{
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span class="bold">'+datosHistorialConsent.message+'</span><br/>',
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
	
		var detalleCD = {
			codDerechoLOPD:'TODO',
			secObjeto:'TODO',
			secEvento:'TODO',
			codTipoObjeto:'TODO'
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
								   msg: '<span class="bold">'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
		    				});
	    				}
	    				
	    				collectionDatosDetCons.add(id,datosDetalleConsent);
	    				cargarCombosDetalleConsentimiento(id);
	    				cargarFormularioDetalleConsentimiento(id,datosDetalleConsent);
	    				
	    				
    				}else{
    					myMask.hide();
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span class="bold">'+datosDetalleConsent.message+'</span><br/>',
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
		
		Ext.get(id).child('.formDatos').child('.detHistCons').child('.txtUbicacionFisica').dom.value = datosDetalleConsent.datos[0].descUbicacion;
 				
 				
 				
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
			estadoGestion.dom.selectedIndex = 0;
		}else{
			estadoGestion.dom.selectedIndex = 1;
		}
		
		//FECHA CAMBIO ESTADO
		//Averiguamos cual es la fecha de cambio de estado
		Ext.each(datosLConsent.datos, function(dato) {
			
		    if (dato.codDerecho == id){
		    	txtFechaCambioEstado.dom.value = convertDateFromJSONLib (dato.fxCambioEstado).format('d.m.Y');
		    }
		});
		
		
		//COMBO OBJETO APLICACION
		//Seleccionamos en el combo
		selTipoObjeto.dom.selectedIndex = 3;
		txtTipoObjeto.dom.value = datosDetalleConsent.datos[0].descTipoObjeto;
		
		//RADIOS OBSERVACIONES O INCIDENCIA
		if(datosDetalleConsent.datos[0].indIncidencia == "S"){
			radIncidencia.dom.checked = true;
		}else{
			radObservaciones.dom.checked = true;
		}
		
		//AREA OBSERVACIONES
		txtArea.dom.value = datosDetalleConsent.datos[0].observacionCD;
		
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
		
		selTipoObjeto.dom.disabled = false
		
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
	
	var nuevaComunicacion = function(){
		
		if(Ext.query('.detExpand', '.resFiltro')[0]){
			var div = Ext.fly(Ext.query('.detExpand', '.resFiltro')[0]);
			div.addClass('detNuevo');
			var id = Ext.query('.detExpand', '.resFiltro')[0].id;
			modoNuevaCom = true;
			habilitarDetalleConsentimiento(id);
			if(!div.child('.formDatos').child('.divUsMod')){
				tplNuevaConsent.append(Ext.get(id).child('.formDatos'), {
					usuarioMod: collectionDatosDetCons.get(id).datos[0].codUsuario
				});
				
				//Asignamos funcionalidad al canlendario
				Calendar.setup({
					inputField: Ext.get(id).child('.formDatos').child('.detHistCons').child('.inputCalendar').id,
					dateFormat: "%d.%m.%Y",
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
			}else{
				div.child('.formDatos').child('.divUsMod').setVisibilityMode(Ext.Element.DISPLAY).show();
			}
		}
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
		
		
		/*TODO:
			Obtener las descripciones de los combos: 
				Estado de la gestion
		*/
		
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
		    	fechaCambio = convertDateFromJSONLib (dato.fxCambioEstado).format('d.m.Y');
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
			txtTipoObjeto.dom.value != collectionDatosDetCons.get(id).datos[0].descTipoObjeto ||
			!booleanIncidencia || txtArea.dom.value != collectionDatosDetCons.get(id).datos[0].observacionCD ||
			selCodigoCampania.dom.value != collectionDatosDetCons.get(id).datos[0].codCampaniaCD ||
			selMedioComnunicacion.dom.value != collectionDatosDetCons.get(id).datos[0].codMedioComunicacion || 
			 selTipoUbicacion.dom.value != collectionDatosDetCons.get(id).datos[0].codTipoUbicacion ||
			 txtUbicacionFisica.dom.value != collectionDatosDetCons.get(id).datos[0].descUbicacion){
			
			return true
		}else{
			return false;
		}
	}
	
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
								   msg: '<span class="bold">'+message+'</span><br/>',
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
						   msg: '<span class="bold">'+combosDetalleConsent.message+'</span><br/>',
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
		var selMedioComnunicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selMediosCom');
		var selTipoUbicacion = Ext.get(id).child('.formDatos').child('.detHistCons').child('.selTipoUbicacion');
		
		
		
		var arrayCombo = new Array();
		arrayCombo.push({texto: '', valor: ''});
		//CARGAR COMBO TIPO COMUNICACION
		Ext.each(combosDetalleConsent.tipoComunicacion.listaDatos,function(dato){
			arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});
		});
		agregarValoresCombo(selTipoComunicacion, arrayCombo, true);
			
		
		arrayCombo = new Array();
		arrayCombo.push({texto: '', valor: ''});
		//CARGAR COMBO ESTADO GESTION
	    Ext.each(combosDetalleConsent.estadoGestion.listaDatos,function(dato){
	    	arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});
	    });
	    agregarValoresCombo(estadoGestion, arrayCombo, true);
			
		
		arrayCombo = new Array();
		arrayCombo.push({texto: '', valor: ''});
		
		//CARGAR COMBO TIPO UBICACION
		Ext.each(combosDetalleConsent.tipoUbicacion.listaDatos,function(dato){
			arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});
		});
		agregarValoresCombo(selTipoUbicacion, arrayCombo, true);
			
		
		arrayCombo = new Array();
		arrayCombo.push({texto: '', valor: ''});
		
		//CARGAR COMBO CAMPANIAS
		Ext.each(combosDetalleConsent.campanias.listaDatos,function(dato){
			arrayCombo.push({texto: dato.codigo, valor: dato.codigo});
		});
		agregarValoresCombo(selCodigoCampania, arrayCombo, true);
			
		
		arrayCombo = new Array();
		arrayCombo.push({texto: '', valor: ''});
		
		//CARGAR COMBO MEDIOS COMUNICACION
		Ext.each(combosDetalleConsent.mediosComunicacion.listaDatos,function(dato){
			arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});
		});
		agregarValoresCombo(selMedioComnunicacion, arrayCombo, true);
			
		
		arrayCombo = new Array();
		arrayCombo.push({texto: '', valor: ''});
		
		//CARGAR COMBO MEDIOS COMUNICACION
		Ext.each(combosDetalleConsent.tiposObjetos.listaDatos,function(dato){
			arrayCombo.push({texto: dato.descripcionNivel, valor: dato.codigoNivel});
		});
		agregarValoresCombo(selTipoObjeto, arrayCombo, true);
			
	    			
	}
	
	var expandirTodosHistoriales = function(){
	
		if(!modoNuevaCom){
			ocultarTodosDivDetalle();
			mostrarTodosDivHistoriales();
			Ext.each(Ext.query('.divConsentimientos .formDatos', '.resFiltro'), function(formDatos) {
			
				var idPadre = Ext.get(formDatos.id).parent('.containerCons').id;
				
				if(Ext.get(formDatos.id).child('.divHistoriales') == null){
					buscarHistorial(idPadre);
				}else{
					if(!Ext.get(formDatos.id).isVisible()){
						Ext.get(formDatos.id).setVisibilityMode(Ext.Element.DISPLAY).show();
					}
				}
				
				Ext.get(idPadre).addClass('histExpand');
				Ext.get(idPadre).removeClass('detExpand');
			});	
		}else{
			
				Ext.Msg.show({
				   title:'Se perderán los datos',
				   msg: '<span>Se van a perder los datos introducidos, ¿Desea continuar?</span>',
				   buttons: Ext.Msg.YESNO,
				   icon: Ext.MessageBox.WARNING,
				   fn:function(respuesta){
				   		if (respuesta == 'yes'){
				   			var idAux = Ext.query('.detNuevo', '.resFiltro')[0].id;
				   			if(comprobarValoresDetalleCons(idAux)){
								cargarFormularioDetalleConsentimiento(idAux,collectionDatosDetCons.get(idAux));
							}
				   			deshabilitarDetalleConsentimiento(idAux);
				   			modoNuevaCom = false;
				   			Ext.fly(Ext.query('.detNuevo', '.resFiltro')[0]).removeClass('detNuevo');
				   			
				   			ocultarTodosDivDetalle();
							mostrarTodosDivHistoriales();
							Ext.each(Ext.query('.divConsentimientos .formDatos', '.resFiltro'), function(formDatos) {
							
								var idPadre = Ext.get(formDatos.id).parent('.containerCons').id;
								
								if(Ext.get(formDatos.id).child('.divHistoriales') == null){
									buscarHistorial(idPadre);
								}else{
									if(!Ext.get(formDatos.id).isVisible()){
										Ext.get(formDatos.id).setVisibilityMode(Ext.Element.DISPLAY).show();
									}
								}
								
								Ext.get(idPadre).addClass('histExpand');
								Ext.get(idPadre).removeClass('detExpand');
							});	
				   			
				   		}
				   }
				});
			
		}
	
	
		
	}
	
	var colapsarTodosHistoriales = function(){
		if(!modoNuevaCom){
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
		}else{
			
			Ext.Msg.show({
			   title:'Se perderán los datos',
			   msg: '<span>Se van a perder los datos introducidos, ¿Desea continuar?</span>',
			   buttons: Ext.Msg.YESNO,
			   icon: Ext.MessageBox.WARNING,
			   fn:function(respuesta){
			   		if (respuesta == 'yes'){
			   			var idAux = Ext.query('.detNuevo', '.resFiltro')[0].id;
			   			if(comprobarValoresDetalleCons(idAux)){
							cargarFormularioDetalleConsentimiento(idAux,collectionDatosDetCons.get(idAux));
						}
						ocultarTodosDivDetalle();
						mostrarTodosDivHistoriales();
			   			deshabilitarDetalleConsentimiento(idAux);
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
		
		Ext.each(Ext.query('.divUsMod', '.resFiltro'), function(divUsMod) {
			Ext.get(divUsMod).setVisibilityMode(Ext.Element.DISPLAY).hide();
		});
	}
		
	/*=====================================================*/
	/*=======================DOMICILIOS====================*/
	/*=====================================================*/
	var mostrarDomicilios = function (){
		Ext.get('btnNuevaComunicacion').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('bhBotones').child('.bhSep').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		Ext.get('divDatosGenerales').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divConsentimientos').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divDomicilios').setVisibilityMode(Ext.Element.DISPLAY).show();
		
		Ext.get('pesDatGen').removeClass('activa');
		Ext.get('pesConsentimientos').removeClass('activa');
		Ext.get('pesDomicilios').addClass('activa');
		
		if(!datosDomicilios || datosDomicilios.length == 0){
			cargarDomicilios();
		}
	}
	
	var iniciarAcordeon = function (){
		contraerTodo();
		
		if (!datosDomicilios || datosDomicilios.direccionesClienteDto.length == 0){
			Ext.get('titDom').addClass('dis');
		}else{
			Ext.get('contDom').dom.innerHTML = '[ '+datosDomicilios.direccionesClienteDto.length+' ]';
		
		}
		
		if (!datosDomicilios || datosDomicilios.numeroTelfFijo.length == 0){
			Ext.get('titTelF').addClass('dis');
		}else{
			Ext.get('contTelF').dom.innerHTML = '[ '+datosDomicilios.numeroTelfFijo.length+' ]';
		
		}
		
		if (!datosDomicilios || datosDomicilios.numeroTelfMovil.length == 0){
			Ext.get('titTelM').addClass('dis');
		}else{
			Ext.get('contTelM').dom.innerHTML = '[ '+datosDomicilios.numeroTelfMovil.length+' ]';
		
		}
		
		if (!datosDomicilios || datosDomicilios.apartadoCorreos.length == 0){
			Ext.get('titAprC').addClass('dis');
		}else{
			Ext.get('contAprC').dom.innerHTML = '[ '+datosDomicilios.apartadoCorreos.length+' ]';
		
		}
		
		if (!datosDomicilios || datosDomicilios.correoElectronico.length == 0){
			Ext.get('titCorreoE').addClass('dis');
		}else{
			Ext.get('contCorreoE').dom.innerHTML = '[ '+datosDomicilios.correoElectronico.length+' ]';
		
		}
		
		if (!datosDomicilios || datosDomicilios.descripcionWeb.length == 0){
			Ext.get('titWeb').addClass('dis');
		}else{
			Ext.get('contWeb').dom.innerHTML = '[ '+datosDomicilios.descripcionWeb.length+' ]';
		
		}
	}
	
	var expandirTodo = function (){
		if ((!datosDomicilios || datosDomicilios.direccionesClienteDto.length > 0) && 
			!Ext.get('divDomGeo').child('.formDatos').isVisible())
			expandirContraerDomicilios('divDomGeo');
			
		if ((!datosDomicilios || datosDomicilios.numeroTelfFijo.length > 0) && 
			!Ext.get('divTelFijo').child('.formDatos').isVisible())
			expandirContraerDomicilios('divTelFijo');
		
		if ((!datosDomicilios || datosDomicilios.numeroTelfMovil.length > 0) && 
			!Ext.get('divTelMovil').child('.formDatos').isVisible())
			expandirContraerDomicilios('divTelMovil');
		
		if ((!datosDomicilios || datosDomicilios.apartadoCorreos.length > 0) && 
			!Ext.get('divAprCorreos').child('.formDatos').isVisible())
			expandirContraerDomicilios('divAprCorreos');
			
		if ((!datosDomicilios || datosDomicilios.correoElectronico.length > 0) && 
			!Ext.get('divCorreoElectronico').child('.formDatos').isVisible())
			expandirContraerDomicilios('divCorreoElectronico');
			
		if ((!datosDomicilios || datosDomicilios.descripcionWeb.length > 0) && 
			!Ext.get('divWeb').child('.formDatos').isVisible())
			expandirContraerDomicilios('divWeb');
	}
	
	var contraerTodo = function (){
		if (Ext.get('divDomGeo').child('.formDatos').isVisible())
			expandirContraerDomicilios('divDomGeo');
			
		if (Ext.get('divTelFijo').child('.formDatos').isVisible())
			expandirContraerDomicilios('divTelFijo');
		
		if (Ext.get('divTelMovil').child('.formDatos').isVisible())
			expandirContraerDomicilios('divTelMovil');
		
		if (Ext.get('divAprCorreos').child('.formDatos').isVisible())
			expandirContraerDomicilios('divAprCorreos');
			
		if (Ext.get('divCorreoElectronico').child('.formDatos').isVisible())
			expandirContraerDomicilios('divCorreoElectronico');
			
		if (Ext.get('divWeb').child('.formDatos').isVisible())
			expandirContraerDomicilios('divWeb');
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
	
	
		myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
		myMask.show();
	
		var domicilios = {
			codigoCliente:'TODO'
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
								   msg: '<span class="bold">'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
		    				});
	    				}
	    				
	    				datosDomicilios = respuesta.datos[0];
	    				
	    				//Pintamos todas las plantillas
	    				var i = 0;
	    				Ext.each(datosDomicilios.direccionesClienteDto,function(clienteDto){
	    					if (i>0)
	    						clienteDto.sep = 'sepDom';
							tplDom.append('domGeograficos', clienteDto);
							i++;
		    			});
		    			
		    			i = 0;
		    			Ext.each(datosDomicilios.numeroTelfFijo,function(telFijo){		
							tplOneRow.append('domTelFijo', {
								sep:(i>0)? 'sepDom' : '',
								label:'TELÉFONO',
								dato:telFijo
							});
							i++;
		    			});
		    			
		    			i = 0;
		    			Ext.each(datosDomicilios.numeroTelfMovil,function(telMovil){		
							tplOneRow.append('domTelMovil', {
								sep:(i>0)? 'sepDom' : '',
								label:'TELÉFONO',
								dato:telMovil
							});
							i++;
		    			});
		    			
		    			i = 0;
		    			Ext.each(datosDomicilios.apartadoCorreos,function(aprCorrreo){		
							tplOneRow.append('domAprCorreos', {
								sep:(i>0)? 'sepDom' : '',
								label:'APARTADO DE CORREOS',
								dato:aprCorrreo
							});
							i++;
		    			});
		    			
		    			i = 0;
		    			Ext.each(datosDomicilios.correoElectronico,function(mail){		
							tplOneRow.append('domCorreoElectronico', {
								sep:(i>0)? 'sepDom' : '',
								label:'CORREO ELECTRÓNICO',
								dato:mail
							});
							i++;
		    			});
		    			
		    			i = 0;
		    			Ext.each(datosDomicilios.descripcionWeb,function(web){		
							tplOneRow.append('domWeb', {
								sep:(i>0)? 'sepDom' : '',
								label:'WEB',
								dato:web
							});
							i++;
		    			});
		    			
		    			iniciarAcordeon ();
    				}else{
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span class="bold">'+respuesta.message+'</span><br/>',
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
		
		// evento click Boton Datos LNM
		Ext.get('btnDatosLNM').on('click', function() {
			mostrarDatosLNM();
		});
		
		/*-----------------------------------
			EVENTOS SOBRE CONSENTIMIENTOS			
		-----------------------------------*/
		Ext.get('btnFiltrar').on('click', function() {
			filtrarCosentimientos();
		});
		
		Ext.get('btnVerTodos').on('click', function() {
			buscarConsentimientos();
		});
		
		Ext.get('expandAllCons').on('click', function() {
			expandirTodosHistoriales();
		});
		
		Ext.get('colAllCon').on('click', function() {
			colapsarTodosHistoriales();
		});
		
		Ext.get('btnNuevaComunicacion').on('click', function() {
			nuevaComunicacion();
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
		
		Ext.get('expTelF').on('click', function() {
			if (!datosDomicilios || datosDomicilios.numeroTelfFijo.length > 0)
				expandirContraerDomicilios('divTelFijo');
		});
		
		Ext.get('expTelM').on('click', function() {
			if (!datosDomicilios || datosDomicilios.numeroTelfMovil.length > 0)
				expandirContraerDomicilios('divTelMovil');
		});
		
		Ext.get('expAprC').on('click', function() {
			if (!datosDomicilios || datosDomicilios.apartadoCorreos.length > 0)
				expandirContraerDomicilios('divAprCorreos');
		});
		
		Ext.get('expCorreoE').on('click', function() {
			if (!datosDomicilios || datosDomicilios.correoElectronico.length > 0)
				expandirContraerDomicilios('divCorreoElectronico');
		});
		
		Ext.get('expWeb').on('click', function() {
			if (!datosDomicilios || datosDomicilios.descripcionWeb.length > 0)
				expandirContraerDomicilios('divWeb');
		});
	}	
	
	var iniciarCalendarios = function (){
	
		Calendar.setup({
			inputField: "txtFechaInicio",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaInicio",
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
			bottomBar: false,
			align:'Tr///T/r',
			onSelect: function() {
				this.hide();
			}
		});
	}
	
	var iniciarDatosCabecera = function (){
		Ext.get('lblOrigen').update(Ext.get('origen').dom.value);
		Ext.get('lblCodCliente').update(Ext.get('codCliente').dom.value);
		Ext.get('lblTipoDoc').update(Ext.get('tipoDoc').dom.value);
		Ext.get('lblNumDoc').update(Ext.get('docIdentif').dom.value);
		
		if (Ext.get('razonSocial').dom.value.length>0){
			Ext.get('lblNombreRazon').update(Ext.get('razonSocial').dom.value);
		}else{
			Ext.get('lblNombreRazon').update(Ext.get('nombre').dom.value + ' ' + Ext.get('primerApellido').dom.value + ' ' + Ext.get('segundoApellido').dom.value);
		}
		
		Ext.get('lblSegmento').update(Ext.get('segmento').dom.value);
		Ext.get('lblSubseg').update(Ext.get('subsegmento').dom.value);
		Ext.get('lblEstado').update(Ext.get('estado').dom.value);
	}
	
	return {
		init: function (){
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			
			iniciarDatosCabecera();
			
			iniciarPestanias();
			
			crearPlantillas();
			
			controlEventos();
			
			iniciarCalendarios ();
		}
	}
	
}();

Ext.onReady (CGLOBAL.init, CGLOBAL, true);