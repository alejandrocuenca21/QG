<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
Ext.ns("CGAUTORIZACIONES");
CGAUTORIZACIONES = function(){
	
	//Funcion PUBLICA para comprobar si existe cambio de pestanias
	var comprobarCambio = function(valor){
		var pesActiva = Ext.query('.activa', 'divPes')[0].id;
		if (pesActiva == 'pesAutorizaciones'){
			if (CGPESAUTORIZ.comprobarCambiosEnPantalla()){	
				llamarPestania(valor);
			}else{
				lanzarPreguntaDatos(valor);
			}
		}
		<sec:authorize ifAnyGranted="ROLE_SA">
		else if (pesActiva == 'pesSisExt'){
			if (CGSISEXT.comprobarCambiosEnPantalla()){
				
				llamarPestania(valor);
			}else{
				lanzarPreguntaDatos(valor);
			}			
		}
		</sec:authorize>
		else if (pesActiva == 'pesServiciosNA'){
			if (CGSRVNA.comprobarCambiosEnPantalla()){
				llamarPestania(valor);
			}else{
				lanzarPreguntaDatos(valor);
			}
		}
	}
	
	//Funcion que llama al metodo correspondiente de cada pestania para mostrarla
	var llamarPestania = function(valor){
		if (valor == 'autorizaciones'){
			mostrarAutorizaciones();
		}
		else if (valor == 'sistemasExternos'){
			mostrarSisExt();
		}
		else if (valor == 'serviciosNA'){
			mostrarServiciosNA();
		}
	}
	
	//Funcion que muestra un mensaje de aviso
	var lanzarPreguntaDatos = function (valor){
		Ext.Msg.show({
		   title:'Los datos se perder&aacute;n',
		   msg: '<span>Se van a perder los datos introducidos, &iquest;Desea continuar?</span>',
		   buttons: Ext.Msg.YESNO,
		   icon: Ext.MessageBox.WARNING,
		   fn:function(respuesta){
		   		if (respuesta == 'yes'){
		   			llamarPestania(valor);
		   		}else{
		   		}
		   }
		});
	}
	
	// Control de eventos
	var controlEventos = function(){
	}
	
    return {
		init: function() {
			Ext.QuickTips.init();
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			
			controlEventos();
		},
		comprobarCambio: function (valor){
			comprobarCambio(valor);
		}	
	}
	 
}();
function comprobarCambio(valor){
	CGAUTORIZACIONES.comprobarCambio(valor);
}
Ext.onReady(CGAUTORIZACIONES.init, CGAUTORIZACIONES, true);
</sec:authorize>