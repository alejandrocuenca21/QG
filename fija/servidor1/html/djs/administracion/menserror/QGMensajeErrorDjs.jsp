<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA">
var CGLOBAL = function (){

   var grid = null;
   var record_antiguo;
   var alta = false;
   var hay_seleccion=true;
   var suspender_eventos=false;
   var datosResultado;
   var myMask;
   
   	//paginacion.
	hayPaginacion = "";
	posicionPaginacion = null;
	nejecucionOld = 0;
	posicionPaginacionOld = new Array();
	maxPagina = 0;
	nejecucion = 0;
	pag_cod="";
	pag_desc="";
	pag_fechaCon="";
   
   
   filaSeleccionada = null;
    
   //Funcion devuelta por obtenerDatos 
   var callBackObtener = function(correcto,datosResultado){

		if (correcto){
			hayPaginacion = datosResultado.indPgnIn;
			posicionPaginacion = datosResultado.pgnTx;
			Ext.getCmp('gridMensajeError').getStore().loadData(datosResultado.datos);
   		}
		obtenerUsuario();
	}
    
    //Funcion que carga el listado de mensajes de error
    var obtenerDatos = function (callback){
       	//Eliminamos la basura que haya en el grid
		filaSeleccionada = null;
		
		Ext.getCmp('gridMensajeError').getSelectionModel().clearSelections(true);
		
		posicionPaginacionOld.push(posicionPaginacion);
		nejecucionOld = nejecucion;
		
   		var datosObtener={
   		   	pgnTx: posicionPaginacion,
   			nejecucion: nejecucion,   
   			accion: "C"
		}    
		llamadaAjax('MensError.do','buscar','menserrorJSON',datosObtener,callBackObtener,true,true);	
    }
   
    //Funcion devuelta por obtenerUsuario
   	var callBackObtenerUsu = function(correcto,datos){
		if(correcto){
   			Ext.get('hiddenUsuarioConectado').dom.value = datos;
		}
	}
    
    //Funcion que carga el listado de mensajes de error
    var obtenerUsuario = function (){
    
   		var datosUsuario={ 
		}    
		llamadaAjax('MensError.do','obtenerUsuario','menserrorJSON',datosUsuario,callBackObtenerUsu,false);	
    }

   //Funcion devuelta por obtenerDatos para Modificacion
   var callBackObtenerMod = function(correcto,datosResultado){
		if (correcto){
			Ext.getCmp('gridMensajeError').getStore().loadData(datosResultado.datos);
   		}
   		// deshabilitamos el botón Eliminar
		Ext.get('btnEliminar').dom.disabled = true;
		Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';
		Ext.get('btnEliminar').addClass('btnDis');
		cargarDatosModificacion(true);
		hay_seleccion=false;
	}
    
    //Funcion que carga el listado de mensajes de error
    var obtenerDatosMod = function (){
       	//Eliminamos la basura que haya en el grid
		filaSeleccionada = null;
		Ext.getCmp('gridMensajeError').getSelectionModel().clearSelections(true);

   		var datosObtener={
		   	pgnTx: posicionPaginacionOld[nejecucionOld],
   			nejecucion: nejecucionOld,
   			accion: "C"
		}    

		llamadaAjax('MensError.do','buscarParaModific','menserrorJSON',datosObtener,callBackObtenerMod,true,true);	
    }


   //Funcion devuelta por modif (distinto de alta y baja debido a que se mantiene la paginacion)
    var callBackGenerarMod = function(correcto,datos){
		if(correcto){
			cancelarMensajeError();
			obtenerDatosMod();
		}
		// deshabilitamos el botón Eliminar
		Ext.get('btnEliminar').dom.disabled = true;
		Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';
		Ext.get('btnEliminar').addClass('btnDis');
		cargarDatosModificacion(true);
		hay_seleccion=false;
		filaSeleccionada = null;
		Ext.getCmp('gridMensajeError').getSelectionModel().clearSelections(true);
	}
    
    //Funcion que modifica un mensaje de error
    var modificarDatos = function (){
    	
		var menserror = {
			codigo:HTML_Texto(Ext.get('txtCodigo').dom.value),
			descripcion:HTML_Texto(Ext.get('txtDescripcion').dom.value),
			fxIniVigencia:Ext.get('txtFechaInicio').dom.value,
			fxFinVigencia:Ext.get('txtFechaFin').dom.value,
			usuarioAlta:Ext.get('txtUsuarioAlta').dom.value,
			usuarioModif:Ext.get('hiddenUsuarioConectado').dom.value,
			grupoResponsable: Ext.get('selGrupoRes').dom.options[Ext.get('selGrupoRes').dom.selectedIndex].text,
			accion: 'M'
		};
		llamadaAjax('MensError.do','gestionar','menserrorJSON',menserror,callBackGenerarMod,false);
    }

   //Funcion devuelta por alta-baja
    var callBackGenerar = function(correcto,datos){
		if(correcto){
			hayPaginacion = "";
			posicionPaginacion = null;
			nejecucion = 0;
			nejecucionOld = 0;
			posicionPaginacionOld = new Array();
				
			cancelarMensajeError();
			obtenerDatos();
		}
		// deshabilitamos el botón Eliminar
		Ext.get('btnEliminar').dom.disabled = true;
		Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';
		Ext.get('btnEliminar').addClass('btnDis');
		cargarDatosModificacion(true);
		hay_seleccion=false;
		filaSeleccionada = null;
		Ext.getCmp('gridMensajeError').getSelectionModel().clearSelections(true);
	}
    
    //Funcion que da de alta un nuevo mensaje de error
    var altaDatos = function (){
    
		var menserror = {
			codigo:HTML_Texto(Ext.get('txtCodigo').dom.value),
			descripcion:HTML_Texto(Ext.get('txtDescripcion').dom.value),
			fxIniVigencia:Ext.get('txtFechaInicio').dom.value,
			fxFinVigencia:Ext.get('txtFechaFin').dom.value,
			usuarioAlta:Ext.get('txtUsuarioAlta').dom.value,
			usuarioModif:Ext.get('txtUsuarioMod').dom.value,
			grupoResponsable: Ext.get('selGrupoRes').dom.options[Ext.get('selGrupoRes').dom.selectedIndex].text,
			accion: 'A'
		};
		llamadaAjax('MensError.do','gestionar','menserrorJSON',menserror,callBackGenerar,false);
    }
    
    //Funcion que elimina un mensaje de error
    var eliminarDatos = function (){
    
		var record = filaSeleccionada;
		var menserror = {
			codigo:record.codigo,
			descripcion:record.descripcion,
			fxIniVigencia:record.fxIniVigencia,
			fxFinVigencia:record.fxFinVigencia,
			usuarioAlta:record.usuarioAlta,
			usuarioModif:record.usuarioModif,
			grupoResponsable: record.grupoResponsable,
			accion: 'B'
		};
		llamadaAjax('MensError.do','gestionar','menserrorJSON',menserror,callBackGenerar,false);
    }
	
	//Pintar grid
	var pintarGrid = function (){
		 
	   // create the data store
	    var store = new Ext.data.Store({
	    	proxy: new Ext.ux.data.PagingMemoryProxy([]),
	    	reader: new Ext.data.ObjectReader({}, [
				{name: 'codigo', type: 'string'},
				{name: 'descripcion', type: 'string'},
				{name: 'grupoResponsable', type: 'string'},
				{name: 'fxIniVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'fxFinVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'usuarioAlta', type: 'string'},
				{name: 'usuarioModif', type: 'string'}
			])
	    });
	    
	  	var columnModel = new Ext.grid.ColumnModel([
		    {header: 'C&oacute;digo',sortable: true,width:60,  dataIndex: 'codigo'},		    
            {header: 'Descripci&oacute;n', sortable: true, id:'colDescripcion', dataIndex: 'descripcion'},
            {header: 'Grupo Responsable', sortable: true, dataIndex: 'grupoResponsable',renderer:rendererGrResponsable},
            {header: 'Fecha inicio vigencia', sortable: true,width:135, align:'center', dataIndex: 'fxIniVigencia'},
            {header: 'Fecha fin vigencia', sortable: true,width:135, align:'center', dataIndex: 'fxFinVigencia'},
          	{header: 'Usuario de alta',sortable: true,width:110,  align:'center', dataIndex: 'usuarioAlta'},
          	{header: 'Usuario de modif.',sortable: true,width:120,  align:'center', dataIndex: 'usuarioModif'}
		]);
		
		var cargarDatosFila = function(fila){
			Ext.each(Ext.query('.marcarFila', grid.id), function(filaMarcada) {
				Ext.fly(filaMarcada).removeClass('marcarFila');
			});
			Ext.fly(grid.getView().getRow(fila)).addClass('marcarFila');
			
			filaSeleccionada = grid.getStore().data.items[fila].data;
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
			
			Ext.get('btnEliminar').dom.disabled = false;
			Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar.gif';
			Ext.get('btnEliminar').removeClass('btnDis');
			
			cargarDatosModificacion(false);
			hay_seleccion=false;
			</sec:authorize>
		}
		
		
	    // create the Grid
	    grid = new Ext.grid.EditorPasteCopyGridPanel({
	    	id:'gridMensajeError',
	    	funcionRollBack: cargarDatosFila,
	        store: store,
			renderTo: 'divGrid',
			cls:'gridCG',
			cm: columnModel,
	        stripeRows: true,
	        autoExpandColumn: 'colDescripcion',
	        height: 177,
	        selectionModel: '',
			bbar: new QGPagingToolbarMensajeError({
               	pageSize: 100,
               	store: store,
               	displayInfo: false,
               	idGrid: 'gridMensajeError'
        	})
	    });
	    
	}
   
   
	//Funcion para mortrar la descripcion de la columna Grupo Responsable
	var rendererGrResponsable = function(data, cellmd, record, rowIndex,colIndex, store) {
		if(data == '')
			return '';
		else if(data == 'C')	
			return 'Calidad Dato';
		else if(data == 'S')	
			return 'Sistema QG';
		else if(data == 'X')	
			return 'Mixto';
		else if(data == 'N')	
			return 'No procede an&aacute;lisis';
	}   
    
   //Control de eventos sobre los botones
	var controlEventos = function() {
		
		Ext.get('marco').on('resize', function() {
			refrescarGrid.defer(20);
		});
		
		// evento click del botón nuevo
		Ext.get('btnNuevo').on('click', function() {
			nuevoMensajeError();
		});
		
		// evento click del botón dar de baja
		Ext.get('btnEliminar').on('click', function() {
			if (filaSeleccionada != null) {
				eliminarMensajeError();
			}
		});
		// evento de cambio en combo
		Ext.get('selGrupoRes').on('change', function(){			 
			cargarValorDescripcion();
			comprobarHabilitarGuardar();
		});
		
		// evento click del botón Guardar
		Ext.get('btnGuardar').on('click', function() {
			guardarMensajeError();
		});
		
		// evento click del botón Cancelar
		Ext.get('btnCancelar').on('click', function() {
			cargarDatosModificacion(true);
		});
		
		// evento click del botón Imprimir
		//Ext.get('btnImprimir').on('click', function() {
			//imprimirMensajeError();
		//});
		
		// evento para los campos editables de la parte Alta/Modif	
		Ext.get('txtCodigo').on('change', function(){			 
			comprobarHabilitarGuardar();
		});
		Ext.get('txtDescripcion').on('change', function(){			 
			comprobarHabilitarGuardar();
		});	
		Ext.get('txtFechaInicio').on('change', function(){			 
			comprobarHabilitarGuardar();
		});		
			
	}
	
	//comprueba que todos los campos estan rellenos
	var comprobarCamposAltaTodos = function() {
		var datosMetidos = Ext.get('txtCodigo').dom.value != ''
				&& Ext.get('txtDescripcion').dom.value != ''
				&& Ext.get('txtFechaInicio').dom.value != '';

		return datosMetidos;
	}

	//Funcion que comprueba para habilitar el botón guardar
	var comprobarHabilitarGuardar = function(){

		var todosRellenos = comprobarCamposAltaTodos();
		var habilitar = false;
		if(alta == true){
			habilitar = todosRellenos;
		}else{
			//Si estan todos rellenos vemos si ademas han cambiado
			if(todosRellenos){
			//Si es una modificacion hay que ver que ademas hayan cambiado los valores del intervalo
				var cambiado = false;
				//Recuperamos los valores de las fechas
				//editables

				if (record_antiguo != null &&
					record_antiguo.codigo != Ext.get('txtCodigo').dom.value ||
					record_antiguo.descripcion != Ext.get('txtDescripcion').dom.value ||
					record_antiguo.grupoResponsable != Ext.get('selGrupoRes').dom.options[Ext.get('selGrupoRes').dom.selectedIndex].text ||
					record_antiguo.fxIniVigencia !=  Ext.get('txtFechaInicio').dom.value){

					cambiado = true;
				
				}
				
				if(todosRellenos && cambiado){
					habilitar = true;
				}else{
					habilitar = false;
				}
			}	
		}

		if (habilitar) {
			Ext.get('btnGuardar').dom.src = contexto + 'images/botones/QGbtGuardar.gif';
			Ext.get('btnGuardar').removeClass('btnDis');
			Ext.get('btnGuardar').dom.disabled = false;
		} else {
			Ext.get('btnGuardar').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
			Ext.get('btnGuardar').addClass('btnDis');
			Ext.get('btnGuardar').dom.disabled = true;
		}			
	}

	//Funcion de refreco de grid
	var refrescarGrid = function() {
		if (Ext.isIE6){
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).hide();
			var ancho = document.body.offsetWidth - 38;
			
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).show();
			grid.setWidth (ancho);
		}
	}
	
	//Funcion que imprime los datos de la pantalla en pdf
	var imprimirMensajeError= function() {
		if (Ext.isIE){
			myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
			myMask.show();
		}

		var busqueda = {
   			pgnTx: posicionPaginacionOld[nejecucionOld],
   			nejecucion: nejecucionOld,
   			accion: "C"
		};
		
		document.getElementById('ifExportar').src = contexto + 'MensError.do?exportarPDF=&menserrorJSON='+Ext.encode(busqueda);
		if (Ext.isIE)
			document.getElementById('ifExportar').onreadystatechange = function(){myMask.hide();};	
	}
	
	//Funcion que elimina un mensaje de error
	var eliminarMensajeError = function() {
	
		Ext.Msg.show({
		   title:'Eliminar',
		   msg: '<span>&iquest;Desea eliminar el registro seleccionado?</span>',
		   buttons: Ext.Msg.YESNO,
		   icon: Ext.MessageBox.WARNING,
		   fn:function(respuesta){
		   		if (respuesta == 'yes'){
		   			eliminarDatos();
		   		}
		   }
		});
	}
	
	//Funcion que guarda un mensaje de error nuevo
	var guardarMensajeError = function() {
		var codigo = Ext.get('txtCodigo').dom.value;
		var descripcion = Ext.get('txtDescripcion').dom.value;
		var fechaInicio = Ext.get('txtFechaInicio').dom.value;
		var fechaFin = Ext.get('txtFechaFin').dom.value;
		var grupoRes = Ext.get('selGrupoRes').dom.value;
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
							guardarDatos();
						}
					}
				}else{
					guardarDatos();
				}
			}
		}else {
			if (codigo.trim()=="")
				msg += '<span class="oblig">C&oacute;digo</span>';
		
			if (descripcion.trim()=="") 
				msg += '<span class="oblig">Descripci&oacute;n</span>';
		
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
	
	//Funcion que comprueba que no existe registros duplicados al modificar o anadir
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
				   msg: '<span>Ya existe un mensaje de error con ese c&oacute;digo.</span>',
				   buttons: Ext.Msg.OK,
				   icon: Ext.MessageBox.ERROR
				});
			}
		}else{
			modificarDatos();
		}
	}
	
	//Funcion que anade un nuevo mensaje de error
	var nuevoMensajeError = function() {
	
		comprobarHabilitarGuardar();
	
		var codigo = Ext.get('txtCodigo').dom.value;
		var descripcion = Ext.get('txtDescripcion').dom.value;
		var fechaInicio = Ext.get('txtFechaInicio').dom.value;
		var fechaFin = Ext.get('txtFechaFin').dom.value;
		var grupoRes = Ext.get('selGrupoRes').dom.value;
		
		if (codigo.trim().length>0 || descripcion.trim().length>0 || fechaInicio.trim().length>0){
			if (record_antiguo != null &&
				record_antiguo.codigo == Ext.get('txtCodigo').dom.value &&
					record_antiguo.descripcion == Ext.get('txtDescripcion').dom.value &&
					record_antiguo.fxIniVigencia == fechaInicio &&
					record_antiguo.grupoResponsable ==  Ext.get('selGrupoRes').dom.options[Ext.get('selGrupoRes').dom.selectedIndex].text){
				
				prepararFormularioAlta();
			}else{
				Ext.Msg.show({
				   title:'Los datos se perder&aacute;n',
				   msg: '<span>Se van a perder los datos introducidos, &iquest;Desea continuar?</span>',
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
					   		}else if (grupoRes.trim().length==0){
					   			Ext.get('selGrupoRes').dom.focus();
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
	
	//Funcion que prepara el formulario para dar de alta un registro
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
			Ext.getCmp('gridMensajeError').getSelectionModel().clearSelections(true);
			
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
		
		Ext.get('txtUsuarioAlta').dom.value=Ext.get('hiddenUsuarioConectado').dom.value;
		Ext.get('txtUsuarioAlta').dom.readOnly = true;
		Ext.get('txtUsuarioAlta').addClass('dis');
		Ext.get('txtUsuarioMod').dom.value='';
		Ext.get('txtUsuarioMod').dom.readOnly = true;
		Ext.get('txtUsuarioMod').addClass('dis');
		Ext.get('txtDescripcion').dom.value='';
		
		Ext.get('txtDescripcionGr').dom.value = "";
		Ext.get('txtDescripcionGr').dom.readOnly = true;
		Ext.get('txtDescripcionGr').addClass('dis');
		Ext.get('selGrupoRes').dom.value = "";
		
		//Mostramos el formulario
		Ext.get('idFormMensajeError').setVisibilityMode(Ext.Element.DISPLAY).show();
		//Colocamos el foco en el campo de texto Codigo
		Ext.get('txtCodigo').dom.focus ();
		
		Ext.get('btnGuardar').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
		Ext.get('btnGuardar').addClass('btnDis');
		Ext.get('btnGuardar').dom.disabled = true;
	}
	
	//Funcion que carga los datos para modificarlos o dar de alta
	 var cargarDatosModificacion = function (cancelar){	 	
	 
	 	comprobarHabilitarGuardar();
	 
	 	var codigo = Ext.get('txtCodigo').dom.value;
		var descripcion = Ext.get('txtDescripcion').dom.value;
		var fechaInicio = Ext.get('txtFechaInicio').dom.value;
		var fechaFin = Ext.get('txtFechaFin').dom.value;
		var grupoRes = Ext.get('selGrupoRes').dom.value;		
		
	  	if (alta){
			if (codigo.trim().length>0 || descripcion.trim().length>0 || fechaInicio.trim().length>0){
				Ext.Msg.show({
				   title:'Los datos se perder&aacute;n',
				   msg: '<span>Se van a perder los datos introducidos, &iquest;Desea continuar?</span>',
				   buttons: Ext.Msg.YESNO,
				   icon: Ext.MessageBox.WARNING,
				   fn:function(respuesta){
				   		if (respuesta == 'yes'){
				   			alta=false;
				   			if(cancelar){
							   	cancelarMensajeError();
							}else{
					  			cargarValoresFomularioMod();
					  		}
				   		}else{
				   			hay_seleccion=true;
				   			filaSeleccionada = null;
				   			Ext.getCmp('gridMensajeError').getSelectionModel().clearSelections(true);
				   			Ext.get('txtCodigo').dom.focus();
				   		}
				   }
				});
			}else {
				alta=false;
		  		if(cancelar){
				   	cancelarMensajeError();
				}else{
		  			cargarValoresFomularioMod();
		  		}
			}
	  	}else{
	  		//MODO MODIFICACION
			if (codigo.trim().length>0 || descripcion.trim().length>0 || fechaInicio.trim().length>0){
				if (record_antiguo != null &&
					record_antiguo.codigo == Ext.get('txtCodigo').dom.value &&
					record_antiguo.descripcion == Ext.get('txtDescripcion').dom.value &&
					record_antiguo.fxIniVigencia == fechaInicio &&
					record_antiguo.grupoResponsable ==  Ext.get('selGrupoRes').dom.options[Ext.get('selGrupoRes').dom.selectedIndex].text){
					
					if(cancelar){
					   	cancelarMensajeError();
					}else{
			  			cargarValoresFomularioMod();
			  		}
				}else{
					Ext.Msg.show({
					   title:'Los datos se perder&aacute;n',
					   msg: '<span>Se van a perder los datos introducidos, &iquest;Desea continuar?</span>',
					   buttons: Ext.Msg.YESNO,
					   icon: Ext.MessageBox.WARNING,
					   fn:function(respuesta){
					   		if (respuesta == 'yes'){
					   			if(cancelar){
								   	cancelarMensajeError();
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
				Ext.get('idFormMensajeError').setVisibilityMode(Ext.Element.DISPLAY).show();
				if(cancelar){
				   	cancelarMensajeError();
				}else{
		  			cargarValoresFomularioMod();
		  		}
				
			}
	  	}
	  }
	  
	  //Funcion que carga los datos para modificarlos
	  var cargarValoresFomularioMod = function (){
	  	//Cargamos los valores en el formulario
	 	Ext.get('txtCodigo').dom.readOnly = true;
	 	Ext.get('txtCodigo').addClass('dis');
		Ext.get('txtCodigo').dom.value = Texto_HTML(filaSeleccionada.codigo);
		
		var fechaInicio = filaSeleccionada.fxIniVigencia;
		
		Ext.get('txtFechaInicio').dom.value = fechaInicio;
				
		var fechaFin = filaSeleccionada.fxFinVigencia;
		Ext.get('txtFechaFin').dom.value = fechaFin;
		Ext.get('txtFechaFin').dom.readOnly = true;
	 	Ext.get('divTxtFechaFin').addClass('dis');
	 	Ext.get('btnCalendarFechaFin').dom.disabled = true;
		
		Ext.get('txtUsuarioAlta').dom.value = filaSeleccionada.usuarioAlta;
		Ext.get('txtUsuarioAlta').dom.readOnly = true;
	 	Ext.get('txtUsuarioAlta').addClass('dis');
	 		
	 	Ext.get('txtUsuarioMod').dom.value = filaSeleccionada.usuarioModif;
		Ext.get('txtUsuarioMod').dom.readOnly = true;
	 	Ext.get('txtUsuarioMod').addClass('dis');
	 		
		Ext.get('txtDescripcion').dom.value = Texto_HTML(filaSeleccionada.descripcion);
		
		cargarGrupoResponsable(filaSeleccionada.grupoResponsable);
		cargarValorDescripcion();
		
		record_antiguo = filaSeleccionada;
		record_antiguo.codigo = Texto_HTML(filaSeleccionada.codigo);		
		record_antiguo.descripcion = Texto_HTML(filaSeleccionada.descripcion);
		
		Ext.get('btnGuardar').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
		Ext.get('btnGuardar').addClass('btnDis');
		Ext.get('btnGuardar').dom.disabled = true;
	 }
	 
	//Funcion que carga el valor seleccionado en el combo
	var cargarValorDescripcion = function(){
		
		Ext.get('txtDescripcionGr').dom.value = Ext.get('selGrupoRes').dom.value;
		Ext.get('txtDescripcionGr').dom.readOnly = true;
	 	Ext.get('txtDescripcionGr').addClass('dis');
	}
	  
	//Cargamos el valor para grupo responsable según el grid
	var cargarGrupoResponsable = function(valor){
		
		for (var i=0;i < Ext.get('selGrupoRes').dom.options.length;i++){
			if (Ext.get('selGrupoRes').dom.options[i].text == valor){
				Ext.get('selGrupoRes').dom.value = valor;
				Ext.get('selGrupoRes').dom.selectedIndex = i;
			}
		}
	} 
	  
	 
	//Funcion que cancela la modificación o el alta de un mensaje de error  
	var cancelarMensajeError = function() {
		Ext.get('txtCodigo').dom.value='';
		Ext.get('txtFechaInicio').dom.value='';
		Ext.get('txtFechaFin').dom.value='';
		Ext.get('txtUsuarioAlta').dom.value='';
		Ext.get('txtUsuarioMod').dom.value='';
		Ext.get('txtDescripcion').dom.value='';
		Ext.get('selGrupoRes').dom.value='';
		
		filaSeleccionada = null;
		Ext.getCmp('gridMensajeError').getSelectionModel().clearSelections(true);
		// deshabilitamos el botón Eliminar
		Ext.get('btnEliminar').dom.disabled = true;
		Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';
		Ext.get('btnEliminar').addClass('btnDis');
		
		record_antiguo=null;
		alta=false;
					
		//Ocultamos el formulario
		Ext.get('idFormMensajeError').setVisibilityMode(Ext.Element.DISPLAY).hide();
	}
	
	//Inicio de calendarios
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
				comprobarHabilitarGuardar();
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
				comprobarHabilitarGuardar();
			}
		});
 	
	}
	
	return {
		init: function (){
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			
			myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
			myMask.show();
			
			pintarGrid();
			
			if (Ext.isIE6){
				var ancho = document.body.offsetWidth - 38;
				grid.setWidth (ancho);
			}
			cancelarMensajeError();
			controlEventos();
			obtenerDatos();
			iniciarCalendarios();
		}
	}
	
}();

Ext.onReady (CGLOBAL.init, CGLOBAL, true);
</sec:authorize>