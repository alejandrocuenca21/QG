/*Variable global para las fechas limites*/
var fxIniMax = Date.parseDate('01.01.1900', 'd.m.Y');
var fxFinMax = Date.parseDate('31.12.2500', 'd.m.Y');	

/*Funcion de minimizado de popUps*/
function minimizarVentana (ventana){
	if(Ext.get(ventana).child('.x-window-bwrap').isVisible()){
		Ext.get(ventana).child('.x-window-bwrap').setVisibilityMode(Ext.Element.DISPLAY).hide();
	    Ext.get(ventana).addClass('windowMin');
	    if(Ext.isIE){
			Ext.each(Ext.query('.x-ie-shadow', Ext.getBody().id), function(sombra) {
				Ext.fly(sombra).setVisibilityMode(Ext.Element.DISPLAY).hide();
			});
		}else{
			Ext.each(Ext.query('.x-shadow', Ext.getBody().id), function(sombra) {
				Ext.fly(sombra).setVisibilityMode(Ext.Element.DISPLAY).hide();
			});
		}
	}else{
		Ext.get(ventana).removeClass('windowMin');
		Ext.get(ventana).child('.x-window-bwrap').setVisibilityMode(Ext.Element.DISPLAY).show();
		if(Ext.isIE){
			Ext.each(Ext.query('.x-ie-shadow', Ext.getBody().id), function(sombra) {
				Ext.fly(sombra).setVisibilityMode(Ext.Element.DISPLAY).show();
			});
		}else{
			Ext.each(Ext.query('.x-shadow', Ext.getBody().id), function(sombra) {
				Ext.fly(sombra).setVisibilityMode(Ext.Element.DISPLAY).show();
			});
		}
	}
}
/**
 * Funcion que maximiza la ventana
 * @param ventana - id de la ventana
 * @return
 */
function maximizarVentana(ventana){
	Ext.get(ventana).removeClass('windowMin');
	Ext.get(ventana).child('.x-window-bwrap').setVisibilityMode(Ext.Element.DISPLAY).show();
	if(Ext.isIE){
		Ext.each(Ext.query('.x-ie-shadow', Ext.getBody().id), function(sombra) {
			Ext.fly(sombra).setVisibilityMode(Ext.Element.DISPLAY).show();
		});
	}else{
		Ext.each(Ext.query('.x-shadow', Ext.getBody().id), function(sombra) {
			Ext.fly(sombra).setVisibilityMode(Ext.Element.DISPLAY).show();
		});
	}
}


/**
 * Convierte una fecha al formato que acepta la libreria JSON-lib
 * @param fecha - fecha en formato Date
 * @return objeto con formato aceptado por JSON-lib.
 */
function convertDateToJSONLib(fecha) {
    
	if (!fecha) {
		return null;
	}
	
	var newFecha = {
		date: fecha.getDate(),
		day: fecha.getDay(),
		hours: fecha.getHours(),
		minutes: fecha.getMinutes(),
		month: fecha.getMonth(),
		seconds: fecha.getSeconds(),
		time: fecha.getTime(),
		year: (fecha.getFullYear() - 1900),
		timezoneOffset: parseInt(fecha.getGMTOffset())
	};
	
	return newFecha;
	
}
function modificarFechaAnio(fecha){
	
	var resto = fecha.format('d.m.')
	var year = fecha.format('Y');
	year = String.leftPad(year, 4, '0');
	
	return resto + year;
}
function convertDateFromJSONLib(fecha) {
	if (!fecha) {
		return null;
	}
	
	var dt = new Date(fecha.year + 1900, fecha.month, fecha.date);
	
	/*dt.setDate(fecha.date);
	dt.setMonth(fecha.month);
	dt.setFullYear(fecha.year + 1900);*/
	dt.setHours(fecha.hours);
	dt.setMinutes(fecha.minutes);
	dt.setSeconds(fecha.seconds);
	
	return dt;
}

/**
 * Metodo generico para agregar datos a un combo.
 * @param combo {object} - identificador o elemento del combo.
 * @param valores {array} - array de objectos con dos propiedades, texto y valor.
 * @param reset {boolean} - si true, eliminara los datos anteriores. 
 */
