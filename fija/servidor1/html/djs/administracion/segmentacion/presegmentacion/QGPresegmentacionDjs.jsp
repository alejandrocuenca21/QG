<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
var CGLOBAL = function (){
	var grid = null;
	var datosResultado;	
	
	//variable para guardar el ultimo modo de busqueda "filtrar" o "todo" para utilizarla despues de eliminar
	ultimaBusquedaTipo = null;
	
	//criterios de la ultima busqueda realizada.
	ultimaBusqueda = new Ext.util.MixedCollection();
	
    //paginacion historico.
	hayPaginacionHist = "";
	posicionPaginacionHist = null;
	nejecucionHist = 0;
	
	regSeleccionado = null;
	
    filaSeleccionada = null;
    
    filaSeleccionadaHist = null;
    
    ventanaActual = null;
    
    var pulsado = false;

	var campoSegmento;
	var campoSubsegmento;
	var campoSubsegmento2;
	var campoDescripcionSubsegmento;
	var campoDescripcionSubsegmento2;
	
	var gridHist = null;
   		
   	cargaHistorico = false;
	
	var selComboAdmin = "";
	
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
	
	modificarReg = false;
	
	//Funciones para cargar el combo Tipo de Documento
	var callBackTipoDocumento = function(correcto,datosResultado){
		if (correcto){
			if(ventanaActual == "busqueda"){
				var arrayCombo = new Array();
		 		arrayCombo.push({texto: "Todos", valor: ""});
		 		Ext.each(datosResultado,function(dato){
		 			arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});
		 		});
				agregarValoresCombo('selTipoDoc', arrayCombo, true);
			}
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">	
			else { //Si estamos en la pantalla Alta-Mod para el Alta
				var arrayCombo = new Array();
		 		arrayCombo.push({texto: "", valor: ""});
		 		Ext.each(datosResultado,function(dato){
		 			arrayCombo.push({texto: dato.descripcion, valor: dato.codigo});
		 		});
				agregarValoresCombo('selTipoDocAS', arrayCombo, true);
				
				if (modificarReg == true){ //Si estamos en la pantalla Alta-Mod para Modficacion
					CGLOBALGESTPRESEG.cargarCombos();
					
					CGLOBALGESTPRESEG.controlarBotonGuardar();
				}
				
			}
			</sec:authorize>	
		}
	}
	
	var cargarTipoDocumento = function (){
		var datos={  
			codigoLinea: "01",
			codigoModalidad: "",
			codigoDocumento: ""						 
		}    
		llamadaAjax('ListaClientes.do','cargarTipoDocumento','busquedaTipoDocJSON',datos,callBackTipoDocumento,false);	
    }

	//Funciones para cargar el combo de segmentos para filtrado y para alta.
	//se carga el combo de segmentos para filtrado y para alta.
	var callBackComboSeg = function(correcto,datosResultado){
		if(correcto){			
			if(ventanaActual == "busqueda"){
				var arrayCombo = new Array();
				arrayCombo.push({texto: "Todos", valor: ""});
				Ext.each(datosResultado,function(dato){
					arrayCombo.push({texto: dato.codigo, valor: dato.descripcion});
				});
			
				agregarValoresCombo('selCodSeg', arrayCombo, true);//combo de la busqueda
				Ext.get('txtDescripcionSeg').dom.value = '';
			}
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
			else{//Si estamos en la pantalla Alta-Mod para el Alta
				var arrayCombo = new Array();		
				arrayCombo.push({texto: "", valor: ""});
				Ext.each(datosResultado,function(dato){
					arrayCombo.push({texto: dato.codigo, valor: dato.descripcion});
				});
				agregarValoresCombo('selCodSegAS', arrayCombo, true);//combo del alta
				Ext.get('txtDescripcionSegAS').dom.value = '';
				
				if (modificarReg == true){//Si estamos en la pantalla Alta-Mod para Modficacion
					CGLOBALGESTPRESEG.marcarSegmento(regSeleccionado.segmento);
				
					cargarTipoDocumento();
				}
			}
			</sec:authorize>
		}
	}

	var cargarCodigoSegmento = function(unidadEntrada,pantalla){
		
		if(pantalla == "busqueda"){
			var arrayCombo = new Array();
			arrayCombo.push({texto: "Todos", valor: ""});
		}
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		else{
			var arrayCombo = new Array();
			arrayCombo.push({texto: "", valor: ""});
		}
		</sec:authorize>
		//Guardamos la pantalla en una variable global para usarla en la llamada Ajax
		ventanaActual = pantalla;
		
		var segmentacion = {
			unidad: unidadEntrada
		};
		llamadaAjax('Presegmentacion.do','cargarCodigosSegmento','segmentacionPresegJSON',segmentacion,callBackComboSeg,false);	
	}

	//Funciones para cargar el combo de Of. Atencion
	var callBackComboOfAtencion = function (correcto,datosResultado){
		if (correcto){
			if(ventanaActual == "busqueda"){
				var arrayCombo = new Array();
		 		arrayCombo.push({texto: "Todos", valor: ""});
		 		Ext.each(datosResultado,function(dato){
		 			arrayCombo.push({texto: dato, valor: dato});
		 		});
				agregarValoresCombo('selOfAtenc', arrayCombo, true);
				
				if (arrayCombo.length > 1){
					Ext.get('selOfAtenc').dom.disabled = false;
				}
				else{
					Ext.get('selOfAtenc').dom.disabled = true;
				}
				
			}
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
			else{//Si estamos en la pantalla Alta-Mod para el Alta
				var arrayCombo = new Array();
		 		arrayCombo.push({texto: "", valor: ""});
		 		Ext.each(datosResultado,function(dato){
		 			arrayCombo.push({texto: dato, valor: dato});
		 		});
				agregarValoresCombo('selOfAtencAS', arrayCombo, true);
				
				if (modificarReg == true){//Si estamos en la pantalla Alta-Mod para Modficacion
					if (arrayCombo.length > 1){//Hay registros, cargamos el siguiente combo
						CGLOBALGESTPRESEG.marcarOfAtencion(regSeleccionado.ofAtencion);
						if (Ext.get('selOfAtencAS').dom.selectedIndex != 0){
							Ext.get('selTandemAS').dom.disabled = false;
							cargarTandem(regSeleccionado.segmento,regSeleccionado.subSegmento,regSeleccionado.ofAtencion);
						}
						else //Borramos si habia algo antes en "tandemAS"
						{
							Ext.get('selTandemAS').dom.disabled = true;
							Ext.get('selTandemAS').dom.selectedIndex = 0;
							var arrayCombo = new Array();		
							arrayCombo.push({texto: "", valor: ""});
							agregarValoresCombo('selTandemAS', arrayCombo, true);
							//Si se modifica siempre hay que comprobar
							//el resto de elementos							
							if (regSeleccionado.segmento != filaSeleccionada.segmento)
								CGLOBALGESTPRESEG.controlarBotonGuardar();							   						
						}
					}
					else{ //Borramos si habia algo antes en "tandemAS"
						Ext.get('selTandemAS').dom.disabled = true;
						Ext.get('selTandemAS').dom.selectedIndex = 0;
						var arrayCombo = new Array();		
						arrayCombo.push({texto: "", valor: ""});
						agregarValoresCombo('selTandemAS', arrayCombo, true);
						if (regSeleccionado.segmento != filaSeleccionada.segmento) //Si se modifica Segmento siempre hay que comprobar
							CGLOBALGESTPRESEG.controlarBotonGuardar();							   //el resto de elementos de su llamada
					}	
				}
				else{//Si estamos en la pantalla Alta-Mod para el Alta
					Ext.get('selTandemAS').dom.disabled = true;
					Ext.get('selTandemAS').dom.selectedIndex = 0;
					var arrayCombo = new Array();		
					arrayCombo.push({texto: "", valor: ""});					
					agregarValoresCombo('selTandemAS', arrayCombo, true);
					CGLOBALGESTPRESEG.controlarBotonGuardar();
				}
			}
			</sec:authorize>
		}
	}
	
	var cargarOfAtencion = function (codSeg,codSub){
		
		var entrada = {
			codigoSegmento: codSeg,
			codigoSubsegmento: codSub
		};
		llamadaAjax('Presegmentacion.do','cargarCodigosOfAtencion','segmentacionPresegJSON',entrada,callBackComboOfAtencion,false);	
	}
	
	//Funciones para cargar el combo de Tandem
	var callBackComboTandem = function (correcto,datosResultado){
		if (correcto){
			if(ventanaActual == "busqueda"){
				var arrayCombo = new Array();
		 		arrayCombo.push({texto: "Todos", valor: ""});
		 		Ext.each(datosResultado,function(dato){
		 			arrayCombo.push({texto: dato.coNemoTandem, valor: dato.coUoVenta});
		 		});
				agregarValoresCombo('selTandem', arrayCombo, true);
				
				if (arrayCombo.length > 1){
					Ext.get('selTandem').dom.disabled = false;
				}
				else{
					Ext.get('selTandem').dom.disabled = true;
				}
				
			}
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
			else{//Si estamos en la pantalla Alta-Mod para el Alta
				var arrayCombo = new Array();
		 		arrayCombo.push({texto: "", valor: ""});
		 		Ext.each(datosResultado,function(dato){
		 			arrayCombo.push({texto: dato.coNemoTandem, valor: dato.coUoVenta});
		 		});
				agregarValoresCombo('selTandemAS', arrayCombo, true);
				
				if (modificarReg == true){//Si estamos en la pantalla Alta-Mod para Modificacion
					if (arrayCombo.length > 1){//Hay registros, cargamos el combo, en caso contrario se queda vacio
						CGLOBALGESTPRESEG.marcarTandem(regSeleccionado.tandem);
						CGLOBALGESTPRESEG.controlarBotonGuardar();
					}
				}
			}
			</sec:authorize>
		}
	}
	
	var cargarTandem = function (codSeg,codSub,codOfAt){
		
		var entrada = {
			codigoSegmento: codSeg,
			codigoSubsegmento: codSub,
			ofAtencion: codOfAt
		};
		llamadaAjax('Presegmentacion.do','cargarCodigosTandem','segmentacionPresegJSON',entrada,callBackComboTandem,false);	
	}
	
	//Cargar subsegmento segun su código
	var cargarSubsegmento = function(codSeg){
			//Cargamos el subsegmento
					
			var codSub = "";
			
			if (codSeg == "AU"){
				codSub = "N2";
			}
			else if (codSeg == "GP"){
				codSub = "R1";
			}
			else if (codSeg == "ME"){
				codSub = "00";
			}
			else if (codSeg == "PE"){
				codSub = "BF";
			}
			else{
				codSub = "";
			}
			return codSub;
	}
	
	//Funciones para cargar la tabla de Presegmentaciones
   	var callBackObtenerDatos = function(correcto,datosResultado){
		if (correcto){
			if(!cargaHistorico){
				hayPaginacion = datosResultado.indPgnIn;
				posicionPaginacion = datosResultado.pgnTx;
				Ext.getCmp('gridBuscadorSegmentos').getStore().loadData(datosResultado.datos);
   			}
			else{
				hayPaginacionHist = datosResultado.indPgnIn;
				posicionPaginacionHist = datosResultado.pgnTx;
   				Ext.getCmp('gridHistorico').getStore().loadData(datosResultado.datos);
			}
   			ultimaBusqueda.add('hayResultados',true);
   		}
   		else{
   			ultimaBusqueda.add('hayResultados',false);
   		}
	}
	
	var obtenerDatos = function (entrada){
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">	
		deshabilitarEstadoBotonModif(true);
		deshabilitarEstadoBotonBaja(true);
		</sec:authorize>
		ultimaBusqueda.add('entradaBusqueda',entrada);
	
		llamadaAjax('Presegmentacion.do','cargarPresegmentaciones','segmentacionPresegJSON',entrada,callBackObtenerDatos,true,true);	
	}

	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
	//Funcion que elimina un registro
	var eliminar = function (){
		
		Ext.Msg.show( {
			msg : '&iquest;Desea dar de baja el registro seleccionado?',
			title : 'Aviso',
			buttons : Ext.Msg.YESNO,
			icon : Ext.Msg.INFO,
			minWidth : 400,
			fn : function(buttonId) {
				
				if (buttonId == 'yes') {
					var entrada = {
						codActuacion: "B",
						nuSegPsg: filaSeleccionada.nuSegPsg,
						tipoDocumento: filaSeleccionada.tipoDocumento,
						numDocumento: filaSeleccionada.numDocumento,
						segmento: filaSeleccionada.segmento,
						subSegmento: filaSeleccionada.subSegmento,
						ofAtencion: filaSeleccionada.ofAtencion,
						tandem: filaSeleccionada.tandem
					};
					operarPreseg(entrada);
				}else{
				}
			}
		});
	}
	
	//Funciones para la llamada al servicio para dar de alta, baja y modificar Presegmentaciones
   	var callBackOperarPreseg = function(correcto,datosResultado){
		if(correcto){
			inicializarCampos();
			ultimaBusquedaTipo = "todos";
			
			hayPaginacion = "";
			posicionPaginacion = null;
			nejecucion = 0;	
	
			var entrada = {
				codActuacion: "C",
				tipoDocumento: "",
				numDocumento: "",
				segmento: "",
				subSegmento: "",
				ofAtencion: "",
				tandem: "",
				pgnTx: hayPaginacion,
				nejecucion: nejecucion,
				tipoConsulta: "01",
				inVigencia: "T"	
			};
			//Llamada al método para cargar la tabla
			obtenerDatos(entrada);
		}
	}
	var operarPreseg = function(entrada){
		var datos = {
			codActuacion: entrada.codActuacion,
			nuSegPsg: entrada.nuSegPsg,
			tipoDocumento: entrada.tipoDocumento,
			numDocumento: entrada.numDocumento,
			segmento: entrada.segmento,
			subSegmento: entrada.subSegmento,
			ofAtencion: entrada.ofAtencion,
			tandem: entrada.tandem
		};
		if (entrada.codActuacion == "A" || entrada.codActuacion == "M"){
			cerrarVentana = true;
			Ext.getCmp('winAnadirSegmentos').hide();
		}
		llamadaAjax('Presegmentacion.do','operarPreseg','segmentacionPresegJSON',datos,callBackOperarPreseg,false);
	}
	
	</sec:authorize>

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
		llamadaAjax('Presegmentacion.do','obtenerUsuario','segmentacionPresegJSON',datosUsuario,callBackObtenerUsu,false);	
    }
   
   <sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
	//Funcion que deshabilita el boton eliminar en caso que este habilitado y viceversa 
	var deshabilitarEstadoBotonBaja = function(estado){
		Ext.get('btnEliminar').dom.disabled = estado
		if(estado){
			Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtBaja_des.gif';	
			Ext.get('btnEliminar').addClass('btnDis'); 

		}else{
			Ext.get('btnEliminar').removeClass('btnDis'); 
			Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtBaja.gif';
		}
		return;
	}
	
	//Funcion que deshabilita el boton modificar en caso que este habilitado y viceversa 
	var deshabilitarEstadoBotonModif = function(estado){
		Ext.get('btnModificar').dom.disabled = estado
		if(estado){
			Ext.get('btnModificar').dom.src = contexto + 'images/botones/QGbtModificar_des.gif';	
			Ext.get('btnModificar').addClass('btnDis'); 

		}else{
			Ext.get('btnModificar').removeClass('btnDis'); 
			Ext.get('btnModificar').dom.src = contexto + 'images/botones/QGbtModificar.gif';
		}
		return;
	}	
	</sec:authorize>
	 
	//Funcion para exportar el contenido de las pestanas a excel
	function exportarExcel(){  
		//Solo exportamos si hay resultados
		if(ultimaBusqueda.get('hayResultados')){
			if (Ext.isIE){
				myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
				myMask.show();
			}
			document.getElementById('ifExportar').src = contexto + 'Presegmentacion.do?exportarExcel=&segmentacionPresegJSON='+Ext.encode(ultimaBusqueda.get('entradaBusqueda'));
			
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
	
	//Funcion que imprime los datos de la pantalla en pdf
	var imprimir = function() {

	
		if(ultimaBusqueda.get('hayResultados')){
			if (Ext.isIE){
				myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
				myMask.show();
			}
			document.getElementById('ifExportar').src = contexto + 'Presegmentacion.do?exportarPDF=&segmentacionPresegJSON='+Ext.encode(ultimaBusqueda.get('entradaBusqueda'));
			
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
	
	//Funcion para mostrar la descripcion de la columna Tipo Documento
	var rendererDescrip = function(data, cellmd, record, rowIndex,colIndex, store) {
		
		for (var i=0;i< Ext.get('selTipoDoc').dom.options.length;i++){
			if (Ext.get('selTipoDoc').dom.options[i].value == data){
				return Ext.get('selTipoDoc').dom.options[i].text;
			}
		}
	}
	
	//Funcion que pinta el grid de registros de Presegmentaciones
	var pintarGrid = function (){

		// create the data store
		var store = new Ext.data.Store({
			reader: new Ext.data.ObjectReader({}, [
			                                       
			                                       {name: 'tipoDocumento', type: 'string'},
			                                       {name: 'numDocumento', type: 'string'},     
			                                       {name: 'segmento', type: 'string'},
			                                       {name: 'subSegmento', type: 'string'},
			                                       {name: 'segmentoDes', type: 'string'},
			                                       {name: 'subSegmentoDes', type: 'string'},
			                                       {name: 'ofAtencion', type: 'string'},
			                                       {name: 'tandem', type: 'string'},
			                                       {name: 'codOrigPresegmentacion', type: 'string'},
			                                       {name: 'desOrigPresegmentacion', type: 'string'},
			                                       {name: 'fechaIniVigencia', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'fechaFinVigencia', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'usuarioAlta', type: 'string'},
			                                       {name: 'fechaModif', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'usuarioModif', type: 'string'},
			                                       {name: 'usuarioBaja', type: 'string'},
			                                       {name: 'codMotivoBaja',type:'string'},
			                                       {name: 'desMotivoBaja',type:'string'},
			                                       {name: 'inPtePreseg',type:'string'},
			                                       {name: 'nuSegPsg',type:'string'}
			                                       ])
		});
		var columnModel = new Ext.grid.ColumnModel([
		                                            {header: 'Tipo Doc.',sortable: true,width: 48,  dataIndex: 'tipoDocumento',renderer:rendererDescrip},
		                                            {header: 'N. Docum.',sortable: true,width: 108, dataIndex: 'numDocumento'},
		                                            {header: 'Segmento',sortable: true, width: 56, dataIndex: 'segmento',renderer:addTooltip},
		                                            {header: 'Subseg.',sortable: true, width: 47, dataIndex: 'subSegmento',renderer:addTooltip},
		                                            {header: 'Of. Atenci&oacute;n',sortable: true,width: 67,  dataIndex: 'ofAtencion'},
		                                            {header: 'T&aacute;ndem',sortable: true, width: 47, dataIndex: 'tandem'},
		                                            {header: 'Orig. Preseg.',sortable: true, width: 101, dataIndex: 'desOrigPresegmentacion'},
		                                            {header: 'F. Ini. Vigen.', sortable: true,width: 62, align:'center', dataIndex: 'fechaIniVigencia'},
		                                            {header: 'F. Fin Vigen.', sortable: true,width: 62, align:'center', dataIndex: 'fechaFinVigencia'},
		                                            {header: 'Us. Alta',sortable: true, width: 62, align:'center', dataIndex: 'usuarioAlta'},
		                                            {header: 'Fecha Mod.', sortable: true,width: 62, align:'center', dataIndex: 'fechaModif'},
		                                            {header: 'Us. Modif.',sortable: true, width: 62, align:'center', dataIndex: 'usuarioModif'},
		                                            {header: 'Us. Baja',sortable: true, width: 62, align:'center', dataIndex: 'usuarioBaja',renderer:rendererUsBaja},
		                                            {header: 'Motivo Baja',sortable: true, width: 62, align:'center', dataIndex: 'desMotivoBaja'}
		                                            ]);
		                                            
		var cargarDatosFila = function(fila){
			Ext.each(Ext.query('.marcarFila', grid.id), function(filaMarcada) {
				Ext.fly(filaMarcada).removeClass('marcarFila');
			});
			Ext.fly(grid.getView().getRow(fila)).addClass('marcarFila');
			
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
			filaSeleccionada = grid.getStore().data.items[fila].data;
			
			var dFechaInicio = new Date();
			var dFechaFin = new Date();
			var dFechaMax = new Date();
			dFechaInicio = Date.parseDate(filaSeleccionada.fechaIniVigencia, 'd.m.Y');
			dFechaFin = Date.parseDate(filaSeleccionada.fechaFinVigencia, 'd.m.Y');
			dFechaMax = Date.parseDate("31.12.2500", 'd.m.Y');
			
			//No activamos el boton de Baja ni el de Modificación en los siguiente casos
			if (filaSeleccionada.inPtePreseg == "G"){
				mostrarMensajeInfo("Identificador Pendiente de Actuaci&oacute;n");
				deshabilitarEstadoBotonModif(true);
				deshabilitarEstadoBotonBaja(true);
			}
			else if (dFechaFin < dFechaMax){
				mostrarMensajeInfo("Identificador Inactivo en Presegmentaci&oacute;n");
				deshabilitarEstadoBotonModif(true);		
				deshabilitarEstadoBotonBaja(true);						
			}
			else{ //Si no es ningun caso de los anteriores, se activan los botones Baja y Modificación
				//En IE6 se come la primera funcion se introduce esta linea para que se habiliten los 2 botones
				var pp=null;
				deshabilitarEstadoBotonModif(false);
				deshabilitarEstadoBotonBaja(false);
			}			
			</sec:authorize>
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
			height: 177,
			bbar: new QGPagingToolbarPresegmentacion({
               	pageSize: 100,
               	store: store,
               	displayInfo: false,
               	idGrid: 'gridBuscadorSegmentos'
        	})
		});
		if (Ext.isIE6){
			anchoContenido = document.body.offsetWidth - 40;
			grid.setWidth (anchoContenido);
		}
	}
	
	//Pintar grid Historico de Presegmentaciones
	var pintarGridHistorico = function (){

		// create the data store
		var store = new Ext.data.Store({
			reader: new Ext.data.ObjectReader({}, [
			                                       
			                                       {name: 'tipoDocumento', type: 'string'},
			                                       {name: 'numDocumento', type: 'string'},     
			                                       {name: 'segmento', type: 'string'},
			                                       {name: 'subSegmento', type: 'string'},
			                                       {name: 'segmentoDes', type: 'string'},
			                                       {name: 'subSegmentoDes', type: 'string'},
			                                       {name: 'ofAtencion', type: 'string'},
			                                       {name: 'tandem', type: 'string'},
			                                       {name: 'codOrigPresegmentacion', type: 'string'},
			                                       {name: 'desOrigPresegmentacion', type: 'string'},
			                                       {name: 'fechaIniVigencia', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'fechaFinVigencia', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'usuarioAlta', type: 'string'},
			                                       {name: 'fechaModif', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'usuarioModif', type: 'string'},
			                                       {name: 'usuarioBaja', type: 'string'},
			                                       {name: 'codMotivoBaja',type:'string'},
			                                       {name: 'desMotivoBaja',type:'string'},
			                                       {name: 'inPtePreseg',type:'string'},
			                                       {name: 'nuSegPsg',type:'string'}
			                                       ])
		});
		var columnModel = new Ext.grid.ColumnModel([
		                                            {header: 'Tipo Doc.',sortable: true,width: 48,  dataIndex: 'tipoDocumento',renderer:rendererDescrip},
		                                            {header: 'N. Docum.',sortable: true,width: 108, dataIndex: 'numDocumento'},
		                                            {header: 'Segmento',sortable: true, width: 56, dataIndex: 'segmento',renderer:addTooltip},
		                                            {header: 'Subseg.',sortable: true, width: 47, dataIndex: 'subSegmento',renderer:addTooltip},
		                                            {header: 'Of. Atenci&oacute;n',sortable: true,width: 67,  dataIndex: 'ofAtencion'},
		                                            {header: 'T&aacute;ndem',sortable: true, width: 47, dataIndex: 'tandem'},
		                                            {header: 'Orig. Preseg.',sortable: true, width: 101, dataIndex: 'desOrigPresegmentacion'},
		                                            {header: 'F. Ini. Vigen.', sortable: true,width: 62, align:'center', dataIndex: 'fechaIniVigencia'},
		                                            {header: 'F. Fin Vigen.', sortable: true,width: 62, align:'center', dataIndex: 'fechaFinVigencia'},
		                                            {header: 'Us. Alta',sortable: true, width: 62, align:'center', dataIndex: 'usuarioAlta'},
		                                            {header: 'Fecha Mod.', sortable: true,width: 62, align:'center', dataIndex: 'fechaModif'},
		                                            {header: 'Us. Modif.',sortable: true, width: 62, align:'center', dataIndex: 'usuarioModif'},
		                                            {header: 'Us. Baja',sortable: true, width: 62, align:'center', dataIndex: 'usuarioBaja',renderer:rendererUsBaja},
		                                            {header: 'Motivo Baja',sortable: true, width: 62, align:'center', dataIndex: 'desMotivoBaja'}
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
	        height: 177,
	        autoWidth:true,
			bbar: new QGPagingToolbarPresegmentacionHist({
               	pageSize: 100,
               	store: store,
               	displayInfo: false,
               	idGrid: 'gridHistorico'
        	})
	    });
	    
	}
	
	//En caso de ser fecha de vigencia distinta de 31-12-2500 se pone el usuario de Modificacion en el Usuario de Baja
	var rendererUsBaja = function(data, cellmd, record, rowIndex,colIndex, store) {
		if (record.data.fechaFinVigencia == "31.12.2500"){
			return 	record.data.usuarioBaja;
		}
		else{
			return record.data.usuarioModif;
		}
	}
	
	//Función para mostrar los tooltip de segmento y subsegmento
	function addTooltip(value,metadata, record, rowIndex, colIndex, store){
	   	//En record viene el elemento formado del grid
		var valorTooltip = "";
		//Dependiendo de la columna que estemos renderizando se metera un valor de descripcion u otro
		//Columna de segmento
		if(colIndex == 2){
			valorTooltip = record.data.segmentoDes;
		//Columna de derecho
		}
		else if(colIndex == 3){
	 		valorTooltip = record.data.subSegmentoDes;
	 	}
		//Accedemos al tooltip
		if(valorTooltip != null && valorTooltip != ''){
			metadata.attr = 'ext:qtip="' + valorTooltip +'"';
		}
		//Debe devolver el valor para rellenar la columna
		return value;
     }
	
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
	//Funcion que inicializa todos los campos de la pantalla de Alta al abrirla
	var limpiarPantallaAlta = function(){
		Ext.get('selTipoDocAS').dom.selectedIndex = 0;		
		Ext.get('selOfAtencAS').dom.selectedIndex = 0;
		Ext.get('selTandemAS').dom.selectedIndex = 0;		
		Ext.get('selCodSegAS').dom.selectedIndex = 0;
		Ext.get('selCodSubSegAS').dom.selectedIndex = 0;

		Ext.get('txtDescripTipoDocAS').dom.value='';
		Ext.get('txtDescripcionSegAS').dom.value='';
		Ext.get('txtDescripcionSubSegAS').dom.value='';
		
		Ext.get('selCodSegAS').dom.value = '';
		Ext.get('selTipoDocAS').dom.value = '';
		Ext.get('selCodSubSegAS').dom.value = '';
		Ext.get('selOfAtencAS').dom.value = '';
		Ext.get('selTandemAS').dom.value = '';		
	
		agregarValoresCombo('selTipoDocAS', new Array(), true);
		agregarValoresCombo('selOfAtencAS', new Array(), true);
		agregarValoresCombo('selTandemAS', new Array(), true);
		agregarValoresCombo('selCodSegAS', new Array(), true);
		agregarValoresCombo('selCodSubSegAS', new Array(), true);
		
		//Se habilitan los combos
		Ext.get('selTipoDocAS').dom.disabled = false;
		Ext.get('selCodSegAS').dom.disabled = false;
	}	
	
	//Cargamos el combo meses para la parte de Administracion
	var cargarComboAdmin = function(){
		var arrayCombo = new Array();
		arrayCombo.push({texto: "Nunca", valor: "0"});
		for (var i=1;i<=12;i++){
			arrayCombo.push({texto: i, valor: i});
		}
		agregarValoresCombo('selMeses', arrayCombo, true);
	}
	
	var cargaComboDato = function(valor){
		
		for (var i=0;i< Ext.get('selMeses').dom.options.length;i++){
			if (Ext.get('selMeses').dom.options[i].value == valor){
				Ext.get('selMeses').dom.value = valor;
				Ext.get('selMeses').dom.selectedIndex = i;
			}
		}
		selComboAdmin = Ext.get('selMeses').dom.options[Ext.get('selMeses').dom.selectedIndex].value;
	}
	
	
	//Llamada al servicio de Administraciones
	var callBackCargarDatosAdmin = function(correcto,datosResultado){
		if (correcto){
			cargaComboDato(datosResultado[0].desValorNum);	
		}
	}
	
	var callBackCargarDatosAdminSalida = function(correcto,datosResultado){
		if (correcto){
			mostrarMensajeInfo("La Administraci&oacute;n se ha guardado correctamente");
			Ext.get('btnGuardarAd').dom.disabled = true;
			Ext.get('btnGuardarAd').addClass('btnDis'); 
			Ext.get('btnGuardarAd').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
   		
   			Ext.get('botFiltro').setVisibilityMode(Ext.Element.DISPLAY).show();
			Ext.get('menuAdmin').setVisibilityMode(Ext.Element.DISPLAY).hide();
			Ext.get('titAdmin').applyStyles('color:rgb(0,0,0);');
			
			//Habilitamos el resto de elementos
			
			habilitarGrid('gridBuscadorSegmentos');
			
			habilitarElementos();
		}
	}
	
	var cargarDatosAdmin = function(entrada){
		if (entrada.inActuacion == "C")
			llamadaAjax('Presegmentacion.do','cargarDatosAdmin','segmentacionPresegJSON',entrada,callBackCargarDatosAdmin,false);
		else if (entrada.inActuacion == "M")
			llamadaAjax('Presegmentacion.do','cargarDatosAdmin','segmentacionPresegJSON',entrada,callBackCargarDatosAdminSalida,false);
	}
	</sec:authorize>
	
	// Controlamos los eventos sobre los botones
	var controlEventos = function() {
		
		window.onresize = function() {
			refrescarGrid.defer(20);
		}

		//evento click del botón Exportar Excel
		Ext.get('btnExportar').on('click', function() {
			exportarExcel();
		});
		
		//evento click del botón Imprimir
		//Ext.get('btnImprimir').on('click', function() {
			//imprimir();
		//});
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		//evento click del boton Eliminar
		Ext.get('btnEliminar').on('click', function() {
			eliminar();
		});

		//evento click del boton Eliminar
		Ext.get('btnModificar').on('click', function() {
			abrirPopUpGestionSegmento("A&ntilde;adir/Modificar Presegmentaci&oacute;n");
			
			regSeleccionado = {};
			
			regSeleccionado.segmento = filaSeleccionada.segmento;
			regSeleccionado.segmentoDes = filaSeleccionada.segmentoDes;
			regSeleccionado.subSegmento = filaSeleccionada.subSegmento;
			regSeleccionado.subSegmentoDes = filaSeleccionada.subSegmentoDes;
			
			regSeleccionado.numDocumento = filaSeleccionada.numDocumento;
			regSeleccionado.tipoDocumento = filaSeleccionada.tipoDocumento;
			
			regSeleccionado.tandem = filaSeleccionada.tandem;
			regSeleccionado.ofAtencion = filaSeleccionada.ofAtencion;
			
			limpiarPantallaAlta();
			//Despues de limpiar la pantalla comprobamos el estado del boton
			//guardar que siempre estara deshabilitado
			
			pulsado = true;

			modificarReg = true;

			cargarCodigoSegmento("01","modif");

			CGLOBALGESTPRESEG.controlarBotonGuardar();		
		});
		</sec:authorize>
		
		//carga el campo de texto con la descripcion del segmento seleccionado, activa "Of. Atención" si es distinto de TODOS
		Ext.get('selCodSeg').on('change', function(){			 
			Ext.get('txtDescripcionSeg').dom.value = Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].value;

			//Cargamos el subsegmento
					
			cargarSubsegmento(Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].text);
			
			Ext.get('selOfAtenc').dom.disabled=true;
			Ext.get('selOfAtenc').dom.selectedIndex = 0;
			
			Ext.get('selTandem').dom.disabled=true;
			Ext.get('selTandem').dom.selectedIndex = 0;
			
			var arrayCombo = new Array();
		 	arrayCombo.push({texto: "Todos", valor: ""});
		 	
			agregarValoresCombo('selOfAtenc', arrayCombo, true);
			agregarValoresCombo('selTandem', arrayCombo, true);
			
			if (Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].value != ""){					 
				Ext.get('selOfAtenc').dom.disabled=false;
				cargarOfAtencion(Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].text,
								 cargarSubsegmento(Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].text));
			}
		});
		
		//al seleccionar un elemento en "Of.Atención", se activa "Tandem" si es distinto de TODOS
		Ext.get('selOfAtenc').on('change', function(){
			if (Ext.get('selOfAtenc').dom.options[Ext.get('selOfAtenc').dom.selectedIndex].value == ""){			 
				Ext.get('selTandem').dom.disabled=true;
				Ext.get('selTandem').dom.selectedIndex = 0;	
			}
			else{
				Ext.get('selTandem').dom.disabled=false;
				cargarTandem(Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].text,
							 cargarSubsegmento(Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].text),
							 Ext.get('selOfAtenc').dom.options[Ext.get('selOfAtenc').dom.selectedIndex].text);
			}
		});
			
		//evento click del boton Filtrar
		Ext.get('btnFiltrar').on('click', function() {
			
			ultimaBusquedaTipo = "filtrado";
			
			hayPaginacion = "";
			posicionPaginacion = null;
			nejecucion = 0;	
			
			var obtener = true;
			
			if (Ext.get('selTipoDoc').dom.options[Ext.get('selTipoDoc').dom.selectedIndex].value != "" && 
				Ext.get('txtDescripTipoDoc').dom.value == ""){
				mostrarMensajeError("Si est&aacute; seleccionado el Tipo de documento es necesario incluir el n&uacute;mero");
				obtener = false;
			}
			if (cogerValorCheck('chkAct') == "N" && cogerValorCheck('chkIna') == "N"){
				mostrarMensajeError("Debe seleccionar registros activos, inactivos o ambos");
				obtener = false;
			}
			if (obtener){
				//Llamada al método para cargar la tabla según el critero elegido
				obtenerDatos(crearEntradaBusqueda(nejecucion,posicionPaginacion));
			}
		});
		
		//evento click del boton Filtrar
		Ext.get('btnVerTodos').on('click', function() {
		
			ultimaBusquedaTipo = "todos";
			
			hayPaginacion = "";
			posicionPaginacion = null;
			nejecucion = 0;	
	
			var entrada = {
				codActuacion: "C",
				tipoDocumento: "",
				numDocumento: "",
				segmento: "",
				subSegmento: "",
				ofAtencion: "",
				tandem: "",
				pgnTx: hayPaginacion,
				nejecucion: nejecucion,
				tipoConsulta: "01",
				inVigencia: "T"	
			};
			//Llamada al método para cargar la tabla historico
			obtenerDatos(entrada);
		});
		
		// eventos para el cuadro de Administracion
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		Ext.get('btnAdmin').on('click', function() {			
			Ext.get('botFiltro').setVisibilityMode(Ext.Element.DISPLAY).hide();
			Ext.get('menuAdmin').setVisibilityMode(Ext.Element.DISPLAY).show();
			Ext.get('titAdmin').applyStyles('color:rgb(255,255,255);');
			
			//Ihabilitamos el resto de elementos
			desHabilitarElementos();
			
			//Carga de elementos del combo
			cargarComboAdmin();
			
			var entrada = {
				inActuacion: "C",
				codParametro: "012",
				desParametro: "PRSGHD"
			};
			
			cargarDatosAdmin(entrada);
		});
		
		Ext.get('btnGuardarAd').on('click', function() {
			var entrada = {
				inActuacion: "M",
				codParametro: "012",
				desParametro: "PRSGHD",
				valorNum: Ext.get('selMeses').dom.options[Ext.get('selMeses').dom.selectedIndex].value,
				tipoValor: "N"
			};
			
			cargarDatosAdmin(entrada);
		});

		Ext.get('selMeses').on('change', function(){
			if (selComboAdmin != Ext.get('selMeses').dom.options[Ext.get('selMeses').dom.selectedIndex].value){
				Ext.get('btnGuardarAd').dom.disabled = false;
				Ext.get('btnGuardarAd').removeClass('btnDis'); 
				Ext.get('btnGuardarAd').dom.src = contexto + 'images/botones/QGbtGuardar.gif';
			}
			else{
				Ext.get('btnGuardarAd').dom.disabled = true;
				Ext.get('btnGuardarAd').addClass('btnDis'); 
				Ext.get('btnGuardarAd').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
			}
		});
		
		Ext.get('btnCancelarAd').on('click', function() {
			
			if (selComboAdmin != Ext.get('selMeses').dom.options[Ext.get('selMeses').dom.selectedIndex].value){
				Ext.Msg.show({
				   title:'Los datos se perder&aacute;n',
				   msg: '<span>Se van a perder los datos introducidos, &iquest;Desea continuar?</span>',
				   buttons: Ext.Msg.YESNO,
				   icon: Ext.MessageBox.WARNING,
				   fn:function(respuesta){
				   		if (respuesta == 'yes'){
				   		
				   			Ext.get('btnGuardarAd').dom.disabled = true;
							Ext.get('btnGuardarAd').addClass('btnDis'); 
							Ext.get('btnGuardarAd').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
				   		
				   			Ext.get('botFiltro').setVisibilityMode(Ext.Element.DISPLAY).show();
							Ext.get('menuAdmin').setVisibilityMode(Ext.Element.DISPLAY).hide();
							Ext.get('titAdmin').applyStyles('color:rgb(0,0,0);');
							
							//Habilitamos el resto de elementos
							
							habilitarGrid('gridBuscadorSegmentos');
							
							habilitarElementos();
				   		}else{
				   		}
				   }
				});
			}
			else{
				
				Ext.get('btnGuardarAd').dom.disabled = true;
				Ext.get('btnGuardarAd').addClass('btnDis'); 
				Ext.get('btnGuardarAd').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
				
				habilitarGrid('gridBuscadorSegmentos');
			
				Ext.get('botFiltro').setVisibilityMode(Ext.Element.DISPLAY).show();
				Ext.get('menuAdmin').setVisibilityMode(Ext.Element.DISPLAY).hide();
				Ext.get('titAdmin').applyStyles('color:rgb(0,0,0);');
				
				//Habilitamos el resto de elementos
				habilitarElementos();
			}
			
		});
		
		// boton nuevo
		Ext.get('btnNuevo').on('click', function() {
			abrirPopUpGestionSegmento("A&ntilde;adir/Modificar Presegmentaci&oacute;n");
			
			limpiarPantallaAlta();
			//Despues de limpiar la pantalla comprobamos el estado del boton
			//guardar que siempre estara deshabilitado
			
			pulsado = true;
			cargarTipoDocumento();
			cargarCodigoSegmento("01","alta");

			modificarReg = false;

			CGLOBALGESTPRESEG.controlarBotonGuardar();			
		});
		</sec:authorize>
		
		
		//boton Volver Historico
		Ext.get('btnVolver').on('click', function() {


			Ext.get('divHistorico').setVisibilityMode(Ext.Element.DISPLAY).hide();
			Ext.get('divPrincipal').setVisibilityMode(Ext.Element.DISPLAY).show();
			
			Ext.get('divTituloPrincipal').setVisibilityMode(Ext.Element.DISPLAY).show();
			Ext.get('divTituloHistorico').setVisibilityMode(Ext.Element.DISPLAY).hide();
			
			Ext.getCmp('gridHistorico').hide();
			Ext.getCmp('gridHistorico').destroy();
			
			cargaHistorico = false;
		});	 
		
		//boton Historico
		Ext.get('btnHistorico').on('click', function() {
			
			Ext.get('divHistorico').setVisibilityMode(Ext.Element.DISPLAY).show();
			Ext.get('divPrincipal').setVisibilityMode(Ext.Element.DISPLAY).hide();
			Ext.get('divTituloPrincipal').setVisibilityMode(Ext.Element.DISPLAY).hide();
			Ext.get('divTituloHistorico').setVisibilityMode(Ext.Element.DISPLAY).show();
			
			
			hayPaginacionHist = "";
			posicionPaginacionHist = null;
			nejecucionHist = 0;	
			
			if(!Ext.getCmp('gridHistorico')){
				pintarGridHistorico();
			
				var entrada = {
					codActuacion: "H",
					tipoDocumento: "",
					numDocumento: "",
					segmento: "",
					subSegmento: "",
					ofAtencion: "",
					tandem: "",
					pgnTx: hayPaginacionHist,
					nejecucion: nejecucionHist,
					tipoConsulta: "01",
					inVigencia: "T"	
				};
				//Llamada al método para cargar la tabla historico
				obtenerDatos(entrada);
			
				cargaHistorico = true;
				
			}	
		});

		if (Ext.isIE){
			document.getElementById('ifExportar').onreadystatechange = function(){myMask.hide();};
		}
		
		if (Ext.isIE6){ //Se usa para que en IE6 no se superponga los combos al menu
			Ext.get('liAdministracion').on('mouseover', function() {
				Ext.get('selCodSeg').setVisibilityMode(Ext.Element.DISPLAY).hide();
			});
			Ext.get('liAdministracion').on('mouseout', function() {
				Ext.get('selCodSeg').setVisibilityMode(Ext.Element.DISPLAY).show();
			});
			
			Ext.get('liSegmentacion').on('mouseover', function() {
				Ext.get('selOfAtenc').setVisibilityMode(Ext.Element.DISPLAY).hide();
				Ext.get('selTandem').setVisibilityMode(Ext.Element.DISPLAY).hide();
			});
			Ext.get('liSegmentacion').on('mouseout', function() {
				Ext.get('selOfAtenc').setVisibilityMode(Ext.Element.DISPLAY).show();
				Ext.get('selTandem').setVisibilityMode(Ext.Element.DISPLAY).show();
			});
		}
	}
	

	//Funciones de habilitar/deshabilitar elementos de la pantalla
	var habilitarElementos = function(){
		Ext.get('btnHistorico').dom.disabled = false;
		Ext.get('btnHistorico').removeClass('btnDis'); 
		Ext.get('btnHistorico').dom.src = contexto + 'images/botones/QGbtHistorico.gif';
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		Ext.get('btnNuevo').dom.disabled = false;
		Ext.get('btnNuevo').removeClass('btnDis'); 
		Ext.get('btnNuevo').dom.src = contexto + 'images/botones/QGbtListadoNuevo.gif';
		</sec:authorize>
			
		Ext.get('btnExportar').dom.disabled = false;
		Ext.get('btnExportar').removeClass('btnDis'); 
		Ext.get('btnExportar').dom.src = contexto + 'images/botones/QGbtnExportarExcel.gif';
		
		//Ext.get('btnImprimir').dom.disabled = false;
		//Ext.get('btnImprimir').removeClass('btnDis'); 
		//Ext.get('btnImprimir').dom.src = contexto + 'images/botones/QGbtImprimir.gif';
		
		Ext.get('selTipoDoc').dom.disabled = false;
		Ext.get('txtDescripTipoDoc').dom.disabled = false;
		Ext.get('selCodSeg').dom.disabled = false;
		Ext.get('txtDescripcionSeg').dom.disabled = false;
		
		if (Ext.get('selOfAtenc').dom.options.length > 1){
			Ext.get('selOfAtenc').dom.disabled = false;
		}
		if (Ext.get('selTandem').dom.options.length > 1){
			Ext.get('selTandem').dom.disabled = false;
		}
		
		Ext.get('chkAct').dom.disabled = false;
		Ext.get('chkIna').dom.disabled = false;
	}
	
	var desHabilitarElementos = function(){
	
		Ext.get('btnHistorico').dom.disabled = true;
		Ext.get('btnHistorico').addClass('btnDis'); 
		Ext.get('btnHistorico').dom.src = contexto + 'images/botones/QGbtHistoricoDis.gif';
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		Ext.get('btnNuevo').dom.disabled = true;
		Ext.get('btnNuevo').addClass('btnDis'); 
		Ext.get('btnNuevo').dom.src = contexto + 'images/botones/QGbtListadoNuevo_des.gif';
		</sec:authorize>
			
		Ext.get('btnExportar').dom.disabled = true;
		Ext.get('btnExportar').addClass('btnDis'); 
		Ext.get('btnExportar').dom.src = contexto + 'images/botones/QGbtnExportarExcelDis.gif';
		
		//Ext.get('btnImprimir').dom.disabled = true;
		//Ext.get('btnImprimir').addClass('btnDis'); 
		//Ext.get('btnImprimir').dom.src = contexto + 'images/botones/QGbtImprimirDis.gif';
		
		Ext.get('selTipoDoc').dom.disabled = true;
		Ext.get('txtDescripTipoDoc').dom.disabled = true;
		Ext.get('selCodSeg').dom.disabled = true;
		Ext.get('txtDescripcionSeg').dom.disabled = true;
		
		Ext.get('selOfAtenc').dom.disabled = true;
		Ext.get('selTandem').dom.disabled = true;
		Ext.get('chkAct').dom.disabled = true;
		Ext.get('chkIna').dom.disabled = true;
		
		inhabilitarGrid('gridBuscadorSegmentos');	
	}
	
	//Opciones de Busqueda segun criterios
	var crearEntradaBusqueda = function(nej,pos){
			hayPaginacion = "";
			posicionPaginacion = null;
			nejecucion = 0;	
	
			//Introducimos los valores de entrada al servicio
			var entrada = {
				codActuacion: "C",
				tipoDocumento: Ext.get('selTipoDoc').dom.options[Ext.get('selTipoDoc').dom.selectedIndex].value,
				numDocumento: Ext.get('txtDescripTipoDoc').dom.value,
				segmento: Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].text,
				subSegmento: cargarSubsegmento(Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].text),
				ofAtencion: Ext.get('selOfAtenc').dom.options[Ext.get('selOfAtenc').dom.selectedIndex].text,
				tandem: Ext.get('selTandem').dom.options[Ext.get('selTandem').dom.selectedIndex].text,
				pgnTx: hayPaginacion,
				nejecucion: nejecucion
			};
			
			//Cambiamos los valores si es "Todos" y enviamos "" (vacío)
			if (entrada.segmento == "Todos"){
				entrada.segmento = "";
			}
			if (entrada.ofAtencion == "Todos"){
				entrada.ofAtencion = "";
			}
			if (entrada.tandem == "Todos"){
				entrada.tandem = "";
			}
			
			//Valor para tipoConsulta
			//-----------------------
			if (entrada.tipoDocumento == "" && entrada.numDocumento == "" && 
				entrada.segmento == "" && entrada.ofAtencion == "" && entrada.tandem == ""){
				entrada.tipoConsulta = "01";  //Ninguno
			}
			else if (entrada.tipoDocumento == "" && entrada.numDocumento != "" && 
				entrada.segmento == "" && entrada.ofAtencion == "" && entrada.tandem == ""){
				entrada.tipoConsulta = "02";  //NºDocumento
			}
			else if (entrada.tipoDocumento != "" && entrada.numDocumento != "" && 
				entrada.segmento == "" && entrada.ofAtencion == "" && entrada.tandem == ""){
				entrada.tipoConsulta = "03";  //Tipo y nº documento
			}
			else if (entrada.tipoDocumento == "" && entrada.numDocumento == "" && 
				entrada.segmento != "" && entrada.ofAtencion == "" && entrada.tandem == ""){
				entrada.tipoConsulta = "04";  //Segmento
			}
			else if (entrada.tipoDocumento == "" && entrada.numDocumento != "" && 
				entrada.segmento != "" && entrada.ofAtencion == "" && entrada.tandem == ""){
				entrada.tipoConsulta = "05";  //Nº documento y segmento
			}
			else if (entrada.tipoDocumento != "" && entrada.numDocumento != "" && 
				entrada.segmento != "" && entrada.ofAtencion == "" && entrada.tandem == ""){
				entrada.tipoConsulta = "06";  //Tipo, nº documento y segmento
			}
			else if (entrada.tipoDocumento == "" && entrada.numDocumento == "" && 
				entrada.segmento != "" && entrada.ofAtencion != "" && entrada.tandem == ""){
				entrada.tipoConsulta = "07";  //Segmento y oficina de atencion
			}
			else if (entrada.tipoDocumento == "" && entrada.numDocumento != "" && 
				entrada.segmento != "" && entrada.ofAtencion != "" && entrada.tandem == ""){
				entrada.tipoConsulta = "08";  //Nº documento, segmento y oficina de atencion
			}
			else if (entrada.tipoDocumento != "" && entrada.numDocumento != "" && 
				entrada.segmento != "" && entrada.ofAtencion != "" && entrada.tandem == ""){
				entrada.tipoConsulta = "09";  //Tipo, nº documento, segmento y oficina de atencion
			}
			else if (entrada.tipoDocumento == "" && entrada.numDocumento == "" && 
				entrada.segmento != "" && entrada.ofAtencion != "" && entrada.tandem != ""){
				entrada.tipoConsulta = "10";  //Segmento, oficina de atención y tándem
			}
			else if (entrada.tipoDocumento == "" && entrada.numDocumento != "" && 
				entrada.segmento != "" && entrada.ofAtencion != "" && entrada.tandem != ""){
				entrada.tipoConsulta = "11";  //Nº documento, segmento, oficina de atención y tándem
			}
			else if (entrada.tipoDocumento != "" && entrada.numDocumento != "" && 
				entrada.segmento != "" && entrada.ofAtencion != "" && entrada.tandem != ""){
				entrada.tipoConsulta = "12";  //Tipo, nº documento, segmento, oficina de atención y tándem
			}

			//Valor para inVigencia
			//---------------------
			if (cogerValorCheck('chkAct') == "S" && cogerValorCheck('chkIna') == "N"){
				entrada.inVigencia = "A";
			}
			else if (cogerValorCheck('chkAct') == "N" && cogerValorCheck('chkIna') == "S"){
				entrada.inVigencia = "I";
			}
			else if (cogerValorCheck('chkAct') == "S" && cogerValorCheck('chkIna') == "S"){
				entrada.inVigencia = "T";
			}
			
			
			
		return entrada;
	}
	
	//Refresco del grid
	var refrescarGrid = function() {
		if (Ext.isIE6){
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).hide();
			var ancho = document.body.offsetWidth - 38;
			
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).show();
			grid.setWidth (ancho);
			//Ext.getCmp('gridBuscadorSegmentosHistorico').setWidth (ancho);
		}else{
			grid.getView().refresh(true);
			//Ext.getCmp('gridBuscadorSegmentosHistorico').getView().refresh(true);
		}
	}
	
	
	//Funcion que comprueba el check especificado y retorna el valor asociado
	var cogerValorCheck = function(idCheck) {
		return (Ext.get(idCheck).dom.checked ? "S" : "N");
	}
	
	//Inicializa los campos de busqueda
	var inicializarCampos = function(){	
	
		cargarTipoDocumento();
		cargarCodigoSegmento("01","busqueda");
		
		Ext.get('txtDescripTipoDoc').dom.value = "";
		Ext.get('txtDescripcionSeg').dom.value = "";
		
		Ext.get('selOfAtenc').dom.disabled=true;
		Ext.get('selOfAtenc').dom.selectedIndex = 0;
		
		Ext.get('selTandem').dom.disabled=true;
		Ext.get('selTandem').dom.selectedIndex = 0;	
		
		Ext.get('chkAct').dom.checked = true;
		Ext.get('chkIna').dom.checked = true;
		
		habilitarElementos();
		
		modificarReg = false;
	}

	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
	//Funcion que abre el popUp de la ventana de Alta-Modificacion
	var abrirPopUpGestionSegmento = function(titulo){
		var win = Ext.getCmp('winAnadirSegmentos');
		
		if(!win){
			win = new Ext.Window({
				id:'winAnadirSegmentos',
				contentEl:'popUpAnadirPreSegmentacion',
				title:titulo,
				width:945,
				height:235,
				closeAction:'hide',
				closable: true,
				animateTarget:'btnNuevo',
				resizable:false,
				minimizable:true,
				draggable:false,
				modal:true,
				listeners:{
					minimize : function(){
						minimizarVentana('winAnadirSegmentos');
					},
					beforehide : function(){
						//Lanzamos la misma funcion que al pulsar el boton cancelar.
						return CGLOBALGESTPRESEG.cancelar();
					}
				}
			});
		}
		
		win.show();
		//Maximinizamos la ventana por si fue minimizada al cerrarse
		maximizarVentana('winAnadirSegmentos');
	}
	</sec:authorize>

	return {
		init: function (){
		//Cargamos la mascara para que se carguen los combos		
		Ext.QuickTips.init();
		
		inicializarCampos();
		
		Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		
		controlEventos();
		pintarGrid();
		obtenerUsuario();
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		deshabilitarEstadoBotonModif(true);
		deshabilitarEstadoBotonBaja(true);
		</sec:authorize>
		},
		cargarOfAtencion: function(codSeg,codSub){
			cargarOfAtencion(codSeg,codSub);
		},
		cargarTandem: function(codSeg,codSub,codOfAt){
			cargarTandem(codSeg,codSub,codOfAt);
		},
		obtenerDatos: function (entrada){
			obtenerDatos(entrada);
		},
		operarPreseg: function(entrada){
			operarPreseg(entrada);
		}
	}
}();

function cargarOfAtencion(codSeg,codSub){
	CGLOBAL.cargarOfAtencion(codSeg,codSub);
}

function cargarTandem(codSeg,codSub,codOfAt){
	CGLOBAL.cargarTandem(codSeg,codSub,codOfAt);
}

function obtenerDatos(entrada){
	CGLOBAL.obtenerDatos(entrada);
}

function operarPreseg(entrada){
	CGLOBAL.operarPreseg(entrada);
}

Ext.onReady (CGLOBAL.init, CGLOBAL, true);
</sec:authorize>