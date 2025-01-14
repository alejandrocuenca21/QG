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
			tipoInforme:'E'
		};
    	Ext.Ajax.request({
    		url:contexto + 'MovimientosTratados.do',
    		params:{
    			buscar:'',
    			movimientosTratadosJSON:Ext.encode(fechas)
    		},
    		callback:function (options, success, response){
    			if (success){
    				resultadoConsulta = Ext.util.JSON.decode (response.responseText);
    				
    				if (resultadoConsulta.message.length>0){
    					Ext.each(resultadoConsulta.message,function(menssage){
	    					Ext.Msg.show({
							   title:'Error',
							   cls:'cgMsgBox',
							   msg: '<span class="bold">'+menssage+'</span><br/>',
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
    				setRevisado();
    			}
    		}
    	});
    }
    
    var setRevisado = function (){
    	Ext.each(Ext.query('input[type=checkbox]', 'divGrid'), function(input) {
			Ext.get(input).on("click", function(e){
				if (Ext.get(input).dom.checked){
					grid.getSelectionModel().getSelected().data.revisado = true;
				}else{
					grid.getSelectionModel().getSelected().data.revisado = false;
				}
			});
		});	
    }
    
    var actualizarEstadisticas = function() {
		Ext.get('conError').dom.innerHTML = datosEstadisticas.regErroneos;
		Ext.get('errorFijo').dom.innerHTML = datosEstadisticas.regErrorFija;
		Ext.get('errorMovil').dom.innerHTML = datosEstadisticas.regErrorMovil;
		Ext.get('procesados').dom.innerHTML = datosEstadisticas.regTratados;
		Ext.get('correctos').dom.innerHTML = datosEstadisticas.regValidos;
		Ext.get('cFijo').dom.innerHTML = datosEstadisticas.regValidosFija;
		Ext.get('cMovil').dom.innerHTML = datosEstadisticas.regValidosMovil;
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
		
		document.getElementById('ifExportar').src = contexto + 'MovimientosTratados.do?exportar=&movimientosTratadosJSON='+Ext.encode(fechas);
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
				{name: 'tipoDocumento', type: 'string'},
				{name: 'numDocumento', type: 'string'},
				{name: 'razonSocial', type: 'string'},
				{name: 'revisado'}
			])
	    });
	    
	  	var columnModel = new Ext.grid.ColumnModel([
		    {header: 'Origen',sortable: true,width:60,  dataIndex: 'indicadorNegocio'},		    
            {header: 'Código cliente', sortable: true, dataIndex: 'clienteNSCO'},
            {header: 'Tipo doc.', sortable: true,width:135, dataIndex: 'tipoDocumento'},
            {header: 'Doc. identif.', sortable: true,width:135, dataIndex: 'numDocumento'},
            {header: 'Nombre/Razón social',sortable: true, id:'colNombre',  dataIndex: 'razonSocial'},
            {header: 'Diagnostico',sortable: true, renderer: mostrarDiagnostico},
          	{header: 'Estado revisión',sortable: true,width:100,  renderer: crearEstado}
		]);
		
	    // create the Grid
	    grid = new Ext.grid.GridPanel({
	    	id:'gridMovimientosTratados',
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
	
	var crearEstado = function(data, cellmd, record, rowIndex,colIndex, store) {
		return data = "<div class='grupoRad'><input type='checkbox' id='" + record.data.clienteNSCO + "' "+((record.data.revisado == true) ? 'checked=checked' : '')+" name='" + record.data.clienteNSCO + "' value='false' /><span>Revisado<span></span>";
	}
	
	var mostrarDiagnostico = function(data, cellmd, record, rowIndex,colIndex, store) {
		return data = "Error";
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
			buscarInformesErrores();
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
		
		Ext.get('btnRevisado').on('click', function() {
			seleccionados = grid.getSelectionModel().getSelections();
			Ext.each(seleccionados,function(seleccionado){
	 			seleccionado.data.revisado = true;
	 			Ext.get(seleccionado.data.clienteNSCO).dom.checked = true;
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
	 			recGrid.data.revisado = false;
	 			Ext.get(recGrid.data.clienteNSCO).dom.checked = false;
	 		});	
	 		
		});
		
		//Evento al cambiar el combo estado de revision
		Ext.get('selEstado').on('change', function() {
			cambiarEstadoRevision();
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
	
	var cambiarEstadoRevision = function() {
		var indice = Ext.get('selEstado').dom.selectedIndex;
		
		if (indice == 1){
			grid.getStore().filterBy( function filter(record){
		        if(record.data.revisado){
		        	return true;
		        }else{
		            return false;
		        }
			});	 
		}else if(indice == 2){
			grid.getStore().filterBy( function filter(record){
		        if(!record.data.revisado){
		        	return true;
		        }else{
		            return false;
		        }
			});
		}else{
			grid.getStore().clearFilter()
		}
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
		alert ("Imprimir Informes Duplicados");
	}
	
	
	var buscarInformesErrores = function() {
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
						alert("Buscar Informe Errores");
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
			alert("Buscar Informe Errores");
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
			bottomBar: false,
			align:'Br///T/r',
			min: diasAntes,
    		max: hoy,
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