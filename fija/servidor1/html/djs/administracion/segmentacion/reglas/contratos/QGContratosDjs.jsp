<%@ page language="java" contentType="text/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
var CGLOBAL = function (){

	var record_antiguo;
    var filaSeleccionada;
   	var hay_seleccion=true;
  	 var suspender_eventos=false;
	//Bandera que indica si se debe eliminar o hay que pedir confirmacion
	var eliminarConfirmado = false;
	
	//Bandera para saber si estamos modificando o creando nuevo al llamar al action.
	var modificacion;
	
	//criterios de la ultima busqueda realizada.
	ultimaBusqueda = new Ext.util.MixedCollection();
	

	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">				
	//Se abre el formulario para incluir un nuevo registro
	var prepararFormularioNuevo = function (){
	 	
		Ext.get('txtUsuarioAlta').dom.value=Ext.get('hiddenUsuarioConectado').dom.value;			
		
		filaSeleccionada = null;
		Ext.getCmp('gridContratos').getSelectionModel().clearSelections(true);
		
		Ext.get('txtCodigo').dom.value = '';
		
		//Si hay un registro seleccionado lo deseleccionamos
		if (filaSeleccionada != null) {
			hay_seleccion=true;
			
			filaSeleccionada = null;
			Ext.getCmp('gridContratos').getSelectionModel().clearSelections(true);
		}
		
		Ext.get('txtFechaInicio').dom.value = '';
		Ext.get('divTxtFechaInicio').removeClass('dis');
		Ext.get('txtFechaInicio').dom.readOnly = false;
  		Ext.get('btnCalendarFechaInicio').dom.disabled = false;
  		
  		Ext.get('txtFechaFin').dom.value = '31.12.2500';
		Ext.get('txtFechaFin').dom.readOnly = true;
		Ext.get('txtFechaFin').addClass('dis');
	 	Ext.get('divTxtFechaFin').addClass('dis');
	 	Ext.get('btnCalendarFechaFin').dom.disabled = true;
		
		Ext.get('txtDescripcion').dom.value = '';
		
		Ext.get('txtUsuarioAlta').dom.value=Ext.get('hiddenUsuarioConectado').dom.value;
		Ext.get('txtUsuarioAlta').dom.readOnly = true;
		Ext.get('txtUsuarioAlta').addClass('dis');
		
		Ext.get('txtUsuarioMod').dom.value='';
		Ext.get('txtUsuarioMod').dom.readOnly = true;
		Ext.get('txtUsuarioMod').addClass('dis');
	
		modificacion = false;							
	
		controlBotonGuardar();
		mostrarFormulario(true);
	}
	
	
	//Funcion que comprueba que todos los campos estan rellenos
	var comprobarCamposAltaTodos = function() {
		var datosMetidos = Ext.get('txtCodigo').dom.value != ''
				&& Ext.get('txtDescripcion').dom.value != ''
				&& Ext.get('txtFechaInicio').dom.value != '';

		return datosMetidos;
	}
	
	//Funcion que compueba si se activa/desactiva el boton guardar
	var controlBotonGuardar = function(){

		var todosRellenos = comprobarCamposAltaTodos();
		var habilitar = false;
		if(modificacion == false){
			habilitar = todosRellenos;
		}else{
			//Si estan todos rellenos vemos si ademas han cambiado
			if(todosRellenos){
				//Si es una modificacion hay que ver que ademas hayan cambiado los valores del intervalo
				var cambiado = false;
				//Recuperamos los valores de las fechas
				//editables
				var fechaIni = Ext.get('txtFechaInicio').dom.value;
				var cod = Ext.get('txtCodigo').dom.value;
				var des = Ext.get('txtDescripcion').dom.value;
				
				if(record_antiguo.fxIniVigencia != fechaIni ||
				   record_antiguo.codigo != cod ||  record_antiguo.descripcion != des){
					cambiado = true;
				}
				
				if(todosRellenos && cambiado){
					habilitar = true;
				}else{
					habilitar = false;
				}
			}
		}
		
		//Segun los criterios de comparacion hacemos una cosa u otra
		if(habilitar){			
				Ext.get('btnGuardar').removeClass('btnDis'); 
				Ext.get('btnGuardar').dom.src =  contexto + 'images/botones/QGbtGuardar.gif';	
				Ext.get('btnGuardar').dom.disabled = false;
			}else{
				Ext.get('btnGuardar').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';	
				Ext.get('btnGuardar').addClass('btnDis'); 
				Ext.get('btnGuardar').dom.disabled = true; 
			}
	}

	//Inicio de calendarios
	var iniciarCalendarios = function() {
		//obtenemos la fecha de hoy

		Calendar.setup( {
			inputField : "txtFechaInicio",
			dateFormat : "%d.%m.%Y",
			trigger : "btnCalendarFechaInicio",
			bottomBar : false,
			align : 'Br///B/r',
			onSelect : function() {
				this.hide();
				controlBotonGuardar();
			}
		});
		Calendar.setup( {
			inputField : "txtFechaFin",
			dateFormat : "%d.%m.%Y",
			trigger : "btnCalendarFechaFin",
			bottomBar : false,
			align : 'Br///B/r',
			onSelect : function() {
				this.hide();
				
				controlBotonGuardar();

			}
		});
	}
	</sec:authorize>

	//cuncion que se llama cuando la llamada ajax vuelve 
	var callBackBusqueda = function (correcto,datos){
		if(correcto){
			Ext.get('divGrid').setVisibilityMode(Ext.Element.DISPLAY).show();
			ultimaBusqueda.add('hayResultados',true);
			
			Ext.getCmp('gridContratos').getStore().loadData (datos);
			//Obtenemos el usuario	
			obtenerUsuario();
			//Inicializamos todos los elementos
			iniciarValores(); 
   		}
   		else{
   			ultimaBusqueda.add('hayResultados',false);
		}	
	}

	var buscar = function (){
		var inActuacion = "C";					
		llamadaAjax('Contratos.do','buscar',inActuacion,null,callBackBusqueda,true);		
	}
	
	//Funcion que carga los datos para modificarlos o dar de alta
	var cargarDatosModificacion = function (cancelar){	 
		<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AP">
		controlBotonGuardar();
		</sec:authorize>
		var descripcion = Ext.get('txtDescripcion').dom.value;
		var codigo = Ext.get('txtCodigo').dom.value;
		var fechaInicio = Ext.get('txtFechaInicio').dom.value;
		var fechaFin = Ext.get('txtFechaFin').dom.value;
		
		if (!modificacion){
			if (descripcion.trim().length>0 || codigo.trim().length>0 || 
				fechaInicio.trim().length>0){
				Ext.Msg.show({
				   title:'Los datos se perder&aacute;n',
				   msg: '<span>Se van a perder los datos introducidos, &iquest;Desea continuar?</span>',
				   buttons: Ext.Msg.YESNO,
				   icon: Ext.MessageBox.WARNING,
				   fn:function(respuesta){
				   		if (respuesta == 'yes'){
				   			modificacion=true;
				   			if(cancelar){
							   	cancelarContrato();
							}else{
					  			cargarValoresFomularioMod();
					  		}
				   		}else{
				   			hay_seleccion=true;
				   			
				   			filaSeleccionada = null;
							Ext.getCmp('gridContratos').getSelectionModel().clearSelections(true);
							
				   			Ext.get('txtCodigo').dom.focus();
				   		}
				   }
				});
			}else {
				modificacion=true;
		  		if(cancelar){
				   	cancelarContrato();
				}else{
		  			cargarValoresFomularioMod();
		  		}
			}
	  	}else{
	  		//MODO MODIFICACION
			if (descripcion.trim().length>0 || codigo.trim().length>0 || 
				fechaInicio.trim().length>0){
				if (record_antiguo != null &&
					record_antiguo.codigo == Ext.get('txtCodigo').dom.value &&
					record_antiguo.descripcion == Ext.get('txtDescripcion').dom.value &&
					record_antiguo.fxIniVigencia == fechaInicio){
					
					if(cancelar){
					   	cancelarContrato();
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
								   	cancelarContrato();
								}else{
						  			cargarValoresFomularioMod();
						  		}
					   		}else{
					   			hay_seleccion=false;
					   			suspender_eventos=true;
					   			
					   			var index = grid.getStore().indexOf (record_antiguo);
					   			filaSeleccionada = record_antiguo;
					   			<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AP">
					   			Ext.get('txtCodigo').dom.focus();
					   			</sec:authorize>
					   		}
					   }
					});
				}
			}else{
				Ext.get('idFormContratos').setVisibilityMode(Ext.Element.DISPLAY).show();
				if(cancelar){
				   	cancelarContrato();
				}else{
		  			cargarValoresFomularioMod();
		  		}
				
			}
	  	}					
	}
	
	//Funcion para cargar los valores en el formulario	
	var cargarValoresFomularioMod = function (){
		
		//Inicializamos los valores
		Ext.get('txtCodigo').dom.value = filaSeleccionada.codigo;
		Ext.get('txtDescripcion').dom.value = filaSeleccionada.descripcion;
		
		var fechaInicio = filaSeleccionada.fxIniVigencia;
		Ext.get('txtFechaInicio').dom.value = fechaInicio;
		<sec:authorize ifAnyGranted="ROLE_AU,ROLE_AB,ROLE_AC,ROLE_AD,ROLE_CO,ROLE_AS,ROLE_AM">
		Ext.get('txtFechaInicio').dom.readOnly = true;
		Ext.get('txtFechaInicio').addClass('dis');
	 	Ext.get('divTxtFechaInicio').addClass('dis');
	 	Ext.get('btnCalendarFechaInicio').dom.disabled = true;
		</sec:authorize>
				
		var fechaFin = filaSeleccionada.fxFinVigencia;
		Ext.get('txtFechaFin').dom.value = fechaFin;
		<sec:authorize ifAnyGranted="ROLE_AU,ROLE_AB,ROLE_AC,ROLE_AD,ROLE_CO,ROLE_AS,ROLE_AM">
		Ext.get('txtFechaFin').dom.readOnly = true;
		Ext.get('txtFechaFin').addClass('dis');
	 	Ext.get('divTxtFechaFin').addClass('dis');
	 	Ext.get('btnCalendarFechaFin').dom.disabled = true;
	 	</sec:authorize>
		
		Ext.get('txtUsuarioAlta').dom.value = filaSeleccionada.usuarioAlta;
		Ext.get('txtUsuarioAlta').dom.readOnly = true;
	 	Ext.get('txtUsuarioAlta').addClass('dis');
	 		
	 	Ext.get('txtUsuarioMod').dom.value = Ext.get('hiddenUsuarioConectado').dom.value;
		Ext.get('txtUsuarioMod').dom.readOnly = true;
	 	Ext.get('txtUsuarioMod').addClass('dis');
	 	
		record_antiguo = filaSeleccionada;
		<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AP">
		Ext.get('btnGuardar').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
		Ext.get('btnGuardar').addClass('btnDis');
		Ext.get('btnGuardar').dom.disabled = true;
		</sec:authorize>
		
		mostrarFormulario(true);
	}
	
	//Muestra el formurario en la parte inferior de la pantalla
	var mostrarFormulario = function(mostrar){
		if(mostrar){
			Ext.get('idFormContratos').setVisibilityMode(Ext.Element.DISPLAY).show();			
		}else{
			filaSeleccionada = null;
			Ext.getCmp('gridContratos').getSelectionModel().clearSelections(true);
			
			Ext.get('idFormContratos').setVisibilityMode(Ext.Element.DISPLAY).hide();
			return;
		}		
	}

	//Funcion que cancela la modificación o el alta de un Contrato
	var cancelarContrato = function() {
		Ext.get('txtCodigo').dom.value='';
		Ext.get('txtDescripcion').dom.value='';
		Ext.get('txtFechaInicio').dom.value='';
		
		
		filaSeleccionada = null;
		Ext.getCmp('gridContratos').getSelectionModel().clearSelections(true);
		record_antiguo=null;
		modificacion=true;
					
		//Ocultamos el formulario
		Ext.get('idFormContratos').setVisibilityMode(Ext.Element.DISPLAY).hide();
	}
	
	//Pinta el grid en pantalla
	var pintarGrid = function (){
		
		// create the data store
	    var store = new Ext.data.Store({
	    	reader: new Ext.data.ObjectReader({}, [
				{name: 'codigo', type: 'string'},
				{name: 'descripcion', type: 'string'},
				{name: 'fxIniVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'fxFinVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'usuarioAlta', type: 'string'},		
				{name: 'usuarioMod', type: 'string'}
			])
	    });
	    	
	     function addTooltip(value,metadata, record, rowIndex, colIndex, store){

	    	
	     }
	     
	  	var columnModel = new Ext.grid.ColumnModel([
		    {header: 'C&oacute;digo',sortable: true, dataIndex: 'codigo'},		    
            {header: 'Descripci&oacute;n', width:335,sortable: true,id:'colDes', dataIndex: 'descripcion'},
            {header: 'F. inicio vigencia', sortable: true, align:'center', dataIndex: 'fxIniVigencia'},
            {header: 'F. fin vigencia', sortable: true, align:'center', dataIndex: 'fxFinVigencia'},
          	{header: 'Usuario de alta',sortable: true, align:'center', dataIndex: 'usuarioAlta'},
          	{header: 'Us. Modificaci&oacute;n',sortable: true, align:'center', dataIndex: 'usuarioMod'}
		]);
	  	
	  	var cargarDatosFila = function(fila){
			Ext.each(Ext.query('.marcarFila', grid.id), function(filaMarcada) {
				Ext.fly(filaMarcada).removeClass('marcarFila');
			});
			Ext.fly(grid.getView().getRow(fila)).addClass('marcarFila');

			filaSeleccionada = grid.getStore().data.items[fila].data;
			
			<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AP">
			modificacion = true;
			cargarDatosModificacion(false);
			</sec:authorize>
			hay_seleccion = false;
			
		}
		
	  		
	    // create the Grid
	    grid = new Ext.grid.EditorPasteCopyGridPanel({
	    	id:'gridContratos',
	        store: store,
			renderTo: 'divGrid',
			autoExpandColumn: 'colDes',
			cls:'gridCG',
			selectionModel: '',
			funcionRollBack: cargarDatosFila,
			cm: columnModel,
	        stripeRows: true,
	        viewConfig: {
				forceFit:true
				},
	        height: 145
	    });
	}
	

	var refrescarGrid = function() {
		if (Ext.isIE6){
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).hide();
			var ancho = document.body.offsetWidth - 38;
			
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).show();
			grid.setWidth (ancho);
		}else{
			grid.getView().refresh(true);
		}
	}

    //Funcion devuelta por obtenerUsuario
   	var callBackObtenerUsu = function(correcto,datos){
		if(correcto){
   			Ext.get('hiddenUsuarioConectado').dom.value = datos;
		}
	}
    
    //Funcion que carga el listado de Autorizaciones
    var obtenerUsuario = function (){
    
   		var datosUsuario={  
		}
		llamadaAjax('Contratos.do','obtenerUsuario','usuarioLogado',datosUsuario,callBackObtenerUsu,false);	
    }	
    
    //Funcion que imprime los datos de la pantalla en pdf
	var imprimir = function() {

		if(ultimaBusqueda.get('hayResultados')){
			if (Ext.isIE){
				myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
				myMask.show();
			}
			document.getElementById('ifExportar').src = contexto + 'Contratos.do?exportarPDF=&inActuacion='+"C";
			
			if (Ext.isIE)
				document.getElementById('ifExportar').onreadystatechange = function(){myMask.hide();}; 
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

	// Controlamos los eventos sobre los botones
	var controlEventos = function() {
		
		window.onresize = function() {
			refrescarGrid.defer(20);
	 	}
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		//evento click del boton Eliminar
		Ext.get('btnNuevo').on('click', function() {
			prepararFormularioNuevo();
		});
		
		Ext.get('txtFechaInicio').on('change', function(){			 			
			controlBotonGuardar();
		});
		
		Ext.get('txtCodigo').on('change', function(){			 
			controlBotonGuardar();
		});
			
		Ext.get('txtDescripcion').on('change', function(){			 
			controlBotonGuardar();
		});
		
		//evento click del boton Guardar
		Ext.get('btnGuardar').on('click', function() {

		});
		//evento click del boton Cancelar
		Ext.get('btnCancelar').on('click', function() {
			cargarDatosModificacion(true);
		});
		</sec:authorize>
		
		//evento click del botón Imprimir
		//Ext.get('btnImprimir').on('click', function() {
			//imprimir();
		//});
		
		Ext.get('btnVolver').on('click', function() {
			verReglas();
		});		
	}
	
	//Volver Reglas
	var verReglas = function (){		
		Ext.get('idMethod').set({
			name:'irReglas'
		});
		
		Ext.get('hiddenEntradaV').dom.value = Ext.get('hiddenEntrada').dom.value;
		Ext.get('hiddenOrigenV').dom.value = Ext.get('hiddenOrigen').dom.value;
		
		irReglas();
	} 
	
	
	var iniciarValores = function(){
		Ext.get('txtUsuarioAlta').dom.value=Ext.get('hiddenUsuarioConectado').dom.value;			
		
		filaSeleccionada = null;
		Ext.getCmp('gridContratos').getSelectionModel().clearSelections(true);
		
		Ext.get('txtCodigo').dom.value = '';
		
		//Si hay un registro seleccionado lo deseleccionamos
		if (filaSeleccionada != null) {
			hay_seleccion=true;
			
			filaSeleccionada = null;
			Ext.getCmp('gridContratos').getSelectionModel().clearSelections(true);
		}
		
		Ext.get('txtFechaInicio').dom.value = '';
		<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AP">
		Ext.get('divTxtFechaInicio').removeClass('dis');
		Ext.get('txtFechaInicio').dom.readOnly = false;
  		Ext.get('btnCalendarFechaInicio').dom.disabled = false;
  		</sec:authorize>
  		
  		Ext.get('txtFechaFin').dom.value = '31.12.2500';
  		<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AP">
		Ext.get('txtFechaFin').dom.readOnly = true;
		Ext.get('txtFechaFin').addClass('dis');
	 	Ext.get('divTxtFechaFin').addClass('dis');
	 	Ext.get('btnCalendarFechaFin').dom.disabled = true;
		</sec:authorize>
		Ext.get('txtDescripcion').dom.value = '';
		
		Ext.get('txtUsuarioAlta').dom.value=Ext.get('hiddenUsuarioConectado').dom.value;
		Ext.get('txtUsuarioAlta').dom.readOnly = true;
		Ext.get('txtUsuarioAlta').addClass('dis');
		
		Ext.get('txtUsuarioMod').dom.value='';
		Ext.get('txtUsuarioMod').dom.readOnly = true;
		Ext.get('txtUsuarioMod').addClass('dis');
	
		modificacion = false;
		record_antiguo = null;
    	filaSeleccionada = null;
   		hay_seleccion=true;
  	 	suspender_eventos=false;
	}
	
	return {
		init: function (){
		 Ext.QuickTips.init();
		 	if (Ext.get('hiddenOrigen').dom.value == "llamadaReglas"){
		 		Ext.get('divVolver').setVisibilityMode(Ext.Element.DISPLAY).show();
		 	}
		 	else{
		 		Ext.get('divVolver').setVisibilityMode(Ext.Element.DISPLAY).hide();
		 	}
		 
		 	pintarGrid();
			controlEventos();
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
			iniciarCalendarios();
			</sec:authorize>
			buscar();		
		}
	}
}();

Ext.onReady (CGLOBAL.init, CGLOBAL, true);
</sec:authorize>