function agregarValoresCombo(combo, valores, reset) {

	var cmb = Ext.get(combo);
	
	if (cmb) {
	
		if (reset === true) {
			cmb.dom.options.length = 0;
		}
		
		if (Ext.isArray(valores)) {
			Ext.each(valores, function(valor) {
			
				if (valor.texto != null) {
				
					if (valor.valor != null) {
						cmb.dom.options[cmb.dom.options.length] = new Option(valor.texto, valor.valor);
					} else {
						cmb.dom.options[cmb.dom.options.length] = new Option(valor.texto, valor.texto);
					}
				
				}
			
			});
		}
	}
}


/*
	FUNCION PARA VALIDAR FECHAS
*/
function esFechaValida(fecha, campo){
	return esFechaValidaConFormato(fecha,campo,'d.m.Y');
}

function esFechaValidaConFormato(fecha,campo,formato){
	if (Date.parseDate(fecha, formato)){
		
		var dia  =  parseInt(fecha.substring(0,2),10);
		var mes  =  parseInt(fecha.substring(3,5),10);
		var anio =  parseInt(fecha.substring(6),10);
		
		switch(mes){
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				numDias=31;
				break;
			case 4: case 6: case 9: case 11:
				numDias=30;
				break;
			case 2:
				if (comprobarSiBisisesto(anio)){ numDias=29 }else{ numDias=28};
				break;
			default:
				Ext.Msg.show({
				   title:'Fecha incorrecta',
				   msg: '<span>La fecha introducida en "'+campo+'" es incorrecta.</span>',
				   buttons: Ext.Msg.OK,
				   icon: Ext.MessageBox.ERROR
				});
				return false;
		}
		
		if (dia>numDias || dia==0){
			Ext.Msg.show({
			   title:'Fecha incorrecta',
			   msg: '<span>La fecha introducida en "'+campo+'" es incorrecta.</span>',
			   buttons: Ext.Msg.OK,
			   icon: Ext.MessageBox.ERROR
			});
			return false;
		}
		
		if (Date.parseDate(fecha, formato) < fxIniMax || Date.parseDate(fecha, formato) > fxFinMax){
			Ext.Msg.show({
			   title:'Fecha incorrecta',
			   msg: '<span>La fecha introducida en "'+campo+'" debe estar entre los aÃ±os 1900 y 2500.</span>',
			   buttons: Ext.Msg.OK,
			   icon: Ext.MessageBox.ERROR
			});
			return false;
		}
		
		return true;
	}else{
		Ext.Msg.show({
		   title:'Formato de fecha incorrecto',
		   msg: '<span>El formato de "'+campo+'" es incorrecto (dd.mm.aaaa).</span>',
		   buttons: Ext.Msg.OK,
		   icon: Ext.MessageBox.ERROR
		});
		return false;
	}
}
function comprobarSiBisisesto(anio){
	if ( ( anio % 100 != 0) && ((anio % 4 == 0) || (anio % 400 == 0))) {
		return true;
	}
	else {
		return false;
	}
}

/**
* Bloquea si lo que introduce el usuario no es un numÃ©rico
* @param evento - evento javascript.
*/
function bloquearNoNumerico(e){
    
    tecla = (document.all) ? e.keyCode : e.which;

    // Tecla de retroceso y la tecla de tabulaciÃ³n estÃ¡n permitidas
    if (tecla==0 ||tecla==8){
        return true;
    }
        
    // Patron de entrada que solo acepta numeros
    patron =/[0-9]/;
    
    tecla_final = String.fromCharCode(tecla);
    
    return patron.test(tecla_final);
}

/*
 * FunciÃ³n que comprueba que el valor del campo es numÃ©rico si no lo borra y muestra un mensaje.
 */
function comprobarNumerico (valor, campo){

	if (!validarNumerico(valor) && !Ext.isEmpty(valor)){ 
			
		// mostramos el mensaje de error
		var msg = 'El valor introducido debe ser num&eacute;rico';	
        Ext.Msg.show({
        	msg: String.escape(msg),
        	buttons: Ext.Msg.OK,
        	icon: Ext.MessageBox.WARNING
        });         
  
	}
}

/**
 * Comprueba si un texto es numerico
 */
function validarNumerico(numero) {
	return (/^\d+$/.test(numero));
}

/**
 * Inhabilitar Grid
 */
function inhabilitarGrid(grid) {
	Ext.getCmp(grid).getView().scroller.child('.x-grid3-body').mask();
}

/**
 * Habilitar Grid
 */
function habilitarGrid(grid) {
	Ext.getCmp(grid).getView().scroller.child('.x-grid3-body').unmask();
}


