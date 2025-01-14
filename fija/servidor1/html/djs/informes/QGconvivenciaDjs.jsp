<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
Ext.ns("CGCONVIVENCIA");
CGCONVIVENCIA = function(){
	
    //criterios de la ultima busqueda realizada.
	var ultimaBusqueda = new Ext.util.MixedCollection();	
	
	//Funcion para exportar el contenido de las pestanas a excel
	function exportarExcel(){  
		//Solo exportamos si hay resultados
		if(ultimaBusqueda.get('hayResultados')){
			if (Ext.isIE){
				myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
				myMask.show();
			}
			var busqueda = {
				fecha: ultimaBusqueda.get('fxBusqueda'),
				informe: "1"
			};
			document.getElementById('ifExportar').src = contexto + 'Convivencia.do?exportar=&busquedaJSON='+Ext.encode(busqueda);
			if (Ext.isIE)
				document.getElementById('ifExportar').onreadystatechange = function(){myMask.hide();};
		}else{
			Ext.Msg.show({
				title:'Error',
				cls:'cgMsgBox',
				msg: '<span>La b&uacute;squeda no ha devuelto resultados. No se puede generar el informe</span><br/>',
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.ERROR
			});
		}
	}
	
	//Mostrar Elementos Pestana
	var mostrarMovTotales = function (){
	    Ext.get('divMovTotales').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('divMovFija').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divMovMovil').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divMovPrepago').setVisibilityMode(Ext.Element.DISPLAY).hide();		
		Ext.get('divReinyeccion').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divErrores').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		Ext.get('pesMovTotales').addClass('activa');
		Ext.get('pesMovFija').removeClass('activa');
		Ext.get('pesMovMovil').removeClass('activa');
		Ext.get('pesMovPrepago').removeClass('activa');		
		Ext.get('pesReinyeccion').removeClass('activa');
		Ext.get('pesErrores').removeClass('activa');
	}
	
	//Mostrar Elementos Pestana
	var mostrarMovFija = function (){
	    Ext.get('divMovTotales').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divMovFija').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('divMovMovil').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divMovPrepago').setVisibilityMode(Ext.Element.DISPLAY).hide();		
		Ext.get('divReinyeccion').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divErrores').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		Ext.get('pesMovTotales').removeClass('activa');
		Ext.get('pesMovFija').addClass('activa');
		Ext.get('pesMovMovil').removeClass('activa');
		Ext.get('pesMovPrepago').removeClass('activa');		
		Ext.get('pesReinyeccion').removeClass('activa');
		Ext.get('pesErrores').removeClass('activa');	    
	}

	//Mostrar Elementos Pestana
	var mostrarMovMovil = function (){
	    Ext.get('divMovTotales').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divMovFija').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divMovMovil').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('divMovPrepago').setVisibilityMode(Ext.Element.DISPLAY).hide();		
		Ext.get('divReinyeccion').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divErrores').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		Ext.get('pesMovTotales').removeClass('activa');
		Ext.get('pesMovFija').removeClass('activa');
		Ext.get('pesMovMovil').addClass('activa');
		Ext.get('pesMovPrepago').removeClass('activa');		
		Ext.get('pesReinyeccion').removeClass('activa');
		Ext.get('pesErrores').removeClass('activa');	    
	}
	
	//Mostrar Elementos Pestana
	var mostrarMovPrepago = function (){
	    Ext.get('divMovTotales').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divMovFija').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divMovMovil').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divMovPrepago').setVisibilityMode(Ext.Element.DISPLAY).show();		
		Ext.get('divReinyeccion').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divErrores').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		Ext.get('pesMovTotales').removeClass('activa');
		Ext.get('pesMovFija').removeClass('activa');
		Ext.get('pesMovMovil').removeClass('activa');
		Ext.get('pesMovPrepago').addClass('activa');		
		Ext.get('pesReinyeccion').removeClass('activa');
		Ext.get('pesErrores').removeClass('activa');	    
	}			

	//Mostrar Elementos Pestana
	var mostrarReinyeccion = function (){
	    Ext.get('divMovTotales').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divMovFija').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divMovMovil').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divMovPrepago').setVisibilityMode(Ext.Element.DISPLAY).hide();		
		Ext.get('divReinyeccion').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('divErrores').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		Ext.get('pesMovTotales').removeClass('activa');
		Ext.get('pesMovFija').removeClass('activa');
		Ext.get('pesMovMovil').removeClass('activa');
		Ext.get('pesMovPrepago').removeClass('activa');		
		Ext.get('pesReinyeccion').addClass('activa');
		Ext.get('pesErrores').removeClass('activa');	  	    
	}

	//Mostrar Elementos Pestana
	var mostrarErrores = function (){
	    Ext.get('divMovTotales').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divMovFija').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divMovMovil').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divMovPrepago').setVisibilityMode(Ext.Element.DISPLAY).hide();		
		Ext.get('divReinyeccion').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divErrores').setVisibilityMode(Ext.Element.DISPLAY).show();
		
		Ext.get('pesMovTotales').removeClass('activa');
		Ext.get('pesMovFija').removeClass('activa');
		Ext.get('pesMovMovil').removeClass('activa');
		Ext.get('pesMovPrepago').removeClass('activa');		
		Ext.get('pesReinyeccion').removeClass('activa');
		Ext.get('pesErrores').addClass('activa');	  
	}
	
	//Funcion que imprime los datos de la pantalla en pdf
	var imprimirConvivencia= function() {

	
		if(ultimaBusqueda.get('hayResultados')){
			if (Ext.isIE){
				myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
				myMask.show();
			}
			var busqueda = {
				fecha: ultimaBusqueda.get('fxBusqueda'),
				informe: "1"
			};
			document.getElementById('ifExportar').src = contexto + 'Convivencia.do?exportarPDF=&busquedaJSON='+Ext.encode(busqueda);
			if (Ext.isIE)
				document.getElementById('ifExportar').onreadystatechange = function(){myMask.hide();};
		}else{
			Ext.Msg.show({
				title:'Error',
				cls:'cgMsgBox',
				msg: '<span>La b&uacute;squeda no ha devuelto resultados. No se puede generar el informe</span><br/>',
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.ERROR
			});
		}
		 
	}	
	
	
	//Control de eventos
	var controlEventos = function(){
		//evento click del botón Exportar Excel
		Ext.get('btnExportar').on('click', function() {
			exportarExcel();
		});
		
		//evento click del botón Imprimir
		Ext.get('btnImprimir').on('click', function() {
			imprimirConvivencia();
		});
		
		//cambio en pestanas
		Ext.get('pesMovTotales').on('click', function() {
			mostrarMovTotales();
		});
		Ext.get('pesMovFija').on('click', function() {
			mostrarMovFija();
		});
		Ext.get('pesMovMovil').on('click', function() {
			mostrarMovMovil();
		});
		Ext.get('pesMovPrepago').on('click', function() {
			mostrarMovPrepago();
		});			
		Ext.get('pesReinyeccion').on('click', function() {
			mostrarReinyeccion();
		});
		Ext.get('pesErrores').on('click', function() {
			mostrarErrores();
		});	
		
		//Busqueda por fechas
		Ext.get('btnBuscar').on('click', function() {
			if (Ext.get('txtFechaBusqueda').dom.value != ""){
				cargarDatos();
			}
			else{
				mostrarMensajeInfo("No ha introducido a&uacute;n una fecha v&aacute;lida");
			}
		});
	}
	
	//Funciones para la carga de la excel en pantalla
	var callBackCargar = function(correcto,datosResultado){
		if(correcto){
		
  			//Si no llegan resultados mostramos un mensaje y no mostramos el grid
			if(datosResultado.length == 0){
				Ext.Msg.show({
					title:'Informaci&oacute;n',
					cls:'cgMsgBox',
					msg: '<span>No se han encontrado resultados que coincidan con los criterios de b&uacute;squeda introducidos.</span><br/>',
					buttons: Ext.Msg.OK,
					icon: Ext.MessageBox.INFO
				});
				ultimaBusqueda.add('hayResultados',false);
			}else{
				//Si hay datos lo indicamos
				ultimaBusqueda.add('hayResultados',true);
				var numDias = datosResultado[0].numDias--;
				
				//Eliminamos todas las filas de error por si ya se habian generado los listados
				Ext.each(Ext.query('.filaError', 'informeSalida'), function(fileError) {
					Ext.fly(fileError).remove();
				});
				
				//Pinta el listado de Movimientos totales
				pintarListado(datosResultado[0].listTotales, numDias,0, 33, 'tableMovTot', false, null, true, 17, 25);
				
				/*Listados para la pestania Movimientos Fija*/
				//Pinta el listado de movimientos fija 
				pintarListado(datosResultado[0].listFijos, numDias, 0, 4, 'tableMovFija', false, null, false);
				
				//Pinta el listado de Tratados
				pintarListado(datosResultado[0].listFijos, numDias, 4, 12, 'tableMovFijaTrat', false, null, false);
				
				//Pinta el listado de Pendientes
				pintarListado(datosResultado[0].listFijos, numDias, 12, 20, 'tableMovFijaPend', false, null, false);
				
				//Array con los ids de la tabla de errores
				var arrayRowIdErrores = new Array(Ext.id(), Ext.id(), "FEA", "FEB", "FEM", "FEC", "FED", "FEE", "FER");
				//Pinta el listado de  Errores
				pintarListado(datosResultado[0].listFijos, numDias, 20, 28, 'tableMovFijaErrores', true, arrayRowIdErrores, false);
				
				//Array con los ids de la tabla de errores informativos
				var arrayRowIdWar = new Array(Ext.id(), Ext.id(), "FWA", "FWB", "FWM", "FWC", "FWD", "FWE", "FWR");
				//Pinta el listado de errores informavitos
				pintarListado(datosResultado[0].listFijos, numDias, 28, 36, 'tableMovFijaErroresInfo', true, arrayRowIdWar, false);
				
				//Pinta los errores para Movimientos Fija
				pintarErrores(datosResultado[0].erroresFijos, arrayRowIdErrores, numDias);
				//Pinta los errores informativos para Movimientos Fija
				pintarErrores(datosResultado[0].erroresFijos, arrayRowIdWar, numDias);
				
				/*Listados para la pestania Movimientos Movil*/
				//Pinta el listado de Movimientos Movil
				pintarListado(datosResultado[0].listMovil, numDias, 0, 4, 'tableMovMovil', false, null, false);
				//Pinta el listado de Tratados
				pintarListado(datosResultado[0].listMovil, numDias, 4, 18, 'tableMovMovilTrat', false, null, false);
				//Pinta el listado de Pendientes
				pintarListado(datosResultado[0].listMovil, numDias, 18, 32, 'tableMovMovilPend', false, null, false);
				
				//Array con los ids de la tabla de errores
				var arrayRowIdErrores = new Array(Ext.id(), Ext.id(), "MEA", "MEAC", "MEAP", "MEB", "MECC", "MECE", "MEM", "MEMC", "MEME", "MEMI", "MEMP", "MEMS", "MENS");
				//Pinta el listado de Errores
				pintarListado(datosResultado[0].listMovil, numDias, 32, 46, 'tableMovMovilErrores', true, arrayRowIdErrores, false);
				
				//Array con los ids de la tabla de errores informativos
				var arrayRowIdWar = new Array(Ext.id(), Ext.id(), "MWA", "MWAC", "MWAP", "MWB", "MWCC", "MWCE", "MWM", "MWMC", "MWME", "MWMI", "MWMP", "MWMS", "MWNS");
				//Pinta el listado de errores informativos
				pintarListado(datosResultado[0].listMovil, numDias, 46, 60, 'tableMovMovilErroresInfor', true, arrayRowIdWar, null, false);
				
				//Pinta el listado de Errores
				pintarErrores(datosResultado[0].erroresMovil, arrayRowIdErrores, numDias);
				//Pinta el listado de Errores informativos
				pintarErrores(datosResultado[0].erroresMovil, arrayRowIdWar, numDias);
				
				/*Listados para la pestania Movimientos Prepago*/
				//Pinta el listado de Movimientos Prepago
				pintarListado(datosResultado[0].listPrepago, numDias, 0, 4, 'tableMovPrepago', false, null, false);
				//Pinta el listado de Tratados
				pintarListado(datosResultado[0].listPrepago, numDias, 4, 8, 'tableMovPrepagoTrat', false, null, false);
				//Pinta el listado de Pendientes
				pintarListado(datosResultado[0].listPrepago, numDias, 8, 12, 'tableMovPrepagoPend', false, null, false);
				
				//Array con los ids de la tabla de errores
				var arrayRowIdErrores = new Array(Ext.id(), Ext.id(), "PEAG", "PEBG", "PEMG");
				//Pinta el listado de Errores
				pintarListado(datosResultado[0].listPrepago, numDias, 12, 16, 'tableMovPrepagoErrores', true, arrayRowIdErrores, false);
				
				//Array con los ids de la tabla de errores informativos
				var arrayRowIdWar = new Array(Ext.id(), Ext.id(), "PWAG", "PWBG", "PWMG");
				//Pinta el listado de errores informativos
				pintarListado(datosResultado[0].listPrepago, numDias, 16, 20, 'tableMovPrepagoErroresInfor', true, arrayRowIdWar, null, false);
				
				//Pinta el listado de Errores
				pintarErrores(datosResultado[0].erroresPrepago, arrayRowIdErrores, numDias);
				//Pinta el listado de Errores informativos
				pintarErrores(datosResultado[0].erroresPrepago, arrayRowIdWar, numDias);								
				
				/*Listados para la pestania Reinyeccion*/
				/*
					//MOVIMIENTOS FIJA
				*/
				//Pinta el listado de Movimientos fija
				pintarListadoReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion.reinyeccionFija,
					0, 4,
					'tableReinyecMovFija'
				);
				
				//Pinta el listado de Tratados fija
				pintarListadoReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion.reinyeccionFija,
					4, 8,
					'tableReinyecTratFija'
				);
				
				//Pinta el listado de Pendientes fija
				pintarListadoReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion.reinyeccionFija,
					8, 12,
					'tableReinyecPendFija'
				);
				
				//Pinta el listado de Errores fija
				var arrayRowIdErroresFija = new Array(Ext.id(), Ext.id(), "FREA", "FREM", "FREB");
				pintarListadoReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion.reinyeccionFija,
					12, 16,
					'tableReinyecErrorFija',
					true,
					arrayRowIdErroresFija
				);
				
				//Pinta el listado de Errores Informativos fija
				var arrayRowIdInfoFija = new Array(Ext.id(), Ext.id(), "FRWA", "FRWM", "FRWB");
				pintarListadoReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion.reinyeccionFija,
					16, 20,
					'tableReinyecInfoFija',
					true,
					arrayRowIdInfoFija
				);
				
				/*
					//MOVIMIENTOS MOVIL
				*/
				//Pinta el listado de Movimientos Movil
				pintarListadoReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion.reinyeccionMovil,
					0, 4,
					'tableReinyecMovMovil'
				);
				
				//Pinta el listado de Tratados Movil
				pintarListadoReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion.reinyeccionMovil,
					4, 8,
					'tableReinyecTratMovil'
				);
				
				//Pinta el listado de Pendientes Movil
				pintarListadoReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion.reinyeccionMovil,
					8, 12,
					'tableReinyecPendMovil'
				);
				
				//Pinta el listado de Errores Movil
				var arrayRowIdErroresMovil = new Array(Ext.id(), Ext.id(), "MREA", "MREM", "MREB");
				pintarListadoReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion.reinyeccionMovil,
					12, 16,
					'tableReinyecErrorMovil',
					true,
					arrayRowIdErroresMovil
				);
				
				//Pinta el listado de Errores Informativos Movil
				var arrayRowIdInfoMovil = new Array(Ext.id(), Ext.id(), "MRWA", "MRWM", "MRWB");
				pintarListadoReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion.reinyeccionMovil,
					16, 20,
					'tableReinyecInfoMovil',
					true,
					arrayRowIdInfoMovil
				);
				
				/*
					//MOVIMIENTOS PREPAGO
				*/
				//Pinta el listado de Movimientos Prepago
				pintarListadoReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion.reinyeccionPrepago,
					0, 4,
					'tableReinyecMovPrepago'
				);
				
				//Pinta el listado de Tratados Prepago
				pintarListadoReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion.reinyeccionPrepago,
					4, 8,
					'tableReinyecTratPrepago'
				);
				
				//Pinta el listado de Pendientes Movil
				pintarListadoReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion.reinyeccionPrepago,
					8, 12,
					'tableReinyecPendPrepago'
				);
				
				//Pinta el listado de Errores Prepago
				var arrayRowIdErroresPrepago = new Array(Ext.id(), Ext.id(), "PREA", "PREM", "PREB");
				pintarListadoReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion.reinyeccionPrepago,
					12, 16,
					'tableReinyecErrorPrepago',
					true,
					arrayRowIdErroresPrepago
				);
				
				//Pinta el listado de Errores Informativos Prepago
				var arrayRowIdInfoPrepago = new Array(Ext.id(), Ext.id(), "PRWA", "PRWM", "PRWB");
				pintarListadoReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion.reinyeccionPrepago,
					16, 20,
					'tableReinyecInfoPrepago',
					true,
					arrayRowIdInfoPrepago
				);				
				
				/*
					//MOVIMIENTOS TOTALES
				*/
				//Pinta el listado de Movimientos Totales
				pintarListadoTotalesReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion,
					0, 4,
					'tableReinyecMovTot'
				);
				
				//Pinta el listado de Tratados Totales
				pintarListadoTotalesReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion,
					4, 8,
					'tableReinyecTratTot'
				);
				
				//Pinta el listado de Pendientes Totales
				pintarListadoTotalesReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion,
					8, 12,
					'tableReinyecPendTot'
				);
				
				//Pinta el listado de Errores Totales
				var arrayRowIdErroresTotales = new Array(Ext.id(), Ext.id(), "TREA", "TREM", "TREB");
				pintarListadoTotalesReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion,
					12, 16,
					'tableReinyecErrorTot',
					true,
					arrayRowIdErroresTotales
				);
				
				//Pinta el listado de Errores Informativos Totales
				var arrayRowIdInfoTotales = new Array(Ext.id(), Ext.id(), "TRWA", "TRWM", "TRWB");
				pintarListadoTotalesReinyeccion(
					datosResultado[0].fechaReinyeccion,
					datosResultado[0].reinyeccion,
					16, 20,
					'tableReinyecInfoTot',
					true,
					arrayRowIdInfoTotales
				);
				
				//Pintar errores en reinyeccion
				var arrayRowIdErrores = new Array(Ext.id(), Ext.id(), "REA", "REM", "REB", "RWA", "RWM", "RWB");
				pintarErroresReinyeccion(datosResultado[0].erroresReinyeccion, arrayRowIdErrores);				
				
				mostrarMovTotales();
				
				if (datosResultado[0].fechaReinyeccion != ""){
					mostrar(true);
				}
				else{
					mostrar(false);
				}
				
			}
		}
		else{
			ultimaBusqueda.add('hayResultados',false);
			ocultar();
		}
	}
	
	/*Funcion generica para pintar los listados
		Parametros:
			listado: Array con todos los elementos de la columna
			numDias: numero de dias (columnas) a mostrar
			filInicio: inicio donde empezar a coger datos en el array de 'listado'
			filFin: fin donde terminar de coger datos en el array de 'listado'
			divRender: div donde renderizar el listado generado
			rowId: true/false si va a llevar id la fila
			arrayRowId: Array con los id's que se le van a poner a cada fila
			rowSpan: true o false segun haya alguna columna que tenga rowSpan o no
			cellRowSpan1: fila1 en la que se hara en rowSpan
			cellRowSpan2: fila2 en la que se hara en rowSpan
	*/
	var pintarListado = function(listado, numDias, filInicio, filFin, divRender, rowId, arrayRowId, rowSpan, cellRowSpan1, cellRowSpan2){
		var table = '';

		table += '<table class="right_table"><tbody>'; 
		
		//Construimos la cabecera de la tabla segun el numero de fechas que vengan del servicio
		table += '<tr>';
		for(var i = 0; i < numDias; i++) {
			table += '<td class="colCabecera">'+Date.parseDate( listado.fechasInforme[i],'Ymd').format('d/m/Y') +'</td>';
			table += '<td class="colCabecera">%</td>';
		}
		table += '<td class="colCabecera" colspan="2">Total (' + 
									Date.parseDate( listado.fechasInforme[0],'Ymd').format('d/m/Y') +
									' al ' +
									Date.parseDate( listado.fechasInforme[i-1],'Ymd').format('d/m/Y') +
						')</td>';
		table += '</tr>';
		
		
		//Construimos las columnas con los datos que nos llegan del servicio
		var count_id = 0;
		
		for(var i = filInicio; i < filFin; i++) {
			if(rowId){
				table += '<tr id="' + arrayRowId[++count_id] + '">';
			}else{
				table += '<tr>';
			}
			
			for(var j = 0; j < numDias; j++) {
				
				table += '<td>'+ formato_numero(listado.listaMovimientos[j].listaValoresMostrar[i],0,",",".") +'</td>';
				
				if(rowSpan && (i == cellRowSpan1 || i == cellRowSpan2)){
					table += '<td class="colAtl" rowspan="2">'+ formato_numero(listado.listaMovimientos[j].listaValoresPorCiento[i],3,",",".") +'%</td>';
				}else{
					if(rowSpan && i != (cellRowSpan1+1) && i != (cellRowSpan2+1)){
						table += '<td class="colAtl">'+ formato_numero(listado.listaMovimientos[j].listaValoresPorCiento[i],3,",",".") +'%</td>';
					}
					
					if(!rowSpan){
						table += '<td class="colAtl">'+ formato_numero(listado.listaMovimientos[j].listaValoresPorCiento[i],3,",",".") +'%</td>';
					}
				}
					
			}
			
			table += '<td>'+ formato_numero(listado.movimientoTotal.listaValoresMostrar[i],0,",",".") +'</td>';
			
			if (rowSpan && (i == cellRowSpan1 || i == cellRowSpan2)){
				table += '<td class="colAtl" rowspan="2">'+ formato_numero(listado.movimientoTotal.listaValoresPorCiento[i],3,",",".") +'%</td>';
			}else{
				if(rowSpan && i != (cellRowSpan1+1) && i != (cellRowSpan2+1)){
					table += '<td class="colAtl">'+ formato_numero(listado.movimientoTotal.listaValoresPorCiento[i],3,",",".") +'%</td>';
				}
				
				if(!rowSpan){
					table += '<td class="colAtl">'+ formato_numero(listado.movimientoTotal.listaValoresPorCiento[i],3,",",".") +'%</td>';
				}
			}
			
			table += '</tr>';
		}		
		
		table += '</tbody></table>';
		
		if (numDias == 1){
			Ext.get(divRender).applyStyles('overflow-x:hidden;');
		}
		else{
			Ext.get(divRender).applyStyles('overflow-x:scroll;');
		}
		
		Ext.get(divRender).update(table);
	}
	
	var pintarErrores = function(listado, arrayRowId, numDias){
		var stringTD = '';
		var stringTR = '';
		var totalCeldas = 0;
		var colAtl = '';
		totalCeldas = (numDias * 2);
		var simbolPer = '';
		var decimales = 0;
		
		for (var i = 0; i< arrayRowId.length; i++){
			for (var j = 0; j< listado.length; j++){
				if(arrayRowId[i] == listado[j].idTable){
					 Ext.get('L'+arrayRowId[i]).insertHtml('afterEnd','<tr class="filaError"><td class="tab30 tdError">'+listado[j].columnas[0]+'</td></tr>');
					 for(var k = 1; k<= totalCeldas; k++){
					 	if(k % 2 == 0){
					 		colAtl = 'class="colAtl"';
					 		simbolPer = '%';
					 		decimales = 3;
					 		
					 	}
					 		
					 	stringTD += '<td ' + colAtl + '>'+formato_numero(listado[j].columnas[k],decimales,",",".")+ simbolPer + '</td>';
					 	
					 	colAtl = '';
					 	simbolPer = '';
					 	decimales = 0;
					 }
					 //Columna Totales
					 stringTD += '<td>'+formato_numero(listado[j].columnas[15],decimales,",",".")+ simbolPer + '</td>';
					 stringTD += '<td  class="colAtl">'+formato_numero(listado[j].columnas[16],decimales,",",".")+ '%' + '</td>';						 
					 
					 stringTR = '<tr>'+stringTD+'</tr>'
					 Ext.get(arrayRowId[i]).insertHtml('afterEnd',stringTR);
					 stringTD = '';
				}
			}
		}
	}
	
	/*Funcion generica para pintar los listados de Reinyeccion
		Parametros:
			listado: Array con todos los elementos de la columna
			fechaReinyeccion: fecha para la cabecera de las tablas
			filInicio: inicio donde empezar a coger datos en el array de 'listado'
			filFin: fin donde terminar de coger datos en el array de 'listado'
			divRender: div donde renderizar el listado generado
			rowId: true/false si va a llevar id la fila
			arrayRowId: Array con los ids para las filas donde iran los errores
	*/
	var pintarListadoReinyeccion = function(fechaReinyeccion, listado, filInicio, filFin, divRender, rowId, arrayRowId){
		var table = '';
		table += '<table class="right_table"><tbody>'; 
		
		//Construimos la cabecera de la tabla segun el numero de fechas que vengan del servicio
		table += '<tr>';
		table += '<td class="colCabecera" style="padding: 0 0px !important;text-align: center;">'+ fechaReinyeccion +'</td>';
		table += '<td class="colCabecera" style="padding: 0 0px !important;text-align: center;">%</td>';

		table += '</tr>';
		
		//Construimos las columnas con los datos que nos llegan del servicio
		var count_id = 0;
		
		for(var i = filInicio; i < filFin; i++) {
			if(rowId){
				table += '<tr id="' + arrayRowId[++count_id] + '">';
			}else{
				table += '<tr>';
			}
			
			table += '<td style="padding:0 0">'+ formato_numero(listado.listaValoresMostrar[i],0,",",".") +'</td>';
			table += '<td class="colAtl" style="padding:0 0">'+ formato_numero(listado.listaValoresPorCiento[i],3,",",".") +'%</td>';

			
			table += '</tr>';
		}
		
		table += '</tbody></table>';
		
		Ext.get(divRender).update(table);
	}
	
	/*Funcion generica para pintar los listados de Reinyeccion de SUMAS TOTALES
		Parametros:
			listado: Array con todos los elementos de la columna
			fechaReinyeccion: fecha para la cabecera de las tablas
			filInicio: inicio donde empezar a coger datos en el array de 'listado'
			filFin: fin donde terminar de coger datos en el array de 'listado'
			divRender: div donde renderizar el listado generado
			rowId: true/false si va a llevar id la fila
			arrayRowId: Array con los ids para las filas donde iran los errores
	*/
	var pintarListadoTotalesReinyeccion = function(fechaReinyeccion, listado, filInicio, filFin, divRender, rowId, arrayRowId){
		var table = '';
		table += '<table class="right_table"><tbody>'; 
		
		//Construimos la cabecera de la tabla segun el numero de fechas que vengan del servicio
		table += '<tr>';
		table += '<td class="colCabecera" style="padding: 0 0px !important;text-align: center;">'+ fechaReinyeccion +'</td>';
		table += '<td class="colCabecera" style="padding: 0 0px !important;text-align: center;">%</td>';

		table += '</tr>';
		
		//Construimos las columnas con los datos que nos llegan del servicio
		var count_id = 0;
		
		for(var i = filInicio; i < filFin; i++) {
			if(rowId){
				table += '<tr id="' + arrayRowId[++count_id] + '">';
			}else{
				table += '<tr>';
			}
			
			table += '<td style="padding:0 0">'+ formato_numero(listado.listaValoresMostrarSumas[i],0,",",".") +'</td>';
			table += '<td class="colAtl" style="padding:0 0">'+ formato_numero(listado.listaValoresPorCientoSumas[i],3,",",".") +'%</td>';

			
			table += '</tr>';
		}
		
		table += '</tbody></table>';
		
		Ext.get(divRender).update(table);
	}
	
	var pintarErroresReinyeccion = function(listado, arrayRowId){
		
		for (var i = 0; i< arrayRowId.length; i++){
			for (var j = 0; j< listado.length; j++){
				if(arrayRowId[i] == listado[j].idTable){
				
				
					//Errores para Fija
					//Columna izquierda
					 Ext.get('LF'+arrayRowId[i]).insertHtml(
						'afterEnd',
						'<tr class="filaError">'+
							'<td class="tdError" style="padding:0 0">'+listado[j].columnas[0]+'</td>'+
						'</tr>'
					);
					//Columna derecha
					Ext.get('F'+arrayRowId[i]).insertHtml(
						'afterEnd',
						'<tr class="filaError">'+
							'<td class="tdError" style="padding:0 0">'+formato_numero(listado[j].columnas[1],0,",",".")+'</td>'+
							'<td class="tdError colAtl" style="padding:0 0">'+formato_numero(listado[j].columnas[2],3,",",".")+'</td>'+
						'</tr>'
					);
					
					//Errores para Movil
					//Columna izquierda
					 Ext.get('LM'+arrayRowId[i]).insertHtml(
						'afterEnd',
						'<tr class="filaError">'+
							'<td class="tdError" style="padding:0 0">'+listado[j].columnas[4]+'</td>'+
						'</tr>'
					);
					//Columna derecha
					Ext.get('M'+arrayRowId[i]).insertHtml(
						'afterEnd',
						'<tr class="filaError">'+
							'<td class="tdError" style="padding:0 0">'+formato_numero(listado[j].columnas[5],0,",",".")+'</td>'+
							'<td class="tdError colAtl" style="padding:0 0">'+formato_numero(listado[j].columnas[6],3,",",".")+'</td>'+
						'</tr>'
					);
					
					//Errores para Prepago
					//Columna izquierda
					 Ext.get('LP'+arrayRowId[i]).insertHtml(
						'afterEnd',
						'<tr class="filaError">'+
							'<td class="tdError" style="padding:0 0">'+listado[j].columnas[8]+'</td>'+
						'</tr>'
					);
					//Columna derecha
					Ext.get('P'+arrayRowId[i]).insertHtml(
						'afterEnd',
						'<tr class="filaError">'+
							'<td class="tdError" style="padding:0 0">'+formato_numero(listado[j].columnas[9],0,",",".")+'</td>'+
							'<td class="tdError colAtl" style="padding:0 0">'+formato_numero(listado[j].columnas[10],3,",",".")+'</td>'+
						'</tr>'
					);					
					
					//Errores para Totales
					//Columna izquierda
					 Ext.get('LT'+arrayRowId[i]).insertHtml(
						'afterEnd',
						'<tr class="filaError">'+
							'<td class="tdError" style="padding:0 0">'+listado[j].columnas[12]+'</td>'+
						'</tr>'
					);
					//Columna derecha
					 Ext.get('T'+arrayRowId[i]).insertHtml(
						'afterEnd',
						'<tr class="filaError">'+
							'<td class="tdError" style="padding:0 0">'+formato_numero(listado[j].columnas[13],0,",",".")+'</td>'+
							'<td class="tdError colAtl" style="padding:0 0">'+formato_numero(listado[j].columnas[14],3,",",".")+'</td>'+
						'</tr>'
					);
				}
			}
		}
	}
				
				
	//Funcion que llama a la carga de los datos de la excel
	var cargarDatos = function(){

		var busqueda = {
			fecha: Ext.get('txtFechaBusqueda').dom.value,
			informe: "1"
		};
		
		ultimaBusqueda.add('fxBusqueda',Ext.get('txtFechaBusqueda').dom.value);
		
		llamadaAjax('Convivencia.do','buscar','busquedaJSON',busqueda,callBackCargar,true);
	}
	
	//Ocultamos las pestanas al inicio
	var ocultar = function (){
		Ext.get('pesMovTotales').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('pesMovFija').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('pesMovMovil').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('pesMovPrepago').setVisibilityMode(Ext.Element.DISPLAY).hide();		
		Ext.get('pesReinyeccion').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('pesErrores').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		Ext.get('informeSalida').setVisibilityMode(Ext.Element.DISPLAY).hide();	
	}

	//Mostramos las pestanas una vez hay datos
	var mostrar = function (hay){
		Ext.get('pesMovTotales').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('pesMovFija').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('pesMovMovil').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('pesMovPrepago').setVisibilityMode(Ext.Element.DISPLAY).show();		
		if (hay){
			Ext.get('pesReinyeccion').setVisibilityMode(Ext.Element.DISPLAY).show();
		}
		else{
			Ext.get('pesReinyeccion').setVisibilityMode(Ext.Element.DISPLAY).hide();
		}
		Ext.get('pesErrores').setVisibilityMode(Ext.Element.DISPLAY).show();
		
		Ext.get('informeSalida').setVisibilityMode(Ext.Element.DISPLAY).show();	
	}	
	
	var numDiasMesFechaActual = function(){
		var fec = convertDateToJSONLib(new Date());
		
		var mes = fec.month - 1;
		
		if (mes == -1){//Si es enero
			mes = 11;
		}
		
		var anio = fec.year + 1900;
		
		if (mes == 0) return 31;
		if (mes == 1){
			if (comprobarSiBisisesto(anio))
		 		return 29;
		 	else return 28;
		}
		if (mes == 2) return 31;
		if (mes == 3) return 30;
		if (mes == 4) return 31;
		if (mes == 5) return 30;
		if (mes == 6) return 31;
		if (mes == 7) return 31;
		if (mes == 8) return 30;
		if (mes == 9) return 31;
		if (mes == 10) return 30;
		if (mes == 11) return 31;
	}
	
	//Funcion que inicia los calendarios entre la fecha actual y un mes antes
	var iniciarCalendarios = function (){

		//Obtenmos la fecha de hoy
		var hoy = new Date();
		var diasAntes = new Date();
		var ayer = new Date();
		
		//Un mes
		diasAntes.setDate(diasAntes.getDate()-numDiasMesFechaActual());
		ayer.setDate(hoy.getDate()-1);
		
		Calendar.setup({
			inputField: "txtFechaBusqueda",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaBusqueda",
			min:diasAntes,
			max:ayer,
			bottomBar: false,
			align:'Br///T/l',
			onSelect: function() {
				this.hide();
			}				
		});
	
	}

    return {
		init: function() {
			Ext.QuickTips.init();
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			
			Ext.get('txtFechaBusqueda').dom.value = "";
			
			ocultar();
			controlEventos();
			iniciarCalendarios();
		}	
	}
	 
}();

Ext.onReady(CGCONVIVENCIA.init, CGCONVIVENCIA, true);
</sec:authorize>