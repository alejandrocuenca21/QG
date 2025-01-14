var CGLOBAL = function (){
	var grid = null;
	var datosResultado;	
	//variable para guardar el �ltimo modo de busqueda "filtrar" o "todo" para utilizarla despues de eliminar
	var ultimaBusquedaTipo;
	//criterios de la ultima busqueda realizada.
	var ultimaBusqueda = new Ext.util.MixedCollection();
	//Bandera que indica si se debe eliminar o hay que pedir confirmacion
	var eliminarConfirmado = false;
	

	//Funcion que validara el el nuevo elemento con respecto a los que haya en la lista
	var comprobarDuplicidad = function (keyNueva,intervaloInicialNuevo,intervaloFinalNuevo){
		if(keyNueva != null && intervaloInicialNuevo != null && intervaloFinalNuevo != null){
			//Si no hay elementos en la lista no se valida
			if(grid != null && grid.getStore() != null && grid.getStore().getCount() > 0){
				var i = 0;
				var error = false;
				//Recorremos el grid
				while(!error && i < grid.getStore().getCount()){
					var elemento = grid.getStore().getAt(i);
					//Si la key es igual hay que comprobar las fechas
					if(elemento.data.keySegmentacion == keyNueva){
					
						//Fechas del elemento que estamos recorriendo
						var fechaElementoGridInicial = Date.parseDate(elemento.data.fechaIniVigencia,'d.m.Y');
						var fechaElementoGridFinal = Date.parseDate(elemento.data.fechaFinVigencia,'d.m.Y');
						
						//Si la fecha inicial o final nueva esta dentro del intervalo del grid 
						if(intervaloInicialNuevo.between(fechaElementoGridInicial,fechaElementoGridFinal) ||
								intervaloFinalNuevo.between(fechaElementoGridInicial,fechaElementoGridFinal)){
							error = true;
						}
						//Si la fecha de inicio de la nueva es anterior a la fecha inicial y la final es posterior a la final
						if(intervaloInicialNuevo.getTime() < fechaElementoGridInicial.getTime() && intervaloFinalNuevo.getTime() > fechaElementoGridFinal.getTime()){
							error = true;
						}
					}
					i++;
				}	
				
				return error;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}
	
	//se carga el combo de segmentos para filtrado y para alta.
	var cargarCodigoSegmento = function (){
		myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
		myMask.show();
		Ext.Ajax.request({
			url:contexto + 'Segmentaciones.do',
			params:{
			cargarCodigosSegmento:''
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
								title:'Informaci�n',
								cls:'cgMsgBox',
								msg: '<span>'+message+'</span><br/>',
								buttons: Ext.Msg.OK,
								icon: Ext.MessageBox.INFO
							});
						});
					}

					var arrayCombo = new Array();
					var arrayComboAlta = new Array();
					arrayCombo.push({texto: "Todos", valor: ""});
					arrayComboAlta.push({texto: "", valor: ""});

					Ext.each(datosResultado.datos,function(dato){
						arrayCombo.push({texto: dato.codigo, valor: dato.descripcion});
						arrayComboAlta.push({texto: dato.codigo, valor: dato.descripcion});//para los combos de la pantalla de alta

					});
					agregarValoresCombo('selCodSeg', arrayCombo, true);//combo de la busqueda
					agregarValoresCombo('selCodSegAS', arrayComboAlta, true);//combo del alta origen

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
	//carga de los combos de subsegmentos
	var cargarSubSegmento = function(pantalla){
		myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
		myMask.show();


		var campoSegmento;
		var campoSubsegmento;
		var campoSubsegmento2;
		var campoDescripcionSubsegmento;
		var campoDescripcionSubsegmento2;

		if(pantalla == "busqueda"){//si es la pantalla de busqueda, trabajamos con los combos de filtrado
			campoSegmento = "selCodSeg";
			campoSubsegmento = "selCodSubSeg";
			campoDescripcionSubsegmento="txtDescripcionSubSeg";
			campoSubsegmento2='';
			campoDescripcionSubsegmento2='';
		}else if(pantalla == "alta"){//si es la pantalla de alta, trabajamos con los combos del alta.
			campoSegmento = "selCodSegAS";
			campoSubsegmento = "selCodSubSegAS";
			campoDescripcionSubsegmento="txtDescripcionSubSegAS";
			campoSubsegmento2='selCodSubSegDestAS';
			campoDescripcionSubsegmento2='txtDescSubSegDestAS';

		}


		var codSegmento = Ext.get(campoSegmento).dom.options[Ext.get(campoSegmento).dom.selectedIndex].innerHTML;




		if(Ext.get(campoSegmento).dom.options[Ext.get(campoSegmento).dom.selectedIndex].value==''){
			var arrayCombo = new Array();
			if(pantalla == "busqueda"){
				arrayCombo.push({texto: "Todos", valor: ""});
			}else{
				arrayCombo.push({texto: "", valor: ""});
			}

			agregarValoresCombo(campoSubsegmento, arrayCombo, true);
			Ext.get(campoDescripcionSubsegmento).dom.value = '';
			if(campoSubsegmento2 != ''){
				agregarValoresCombo(campoSubsegmento2, arrayCombo, true);
				Ext.get(campoDescripcionSubsegmento2).dom.value = '';
			}
			myMask.hide();
		}else{

			Ext.Ajax.request({
				url:contexto + 'Segmentaciones.do',
				params:{
				cargarCodigosSubSegmento:'',
				codSegmentoSeleccionado:codSegmento
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
									title:'Informaci�n',
									cls:'cgMsgBox',
									msg: '<span>'+message+'</span><br/>',
									buttons: Ext.Msg.OK,
									icon: Ext.MessageBox.INFO
								});
							});
						}
						var arrayCombo = new Array();
						if(pantalla == "busqueda"){
							arrayCombo.push({texto: "Todos", valor: ""});
						}else{
							arrayCombo.push({texto: "", valor: ""});
						}
						Ext.each(datosResultado.datos,function(dato){
							arrayCombo.push({texto: dato.codigo, valor: dato.descripcion});																
						});

						agregarValoresCombo(campoSubsegmento, arrayCombo, true);					
						Ext.get(campoDescripcionSubsegmento).dom.value = '';
						if(campoSubsegmento2 != ''){
							agregarValoresCombo(campoSubsegmento2, arrayCombo, true);
							Ext.get(campoDescripcionSubsegmento2).dom.value = '';
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
	}


	//boton filtrar
	var obtenerDatos = function (boton){

		//Tenemos que comprobar si el boton ha sido pulsado en algun momento
		if(boton != null){
			
			var criterioBusquedaSegmentacion={};
			if(boton != "todos"){    					
	
				criterioBusquedaSegmentacion.unidad = Ext.get('selUnidad').dom.options[Ext.get('selUnidad').dom.selectedIndex].value;
	
				criterioBusquedaSegmentacion.operacion = Ext.get('selOperacion').dom.options[Ext.get('selOperacion').dom.selectedIndex].value;
	
				if(Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].innerHTML != 'Todos'){
					criterioBusquedaSegmentacion.codigoSegmentoOrigen = Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].innerHTML;
				}
				if(Ext.get('selCodSubSeg').dom.options[Ext.get('selCodSubSeg').dom.selectedIndex].innerHTML != 'Todos'){
					criterioBusquedaSegmentacion.codigoSubSegmentoOrigen = Ext.get('selCodSubSeg').dom.options[Ext.get('selCodSubSeg').dom.selectedIndex].innerHTML;
				}
			}
			myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
			myMask.show();
	
			Ext.Ajax.request({
				url:contexto + 'Segmentaciones.do',
				params:{
				buscar:'',
				segmentacionJSON:Ext.encode(criterioBusquedaSegmentacion)
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
									title:'Informaci&oacute;n',
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
								title:'Informaci&oacute;n',
								cls:'cgMsgBox',
								msg: '<span>No se han encontrado segmentaciones que coincidan con los criterios de b&uacute;squeda introducidos.</span><br/>',
								buttons: Ext.Msg.OK,
								icon: Ext.MessageBox.INFO
							});
							ultimaBusqueda.add('hayResultados',false);
							Ext.getCmp('gridBuscadorSegmentos').getStore().loadData (new Array());
						}else{
							Ext.get('divGrid').setVisibilityMode(Ext.Element.DISPLAY).show();
							Ext.getCmp('gridBuscadorSegmentos').getStore().loadData (datosResultado.datos);
	
							ultimaBusqueda.add('hayResultados',true);
							
						}
						//Habilita el boton nuevo
						deshabilitarEstadoBotonNuevo(false);
					}else{
						//Ha saltado una excepcion y viene un objeto con un mensaje de error.
						Ext.Msg.show({
							title:'Error',
							cls:'cgMsgBox',
							msg: '<span>'+datosResultado.message+'</span><br/>',
							buttons: Ext.Msg.OK,
							icon: Ext.MessageBox.ERROR
						});
						ultimaBusqueda.add('hayResultados',false);
						Ext.getCmp('gridBuscadorSegmentos').getStore().loadData (new Array());
						//Deshabilita el boton nuevo
						deshabilitarEstadoBotonNuevo(true);
					}
					
					// deshabilitamos el botón  eliminar al realizar una busuqeda
					deshabilitarEstadoBotonBaja(true);
				}
			}
			});
			
			//Haga lo que haga la funcion buscar hay que tener almacenados los parametros de la ultima busqueda
			
			ultimaBusqueda.add('unidad',Ext.get('selUnidad').dom.options[Ext.get('selUnidad').dom.selectedIndex].value);
			ultimaBusqueda.add('operacion',Ext.get('selOperacion').dom.options[Ext.get('selOperacion').dom.selectedIndex].value);
			
			//Indices seleccionados en los combos (solo se usan para la precarga de la pantalla de alta)
			ultimaBusqueda.add('indexOperacion',Ext.get('selOperacion').dom.selectedIndex);
			ultimaBusqueda.add('indexUnidad',Ext.get('selUnidad').dom.selectedIndex);
			ultimaBusqueda.add('indexCodigoSegmentoOrigen',Ext.get('selCodSeg').dom.selectedIndex);
			ultimaBusqueda.add('indexCodigoSubSegmentoOrigen',Ext.get('selCodSubSeg').dom.selectedIndex);

			
			if(Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].innerHTML != 'Todos'){
				ultimaBusqueda.add('codigoSegmentoOrigen',Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].innerHTML);
			}else{
				ultimaBusqueda.add('codigoSegmentoOrigen','');
			}
			if(Ext.get('selCodSubSeg').dom.options[Ext.get('selCodSubSeg').dom.selectedIndex].innerHTML != 'Todos'){
				ultimaBusqueda.add('codigoSubSegmentoOrigen',Ext.get('selCodSubSeg').dom.options[Ext.get('selCodSubSeg').dom.selectedIndex].innerHTML);
			}else{
				ultimaBusqueda.add('codigoSubSegmentoOrigen','');
			}
			
			
		}
	}

	//boton eliminar
	var eliminar = function (){

		if(eliminarConfirmado){
			//Inicializamos pase lo que pase
			eliminarConfirmado = false;
			var datosSegmentacionEliminar={    	 
					unidad:grid.getSelectionModel().getSelected().data.unidad,
					operacion:grid.getSelectionModel().getSelected().data.operacion,
					segmentoOrigen:grid.getSelectionModel().getSelected().data.segmentoOrigen,   		    	
					subSegmentoOrigen:grid.getSelectionModel().getSelected().data.subSegmentoOrigen,    	
					subSegmentoDestino:grid.getSelectionModel().getSelected().data.subSegmentoDestino,
					fechaIniVigencia:grid.getSelectionModel().getSelected().data.fechaIniVigencia,
					fechaFinVigencia:grid.getSelectionModel().getSelected().data.fechaFinVigencia	    	
			}    	
			myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
			myMask.show();
			Ext.Ajax.request({
				url:contexto + 'Segmentaciones.do',
				params:{
				baja:'',
				segmentacionJSON:Ext.encode(datosSegmentacionEliminar)
			},
			callback:function (options, success, response){
				myMask.hide();
				if (success){
					datosResultado = Ext.util.JSON.decode (response.responseText);
	
					if (datosResultado.success){
						//al ser una eliminaci�n, mostramos un mensaje con el resultado.
						if (datosResultado.message && datosResultado.message.length>0){
							Ext.each(datosResultado.message,function(message){
								Ext.Msg.show({
									title:'Informaci&oacute;n',
									cls:'cgMsgBox',
									msg: '<span>'+message+'</span><br/>',
									buttons: Ext.Msg.OK,
									icon: Ext.MessageBox.INFO
								});
							});
						}
						//realizamos la busqueda de nuevo del mismo modo que se hizo la �ltima (filtrada o todos)
						// deshabilitamos el botón  eliminar
						deshabilitarEstadoBotonBaja(true);
						obtenerDatos(ultimaBusquedaTipo);
						
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
		}else{
			
			Ext.Msg
			.show( {
				msg : '<span>&iquest;Desea eliminar el registro seleccionado?</span><br/>',
				title : 'Aviso',
				buttons : Ext.Msg.YESNO,
				icon : Ext.Msg.INFO,
				minWidth : 400,
				fn : function(buttonId) {
					
					if (buttonId == 'yes') {
						eliminarConfirmado = true;
						eliminar();
					}else{
						//Inicializamos el valor de nuevo
						eliminarConfirmado = false;
					}
				}
			});
			
			
			eliminarConfirmado = false;
		}
	}

	//Boton imprimir
	var imprimirSegmentaciones = function(){

		
		if(ultimaBusqueda.get('hayResultados')){
			
			myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
			myMask.show();
			
			//si la ultima b�squeda fue con filtro, cogemos los datos de filtrado.
			var segmentacionImprimir={};
			if(ultimaBusquedaTipo == "filtrado"){    		
	
				var segmentacionImprimir = {
						unidad:ultimaBusqueda.get('unidad'),
						operacion:ultimaBusqueda.get('operacion'),
						codigoSegmentoOrigen:ultimaBusqueda.get('codigoSegmentoOrigen'),
						descSegmentoOrigen:ultimaBusqueda.get('descSegmentoOrigen'),
						codigoSubSegmentoOrigen:ultimaBusqueda.get('codigoSubSegmentoOrigen'),
						descSubSegmentoOrigen:ultimaBusqueda.get('descSubSegmentoOrigen')
	
				};
			}
			myMask.hide();
			document.getElementById('ifExportar').src = contexto + 'Segmentaciones.do?exportarPDF=&segmentacionJSON='+Ext.encode(segmentacionImprimir);	
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

	//carga el usuario de alta en el campo de la pantalla de alta.
	var obtenerUsuarioSesion = function(){

		Ext.Ajax.request({
			url:contexto + 'Segmentaciones.do',
			params:{
			obtenerUsuario:''
		},
		callback:function (options, success, response){
			myMask.hide();
			if (success){
				datosResultado = Ext.util.JSON.decode (response.responseText);

				if (datosResultado.success){
					//al ser una eliminaci�n, mostramos un mensaje con el resultado.
					if (datosResultado.message && datosResultado.message.length>0){
						Ext.each(datosResultado.message,function(message){
							Ext.Msg.show({
								title:'Informaci&oacute;n',
								cls:'cgMsgBox',
								msg: '<span>'+message+'</span><br/>',
								buttons: Ext.Msg.OK,
								icon: Ext.MessageBox.INFO
							});
						});
					}
					Ext.get('txtUsuarioAltaAS').dom.value = datosResultado.datos[0];
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
	/*
	 *Funcion que deshabilita el boton nuevo en caso que este habilitado y viceversa 
	 */
	var deshabilitarEstadoBotonNuevo = function(estado){
		Ext.get('btnNuevo').dom.disabled = estado
		if(estado){
			Ext.get('btnNuevo').addClass('btnDis'); 
			Ext.get('btnNuevo').dom.src = contexto + 'images/botones/QGbtListadoNuevo_des.gif';
		}else{
			Ext.get('btnNuevo').removeClass('btnDis'); 
			Ext.get('btnNuevo').dom.src = contexto + 'images/botones/QGbtListadoNuevo.gif';

		}

	}
	 /*
	  * Funcion que deshabilita el boton eliminar en caso que este habilitado y viceversa 
	  */
	var deshabilitarEstadoBotonBaja = function(estado){
		Ext.get('btnEliminar').dom.disabled = estado
		if(estado){
		
			Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtBaja_des.gif';	
			Ext.get('btnEliminar').addClass('btnDis'); 

		}else{
			Ext.get('btnEliminar').removeClass('btnDis'); 
			Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtBaja.gif';

		}
	}
	 
	// inicializa todos los campos de la pantalla de alta al abrirla
	var limpiarPantallaAlta = function(){

		Ext.get('selUnidadAS').dom.selectedIndex = 0;		
		Ext.get('selOperacionAS').dom.selectedIndex = 0;		
		Ext.get('txtIntervaloVigASIni').dom.value='';
		Ext.get('txtIntervaloVigASFin').dom.value='';		

		Ext.get('selCodSegAS').dom.selectedIndex = 0;		
		agregarValoresCombo('selCodSubSegAS', new Array(), true);
		agregarValoresCombo('selCodDestSegAS', new Array(), true);
		agregarValoresCombo('selCodSubSegDestAS', new Array(), true);
		Ext.get('txtDescripcionSegAS').dom.value='';
		Ext.get('txtDescripcionSubSegAS').dom.value='';
		Ext.get('txtDescDestSegAS').dom.value='';
		Ext.get('txtDescSubSegDestAS').dom.value='';
		
		//Se habilitan los combos
		Ext.get('selUnidadAS').dom.disabled = false;
		Ext.get('selOperacionAS').dom.disabled = false;
		Ext.get('selCodSegAS').dom.disabled = false;
		Ext.get('selCodSubSegAS').dom.disabled = false;

	}
	
	
	/**
	 * Funcion que crea el tooltip para la columna
	 * @param metadata
	 * @param record
	 * @param rowIndex
	 * @param colIndex
	 * @param store
	 * @return
	 */
	function addTooltip(value,metadata, record, rowIndex, colIndex, store){
		//En record viene el elemento formado del grid
		var valorTooltip = "";
		//Dependiendo de la columna que estemos renderizando se metera un valor de descripcion u otro
		//Columna de segmento origen
		if(colIndex == 2){
			valorTooltip = record.data.segmentoOrigenDescripcion;
		//Columna de sbseg origen
		}else if(colIndex == 3 ){
			valorTooltip = record.data.subSegmentoOrigenDescripcion;
		//Columna de segmento destino
		}else if(colIndex == 4 ){
			valorTooltip = record.data.segmentoDestinoDescripcion;
		//Columna de sbseg destino
		}else if(colIndex == 5 ){
			valorTooltip = record.data.subSegmentoDestinoDescripcion;
		}
		//Accedemos al tooltip
		if(valorTooltip != null && valorTooltip != ''){
			metadata.attr = 'ext:qtip="' + valorTooltip +'"';
		}
		//Debe devolver el valor para rellenar la columna
		return value;
	}

	
	var pintarGrid = function (){
		
	

		var selectionModel = new Ext.grid.RowSelectionModel({
			singleSelect: true,
			listeners: {

			rowdeselect: function(sm) {
			// deshabilitamos el botón  eliminar
				deshabilitarEstadoBotonBaja(true);

		},
		rowselect: function(sm) {
			// habilitamos el boton eliminar
				deshabilitarEstadoBotonBaja(false);
		}
		}
		});
		// create the data store
		var store = new Ext.data.Store({
			reader: new Ext.data.ObjectReader({}, [
			                                       {name: 'unidad', type: 'string'},
			                                       {name: 'descUnidad', type: 'string'},
			                                       {name: 'operacion', type: 'string'},
			                                       {name: 'descOperacion', type: 'string'},
			                                       {name: 'segmentoOrigen', type: 'string'},
			                                       {name: 'subSegmentoOrigen', type: 'string'},
			                                       {name: 'segmentoDestino', type: 'string'},
			                                       {name: 'subSegmentoDestino', type: 'string'},
			                                       {name: 'fechaIniVigencia', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'fechaFinVigencia', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'usuarioAlta', type: 'string'},
			                                       {name: 'keySegmentacion', type:'string'},
			                                       {name: 'segmentoOrigenDescripcion',type:'string'},
			                                       {name: 'subSegmentoOrigenDescripcion',type:'string'},
			                                       {name: 'segmentoDestinoDescripcion',type:'string'},
			                                       {name: 'subSegmentoDestinoDescripcion',type:'string'}
			                                       ])
		});
		var columnModel = new Ext.grid.ColumnModel([
		                                            {header: 'Unidad',sortable: true,  dataIndex: 'descUnidad'},
		                                            {header: 'Operaci&oacute;n',sortable: true,  dataIndex: 'descOperacion', id:'colDesc'},
		                                            {header: 'Seg. Origen',sortable: true,  dataIndex: 'segmentoOrigen', renderer:addTooltip},
		                                            {header: 'Subseg. Origen',sortable: true,  dataIndex: 'subSegmentoOrigen', renderer:addTooltip},
		                                            {header: 'Seg. Destino',sortable: true,  dataIndex: 'segmentoDestino', renderer:addTooltip},
		                                            {header: 'Subseg. Destino',sortable: true, dataIndex: 'subSegmentoDestino', renderer:addTooltip},
		                                            {header: 'F. Ini. Vigencia', sortable: true, align:'center', dataIndex: 'fechaIniVigencia'},
		                                            {header: 'F. Fin Vigencia', sortable: true, align:'center', dataIndex: 'fechaFinVigencia'},
		                                            {header: 'Us. Alta',sortable: true,  align:'center', dataIndex: 'usuarioAlta'}
		                                            ]);
		// create the Grid
		grid = new Ext.grid.GridPanel({
			id:'gridBuscadorSegmentos',
			store: store,
			renderTo: 'divGrid',
			cls:'gridCG',
			sm: selectionModel,
			viewConfig: {
				forceFit:true
			},
			cm: columnModel,
			stripeRows: true,
			height: 177
		});
		if (Ext.isIE6){
			anchoContenido = document.body.offsetWidth - 40;
			grid.setWidth (anchoContenido);
		}
	}

	// Controlamos los eventos sobre los botones
	var controlEventos = function() {
		
		window.onresize = function() {
			refrescarGrid.defer(20);
		}
		
		//carga el campo de texto con la descripcion del segmento seleccionado
		Ext.get('selCodSeg').on('change', function(){			 
			Ext.get('txtDescripcionSeg').dom.value = Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].value;
			cargarSubSegmento("busqueda");
		});
		//carga el campo de texto con la descripcion del subsegmento seleccionado
		Ext.get('selCodSubSeg').on('change', function(){			 		
			Ext.get('txtDescripcionSubSeg').dom.value = Ext.get('selCodSubSeg').dom.options[Ext.get('selCodSubSeg').dom.selectedIndex].value;						
		});
		//evento click del boton Filtrar
		Ext.get('btnFiltrar').on('click', function() {
			ultimaBusquedaTipo = "filtrado";			
			obtenerDatos("filtrado");
		});
		//evento click del boton Filtrar
		Ext.get('btnVerTodos').on('click', function() {
			ultimaBusquedaTipo = "todos";
			obtenerDatos("todos");
		});
		//evento click del boton Eliminar
		Ext.get('btnEliminar').on('click', function() {
			eliminar();
		});
		// evento click boton Nuevo
		Ext.get('btnNuevo').on('click', function() {
			abrirPopUpGestionSegmento('A&ntilde;adir Segmentaci&oacute;n');
			limpiarPantallaAlta();
			//Despues de limpiar la pantalla comprobamos el estado del boton
			//guardar que siempre estara deshabilitado
			CGLOBALGESTSEG.cargarPantallaAlta(ultimaBusqueda,ultimaBusquedaTipo)
			CGLOBALGESTSEG.controlarBotonGuardar();

		});

		// evento click del boton Imprimir
//		Ext.get('btnImprimir').on('click', function() {
//			imprimirSegmentaciones();
//		});		 

		if (Ext.isIE){
			document.getElementById('ifExportar').onreadystatechange = function(){myMask.hide();};
		}
	}

	var abrirPopUpGestionSegmento = function(titulo){
		var win = Ext.getCmp('winAnadirSegmentos');
		
		if(!win){
			win = new Ext.Window({
				id:'winAnadirSegmentos',
				contentEl:'popUpAnadirSegmentacion',
				title:titulo,
				width:945,
				height:380,
				closeAction:'hide',
				closable: true,
				animateTarget:'btnNuevo',
				resizable:false,
				minimizable:true,
				draggable:false,
				modal:true,
				listeners:{
					minimize : function(){
						minimizarVentana('winAnadirSegmentos');
					},
					beforehide : function(){
						//Lanzamos la misma funcion que al pulsar el boton cancelar.
						return CGLOBALGESTSEG.cancelar();
					}
				}
			});
		}
		
		win.show();
		//Maximinizamos la ventana por si fue minimizada al cerrarse
		maximizarVentana('winAnadirSegmentos');
	}
	
	var refrescarGrid = function() {
		if (Ext.isIE6){
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).hide();
			var ancho = document.body.offsetWidth - 38;
			
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).show();
			grid.setWidth (ancho);
			Ext.getCmp('gridBuscadorSegmentosHistorico').setWidth (ancho);
		}else{
			grid.getView().refresh(true);
			Ext.getCmp('gridBuscadorSegmentosHistorico').getView().refresh(true);
		}
	}
	
	/*
	 * Inicializa los campos de b�squeda
	 */
	var inicializarCampos = function(){

		Ext.get('txtDescripcionSeg').dom.value = "";
		Ext.get('txtDescripcionSubSeg').dom.value = "";	

	}

	return {
		init: function (){
		//Cargamos la mascara para que se carguen los combos
		
		
		
		Ext.QuickTips.init();
		inicializarCampos();
		Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		controlEventos();
		//Cargamos los combos
		cargarCodigoSegmento();
		pintarGrid();
		obtenerUsuarioSesion();
		//Deshabilitamos inicialmente el boton nuevo
		deshabilitarEstadoBotonNuevo(true);
		
	},
	cargarSubSegmento: function(pantalla){
		cargarSubSegmento(pantalla);
	},
	obtenerDatos: function(){
		obtenerDatos(ultimaBusquedaTipo);
	},
	comprobarDuplicidad: function(keyNueva,intervaloInicialNuevo,intervaloFinalNuevo){
		return comprobarDuplicidad(keyNueva,intervaloInicialNuevo,intervaloFinalNuevo);
	}
	}
}();

function cargarSubSegmento(pantalla){
	CGLOBAL.cargarSubSegmento(pantalla);
}
function obtenerDatos(){
	CGLOBAL.obtenerDatos();
}
function comprobarDuplicidad(keyNueva,intervaloInicialNuevo,intervaloFinalNuevo){
	return CGLOBAL.comprobarDuplicidad(keyNueva,intervaloInicialNuevo,intervaloFinalNuevo);
}
Ext.onReady (CGLOBAL.init, CGLOBAL, true);