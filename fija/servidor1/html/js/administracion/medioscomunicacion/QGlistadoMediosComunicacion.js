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
    		url:contexto + 'MediosComunicacion.do',
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
			fechaIniVigencia:convertDateToJSONLib(fechaIncio),
			fechaFinVigencia:convertDateToJSONLib(fechaFin),
			usuarioAlta:Ext.get('txtUsuarioAlta').dom.value,
			usuarioMod:Ext.get('txtUsuarioMod').dom.value
		};
		
    	Ext.Ajax.request({
    		url:contexto + 'MediosComunicacion.do',
    		params:{
    			modificar:'',
    			medioComunicacionJSON:Ext.encode(medios)
    			
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
			fechaIniVigencia:convertDateToJSONLib(fechaIncio),
			fechaFinVigencia:convertDateToJSONLib(fechaFin),
			usuarioAlta:Ext.get('txtUsuarioAlta').dom.value,
			usuarioMod:Ext.get('txtUsuarioMod').dom.value
		};
		
		var recordCreator = grid.getStore().recordType;
		
		var miRecordMedio = new recordCreator({
	        codigo: medios.codigo,
	        descripcion: medios.descripcion,
	        fechaIniVigencia:fechaIncio,
	        fechaFinVigencia: fechaFin,
	        usuarioAlta: medios.usuarioAlta,
	        usuarioMod: medios.usuarioMod
	    });
		
    	Ext.Ajax.request({
    		url:contexto + 'MediosComunicacion.do',
    		params:{
    			alta:'',
    			medioComunicacionJSON:Ext.encode(medios)
    			
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
			fechaIniVigencia:convertDateToJSONLib(record.data.fechaIniVigencia),
			fechaFinVigencia:convertDateToJSONLib(record.data.fechaFinVigencia),
			usuarioAlta:record.data.usuarioAlta,
			usuarioMod:record.data.usuarioMod
		};
		
    	Ext.Ajax.request({
    		url:contexto + 'MediosComunicacion.do',
    		params:{
    			baja:'',
    			medioComunicacionJSON:Ext.encode(medios)
    			
    		},
    		callback:function (options, success, response){
    			if (success){
    				var respuesta = Ext.util.JSON.decode (response.responseText);
    				if (respuesta.success){
    					//Eliminamos el registro seleccionado.
    					grid.getStore().remove(record);
    					//Limpiamos el formulario y lo ocultamos.
    					cancelarMediosComunicacion();
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
						
							cancelarMediosComunicacion();
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
				{name: 'fechaIniVigencia', type: 'date'},
				{name: 'fechaFinVigencia', type: 'date'},
				{name: 'usuarioAlta', type: 'string'},
				{name: 'usuarioMod', type: 'string'}
			])
	    });
	    
	  	var columnModel = new Ext.grid.ColumnModel([
		    {header: 'Código',sortable: true,width:60,  dataIndex: 'codigo'},		    
            {header: 'Descripción', sortable: true, id:'colDescripcion', dataIndex: 'descripcion'},
            {header: 'Fecha inicio vigencia', sortable: true,width:135, align:'center', dataIndex: 'fechaIniVigencia', renderer: Ext.util.Format.dateRenderer('d.m.Y')},
            {header: 'Fecha fin vigencia', sortable: true,width:135, align:'center', dataIndex: 'fechaFinVigencia', renderer: Ext.util.Format.dateRenderer('d.m.Y')},
          	{header: 'Usuario de alta',sortable: true,width:110,  align:'center', dataIndex: 'usuarioAlta'},
          	{header: 'Usuario de modif.',sortable: true,width:120,  align:'center', dataIndex: 'usuarioMod'}
		]);
		
	    // create the Grid
	    grid = new Ext.grid.GridPanel({
	    	id:'gridMediosComunicacion',
	        store: store,
			renderTo: 'divGrid',
			cls:'gridCG',
			sm: selectionModel,
			cm: columnModel,
	        stripeRows: true,
	        autoExpandColumn: 'colDescripcion',
	        height: 177,
	        listeners:{
	        	render:function(){
			    	obtenerDatos(function (){
			    		store.loadData (datosResultado.datos);	
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
				nuevoMediosComunicacion();
		});
		
		// evento click del botón dar de baja
		Ext.get('btnEliminar').on('click', function() {
			if (Ext.getCmp('gridMediosComunicacion').getSelectionModel().hasSelection()) {
				eliminarMediosComunicacion();
			}
		});
		
		// evento click del botón Imprimir
//		Ext.get('btnImprimir').on('click', function() {//
//			imprimirMediosComunicacion();//
//		});
		
		// evento click del botón Guardar
		Ext.get('btnGuardar').on('click', function() {
				guardarMediosComunicacion();
		});
		
		// evento click del botón Cancelar
		Ext.get('btnCancelar').on('click', function() {
				//cancelarMediosComunicacion();
				cargarDatosModificacion(true);
		});
	}	
	
	var eliminarMediosComunicacion = function() {
	
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
	
	var imprimirMediosComunicacion = function() {
		alert ("Imprimir Medio de comunicacion");
	}
	
	var guardarMediosComunicacion = function() {
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
							   msg: '<span class="bold">Ya existe un medio de comunicación con ese código.</span>',
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
	
	/*	NUEVO MEDIO DE COMUNICACION*/
	var nuevoMediosComunicacion = function() {
		var codigo = Ext.get('txtCodigo').dom.value;
		var descripcion = Ext.get('txtDescripcion').dom.value;
		var fechaInicio = Ext.get('txtFechaInicio').dom.value;
		var fechaFin = Ext.get('txtFechaFin').dom.value;
		
		if (codigo.trim().length>0 || descripcion.trim().length>0 || fechaInicio.trim().length>0 || fechaFin.trim().length>0){
			if (record_antiguo != null &&
				record_antiguo.data.codigo == Ext.get('txtCodigo').dom.value &&
					record_antiguo.data.descripcion == Ext.get('txtDescripcion').dom.value &&
					record_antiguo.data.fechaIniVigencia.format ('d.m.Y') == fechaInicio &&
					record_antiguo.data.fechaFinVigencia.format('d.m.Y') == fechaFin){
				
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
					   		}else{
					   			Ext.get('txtDescripcion').dom.focus();
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
		//Mostramos el formulario
		Ext.get('idFormMediosComunicacion').setVisibilityMode(Ext.Element.DISPLAY).show();
		//Colocamos el foco en el campo de texto Codigo
		Ext.get('txtCodigo').dom.focus ();
	}
	
	/*	MODIFICACION MEDIO DE COMUNICACION*/
	 var cargarDatosModificacion = function (cancelar){	 	
	 	var codigo = Ext.get('txtCodigo').dom.value;
		var descripcion = Ext.get('txtDescripcion').dom.value;
		var fechaInicio = Ext.get('txtFechaInicio').dom.value;
		var fechaFin = Ext.get('txtFechaFin').dom.value;
		
	  	if (alta){
			if (codigo.trim().length>0 || descripcion.trim().length>0 || fechaInicio.trim().length>0 || fechaFin.trim().length>0){
				Ext.Msg.show({
				   title:'Los datos se perderan.',
				   msg: 'Los datos se perderan.',
				   buttons: Ext.Msg.YESNO,
				   icon: Ext.MessageBox.WARNING,
				   fn:function(respuesta){
				   		if (respuesta == 'yes'){
				   			alta=false;
				   			if(cancelar){
							   	cancelarMediosComunicacion();
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
				   	cancelarMediosComunicacion();
				}else{
		  			cargarValoresFomularioMod();
		  		}
			}
	  	}else{
	  		//MODO MODIFICACION
			if (codigo.trim().length>0 || descripcion.trim().length>0 || fechaInicio.trim().length>0 || fechaFin.trim().length>0){
				if (record_antiguo != null &&
					record_antiguo.data.codigo == Ext.get('txtCodigo').dom.value &&
					record_antiguo.data.descripcion == Ext.get('txtDescripcion').dom.value &&
					record_antiguo.data.fechaIniVigencia.format ('d.m.Y') == fechaInicio &&
					record_antiguo.data.fechaFinVigencia.format('d.m.Y') == fechaFin){
					
					if(cancelar){
					   	cancelarMediosComunicacion();
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
								   	cancelarMediosComunicacion();
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
				Ext.get('idFormMediosComunicacion').setVisibilityMode(Ext.Element.DISPLAY).show();
				if(cancelar){
				   	cancelarMediosComunicacion();
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
		record_antiguo = grid.getSelectionModel().getSelected();
		
	  }
	  
	var cancelarMediosComunicacion = function() {
		Ext.get('txtCodigo').dom.value='';
		Ext.get('txtFechaInicio').dom.value='';
		Ext.get('txtFechaFin').dom.value='';
		Ext.get('txtUsuarioAlta').dom.value='';
		Ext.get('txtUsuarioMod').dom.value='';
		Ext.get('txtDescripcion').dom.value='';
		
		grid.getSelectionModel().clearSelections();
		
		record_antiguo=null;
		alta=false;
					
		//Ocultamos el formulario
		Ext.get('idFormMediosComunicacion').setVisibilityMode(Ext.Element.DISPLAY).hide();
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
			bottomBar: false,
			align:'Tl///T/r',
			onSelect: function() {
				this.hide();
			}
		});
 
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