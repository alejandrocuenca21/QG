<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
var CGLOBALGESTTRA = function() {
	//Variable que indicara en todo momento si esta haciendo una baja (true) o una modificacion (false) para cambiar el mensaje
	var baja;
	//Variable que sirve de bandera para ocultar automaticamente el popup sin preguntar
	var cerrarVentana = false;
	//Bandera que indica si se esta dando de alta un nuevo elemento o dando de baja uno existente
	var esAlta = false;
	/**
	 * Funcion que vuelca los datos al popup
	 * 
	 * @return
	 */
	var volcarDatosPopup = function(seleccionado) {
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		esAlta = false;
		
		//Mostramos 
		
		Ext.get('txtPlanCuentas').dom.value = seleccionado.planCuentas;
		
		Ext.get('txtIntervaloVigIni').dom.value = seleccionado.fechaIniVigencia;
		Ext.get('txtIntervaloVigIni').dom.readOnly = true;
		
		//Ext.get('divTxtIntervaloVigIni').addClass('dis');
		Ext.get('divTxtIntervaloVigIni').addClass('divCalendarDis');
		
		Ext.get('btnCalendarIntervaloVigIni').dom.disabled= true;
		Ext.get('btnCalendarIntervaloVigIni').addClass('icoCalendarDis')
		Ext.get('txtIntervaloVigFin').dom.value = seleccionado.fechaFinVigencia;
		Ext.get('txtIntervaloVigFinBaja').dom.value = '';

		//El combo se rellena con un unico elemento igual al valor del elemento
		var arrayCombo = new Array();
		arrayCombo.push({texto: seleccionado.codigoSegmentoFijo, valor: seleccionado.codigoSegmentoFijo});
		agregarValoresCombo('selCodSegFijoPop', arrayCombo, true);
		Ext.get('selCodSegFijoPop').dom.disabled = true;
		Ext.get('selCodSegFijoPop').addClass('dis');

		arrayCombo = new Array();
		arrayCombo.push({texto: seleccionado.codigoSubSegmentoFijo, valor: seleccionado.codigoSubSegmentoFijo});
		agregarValoresCombo('selCodSubSegFijoPop', arrayCombo, true);
		Ext.get('selCodSubSegFijoPop').dom.disabled = true;
		Ext.get('selCodSubSegFijoPop').addClass('dis');

		arrayCombo = new Array();
		arrayCombo.push({texto: seleccionado.codigoSegmentoMovil, valor: seleccionado.codigoSegmentoMovil});
		agregarValoresCombo('selCodSegMovilPop', arrayCombo, true);
		Ext.get('selCodSegMovilPop').dom.disabled = true;
		Ext.get('selCodSegMovilPop').addClass('dis');

		arrayCombo = new Array();
		arrayCombo.push({texto: seleccionado.codigoSubSegmentoMovil, valor: seleccionado.codigoSubSegmentoMovil});
		agregarValoresCombo('selCodSubSegMovilPop', arrayCombo, true);
		Ext.get('selCodSubSegMovilPop').dom.disabled = true;
		Ext.get('selCodSubSegMovilPop').addClass('dis');

		Ext.get('txtDescSegFijoPop').dom.value = seleccionado.descSegmentoFijo;
		Ext.get('txtDescSubSegFijoPop').dom.value = seleccionado.descSubSegmentoFijo;
		Ext.get('txtDescSegMovilPop').dom.value = seleccionado.descSegmentoMovil;
		Ext.get('txtDescSubSegMovilPop').dom.value = seleccionado.descSubSegmentoMovil;

		//Mostramos el campo de fecha de fin para la baja y ocultamos el del alta
		Ext.get('divTxtIntervaloVigFin').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divTxtIntervaloVigFinBaja').setVisibilityMode(Ext.Element.DISPLAY).show();
		</sec:authorize>
	}
	/**
	 * Da de baja la traduccion cargada
	 * @return
	 */
	var bajaTraduccion = function(){
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		var datosTraduccionBaja = {
				codigoSegmentoFijo:Ext.get('selCodSegFijoPop').dom.options[Ext.get('selCodSegFijoPop').dom.selectedIndex].innerHTML,
				codigoSubSegmentoFijo:Ext.get('selCodSubSegFijoPop').dom.options[Ext.get('selCodSubSegFijoPop').dom.selectedIndex].innerHTML,
				codigoSegmentoMovil:Ext.get('selCodSegMovilPop').dom.options[Ext.get('selCodSegMovilPop').dom.selectedIndex].innerHTML,
				codigoSubSegmentoMovil:Ext.get('selCodSubSegMovilPop').dom.options[Ext.get('selCodSubSegMovilPop').dom.selectedIndex].innerHTML,
				fechaIniVigencia:Ext.get('txtIntervaloVigIni').dom.value,
				planCuentas:Ext.get('txtPlanCuentas').dom.value,
				fechaFinVigencia:Ext.get('txtIntervaloVigFin').dom.value,
				fechaFinVigenciaBaja:Ext.get('txtIntervaloVigFinBaja').dom.value,
				usuarioMod:Ext.get('hiddenUsuarioConectado').dom.value
				// La fecha de alta la asignaremos en el servicio
			}
		
		// Si la fecha no viene informada o en caso de venir cumple los requisitos...
		if(datosTraduccionBaja.fechaFinVigenciaBaja == null || 
				datosTraduccionBaja.fechaFinVigenciaBaja == '' || 
				esFechaValida(datosTraduccionBaja.fechaFinVigenciaBaja,"fecha de fin de vigencia nueva")){
		
			// Validamos que la fecha final NUEVA sea superior a la fecha inicial y al dia de hoy en caso que vengan informadas
			var fechaIni = Date.parseDate(datosTraduccionBaja.fechaIniVigencia,'d.m.Y');
			var fechaFinBaja = Date.parseDate(datosTraduccionBaja.fechaFinVigenciaBaja,'d.m.Y');
			var fechaFin = Date.parseDate(datosTraduccionBaja.fechaFinVigencia,'d.m.Y');
			
			if(fechaFinBaja != null && fechaFinBaja != '' && fechaFinBaja.clearTime().getTime()<fechaHoy() ){
				mostrarMensajeError("La fecha de fin de vigencia nueva no puede ser menor a la del d&iacute;a de hoy");
			}else if(fechaFinBaja != null && fechaFinBaja != '' && fechaIni.clearTime().getTime() > fechaFinBaja.clearTime().getTime()){
				mostrarMensajeError("La fecha de inicio debe ser anterior a la de fin");
			}else if(fechaFinBaja != null && fechaFinBaja != '' && fechaFinBaja.clearTime().getTime() == fechaFin.clearTime().getTime()){
				mostrarMensajeError("El registro ya finaliza ese d&iacute;a");
			}else{
				llamadaAjax('Traduccion.do','baja','segmentacionTraduccionJSON',datosTraduccionBaja,callBackBajaTraduccion,false);
			}
		
		}
		</sec:authorize>
	 }
	
	 /**
		 * Respuesta a la llamada de baja traduccion
		 */
		var callBackBajaTraduccion = function(correcto,datos){
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
			if(correcto){
				cerrarVentana = true;
				Ext.getCmp('winAnadirTraduccion').hide();
				CGLOBALTRA.obtenerDatos();
				if(baja == true){
					mostrarMensajeInfo("Se ha dado de baja correctamente la traducci&oacute;n")
				}else{
				mostrarMensajeInfo("Se ha modificado correctamente la traducci&oacute;n")
				}
			}
			</sec:authorize>
		}
	 
	 
	/**
	 * Respuesta a la llamada del alta nueva traduccion
	 */
	var callBackNuevaTraduccion = function(correcto,datos){
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		if(correcto){
			cerrarVentana = true;
			Ext.getCmp('winAnadirTraduccion').hide();
			CGLOBALTRA.obtenerDatos();
			mostrarMensajeInfo("Se ha dado de alta correctamente la traducci&oacute;n")
		}
	</sec:authorize>
	}
	
	/**
	 * FUncion que da de alta una nueva traduccion
	 */
	var nuevaTraduccion = function(){
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		//Creamos el objeto que contendra la nueva alta.
		var datosTraduccionAlta = {
			codigoSegmentoFijo:Ext.get('selCodSegFijoPop').dom.options[Ext.get('selCodSegFijoPop').dom.selectedIndex].innerHTML,
			codigoSubSegmentoFijo:Ext.get('selCodSubSegFijoPop').dom.options[Ext.get('selCodSubSegFijoPop').dom.selectedIndex].innerHTML,
			codigoSegmentoMovil:Ext.get('selCodSegMovilPop').dom.options[Ext.get('selCodSegMovilPop').dom.selectedIndex].innerHTML,
			codigoSubSegmentoMovil:Ext.get('selCodSubSegMovilPop').dom.options[Ext.get('selCodSubSegMovilPop').dom.selectedIndex].innerHTML,
			fechaIniVigencia:Ext.get('txtIntervaloVigIni').dom.value,
			fechaFinVigencia:Ext.get('txtIntervaloVigFin').dom.value,
			usuarioAlta:Ext.get('hiddenUsuarioConectado').dom.value
			// La fecha de alta la asignaremos en el servicio
		}
		
		//Validamos las fechas
		if(esFechaValida(datosTraduccionAlta.fechaIniVigencia,"fecha de inicio de vigencia") && esFechaValida(datosTraduccionAlta.fechaFinVigencia,"fecha de fin de vigencia")){
			//Validamos que la fecha final sea superior a la fecha inicial
			var fechaIni = Date.parseDate(datosTraduccionAlta.fechaIniVigencia,'d.m.Y');
			var fechaFin = Date.parseDate(datosTraduccionAlta.fechaFinVigencia,'d.m.Y');
			if(fechaFin<fechaHoy() || fechaIni < fechaHoy()){
				mostrarMensajeError("Las fechas de vigencia no pueden ser menor a la del d&iacute;a de hoy");
			}else if(fechaIni.getTime() > fechaFin.getTime()){
				mostrarMensajeError("La fecha de inicio debe ser anterior a la de fin");
			}else {
				llamadaAjax('Traduccion.do','alta','segmentacionTraduccionJSON',datosTraduccionAlta,callBackNuevaTraduccion,false);
			}
		}
		
		</sec:authorize>
	}

	/**
	 * Funcion que crea una nueva traduccion
	 * @return
	 */
	var crearTraduccionPopup = function() {
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		esAlta = true;
		Ext.get('txtPlanCuentas').dom.value = '';
		Ext.get('txtIntervaloVigIni').dom.value = '';
		Ext.get('txtIntervaloVigFin').dom.value = '';
		Ext.get('txtIntervaloVigFinBaja').dom.value = '';

		Ext.get('selCodSegFijoPop').dom.value = '';
		Ext.get('selCodSegFijoPop').dom.disabled = false;
		Ext.get('selCodSegFijoPop').removeClass('dis');

		Ext.get('selCodSubSegFijoPop').dom.value = '';
		Ext.get('selCodSubSegFijoPop').dom.disabled = false;
		Ext.get('selCodSubSegFijoPop').removeClass('dis');

		Ext.get('selCodSegMovilPop').dom.value = '';
		Ext.get('selCodSegMovilPop').dom.disabled = false;
		Ext.get('selCodSegMovilPop').removeClass('dis');

		Ext.get('selCodSubSegMovilPop').dom.value = '';
		Ext.get('selCodSubSegMovilPop').dom.disabled = false;
		Ext.get('selCodSubSegMovilPop').removeClass('dis');

		Ext.get('txtDescSegFijoPop').dom.value = '';
		Ext.get('txtDescSubSegFijoPop').dom.value = '';
		Ext.get('txtDescSegMovilPop').dom.value = '';
		Ext.get('txtDescSubSegMovilPop').dom.value = '';
		
		//Mostramos el campo de fecha de fin y ocultamos el de baja
		Ext.get('divTxtIntervaloVigFin').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('divTxtIntervaloVigFinBaja').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		//Quitamos el disabled de fecha ini
		Ext.get('txtIntervaloVigIni').dom.readOnly = false;
		
		//Ext.get('divTxtIntervaloVigIni').removeClass('dis');
		Ext.get('divTxtIntervaloVigIni').removeClass('divCalendarDis');
		
		Ext.get('btnCalendarIntervaloVigIni').dom.disabled= false;
		Ext.get('btnCalendarIntervaloVigIni').removeClass('icoCalendarDis')
		
		
		</sec:authorize>
	}

	//comprueba que todos los campos estan rellenos
	var comprobarCamposAltaTodos = function() {
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		var datosMetidos = Ext.get('txtIntervaloVigIni').dom.value != ''
				&& Ext.get('txtIntervaloVigFin').dom.value != ''
				&& Ext.get('selCodSegFijoPop').dom.value != ''
				&& Ext.get('selCodSubSegFijoPop').dom.value != ''
				&& Ext.get('selCodSegMovilPop').dom.value != ''
				&& Ext.get('selCodSubSegMovilPop').dom.value != '';

		return datosMetidos;
	</sec:authorize>
	}

	//comprueba que alguno de los campos est� relleno
	var comprobarCamposAltaAlguno = function() {
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		var datosMetidos = Ext.get('txtIntervaloVigIni').dom.value != ''
				|| Ext.get('txtIntervaloVigFin').dom.value.trim() != ''
				|| Ext.get('selCodSegFijoPop').dom.value.trim() != ''
				|| Ext.get('selCodSubSegFijoPop').dom.value != ''
				|| Ext.get('selCodSegMovilPop').dom.value != ''
				|| Ext.get('selCodSubSegMovilPop').dom.value != '';

		return datosMetidos;
		</sec:authorize>
	}

	/**
	 * Cierra el formulario
	 */
	var cerrarFormulario = function() {
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		cerrarVentana = true;
		Ext.getCmp('winAnadirTraduccion').hide();
	</sec:authorize>
	}
	/**
	 * Lo mantiene abierto.
	 * @return
	 */
	var noCerrarFormulario = function() {
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		cerrarVentana = false;
	</sec:authorize>
	}

	//Boton cancelar, controla que aparezca el mensaje de aviso
	var cancelar = function() {
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		//En la modificacion no se muestra el mensaje
		// Si esta a true comprobamos todo
		if (!cerrarVentana && esAlta) {
			cerrarVentana = true;
			if (comprobarCamposAltaAlguno()) {
				mostrarConfirmacion(
						"Se van a perder los datos introducidos,&iquest;Desea continuar?",
						cerrarFormulario, noCerrarFormulario);
				return false;
			} else {
				cerrarVentana = false;
				return true;
			}
		} else {
			cerrarVentana = false;
			return true;
		}
		</sec:authorize>
	}

	//controla que el boton guardar est� deshabilitado hasta que se rellenen los campos	
	var controlarBotonGuardar = function() {
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		if (comprobarCamposAltaTodos()) {
			Ext.get('btnGuardar').dom.disabled = false;
			Ext.get('btnGuardar').dom.src = contexto + 'images/botones/QGbtGuardar.gif';
			Ext.get('btnGuardar').removeClass('btnDis');
		} else {
			Ext.get('btnGuardar').dom.disabled = true;
			Ext.get('btnGuardar').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
			Ext.get('btnGuardar').addClass('btnDis');
		}
	</sec:authorize>
	}

	var iniciarCalendarios = function() {
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		//obtenemos la fecha de hoy
		var hoy = fechaHoy();

		Calendar.setup( {
			inputField : "txtIntervaloVigIni",
			dateFormat : "%d.%m.%Y",
			trigger : "btnCalendarIntervaloVigIni",
			bottomBar : false,
			align : 'Br///B/r',
			min : hoy,
			onSelect : function() {
				this.hide();
				controlarBotonGuardar();
			
			}
		});

		Calendar.setup( {
			inputField : "txtIntervaloVigFin",
			dateFormat : "%d.%m.%Y",
			trigger : "btnCalendarIntervaloVigFin",
			bottomBar : false,
			align : 'Br///B/r',
			min : hoy,
			onSelect : function() {
				this.hide();
				controlarBotonGuardar();
			}
		});
		
		Calendar.setup( {
			inputField : "txtIntervaloVigFinBaja",
			dateFormat : "%d.%m.%Y",
			trigger : "btnCalendarIntervaloVigFinBaja",
			bottomBar : false,
			align : 'Br///B/r',
			min : hoy,
			onSelect : function() {
				this.hide();
				
			}
		});
	</sec:authorize>
	}

	var controlEventos = function() {
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		Ext.get('txtIntervaloVigFin').on('change', function() {
			controlarBotonGuardar();
		});
		
		Ext.get('txtIntervaloVigIni').on('change', function() {
			controlarBotonGuardar();
		});
		

		// Evento change del combo de seg fijo.
		Ext
				.get('selCodSegFijoPop')
				.on(
						'change',
						function() {
							Ext.get('txtDescSegFijoPop').dom.value = Ext
									.get('selCodSegFijoPop').dom.options[Ext
									.get('selCodSegFijoPop').dom.selectedIndex].value;
							controlarBotonGuardar();

							var codSegmento = Ext.get('selCodSegFijoPop').dom.options[Ext
									.get('selCodSegFijoPop').dom.selectedIndex].innerHTML;
							cargarSubSegmento(codSegmento, true);
							Ext.get('txtDescSubSegFijoPop').dom.value = '';
						});

		// Evento change del combo de subseg fijo.
		Ext
				.get('selCodSubSegFijoPop')
				.on(
						'change',
						function() {
							Ext.get('txtDescSubSegFijoPop').dom.value = Ext
									.get('selCodSubSegFijoPop').dom.options[Ext
									.get('selCodSubSegFijoPop').dom.selectedIndex].value;
							controlarBotonGuardar();
							
							
						});

		// Evento change del combo de seg movil.
		Ext
				.get('selCodSegMovilPop')
				.on(
						'change',
						function() {
							Ext.get('txtDescSegMovilPop').dom.value = Ext
									.get('selCodSegMovilPop').dom.options[Ext
									.get('selCodSegMovilPop').dom.selectedIndex].value;
							controlarBotonGuardar();

							var codSegmento = Ext.get('selCodSegMovilPop').dom.options[Ext
									.get('selCodSegMovilPop').dom.selectedIndex].innerHTML;
							cargarSubSegmento(codSegmento, false);
							Ext.get('txtDescSubSegMovilPop').dom.value = '';
						});

		// Evento change del combo de subseg movil.
		Ext
				.get('selCodSubSegMovilPop')
				.on(
						'change',
						function() {
							Ext.get('txtDescSubSegMovilPop').dom.value = Ext
									.get('selCodSubSegMovilPop').dom.options[Ext
									.get('selCodSubSegMovilPop').dom.selectedIndex].value;
							controlarBotonGuardar();
						});

		// evento click boton Cancelar
		Ext.get('btnCancelar').on('click', function() {
			Ext.getCmp('winAnadirTraduccion').hide();

		});
		
		// evento click boton guardar
		Ext.get('btnGuardar').on('click', function() {
			if(esAlta){
				nuevaTraduccion();
			}else{
				var fechaBaja = Ext.get('txtIntervaloVigFinBaja').dom.value;
				if(fechaBaja == null || fechaBaja == ""){
					mostrarConfirmacion("&iquest;Desea dar de baja registro seleccionado?",bajaTraduccion,null);
					//Es una baja
					baja = true;
				}else{
					mostrarConfirmacion("&iquest;Desea modificar el registro seleccionado?",bajaTraduccion,null);
					//Es una modificacion
					baja = false;
				}
			}

		});
	</sec:authorize>
	}

	/**
	 * Rellena el combo con los datos obtenidos de la llamada ajax
	 */
	var callbackRellenarSubComboFijo = function(correcto, datos) {
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		rellenarCombo('selCodSubSegFijoPop', "", correcto, datos);
	</sec:authorize>
	}
	/**
	 * Rellena el combo con los datos obtenidos de la llamada ajax
	 */
	var callbackRellenarSubComboMovil = function(correcto, datos) {
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		rellenarCombo('selCodSubSegMovilPop', "", correcto, datos);
		</sec:authorize>
	}

	/**
	 * Carga el subsegmento fijo o movil.
	 */
	var cargarSubSegmento = function(codSegmento, fijo) {
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
		//Formamos el objeto de busqueda
		var tipoComboValor = {
			valorCombo : codSegmento,
			tipoSegmento : fijo
		};
		if (fijo) {
			llamadaAjax('Traduccion.do', 'cargarCodigosSubSegmento',
					'tipoComboValorJSON', tipoComboValor,
					callbackRellenarSubComboFijo, false);
		} else {
			llamadaAjax('Traduccion.do', 'cargarCodigosSubSegmento',
					'tipoComboValorJSON', tipoComboValor,
					callbackRellenarSubComboMovil, false);
		}
	</sec:authorize>
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

		cancelar : function() {
			return cancelar();
		},
		volcarDatosPopup : function(seleccionado) {
			return volcarDatosPopup(seleccionado);
		},
		crearTraduccionPopup : function() {
			return crearTraduccionPopup();
		}
	}
}();
function controlarBotonGuardar() {
	CGLOBALGESTTRA.controlarBotonGuardar();
}
function cancelar() {
	return CGLOBALGESTTRA.cancelar();
}
function volcarDatosPopup(seleccionado) {
	return CGLOBALGESTTRA.volcarDatosPopup(seleccionado);
}
function crearTraduccionPopup() {
	CGLOBALGESTTRA.crearTraduccionPopup();
}
Ext.onReady(CGLOBALGESTTRA.init, CGLOBALGESTTRA, true);
</sec:authorize>