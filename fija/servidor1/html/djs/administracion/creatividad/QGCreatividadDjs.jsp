<%@ page language="java" contentType="text/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
var CGLOBAL = function (){

	var filaSeleccionada;

<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AM">
    //Bandera que indica si se debe eliminar o hay que pedir confirmacion
	var eliminarConfirmado = false;
	
	//Bandera que indica si estamos en un alta o no
	var estadoMod = false;
	
	//Variable que guarda siempre los valores del intervalo para controlar que han cambiado o no y permitir el guardado
	var datosAntiguos = {
		fechaInicio:"",
		fechafin:"",
		creatividad:""
	};

	var callbackGuardar = function(correcto,datos){
		
		// deshabilitamos el boton  eliminar, cerramos el formulario y buscamos
		mostrarFormulario(false);
		deshabilitarBotonEliminar(true);
		buscar();
	}
	
	/**
	 * Comprueba que la key (linea negocio + cod segmento + cod derecho) no este repetida en la lista
	 */
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
	
	/*
	 * Guarda la creatividad
	 */
	var guardarCreatividad = function(){
		
		//Formamos el objeto para guardar
		var datosCreatividad={    	 
				codLineaNegocio:Ext.get('selLineaNegocio').dom.options[Ext.get('selLineaNegocio').dom.selectedIndex].value,
				codSegmento:Ext.get('selCodigoSeg').dom.options[Ext.get('selCodigoSeg').dom.selectedIndex].innerHTML,
				codDerecho:Ext.get('selCodigoDer').dom.options[Ext.get('selCodigoDer').dom.selectedIndex].innerHTML,
				fecIniVigencia:Ext.get('txtFechaInicio').dom.value,
				fecFinVigencia:Ext.get('txtFechaFin').dom.value,
				usuarioAlta:Ext.get('txtUsuarioAlta').dom.value,
				usuarioMod:"",
				descCreatividad:HTML_Texto(Ext.get('txtCreatividad').dom.value),
				fecAlta:"",
				fecMod:"",
				modificacion:estadoMod
		} 
		if(validarIntervalosFechasSinDia(datosCreatividad.fecIniVigencia,datosCreatividad.fecFinVigencia,null)){
			//Si es un alta, comprobamos duplicidad con los elementos de la lista
			var hayDuplicidad = false;
			if(!datosCreatividad.modificacion){
				//Creamos una "key" con la linea de negocio, el codigo de segmento y el codigo de derecho
				hayDuplicidad = comprobarDuplicidad(datosCreatividad.codLineaNegocio+datosCreatividad.codSegmento+datosCreatividad.codDerecho);
			}
			
			//Si todo ha ido bien lanzamos la llamada
			if(!hayDuplicidad){
				llamadaAjax('Creatividad.do','guardar','creatividadJSON',datosCreatividad,callbackGuardar,false);
			}else{
				mostrarMensajeError("El registro que intenta crear ya existe.");
			}
		}
	}
	/**
	 * Funcion callback para el boton eliminar.
	 */
	var callbackEliminar = function(correcto,datos){
		
		// deshabilitamos el boton  eliminar, cerramos el formulario y buscamos
		mostrarFormulario(false);
		deshabilitarBotonEliminar(true);
		buscar();
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
	/**
	 * Evento del boton eliminar
	 */
	var eliminar = function (){

		if(eliminarConfirmado){
			//Inicializamos pase lo que pase
			eliminarConfirmado = false;
			var datosCreatividadEliminar={    	 
					descLineaNegocio: filaSeleccionada.descLineaNegocio,
					codLineaNegocio: filaSeleccionada.codLineaNegocio,
					codSegmento: filaSeleccionada.codSegmento,   		    	
					codDerecho: filaSeleccionada.codDerecho,    	
					descCreatividad: filaSeleccionada.descCreatividad,
					fecIniVigencia: filaSeleccionada.fecIniVigencia,
					fecFinVigencia: filaSeleccionada.fecFinVigencia
					}	    	

			llamadaAjax('Creatividad.do','baja','creatividadJSON',datosCreatividadEliminar,callbackEliminar,false);		
			 
		}else{
			var mensaje = 
			"&iquest;Desea eliminar el registro L&iacute;nea de negocio: "+filaSeleccionada.descLineaNegocio+
			", Segmento "+filaSeleccionada.codSegmento+" y Derecho "+filaSeleccionada.codDerecho+"?"
			mostrarConfirmacion(mensaje,funcionSiEliminar,funcionNoEliminar);
			eliminarConfirmado = false;
		}
	}
    /*
	 * Funcion que deshabilita el boton eliminar en caso que este habilitado y viceversa 
	 */
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
	
	 /**
	  * Crea y prepara el formulario para crear la creatividad
	  */
	 var crearCreatividad = function(){
		
		// Linea negocio
		Ext.get('selLineaNegocio').dom.value = "";
		Ext.get('selLineaNegocio').dom.disabled = false;
		Ext.get('selLineaNegocio').removeClass('dis');		
		// Creatividad
		Ext.get('txtCreatividad').dom.value = "";
		// Fecha de inicio
		Ext.get('txtFechaInicio').dom.value = "";
		//Fecha de fin
		Ext.get('txtFechaFin').dom.value = "";
		//Usuario alta
		Ext.get('txtUsuarioAlta').dom.value = Ext.get('hiddenUsuarioAlta').dom.value;
		//Usuario modificacion
		Ext.get('txtUsuarioMod').dom.value = "";

		Ext.get('selCodigoSeg').dom.value = "";
		//se deshabilita el codigo de segmento
		Ext.get('selCodigoSeg').dom.disabled = false;
		Ext.get('selCodigoSeg').removeClass('dis');
		
		var arrayCombo = new Array();
		arrayCombo.push({texto: "", valor: ""});
		agregarValoresCombo('selCodigoDer', arrayCombo, true);

		//Descripcion de segmento
		Ext.get('txtDescripcionSeg').dom.value = "";
		
		//Descripcion de derecho
		Ext.get('txtDescripcionDer').dom.value = "";
			
	 }
</sec:authorize>
	
	
	//funcion que se llama cuando la llamada ajax vuelve 
	var callbackBusqueda = function (correcto,datos){
		if(correcto){
			Ext.get('divGrid').setVisibilityMode(Ext.Element.DISPLAY).show();
			
		}
		Ext.getCmp('gridCreatividad').getStore().loadData (datos);	
		//obtenemos el usuario de session
		obtenerUsuarioSesion();			
		//Rellenamos el combo de segmentos
		rellenarComboSegmentos();
	}
	
	//funcion de busqueda
	var buscar = function (){
							
		var datos={   
		}    					
		llamadaAjax('Creatividad.do','buscar','creatividadJSON',datos,callbackBusqueda,true);		
	}
	
	//Imprime el informe de creatividad
	var imprimirCreatividad = function(){
	
		if (Ext.isIE){
			myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
			myMask.show();
		}
		
		document.getElementById('ifExportar').src = contexto + 'Creatividad.do?exportarPDF=';
		
		if (Ext.isIE)
			document.getElementById('ifExportar').onreadystatechange = function(){myMask.hide();}; 
			
	}

	var mostrarFormulario = function(mostrar){
		
		if(mostrar){
			Ext.get('idFormCreatividad').setVisibilityMode(Ext.Element.DISPLAY).show();	
		}else{
			Ext.get('idFormCreatividad').setVisibilityMode(Ext.Element.DISPLAY).hide();
			//Limpiamos las selecciones del grid
			filaSeleccionada = null;
			Ext.getCmp('gridCreatividad').getSelectionModel().clearSelections(true);
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AM">
			deshabilitarBotonEliminar(true);
			</sec:authorize>	
		}
	}
	
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
	/**
	 * Funcion que vuelca los datos seleccionados en el formulario de modificacion
	 */
	var cargarDatosFormularios = function(seleccionado){
		//Volcamos los datos del registro seleccionado al formulario
		
		if(seleccionado != null){
			
			Ext.get('selLineaNegocio').dom.value = seleccionado.codLineaNegocio;
			
			Ext.get('selLineaNegocio').dom.disabled = false;
			Ext.get('selLineaNegocio').removeClass('dis');
			
			// Creatividad
			if (Ext.get('selLineaNegocio').dom.value == "01"){
				//se deshabilita Creatividad
				Ext.get('txtCreatividad').dom.disabled = true;
				Ext.get('txtCreatividad').addClass('dis');
				
				Ext.get('txtCreatividad').dom.value = '';
			}
			else{
				//se habilita Creatividad
				Ext.get('txtCreatividad').dom.disabled = false;
				Ext.get('txtCreatividad').removeClass('dis');
				
				Ext.get('txtCreatividad').dom.value = Texto_HTML(seleccionado.descCreatividad);
			}
			
			// Fecha de inicio
			Ext.get('txtFechaInicio').dom.value = seleccionado.fecIniVigencia;
			//Fecha de fin
			Ext.get('txtFechaFin').dom.value = seleccionado.fecFinVigencia;
			
			//Usuario alta
			Ext.get('txtUsuarioAlta').dom.value = seleccionado.usuarioAlta;
			//Usuario modificacion
			Ext.get('txtUsuarioMod').dom.value = seleccionado.usuarioMod;
			
			//Combo de segmento
			 Ext.get('selCodigoSeg').dom.value = seleccionado.descSegmento;
			//se deshabilita el codigo de segmento
			Ext.get('selCodigoSeg').dom.disabled =true;
			Ext.get('selCodigoSeg').addClass('dis');
			
			//Descripcion de segmento
			Ext.get('txtDescripcionSeg').dom.value = seleccionado.descSegmento;
			
			//Combo de derecho
			var arrayCombo = new Array();
			arrayCombo.push({texto: seleccionado.codDerecho, valor: seleccionado.codDerecho});
			agregarValoresCombo('selCodigoDer', arrayCombo, true);

			//se deshabilita el codigo de derecho
			Ext.get('selCodigoDer').dom.disabled =true;
			Ext.get('selCodigoDer').addClass('dis');
			//Descripcion de derecho
			Ext.get('txtDescripcionDer').dom.value = seleccionado.descDerecho;
						
			//Guarda los valores del intervalo
			datosAntiguos.fechaInicio = seleccionado.fecIniVigencia;
			datosAntiguos.fechaFin = seleccionado.fecFinVigencia;
			datosAntiguos.creatividad = seleccionado.descCreatividad;
				
		}
	}
	
	 
	 //OBTENER USUARIO
	var obtenerUsuarioSesion = function(){
		llamadaAjax('Creatividad.do','obtenerUsuario',null,null,resultadoObtenerUsuario,false);			
	}
	var resultadoObtenerUsuario = function (correcto, datos){
		Ext.get('hiddenUsuarioAlta').dom.value = datosResultado.datos[0];
	}
	 
	//METODOS PARA RELLENAR LOS COMBOS	
 
	//bandera para saber si se ha cargado el combo de segmentos.
	var comboSegmentosYaCargado = false;
	
	var rellenarComboSegmentos = function(){		
		llamadaAjax('Creatividad.do','obtenerSegmentos',null,null,respuestaRellenarComboSegmentos);
	}
	
	var rellenarComboDerechos = function(lineaNegocio){
		if(lineaNegocio != null && lineaNegocio != ''){
			llamadaAjax('Creatividad.do','obtenerDerechos','lineaNegocio',lineaNegocio,respuestaRellenarComboDerechos);
		}else{
			//Vaciamos combo
			var arrayCombo = new Array();
			arrayCombo.push({texto: "", valor: ""});
			agregarValoresCombo('selCodigoDer', arrayCombo, true);
		}
	}
	
	var respuestaRellenarComboSegmentos = function(correcto,datos){
		
		if(correcto){
			var arrayCombo = new Array();
			arrayCombo.push({texto: "", valor: ""});
			Ext.each(datos,function(dato){
				arrayCombo.push({texto: dato.codigo, valor: dato.descripcion});				
			});
			agregarValoresCombo('selCodigoSeg', arrayCombo, true); 
			comboSegmentosYaCargado = true;
		
		}else{
			comboSegmentosYaCargado = false;
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
		}
	}

	
	var pintarGrid = function (){
	 
	   // create the data store
	    var store = new Ext.data.Store({
	    	reader: new Ext.data.ObjectReader({}, [
				{name: 'descLineaNegocio', type: 'string'},
				{name: 'codSegmento', type: 'string'},
				{name: 'codDerecho', type: 'string'},
		  		{name: 'descCreatividad', type: 'string'},
				{name: 'fecIniVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'fecFinVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'usuarioAlta', type: 'string'},
				{name: 'fecAlta', type: 'string',sortType:convertirEnFecha},
				{name: 'usuarioMod', type: 'string'},
				{name: 'fecMod', type: 'string',sortType:convertirEnFecha},
				{name: 'codLineaNegocio', type: 'string'},
				{name: 'descSegmento', type: 'string'},
				{name: 'descDerecho', type: 'string'}
			])
	    });
	   
	  	var columnModel = new Ext.grid.ColumnModel([
		    {header: 'L&iacute;nea Negocio',sortable: true, dataIndex: 'descLineaNegocio'},		    
            {header: 'Segmento', sortable: true, dataIndex: 'codSegmento',renderer:addTooltip},
            {header: 'Derecho', sortable: true,  dataIndex: 'codDerecho',renderer:addTooltip},
            {header: 'Creatividad', sortable: true,  dataIndex: 'descCreatividad'},
            {header: 'F. Inicio vigencia', sortable: true, align:'center', dataIndex: 'fecIniVigencia'},
            {header: 'F. Fin vigencia', sortable: true, align:'center', dataIndex: 'fecFinVigencia'},
          	{header: 'Usuario Alta',sortable: true, align:'center', dataIndex: 'usuarioAlta'},
          	{header: 'F. Alta', sortable: true,width:135, align:'center', dataIndex: 'fecAlta'},
          	{header: 'Us. Modificaci&oacute;n',sortable: true, align:'center', dataIndex: 'usuarioMod'},
          	{header: 'F. Modificaci&oacute;n', sortable: true, align:'center', dataIndex: 'fecMod'}
		]);
	  	
	  	var cargarDatosFila = function(fila){

			Ext.each(Ext.query('.marcarFila', grid.id), function(filaMarcada) {
				Ext.fly(filaMarcada).removeClass('marcarFila');
			});
			Ext.fly(grid.getView().getRow(fila)).addClass('marcarFila');

			filaSeleccionada = grid.getStore().data.items[fila].data;
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AM">
			//Deshabilitamos la bandera de alta
			estadoMod = true;				
			// habilitamos el boton eliminar
			deshabilitarBotonEliminar(false);
			cargarDatosFormularios(filaSeleccionada);
			mostrarFormulario(true);
			controlarBotonGuardar();
			</sec:authorize>
		}	  	
	  			
	    // create the Grid
	    grid = new Ext.grid.EditorPasteCopyGridPanel({
	    	id:'gridCreatividad',
	        store: store,
			renderTo: 'divGrid',
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
	
	
	// Controlamos los eventos sobre los botones
	var controlEventos = function() {
		
		window.onresize = function() {
			refrescarGrid.defer(20);
		}
<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AM">
		//evento click del boton Eliminar
		Ext.get('btnEliminar').on('click', function() {
			eliminar();
		});
		//evento click del boton Eliminar
		Ext.get('btnNuevo').on('click', function() {
			//Limpiamos las selecciones del grid
			filaSeleccionada = null;
			Ext.getCmp('gridCreatividad').getSelectionModel().clearSelections(true);
			deshabilitarBotonEliminar(true);
			//Activamos la bandera:
			estadoMod = false;
			crearCreatividad();
			mostrarFormulario(true);
			controlarBotonGuardar();
		});
		
		//carga el campo de texto con la descripcion del segmento seleccionado
		Ext.get('selCodigoSeg').on('change', function(){
			controlarBotonGuardar();
			Ext.get('txtDescripcionSeg').dom.value = Ext.get('selCodigoSeg').dom.options[Ext.get('selCodigoSeg').dom.selectedIndex].value;
		});
		
		//carga el campo de texto con la descripcion del derecho seleccionado
		Ext.get('selCodigoDer').on('change', function(){
			controlarBotonGuardar();
			Ext.get('txtDescripcionDer').dom.value = Ext.get('selCodigoDer').dom.options[Ext.get('selCodigoDer').dom.selectedIndex].value;
		});
		
		//carga el combo de derechos segun la linea de negocio
		Ext.get('selLineaNegocio').on('change', function(){
			controlarBotonGuardar();
			var lineaNegocio = Ext.get('selLineaNegocio').dom.options[Ext.get('selLineaNegocio').dom.selectedIndex].value;
			if (lineaNegocio == "01"){
				//se deshabilita Creatividad
				Ext.get('txtCreatividad').dom.disabled = true;
				Ext.get('txtCreatividad').addClass('dis');
			}
			else{
				//se habilita Creatividad
				Ext.get('txtCreatividad').dom.disabled = false;
				Ext.get('txtCreatividad').removeClass('dis');
			}
			
			Ext.get('txtCreatividad').dom.value = '';
						
			var lineaNegocioSelec = Ext.get('selLineaNegocio').dom.options[Ext.get('selLineaNegocio').dom.selectedIndex].value;

			if(lineaNegocioSelec != null && lineaNegocioSelec != ""){
				rellenarComboDerechos(lineaNegocioSelec);
				Ext.get('selCodigoDer').dom.disabled =false;
				Ext.get('selCodigoDer').removeClass('dis');
			}else{
				rellenarComboDerechos("");
				Ext.get('selCodigoDer').dom.disabled =true;
				Ext.get('selCodigoDer').addClass('dis');
			}
		});
				
		Ext.get('txtCreatividad').on('change',function(){
			controlarBotonGuardar();
		});
		Ext.get('txtFechaInicio').on('change',function(){
			controlarBotonGuardar();
		});
		Ext.get('txtFechaFin').on('change',function(){
			controlarBotonGuardar();
		});			
		Ext.get('btnCancelar').on('click', function() {
			cancelar();
		});
		Ext.get('btnGuardar').on('click',function(){
			guardarCreatividad();
		});
		
</sec:authorize>
		// evento click del boton Imprimir
		//Ext.get('btnImprimirCrea').on('click', function() {
			//imprimirCreatividad();
		//});	
		
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
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AM">
	var iniciarCalendarios = function() {
		//obtenemos la fecha de hoy
		
		Calendar.setup( {
			inputField : "txtFechaInicio",
			dateFormat : "%d.%m.%Y",
			trigger : "btnCalendarIntervaloVigASIni",
			bottomBar : false,
			align : 'Br///B/r',
			onSelect : function() {
				this.hide();
				controlarBotonGuardar();
			}
		});

		Calendar.setup( {
			inputField : "txtFechaFin",
			dateFormat : "%d.%m.%Y",
			trigger : "btnCalendarIntervaloVigASFin",
			bottomBar : false,
			align : 'Br///B/r',
			onSelect : function() {
				this.hide();
				controlarBotonGuardar();
			}
		});
	}
	
	//comprueba que todos los campos estan rellenos
	var comprobarCamposAltaTodos = function() {
		var datosMetidos = Ext.get('selLineaNegocio').dom.value != ''
				&& Ext.get('txtFechaInicio').dom.value != ''
				&& Ext.get('txtFechaFin').dom.value != ''
				&& Ext.get('selCodigoSeg').dom.value != ''
				&& Ext.get('selCodigoDer').dom.value != ''

		return datosMetidos;
	}

	//comprueba que alguno de los campos este relleno
	var comprobarCamposAltaAlguno = function() {
		var datosMetidos = Ext.get('selLineaNegocio').dom.value != ''
				|| Ext.get('txtFechaInicio').dom.value.trim() != ''
				|| Ext.get('txtFechaFin').dom.value.trim() != ''
				|| Ext.get('selCodigoSeg').dom.value != ''
				|| Ext.get('selCodigoDer').dom.value != '';

		return datosMetidos;
	}
	
	//controla que el boton guardar est� deshabilitado hasta que se rellenen los campos	
	var controlarBotonGuardar = function() {

		var todosRellenos = comprobarCamposAltaTodos();
		var habilitar = false;
		if(estadoMod == false){
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
				var creatividad = HTML_Texto(Ext.get('txtCreatividad').dom.value);
				
				if(datosAntiguos.fechaInicio != fechaIni || datosAntiguos.fechaFin != fechaFin || datosAntiguos.creatividad != creatividad){
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
	
	
	
	//Boton cancelar, controla que aparezca el mensaje de aviso
	var cancelar = function() {
		//En la modificacion no se muestra el mensaje
		if (comprobarCamposAltaAlguno() && !estadoMod ) {
				mostrarConfirmacion("Se van a perder los datos introducidos,&iquest;Desea continuar?",cerrarFormulario,null);
		}else{
			mostrarFormulario(false);
		}
	}
	</sec:authorize>
	
	/**
	 * Cierra el formulario
	 */
	var cerrarFormulario = function(){
		mostrarFormulario(false);
	}
	//funcion que se llama cuando la llamada ajax vuelve 
	var volcarUsuario = function (correcto,datos){
		if(correcto){
			Ext.get('hiddenUsuarioAlta').dom.value = datos[0];
		}

	}
	
	//carga el usuario de alta en el campo de la pantalla de alta.
	var obtenerUsuarioSesion = function(){
		llamadaAjax('Creatividad.do','obtenerUsuario',null,null,volcarUsuario,false);		
	}
	
	
	
	return {
		init: function (){
		 	Ext.QuickTips.init();
			pintarGrid();
			controlEventos();
<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AM">	
			iniciarCalendarios();
			deshabilitarBotonEliminar(true);
			//Controlamos el boton guardar
			controlarBotonGuardar();
</sec:authorize>			
			//Ocultamos el formulario de creacion
			mostrarFormulario(false);

			buscar();
		}
	}
}();

Ext.onReady (CGLOBAL.init, CGLOBAL, true);
</sec:authorize>