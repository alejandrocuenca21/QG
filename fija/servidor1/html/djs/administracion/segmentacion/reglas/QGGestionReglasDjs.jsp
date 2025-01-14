<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
var CGLOBALGESTREGSEG = function() {

	//Variable que sirve de bandera para ocultar automaticamente el popup sin preguntar
	var cerrarVentana = false;
	var bFechaOK = true;
			
	//comprueba que alguno de los campos esta relleno
	var comprobarCamposAltaAlguno = function() {
		var datosMetidos = Ext.get('selCodSegAS').dom.value != ''
				|| Ext.get('txtDescripcionSegAS').dom.value != ''
				|| Ext.get('selCodSubSegAS').dom.value.trim() != ''
				|| Ext.get('txtDescripcionSubSegAS').dom.value.trim() != ''
				|| Ext.get('selCodReglaAS').dom.value != ''
				|| Ext.get('txtDescripcionReglaAS').dom.value.trim() != ''
				|| Ext.get('txtLinFixAS').dom.value != ''
				|| Ext.get('txtLinMovAS').dom.value != ''
				|| Ext.get('txtLinTotAS').dom.value != ''
				|| Ext.get('selDiasAS').dom.value != ''
				|| Ext.get('selTandemAS').dom.value != ''
				|| Ext.get('selOfAtencAS').dom.value != ''
				|| Ext.get('txtIntervaloVigASIni').dom.value != ''
				|| Ext.get('txtIntervaloVigASFin').dom.value != '';

		return datosMetidos;
	}
	
	//comprueba que todos los campos estan rellenos
	var comprobarCamposAltaTodos = function() {
		var datosMetidos = Ext.get('selCodSegAS').dom.value != ''
				&& Ext.get('txtDescripcionSegAS').dom.value != ''
				&& Ext.get('selCodSubSegAS').dom.value.trim() != ''
				&& Ext.get('txtDescripcionSubSegAS').dom.value.trim() != ''
				&& Ext.get('selCodReglaAS').dom.value != ''
				&& Ext.get('txtDescripcionReglaAS').dom.value.trim() != ''
				&& Ext.get('txtIntervaloVigASIni').dom.value.trim() != ''
				&& Ext.get('txtIntervaloVigASFin').dom.value.trim() != '';

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
					filaSeleccionada.subSegmento != Ext.get('selCodSubSegAS').dom.options[Ext.get('selCodSubSegAS').dom.selectedIndex].text ||							
					filaSeleccionada.regla != Ext.get('selCodReglaAS').dom.options[Ext.get('selCodReglaAS').dom.selectedIndex].text ||
					filaSeleccionada.ofAtencion.trim() != Ext.get('selOfAtencAS').dom.options[Ext.get('selOfAtencAS').dom.selectedIndex].text.trim() ||
					filaSeleccionada.NLinFija != Ext.get('txtLinFixAS').dom.value ||
					filaSeleccionada.NLinMov != Ext.get('txtLinMovAS').dom.value ||
					filaSeleccionada.NTotalLin != Ext.get('txtLinTotAS').dom.value ||
					filaSeleccionada.NDias != Ext.get('selDiasAS').dom.options[Ext.get('selDiasAS').dom.selectedIndex].text ||
					filaSeleccionada.tandem.trim() != Ext.get('selTandemAS').dom.options[Ext.get('selTandemAS').dom.selectedIndex].text.trim() ||
					filaSeleccionada.fechaIniVigencia.trim() != Ext.get('txtIntervaloVigASIni').dom.value ||
					filaSeleccionada.fechaFinVigencia.trim() != Ext.get('txtIntervaloVigASFin').dom.value){

					cambiado = true;
				
				}
				
				if(todosRellenos && cambiado && bFechaOK){
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
	
	//Carga el combo de N Lineas Totales (modificacion)
	/*
	var cargaComboTotalesSumaAS = function(suma){
		for (var i=0;i< Ext.get('selLinTotAS').dom.options.length;i++){
			if (Ext.get('selLinTotAS').dom.options[i].text == suma.toString()){
				Ext.get('selLinTotAS').dom.value = suma.toString();
				Ext.get('selLinTotAS').dom.selectedIndex = i;
				break;
			}
		}
	}
	*/
	//Llamada al servicio encargado de guardar las modificaciones
	var callBackGuardarRegla = function (correcto,datosResultado){
		if (correcto){ //Cerramos la ventana, limpiamos el flitro y buscamos todos los elementos
			inicioFiltro();
			Ext.getCmp('winAnadirSegmentos').hide();
			CGLOBAL.inicializarCampos();

	   		var entrada = {
				codActuacion: "C",
				segmento: "",
				subSegmento: "",
				ofAtencion: "",
				tandem: "",
				regla: "",
				NLinFija: "",
				NLinMovil: "",
				NTotalLin: "",
				NDias: "",
				tipoConsulta: "",
				ultimaBusquedaTipo: "todos"
			};
			//Llamada al método para cargar la tabla historico
			CGLOBAL.obtenerDatos(entrada)
		}
	}
	var guardarRegla = function(){			
		
		var entrada = {
			regla: Ext.get('selCodReglaAS').dom.options[Ext.get('selCodReglaAS').dom.selectedIndex].text,
			segmento: Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text,
			subSegmento: Ext.get('selCodSubSegAS').dom.options[Ext.get('selCodSubSegAS').dom.selectedIndex].text,			
			codActuacion: "M",
			segmentoOrigen: filaSeleccionada.segmentoOrigen,
			ofAtencion: Ext.get('selOfAtencAS').dom.options[Ext.get('selOfAtencAS').dom.selectedIndex].text,
			tandem: Ext.get('selTandemAS').dom.options[Ext.get('selTandemAS').dom.selectedIndex].text,
			NLinFija: Ext.get('txtLinFixAS').dom.value,
			NLinMovil: Ext.get('txtLinMovAS').dom.value,
			NTotalLin: Ext.get('txtLinTotAS').dom.value,
			NDias: Ext.get('selDiasAS').dom.options[Ext.get('selDiasAS').dom.selectedIndex].text,
			fechaFinVigencia: Ext.get('txtIntervaloVigASFin').dom.value
		};
						
		llamadaAjax('Reglas.do','operarReglas','segmentacionReglasJSON',entrada,callBackGuardarRegla,false);
	}
	
	// Control de eventos sobre el popUp
	var controlEventos = function() {
	
		// evento click boton Guardar
		Ext.get('btnGuardar').on('click', function() {
			guardarRegla();
		});

		// evento click boton Cancelar
		Ext.get('btnCancelar').on('click', function() {
			Ext.getCmp('winAnadirSegmentos').hide();
		});

		Ext.get('selCodReglaAS').on('change', function(){
			Ext.get('txtDescripcionReglaAS').dom.value = Ext.get('selCodReglaAS').dom.options[Ext.get('selCodReglaAS').dom.selectedIndex].value;
			controlarBotonGuardar();
		});		
		//Si cambia el combo de lineas fijas
		Ext.get('txtLinFixAS').on('change', function(){
		/*	
			if (Ext.get('selLinFixAS').dom.options[Ext.get('selLinFixAS').dom.selectedIndex].text != "" &&
				Ext.get('selLinMovAS').dom.options[Ext.get('selLinMovAS').dom.selectedIndex].text != ""){
				
				var suma = parseInt(Ext.get('selLinFixAS').dom.options[Ext.get('selLinFixAS').dom.selectedIndex].text) +
						   parseInt(Ext.get('selLinMovAS').dom.options[Ext.get('selLinMovAS').dom.selectedIndex].text);
			
				cargaComboTotalesSumaAS(suma);
			}
			*/
			controlarBotonGuardar();
		});	
		
		Ext.get('txtLinMovAS').on('change', function(){
		/*	
			if (Ext.get('selLinFixAS').dom.options[Ext.get('selLinFixAS').dom.selectedIndex].text != "" &&
				Ext.get('selLinMovAS').dom.options[Ext.get('selLinMovAS').dom.selectedIndex].text != ""){
				
				var suma = parseInt(Ext.get('selLinFixAS').dom.options[Ext.get('selLinFixAS').dom.selectedIndex].text) +
						   parseInt(Ext.get('selLinMovAS').dom.options[Ext.get('selLinMovAS').dom.selectedIndex].text);
				
				cargaComboTotalesSumaAS(suma);
			}
			*/
			controlarBotonGuardar();
		});
		
		
		Ext.get('selDiasAS').on('change', function(){	
			controlarBotonGuardar();
		});
		
		Ext.get('txtLinTotAS').on('change', function(){	
			controlarBotonGuardar();
		});
		
		//carga el campo de texto con la descripcion del segmento seleccionado, activa "Subsegmento" si es distinto de ""
		Ext.get('selCodSegAS').on('change', function(){			 
			Ext.get('txtDescripcionSegAS').dom.value = Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].value;
			
			regSeleccionado.segmento = Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text;			
			regSeleccionado.segmentoDes = Ext.get('txtDescripcionSegAS').dom.value;
			regSeleccionado.subSegmento = "";			
			regSeleccionado.subSegmentoDes = "";
			regSeleccionado.ofAtencion = "";
			regSeleccionado.tandem = "";						

			//Cargamos el subsegmento
			if (Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].value == ""){			 
				Ext.get('selCodSubSegAS').dom.disabled=true;
				Ext.get('selCodSubSegAS').dom.selectedIndex = 0;	
			}		
			else
			{		
				Ext.get('selCodSubSegAS').dom.disabled=false;	
				CGLOBAL.cargarSubsegmento(Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text);
			}
			
			controlarBotonGuardar();
		});
		
		//al seleccionar un elemento en "Subsegmento", se activa "Of.Atención"si es distinto de ""
		Ext.get('selCodSubSegAS').on('change', function(){
			Ext.get('txtDescripcionSubSegAS').dom.value = Ext.get('selCodSubSegAS').dom.options[Ext.get('selCodSubSegAS').dom.selectedIndex].value;		
			
			regSeleccionado.segmento = Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text;			
			regSeleccionado.segmentoDes = Ext.get('txtDescripcionSegAS').dom.value;			
			regSeleccionado.subSegmento = Ext.get('selCodSubSegAS').dom.options[Ext.get('selCodSubSegAS').dom.selectedIndex].text;
			regSeleccionado.subSegmentoDes = Ext.get('txtDescripcionSubSegAS').dom.value;
			regSeleccionado.ofAtencion = "";
			regSeleccionado.tandem = "";		
		
			if (Ext.get('selCodSubSegAS').dom.options[Ext.get('selCodSubSegAS').dom.selectedIndex].value == ""){			 
				Ext.get('selOfAtencAS').dom.disabled=true;
				Ext.get('selOfAtencAS').dom.selectedIndex = 0;	
			}
			else{
				Ext.get('selOfAtencAS').dom.disabled=false;
				CGLOBAL.cargarOfAtencion(Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text,
							 			Ext.get('selCodSubSegAS').dom.options[Ext.get('selCodSubSegAS').dom.selectedIndex].text);
			}
			controlarBotonGuardar();
		});		
		
		//al seleccionar un elemento en "Of.Atención", se activa "Tandem" si es distinto de ""
		Ext.get('selOfAtencAS').on('change', function(){
			regSeleccionado.segmento = Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text;			
			regSeleccionado.segmentoDes = Ext.get('txtDescripcionSegAS').dom.value;			
			regSeleccionado.subSegmento = Ext.get('selCodSubSegAS').dom.options[Ext.get('selCodSubSegAS').dom.selectedIndex].text;
			regSeleccionado.subSegmentoDes = Ext.get('txtDescripcionSubSegAS').dom.value;
			regSeleccionado.ofAtencion = Ext.get('selOfAtencAS').dom.options[Ext.get('selOfAtencAS').dom.selectedIndex].value;
			regSeleccionado.tandem = "";		
		
			if (Ext.get('selOfAtencAS').dom.options[Ext.get('selOfAtencAS').dom.selectedIndex].value == ""){			 
				Ext.get('selTandemAS').dom.disabled=true;
				Ext.get('selTandemAS').dom.selectedIndex = 0;	
			}
			else{
				Ext.get('selTandemAS').dom.disabled=false;
				CGLOBAL.cargarTandem(Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text,
									Ext.get('selCodSubSegAS').dom.options[Ext.get('selCodSubSegAS').dom.selectedIndex].text,
									Ext.get('selOfAtencAS').dom.options[Ext.get('selOfAtencAS').dom.selectedIndex].text);
			}
			controlarBotonGuardar();
		});
		
		Ext.get('selTandemAS').on('change', function(){
			regSeleccionado.segmento = Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text;			
			regSeleccionado.segmentoDes = Ext.get('txtDescripcionSegAS').dom.value;			
			regSeleccionado.subSegmento = Ext.get('selCodSubSegAS').dom.options[Ext.get('selCodSubSegAS').dom.selectedIndex].text;
			regSeleccionado.subSegmentoDes = Ext.get('txtDescripcionSubSegAS').dom.value;
			regSeleccionado.ofAtencion = Ext.get('selOfAtencAS').dom.options[Ext.get('selOfAtencAS').dom.selectedIndex].value;
			regSeleccionado.tandem = Ext.get('selTandemAS').dom.options[Ext.get('selTandemAS').dom.selectedIndex].value;		
		
			controlarBotonGuardar();
		});
		
		Ext.get('txtIntervaloVigASFin').on('change', function(){
			//Validamos las fechas
			if(esFechaValida(Ext.get('txtIntervaloVigASFin').dom.value,"fecha de fin de vigencia")){
			
				//Validamos que la fecha final sea superior o igual a la del dia
				var fechaFin = Date.parseDate(Ext.get('txtIntervaloVigASFin').dom.value,'d.m.Y');
				var hoy = new Date();
				if( fechaFin.getTime()< hoy.getTime()){
					Ext.Msg
					.show( {
						title : 'Error',
						cls : 'cgMsgBox',
						msg : '<span>' + 'La fecha final de vigencia debe ser mayor &oacute; igual a la del d&iacute;a' + '</span><br/>',
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.ERROR
					});
					bFechaOK = false;				
				}
				else
					bFechaOK = true;					
			}
			else
				bFechaOK = false;
								
			controlarBotonGuardar();
		});		

		if (Ext.isIE6) {
		}
	}
	
	var inicioFiltro = function (){
	
		//Parte Reglas
		Ext.get('selCodReglaAS').dom.disabled=true;
		Ext.get('selCodReglaAS').dom.selectedIndex = 0;
	
		Ext.get('txtDescripcionReglaAS').dom.value = "";
		/*
		Ext.get('selLinFixAS').dom.disabled=false;
		Ext.get('selLinFixAS').dom.selectedIndex = 0;
		*/
		Ext.get('txtLinFixAS').dom.value='';
		Ext.get('txtLinFixAS').dom.readOnly = false;
		Ext.get('txtLinFixAS').removeClass('dis');		
		/*
		Ext.get('selLinMovAS').dom.disabled=false;
		Ext.get('selLinMovAS').dom.selectedIndex = 0;
		*/
		Ext.get('txtLinMovAS').dom.value='';
		Ext.get('txtLinMovAS').dom.readOnly = false;
		Ext.get('txtLinMovAS').removeClass('dis');
		/*				
		Ext.get('selLinTotAS').dom.disabled=true;
		Ext.get('selLinTotAS').dom.selectedIndex = 0;
		*/
		Ext.get('txtLinTotAS').dom.value='';
		Ext.get('txtLinTotAS').dom.readOnly = false;
		Ext.get('txtLinTotAS').removeClass('dis');
				
		Ext.get('selDiasAS').dom.disabled=false;
		Ext.get('selDiasAS').dom.selectedIndex = 0;
		
		//Parte Segmentacion
		Ext.get('selCodSegAS').dom.disabled=false;
		Ext.get('selCodSegAS').dom.selectedIndex = 0;
		
		Ext.get('selCodSubSegAS').dom.disabled=true;
		Ext.get('selCodSubSegAS').dom.selectedIndex = 0;
		
		Ext.get('txtDescripcionSegAS').dom.value = "";
		Ext.get('txtDescripcionSubSegAS').dom.value = "";
		
		Ext.get('selOfAtencAS').dom.disabled=true;
		Ext.get('selOfAtencAS').dom.selectedIndex = 0;
		
		Ext.get('selTandemAS').dom.disabled=true;
		Ext.get('selTandemAS').dom.selectedIndex = 0;	
		
		Ext.get('txtIntervaloVigASIni').dom.value='';
		Ext.get('txtIntervaloVigASIni').dom.readOnly = true;
		Ext.get('divTxtIntervaloVigASIni').addClass('divCalendarDis');
		
		Ext.get('btnCalendarIntervaloVigASIni').dom.disabled= true;
		Ext.get('btnCalendarIntervaloVigASIni').addClass('icoCalendarDis')		
		
		Ext.get('txtIntervaloVigASFin').dom.value='';
		Ext.get('txtIntervaloVigASFin').dom.readOnly = false;
	}
	
	var marcarRegla = function(valor){
		
		for (var i=0;i< Ext.get('selCodReglaAS').dom.options.length;i++){
			if (Ext.get('selCodReglaAS').dom.options[i].text == valor){
				Ext.get('selCodReglaAS').dom.value = valor;
				Ext.get('selCodReglaAS').dom.selectedIndex = i;
				break;
			}
		}
		
		Ext.get('txtDescripcionReglaAS').dom.value =  Ext.get('selCodReglaAS').dom.options[Ext.get('selCodReglaAS').dom.selectedIndex].value;
		
		if (valor == "SBF")
		{
			Ext.get('txtLinMovAS').dom.readOnly = true;
			Ext.get('txtLinMovAS').addClass('dis');		
		}
		else if (valor == "SBM")
		{
			Ext.get('txtLinFixAS').dom.readOnly = true;
			Ext.get('txtLinFixAS').addClass('dis');		
		}
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
	
	var marcarSubsegmento = function(valor){
		
		for (var i=0;i< Ext.get('selCodSubSegAS').dom.options.length;i++){
			if (Ext.get('selCodSubSegAS').dom.options[i].text == valor){
				Ext.get('selCodSubSegAS').dom.value = valor;
				Ext.get('selCodSubSegAS').dom.selectedIndex = i;
				break;
			}
		}
		
		Ext.get('txtDescripcionSubSegAS').dom.value =  Ext.get('selCodSubSegAS').dom.options[Ext.get('selCodSubSegAS').dom.selectedIndex].value;
		
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
	
	var marcarLineasFix = function(valor){
		/*
		for (var i=0;i< Ext.get('selLinFixAS').dom.options.length;i++){
			if (Ext.get('selLinFixAS').dom.options[i].text == valor){
				Ext.get('selLinFixAS').dom.value = valor;
				Ext.get('selLinFixAS').dom.selectedIndex = i;
				break;
			}
		}
		*/
		Ext.get('txtLinFixAS').dom.value = valor;		
	}
	
	var marcarLineasMov = function(valor){
		/*
		for (var i=0;i< Ext.get('selLinMovAS').dom.options.length;i++){
			if (Ext.get('selLinMovAS').dom.options[i].text == valor){
				Ext.get('selLinMovAS').dom.value = valor;
				Ext.get('selLinMovAS').dom.selectedIndex = i;
				break;
			}
		}
		*/
		Ext.get('txtLinMovAS').dom.value = valor;	
	}
	
	var marcarLineasTot = function(valor){
		/*
		for (var i=0;i< Ext.get('selLinTotAS').dom.options.length;i++){
			if (Ext.get('selLinTotAS').dom.options[i].text == valor){
				Ext.get('selLinTotAS').dom.value = valor;
				Ext.get('selLinTotAS').dom.selectedIndex = i;
				break;
			}
		}
		*/
		Ext.get('txtLinTotAS').dom.value = valor;
	}
	
	var marcarDias = function(valor){
		
		for (var i=0;i< Ext.get('selDiasAS').dom.options.length;i++){
			if (Ext.get('selDiasAS').dom.options[i].text == valor){
				Ext.get('selDiasAS').dom.value = valor;
				Ext.get('selDiasAS').dom.selectedIndex = i;
				break;
			}
		}
	}
	
	var marcarFechaInicio = function(valor){
		Ext.get('txtIntervaloVigASIni').dom.value = valor;
	}
	
	var marcarFechaFin = function(valor){
		Ext.get('txtIntervaloVigASFin').dom.value = valor;
	}		
	
	var iniciarCalendarios = function() {
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AS">	
		//obtenemos la fecha de hoy
		var hoy = new Date();
		
		Calendar.setup( {
			inputField : "txtIntervaloVigASFin",
			dateFormat : "%d.%m.%Y",
			trigger : "btnCalendarIntervaloVigASFin",
			bottomBar : false,
			align : 'Br///B/r',
			min: hoy,
			onSelect : function() {
				this.hide();
				// Al cambiar mediante el calendar la fecha tambien debemos
			// controlar el estado del boton guardar
			controlarBotonGuardar();
		}
		});
		</sec:authorize>
	}		
	
	var iniciarCombos = function(pantalla){
		CGLOBAL.cargarCodigoSegmento("01",pantalla);
		CGLOBAL.cargarReglas();
		CGLOBAL.cargarLineasFix();
		CGLOBAL.cargarLineasMov();
		CGLOBAL.cargarTotalLin();
		CGLOBAL.cargarDias();
		CGLOBAL.cargarFechas();
	}	

	return {
		init : function() {
			Ext.QuickTips.init();
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			iniciarCalendarios();
			controlarBotonGuardar();
			controlEventos();	
		},
		controlarBotonGuardar : function() {
			controlarBotonGuardar();
		},
		inicioFiltro: function(){
			inicioFiltro();
		},
		iniciarCombos: function(pantalla){
			iniciarCombos(pantalla);
		},
		cancelar : function(){
			return cancelar();
		},
		marcarSegmento : function(valor){
			return marcarSegmento(valor);
		},
		marcarSubsegmento : function(valor){
			return marcarSubsegmento(valor);
		},		
		marcarOfAtencion : function(valor){
			return marcarOfAtencion(valor);
		},
		marcarTandem : function(valor){
			return marcarTandem(valor);
		},
		marcarLineasFix : function(valor){
			return marcarLineasFix(valor);
		},
		marcarLineasMov : function(valor){
			return marcarLineasMov(valor);
		},
		marcarLineasTot : function(valor){
			return marcarLineasTot(valor);
		},
		marcarDias : function(valor){
			return marcarDias(valor);
		},
		marcarRegla : function(valor){
			return marcarRegla(valor);
		},
		marcarFechaInicio : function(valor){
			return marcarFechaInicio(valor);
		},
		marcarFechaFin : function(valor){
			return marcarFechaFin(valor);
		}				
	}
}();

function inicioFiltro(){
	return CGLOBALGESTREGSEG.inicioFiltro();
}

function iniciarCombos(pantalla){
	CGLOBALGESTREGSEG.iniciarCombos(pantalla);
}

function cancelar(){
	return CGLOBALGESTREGSEG.cancelar();
}

function marcarSegmento(valor){
	return CGLOBALGESTREGSEG.marcarSegmento(valor);
}

function marcarSubsegmento(valor){
	return CGLOBALGESTREGSEG.marcarSubsegmento(valor);
}

function marcarOfAtencion(valor){
	return CGLOBALGESTREGSEG.marcarOfAtencion(valor);
}

function marcarTandem(valor){
	return CGLOBALGESTREGSEG.marcarTandem(valor);
}

function marcarLineasFix(valor){
	return CGLOBALGESTREGSEG.marcarLineasFix(valor);
}

function marcarLineasMov(valor){
	return CGLOBALGESTREGSEG.marcarLineasMov(valor);
}

function marcarLineasTot(valor){
	return CGLOBALGESTREGSEG.marcarLineasTot(valor);
}

function marcarDias(valor){
	return CGLOBALGESTREGSEG.marcarDias(valor);
}

function marcarRegla(valor){
	return CGLOBALGESTREGSEG.marcarRegla(valor);
}

function marcarFechaInicio(valor){
	return CGLOBALGESTREGSEG.marcarFechaInicio(valor);
}

function marcarFechaFin(valor){
	return CGLOBALGESTREGSEG.marcarFechaFin(valor);
}

function controlarBotonGuardar() {
	return CGLOBALGESTREGSEG.controlarBotonGuardar();
}
Ext.onReady(CGLOBALGESTREGSEG.init, CGLOBALGESTREGSEG, true);
</sec:authorize>