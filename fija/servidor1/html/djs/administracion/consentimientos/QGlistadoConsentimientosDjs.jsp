<%@ page language="java" contentType="text/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
var CGLOBAL = function (){
    var datosResultado;
    var grid;
    var myMask;

	var filaSeleccionada;

    var obtenerDatos = function (callback){
    	Ext.Ajax.request({
    		url:contexto + 'ConsentimientosDerechos.do',
    		params:{
    			buscar:''
    		},
    		callback:function (options, success, response){
    			myMask.hide();
    			if (success){
    				datosResultado = Ext.util.JSON.decode (response.responseText);
    				
    				if (datosResultado.success){
    					Ext.each(datosResultado.datos,function(dato){
	    					if (dato.tipoLogica == "T"){
	    						dato.tipoLogica = "Tácito";
	    					}else{
	    						dato.tipoLogica = "Expreso";
	    					}
	    				});
	    				
    					//Pueden llegar mensajes informativos.
    					if (datosResultado.message && datosResultado.message.length>0){
	    					Ext.each(datosResultado.message,function(message){
		    					Ext.Msg.show({
								   title:'Información',
								   cls:'cgMsgBox',
								   msg: '<span>'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
		    				});
	    				}
    				}else{
    					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
    					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span>'+datosResultado.message+'</span><br/>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
    				}
    				
    				callback();
    			}
	 			//Deshabilitamos los botones.
	 			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
	 			Ext.get('btnEliminar').dom.disabled = true;
				Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';
	 			Ext.get('btnEliminar').addClass('btnDis');
	 			</sec:authorize>		
	 			Ext.get('btnVerDetalle').dom.disabled = true;
				Ext.get('btnVerDetalle').dom.src = contexto + 'images/botones/QGbtListadoDetalle_des.gif';
				Ext.get('btnVerDetalle').addClass('btnDis');
    		}
    	});
    }
    
    <sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
    var eliminarDatos = function (){
  		
		var record = filaSeleccionada;
		
		var consentimientos = {		          
			codigoDerecho:record.codigoLOPD   
		};
		
		myMask = new Ext.LoadMask('marco', {msg:"Eliminando datos..."});
		myMask.show();
		
    	Ext.Ajax.request({
    		url:contexto + 'ConsentimientosDerechos.do',
    		params:{
    			baja:'',
    			consentimientosDerechosJSON:Ext.encode(consentimientos)
    			
    		},
    		callback:function (options, success, response){
    			myMask.hide();
    			if (success){
    				var respuesta = Ext.util.JSON.decode (response.responseText);
    				if (respuesta.success){
    					//Eliminamos el registro seleccionado.
    					grid.getStore().remove(record);
    					//Deshabilitamos el boton.
    					<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
    					Ext.get('btnEliminar').dom.disabled = true;
						Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtEliminar_des.gif';
    					Ext.get('btnEliminar').addClass('btnDis');
    					</sec:authorize>
    					
    					Ext.get('btnVerDetalle').dom.disabled = true;
						Ext.get('btnVerDetalle').dom.src = contexto + 'images/botones/QGbtListadoDetalle_des.gif';
						Ext.get('btnVerDetalle').addClass('btnDis');
    					
    					filaSeleccionada = null;
    					
    					//Pueden llegar mensajes informativos.
    					if (respuesta.message && respuesta.message.length>0){
	    					Ext.each(respuesta.message,function(message){
		    					Ext.Msg.show({
								   title:'Información',
								   cls:'cgMsgBox',
								   msg: '<span>'+message+'</span><br/>',
								   buttons: Ext.Msg.OK,
								   icon: Ext.MessageBox.INFO
								});
		    				});
	    				}
    				}else{
	   					//Ha saltado una excepcion y viene un objeto con un mensaje de error.
	   					Ext.Msg.show({
						   title:'Error',
						   cls:'cgMsgBox',
						   msg: '<span>'+respuesta.message+'</span><br/>',
						   buttons: Ext.Msg.OK,
						   icon: Ext.MessageBox.ERROR
						});
	   				}
    			}
    		}
    	});
    }
    
    var nuevoConsentimiento = function() {
		
		Ext.get('idMethod').set({
			name:'irAlta'
		});
	}
	
	var bajaConsentimiento = function() {
		
		Ext.Msg.show({
		   title:'Eliminar',
		   msg: '<span>¿Desea eliminar el registro seleccionado?</span>',
		   buttons: Ext.Msg.YESNO,
		   icon: Ext.MessageBox.WARNING,
		   fn:function(respuesta){
		   		if (respuesta == 'yes'){
		   			eliminarDatos();
		   		}
		   }
		});
	}
	</sec:authorize>
	
	var pintarGrid = function (){
    	  	
	  	// create the data store
	    var store = new Ext.data.Store({
	    	reader: new Ext.data.ObjectReader({}, [
				{name: 'codigoLOPD', type: 'string'},
				{name: 'descDerecho', type: 'string'},
				{name: 'codNegocioFija', type: 'string'},
				{name: 'codNegocioMovil', type: 'string'},
				{name: 'descAmbito', type: 'string'},
				{name: 'tipoLogica', type: 'string'},
				{name: 'fxIniVigencia', type: 'string'},
				{name: 'fxFinVigencia', type: 'string'},
				{name: 'usuarioAlta', type: 'string'},
				{name: 'usuarioMod', type: 'string'}
			])
	    });
	
	    var columnModel = new Ext.grid.ColumnModel([
			{header: 'Codigo',sortable: true,width:60, dataIndex: 'codigoLOPD'},
			{header: 'Descripcion',width:60, id:'colDescripcion', sortable: true, dataIndex: 'descDerecho'},
			{header: 'Codigo TdE',width:80,sortable: true, dataIndex: 'codNegocioFija'},
			{header: 'Codigo TME',width:80, sortable: true, dataIndex: 'codNegocioMovil'},
			{header: 'Ambito', width:60,sortable: true, dataIndex: 'descAmbito'},
			{header: 'Logica aplic.',width:85, sortable: true, dataIndex: 'tipoLogica'},
			{header: 'Fecha inicio vigencia',width:135, sortable: true, align:'center', dataIndex: 'fxIniVigencia'},
			{header: 'Fecha fin vigencia',width:125, sortable: true, align:'center', dataIndex: 'fxFinVigencia'}
		]);
	
		
		var cargarDatosFila = function(fila){
			Ext.each(Ext.query('.marcarFila', grid.id), function(filaMarcada) {
				Ext.fly(filaMarcada).removeClass('marcarFila');
			});
			Ext.fly(grid.getView().getRow(fila)).addClass('marcarFila');
						
			filaSeleccionada = grid.getStore().data.items[fila].data;
			// habilitamos el botón dar de baja
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
			Ext.get('btnEliminar').dom.disabled = false;
			Ext.get('btnEliminar').dom.src = contexto + 'images/botones/QGbtListadoBaja.gif';
			Ext.get('btnEliminar').removeClass('btnDis');
			</sec:authorize>
			Ext.get('btnVerDetalle').dom.disabled = false;
			Ext.get('btnVerDetalle').dom.src = contexto + 'images/botones/QGbtListadoDetalle.gif';
			Ext.get('btnVerDetalle').removeClass('btnDis');
		}

	    // create the Grid
	    grid = new Ext.grid.EditorPasteCopyGridPanel({
	    	id:'gridConsentimientos',
	        store: store,
	       	funcionRollBack: cargarDatosFila,
			renderTo: 'divGrid',
			cls:'gridCG',
			cm: columnModel,
			autoExpandColumn: 'colDescripcion',
	        stripeRows: true,
	        autoScroll: true,
	        height: 183, 
	        listeners:{
	        	render:function(){
			    	obtenerDatos(function (){
			    		store.loadData (datosResultado.datos);	
			    	});
			    }
	        }
	    });
	}
    
   // Controlamos los eventos sobre los botones
	var controlEventos = function() {
		
		Ext.get('marco').on('resize', function() {
			refrescarGrid.defer(20);
		});
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
		// evento click del botón nuevo
		Ext.get('btnNuevo').on('click', function() {
				nuevoConsentimiento();
		});
		
		// evento click del botón dar de baja
		Ext.get('btnEliminar').on('click', function() {
			if (filaSeleccionada != null) {
				bajaConsentimiento();
			}
		});
		</sec:authorize>
		
		// evento click del botón ver detalle
		Ext.get('btnVerDetalle').on('click', function() {
			if (filaSeleccionada != null) {
				verDetalleConsentimientos();
			}
		});
		
		// evento click del botón Imprimir
		//Ext.get('btnImprimir').on('click', function() {
			//imprimirConsentimiento();
		//});
		
		if (Ext.isIE)
			document.getElementById('ifExportar').onreadystatechange = function(){myMask.hide();};
	}	
	
	var refrescarGrid = function() {
		if (Ext.isIE6){
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).hide();
			var ancho = document.body.offsetWidth - 38;
			
			Ext.get('marco').setVisibilityMode(Ext.Element.DISPLAY).show();
			grid.setWidth (ancho);
		}
	}
	
	var verDetalleConsentimientos = function (){
		Ext.get('codDerecho').dom.value = filaSeleccionada.codigoLOPD;
		
		Ext.get('idMethod').set({
			name:'irDetalle'
		});
	}
	
	
	
	var imprimirConsentimiento = function() {
		if (Ext.isIE){
			myMask = new Ext.LoadMask('marco', {msg:"Generando informe..."});
			myMask.show();
		}
		document.getElementById('ifExportar').src = contexto + 'ConsentimientosDerechos.do?exportarPDF=';
	}
	
	
	return {
		init: function (){
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			
			myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
			myMask.show();
			
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
			Ext.get('btnEliminar').setVisibilityMode(Ext.Element.DISPLAY).hide();
			</sec:authorize>
			
			pintarGrid();
			
			if (Ext.isIE6){
				var ancho = document.body.offsetWidth - 38;
				grid.setWidth (ancho);
			}
			controlEventos();
		}
	}
	
}();

Ext.onReady (CGLOBAL.init, CGLOBAL, true);
</sec:authorize>