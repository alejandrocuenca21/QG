var CGLOBAL = function (){
    var grid = null;
    var record_antiguo;
    var alta = false;
    var hay_seleccion=true;
    var suspender_eventos=false;
    var datosResultado;
    var myMask;
    
    var obtenerDatos = function (callback){
    	Ext.Ajax.request({
    		url:contexto + 'Campanias.do',
    		params:{
    			buscar:''
    		},
    		callback:function (options, success, response){
    			if (success){
    				datosResultado = Ext.util.JSON.decode (response.responseText);
    				if (datosResultado.message.length>0){
    					Ext.each(datosResultado.message,function(message){
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
	    				Ext.each(datosResultado.datos,function(dato){
	    					dato.fechaIniVigencia = convertDateFromJSONLib (dato.fechaIniVigencia);
	    					dato.fechaFinVigencia = convertDateFromJSONLib (dato.fechaFinVigencia);
	    				});
	    				callback();
	    				myMask.hide();
	    			}
    			}
    		}
    	});
    }
    
    var modificarDatos = function (){
  		
		var fechaIncio = Date.parseDate(Ext.get('txtFechaInicio').dom.value,'d.m.Y');
		var fechaFin = Date.parseDate(Ext.get('txtFechaFin').dom.value,'d.m.Y');
		
		var medios = {
			codigo:Ext.get('txtCodigo').dom.value,
			descripcion:Ext.get('txtDescripcion').dom.value,
			observaciones:Ext.get('txtObservaciones').dom.value,
			fechaIniVigencia:convertDateToJSONLib(fechaIncio),
			fechaFinVigencia:convertDateToJSONLib(fechaFin),
			usuarioAlta:Ext.get('txtUsuarioAlta').dom.value,
			usuarioMod:Ext.get('txtUsuarioMod').dom.value
		};
		
    	Ext.Ajax.request({
    		url:contexto + 'Campanias.do',
    		params:{
    			modificar:'',
    			campaniasJSON:Ext.encode(medios)
    			
    		},
    		callback:function (options, success, response){
    			if (success){
    				var respuesta = Ext.util.JSON.decode (response.responseText);
    				if (respuesta.success){
    					//Modificamos el grid con los datos modificados
    					var record = grid.getSelectionModel().getSelected();
    					if (record){
    						record.set('codigo',medios.codigo);
    						record.set('descripcion',medios.descripcion);
    						record.set ('observaciones',medios.observaciones);
    						record.set('fechaIniVigencia',fechaIncio);
    						record.set('fechaFinVigencia',fechaFin);
    						record.set('usuarioAlta',medios.usuarioAlta);
    						record.set('usuarioMod',medios.usuarioMod);
    						
    						record.commit(true);
    					}
    					
    					if (respuesta.message.length>0){
	    					Ext.each(respuesta.message,function(message){
		    					Ext.Msg.show({
								   title:'Error',
								   cls:'cgMsgBox',
								   msg: '<span class="bold">'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.ERROR
								});
		    				});
	    				}
    				}
    			}
    		}
    	});
    }
    
    var altaDatos = function (){
  		
		var fechaIncio = Date.parseDate(Ext.get('txtFechaInicio').dom.value,'d.m.Y');
		var fechaFin = Date.parseDate(Ext.get('txtFechaFin').dom.value,'d.m.Y');
		
		var medios = {
			codigo:Ext.get('txtCodigo').dom.value,
			descripcion:Ext.get('txtDescripcion').dom.value,
			observaciones:Ext.get('txtObservaciones').dom.value,
			fechaIniVigencia:convertDateToJSONLib(fechaIncio),
			fechaFinVigencia:convertDateToJSONLib(fechaFin),
			usuarioAlta:Ext.get('txtUsuarioAlta').dom.value,
			usuarioMod:Ext.get('txtUsuarioMod').dom.value
		};
		
		
		var recordCreator = grid.getStore().recordType;
		
		var miRecordMedio = new recordCreator({
	        codigo: medios.codigo,
	        descripcion: medios.descripcion,
	        observaciones: medios.observaciones,
	        fechaIniVigencia:fechaIncio,
	        fechaFinVigencia: fechaFin,
	        usuarioAlta: medios.usuarioAlta,
	        usuarioMod: medios.usuarioMod,
	        ocultarObservaciones:(Ext.isEmpty(medios.observaciones)) ? true : false
	    });
		
    	Ext.Ajax.request({
    		url:contexto + 'Campanias.do',
    		params:{
    			alta:'',
    			campaniasJSON:Ext.encode(medios)
    			
    		},
    		callback:function (options, success, response){
    			if (success){
    				var respuesta = Ext.util.JSON.decode (response.responseText);
    				if (respuesta.success){
    					//Modificamos el grid con los datos del alta
    					grid.getStore().add(miRecordMedio);
    					prepararFormularioAlta();
   						
    					if (respuesta.message.length>0){
	    					Ext.each(respuesta.message,function(message){
		    					Ext.Msg.show({
								   title:'Error',
								   cls:'cgMsgBox',
								   msg: '<span class="bold">'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.ERROR
								});
		    				});
	    				}
    				}
    			}
    		}
    	});
    }
    
    var eliminarDatos = function (){
  		
		var fechaIncio = Date.parseDate(Ext.get('txtFechaInicio').dom.value,'d.m.Y');
		var fechaFin = Date.parseDate(Ext.get('txtFechaFin').dom.value,'d.m.Y');
		
		var record = grid.getSelectionModel().getSelected();
		var medios = {
			codigo:record.data.codigo,
			descripcion:record.data.descripcion,
			observaciones:record.data.observaciones,
			fechaIniVigencia:convertDateToJSONLib(record.data.fechaIniVigencia),
			fechaFinVigencia:convertDateToJSONLib(record.data.fechaFinVigencia),
			usuarioAlta:record.data.usuarioAlta,
			usuarioMod:record.data.usuarioMod
		};
		
    	Ext.Ajax.request({
    		url:contexto + 'Campanias.do',
    		params:{
    			baja:'',
    			campaniasJSON:Ext.encode(medios)
    			
    		},
    		callback:function (options, success, response){
    			if (success){
    				var respuesta = Ext.util.JSON.decode (response.responseText);
    				if (respuesta.success){
    					//Eliminamos el registro seleccionado.
    					grid.getStore().remove(record);
    					//Limpiamos el formulario y lo ocultamos.
    					cancelarCampania();
    					//Deshabilitamos el boton.
    					Ext.get('btnEliminar').dom.disabled = true;
						Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';
    					
    					if (respuesta.message.length>0){
	    					Ext.each(respuesta.message,function(message){
		    					Ext.Msg.show({
								   title:'Error',
								   cls:'cgMsgBox',
								   msg: '<span class="bold">'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.ERROR
								});
		    				});
	    				}
    				}
    			}
    		}
    	});
    }
    
	var pintarGrid = function (){
	    
		var selectionModel = new Ext.grid.RowSelectionModel({
			singleSelect: true, 
			listeners: {
				
				beforerowselect: function(sm) {
					if (!suspender_eventos){
						hay_seleccion=true;
					}
					return true;
				},
				rowdeselect: function(sm) {
					if (!suspender_eventos){
						if (!hay_seleccion){
							// deshabilitamos el botón Eliminar
							Ext.get('btnEliminar').dom.disabled = true;
							Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';
						
							cancelarCampania();
						}
						hay_seleccion=false;
					}
				},
				rowselect: function(sm) {
					if (!suspender_eventos){
						// habilitamos el botón Eliminar
						Ext.get('btnEliminar').dom.disabled = false;
						Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar.gif';
						
						cargarDatosModificacion(false);
						hay_seleccion=false;
					}else{
						suspender_eventos=false;
					}
				}
			}
		});
	 	
	 	// create the data store
	    var store = new Ext.data.Store({
	    	reader: new Ext.data.ObjectReader({}, [
				{name: 'codigo', type: 'string'},
				{name: 'descripcion', type: 'string'},
				{name: 'observaciones', type: 'string'},
				{name: 'fechaIniVigencia', type: 'date'},
				{name: 'fechaFinVigencia', type: 'date'},
				{name: 'usuarioAlta', type: 'string'},
				{name: 'usuarioMod', type: 'string'},
				{name: 'ocultarObservaciones'}
			])
	    });
	    
	     //Actions
		var actions= new Ext.ux.grid.RowActions({			
			menuDisabled: false, 
			header: 'Observ.',	
			autoWidth: false,
			width: 70,
			actions: [{
				iconCls: 'iconoObservaciones centrarIco',
				qtipIndex: 'observaciones',
				align:'right',
				hideIndex:'ocultarObservaciones'
			}] 
		});
		
	  	var columnModel = new Ext.grid.ColumnModel([
		    {header: 'Código',sortable: true,width:140,  dataIndex: 'codigo'},		    
            {header: 'Descripción', sortable: true, id:'colDescripcion', dataIndex: 'descripcion'},
            actions,
            {header: 'Fecha inicio vigencia', sortable: true,width:135, align:'center', dataIndex: 'fechaIniVigencia', renderer: Ext.util.Format.dateRenderer('d.m.Y')},
            {header: 'Fecha fin vigencia', sortable: true,width:135, align:'center', dataIndex: 'fechaFinVigencia', renderer: Ext.util.Format.dateRenderer('d.m.Y')},
          	{header: 'Usuario de alta',sortable: true,width:110,  align:'center', dataIndex: 'usuarioAlta'},
          	{header: 'Usuario de modif.',sortable: true,width:120,  align:'center', dataIndex: 'usuarioMod'}
		]);
	
		
		
	
	    // create the Grid
	    grid = new Ext.grid.GridPanel({
	    	id:'gridCampania',
	        store: store,
			renderTo: 'divGrid',
			cls:'gridCG',
			plugins: [actions],
			sm: selectionModel,
			cm: columnModel,
	        stripeRows: true,
	        autoExpandColumn: 'colDescripcion',
	        height: 177,
	        listeners:{
	        	render:function(){
			    	obtenerDatos(function (){
			    	
			    		store.loadData (datosResultado.datos);
			    		
			    		Ext.each(store.data.items, function(row) {
							row.data.ocultarObservaciones = Ext.isEmpty(row.data.observaciones);
						});
			    		
			    		grid.getView().refresh(true);
			    	});
			    }
	        }
	    });
	    
	    
	}
    
   // Controlamos los eventos sobre los botones
	var controlEventos = function() {
		
		window.onresize = function() {
			suspender_eventos=true;
			if (Ext.isIE6){
				var ancho = document.body.offsetWidth - 53;
				grid.setWidth (ancho);
			}else{
				grid.getView().refresh(true);
			}
		}
		
		// evento click del botón nuevo
		Ext.get('btnNuevo').on('click', function() {
				nuevoCampania();
		});
		
		// evento click del botón dar de baja
		Ext.get('btnEliminar').on('click', function() {
			if (Ext.getCmp('gridCampania').getSelectionModel().hasSelection()) {
				eliminarCampania();
			}
		});
		
		// evento click del botón Imprimir
//		Ext.get('btnImprimir').on('click', function() {//
//			imprimirCampania();//
//		});
		
		// evento click del botón Guardar
		Ext.get('btnGuardar').on('click', function() {
				guardarCampania();
		});
		
		// evento click del botón Cancelar
		Ext.get('btnCancelar').on('click', function() {
				cargarDatosModificacion(true);
		});
		
		var retardo = 200;
		//Controlamos que solo se pueda pegar 160 caracteres en el textarea
		Ext.get('txtObservaciones').on('paste', function() {
			controlarMaxTxtArea.defer(retardo,this,[this.id,160]);
		});
	}	
	
	var controlarMaxTxtArea = function(id,tam){
		Ext.get(id).dom.value =Ext.get(id).dom.value.substring(0,tam);
	}
	
	var eliminarCampania = function() {
		Ext.Msg.show({
		   title:'Eliminar',
		   msg: '<span class="bold">¿Desea eliminar el registro seleccionado?</span>',
		   buttons: Ext.Msg.YESNO,
		   icon: Ext.MessageBox.WARNING,
		   fn:function(respuesta){
		   		if (respuesta == 'yes'){
		   			eliminarDatos();
		   		}
		   }
		});
	}
	
	
	var imprimirCampania = function() {
		alert ("Imprimir Campaña");
	}
	
	var guardarCampania = function() {
		var existe = false;
		var codigo = Ext.get('txtCodigo').dom.value;
		var descripcion = Ext.get('txtDescripcion').dom.value;
		var fechaInicio = Ext.get('txtFechaInicio').dom.value;
		var fechaFin = Ext.get('txtFechaFin').dom.value;
		var dFechaInicio = new Date();
		var dFechaFin = new Date();
		dFechaInicio = Date.parseDate(fechaInicio, 'd.m.Y');
		dFechaFin = Date.parseDate(fechaFin, 'd.m.Y');
		
		if (codigo.trim().length>0 && descripcion.trim().length>0 && fechaInicio.trim().length>0){
			//Comparamos las fechas.
			if (esFechaValida(fechaInicio) && esFechaValida(fechaFin)){
				if (dFechaInicio >= dFechaFin){
					Ext.Msg.show({
					   title:'Fecha incorrecta',
					   msg: '<span class="bold">La fecha de fin vigencia debe ser mayor a la fecha de inicio vigencia.</span>',
					   buttons: Ext.Msg.OK,
					   icon: Ext.MessageBox.ERROR
					});
				}else{
					if (alta){
						//Comprobamos que no exista el codigo en el grid.
						Ext.each(grid.getStore().data.items,function(dato){
	    					if (dato.data.codigo == codigo){
	    						existe = true;
	    					}
	    				});
	    				
	    				if (!existe){
							altaDatos();
						}else{
							Ext.Msg.show({
							   title:'Error',
							   msg: '<span class="bold">Ya existe una campaña con ese código.</span>',
							   buttons: Ext.Msg.OK,
							   icon: Ext.MessageBox.ERROR
							});
						}
					}else{
						modificarDatos();
					}
				}
				
			}
		}else {
			Ext.Msg.show({
			   title:'Campos vacios.',
			   msg: 'Debe rellenar todos los campos.',
			   buttons: Ext.Msg.OK,
			   icon: Ext.MessageBox.ERROR,
			   fn:function(){
			   		if (codigo.trim().length==0){
			   			Ext.get('txtCodigo').dom.focus();
			   		}else if (fechaInicio.trim().length==0){
			   			Ext.get('txtFechaInicio').dom.focus();
			   		}else{
			   			Ext.get('txtDescripcion').dom.focus();
			   		}
			   }
			});
		}
	}
	
	/*	NUEVA CAMPANIA*/
	var nuevoCampania = function() {
		var codigo = Ext.get('txtCodigo').dom.value;
		var descripcion = Ext.get('txtDescripcion').dom.value;
		var fechaInicio = Ext.get('txtFechaInicio').dom.value;
		var fechaFin = Ext.get('txtFechaFin').dom.value;
		var observaciones = Ext.get('txtObservaciones').dom.value;
		
		if (codigo.trim().length>0 || descripcion.trim().length>0 || 
			fechaInicio.trim().length>0 || fechaFin.trim().length>0 ||observaciones.trim().length>0){
			if (record_antiguo != null &&
				record_antiguo.data.codigo == codigo &&
					record_antiguo.data.descripcion == descripcion &&
					record_antiguo.data.fechaIniVigencia.format ('d.m.Y') == fechaInicio &&
					record_antiguo.data.fechaFinVigencia.format('d.m.Y') == fechaFin &&
					record_antiguo.data.observaciones == observaciones){
				
				prepararFormularioAlta();
			}else{
				Ext.Msg.show({
				   title:'Los datos se perderan.',
				   msg: 'Los datos se perderan.',
				   buttons: Ext.Msg.YESNO,
				   icon: Ext.MessageBox.WARNING,
				   fn:function(respuesta){
				   		if (respuesta == 'yes'){
				   			prepararFormularioAlta();
				   		}else{
				   			if (codigo.trim().length==0){
					   			Ext.get('txtCodigo').dom.focus();
					   		}else if (fechaInicio.trim().length==0){
					   			Ext.get('txtFechaInicio').dom.focus();
					   		}else if (fechaFin.trim().length==0){
					   			Ext.get('txtFechaFin').dom.focus();
					   		}else if (descripcion.trim().length==0){
					   			Ext.get('txtDescripcion').dom.focus();
					   		}else{
					   			Ext.get('txtObservaciones').dom.focus();
					   		}
				   		}
				   }
				});
			}
		}else{
			prepararFormularioAlta();
		}
	}
	
	/*	PREPARAMOS EL FORMULARIO PARA DAR UN ALTA	*/
	var prepararFormularioAlta = function (){
		alta = true;
		//Habilitamos el campo codigo
		Ext.get('txtCodigo').dom.readOnly = false;
		//Le quitamos la clase que le da estilos de deshabiltiado
		Ext.get('txtCodigo').removeClass('dis');
		  		
		//Si hay un registro seleccionado lo deseleccionamos
		if (grid.getSelectionModel().hasSelection()) {
			hay_seleccion=true;
			grid.getSelectionModel().clearSelections();
			
			//Deshabilitamos el boton Eliminar
			Ext.get('btnEliminar').dom.disabled = true;
			Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';
		}
		//Limpiamos los valores del formulario
		Ext.get('txtCodigo').dom.value='';
		
		Ext.get('txtFechaInicio').dom.value='';
  		Ext.get('divTxtFechaInicio').removeClass('dis');
  		Ext.get('btnCalendarFechaInicio').dom.disabled = false;
  		
		Ext.get('txtFechaFin').dom.value='';
		Ext.get('txtUsuarioAlta').dom.value='UsuarioAlta';
		Ext.get('txtUsuarioAlta').dom.readOnly = true;
		Ext.get('txtUsuarioAlta').addClass('dis');
		Ext.get('txtUsuarioMod').dom.value='UsuarioMod';
		Ext.get('txtUsuarioMod').dom.readOnly = true;
		Ext.get('txtUsuarioMod').addClass('dis');
		Ext.get('txtDescripcion').dom.value='';
		Ext.get('txtObservaciones').dom.value='';
		//Mostramos el formulario
		Ext.get('idFormCampania').setVisibilityMode(Ext.Element.DISPLAY).show();
		//Colocamos el foco en el campo de texto Codigo
		Ext.get('txtCodigo').dom.focus ();
	}
	
	/*	MODIFICACION CAMPANIA*/
	 var cargarDatosModificacion = function (cancelar){	 	
	 	var codigo = Ext.get('txtCodigo').dom.value;
		var descripcion = Ext.get('txtDescripcion').dom.value;
		var fechaInicio = Ext.get('txtFechaInicio').dom.value;
		var fechaFin = Ext.get('txtFechaFin').dom.value;
		var observaciones = Ext.get('txtObservaciones').dom.value;
		
	  	if (alta){
			if (codigo.trim().length>0 || descripcion.trim().length>0 || 
				fechaInicio.trim().length>0 || fechaFin.trim().length>0 || observaciones.trim().length>0){
				
				Ext.Msg.show({
				   title:'Los datos se perderan.',
				   msg: 'Los datos se perderan.',
				   buttons: Ext.Msg.YESNO,
				   icon: Ext.MessageBox.WARNING,
				   fn:function(respuesta){
				   		if (respuesta == 'yes'){
				   			alta=false;
				   			if(cancelar){
							   	cancelarCampania();
							}else{
					  			cargarValoresFomularioMod();
					  		}
				   		}else{
				   			hay_seleccion=true;
				   			grid.getSelectionModel().clearSelections();
				   			Ext.get('txtCodigo').dom.focus();
				   		}
				   }
				});
			}else {
				alta=false;
				if(cancelar){
				   	cancelarCampania();
				}else{
		  			cargarValoresFomularioMod();
		  		}
		  		
			}
	  	}else{
	  		//MODO MODIFICACION
			if (codigo.trim().length>0 || descripcion.trim().length>0 || fechaInicio.trim().length>0 || 
				fechaFin.trim().length>0 || observaciones.trim().length>0){
				if (record_antiguo != null &&
					record_antiguo.data.codigo == codigo &&
					record_antiguo.data.descripcion ==descripcion &&
					record_antiguo.data.fechaIniVigencia.format ('d.m.Y') == fechaInicio &&
					record_antiguo.data.fechaFinVigencia.format('d.m.Y') == fechaFin &&
					record_antiguo.data.observaciones == observaciones){
					
					if(cancelar){
					   	cancelarCampania();
					}else{
			  			cargarValoresFomularioMod();
			  		}
				}else{
					Ext.Msg.show({
					   title:'Los datos se perderan.',
					   msg: 'Los datos se perderan.',
					   buttons: Ext.Msg.YESNO,
					   icon: Ext.MessageBox.WARNING,
					   fn:function(respuesta){
					   		if (respuesta == 'yes'){
					   			if(cancelar){
								   	cancelarCampania();
								}else{
						  			cargarValoresFomularioMod();
						  		}
					   		}else{
					   			hay_seleccion=true;
					   			suspender_eventos=true;
					   			
					   			var index = grid.getStore().indexOf (record_antiguo);
					   			grid.getSelectionModel().selectRecords([record_antiguo]);
					   			Ext.get('txtDescripcion').dom.focus();
					   		}
					   }
					});
				}
			}else{
				Ext.get('idFormCampania').setVisibilityMode(Ext.Element.DISPLAY).show();
				if(cancelar){
				   	cancelarCampania();
				}else{
		  			cargarValoresFomularioMod();
		  		}
			}
	  		
	  		
	  	}
	  }
	  
	  var cargarValoresFomularioMod = function (){
	  	//Cargamos los valores en el formulario
	 	Ext.get('txtCodigo').dom.readOnly = true;
	 	Ext.get('txtCodigo').addClass('dis');
		Ext.get('txtCodigo').dom.value = grid.getSelectionModel().getSelected().data.codigo;
		
		var fechaInicio = grid.getSelectionModel().getSelected().data.fechaIniVigencia;
		Ext.get('txtFechaInicio').dom.value = fechaInicio.format('d.m.Y');
		Ext.get('txtFechaInicio').dom.readOnly = true;
	 	Ext.get('divTxtFechaInicio').addClass('dis');
	 	Ext.get('btnCalendarFechaInicio').dom.disabled = true;
		
		var fechaFin = grid.getSelectionModel().getSelected().data.fechaFinVigencia;
		Ext.get('txtFechaFin').dom.value = fechaFin.format('d.m.Y');
		
		Ext.get('txtUsuarioAlta').dom.value = grid.getSelectionModel().getSelected().data.usuarioAlta;
		Ext.get('txtUsuarioAlta').dom.readOnly = true;
	 	Ext.get('txtUsuarioAlta').addClass('dis');
	 		
	 	Ext.get('txtUsuarioMod').dom.value = grid.getSelectionModel().getSelected().data.usuarioMod;
		Ext.get('txtUsuarioMod').dom.readOnly = true;
	 	Ext.get('txtUsuarioMod').addClass('dis');
	 	
		Ext.get('txtDescripcion').dom.value = grid.getSelectionModel().getSelected().data.descripcion;
		
		Ext.get('txtObservaciones').dom.value = grid.getSelectionModel().getSelected().data.observaciones;
		
		record_antiguo = grid.getSelectionModel().getSelected();
		
	  }
	  
	
	var iniciarCalendarios = function (){
		
		new Calendar({
			inputField: "txtFechaInicio",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaInicio",
			bottomBar: false,
			align:'Tl///T/l',
			onSelect: function() {
				this.hide();
			}
		});
		
		new Calendar({
			inputField: "txtFechaFin",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaFin",
			align:'Tl///T/r',
			bottomBar: false,
			onSelect: function() {
				this.hide();
			}
		});
 
	}
	  
	var cancelarCampania = function() {
		Ext.get('txtCodigo').dom.value = '';
		Ext.get('txtFechaInicio').dom.value = '';
		Ext.get('txtFechaFin').dom.value = '';
		Ext.get('txtUsuarioAlta').dom.value = '';
		Ext.get('txtUsuarioMod').dom.value = '';
		Ext.get('txtDescripcion').dom.value = '';
		Ext.get('txtObservaciones').dom.value = '';
		
		grid.getSelectionModel().clearSelections();
		
		record_antiguo=null;
		alta=false;
					
		//Ocultamos el formulario
		Ext.get('idFormCampania').setVisibilityMode(Ext.Element.DISPLAY).hide();
	}
	
	return {
		init: function (){
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			Ext.QuickTips.init();
			
			myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
			myMask.show();
			
			pintarGrid();
			
			controlEventos();
			
			iniciarCalendarios ();
		}
	}
	
}();

Ext.onReady (CGLOBAL.init, CGLOBAL, true);