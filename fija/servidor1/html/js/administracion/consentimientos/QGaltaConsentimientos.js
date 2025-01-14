var CGLOBAL = function(){
	var datosResultado;
	var datosResultadoSegmentos;
	var datosResultadoMediosComunicacion;
	var datosResultadoAmbito;
	var datosResultadoNivelAplicacion;
	var datosResultadoTipoObjeto;
	var indiceAmbito;
	var myMask;
	
	var crearGridSegmentos = function(){
		var selectionModel = new Ext.grid.RowSelectionModel({
			singleSelect: false
		});
		
		// create the data store
	    var store = new Ext.data.Store({
	    	reader: new Ext.data.ObjectReader({}, [
				{name: 'codigo', type: 'string'},
				{name: 'descripcion', type: 'string'}
			])
	    });
	    
	    var columnModel = new Ext.grid.ColumnModel([
		    {header: '', id:'colDescripcion',  dataIndex: 'descripcion'}
		]);
		
		// create the Grid
	    gridSegmentos = new Ext.grid.GridPanel({
	    	id:'gridSegmentos',
	        store: store,
			renderTo: 'capaAniadirSegmento',
			cls:'gridConsentimiento gridAltaConsentimiento',
			sm: selectionModel,
			cm: columnModel,
	        stripeRows: true,
	        autoExpandColumn: 'colDescripcion',
	        width: 400,
	        height: 150,
	        title: 'Añadir segmento/subsegmento...',
	        listeners:{
	        	render:function(){
			    	crearArbolMedioComunicacion();
			    }
	        }
	    });
	 }
	 
	 var crearArbolMedioComunicacion = function(){
	 	var selectionModel = new Ext.grid.RowSelectionModel({
			singleSelect: false
		});
		
		// create the data store
	    var store = new Ext.data.Store({
	    	reader: new Ext.data.ObjectReader({}, [
				{name: 'codigo', type: 'string'},
				{name: 'descripcion', type: 'string'}
			])
	    });
	    
	    var columnModel = new Ext.grid.ColumnModel([
		    {header: '', id:'colDescripcion',  dataIndex: 'descripcion'}
		]);
		
		// create the Grid
	    gridMediosCom = new Ext.grid.GridPanel({
	    	id:'gridMediosComunicacion',
	        store: store,
			renderTo: 'capaAniadirMedio',
			cls:'gridConsentimiento gridAltaConsentimiento',
			sm: selectionModel,
			cm: columnModel,
	        stripeRows: true,
	        autoExpandColumn: 'colDescripcion',
	        width: 400,
	        height: 100,
	        title: 'Añadir medio de comunicación...',
	        listeners:{
	        	render:function(){
			    	cargarDatos();
			    }
	        }
	    });
	 }   
	 
	var resaltarCamposObligatorios = function(){
		
		Ext.get('txtCodigo').addClass('oblig');
		Ext.get('txtDescripcion').addClass('oblig');
		Ext.get('txtDescDetallada').addClass('oblig');
		Ext.get('txtTextoLegal').addClass('oblig');
		Ext.get('selNivel').addClass('oblig');
		Ext.get('selConsen').addClass('oblig');
		Ext.get('selAmbito').addClass('oblig');
		Ext.get('selObjeto').addClass('oblig');
		Ext.get('inputCalOblig').addClass('divCalendarOblig');
	}
	
	var iniciarCalendarios = function (){
		
		Calendar.setup({
			inputField: "txtFechaVigor",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaInicio",
			bottomBar: false,
			align:'Tl///T/r',
			onSelect: function() {
				this.hide();
			}
		});
		
		Calendar.setup({
			inputField: "txtFinVigencia",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaFin",
			bottomBar: false,
			align:'Tl///T/r',
			onSelect: function() {
				this.hide();
			}
		});
	}
	
	var cargarDatos = function (){
		
		Ext.Ajax.request({
    		url:contexto + 'DetalleConsentimientosDerechos.do',
    		params:{
    			cargarDatosConsentimientos:''
    		},
    		callback:function (options, success, response){
    			myMask.hide();
    			if (success){
    				datosResultado = Ext.util.JSON.decode (response.responseText);
    				
    				if (datosResultado.success){
    					//Pueden llegar mensajes informativos.
    					if (datosResultado.message && datosResultado.message.length>0){
	    					Ext.each(datosResultado.message,function(message){
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
    				
	    				//CARGAR COMBO DE AMBITOS
	    				//Obtengo el indice en el que esta el codigo que tiene que ir seleccionado por defecto
	    				var arrayCombo = new Array();
	    				Ext.each(datosResultado.ambitos.listaDatos,function(dato){
	    					arrayCombo.push({texto: dato.descAmbito, valor: dato.ambitoAplicacion});
	    					if (dato.ambitoAplicacion == "02"){
	    						indiceAmbito = i;
	    					}
	    					i++;
	    				});
						agregarValoresCombo('selAmbito', arrayCombo, true);
						//Selecciona por defecto el segundo elemento del combo que deberia ser 02
						Ext.get('selAmbito').dom.selectedIndex = indiceAmbito;
							
	    				//CARGAR COMBO DE TIPO OBJETO
	    				//Obtengo el indice en el que esta el codigo que tiene que ir seleccionado por defecto
	    				var arrayCombo = new Array();
	    				arrayCombo.push({texto: '', valor: ''});
	    				Ext.each(datosResultado.tiposObjetos.listaDatos,function(dato){
	    					arrayCombo.push({texto: dato.descripcionNivel, valor: dato.codigoNivel});
	    				});
						agregarValoresCombo('selObjeto', arrayCombo, true);
						
						//
	    				//CARGAR COMBO DE TIPO CONSENTIMIENTOS
	    				//Obtengo el indice en el que esta el codigo que tiene que ir seleccionado por defecto
	    				var arrayCombo = new Array();
	    				arrayCombo.push({texto: '', valor: ''});
	    				Ext.each(datosResultado.tiposConsentimiento.listaDatos,function(dato){
	    					arrayCombo.push({texto: dato.descConsentimiento, valor: dato.codigoConsentimiento});
	    				});
						agregarValoresCombo('selConsen', arrayCombo, true);
						//
							
	    				//CARGAR COMBO DE NIVEL DE APLICACION
						//Ejecuta la funcion que muestra y oculta los Niveles de aplicacion segun Exclusividad
						mostrarNivelAplicacionExclusividad();
						
	    				//CARGAR GRID SEGMENTOS
	    				gridSegmentos.getStore().loadData (datosResultado.segmentos.listaDatos);
	    				
	    				//CARGAR GRID MEDIOS COMUNICACION
	    				gridMediosCom.getStore().loadData (datosResultado.medios.listaDatos);
    				}else{
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span>'+datosResultado.message+'</span><br/>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR,
						   fn:function(respuesta){
						   		//Volvemos al listado de consentimiento derecho
		   						document.location.href=contexto+'vConsentimientosDerechos.do';
						   }
						});
    				}
    			}
    		}
    	});
		
		
		
	}
	
	//funcion que muestra y oculta los Niveles de aplicacion segun Exclusividad
	var mostrarNivelAplicacionExclusividad = function (){
	
		var arrayCombo = new Array();
		if (Ext.get('rbExclusivoSi').dom.checked){
			//Inserto en el Array el elemento cuyo codigo es C
			Ext.each(datosResultado.nivelesAplicacion.listaDatos,function(dato){
 				if (dato.codigoNivel == "C"){
 					arrayCombo.push({texto: dato.descripcionNivel, valor: dato.codigoNivel});
 				}
 			});
		}else{
			//Inserto en el Array el elemento cuyo codigo es distinto de C
			Ext.each(datosResultado.nivelesAplicacion.listaDatos,function(dato){
 				if (dato.codigoNivel != "C"){
 					arrayCombo.push({texto: dato.descripcionNivel, valor: dato.codigoNivel});
 				}
 			});
		}
		
		agregarValoresCombo('selNivel', arrayCombo, true);
		
		//Selecciona por defecto el primer elemento
		Ext.get('selNivel').dom.selectedIndex = 0;
	}
	
	var iniciarValoresDefecto = function (){
		Ext.get('rbExclusivoSi').dom.checked = true
		Ext.get('rbContactadoNo').dom.checked = true
	}
	
	
	//funcion que habilita o deshabilita los dias liberacion segun logica
	var mostrarDiasLiberacion = function (){
		if (Ext.get('rbLogicaE').dom.checked){
			Ext.get('selConsen').dom.disabled = true;
			Ext.get('selConsen').dom.value = '';
			Ext.get('txtLiberacion').addClass('dis');
			Ext.get('txtLiberacion').dom.value='';
			Ext.get('txtLiberacion').dom.readOnly = true;
		}else if (Ext.get('rbLogicaT').dom.checked){
			Ext.get('selConsen').dom.disabled = false;
			Ext.get('txtLiberacion').removeClass('dis');
			Ext.get('txtLiberacion').dom.value='0';
			Ext.get('txtLiberacion').dom.readOnly = false;
		}
	}
	
	// Controlamos los eventos sobre los botones
	var controlEventos = function() {
		
		// evento click del radioButton Exclusivo Si
		Ext.get('rbExclusivoSi').on('click', function() {
				mostrarNivelAplicacionExclusividad();
		});
		
		// evento click del radioButton Exclusivo No
		Ext.get('rbExclusivoNo').on('click', function() {
				mostrarNivelAplicacionExclusividad();
		});
		
		// evento click del radioButton Logica T
		Ext.get('rbLogicaT').on('click', function() {
				mostrarDiasLiberacion();
		});
		
		// evento click del radioButton Logica E
		Ext.get('rbLogicaE').on('click', function() {
				mostrarDiasLiberacion();
		});
		
		// evento click del boton limpiar
		Ext.get('btnEliminar').on('click', function() {
				limpiar();
		});
		
		// evento click del boton cerrar
		Ext.get('btnCerrar').on('click', function() {
				cerrar();
		});
		
		// evento click del boton guardar
		Ext.get('btnGuardar').on('click', function() {
				altaDatos();
		});
		
		var retardo = 200;
		//Controlamos que solo se pueda pegar 160 caracteres en el textarea de Descripcion Detallada
		Ext.get('txtDescDetallada').on('paste', function() {
			controlarMaxTxtArea.defer(retardo,this,[this.id,160]);
		});
		
		//Controlamos que solo se pueda pegar 160 caracteres en el textarea de Observaciones
		Ext.get('txtObservaciones').on('paste', function() {
			controlarMaxTxtArea.defer(retardo,this,[this.id,160]);
		});
		
		if (Ext.isIE6){ //Se usa para que en IE6 no se superponga los combos al menu
			Ext.get('liSegmentacion').on('mouseover', function() {
				Ext.get('selAmbito').setVisibilityMode(Ext.Element.DISPLAY).hide();
			});
			Ext.get('liSegmentacion').on('mouseout', function() {
				Ext.get('selAmbito').setVisibilityMode(Ext.Element.DISPLAY).show();
			});
		}
	}
	
	var controlarMaxTxtArea = function(id,tam){
		Ext.get(id).dom.value =Ext.get(id).dom.value.substring(0,tam);
	}
	
	var altaDatos = function() {
		var codigo = Ext.get('txtCodigo').dom.value;
		var desc = Ext.get('txtDescripcion').dom.value;
		var descDet = Ext.get('txtDescDetallada').dom.value;
		var txtLegal = Ext.get('txtTextoLegal').dom.value;
		var exclusivoNo = Ext.get('rbExclusivoNo').dom.checked;
		var exclusivoSi = Ext.get('rbExclusivoSi').dom.checked;
		var origenContSi = Ext.get('rbOrigenSi').dom.checked;
		var origenContNo = Ext.get('rbOrigenNo').dom.checked;
		var logicaT = Ext.get('rbLogicaT').dom.checked;
		var logicaE = Ext.get('rbLogicaE').dom.checked;
		var contactadoSi = Ext.get('rbContactadoSi').dom.checked;
		var contactadoNo = Ext.get('rbContactadoNo').dom.checked;
		var fechaInicio = Ext.get('txtFechaVigor').dom.value;
		var fechaFin = Ext.get('txtFinVigencia').dom.value;
		var tipoObjeto = Ext.get('selObjeto').dom.value;
		var nivelAplicacion = Ext.get('selNivel').dom.value;
		var tipoConsen = Ext.get('selConsen').dom.value;
		var com = Ext.get('txtComunicacion').dom.value;
		var ambito = Ext.get('selAmbito').dom.value;
		var tde = Ext.get('txtCodigoTde').dom.value;
		var tme = Ext.get('txtCodigoTme').dom.value;
		var unidadFija = Ext.get('rbFija').dom.checked;
		var unidadMovil = Ext.get('rbMovil').dom.checked;
		var unidadAmbas = Ext.get('rbAmbas').dom.checked;
		var observaciones = Ext.get('txtObservaciones').dom.value;
		var segmentos = gridSegmentos.getSelectionModel().getSelections().length;
		var mediosCom = gridMediosCom.getSelectionModel().getSelections().length;
		dFechaInicio = Date.parseDate(fechaInicio, 'd.m.Y');
		dFechaFin = Date.parseDate(fechaFin, 'd.m.Y');
		var msg = '';
		
		if(codigo.trim()=="")
			msg += '<span class="oblig">C&oacute;digo del derecho</span>';
			
		if(desc.trim()=="")
			msg += '<span class="oblig">Descripci&oacute;n</span>';
		
		if(descDet.trim()=="")
			msg += '<span class="oblig">Descripci&oacute;n detallada</span>';
		
		if(txtLegal.trim()=="")
			msg += '<span class="oblig">Texto Legal</span>';
			
		if(!exclusivoNo && !exclusivoSi)
			msg += '<span class="oblig">Exclusivo</span>';
			
		if(!origenContNo && !origenContSi)
			msg += '<span class="oblig">Origen de Contrataci&oacute;n</span>';	
			
		if(!logicaT && !logicaE)
			msg += '<span class="oblig">Lógica</span>';	
			
		if(!contactadoSi && !contactadoNo)
			msg += '<span class="oblig">Aplica a p&uacute;blico contactado</span>';		
			
		if(fechaInicio.trim()=="")
			msg += '<span class="oblig">Fecha entrada en vigor</span>';
			
		if(nivelAplicacion.trim()=="")
			msg += '<span class="oblig">Nivel de Aplicaci&oacute;n</span>';
		
		if(ambito.trim()=="")
			msg += '<span class="oblig">&Aacute;mbito</span>';		
			
		if(!unidadFija && !unidadMovil && !unidadAmbas)
			msg += '<span class="oblig">Unidad de Aplicaci&oacute;n</span>';	
			
		if(segmentos==0)
			msg += '<span class="oblig">Segmento/subsegmento de aplicaci&oacute;n</span>';
		
		if(mediosCom==0)
			msg += '<span class="oblig">Medios de Comunicaci&oacute;n</span>';
			
		if(tipoObjeto == 0)
			msg += '<span class="oblig">Tipo Objeto</span>';
		
		if(tipoConsen == 0 && logicaT == true)
			msg += '<span class="oblig">Tipo Consentimiento</span>';
			
		//No ha rellenado los campos obligatorios
		if(msg.length>0){
			Ext.Msg.show({
			   title:'Quedan campos obligatorios sin informar',
			   msg: '<span>No se han rellenado todos los campos obligatorios:</span><br/>'+msg,
			   buttons: Ext.Msg.OK,
			   width:400,
			   icon: Ext.MessageBox.ERROR
			});
		}else {
		
			if (fechaFin.length > 0){
				if(esFechaValida(fechaInicio, "Fecha entrada en vigor") && esFechaValida(fechaFin, "Fecha fin de vigencia") ){
					if (dFechaInicio < dFechaFin){
						guardarDatosAlta();
					}else{
						Ext.Msg.show({
						   title:'Fecha incorrecta',
						   msg: '<span>La fecha de fin vigencia debe ser mayor a la fecha de entrada en vigor.</span>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
					}
				}
			}else{
				if(esFechaValida(fechaInicio, "Fecha entrada en vigor")){
					guardarDatosAlta();
				}
			}
		}
	}
	
	var guardarDatosAlta = function() {
		var fechaIncio = Date.parseDate(Ext.get('txtFechaVigor').dom.value,'d.m.Y');
		var fechaFin = Date.parseDate(Ext.get('txtFinVigencia').dom.value,'d.m.Y');
		var segmentos = gridSegmentos.getSelectionModel().getSelections();
		var todosSegmentos = gridSegmentos.store.data.items;
		var mediosCom = gridMediosCom.getSelectionModel().getSelections();
		var todosMedios = gridMediosCom.store.data.items;
		
		var unidadFija = Ext.get('rbFija').dom.checked;
		var unidadMovil = Ext.get('rbMovil').dom.checked;
		var unidadAmbas = Ext.get('rbAmbas').dom.checked;
		var lineaNegocio = null;
		
		if(unidadFija)
			lineaNegocio = '01';
		if(unidadMovil)
			lineaNegocio = '02';	
		if(unidadAmbas)
			lineaNegocio = '00';	
			
		var arraySegmentos = new Array();
		var arrayMedios = new Array();
		
		var introducido = false;
 		Ext.each(todosSegmentos,function(todos){
 			Ext.each(segmentos,function(segmento){
 				if(segmento.data.codigo == todos.data.codigo){
 					arraySegmentos.push({codigo: segmento.data.codigo, descripcion: segmento.data.descripcion, marcado: true});
 					introducido = true;
 				}
 			});
 			if(introducido == false){
 				arraySegmentos.push({codigo: todos.data.codigo, descripcion: todos.data.descripcion, marcado: false});
 			}
 			introducido = false;
 		});
 		
 		introducido = false;
 		Ext.each(todosMedios,function(todos){
 			Ext.each(mediosCom,function(medio){
 				if(medio.data.codigo == todos.data.codigo){
 					arrayMedios.push({codigo: medio.data.codigo, descripcion: medio.data.descripcion, marcado: true});
 					introducido = true;
 				}
 			});
 			if(introducido == false){
 				arrayMedios.push({codigo: todos.data.codigo, descripcion: todos.data.descripcion, marcado: false});
 			}
 			introducido = false;
 		});
		
		var derecho = {
			codigoDerecho:Ext.get('txtCodigo').dom.value,
			descDerechoInterno:Ext.get('txtDescripcion').dom.value,
			descDerechoExterno:Ext.get('txtDescDetallada').dom.value,
			exclusividad:Ext.get('rbExclusivoSi').dom.checked,
			marcaContratoAlta:Ext.get('rbOrigenSi').dom.checked,
			tipoLogica:Ext.get('rbLogicaT').dom.checked,
			diasLiberacion:Ext.get('txtLiberacion').dom.value,
			diasNuevaComunicacion:Ext.get('txtComunicacion').dom.value,
			contactadoTdE:Ext.get('rbContactadoSi').dom.checked,
			fechaInicioVigencia:convertDateToJSONLib(fechaIncio),
			fechaFinVigencia:convertDateToJSONLib(fechaFin),
			codTipoObjeto:Ext.get('selObjeto').dom.value,
			descTipoObjeto:Ext.get('selObjeto').dom.children[Ext.get('selObjeto').dom.selectedIndex].text,
			codNivelAplicacion:Ext.get('selNivel').dom.value,
			descNivelAlpicacion:Ext.get('selNivel').dom.children[Ext.get('selNivel').dom.selectedIndex].text,
			codAmbito:Ext.get('selAmbito').dom.value,
			descAmbito:Ext.get('selAmbito').dom.children[Ext.get('selAmbito').dom.selectedIndex].text,
			grupoSegmentos:arraySegmentos,
			grupoMediosComunicacion:arrayMedios,
			descObservacion:Ext.get('txtObservaciones').dom.value,
			codigoTDE:Ext.get('txtCodigoTde').dom.value,
			codigoTME:Ext.get('txtCodigoTme').dom.value,
			codLineaNegocio:lineaNegocio,
			codigoConsentimiento:Ext.get('selConsen').dom.value
		};
		
		var textoLegal = {
			codigoDerecho:Ext.get('txtCodigo').dom.value,
			textoLegal:Ext.get("txtTextoLegal").dom.value,			
			fecInicioVigencia:convertDateToJSONLib(fechaIncio),
			fecFinVigencia:convertDateToJSONLib(fechaFin)
		};
		
		myMask = new Ext.LoadMask('marco', {msg:"Guardando datos..."});
		myMask.show();
		
    	Ext.Ajax.request({
    		url:contexto + 'DetalleConsentimientosDerechos.do',
    		params:{
    			alta:'',
    			consentimientosDerechosJSON:Ext.encode(derecho),
    			textoLegalJSON:Ext.encode(textoLegal)
    		},
    		callback:function (options, success, response){
    			myMask.hide();
    			if (success){
    				var respuesta = Ext.util.JSON.decode (response.responseText);
    				if (respuesta.success){
    					if (respuesta.message.length>0){
	    					Ext.each(respuesta.message,function(message){
		    					Ext.Msg.show({
								   title:'Error',
								   cls:'cgMsgBox',
								   msg: '<span>'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.ERROR
								});
		    				});
	    				}
	    				
	    				Ext.Msg.show({
						   title:'Alta',
						   msg: '<span>Operaci&oacute;n realizada correctamente.</span><br />¿Desea realizar un nuevo alta?',
						   buttons: Ext.Msg.YESNO,
						   icon: Ext.MessageBox.INFO,
						   fn:function(respuesta){
						   		if (respuesta == 'yes'){
						   			limpiarValores();
						   		}else{
						   			//Volvemos al listado de consentimiento derecho
			   						document.location.href=contexto+'vConsentimientosDerechos.do';
						   		}
						   }
						});		
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
	var comprobarValores = function() {
	// TODO comprobar si es necesario meter aqui el combo consentimiento
		var codigo = Ext.get('txtCodigo').dom.value;
		var desc = Ext.get('txtDescripcion').dom.value;
		var descDet = Ext.get('txtDescDetallada').dom.value;
		var txtLegal = Ext.get('txtTextoLegal').dom.value;
		var exclusivoNo = Ext.get('rbExclusivoNo').dom.checked;
		var origenContSi = Ext.get('rbOrigenSi').dom.checked;
		var origenContNo = Ext.get('rbOrigenNo').dom.checked;
		var logicaT = Ext.get('rbLogicaT').dom.checked;
		var logicaE = Ext.get('rbLogicaE').dom.checked;
		var contactadoSi = Ext.get('rbContactadoSi').dom.checked;
		var fechaInicio = Ext.get('txtFechaVigor').dom.value;
		var fechaFin = Ext.get('txtFinVigencia').dom.value;
		var com = Ext.get('txtComunicacion').dom.value;
		var ambito = Ext.get('selAmbito').dom.value;
		var tipoConsen = Ext.get('selConsen').dom.value;
		var tde = Ext.get('txtCodigoTde').dom.value;
		var tme = Ext.get('txtCodigoTme').dom.value;
		var unidadFija = Ext.get('rbFija').dom.checked;
		var unidadMovil = Ext.get('rbMovil').dom.checked;
		var unidadAmbas = Ext.get('rbAmbas').dom.checked;
		var observaciones = Ext.get('txtObservaciones').dom.value;
		var segmentos = gridSegmentos.getSelectionModel().getSelections().length;
		var mediosCom = gridMediosCom.getSelectionModel().getSelections().length;
		
		if(codigo!="" || desc!="" || descDet!="" || txtLegal!="" || exclusivoNo || origenContSi || origenContNo ||
			logicaT || logicaE || contactadoSi || fechaInicio!="" || fechaFin!="" || com!="" || 
			ambito!="02" || tipoConsen!="" || unidadFija || unidadMovil || unidadAmbas || observaciones!="" || 
			segmentos>0 || mediosCom>0){
			
			//Hay valores introducidos
			return true;
		}else {
			return false;
		}
	}
	
	var limpiar = function() {
		
		if (comprobarValores()){
			Ext.Msg.show({
			   title:'Limpiar datos',
			   msg: '<span>Se van a perder los datos introducidos, ¿Desea continuar?</span>',
			   buttons: Ext.Msg.YESNO,
			   icon: Ext.MessageBox.WARNING,
			   fn:function(respuesta){
			   		if (respuesta == 'yes'){
			   			limpiarValores();
			   		}
			   }
			});
		}
	}
	
	var cerrar = function() {
		
		if (comprobarValores()){
			Ext.Msg.show({
			   title:'Cerrar',
			   msg: '<span>Se van a perder los datos introducidos, ¿Desea continuar?</span>',
			   buttons: Ext.Msg.YESNO,
			   icon: Ext.MessageBox.WARNING,
			   fn:function(respuesta){
			   		if (respuesta == 'yes'){
			   			//Volvemos al listado de consentimiento derecho
			   			document.location.href=contexto+'vConsentimientosDerechos.do';
			   		}
			   }
			});
		}else{
			//Volvemos al listado de consentimiento derecho
			document.location.href=contexto+'vConsentimientosDerechos.do';
		}
		
	}
	
	var limpiarValores = function() {
		Ext.get('txtCodigo').dom.value = '';
		Ext.get('txtDescripcion').dom.value = '';
		Ext.get('txtDescDetallada').dom.value = '';
		Ext.get('txtTextoLegal').dom.value = '';
		Ext.get('rbExclusivoSi').dom.checked = true;
		Ext.get('rbOrigenSi').dom.checked = false;
		Ext.get('rbOrigenNo').dom.checked = false;
		Ext.get('rbLogicaT').dom.checked = false;
		Ext.get('rbLogicaE').dom.checked = false;
		Ext.get('rbContactadoNo').dom.checked = true;
		Ext.get('txtFechaVigor').dom.value = '';
		Ext.get('txtFinVigencia').dom.value = '';
		Ext.get('txtComunicacion').dom.value = '';
		Ext.get('selAmbito').dom.selectedIndex = indiceAmbito;
		Ext.get('selConsen').dom.selectedIndex = 0;
		Ext.get('txtCodigoTde').dom.value = '';
		Ext.get('txtCodigoTme').dom.value = '';
		Ext.get('rbFija').dom.checked = false;
		Ext.get('rbMovil').dom.checked = false;
		Ext.get('rbAmbas').dom.checked = false;
		Ext.get('txtObservaciones').dom.value = '';
		gridSegmentos.getSelectionModel().clearSelections();
		gridMediosCom.getSelectionModel().clearSelections();
		Ext.get('txtLiberacion').addClass('dis');
		Ext.get('txtLiberacion').dom.value='';
		Ext.get('txtLiberacion').dom.readOnly = true;
			
		mostrarNivelAplicacionExclusividad();
		
	}
	
    return {

		init: function() {
			Ext.get('idSubTitulo').update('Alta de Derecho/Consentimiento');
			
			myMask = new Ext.LoadMask(Ext.getBody(), {msg:"Cargando datos..."});
			myMask.show();
			
			crearGridSegmentos();
			iniciarValoresDefecto();
			resaltarCamposObligatorios();
			iniciarCalendarios ();
			controlEventos();
		}	
	
	}
	 
}();

Ext.onReady(CGLOBAL.init, CGLOBAL);