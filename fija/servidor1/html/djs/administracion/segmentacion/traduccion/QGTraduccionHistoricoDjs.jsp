<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
var CGLOBALHISTORICOSEG = function (){
	var grid = null;
	
	var filaSeleccionada = null;
	
	var callbackHistorico = function(correcto,datos){
		grid.getStore().proxy =  new Ext.ux.data.PagingMemoryProxy(datos);
		grid.getStore().load({params: {start: 0, limit: 100}});
	}
	
	//boton historico
	var obtenerDatosHistorico = function (){
		//Obtenemos el historico
		llamadaAjax('Traduccion.do','obtenerHistorico',null,null,callbackHistorico,true);
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
		
		Ext.get('divTituloPrincipal').setVisibilityMode(Ext.Element.DISPLAY).hide();
		
		//Ocultando el div que contiene al grid metia un estilo por el cual luego el toolbar no se mostraba.
		Ext.get('divTituloHistorico').setVisibilityMode(Ext.Element.DISPLAY).show();
		grid.show();
		Ext.get('divBotonesHistorico').setVisibilityMode(Ext.Element.DISPLAY).show();
		
	}
	
	var ocultarHistorico = function() {
		
		Ext.get('divPrincipal').setVisibilityMode(Ext.Element.DISPLAY).show();

		Ext.get('divTituloPrincipal').setVisibilityMode(Ext.Element.DISPLAY).show();
		
		//Ocultando el div que contiene al grid metia un estilo por el cual luego el toolbar no se mostraba.
		Ext.get('divTituloHistorico').setVisibilityMode(Ext.Element.DISPLAY).hide();
		grid.hide();
		Ext.get('divBotonesHistorico').setVisibilityMode(Ext.Element.DISPLAY).hide();
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
		if(colIndex == 0){
			valorTooltip = record.data.descSegmentoFijo;
		//Columna de sbseg origen
		}else if(colIndex == 1 ){
			valorTooltip = record.data.descSubSegmentoFijo;
		//Columna de segmento destino
		}else if(colIndex == 2 ){
			valorTooltip = record.data.descSegmentoMovil;
		//Columna de sbseg destino
		}else if(colIndex == 3 ){
			valorTooltip = record.data.descSubSegmentoMovil;
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
			proxy: new Ext.ux.data.PagingMemoryProxy([]),
			reader: new Ext.data.ObjectReader({}, [
			                                       {name: 'codigoSegmentoFijo', type: 'string'},
			                                       {name: 'codigoSubSegmentoFijo', type: 'string'},
			                                       {name: 'codigoSegmentoMovil', type: 'string'},
			                                       {name: 'codigoSubSegmentoMovil', type: 'string'},
			                                       {name: 'planCuentas', type: 'string'},
			                                       {name: 'fechaIniVigencia', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'fechaFinVigencia', type: 'string',sortType:convertirEnFecha},
			                                       {name: 'usuarioAlta', type: 'string'},
			                                       {name: 'fechaAlta',type:'string',sortType:convertirEnFecha},
			                                       {name: 'usuarioMod',type:'string'},
			                                       {name: 'fechaMod',type:'string',sortType:convertirEnFecha},
			                                       {name: 'usuarioBaja', type:'string'},
			                                       {name: 'fechaBaja',type:'string',sortType:convertirEnFecha},
			                                       {name: 'descSegmentoFijo', type: 'string'},
			                                       {name: 'descSubSegmentoFijo', type: 'string'},
			                                       {name: 'descSegmentoMovil', type: 'string'},
			                                       {name: 'descSubSegmentoMovil', type: 'string'}
			                                       ])
		});
		var columnModel = new Ext.grid.ColumnModel([
		                                            {header: 'Seg. Fijo',sortable: true,  dataIndex: 'codigoSegmentoFijo',renderer:addTooltip},
		                                            {header: 'Subseg. Fijo',sortable: true,  dataIndex: 'codigoSubSegmentoFijo',renderer:addTooltip},
		                                            {header: 'Seg. M&oacute;vil',sortable: true,  dataIndex: 'codigoSegmentoMovil',renderer:addTooltip},
		                                            {header: 'Subseg. M&oacute;vil',sortable: true,  dataIndex: 'codigoSubSegmentoMovil',renderer:addTooltip},
		                                            {header: 'Plan de Cuentas',sortable: true,  dataIndex: 'planCuentas'},
		                                            {header: 'F. Ini. Vigencia', sortable: true, align:'center', dataIndex: 'fechaIniVigencia'},
		                                            {header: 'F. Fin Vigencia', sortable: true, align:'center', dataIndex: 'fechaFinVigencia'},
		                                            {header: 'Us. Alta',sortable: true,  align:'center', dataIndex: 'usuarioAlta'},
		                                            {header: 'Fecha Alta',sortable: true,  align:'center', dataIndex: 'fechaAlta'},
		                                            {header: 'Us. Mod',sortable: true,  align:'center', dataIndex: 'usuarioMod'},
		                                            {header: 'Fecha Mod',sortable: true,  align:'center', dataIndex: 'fechaMod'}
		                                         
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
			id:'gridBuscadorTraduccionHistorico',
			store: store,
			renderTo: 'divGridHistorico',
			cls:'gridCG',
			selectionModel: '',
			funcionRollBack: cargarDatosFila,
			viewConfig: {
				forceFit:true
			},
			hidden: true,
			cm: columnModel,
			stripeRows: true,
			height: 248,
			bbar: new NoRefreshPagingToolbar({
       			store: store,       // grid and PagingToolbar using same store
       		 	displayInfo: false,
        		pageSize: 100
        	}),
	        listeners:{
	        	render:function(){
			    	Ext.getCmp('gridBuscadorTraduccionHistorico').getView().refresh(true);
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