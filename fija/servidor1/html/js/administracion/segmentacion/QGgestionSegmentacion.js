var CGLOBALGESTSEG = function() {
	//Variable que sirve de bandera para ocultar automaticamente el popup sin preguntar
	var cerrarVentana = false;
	
	
	//Habilita y Deshabilita cargando los datos prefijados en la ultima busqueda hecha por el usuario
	var cargarPantallaAlta = function(ultimaBusqueda, ultimaBusquedaTipo) {
		//Si no hay busqueda anterior no se hace nada y ademas esta debe ser de tipo filtrado
		if (ultimaBusqueda != null && ultimaBusquedaTipo != null
				&& ultimaBusquedaTipo == "filtrado") {
			//Obtenemos los posibles valores por los que se ha realizado la busqueda

			var indexUnidad = ultimaBusqueda.get('indexUnidad');
			var indexOperacion = ultimaBusqueda.get('indexOperacion');
			var indexCodigoSegmentoOrigen = ultimaBusqueda
					.get('indexCodigoSegmentoOrigen');
			var indexCodigoSubSegmentoOrigen = ultimaBusqueda
					.get('indexCodigoSubSegmentoOrigen');

			// Combo de operacion
			Ext.get('selOperacionAS').dom.selectedIndex = indexOperacion;
			// Combo de unidad
			Ext.get('selUnidadAS').dom.selectedIndex = indexUnidad;
			// Combo de segmento origen
			Ext.get('selCodSegAS').dom.selectedIndex = indexCodigoSegmentoOrigen;

			// Cargo el subsegmento, que es el mismo que el de la pantalla de
			// busqueda
			// y cargo el segmento destino
			Ext.get('txtDescripcionSegAS').dom.value = Ext.get('selCodSegAS').dom.options[Ext
					.get('selCodSegAS').dom.selectedIndex].value;
			var arrayComboDestinoAlta = new Array();
			// relleno el dato del combo y el campo de texto de
			// destino
			arrayComboDestinoAlta.push( {
				texto : Ext.get('selCodSegAS').dom.options[Ext
						.get('selCodSegAS').dom.selectedIndex].innerHTML,
				valor : Ext.get('selCodSegAS').dom.options[Ext
						.get('selCodSegAS').dom.selectedIndex].value
			});
			agregarValoresCombo('selCodDestSegAS', arrayComboDestinoAlta, true);
			// combo del alta destino
			Ext.get('txtDescDestSegAS').dom.value = Ext
					.get('txtDescripcionSegAS').dom.value;

			// Cargamos el combo de subsegmento
			var comboSubSegmento = new Array();

			Ext.each(Ext.get('selCodSubSeg').dom.options, function(dato) {
				//Como copiamos los combos sustituimos el todos por el cadena vacia
					if (dato.text == "Todos") {
						texto = "";
					} else {
						texto = dato.text;
					}
					comboSubSegmento.push( {
						texto : texto,
						valor : dato.value
					});
				});

			// Rellenamos los combos de subelementos
			agregarValoresCombo('selCodSubSegAS', comboSubSegmento, true);
			agregarValoresCombo('selCodSubSegDestAS', comboSubSegmento, true);

			Ext.get('selCodSubSegAS').dom.selectedIndex = indexCodigoSubSegmentoOrigen;
			Ext.get('txtDescripcionSubSegAS').dom.value = Ext
					.get('selCodSubSegAS').dom.options[Ext
					.get('selCodSubSegAS').dom.selectedIndex].value;

			// Deshabilitamos los elementos para ello si hay algun indice de
			// combo
			// mayor que 0 se deshabilita ya que indica que en la pantalla de
			// busqueda se ha seleccionado algun elemento
			Ext.get('selUnidadAS').dom.disabled = (indexUnidad != null && indexUnidad > 0);
			Ext.get('selOperacionAS').dom.disabled = (indexOperacion != null && indexOperacion > 0);
			Ext.get('selCodSegAS').dom.disabled = (indexCodigoSegmentoOrigen != null && indexCodigoSegmentoOrigen > 0);
			Ext.get('selCodSubSegAS').dom.disabled = (indexCodigoSubSegmentoOrigen != null && indexCodigoSubSegmentoOrigen > 0);

		}
	}
	//boton Guardar
	var guardarSegmentacion = function() {

		var datosSegmentacionAlta = {
			unidad : Ext.get('selUnidadAS').dom.options[Ext.get('selUnidadAS').dom.selectedIndex].value,
			operacion : Ext.get('selOperacionAS').dom.options[Ext
					.get('selOperacionAS').dom.selectedIndex].value,
			segmentoOrigen : Ext.get('selCodSegAS').dom.options[Ext
					.get('selCodSegAS').dom.selectedIndex].innerHTML,
			subSegmentoOrigen : Ext.get('selCodSubSegAS').dom.options[Ext
					.get('selCodSubSegAS').dom.selectedIndex].innerHTML,
			segmentoDestino : Ext.get('selCodDestSegAS').dom.options[Ext
					.get('selCodDestSegAS').dom.selectedIndex].innerHTML,
			subSegmentoDestino : Ext.get('selCodSubSegDestAS').dom.options[Ext
					.get('selCodSubSegDestAS').dom.selectedIndex].innerHTML,
			fechaIniVigencia : Ext.get('txtIntervaloVigASIni').dom.value,
			fechaFinVigencia : Ext.get('txtIntervaloVigASFin').dom.value
		}
		

		//Validamos las fechas
		if(esFechaValida(datosSegmentacionAlta.fechaIniVigencia,"fecha de inicio de vigencia") && esFechaValida(datosSegmentacionAlta.fechaFinVigencia,"fecha de fin de vigencia")){
		
			//Validamos que la fecha final sea superior a la fecha inicial
			var fechaIni = Date.parseDate(datosSegmentacionAlta.fechaIniVigencia,'d.m.Y');
			var fechaFin = Date.parseDate(datosSegmentacionAlta.fechaFinVigencia,'d.m.Y');
			if(fechaFin<new Date()){
				Ext.Msg
				.show( {
					title : 'Error',
					cls : 'cgMsgBox',
					msg : '<span>La Fecha de fin de vigencia no puede ser menor a la actual.</span><br/>',
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.ERROR
				});
			}else
			if(fechaIni.getTime() < fechaFin.getTime()){
				
				myMask = new Ext.LoadMask('marco', {
					msg : "Cargando datos..."
				});
				myMask.show();
				
				// Debemos comprobar que no este repetido con respecto a los valores que
				// haya en la pantalla
		
				var duplicidad = CGLOBAL.comprobarDuplicidad(datosSegmentacionAlta.unidad
						+ datosSegmentacionAlta.operacion
						+ datosSegmentacionAlta.segmentoOrigen
						+ datosSegmentacionAlta.subSegmentoOrigen,
						fechaIni,
						fechaFin);
				if(!duplicidad){
					Ext.Ajax
						.request( {
							url : contexto + 'Segmentaciones.do',
							params : {
								alta : '',
								segmentacionJSON : Ext.encode(datosSegmentacionAlta)
							},
							callback : function(options, success, response) {
								myMask.hide();
								if (success) {
									datosResultado = Ext.util.JSON
											.decode(response.responseText);
		
									if (datosResultado.success) {
										//al ser un guardado, mostramos un mensaje con el resultado.
										if (datosResultado.message
												&& datosResultado.message.length > 0) {
											Ext
													.each(
															datosResultado.message,
															function(message) {
																Ext.Msg
																		.show( {
																			title : 'Informaci&oacute;n',
																			cls : 'cgMsgBox',
																			msg : '<span>' + message + '</span><br/>',
																			buttons : Ext.Msg.OK,
																			icon : Ext.MessageBox.INFO
																		});
															});
		
											// Ocultamos el popup y volvemos a obtener 
											// los datos por criterio para ello activamos la bandera
											cerrarVentana = true;
											Ext.getCmp('winAnadirSegmentos').hide();
											CGLOBAL.obtenerDatos();
										}
									} else {
										//Ha saltado una excepcion y viene un objeto con un mensaje de error.
										Ext.Msg
												.show( {
													title : 'Error',
													cls : 'cgMsgBox',
													msg : '<span>' + datosResultado.message + '</span><br/>',
													buttons : Ext.Msg.OK,
													icon : Ext.MessageBox.ERROR
												});
									}
								}
							}
						});
			
			}else{
				//Esta duplicado
				myMask.hide();
				Ext.Msg
						.show( {
							title : 'Error',
							cls : 'cgMsgBox',
							msg : '<span>' + 'La segmentaci&oacute;n que intenta crear est&aacute; solapada con alguna de la lista' + '</span><br/>',
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.ERROR
						});
			}
			}else{
				//Las fecha final no es posterior
				
				Ext.Msg
				.show( {
					title : 'Error',
					cls : 'cgMsgBox',
					msg : '<span>' + 'La fecha final de vigencia debe ser posterior a la inicial' + '</span><br/>',
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.ERROR
				});
			}
		}
	}
	//comprueba que alguno de los campos est� relleno
	var comprobarCamposAltaAlguno = function() {
		var datosMetidos = Ext.get('selUnidadAS').dom.value != ''
				|| Ext.get('selOperacionAS').dom.value != ''
				|| Ext.get('txtIntervaloVigASIni').dom.value.trim() != ''
				|| Ext.get('txtIntervaloVigASFin').dom.value.trim() != ''
				|| Ext.get('selCodSegAS').dom.value != ''
				|| Ext.get('selCodSubSegAS').dom.value != ''
				|| Ext.get('selCodSubSegDestAS').dom.value != '';

		return datosMetidos;
	}
	//comprueba que todos los campos estan rellenos
	var comprobarCamposAltaTodos = function() {
		var datosMetidos = Ext.get('selUnidadAS').dom.value != ''
				&& Ext.get('selOperacionAS').dom.value != ''
				&& Ext.get('txtIntervaloVigASIni').dom.value.trim() != ''
				&& Ext.get('txtIntervaloVigASFin').dom.value.trim() != ''
				&& Ext.get('selCodSegAS').dom.value != ''
				&& Ext.get('selCodSubSegAS').dom.value != ''
				&& Ext.get('selCodSubSegDestAS').dom.value != '';

		return datosMetidos;
	}

	//controla que el boton guardar est� deshabilitado hasta que se rellenen los campos	
	var controlarBotonGuardar = function() {

		if (comprobarCamposAltaTodos()) {
			Ext.get('btnGuardar').dom.disabled = false;
			Ext.get('btnGuardar').dom.src = contexto + 'images/botones/QGbtGuardar.gif';
		} else {
			Ext.get('btnGuardar').dom.disabled = true;
			Ext.get('btnGuardar').dom.src = contexto + 'images/botones/QGbtGuardar_des.gif';
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
				return true;
			}

		}else{
			cerrarVentana = false;
			return true;
		}

	}

	var controlEventos = function() {

		//evento change del combo de segmentos en la pantalla de alta
		// carga el campo de texto con la descripcion del segmento seleccionado
		// en alta origen y destino
		Ext
				.get('selCodSegAS')
				.on(
						'change',
						function() {
							Ext.get('txtDescripcionSegAS').dom.value = Ext
									.get('selCodSegAS').dom.options[Ext
									.get('selCodSegAS').dom.selectedIndex].value;
							var arrayComboDestinoAlta = new Array();
							// relleno el dato del combo y el campo de texto de
							// destino
							arrayComboDestinoAlta
									.push( {
										texto : Ext.get('selCodSegAS').dom.options[Ext
												.get('selCodSegAS').dom.selectedIndex].innerHTML,
										valor : Ext.get('selCodSegAS').dom.options[Ext
												.get('selCodSegAS').dom.selectedIndex].value
									});
							agregarValoresCombo('selCodDestSegAS',
									arrayComboDestinoAlta, true);
							// combo
							// del
							// alta
							// destino
							Ext.get('txtDescDestSegAS').dom.value = Ext
									.get('txtDescripcionSegAS').dom.value;
							CGLOBAL.cargarSubSegmento("alta");
							controlarBotonGuardar();
						});
		// evento change del combo de subsegmentos en la pantalla de alta
		// carga el campo de texto con la descripcion del subsegmento
		// seleccionado en el alta origen
		Ext.get('selCodSubSegAS').on(
				'change',
				function() {
					Ext.get('txtDescripcionSubSegAS').dom.value = Ext
							.get('selCodSubSegAS').dom.options[Ext
							.get('selCodSubSegAS').dom.selectedIndex].value;
					controlarBotonGuardar();
				});
		// evento change en el combo subsegmento destino de la pantalla de alta
		// carga el campo de texto con la descripcion del subsegmento
		// seleccionado en el alta destino
		Ext
				.get('selCodSubSegDestAS')
				.on(
						'change',
						function() {
							Ext.get('txtDescSubSegDestAS').dom.value = Ext
									.get('selCodSubSegDestAS').dom.options[Ext
									.get('selCodSubSegDestAS').dom.selectedIndex].value;
							controlarBotonGuardar();
						});

		Ext.get('txtIntervaloVigASIni').on('change', function() {
			controlarBotonGuardar();
		});
		Ext.get('txtIntervaloVigASFin').on('change', function() {
			controlarBotonGuardar();
		});
		Ext.get('selUnidadAS').on('change', function() {
			controlarBotonGuardar();
		});

		Ext.get('selOperacionAS').on('change', function() {
			controlarBotonGuardar();
		});

		// evento click boton Guardar
		Ext.get('btnGuardar').on('click', function() {
			guardarSegmentacion();
		});

		// evento click boton Cancelar
		Ext.get('btnCancelar').on('click', function() {
			Ext.getCmp('winAnadirSegmentos').hide();

		});

		if (Ext.isIE6) {
			Ext.get('btnCalendarIntervaloVigASIni').on(
					'click',
					function() {
						Ext.get('selCodSubSegAS').setVisibilityMode(
								Ext.Element.VISIBILITY).hide();
					});
		}

	}

	var iniciarCalendarios = function() {
		//obtenemos la fecha de hoy
		var hoy = new Date();
		
		Calendar.setup( {
			inputField : "txtIntervaloVigASIni",
			dateFormat : "%d.%m.%Y",
			trigger : "btnCalendarIntervaloVigASIni",
			bottomBar : false,
			align : 'Br///B/r',
			onSelect : function() {
				this.hide();
				// Al cambiar mediante el calendar la fecha tambien debemos
			// controlar el estado del boton guardar
			controlarBotonGuardar();
		},
			onBlur : function() {
				if (Ext.isIE6) {
					Ext.get('selCodSubSegAS').setVisibilityMode(
							Ext.Element.VISIBILITY).show();
				}
			}
		});

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
		cargarPantallaAlta : function(ultimaBusqueda, ultimaBusquedaTipo) {
			cargarPantallaAlta(ultimaBusqueda, ultimaBusquedaTipo);
		},
		cancelar : function(){
			return cancelar();
		}
	}
}();
function controlarBotonGuardar() {
	CGLOBALGESTSEG.controlarBotonGuardar();
}
//Funcion que carga la pantalla de alta con los valores prefijados en la pantalla de busqueda
function cargarPantallaAlta(ultimaBusqueda, ultimaBusquedaTipo) {
	CGLOBALGESTSEG.cargarPantallaAlta(ultimaBusqueda, ultimaBusquedaTipo);
}
function cancelar(){
	return CGLOBALGESTSEG.cancelar();
}
Ext.onReady(CGLOBALGESTSEG.init, CGLOBALGESTSEG, true);