<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
var CGLOBALGESTPRESEG = function() {

	//Variable que sirve de bandera para ocultar automaticamente el popup sin preguntar
	cerrarVentana = false;
	
	
	//boton Guardar
	var guardarPresegmentacion = function() {
		var entrada = {
			tipoDocumento: Ext.get('selTipoDocAS').dom.options[Ext.get('selTipoDocAS').dom.selectedIndex].value,
			numDocumento: Ext.get('txtDescripTipoDocAS').dom.value,
			segmento:  Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text,
			subSegmento:  Ext.get('selCodSubSegAS').dom.options[Ext.get('selCodSubSegAS').dom.selectedIndex].text
		};
		if(ventanaActual == "alta"){
			entrada.nuSegPsg = "";
			entrada.codActuacion = "A";
		}else{
			entrada.nuSegPsg = filaSeleccionada.nuSegPsg;
			entrada.codActuacion = "M";
		}
		
		if (Ext.get('selOfAtencAS').dom.value != ""){
			entrada.ofAtencion = Ext.get('selOfAtencAS').dom.options[Ext.get('selOfAtencAS').dom.selectedIndex].text;
			
		}
		if (Ext.get('selTandemAS').dom.value != ""){
			entrada.tandem = Ext.get('selTandemAS').dom.options[Ext.get('selTandemAS').dom.selectedIndex].text;
		}
		
		CGLOBAL.operarPreseg(entrada);
	}
		
	//comprueba que alguno de los campos esta relleno
	var comprobarCamposAltaAlguno = function() {
		var datosMetidos = Ext.get('selCodSegAS').dom.value != ''
				|| Ext.get('txtDescripcionSegAS').dom.value != ''
				|| Ext.get('selCodSubSegAS').dom.value.trim() != ''
				|| Ext.get('txtDescripcionSubSegAS').dom.value.trim() != ''
				|| Ext.get('txtDescripTipoDocAS').dom.value != '';

		return datosMetidos;
	}
	
	//comprueba que todos los campos estan rellenos
	var comprobarCamposAltaTodos = function() {
		var datosMetidos = Ext.get('selCodSegAS').dom.value != ''
				&& Ext.get('txtDescripcionSegAS').dom.value != ''
				&& Ext.get('selCodSubSegAS').dom.value.trim() != ''
				&& Ext.get('txtDescripcionSubSegAS').dom.value.trim() != ''
				&& Ext.get('txtDescripTipoDocAS').dom.value != '';

		return datosMetidos;
	}

	//controla que el boton guardar esta deshabilitado hasta que se rellenen los campos	
	var controlarBotonGuardar = function() {
		
		var todosRellenos = comprobarCamposAltaTodos();
		var habilitar = false;
		if(ventanaActual == "alta"){
			habilitar = todosRellenos;
		}else{
			//Si estan todos rellenos vemos si ademas han cambiado
			if(todosRellenos){
			//Si es una modificacion hay que ver que ademas hayan cambiado los valores del intervalo
				var cambiado = false;
				//Recuperamos los valores de las fechas
				//editables

				if (filaSeleccionada != null &&
					filaSeleccionada.segmento != Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text ||
					filaSeleccionada.tipoDocumento != Ext.get('selTipoDocAS').dom.options[Ext.get('selTipoDocAS').dom.selectedIndex].value ||					
					filaSeleccionada.numDocumento != Ext.get('txtDescripTipoDocAS').dom.value ||
					filaSeleccionada.ofAtencion != Ext.get('selOfAtencAS').dom.options[Ext.get('selOfAtencAS').dom.selectedIndex].text ||
					filaSeleccionada.tandem != Ext.get('selTandemAS').dom.options[Ext.get('selTandemAS').dom.selectedIndex].text){

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
		//Si esta a true comprobamos todo
		if(!cerrarVentana){
			cerrarVentana = true;
			if (comprobarCamposAltaAlguno()) {
				
				Ext.Msg
						.show( {
							msg : 'Se van a perder los datos introducidos,&iquest;Desea continuar?',
							title : 'Aviso',
							buttons : Ext.Msg.YESNO,
							icon : Ext.Msg.INFO,
							minWidth : 400,
							fn : function(buttonId) {
								
								if (buttonId == 'yes') {
									cerrarVentana = true;
									regSeleccionado = null;
									Ext.getCmp('winAnadirSegmentos').hide();
								}else{
									//Inicializamos el valor de nuevo
									cerrarVentana = false;
								}
							}
						});
				return false;
				
			} else {
				cerrarVentana = false;
				regSeleccionado = null;
				return true;
			}
		}else{
			cerrarVentana = false;
			regSeleccionado = null;
			return true;
		}
	}

	var limpiarCombos = function(){	
		agregarValoresCombo('selCodSegAS', new Array(), true);
		agregarValoresCombo('selCodSubSegAS', new Array(), true);
		agregarValoresCombo('selTipoDocAS', new Array(), true);
		agregarValoresCombo('selOfAtencAS', new Array(), true);
		agregarValoresCombo('selTandemAS', new Array(), true);
		Ext.get('txtDescripcionSegAS').dom.value='';
		Ext.get('txtDescripcionSubSegAS').dom.value='';
		Ext.get('txtDescripTipoDocAS').dom.value='';
	}
	
	//Cargar subsegmento
	var cargarSubsegmentoAS = function(codSeg){
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

	// Control de eventos sobre el popUp
	var controlEventos = function() {
	
		// evento click boton Guardar
		Ext.get('btnGuardar').on('click', function() {
			guardarPresegmentacion();
		});

		// evento click boton Cancelar
		Ext.get('btnCancelar').on('click', function() {
			Ext.getCmp('winAnadirSegmentos').hide();
		});

		//combo codigo de segmento
		Ext.get('selCodSegAS').on('change',	function() {
			
			if (modificarReg == true)
				regSeleccionado.segmento = Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text;
			
			if (Ext.get('selCodSegAS').dom.selectedIndex != 0){ //Si no es vacio, carga el resto de combos segun los valores de entrada
				var codSeg = Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text;
				
				Ext.get('txtDescripcionSegAS').dom.value = Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].value;
				
				var codSub = cargarSubsegmentoAS(codSeg);
				
				if (modificarReg == true)
					regSeleccionado.subSegmento = codSub;
				
				cargarSubSegmento(codSeg,codSub);
			}
			else{ //Si es vacio, lo seran el resto de combos y cajas de texto
				Ext.get('txtDescripcionSubSegAS').dom.value='';
				
				Ext.get('selCodSubSegAS').dom.disabled = true;
				Ext.get('selCodSubSegAS').dom.selectedIndex  = 0;
				agregarValoresCombo('selCodSubSegAS', new Array(), true);
				
				Ext.get('selOfAtencAS').dom.disabled = true;
				Ext.get('selOfAtencAS').dom.selectedIndex  = 0;
				agregarValoresCombo('selOfAtencAS', new Array(), true);
				
				Ext.get('selTandemAS').dom.disabled = true;
				Ext.get('selTandemAS').dom.selectedIndex  = 0;
				agregarValoresCombo('selTandemAS', new Array(), true);
			}
			controlarBotonGuardar();
		});
		
		// Combo Codigo Subsegmento
		Ext.get('selCodSubSegAS').on('change', function() {
			controlarBotonGuardar();
		});
		
		// combo OfAtencion
		Ext.get('selOfAtencAS').on('change', function() {
			
			if (modificarReg == true)
				regSeleccionado.ofAtencion = Ext.get('selOfAtencAS').dom.options[Ext.get('selOfAtencAS').dom.selectedIndex].value;
		
			if (Ext.get('selOfAtencAS').dom.selectedIndex != 0){ //Si no es vacio, llamo al servio para recargar el combo de tandem
				Ext.get('selTandemAS').dom.disabled = false;
				
				var codSeg = Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text;
				var codSub = Ext.get('selCodSubSegAS').dom.options[Ext.get('selCodSubSegAS').dom.selectedIndex].text;
				var codOfAt = Ext.get('selOfAtencAS').dom.options[Ext.get('selOfAtencAS').dom.selectedIndex].text;
				
				CGLOBAL.cargarTandem(codSeg,codSub,codOfAt);
			}
			else{ //si es vacio, el combo de tandem tambien lo sera
				Ext.get('selTandemAS').dom.disabled = true;
				Ext.get('selTandemAS').dom.selectedIndex  = 0;
				agregarValoresCombo('selTandemAS', new Array(), true);
			}
		
		
			controlarBotonGuardar();
		});
		
		// combo tipo Documento
		Ext.get('selTipoDocAS').on('change', function() {
			if (modificarReg == true)
				regSeleccionado.tipoDocumento = Ext.get('selTipoDocAS').dom.options[Ext.get('selTipoDocAS').dom.selectedIndex].value;
			
			controlarBotonGuardar();
		});
		
		// combo Tandem
		Ext.get('selTandemAS').on('change', function() {
			if (modificarReg == true)
				regSeleccionado.tandem = Ext.get('selTandemAS').dom.options[Ext.get('selTandemAS').dom.selectedIndex].value;
			controlarBotonGuardar();
		});
		
		//combo numDocumento
		Ext.get('txtDescripTipoDocAS').on('change', function() {
			controlarBotonGuardar();
		});

		if (Ext.isIE6) {
		}
	}
	
	//se carga el combo de segmentos para filtrado y para alta.
	var callBackComboSubSeg = function(correcto,datosResultado){
		if(correcto){
			var arrayCombo = new Array();
			Ext.each(datosResultado,function(dato){
				arrayCombo.push({texto: dato.codigo, valor: dato.descripcion});																
			});
			
			agregarValoresCombo('selCodSubSegAS', arrayCombo, true);
			
			Ext.get('txtDescripcionSubSegAS').dom.value = Ext.get('selCodSubSegAS').dom.options[Ext.get('selCodSubSegAS').dom.selectedIndex].value;
		
			if (modificarReg == true){
				Ext.get('selOfAtencAS').dom.disabled = false;
				CGLOBAL.cargarOfAtencion (regSeleccionado.segmento,cargarSubsegmentoAS(regSeleccionado.segmento));
			}
			else{
				Ext.get('selOfAtencAS').dom.disabled = false;
				CGLOBAL.cargarOfAtencion (Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text,
										  cargarSubsegmentoAS(Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text));
			}
		}
	}

	//Funcion que llama al servicio para cargar la descripcion del subsegmento
	var cargarSubSegmento = function (codSeg,codSub){
	
		var entrada = {
			tipoConsulta: "01",
			segmento: codSeg,
			subSegmento: codSub
		};
		llamadaAjax('Presegmentacion.do','cargarDescripcionSubsegmento','segmentacionPresegJSON',entrada,callBackComboSubSeg,false);	
	}
	
	//Funciones para marcar la opcion en los distintos combos al cargar el registro a modificar
	var marcarTipoDoc = function(valor){
		
		for (var i=0;i< Ext.get('selTipoDocAS').dom.options.length;i++){
			if (Ext.get('selTipoDocAS').dom.options[i].value== valor){
				Ext.get('selTipoDocAS').dom.value = valor;
				Ext.get('selTipoDocAS').dom.selectedIndex = i;
				break;
			}
		}
		Ext.get('txtDescripTipoDocAS').dom.value = regSeleccionado.numDocumento;
	}
	
	var marcarSegmento = function(valor){
		
		for (var i=0;i< Ext.get('selCodSegAS').dom.options.length;i++){
			if (Ext.get('selCodSegAS').dom.options[i].text == valor){
				Ext.get('selCodSegAS').dom.value = valor;
				Ext.get('selCodSegAS').dom.selectedIndex = i;
				break;
			}
		}
		
		Ext.get('txtDescripcionSegAS').dom.value =  Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].value;
		
	}
	
	var marcarOfAtencion = function(valor){
		
		for (var i=0;i< Ext.get('selOfAtencAS').dom.options.length;i++){
			if (Ext.get('selOfAtencAS').dom.options[i].text == valor){
				Ext.get('selOfAtencAS').dom.value = valor;
				Ext.get('selOfAtencAS').dom.selectedIndex = i;
				break;
			}
		}
	}
	
	var marcarTandem = function(valor){
		
		for (var i=0;i< Ext.get('selTandemAS').dom.options.length;i++){
			if (Ext.get('selTandemAS').dom.options[i].text == valor){
				Ext.get('selTandemAS').dom.value = valor;
				Ext.get('selTandemAS').dom.selectedIndex = i;
				break;
			}
		}
	}
	
	//Funcion que llama a carga el correspondiente subsegmento del segmento seleccionado
	var cargarCombos = function(){
		marcarTipoDoc(regSeleccionado.tipoDocumento);
		marcarSegmento(regSeleccionado.segmento);
	
		cargarSubSegmento(regSeleccionado.segmento,cargarSubsegmentoAS(regSeleccionado.segmento));
	}

	return {
		init : function() {
			Ext.QuickTips.init();
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			controlarBotonGuardar();
			controlEventos();			
		},
		controlarBotonGuardar : function() {
			controlarBotonGuardar();
		},
		cargarPantallaAlta : function(ultimaBusqueda, ultimaBusquedaTipo) {
			cargarPantallaAlta(ultimaBusqueda, ultimaBusquedaTipo);
		},
		cancelar : function(){
			return cancelar();
		},
		marcarTipoDoc : function(valor){
			return marcarTipoDoc(valor);
		},
		marcarSegmento : function(valor){
			return marcarSegmento(valor);
		},
		marcarOfAtencion : function(valor){
			return marcarOfAtencion(valor);
		},
		marcarTandem : function(valor){
			return marcarTandem(valor);
		},
		cargarCombos : function(){
			return cargarCombos();
		}
	}
	
}();

function controlarBotonGuardar() {
	return CGLOBALGESTPRESEG.controlarBotonGuardar();
}
//Funcion que carga la pantalla de alta con los valores prefijados en la pantalla de busqueda
function cargarPantallaAlta(ultimaBusqueda, ultimaBusquedaTipo) {
	return CGLOBALGESTPRESEG.cargarPantallaAlta(ultimaBusqueda, ultimaBusquedaTipo);
}
function cancelar(){
	return CGLOBALGESTPRESEG.cancelar();
}
function marcarTipoDoc(valor){
	return CGLOBALGESTPRESEG.marcarTipoDoc(valor);
}
function marcarSegmento(valor){
	return CGLOBALGESTPRESEG.marcarSegmento(valor);
}
function marcarOfAtencion(valor){
	return CGLOBALGESTPRESEG.marcarOfAtencion(valor);
}
function marcarTandem(valor){
	return CGLOBALGESTPRESEG.marcarTandem(valor);
}
function cargarCombos(){
	return CGLOBALGESTPRESEG.cargarCombos();
}

Ext.onReady(CGLOBALGESTPRESEG.init, CGLOBALGESTPRESEG, true);
</sec:authorize>