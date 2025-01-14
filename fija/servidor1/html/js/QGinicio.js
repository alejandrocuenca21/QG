var CGLOBAL = function (){



	var mensajeInformativo = function(){

		var message =	'<div class="msgInfo">'+

						'<span class="aviso">AVISO</span>'+

						'<ul>'+

						'<li>Ha accedido a un sistema propiedad de TELEF&Oacute;NICA ESPA&Ntilde;A. Necesita tener autorizaci&oacute;n antes de usarlo, estando usted estrictamente limitado al uso indicado en dicha autorizaci&oacute;n.</li>'+

						'<li>En cumplimiento de lo dispuesto en la Ley 15/1999, de 13 de diciembre, de Protecci&oacute;n de Datos de Car&aacute;cter Personal y en el Real Decreto 1720/2007, de 21 de diciembre, por el que se aprueba su Reglamento de desarrollo y las medidas de seguridad aplicables a los ficheros, TELEF&Oacute;NICA ESPA&Ntilde;A le recuerda que como usuario de sus Ficheros de datos de car&aacute;cter personal tiene las siguientes obligaciones generales:'+

							'<ul>'+

							'<li>Deber de guardar secreto profesional respecto de los datos de car&aacute;cter personal a los que acceda y deber de guardarlos, subsistiendo estas obligaciones a&uacute;n despu&eacute;s de finalizada la relaci&oacute;n profesional o contractual con TELEF&Oacute;NICA ESPA&Ntilde;A.</li>'+

							'<li>Aplicar las directrices de seguridad que impone la normativa interna de TELEF&Oacute;NICA ESPA&Ntilde;A.</li>'+

							'<li>Usar los datos exclusivamente para el fin que le han sido facilitados y de acuerdo con la funci&oacute;n que tenga encomendada.</li>'+

							'<li>Evitar incluir datos de car&aacute;cter personal en los campos de texto libre. Si ello no fuera posible, deber&aacute; evitar, al menos, incluir datos de car&aacute;cter personal a los que pueda corresponder medidas de seguridad de nivel medio o alto, y en concreto, datos relativos a ideolog&iacute;a, afiliaci&oacute;n sindical, religi&oacute;n, creencias, origen racial, salud, vida sexual o infracciones administrativas o penales.</li>'+ 

							'<li>Utilizar software homologado o autorizado por TELEF&Oacute;NICA ESPA&Ntilde;A.</li>'+

							'<li>Bloquear la sesi&oacute;n utilizando el protector de pantalla con contrase&ntilde;a siempre que vaya a ausentarse de su puesto de trabajo aunque sea por un breve periodo de tiempo.</li>'+

							'<li>Proteger y mantener en secreto las contrase&ntilde;as utilizadas para autenticarse en el sistema o aplicaci&oacute;n que se acceda para el tratamiento de los datos del fichero, siguiendo las directrices de la normativa interna de TELEF&Oacute;NICA ESPA&Ntilde;A en cuanto a sintaxis y obligaci&oacute;n de cambio de las mismas.</li>'+ 

							'<li>Apagar siempre de forma ordenada la estaci&oacute;n de trabajo, al finalizar su jornada laboral.</li>'+ 

							'<li>Comunicar cualquier anomal&iacute;a por mal funcionamiento al &aacute;rea de Soporte de Incidencias, as&iacute; como cualquier incidencia de seguridad al Responsable de Seguridad del Fichero. Asimismo, como usuario tiene las siguientes obligaciones respecto de la creaci&oacute;n o uso de ficheros temporales o copias de trabajo:'+ 

								'<ul>'+ 

								'<li>Respetar las medidas y controles correspondientes al nivel de seguridad del Fichero del que procedan los datos de car&aacute;cter personal, conforme a la normativa legal e interna vigente, as&iacute; como las directrices indicadas por el Responsable de Seguridad.</li>'+ 

								'<li>Usar o crear ficheros temporales o copias de trabajo s&oacute;lo en caso de estar expresamente autorizado para ello. Si no es as&iacute; deber&aacute; suspender inmediatamente su creaci&oacute;n y/o dejar de usarlos, y recabar con car&aacute;cter previo la preceptiva autorizaci&oacute;n del Responsable de Seguridad del Fichero.</li>'+ 

								'<li>Respetar siempre la finalidad espec&iacute;fica para la que fue creado el fichero temporal o la copia de trabajo.</li>'+ 

								'<li>Proceder al borrado o destrucci&oacute;n segura del fichero temporal o copia de trabajo una vez que haya dejado de ser necesario para los fines que motivaron su creaci&oacute;n. Adem&aacute;s, debe saber que el uso que realice de este sistema puede ser monitorizado. Del mismo modo, le recordamos que el acceso no autorizado a este sistema o el uso indebido del mismo est&aacute; expresamente prohibido, siendo contrario a la normativa interna y legal vigente.</li>'+ 

								'</ul>'+ 

							'</li>'+ 

							'</ul>'+ 

						'</li>'+ 

						'<li>Si es usted empleado de TELEF&Oacute;NICA ESPA&Ntilde;A debe recordar que el incumplimiento de sus obligaciones en el uso de los sistemas puede ser constitutivo de falta laboral grave, sin perjuicio de otras responsabilidades que pudieran derivarse de la comisi&oacute;n de infracciones contra las normas en materia de protecci&oacute;n de datos de car&aacute;cter personal.</li>'+ 

						'<li>Si no es usted empleado de TELEF&Oacute;NICA ESPA&Ntilde;A estar&aacute; sujeto a lo dispuesto por su empresa en materia de incumplimiento de obligaciones laborales, sin perjuicio de las responsabilidades que para usted y para su empresa pudieran derivarse de la comisi&oacute;n de infracciones contra las normas en materia de protecci&oacute;n de datos de car&aacute;cter personal.</li>'+ 

						'</ul></div>';

		

		Ext.Msg.show({

		   title:'Informaci&oacute;n',

		   cls:'cgMsgBox',

		   msg: '<span>'+message+'</span>',

		   buttons: Ext.Msg.OK,

		   icon: Ext.MessageBox.INFO,

		   minWidth:'975'

		});

	}

	 // NUEVO: Función para mostrar ventana de accesos
	  var mostrarVentanaAccesos = function(accesos) {

	    // Si hay 2 ó más accesos, mostramos la ventana
	    if (accesos && accesos.length > 1) {

	        var storeAccesos = new Ext.data.ArrayStore({
	            fields: ['accesoSeleccionado'],
	            data: accesos.map(function(item){ return [item]; })
	          });
	    	
	      // 1) Creamos un pequeño FormPanel
	      var formAccesos = new Ext.FormPanel({
	        labelWidth: 100,
	        bodyStyle: 'padding:10px;',
	        items: [
	            {
	                // Campo que muestra el usuario (matrícula)
	                xtype: 'displayfield',
	                fieldLabel: 'Usuario',	             
	                value: usuarioJS
	              },
	            {
	              xtype: 'label',	          
	              style: 'display:block; font-weight:bold; margin-top:15px;',
	              text: 'Seleccione el perfil con el que desea acceder a la aplicación'
	            },
	            {
	                xtype: 'combo',
	                fieldLabel: 'Perfiles disponibles',
	                name: 'accesoSeleccionado',
	                store: storeAccesos,
	                displayField: 'accesoSeleccionado',
	                valueField: 'accesoSeleccionado', 
	                mode: 'local',
	                triggerAction: 'all',
	                forceSelection: true,
	                editable: false,
	                anchor: '90%'
	              }
	            ]
	      });

	      // 2) Creamos la ventana modal
	      var win = new Ext.Window({
	        title: 'Referencia de clientes',
	        cls: 'cgMsgBox',     // Para usar el mismo estilo
	        width: 450,
	        height: 220,
	        modal: true,
	        layout: 'fit',
	        items: [formAccesos],
	        buttons: [{
	          text: 'Aceptar',
	          handler: function(){
	            var vals = formAccesos.getForm().getValues();
	            var sel = vals['accesoSeleccionado'];
	            if (!sel) {
	              Ext.Msg.alert("Atención", "Seleccione un acceso primero.");
	              return;
	            }
	            // Pasar el seleccionado al input hidden y hacer submit
	            document.getElementById("acceso").value = sel;  
	            document.getElementById("formSeleccionAcceso").submit();

	            win.close();
	          }
	        },{
	          text: 'Cancelar',
	          handler: function(){
	            win.close();
	          }
	        }]
	      });

	      // 3) Mostramos la ventana
	      win.show();
	    }
	  };

	return {

		init: function (){

		      //  Comentamos el popUp del modal
//		      if (typeof accesosJS !== 'undefined') {
//		        mostrarVentanaAccesos(accesosJS);
//		      }
		      
				mensajeInformativo();

		}

	}

}();



Ext.onReady (CGLOBAL.init, CGLOBAL, true);