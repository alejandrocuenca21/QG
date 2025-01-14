<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
Ext.ns("CGCONCILIACION");
CGCONCILIACION = function(){
	
    //criterios de la ultima busqueda realizada.
	var ultimaBusqueda = new Ext.util.MixedCollection();	
	
	//Funcion para exportar el contenido de las pestanas a excel
	function exportarExcel(){  
		//Solo exportamos si hay resultados
		if(ultimaBusqueda.get('hayResultados')){
			if (Ext.isIE){
				myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
				myMask.show();
			}
			var busqueda = {
				fecha: ultimaBusqueda.get('fxBusqueda'),
				informe: "3"
			};
			document.getElementById('ifExportar').src = contexto + 'Conciliacion.do?exportarConciliacion=&busquedaJSON='+Ext.encode(busqueda);
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
	var imprimirConciliacion= function() {

	
		if(ultimaBusqueda.get('hayResultados')){
			if (Ext.isIE){
				myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
				myMask.show();
			}
			var busqueda = {
				fecha: ultimaBusqueda.get('fxBusqueda'),
				informe: "3"
			};
			document.getElementById('ifExportar').src = contexto + 'Conciliacion.do?exportarConciliacionPDF=&busquedaJSON='+Ext.encode(busqueda);
			
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

	//Control de eventos
	var controlEventos = function(){
		//evento click del botón Exportar Excel
		Ext.get('btnExportar').on('click', function() {
			exportarExcel();
		});
		
		//evento click del botón Imprimir
		Ext.get('btnImprimir').on('click', function() {
			imprimirConciliacion();
		});
		
		//cambio en pestanas
		Ext.get('pesInforme').on('click', function() {
			mostrarInforme();
		});
		Ext.get('pesNSCO').on('click', function() {
			mostrarNSCO();
		});
		Ext.get('pesPREPAGO').on('click', function() {
			mostrarPREPAGO();
		});		
		Ext.get('pesTW').on('click', function() {
			mostrarTW();
		});	
		Ext.get('pesInformativo').on('click', function() {
			mostrarInformativo();
		});		
		
		//Busqueda por fechas
		Ext.get('btnBuscar').on('click', function() {
			if (Ext.get('txtFechaBusqueda').dom.value != ""){
				cargarDatos();
			}
			else{
				mostrarMensajeInfo("No ha introducido a&uacute;n una fecha v&aacute;lida");
			}
		});
	}
	
	//Mostrar Elementos Pestana
	var mostrarInforme = function (){
	    Ext.get('divInforme').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('divNSCO').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divPREPAGO').setVisibilityMode(Ext.Element.DISPLAY).hide();		
		Ext.get('divTW').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divInformativo').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		Ext.get('pesInforme').addClass('activa');
		Ext.get('pesNSCO').removeClass('activa');
		Ext.get('pesPREPAGO').removeClass('activa');		
		Ext.get('pesTW').removeClass('activa');
		Ext.get('pesInformativo').removeClass('activa');  	    
	}
	
	//Mostrar Elementos Pestana
	var mostrarNSCO = function (){
	    Ext.get('divInforme').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divNSCO').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('divPREPAGO').setVisibilityMode(Ext.Element.DISPLAY).hide();		
		Ext.get('divTW').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divInformativo').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		Ext.get('pesInforme').removeClass('activa');
		Ext.get('pesNSCO').addClass('activa');
		Ext.get('pesPREPAGO').removeClass('activa');		
		Ext.get('pesTW').removeClass('activa');
		Ext.get('pesInformativo').removeClass('activa');  	    
	}
	
	//Mostrar Elementos Pestana
	var mostrarPREPAGO = function (){
	    Ext.get('divInforme').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divNSCO').setVisibilityMode(Ext.Element.DISPLAY).hide();	    
		Ext.get('divPREPAGO').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('divTW').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divInformativo').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		Ext.get('pesInforme').removeClass('activa');
		Ext.get('pesNSCO').removeClass('activa');
		Ext.get('pesPREPAGO').addClass('activa');		
		Ext.get('pesTW').removeClass('activa');
		Ext.get('pesInformativo').removeClass('activa');  	    
	}	
	
	//Mostrar Elementos Pestana
	var mostrarTW = function (){
	    Ext.get('divInforme').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divNSCO').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divPREPAGO').setVisibilityMode(Ext.Element.DISPLAY).hide();		
		Ext.get('divTW').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('divInformativo').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		Ext.get('pesInforme').removeClass('activa');
		Ext.get('pesNSCO').removeClass('activa');
		Ext.get('pesPREPAGO').removeClass('activa');		
		Ext.get('pesTW').addClass('activa');
		Ext.get('pesInformativo').removeClass('activa');  	    
	}
	
	//Mostrar Elementos Pestana
	var mostrarInformativo = function (){
	    Ext.get('divInforme').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divNSCO').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divPREPAGO').setVisibilityMode(Ext.Element.DISPLAY).hide();		
		Ext.get('divTW').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divInformativo').setVisibilityMode(Ext.Element.DISPLAY).show();
		
		Ext.get('pesInforme').removeClass('activa');
		Ext.get('pesNSCO').removeClass('activa');
		Ext.get('pesPREPAGO').removeClass('activa');		
		Ext.get('pesTW').removeClass('activa');
		Ext.get('pesInformativo').addClass('activa');  	    
	}	
	
	//Funciones para la carga de la excel en pantalla
	var callBackCargar = function(correcto,datosResultado){
		if(correcto){
		
  			//Si no llegan resultados mostramos un mensaje y no mostramos el grid
			if(datosResultado.length == 0){
				Ext.Msg.show({
					title:'Informaci&oacute;n',
					cls:'cgMsgBox',
					msg: '<span>No se han encontrado resultados que coincidan con los criterios de b&uacute;squeda introducidos.</span><br/>',
					buttons: Ext.Msg.OK,
					icon: Ext.MessageBox.INFO
				});
				ultimaBusqueda.add('hayResultados',false);
			}else{
				ultimaBusqueda.add('hayResultados',true);
				
				//Cambiamos el nombre a la primera pestana por la fecha de busqueda
				Ext.get('pesInforme').update('<span>' + datosResultado[0].fechaSolicitud + '</span>');
				
				pintarListado(datosResultado[0].NSCO, 'tableNSCO'); //Listado NSCO
				pintarListado(datosResultado[0].PREPAGO, 'tablePREPAGO'); //Listado PREPAGO				
				pintarListado(datosResultado[0].TW, 'tableTW'); //Listado TW
				
				pintarListadoInforme(datosResultado[0].depuracionDatosClientes,'tableDepDatosCli',6);
				pintarListadoInforme(datosResultado[0].clientesConciliados,'tableCliConciliados',5);
				pintarListadoInforme(datosResultado[0].conciliacionDirecciones,'tableConciliacionDir',7);
			
				mostrar();	
			}
		}
		else{
			ultimaBusqueda.add('hayResultados',false);
			ocultar();
		}
	}		
				
	//Funcion que llama a la carga de los datos de la excel
	var cargarDatos = function(){

		var busqueda = {
			fecha: Ext.get('txtFechaBusqueda').dom.value,
			informe: "3"
		};
		
		ultimaBusqueda.add('fxBusqueda',Ext.get('txtFechaBusqueda').dom.value);
		
		llamadaAjax('Conciliacion.do','buscar','busquedaJSON',busqueda,callBackCargar,true);
	}

	var pintarListado = function(listado, divRender){
		var table = '';
		table += '<table class="right_table"><tbody>'; 
		
		table += '<tr>';
		table += '<td class="noBrTable">&nbsp;</td>';
		table += '</tr>';
		
		
		//Construimos las columnas con los datos que nos llegan del servicio
		
		for(var i = 0; i < listado.length; i++) {
			table += '<tr>';
			table += '<td>'+ formato_numero(listado[i],0,",",".") +'</td>';
			table += '</tr>';
		}
		
		table += '</tbody></table>';
		
		Ext.get(divRender).update(table);
	}
	
	var pintarListadoInforme = function(listado, divRender, elementos){
		var table = '';
		table += '<table class="right_table"><tbody>'; 
		
		var saltolinea = '';
		
		if(divRender == 'tableDepDatosCli'){
			table += '<tr>';
				table += '<td colspan="12" class="colCabecera">DEPURACI&Oacute;N DE DATOS DE CLIENTES</td>';
			table += '</tr>';
			table += '<tr>';
				table += '<td colspan="4" class="colCabecera">DEPURACI&Oacute;N CON TW</td>';
				table += '<td colspan="4" class="colCabecera">DEPURACI&Oacute;N CON NSCO</td>';
				table += '<td colspan="4" class="colCabecera">DEPURACI&Oacute;N CON PREPAGO</td>';				
			table += '</tr>';
			saltolinea = '<br> &nbsp;';
		}
		
		if(divRender == 'tableCliConciliados'){
			table += '<tr>';
				table += '<td colspan="9" class="colCabecera">CLIENTES CONCILIADOS</td>';
			table += '</tr>';
			table += '<tr>';
				table += '<td colspan="3" class="colCabecera">CONCILIACI&Oacute;N CON TW</td>';
				table += '<td colspan="3" class="colCabecera">CONCILIACI&Oacute;N CON NSCO</td>';
				table += '<td colspan="3" class="colCabecera">CONCILIACI&Oacute;N CON PREPAGO</td>';				
			table += '</tr>';
		}
		
		if(divRender == 'tableConciliacionDir'){
			table += '<tr>';
				table += '<td colspan="4" class="colCabecera">CONCILIACI&Oacute;N DE DIRECCIONES</td>';
			table += '</tr>';					
		}
		table += '<tr>';
		
		if (listado.TW != undefined){
			if (listado.TW.listaDatos.length != 0)
				table += '<td class="colCabecera">'+ "TW" +'</td>';
			if (listado.TW.listaPorcentajes.length != 0)
				table += '<td class="colCabecera">%</td>';
		}
		if (listado.QGTW != undefined){
			if (listado.QGTW.listaDatos.length != 0)
				table += '<td class="colCabecera">'+ "QG" +'</td>';
			if (listado.QGTW.listaPorcentajes.length != 0)
				table += '<td class="colCabecera">%</td>';
		}
		if (listado.NSCO != undefined){	
			if (listado.NSCO.listaDatos.length != 0)
				table += '<td class="colCabecera">'+ "NSCO" +'</td>';
			if (listado.NSCO.listaPorcentajes.length != 0)	
				table += '<td class="colCabecera">%</td>';				
		}
		if (listado.QGNSCO != undefined){	
			if (listado.QGNSCO.listaDatos.length != 0)
				table += '<td class="colCabecera">'+ "QG" +'</td>';
			if (listado.QGNSCO.listaPorcentajes.length != 0)
				table += '<td class="colCabecera">%</td>';				
		}
		if (listado.PREPAGO != undefined){	
			if (listado.PREPAGO.listaDatos.length != 0)
				table += '<td class="colCabecera">'+ "PREPAGO" +'</td>';
			if (listado.PREPAGO.listaPorcentajes.length != 0)	
				table += '<td class="colCabecera">%</td>';				
		}
		if (listado.QGPREPAGO != undefined){	
			if (listado.QGPREPAGO.listaDatos.length != 0)
				table += '<td class="colCabecera">'+ "QG" +'</td>';
			if (listado.QGPREPAGO.listaPorcentajes.length != 0)
				table += '<td class="colCabecera">%</td>';				
		}
		
		if (listado.QG != undefined){	
			if (listado.QG.listaDatos.length != 0)
				table += '<td class="colCabecera">'+ "QG" +'</td>';
			if (listado.QG.listaPorcentajes.length != 0)
				table += '<td class="colCabecera">%</td>';
		}
		table += '</tr>';
		
		
		for(var i = 0; i < elementos; i++) {
			table += '<tr>';
			
			if (listado.TW != undefined){
				if (listado.TW.listaDatos.length != 0){
					if (listado.TW.listaDatos[i] != null)
						table += '<td>'+ formato_numero(listado.TW.listaDatos[i],0,",",".")+saltolinea +'</td>';
					else
						table += '<td class="cellBlank">'+saltolinea+'</td>';
				}
				if (listado.TW.listaPorcentajes.length != 0)
					if (listado.TW.listaPorcentajes[i] != null)
						table += '<td>'+ formato_numero(listado.TW.listaPorcentajes[i],2,",",".") +'%'+saltolinea+'</td>';
					else
						table += '<td class="cellBlank">'+saltolinea+'</td>';
			}
			if (listado.QGTW != undefined){
				if (listado.QGTW.listaDatos.length != 0){
					if (listado.QGTW.listaDatos[i] != null)
						table += '<td>'+ formato_numero(listado.QGTW.listaDatos[i],0,",",".") +saltolinea+'</td>';
					else
						table += '<td class="cellBlank">'+saltolinea+'</td>';
				}
				if (listado.QGTW.listaPorcentajes.length != 0){
					if (listado.QGTW.listaPorcentajes[i] != null)
						table += '<td>'+ formato_numero(listado.QGTW.listaPorcentajes[i],2,",",".") +'%'+saltolinea+'</td>';
					else
						table += '<td class="cellBlank">'+saltolinea+'</td>';
				}
			}
			if (listado.NSCO != undefined){
				if (listado.NSCO.listaDatos.length != 0){
					if (listado.NSCO.listaDatos[i] != null)
						table += '<td>'+ formato_numero(listado.NSCO.listaDatos[i],0,",",".") +saltolinea+'</td>';
					else
						table += '<td class="cellBlank">'+saltolinea+'</td>';
				}
				if (listado.NSCO.listaPorcentajes.length != 0){
					if (listado.NSCO.listaPorcentajes[i] != null)
						table += '<td>'+ formato_numero(listado.NSCO.listaPorcentajes[i],2,",",".") +'%'+saltolinea+'</td>';
					else
						table += '<td class="cellBlank">'+saltolinea+'</td>';					
				}
			}			
			if (listado.QGNSCO != undefined){
				if (listado.QGNSCO.listaDatos.length != 0){
					if (listado.QGNSCO.listaDatos[i] != null)
						table += '<td>'+ formato_numero(listado.QGNSCO.listaDatos[i],0,",",".") +saltolinea+'</td>';
					else
						table += '<td class="cellBlank">'+saltolinea+'</td>';
				}
				if (listado.QGNSCO.listaPorcentajes.length != 0){
					if (listado.QGNSCO.listaPorcentajes[i] != null)
						table += '<td>'+ formato_numero(listado.QGNSCO.listaPorcentajes[i],2,",",".") +'%'+saltolinea+'</td>';
					else
						table += '<td class="cellBlank">'+saltolinea+'</td>';
				}
			}
			if (listado.PREPAGO != undefined){
				if (listado.PREPAGO.listaDatos.length != 0){
					if (listado.PREPAGO.listaDatos[i] != null)
						table += '<td>'+ formato_numero(listado.PREPAGO.listaDatos[i],0,",",".") +saltolinea+'</td>';
					else
						table += '<td class="cellBlank">'+saltolinea+'</td>';
				}
				if (listado.PREPAGO.listaPorcentajes.length != 0){
					if (listado.PREPAGO.listaPorcentajes[i] != null)
						table += '<td>'+ formato_numero(listado.PREPAGO.listaPorcentajes[i],2,",",".") +'%'+saltolinea+'</td>';
					else
						table += '<td class="cellBlank">'+saltolinea+'</td>';					
				}
			}			
			if (listado.QGPREPAGO != undefined){
				if (listado.QGPREPAGO.listaDatos.length != 0){
					if (listado.QGPREPAGO.listaDatos[i] != null)
						table += '<td>'+ formato_numero(listado.QGPREPAGO.listaDatos[i],0,",",".") +saltolinea+'</td>';
					else
						table += '<td class="cellBlank">'+saltolinea+'</td>';
				}
				if (listado.QGPREPAGO.listaPorcentajes.length != 0){
					if (listado.QGPREPAGO.listaPorcentajes[i] != null)
						table += '<td>'+ formato_numero(listado.QGPREPAGO.listaPorcentajes[i],2,",",".") +'%'+saltolinea+'</td>';
					else
						table += '<td class="cellBlank">'+saltolinea+'</td>';
				}
			}			
			if (listado.QG != undefined){
				if (listado.QG.listaDatos.length != 0){
					if (listado.QG.listaDatos[i] != null)
						table += '<td>'+ formato_numero(listado.QG.listaDatos[i],0,",",".") +saltolinea+'</td>';
					else
						table += '<td class="cellBlank">'+saltolinea+'</td>';
				}
				if (listado.QG.listaPorcentajes.length != 0){
					if (listado.QG.listaPorcentajes[i] != null)
						table += '<td>'+ formato_numero(listado.QG.listaPorcentajes[i],2,",",".") +'%'+saltolinea+'</td>';
					else
						table += '<td class="cellBlank">'+saltolinea+'</td>';
				}
			}			
			
			table += '</tr>';
		}
		
		table += '</tbody></table>';
		
		Ext.get(divRender).update(table);		
	}			
	
	//Ocultamos las pestanas al inicio
	var ocultar = function (){
		Ext.get('pesInforme').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('pesNSCO').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('pesPREPAGO').setVisibilityMode(Ext.Element.DISPLAY).hide();		
		Ext.get('pesTW').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('pesInformativo').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		Ext.get('informeSalida').setVisibilityMode(Ext.Element.DISPLAY).hide();	
	}

	//Mostramos las pestanas una vez hay datos
	var mostrar = function (){
		Ext.get('pesInforme').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('pesNSCO').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('pesPREPAGO').setVisibilityMode(Ext.Element.DISPLAY).show();		
		Ext.get('pesTW').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('pesInformativo').setVisibilityMode(Ext.Element.DISPLAY).show();
		
		Ext.get('informeSalida').setVisibilityMode(Ext.Element.DISPLAY).show();	
	}		
	
	var numDiasMesFechaActual = function(){
		var fec = convertDateToJSONLib(new Date());
		
		var mes = fec.month - 1;
		
		if (mes == -1){//Si es enero
			mes = 11;
		}
		
		var anio = fec.year + 1900;
		
		if (mes == 0) return 31;
		if (mes == 1){
			if (comprobarSiBisisesto(anio))
		 		return 29;
		 	else return 28;
		}
		if (mes == 2) return 31;
		if (mes == 3) return 30;
		if (mes == 4) return 31;
		if (mes == 5) return 30;
		if (mes == 6) return 31;
		if (mes == 7) return 31;
		if (mes == 8) return 30;
		if (mes == 9) return 31;
		if (mes == 10) return 30;
		if (mes == 11) return 31;
	}
		
	//Funcion que inicia los calendarios entre la fecha actual y un mes antes
	var iniciarCalendarios = function (){

		//Obtenmos la fecha de hoy
		var hoy = new Date();
		var diasAntes = new Date();
		
		//Una semana
		diasAntes.setDate(diasAntes.getDate()-numDiasMesFechaActual());
		
		Calendar.setup({
			inputField: "txtFechaBusqueda",
			dateFormat: "%d.%m.%Y",
			trigger: "btnCalendarFechaBusqueda",
			min:diasAntes,
			max:hoy,
			bottomBar: false,
			align:'Br///T/l',
			onSelect: function() {
				this.hide();
			}				
		});
	
	}

    return {
		init: function() {
			Ext.QuickTips.init();
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			
			Ext.get('txtFechaBusqueda').dom.value = "";
			
			ocultar();
			controlEventos();
			iniciarCalendarios();
		}	
	}
	 
}();

Ext.onReady(CGCONCILIACION.init, CGCONCILIACION, true);
</sec:authorize>