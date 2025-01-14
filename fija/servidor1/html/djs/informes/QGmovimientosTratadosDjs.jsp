<%@ page language="java" contentType="text/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
var CGLOBAL = function (){
    var grid = null;
    var datosResultado; 
    var datosEstadisticas;
    var myMask;
    
   	var filaSeleccionada;
   	
   	var nElems; 		//Filas que existen
   	var elemColum = 6;	//Numero de columnas mostradas (contadas a partir de 0)
    
    //criterios de la ultima busqueda realizada.
	var ultimaBusqueda = new Ext.util.MixedCollection();
    
    var obtenerDatos = function (){
    	
    	var fechaBusqueda = Ext.get('txtFechaBusqueda').dom.value;
    	var hoy = new Date();
    	
    	if (fechaBusqueda == null){
    	 	//La fecha es obligatoria
    		Ext.Msg.show({
					title:'Error',
					cls:'cgMsgBox',
					msg: '<span>Es necesario introducir la fecha para realizar la b&uacute;squeda.</span><br/>',
					buttons: Ext.Msg.OK,
					icon: Ext.MessageBox.ERROR
					});
    	}else{
			
			//La fecha debe ser valida
			if (esFechaValida(fechaBusqueda, "Fecha de b&uacute;squeda")){
			
						var fechaAnterior = new Date().getTime();
						//le restamos los milisegundos de 6 dias.
						var milisegundosDia = 24*60*60*1000;
						fechaAnterior = fechaAnterior-7*milisegundosDia;
					
					var fechaParseada = Date.parseDate(fechaBusqueda,'d.m.Y').getTime();
					var hoy = new Date().getTime();
					if( fechaParseada < fechaAnterior || fechaParseada > hoy){
					
						//La fecha debe estar en los ultimos 7 dias
		    			Ext.Msg.show({
							title:'Error',
							cls:'cgMsgBox',
							msg: '<span>La fecha de b&uacute;squeda debe estar entre los &uacute;ltimos 7 d&iacute;as</span><br/>',
							buttons: Ext.Msg.OK,
							icon: Ext.MessageBox.ERROR
							});
					} else{ 
					   	var fechas = {
							fxBusqueda:fechaBusqueda
						};
						myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
						myMask.show();
						
				    	Ext.Ajax.request({
				    		url:contexto + 'MovimientosTratados.do',
				    		params:{
				    			buscar:'',
				    			movimientosTratadosJSON:Ext.encode(fechas)
				    		},
				    		callback:function (options, success, response){
				    			myMask.hide();
				    			if (success){
				    				resultadoConsulta = Ext.util.JSON.decode (response.responseText);
				    					
				    				if (resultadoConsulta.success){
				    					var informeDto = resultadoConsulta.datos[0];
					    					    			
					    				datosResultado = informeDto.listaClientes;
					    				datosEstadisticas = informeDto.estadisticasDto;
					    				
					    				//Almacenamos el numero de filas devueltas (a partir de 0)
					    				nElems = datosResultado.length - 1;
					    				
						    			actualizarEstadisticas();
						    			Ext.get('horaConsulta').dom.innerHTML = actualizarTiempoConsulta();
				    					
				    					//Pueden llegar mensajes informativos.
				    					if (resultadoConsulta.message && resultadoConsulta.message.length>0){
					    					Ext.each(resultadoConsulta.message,function(message){
						    					Ext.Msg.show({
												   title:'Informaci&oacute;n',
												   cls:'cgMsgBox',
												   msg: '<span>'+message+'</span><br/>',
												   buttons: Ext.Msg.OK,
												   icon: Ext.MessageBox.INFO
												});
						    				});
					    				}
					    				
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
										}
							    				
				    				}else{
				    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
				    					Ext.Msg.show({
										   title:'Error',
										   cls:'cgMsgBox',
										   msg: '<span>'+resultadoConsulta.message+'</span><br/>',
										   buttons: Ext.Msg.OK,
										   icon: Ext.MessageBox.ERROR
										});
										ultimaBusqueda.add('hayResultados',false);
				    				}
				    				
									grid.getStore().proxy =  new Ext.ux.data.PagingMemoryProxy(datosResultado);
									grid.getStore().load({params: {start: 0, limit: 20}});
				    				//Ext.getCmp('gridMovimientosTratados').getStore().loadData(datosResultado);
				    			}
			    			
			    		//Guardamos la fecha de ultima busqueda para las exportaciones
			    		ultimaBusqueda.add('fxBusqueda',fechaBusqueda);
			    		}
			    	});
			    }
		    
	    }
	    }
	 }
    
    
    var buscarInformesErrores = function() {
	
		obtenerDatos();

	}
    
    
    
    var imprimirInformesDuplicados = function() {
		
		//Solo exportamos si hay resultados
		if(ultimaBusqueda.get('hayResultados')){
			    	
	    	
	    	var fechas = {
				fxBusqueda:ultimaBusqueda.get('fxBusqueda')
			};
			
			if (Ext.isIE){
				myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
				myMask.show();
			}
			
			document.getElementById('ifExportar').src = contexto + 'MovimientosTratados.do?exportarPDF=&movimientosTratadosJSON='+Ext.encode(fechas);
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
	
	function exportarExcel(){  
		//Solo exportamos si hay resultados
		if(ultimaBusqueda.get('hayResultados')){
		    	
    	
    	var fechas = {
			fxBusqueda:ultimaBusqueda.get('fxBusqueda')
		};
		
		document.getElementById('ifExportar').src = contexto + 'MovimientosTratados.do?exportar=&movimientosTratadosJSON='+Ext.encode(fechas);
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
	
  
    
    <sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA">
    var setRevisado = function (){
    	Ext.each(Ext.query('input[type=checkbox]', 'divGrid'), function(input) {
			Ext.get(input).on("click", function(e){
				if (Ext.get(input).dom.checked){
					filaSeleccionada.revisado = true;
				}else{
					filaSeleccionada.revisado = false;
				}
			});
		});	
    }
    </sec:authorize>
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
    
	
	var pintarGrid = function (){
		
		var view = new Ext.grid.GridView({
			listeners:{
	        	refresh:function(){
			    	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA">
		    		setRevisado();
		    		</sec:authorize>
			    }
	        }
		});
	 
	   // create the data store
	    var store = new Ext.data.Store({
	    		autoLoad: {params:{start: 0, limit: 20}},
	    		baseParams:{params:{start: 0, limit: 20}},
	    		proxy: new Ext.ux.data.PagingMemoryProxy([]),
	    		reader: new Ext.data.ObjectReader({}, [
				{name: 'indicadorNegocio', type: 'string'},
				{name: 'clienteNSCO', type: 'string'},
				{name: 'tipoDocumento', type: 'string'},
				{name: 'numDocumento', type: 'string'},
				{name: 'razonSocial', type: 'string'},
				{name: 'codigoError', type: 'string'},
				{name: 'fxFin', type: 'date'},
				{name: 'fxInicio', type: 'date'},
				{name: 'fxMovimiento', type: 'date'}
				<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA">
				,
				{name: 'revisado'},
				{name: 'idCheckRevisado', type: 'string'}
				</sec:authorize>
			])

	    });

	  	var columnModel = new Ext.grid.ColumnModel([
		    {header: 'Origen',sortable: true,width:60,  dataIndex: 'indicadorNegocio', renderer: rendererOrigen},		    
            {header: 'Código cliente', sortable: true, dataIndex: 'clienteNSCO'},
            {header: 'Tipo doc.', sortable: true,width:135, dataIndex: 'tipoDocumento'},
            {header: 'Doc. identif.', sortable: true,width:135, dataIndex: 'numDocumento'},
            {header: 'Nombre/Razón social',sortable: true, id:'colNombre',  dataIndex: 'razonSocial'},
            {header: 'Diagnóstico',sortable: true, width:85, dataIndex: 'codigoError'}
            <sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA">
            ,
          	{header: 'Estado revisión',sortable: true,width:100,  renderer: crearEstado}
          	</sec:authorize>
		]);
		
		var cargarDatosFila = function(fila){
			Ext.each(Ext.query('.marcarFila', grid.id), function(filaMarcada) {
				Ext.fly(filaMarcada).removeClass('marcarFila');
			});
			Ext.fly(grid.getView().getRow(fila)).addClass('marcarFila');
			
			filaSeleccionada = grid.getStore().data.items[fila].data;
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA">
			Ext.get('btnRevisado').dom.disabled = false;
			Ext.get('btnRevisado').dom.src = contexto + 'images/botones/QGbtRevisado.gif';
			Ext.get('btnRevisado').removeClass('btnDis');
			</sec:authorize>
		}	

	    // create the Grid
	    grid = new Ext.grid.EditorPasteCopyGridPanel({
	    	id:'gridMovimientosTratados',
	        store: store,
			renderTo: 'divGrid',
			cls:'gridCG',
			view:view,
			selectionModel: '',
			funcionRollBack: cargarDatosFila,
			cm: columnModel,
	        stripeRows: true,
	        autoExpandColumn: 'colNombre',
	        height: 177,
	        bbar: new NoRefreshPagingToolbar({
       			store: store,       // grid and PagingToolbar using same store
       		 	displayInfo: false,
        		pageSize: 20
        	})
	    });
	    
	    if (Ext.isIE6){
			var ancho = document.body.offsetWidth - 244;
			grid.setWidth (ancho);
		} 
	}
	
	var rendererOrigen = function(data, cellmd, record, rowIndex,colIndex, store) {

		if (data == "Fijo"){
			data = "<span class='colFijo'>Fijo</span>";
		}else{
			data = "<span class='colMovil'>Móvil</span>";
		}

		return data;
	}
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA">
	var crearEstado = function(data, cellmd, record, rowIndex,colIndex, store) {
		record.data.idCheckRevisado = Ext.id();
		return data = "<div class='grupoRad'><input type='checkbox' id='" + record.data.idCheckRevisado + "' "+((record.data.revisado == true) ? 'checked=checked' : '')+" name='" + record.data.clienteNSCO + "' value='false' /><span>Revisado<span></span>";
	}
	</sec:authorize>
	
   // Controlamos los eventos sobre los botones
	var controlEventos = function() {
		
		Ext.get('marco').on('resize', function() {
			refrescarGrid.defer(20);
		});
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA">
		// evento click del botón Guardar
		Ext.get('btnGuardar').on('click', function() {
				
		});
		
		Ext.get('btnRevisado').on('click', function() {
	 		var filaIni = Ext.getCmp('gridMovimientosTratados').getSelectionModel().getSelectedCellRange()[0];
	 		var filaFin = Ext.getCmp('gridMovimientosTratados').getSelectionModel().getSelectedCellRange()[2];
	 		for (var i = filaIni;i <= filaFin;i++){
	 			grid.getStore().data.items[i].data.revisado = true;
	 			Ext.get(grid.getStore().data.items[i].data.idCheckRevisado).dom.checked = true;
	 		}
	 		
		});		
		
		Ext.get('btnTodo').on('click', function() {
			//Seleccionamos desde la celda 0,0 hasta la celda nElems,elemColum
			Ext.getCmp('gridMovimientosTratados').getSelectionModel().getSelectedCellRange()[0] = 0;
			Ext.getCmp('gridMovimientosTratados').getSelectionModel().getSelectedCellRange()[1] = 0;
			Ext.getCmp('gridMovimientosTratados').getSelectionModel().getSelectedCellRange()[2] = nElems;
			Ext.getCmp('gridMovimientosTratados').getSelectionModel().getSelectedCellRange()[3] = elemColum;

			for(var i=0;i <= nElems; i++){
				for (var j =0;j <= elemColum;j++){
					Ext.getCmp('gridMovimientosTratados').getSelectionModel().select(i,j);
				}
			}
			
			Ext.get('btnRevisado').dom.disabled = false;
			Ext.get('btnRevisado').dom.src = contexto + 'images/botones/QGbtRevisado.gif';
			Ext.get('btnRevisado').removeClass('btnDis');
		});
		
		Ext.get('btnNada').on('click', function() {
			filaSeleccionada = null;
			Ext.getCmp('gridMovimientosTratados').getSelectionModel().clearSelections(true);
			// deshabilitamos el botón Revisado
			Ext.get('btnRevisado').dom.disabled = true;
			Ext.get('btnRevisado').dom.src = contexto + 'images/botones/QGbtRevisado_des.gif';
			Ext.get('btnRevisado').addClass('btnDis');
		});
		
		//Evento en el boton Limpiar
		Ext.get('btnLimpiar').on('click', function() {
			filaSeleccionada = null;
			Ext.getCmp('gridMovimientosTratados').getSelectionModel().clearSelections(true);
	 		grid.getStore().each(function(recGrid) {
	 			recGrid.data.revisado = false;
	 			Ext.get(recGrid.data.idCheckRevisado).dom.checked = false;
	 			
	 		});	
	 		// deshabilitamos el botón Revisado
			Ext.get('btnRevisado').dom.disabled = true;
			Ext.get('btnRevisado').dom.src = contexto + 'images/botones/QGbtRevisado_des.gif';
			Ext.get('btnRevisado').addClass('btnDis');
	 		
		});
		
		//Evento al cambiar el combo estado de revision
		Ext.get('selEstado').on('change', function() {
			cambiarEstadoRevision();
		});		
		</sec:authorize>
		
		// evento click del botón Buscar (Lupa)
		Ext.get('btnBuscar').on('click', function() {
			buscarInformesErrores();
		});
		
		// evento click del botón Imprimir
		Ext.get('btnImprimir').on('click', function() {
			imprimirInformesDuplicados();
		});
		
		if (Ext.isIE)
			document.getElementById('ifExportar').onreadystatechange = function(){myMask.hide();};
			
		// evento click del botón Exportar Excel
		Ext.get('btnExportar').on('click', function() {
			exportarExcel();
		});
		

	}
	
	var refrescarGrid = function() {
		if (Ext.isIE6){
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).hide();
			var ancho = document.body.offsetWidth - 244;
			
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).show();
			grid.setWidth (ancho);
		}
	}
	
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA">
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
	</sec:authorize>

	
	
	var iniciarCalendarios = function (){

		//Obtenmos la fecha de hoy
		var hoy = new Date();
		var diasAntes = new Date();
		
		//Una semana
		diasAntes.setDate(diasAntes.getDate()-6);
		
		Calendar.setup({
			inputField: "txtFechaBusqueda",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaBusqueda",
			min:diasAntes,
			max:hoy,
			bottomBar: false,
			align:'Br///T/l',
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
			
			if (Ext.isIE6){
				var ancho = document.body.offsetWidth - 244;
				grid.setWidth (ancho);
			}
			
			controlEventos();
			
			iniciarCalendarios ();
			
			myMask.hide();
		}
	}
	
}();

Ext.onReady (CGLOBAL.init, CGLOBAL, true);
</sec:authorize>