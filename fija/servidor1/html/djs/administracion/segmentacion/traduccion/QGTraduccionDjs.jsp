<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
var CGLOBALTRA = function (){
	var grid = null;
	//variable para guardar el ultimo modo de busqueda "filtrar" o "todo" para utilizarla despues de eliminar
	var ultimaBusquedaTipo;
	//criterios de la ultima busqueda realizada.
	var ultimaBusqueda = new Ext.util.MixedCollection();
	//Bandera que indica si se debe eliminar o hay que pedir confirmacion
	var eliminarConfirmado = false;

	var filaSeleccionada = null;

	 /*
	  * Funcion que deshabilita el boton eliminar en caso que este habilitado y viceversa 
	  */
	var deshabilitarEstadoBotonBaja = function(estado){
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		Ext.get('btnEliminar').dom.disabled = estado
		if(estado){
		
			Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtBaja_des.gif';	
			Ext.get('btnEliminar').addClass('btnDis'); 

		}else{
			Ext.get('btnEliminar').removeClass('btnDis'); 
			Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtBaja.gif';

		}
		</sec:authorize>
	}
	 
	 /**
		 * Funcion que crea el tooltip para la columna
		 * @param metadata
		 * @param record
		 * @param rowIndex
		 * @param colIndex
		 * @param store
		 * @return
		 */
		function addTooltip(value,metadata, record, rowIndex, colIndex, store){
			//En record viene el elemento formado del grid
			var valorTooltip = "";
			//Dependiendo de la columna que estemos renderizando se metera un valor de descripcion u otro
			//Columna de segmento origen
			if(colIndex == 0){
				valorTooltip = record.data.descSegmentoFijo;
			//Columna de sbseg origen
			}else if(colIndex == 1 ){
				valorTooltip = record.data.descSubSegmentoFijo;
			//Columna de segmento destino
			}else if(colIndex == 2 ){
				valorTooltip = record.data.descSegmentoMovil;
			//Columna de sbseg destino
			}else if(colIndex == 3 ){
				valorTooltip = record.data.descSubSegmentoMovil;
			}
			//Accedemos al tooltip
			if(valorTooltip != null && valorTooltip != ''){
				metadata.attr = 'ext:qtip="' + valorTooltip +'"';
			}
			//Debe devolver el valor para rellenar la columna
			return value;
		}

	 

	var pintarGrid = function (){
		
		// create the data store
		var store = new Ext.data.Store({
			proxy: new Ext.ux.data.PagingMemoryProxy([]),
			reader: new Ext.data.ObjectReader({}, [
			                                       {name: 'codigoSegmentoFijo', type: 'string'},
			                                       {name: 'codigoSubSegmentoFijo', type: 'string'},
			                                       {name: 'codigoSegmentoMovil', type: 'string'},
			                                       {name: 'codigoSubSegmentoMovil', type: 'string'},
			                                       {name: 'planCuentas', type: 'string'},			                                       
			                                       {name: 'fechaIniVigencia', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'fechaFinVigencia', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'usuarioAlta', type: 'string'},
			                                       {name: 'fechaAlta',type:'string',sortType:convertirEnFecha},
			                                       {name: 'usuarioMod',type:'string'},
			                                       {name: 'fechaMod',type:'string',sortType:convertirEnFecha},
			                                       {name: 'usuarioBaja', type:'string'},
			                                       {name: 'fechaBaja',type:'string',sortType:convertirEnFecha},
			                                       {name: 'descSegmentoFijo', type: 'string'},
			                                       {name: 'descSubSegmentoFijo', type: 'string'},
			                                       {name: 'descSegmentoMovil', type: 'string'},
			                                       {name: 'descSubSegmentoMovil', type: 'string'},
			                                       ])
		});
		var columnModel = new Ext.grid.ColumnModel([
		                                            {header: 'Seg. Fijo',sortable: true,  dataIndex: 'codigoSegmentoFijo',renderer:addTooltip},
		                                            {header: 'Subseg. Fijo',sortable: true,  dataIndex: 'codigoSubSegmentoFijo',renderer:addTooltip},
		                                            {header: 'Seg. M&oacute;vil',sortable: true,  dataIndex: 'codigoSegmentoMovil',renderer:addTooltip},
		                                            {header: 'Subseg. M&oacute;vil',sortable: true,  dataIndex: 'codigoSubSegmentoMovil',renderer:addTooltip},
		                                            {header: 'Plan de Cuentas',sortable: true,  dataIndex: 'planCuentas'},
		                                            {header: 'F. Ini. Vigencia', sortable: true, align:'center', dataIndex: 'fechaIniVigencia'},
		                                            {header: 'F. Fin Vigencia', sortable: true, align:'center', dataIndex: 'fechaFinVigencia'},
		                                            {header: 'Us. Alta',sortable: true,  align:'center', dataIndex: 'usuarioAlta'},
		                                            {header: 'Fecha Alta',sortable: true,  align:'center', dataIndex: 'fechaAlta'},
		                                            {header: 'Us. Mod',sortable: true,  align:'center', dataIndex: 'usuarioMod'},
		                                            {header: 'Fecha Mod',sortable: true,  align:'center', dataIndex: 'fechaMod'}
		                                            ]);
		                                            
		 var cargarDatosFila = function(fila){
		 	Ext.each(Ext.query('.marcarFila', grid.id), function(filaMarcada) {
				Ext.fly(filaMarcada).removeClass('marcarFila');
			});
			Ext.fly(grid.getView().getRow(fila)).addClass('marcarFila');
			
			filaSeleccionada = grid.getStore().data.items[fila].data;
			deshabilitarEstadoBotonBaja(false);
		}                                            
		// create the Grid
		grid = new Ext.grid.EditorPasteCopyGridPanel({
			id:'gridBuscadorSegmentos',
			store: store,
			renderTo: 'divGrid',
			cls:'gridCG',
			funcionRollBack: cargarDatosFila,
			selectionModel: '',
			viewConfig: {
				forceFit:true
			},
			cm: columnModel,
			stripeRows: true,
			height: 148,
			bbar: new NoRefreshPagingToolbar({
       			store: store,       // grid and PagingToolbar using same store
       		 	displayInfo: false,
        		pageSize: 100
        	})
		});
		if (Ext.isIE6){
			anchoContenido = document.body.offsetWidth - 40;
			grid.setWidth (anchoContenido);
		}
	}

	// Controlamos los eventos sobre los botones
	var controlEventos = function() {
		
		window.onresize = function() {
			refrescarGrid.defer(20);
		}

		//evento click del boton Filtrar
		Ext.get('btnFiltrar').on('click', function() {
			ultimaBusquedaTipo = "filtrado";			
			obtenerDatos("filtrado");
		});
		//evento click del boton Filtrar
		Ext.get('btnVerTodos').on('click', function() {
			ultimaBusquedaTipo = "todos";
			obtenerDatos("todos");
		});
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		//evento click del boton Eliminar
		Ext.get('btnEliminar').on('click', function() {
			//eliminar();
			//Mostramos el popup.
			abrirPopUpGestionTraduccion();
			//Dicho boton no elimina, carga datos en una pantalla
			CGLOBALGESTTRA.volcarDatosPopup(filaSeleccionada);
			CGLOBALGESTTRA.controlarBotonGuardar();
		});
		
		//evento click del boton Nuevo
		Ext.get('btnNuevo').on('click', function() {
			abrirPopUpGestionTraduccion();
			//Funcion que vuelca el combo de segmentos fijos y moviles de pantalla de busqueda al
			// popup de creacion ya que hay que inicializar con los valores. 
			//se vuelca para no llamar a la cache.
			reiniciarComboPopup();
			CGLOBALGESTTRA.crearTraduccionPopup();
		});
		
		</sec:authorize>
		
		// evento click del boton Imprimir
		//Ext.get('btnImprimir').on('click', function() {
			//imprimirTraducciones();
		//});	
		
		//Controles de los combos
		//carga el campo de texto con la descripcion del segmento seleccionado
		Ext.get('selCodSegFijo').on('change', function(){			 
			Ext.get('txtDescripcionSegFijo').dom.value = Ext.get('selCodSegFijo').dom.options[Ext.get('selCodSegFijo').dom.selectedIndex].value;
			var codSegmento = Ext.get('selCodSegFijo').dom.options[Ext.get('selCodSegFijo').dom.selectedIndex].innerHTML;
			cargarSubSegmento(codSegmento,true);
		});
		
		Ext.get('selCodSubSegFijo').on('change', function(){			 
			Ext.get('txtDescripcionSubSegFijo').dom.value = Ext.get('selCodSubSegFijo').dom.options[Ext.get('selCodSubSegFijo').dom.selectedIndex].value;
		});
		
		Ext.get('selCodSegMov').on('change', function(){			 
			Ext.get('txtDescripcionSegMov').dom.value = Ext.get('selCodSegMov').dom.options[Ext.get('selCodSegMov').dom.selectedIndex].value;
			var codSegmento = Ext.get('selCodSegMov').dom.options[Ext.get('selCodSegMov').dom.selectedIndex].innerHTML;
			cargarSubSegmento(codSegmento,false);
		});
		
		Ext.get('selCodSubSegMov').on('change', function(){			 
			Ext.get('txtDescripcionSubSegMov').dom.value = Ext.get('selCodSubSegMov').dom.options[Ext.get('selCodSubSegMov').dom.selectedIndex].value;
		});
		
		if (Ext.isIE6){ //Se usa para que en IE6 no se superponga los combos al menu
			Ext.get('liSegmentacion').on('mouseover', function() {
				Ext.get('selCodSegMov').setVisibilityMode(Ext.Element.DISPLAY).hide();
				Ext.get('selCodSubSegMov').setVisibilityMode(Ext.Element.DISPLAY).hide();
			});
			Ext.get('liSegmentacion').on('mouseout', function() {
				Ext.get('selCodSegMov').setVisibilityMode(Ext.Element.DISPLAY).show();
				Ext.get('selCodSubSegMov').setVisibilityMode(Ext.Element.DISPLAY).show();
			});
		}
	}
	
	/**
	 * Funcion si se pulsa si en eliminar
	 */ 
	var funcionSiEliminar = function(){
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		 eliminarConfirmado = true;
		 eliminar();
		</sec:authorize>
	 }
	/**
	 * Funcion si se pulsa no en eliminar
	 */
	var funcionNoEliminar = function(){
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		eliminarConfirmado = false;
		</sec:authorize>
	}
	
	/**
	 * Respuesta de la funcion eliminar 
	 */
	var callBackEliminarTraduccion = function(correcto,datos){
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		if(correcto){
			deshabilitarEstadoBotonBaja(true);
			obtenerDatos(ultimaBusquedaTipo);
		}
	</sec:authorize>
	}

	/**
	 * Funcion eliminar traduccion
	 */
	var eliminar = function (){
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		if(eliminarConfirmado){
			//Inicializamos pase lo que pase
			eliminarConfirmado = false;
			
			var datosTraduccionEliminar={   
					
					codigoSegmentoFijo: filaSeleccionada.codigoSegmentoFijo,
					codigoSubSegmentoFijo: filaSeleccionada.codigoSubSegmentoFijo,
					codigoSegmentoMovil: filaSeleccionada.codigoSegmentoMovil,   		    	
					codigoSubSegmentoMovil: filaSeleccionada.codigoSubSegmentoMovil,    	
					planCuentas: filaSeleccionada.planCuentas,
					fechaIniVigencia: filaSeleccionada.fechaIniVigencia,
					fechaFinVigencia: filaSeleccionada.fechaFinVigencia

			}    
			
			llamadaAjax('Traduccion.do','buscar','segmentacionTraduccionJSON',datosTraduccionEliminar,callBackEliminarTraduccion,false);	
			
			}else{
				mostrarConfirmacion("&iquest;Desea dar de baja registro seleccionado?",funcionSiEliminar,funcionNoEliminar);
				eliminarConfirmado = false;
			
		}
		</sec:authorize>
	}

	//Boton imprimir
	var imprimirTraducciones = function(){

		if (Ext.isIE){
			myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
			myMask.show();
		}
		
		if(ultimaBusqueda.get('hayResultados')){
	
			//si la ultima busqueda fue con filtro, cogemos los datos de filtrado.
			var traduccionImprimir={};
			if(ultimaBusquedaTipo == "filtrado"){    		
	
				var traduccionImprimir = {
						
						codigoSegmentoFijo:ultimaBusqueda.get('codigoSegmentoFijo'),
						codigoSubSegmentoFijo:ultimaBusqueda.get('codigoSubSegmentoFijo'),
						codigoSegmentoMovil:ultimaBusqueda.get('codigoSegmentoMovil'),   		    	
						codigoSubSegmentoMovil:ultimaBusqueda.get('codigoSubSegmentoMovil')  	
							
				};
			}
			document.getElementById('ifExportar').src = contexto + 'Traduccion.do?exportarPDF=&segmentacionTraduccionJSON='+Ext.encode(traduccionImprimir);	
		}else{
			mostrarMensajeError("La b&uacute;squeda no ha devuelto resultados. No se puede generar el informe.");
		}
		if (Ext.isIE)
			document.getElementById('ifExportar').onreadystatechange = function(){myMask.hide();}; 		
		
	}
	
	
	
	
	/*
	 * Callback de la busqueda de traducciones
	 */
	var callBackBusquedaTraduccion = function(correcto,datos){
		if(correcto){
			Ext.get('divGrid').setVisibilityMode(Ext.Element.DISPLAY).show();
			ultimaBusqueda.add('hayResultados',true);
			
		}else{
			ultimaBusqueda.add('hayResultados',false);
			grid.getStore().load({params: {start: 0, limit: 100}});
		}
		
		grid.getStore().proxy =  new Ext.ux.data.PagingMemoryProxy(datos);
		grid.getStore().load({params: {start: 0, limit: 100}});
		
		
		//Habilita el boton nuevo
		// deshabilitamos el boton  eliminar al realizar una busuqeda
		deshabilitarEstadoBotonBaja(true);
		
		//Hay que coger el innerHtml, inicializamos a vacio
		ultimaBusqueda.add('codigoSegmentoFijo','');
		ultimaBusqueda.add('codigoSubSegmentoFijo','');
		ultimaBusqueda.add('codigoSegmentoMovil','');
		ultimaBusqueda.add('codigoSubSegmentoMovil','');
		
		
		if(Ext.get('selCodSegFijo').dom.options[Ext.get('selCodSegFijo').dom.selectedIndex].innerHTML != 'Todos'){
			ultimaBusqueda.add('codigoSegmentoFijo',Ext.get('selCodSegFijo').dom.options[Ext.get('selCodSegFijo').dom.selectedIndex].innerHTML);
		}
		if(Ext.get('selCodSubSegFijo').dom.options[Ext.get('selCodSubSegFijo').dom.selectedIndex].innerHTML != 'Todos'){
			ultimaBusqueda.add('codigoSubSegmentoFijo',Ext.get('selCodSubSegFijo').dom.options[Ext.get('selCodSubSegFijo').dom.selectedIndex].innerHTML);
		}
		if(Ext.get('selCodSegMov').dom.options[Ext.get('selCodSegMov').dom.selectedIndex].innerHTML != 'Todos'){
			ultimaBusqueda.add('codigoSegmentoMovil',Ext.get('selCodSegMov').dom.options[Ext.get('selCodSegMov').dom.selectedIndex].innerHTML);
		}
		if(Ext.get('selCodSubSegMov').dom.options[Ext.get('selCodSubSegMov').dom.selectedIndex].innerHTML != 'Todos'){
			ultimaBusqueda.add('codigoSubSegmentoMovil',Ext.get('selCodSubSegMov').dom.options[Ext.get('selCodSubSegMov').dom.selectedIndex].innerHTML);
		}
	}
	
	//boton filtrar o ver todos
	var obtenerDatos = function (boton){

		//Tenemos que comprobar si el boton ha sido pulsado en algun momento
		if(boton != null){
			
			var criterioBusqueda={};
			if(boton != "todos"){
				
				//Rellenamos el criterio de busqueda con los parametros que vengan en los combos
				if(Ext.get('selCodSegFijo').dom.options[Ext.get('selCodSegFijo').dom.selectedIndex].innerHTML != 'Todos'){
					criterioBusqueda.codigoSegmentoFijo = Ext.get('selCodSegFijo').dom.options[Ext.get('selCodSegFijo').dom.selectedIndex].innerHTML;
				}
				if(Ext.get('selCodSubSegFijo').dom.options[Ext.get('selCodSubSegFijo').dom.selectedIndex].innerHTML != 'Todos'){
					criterioBusqueda.codigoSubSegmentoFijo = Ext.get('selCodSubSegFijo').dom.options[Ext.get('selCodSubSegFijo').dom.selectedIndex].innerHTML;
				}
				if(Ext.get('selCodSegMov').dom.options[Ext.get('selCodSegMov').dom.selectedIndex].innerHTML != 'Todos'){
					criterioBusqueda.codigoSegmentoMovil = Ext.get('selCodSegMov').dom.options[Ext.get('selCodSegMov').dom.selectedIndex].innerHTML;
				}
				if(Ext.get('selCodSubSegMov').dom.options[Ext.get('selCodSubSegMov').dom.selectedIndex].innerHTML != 'Todos'){
					criterioBusqueda.codigoSubSegmentoMovil = Ext.get('selCodSubSegMov').dom.options[Ext.get('selCodSubSegMov').dom.selectedIndex].innerHTML;
				}

			}
			llamadaAjax('Traduccion.do','buscar','segmentacionTraduccionJSON',criterioBusqueda,callBackBusquedaTraduccion,true);	
			
		}
	}
	
	
	 //OBTENER USUARIO
	var obtenerUsuarioSesion = function(){
		llamadaAjax('Traduccion.do','obtenerUsuario',null,null,resultadoObtenerUsuario,false);			
	}
	var resultadoObtenerUsuario = function (correcto, datos){
		Ext.get('hiddenUsuarioConectado').dom.value = datosResultado.datos[0];
	}
	

	var abrirPopUpGestionTraduccion = function(){
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		var win = Ext.getCmp('winAnadirTraduccion');
		
		if(!win){
			win = new Ext.Window({
				id:'winAnadirTraduccion',
				contentEl:'popUpAnadirTraduccion',
				title:'A&ntilde;adir/Dar de baja Traducci&oacute;n',
				width:945,
				height:380,
				closeAction:'hide',
				closable: true,
				animateTarget:'btnNuevo',
				resizable:false,
				minimizable:true,
				draggable:false,
				modal:true,
				listeners:{
					minimize : function(){
						minimizarVentana('winAnadirTraduccion');
					},
					beforehide : function(){
						//Lanzamos la misma funcion que al pulsar el boton cancelar.
						return CGLOBALGESTTRA.cancelar();
					}
				}
			});
		}
		
		win.show();
		//Maximinizamos la ventana por si fue minimizada al cerrarse
		maximizarVentana('winAnadirTraduccion');
		</sec:authorize>
	}
	
	var refrescarGrid = function() {
		if (Ext.isIE6){
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).hide();
			var ancho = document.body.offsetWidth - 38;
			
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).show();
			grid.setWidth (ancho);
			Ext.getCmp('gridBuscadorTraduccionHistorico').setWidth (ancho);
		}else{
			grid.getView().refresh(true);
			Ext.getCmp('gridBuscadorTraduccionHistorico').getView().refresh(true);
		}
	}
	
	/*
	 * Inicializa los campos de busqueda
	 */
	var inicializarCampos = function(){

		Ext.get('txtDescripcionSubSegFijo').dom.value = "";
		Ext.get('txtDescripcionSegFijo').dom.value = "";	
		
		Ext.get('txtDescripcionSegMov').dom.value = "";
		Ext.get('txtDescripcionSubSegMov').dom.value = "";	

	}
	 
	
	 
	 /**
	  * Rellena el combo con los datos obtenidos de la llamada ajax
	  */
	 var callbackRellenarComboMovil = function(correcto,datos){
		  rellenarCombo('selCodSegMov',"Todos",correcto,datos);
		  //Rellenamos con los mismos datos el combo del popup
		  rellenarCombo('selCodSegMovilPop',"",correcto,datos);
	 }
	 
	 /**
	  * Rellena el combo con los datos obtenidos de la llamada ajax
	  */
	 var callbackRellenarComboFijo = function(correcto,datos){
	     //En el callback rellenamos el combo de segmentos moviles para que sean llamadas sincronas. 	  
		 var tipoComboValor = {}; 
		
		 tipoComboValor.tipoSegmento = false;
		 llamadaAjax('Traduccion.do','cargarCodigosSegmento','tipoComboValorJSON',tipoComboValor,callbackRellenarComboMovil,false);
	 
		  rellenarCombo('selCodSegFijo',"Todos",correcto,datos);
		  //Rellenamos con los mismos datos el combo del popup
		  rellenarCombo('selCodSegFijoPop',"",correcto,datos);
	 }

	 /**
	  * Carga los combos de segmentos
	  */
	 var cargarCombos = function(){

		 var tipoComboValor = {}; 
		 
		 tipoComboValor.tipoSegmento = true;
		 //En el callback de fijos llamamos al de moviles para rellenar las caches.
		 llamadaAjax('Traduccion.do','cargarCodigosSegmento','tipoComboValorJSON',tipoComboValor,callbackRellenarComboFijo,false);
	  } 
	 
	  /**
	  * Vuelca el contenido de los combos a los de pantalla.
	  */
		 var reiniciarComboPopup = function(){
		 <sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		  	//Tenemos que quitar el valor "Todos" del combo de la pantalla de busqueda, por tanto debemos 
		  	//concatenar un array con el nuevo valor vacio con el vector obtenido del combo de la pantalla 
		  	//sin el valor Todos.
		  	 var arrayVacio = new Array();
		  	 arrayVacio.push({texto: "" , valor: ""});
		  	 
		  	 //El segundo parametro a true indica que hay que quitar los valores vacios
		  	 var array = arrayVacio.concat(recuperarValoresCombo('selCodSegFijo',true));
		  	 agregarValoresCombo('selCodSegFijoPop',array , true);
		  	 
		  	 //Repetimos la operacion para dicho combo
		  	 array = arrayVacio.concat(recuperarValoresCombo('selCodSegMov',true));
		  	 agregarValoresCombo('selCodSegMovilPop',array , true);
			 
			 agregarValoresCombo('selCodSubSegFijoPop',new Array(), true);
			 agregarValoresCombo('selCodSubSegMovilPop',new Array(), true);
			 </sec:authorize>
		 }
	 
		 
	 /**
	   * Rellena el combo con los datos obtenidos de la llamada ajax
	   */
	 var callbackRellenarSubComboFijo = function(correcto,datos){
		 rellenarCombo('selCodSubSegFijo',"Todos",correcto,datos);
		 Ext.get('txtDescripcionSubSegFijo').dom.value = "";
	 }
	 /**
	  * Rellena el combo con los datos obtenidos de la llamada ajax
	  */
	 var callbackRellenarSubComboMovil = function(correcto,datos){
		 rellenarCombo('selCodSubSegMov',"Todos",correcto,datos);
		 Ext.get('txtDescripcionSubSegMov').dom.value = "";	
	 }
		 
	  /**
	   * Carga el subsegmento fijo o movil.
	   */
	 var cargarSubSegmento = function(codSegmento,fijo){
		//Formamos el objeto de busqueda
		 var tipoComboValor = {
				valorCombo:codSegmento,
		 		tipoSegmento:fijo
		 };
		 if(fijo){
			 llamadaAjax('Traduccion.do','cargarCodigosSubSegmento','tipoComboValorJSON',tipoComboValor,callbackRellenarSubComboFijo,false);
		 }else{
			 llamadaAjax('Traduccion.do','cargarCodigosSubSegmento','tipoComboValorJSON',tipoComboValor,callbackRellenarSubComboMovil,false);
		 }
	 }
	  

	return {
		init: function (){
			Ext.QuickTips.init();
			inicializarCampos();
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			controlEventos();
			//Cargamos los combos de segmentos
			cargarCombos();
			pintarGrid();
			obtenerUsuarioSesion();

		},
		obtenerDatos: function(){
			obtenerDatos(ultimaBusquedaTipo);
		}
	}
}();

function obtenerDatos(){
	CGLOBALTRA.obtenerDatos();
}

Ext.onReady (CGLOBALTRA.init, CGLOBAL, true);
</sec:authorize>