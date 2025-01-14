<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
Ext.ns("CGSRVNA");
CGSRVNA = function(){

   //Variables globales	
   var grid = null;
   var alta = false;
   var record_antiguo;
   var hay_seleccion=true;
   var suspender_eventos=false;
   var datosResultado;
   var myMask;
   
   //Variables para radioB  y checks
	var onlineBatch = '';
	var tipo = '';
	var display = '';
	var logError = '';
	var habilitado = ''; 
	
    //paginacion.
    hayPaginacion = "";
    posicionPaginacion = null;
    maxPagina = 0;
    nejecucion = 0;
    nejecucionOld = 0;
    posicionPaginacionOld = null;
   
   //Variables para cambios en radioB  y checks    
 	var onlineBatchC = '';
	var tipoC = '';
	
  	var displayC = '';
	var logErrorC = '';
	var habilitadoC = '';
	
   var filaSeleccionada = null;	
	
   //Funcion devuelta por obtenerDatos 
   var callBackObtener = function(correcto,datos){
		if(correcto){
		    hayPaginacion = datos.indPgnIn;
            posicionPaginacion = datos.pgnTx;
   			Ext.getCmp('gridSerNA').getStore().loadData(datos.listaDatos);
   			obtenerUsuario();
		}
	}
    
    //Funcion que carga el listado de mensajes de error
    var obtenerDatos = function (callback){
    
    	Ext.getCmp('gridSerNA').getStore().removeAll();
    	
        posicionPaginacionOld.push(posicionPaginacion); 
        nejecucionOld = nejecucion;
        
   		var datosObtener={ 
   		   nejecucion: nejecucion,
   		   pgnTx:  posicionPaginacion
		}    
		
		llamadaAjax('ServiciosNA.do','buscar','serviciosNAJSON',datosObtener,callBackObtener,false);	
    }
   
    //Funcion devuelta por obtenerUsuario
   	var callBackObtenerUsu = function(correcto,datos){
		if(correcto){
   			Ext.get('hiddenUsuarioConectado').dom.value = datos;
		}
	}
    
    //Funcion que carga el listado de Servicios NA
    var obtenerUsuario = function (){
    
   		var datosUsuario={	  
		}    
		
		llamadaAjax('ServiciosNA.do','obtenerUsuario','serviciosNAJSON',datosUsuario,callBackObtenerUsu,false);	
    }

	<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
	//Funcion devuelta por alta-modificacion-baja
    var callBackGenerar = function(correcto,datos){
		if(correcto){
		    hayPaginacion = "";
            posicionPaginacion = null;
            nejecucion = 0;
            nejecucionOld = 0;
			cancelarServicioNA();
		
			obtenerDatos();
		}
	}

	//Funcion que modifica un Servicio NA
    var modificarDatos = function (){

		var servicioNA = {
			codigo:HTML_Texto(Ext.get('txtCodigoSer').dom.value),
			descripcion:HTML_Texto(Ext.get('txtDescripcionSer').dom.value),
			funcion:Ext.get('selFuncionSer').dom.value,
			onlineBatch: onlineBatchC,
			tipo: tipoC,
			display: cogerValorCheck('chkDis'),
			logError: cogerValorCheck('chkLog'),
			habilitado: cogerValorCheck('chkHab'),
			fxIniVigencia:Ext.get('txtFechaInicioSer').dom.value,
			fxFinVigencia:Ext.get('txtFechaFinSer').dom.value,
			usuarioAlta:Ext.get('txtUsuarioAlta').dom.value,
			usuarioModif:Ext.get('hiddenUsuarioConectado').dom.value,
			accion: 'M'	
		};
		llamadaAjax('ServiciosNA.do','gestionar','serviciosNAJSON',servicioNA,callBackGenerar,false);
    }
    
    //Funcion que da de alta un Servicio NA
    var altaDatos = function (){
    
		var servicioNA = {
			codigo:HTML_Texto(Ext.get('txtCodigoSer').dom.value),
			descripcion:HTML_Texto(Ext.get('txtDescripcionSer').dom.value),
			funcion:Ext.get('selFuncionSer').dom.value,
			onlineBatch: onlineBatchC,
			tipo: tipoC,
			display: cogerValorCheck('chkDis'),
			logError: cogerValorCheck('chkLog'),
			habilitado: cogerValorCheck('chkHab'),
			fxIniVigencia:Ext.get('txtFechaInicioSer').dom.value,
			fxFinVigencia:Ext.get('txtFechaFinSer').dom.value,
			usuarioAlta:Ext.get('txtUsuarioAlta').dom.value,
			usuarioModif:Ext.get('hiddenUsuarioConectado').dom.value,
			accion: 'A'	
		};
		llamadaAjax('ServiciosNA.do','gestionar','serviciosNAJSON',servicioNA,callBackGenerar,false);
    }

	//Funcion que elimina un Servicio NA
    var eliminarDatos = function (){
    
		var record = filaSeleccionada;
		var servicioNA = {
			codigo:record.codigo,
			descripcion:record.descripcion,
			funcion:record.funcion,
			onlineBatch: record.onlineBatch,
			tipo: record.tipo,
			display: record.display,
			logError: record.logError,
			habilitado: record.habilitado,
			fxIniVigencia:record.fxIniVigencia,
			fxFinVigencia:record.fxFinVigencia,
			usuarioAlta:record.usuarioAlta,
			usuarioModif:record.usuarioModif,
			accion: 'B'	
		};
		llamadaAjax('ServiciosNA.do','gestionar','serviciosNAJSON',servicioNA,callBackGenerar,false);	
    }    
	
	//Funcion que agrega un nuevo servicio NA a la lista
	var nuevoServicioNA = function(){

		comprobarHabilitarGuardar();

		var codigo = Ext.get('txtCodigoSer').dom.value;
		var descripcion = Ext.get('txtDescripcionSer').dom.value;
		
		var funcion = Ext.get('selFuncionSer').dom.value;
				
		var fechaInicio = Ext.get('txtFechaInicioSer').dom.value;
		var fechaFin = Ext.get('txtFechaFinSer').dom.value;
		
		if (codigo.trim().length>0 ||descripcion.trim().length>0 ||funcion.trim().length>0 || fechaInicio.trim().length>0 ||
			onlineBatch.trim().length>0 ||tipo.trim().length>0 ||display.trim().length>0 || logError.trim().length>0 || habilitado.trim().length>0){
			if (record_antiguo != null &&
				record_antiguo.codigo == HTML_Texto(Ext.get('txtCodigoSer').dom.value) &&
				record_antiguo.descripcion == HTML_Texto(Ext.get('txtDescripcionSer').dom.value) &&
				record_antiguo.funcion == Ext.get('selFuncionSer').dom.value &&
				record_antiguo.onlineBatch == onlineBatchC &&
				record_antiguo.tipo == tipoC &&
				record_antiguo.display == displayC &&
				record_antiguo.logError == logErrorC &&
				record_antiguo.habilitado == habilitadoC &&
				record_antiguo.fxIniVigencia == fechaInicio){
				
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
					   			Ext.get('txtCodigoSer').dom.focus();
					   		}else if (descripcion.trim().length==0){
					   			Ext.get('txtDescripcionSer').dom.focus();
					   		}else if (funcion.trim().length==0){
					   			Ext.get('selFuncionSer').dom.focus();
					   		}else if (fechaInicio.trim().length==0){
					   			Ext.get('txtFechaInicioSer').dom.focus();
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
		//Habilitamos el codigo y les quitamos la clase que le da estilos de deshabiltiado
		Ext.get('txtCodigoSer').dom.readOnly = false;
		Ext.get('txtCodigoSer').removeClass('dis');
	
		//Si hay un registro seleccionado lo deseleccionamos
		if (filaSeleccionada != null) {
			hay_seleccion=true;
			
			filaSeleccionada = null;
			Ext.getCmp('gridSerNA').getSelectionModel().clearSelections(true);
			<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
			//Deshabilitamos el boton Eliminar
			Ext.get('btnEliminarSer').dom.disabled = true;
			Ext.get('btnEliminarSer').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';
			Ext.get('btnEliminarSer').addClass('btnDis');
			</sec:authorize>
		}
		//Limpiamos los valores del formulario
		Ext.get('txtCodigoSer').dom.value='';
		Ext.get('selFuncionSer').dom.value='';
		
		//Variables para radioB  y checks
		onlineBatch = '';
		tipo = '';
		display = '';
		logError = '';
		habilitado = ''; 
	
	   	//Variables para cambios en radioB  y checks    
	 	onlineBatchC = '';
		tipoC = '';
		displayC = '';
		logErrorC = '';
		habilitadoC = '';
		
		Ext.get('rbBatch').dom.checked=false;
		Ext.get('rbOnline').dom.checked=false;
		Ext.get('rbConsulta').dom.checked=false;
		Ext.get('rbAutorizacion').dom.checked=false;
		
		Ext.get('chkDis').dom.checked=false;
		Ext.get('chkLog').dom.checked=false;
		Ext.get('chkHab').dom.checked=false;
		
		Ext.get('txtFechaInicioSer').dom.value='';
		Ext.get('divTxtFechaInicioSer').removeClass('dis');
		Ext.get('txtFechaInicioSer').dom.readOnly = false;
  		Ext.get('btnCalendarFechaInicioSer').dom.disabled = false;
  		
  		Ext.get('txtFechaFinSer').dom.value = '31.12.2500';
		Ext.get('txtFechaFinSer').dom.readOnly = true;
		Ext.get('txtFechaFinSer').addClass('dis');
	 	Ext.get('divTxtFechaFinSer').addClass('dis');
	 	Ext.get('btnCalendarFechaFinSer').dom.disabled = true;
		
		Ext.get('txtUsuarioAltaSer').dom.value=Ext.get('hiddenUsuarioConectado').dom.value;
		Ext.get('txtUsuarioAltaSer').dom.readOnly = true;
		Ext.get('txtUsuarioAltaSer').addClass('dis');
		
		Ext.get('txtUsuarioModSer').dom.value='';
		Ext.get('txtUsuarioModSer').dom.readOnly = true;
		Ext.get('txtUsuarioModSer').addClass('dis');
		
		Ext.get('txtDescripcionSer').dom.value='';
		Ext.get('txtDescripcionSer').dom.readOnly = false;
		Ext.get('txtDescripcionSer').removeClass('dis');
		
		//Mostramos el formulario
		Ext.get('idFormServiciosNA').setVisibilityMode(Ext.Element.DISPLAY).show();
		//Colocamos el foco en el campo de texto Codigo
		Ext.get('txtCodigoSer').dom.focus ();
		
		Ext.get('btnGuardarSer').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
		Ext.get('btnGuardarSer').addClass('btnDis');
		Ext.get('btnGuardarSer').dom.disabled = true;		
	}
	</sec:authorize>
	//Funcion que carga los datos para modificarlos o dar de alta
	var cargarDatosModificacion = function (cancelar){	 
		<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
		comprobarHabilitarGuardar();
		</sec:authorize>
		var codigoSer = Ext.get('txtCodigoSer').dom.value;
		var descripcionSer = Ext.get('txtDescripcionSer').dom.value;
		var funcionSer = Ext.get('selFuncionSer').dom.value;
		
		var fechaInicio = Ext.get('txtFechaInicioSer').dom.value;
		var fechaFin = Ext.get('txtFechaFinSer').dom.value;
				
		if (alta){
			if (codigoSer.trim().length>0 || descripcionSer.trim().length>0 || funcionSer.trim().length>0 || fechaInicio.trim().length>0 ||
				onlineBatchC.trim().length>0 || tipoC.trim().length>0 || displayC.trim().length>0 || logErrorC.trim().length>0 || habilitadoC.trim().length>0){
				Ext.Msg.show({
				   title:'Los datos se perder&aacute;n',
				   msg: '<span>Se van a perder los datos introducidos, &iquest;Desea continuar?</span>',
				   buttons: Ext.Msg.YESNO,
				   icon: Ext.MessageBox.WARNING,
				   fn:function(respuesta){
				   		if (respuesta == 'yes'){
				   			alta=false;
				   			if(cancelar){
							   	cancelarServicioNA();
							}else{
					  			cargarValoresFomularioMod();
					  		}
				   		}else{
				   			hay_seleccion=true;
				   			
				   			filaSeleccionada = null;
							Ext.getCmp('gridSerNA').getSelectionModel().clearSelections(true);
				   			
				   			Ext.get('txtCodigoSer').dom.focus();
				   		}
				   }
				});
			}else {
				alta=false;
		  		if(cancelar){
				   	cancelarServicioNA();
				}else{
		  			cargarValoresFomularioMod();
		  		}
			}
	  	}else{
	  		//MODO MODIFICACION
			if (codigoSer.trim().length>0 || descripcionSer.trim().length>0 || funcionSer.trim().length>0 || fechaInicio.trim().length>0 ||
				onlineBatchC.trim().length>0 || tipoC.trim().length>0 || displayC.trim().length>0 || logErrorC.trim().length>0 || habilitadoC.trim().length>0){
				if (record_antiguo != null &&
					record_antiguo.codigo == HTML_Texto(Ext.get('txtCodigoSer').dom.value) &&
					record_antiguo.descripcion == HTML_Texto(Ext.get('txtDescripcionSer').dom.value) &&
					record_antiguo.funcion == Ext.get('selFuncionSer').dom.value &&
					record_antiguo.onlineBatch == onlineBatchC &&
					record_antiguo.tipo == tipoC &&
					record_antiguo.display == displayC &&
					record_antiguo.logError == logErrorC &&
					record_antiguo.habilitado == habilitadoC &&
					record_antiguo.fxIniVigencia == fechaInicio){
					
					if(cancelar){
					   	cancelarServicioNA();
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
								   	cancelarServicioNA();
								}else{
						  			cargarValoresFomularioMod();
						  		}
					   		}else{
					   			hay_seleccion=false;
					   			suspender_eventos=true;
					   			
					   			var index = grid.getStore().indexOf (record_antiguo);
					   			filaSeleccionada = record_antiguo;
					   			Ext.get('txtDescripcionSer').dom.focus();
					   		}
					   }
					});
				}
			}else{
				Ext.get('idFormServiciosNA').setVisibilityMode(Ext.Element.DISPLAY).show();
				if(cancelar){
				   	cancelarServicioNA();
				}else{
		  			cargarValoresFomularioMod();
		  		}
				
			}
	  	}					
	}

	//Funcion que cancela la modificación o el alta de una Autorizacion
	var cancelarServicioNA = function() {
		Ext.get('txtCodigoSer').dom.value='';
		Ext.get('txtDescripcionSer').dom.value='';
		Ext.get('selFuncionSer').dom.value='';
		
		Ext.get('rbBatch').dom.checked=false;
		Ext.get('rbOnline').dom.checked=false;
		Ext.get('rbConsulta').dom.checked=false;
		Ext.get('rbAutorizacion').dom.checked=false;
		
		Ext.get('chkDis').dom.checked=false;
		Ext.get('chkLog').dom.checked=false;
		Ext.get('chkHab').dom.checked=false;
		
		//Variables para radioB  y checks
		onlineBatch = '';
		tipo = '';
		display = '';
		logError = '';
		habilitado = ''; 
	
	   	//Variables para cambios en radioB  y checks    
	 	onlineBatchC = '';
		tipoC = '';
		displayC = '';
		logErrorC = '';
		habilitadoC = ''; 
		
		Ext.get('txtFechaInicioSer').dom.value='';
		Ext.get('txtFechaFinSer').dom.value='';
		
		filaSeleccionada = null;
		Ext.getCmp('gridSerNA').getSelectionModel().clearSelections(true);
		<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
		Ext.get('btnEliminarSer').dom.disabled = true;
		Ext.get('btnEliminarSer').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';
		Ext.get('btnEliminarSer').addClass('btnDis');
		</sec:authorize>
		record_antiguo=null;
		alta=false;
					
		//Ocultamos el formulario
		Ext.get('idFormServiciosNA').setVisibilityMode(Ext.Element.DISPLAY).hide();
	}
	
	//Funcion que selecciona en el combo el elemento indicado por parametros
	var cargarFuncion = function(valor){	
		for (var i=0;i< Ext.get('selFuncionSer').dom.options.length;i++){
			if (Ext.get('selFuncionSer').dom.options[i].value == valor){
				Ext.get('selFuncionSer').dom.value = valor;
				Ext.get('selFuncionSer').dom.selectedIndex = i;
			}
		}
	}	

	//Funcion que selecciona/deselecciona los elementos de radioButton "Programa"
	var cargarRadioPrograma = function(valor){
		if (valor == "O"){
			Ext.get('rbBatch').dom.checked = false;
			Ext.get('rbOnline').dom.checked = true;
		}else if (valor == "B"){
			Ext.get('rbBatch').dom.checked = true;
			Ext.get('rbOnline').dom.checked = false;
		}
	}

	//Funcion que selecciona/deselecciona los elementos de radioButton "Tipo Servicio"
	var cargarRadioServicio = function(valor){
		if (valor == "C"){
			Ext.get('rbConsulta').dom.checked = true;
			Ext.get('rbAutorizacion').dom.checked = false;
		}else if (valor == "A"){
			Ext.get('rbConsulta').dom.checked = false;
			Ext.get('rbAutorizacion').dom.checked = true;
		}
	}
	
	//Funcion para marcar/desmarcar los checks de la pestania
	var cargarChecks = function(){
		if (filaSeleccionada.display == "S"){
			Ext.get('chkDis').dom.checked = true;
		}else{
			Ext.get('chkDis').dom.checked = false;
		}
		
		if (filaSeleccionada.logError == "S"){
			Ext.get('chkLog').dom.checked = true;
		}else{
			Ext.get('chkLog').dom.checked = false;
		}
		
		if (filaSeleccionada.habilitado == "S"){
			Ext.get('chkHab').dom.checked = true;
		}else{
			Ext.get('chkHab').dom.checked = false;
		}
	}
	
	//Funcion para cargar los valores en el formulario	
	var cargarValoresFomularioMod = function (){
		
		//Inhabilitamos los combos
	 	Ext.get('txtCodigoSer').dom.readOnly = true;
	 	Ext.get('txtCodigoSer').addClass('dis');
	 	Ext.get('txtCodigoSer').dom.value = Texto_HTML(filaSeleccionada.codigo);	
	 		
	 	Ext.get('txtDescripcionSer').dom.value = Texto_HTML(filaSeleccionada.descripcion);
		cargarFuncion(filaSeleccionada.funcion);

		onlineBatch = filaSeleccionada.onlineBatch;
		onlineBatchC = filaSeleccionada.onlineBatch;
		cargarRadioPrograma(onlineBatch);
		
		tipo = filaSeleccionada.tipo;
		tipoC = filaSeleccionada.tipo;
		cargarRadioServicio(tipo);
		
		display = filaSeleccionada.display;
		displayC = filaSeleccionada.display;
		logError = filaSeleccionada.logError;
		logErrorC = filaSeleccionada.logError;		
		habilitado = filaSeleccionada.habilitado;
		habilitadoC = filaSeleccionada.habilitado;
		
		cargarChecks();
	
		var fechaInicio = filaSeleccionada.fxIniVigencia;
		Ext.get('txtFechaInicioSer').dom.value = fechaInicio;
		<sec:authorize ifAnyGranted="ROLE_AU">
		Ext.get('txtFechaInicioSer').dom.readOnly = true;
		Ext.get('txtFechaInicioSer').addClass('dis');
	 	Ext.get('divTxtFechaInicioSer').addClass('dis');
	 	Ext.get('btnCalendarFechaInicioSer').dom.disabled = true;
	 	</sec:authorize>		
		var fechaFin = filaSeleccionada.fxFinVigencia;
		Ext.get('txtFechaFinSer').dom.value = fechaFin;
		Ext.get('txtFechaFinSer').dom.readOnly = true;
		Ext.get('txtFechaFinSer').addClass('dis');
	 	Ext.get('divTxtFechaFinSer').addClass('dis');
	 	Ext.get('btnCalendarFechaFinSer').dom.disabled = true;
		
		Ext.get('txtUsuarioAltaSer').dom.value = filaSeleccionada.usuarioAlta;
		Ext.get('txtUsuarioAltaSer').dom.readOnly = true;
	 	Ext.get('txtUsuarioAltaSer').addClass('dis');
	 		
	 	Ext.get('txtUsuarioModSer').dom.value = Ext.get('hiddenUsuarioConectado').dom.value;
		Ext.get('txtUsuarioModSer').dom.readOnly = true;
	 	Ext.get('txtUsuarioModSer').addClass('dis');
	 	
		record_antiguo = filaSeleccionada;
		<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
		Ext.get('btnGuardarSer').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
		Ext.get('btnGuardarSer').addClass('btnDis');
		Ext.get('btnGuardarSer').dom.disabled = true;
		</sec:authorize>		
		
	}

	//Funciones para seleccionar el radioButton
	var seleccionarRadioPrograma = function (selec){
		if (selec){
			Ext.get('rbBatch').dom.checked = true;
			Ext.get('rbOnline').dom.checked = false;
			onlineBatchC = "B";
		}
		else{
			Ext.get('rbBatch').dom.checked = false;
			Ext.get('rbOnline').dom.checked = true;
			onlineBatchC = "O";
		}
	}
	
	var seleccionarRadioServicio = function (selec){
		if (selec){
			Ext.get('rbConsulta').dom.checked = true;
			Ext.get('rbAutorizacion').dom.checked = false;
			tipoC = "C";
		}
		else{
			Ext.get('rbConsulta').dom.checked = false;
			Ext.get('rbAutorizacion').dom.checked = true;
			tipoC = "A";
		}
	}

	//Funcion que comprueba el check especificado y retorna el valor asociado
	var cogerValorCheck = function(idCheck) {
		return (Ext.get(idCheck).dom.checked ? "S" : "N");
	}

	//Funcion que comprueba que todos los campos estan rellenos
	var comprobarCamposAltaTodos = function() {
		var datosMetidos = Ext.get('txtCodigoSer').dom.value != ''
				&& Ext.get('txtDescripcionSer').dom.value != ''
				&& Ext.get('txtFechaInicioSer').dom.value != '';

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
					record_antiguo.codigo != HTML_Texto(Ext.get('txtCodigoSer').dom.value) ||
					record_antiguo.descripcion != HTML_Texto(Ext.get('txtDescripcionSer').dom.value) ||
					record_antiguo.fxIniVigencia != Ext.get('txtFechaInicioSer').dom.value
					<sec:authorize ifAnyGranted="ROLE_SA">
					||
					record_antiguo.funcion != Ext.get('selFuncionSer').dom.value ||
					record_antiguo.onlineBatch != onlineBatchC ||
					record_antiguo.tipo != tipoC ||
					record_antiguo.display != cogerValorCheck('chkDis') ||
					record_antiguo.logError != cogerValorCheck('chkLog') ||
					record_antiguo.habilitado != cogerValorCheck('chkHab')
					</sec:authorize>
					){

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
			Ext.get('btnGuardarSer').dom.src = contexto + 'images/botones/QGbtGuardar.gif';
			Ext.get('btnGuardarSer').removeClass('btnDis');
			Ext.get('btnGuardarSer').dom.disabled = false;
		} else {
			Ext.get('btnGuardarSer').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
			Ext.get('btnGuardarSer').addClass('btnDis');
			Ext.get('btnGuardarSer').dom.disabled = true;
		}			
	}
	<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
	//Funcion que elimina un Servicio NA
	var eliminarServicioNA = function() {
	
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

	//Funcion que guarda un ServicioNA nuevo
	var guardarServicioNA = function() {
	
		var codigo = Ext.get('txtCodigoSer').dom.value;
		var descripcion = Ext.get('txtDescripcionSer').dom.value;
		var funcion = Ext.get('selFuncionSer').dom.value;
		var fechaInicio = Ext.get('txtFechaInicioSer').dom.value;
		var fechaFin = Ext.get('txtFechaFinSer').dom.value;
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
				   			Ext.get('txtCodigoSer').dom.focus();
				   		}else if (fechaInicio.trim().length==0){
				   			Ext.get('txtFechaInicioSer').dom.focus();
				   		}else if (descripcion.trim().length==0){
				   			Ext.get('txtDescripcionSer').dom.focus();
				   		}
				   }
				});
			}
		}
	}

	//Funcion que comprueba que no existe registros duplicados al modificar o anadir
	var guardarDatos = function (){
		var codigo = HTML_Texto(Ext.get('txtCodigoSer').dom.value);
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
				   msg: '<span>Ya existe un Servicio NA con ese c&oacute;digo.</span>',
				   buttons: Ext.Msg.OK,
				   icon: Ext.MessageBox.ERROR
				});
			}
		}else{
			modificarDatos();
		}
	}	
	</sec:authorize>
	//Inicio de calendarios
	var iniciarCalendarios = function (){
		
		Calendar.setup({
			inputField: "txtFechaInicioSer",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaInicioSer",
			min:fxIniMax,
			max:fxFinMax,
			bottomBar: false,
			align:'Tl///T/l',
			onSelect: function() {
				this.hide();
				<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
				comprobarHabilitarGuardar();
				</sec:authorize>
			}
		});
		
		Calendar.setup({
			inputField: "txtFechaFinSer",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaFinSer",
			min:fxIniMax,
			max:fxFinMax,
			bottomBar: false,
			align:'Tl///T/r',
			onSelect: function() {
				this.hide();
				<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
				comprobarHabilitarGuardar();
				</sec:authorize>
			}
		});
	}

	//Pintar grid
	var pintarGrid = function (){
		
	   // create the data store
	    var store = new Ext.data.Store({
	    	reader: new Ext.data.ObjectReader({}, [
				{name: 'codigo', type: 'string'},
				{name: 'descripcion', type: 'string'},
				{name: 'funcion', type: 'string'},
				{name: 'onlineBatch', type: 'string'},
				{name: 'tipo', type: 'string'},
				{name: 'display', type: 'string'},
				{name: 'logError', type: 'string'},
				{name: 'habilitado', type: 'string'},
				{name: 'fxIniVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'fxFinVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'usuarioAlta', type: 'string'},
				{name: 'usuarioModif', type: 'string'}
			])
	    });
	    
	  	var columnModel = new Ext.grid.ColumnModel([
		    {header: 'C&oacute;digo',sortable: true,width:73,  dataIndex: 'codigo'},		    
            {header: 'Descripci&oacute;n', id:'colDescripcion',width:164,sortable: true, dataIndex: 'descripcion',renderer:addTooltip},
            {header: 'Funci&oacute;n', sortable: true,width:68, dataIndex: 'funcion',renderer:rendererFuncion},
            {header: 'Programa', sortable: true,width:53, dataIndex: 'onlineBatch',renderer:rendererPrograma},
            {header: 'Tipo Servicio', sortable: true,width:67, dataIndex: 'tipo',renderer:rendererTipo},
            {header: 'Display', sortable: true,width:41, dataIndex: 'display',renderer:rendererCheck},
            {header: 'Log Error', sortable: true,width:51, dataIndex: 'logError',renderer:rendererCheck},
            {header: 'Habiliado', sortable: true,width:51, dataIndex: 'habilitado',renderer:rendererCheck},
            {header: 'Fecha inicio vigencia', sortable: true,width:106, align:'center', dataIndex: 'fxIniVigencia'},
            {header: 'Fecha fin vigencia', sortable: true,width:95, align:'center', dataIndex: 'fxFinVigencia'},
          	{header: 'Usuario de alta',sortable: true,width:79,  align:'center', dataIndex: 'usuarioAlta'},
          	{header: 'Usuario de modif.',sortable: true,width:90,  align:'center', dataIndex: 'usuarioModif'}
		]);
		
		
		var cargarDatosFila = function(fila){
			Ext.each(Ext.query('.marcarFila', grid.id), function(filaMarcada) {
				Ext.fly(filaMarcada).removeClass('marcarFila');
			});
			Ext.fly(grid.getView().getRow(fila)).addClass('marcarFila');
			
			filaSeleccionada = grid.getStore().data.items[fila].data;
			// habilitamos el botón Eliminar
			<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
			Ext.get('btnEliminarSer').dom.disabled = false;
			Ext.get('btnEliminarSer').dom.src = contexto + 'images/botones/QGbtEliminar.gif';
			Ext.get('btnEliminarSer').removeClass('btnDis');
			cargarDatosModificacion(false);
			</sec:authorize>
			hay_seleccion=false;
		}
		
	    // create the Grid
	    grid = new Ext.grid.EditorPasteCopyGridPanel({
	    	id:'gridSerNA',
	        store: store,
			renderTo: 'divGridServiciosNA',
			cls:'gridCG',
			selectionModel: '',
			funcionRollBack: cargarDatosFila,
			cm: columnModel,
	        stripeRows: true,
	        autoExpandColumn: 'colDescripcion',
	        height: 200,
	        autoWidth:true,
	        width:1242,
            bbar: new QGPagingToolbarSrvNAPst({
                pageSize: 10,
                store: store,
                displayInfo: false,
                idGrid: 'gridSerNA'
            })
	    });
	}    
	
	//Funcion para mostrar la descripcion también con un tooltip
    function addTooltip(value,metadata, record, rowIndex, colIndex, store){
	   	//En record viene el elemento formado del grid
		var valorTooltip = "";
		if(colIndex == 1 && record.data.descripcion.length > 15){
			valorTooltip = record.data.descripcion;
		}
		//Accedemos al tooltip
		if(valorTooltip != null && valorTooltip != ''){
			metadata.attr = 'ext:qtip="' + valorTooltip +'"';
		}
		//Debe devolver el valor para rellenar la columna
		return value;
    }	
    
   	//Funcion para mostrar la descripcion de la columna Funcion
	var rendererFuncion = function(data, cellmd, record, rowIndex,colIndex, store) {
		if(data == '')
			return '';
		else if(data == '000')	
			return 'TODAS LAS FUNCIONES';
		else if(data == '100')	
			return 'ADMINISTRACI&Oacute;N DEL SISTEMA';
		else if(data == '200')	
			return 'CONSULTAS';
	}

   	//Funcion para mostrar la descripcion de la columna Programa
	var rendererPrograma = function(data, cellmd, record, rowIndex,colIndex, store) {
		if(data == 'O')	
			return 'Online';
		else if(data == 'B')	
			return 'Batch';
	}    
    
    //Funcion para mostrar la descripcion de la columna Tipo Servicio
	var rendererTipo = function(data, cellmd, record, rowIndex,colIndex, store) {
		if(data == 'A')	
			return 'Actualizaci&oacute;n';
		else if(data == 'C')	
			return 'Consulta';
	} 

    //Funcion para mostrar la descripcion de la columnas de tipo check
	var rendererCheck = function(data, cellmd, record, rowIndex,colIndex, store) {
		if(data == 'S')	
			return 'SI';
		else if(data == 'N')	
			return 'NO';
	} 
	    
	//Muestra PUBLICA los elementos de esta pestania
	var mostrarServiciosNA = function (){
		
		Ext.get('divAutorizaciones').setVisibilityMode(Ext.Element.DISPLAY).hide();
		<sec:authorize ifAnyGranted="ROLE_SA">
		Ext.get('divSisExt').setVisibilityMode(Ext.Element.DISPLAY).hide();
		</sec:authorize>
		Ext.get('divServiciosNA').setVisibilityMode(Ext.Element.DISPLAY).show();
		
		Ext.get('pesAutorizaciones').removeClass('activa');
		<sec:authorize ifAnyGranted="ROLE_SA">
		Ext.get('pesSisExt').removeClass('activa');
		</sec:authorize>
		Ext.get('pesServiciosNA').addClass('activa');
		
		hayPaginacion = "";
        posicionPaginacion = null;
        nejecucion = 0;
        nejecucionOld = 0;
        posicionPaginacionOld = new Array();
        
		cancelarServicioNA();
				
		obtenerDatos();
	}
	
	//Funcion que imprime los datos de la pantalla en pdf
	var imprimirServiciosNA= function() {
		if (Ext.isIE){
			myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
			myMask.show();
		}

		document.getElementById('ifExportar').src = contexto + 'ServiciosNA.do?exportarPDF=';
		if (Ext.isIE)
			document.getElementById('ifExportar').onreadystatechange = function(){myMask.hide();}; 
	}	

	//Funcion que comprueba que algun campo se ha modificado
	var comprobarCampos = function() {
		var datosMetidos = Ext.get('txtCodigoSer').dom.value == ''
				&& Ext.get('txtDescripcionSer').dom.value == ''
				&& Ext.get('selFuncionSer').dom.value == ''
				&& Ext.get('txtFechaInicioSer').dom.value == '';

		return datosMetidos;
	}

	//Funcion PUBLICA que comprueba si se han realizado cambios en la pantalla tanto en alta como en modificacion, usada para le cambio de pestanias
	var comprobarCambiosEnPantalla = function(){
		if (comprobarCampos()){
			return true;
		}
		else{
			if (record_antiguo != null &&
						record_antiguo.codigo == HTML_Texto(Ext.get('txtCodigoSer').dom.value) &&
						record_antiguo.descripcion == HTML_Texto(Ext.get('txtDescripcionSer').dom.value) &&
						record_antiguo.funcion == Ext.get('selFuncionSer').dom.value &&
						record_antiguo.onlineBatch == onlineBatchC &&
						record_antiguo.tipo == tipoC &&
						record_antiguo.display == displayC &&
						record_antiguo.logError == logErrorC &&
						record_antiguo.habilitado == habilitadoC &&
						record_antiguo.fxIniVigencia == Ext.get('txtFechaInicioSer').dom.value){
				return true;
			}
			else{
				return false;
			}
		}			
	}	
	
	var controlEventos = function(){
		//llama a comprobar si hay cambio de pestañas
		Ext.get('pesServiciosNA').on('click', function() {
			comprobarCambio('serviciosNA');
		});
		<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
		// evento click del botón nuevo
		Ext.get('btnNuevoSer').on('click', function() {
			nuevoServicioNA();
		});
		// click en radioButton Programa - Batch
		Ext.get('rbBatch').on('click', function() {
			seleccionarRadioPrograma(true);
			comprobarHabilitarGuardar();
		});
		// click en radioButton Programa - Online
		Ext.get('rbOnline').on('click', function() {
			seleccionarRadioPrograma(false);
			comprobarHabilitarGuardar();
		});
		// click en radioButton Tipo de Consulta - Consulta
		Ext.get('rbConsulta').on('click', function() {
			seleccionarRadioServicio(true);
			comprobarHabilitarGuardar();
		});
		// click en radioButton Tipo de Consulta - Actualizacion
		Ext.get('rbAutorizacion').on('click', function() {
			seleccionarRadioServicio(false);
			comprobarHabilitarGuardar();
		});
		// click en check Display
		Ext.get('chkDis').on('click', function() {
			displayC = cogerValorCheck('chkDis');
			comprobarHabilitarGuardar();
		});
		// click en check Log Error			
		Ext.get('chkLog').on('click', function() {
			logErrorC = cogerValorCheck('chkLog');
			comprobarHabilitarGuardar();
		});
		// click en check Habilitado	
		Ext.get('chkHab').on('click', function() {
			habilitadoC = cogerValorCheck('chkHab');
			comprobarHabilitarGuardar();
		});			
		// cambios en la fecha de inicio
		Ext.get('txtFechaInicioSer').on('change', function(){			 
			comprobarHabilitarGuardar();
		});
		// evento para los campos editables de la parte Alta/Modif	
		Ext.get('txtCodigoSer').on('change', function(){			 
			comprobarHabilitarGuardar();
		});
		// cambios en el campo descripcion
		Ext.get('txtDescripcionSer').on('change', function(){			 
			comprobarHabilitarGuardar();
		});	
		// cambios en el combo funcion
		Ext.get('selFuncionSer').on('change', function(){			 
			comprobarHabilitarGuardar();
		});
		// evento click del botón Cancelar
		Ext.get('btnCancelarSer').on('click', function() {
			cargarDatosModificacion(true);
		});
		// evento click del botón dar de baja
		Ext.get('btnEliminarSer').on('click', function() {
			if (filaSeleccionada != null) {
				eliminarServicioNA();
			}
		});
		// evento click del botón Guardar
		Ext.get('btnGuardarSer').on('click', function() {
			guardarServicioNA();
		});
		</sec:authorize>
		// evento click del botón Imprimir
		//Ext.get('btnImprimirSer').on('click', function() {
			//imprimirServiciosNA();
		//});		
				
	}
	
    return {
		init: function() {
			Ext.QuickTips.init();
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			
			pintarGrid();
			
			if (Ext.isIE6){
				var ancho = document.body.offsetWidth - 38;
				grid.setWidth (ancho);
			}			
			controlEventos();
			iniciarCalendarios ();
		},
		mostrarServiciosNA: function (){
			mostrarServiciosNA();
		},
		comprobarCambiosEnPantalla: function (){
			return comprobarCambiosEnPantalla();
		}
	
	}
	 
}();
function mostrarServiciosNA(){
	return CGSRVNA.mostrarServiciosNA();
}
function comprobarCambiosEnPantalla(){
	CGSRVNA.comprobarCambiosEnPantalla();
}
Ext.onReady(CGSRVNA.init, CGSRVNA, true);
</sec:authorize>