var CGLOBAL = function (){
    var grid = null;
    var datosResultado;
    var datosEstadisticas;
    var myMask;
    
    var obtenerDatos = function (callback){
    	var fechaInicio = Date.parseDate(Ext.get('txtFechaInicio').dom.value,'d.m.Y');
    	var fechaFin = Date.parseDate(Ext.get('txtFechaFin').dom.value,'d.m.Y');
    	
    	if (!fechaInicio){
    	
    		fechaInicio = Date.parseDate("1900-01-01",'Y-m-d');
    	}
    	
    	if (!fechaFin){
    		fechaFin = Date.parseDate("2500-12-31",'Y-m-d');
    	}
    	
    	var fechas = {
			fxInicio:convertDateToJSONLib(fechaInicio),
			fxFin:convertDateToJSONLib(fechaFin),
			tipoInforme:'D'
		};
    	Ext.Ajax.request({
    		url:contexto + 'DocumentoDuplicado.do',
    		params:{
    			buscar:'',
    			documentoDuplicadoJSON:Ext.encode(fechas)
    		},
    		callback:function (options, success, response){
    			if (success){
    				resultadoConsulta = Ext.util.JSON.decode (response.responseText);
    				if (resultadoConsulta.message.length>0){
    					Ext.each(resultadoConsulta.message,function(message){
	    					Ext.Msg.show({
							   title:'Error',
							   cls:'cgMsgBox',
							   msg: '<span class="bold">'+message+'</span><br/>',
							   buttons: Ext.Msg.OK,
							   icon: Ext.MessageBox.ERROR
							});
	    				});
	    				myMask.hide();
    				}else{
	    				var informeDto = resultadoConsulta.datos[0];
	    				
	    				datosResultado = informeDto.listaClientes;
	    				datosEstadisticas = informeDto.estadisticasDto;
	    					    			
		    			actualizarEstadisticas();
		    			
		    			Ext.get('horaConsulta').dom.innerHTML = actualizarTiempoConsulta();
    				}
    				callback();
    				myMask.hide();
    				setAceptado();
    			}
    		}
    	});
    }
    
     var setAceptado = function (){
    	Ext.each(Ext.query('input[type=radio]', 'divGrid'), function(input) {
			Ext.get(input).on("click", function(e){
				if (Ext.get(input).dom.value == "true"){
					grid.getSelectionModel().getSelected().data.aceptado = true;
				}else{
					grid.getSelectionModel().getSelected().data.aceptado = false;
				}
			});
		});	
    }
    
    var actualizarEstadisticas = function() {
		
		Ext.get('rechazados').dom.innerHTML = datosEstadisticas.regErroneos;
		Ext.get('rechFijo').dom.innerHTML = datosEstadisticas.regErrorFija;
		Ext.get('rechMovil').dom.innerHTML = datosEstadisticas.regErrorMovil;
		Ext.get('procesados').dom.innerHTML = datosEstadisticas.regTratados;
		Ext.get('aceptados').dom.innerHTML = datosEstadisticas.regValidos;
		Ext.get('aceptFijo').dom.innerHTML = datosEstadisticas.regValidosFija;
		Ext.get('aceptMovil').dom.innerHTML = datosEstadisticas.regValidosMovil;
		Ext.get('totalReg').dom.innerHTML = datosResultado.length;
	}	
    
	function exportarExcel(){  
		
		var fechaInicio = Date.parseDate(Ext.get('txtFechaInicio').dom.value,'d.m.Y');
    	var fechaFin = Date.parseDate(Ext.get('txtFechaFin').dom.value,'d.m.Y');
    	
    	if (!fechaInicio){
    	
    		fechaInicio = Date.parseDate("1900-01-01",'Y-m-d');
    	}
    	
    	if (!fechaFin){
    		fechaFin = Date.parseDate("2500-12-31",'Y-m-d');
    	}
    	
    	var fechas = {
			fxInicio:convertDateToJSONLib(fechaInicio),
			fxFin:convertDateToJSONLib(fechaFin)
		};
		
		document.getElementById('ifExportar').src = contexto + 'DocumentoDuplicado.do?exportar=&documentoDuplicadoJSON='+Ext.encode(fechas);
	}
    
	var pintarGrid = function (){
	
		var selectionModel = new Ext.grid.RowSelectionModel({
			singleSelect: false, 
			listeners: {
				
				beforerowselect: function(sm) {
				},
				rowdeselect: function(sm) {
				},
				rowselect: function(sm) {
				}
			}
		});
	 
	   // create the data store
	    var store = new Ext.data.Store({
	    	reader: new Ext.data.ObjectReader({}, [
				{name: 'indicadorNegocio', type: 'string'},
				{name: 'clienteNSCO', type: 'string'},
				{name: 'fxFin', type: 'date'},
				{name: 'fxInicio', type: 'date'},
				{name: 'fxMovimiento', type: 'date'},
				{name: 'clienteNSCO', type: 'string'},
				{name: 'tipoDocumento', type: 'string'},
				{name: 'numDocumento', type: 'string'},
				{name: 'razonSocial', type: 'string'},
				{name: 'aceptado'}
			])
	    });
	    
	  	var columnModel = new Ext.grid.ColumnModel([
		    {header: 'Origen',sortable: true,width:60,  dataIndex: 'indicadorNegocio'},		    
            {header: 'Código cliente', sortable: true, dataIndex: 'clienteNSCO'},
            {header: 'Tipo doc.', sortable: true,width:135, dataIndex: 'tipoDocumento'},
            {header: 'Doc. identif.', sortable: true,width:135, dataIndex: 'numDocumento'},
            {header: 'Nombre/Razón social',sortable: true, id:'colNombre',  dataIndex: 'razonSocial'},
          	{header: 'Aceptado',sortable: true,width:100,  align:'center', renderer: crearAceptado},
          	{header: 'Rechazado',sortable: true,width:100,  align:'center', renderer: crearRechazado}
          	
		]);
		
	    // create the Grid
	    grid = new Ext.grid.GridPanel({
	    	id:'gridDocumentoDuplicado',
	        store: store,
			renderTo: 'divGrid',
			cls:'gridCG',
			sm: selectionModel,
			cm: columnModel,
	        stripeRows: true,
	        autoExpandColumn: 'colNombre',
	        height: 177,
	        listeners:{
	        	render:function(){
			    	obtenerDatos(function (){
			    		store.loadData (datosResultado);	
			    	});
			    }
	        }
	    });
	    
	    
	}
	
	var crearAceptado = function(data, cellmd, record, rowIndex,colIndex, store) {
		return data = "<input type='radio' class='noBrd' id='" + record.data.clienteNSCO + "A' name='" + record.data.clienteNSCO + "' value='true' />";
	}
	
	var crearRechazado = function(data, cellmd, record, rowIndex,colIndex, store) {
		return data = "<input type='radio' class='noBrd' id='" + record.data.clienteNSCO + "R' name='" + record.data.clienteNSCO + "' value='false' />";
	}
    
   // Controlamos los eventos sobre los botones
	var controlEventos = function() {
		
		window.onresize = function() {
			suspender_eventos=true;
			if (Ext.isIE6){
				var ancho = document.body.offsetWidth - 258;
				grid.setWidth (ancho);
			}else{
				grid.getView().refresh(true);
			}
		}
		
		// evento click del botón Buscar (Lupa)
		Ext.get('btnBuscar').on('click', function() {
			buscarInformesDuplicados();
		});
		
		// evento click del botón Imprimir
		Ext.get('btnImprimir').on('click', function() {
			imprimirInformesDuplicados();
		});
		
		// evento click del botón Guardar
		Ext.get('btnGuardar').on('click', function() {
				
		});
		
		// evento click del botón Exportar Excel
		Ext.get('btnExportar').on('click', function() {
			exportarExcel();
		});
		
		Ext.get('btnAceptar').on('click', function() {
			seleccionados = grid.getSelectionModel().getSelections();
			Ext.each(seleccionados,function(seleccionado){
	 			seleccionado.data.aceptado = true;
	 			Ext.get(seleccionado.data.clienteNSCO + 'A').dom.checked = true;
	 		});
		});
		
		Ext.get('btnRechazar').on('click', function() {
			seleccionados = grid.getSelectionModel().getSelections();
			Ext.each(seleccionados,function(seleccionado){
	 			seleccionado.data.aceptado = false;
	 			Ext.get(seleccionado.data.clienteNSCO + 'R').dom.checked = true;
	 		});
		});
		
		Ext.get('btnTodo').on('click', function() {
			grid.getSelectionModel().selectAll();
		});
		
		Ext.get('btnNada').on('click', function() {
			grid.getSelectionModel().clearSelections();
		});
		
		//Evento en el boton Limpiar
		Ext.get('btnLimpiar').on('click', function() {
			grid.getSelectionModel().clearSelections();
	 		grid.getStore().each(function(recGrid) {
	 			recGrid.data.aceptado = false;
	 			Ext.get(recGrid.data.clienteNSCO + 'A').dom.checked = false;
	 			Ext.get(recGrid.data.clienteNSCO + 'R').dom.checked = false;
	 		});	
	 		
		});
		
		//Campo de texto Fecha inicio
		Ext.get('txtFechaInicio').on('keyup', function() {
			controlCamposFecha.defer(200);
		});
		
		Ext.get('txtFechaInicio').on('cut', function() {
			controlCamposFecha.defer(200);
		});
		
		Ext.get('txtFechaInicio').on('paste', function() {
			controlCamposFecha.defer(200);
		});
	}	
	
	var controlCamposFecha = function() {
		
		if (Ext.get('txtFechaInicio').dom.value.trim().length > 0){
			Ext.get('txtFechaFin').dom.readOnly = false;
			Ext.get('divTxtFechaFin').removeClass('calendarDis');
			Ext.get('btnCalendarFechaFin').dom.disabled = false;
		}else{
			Ext.get('txtFechaFin').dom.readOnly = true;
			Ext.get('txtFechaFin').dom.value = '';
			Ext.get('divTxtFechaFin').addClass('calendarDis');
			Ext.get('btnCalendarFechaFin').dom.disabled = true;
		}
	}
	
	var imprimirInformesDuplicados = function() {
		
		var fechaInicio = Date.parseDate(Ext.get('txtFechaInicio').dom.value,'d.m.Y');
    	var fechaFin = Date.parseDate(Ext.get('txtFechaFin').dom.value,'d.m.Y');
    	
    	if (!fechaInicio){
    	
    		fechaInicio = Date.parseDate("1900-01-01",'Y-m-d');
    	}
    	
    	if (!fechaFin){
    		fechaFin = Date.parseDate("2500-12-31",'Y-m-d');
    	}
    	
    	var fechas = {
			fxInicio:convertDateToJSONLib(fechaInicio),
			fxFin:convertDateToJSONLib(fechaFin)
		};
		
		document.getElementById('ifExportar').src = contexto + 'DocumentoDuplicado.do?exportarPDF=&documentoDuplicadoJSON='+Ext.encode(fechas);
		
		
	}
	
	var buscarInformesDuplicados = function() {
		var fechaInicio = Ext.get('txtFechaInicio').dom.value;
		var fechaFin = Ext.get('txtFechaFin').dom.value;
		var dFechaInicio = new Date();
		var dFechaFin = new Date();
		dFechaInicio = Date.parseDate(fechaInicio, 'd.m.Y');
		dFechaFin = Date.parseDate(fechaFin, 'd.m.Y');
		
		//Comparamos las fechas.
		if (fechaInicio.trim().length > 0){
			if (fechaFin.trim().length > 0){
				if (esFechaValida(fechaInicio) && esFechaValida(fechaFin)){
				
					if (dFechaInicio >= dFechaFin){
						Ext.Msg.show({
						   title:'Fecha incorrecta',
						   msg: '<span class="bold">La fecha "desde" debe ser menor a la fecha "hasta".</span>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
					}else{
						alert("Buscar Documento duplicados");
					}
				}
			}else{
				Ext.Msg.show({
				   title:'Fecha incorrecta',
				   msg: '<span class="bold">La fecha "hasta" es obligatoria.</span>',
				   buttons: Ext.Msg.OK,
				   icon: Ext.MessageBox.ERROR
				});
			}
		}else{
			alert("Buscar Documento duplicados");
		}	
	}
	
	
	
	var iniciarCalendarios = function (){
		
		Ext.get('txtFechaFin').dom.readOnly = true;
		Ext.get('divTxtFechaFin').addClass('calendarDis');
		Ext.get('btnCalendarFechaFin').dom.disabled = true;
		Ext.get('txtFechaFin').dom.value = '';
		
		//Obtenmos la fecha de hoy
		var hoy = new Date();
		var diasAntes = new Date();
		
		
		diasAntes.setDate(diasAntes.getDate()-7);
		
		new Calendar({
			inputField: "txtFechaInicio",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaInicio",
			bottomBar: false,
			align:'Br///T/l',
			min: diasAntes,
    		max: hoy,
			onSelect: function() {
				this.hide();
				controlCamposFecha();
			}
		});
		
		new Calendar({
			inputField: "txtFechaFin",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaFin",
			min: diasAntes,
    		max: hoy,
			bottomBar: false,
			align:'Br///T/r',
			onSelect: function() {
				this.hide();
			}
		});
 
	}
	
	
	var actualizarTiempoConsulta = function(){
		var ahora = new Date();
		
		var diaSemana = ahora.getDate();
		
		if (diaSemana < 10){
			diaSemana = '0' + diaSemana;
		}
		
		var mes = ahora.getMonth() + 1;
		
		if (mes < 10){
			mes = '0' + mes;
		}
		
		var hora = ahora.getHours();
		
		if (hora < 10){
			hora = '0' + hora;
		}
		
		var minutos = ahora.getMinutes();
		
		if (minutos < 10){
			minutos = '0' + minutos;
		}
		
		return diaSemana + '.' + mes + '.' + ahora.getFullYear() + ' ' + hora + ':' + minutos;
	}
	
	return {
		init: function (){
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			
			myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
			myMask.show();
			
			pintarGrid();
			
			controlEventos();
			
			iniciarCalendarios ();
		}
	}
	
}();

Ext.onReady (CGLOBAL.init, CGLOBAL, true);