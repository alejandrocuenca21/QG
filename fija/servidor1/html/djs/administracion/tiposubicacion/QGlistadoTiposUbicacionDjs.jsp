<%@ page language="java" contentType="text/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
var CGLOBAL = function (){
    var grid = null;
    var record_antiguo;
    var alta = false;
    var hay_seleccion=true;
    var suspender_eventos=false;
    var datosResultado;
    var myMask;
    
    
    var filaSeleccionada;
    
   //Funcion devuelta por obtenerDatos 
   var callBackObtener = function(correcto,datosResultado){

		if (correcto){
	    	Ext.getCmp('gridTiposUbicacion').getStore().loadData(datosResultado.datos);
	    }
	    else{				
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
   		}
	}
    
    //Funcion que carga el listado de mensajes de error
    var obtenerDatos = function (){
       	//Eliminamos la basura que haya en el grid
		filaSeleccionada = null;
		Ext.getCmp('gridTiposUbicacion').getSelectionModel().clearSelections(true);

		llamadaAjax('TiposUbicacion.do','buscar',null,null,callBackObtener,true,true);	
    }

    <sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
    //Funcion devuelta por modif
    var callBackGenerarMod = function(correcto,datos){
		if(correcto){
			cancelarTiposUbicacion();
			obtenerDatos();
		}

		cargarDatosModificacion(true);
		hay_seleccion=false;
		filaSeleccionada = null;
		Ext.getCmp('gridTiposUbicacion').getSelectionModel().clearSelections(true);
	}
    
    //Funcion que modifica un medio de comunicacion
    var modificarDatos = function (){

		var medios = {
			codigo:HTML_Texto(Ext.get('txtCodigo').dom.value),
			descripcion:HTML_Texto(Ext.get('txtDescripcion').dom.value),
			fechaIniVigencia:Ext.get('txtFechaInicio').dom.value,
			fechaFinVigencia:Ext.get('txtFechaFin').dom.value,
			usuarioAlta:Ext.get('txtUsuarioAlta').dom.value,
			usuarioMod:Ext.get('txtUsuarioMod').dom.value
		};
		llamadaAjax('TiposUbicacion.do','modificar','tiposUbicacionJSON',medios,callBackGenerarMod,false);
    }  

    //Funcion devuelta por alta 
    var callBackGenerarAlta = function(correcto,datos){
		if(correcto){
			var recordCreator = grid.getStore().recordType;
		
			var miRecordMedio = new recordCreator({
		        codigo: HTML_Texto(Ext.get('txtCodigo').dom.value),
		        descripcion: HTML_Texto(Ext.get('txtDescripcion').dom.value),
		        fechaIniVigencia: Ext.get('txtFechaInicio').dom.value,
		        fechaFinVigencia: Ext.get('txtFechaFin').dom.value,
		        usuarioAlta: Ext.get('hiddenUsuarioAlta').dom.value,
		        usuarioMod: Ext.get('txtUsuarioMod').dom.value
		    });
		
			//Modificamos el grid con los datos del alta
    		grid.getStore().add(miRecordMedio);
    		prepararFormularioAlta();
		}

		cargarDatosModificacion(true);
		hay_seleccion=false;
		filaSeleccionada = null;
		Ext.getCmp('gridTiposUbicacion').getSelectionModel().clearSelections(true);
	}
    
    //Funcion que da de alta un medio de comunicacion
    var altaDatos = function (){

		if(!Ext.get('txtFechaFin').dom.value)
			Ext.get('txtFechaFin').dom.value ='31.12.2500';
		 
		var medios = {
			codigo:HTML_Texto(Ext.get('txtCodigo').dom.value),
			descripcion:HTML_Texto(Ext.get('txtDescripcion').dom.value),
			fechaIniVigencia:Ext.get('txtFechaInicio').dom.value,
			fechaFinVigencia:Ext.get('txtFechaFin').dom.value,
			usuarioAlta:Ext.get('txtUsuarioAlta').dom.value,
			usuarioMod:Ext.get('txtUsuarioMod').dom.value
		};

		llamadaAjax('TiposUbicacion.do','alta','tiposUbicacionJSON',medios,callBackGenerarAlta,false);
    }      
    
    var eliminarDatos = function (){
  		
		var record = filaSeleccionada;
		var medios = {
			codigo:record.codigo,
			descripcion:record.descripcion,
			fechaIniVigencia:record.fechaIniVigencia,
			fechaFinVigencia:record.fechaFinVigencia,
			usuarioAlta:record.usuarioAlta,
			usuarioMod:record.usuarioMod
		};
		
		myMask = new Ext.LoadMask('marco', {msg:"Eliminando datos..."});
		myMask.show();
		
    	Ext.Ajax.request({
    		url:contexto + 'TiposUbicacion.do',
    		params:{
    			baja:'',
    			tiposUbicacionJSON:Ext.encode(medios)
    			
    		},
    		callback:function (options, success, response){
    			myMask.hide();
    			if (success){
    				var respuesta = Ext.util.JSON.decode (response.responseText);
    				if (respuesta.success){
    					//Eliminamos el registro seleccionado.
    					grid.getStore().remove(record);
    					//Limpiamos el formulario y lo ocultamos.
    					cancelarTiposUbicacion();
    					//Deshabilitamos el boton.
    					Ext.get('btnEliminar').dom.disabled = true;
						Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';
    					Ext.get('btnEliminar').addClass('btnDis');
    					//Pueden llegar mensajes informativos.
    					if (respuesta.message && respuesta.message.length>0){
	    					Ext.each(respuesta.message,function(message){
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
						   msg: '<span>'+respuesta.message+'</span><br/>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
    				}
    			}
    		}
    	});
    }
	</sec:authorize>
    
	var pintarGrid = function (){
	
	 	
	   // create the data store
	    var store = new Ext.data.Store({
	    	reader: new Ext.data.ObjectReader({}, [
				{name: 'codigo', type: 'string'},
				{name: 'descripcion', type: 'string'},
				{name: 'fechaIniVigencia', type: 'string'},
				{name: 'fechaFinVigencia', type: 'string'},
				{name: 'usuarioAlta', type: 'string'},
				{name: 'usuarioMod', type: 'string'}
			])
	    });
	    
	  	var columnModel = new Ext.grid.ColumnModel([
		    {header: 'Código',sortable: true,width:60,  dataIndex: 'codigo'},		    
            {header: 'Descripción', sortable: true, id:'colDescripcion', dataIndex: 'descripcion'},
            {header: 'Fecha inicio vigencia', sortable: true,width:135, align:'center', dataIndex: 'fechaIniVigencia'},
            {header: 'Fecha fin vigencia', sortable: true,width:135, align:'center', dataIndex: 'fechaFinVigencia'},
          	{header: 'Usuario de alta',sortable: true,width:110,  align:'center', dataIndex: 'usuarioAlta'},
          	{header: 'Usuario de modif.',sortable: true,width:120,  align:'center', dataIndex: 'usuarioMod'}
		]);
		
		var cargarDatosFila = function(fila){
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
			Ext.each(Ext.query('.marcarFila', grid.id), function(filaMarcada) {
				Ext.fly(filaMarcada).removeClass('marcarFila');
			});
			Ext.fly(grid.getView().getRow(fila)).addClass('marcarFila');
				
			filaSeleccionada = grid.getStore().data.items[fila].data;
			Ext.get('btnEliminar').dom.disabled = false;
			Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar.gif';
			Ext.get('btnEliminar').removeClass('btnDis');
			cargarDatosModificacion(false);
			hay_seleccion=false;
			</sec:authorize>
		}
		
			
	    // create the Grid
	    grid = new Ext.grid.EditorPasteCopyGridPanel({
	    	id:'gridTiposUbicacion',
	        store: store,
			renderTo: 'divGrid',
			cls:'gridCG',
			funcionRollBack :cargarDatosFila,
			selectionModel: '',
			cm: columnModel,
	        stripeRows: true,
	        autoExpandColumn: 'colDescripcion',
	        height: 177
	    });
	}
    
   // Controlamos los eventos sobre los botones
	var controlEventos = function() {
		
		Ext.get('marco').on('resize', function() {
			refrescarGrid.defer(20);
		});
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
		
		// evento click del botón nuevo
		Ext.get('btnNuevo').on('click', function() {
				nuevoTiposUbicacion();
		});
		
		// evento click del botón dar de baja
		Ext.get('btnEliminar').on('click', function() {
			if (filaSeleccionada != null) {
				eliminarTiposUbicacion();
			}
		});
		
		// evento click del botón Guardar
		Ext.get('btnGuardar').on('click', function() {
				guardarTiposUbicacion();
		});
		
		// evento click del botón Cancelar
		Ext.get('btnCancelar').on('click', function() {
				cargarDatosModificacion(true);
		});
		
		</sec:authorize>
		
		
		// evento click del botón Imprimir
		//Ext.get('btnImprimir').on('click', function() {
			//imprimirTiposUbicacion();
		//});
	
		if (Ext.isIE)
			document.getElementById('ifExportar').onreadystatechange = function(){myMask.hide();};	
	}
	
	var refrescarGrid = function() {
		if (Ext.isIE6){
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).hide();
			var ancho = document.body.offsetWidth - 38;
			
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).show();
			grid.setWidth (ancho);
		}
	}
	
	var imprimirTiposUbicacion = function() {
	
		if (Ext.isIE){
			myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
			myMask.show();
		}
		document.getElementById('ifExportar').src = contexto + 'TiposUbicacion.do?exportarPDF=';
	}
	
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
	var eliminarTiposUbicacion = function() {
		Ext.Msg.show({
		   title:'Eliminar',
		   msg: '<span>¿Desea eliminar el registro seleccionado?</span>',
		   buttons: Ext.Msg.YESNO,
		   icon: Ext.MessageBox.WARNING,
		   fn:function(respuesta){
		   		if (respuesta == 'yes'){
		   			eliminarDatos();
		   		}
		   }
		});
	}
	
	var guardarTiposUbicacion = function() {
		var codigo = Ext.get('txtCodigo').dom.value;
		var descripcion = Ext.get('txtDescripcion').dom.value;
		var fechaInicio = Ext.get('txtFechaInicio').dom.value;
		var fechaFin = Ext.get('txtFechaFin').dom.value;
		var dFechaInicio = new Date();
		var dFechaFin = new Date();
		dFechaInicio = Date.parseDate(fechaInicio, 'd.m.Y');
		dFechaFin = Date.parseDate(fechaFin, 'd.m.Y');
		var hoy = new Date();
		var msg = '';
		
		if (codigo.trim().length>0 && descripcion.trim().length>0 && fechaInicio.trim().length>0){
			//Comparamos las fechas.
			if (esFechaValida(fechaInicio, "Fecha inicio vigencia")){
				if(fechaFin.trim().length>0){
					if(esFechaValida(fechaFin, "Fecha fin vigencia")){
						if (dFechaInicio > dFechaFin){
							Ext.Msg.show({
							   title:'Fecha incorrecta',
							   msg: '<span>La fecha de fin vigencia debe ser mayor a la fecha de inicio vigencia.</span>',
							   buttons: Ext.Msg.OK,
							   icon: Ext.MessageBox.ERROR
							});
						}else{
							if(hoy < dFechaFin){
								guardarDatos();
							}else{
								Ext.Msg.show({
								   title:'Fecha incorrecta',
								   msg: '<span>La fecha de fin vigencia debe ser mayor a la fecha actual.</span>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.ERROR
								});
							}
						}
					}
				}else{
					guardarDatos();
				}
			}
		}else {
			if (codigo.trim()=="")
				msg += '<span class="oblig">Código</span>';
		
			if (descripcion.trim()=="") 
				msg += '<span class="oblig">Descripción</span>';
			
			if (fechaInicio.trim()=="")
				msg += '<span class="oblig">Fecha Inicio</span>';
				
			
			if(msg.length>0){
				Ext.Msg.show({
				   title:'Quedan campos obligatorios sin informar',
				   msg: '<span>No se han rellenado todos los campos obligatorios:</span><br/>'+msg,
				   buttons: Ext.Msg.OK,
				   icon: Ext.MessageBox.ERROR,
				   width:400,
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
	}

	var guardarDatos = function (){
		var codigo = HTML_Texto(Ext.get('txtCodigo').dom.value);
		var existe = false;
		
		if (alta){
			//Comprobamos que no exista el codigo en el grid.
			Ext.each(grid.getStore().data.items,function(dato){
				if (dato.data.codigo.toLowerCase() == codigo.toLowerCase()){
					existe = true;
				}
			});
 				
			if (!existe){
				altaDatos();
			}else{
				Ext.Msg.show({
				   title:'Registro duplicado',
				   msg: '<span>Ya existe un tipo de ubicación con ese código.</span>',
				   buttons: Ext.Msg.OK,
				   icon: Ext.MessageBox.ERROR
				});
			}
		}else{
			modificarDatos();
		}
	}
	
	/*	NUEVO TIPO DE UBICACION*/
	var nuevoTiposUbicacion = function() {
		var codigo = Ext.get('txtCodigo').dom.value;
		var descripcion = Ext.get('txtDescripcion').dom.value;
		var fechaInicio = Ext.get('txtFechaInicio').dom.value;
		var fechaFin = Ext.get('txtFechaFin').dom.value;
		
		if (codigo.trim().length>0 || descripcion.trim().length>0 || fechaInicio.trim().length>0 || fechaFin.trim().length>0){
			if (record_antiguo != null &&
				record_antiguo.codigo == HTML_Texto(Ext.get('txtCodigo').dom.value) &&
					record_antiguo.descripcion == HTML_Texto(Ext.get('txtDescripcion').dom.value) &&
					record_antiguo.fechaIniVigencia == fechaInicio){
				
				prepararFormularioAlta();
			}else{
				Ext.Msg.show({
				   title:'Los datos se perderán',
				   msg: '<span>Se van a perder los datos introducidos, ¿Desea continuar?</span>',
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
		if (filaSeleccionada != null) {
			hay_seleccion=true;
			
			filaSeleccionada = null;
			Ext.getCmp('gridTiposUbicacion').getSelectionModel().clearSelections(true);
			
			//Deshabilitamos el boton Eliminar
			Ext.get('btnEliminar').dom.disabled = true;
			Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';
			Ext.get('btnEliminar').addClass('btnDis');
		}
		//Limpiamos los valores del formulario
		Ext.get('txtCodigo').dom.value='';
		
		Ext.get('txtFechaInicio').dom.value='';
  		Ext.get('divTxtFechaInicio').removeClass('dis');
  		Ext.get('txtFechaInicio').dom.readOnly = false;
	 	Ext.get('btnCalendarFechaInicio').dom.disabled = false;
  		
		Ext.get('txtFechaFin').dom.value = '31.12.2500';
		Ext.get('txtFechaFin').dom.readOnly = true;
	 	Ext.get('divTxtFechaFin').addClass('dis');
	 	Ext.get('btnCalendarFechaFin').dom.disabled = true;
  		
		Ext.get('txtUsuarioAlta').dom.value=Ext.get('hiddenUsuarioAlta').dom.value;
		Ext.get('txtUsuarioAlta').dom.readOnly = true;
		Ext.get('txtUsuarioAlta').addClass('dis');
		Ext.get('txtUsuarioMod').dom.value='';
		Ext.get('txtUsuarioMod').dom.readOnly = true;
		Ext.get('txtUsuarioMod').addClass('dis');
		Ext.get('txtDescripcion').dom.value='';
		//Mostramos el formulario
		Ext.get('idFormTiposUbicacion').setVisibilityMode(Ext.Element.DISPLAY).show();
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
			if (codigo.trim().length>0 || descripcion.trim().length>0 || fechaInicio.trim().length>0){
				Ext.Msg.show({
				   title:'Los datos se perderán',
				   msg: '<span>Se van a perder los datos introducidos, ¿Desea continuar?</span>',
				   buttons: Ext.Msg.YESNO,
				   icon: Ext.MessageBox.WARNING,
				   fn:function(respuesta){
				   		if (respuesta == 'yes'){
				   			alta=false;
				   			if(cancelar){
							   	cancelarTiposUbicacion();
							}else{
					  			cargarValoresFomularioMod();
					  		}
				   		}else{
				   			hay_seleccion=true;
				   			filaSeleccionada = null;
				   			Ext.getCmp('gridTiposUbicacion').getSelectionModel().clearSelections(true);
				   			Ext.get('txtCodigo').dom.focus();
				   		}
				   }
				});
			}else {
				alta=false;
		  		if(cancelar){
				   	cancelarTiposUbicacion();
				}else{
		  			cargarValoresFomularioMod();
		  		}
			}
	  	}else{
	  		//MODO MODIFICACION
			if (codigo.trim().length>0 || descripcion.trim().length>0 || fechaInicio.trim().length>0){
				if (record_antiguo != null &&
					record_antiguo.codigo == HTML_Texto(Ext.get('txtCodigo').dom.value) &&
					record_antiguo.descripcion == HTML_Texto(Ext.get('txtDescripcion').dom.value) &&
					record_antiguo.fechaIniVigencia == fechaInicio){
					
					if(cancelar){
					   	cancelarTiposUbicacion();
					}else{
			  			cargarValoresFomularioMod();
			  		}
				}else{
					Ext.Msg.show({
					   title:'Los datos se perderán',
					   msg: '<span>Se van a perder los datos introducidos, ¿Desea continuar?</span>',
					   buttons: Ext.Msg.YESNO,
					   icon: Ext.MessageBox.WARNING,
					   fn:function(respuesta){
					   		if (respuesta == 'yes'){
					   			if(cancelar){
								   	cancelarTiposUbicacion();
								}else{
						  			cargarValoresFomularioMod();
						  		}
					   		}else{
					   			hay_seleccion=false;
					   			suspender_eventos=true;
					   			
					   			var index = grid.getStore().indexOf (record_antiguo);
					   			filaSeleccionada = record_antiguo;
					   			Ext.get('txtDescripcion').dom.focus();
					   		}
					   }
					});
				}
			}else{
				Ext.get('idFormTiposUbicacion').setVisibilityMode(Ext.Element.DISPLAY).show();
				if(cancelar){
				   	cancelarTiposUbicacion();
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
		Ext.get('txtCodigo').dom.value = Texto_HTML(filaSeleccionada.codigo);
		
		Ext.get('txtFechaInicio').dom.value = filaSeleccionada.fechaIniVigencia;
		Ext.get('txtFechaInicio').dom.readOnly = true;
  		Ext.get('divTxtFechaInicio').addClass('dis');
	 	Ext.get('btnCalendarFechaInicio').dom.disabled = true;
	 	
		Ext.get('txtFechaFin').dom.value = filaSeleccionada.fechaFinVigencia;
		Ext.get('txtFechaFin').dom.readOnly = true;
	 	Ext.get('divTxtFechaFin').addClass('dis');
	 	Ext.get('btnCalendarFechaFin').dom.disabled = true;
		
		Ext.get('txtUsuarioAlta').dom.value = filaSeleccionada.usuarioAlta;
		Ext.get('txtUsuarioAlta').dom.readOnly = true;
  		Ext.get('txtUsuarioAlta').addClass('dis');
  		
  		Ext.get('txtUsuarioMod').dom.value = filaSeleccionada.usuarioMod;
		Ext.get('txtUsuarioMod').dom.readOnly = true;
  		Ext.get('txtUsuarioMod').addClass('dis');
  		
		Ext.get('txtDescripcion').dom.value = Texto_HTML(filaSeleccionada.descripcion);
		record_antiguo = filaSeleccionada;
	  }
	  
	var cancelarTiposUbicacion = function() {
		Ext.get('txtCodigo').dom.value='';
		Ext.get('txtFechaInicio').dom.value='';
		Ext.get('txtFechaFin').dom.value='';
		Ext.get('txtUsuarioAlta').dom.value='';
		Ext.get('txtUsuarioMod').dom.value='';
		Ext.get('txtDescripcion').dom.value='';
		
		filaSeleccionada = null;
		Ext.getCmp('gridTiposUbicacion').getSelectionModel().clearSelections(true);
		
		record_antiguo=null;
		alta=false;
					
		//Ocultamos el formulario
		Ext.get('idFormTiposUbicacion').setVisibilityMode(Ext.Element.DISPLAY).hide();
	}
	
	var iniciarCalendarios = function (){
			
		Calendar.setup({
			inputField: "txtFechaInicio",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaInicio",
			min:fxIniMax,
			max:fxFinMax,
			bottomBar: false,
			align:'Tl///T/l',
			onSelect: function() {
				this.hide();
			}
		});
		
		Calendar.setup({
			inputField: "txtFechaFin",
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
	return {
		init: function (){
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			
			myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
			myMask.show();
			
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
			Ext.get('btnEliminar').setVisibilityMode(Ext.Element.DISPLAY).hide();
			</sec:authorize>
			
			pintarGrid();
			
			if (Ext.isIE6){
				var ancho = document.body.offsetWidth - 38;
				grid.setWidth (ancho);
			}
			
			controlEventos();
			
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
			iniciarCalendarios ();
			</sec:authorize>
			obtenerDatos();
		}
	}
	
}();

Ext.onReady (CGLOBAL.init, CGLOBAL, true);
</sec:authorize>