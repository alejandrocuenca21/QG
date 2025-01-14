<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize ifAnyGranted="ROLE_SA"> 
Ext.ns("CGSISEXT");
CGSISEXT = function(){

   //Variables globales		
   var grid = null;
   var alta = false;
   var record_antiguo;
   var hay_seleccion=true;
   var suspender_eventos=false;
   var datosResultado;
   var myMask;
   //paginacion 
   hayPaginacion = "";
   posicionPaginacion = null;
   maxPagina = 0;
   nejecucion = 0;
   
   nejecucionOld = 0;
   posicionPaginacionOld = null;
   
   var arrayComboSisExt = null;
   
   
   var filaSeleccionada = null;
   
   //Funcion devuelta por obtenerDatosLinea 
   var callBackObtenerLinea = function(correcto,datos){
		if(correcto){
   			var arrayCombo = new Array();
			arrayComboSisExt = new Array();
			arrayCombo.push({texto: "", valor: ""});
			arrayComboSisExt.push({texto: "", valor: ""});

			Ext.each(datos,function(dato){
				arrayCombo.push({texto: dato.nombre, valor: dato.descripcion});
				arrayComboSisExt.push({texto: dato.nombre, valor: dato.descripcion});//para los combos de la pantalla de alta

			});
			agregarValoresCombo('lineaNegocioSis', arrayCombo, true);//combo de la busqueda
		}
		obtenerDatos();
	}
    
    //Funcion que carga el combo para Lineas de Negocio
    var obtenerDatosLinea = function (callback){
    
   		var datosObtener={   
		}    
		
		llamadaAjax('SistemasExternos.do','buscarLinNegocio','sistemasExternosJSON',datosObtener,callBackObtenerLinea,false);	
    }
   
    
   //Funcion devuelta por obtenerDatos 
   var callBackObtener = function(correcto,datosResultado){
		if(correcto){
		    hayPaginacion = datosResultado.indPgnIn;
            posicionPaginacion = datosResultado.pgnTx; 
   			Ext.getCmp('gridSisExt').getStore().loadData(datosResultado.datos);
   			Ext.getCmp('gridSisExt').getView().refresh();

   			obtenerUsuario();
		}
	}
    
    //Funcion que carga el listado de Sistemas Externos
    var obtenerDatos = function (callback){
    	   			
    	Ext.getCmp('gridSisExt').getStore().removeAll();
  		hayPaginacion = "";
	    posicionPaginacion = null;
        nejecucion = 0;	
   		var datosObtener={ 
   		   pgnTx: null,
           nejecucion: 0  
		}    
		
		llamadaAjax('SistemasExternos.do','buscar','busquedaJSON',datosObtener,callBackObtener,true,true);	
    }
   
   var obtenerDatosMod = function (callback){
        Ext.getCmp('gridSisExt').getStore().removeAll();
        var posicion =  nejecucion - 1;   
        var datosObtener={ 
           pgnTx: posicionPaginacionOld[posicion],
           nejecucion: nejecucion  
        }    
        
        llamadaAjax('SistemasExternos.do','buscarParaModific','busquedaJSON',datosObtener,callBackObtener,true,true);  
    
    
   }
   
    //Funcion devuelta por obtenerUsuario
   	var callBackObtenerUsu = function(correcto,datos){
		if(correcto){
   			Ext.get('hiddenUsuarioConectado').dom.value = datos;
		}
	}
    
    //Funcion que carga el listado de Sistemas Externos
    var obtenerUsuario = function (){
    
   		var datosUsuario={
   			  
		}    
		
		llamadaAjax('SistemasExternos.do','obtenerUsuario','sistemasExternosJSON',datosUsuario,callBackObtenerUsu,false);	
    }

	//Funcion devuelta por alta-modificacion-baja
    var callBackGenerar = function(correcto,datos){
		if(correcto){
			cancelarSistemasExternos();
			obtenerDatos();
		}
	}
	//Funcion devuelta por modificacion 
	var callBackModificacion = function(correcto,datos){
	   if(correcto){
	       cancelarSistemasExternos();
	       obtenerDatosMod();
	   }
	   
	}
	//Funcion que modifica un Sistema Externo
    var modificarDatos = function (){

		var sistema = {
			anagrama:HTML_Texto(Ext.get('txtAnagramaSis').dom.value),
			nombre:HTML_Texto(Ext.get('txtNombreSis').dom.value),
			descripcion: HTML_Texto(Ext.get('txtDescripcionSis').dom.value),
			lineaNegocio:Ext.get('lineaNegocioSis').dom.options[Ext.get('lineaNegocioSis').dom.selectedIndex].text,
			fxIniVigencia:Ext.get('txtFechaInicioSis').dom.value,
			fxFinVigencia:Ext.get('txtFechaFinSis').dom.value,
			usuarioAlta:Ext.get('txtUsuarioAltaSis').dom.value,
			usuarioModif:Ext.get('hiddenUsuarioConectado').dom.value,
			accion: 'M'
		};
		llamadaAjax('SistemasExternos.do','gestionar','sistemasExternosJSON',sistema,callBackModificacion,false);
    }
    
    //Funcion que da de alta un nuevo Sistema Externo
    var altaDatos = function (){
    
		var sistema = {
			anagrama:HTML_Texto(Ext.get('txtAnagramaSis').dom.value),
			nombre:HTML_Texto(Ext.get('txtNombreSis').dom.value),
			descripcion: HTML_Texto(Ext.get('txtDescripcionSis').dom.value),
			lineaNegocio:Ext.get('lineaNegocioSis').dom.options[Ext.get('lineaNegocioSis').dom.selectedIndex].text,
			fxIniVigencia:Ext.get('txtFechaInicioSis').dom.value,
			fxFinVigencia:Ext.get('txtFechaFinSis').dom.value,
			usuarioAlta:Ext.get('txtUsuarioAltaSis').dom.value,
			usuarioModif:Ext.get('hiddenUsuarioConectado').dom.value,
			accion: 'A'
		};
		llamadaAjax('SistemasExternos.do','gestionar','sistemasExternosJSON',sistema,callBackGenerar,false);
    }

	//Funcion que elimina un Sistema Externo
    var eliminarDatos = function (){
    
		var record = filaSeleccionada;
		var sistema = {
			anagrama:record.anagrama,
			nombre:record.nombre,
			descripcion:record.descripcion,
			lineaNegocio:record.lineaNegocio,
			fxIniVigencia:record.fxIniVigencia,
			fxFinVigencia:record.fxFinVigencia,
			usuarioAlta:record.usuarioAlta,
			usuarioModif:record.usuarioModif,
			accion: 'B'
		};
		
		llamadaAjax('SistemasExternos.do','gestionar','sistemasExternosJSON',sistema,callBackGenerar,false);
    }
	
	//Funcion que elimina un Sistema Externo
	var eliminarSisExt = function() {
	
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

	//Funcion PUBLICA que muestra los datos de esta pestania
	var mostrarSisExt = function (){
		
		Ext.get('divAutorizaciones').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divSisExt').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('divServiciosNA').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		Ext.get('pesAutorizaciones').removeClass('activa');
		Ext.get('pesSisExt').addClass('activa');
		Ext.get('pesServiciosNA').removeClass('activa');
		
		hayPaginacion = "";
        posicionPaginacion = null;
        nejecucion = 0;
        nejecucionOld = 0;
        posicionPaginacionOld = new Array();
        
		Ext.getCmp('gridSisExt').destroy();
		
		pintarGrid();
		
		cancelarSistemasExternos();
		obtenerDatosLinea();
	}
	

	//comprueba que todos los campos estan rellenos
	var comprobarCamposAltaTodos = function() {
		var datosMetidos = Ext.get('txtAnagramaSis').dom.value != ''
				&& Ext.get('txtNombreSis').dom.value != ''
				&& Ext.get('txtDescripcionSis').dom.value != ''
				&& Ext.get('txtFechaInicioSis').dom.value != '';

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
					record_antiguo.anagrama != HTML_Texto(Ext.get('txtAnagramaSis').dom.value) ||
					record_antiguo.nombre != HTML_Texto(Ext.get('txtNombreSis').dom.value) ||
					record_antiguo.descripcion != HTML_Texto(Ext.get('txtDescripcionSis').dom.value) ||
					record_antiguo.lineaNegocio != Ext.get('lineaNegocioSis').dom.options[Ext.get('lineaNegocioSis').dom.selectedIndex].text ||
					record_antiguo.fxIniVigencia != Ext.get('txtFechaInicioSis').dom.value){

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
			Ext.get('btnGuardarSis').dom.src = contexto + 'images/botones/QGbtGuardar.gif';
			Ext.get('btnGuardarSis').removeClass('btnDis');
			Ext.get('btnGuardarSis').dom.disabled = false;
		} else {
			Ext.get('btnGuardarSis').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
			Ext.get('btnGuardarSis').addClass('btnDis');
			Ext.get('btnGuardarSis').dom.disabled = true;
		}			
	}
	
	//Funcion que agrega un nuevo sistema externo a la lista
	var nuevoSistemaExt = function(){

		comprobarHabilitarGuardar();

		var anagrama = Ext.get('txtAnagramaSis').dom.value;
		var nombre = Ext.get('txtNombreSis').dom.value;
		var descripcion = Ext.get('txtDescripcionSis').dom.value;
		var linNegocio = Ext.get('lineaNegocioSis').dom.value;
		var fechaInicio = Ext.get('txtFechaInicioSis').dom.value;
		var fechaFin = Ext.get('txtFechaFinSis').dom.value;
		
		if (anagrama.trim().length>0 ||nombre.trim().length>0 ||linNegocio.trim().length>0 || descripcion.trim().length>0 || fechaInicio.trim().length>0){
			if (record_antiguo != null &&
				record_antiguo.anagrama == HTML_Texto(Ext.get('txtAnagramaSis').dom.value) &&
				record_antiguo.lineaNegocio == Ext.get('lineaNegocioSis').dom.options[Ext.get('lineaNegocioSis').dom.selectedIndex].text &&
				record_antiguo.nombre == HTML_Texto(Ext.get('txtNombreSis').dom.value) &&
				record_antiguo.descripcion == HTML_Texto(Ext.get('txtDescripcionSis').dom.value) &&
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
				   			if (anagrama.trim().length==0){
					   			Ext.get('txtAnagramaSis').dom.focus();
					   		}else if (nombre.trim().length==0){
					   			Ext.get('txtNombreSis').dom.focus();
					   		}else if (descripcion.trim().length==0){
					   			Ext.get('txtDescripcionSis').dom.focus();
					   		}else if (linNegocio.trim().length==0){
					   			Ext.get('lineaNegocioSis').dom.focus();
					   		}else if (fechaInicio.trim().length==0){
					   			Ext.get('txtFechaInicioSis').dom.focus();
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
		Ext.get('txtAnagramaSis').dom.readOnly = false;
		Ext.get('txtAnagramaSis').removeClass('dis');
		
		  		
		//Si hay un registro seleccionado lo deseleccionamos
		if (filaSeleccionada != null) {
			hay_seleccion=true;
			filaSeleccionada = null;
			Ext.getCmp('gridSisExt').getSelectionModel().clearSelections(true);
							
			
			//Deshabilitamos el boton Eliminar
			Ext.get('btnEliminarSis').dom.disabled = true;
			Ext.get('btnEliminarSis').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';
			Ext.get('btnEliminarSis').addClass('btnDis');
		}
		//Limpiamos los valores del formulario
		Ext.get('txtAnagramaSis').dom.value='';
		Ext.get('txtNombreSis').dom.value='';
		Ext.get('lineaNegocioSis').dom.value='';
		Ext.get('txtDescripcionLNS').dom.value='';
		
		Ext.get('txtFechaInicioSis').dom.value='';
		Ext.get('divTxtFechaInicioSis').removeClass('dis');
		Ext.get('txtFechaInicioSis').dom.readOnly = false;
  		Ext.get('btnCalendarFechaInicioSis').dom.disabled = false;
  		
  		Ext.get('txtFechaFinSis').dom.value = '31.12.2500';
		Ext.get('txtFechaFinSis').dom.readOnly = true;
		Ext.get('txtFechaFinSis').addClass('dis');
	 	Ext.get('divTxtFechaFinSis').addClass('dis');
	 	Ext.get('btnCalendarFechaFinSis').dom.disabled = true;
		
		Ext.get('txtUsuarioAltaSis').dom.value=Ext.get('hiddenUsuarioConectado').dom.value;
		Ext.get('txtUsuarioAltaSis').dom.readOnly = true;
		Ext.get('txtUsuarioAltaSis').addClass('dis');
		
		Ext.get('txtUsuarioModSis').dom.value='';
		Ext.get('txtUsuarioModSis').dom.readOnly = true;
		Ext.get('txtUsuarioModSis').addClass('dis');
		
		Ext.get('txtDescripcionSis').dom.value='';
		
		//Mostramos el formulario
		Ext.get('idFormSistemasExternos').setVisibilityMode(Ext.Element.DISPLAY).show();
		//Colocamos el foco en el campo de texto Codigo
		Ext.get('txtAnagramaSis').dom.focus ();

		Ext.get('btnGuardarSis').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
		Ext.get('btnGuardarSis').addClass('btnDis');
		Ext.get('btnGuardarSis').dom.disabled = true;		
	}
	
	//Funcion que carga los datos para modificarlos o dar de alta
	 var cargarDatosModificacion = function (cancelar){	 
	 
	 	comprobarHabilitarGuardar();
	 		
	 	var anagrama = Ext.get('txtAnagramaSis').dom.value;
	 	var nombre = Ext.get('txtNombreSis').dom.value;
	 	var linNegocio = Ext.get('lineaNegocioSis').dom.value;
	 	var descripcion = Ext.get('txtDescripcionSis').dom.value;
		var fechaInicio = Ext.get('txtFechaInicioSis').dom.value;
		var fechaFin = Ext.get('txtFechaFinSis').dom.value;
		
	  	if (alta){
			if (anagrama.trim().length>0 || nombre.trim().length>0 ||linNegocio.trim().length>0 || descripcion.trim().length>0 || fechaInicio.trim().length>0){
				Ext.Msg.show({
				   title:'Los datos se perder&aacute;n',
				   msg: '<span>Se van a perder los datos introducidos, &iquest;Desea continuar?</span>',
				   buttons: Ext.Msg.YESNO,
				   icon: Ext.MessageBox.WARNING,
				   fn:function(respuesta){
				   		if (respuesta == 'yes'){
				   			alta=false;
				   			if(cancelar){
							   	cancelarSistemasExternos();
							}else{
					  			cargarValoresFomularioMod();
					  		}
				   		}else{
				   			hay_seleccion=true;
				   			filaSeleccionada = null;
							Ext.getCmp('gridSisExt').getSelectionModel().clearSelections(true);
				   			Ext.get('txtAnagramaSis').dom.focus();
				   		}
				   }
				});
			}else {
				alta=false;
		  		if(cancelar){
				   	cancelarSistemasExternos();
				}else{
		  			cargarValoresFomularioMod();
		  		}
			}
	  	}else{
	  		//MODO MODIFICACION
			if (anagrama.trim().length>0 ||nombre.trim().length>0 ||linNegocio.trim().length>0 || descripcion.trim().length>0 || fechaInicio.trim().length>0 || fechaFin.trim().length>0){
				if (record_antiguo != null &&
					record_antiguo.anagrama == HTML_Texto(Ext.get('txtAnagramaSis').dom.value) &&
					record_antiguo.lineaNegocio == Ext.get('lineaNegocioSis').dom.options[Ext.get('lineaNegocioSis').dom.selectedIndex].text &&
					record_antiguo.nombre == HTML_Texto(Ext.get('txtNombreSis').dom.value) &&
					record_antiguo.descripcion == HTML_Texto(Ext.get('txtDescripcionSis').dom.value) &&
					record_antiguo.fxIniVigencia == fechaInicio){
					
					if(cancelar){
					   	cancelarSistemasExternos();
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
								   	cancelarSistemasExternos();
								}else{
						  			cargarValoresFomularioMod();
						  		}
					   		}else{
					   			hay_seleccion=false;
					   			suspender_eventos=true;
					   			
					   			var index = grid.getStore().indexOf (record_antiguo);
					   			filaSeleccionada = record_antiguo;
					   			Ext.get('txtFechaInicioSis').dom.focus();
					   		}
					   }
					});
				}
			}else{
				Ext.get('idFormSistemasExternos').setVisibilityMode(Ext.Element.DISPLAY).show();
				if(cancelar){
				   	cancelarSistemasExternos();
				}else{
		  			cargarValoresFomularioMod();
		  		}
				
			}
	  	}
	}
	
	//Funcion que carga el combo de linea de negocio al cargar el registro de la tabla
	var cargarLineaNegocio = function(valor){
		for (var i=0;i< Ext.get('lineaNegocioSis').dom.options.length;i++){
			if (Ext.get('lineaNegocioSis').dom.options[i].text == valor){
				Ext.get('lineaNegocioSis').dom.value = valor;
				Ext.get('lineaNegocioSis').dom.selectedIndex = i;
			}
		}
	}
	
	//Funcion que carga los datos para modificarlos
	var cargarValoresFomularioMod = function (){
	  	//Cargamos los valores en el formulario
	 	Ext.get('txtAnagramaSis').dom.readOnly = true;
	 	Ext.get('txtAnagramaSis').addClass('dis');
		Ext.get('txtAnagramaSis').dom.value = Texto_HTML(filaSeleccionada.anagrama);
			  	
		Ext.get('txtNombreSis').dom.value = Texto_HTML(filaSeleccionada.nombre);

		Ext.get('lineaNegocioSis').dom.value = filaSeleccionada.lineaNegocio;
		cargarLineaNegocio(filaSeleccionada.lineaNegocio);
		Ext.get('txtDescripcionLNS').dom.value = Ext.get('lineaNegocioSis').dom.options[Ext.get('lineaNegocioSis').dom.selectedIndex].value;
		
		var fechaInicio = filaSeleccionada.fxIniVigencia;
		Ext.get('txtFechaInicioSis').dom.value = fechaInicio;
				
		var fechaFin = filaSeleccionada.fxFinVigencia;
		Ext.get('txtFechaFinSis').dom.value = fechaFin;
		Ext.get('txtFechaFinSis').addClass('dis');
		Ext.get('txtFechaFinSis').dom.readOnly = true;
	 	Ext.get('divTxtFechaFinSis').addClass('dis');
	 	Ext.get('btnCalendarFechaFinSis').dom.disabled = true;
		
		Ext.get('txtUsuarioAltaSis').dom.value = filaSeleccionada.usuarioAlta;
		Ext.get('txtUsuarioAltaSis').dom.readOnly = true;
	 	Ext.get('txtUsuarioAltaSis').addClass('dis');
	 		
	 	Ext.get('txtUsuarioModSis').dom.value = Ext.get('hiddenUsuarioConectado').dom.value;
		Ext.get('txtUsuarioModSis').dom.readOnly = true;
	 	Ext.get('txtUsuarioModSis').addClass('dis');
	 		
		Ext.get('txtDescripcionSis').dom.value = Texto_HTML(filaSeleccionada.descripcion);
	 	
		record_antiguo = filaSeleccionada;

		Ext.get('btnGuardarSis').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
		Ext.get('btnGuardarSis').addClass('btnDis');
		Ext.get('btnGuardarSis').dom.disabled = true;
	}	  
	  	
	
	//Funcion que cancela la modificación o el alta de un Sistema Externo
	var cancelarSistemasExternos = function() {
		Ext.get('txtAnagramaSis').dom.value='';
		Ext.get('txtNombreSis').dom.value='';
		Ext.get('lineaNegocioSis').dom.value='';
		Ext.get('txtFechaInicioSis').dom.value='';
		Ext.get('txtFechaFinSis').dom.value='';
		Ext.get('txtUsuarioAltaSis').dom.value='';
		Ext.get('txtUsuarioModSis').dom.value='';
		Ext.get('txtDescripcionSis').dom.value='';
		
		filaSeleccionada = null;
		Ext.getCmp('gridSisExt').getSelectionModel().clearSelections(true);
		
		Ext.get('btnEliminarSis').dom.disabled = true;
		Ext.get('btnEliminarSis').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';
		Ext.get('btnEliminarSis').addClass('btnDis');
		
		record_antiguo=null;
		alta=false;
					
		//Ocultamos el formulario
		Ext.get('idFormSistemasExternos').setVisibilityMode(Ext.Element.DISPLAY).hide();
	}

	//Funcion que guarda un Sistema Externo
	var guardarSistemasExternos = function() {
		var anagrama = Ext.get('txtAnagramaSis').dom.value;
		var nombre = Ext.get('txtNombreSis').dom.value;
		var descripcion = Ext.get('txtDescripcionSis').dom.value;
		var linNegocio = Ext.get('lineaNegocioSis').dom.value;
		var fechaInicio = Ext.get('txtFechaInicioSis').dom.value;
		var fechaFin = Ext.get('txtFechaFinSis').dom.value;
		var dFechaInicio = new Date();
		var dFechaFin = new Date();
		dFechaInicio = Date.parseDate(fechaInicio, 'd.m.Y');
		dFechaFin = Date.parseDate(fechaFin, 'd.m.Y');
		var hoy = new Date();
		var msg = '';
		
		if (anagrama.trim().length>0 && nombre.trim().length>0 && descripcion.trim().length>0 && fechaInicio.trim().length>0){
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
			if (anagrama.trim()=="")
				msg += '<span class="oblig">Sistema Externo</span>';
		
			if (nombre.trim()=="") 
				msg += '<span class="oblig">Nombre</span>';

			if (descripcion.trim()=="") 
				msg += '<span class="oblig">Descripcion</span>';

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
				   		if (anagrama.trim().length==0){
				   			Ext.get('txtAnagramaSis').dom.focus();
				   		}else if (fechaInicio.trim().length==0){
				   			Ext.get('txtFechaInicioSis').dom.focus();
				   		}else if (nombre.trim().length==0){
				   			Ext.get('txtNombreSis').dom.focus();
				   		}else if (descripcion.trim().length==0){
				   			Ext.get('txtDescripcionSis').dom.focus();
				   		}
				   }
				});
			}
		}
	}
	
	//Funcion que imprime los datos de la pantalla en pdf
	var imprimirSistemasExternos= function() {
		if (Ext.isIE){
			myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
			myMask.show();
		}

		document.getElementById('ifExportar').src = contexto + 'SistemasExternos.do?exportarPDF=';	
		if (Ext.isIE)
			document.getElementById('ifExportar').onreadystatechange = function(){myMask.hide();}; 
		
	}

	//Funcion que comprueba que no existe registros duplicados al modificar o anadir
	var guardarDatos = function (){
		var anagrama = HTML_Texto(Ext.get('txtAnagramaSis').dom.value);
		var existe = false;
		
		if (alta){
			//Comprobamos que no exista el codigo en el grid.
			Ext.each(grid.getStore().data.items,function(dato){
				if (dato.data.anagrama.toLowerCase() == anagrama.toLowerCase()){
					existe = true;
				}
			});
 				
			if (!existe){
				altaDatos();
			}else{
				Ext.Msg.show({
				   title:'Registro duplicado',
				   msg: '<span>Ya existe un Sistema Externo con ese anagrama.</span>',
				   buttons: Ext.Msg.OK,
				   icon: Ext.MessageBox.ERROR
				});
			}
		}else{
			modificarDatos();
		}

	}	

	//Inicio de calendarios
	var iniciarCalendarios = function (){
		
		Calendar.setup({
			inputField: "txtFechaInicioSis",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaInicioSis",
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
			inputField: "txtFechaFinSis",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaFinSis",
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

	//Pintar grid
	var pintarGrid = function (){
	 
	   // create the data store
	    var store = new Ext.data.Store({
	    	reader: new Ext.data.ObjectReader({}, [
				{name: 'anagrama', type: 'string'},
				{name: 'nombre', type: 'string'},
				{name: 'descripcion', type: 'string'},
				{name: 'lineaNegocio', type: 'string'},
				{name: 'fxIniVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'fxFinVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'usuarioAlta', type: 'string'},
				{name: 'usuarioModif', type: 'string'}
			])
	    });
	    
	  	var columnModel = new Ext.grid.ColumnModel([
		    {header: 'Anagrama',sortable: true,width:60,  dataIndex: 'anagrama'},		    
            {header: 'Nombre', sortable: true, dataIndex: 'nombre'},
            {header: 'Descripci&oacute;n', id:'colDescripcion',sortable: true, dataIndex: 'descripcion'},
            {header: 'L&iacute;nea Negocio', sortable: true, dataIndex: 'lineaNegocio', renderer:rendererLineaNegocioSis},
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
			// habilitamos el botón Eliminar
			Ext.get('btnEliminarSis').dom.disabled = false;
			Ext.get('btnEliminarSis').dom.src = contexto + 'images/botones/QGbtEliminar.gif';
			Ext.get('btnEliminarSis').removeClass('btnDis');
			
			cargarDatosModificacion(false);
			hay_seleccion=false;
		}
		
	    // create the Grid
	    grid = new Ext.grid.EditorPasteCopyGridPanel({
	    	id:'gridSisExt',
	        store: store,
			renderTo: 'divGridSisExt',
			cls:'gridCG',
			selectionModel: '',
			funcionRollBack: cargarDatosFila,
			cm: columnModel,
	        stripeRows: true,
	        autoExpandColumn: 'colDescripcion',
	        height: 177,
	        autoWidth:true,
	        bbar: new QGPagingToolbarSisExtPst({
                pageSize: 50,
                store: store,
                displayInfo: false,
                idGrid: 'gridSisExt'
            })
	    });
	    
	}

	//Funcion que carga la descripcion de linea de negocio al cargar el registro en tabla
	var rendererLineaNegocioSis = function(data, cellmd, record, rowIndex,colIndex, store) {
		for (var i=0;i< Ext.get('lineaNegocioSis').dom.options.length;i++){
			if (Ext.get('lineaNegocioSis').dom.options[i].text == data){
				return Ext.get('lineaNegocioSis').dom.options[i].value;
			}
		}
	}

	//Funcion que comprueba que algun campo se ha modificado
	var comprobarCampos = function() {
		var datosMetidos = Ext.get('txtAnagramaSis').dom.value == ''
				&& Ext.get('txtNombreSis').dom.value == ''
				&& Ext.get('txtDescripcionSis').dom.value == ''
				&& Ext.get('txtFechaInicioSis').dom.value == ''
				&& Ext.get('lineaNegocioSis').dom.value == '';

		return datosMetidos;
	}
	
	//Funcion PUBLICA que comprueba si se han realizado cambios en la pantalla tanto en alta como en modificacion, usada para le cambio de pestanias
	var comprobarCambiosEnPantalla = function(){
		if (comprobarCampos()){
			return true;
		}
		else{
			if (record_antiguo != null &&
					record_antiguo.anagrama == HTML_Texto(Ext.get('txtAnagramaSis').dom.value) &&
					record_antiguo.lineaNegocio == Ext.get('lineaNegocioSis').dom.options[Ext.get('lineaNegocioSis').dom.selectedIndex].text &&
					record_antiguo.nombre == HTML_Texto(Ext.get('txtNombreSis').dom.value) &&
					record_antiguo.descripcion == HTML_Texto(Ext.get('txtDescripcionSis').dom.value) &&
					record_antiguo.fxIniVigencia == Ext.get('txtFechaInicioSis').dom.value){
				return true;
			}
			else{
				return false;
			}
		}				
	}
	
	var controlEventos = function(){
		//llama a comprobar si hay cambio de pestañas
		Ext.get('pesSisExt').on('click', function() {
			comprobarCambio('sistemasExternos');
		});
		// evento click del botón nuevo
		Ext.get('btnNuevoSis').on('click', function() {
			nuevoSistemaExt();
		});
		// evento click del botón Cancelar
		Ext.get('btnCancelarSis').on('click', function() {
			cargarDatosModificacion(true);
		});
		//carga el codigo de linea de negocio
		Ext.get('lineaNegocioSis').on('change', function(){			 		
			codLinNegocio = Ext.get('lineaNegocioSis').dom.options[Ext.get('lineaNegocioSis').dom.selectedIndex].text;
			Ext.get('txtDescripcionLNS').dom.value = Ext.get('lineaNegocioSis').dom.options[Ext.get('lineaNegocioSis').dom.selectedIndex].value;
			comprobarHabilitarGuardar();	
		});
		// cambios en fecha de inicio
		Ext.get('txtFechaInicioSis').on('change', function(){			 
			comprobarHabilitarGuardar();
		});
		// evento para los campos editables de la parte Alta/Modif	
		Ext.get('txtAnagramaSis').on('change', function(){			 
			comprobarHabilitarGuardar();
		});
		// cambios en descripcion
		Ext.get('txtDescripcionSis').on('change', function(){			 
			comprobarHabilitarGuardar();
		});	
		// cambios en nombre
		Ext.get('txtNombreSis').on('change', function(){			 
			comprobarHabilitarGuardar();
		});
		// evento click del botón Guardar
		Ext.get('btnGuardarSis').on('click', function() {
			guardarSistemasExternos();
		});	
		// evento click del botón dar de baja
		Ext.get('btnEliminarSis').on('click', function() {
			if (filaSeleccionada != null) {
				eliminarSisExt();
			}
		});	
		// evento click del botón Imprimir
		//Ext.get('btnImprimirSis').on('click', function() {
			//imprimirSistemasExternos();
		//});
	}
	
    return {
		init: function() {
			Ext.QuickTips.init();
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

			myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
			myMask.show();
			
			pintarGrid();
			
			if (Ext.isIE6){
				var ancho = document.body.offsetWidth - 38;
				grid.setWidth (ancho);
			}			
			controlEventos();
			iniciarCalendarios ();
		},
		mostrarSisExt: function (){
			mostrarSisExt();
		},
		comprobarCambiosEnPantalla: function (){
			return comprobarCambiosEnPantalla();
		}
	
	}
	
}();
function mostrarSisExt(){
	CGSISEXT.mostrarSisExt();
}
function comprobarCambiosEnPantalla(){
	return CGSISEXT.comprobarCambiosEnPantalla();
}

Ext.onReady(CGSISEXT.init, CGSISEXT, true);
</sec:authorize>