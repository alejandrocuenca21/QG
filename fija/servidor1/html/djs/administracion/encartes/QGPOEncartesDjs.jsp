<%@ page language="java" contentType="text/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
var CGLOBAL = function (){

    var filaSeleccionada;
    
    var filaSeleccionadaHist;

<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
	
	//Bandera que indica si se debe eliminar o hay que pedir confirmacion
	var eliminarConfirmado = false;
	
	//Bandera para saber si estamos modificando o creando nuevo al llamar al action.
	var modificacion;
	
	//Variable que guarda siempre los valores del intervalo para controlar que han cambiado o no y permitir el guardado
	var datosAntiguos = {
		fechaInicio:"",
		fechafin:""
	};
	
				
//BOTON ELIMINAR	
	var eliminar = function (){
		if(eliminarConfirmado){
			//Inicializamos pase lo que pase
			eliminarConfirmado = false;
 	
			var datosEncarteEliminar={    	 
					codLineaNegocio: filaSeleccionada.codLineaNegocio,
					codSegmento: filaSeleccionada.codSegmento,   		    	
					codDerecho: filaSeleccionada.codDerecho						
					
			}    	
			llamadaAjax('POEncartes.do','baja','encartesJSON',datosEncarteEliminar,resultadoEliminar,false);			
			mostrarFormulario(false);
		}else{
			var mensaje = 
			"&iquest;Desea eliminar el registro L&iacute;nea de negocio: "+filaSeleccionada.descLineaNegocio+
			", Segmento "+filaSeleccionada.codSegmento+" y Derecho "+filaSeleccionada.codDerecho+"?"
			mostrarConfirmacion(mensaje,funcionSiEliminar,funcionNoEliminar);
			eliminarConfirmado = false;
		}
	}
	
	/**
	 * Funcion si se pulsa si en eliminar
	 */ 
	var funcionSiEliminar = function(){
		 eliminarConfirmado = true;
		eliminar();
	 }
	/**
	 * Funcion si se pulsa no en eliminar
	 */
	var funcionNoEliminar = function(){
		eliminarConfirmado = false;
	}
	
	
	var resultadoEliminar = function(correcto,datos){
		deshabilitarBotonEliminar(true);
		buscar();
	}
		
//COMPROBAR DUPLICIDAD
	var comprobarDuplicidad = function(key){
        if(key != null){
                    //Si no hay elementos en la lista no se valida
                    if(grid != null && grid.getStore() != null && grid.getStore().getCount() > 0){
                         var i = 0;
                         var error = false;
                         //Recorremos el grid
                         while(!error && i < grid.getStore().getCount()){
                               var elemento = grid.getStore().getAt(i);
                               //Si la key es igual a la key del elemento del listado hay duplicidad
                               var keyActual = elemento.data.codLineaNegocio + elemento.data.codSegmento +  elemento.data.codDerecho;
                               if(keyActual == key){
                                           error = true;
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
 	
//GUARDAR	
	var guardar=function (){
		
		//validar datos.
		if(validarIntervalosFechasSinDia(Ext.get('txtFechaInicio').dom.value,Ext.get('txtFechaFin').dom.value)){
			//llamar ajax.
			var datosEncarteGuardar= {
					codLineaNegocio:Ext.get('selLineaNegocio').dom.options[Ext.get('selLineaNegocio').dom.selectedIndex].value,
					codSegmento:Ext.get('selCodigoSeg').dom.options[Ext.get('selCodigoSeg').dom.selectedIndex].innerHTML,
					codDerecho:Ext.get('selCodigoDer').dom.options[Ext.get('selCodigoDer').dom.selectedIndex].innerHTML,
					fecIniVigencia:Ext.get('txtFechaInicio').dom.value,
					fecFinVigencia:Ext.get('txtFechaFin').dom.value,
					modificacion: modificacion
			}
			var hayDuplicidad = false;
			if(!modificacion){							
                    //Creamos una "key" con la linea de negocio, el codigo de segmento y el codigo de derecho
                    hayDuplicidad = comprobarDuplicidad(datosEncarteGuardar.codLineaNegocio+datosEncarteGuardar.codSegmento+datosEncarteGuardar.codDerecho);;
            } 
			if(!hayDuplicidad){
				llamadaAjax('POEncartes.do','guardar','encartesJSON',datosEncarteGuardar,resultadoGuardar,false);
			}else{
				mostrarMensajeError("El elemento que intenta crear ya existe.");
			}
		}	
	}
	var resultadoGuardar = function(){
		mostrarFormulario(false);
		buscar();
	}
	
//DESHABILITAR ELIMINAR
	var deshabilitarBotonEliminar = function(estado){
		Ext.get('btnEliminar').dom.disabled = estado
		if(estado){
		
			Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';	
			Ext.get('btnEliminar').addClass('btnDis'); 

		}else{
			Ext.get('btnEliminar').removeClass('btnDis'); 
			Ext.get('btnEliminar').dom.src =  contexto + 'images/botones/QGbtEliminar.gif';
		}
	}
	
//PREPARAR FORMULARIO NUEVO
	var prepararFormularioNuevo = function (){
	 
	 var combos =Ext.select("div[@id='idFormEncartes'] select[@id^='sel']").elements;		
		for (var i=0;i < combos.length ;i++){
			combos[i].disabled = false;		
			combos[i].selectedIndex = 0;				
		}
		var textos =Ext.select("div[@id='idFormEncartes'] input[@id^='txt']").elements;		
		for(var i=0;i< textos.length;i++){
			textos[i].value="";
		}
		Ext.get('txtUsuarioAlta').dom.value=Ext.get('hiddenUsuarioConectado').dom.value;			
		
		filaSeleccionada = null;
		Ext.getCmp('gridEncartes').getSelectionModel().clearSelections(true);
		deshabilitarBotonEliminar(true);
		
		modificacion = false;							
		var arrayCombo = new Array(); 	
		arrayCombo.push({texto: "", valor: ""});				
		agregarValoresCombo('selCodigoDer', arrayCombo, true); 		
		Ext.get('selCodigoDer').dom.disabled = true;		
		controlBotonGuardar();
		mostrarFormulario(true);
	}
	
//CONTROL CAMPOS FORMULARIO
	var controlCamposFormulario = function(todos) {
		
		var contadorRellenos=0;													
			var textos =Ext.select("div[@id='idFormEncartes'] input[@id^='txtF']").elements;		
			for(var i=0;i < textos.length;i++){
				if(textos[i].value!=""){
					contadorRellenos++;
				}
			}
			var combos =Ext.select("div[@id='idFormEncartes'] select[@id^='sel']").elements;		
			for(var i=0;i < combos.length;i++){
				if(combos[i].value!=""){
					contadorRellenos++;
				}
			}
			
			if(todos){	
				//todos	
				return contadorRellenos == textos.length + combos.length;
			}else{
				//alguno
				return contadorRellenos != 0;
			}
	}
//CONTROL BOTON GUARDAR	
	var controlBotonGuardar = function(){

		var todosRellenos = controlCamposFormulario(true);
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
				var fechaFin = Ext.get('txtFechaFin').dom.value;
				
				if(datosAntiguos.fechaInicio != fechaIni || datosAntiguos.fechaFin != fechaFin){
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
	
//INICIAR CALENDARIOS
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

			
//ABRIR POP UP HISTORICO
		
		var obtenerHistorico = function(criterioBusqueda){			
			var criterioBusquedaHistorico = {
				codDerecho:criterioBusqueda.codDerecho,
				codLineaNegocio:criterioBusqueda.codLineaNegocio,
				codSegmento:criterioBusqueda.codSegmento
			
			};
			llamadaAjax('POEncartes.do','obtenerHistorico','encartesJSON',criterioBusquedaHistorico,rellenarResultadoBusquedaHistorico,true);		
		}		
		//funcion de respuesta de la llamada ajax en la busqueda de los datos de historico.
		var rellenarResultadoBusquedaHistorico = function(correcto, datos){
			if(correcto){
				Ext.get('divGridHistoricoPO').setVisibilityMode(Ext.Element.DISPLAY).show();
			}
			Ext.getCmp('gridHistorico').getStore().loadData (datos);	
		}
		
//BUSCAR
	var buscar = function (){
							
		llamadaAjax('POEncartes.do','buscar',null,null,rellenarResultadoBusqueda,true);		
	}
	//cuncion que se llama cuando la llamada ajax vuelve 
	var rellenarResultadoBusqueda = function (correcto,datos){
		if(correcto){
			Ext.get('divGrid').setVisibilityMode(Ext.Element.DISPLAY).show();
		}
		Ext.getCmp('gridEncartes').getStore().loadData (datos);	
		obtenerUsuarioSesion();
		rellenarComboSegmentos();
	}
	
//PREPARAR FORMULARIO MODIFICAR	
	var prepararFormularioModificar = function(){

		modificacion = true;
		
		var combos =Ext.select("select[@id^='sel']").elements;		
		for (var i=0;i < combos.length ;i++){
			combos[i].disabled = true;		
		}
			
		Ext.get('selLineaNegocio').dom.value= filaSeleccionada.codLineaNegocio;
		Ext.get('selCodigoSeg').dom.value=filaSeleccionada.descSegmento;
		Ext.get('txtDescripcionSeg').dom.value= filaSeleccionada.descSegmento;
					
		arrayCombo = new Array(); 	
		arrayCombo.push({texto:filaSeleccionada.codDerecho , valor: filaSeleccionada.descDerecho});		
		agregarValoresCombo('selCodigoDer', arrayCombo, true); 
		Ext.get('txtDescripcionDer').dom.value= filaSeleccionada.descDerecho;
		
		Ext.get('txtUsuarioAlta').dom.value = filaSeleccionada.usuarioAlta;
		Ext.get('txtUsuarioMod').dom.value= filaSeleccionada.usuarioMod;
		
		//editables
		Ext.get('txtFechaInicio').dom.value = filaSeleccionada.fecIniVigencia;
		Ext.get('txtFechaFin').dom.value = filaSeleccionada.fecFinVigencia;

		//Guarda los valores del intervalo
		datosAntiguos.fechaInicio = filaSeleccionada.fecIniVigencia;
		datosAntiguos.fechaFin = filaSeleccionada.fecFinVigencia;
		
		controlBotonGuardar();
		
		mostrarFormulario(true);
	}
	
//MOSTRAR FORMULARIO	
	var mostrarFormulario = function(mostrar){
		if(mostrar){
			Ext.get('idFormEncartes').setVisibilityMode(Ext.Element.DISPLAY).show();			
		}else{
			filaSeleccionada = null;
			Ext.getCmp('gridEncartes').getSelectionModel().clearSelections(true);
			
			Ext.get('idFormEncartes').setVisibilityMode(Ext.Element.DISPLAY).hide();
			return;
		}		
	}
	
//METODOS PARA RELLENAR LOS COMBOS	
	 	 
	var rellenarComboSegmentos = function(){		
		llamadaAjax('POEncartes.do','obtenerSegmentos',null,null,respuestaRellenarComboSegmentos);
	}
	var rellenarComboDerechos = function(lineaNegocio){
		llamadaAjax('POEncartes.do','obtenerDerechos','encartesJSON',lineaNegocio,respuestaRellenarComboDerechos);
	}
	var respuestaRellenarComboSegmentos = function(correcto,datos){
		if(correcto){
			var arrayCombo = new Array(); 			
			arrayCombo.push({texto: "", valor: ""});
			Ext.each(datos,function(dato){
				arrayCombo.push({texto: dato.codigo, valor: dato.descripcion});				
			});
			agregarValoresCombo('selCodigoSeg', arrayCombo, true); 
			comboSegmentosYaCargado=true;
		}
	}
	var respuestaRellenarComboDerechos = function(correcto,datos){		
		if(correcto){
			var arrayCombo = new Array(); 	
			arrayCombo.push({texto: "", valor: ""});
			Ext.each(datos,function(dato){
				arrayCombo.push({texto: dato.codDerecho, valor: dato.descDerecho});				
			}); 
			agregarValoresCombo('selCodigoDer', arrayCombo, true); 
		
			Ext.get('selCodigoDer').dom.disabled=false;
								
		}
	}

//BOTON CANCELAR
	var cancelar = function(){
		if(controlCamposFormulario(false) && !modificacion){
			//mostrar mensaje
			mostrarConfirmacion('Se van a perder los datos introducidos,&iquest;Desea continuar?',cerrarFormulario);
		}	else{
			cerrarFormulario();
		}			
	}
	var cerrarFormulario = function(){
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
		// deshabilitamos el boton  eliminar					
		deshabilitarBotonEliminar(true);
		</sec:authorize>
		mostrarFormulario(false);
	}
	
//BOTON IMPRIMIR
	var imprimirEncartes = function(){
		
		if (Ext.isIE){
			myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
			myMask.show();
		}
		
		document.getElementById('ifExportar').src = contexto + 'POEncartes.do?exportarPDF=';
		
		if (Ext.isIE)
			document.getElementById('ifExportar').onreadystatechange = function(){myMask.hide();}; 		
	}
	
//PINTAR GRID	
	var pintarGrid = function (){
		
		// create the data store
	    var store = new Ext.data.Store({
	    	reader: new Ext.data.ObjectReader({}, [
				{name: 'descLineaNegocio', type: 'string'},
				{name: 'codSegmento', type: 'string'},
				{name: 'codDerecho', type: 'string'},
				{name: 'fecIniVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'fecFinVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'usuarioAlta', type: 'string'},
				{name: 'fecAlta', type: 'string',sortType:convertirEnFecha},
				{name: 'usuarioMod', type: 'string'},
				{name: 'fecMod', type: 'string',sortType:convertirEnFecha},
				{name: 'ocultarHistorico'},
				{name: 'codLineaNegocio', type: 'string'},
				{name: 'descSegmento', type: 'string'},
				{name: 'descDerecho', type: 'string'}
			])
	    });
	    
	     var actionsHistorico = new Ext.ux.grid.RowActions({
			header: 'Hist&oacute;rico',
			autoWidth: false,
			width:60,
			actions: [{
				iconCls: 'iconoHistorico centrarIco',
				align:'right',
				hideIndex:'ocultarHistorico',
				callback: function(g, record, action, rowIndex, colIndex) {
					abrirPopUpHistorico(record.data);
				}
			}]
		});
		
	     function addTooltip(value,metadata, record, rowIndex, colIndex, store){

	    	//En record viene el elemento formado del grid
	 		var valorTooltip = "";
	 		//Dependiendo de la columna que estemos renderizando se metera un valor de descripcion u otro
	 		//Columna de segmento
	 		if(colIndex == 1){
	 			valorTooltip = record.data.descSegmento;
	 		//Columna de derecho
	 		}else if(colIndex == 2 ){
	 			valorTooltip = record.data.descDerecho;
	 		}
	 		//Accedemos al tooltip
	 		if(valorTooltip != null && valorTooltip != ''){
	 			metadata.attr = 'ext:qtip="' + valorTooltip +'"';
	 		}
	 		//Debe devolver el valor para rellenar la columna
	 		return value;
	     }
	     
	  	var columnModel = new Ext.grid.ColumnModel([
		    {header: 'L&iacute;nea Negocio',sortable: true, dataIndex: 'descLineaNegocio'},		    
            {header: 'Segmento', sortable: true, dataIndex: 'codSegmento', renderer:addTooltip},
            {header: 'Derecho', sortable: true,  dataIndex: 'codDerecho', renderer:addTooltip},
            {header: 'F. inicio vigencia', sortable: true, align:'center', dataIndex: 'fecIniVigencia'},
            {header: 'F. fin vigencia', sortable: true, align:'center', dataIndex: 'fecFinVigencia'},
          	{header: 'Usuario de alta',sortable: true, align:'center', dataIndex: 'usuarioAlta'},
          	{header: 'F. Alta', sortable: true,width:135, align:'center', dataIndex: 'fecAlta'},
          	{header: 'Us. Modificaci&oacute;n',sortable: true, align:'center', dataIndex: 'usuarioMod'},
          	{header: 'F. Modificaci&oacute;n', sortable: true, align:'center', dataIndex: 'fecMod'},
          	actionsHistorico
		]);
	  	
	  	var cargarDatosFila = function(fila){
			Ext.each(Ext.query('.marcarFila', grid.id), function(filaMarcada) {
				Ext.fly(filaMarcada).removeClass('marcarFila');
			});
			Ext.fly(grid.getView().getRow(fila)).addClass('marcarFila');

			filaSeleccionada = grid.getStore().data.items[fila].data;
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
			// habilitamos el boton eliminar
			deshabilitarBotonEliminar(false);
			prepararFormularioModificar();
			</sec:authorize>
		}
		
	  		
	    // create the Grid
	    grid = new Ext.grid.EditorPasteCopyGridPanel({
	    	id:'gridEncartes',
	        store: store,
			renderTo: 'divGrid',
			cls:'gridCG',
			selectionModel: '',
			funcionRollBack: cargarDatosFila,
			cm: columnModel,
	        stripeRows: true,
	        plugins: [actionsHistorico],
	        viewConfig: {
				forceFit:true
				},
	        height: 145
	    });
	}
	
	var abrirPopUpHistorico = function (datos){
		var win = Ext.getCmp('winHistorico');
				 	
		obtenerHistorico(datos);
								
		Ext.get('txtNegocio').dom.value =datos.descLineaNegocio;
		Ext.get('txtCodSegmento').dom.value =datos.codSegmento;
		Ext.get('txtDesSegmento').dom.value =datos.descSegmento;
		Ext.get('txtCodConsent').dom.value =datos.codDerecho;
		Ext.get('txtDesConsent').dom.value =datos.descDerecho;
		
		if(!win){
			win = new Ext.Window({
				id:'winHistorico',
				contentEl:'popUpHistorico',
				title:'Hist&oacute;rico P.O. para Encartes',
				width:780,
				height:360,
				closeAction:'hide',
				closable: true,
				animateTarget:'gridEncartes',
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
	var pintarGridHistorico = function (){
	
	   // create the data store
	    var storeHistorico = new Ext.data.Store({
	    	reader: new Ext.data.ObjectReader({}, [
				{name: 'fecIniVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'fecFinVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'usuarioAlta', type: 'string'},
				{name: 'fecAlta', type: 'string',sortType:convertirEnFecha},
				{name: 'usuarioMod', type: 'string'},
				{name: 'fecMod', type: 'string',sortType:convertirEnFecha}
			])
	    });
	    
	  	var columnModelHistorico = new Ext.grid.ColumnModel([ 
            {header: 'F. inicio vigencia', sortable: true,width: 120, align:'center', dataIndex: 'fecIniVigencia'},
            {header: 'F. fin vigencia', sortable: true,width: 110, align:'center', dataIndex: 'fecFinVigencia'},
          	{header: 'Usuario de alta',sortable: true,id:'colUsAlta', align:'center', dataIndex: 'usuarioAlta'},
          	{header: 'F. Alta', sortable: true, align:'center',width: 110, dataIndex: 'fecAlta'},
          	{header: 'Us. Modificaci&oacute;n',sortable: true,width: 120, align:'center', dataIndex: 'usuarioMod'},
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
			renderTo: 'divGridHistoricoPO',
			autoExpandColumn: 'colUsAlta',
			cls:'gridCG',
			width:740,
			cm: columnModelHistorico,
	        stripeRows: true,	  
	        height: 200
	    });
	}

	// Controlamos los eventos sobre los botones
	var controlEventos = function() {
		
		window.onresize = function() {
			refrescarGrid.defer(20);
	 	}
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
		//evento click del boton Eliminar
		Ext.get('btnEliminar').on('click', function() {
			eliminar();
		});
		//evento click del boton Eliminar
		Ext.get('btnNuevo').on('click', function() {
			prepararFormularioNuevo();
		});
		Ext.get('txtFechaInicio').on('change', function(){			 			
			controlBotonGuardar();
		});
		
		Ext.get('txtFechaFin').on('change', function(){			 
		 
			controlBotonGuardar();
		});
		Ext.get('selCodigoSeg').on('change', function(){			 
			Ext.get('txtDescripcionSeg').dom.value = Ext.get('selCodigoSeg').dom.options[Ext.get('selCodigoSeg').dom.selectedIndex].value;
			controlBotonGuardar();
		});
		//carga el campo de texto con la descripcion del derecho seleccionado
		Ext.get('selCodigoDer').on('change', function(){			 
			Ext.get('txtDescripcionDer').dom.value = Ext.get('selCodigoDer').dom.options[Ext.get('selCodigoDer').dom.selectedIndex].value;
			controlBotonGuardar();
		});
		
		//carga el combo de derechos segun la linea de negocio
		Ext.get('selLineaNegocio').on('change', function(){
			var lineaNegocio = Ext.get('selLineaNegocio').dom.options[Ext.get('selLineaNegocio').dom.selectedIndex].value;
			if(lineaNegocio != null && lineaNegocio != ""){
				rellenarComboDerechos(lineaNegocio);
			}else{
				var arrayCombo = new Array(); 	
				arrayCombo.push({texto: "", valor: ""});				
				agregarValoresCombo('selCodigoDer', arrayCombo, true); 
				Ext.get('selCodigoDer').dom.disabled=true;
				Ext.get('txtDescripcionDer').dom.value="";
				Ext.get('txtDescripcionDer').addClass('dis');  
				Ext.get('txtDescripcionDer').readonly=true;
				
			}
			
			controlBotonGuardar();
			//Limpiamos la descripcion de derechos
			Ext.get('txtDescripcionDer').dom.value = '';
		});
		
		//evento click del boton Guardar
		Ext.get('btnGuardar').on('click', function() {
			guardar();
		});
		//evento click del boton Cancelar
		Ext.get('btnCancelar').on('click', function() {
			cancelar();
		});
		</sec:authorize>
		
		//Ext.get('btnImprimirEnc').on('click', function() {
			//imprimirEncartes();
		//});
		
		Ext.get('btnVolver').on('click', function() {
			Ext.getCmp('winHistorico').hide();
		});		
	}
	
	//carga el usuario conectado en el campo de la pantalla de alta.
	var obtenerUsuarioSesion = function(){
		llamadaAjax('POEncartes.do ','obtenerUsuario',null,null,volcarUsuario,false);		
	}//funcion que se llama cuando la llamada ajax vuelve 
	var volcarUsuario = function (correcto,datos){
		if(correcto){
			Ext.get('hiddenUsuarioConectado').dom.value = datos[0];
		}
	}
	
	return {
		init: function (){
		 Ext.QuickTips.init();
			pintarGrid();
			pintarGridHistorico();
			controlEventos();
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
			deshabilitarBotonEliminar(true);
			iniciarCalendarios();
		</sec:authorize> 

			buscar();			
			
		}
	}
}();

Ext.onReady (CGLOBAL.init, CGLOBAL, true);
</sec:authorize>