function  maxlengthArea(id, e, tam){ 
    
    var tcl = (document.all)?e.keyCode:e.which;
    
    if (document.getElementById(id).value.length >= tam){
    	if(tcl == 8 || tcl == 0){
           return true;
        }else{
        	return false;
        }
    }else{
           return true;
    }
}

/**
 * Corta una cadena
 */
function cortarCadena (cadena, longitud) {
 	

 	if ((!Ext.isEmpty(cadena)) && (cadena.trim().length > longitud)){
 		cadena = cadena.trim().substr(0,longitud) + "...";
 	}
 	return cadena;
 	 
 }
/**
 * Convierte en una fecha el string con el formato d.m.y.
 * 
 * - Se usa principalmente para ordenar strings que representan fechas
 * @param valor
 * @return
 */	
function convertirEnFecha(valor){
		var fecha = Date.parseDate(valor,'d.m.Y');
		return fecha;
}
/**
 * Valida un intervalo de fechas.
 *  - Valida el formato en d.m.y
 * @param fechaInicio
 * @param fechaFin
 * @return
 */
function validarIntervalosFechas(fechaInicio,fechaFinal,formatoFecha){
	//Podemos asignarle un formato, pero sino se pone por defecto
	if(formatoFecha == null || formatoFecha == ""){
		formatoFecha = 'd.m.Y';
	}
	//Validamos las fechas
	if(esFechaValidaConFormato(fechaInicio,"fecha de inicio de vigencia",formatoFecha) && esFechaValidaConFormato(fechaFinal,"fecha de fin de vigencia",formatoFecha)){
		//Validamos que la fecha final sea superior a la fecha inicial
		var fechaIni = Date.parseDate(fechaInicio,formatoFecha);
		var fechaFin = Date.parseDate(fechaFinal,formatoFecha);
		if(fechaFin<new Date()){
			mostrarMensajeError("La Fecha de fin de vigencia no puede ser menor a la de hoy.");
			return false;
		}else if(fechaIni.getTime() < fechaFin.getTime()){
			return true;
			
		}else{
			//Las fecha final no es posterior
			mostrarMensajeError("La fecha final de vigencia debe ser posterior a la inicial");
			return false;
		}
	}else{
		return false;
	}
}
 
 
 function validarIntervalosFechasSinDia(fechaInicio,fechaFinal,formatoFecha){
		//Podemos asignarle un formato, pero sino se pone por defecto
		if(formatoFecha == null || formatoFecha == ""){
			formatoFecha = 'd.m.Y';
		}
		//Validamos las fechas
		if(esFechaValidaConFormato(fechaInicio,"fecha de inicio de vigencia",formatoFecha) && esFechaValidaConFormato(fechaFinal,"fecha de fin de vigencia",formatoFecha)){
			//Validamos que la fecha final sea superior a la fecha inicial
			var fechaIni = Date.parseDate(fechaInicio,formatoFecha);
			var fechaFin = Date.parseDate(fechaFinal,formatoFecha);
			if(fechaIni.getTime() < fechaFin.getTime()){
				return true;
			}else{
				//Las fecha final no es posterior
				mostrarMensajeError("La fecha final de vigencia debe ser posterior a la inicial");
				return false;
			}
		}else{
			return false;
		}
	} 


/**
 * MÃ©todo que realiza una llamada ajax al action.
 * 
 * @param urlLlamada url de llamada al action (loquesea.do)
 * @param funcionLlamada funcion del action a la que hay que llamar para obtener los datos.
 * @param parametroJson parametro del form donde se mueven los datos entre el action y la pantalla
 * @param datosJson datos a enviar al action a traves del parametro json	
 * @param funcionRespuesta funcion javascript a la que se llamarÃ¡ con la respuesta. tendrÃ¡ dos parametros de entrada uno booleano para saber
 * si la llamada ha sido correcta y ha devuelto resultados y otro el listado de los datos.(se crea como una variable var funcionRespuesta = function (){})
 * @param busqueda indica si la llamada ajax es una busqueda, para comprobar el numero de elementos que se reciben.
 * @param devolverTodo booleano para saber si en la llamada al callback se devuelve el objeto json completo.(por ejemplo para las paginaciones.)
 * @return
 */

