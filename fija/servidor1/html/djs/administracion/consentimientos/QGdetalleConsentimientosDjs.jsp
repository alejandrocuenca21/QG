<%@ page language="java" contentType="text/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
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
	    	reader: new Ext.data.ObjectReader({id:'codigo'}, [
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
			cls:'gridConsentimiento',
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
	    	reader: new Ext.data.ObjectReader({id:'codigo'}, [
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
			cls:'gridConsentimiento',
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
	
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
	var iniciarCalendarios = function (){
		
		Calendar.setup({
			inputField: "txtFechaVigor",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaInicio",
			min:fxIniMax,
			max:fxFinMax,
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
			min:fxIniMax,
			max:fxFinMax,
			bottomBar: false,
			align:'Tl///T/r',
			onSelect: function() {
				this.hide();
			}
		});
	}
	</sec:authorize>
	
	var cargarDatos = function (){
		var derechos = {
			codigoDerecho:Ext.get('codDerecho').dom.value
		};
		Ext.Ajax.request({
    		url:contexto + 'DetalleConsentimientosDerechos.do',
    		params:{
    			cargarDatosConsentimientos:'',
    			consentimientosDerechosJSON:Ext.encode(derechos)
    		},
    		callback:function (options, success, response){
    			if (success){
    				datosResultado = Ext.util.JSON.decode (response.responseText);
    				if (datosResultado.success){
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
													                  
                        //CARGAR COMBO DE TIPO CONSENTIMIENTOS
                        //Obtengo el indice en el que esta el codigo que tiene que ir seleccionado por defecto
                        var arrayCombo = new Array();
                        arrayCombo.push({texto: '', valor: ''});
                        Ext.each(datosResultado.tiposConsentimiento.listaDatos,function(dato){
                            arrayCombo.push({texto: dato.descConsentimiento, valor: dato.codigoConsentimiento});
                        });
                        agregarValoresCombo('selConsen', arrayCombo, true);
             
							
	    				//CARGAR COMBO DE NIVEL DE APLICACION
						//Ejecuta la funcion que muestra y oculta los Niveles de aplicacion segun Exclusividad
						mostrarNivelAplicacionExclusividad();
						
	    				//CARGAR GRID SEGMENTOS
	    				gridSegmentos.getStore().loadData (datosResultado.segmentos.listaDatos);
	    				
	    				//CARGAR GRID MEDIOS COMUNICACION
	    				gridMediosCom.getStore().loadData (datosResultado.medios.listaDatos);
	    				
	    				//Cargamos los datos del consentimiento para su modificacion
	    				cargaDatosModificar();
	    				
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
	
	//Funcion que carga los datos obtenidos por el servicio para su modificacion
	var cargaDatosModificar = function (){
		var derechos = {
			codigoDerecho:Ext.get('codDerecho').dom.value
		};	
		
		Ext.Ajax.request({
    		url:contexto + 'DetalleConsentimientosDerechos.do',
    		params:{
    			obtenerDatosDetalle:'',
    			consentimientosDerechosJSON:Ext.encode(derechos)
    		},
    		callback:function (options, success, response){
    			myMask.hide();
    			if (success){
    				obtenerDatos = Ext.util.JSON.decode (response.responseText);
    				
    				if (obtenerDatos.success){
    					//Pueden llegar mensajes informativos.
    					if (obtenerDatos.message && obtenerDatos.message.length>0){
	    					Ext.each(obtenerDatos.message,function(message){
		    					Ext.Msg.show({
								   title:'Información',
								   cls:'cgMsgBox',
								   msg: '<span>'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
		    				});
	    				}
    					
    					cargarDatosRecibidosMod();
    				}else{
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span>'+obtenerDatos.message+'</span><br/>',
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
	
	var cargarDatosRecibidosMod = function (){
		var i=0;
		
		var codigo = Ext.get('txtCodigo');
		var desc = Ext.get('txtDescripcion');
		var descDet = Ext.get('txtDescDetallada');
		var txtLegal = Ext.get('txtTextoLegal');
		var exclusivoNo = Ext.get('rbExclusivoNo');
		var exclusivoSi = Ext.get('rbExclusivoSi');
		var origenContSi = Ext.get('rbOrigenSi');
		var origenContNo = Ext.get('rbOrigenNo');
		var logicaT = Ext.get('rbLogicaT');
		var logicaE = Ext.get('rbLogicaE');
		var contactadoSi = Ext.get('rbContactadoSi');
		var contactadoNo = Ext.get('rbContactadoNo');
		var fechaInicio = Ext.get('txtFechaVigor');
		var fechaFin = Ext.get('txtFinVigencia');
		var tipoObjeto = Ext.get('selObjeto');
		var nivelAplicacion = Ext.get('selNivel');
		var com = Ext.get('txtComunicacion');
		var tde = Ext.get('txtCodigoTde');
		var tme = Ext.get('txtCodigoTme');
		var unidadFija = Ext.get('rbFija');
		var unidadMovil = Ext.get('rbMovil');
		var unidadAmbas = Ext.get('rbAmbas');
		var observaciones = Ext.get('txtObservaciones');
		var liberacion = Ext.get('txtLiberacion');
		
		//Cargamos los datos recibidos en el formulario
		codigo.dom.value = Texto_HTML(obtenerDatos.datos[0].codigoDerecho);
		desc.dom.value = Texto_HTML(obtenerDatos.datos[0].descDerechoInterno);
		descDet.dom.value = Texto_HTML(obtenerDatos.datos[0].descDerechoExterno);
		txtLegal.dom.value = Texto_HTML(obtenerDatos.datos[1].listaDatos[0].textoLegal);
		
		if (obtenerDatos.datos[0].exclusividad){
			exclusivoSi.dom.checked = true;
		}else{
			exclusivoNo.dom.checked = true;
		}
		
		mostrarNivelAplicacionExclusividad();
		
		if (obtenerDatos.datos[0].marcaContratoAlta){
			origenContSi.dom.checked = true;
		}else{
			origenContNo.dom.checked = true;
		}
		
		if (obtenerDatos.datos[0].tipoLogica){
			logicaT.dom.checked = true;
			liberacion.dom.readOnly = false;
			liberacion.removeClass('dis');
			liberacion.dom.value = obtenerDatos.datos[0].diasLiberacion;
		}else{
			logicaE.dom.checked = true;
			liberacion.dom.readOnly = true;
			liberacion.addClass('dis');
			liberacion.dom.value = '';
		}
		
		if (obtenerDatos.datos[0].contactadoTdE){
			contactadoSi.dom.checked = true;
		}else{
			contactadoNo.dom.checked = true;
		}
		
		var fintermedia = convertDateFromJSONLib (obtenerDatos.datos[0].fechaInicioVigencia);
		fechaInicio.dom.value = modificarFechaAnio(fintermedia);
		
		var fintermedia = convertDateFromJSONLib (obtenerDatos.datos[0].fechaFinVigencia);
		fechaFin.dom.value = modificarFechaAnio(fintermedia);
		
		Ext.each(Ext.get('selObjeto').dom.options,function(dato){    					
			if (dato.value == obtenerDatos.datos[0].codTipoObjeto){
				Ext.get('selObjeto').dom.selectedIndex = i;
			}
			i++;
		});
		
		i = 0;
		Ext.each(Ext.get('selNivel').dom.options,function(dato){
			if (dato.value == obtenerDatos.datos[0].codNivelAplicacion){
				Ext.get('selNivel').dom.selectedIndex = i;
			}
			i++;
		});
		
		i = 0;
		Ext.each(Ext.get('selAmbito').dom.options,function(dato){    					
			if (dato.value == obtenerDatos.datos[0].codAmbito){
				Ext.get('selAmbito').dom.selectedIndex = i;
			}
			i++;
		});
		
		i = 0;
        Ext.each(Ext.get('selConsen').dom.options,function(dato){                       
            if (dato.value == obtenerDatos.datos[0].codigoTipoConsentimiento){
                Ext.get('selConsen').dom.selectedIndex = i;
            }
            i++;
        });
		
		com.dom.value = obtenerDatos.datos[0].diasNuevaComunicacion;
		
		tde.dom.value = Texto_HTML(obtenerDatos.datos[0].codigoTDE);
		tme.dom.value = Texto_HTML(obtenerDatos.datos[0].codigoTME);
		
		if(obtenerDatos.datos[0].codLineaNegocio == "01"){
			unidadFija.dom.checked = true;
		}else if (obtenerDatos.datos[0].codLineaNegocio == "02"){
			unidadMovil.dom.checked = true;
		}else if(obtenerDatos.datos[0].codLineaNegocio == "00"){
			unidadAmbas.dom.checked = true;
		}
		
		var arraySegmentos = [];
		Ext.each(obtenerDatos.datos[0].grupoSegmentos,function(dato){
			    					
			var segRecord = gridSegmentos.getStore().getById(dato.codigo);
			if(dato.marcado)
				arraySegmentos.push(segRecord);
		});
		
		var arrayMedios = [];
		Ext.each(obtenerDatos.datos[0].grupoMediosComunicacion,function(dato){
			    					
			var medRecord = gridMediosCom.getStore().getById(dato.codigo);
			if(dato.marcado)
				arrayMedios.push(medRecord);
		});
		
		gridSegmentos.getSelectionModel().selectRecords(arraySegmentos);
		gridMediosCom.getSelectionModel().selectRecords(arrayMedios);
		
		
		observaciones.dom.value = Texto_HTML(obtenerDatos.datos[0].descObservacion);
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
		comprobarCDEjercido(modificarFechaAnio(fintermedia));
		</sec:authorize>
		
		<sec:authorize ifAnyGranted="ROLE_AU,ROLE_AS,ROLE_AM">
		modoSoloLectura();
		</sec:authorize>
			
		myMask.hide();
	}
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
	var comprobarCDEjercido = function(fechaFin){
		
		fechaHoy = obtenerFecha();
		
		dFechaHoy = Date.parseDate(fechaHoy, 'd.m.Y');;
		dFechaFin = Date.parseDate(fechaFin, 'd.m.Y');
		
		if (dFechaHoy > dFechaFin){
			modoSoloLectura();
		}
	}
	
	var obtenerFecha = function() {
		var ahora = new Date();
		
		var diaSemana = ahora.getDate();
		
		if (diaSemana < 10){
			diaSemana = '0' + diaSemana;
		}
		
		var mes = ahora.getMonth() + 1;
		
		if (mes < 10){
			mes = '0' + mes;
		}
		
		return diaSemana + '.' + mes + '.' + ahora.getFullYear();
	}
	</sec:authorize>
	var modoSoloLectura = function() {
		var desc = Ext.get('txtDescripcion');
		var descDet = Ext.get('txtDescDetallada');
		var txtLegal = Ext.get('txtTextoLegal');
		var exclusivoNo = Ext.get('rbExclusivoNo');
		var exclusivoSi = Ext.get('rbExclusivoSi');
		var origenContSi = Ext.get('rbOrigenSi');
		var origenContNo = Ext.get('rbOrigenNo');
		var logicaT = Ext.get('rbLogicaT');
		var logicaE = Ext.get('rbLogicaE');
		var contactadoSi = Ext.get('rbContactadoSi');
		var contactadoNo = Ext.get('rbContactadoNo');
		var fechaInicio = Ext.get('txtFechaVigor');
		var fechaFin = Ext.get('txtFinVigencia');
		var ambito = Ext.get('selAmbito');
		var tipoObjeto = Ext.get('selObjeto');
		var nivelAplicacion = Ext.get('selNivel');
        var tipoConsen = Ext.get('selConsen');
		var com = Ext.get('txtComunicacion');
		var tde = Ext.get('txtCodigoTde');
		var tme = Ext.get('txtCodigoTme');
		var unidadFija = Ext.get('rbFija');
		var unidadMovil = Ext.get('rbMovil');
		var unidadAmbas = Ext.get('rbAmbas');
		var observaciones = Ext.get('txtObservaciones');
		var liberacion = Ext.get('txtLiberacion');
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
		Ext.get('btnGuardar').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('btnEliminar').setVisibilityMode(Ext.Element.DISPLAY).hide();
		</sec:authorize>
		
		desc.dom.readOnly = true;
		desc.addClass('dis');
		
		descDet.dom.readOnly = true;
		descDet.addClass('dis');
		
		txtLegal.dom.readOnly = true;
		txtLegal.addClass('dis');
		
		exclusivoNo.dom.disabled = true;
		exclusivoSi.dom.disabled = true;
		
		nivelAplicacion.dom.disabled = true;
		
		tipoConsen.dom.disabled = true;
				
		origenContSi.dom.disabled = true;
		origenContNo.dom.disabled = true;
		
		logicaT.dom.disabled = true;
		logicaE.dom.disabled = true;
		
		liberacion.dom.readOnly = true;
		liberacion.addClass('dis');
		
		com.dom.readOnly = true;
		com.addClass('dis');
		
		contactadoSi.dom.disabled = true;
		contactadoNo.dom.disabled = true;
		
		fechaInicio.dom.readOnly = true;
		Ext.get('inputCalOblig').addClass('calendarDis');
		Ext.get('btnCalendarFechaInicio').dom.disabled = true;
		
		fechaFin.dom.readOnly = true;
		Ext.get('divCalFechaFin').addClass('calendarDis');
		Ext.get('btnCalendarFechaFin').dom.disabled = true;
		
		tipoObjeto.dom.disabled = true;
		
		ambito.dom.disabled = true;
		
		tde.dom.readOnly = true;
		tde.addClass('dis');
		
		tme.dom.readOnly = true;
		tme.addClass('dis');
		
		unidadFija.dom.disabled = true;
		unidadMovil.dom.disabled = true;
		unidadAmbas.dom.disabled = true;
		
		observaciones.dom.readOnly = true;
		observaciones.addClass('dis');
		
		inhabilitarGrid('gridSegmentos');
		inhabilitarGrid('gridMediosComunicacion');
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
		//Deshabilitamos el campos codigo
		Ext.get('txtCodigo').dom.readOnly = true;
  		Ext.get('txtCodigo').addClass('dis');
	}
	
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
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
	</sec:authorize>
	
	// Controlamos los eventos sobre los botones
	var controlEventos = function() {
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
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
				restaurarDatos();
		});
		
		// evento click del boton guardar
		Ext.get('btnGuardar').on('click', function() {
				guardarModificacion();
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
		</sec:authorize>
		
		// evento click del boton cerrar
		Ext.get('btnCerrar').on('click', function() {
				cerrar();
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
	
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
	var controlarMaxTxtArea = function(id,tam){
		Ext.get(id).dom.value =Ext.get(id).dom.value.substring(0,tam);
	}
	
	var guardarModificacion = function() {
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
			msg += '<span class="oblig">Código del derecho</span>';
			
		if(desc.trim()=="")
			msg += '<span class="oblig">Descripción</span>';
		
		if(descDet.trim()=="")
			msg += '<span class="oblig">Descripción detallada</span>';
		
		if(txtLegal.trim()=="")
			msg += '<span class="oblig">Texto Legal</span>';
			
		if(!exclusivoNo && !exclusivoSi)
			msg += '<span class="oblig">Exclusivo</span>';
			
		if(!origenContNo && !origenContSi)
			msg += '<span class="oblig">Origen de Contratación</span>';	
			
		if(!logicaT && !logicaE)
			msg += '<span class="oblig">Lógica</span>';	
			
		if(!contactadoSi && !contactadoNo)
			msg += '<span class="oblig">Aplica a público contactado</span>';		
			
		if(fechaInicio.trim()=="")
			msg += '<span class="oblig">Fecha entrada en vigor</span>';
			
		if(nivelAplicacion.trim()=="")
			msg += '<span class="oblig">Nivel de Aplicación</span>';
		
		if(ambito.trim()=="")
			msg += '<span class="oblig">Ámbito</span>';		
			
		if(!unidadFija && !unidadMovil && !unidadAmbas)
			msg += '<span class="oblig">Unidad de Aplicación</span>';	
			
		if(segmentos==0)
			msg += '<span class="oblig">Segmento/subsegmento de aplicación</span>';
		
		if(mediosCom==0)
			msg += '<span class="oblig">Medios de Comunicación</span>';
				
		if(tipoObjeto == 0)
			msg += '<span class="oblig">Tipo Objeto</span>';
		
        if(tipoConsen == 0 && logicaT == true)
            msg += '<span class="oblig">Tipo Consentimiento</span>';
		
		//No ha rellenado los campos obligatorios
		if(msg.length>0){
			//No ha rellenado los campos obligatorios
			Ext.Msg.show({
			   title:'Quedan campos obligatorios sin informar',
			   msg: '<span>No se han rellenado todos los campos obligatorios:</span><br/>'+msg,
			   buttons: Ext.Msg.OK,
			   width:400,
			   icon: Ext.MessageBox.ERROR
			});
		}else{
			if (fechaFin.length > 0){
				if(esFechaValida(fechaInicio, "Fecha entrada en vigor") && esFechaValida(fechaFin, "Fecha fin de vigencia") ){
					if (dFechaInicio < dFechaFin){
						if (comprobarValores()){
							guardarDatosMod();
						}else{
							Ext.Msg.show({
							   title:'Modificar datos',
							   msg: '<span>No se ha realizado ninguna modificación.</span>',
							   buttons: Ext.Msg.OK,
							   icon: Ext.MessageBox.ERROR
							});
						}
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
					if (comprobarValores()){
						guardarDatosMod();
					}else{
						Ext.Msg.show({
						   title:'Modificar datos',
						   msg: '<span>No se ha realizado ninguna modificación.</span>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
					}
				}
			}
		}
	}
	
	var guardarDatosMod = function() {
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
			codigoDerecho:HTML_Texto(Ext.get('txtCodigo').dom.value),
			descDerechoInterno:HTML_Texto(Ext.get('txtDescripcion').dom.value),
			descDerechoExterno:HTML_Texto(Ext.get('txtDescDetallada').dom.value),
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
			codigoTDE:HTML_Texto(Ext.get('txtCodigoTde').dom.value),
			codigoTME:HTML_Texto(Ext.get('txtCodigoTme').dom.value),
			descObservacion:HTML_Texto(Ext.get('txtObservaciones').dom.value),
			codLineaNegocio:lineaNegocio,
			fechaAlta:obtenerDatos.datos[0].fechaAlta,
			usuarioAlta:obtenerDatos.datos[0].usuarioAlta,
            codigoConsentimiento:Ext.get('selConsen').dom.value
		};
		var textoLegal = {
			codigoDerecho:HTML_Texto(Ext.get('txtCodigo').dom.value),
			textoLegal:HTML_Texto(Ext.get("txtTextoLegal").dom.value),		
			fecInicioVigencia:convertDateToJSONLib(fechaIncio),
			fecFinVigencia:convertDateToJSONLib(fechaFin)
		};
		
		myMask = new Ext.LoadMask('marco', {msg:"Guardando datos..."});
		myMask.show();
		
    	Ext.Ajax.request({
    		url:contexto + 'DetalleConsentimientosDerechos.do',
    		params:{
    			modificar:'',
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
						   title:'Modificar',
						   msg: '<span>Operación realizada correctamente.</span>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.INFO,
						   fn:function(){
						   		//Volvemos al listado de consentimiento derecho
			   					document.location.href=contexto+'vConsentimientosDerechos.do';
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
	
		var codigo = Ext.get('txtCodigo').dom.value;
		var desc = Ext.get('txtDescripcion').dom.value;
		var descDet = Ext.get('txtDescDetallada').dom.value;
		var txtLegal = Ext.get('txtTextoLegal').dom.value;
		var exclusivoSi = Ext.get('rbExclusivoSi').dom.checked;
		var origenContSi = Ext.get('rbOrigenSi').dom.checked;
		var origenContNo = Ext.get('rbOrigenNo').dom.checked;
		var logicaT = Ext.get('rbLogicaT').dom.checked;
		var logicaE = Ext.get('rbLogicaE').dom.checked;
		var diasLiberacion = Ext.get('txtLiberacion').dom.value;
		var contactadoSi = Ext.get('rbContactadoSi').dom.checked;
		var fechaInicio = Ext.get('txtFechaVigor').dom.value;
		var fechaFin = Ext.get('txtFinVigencia').dom.value;
		var tipoObjeto = Ext.get('selObjeto').dom.value;
		var com = Ext.get('txtComunicacion').dom.value;
		var ambito = Ext.get('selAmbito').dom.value;
		var nivelAplic = Ext.get('selNivel').dom.value;
        var tipoConsen = Ext.get('selConsen').dom.value;
		var tde = Ext.get('txtCodigoTde').dom.value;
		var tme = Ext.get('txtCodigoTme').dom.value;
		var unidadFija = Ext.get('rbFija').dom.checked;
		var unidadMovil = Ext.get('rbMovil').dom.checked;
		var unidadAmbas = Ext.get('rbAmbas').dom.checked;
		var observaciones = Ext.get('txtObservaciones').dom.value;
		var segmentos = gridSegmentos.getSelectionModel().getSelections();
		var mediosCom = gridMediosCom.getSelectionModel().getSelections();
		
		var haySeleccionSegmentos = false;
		var haySeleccionMedios = false;
		
		var isDistinto = false;
		
		var segMarcadosServicio = new Array();
		
		Ext.each(obtenerDatos.datos[0].grupoSegmentos, function(dato) {
			if(dato.marcado){
				segMarcadosServicio.push(dato);
			}
		});
		
		if (segMarcadosServicio.length == segmentos.length){
			Ext.each(segmentos, function(segmento) {
				var existe = false;
				
				Ext.each(segMarcadosServicio, function(dato) {
					if(segmento.data.codigo == dato.codigo){
						existe = true;	
						return false;
					}
				});
				if (!existe) {
					haySeleccionSegmentos = true;
					return false;
				}
			});
		}else{
			haySeleccionSegmentos = true;
		}
		
		var medMarcadosServicio = new Array();
		
		Ext.each(obtenerDatos.datos[0].grupoMediosComunicacion, function(dato) {
			if(dato.marcado){
				medMarcadosServicio.push(dato);
			}
		});
		
		if (medMarcadosServicio.length == mediosCom.length){
			Ext.each(mediosCom, function(medio) {
				var existe = false;
				
				Ext.each(medMarcadosServicio, function(dato) {
					if(medio.data.codigo == dato.codigo){
						existe = true;
						return false;
					}
				});
				if (!existe) {
					haySeleccionMedios = true;
					return false;
				}
			});
		}else{
			haySeleccionMedios = true;
		}
		
		
		    				
		    				
		var exclusivo;
		if(exclusivoSi){
			exclusivo = true;
		}else{
			exclusivo = false;
		}
		
		var origen;
		if(origenContSi){
			origen = true;
		}else{
			origen = false;
		}
		
		var logica;
		if(logicaT){
			logica = true;
		}else{
			logica = false;
		}
		
		var contactado;
		if(contactadoSi){
			contactado = true;
		}else{
			contactado = false;
		}
		
		var unidadAplic;
		if(unidadFija){
			unidadAplic = "01";
		}else if (unidadFija){
			unidadAplic = "02";
		}else if(unidadAmbas){
			unidadAplic = "00";
		}
		
		
		var fintermedia = convertDateFromJSONLib (obtenerDatos.datos[0].fechaFinVigencia);
 		
 		
 		var inicioIntermedia = convertDateFromJSONLib (obtenerDatos.datos[0].fechaInicioVigencia);
    				
		if(desc!=Texto_HTML(obtenerDatos.datos[0].descDerechoInterno) || 
			descDet!=Texto_HTML(obtenerDatos.datos[0].descDerechoExterno) || Texto_HTML(txtLegal!=datosResultado.textoLegal.listaDatos[0].textoLegal) || 
			obtenerDatos.datos[0].exclusividad != exclusivo || diasLiberacion != obtenerDatos.datos[0].diasLiberacion ||
			obtenerDatos.datos[0].marcaContratoAlta != origen || obtenerDatos.datos[0].tipoLogica != logica ||  
			obtenerDatos.datos[0].contactadoTdE != contactado || fechaInicio!=modificarFechaAnio(inicioIntermedia) || 
			fechaFin!=modificarFechaAnio(fintermedia) || tipoObjeto!=obtenerDatos.datos[0].codTipoObjeto || 
			com!=obtenerDatos.datos[0].diasNuevaComunicacion ||	ambito!=obtenerDatos.datos[0].codAmbito || 
			nivelAplic!= obtenerDatos.datos[0].codNivelAplicacion || tipoConsen!= obtenerDatos.datos[0].codigoTipoConsentimiento ||
			tde!=Texto_HTML(obtenerDatos.datos[0].codigoTDE) ||  tme!=Texto_HTML(obtenerDatos.datos[0].codigoTME) || 
			obtenerDatos.datos[0].codLineaNegocio!=unidadAplic || observaciones!=Texto_HTML(obtenerDatos.datos[0].descObservacion) || 
			haySeleccionSegmentos || haySeleccionMedios){
			
			//Se ha modificado algun valor
			return true;
		}else {
			return false;
		}
	}
	
	var restaurarDatos = function() {
		
		if (comprobarValores()){
			Ext.Msg.show({
			   title:'Limpiar datos',
			   msg: '<span>Se van a perder los datos introducidos, ¿Desea continuar?</span>',
			   buttons: Ext.Msg.YESNO,
			   icon: Ext.MessageBox.WARNING,
			   fn:function(respuesta){
			   		if (respuesta == 'yes'){
			   			cargarDatosRecibidosMod();
			   		}
			   }
			});
		}
	}
	</sec:authorize>
	var cerrar = function() {
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
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
		</sec:authorize>
			//Volvemos al listado de consentimiento derecho
			document.location.href=contexto+'vConsentimientosDerechos.do';
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
		}
		</sec:authorize>
		
	}
	
    return {

		init: function() {
			Ext.get('idSubTitulo').update('Detalle de Derecho');
			
			myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
			myMask.show();
			
			crearGridSegmentos();
			iniciarValoresDefecto();
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
			iniciarCalendarios ();
			Ext.get('btnEliminar').setVisibilityMode(Ext.Element.DISPLAY).hide();
			</sec:authorize>
			controlEventos();
		}	
	
	}
	 
}();

Ext.onReady(CGLOBAL.init, CGLOBAL);
</sec:authorize>