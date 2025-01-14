<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
var CGLOBAL = function (){
	var grid = null;
	var datosResultado;	
		
	//criterios de la ultima busqueda realizada.
	ultimaBusqueda = new Ext.util.MixedCollection();
	
	regSeleccionado = null;
	
    filaSeleccionada = null;
    
    filaSeleccionadaHist = null;
    
    ventanaActual = null;
    
    entradaGuardada = null;
    
    var pulsado = false;

	var campoSegmento;
	var campoSubsegmento;
	var campoSubsegmento2;
	var campoDescripcionSubsegmento;
	var campoDescripcionSubsegmento2;
	
	var gridHist = null;
   		
   	cargaHistorico = false;   	
	
	var selComboAdmin = "";
	
	modificarReg = false;
	
	//Funciones que hacen la llamada para carga el combo de Reglas
	var callBackComboReglas = function(correcto,datosResultado){
		if(correcto){
		
			if(ventanaActual == "busqueda"){
				var arrayCombo = new Array();
				arrayCombo.push({texto: "Todas", valor: ""});
				Ext.each(datosResultado,function(dato){
					arrayCombo.push({texto: dato.codigo, valor: dato.descripcion});
				});
			
				agregarValoresCombo('selCodRegla', arrayCombo, true);//combo de la busqueda
				
				if (Ext.get('hiddenOrigenVuelta').dom.value == "llamadaReglas"){
		   	
				   	entradaGuardada = Ext.util.JSON.decode(Ext.get('hiddenEntradaVuelta').dom.value);
				   	ultimaBusqueda.add('hayResultados',true);
		   			ultimaBusqueda.add('entradaBusqueda',entradaGuardada);
		
					if (entradaGuardada != null){	
						cargarFiltro();
					}
				}		
			}
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
			else{
				var arrayCombo = new Array();
				arrayCombo.push({texto: "", valor: ""});
				Ext.each(datosResultado,function(dato){
					arrayCombo.push({texto: dato.codigo, valor: dato.descripcion});
				});
			
				agregarValoresCombo('selCodReglaAS', arrayCombo, true);//combo de la busqueda
				if (modificarReg == true){//Si estamos en la pantalla Alta-Mod para Modificacion
					if (arrayCombo.length > 1){//Hay registros, cargamos el combo, en caso contrario se queda vacio
						CGLOBALGESTREGSEG.marcarRegla(regSeleccionado.regla);
						CGLOBALGESTREGSEG.controlarBotonGuardar();
					}
				}
			}
			</sec:authorize>
		}
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">		
		else
		{
			if(ventanaActual == "modif")
				CGLOBALGESTREGSEG.controlarBotonGuardar();
		}
		</sec:authorize>
	}
	
	var cargarReglas = function(){
		var inActuacion = "D";   
		llamadaAjax('Reglas.do','cargarComboReglas','inActuacion',inActuacion,callBackComboReglas,false);	
		
	}
	
	//Carga el combo de Lineas Fijas
	/*
	var cargarLineasFix = function(){
		var arrayCombo = new Array();
		if(ventanaActual == "busqueda"){
			arrayCombo.push({texto: "Todas", valor: ""});
			for (var i=0;i<=20;i++){
				arrayCombo.push({texto: i, valor: "i"});
			}
			agregarValoresCombo('selLinFix', arrayCombo, true);
		}
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		else{
			arrayCombo.push({texto: "", valor: ""});
			for (var i=0;i<=20;i++){
				arrayCombo.push({texto: i, valor: "i"});
			}
			agregarValoresCombo('selLinFixAS', arrayCombo, true);
			if (modificarReg == true){//Si estamos en la pantalla Alta-Mod para Modificacion
				if (arrayCombo.length > 1){//Hay registros, cargamos el combo, en caso contrario se queda vacio
					CGLOBALGESTREGSEG.marcarLineasFix(regSeleccionado.NLinFija);
					CGLOBALGESTREGSEG.controlarBotonGuardar();
				}
			}
		}
		</sec:authorize>
	}
	*/
	var cargarLineasFix = function(){
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		if(ventanaActual == "modif" && modificarReg == true)
		{
			CGLOBALGESTREGSEG.marcarLineasFix(regSeleccionado.NLinFija);
			CGLOBALGESTREGSEG.controlarBotonGuardar();		
		}
		</sec:authorize>
	}
	
	//Carga el combo de Lineas Movil
	/* 
	var cargarLineasMov = function(){
		var arrayCombo = new Array();
		if(ventanaActual == "busqueda"){
			arrayCombo.push({texto: "Todas", valor: ""});
			for (var i=0;i<=20;i++){
				arrayCombo.push({texto: i, valor: "i"});
			}
			agregarValoresCombo('selLinMov', arrayCombo, true);
		}
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		else{
			arrayCombo.push({texto: "", valor: ""});
			for (var i=0;i<=20;i++){
				arrayCombo.push({texto: i, valor: "i"});
			}
			agregarValoresCombo('selLinMovAS', arrayCombo, true);
			if (modificarReg == true){//Si estamos en la pantalla Alta-Mod para Modificacion
				if (arrayCombo.length > 1){//Hay registros, cargamos el combo, en caso contrario se queda vacio
					CGLOBALGESTREGSEG.marcarLineasMov(regSeleccionado.NLinMov);
					CGLOBALGESTREGSEG.controlarBotonGuardar();
				}
			}
		}
		</sec:authorize>
	}
	*/
	var cargarLineasMov = function(){
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		if(ventanaActual == "modif" && modificarReg == true)
		{
			CGLOBALGESTREGSEG.marcarLineasMov(regSeleccionado.NLinMov);
			CGLOBALGESTREGSEG.controlarBotonGuardar();		
		}
		</sec:authorize>
	}	
	
	//Carga el combo de Linas Totales
	/*
	var cargarTotalLin = function(){
		var arrayCombo = new Array();
		if(ventanaActual == "busqueda"){
			arrayCombo.push({texto: "Todas", valor: ""});	
			for (var i=0;i<=40;i++){
				arrayCombo.push({texto: i, valor: "i"});
			}
			agregarValoresCombo('selLinTot', arrayCombo, true);
		}
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		else{
			arrayCombo.push({texto: "", valor: ""});	
			for (var i=0;i<=40;i++){
				arrayCombo.push({texto: i, valor: "i"});
			}
			agregarValoresCombo('selLinTotAS', arrayCombo, true);
			if (modificarReg == true){//Si estamos en la pantalla Alta-Mod para Modificacion
				if (arrayCombo.length > 1){//Hay registros, cargamos el combo, en caso contrario se queda vacio
					CGLOBALGESTREGSEG.marcarLineasTot(regSeleccionado.NTotalLin);
					CGLOBALGESTREGSEG.controlarBotonGuardar();
				}
			}
		}
		</sec:authorize>
	}
	*/
	var cargarTotalLin = function(){
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		if(ventanaActual == "modif" && modificarReg == true)
		{
			CGLOBALGESTREGSEG.marcarLineasTot(regSeleccionado.NTotalLin);
			CGLOBALGESTREGSEG.controlarBotonGuardar();		
		}
		</sec:authorize>
	}	
	
	//Carga el combo de dias
	var cargarDias = function(){
		var arrayCombo = new Array();
		if(ventanaActual == "busqueda"){
			arrayCombo.push({texto: "Todos", valor: ""});			
			for (var i=0;i<=100;i++){
				arrayCombo.push({texto: i, valor: "i"});
			}
			agregarValoresCombo('selDias', arrayCombo, true);
		}
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		else{
			arrayCombo.push({texto: "", valor: ""});			
			for (var i=0;i<=100;i++){
				arrayCombo.push({texto: i, valor: "i"});
			}
			agregarValoresCombo('selDiasAS', arrayCombo, true);
			if (modificarReg == true){//Si estamos en la pantalla Alta-Mod para Modificacion
				if (arrayCombo.length > 1){//Hay registros, cargamos el combo, en caso contrario se queda vacio
					CGLOBALGESTREGSEG.marcarDias(regSeleccionado.NDias);
					CGLOBALGESTREGSEG.controlarBotonGuardar();
				}
			}
		}
		</sec:authorize>			
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
				
				//Borramos si habia algo antes en las otras combos
				Ext.get('selCodSubSeg').dom.disabled = true;
				Ext.get('selCodSubSeg').dom.selectedIndex = 0;
				Ext.get('txtDescripcionSubSeg').dom.value = '';
				var arrayComboT = new Array();
		 		arrayComboT.push({texto: "Todos", valor: ""});
				agregarValoresCombo('selCodSubSeg', arrayComboT, true);
				
				Ext.get('selOfAtenc').dom.disabled = true;
				Ext.get('selOfAtenc').dom.selectedIndex = 0;
				var arrayComboT = new Array();
		 		arrayComboT.push({texto: "Todos", valor: ""});
				agregarValoresCombo('selOfAtenc', arrayComboT, true);
				
				Ext.get('selTandem').dom.disabled = true;
				Ext.get('selTandem').dom.selectedIndex = 0;
				var arrayComboT = new Array();
		 		arrayComboT.push({texto: "Todos", valor: ""});
				agregarValoresCombo('selTandem', arrayComboT, true);
				
				if (Ext.get('hiddenOrigenVuelta').dom.value == "llamadaReglas"){
		   	
				   	entradaGuardada = Ext.util.JSON.decode(Ext.get('hiddenEntradaVuelta').dom.value);
				   	ultimaBusqueda.add('hayResultados',true);
		   			ultimaBusqueda.add('entradaBusqueda',entradaGuardada);
		
					if (entradaGuardada != null){	
						cargarFiltro();
					}
				}	
				
			}
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
			else{
				var arrayCombo = new Array();
				arrayCombo.push({texto: "", valor: ""});
				Ext.each(datosResultado,function(dato){
					arrayCombo.push({texto: dato.codigo, valor: dato.descripcion});
				});
			
				agregarValoresCombo('selCodSegAS', arrayCombo, true);//combo de la busqueda
				Ext.get('txtDescripcionSegAS').dom.value = '';
				
				if (modificarReg == true){//Si estamos en la pantalla Alta-Mod para Modificacion
					CGLOBALGESTREGSEG.marcarSegmento(regSeleccionado.segmento);
					Ext.get('txtDescripcionSegAS').dom.value = regSeleccionado.segmentoDes;
					if (Ext.get('selCodSegAS').dom.selectedIndex != 0){
						Ext.get('selCodSubSegAS').dom.disabled = false;
						cargarSubsegmento(Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text);
					}					
					//Si se modifica siempre hay que comprobar
					//el resto de elementos de su llamada					
					if (regSeleccionado.segmentoDes != filaSeleccionada.segmentoDes)
						CGLOBALGESTREGSEG.controlarBotonGuardar();					
				}	
				else{//Si estamos en la pantalla Alta-Mod para el Alta
					Ext.get('selCodSubSegAS').dom.disabled = true;
					Ext.get('selCodSubSegAS').dom.selectedIndex = 0;
					var arrayCombo = new Array();
	 				arrayCombo.push({texto: "", valor: ""});
					agregarValoresCombo('selCodSubSegAS', arrayCombo, true);
				}
			}
			</sec:authorize>
		}
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">		
		else
		{
			if(ventanaActual == "modif")
				CGLOBALGESTREGSEG.controlarBotonGuardar();
		}
		</sec:authorize>		
	}

	var cargarCodigoSegmento = function(unidadEntrada,pantalla){
		
		if(pantalla == "busqueda"){
			var arrayCombo = new Array();
			arrayCombo.push({texto: "Todos", valor: ""});
		}
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
				
				//Borramos si habia algo antes en "tandem"
				Ext.get('selTandem').dom.disabled = true;
				Ext.get('selTandem').dom.selectedIndex = 0;
				var arrayComboT = new Array();
		 		arrayComboT.push({texto: "Todos", valor: ""});
				agregarValoresCombo('selTandem', arrayComboT, true);
							
				if (Ext.get('hiddenOrigenVuelta').dom.value == "llamadaReglas" && entradaGuardada != null){
					marcarOfAtencion(entradaGuardada.ofAtencion);
					if (entradaGuardada.ofAtencion != "" && arrayCombo.length > 1)
						cargarTandem(entradaGuardada.codigoSegmento,entradaGuardada.codigoSubsegmento,entradaGuardada.ofAtencion);
				}
			}
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
			else{
				var arrayCombo = new Array();
		 		arrayCombo.push({texto: "", valor: ""});
		 		Ext.each(datosResultado,function(dato){
		 			arrayCombo.push({texto: dato, valor: dato});
		 		});
				agregarValoresCombo('selOfAtencAS', arrayCombo, true);

				if (modificarReg == true){//Si estamos en la pantalla Alta-Mod para Modficacion
					if (arrayCombo.length > 1){//Hay registros, cargamos el siguiente combo
						CGLOBALGESTREGSEG.marcarOfAtencion(regSeleccionado.ofAtencion);
						if (Ext.get('selOfAtencAS').dom.selectedIndex != 0){
							Ext.get('selTandemAS').dom.disabled = false;
							cargarTandem(Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text,
										Ext.get('selCodSubSegAS').dom.options[Ext.get('selCodSubSegAS').dom.selectedIndex].text,
										Ext.get('selOfAtencAS').dom.options[Ext.get('selOfAtencAS').dom.selectedIndex].text);
						}
						else
						{
	 						//Borramos si habia algo antes en "tandemAS"
							Ext.get('selTandemAS').dom.disabled = true;
							Ext.get('selTandemAS').dom.selectedIndex = 0;
							var arrayCombo = new Array();
		 					arrayCombo.push({texto: "", valor: ""});
							agregarValoresCombo('selTandemAS', arrayCombo, true);
							//Si se modifica siempre hay que comprobar
							//el resto de elementos de su llamada					
							if (regSeleccionado.ofAtencion != filaSeleccionada.ofAtencion)
								CGLOBALGESTREGSEG.controlarBotonGuardar();													
						}
					}
					else{ //Borramos si habia algo antes en "tandemAS"
						Ext.get('selTandemAS').dom.disabled = true;
						Ext.get('selTandemAS').dom.selectedIndex = 0;
						var arrayCombo = new Array();
	 					arrayCombo.push({texto: "", valor: ""});						
						agregarValoresCombo('selTandemAS', arrayCombo, true);
						//Si se modifica siempre hay que comprobar
						//el resto de elementos de su llamada					
						if (regSeleccionado.ofAtencion != filaSeleccionada.ofAtencion)
							CGLOBALGESTREGSEG.controlarBotonGuardar();						
					}
				}
				else{//Si estamos en la pantalla Alta-Mod para el Alta
					Ext.get('selTandemAS').dom.disabled = true;
					Ext.get('selTandemAS').dom.selectedIndex = 0;
					var arrayCombo = new Array();
	 				arrayCombo.push({texto: "", valor: ""});
					agregarValoresCombo('selTandemAS', arrayCombo, true);
				}				
			}
			</sec:authorize>
		}
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">		
		else
		{		
			if(ventanaActual == "modif")
				CGLOBALGESTREGSEG.controlarBotonGuardar();
		}
		</sec:authorize>		
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
				
				if (Ext.get('hiddenOrigenVuelta').dom.value == "llamadaReglas" && entradaGuardada != null){
					if (entradaGuardada.tandem != "" && arrayCombo.length > 1){
						Ext.get('selTandem').dom.disabled = false;
						marcarTandem(entradaGuardada.tandem);
					}
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
						CGLOBALGESTREGSEG.marcarTandem(regSeleccionado.tandem);
						CGLOBALGESTREGSEG.controlarBotonGuardar();
					}
					//Si se modifica siempre hay que comprobar
					//el resto de elementos de su llamada					
					if (regSeleccionado.tandem != filaSeleccionada.tandem)
						CGLOBALGESTREGSEG.controlarBotonGuardar();					
				}
			}
			</sec:authorize>
		}
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">		
		else
		{	
			if(ventanaActual == "modif")
				CGLOBALGESTREGSEG.controlarBotonGuardar();
		}
		</sec:authorize>		
	}
	
	var cargarTandem = function (codSeg,codSub,codOfAt){
		
		var entrada = {
			codigoSegmento: codSeg,
			codigoSubsegmento: codSub,
			ofAtencion: codOfAt
		};
		llamadaAjax('Presegmentacion.do','cargarCodigosTandem','segmentacionPresegJSON',entrada,callBackComboTandem,false);	
	}	
	
	 //Carga la combo subsegmento.
	 var cargarSubsegmento = function(codSeg){
		//Formamos el objeto de busqueda
		 var tipoComboValor = {
				valorCombo:codSeg,
		 		tipoSegmento:true
		 };
		 llamadaAjax('Traduccion.do','cargarCodigosSubSegmento','tipoComboValorJSON',tipoComboValor,callbackRellenarSubCombo,false);
	 }
	 
	 //Rellena el combo con los datos obtenidos de la llamada ajax
	 var callbackRellenarSubCombo = function(correcto,datosResultado){
		if(correcto){			
			if(ventanaActual == "busqueda"){
				var arrayCombo = new Array();
				arrayCombo.push({texto: "Todos", valor: ""});
				Ext.each(datosResultado,function(dato){
					arrayCombo.push({texto: dato.codigo, valor: dato.descripcion});
				});
			
				agregarValoresCombo('selCodSubSeg', arrayCombo, true);//combo de la busqueda
				Ext.get('txtDescripcionSubSeg').dom.value = '';
				
				//Borramos si habia algo antes en las otras combos			
				Ext.get('selOfAtenc').dom.disabled = true;
				Ext.get('selOfAtenc').dom.selectedIndex = 0;
				var arrayComboT = new Array();
		 		arrayComboT.push({texto: "Todos", valor: ""});
				agregarValoresCombo('selOfAtenc', arrayComboT, true);
				
				Ext.get('selTandem').dom.disabled = true;
				Ext.get('selTandem').dom.selectedIndex = 0;
				var arrayComboT = new Array();
		 		arrayComboT.push({texto: "Todos", valor: ""});
				agregarValoresCombo('selTandem', arrayComboT, true);
				
				if (Ext.get('hiddenOrigenVuelta').dom.value == "llamadaReglas" && entradaGuardada != null){
					marcarSubsegmento(entradaGuardada.codigoSubsegmento);
					if (entradaGuardada.codigoSubsegmento != "" && arrayCombo.length > 1)
						cargarOfAtencion(entradaGuardada.codigoSegmento,entradaGuardada.codigoSubsegmento);
				}				
				
			}
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
			else{
				var arrayCombo = new Array();
				arrayCombo.push({texto: "", valor: ""});
				Ext.each(datosResultado,function(dato){
					arrayCombo.push({texto: dato.codigo, valor: dato.descripcion});
				});
			
				agregarValoresCombo('selCodSubSegAS', arrayCombo, true);//combo de la busqueda
				Ext.get('txtDescripcionSubSegAS').dom.value = '';	
				
				if (modificarReg == true){//Si estamos en la pantalla Alta-Mod para Modificacion
					CGLOBALGESTREGSEG.marcarSubsegmento(regSeleccionado.subSegmento);
					Ext.get('txtDescripcionSubSegAS').dom.value = regSeleccionado.subSegmentoDes;
					if (Ext.get('selCodSubSegAS').dom.selectedIndex != 0){
						Ext.get('selOfAtencAS').dom.disabled = false;
						cargarOfAtencion(Ext.get('selCodSegAS').dom.options[Ext.get('selCodSegAS').dom.selectedIndex].text,
										Ext.get('selCodSubSegAS').dom.options[Ext.get('selCodSubSegAS').dom.selectedIndex].text);
					}
					else
					{
						//Borramos lo que hubiese en Of. Atencion y Tandem
						Ext.get('selOfAtencAS').dom.disabled = true;
						Ext.get('selOfAtencAS').dom.selectedIndex = 0;
						var arrayCombo = new Array();
	 					arrayCombo.push({texto: "", valor: ""});
						agregarValoresCombo('selOfAtencAS', arrayCombo, true);
						Ext.get('selTandemAS').dom.disabled = true;
						Ext.get('selTandemAS').dom.selectedIndex = 0;
						agregarValoresCombo('selTandemAS', arrayCombo, true);
						//Si se modifica siempre hay que comprobar
						//el resto de elementos de su llamada					
						if (regSeleccionado.subSegmentoDes != filaSeleccionada.subSegmentoDes)
							CGLOBALGESTREGSEG.controlarBotonGuardar();						
					}
				}	
				else{//Si estamos en la pantalla Alta-Mod para el Alta
					Ext.get('selOfAtencAS').dom.disabled = true;
					Ext.get('selOfAtencAS').dom.selectedIndex = 0;
					var arrayCombo = new Array();
	 				arrayCombo.push({texto: "", valor: ""});
					agregarValoresCombo('selOfAtencAS', arrayCombo, true);
					Ext.get('selTandemAS').dom.disabled = true;
					Ext.get('selTandemAS').dom.selectedIndex = 0;
					agregarValoresCombo('selTandemAS', arrayCombo, true);					
				}
			}
			</sec:authorize>
		}
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">		
		else
		{
			if(ventanaActual == "modif")
				CGLOBALGESTREGSEG.controlarBotonGuardar();
		}
		</sec:authorize>		
	 }	 	
	 
 	var cargarFechas = function(){
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		if(ventanaActual == "modif" && modificarReg == true)
		{
			CGLOBALGESTREGSEG.marcarFechaInicio(regSeleccionado.fechaIniVigencia);
			CGLOBALGESTREGSEG.marcarFechaFin(regSeleccionado.fechaFinVigencia);			
			CGLOBALGESTREGSEG.controlarBotonGuardar();		
		}
		</sec:authorize>
	}
	
	//Funciones para cargar la tabla de Presegmentaciones
   	var callBackObtenerDatos = function(correcto,datosResultado){
		if (correcto){
			if(!cargaHistorico){
				Ext.getCmp('gridBuscadorReglas').getStore().loadData(datosResultado);
   			}
			else{
   				Ext.getCmp('gridHistoricoReglas').getStore().loadData(datosResultado);
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
		</sec:authorize>
		if (cargaHistorico)
			llamadaAjax('Reglas.do','cargarReglasTotal','segmentacionReglasJSON',entrada,callBackObtenerDatos,false);		
		else
		{
			if (Ext.get('rbReg').dom.checked == true && Ext.get('rbSeg').dom.checked == false ){//Llamada al servicio para reglas
				entrada.ultimaBusquedaTipo = "reglas";
				ultimaBusqueda.add('entradaBusqueda',entrada);
				llamadaAjax('Reglas.do','cargarReglas','segmentacionReglasJSON',entrada,callBackObtenerDatos,false);
			}
			else if (Ext.get('rbReg').dom.checked == false && Ext.get('rbSeg').dom.checked == true){ //Llamada al servicio para segmentos
				entrada.ultimaBusquedaTipo = "segmentos";
				ultimaBusqueda.add('entradaBusqueda',entrada);
				llamadaAjax('Reglas.do','cargarReglasSeg','segmentacionReglasJSON',entrada,callBackObtenerDatos,false);
			}
			else if (Ext.get('rbReg').dom.checked == false && Ext.get('rbSeg').dom.checked == false){ //No hay ningun radio button seleccionado
				entrada.ultimaBusquedaTipo = "todos";
				ultimaBusqueda.add('entradaBusqueda',entrada);
				llamadaAjax('Reglas.do','cargarReglasTotal','segmentacionReglasJSON',entrada,callBackObtenerDatos,false);
			}
		}	
	}

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
		llamadaAjax('Reglas.do','obtenerUsuario','segmentacionReglasJSON',datosUsuario,callBackObtenerUsu,false);	
    }


	//Funcion que imprime los datos de la pantalla en pdf
	var imprimir = function() {

		if(ultimaBusqueda.get('hayResultados')){
			if (Ext.isIE){
				myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
				myMask.show();
			}
			document.getElementById('ifExportar').src = contexto + 'Reglas.do?exportarPDF=&segmentacionReglasJSON='+Ext.encode(ultimaBusqueda.get('entradaBusqueda'));
			
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

	
	//Funcion que pinta el grid de registros de Reglas
	var pintarGrid = function (){

		// create the data store
		var store = new Ext.data.Store({
			reader: new Ext.data.ObjectReader({}, [
			                                       
			                                       {name: 'regla', type: 'string'},
			                                       {name: 'segmentoOrigen', type: 'string'},
			                                       {name: 'reglaDes', type: 'string'},
			                                       {name: 'NLinFija', type: 'string'},     
			                                       {name: 'NLinMov', type: 'string'},
			                                       {name: 'NTotalLin', type: 'string'},
			                                       {name: 'NDias', type: 'string'},
			                                       {name: 'segmento', type: 'string'},
			                                       {name: 'segmentoDes', type: 'string'},
			                                       {name: 'subSegmento', type: 'string'},
			                                       {name: 'subSegmentoDes', type: 'string'},
			                                       {name: 'ofAtencion', type: 'string'},
			                                       {name: 'tandem', type: 'string'},
			                                       {name: 'fechaIniVigencia', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'fechaFinVigencia', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'usuAlta', type: 'string'},
			                                       {name: 'fechaModRegla', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'fechaModSegmento',type:'string',sortType:convertirEnFecha},
			                                       {name: 'usuModRegla',type:'string'},
			                                       {name: 'usuModSegmento',type:'string'}
			                                       ])
		});
		var columnModel = new Ext.grid.ColumnModel([
		                                            {header: 'Regla',sortable: true,  width: 56, dataIndex: 'regla',renderer:addTooltip},
		                                            {header: 'N.Lin.Fija',sortable: true, width: 56,  dataIndex: 'NLinFija'},
		                                            {header: 'N.Lin.M&oacute;v.',sortable: true, width: 61,  dataIndex: 'NLinMov'},
		                                            {header: 'N.Tot.Lin.',sortable: true,  width: 57, dataIndex: 'NTotalLin'},
		                                            {header: 'N.D&iacute;as',sortable: true,  width: 57, dataIndex: 'NDias'},
		                                            {header: 'Seg.',sortable: true,  width: 47, dataIndex: 'segmento',renderer:addTooltip},
		                                            {header: 'Subseg.',sortable: true, width: 32,  dataIndex: 'subSegmento',renderer:addTooltip},
		                                            {header: 'Of. Atenc.',sortable: true,  width: 54, dataIndex: 'ofAtencion'},
		                                            {header: 'T&aacute;ndem',sortable: true, width: 49, dataIndex: 'tandem'},
		                                            {header: 'F.Ini.Vig.', sortable: true, width: 62, align:'center', dataIndex: 'fechaIniVigencia'},
		                                            {header: 'F.Fin Vig.', sortable: true, width: 62, align:'center', dataIndex: 'fechaFinVigencia'},
		                                            {header: 'Us. Alta',sortable: true,  width: 49, align:'center', dataIndex: 'usuAlta'},
		                                            {header: 'F.Mod.Regla', sortable: true, width: 65, align:'center', dataIndex: 'fechaModRegla'},
		                                            {header: 'F.Mod.Seg', sortable: true, width: 64, align:'center', dataIndex: 'fechaModSegmento'},
		                                            {header: 'Us.Mod.Regla',sortable: true, width: 77,  align:'center', dataIndex: 'usuModRegla'},
		                                            {header: 'Us.Mod.Seg.',sortable: true,  width: 75, align:'center', dataIndex: 'usuModSegmento'}
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
			var dFechaHoy = new Date();
			dFechaInicio = Date.parseDate(filaSeleccionada.fechaIniVigencia, 'd.m.Y');
			dFechaFin = Date.parseDate(filaSeleccionada.fechaFinVigencia, 'd.m.Y');
			
//			if (dFechaInicio < dFechaHoy && dFechaHoy < dFechaFin){
				//Habilitamos el boton Modificar	
				deshabilitarEstadoBotonModif(false);
/*			}
			else {
				//Deshabilitamos el boton Modificar	
				deshabilitarEstadoBotonModif(true);
			}*/		
			</sec:authorize>
		}
		
		// create the Grid
		grid = new Ext.grid.EditorPasteCopyGridPanel({
			id:'gridBuscadorReglas',
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
			height: 177
		});
		if (Ext.isIE6){
			anchoContenido = document.body.offsetWidth - 40;
			grid.setWidth (anchoContenido);
		}
	}
	
	//Pintar grid Historico de Reglas
	var pintarGridHistorico = function (){

		// create the data store
		var store = new Ext.data.Store({
			reader: new Ext.data.ObjectReader({}, [
			                                       {name: 'regla', type: 'string'},
			                                       {name: 'segmentoOrigen', type: 'string'},
			                                       {name: 'reglaDes', type: 'string'},
			                                       {name: 'NLinFija', type: 'string'},     
			                                       {name: 'NLinMov', type: 'string'},
			                                       {name: 'NTotalLin', type: 'string'},
			                                       {name: 'NDias', type: 'string'},
			                                       {name: 'segmento', type: 'string'},
			                                       {name: 'segmentoDes', type: 'string'},
			                                       {name: 'subSegmento', type: 'string'},
			                                       {name: 'subSegmentoDes', type: 'string'},
			                                       {name: 'ofAtencion', type: 'string'},
			                                       {name: 'tandem', type: 'string'},
			                                       {name: 'fechaIniVigencia', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'fechaFinVigencia', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'usuAlta', type: 'string'},
			                                       {name: 'fechaModRegla', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'fechaModSegmento',type:'string',sortType:convertirEnFecha},
			                                       {name: 'usuModRegla',type:'string'},
			                                       {name: 'usuModSegmento',type:'string'}
			                                       ])
		});
		var columnModel = new Ext.grid.ColumnModel([
		                                            {header: 'Regla',sortable: true,  width: 56, dataIndex: 'regla',renderer:addTooltip},
		                                            {header: 'N.Lin.Fija',sortable: true, width: 56,  dataIndex: 'NLinFija'},
		                                            {header: 'N.Lin.M&oacute;v.',sortable: true, width: 61,  dataIndex: 'NLinMov'},
		                                            {header: 'N.Tot.Lin.',sortable: true,  width: 57, dataIndex: 'NTotalLin'},
		                                            {header: 'N.D&iacute;as',sortable: true,  width: 57, dataIndex: 'NDias'},
		                                            {header: 'Seg.',sortable: true,  width: 47, dataIndex: 'segmento',renderer:addTooltip},
		                                            {header: 'Subseg.',sortable: true, width: 32,  dataIndex: 'subSegmento',renderer:addTooltip},
		                                            {header: 'Of. Atenc.',sortable: true,  width: 54, dataIndex: 'ofAtencion'},
		                                            {header: 'T&aacute;ndem',sortable: true, width: 49, dataIndex: 'tandem'},
		                                            {header: 'F.Ini.Vig.', sortable: true, width: 62, align:'center', dataIndex: 'fechaIniVigencia'},
		                                            {header: 'F.Fin Vig.', sortable: true, width: 62, align:'center', dataIndex: 'fechaFinVigencia'},
		                                            {header: 'Us. Alta',sortable: true,  width: 49, align:'center', dataIndex: 'usuAlta'},
		                                            {header: 'F.Mod.Regla', sortable: true, width: 65, align:'center', dataIndex: 'fechaModRegla'},
		                                            {header: 'F.Mod.Seg', sortable: true, width: 64, align:'center', dataIndex: 'fechaModSegmento'},
		                                            {header: 'Us.Mod.Regla',sortable: true, width: 77,  align:'center', dataIndex: 'usuModRegla'},
		                                            {header: 'Us.Mod.Seg.',sortable: true,  width: 75, align:'center', dataIndex: 'usuModSegmento'}
		                                            ]);
		var cargarDatosFilaHist = function(fila){ 
			Ext.each(Ext.query('.marcarFila', Ext.getCmp('gridHistoricoReglas').id), function(filaMarcada) {
				Ext.fly(filaMarcada).removeClass('marcarFila');
			});
			Ext.fly(Ext.getCmp('gridHistoricoReglas').getView().getRow(fila)).addClass('marcarFila');
			
			filaSeleccionadaHist = Ext.getCmp('gridHistoricoReglas').getStore().data.items[fila].data;						
		}
		
	    // create the Grid
	    gridHist = new Ext.grid.EditorPasteCopyGridPanel({
	    	id:'gridHistoricoReglas',
	        store: store,
			renderTo: 'divGridHistorico',
			cls:'gridCG',
			selectionModel: '',
			funcionRollBack: cargarDatosFilaHist,
			cm: columnModel,
	        stripeRows: true,
	        height: 177,
	        autoWidth:true
	    });
	    
	}
	
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
	var deshabilitarEstadoBotonModif = function(estado){
		Ext.get('btnModificar').dom.disabled = estado
		if(estado){
			Ext.get('btnModificar').dom.src = contexto + 'images/botones/QGbtModificar_des.gif';	
			Ext.get('btnModificar').addClass('btnDis'); 

		}else{
			Ext.get('btnModificar').removeClass('btnDis'); 
			Ext.get('btnModificar').dom.src = contexto + 'images/botones/QGbtModificar.gif';
		}
	}
	</sec:authorize>

	//Función para mostrar los tooltip de segmento y subsegmento
	function addTooltip(value,metadata, record, rowIndex, colIndex, store){
	   	//En record viene el elemento formado del grid
		var valorTooltip = "";
		//Dependiendo de la columna que estemos renderizando se metera un valor de descripcion u otro
		//Columna de regla
		if(colIndex == 0){
			valorTooltip = record.data.reglaDes;
		}						
		else if(colIndex == 5){
			valorTooltip = record.data.segmentoDes;
		
		}
		else if(colIndex == 6){
	 		valorTooltip = record.data.subSegmentoDes;
	 	}
	 		 	
		//Accedemos al tooltip
		if(valorTooltip != null && valorTooltip != ''){
			metadata.attr = 'ext:qtip="' + valorTooltip +'"';
		}
		//Debe devolver el valor para rellenar la columna
		return value;
     }
	
	/*
	var cargaComboTotalesSuma = function(suma){
		for (var i=0;i< Ext.get('selLinTot').dom.options.length;i++){
			if (Ext.get('selLinTot').dom.options[i].text == suma.toString()){
				Ext.get('selLinTot').dom.value = suma.toString();
				Ext.get('selLinTot').dom.selectedIndex = i;
			}
		}
	}
	*/

	//Accdemos a la ventana de contratos
	var verContratos = function (){		
		Ext.get('idMethod').set({
			name:'irContratos'
		});
		
		Ext.get('hiddenEntrada').dom.value = Ext.encode(ultimaBusqueda.get('entradaBusqueda'));
		Ext.get('hiddenOrigen').dom.value = "llamadaReglas";
		irContratos();
	} 

	// Controlamos los eventos sobre los botones
	var controlEventos = function() {
		
		window.onresize = function() {
			refrescarGrid.defer(20);
		}
		
		//evento click del botón Contratos
/*		Ext.get('btnContratos').on('click', function() {
			verContratos();
		});
*/
		Ext.get('selCodRegla').on('change', function(){
			Ext.get('txtDescripcionRegla').dom.value = Ext.get('selCodRegla').dom.options[Ext.get('selCodRegla').dom.selectedIndex].value;
		});
		//Si cambia el combo de lineas fijas
		/*
		Ext.get('selLinFix').on('change', function(){	
			if (Ext.get('selLinFix').dom.options[Ext.get('selLinFix').dom.selectedIndex].text != "Todas" &&
				Ext.get('selLinMov').dom.options[Ext.get('selLinMov').dom.selectedIndex].text != "Todas"){
				
				var suma = parseInt(Ext.get('selLinFix').dom.options[Ext.get('selLinFix').dom.selectedIndex].text) +
						   parseInt(Ext.get('selLinMov').dom.options[Ext.get('selLinMov').dom.selectedIndex].text);
			
				cargaComboTotalesSuma(suma);
			}
		});	
		
		Ext.get('selLinMov').on('change', function(){	
			if (Ext.get('selLinFix').dom.options[Ext.get('selLinFix').dom.selectedIndex].text != "Todas" &&
				Ext.get('selLinMov').dom.options[Ext.get('selLinMov').dom.selectedIndex].text != "Todas"){
				
				var suma = parseInt(Ext.get('selLinFix').dom.options[Ext.get('selLinFix').dom.selectedIndex].text) +
						   parseInt(Ext.get('selLinMov').dom.options[Ext.get('selLinMov').dom.selectedIndex].text);
				
				cargaComboTotalesSuma(suma);
			}
		});
		*/
		//carga el campo de texto con la descripcion del segmento seleccionado, activa "Subsegmento" si es distinto de TODOS
		Ext.get('selCodSeg').on('change', function(){			 
			Ext.get('txtDescripcionSeg').dom.value = Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].value;
			if (Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].value == "Todos"){
				Ext.get('selCodSubSeg').dom.disabled=true;
				Ext.get('selCodSubSeg').dom.selectedIndex = 0;			
			}
			else
			{
				Ext.get('selCodSubSeg').dom.disabled=false;						
				cargarSubsegmento(Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].text);
			}					  
		});
		//al seleccionar un elemento en "Subsegmento", se activa "Of.Atención" si es distinto de TODOS		
		Ext.get('selCodSubSeg').on('change', function(){			 
			Ext.get('txtDescripcionSubSeg').dom.value = Ext.get('selCodSubSeg').dom.options[Ext.get('selCodSubSeg').dom.selectedIndex].value;
			if (Ext.get('selCodSubSeg').dom.options[Ext.get('selCodSubSeg').dom.selectedIndex].value == "Todos"){
				Ext.get('selOfAtenc').dom.disabled=true;
				Ext.get('selOfAtenc').dom.selectedIndex = 0;			
			}
			else
			{
				Ext.get('selOfAtenc').dom.disabled=false;
				cargarOfAtencion(Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].text,
				Ext.get('selCodSubSeg').dom.options[Ext.get('selCodSubSeg').dom.selectedIndex].text);			
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
							 Ext.get('selCodSubSeg').dom.options[Ext.get('selCodSubSeg').dom.selectedIndex].text,
							 Ext.get('selOfAtenc').dom.options[Ext.get('selOfAtenc').dom.selectedIndex].text);
			}
		});
		
		//evento click del botón Imprimir
		//Ext.get('btnImprimir').on('click', function() {
			//imprimir();
		//});
			
		//evento click del boton Filtrar
		Ext.get('btnVerTodos').on('click', function() {
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
			deshabilitarEstadoBotonModif(true);
			</sec:authorize>

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
			
			ultimaBusqueda.add('entradaBusqueda',entrada);
						
			//Llamada al método para cargar la tabla
			llamadaAjax('Reglas.do','cargarReglasTotal','segmentacionReglasJSON',entrada,callBackObtenerDatos,false);
		});
		

		//boton Historico
		/*
		Ext.get('btnHistorico').on('click', function() {

			Ext.get('divHistorico').setVisibilityMode(Ext.Element.DISPLAY).show();
			Ext.get('divPrincipal').setVisibilityMode(Ext.Element.DISPLAY).hide();
			Ext.get('divTituloPrincipal').setVisibilityMode(Ext.Element.DISPLAY).hide();
			Ext.get('divTituloHistorico').setVisibilityMode(Ext.Element.DISPLAY).show();
			
			if(!Ext.getCmp('gridHistoricoReglas')){
				pintarGridHistorico();
			
		   		var entrada = {
					codActuacion: "H",
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
					ultimaBusquedaTipo: ""
				};
				
				cargaHistorico = true;
				
				//Llamada al método para cargar la tabla historico
				obtenerDatos(entrada);							
			}	
		});
		*/
		//boton Volver Historico
		Ext.get('btnVolver').on('click', function() {

			Ext.get('divHistorico').setVisibilityMode(Ext.Element.DISPLAY).hide();
			Ext.get('divPrincipal').setVisibilityMode(Ext.Element.DISPLAY).show();
			
			Ext.get('divTituloPrincipal').setVisibilityMode(Ext.Element.DISPLAY).show();
			Ext.get('divTituloHistorico').setVisibilityMode(Ext.Element.DISPLAY).hide();
			
			Ext.getCmp('gridHistoricoReglas').hide();
			Ext.getCmp('gridHistoricoReglas').destroy();
			
			cargaHistorico = false;
		});	 
		
		Ext.get('rbReg').on('click', function() {
			habilitarReglas();
			deshabilitarSegmentacion();
		});
		
		Ext.get('rbSeg').on('click', function() {
			deshabilitarReglas();
			habilitarSegmentacion();
		});
		
		Ext.get('btnFiltrar').on('click', function() {
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
			deshabilitarEstadoBotonModif(true);
			</sec:authorize>
			//Llamada al método para cargar la tabla según el critero elegido
			obtenerDatos(crearEntradaBusqueda());
		});
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		
		// boton modificar
		Ext.get('btnModificar').on('click', function() {
			abrirPopUpGestionRegla("Modificar Regla");
			
			modificarReg = true;
			
			regSeleccionado = {};
			regSeleccionado.segmento = filaSeleccionada.segmento;
			regSeleccionado.segmentoDes = filaSeleccionada.segmentoDes;
			regSeleccionado.subSegmento = filaSeleccionada.subSegmento;
			regSeleccionado.subSegmentoDes = filaSeleccionada.subSegmentoDes;
			
			regSeleccionado.regla = filaSeleccionada.regla;
			regSeleccionado.NLinFija = filaSeleccionada.NLinFija;
			regSeleccionado.NLinMov = filaSeleccionada.NLinMov;
			regSeleccionado.NTotalLin = filaSeleccionada.NTotalLin;
			regSeleccionado.NDias = filaSeleccionada.NDias;

			regSeleccionado.tandem = filaSeleccionada.tandem;
			regSeleccionado.ofAtencion = filaSeleccionada.ofAtencion;
			
			regSeleccionado.fechaIniVigencia = filaSeleccionada.fechaIniVigencia;
			regSeleccionado.fechaFinVigencia = filaSeleccionada.fechaFinVigencia;			
						
			CGLOBALGESTREGSEG.inicioFiltro();
			CGLOBALGESTREGSEG.iniciarCombos("modif");
		});		
		</sec:authorize>

		if (Ext.isIE){
			document.getElementById('ifExportar').onreadystatechange = function(){myMask.hide();};
		}
		if (Ext.isIE6){ //Se usa para que en IE6 no se superponga los combos al menu
			Ext.get('liAdministracion').on('mouseover', function() {
				//Ext.get('selLinTot').setVisibilityMode(Ext.Element.DISPLAY).hide();
				Ext.get('selDias').setVisibilityMode(Ext.Element.DISPLAY).hide();
			});
			Ext.get('liAdministracion').on('mouseout', function() {
				//Ext.get('selLinTot').setVisibilityMode(Ext.Element.DISPLAY).show();
				Ext.get('selDias').setVisibilityMode(Ext.Element.DISPLAY).show();
			});
			
			Ext.get('liSegmentacion').on('mouseover', function() {
				Ext.get('selCodSeg').setVisibilityMode(Ext.Element.DISPLAY).hide();
				Ext.get('selCodSubSeg').setVisibilityMode(Ext.Element.DISPLAY).hide();
			});
			Ext.get('liSegmentacion').on('mouseout', function() {
				Ext.get('selCodSeg').setVisibilityMode(Ext.Element.DISPLAY).show();
				Ext.get('selCodSubSeg').setVisibilityMode(Ext.Element.DISPLAY).show();
			});
		}
	}
	
	<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
	//Funcion que abre el popUp de la ventana de Alta-Modificacion
	var abrirPopUpGestionRegla = function(titulo){
		var win = Ext.getCmp('winAnadirSegmentos');
		
		if(!win){
			win = new Ext.Window({
				id:'winAnadirSegmentos',
				contentEl:'popUpAnadirRegla',
				title:titulo,
				width:945,
				height:255,
				closeAction:'hide',
				closable: true,
				animateTarget:'btnModificar',
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
						return CGLOBALGESTREGSEG.cancelar();
					}
				}
			});
		}
		
		win.show();
		//Maximinizamos la ventana por si fue minimizada al cerrarse
		maximizarVentana('winAnadirSegmentos');
	}
	</sec:authorize>
	
	//Opciones de Busqueda segun criterios
	var crearEntradaBusqueda = function(){
			//Introducimos los valores de entrada al servicio
			var entrada = {
				codActuacion: "C",
				segmento: Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].text,
				subSegmento: Ext.get('selCodSubSeg').dom.options[Ext.get('selCodSubSeg').dom.selectedIndex].text,
				ofAtencion: Ext.get('selOfAtenc').dom.options[Ext.get('selOfAtenc').dom.selectedIndex].text,
				tandem: Ext.get('selTandem').dom.options[Ext.get('selTandem').dom.selectedIndex].text,
				regla: Ext.get('selCodRegla').dom.options[Ext.get('selCodRegla').dom.selectedIndex].text,
				//NLinFija: Ext.get('selLinFix').dom.options[Ext.get('selLinFix').dom.selectedIndex].text,				
				NLinFija: Ext.get('txtLinFix').dom.value,
				//NLinMovil: Ext.get('selLinMov').dom.options[Ext.get('selLinMov').dom.selectedIndex].text,
				NLinMovil: Ext.get('txtLinMov').dom.value,
				//NTotalLin: Ext.get('selLinTot').dom.options[Ext.get('selLinTot').dom.selectedIndex].text,
				NTotalLin: Ext.get('txtLinTot').dom.value,				
				NDias: Ext.get('selDias').dom.options[Ext.get('selDias').dom.selectedIndex].text
			};
			//Cambiamos los valores si es "Tod@s" y enviamos "" (vacío)
			if (entrada.segmento == "Todos"){
				entrada.segmento = "";
			}
			if (entrada.subSegmento == "Todos"){
				entrada.subSegmento = "";
			}			
			if (entrada.ofAtencion == "Todos"){
				entrada.ofAtencion = "";
			}
			if (entrada.tandem == "Todos"){
				entrada.tandem = "";
			}
			if (entrada.regla == "Todas"){
				entrada.regla = "";
			}
			/*
			if (entrada.NLinFija == "Todas"){
				entrada.NLinFija = "";
			}
			if (entrada.NLinMovil == "Todas"){
				entrada.NLinMovil = "";
			}
			if (entrada.NTotalLin == "Todas"){
				entrada.NTotalLin = "";
			}
			*/
			if (entrada.NDias == "Todos"){
				entrada.NDias = "";
			}
			
			//Valor para tipoConsulta
			//-----------------------
			if (entrada.regla != ""){
				entrada.tipoConsulta = "01"; 
			}
			if (entrada.regla == "" && entrada.NLinFija != "" && 
				entrada.NLinMovil == "" && entrada.NTotalLin == "" && entrada.NDias == ""){
				entrada.tipoConsulta = "02"; 
			}
			if (entrada.regla == "" && entrada.NLinFija == "" && 
				entrada.NLinMovil != "" && entrada.NTotalLin == "" && entrada.NDias == ""){
				entrada.tipoConsulta = "03"; 
			}
			if (entrada.regla == "" && entrada.NLinFija == "" && 
				entrada.NLinMovil == "" && entrada.NTotalLin != "" && entrada.NDias == ""){
				entrada.tipoConsulta = "04";
			}
			if (entrada.regla == "" && entrada.NLinFija == "" && 
				entrada.NLinMovil == "" && entrada.NTotalLin == "" && entrada.NDias != ""){
				entrada.tipoConsulta = "05"; 
			}
			if (entrada.regla == "" && entrada.NLinFija != "" && 
				entrada.NLinMovil != "" && entrada.NTotalLin == "" && entrada.NDias == ""){
				entrada.tipoConsulta = "06"; 
			}
			if (entrada.regla == "" && entrada.NLinFija != "" && 
				entrada.NLinMovil == "" && entrada.NTotalLin == "" && entrada.NDias != ""){
				entrada.tipoConsulta = "07";  
			}
			if (entrada.regla == "" && entrada.NLinFija == "" && 
				entrada.NLinMovil != "" && entrada.NTotalLin == "" && entrada.NDias != ""){
				entrada.tipoConsulta = "08"; 
			}
			if (entrada.regla == "" && entrada.NLinFija == "" && 
				entrada.NLinMovil == "" && entrada.NTotalLin != "" && entrada.NDias != ""){
				entrada.tipoConsulta = "09"; 
			}
			if (entrada.segmento != "" && entrada.subSegmento == "" && 
				entrada.ofAtencion == "" && entrada.tandem == ""){
				entrada.tipoConsulta = "10"; 
			}
			if (entrada.segmento != "" && entrada.subSegmento != "" && 
				entrada.ofAtencion == "" && entrada.tandem == ""){
				entrada.tipoConsulta = "11"; 
			}
			if (entrada.segmento != "" && entrada.subSegmento != "" && 
				entrada.ofAtencion != "" && entrada.tandem == ""){
				entrada.tipoConsulta = "12"; 
			}
			if (entrada.segmento != "" && entrada.subSegmento != "" && 
				entrada.ofAtencion != "" && entrada.tandem != ""){
				entrada.tipoConsulta = "13"; 
			}

			//Cambiamos las variables regla y segmento a null, dependiendo del radioButton seleccionado
			if (Ext.get('rbReg').dom.checked == true){
				entrada.segmento = null;
			}
			else if (Ext.get('rbSeg').dom.checked == true){
				entrada.regla = null;
			}


		return entrada;
	}

	var inicioFiltro = function (){
	
		//Parte Reglas
		Ext.get('selCodRegla').dom.disabled=true;
		Ext.get('selCodRegla').dom.selectedIndex = 0;
	
		Ext.get('txtDescripcionRegla').dom.value = "";
		/*
		Ext.get('selLinFix').dom.disabled=true;
		Ext.get('selLinFix').dom.selectedIndex = 0;
		*/
		Ext.get('txtLinFix').dom.value='';
		Ext.get('txtLinFix').dom.readOnly = true;
		Ext.get('txtLinFix').addClass('dis');		
		/*
		Ext.get('selLinMov').dom.disabled=true;
		Ext.get('selLinMov').dom.selectedIndex = 0;
		*/
		Ext.get('txtLinMov').dom.value='';
		Ext.get('txtLinMov').dom.readOnly = true;
		Ext.get('txtLinMov').addClass('dis');
		/*
		Ext.get('selLinTot').dom.disabled=true;
		Ext.get('selLinTot').dom.selectedIndex = 0;
		*/
		Ext.get('txtLinTot').dom.value='';
		Ext.get('txtLinTot').dom.readOnly = true;
		Ext.get('txtLinTot').addClass('dis');		
		
		Ext.get('selDias').dom.disabled=true;
		Ext.get('selDias').dom.selectedIndex = 0;
		
		//Parte Segmentacion
		Ext.get('selCodSeg').dom.disabled=true;
		Ext.get('selCodSeg').dom.selectedIndex = 0;
		
		Ext.get('selCodSubSeg').dom.disabled=true;
		Ext.get('selCodSubSeg').dom.selectedIndex = 0;
		
		Ext.get('txtDescripcionSeg').dom.value = "";
		Ext.get('txtDescripcionSubSeg').dom.value = "";
		
		Ext.get('selOfAtenc').dom.disabled=true;
		Ext.get('selOfAtenc').dom.selectedIndex = 0;
		
		Ext.get('selTandem').dom.disabled=true;
		Ext.get('selTandem').dom.selectedIndex = 0;
		
		Ext.get('rbReg').dom.checked = false;
		Ext.get('rbSeg').dom.checked = false;	
	}

	var habilitarReglas = function (){
		Ext.get('selCodRegla').dom.disabled=false;
		Ext.get('selCodRegla').dom.selectedIndex = 0;
	
		Ext.get('txtDescripcionRegla').dom.value = "";
		/*
		Ext.get('selLinFix').dom.disabled=false;
		Ext.get('selLinFix').dom.selectedIndex = 0;
		*/
		Ext.get('txtLinFix').dom.value = "";
		Ext.get('txtLinFix').dom.readOnly = false;
		Ext.get('txtLinFix').removeClass('dis');		
		/*
		Ext.get('selLinMov').dom.disabled=false;
		Ext.get('selLinMov').dom.selectedIndex = 0;
		*/
		Ext.get('txtLinMov').dom.value = "";
		Ext.get('txtLinMov').dom.readOnly = false;
		Ext.get('txtLinMov').removeClass('dis');		
		/*
		Ext.get('selLinTot').dom.disabled=false;
		Ext.get('selLinTot').dom.selectedIndex = 0;
		*/
		Ext.get('txtLinTot').dom.value = "";
		Ext.get('txtLinTot').dom.readOnly = false;
		Ext.get('txtLinTot').removeClass('dis');		
		
		Ext.get('selDias').dom.disabled=false;
		Ext.get('selDias').dom.selectedIndex = 0;
		
		Ext.get('rbReg').dom.checked = true;
		Ext.get('rbSeg').dom.checked = false;
	}
	
	var deshabilitarReglas = function (){
		Ext.get('selCodRegla').dom.disabled=true;
		Ext.get('selCodRegla').dom.selectedIndex = 0;
	
		Ext.get('txtDescripcionRegla').dom.value = "";
		/*
		Ext.get('selLinFix').dom.disabled=true;
		Ext.get('selLinFix').dom.selectedIndex = 0;
		*/
		Ext.get('txtLinFix').dom.value = "";
		Ext.get('txtLinFix').dom.readOnly = true;
		Ext.get('txtLinFix').addClass('dis');		
		/*
		Ext.get('selLinMov').dom.disabled=true;
		Ext.get('selLinMov').dom.selectedIndex = 0;
		*/
		Ext.get('txtLinMov').dom.value = "";
		Ext.get('txtLinMov').dom.readOnly = true;
		Ext.get('txtLinMov').addClass('dis');		
		/*
		Ext.get('selLinTot').dom.disabled=true;
		Ext.get('selLinTot').dom.selectedIndex = 0;
		*/
		Ext.get('txtLinTot').dom.value = "";
		Ext.get('txtLinTot').dom.readOnly = true;
		Ext.get('txtLinTot').addClass('dis');
				
		Ext.get('selDias').dom.disabled=true;
		Ext.get('selDias').dom.selectedIndex = 0;
		
		Ext.get('rbReg').dom.checked = true;
		Ext.get('rbSeg').dom.checked = false;	
	}
	
	var habilitarSegmentacion = function (){
		Ext.get('selCodSeg').dom.disabled=false;
		Ext.get('selCodSeg').dom.selectedIndex = 0;
		
		Ext.get('selCodSubSeg').dom.disabled=true;
		
		var arrayCombo = new Array();
 		arrayCombo.push({texto: "Todos", valor: ""});
		agregarValoresCombo('selCodSubSeg', arrayCombo, true);
		
		Ext.get('selCodSubSeg').dom.selectedIndex = 0;
		
		Ext.get('txtDescripcionSeg').dom.value = "";
		Ext.get('txtDescripcionSubSeg').dom.value = "";
		
		Ext.get('selOfAtenc').dom.disabled=true;
		Ext.get('selOfAtenc').dom.selectedIndex = 0;
		
		Ext.get('selTandem').dom.disabled=true;
		Ext.get('selTandem').dom.selectedIndex = 0;
		
		Ext.get('rbReg').dom.checked = false;
		Ext.get('rbSeg').dom.checked = true;	
	}
	
	var deshabilitarSegmentacion = function (){
		Ext.get('selCodSeg').dom.disabled=true;
		Ext.get('selCodSeg').dom.selectedIndex = 0;
		
		Ext.get('selCodSubSeg').dom.disabled=true;
		
		var arrayCombo = new Array();
 		arrayCombo.push({texto: "Todos", valor: ""});
		agregarValoresCombo('selCodSubSeg', arrayCombo, true);
		
		Ext.get('selCodSubSeg').dom.selectedIndex = 0;
		
		Ext.get('txtDescripcionSeg').dom.value = "";
		Ext.get('txtDescripcionSubSeg').dom.value = "";
		
		Ext.get('selOfAtenc').dom.disabled=true;
		Ext.get('selOfAtenc').dom.selectedIndex = 0;
		arrayCombo.push({texto: "Todos", valor: ""});
		agregarValoresCombo('selOfAtenc', arrayCombo, true);
		
		Ext.get('selTandem').dom.disabled=true;
		Ext.get('selTandem').dom.selectedIndex = 0;	
		arrayCombo.push({texto: "Todos", valor: ""});
		agregarValoresCombo('selTandem', arrayCombo, true);
	}
	

	//Inicializa los campos de busqueda
	var inicializarCampos = function(){	
		//Carga combo de Reglas
		
		cargarCodigoSegmento("01","busqueda");
		
		cargarReglas();
		cargarLineasFix();
		cargarLineasMov();
		cargarTotalLin();
		cargarDias();

		inicioFiltro();
	}
	
	//Funciones para marcar los datos en los combos a la vuelta de Contratos
	var marcarRegla = function(valor){
		
		for (var i=0;i< Ext.get('selCodRegla').dom.options.length;i++){
			if (Ext.get('selCodRegla').dom.options[i].text == valor){
				Ext.get('selCodRegla').dom.value = valor;
				Ext.get('selCodRegla').dom.selectedIndex = i;
			}
		}
		
		Ext.get('txtDescripcionRegla').dom.value =  Ext.get('selCodRegla').dom.options[Ext.get('selCodRegla').dom.selectedIndex].value;
		
	}
	
	
	var marcarSegmento = function(valor){
		
		for (var i=0;i< Ext.get('selCodSeg').dom.options.length;i++){
			if (Ext.get('selCodSeg').dom.options[i].text == valor){
				Ext.get('selCodSeg').dom.value = valor;
				Ext.get('selCodSeg').dom.selectedIndex = i;
			}
		}
		
		Ext.get('txtDescripcionSeg').dom.value =  Ext.get('selCodSeg').dom.options[Ext.get('selCodSeg').dom.selectedIndex].value;
		
	}
	
	var marcarSubsegmento = function(valor){
		
		for (var i=0;i< Ext.get('selCodSubSeg').dom.options.length;i++){
			if (Ext.get('selCodSubSeg').dom.options[i].text == valor){
				Ext.get('selCodSubSeg').dom.value = valor;
				Ext.get('selCodSubSeg').dom.selectedIndex = i;
			}
		}
		
		Ext.get('txtDescripcionSubSeg').dom.value =  Ext.get('selCodSubSeg').dom.options[Ext.get('selCodSubSeg').dom.selectedIndex].value;
		
	}	
	
	var marcarOfAtencion = function(valor){
		
		for (var i=0;i< Ext.get('selOfAtenc').dom.options.length;i++){
			if (Ext.get('selOfAtenc').dom.options[i].text == valor){
				Ext.get('selOfAtenc').dom.value = valor;
				Ext.get('selOfAtenc').dom.selectedIndex = i;
			}
		}
	}
	
	var marcarTandem = function(valor){
		
		for (var i=0;i< Ext.get('selTandem').dom.options.length;i++){
			if (Ext.get('selTandem').dom.options[i].text == valor){
				Ext.get('selTandem').dom.value = valor;
				Ext.get('selTandem').dom.selectedIndex = i;
			}
		}
	}
	
	var marcarLineasFix = function(valor){
		/*
		for (var i=0;i< Ext.get('selLinFix').dom.options.length;i++){
			if (Ext.get('selLinFix').dom.options[i].text == valor){
				Ext.get('selLinFix').dom.value = valor;
				Ext.get('selLinFix').dom.selectedIndex = i;
			}
		}
		*/
		Ext.get('txtLinFix').dom.value = valor;
	}
	
	var marcarLineasMov = function(valor){
		/*
		for (var i=0;i< Ext.get('selLinMov').dom.options.length;i++){
			if (Ext.get('selLinMov').dom.options[i].text == valor){
				Ext.get('selLinMov').dom.value = valor;
				Ext.get('selLinMov').dom.selectedIndex = i;
			}
		}
		*/
		Ext.get('txtLinMov').dom.value = valor;
	}
	
	var marcarLineasTot = function(valor){
		/*
		for (var i=0;i< Ext.get('selLinTot').dom.options.length;i++){
			if (Ext.get('selLinTot').dom.options[i].text == valor){
				Ext.get('selLinTot').dom.value = valor;
				Ext.get('selLinTot').dom.selectedIndex = i;
			}
		}
		*/
		Ext.get('txtLinTot').dom.value = valor;
	}
	
	var marcarDias = function(valor){
		
		for (var i=0;i< Ext.get('selDias').dom.options.length;i++){
			if (Ext.get('selDias').dom.options[i].text == valor){
				Ext.get('selDias').dom.value = valor;
				Ext.get('selDias').dom.selectedIndex = i;
			}
		}
	}

	//Cargamos los datos del combo guardados antes de la llamada a Contratos
	var cargarFiltro = function(){
		
		if (entradaGuardada.regla != null){
			habilitarReglas();
			deshabilitarSegmentacion();
			
			marcarRegla(entradaGuardada.regla);
			marcarLineasFix(entradaGuardada.NLinFija);
			marcarLineasMov(entradaGuardada.NLinMovil);
			marcarLineasTot(entradaGuardada.NTotalLin);
			marcarDias(entradaGuardada.NDias);
		}
		else if (entradaGuardada.segmento != null){
		
			deshabilitarReglas();
			habilitarSegmentacion();
		
			marcarSegmento(entradaGuardada.segmento);
			marcarSubsegmento(entradaGuardada.codigoSubsegmento);
			marcarOfAtencion(entradaGuardada.ofAtencion);
			marcarTandem(entradaGuardada.tandem);						
		}
		
		obtenerDatos(entradaGuardada);
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

	return {
		init: function (){
		//Cargamos la mascara para que se carguen los combos		
		Ext.QuickTips.init();
		
		inicializarCampos();
		
		Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		deshabilitarEstadoBotonModif(true);
		</sec:authorize>
		
		controlEventos();
		pintarGrid();
		obtenerUsuario();

		},
		obtenerDatos: function(entrada){
			obtenerDatos(entrada);
		},
		cargarReglas: function(){
			cargarReglas();
		},
		cargarLineasFix: function(){
			cargarLineasFix();
		},
		cargarLineasMov: function(){
			cargarLineasMov();
		},
		cargarTotalLin: function(){
			cargarTotalLin();
		},
		cargarDias: function(){
			cargarDias();
		},
		cargarFechas: function(){
			cargarFechas();
		},		
		cargarCodigoSegmento: function(unidadEntrada,pantalla){
			cargarCodigoSegmento(unidadEntrada,pantalla);
		},
		cargarSubsegmento: function(codSeg){
			cargarSubsegmento(codSeg);
		},
		cargarOfAtencion: function(codSeg,codSub){
			cargarOfAtencion(codSeg,codSub);
		},		
		cargarTandem: function(codSeg,codSub,codOfAt){
			cargarTandem(codSeg,codSub,codOfAt);
		},
		inicializarCampos: function(){
			inicializarCampos();
		}
	}
}();

function obtenerDatos(entrada){
	CGLOBAL.obtenerDatos(entrada);
}

function cargarReglas(){
	CGLOBAL.cargarReglas();
}

function cargarLineasFix(){
	CGLOBAL.cargarLineasFix();
}

function cargarLineasMov(){
	CGLOBAL.cargarLineasMov();
}

function cargarTotalLin(){
	CGLOBAL.cargarTotalLin();
}

function cargarDias(){
	CGLOBAL.cargarDias();
}

function cargarCodigoSegmento(unidadEntrada,pantalla){
	CGLOBAL.cargarCodigoSegmento(unidadEntrada,pantalla);
}

function cargarSubsegmento(codSeg){
	CGLOBAL.cargarSubsegmento(codSeg);
}

function cargarOfAtencion(codSeg,codSub){
	CGLOBAL.cargarOfAtencion(codSeg,codSub);
}

function cargarTandem(codSeg,codSub,codOfAt){
	GLOBAL.cargarTandem(codSeg,codSub,codOfAt);	
}

function cargarFechas(){
	GLOBAL.cargarFechas();	
}

function inicializarCampos(){
	CGLOBAL.inicializarCampos();
}

Ext.onReady (CGLOBAL.init, CGLOBAL, true);
</sec:authorize>