function llamadaAjax(urlLlamada,funcionLlamada,parametroJson,datosJson,funcionRespuesta,busqueda,devolverTodo){
	
	myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
	myMask.show();


	if(devolverTodo==undefined){
		devolverTodo = false;
	}
	
	var parametros = {};
	eval("parametros."+funcionLlamada+" = ''");
	
	if(datosJson != null){
		var datosCodificados = null;
		if(datosJson instanceof Object){
			datosCodificados = Ext.encode(datosJson);
		}else{
			datosCodificados = datosJson;
		}
		eval("parametros."+parametroJson+" ='"+ datosCodificados+"'");
	}
	
	Ext.Ajax.request({
		url:contexto + urlLlamada,
		params:parametros,
		callback:function (options, success, response){
		myMask.hide();
		if (success){
			datosResultado = Ext.util.JSON.decode (response.responseText);

			if (datosResultado.success){
				//Pueden llegar mensajes informativos.
				if (datosResultado.message && datosResultado.message.length>0){
					Ext.each(datosResultado.message,function(message){
						mostrarMensajeInfo(message);
						
					});
				}
				//Si no llegan resultados mostramos un mensaje y no mostramos el grid
				if(busqueda && datosResultado.total == 0){
					mostrarMensajeInfo("No se han encontrado registros");
					if(funcionRespuesta != undefined && funcionRespuesta != null){
						funcionRespuesta(false,new Array());	
					}
			 
				}else{
					if(devolverTodo){
						if(funcionRespuesta != undefined && funcionRespuesta != null){
							funcionRespuesta(true,datosResultado)
						}
					}else{
						if(funcionRespuesta != undefined && funcionRespuesta != null){
							funcionRespuesta(true,datosResultado.datos);
						}
					}
				}						
			}else{
				//Ha saltado una excepcion y viene un objeto con un mensaje de error.
				mostrarMensajeError(datosResultado.message);
				if(funcionRespuesta != undefined && funcionRespuesta != null){
					funcionRespuesta(false,new Array());	
				}
				
				 				 
			}									
		}
	}
	});			
	
}
 
function mostrarMensajeInfo(mensaje){
	Ext.Msg.show({
		title:'Informaci&oacute;n',
		cls:'cgMsgBox',
		msg: '<span>' + mensaje +'</span><br/>',
		buttons: Ext.Msg.OK,
		icon: Ext.MessageBox.INFO
	});
}
function mostrarMensajeError(mensaje){
	Ext.Msg.show({
		title:'Error',
		cls:'cgMsgBox',
		msg: '<span>'+mensaje+'</span><br/>',
		buttons: Ext.Msg.OK,
		icon: Ext.MessageBox.ERROR
	});

}
function mostrarConfirmacion(pregunta,funcionSi,funcionNo){
	Ext.Msg
	.show( {
		msg : '<span>'+pregunta+'</span><br/>',
		title : 'Aviso',
		buttons : Ext.Msg.YESNO,
		icon : Ext.Msg.INFO,
		minWidth : 400,
		fn : function(buttonId) {
			
		if (buttonId == 'yes') {
			if(funcionSi!= undefined && funcionSi!= null){
				funcionSi();
			}
		}else{
			if(funcionNo!= undefined && funcionNo!= null){
				funcionNo();
			}				 
		
		}
	}
	});
}

/**
 * Funcion comun que rellena dado un ID de combo unos datos y una bandera de correcto.
 * - Tiene un parametro valorVacio por si se quiere añadir un elemento al combo con valor ""
 * Si correcto rellena el combo ID con los datos, si no correcto vacia el combo.
 */
function rellenarCombo(id,valorVacio,correcto,datos){
	//Si correcto se rellena con los valores sino, se vacia
	 var arrayCombo = new Array();
		if(valorVacio != undefined && valorVacio != null){
			arrayCombo.push({texto:valorVacio , valor: ""});
		}
	 
	 if(correcto){
		 Ext.each(datos,function(dato){
				arrayCombo.push({texto: dato.codigo, valor: dato.descripcion});
			});
	 }
	 
	 agregarValoresCombo(id, arrayCombo, true);//combo de la busqueda
}
/**
 * Devuelve la fecha de hoy sin horas, minutos, segundos y milisegundos
 * @return Date.
 */
function fechaHoy(){
	return (new Date).clearTime();
}

/**
 * Devuelve un array de objetos (texto , valor ) de los elementos de un combo
 - quitarVacio: indica si quitar del vector el elemento vacio del combo
 * @param combo
 * @param reset
 * @return
 */
