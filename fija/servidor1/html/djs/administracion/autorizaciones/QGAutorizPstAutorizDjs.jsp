<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
Ext.ns("CGPESAUTORIZ");
CGPESAUTORIZ = function(){

   //Variables globales	
   var grid = null;
   var record_antiguo;
   var alta = false;
   var hay_seleccion=true;
   var suspender_eventos=false;
   var datosResultado;
   var myMask;
   
   var codLinNegocio = "";
   var codigoSis = "";
   var codigoSer = "";      
   var criterio = null;

   var pulsaNuevo = false;
   
   var arrayComboSisExt = null;
   var arrayComboSerNA = null;
   
   var gridHist = null;
   		
   	cargaHistorico = false;
   
    //paginacion.
	hayPaginacion = "";
	posicionPaginacion = null;
	maxPagina = 0;
	nejecucion = 0;
	
	pag_cod="";
	pag_desc="";
	pag_fechaCon="";
	
	nejecucionOld = 0;
	posicionPaginacionOld = null;
	
	//paginacion historico.
	hayPaginacionHist = "";
	posicionPaginacionHist = null;
	nejecucionHist = 0;
	
    filaSeleccionada = null;
    
    filaSeleccionadaHist = null;
   
	//Funcion que carga los datos del filtro
	var cargarDatosAutorizaciones = function(){
		Ext.get('txtDescripcionSE').dom.value='';
		Ext.get('txtDescripcionSNA').dom.value='';
		Ext.get('txtDescripcionSEed').dom.value='';
		Ext.get('txtDescripcionSNAed').dom.value='';
		
		cargarComboSisExt();
		cargarComboServNA();
	}

	
	//Funciones para la carga del combo Sistemas Externos
	var callBackComboSisExt = function(correcto,datos){
		if(correcto){
   			var arrayCombo = new Array();
			arrayComboSisExt = new Array();
			arrayCombo.push({texto: "Todos", valor: ""});
			arrayComboSisExt.push({texto: "Todos", valor: ""});

			Ext.each(datos,function(dato){
				var fechaIni = Date.parseDate(dato.fxIniVigencia,'d.m.Y');
				var fechaFin = Date.parseDate(dato.fxFinVigencia,'d.m.Y');
				if(fechaIni.getTime() <= fechaHoy().getTime() && fechaHoy().getTime() <= fechaFin.getTime()){
					arrayCombo.push({texto: Texto_HTML(dato.anagrama), valor: Texto_HTML(dato.descripcion)});
					arrayComboSisExt.push({texto: Texto_HTML(dato.anagrama), valor: Texto_HTML(dato.descripcion)});//para los combos de la pantalla de alta
				}
			});
			agregarValoresCombo('selCodigoSE', arrayCombo, true);//combo de la busqueda
			agregarValoresCombo('selCodigoSEed', arrayCombo, true);//combo de la alta/mod

		}
	}

	var cargarComboSisExt = function(){
		var datosSisExt={   
		}    
		llamadaAjax('Autorizaciones.do','cargarComboSisEx','autorizacionesJSON',datosSisExt,callBackComboSisExt,false);	
	}

	//Funciones para la carga del combo Servicios NA
	var callBackComboSerNA = function(correcto,datos){
		if(correcto){
   			var arrayCombo = new Array();
			arrayComboSerNA = new Array();
			arrayCombo.push({texto: "Todos", valor: ""});
			arrayComboSerNA.push({texto: "Todos", valor: ""});

			Ext.each(datos,function(dato){
				var fechaIni = Date.parseDate(dato.fxIniVigencia,'d.m.Y');
				var fechaFin = Date.parseDate(dato.fxFinVigencia,'d.m.Y');
				if(fechaIni.getTime() <= fechaHoy().getTime() && fechaHoy().getTime() <= fechaFin.getTime()){
					arrayCombo.push({texto: Texto_HTML(dato.codigo), valor: Texto_HTML(dato.descripcion)});
					arrayComboSerNA.push({texto: Texto_HTML(dato.codigo), valor: Texto_HTML(dato.descripcion)});//para los combos de la pantalla de alta
				}
			});
			agregarValoresCombo('selCodigoSNA', arrayCombo, true);//combo de la busqueda
			agregarValoresCombo('selCodigoSNAed', arrayCombo, true);//combo de alta/mod
		}
	}

	var cargarComboServNA = function(){
		var datosSerNA={   
		}    
		llamadaAjax('Autorizaciones.do','cargarComboSerNA','autorizacionesJSON',datosSerNA,callBackComboSerNA,false);	
	}
	
	//Funciones para la carga del grid de autorizaciones
	var callBackCargarGrid = function(correcto,datosResultado){
		if(correcto){
			if(!cargaHistorico){
				hayPaginacion = datosResultado.indPgnIn;
				posicionPaginacion = datosResultado.pgnTx;
   				Ext.getCmp('gridAutorizaciones').getStore().loadData(datosResultado.datos);
   			}else{
   				hayPaginacionHist = datosResultado.indPgnIn;
				posicionPaginacionHist = datosResultado.pgnTx;
   				Ext.getCmp('gridHistorico').getStore().loadData(datosResultado.datos);
   			}
		}
	}
	
	//Funcion que carga los Datos de Autorizaciones en el grid
	var cargarDatosGrid = function(criterio){

		var posAux;
		var nejAux;
		
		if(!cargaHistorico){
			posicionPaginacionOld = new Array();
			posicionPaginacionOld.push(posicionPaginacion);
			nejecucionOld = nejecucion;
			Ext.getCmp('gridAutorizaciones').getStore().removeAll();
			
			posAux = posicionPaginacion;
			nejAux = nejecucion;
			
		}
		else{
			Ext.getCmp('gridHistorico').getStore().removeAll();
			
			posAux = posicionPaginacionHist;
			nejAux = nejecucionHist;
		}
		
		if (criterio != null){
			var busqueda = {
				codSistemaExterno: criterio.codSis,
				codServicioNA: criterio.codSer,
				inActuacion: criterio.inActuacion,
				pgnTx: posAux,
   				nejecucion: nejAux   
			};
		}
		
		llamadaAjax('Autorizaciones.do','buscar','busquedaJSON',busqueda,callBackCargarGrid,true,true);
	}
	
	//Pintar grid
	var pintarGrid = function (){

	   // create the data store
	    var store = new Ext.data.Store({
	    	reader: new Ext.data.ObjectReader({}, [
				{name: 'sistema', type: 'string'},
				{name: 'servicioNA', type: 'string'},
				{name: 'lineaNegocio', type: 'string'},
				{name: 'fxIniVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'fxFinVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'usuarioAlta', type: 'string'},
				{name: 'usuarioModif', type: 'string'}
			])
	    });
	    
	  	var columnModel = new Ext.grid.ColumnModel([
		    {header: 'Sistema',sortable: true,width:80,  dataIndex: 'sistema',renderer:addTooltip},		    
            {header: 'Servicio NA', sortable: true,id:'colServNA', dataIndex: 'servicioNA',renderer:addTooltip},
            {header: 'L&iacute;nea Negocio', sortable: true, dataIndex: 'lineaNegocio', renderer:rendererLineaNegocio},
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
			<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
			Ext.get('btnEliminar').dom.disabled = false;
			Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar.gif';
			Ext.get('btnEliminar').removeClass('btnDis');
			
			cargarDatosModificacion(false);
			</sec:authorize>
			hay_seleccion=false;
		}
		
		
	    // create the Grid
	    grid = new Ext.grid.EditorPasteCopyGridPanel({
	    	id:'gridAutorizaciones',
	        store: store,
			renderTo: 'divGridAutorizaciones',
			cls:'gridCG',
			selectionModel: '',
			funcionRollBack: cargarDatosFila,
			cm: columnModel,
	        stripeRows: true,
	        autoExpandColumn: 'colServNA',
	        height: 177,
	        autoWidth:true,
			bbar: new QGPagingToolbarAutorizPst({
               	pageSize: 100,
               	store: store,
               	displayInfo: false,
               	idGrid: 'gridAutorizaciones'
        	})
	    });
	    
	}
	
	//Funcion para mortrar la descripcion de la columna Línea de negocio
	var rendererLineaNegocio = function(data, cellmd, record, rowIndex,colIndex, store) {
		if(data == '')
			return '';
		else if(data == 'C')	
			return 'Convergente';
		else if(data == 'F')	
			return 'Fija';
		else if(data == 'M')	
			return 'M&oacute;vil';
	}

	//Funciones para mostrar el tooltip
	var cargarSistemasExTooltip = function(valor){
		
		for (var i=0;i< Ext.get('selCodigoSE').dom.options.length;i++){
			if (Ext.get('selCodigoSE').dom.options[i].text == valor){
				return Ext.get('selCodigoSE').dom.options[i].value;
			}
		}
	}
	
	var cargarServiciosNATooltip = function(valor){
		
		for (var i=0;i< Ext.get('selCodigoSNA').dom.options.length;i++){
			if (Ext.get('selCodigoSNA').dom.options[i].text == valor){
				return Ext.get('selCodigoSNA').dom.options[i].value;
			}
		}
	}
	
    function addTooltip(value,metadata, record, rowIndex, colIndex, store){
	   	//En record viene el elemento formado del grid
		var valorTooltip = "";
		//Dependiendo de la columna que estemos renderizando se metera un valor de descripcion u otro
		//Columna de segmento
		if(colIndex == 0){
			valorTooltip = cargarSistemasExTooltip(record.data.sistema);
		//Columna de derecho
		}
		else if(colIndex == 1){
	 		valorTooltip = cargarServiciosNATooltip(record.data.servicioNA)
	 	}
		//Accedemos al tooltip
		if(valorTooltip != null && valorTooltip != ''){
			metadata.attr = 'ext:qtip="' + valorTooltip +'"';
		}
		//Debe devolver el valor para rellenar la columna
		return value;
     }
	
	//Pintar grid Historico
	var pintarGridHistorico = function (){

	   // create the data store
	    var store = new Ext.data.Store({
	    	reader: new Ext.data.ObjectReader({}, [
				{name: 'sistema', type: 'string'},
				{name: 'servicioNA', type: 'string'},
				{name: 'lineaNegocio', type: 'string'},
				{name: 'fxIniVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'fxFinVigencia', type: 'string',sortType:convertirEnFecha},
				{name: 'usuarioAlta', type: 'string'},
				{name: 'usuarioModif', type: 'string'}
			])
	    });
	    
	  	var columnModel = new Ext.grid.ColumnModel([
		    {header: 'Sistema',sortable: true,width:80,  dataIndex: 'sistema',renderer:addTooltip},		    
            {header: 'Servicio NA', sortable: true,id:'colServNA', dataIndex: 'servicioNA',renderer:addTooltip},
            {header: 'L&iacute;nea Negocio', sortable: true, dataIndex: 'lineaNegocio', renderer:rendererLineaNegocio},
            {header: 'Fecha inicio vigencia', sortable: true,width:135, align:'center', dataIndex: 'fxIniVigencia'},
            {header: 'Fecha fin vigencia', sortable: true,width:135, align:'center', dataIndex: 'fxFinVigencia'},
          	{header: 'Usuario de alta',sortable: true,width:110,  align:'center', dataIndex: 'usuarioAlta'},
          	{header: 'Usuario de modif.',sortable: true,width:120,  align:'center', dataIndex: 'usuarioModif'}
		]);
		
		var cargarDatosFilaHist = function(fila){ 
			Ext.each(Ext.query('.marcarFila', Ext.getCmp('gridHistorico').id), function(filaMarcada) {
				Ext.fly(filaMarcada).removeClass('marcarFila');
			});
			Ext.fly(Ext.getCmp('gridHistorico').getView().getRow(fila)).addClass('marcarFila');
			
			filaSeleccionadaHist = Ext.getCmp('gridHistorico').getStore().data.items[fila].data;
		}
		
	    // create the Grid
	    gridHist = new Ext.grid.EditorPasteCopyGridPanel({
	    	id:'gridHistorico',
	        store: store,
			renderTo: 'divGridHistorico',
			cls:'gridCG',
			selectionModel: '',
			funcionRollBack: cargarDatosFilaHist,
			cm: columnModel,
	        stripeRows: true,
	        autoExpandColumn: 'colServNA',
	        height: 177,
	        autoWidth:true,
			bbar: new QGPagingToolbarAutorizPstHist({
               	pageSize: 100,
               	store: store,
               	displayInfo: false,
               	idGrid: 'gridHistorico'
        	})
	    });
	    
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
				<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
				comprobarHabilitarGuardar();
				</sec:authorize>
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
				<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
				comprobarHabilitarGuardar();
				</sec:authorize>
			}
		});
	}
	
	//Funcion que carga los datos para modificarlos o dar de alta
	var cargarDatosModificacion = function (cancelar){	 
		<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
		comprobarHabilitarGuardar();
		</sec:authorize>
		var descripcionSis = Ext.get('txtDescripcionSEed').dom.value;
		var descripcionSer = Ext.get('txtDescripcionSNAed').dom.value;
		var lineaNegocio = Ext.get('lineaNegocio').dom.value;
		
		
		var fechaInicio = Ext.get('txtFechaInicio').dom.value;
		var fechaFin = Ext.get('txtFechaFin').dom.value;
		
		if (alta){
			if (descripcionSis.trim().length>0 || descripcionSer.trim().length>0 || 
				fechaInicio.trim().length>0){
				Ext.Msg.show({
				   title:'Los datos se perder&aacute;n',
				   msg: '<span>Se van a perder los datos introducidos, &iquest;Desea continuar?</span>',
				   buttons: Ext.Msg.YESNO,
				   icon: Ext.MessageBox.WARNING,
				   fn:function(respuesta){
				   		if (respuesta == 'yes'){
				   			alta=false;
				   			if(cancelar){
							   	cancelarAutorizacion();
							}else{
					  			cargarValoresFomularioMod();
					  		}
				   		}else{
				   			hay_seleccion=true;
				   			
				   			filaSeleccionada = null;
							Ext.getCmp('gridAutorizaciones').getSelectionModel().clearSelections(true);
							
				   			Ext.get('txtCodigo').dom.focus();
				   		}
				   }
				});
			}else {
				alta=false;
		  		if(cancelar){
				   	cancelarAutorizacion();
				}else{
		  			cargarValoresFomularioMod();
		  		}
			}
	  	}else{
	  		//MODO MODIFICACION
			if (descripcionSis.trim().length>0 || descripcionSer.trim().length>0 || 
				fechaInicio.trim().length>0){
				if (record_antiguo != null &&
					record_antiguo.sistema == Ext.get('selCodigoSEed').dom.options[Ext.get('selCodigoSEed').dom.selectedIndex].text &&
					record_antiguo.servicioNA == Ext.get('selCodigoSNAed').dom.options[Ext.get('selCodigoSNAed').dom.selectedIndex].text &&
					record_antiguo.lineaNegocio == Ext.get('lineaNegocio').dom.value &&
					record_antiguo.fxIniVigencia == fechaInicio){
					
					if(cancelar){
					   	cancelarAutorizacion();
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
								   	cancelarAutorizacion();
								}else{
						  			cargarValoresFomularioMod();
						  		}
					   		}else{
					   			hay_seleccion=false;
					   			suspender_eventos=true;
					   			
					   			var index = grid.getStore().indexOf (record_antiguo);
					   			filaSeleccionada = record_antiguo;
					   			<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
					   			Ext.get('lineaNegocio').dom.focus();
					   			</sec:authorize>
					   		}
					   }
					});
				}
			}else{
				Ext.get('idFormAutorizaciones').setVisibilityMode(Ext.Element.DISPLAY).show();
				if(cancelar){
				   	cancelarAutorizacion();
				}else{
		  			cargarValoresFomularioMod();
		  		}
				
			}
	  	}					
	}

	//Funcion que cancela la modificación o el alta de una Autorizacion
	var cancelarAutorizacion = function() {
		Ext.get('selCodigoSEed').dom.value='';
		Ext.get('txtDescripcionSEed').dom.value='';
		Ext.get('selCodigoSNAed').dom.value='';
		Ext.get('txtDescripcionSNAed').dom.value='';
		Ext.get('lineaNegocio').dom.value='';
		Ext.get('txtUsuarioAlta').dom.value='';
		Ext.get('txtUsuarioMod').dom.value='';
		Ext.get('txtFechaInicio').dom.value='';
		Ext.get('txtFechaFin').dom.value='';
		
		filaSeleccionada = null;
		Ext.getCmp('gridAutorizaciones').getSelectionModel().clearSelections(true);
		<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">	
		//Deshabilitamos el boton Eliminar
		Ext.get('btnEliminar').dom.disabled = true;
		Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';
		Ext.get('btnEliminar').addClass('btnDis');
		</sec:authorize>
		record_antiguo=null;
		alta=false;
					
		//Ocultamos el formulario
		Ext.get('idFormAutorizaciones').setVisibilityMode(Ext.Element.DISPLAY).hide();
	}
	
	//Funciones que cargan los combos al seleccionar un valor en el grid
	var cargarLineaNegocio = function(valor){
		for (var i=0;i< Ext.get('lineaNegocio').dom.options.length;i++){
			if (Ext.get('lineaNegocio').dom.options[i].value == valor){
				Ext.get('lineaNegocio').dom.value = valor;
				Ext.get('lineaNegocio').dom.selectedIndex = i;
			}
		}
	}
	
	var cargarSistemasEx = function(valor){
		
		for (var i=0;i< Ext.get('selCodigoSEed').dom.options.length;i++){
			if (Ext.get('selCodigoSEed').dom.options[i].text == valor){
				Ext.get('selCodigoSEed').dom.value = valor;
				Ext.get('selCodigoSEed').dom.selectedIndex = i;
			}
		}
	}
	
	var cargarServiciosNA = function(valor){
		
		for (var i=0;i< Ext.get('selCodigoSNAed').dom.options.length;i++){
			if (Ext.get('selCodigoSNAed').dom.options[i].text == valor){
				Ext.get('selCodigoSNAed').dom.value = valor;
				Ext.get('selCodigoSNAed').dom.selectedIndex = i;
			}
		}
	}

	//Funcion para cargar los valores en el formulario	
	var cargarValoresFomularioMod = function (){
		
		//Inicializamos los combos
		Ext.get('txtDescripcionSEed').dom.value = "";
		Ext.get('selCodigoSEed').dom.value = Ext.get('selCodigoSE').dom.options[0].value;
		Ext.get('txtDescripcionSNAed').dom.value = "";
		Ext.get('selCodigoSNAed').dom.value=Ext.get('selCodigoSNA').dom.options[0].value;
		Ext.get('lineaNegocio').dom.value='';
		
		//Inhabilitamos los combos y cargamos valores
	 	Ext.get('selCodigoSEed').dom.readOnly = true;
	 	Ext.get('selCodigoSEed').dom.disabled = true;
	 	Ext.get('selCodigoSEed').addClass('dis');	
	 	cargarSistemasEx(filaSeleccionada.sistema);
		Ext.get('txtDescripcionSEed').dom.value = Ext.get('selCodigoSEed').dom.options[Ext.get('selCodigoSEed').dom.selectedIndex].value;

	 	Ext.get('selCodigoSNAed').dom.readOnly = true;
	 	Ext.get('selCodigoSNAed').dom.disabled = true;
	 	Ext.get('selCodigoSNAed').addClass('dis');
	 	cargarServiciosNA(filaSeleccionada.servicioNA);
		Ext.get('txtDescripcionSNAed').dom.value = Ext.get('selCodigoSNAed').dom.options[Ext.get('selCodigoSNAed').dom.selectedIndex].value;

		Ext.get('lineaNegocio').dom.readOnly = true;
	 	Ext.get('lineaNegocio').dom.disabled = true;
	 	Ext.get('lineaNegocio').addClass('dis');	
		cargarLineaNegocio(filaSeleccionada.lineaNegocio);
		
		var fechaInicio = filaSeleccionada.fxIniVigencia;
		Ext.get('txtFechaInicio').dom.value = fechaInicio;
		<sec:authorize ifAnyGranted="ROLE_AU">
		Ext.get('txtFechaInicio').dom.readOnly = true;
		Ext.get('txtFechaInicio').addClass('dis');
	 	Ext.get('divTxtFechaInicio').addClass('dis');
	 	Ext.get('btnCalendarFechaInicio').dom.disabled = true;
		</sec:authorize>
				
		var fechaFin = filaSeleccionada.fxFinVigencia;
		Ext.get('txtFechaFin').dom.value = fechaFin;
		Ext.get('txtFechaFin').dom.readOnly = true;
		Ext.get('txtFechaFin').addClass('dis');
	 	Ext.get('divTxtFechaFin').addClass('dis');
	 	Ext.get('btnCalendarFechaFin').dom.disabled = true;
		
		Ext.get('txtUsuarioAlta').dom.value = filaSeleccionada.usuarioAlta;
		Ext.get('txtUsuarioAlta').dom.readOnly = true;
	 	Ext.get('txtUsuarioAlta').addClass('dis');
	 		
	 	Ext.get('txtUsuarioMod').dom.value = Ext.get('hiddenUsuarioConectado').dom.value;
		Ext.get('txtUsuarioMod').dom.readOnly = true;
	 	Ext.get('txtUsuarioMod').addClass('dis');
	 	
		record_antiguo = filaSeleccionada;
		<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
		Ext.get('btnGuardar').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
		Ext.get('btnGuardar').addClass('btnDis');
		Ext.get('btnGuardar').dom.disabled = true;
		</sec:authorize>
	}
	
	//Funcion PUBLICA llamada al pulsar sobre la pestania Autorizaciones
	var mostrarAutorizaciones = function (){
		
		Ext.get('divAutorizaciones').setVisibilityMode(Ext.Element.DISPLAY).show();
		<sec:authorize ifAnyGranted="ROLE_SA">
		Ext.get('divSisExt').setVisibilityMode(Ext.Element.DISPLAY).hide();
		</sec:authorize>
		Ext.get('divServiciosNA').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		Ext.get('pesAutorizaciones').addClass('activa');
		<sec:authorize ifAnyGranted="ROLE_SA">
		Ext.get('pesSisExt').removeClass('activa');
		</sec:authorize>
		Ext.get('pesServiciosNA').removeClass('activa');

		hayPaginacion = "";
		posicionPaginacion = null;
		nejecucion = 0;
		nejecucionOld = 0;
		posicionPaginacionOld = new Array();	
		
		Ext.getCmp('gridAutorizaciones').destroy();
		
		pintarGrid();

		cargarDatosAutorizaciones();
		iniciarCalendarios();
		obtenerUsuario();
		
		cancelarAutorizacion();
	}
	<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
	//Funcion que crea una nueva Autorizacion
	var nuevaAutorizacion = function(){

		comprobarHabilitarGuardar();
	
		var descripcionSis = Ext.get('txtDescripcionSEed').dom.value;
		var descripcionSer = Ext.get('txtDescripcionSNAed').dom.value;
		var lineaNegocio = Ext.get('lineaNegocio').dom.value;

		var fechaInicio = Ext.get('txtFechaInicio').dom.value;
		var fechaFin = Ext.get('txtFechaFin').dom.value;
		
		if (descripcionSis.trim().length>0 || descripcionSer.trim().length>0 || 
			fechaInicio.trim().length>0){
			if (record_antiguo != null &&
					record_antiguo.sistema == Ext.get('selCodigoSEed').dom.options[Ext.get('selCodigoSEed').dom.selectedIndex].text &&
					record_antiguo.servicioNA == Ext.get('selCodigoSNAed').dom.options[Ext.get('selCodigoSNAed').dom.selectedIndex].text &&
					record_antiguo.lineaNegocio == Ext.get('lineaNegocio').dom.value &&
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
				   			if (descripcionSis.trim().length==0){
					   			Ext.get('selCodigoSEed').dom.focus();
					   		}else if (descripcionSer.trim().length==0){
					   			Ext.get('selCodigoSNAed').dom.focus();
					   		}else if (fechaInicio.trim().length==0){
					   			Ext.get('txtFechaInicio').dom.focus();
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
		 
		//Habilitamos los campos y les quitamos la clase que le da estilos de deshabiltiado
		Ext.get('selCodigoSEed').dom.readOnly = false;
		Ext.get('selCodigoSEed').dom.disabled = false;
		Ext.get('selCodigoSEed').removeClass('dis');
		
		Ext.get('selCodigoSNAed').dom.readOnly = false;
		Ext.get('selCodigoSNAed').dom.disabled = false;
		Ext.get('selCodigoSNAed').removeClass('dis');
		
		Ext.get('lineaNegocio').dom.readOnly = false;
		Ext.get('lineaNegocio').dom.disabled = false;
		Ext.get('lineaNegocio').removeClass('dis');
		 
		  		
		//Si hay un registro seleccionado lo deseleccionamos
		if (filaSeleccionada != null) {
			hay_seleccion=true;
			
			filaSeleccionada = null;
			Ext.getCmp('gridAutorizaciones').getSelectionModel().clearSelections(true);
			
			//Deshabilitamos el boton Eliminar
			<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
			Ext.get('btnEliminar').dom.disabled = true;
			Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';
			Ext.get('btnEliminar').addClass('btnDis');
			</sec:authorize>
		}
		
		//Limpiamos los valores del formulario
		Ext.get('selCodigoSEed').dom.value='';
		Ext.get('txtDescripcionSEed').dom.value='';
		
		Ext.get('selCodigoSNAed').dom.value='';
		Ext.get('txtDescripcionSNAed').dom.value='';
		
		Ext.get('lineaNegocio').dom.value='';
		
		Ext.get('txtFechaInicio').dom.value = '';
		Ext.get('divTxtFechaInicio').removeClass('dis');
		Ext.get('txtFechaInicio').dom.readOnly = false;
  		Ext.get('btnCalendarFechaInicio').dom.disabled = false;
  		
  		Ext.get('txtFechaFin').dom.value = '31.12.2500';
		Ext.get('txtFechaFin').dom.readOnly = true;
		Ext.get('txtFechaFin').addClass('dis');
	 	Ext.get('divTxtFechaFin').addClass('dis');
	 	Ext.get('btnCalendarFechaFin').dom.disabled = true;
		
		Ext.get('txtUsuarioAlta').dom.value=Ext.get('hiddenUsuarioConectado').dom.value;
		Ext.get('txtUsuarioAlta').dom.readOnly = true;
		Ext.get('txtUsuarioAlta').addClass('dis');
		
		Ext.get('txtUsuarioMod').dom.value='';
		Ext.get('txtUsuarioMod').dom.readOnly = true;
		Ext.get('txtUsuarioMod').addClass('dis');

		
		//Mostramos el formulario
		Ext.get('idFormAutorizaciones').setVisibilityMode(Ext.Element.DISPLAY).show();
		//Colocamos el foco en el campo de texto Codigo
		Ext.get('selCodigoSEed').dom.focus();
		
		Ext.get('btnGuardar').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
		Ext.get('btnGuardar').addClass('btnDis');
		Ext.get('btnGuardar').dom.disabled = true;
	}
	</sec:authorize>
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
		llamadaAjax('Autorizaciones.do','obtenerUsuario','autorizacionesJSON',datosUsuario,callBackObtenerUsu,false);	
    }	
	
	//Funcion que comprueba que todos los campos estan rellenos
	var comprobarCamposAltaTodos = function() {
		var datosMetidos = Ext.get('selCodigoSEed').dom.value != ''
				&& Ext.get('selCodigoSNAed').dom.value != ''
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
					record_antiguo.sistema != Ext.get('selCodigoSEed').dom.options[Ext.get('selCodigoSEed').dom.selectedIndex].text ||
					record_antiguo.servicioNA != Ext.get('selCodigoSNAed').dom.options[Ext.get('selCodigoSNAed').dom.selectedIndex].text ||					
					<sec:authorize ifAnyGranted="ROLE_SA">
					record_antiguo.lineaNegocio != Ext.get('lineaNegocio').dom.value ||
					</sec:authorize>
					record_antiguo.fxIniVigencia != Ext.get('txtFechaInicio').dom.value){

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
	<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">

	//Funciones para la carga del grid de autorizaciones
	var callBackCargarGridMod = function(correcto,datosResultado){
		if(correcto){
   			Ext.getCmp('gridAutorizaciones').getStore().loadData(datosResultado.datos);
		}
	}
	
	//Funcion que carga los Datos de Autorizaciones en el grid para modificacion
	var cargarDatosGridMod = function(criterio){

		if (criterio != null){
			var busqueda = {
				codSistemaExterno: criterio.codSis,
				codServicioNA: criterio.codSer,
				inActuacion: criterio.inActuacion,
				pgnTx: posicionPaginacionOld[nejecucionOld],
   				nejecucion: nejecucionOld
			};
		}
		
		llamadaAjax('Autorizaciones.do','buscarParaModific','busquedaJSON',busqueda,callBackCargarGridMod,true,true);
	}


	//Funcion devuelta por modificacion
    var callBackGenerarMod = function(correcto,datosResultado){
		if(correcto){
			
			cancelarAutorizacion();
			var codSistema = "";
			if (Ext.get('selCodigoSE').dom.options[Ext.get('selCodigoSE').dom.selectedIndex].text != "Todos"){
				codSistema = Ext.get('selCodigoSE').dom.options[Ext.get('selCodigoSE').dom.selectedIndex].text;
			}
			var codServicio = "";
			if (Ext.get('selCodigoSNA').dom.options[Ext.get('selCodigoSNA').dom.selectedIndex].text != "Todos"){
				codServicio = Ext.get('selCodigoSNA').dom.options[Ext.get('selCodigoSNA').dom.selectedIndex].text;
			}
			criterio  = {
				codSis: codSistema,
				codSer: codServicio,
				inActuacion: "C"
			};
			cargarDatosGridMod(criterio);
		}
	}
	
	//Funcion que modifica una autorizacion
    var modificarDatos = function (){

		var autorizacion = {
			sistema:Ext.get('selCodigoSEed').dom.options[Ext.get('selCodigoSEed').dom.selectedIndex].text,
			servicioNA:Ext.get('selCodigoSNAed').dom.options[Ext.get('selCodigoSNAed').dom.selectedIndex].text,
			lineaNegocio:Ext.get('lineaNegocio').dom.value,
			fxIniVigencia:Ext.get('txtFechaInicio').dom.value,
			fxFinVigencia:Ext.get('txtFechaFin').dom.value,
			usuarioAlta:Ext.get('txtUsuarioAlta').dom.value,
			usuarioModif:Ext.get('hiddenUsuarioConectado').dom.value,
			accion: 'M'
		};
		llamadaAjax('Autorizaciones.do','gestionar','autorizacionesJSON',autorizacion,callBackGenerarMod,false,true);
    }
    
    //Funcion devuelta por alta-baja
    var callBackGenerar = function(correcto,datosResultado){
		if(correcto){
			hayPaginacion = "";
			posicionPaginacion = null;
			nejecucion = 0;
			nejecucionOld = 0;
			posicionPaginacionOld = new Array();
			
			cancelarAutorizacion();
			var codSistema = "";
			if (Ext.get('selCodigoSE').dom.options[Ext.get('selCodigoSE').dom.selectedIndex].text != "Todos"){
				codSistema = Ext.get('selCodigoSE').dom.options[Ext.get('selCodigoSE').dom.selectedIndex].text;
			}
			var codServicio = "";
			if (Ext.get('selCodigoSNA').dom.options[Ext.get('selCodigoSNA').dom.selectedIndex].text != "Todos"){
				codServicio = Ext.get('selCodigoSNA').dom.options[Ext.get('selCodigoSNA').dom.selectedIndex].text;
			}
			criterio  = {
				codSis: codSistema,
				codSer: codServicio,
				inActuacion: "C"
			};
			cargarDatosGrid(criterio);
		}
	}
    //Funcion que da de alta una autorizacion
    var altaDatos = function (){
    
		var autorizacion = {
			sistema:Ext.get('selCodigoSEed').dom.options[Ext.get('selCodigoSEed').dom.selectedIndex].text,
			servicioNA:Ext.get('selCodigoSNAed').dom.options[Ext.get('selCodigoSNAed').dom.selectedIndex].text,
			lineaNegocio:Ext.get('lineaNegocio').dom.value,
			fxIniVigencia:Ext.get('txtFechaInicio').dom.value,
			fxFinVigencia:Ext.get('txtFechaFin').dom.value,
			usuarioAlta:Ext.get('txtUsuarioAlta').dom.value,
			usuarioModif:Ext.get('txtUsuarioMod').dom.value,
			accion: 'A'
		};
		llamadaAjax('Autorizaciones.do','gestionar','autorizacionesJSON',autorizacion,callBackGenerar,false,true);
    }

	//Funcion que elimina una autorizacion
    var eliminarDatos = function (){
    
		var record = filaSeleccionada;
		var autorizacion = {
			sistema:record.sistema,
			servicioNA:record.servicioNA,
			lineaNegocio:record.lineaNegocio,
			fxIniVigencia:record.fxIniVigencia,
			fxFinVigencia:record.fxFinVigencia,
			usuarioAlta:record.usuarioAlta,
			usuarioModif:record.usuarioModif,
			accion: 'B'
		};
		
		llamadaAjax('Autorizaciones.do','gestionar','autorizacionesJSON',autorizacion,callBackGenerar,false);
    }

	//Funcion que elimina una autorizacion
	var eliminarAutorizaciones = function() {
	
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
	
	//Funcion que guarda una autorizacion nueva
	var guardarAutorizaciones = function() {
		var sistema = Ext.get('txtDescripcionSEed').dom.value;
		var servicioNA = Ext.get('txtDescripcionSNAed').dom.value;
		var linNegocio = Ext.get('lineaNegocio').dom.value;
		var fechaInicio = Ext.get('txtFechaInicio').dom.value;
		var fechaFin = Ext.get('txtFechaFin').dom.value;
		var dFechaInicio = new Date();
		var dFechaFin = new Date();
		dFechaInicio = Date.parseDate(fechaInicio, 'd.m.Y');
		dFechaFin = Date.parseDate(fechaFin, 'd.m.Y');
		var hoy = new Date();
		var msg = '';
		
		if (sistema.trim().length>0 && servicioNA.trim().length>0 && fechaInicio.trim().length>0){
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
			if (sistema.trim()=="")
				msg += '<span class="oblig">Sistema Externo</span>';
		
			if (servicioNA.trim()=="") 
				msg += '<span class="oblig">Servicio NA</span>';
		
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
				   		if (sistema.trim().length==0){
				   			Ext.get('selCodigoSEed').dom.focus();
				   		}else if (fechaInicio.trim().length==0){
				   			Ext.get('txtFechaInicio').dom.focus();
				   		}else if (servicioNA.trim().length==0){
				   			Ext.get('selCodigoSNAed').dom.focus();
				   		}
				   }
				});
			}
		}
	}

	//Funcion que comprueba que no existe registros duplicados al modificar o anadir
	var guardarDatos = function (){
		var sistema = Ext.get('selCodigoSEed').dom.options[Ext.get('selCodigoSEed').dom.selectedIndex].text;
		var servicioNA = Ext.get('selCodigoSNAed').dom.options[Ext.get('selCodigoSNAed').dom.selectedIndex].text;
		var lineaNegocio = Ext.get('lineaNegocio').dom.value;
		var existe = false;
		
		if (alta){
			//Comprobamos que no exista el codigo en el grid.
			Ext.each(grid.getStore().data.items,function(dato){
				if (dato.data.sistema.toLowerCase() == sistema.toLowerCase() &&
					dato.data.servicioNA.toLowerCase() == servicioNA.toLowerCase() &&
					dato.data.lineaNegocio.toLowerCase() == lineaNegocio.toLowerCase()){
					existe = true;
				}
			});
 				
			if (!existe){
				altaDatos();
			}else{
				Ext.Msg.show({
				   title:'Registro duplicado',
				   msg: '<span>Ya existe una Autorizaci&oacute;n con ese tr&iacute;o Sistema - Servicio NA - L&iacute;nea Negocio.</span>',
				   buttons: Ext.Msg.OK,
				   icon: Ext.MessageBox.ERROR
				});
			}
		}else{
			modificarDatos();
		}
	}
	</sec:authorize>
	
	//Funcion que imprime los datos de la pantalla en pdf
	var imprimirAutorizaciones= function() {
		if (Ext.isIE){
			myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
			myMask.show();
		}
		var codSistema = "";
		if (Ext.get('selCodigoSE').dom.options[Ext.get('selCodigoSE').dom.selectedIndex].text != "Todos"){
			codSistema = Ext.get('selCodigoSE').dom.options[Ext.get('selCodigoSE').dom.selectedIndex].text;
		}
		var codServicio = "";
		if (Ext.get('selCodigoSNA').dom.options[Ext.get('selCodigoSNA').dom.selectedIndex].text != "Todos"){
			codServicio = Ext.get('selCodigoSNA').dom.options[Ext.get('selCodigoSNA').dom.selectedIndex].text;
		}
		var busqueda = {
			codSistemaExterno: codSistema,
			codServicioNA: codServicio,
			inActuacion: "C",
   			pgnTx: posicionPaginacionOld[nejecucionOld],
   			nejecucion: nejecucionOld
		};

		
		document.getElementById('ifExportar').src = contexto + 'Autorizaciones.do?exportarPDF=&busquedaJSON='+Ext.encode(busqueda);
		if (Ext.isIE)
			document.getElementById('ifExportar').onreadystatechange = function(){myMask.hide();}; 
	}
	
	//Funcion que comprueba que algun campo se ha modificado
	var comprobarCampos = function() {
		var datosMetidos = Ext.get('selCodigoSEed').dom.value == ''
				&& Ext.get('selCodigoSNAed').dom.value == ''
				&& Ext.get('txtFechaInicio').dom.value == ''
				<sec:authorize ifAnyGranted="ROLE_SA">
				&& Ext.get('lineaNegocio').dom.value == ''
				</sec:authorize>
				;

		return datosMetidos;
	}

	//Funcion PUBLICA que comprueba si se han realizado cambios en la pantalla tanto en alta como en modificacion, usada para le cambio de pestanias
	var comprobarCambiosEnPantalla = function(){
		if (comprobarCampos()){
			return true;
		}
		else{
			if (record_antiguo != null &&
					record_antiguo.sistema == Ext.get('selCodigoSEed').dom.options[Ext.get('selCodigoSEed').dom.selectedIndex].text &&
					record_antiguo.servicioNA == Ext.get('selCodigoSNAed').dom.options[Ext.get('selCodigoSNAed').dom.selectedIndex].text &&
					record_antiguo.lineaNegocio == Ext.get('lineaNegocio').dom.value &&
					record_antiguo.fxIniVigencia == Ext.get('txtFechaInicio').dom.value){
				return true;
			}
			else{
				return false;
			}
		}				
	}
	
	//Control de Eventos
	var controlEventos = function(){
		//llama a comprobar si hay cambio de pestañas
		Ext.get('pesAutorizaciones').on('click', function() {
			comprobarCambio('autorizaciones');
		});
		//carga el campo de texto con la descripcion del Servicio NA
		Ext.get('selCodigoSE').on('change', function(){			 		
			Ext.get('txtDescripcionSE').dom.value = Ext.get('selCodigoSE').dom.options[Ext.get('selCodigoSE').dom.selectedIndex].value;	
		});
		//carga el campo de texto con la descripcion del Sistema Externo
		Ext.get('selCodigoSNA').on('change', function(){			 		
			Ext.get('txtDescripcionSNA').dom.value = Ext.get('selCodigoSNA').dom.options[Ext.get('selCodigoSNA').dom.selectedIndex].value;	
		});
		<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
		//carga el campo de texto con la descripcion del Servicio NA alta/mod
		Ext.get('selCodigoSEed').on('change', function(){			 		
			Ext.get('txtDescripcionSEed').dom.value = Ext.get('selCodigoSEed').dom.options[Ext.get('selCodigoSEed').dom.selectedIndex].value;
			comprobarHabilitarGuardar();	
		});
		//carga el campo de texto con la descripcion del Sistema Externo alta/mod
		Ext.get('selCodigoSNAed').on('change', function(){			 		
			Ext.get('txtDescripcionSNAed').dom.value = Ext.get('selCodigoSNAed').dom.options[Ext.get('selCodigoSNAed').dom.selectedIndex].value;
			comprobarHabilitarGuardar();	
		});
		//carga el codigo de linea de negocio
		Ext.get('lineaNegocio').on('change', function(){			 		
			codLinNegocio = Ext.get('lineaNegocio').dom.options[Ext.get('lineaNegocio').dom.selectedIndex].value;
			comprobarHabilitarGuardar();	
		});
		// cambios en la fecha de inicio
		Ext.get('txtFechaInicio').on('change', function(){			 
			comprobarHabilitarGuardar();
		});
		</sec:authorize>	
		//boton pulsar ver todos
		Ext.get('btnVerTodos').on('click', function() {
			cancelarAutorizacion();
		
			Ext.get('txtDescripcionSE').dom.value = "";
			Ext.get('selCodigoSE').dom.value = Ext.get('selCodigoSE').dom.options[0].value;
			Ext.get('txtDescripcionSNA').dom.value = "";
			Ext.get('selCodigoSNA').dom.value=Ext.get('selCodigoSNA').dom.options[0].value;
		
		
			hayPaginacion = "";
			posicionPaginacion = null;
			nejecucion = 0;
			nejecucionOld = 0;
			posicionPaginacionOld = new Array();
		
			criterio  = {
				codSis: "",
				codSer: "",
				inActuacion: "C"
			};
			cargarDatosGrid(criterio);
		});
		//boton Historico
		Ext.get('btnHistorico').on('click', function() {
			Ext.get('recPPal').removeClass('recCentContFC');
			Ext.get('recPPal').addClass('recCentCont');
			Ext.get('divPes').setVisibilityMode(Ext.Element.DISPLAY).hide();
			Ext.get('divHistorico').setVisibilityMode(Ext.Element.DISPLAY).show();
			Ext.get('tituloCabecera').update('Hist&oacute;rico Autorizaciones');
			
			
			hayPaginacionHist = "";
			posicionPaginacionHist = null;
			nejecucionHist = 0;	
			
			if(!Ext.getCmp('gridHistorico')){
				pintarGridHistorico();
				criterio  = {
					codSis: "",
					codSer: "",
					inActuacion: "H"
				};
				cargaHistorico = true;
				cargarDatosGrid(criterio);
			}	
		});
		//boton Volver Historico
		Ext.get('btnVolver').on('click', function() {
			Ext.get('recPPal').removeClass('recCentCont');
			Ext.get('recPPal').addClass('recCentContFC');
			Ext.get('divHistorico').setVisibilityMode(Ext.Element.DISPLAY).hide();
			Ext.get('divPes').setVisibilityMode(Ext.Element.DISPLAY).show();
			Ext.get('tituloCabecera').update('Autorizaciones');
			
			Ext.getCmp('gridHistorico').hide();
			Ext.getCmp('gridHistorico').destroy();
			
			cargaHistorico = false;
		});
		//boton pulsar filtro
		Ext.get('btnFiltrar').on('click', function() {
			cancelarAutorizacion();
		
			hayPaginacion = "";
			posicionPaginacion = null;
			nejecucion = 0;
			nejecucionOld = 0;
			posicionPaginacionOld = new Array();
		
			var codSistema = "";
			if (Ext.get('selCodigoSE').dom.options[Ext.get('selCodigoSE').dom.selectedIndex].text != "Todos"){
				codSistema = HTML_Texto(Ext.get('selCodigoSE').dom.options[Ext.get('selCodigoSE').dom.selectedIndex].text);
			}
			var codServicio = "";
			if (Ext.get('selCodigoSNA').dom.options[Ext.get('selCodigoSNA').dom.selectedIndex].text != "Todos"){
				codServicio = HTML_Texto(Ext.get('selCodigoSNA').dom.options[Ext.get('selCodigoSNA').dom.selectedIndex].text);
			}
			criterio  = {
				codSis: codSistema,
				codSer: codServicio,
				inActuacion: "C"
			};
			cargarDatosGrid(criterio);
		});
		<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
		// evento click del botón nuevo
		Ext.get('btnNuevo').on('click', function() {
			nuevaAutorizacion();
		});
		// evento click del botón Cancelar
		Ext.get('btnCancelar').on('click', function() {
			cargarDatosModificacion(true);
		});
		// evento click del botón dar de baja
		Ext.get('btnEliminar').on('click', function() {
			if (filaSeleccionada != null) {
				eliminarAutorizaciones();
			}
		});
		// evento click del botón Guardar
		Ext.get('btnGuardar').on('click', function() {
			guardarAutorizaciones();
		});
		</sec:authorize>
		// evento click del botón Imprimir
		//Ext.get('btnImprimir').on('click', function() {
			//imprimirAutorizaciones();
		//});
		
		if (Ext.isIE6){ //Se usa para que en IE6 no se superponga los combos al menu
			Ext.get('liAdministracion').on('mouseover', function() {
				Ext.get('selCodigoSNA').setVisibilityMode(Ext.Element.DISPLAY).hide();
			});
			Ext.get('liAdministracion').on('mouseout', function() {
				Ext.get('selCodigoSNA').setVisibilityMode(Ext.Element.DISPLAY).show();
			});
		}
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
			
			cargarDatosAutorizaciones();
			controlEventos();
			iniciarCalendarios();
			obtenerUsuario();
		},
		mostrarAutorizaciones: function (){
			mostrarAutorizaciones();
		},
		comprobarCambiosEnPantalla: function (){
			return comprobarCambiosEnPantalla();
		}	
	
	}
	 
}();

function mostrarAutorizaciones(){
	CGPESAUTORIZ.mostrarAutorizaciones();
}
function comprobarCambiosEnPantalla(){
	return CGPESAUTORIZ.comprobarCambiosEnPantalla();
}



Ext.onReady(CGPESAUTORIZ.init, CGPESAUTORIZ, true);
</sec:authorize>