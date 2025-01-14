<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">

var CGLOBALHISTORICOSEG = function (){
	
	var filaSeleccionada;
	
	//boton filtrar
	var obtenerDatosHistorico = function (){

		myMask = new Ext.LoadMask('marco', {msg:"Cargando datos..."});
		myMask.show();
	
		Ext.Ajax.request({
				url:contexto + 'Segmentaciones.do',
				params:{
				obtenerHistorico:''
				
			},
	
			callback:function (options, success, response){
				myMask.hide();
				if (success){
					datosResultado = Ext.util.JSON.decode (response.responseText);
	
					if (datosResultado.success){
						//Pueden llegar mensajes informativos.
						if (datosResultado.message && datosResultado.message.length>0){
							Ext.each(datosResultado.message,function(message){
								Ext.Msg.show({
									title:'Informaci&oacute;n',
									cls:'cgMsgBox',
									msg: '<span>'+message+'</span><br/>',
									buttons: Ext.Msg.OK,
									icon: Ext.MessageBox.INFO
								});
							});
						}
						//Si no llegan resultados mostramos un mensaje y no mostramos el grid
						if(datosResultado.total == 0){
							Ext.Msg.show({
								title:'Informaci&oacute;n',
								cls:'cgMsgBox',
								msg: '<span>No se han encontrado segmentaciones en el hist&oacute;rico.</span><br/>',
								buttons: Ext.Msg.OK,
								icon: Ext.MessageBox.INFO
							});
							Ext.getCmp('gridBuscadorSegmentosHistorico').getStore().loadData (new Array());
						}else{
							Ext.getCmp('gridBuscadorSegmentosHistorico').getStore().loadData (datosResultado.datos);
						}
						
						Ext.getCmp('gridBuscadorSegmentosHistorico').getView().refresh(true);
						
					}else{
						//Ha saltado una excepcion y viene un objeto con un mensaje de error.
						Ext.Msg.show({
							title:'Error',
							cls:'cgMsgBox',
							msg: '<span>'+datosResultado.message+'</span><br/>',
							buttons: Ext.Msg.OK,
							icon: Ext.MessageBox.ERROR
						});
						
						Ext.getCmp('gridBuscadorSegmentosHistorico').getStore().loadData (new Array());
					}
					
					
				}
			}
			});

		
	}
	
	
	// Controlamos los eventos sobre los botones
	var controlEventos = function() {
		//evento click del boton historico
		Ext.get('btnHistorico').on('click', function() {
			mostrarHistorico();
			obtenerDatosHistorico();
		});
		
		Ext.get('btnVolver').on('click', function() {
			ocultarHistorico();
		});
	}
	
	var mostrarHistorico = function() {
		Ext.get('divPrincipal').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divHistorico').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('divTituloPrincipal').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divTituloHistorico').setVisibilityMode(Ext.Element.DISPLAY).show();
	}
	
	var ocultarHistorico = function() {
		Ext.get('divPrincipal').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('divHistorico').setVisibilityMode(Ext.Element.DISPLAY).hide();
		Ext.get('divTituloPrincipal').setVisibilityMode(Ext.Element.DISPLAY).show();
		Ext.get('divTituloHistorico').setVisibilityMode(Ext.Element.DISPLAY).hide();
	}
	
	/**
	 * Funcion que crea el tooltip para la columna
	 * @param metadata
	 * @param record
	 * @param rowIndex
	 * @param colIndex
	 * @param store
	 * @return
	 */
	function addTooltip(value,metadata, record, rowIndex, colIndex, store){
		//En record viene el elemento formado del grid
		var valorTooltip = "";
		//Dependiendo de la columna que estemos renderizando se metera un valor de descripcion u otro
		//Columna de segmento origen
		if(colIndex == 2){
			valorTooltip = record.data.segmentoOrigenDescripcion;
		//Columna de sbseg origen
		}else if(colIndex == 3 ){
			valorTooltip = record.data.subSegmentoOrigenDescripcion;
		//Columna de segmento destino
		}else if(colIndex == 4 ){
			valorTooltip = record.data.segmentoDestinoDescripcion;
		//Columna de sbseg destino
		}else if(colIndex == 5 ){
			valorTooltip = record.data.subSegmentoDestinoDescripcion;
		}
		//Accedemos al tooltip
		if(valorTooltip != null && valorTooltip != ''){
			metadata.attr = 'ext:qtip="' + valorTooltip +'"';
		}
		//Debe devolver el valor para rellenar la columna
		return value;
	}
	
	
	var pintarGrid = function (){
		
		// create the data store
		var store = new Ext.data.Store({
			reader: new Ext.data.ObjectReader({}, [
	            
	            {name: 'descUnidad', type: 'string'},
	            {name: 'descOperacion', type: 'string'},
	            {name: 'segmentoOrigen', type: 'string'},
	            {name: 'subSegmentoOrigen', type: 'string'},
	            {name: 'segmentoDestino', type: 'string'},
	            {name: 'subSegmentoDestino', type: 'string'},
	            {name: 'fechaIniVigencia', type: 'string',sortType:convertirEnFecha},
	            {name: 'fechaFinVigencia', type: 'string',sortType:convertirEnFecha},
	            {name: 'usuarioAlta', type: 'string'},
	            {name: 'fechaBaja',type:'string',sortType:convertirEnFecha},
	            {name: 'usuarioBaja',type:'string'},
	            {name: 'keySegmentacion', type:'string'},
	            {name: 'segmentoOrigenDescripcion',type:'string'},
	            {name: 'subSegmentoOrigenDescripcion',type:'string'},
	            {name: 'segmentoDestinoDescripcion',type:'string'},
	            {name: 'subSegmentoDestinoDescripcion',type:'string'}, 
	            {name: 'unidad', type: 'string'},
	            {name: 'operacion', type: 'string'}
	            
            ])
		});
		var columnModel = new Ext.grid.ColumnModel([
	        {header: 'Unidad',sortable: true,  dataIndex: 'descUnidad'},
	        {header: 'Operaci&oacute;n',sortable: true,  dataIndex: 'descOperacion', id:'colDesc'},
	        {header: 'Seg. Origen',sortable: true,  dataIndex: 'segmentoOrigen', renderer:addTooltip},
	        {header: 'Subseg. Origen',sortable: true,  dataIndex: 'subSegmentoOrigen', renderer:addTooltip},
	        {header: 'Seg. Destino',sortable: true,  dataIndex: 'segmentoDestino', renderer:addTooltip},
	        {header: 'Subseg. Destino',sortable: true, dataIndex: 'subSegmentoDestino', renderer:addTooltip},
	        {header: 'F. Ini. Vigencia', sortable: true, align:'center', dataIndex: 'fechaIniVigencia'},
	        {header: 'F. Fin Vigencia', sortable: true, align:'center', dataIndex: 'fechaFinVigencia'},
	        {header: 'Us. Alta',sortable: true,  align:'center', dataIndex: 'usuarioAlta'},
	        {header: 'F. Baja',sortable: true,  align:'center', dataIndex: 'fechaBaja'},
	        {header: 'Usuario Baja',sortable: true,  align:'center', dataIndex: 'usuarioBaja'}
        ]);
        
        var cargarDatosFila = function(fila){
        	Ext.each(Ext.query('.marcarFila', grid.id), function(filaMarcada) {
				Ext.fly(filaMarcada).removeClass('marcarFila');
			});
			Ext.fly(grid.getView().getRow(fila)).addClass('marcarFila');
			
			filaSeleccionada = grid.getStore().data.items[fila].data;
		}
        
		// create the Grid
		grid = new Ext.grid.EditorPasteCopyGridPanel({
			id:'gridBuscadorSegmentosHistorico',
			store: store,
			renderTo: 'divGridHistorico',
			cls:'gridCG',
			selectionModel: '',
			funcionRollBack: cargarDatosFila,
			viewConfig: {
				forceFit:true
			},
			cm: columnModel,
			stripeRows: true,
			height: 177,
	        listeners:{
	        	render:function(){
			    	Ext.getCmp('gridBuscadorSegmentosHistorico').getView().refresh(true);
			    }
	        }
		});
		
		if (Ext.isIE6){
			anchoContenido = document.body.offsetWidth - 40;
			grid.setWidth (anchoContenido);
		}
	}
	

	return {
		init : function() {
			Ext.QuickTips.init();
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			controlEventos();
			pintarGrid();
		}
	}
}();

Ext.onReady (CGLOBALHISTORICOSEG.init, CGLOBALHISTORICOSEG, true);
</sec:authorize>