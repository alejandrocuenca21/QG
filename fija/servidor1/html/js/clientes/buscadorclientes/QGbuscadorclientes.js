var CGLOBAL = function (){
    var grid = null;
    var gridHistorico = null;
    
    var record_antiguo;
    var alta = false;
    var hay_seleccion=true;
    var suspender_eventos=false;
    var datosResultado;
    var tiposDocumento;
    var myMask;
    var ultimaBusqueda = new Ext.util.MixedCollection();
    
    var filaSeleccionada;
    
    var cargarTipoDocumento = function (linea,modalidad){
    
		var parametros = {};
		eval("parametros.cargarTipoDocumento = ''");    
    
    	var datosJson={  
			codigoLinea: linea,
			codigoModalidad: modalidad,
			codigoDocumento: ""						 
		}			
		
		if(datosJson instanceof Object){
			datosCodificados = Ext.encode(datosJson);
		}else{
			datosCodificados = datosJson;
		}
		eval("parametros.busquedaTipoDocJSON ='"+ datosCodificados+"'");		
    
    	myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
		myMask.show();
    	Ext.Ajax.request({
    		url:contexto + 'ListaClientes.do',
    		params:parametros,
    		callback:function (options, success, response){
    			myMask.hide();
    			if (success){
    				tiposDocumento = Ext.util.JSON.decode (response.responseText);
    				if (tiposDocumento.success){
   						//Pueden llegar mensajes informativos.
    					if (tiposDocumento.message && tiposDocumento.message.length>0){
	    					Ext.each(tiposDocumento.message,function(message){
		    					Ext.Msg.show({
								   title:'Información',
								   cls:'cgMsgBox',
								   msg: '<span>'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
		    				});
	    				}
	    				

	    				var arrayCombo = new Array();
	    				arrayCombo.push({texto: "", valor: ""});
	    				Ext.each(tiposDocumento.datos,function(dato){
	    					arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});
	    				});
						agregarValoresCombo('selTipoDoc', arrayCombo, true);
						
						//Si venimos del Detalle hay datos en el historial, por tanto lanzamos la busqueda anterior
						//if (!Ext.get('historialBuscador').child('li').hasClass('noHist')){
						if (Ext.get('hiddenVueltaFC').dom.value == "accedido"){
							buscarVuelta();
						}
						
    				}else{
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span>'+tiposDocumento.message+'</span><br/>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
    				}
    			}
    		}
    	});
    }
    
    var obtenerDatos = function (){
    
    	var uNegocio = "";
    	var mMovil = "";
    	var codCliente = "";
    	var tipDocumento = "";   	
    	var numDocumento = "";
    	var razSocial = "";
    	var nomCliente = "";
    	var apellido1 = "";
    	var apellido2 = "";
    	
    	//Limpiamos los datos    	
    	Ext.get('divResultados').setVisibilityMode(Ext.Element.DISPLAY).hide();			
    	   		
    	if (Ext.get('selNegocio').dom.options[Ext.get('selNegocio').dom.selectedIndex].value == "F")
    	{
			uNegocio = "F";    		
    		mMovil = "N";
    	}
    	else if (Ext.get('selNegocio').dom.options[Ext.get('selNegocio').dom.selectedIndex].value == "M")
    	{
			uNegocio = "M";    	
    		mMovil = "C";
    	}
    	else if(Ext.get('selNegocio').dom.options[Ext.get('selNegocio').dom.selectedIndex].value == "P")
    	{
    		uNegocio = "M";  
			mMovil = "P";
    	}
    	else
    	{
			uNegocio = "";  
			mMovil = "";  	
    	}
    	
    	if(Ext.get('radCod').dom.checked){
    		codCliente = Ext.get('txtCodCliente').dom.value;
    	}
    	
		if(Ext.get('radTipoDoc').dom.checked){
			tipDocumento = Ext.get('selTipoDoc').dom.value;
			numDocumento = Ext.get('txtNumeroDoc').dom.value;
		}
		
		if(Ext.get('radNombre').dom.checked){
			nomCliente = Ext.get('txtNombreCliente').dom.value;
			apellido1 = Ext.get('txtApellido1').dom.value;
			apellido2 = Ext.get('txtApellido2').dom.value;
		}
		
		if(Ext.get('radRazon').dom.checked){
			razSocial = Ext.get('txtRazonSocial').dom.value;
		}    
    
    	var cliente = {
			unidadNegocio:uNegocio,
			modalidadMovil:mMovil,
			codigoCliente:codCliente,
			tipoDocumento:tipDocumento,		
			numeroDocumento:numDocumento,
			razonSocial:razSocial,
			nombreCliente:nomCliente,
			primerApellido:apellido1,
			segundoApellido:apellido2
		};
		
		myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
		myMask.show();
			
    	Ext.Ajax.request({
    		url:contexto + 'ListaClientes.do',
    		params:{
    			buscar:'',
    			listaClientesJSON:Ext.encode(cliente)
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
	    				
	    				//Si no llegan resultados mostramos un mensaje y no mostramos el grid
	    				if(datosResultado.total == 0){
	    					Ext.Msg.show({
							   title:'Información',
							   cls:'cgMsgBox',
							   msg: '<span>No se han encontrado clientes que coincidan con los criterios de búsqueda introducidos.</span><br/>',
							   buttons: Ext.Msg.OK,
							   icon: Ext.MessageBox.INFO
							});
	    				}else{
	    					Ext.each(datosResultado.datos,function(dato){
	    						if (dato.inUniNgcCliGbl == "F"){
	    							dato.unidadNegocio = "LNF";
	    						}
	    						else if (dato.inUniNgcCliGbl == "M"){
	    							dato.unidadNegocio = "LNM";
	    						}    						
	    						else{
	    							dato.unidadNegocio = "Convergente";
	    						}
	    						if (dato.inModalidad == "C"){
	    							dato.modalidadMovil = "Contrato";
	    						}
	    						else if (dato.inModalidad == "P"){
	    							dato.modalidadMovil = "Prepago";
	    						}    						
	    						else if (dato.inModalidad == "A"){
	    							dato.modalidadMovil = "Ambas";
	    						}
	    						else{
	    							dato.modalidadMovil = "";
	    						}	    						    						
		    				});
		    				
		    				Ext.get('divResultados').setVisibilityMode(Ext.Element.DISPLAY).show();
							Ext.getCmp('gridBuscadorClientes').getStore().loadData (datosResultado.datos);
						
							Ext.get('totalReg').dom.innerHTML = '['+datosResultado.total+']';
							
							ultimaBusqueda.add('unidadNegocio',uNegocio);
							ultimaBusqueda.add('modalidadMovil',mMovil);							
							ultimaBusqueda.add('codigoCliente',codCliente);
							ultimaBusqueda.add('tipoDocumento',tipDocumento);
							ultimaBusqueda.add('numeroDocumento',numDocumento);
							ultimaBusqueda.add('razonSocial',razSocial);
							ultimaBusqueda.add('nombreCliente',nomCliente);
							ultimaBusqueda.add('primerApellido',apellido1);
							ultimaBusqueda.add('segundoApellido',apellido2);
	    				}
    				}else{
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span>'+datosResultado.message+'</span><br/>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
    				}
    			}
    		}
    	});
    }
   
	var pintarGrid = function (){
	 
	   // create the data store
	    var store = new Ext.data.Store({
	    	reader: new Ext.data.ObjectReader({}, [
				{name: 'unidadNegocio', type: 'string'},
				{name: 'modalidadMovil', type: 'string'},				
				{name: 'codigoCliente', type: 'string'},
				{name: 'dsTipoDocumento', type: 'string'},			
				{name: 'numeroDocumento', type: 'string'},
				{name: 'nombreRazonSocial', type: 'string'},
				{name: 'estadoCliente', type: 'string'},
				{name: 'segmentoSubsegmento', type: 'string'},
				{name: 'nombreCliente', type: 'string'},
				{name: 'primerApellido', type: 'string'},
				{name: 'segundoApellido', type: 'string'},
				{name: 'razonSocial', type: 'string'},
				{name: 'codigoSegmento', type: 'string'},
				{name: 'codigoSubsegmento', type: 'string'},
				{name: 'tipoDocumento', type: 'string'},
				{name: 'dsSegmento', type: 'string'},
				{name: 'dsSubsegmento', type: 'string'},
				{name: 'ocultarHistorico'}
			])
	    });
	    
	     var actionsHistorico = new Ext.ux.grid.RowActions({
			header: '',
			autoWidth: false,
			width:30,
			actions: [{
				iconCls: 'iconoHistorico centrarIco',
				align:'right',
				hideIndex:'ocultarHistorico',
				callback: function(g, record, action, rowIndex, colIndex) {
					abrirPopUpHistorico(record.data);
				}
			}]
		});
	    
	  	var columnModel = new Ext.grid.ColumnModel([
		    {header: 'Origen',sortable: true,width:67,  dataIndex: 'unidadNegocio'},
		    {header: 'Modalidad',sortable: true,width:67,  dataIndex: 'modalidadMovil'},		    		    
            {header: 'C&oacute;digo cliente Global', sortable: true,width:115, dataIndex: 'codigoCliente'},
            {header: 'Tipo doc.', sortable: true,width:56, dataIndex: 'dsTipoDocumento'},
            {header: 'Doc. identif.', sortable: true,width:112, dataIndex: 'numeroDocumento'},
          	{header: 'Nombre/Raz&oacute;n social',sortable: true,width:156, dataIndex: 'nombreRazonSocial', id:'colNombre'},
          	{header: 'Estado',sortable: true,width:78, dataIndex: 'estadoCliente'},
          	{header: 'Segmento/subsegm.',sortable: true,width:118, dataIndex: 'segmentoSubsegmento',renderer: addTooltip},
          	actionsHistorico
		]);

		var cargarDatosFila = function(fila){
			Ext.each(Ext.query('.marcarFila', grid.id), function(filaMarcada) {
				Ext.fly(filaMarcada).removeClass('marcarFila');
			});
			Ext.fly(grid.getView().getRow(fila)).addClass('marcarFila');
			
			filaSeleccionada = grid.getStore().data.items[fila].data
			Ext.get('btnVerDetalle').dom.disabled = false;
			Ext.get('btnVerDetalle').dom.src = contexto + 'images/botones/QGbtVerDetalle.gif';	
		}

	   // create the Grid
	    grid = new Ext.grid.EditorPasteCopyGridPanel({
	    	id:'gridBuscadorClientes',
	        store: store,
			renderTo: 'divGrid',
			autoExpandColumn: 'colNombre',
			cls:'gridCG',
			selectionModel: '',
	    	funcionRollBack :cargarDatosFila,
			cm: columnModel,
	        stripeRows: true,
	        plugins: [actionsHistorico],
	        viewConfig: {
				forceFit:true
				},
	        height: 177
	    });
	}
	
	var pintarGridHistorico = function (){
	
	   // create the data store
	    var storeHistorico = new Ext.data.Store({
	    	reader: new Ext.data.ObjectReader({}, [
				{name: 'codSegmento', type: 'string'},
				{name: 'desSegmento', type: 'string'},
				{name: 'codSubSegmento', type: 'string'},
				{name: 'desSubSegmento', type: 'string'},
				{name: 'fecMod', type: 'string',sortType:convertirEnFecha}
			])
	    });
	    
	  	var columnModelHistorico = new Ext.grid.ColumnModel([ 
            {header: 'C&oacute;d. Segmento', sortable: true,width: 120, align:'center', dataIndex: 'codSegmento'},
            {header: 'Des. Segmento', sortable: true,width: 110,id:'colDesc', align:'center', dataIndex: 'desSegmento'},
          	{header: 'C&oacute;d. Subsegmento',sortable: true, align:'center', dataIndex: 'codSubSegmento'},
          	{header: 'Des. Subsegmento', sortable: true, align:'center',width: 110, dataIndex: 'desSubSegmento'},
          	{header: 'F. Modificaci&oacute;n', sortable: true,width: 110, align:'center', dataIndex: 'fecMod'}
		]);
		
		var cargarDatosFilaHist = function(fila){
			Ext.each(Ext.query('.marcarFila', Ext.getCmp('gridHistorico').id), function(filaMarcada) {
				Ext.fly(filaMarcada).removeClass('marcarFila');
			});
			Ext.fly(Ext.getCmp('gridHistorico').getView().getRow(fila)).addClass('marcarFila');
			
			filaSeleccionadaHist = Ext.getCmp('gridHistorico').getStore().data.items[fila].data;
		}
			
	    // create the Grid
	    gridHistorico = new Ext.grid.EditorPasteCopyGridPanel({
	    	id:'gridHistorico',
	        store: storeHistorico,
	        selectionModel: '',
	        funcionRollBack: cargarDatosFilaHist,
			renderTo: 'divGridHistoricoCli',
			autoExpandColumn: 'colDesc',
			cls:'gridCG',
			width:740,
			cm: columnModelHistorico,
	        stripeRows: true,	  
	        height: 200
	    });
	}

	//funcion de respuesta de la llamada ajax en la busqueda de los datos de historico.
	var callBackResultadoBusquedaHistorico = function(correcto, datos){
			if(correcto){
				Ext.get('divGridHistoricoCli').setVisibilityMode(Ext.Element.DISPLAY).show();
				
				if (datos.length > 0){
					Ext.getCmp('gridHistorico').getStore().loadData (datos);
				}else{
					mostrarMensajeError("E$l cliente no ha tenido modificaciones de segmentaci&oacute;n")
				}
			}
	}

	var obtenerHistorico = function(criterioBusqueda){			
			var datos = {
				codigoCliente: criterioBusqueda.codigoCliente
			};
			llamadaAjax('ListaClientes.do','obtenerHistorico','listaClientesJSON',datos,callBackResultadoBusquedaHistorico,true);		
		}		
	
	
	
	var abrirPopUpHistorico = function (datos){
		var win = Ext.getCmp('winHistorico');
				 	
		obtenerHistorico(datos);
								
		Ext.get('txtCodCliGlo').dom.value =datos.codigoCliente;
		Ext.get('txtTipoDoc').dom.value =datos.dsTipoDocumento;
		Ext.get('txtNumDoc').dom.value =datos.numeroDocumento;
		Ext.get('txtNombre').dom.value =datos.nombreRazonSocial;
		Ext.get('txtOrigen').dom.value =datos.unidadNegocio;
		
		Ext.get('txtCodSegmento').dom.value =datos.codigoSegmento;
		Ext.get('txtDesSegmento').dom.value =datos.dsSegmento;
		Ext.get('txtCodSubSegmento').dom.value =datos.codigoSubsegmento;
		Ext.get('txtDesSubSegmento').dom.value =datos.dsSubsegmento;
		
		if(!win){
			win = new Ext.Window({
				id:'winHistorico',
				contentEl:'popUpHistorico',
				title:'Historial Segmentaci&oacute;n',
				width:780,
				height:400,
				closeAction:'hide',
				closable: true,
				animateTarget:'gridBuscadorClientes',
				resizable:false,
				minimizable:true,
				draggable:false,
				modal:true,
				listeners:{
					minimize : function(){
						minimizarVentana('winHistorico');
					},
					beforehide : function(){
						
					}
				}
			});
		}
		win.show();
	}
	
	
	var addTooltip = function(value,metadata, record, rowIndex, colIndex, store){
		//En record viene el elemento formado del grid
		var valorTooltip = "";
		//Valor de la descripcion de segmento/subsegmento
	 	valorTooltip = record.data.dsSegmento+"/"+record.data.dsSubsegmento;
		//Accedemos al tooltip
		if(valorTooltip != null && valorTooltip != ''){
			metadata.attr = 'ext:qtip="' + valorTooltip +'"';
		}
		//Debe devolver el valor para rellenar la columna
		return value;
    }
	
	var borrarHistorial = function(){
		if(!Ext.get('historialBuscador').child('li').hasClass('noHist')){
			Ext.Msg.show({
			   title:'Borrar historial.',
			   msg: '<span>Se van a borrar el historial de sesión, ¿Desea continuar?</span>',
			   buttons: Ext.Msg.YESNO,
			   icon: Ext.MessageBox.INFO,
			   fn:function(respuesta){
			   		if (respuesta == 'yes'){
			   			var nodo = '<li class="noHist"><div><span>No hay clientes en el historial.</span></div></li>';
			   			Ext.Ajax.request({
					   		url:contexto + 'ListaClientes.do',
					   		params:{
					   			borrarHistorial:''
					   		}
					   	});
					   	
					   	Ext.each(Ext.query('li', 'historialBuscador'), function(lineaCliente) {
				 			Ext.fly(lineaCliente).remove();
						});
						
						Ext.each(Ext.query('li', 'historial'), function(lineaCliente) {
				 			Ext.fly(lineaCliente).remove();
						});
						
						Ext.DomHelper.append(Ext.get('historialBuscador'), nodo);
						Ext.DomHelper.append(Ext.get('historial'), nodo);
						
						refrescarGrid.defer(20);
			   		}
			   }
			});
		}
	}
	
   // Controlamos los eventos sobre los botones
	var controlEventos = function() {
		
		Ext.get('marco').on('resize', function() {
			refrescarGrid.defer(20);
		});
		
		Ext.get('lnkBorrarHistorial').on('click', function(){
			borrarHistorial();
		});
		
		// evento click del botón Imprimir
		//Ext.get('btnImprimir').on('click', function() {
			//imprimirClientes();
		//});
		
		// evento click del botón Buscar
		Ext.get('btnBuscar').on('click', function() {
			obtenerDatos();
		});
		
		// evento click del botón Limpiar
		Ext.get('btnLimpiar').on('click', function() {
			limpiarBuscadorClientes();
		});
		
		// evento click del botón ver detalle
		Ext.get('btnVerDetalle').on('click', function() {
			if (filaSeleccionada != null) {
				verDetalleCliente();
			}
		});
		
		//Eventos para los radio del formulario de busqueda
		Ext.get('radCod').on('click', function() {
			habilitarFormularioBusqueda();
		});
		
		Ext.get('radTipoDoc').on('click', function() {
			habilitarFormularioBusqueda();
		});
		
		Ext.get('radNombre').on('click', function() {
			habilitarFormularioBusqueda();
		});
		
		Ext.get('radRazon').on('click', function() {
			habilitarFormularioBusqueda();
		});
		
		//Eventos para habilitar o deshabilitar el boton Buscar
		var retardo = 200;
		Ext.get('txtCodCliente').on('keyup', function() {
			controlCampos.defer(200);
		});
		
		Ext.get('txtCodCliente').on('cut', function() {
			controlCampos.defer(200);
		});
		
		Ext.get('txtCodCliente').on('paste', function() {
			controlCampos.defer(200);
		});
		
		//Campo de texto de tipo documento
		
		Ext.get('txtNumeroDoc').on('keyup', function() {
			controlCampos.defer(200);
		});
		
		Ext.get('txtNumeroDoc').on('cut', function() {
			controlCampos.defer(200);
		});
		
		Ext.get('txtNumeroDoc').on('paste', function() {
			controlCampos.defer(200);
		});
		
		//Campo de texto Nombre
		Ext.get('txtNombreCliente').on('keyup', function() {
			controlCampos.defer(200);
		});
		
		Ext.get('txtNombreCliente').on('cut', function() {
			controlCampos.defer(200);
		});
		
		Ext.get('txtNombreCliente').on('paste', function() {
			controlCampos.defer(200);
		});
		
		//Campo de texto Apellido 1
		Ext.get('txtApellido1').on('keyup', function() {
			controlCampos.defer(200);
		});
		
		Ext.get('txtApellido1').on('cut', function() {
			controlCampos.defer(200);
		});
		
		Ext.get('txtApellido1').on('paste', function() {
			controlCampos.defer(200);
		});
		
		//Campo de texto Apellido 2
		Ext.get('txtApellido2').on('keyup', function() {
			controlCampos.defer(200);
		});
		
		Ext.get('txtApellido2').on('cut', function() {
			controlCampos.defer(200);
		});
		
		Ext.get('txtApellido2').on('paste', function() {
			controlCampos.defer(200);
		});
		
		//Campo de texto Razon Social
		Ext.get('txtRazonSocial').on('keyup', function() {
			controlCampos.defer(200);
		});
		
		Ext.get('txtRazonSocial').on('cut', function() {
			controlCampos.defer(200);
		});
		
		Ext.get('txtRazonSocial').on('paste', function() {
			controlCampos.defer(200);
		});
		
		// evento click de los combos
		Ext.get('selNegocio').on('click', function() {		
			controlCampos.defer(200);
		});
		Ext.get('selTipoDoc').on('click', function() {
			controlCampos.defer(200);
		});
		
		//evento change de la combo de linea
		Ext.get('selNegocio').on('change', function(){
			var arrayCombo = new Array();
   			arrayCombo.push({texto: "", valor: ""});
   			Ext.each(tiposDocumento.datos,function(dato){
				if (Ext.get('selNegocio').dom.options[Ext.get('selNegocio').dom.selectedIndex].value == "F")
				{
					//cargarTipoDocumento("01","");
	   				if (dato.linea == "01" || dato.linea == "03")
	   					arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});	   													
				}
				else if (Ext.get('selNegocio').dom.options[Ext.get('selNegocio').dom.selectedIndex].value == "M")
				{
					//cargarTipoDocumento("02","M");
					if ((dato.linea == "02" || dato.linea == "03") && (dato.modalidad == "C" || dato.modalidad == "A"))
	   					arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});
				}
				else if(Ext.get('selNegocio').dom.options[Ext.get('selNegocio').dom.selectedIndex].value == "P")
				{
					//cargarTipoDocumento("02","P");
					if ((dato.linea == "02" || dato.linea == "03") && (dato.modalidad == "P" || dato.modalidad == "A"))
	   					arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});					
				}			
				else
					//cargarTipoDocumento("03","");
					arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});
			});	
			agregarValoresCombo('selTipoDoc', arrayCombo, true);				
		});
		
		Ext.get('btnVolver').on('click', function() {
			Ext.getCmp('winHistorico').hide();
		});	
		
		if (Ext.isIE)
			document.getElementById('ifExportar').onreadystatechange = function(){myMask.hide();};	
		
		if (Ext.isIE6){ //Se usa para que en IE6 no se superponga los combos al menu
			Ext.get('liAdministracion').on('mouseover', function() {
				Ext.get('selNegocio').setVisibilityMode(Ext.Element.DISPLAY).hide();
				Ext.get('selTipoDoc').setVisibilityMode(Ext.Element.DISPLAY).hide();
			});
			Ext.get('liAdministracion').on('mouseout', function() {
				Ext.get('selNegocio').setVisibilityMode(Ext.Element.DISPLAY).show();
				Ext.get('selTipoDoc').setVisibilityMode(Ext.Element.DISPLAY).show();
			});
			
			Ext.get('liSegmentacion').on('mouseover', function() {
				Ext.get('selNegocio').setVisibilityMode(Ext.Element.DISPLAY).hide();
				Ext.get('selTipoDoc').setVisibilityMode(Ext.Element.DISPLAY).hide();
			});
			Ext.get('liSegmentacion').on('mouseout', function() {
				Ext.get('selNegocio').setVisibilityMode(Ext.Element.DISPLAY).show();
				Ext.get('selTipoDoc').setVisibilityMode(Ext.Element.DISPLAY).show();
			});
			
			Ext.get('liInformes').on('mouseover', function() {
				Ext.get('selNegocio').setVisibilityMode(Ext.Element.DISPLAY).hide();
			});
			Ext.get('liInformes').on('mouseout', function() {
				Ext.get('selNegocio').setVisibilityMode(Ext.Element.DISPLAY).show();
			});
		}
		
			
		asignarEventosHistorialBusquedaClientes.defer(200);
	}
	
	var refrescarGrid = function() {
		if (Ext.isIE6){
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).hide();
			var ancho = document.body.offsetWidth - 295;
			
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).show();
			grid.setWidth (ancho);
		}
	}
	
	var asignarEventosHistorialBusquedaClientes = function (){
		Ext.each(Ext.query('li div', 'historialBuscador'), function(lineaCliente) {
 			Ext.fly(lineaCliente).on('click', function() {
				verDetalleClienteDesdeHistorial(lineaCliente.getAttribute('idCliente'));
			});
			
			Ext.fly(lineaCliente).on('mouseover', function() {
				anadirHover(lineaCliente.id);
			});
		});		
	}
	
	var anadirHover = function (id){
		Ext.get(id).addClassOnOver('hover');
	}
	
	var verDetalleCliente = function (){					
		Ext.get('historialOrigen').dom.value = filaSeleccionada.unidadNegocio;
		Ext.get('historialModalidad').dom.value = filaSeleccionada.modalidadMovil;		
		Ext.get('historialCodCliente').dom.value = filaSeleccionada.codigoCliente;
		Ext.get('historialTipoDoc').dom.value = filaSeleccionada.dsTipoDocumento;
		Ext.get('historialDocIdentif').dom.value = filaSeleccionada.numeroDocumento;
		Ext.get('historialRazonSocial').dom.value = filaSeleccionada.razonSocial;
		Ext.get('historialNombre').dom.value = filaSeleccionada.nombreCliente;
		Ext.get('historialPrimerApellido').dom.value = filaSeleccionada.primerApellido;
		Ext.get('historialSegundoApellido').dom.value = filaSeleccionada.segundoApellido;
		Ext.get('historialEstado').dom.value = filaSeleccionada.estadoCliente;
		Ext.get('historialSegmento').dom.value = filaSeleccionada.codigoSegmento;
		Ext.get('historialSubsegmento').dom.value = filaSeleccionada.codigoSubsegmento;
		
		var historial = {
			codCliente:Ext.get('historialCodCliente').dom.value,
			nombre:filaSeleccionada.razonSocial,
			fechaConsulta:new Date(),
			origen:Ext.get('historialOrigen').dom.value,
			modalidad:Ext.get('historialModalidad').dom.value,			
			tipoDocumento:Ext.get('historialTipoDoc').dom.value,
			numDocumento:Ext.get('historialDocIdentif').dom.value,
			codSegmento:Ext.get('historialSegmento').dom.value,
			codSubSegmento:Ext.get('historialSubsegmento').dom.value,
			estado:Ext.get('historialEstado').dom.value
		};
		
		if(historial.nombre == ""){
			historial.nombre = filaSeleccionada.nombreCliente + ' ' 
				+ filaSeleccionada.primerApellido + ' '
				+ filaSeleccionada.segundoApellido;
		}		
 		
 		irDetalleCliente(historial,"busqueda");
	}
	
	var habilitarBuscar = function() {
		Ext.get('btnBuscar').dom.disabled = false;
		Ext.get('btnBuscar').dom.src = contexto + 'images/botones/QGbtBuscar.gif';
	}
	
	var deshabilitarBuscar = function() {
		Ext.get('btnBuscar').dom.disabled = true;
		Ext.get('btnBuscar').dom.src = contexto + 'images/botones/QGbtBuscar_des.gif';
	}
	
	var habilitarLimpiar = function() {
		Ext.get('btnLimpiar').dom.disabled = false;
		Ext.get('btnLimpiar').dom.src = contexto + 'images/botones/QGbtLimpiar.gif';
	}
	
	var deshabilitarLimpiar = function() {
		Ext.get('btnLimpiar').dom.disabled = true;
		Ext.get('btnLimpiar').dom.src = contexto + 'images/botones/QGbtLimpiar_des.gif';
	}
	
	var controlCampos = function() {
	
		var codigo = false;
		var tipoDoc = false;
		var nombre = false;
		var razon = false;
		
		if (Ext.get('radCod').dom.checked){
			if (Ext.get('txtCodCliente').dom.value.trim().length == 0 || Ext.get('selNegocio').dom.selectedIndex == 0){
				codigo = false;
			}else{
				codigo = true;
			}
		}
		
		if (Ext.get('radTipoDoc').dom.checked){
			if (Ext.get('txtNumeroDoc').dom.value.trim().length == 0){
				tipoDoc = false;
			}else{
				tipoDoc = true;
			}
		}
		
		if (Ext.get('radNombre').dom.checked){
			if (Ext.get('txtNombreCliente').dom.value.trim().length == 0 ||
				Ext.get('txtApellido1').dom.value.trim().length == 0){
				nombre = false;
			}else{
				nombre = true;
			}
		}
		
		if (Ext.get('radRazon').dom.checked){
			if (Ext.get('txtRazonSocial').dom.value.trim().length == 0){
				razon = false;
			}else{
				razon = true;
			}
		}
		
		if (codigo || tipoDoc || nombre || razon){
			habilitarBuscar();
		}else{
			deshabilitarBuscar();
		}
	}
	
    var activarComboLinea = function(){
   		//Activamos el combo linea de negocio
		Ext.get('selNegocio').removeClass('dis');
		Ext.get('selNegocio').dom.readOnly = false;
		
		var arrayCombo = new Array();
		arrayCombo.push({texto: "   ", valor: ""});
    	arrayCombo.push({texto: "LNF", valor: "F"});
    	arrayCombo.push({texto: "LNM/Contrato", valor: "M"});
    	arrayCombo.push({texto: "LNM/Prepago", valor: "P"});    	
    	agregarValoresCombo('selNegocio', arrayCombo, true);
    }
	
	
	var habilitarFormularioBusqueda = function() {
		controlCampos();
		habilitarLimpiar();
		
		if (Ext.get('radCod').dom.checked){
			Ext.get('txtCodCliente').removeClass('dis');
			Ext.get('txtCodCliente').dom.readOnly = false;
		}else{		
			Ext.get('txtCodCliente').addClass('dis');
			Ext.get('txtCodCliente').dom.readOnly = true;
			Ext.get('txtCodCliente').dom.value='';
		}
		
		if (Ext.get('radTipoDoc').dom.checked){
			Ext.get('txtNumeroDoc').removeClass('dis');
			Ext.get('txtNumeroDoc').dom.readOnly = false;
			Ext.get('selTipoDoc').dom.disabled = false;
		}else{
			Ext.get('txtNumeroDoc').addClass('dis');
			Ext.get('txtNumeroDoc').dom.readOnly = true;
			Ext.get('selTipoDoc').dom.disabled = true;
			Ext.get('selTipoDoc').dom.selectedIndex = 0;
			Ext.get('txtNumeroDoc').dom.value='';
		}
		
		if (Ext.get('radNombre').dom.checked){
			Ext.get('txtNombreCliente').removeClass('dis');
			Ext.get('txtNombreCliente').dom.readOnly = false;
			Ext.get('txtApellido1').removeClass('dis');
			Ext.get('txtApellido1').dom.readOnly = false;
			Ext.get('txtApellido2').removeClass('dis');
			Ext.get('txtApellido2').dom.readOnly = false;
		}else{
			Ext.get('txtNombreCliente').addClass('dis');
			Ext.get('txtNombreCliente').dom.readOnly = true;
			Ext.get('txtNombreCliente').dom.value='';
			Ext.get('txtApellido1').addClass('dis');
			Ext.get('txtApellido1').dom.readOnly = true;
			Ext.get('txtApellido1').dom.value='';
			Ext.get('txtApellido2').addClass('dis');
			Ext.get('txtApellido2').dom.readOnly = true;
			Ext.get('txtApellido2').dom.value='';
		}
		
		if (Ext.get('radRazon').dom.checked){
			Ext.get('txtRazonSocial').removeClass('dis');
			Ext.get('txtRazonSocial').dom.readOnly = false;
		}else{
			Ext.get('txtRazonSocial').addClass('dis');
			Ext.get('txtRazonSocial').dom.readOnly = true;
			Ext.get('txtRazonSocial').dom.value='';
		}	
	}
	
	var habilitarBusquedaCodigo = function() {
		Ext.get('txtCodCliente').removeClass('dis');
		Ext.get('txtCodCliente').dom.readOnly = false;
		Ext.get('txtCodCliente').dom.value = '';
		Ext.get('radTipoDoc').dom.checked = false;
		Ext.get('radNombre').dom.checked = false;
		Ext.get('radRazon').dom.checked = false;
	}
	
	
	
	var limpiarBuscadorClientes = function() {		
		Ext.get('selNegocio').dom.selectedIndex = 0;
		Ext.get('txtCodCliente').dom.value = "";
		Ext.get('selTipoDoc').dom.selectedIndex = 0;
		Ext.get('txtNumeroDoc').dom.value = "";
		Ext.get('txtNombreCliente').dom.value = "";
		Ext.get('txtApellido1').dom.value = "";
		Ext.get('txtApellido2').dom.value = "";
		Ext.get('txtRazonSocial').dom.value = "";
		Ext.get('radCod').dom.checked = false;
		Ext.get('radTipoDoc').dom.checked = false;
		Ext.get('radNombre').dom.checked = false;
		Ext.get('radRazon').dom.checked = false;
		
		inicializarFormulario();
	}
	
	var imprimirClientes = function() {
		if (Ext.isIE){
			myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
			myMask.show();
		}
	
    	var cliente = {
			unidadNegocio:ultimaBusqueda.get('unidadNegocio'),
			modalidadMovil:ultimaBusqueda.get('modalidadMovil'),		
			codigoCliente:ultimaBusqueda.get('codigoCliente'),
			tipoDocumento:ultimaBusqueda.get('tipoDocumento'),
			numeroDocumento:ultimaBusqueda.get('numeroDocumento'),
			razonSocial:ultimaBusqueda.get('razonSocial'),
			nombreCliente:ultimaBusqueda.get('nombreCliente'),
			primerApellido:ultimaBusqueda.get('primerApellido'),
			segundoApellido:ultimaBusqueda.get('segundoApellido')
		};
		
		document.getElementById('ifExportar').src = contexto + 'ListaClientes.do?exportarPDF=&listaClientesJSON='+Ext.encode(cliente);	
	}

	var inicializarFormulario = function() {
		Ext.get('txtCodCliente').addClass('dis');
		Ext.get('txtCodCliente').dom.readOnly = true;
		Ext.get('txtNumeroDoc').addClass('dis');
		Ext.get('txtNumeroDoc').dom.readOnly = true;
		Ext.get('txtNombreCliente').addClass('dis');
		Ext.get('txtNombreCliente').dom.readOnly = true;
		Ext.get('txtApellido1').addClass('dis');
		Ext.get('txtApellido1').dom.readOnly = true;
		Ext.get('txtApellido2').addClass('dis');
		Ext.get('txtApellido2').dom.readOnly = true;
		Ext.get('txtRazonSocial').addClass('dis');
		Ext.get('txtRazonSocial').dom.readOnly = true;
		
		Ext.get('selTipoDoc').dom.disabled = true;
		
		deshabilitarBuscar();
		deshabilitarLimpiar();
	}
	
	var ocultarResultados = function() {
		Ext.get('divResultados').setVisibilityMode(Ext.Element.DISPLAY).hide();
	}
	
	var buscarVuelta = function (){

		//Cargamos el combo por si fuera necesario
		//Activamos el combo linea de negocio
		activarComboLinea();

		myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
		myMask.show();
			
    	Ext.Ajax.request({
    		url:contexto + 'ListaClientes.do',
    		params:{
    			buscarVueltaAtras:''
    		},
    		callback:function (options, success, response){
    			myMask.hide();
    			if (success){
    				if (response.responseText != ""){
    			
	    				datosResultado = Ext.util.JSON.decode (response.responseText);
	    				
	    				if (datosResultado.success){
	    					if (datosResultado.datos[0] != null){
								respBusqueda(datosResultado.datos[0]);
								//Lanzamos la busqueda de datos con los resultados recuperados de la anterior busqueda
								obtenerDatos();	
							}
	    				}else{
	    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
	    					Ext.Msg.show({
							   title:'Error',
							   cls:'cgMsgBox',
							   msg: '<span>'+datosResultado.message+'</span><br/>',
							   buttons: Ext.Msg.OK,
							   icon: Ext.MessageBox.ERROR
							});
	    				}
	    			}
    			}
    		}
    	});
    }
	
	var buscarTipoDoc = function(dato){
	
		for(var i=0;i<Ext.get('selTipoDoc').dom.options.length;i++){
			if(Ext.get('selTipoDoc').dom.options[i].value == dato){
				 Ext.get('selTipoDoc').dom.selectedIndex = i;
			}
		}
	}
	
	//Funcion que coloca los datos recuperados de la busqueda anterior en pantalla
	var respBusqueda = function(datos){
		deshabilitarBuscar();
		habilitarLimpiar();	
		
		if (datos.unidadNegocio != "")
		{
			if (datos.unidadNegocio == "F")
				Ext.get('selNegocio').dom.value = "F";
			else
				Ext.get('selNegocio').dom.value = datos.modalidadMovil;
		}	
		//Ext.get('selNegocio').dom.value = datos.unidadNegocio;
		
		if (datos.codigoCliente != ""){
			Ext.get('txtCodCliente').dom.value = datos.codigoCliente;
			Ext.get('radCod').dom.checked = true;
			Ext.get('txtCodCliente').removeClass('dis');
			Ext.get('txtCodCliente').dom.readOnly = false;
			habilitarBuscar();
		}else{
			Ext.get('txtCodCliente').addClass('dis');
			Ext.get('txtCodCliente').dom.readOnly = true;
			Ext.get('txtCodCliente').dom.value='';
		}
		
		if (datos.numeroDocumento != ""){
			Ext.get('radTipoDoc').dom.checked = true;
			
			Ext.get('txtNumeroDoc').dom.value = datos.numeroDocumento;	
			buscarTipoDoc(datos.tipoDocumento);
			
			Ext.get('txtNumeroDoc').removeClass('dis');
			Ext.get('txtNumeroDoc').dom.readOnly = false;
			Ext.get('selTipoDoc').dom.disabled = false;
			habilitarBuscar();
		}else{
			Ext.get('txtNumeroDoc').addClass('dis');
			Ext.get('txtNumeroDoc').dom.readOnly = true;
			Ext.get('selTipoDoc').dom.disabled = true;
			Ext.get('selTipoDoc').dom.selectedIndex = 0;
			Ext.get('txtNumeroDoc').dom.value='';
		}
		
		if (datos.nombreCliente != ""){
			Ext.get('radNombre').dom.checked = true;
			
			Ext.get('txtNombreCliente').dom.value = datos.nombreCliente;
			Ext.get('txtApellido1').dom.value = datos.primerApellido;
			Ext.get('txtApellido2').dom.value = datos.segundoApellido;
			
			Ext.get('txtNombreCliente').removeClass('dis');
			Ext.get('txtNombreCliente').dom.readOnly = false;
			Ext.get('txtApellido1').removeClass('dis');
			Ext.get('txtApellido1').dom.readOnly = false;
			Ext.get('txtApellido2').removeClass('dis');
			Ext.get('txtApellido2').dom.readOnly = false;
			habilitarBuscar();
		}else{
			Ext.get('txtNombreCliente').addClass('dis');
			Ext.get('txtNombreCliente').dom.readOnly = true;
			Ext.get('txtNombreCliente').dom.value='';
			Ext.get('txtApellido1').addClass('dis');
			Ext.get('txtApellido1').dom.readOnly = true;
			Ext.get('txtApellido1').dom.value='';
			Ext.get('txtApellido2').addClass('dis');
			Ext.get('txtApellido2').dom.readOnly = true;
			Ext.get('txtApellido2').dom.value='';
		}
		
		if (datos.razonSocial != ""){
			Ext.get('radRazon').dom.checked = true;
		
			Ext.get('txtRazonSocial').dom.value = datos.razonSocial;
		
			Ext.get('txtRazonSocial').removeClass('dis');
			Ext.get('txtRazonSocial').dom.readOnly = false;
			habilitarBuscar();
		}else{
			Ext.get('txtRazonSocial').addClass('dis');
			Ext.get('txtRazonSocial').dom.readOnly = true;
			Ext.get('txtRazonSocial').dom.value='';
		}
	}
	
	
	return {
		init: function (){
			Ext.QuickTips.init();
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			
			activarComboLinea();
			
			ocultarResultados();
			inicializarFormulario();
			
			cargarTipoDocumento("03","");
			
			pintarGrid();
			pintarGridHistorico();
			
			if (Ext.isIE6){
				var ancho = document.body.offsetWidth - 295;
				grid.setWidth (ancho);
			}
			
			controlEventos();

		}
	}
	
}();

Ext.onReady (CGLOBAL.init, CGLOBAL, true);