function recuperarValoresCombo(combo,quitarVacio){
	 var cmb = Ext.get(combo);
	 var arrayCombo = new Array();
	 
	 if(cmb){
		 Ext.each(cmb.dom.options, function(opcion) {
			 //Si el nulo hay que quitarlo vemos si el valor es cadena vacia o null
			if(!quitarVacio){
				arrayCombo.push({texto: opcion.text , valor: opcion.value});
			}else{
				// Vemos si el valor es null o cadena vacia
				if(opcion.value != null && opcion.value != '' ){
					arrayCombo.push({texto: opcion.text , valor: opcion.value});
				}
			}
		 });
	 }
	 
	 return arrayCombo;
}
/**
 * Crea un array con los valores de la linea de negocio ("", LNF y LNM)
 * @return
 */
function crearArrayLineaNegocio(){
	var arrayCombo = new Array(); 
	arrayCombo.push({texto: "", valor: ""});
	arrayCombo.push({texto: "LNF", valor: "01"});
	arrayCombo.push({texto: "LNM", valor: "02"});
	return arrayCombo;
}

/*
 * Da formato a un número para su visualización
 *
 * numero (Number o String) - Número que se mostrará
 * decimales (Number, opcional) - Nº de decimales (por defecto, auto)
 * separador_decimal (String, opcional) - Separador decimal (por defecto, coma)
 * separador_miles (String, opcional) - Separador de miles (por defecto, ninguno)
 */
function formato_numero(numero, decimales, separador_decimal, separador_miles){
    numero=parseFloat(numero);
    if(isNaN(numero)){
        return "";
    }

    if(decimales!=undefined){
        // Redondeamos
        numero=numero.toFixed(decimales);
    }

    // Convertimos el punto en separador_decimal
    numero=numero.toString().replace(".", separador_decimal!==undefined ? separador_decimal : ",");

    if(separador_miles){
        // Añadimos los separadores de miles
        var miles=new RegExp("(-?[0-9]+)([0-9]{3})");
        while(miles.test(numero)) {
            numero=numero.replace(miles, "$1" + separador_miles + "$2");
        }
    }

    return numero;
}

/*
 * Da formato a un Texto para su grabación en DB2
 *
 * descripcion (String) - Descripcion que se grabará
*/
function HTML_Texto(descripcion)
{
	if (descripcion != "")
	{	
		while (descripcion.toString().indexOf("&") != -1) 
			descripcion = descripcion.toString().replace("&","&amp;");
		while (descripcion.toString().indexOf("<") != -1) 
			descripcion = descripcion.toString().replace("<","&lt;");
		while (descripcion.toString().indexOf(">") != -1) 
			descripcion = descripcion.toString().replace(">","&gt;");
		while (descripcion.toString().indexOf('"') != -1) 
			descripcion = descripcion.toString().replace('"','&quot;');
		while (descripcion.toString().indexOf("'") != -1) 
			descripcion = descripcion.toString().replace("'","&#039;");					
	}
	return descripcion;
}

/*
 * Da formato a un Texto para su visualización en WEB
 *
 * descripcion (String) - Descripcion que se mostrará
*/
function Texto_HTML(descripcion)
{
	if (descripcion != "")
	{
		while (descripcion.toString().indexOf("&lt;") != -1) 
			descripcion = descripcion.toString().replace("&lt;","<");
		while (descripcion.toString().indexOf("&LT;") != -1) 
			descripcion = descripcion.toString().replace("&LT;","<");			
		while (descripcion.toString().indexOf("&gt;") != -1) 
			descripcion = descripcion.toString().replace("&gt;",">");
		while (descripcion.toString().indexOf("&GT;") != -1) 
			descripcion = descripcion.toString().replace("&GT;",">");			
		while (descripcion.toString().indexOf('&quot;') != -1) 
			descripcion = descripcion.toString().replace('&quot;','"');
		while (descripcion.toString().indexOf('&QUOT;') != -1) 
			descripcion = descripcion.toString().replace('&QUOT;','"');			
		while (descripcion.toString().indexOf("&#039;") != -1) 
			descripcion = descripcion.toString().replace("&#039;","'");
		while (descripcion.toString().indexOf("&amp;") != -1) 
			descripcion = descripcion.toString().replace("&amp;","&");
		while (descripcion.toString().indexOf("&AMP;") != -1) 
			descripcion = descripcion.toString().replace("&AMP;","&");
	}
	return descripcion;
}
