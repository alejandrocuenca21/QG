<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html:xhtml/>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
<!-- Menu de la izquierda -->
<div class="divIzqInf">
	<div class="divInformes">
		<!-- Esquinas redondeadas superiores -->
		<div class="sup"><div class="supIzq"></div><div class="supMed"></div><div class="supDrch"></div></div>
		<h2>Informes</h2>
		<div class="cont">
			<div class="contIzq"><div class="contDrch">
				<ul class="menuIzq">
					<li class="linkDis">
						<!--<html:link action="vInformesDuplicados">Duplicados</html:link>-->
						<a>Duplicados</a>
					</li>
					<li class="link">
						<html:link action="vMovimientosTratados">Errores</html:link>
					</li>
					<li class="sel">
						<html:link action="vConvivencia">Convivencia</html:link>
					</li>
					<li class="link">
						<html:link action="vConciliacion">Conciliaci&oacute;n</html:link>
					</li>
				</ul>
			</div></div>
		</div>
		<!-- Esquinas redondeadas inferiores -->
		<div class="inf"><div class="infIzq"></div><div class="infMed"></div><div class="infDrch"></div></div>
	</div>
</div>

<!-- Contenido de la derecha -->
<div class="divCenterTwoCol">
	<div class="recCentral">
		<div class="recHead">
			<html:img styleClass="imgSupL" page="/images/QGrecContSupIzq.gif" />
			<html:img styleClass="imgSupR" page="/images/QGrecContSupDrch.gif" />
			
			<h2 class="titInformes">Informe de convivencia</h2>
		</div>
			
		<div class="recCentCont">
			
			<table class="formInformes">
				<colgroup>
					<col width="75"/><col width="106"/><col width="273"/>
				</colgroup>
				<tbody>
					<tr>
						<td><label>Informe del día:</label></td>
						<td>
							<div class="divCalendar" id="divTxtFechaBusqueda">
								<input type="text" maxlength="10" class="inputCalendar" id="txtFechaBusqueda" />
								<html:image styleClass="icoCalendar" styleId="btnCalendarFechaBusqueda" page="/images/iconos/QGicoCalendar.gif"/>
							</div>
						</td>

						<td>
							<html:image styleClass="btn" styleId="btnBuscar" page="/images/botones/QGbtBuscarInformes.gif" alt="Buscar" onclick="return false;" />
						</td>
						<td>
							<div class="divBotones">																		
								<span class="edicion">
									<html:image styleClass="btn" styleId="btnExportar" page="/images/botones/QGbtnExportarExcel.gif" alt="Exportar Excel" onclick="return false;" />
									<html:image styleClass="btn" styleId="btnImprimir" page="/images/botones/QGbtImprimir.gif" alt="Imprimir" onclick="return false;"/>
								</span>
							</div>						
						</td>							
					</tr>
				</tbody>
			</table>	
					
			<!-- Inicio Pestañas -->
			<div id="divPes" class="divPes">
				<ul>
					<li id="pesMovTotales" class="activa">
						<span>Mvto. Totales</span>
					</li>
					<li id="pesMovFija">
						<span>Mvto. Fija</span>
					</li>
					<li id="pesMovMovil">
						<span>Mvto. M&oacute;vil</span>
					</li>
					<li id="pesMovPrepago">
						<span>Mvto. Prepago</span>
					</li>					
					<li id="pesReinyeccion">
						<span>Reinyecci&oacute;n</span>
					</li>
					<li id="pesErrores">
						<span>Errores</span>
					</li>
				</ul>	
				
				<div class="contPest" id="informeSalida">	
					<div id="divMovTotales" class="divDatosGenerales">					
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td>MOVIMIENTOS TOTAL (ON LINE + BATCH)</td>
									</tr>
									<tr>
										<td class="tab30">TRATADOS</td>
									</tr>
									<tr>
										<td class="tab50">Tratados en Fecha</td>
									</tr>
									<tr>
										<td class="tab50">Tratados Posterior</td>
									</tr>
									<tr>
										<td class="tab30">PENDIENTES</td>
									</tr>
									<tr>
										<td class="tab50">Pendientes en Fecha</td>
									</tr>
									<tr>
										<td class="tab50">Pendientes Tratados Posterior</td>
									</tr>
									<tr>
										<td class="tab30">ERRORES</td>
									</tr>
									<tr>
										<td class="tab50">Errores en Fecha</td>
									</tr>
									<tr>
										<td class="tab50">Errores Tratados Posterior</td>
									</tr>
									<tr>
										<td>MOVIMIENTOS FIJA (BATCH)</td>
									</tr>
									<tr>
										<td class="tab30">TRATADOS EN FECHA</td>
									</tr>
									<tr>
										<td class="tab30">TRATADOS Posterior</td>
									</tr>
									<tr>
										<td class="tab30">PENDIENTES EN FECHA</td>
									</tr>
									<tr>
										<td class="tab30">Pendientes Tratados Posterior</td>
									</tr>
									<tr>
										<td class="tab30">ERRORES EN FECHA</td>
									</tr>
									<tr>
										<td class="tab30">Erroneos Tratados Posterior</td>
									</tr>
									<tr>
										<td>MOVIMIENTOS MOVIL (BATCH)</td>
									</tr>
									<tr>
										<td>MOVIMIENTOS MOVIL (ON LINE)</td>
									</tr>
									<tr>
										<td class="tab30">TRATADOS EN FECHA</td>
									</tr>
									<tr>
										<td class="tab30">Tratados Posterior</td>
									</tr>
									<tr>
										<td class="tab30">PENDIENTES EN FECHA</td>
									</tr>
									<tr>
										<td class="tab30">Pendientes Tratados Posterior</td>
									</tr>
									<tr>
										<td class="tab30">ERRORES EN FECHA</td>
									</tr>
									<tr>
										<td class="tab30">Erroneos tratados Posterior</td>
									</tr>
									<tr>
										<td>MOVIMIENTOS PREPAGO (BATCH)</td>
									</tr>
									<tr>
										<td>MOVIMIENTOS PREPAGO (ON LINE)</td>
									</tr>
									<tr>
										<td class="tab30">TRATADOS EN FECHA</td>
									</tr>
									<tr>
										<td class="tab30">Tratados Posterior</td>
									</tr>
									<tr>
										<td class="tab30">PENDIENTES EN FECHA</td>
									</tr>
									<tr>
										<td class="tab30">Pendientes Tratados Posterior</td>
									</tr>
									<tr>
										<td class="tab30">ERRORES EN FECHA</td>
									</tr>
									<tr>
										<td class="tab30">Erroneos tratados Posterior</td>
									</tr>									
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableMovTot">
						
						</div>
						<br style="clear:both;">
					</div>
					
					
					<div id="divMovFija" class="divDatosGenerales" style="display:none;overflow-y: none;">
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">MOVIMIENTOS FIJA</td>
									</tr>
									<tr>
										<td>TRATADOS</td>
									</tr>
									<tr>
										<td>PENDIENTES</td>
									</tr>
									<tr>
										<td>ERRORES</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableMovFija">
						</div>
						<br style="clear:both;">
						
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">TRATADOS</td>
									</tr>
									<tr>
										<td>A - ALTA</td>
									</tr>
									<tr>
										<td>B - BAJA</td>
									</tr>
									<tr>
										<td>M - MODIFICACION</td>
									</tr>
									<tr>
										<td>C - CAMBIO SEGMENTO</td>
									</tr>
									<tr>
										<td>D - CAMBIO SEGMENTO DIFERIDO</td>
									</tr>
									<tr>
										<td>E - BAJA EN DIFERIDO</td>
									</tr>
									<tr>
										<td>R - REACTIVACION DE CLIENTES</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableMovFijaTrat">
						</div>	
						<br style="clear:both;">
						
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">PENDIENTES</td>
									</tr>
									<tr>
										<td>A - ALTA</td>
									</tr>
									<tr>
										<td>B - BAJA</td>
									</tr>
									<tr>
										<td>M - MODIFICACION</td>
									</tr>
									<tr>
										<td>C - CAMBIO SEGMENTO</td>
									</tr>
									<tr>
										<td>D - CAMBIO SEGMENTO DIFERIDO</td>
									</tr>
									<tr>
										<td>E - BAJA EN DIFERIDO</td>
									</tr>
									<tr>
										<td>R - REACTIVACION DE CLIENTES</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableMovFijaPend">
						</div>	
						<br style="clear:both;">
						
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">ERRORES</td>
									</tr>
									<tr id="LFEA">
										<td>A - ALTA</td>
									</tr>
									<tr id="LFEB">
										<td>B - BAJA</td>
									</tr>
									<tr id="LFEM">
										<td>M - MODIFICACION</td>
									</tr>
									<tr id="LFEC">
										<td>C - CAMBIO SEGMENTO</td>
									</tr>
									<tr id="LFED">
										<td>D - CAMBIO SEGMENTO DIFERIDO</td>
									</tr>
									<tr id="LFEE">
										<td>E - BAJA EN DIFERIDO</td>
									</tr>
									<tr id="LFER">
										<td>R - REACTIVACION DE CLIENTES</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableMovFijaErrores">
						</div>	
						<br style="clear:both;">
						
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">ERRORES INFORMATIVOS</td>
									</tr>
									<tr id="LFWA">
										<td>A - ALTA</td>
									</tr>
									<tr id="LFWB">
										<td>B - BAJA</td>
									</tr>
									<tr id="LFWM">
										<td>M - MODIFICACION</td>
									</tr>
									<tr id="LFWC">
										<td>C - CAMBIO SEGMENTO</td>
									</tr>
									<tr id="LFWD">
										<td>D - CAMBIO SEGMENTO DIFERIDO</td>
									</tr>
									<tr id="LFWE">
										<td>E - BAJA EN DIFERIDO</td>
									</tr>
									<tr id="LFWR">
										<td>R - REACTIVACION DE CLIENTES</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableMovFijaErroresInfo">
						</div>	
						<br style="clear:both;">
						
					</div>

					<div id="divMovMovil" class="divDatosGenerales" style="display:none;">
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">MOVIMIENTOS MOVIL</td>
									</tr>
									<tr>
										<td>TRATADOS</td>
									</tr>
									<tr>
										<td>PENDIENTES</td>
									</tr>
									<tr>
										<td>ERRORES</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableMovMovil">
						</div>
						<br style="clear:both;">
						
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">TRATADOS</td>
									</tr>
									<tr>
										<td>A - ALTAS DIRECCIONES</td>
									</tr>
									<tr>
										<td>AC - ALTA CLIENTE</td>
									</tr>
									<tr>
										<td>AP - ALTA PRECLIENTE</td>
									</tr>
									<tr>
										<td>B - BAJAS DIRECCIONES ELECTRONICAS</td>
									</tr>
									<tr>
										<td>CC - CANCELACION</td>
									</tr>
									<tr>
										<td>CE - CAMBIO DE ESTADO</td>
									</tr>
									<tr>
										<td>M - MODIFICACION DIRECCIONES</td>
									</tr>
									<tr>
										<td>MC - MODIFICACION DE DATOS VARIOS DEL CLIENTE</td>
									</tr>
									<tr>
										<td>ME - MODIFICACION ESTABLECIMIENTO</td>
									</tr>
									<tr>
										<td>MI - MODIFICACION IMPRESION FACTURA</td>
									</tr>
									<tr>
										<td>MP - MIGRACION DE PRECLIENTE A CLIENTE</td>
									</tr>
									<tr>
										<td>MS - MODIFICACION SEGMENTACION</td>
									</tr>
									<tr>
										<td>NS - ASIGNACION SEGMENTACION</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableMovMovilTrat">
						</div>	
						<br style="clear:both;">
						
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">PENDIENTES</td>
									</tr>
									<tr>
										<td>A - ALTAS DIRECCIONES</td>
									</tr>
									<tr>
										<td>AC - ALTA CLIENTE</td>
									</tr>
									<tr>
										<td>AP - ALTA PRECLIENTE</td>
									</tr>
									<tr>
										<td>B - BAJAS DIRECCIONES ELECTRONICAS</td>
									</tr>
									<tr>
										<td>CC - CANCELACION</td>
									</tr>
									<tr>
										<td>CE - CAMBIO DE ESTADO</td>
									</tr>
									<tr>
										<td>M - MODIFICACION DIRECCIONES</td>
									</tr>
									<tr>
										<td>MC - MODIFICACION DE DATOS VARIOS DEL CLIENTE</td>
									</tr>
									<tr>
										<td>ME - MODIFICACION ESTABLECIMIENTO</td>
									</tr>
									<tr>
										<td>MI - MODIFICACION IMPRESION FACTURA</td>
									</tr>
									<tr>
										<td>MP - MIGRACION DE PRECLIENTE A CLIENTE</td>
									</tr>
									<tr>
										<td>MS - MODIFICACION SEGMENTACION</td>
									</tr>
									<tr>
										<td>NS - ASIGNACION SEGMENTACION</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableMovMovilPend">
						</div>	
						<br style="clear:both;">
						
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">ERRORES</td>
									</tr>
									<tr id="LMEA">
										<td>A - ALTAS DIRECCIONES</td>
									</tr>
									<tr id="LMEAC">
										<td>AC - ALTA CLIENTE</td>
									</tr>
									<tr id="LMEAP">
										<td>AP - ALTA PRECLIENTE</td>
									</tr>
									<tr id="LMEB">
										<td>B - BAJAS DIRECCIONES ELECTRONICAS</td>
									</tr>
									<tr id="LMECC">
										<td>CC - CANCELACION</td>
									</tr>
									<tr id="LMECE">
										<td>CE - CAMBIO DE ESTADO</td>
									</tr>
									<tr id="LMEM">
										<td>M - MODIFICACION DIRECCIONES</td>
									</tr>
									<tr id="LMEMC">
										<td>MC - MODIFICACION DE DATOS VARIOS DEL CLIENTE</td>
									</tr>
									<tr id="LMEME">
										<td>ME - MOD. ESTABLECIMIENTO</td>
									</tr>
									<tr id="LMEMI">
										<td>MI - MODIFICACION IMPRESION FACTURA</td>
									</tr>
									<tr id="LMEMP">
										<td>MP - MIGRACION DE PRECLIENTE A CLIENTE</td>
									</tr>
									<tr id="LMEMS">
										<td>MS - MODIFICACION SEGMENTACION</td>
									</tr>
									<tr id="LMENS">
										<td>NS - ASIGNACION SEGMENTACION</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableMovMovilErrores">
						</div>	
						<br style="clear:both;">
						
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">ERRORES INFORMATIVOS</td>
									</tr>
									<tr id="LMWA">
										<td>A - ALTAS DIRECCIONES</td>
									</tr>
									<tr id="LMWAC">
										<td>AC - ALTA CLIENTE</td>
									</tr>
									<tr id="LMWAP">
										<td>AP - ALTA PRECLIENTE</td>
									</tr>
									<tr id="LMWB">
										<td>B - BAJAS DIRECCIONES ELECTRONICAS</td>
									</tr>
									<tr id="LMWCC">
										<td>CC - CANCELACION</td>
									</tr>
									<tr id="LMWCE">
										<td>CE - CAMBIO DE ESTADO</td>
									</tr>
									<tr id="LMWM">
										<td>M - MODIFICACION DIRECCIONES</td>
									</tr>
									<tr id="LMWMC">
										<td>MC - MODIFICACION DE DATOS VARIOS DEL CLIENTE</td>
									</tr>
									<tr id="LMWME">
										<td>ME - MOD. ESTABLECIMIENTO</td>
									</tr>
									<tr id="LMWMI">
										<td>MI - MODIFICACION IMPRESION FACTURA</td>
									</tr>
									<tr id="LMWMP">
										<td>MP - MIGRACION DE PRECLIENTE A CLIENTE</td>
									</tr>
									<tr id="LMWMS">
										<td>MS - MODIFICACION SEGMENTACION</td>
									</tr>
									<tr id="LMWNS">
										<td>NS - ASIGNACION SEGMENTACION</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableMovMovilErroresInfor">
						</div>	
						<br style="clear:both;">
					</div>
					
					<div id="divMovPrepago" class="divDatosGenerales" style="display:none;">
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">MOVIMIENTOS PREPAGO</td>
									</tr>
									<tr>
										<td>TRATADOS</td>
									</tr>
									<tr>
										<td>PENDIENTES</td>
									</tr>
									<tr>
										<td>ERRORES</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableMovPrepago">
						</div>
						<br style="clear:both;">
						
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">TRATADOS</td>
									</tr>
									<tr>
										<td>AG - ALTA CLIENTE</td>
									</tr>
									<tr>
										<td>BG - BAJA CLIENTE</td>
									</tr>
									<tr>
										<td>MG - MODIFICACION DE DATOS VARIOS DEL CLIENTE</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableMovPrepagoTrat">
						</div>	
						<br style="clear:both;">
						
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">PENDIENTES</td>
									</tr>
									<tr>
										<td>AG - ALTA CLIENTE</td>
									</tr>
									<tr>
										<td>BG - BAJA CLIENTE</td>
									</tr>
									<tr>
										<td>MG - MODIFICACION DE DATOS VARIOS DEL CLIENTE</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableMovPrepagoPend">
						</div>	
						<br style="clear:both;">
						
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">ERRORES</td>
									</tr>
									<tr id="LPEAG">
										<td>AG - ALTA CLIENTE</td>
									</tr>
									<tr id="LPEBG">
										<td>BG - BAJA CLIENTE</td>
									</tr>
									<tr id="LPEMG">
										<td>MG - MODIFICACION DE DATOS VARIOS DEL CLIENTE</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableMovPrepagoErrores">
						</div>	
						<br style="clear:both;">
						
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">ERRORES INFORMATIVOS</td>
									</tr>
									<tr id="LPWAG">
										<td>AG - ALTA CLIENTE</td>
									</tr>
									<tr id="LPWBG">
										<td>BG - BAJA CLIENTE</td>
									</tr>
									<tr id="LPWMG">
										<td>MG - MODIFICACION DE DATOS VARIOS DEL CLIENTE</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableMovPrepagoErroresInfor">
						</div>	
						<br style="clear:both;">
					</div>					

					<div id="divReinyeccion" class="divDatosGenerales divDatosReinyeccion" style="display: none; padding:0;">
						<div style="overflow-x: scroll; overflow-y: hidden; width:732px;">
						<table>
							<tr>
								<td style="border: 0 solid #DDDDDD;">
									<div class="colReinyeccA" style="width: 236px;">
										<!-- REINYECCION MOVIMIENTOS FIJA -->
										<div class="table_container_left" style="width: 95px;">
											
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">MOV. FIJA</td>
													</tr>
													<tr>
														<td style="padding:0 0">TRATADOS</td>
													</tr>
													<tr>
														<td style="padding:0 0">PENDIENTES</td>
													</tr>
													<tr>
														<td style="padding:0 0">ERRORES</td>
													</tr>
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecMovFija" style="width: 139px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">TRAT. FIJA</td>
													</tr>
													<tr>
														<td style="padding:0 0">F - ALTA</td>
													</tr>
													<tr>
														<td style="padding:0 0">S - MODIFICACION</td>
													</tr>
  													<tr>
														<td style="padding:0 0">&nbsp;</td>
													</tr>												
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecTratFija" style="width: 138px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">PEND. FIJA</td>
													</tr>
													<tr>
														<td style="padding:0 0">F - ALTA</td>
													</tr>
													<tr>
														<td style="padding:0 0">S - MODIFICACION</td>
													</tr> 													
													<tr>
														<td style="padding:0 0">&nbsp;</td>
													</tr>																										
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecPendFija" style="width: 138px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">ERR. FIJA</td>
													</tr>
													<tr id="LFREA">
														<td style="padding:0 0">F - ALTA</td>
													</tr>
													<tr id="LFREM">
														<td style="padding:0 0">S - MODIFICACION</td>
													</tr>  													
													<tr id="LFREB">
														<td style="padding:0 0">&nbsp;</td>
													</tr>
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecErrorFija" style="width: 138px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">ERR. INF. FIJA</td>
													</tr>
													<tr id="LFRWA">
														<td style="padding:0 0">F - ALTA</td>
													</tr>
													<tr id="LFRWM">
														<td style="padding:0 0">S - MODIFICACION</td>
													</tr>  													
													<tr id="LFRWB">
														<td style="padding:0 0">&nbsp;</td>
													</tr>
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecInfoFija" style="width: 138px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										<!-- FIN REINYECCION MOVIMIENTOS FIJA -->
									</div>
								</td>
								<td style="border: 0 solid #DDDDDD;">
									<div class="colReinyeccA" style="width: 239px;">
										<!-- REINYECCION MOVIMIENTOS MOVIL -->
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">MOV. MOVIL</td>
													</tr>
													<tr>
														<td style="padding:0 0">TRATADOS</td>
													</tr>
													<tr>
														<td style="padding:0 0">PENDIENTES</td>
													</tr>
													<tr>
														<td style="padding:0 0">ERRORES</td>
													</tr>
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecMovMovil" style="width: 138px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">TRAT. MOVIL</td>
													</tr>
													<tr>
														<td style="padding:0 0">RA - ALTA</td>
													</tr>
													<tr>
														<td style="padding:0 0">MA - MODIFICACION</td>
													</tr>  													
													<tr>
														<td style="padding:0 0">&nbsp;</td>
													</tr>																										
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecTratMovil" style="width: 138px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">PEND. MOVIL</td>
													</tr>
													<tr>
														<td style="padding:0 0">RA - ALTA</td>
													</tr>
													<tr>
														<td style="padding:0 0">MA - MODIFICACION</td>
													</tr>  													
													<tr>
														<td style="padding:0 0">&nbsp;</td>
													</tr>																										
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecPendMovil" style="width: 138px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">ERR. MOVIL</td>
													</tr>
													<tr id="LMREA">
														<td style="padding:0 0">RA - ALTA</td>
													</tr>
													<tr id="LMREM">
														<td style="padding:0 0">MA - MODIFICACION</td>
													</tr>  													
													<tr id="LMREB">
														<td style="padding:0 0">&nbsp;</td>
													</tr>																										
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecErrorMovil" style="width: 138px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">ERR. INF. MOVIL</td>
													</tr>
													<tr id="LMRWA">
														<td style="padding:0 0">RA - ALTA</td>
													</tr>
													<tr id="LMRWM">
														<td style="padding:0 0">MA - MODIFICACION</td>
													</tr>  													
													<tr id="LMRWB">
														<td style="padding:0 0">&nbsp;</td>
													</tr>																										
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecInfoMovil" style="width: 138px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										<!-- FIN REINYECCION MOVIMIENTOS MOVIL -->
									</div>	
								</td>
								<td style="border: 0 solid #DDDDDD;">
									<div class="colReinyeccA" style="width: 239px;">
										<!-- REINYECCION MOVIMIENTOS PREPAGO -->
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">MOV. PREPAGO</td>
													</tr>
													<tr>
														<td style="padding:0 0">TRATADOS</td>
													</tr>
													<tr>
														<td style="padding:0 0">PENDIENTES</td>
													</tr>
													<tr>
														<td style="padding:0 0">ERRORES</td>
													</tr>
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecMovPrepago" style="width: 138px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">TRAT. PREPAGO</td>
													</tr>
													<tr>
														<td style="padding:0 0">GA - ALTA</td>
													</tr>
													<tr>
														<td style="padding:0 0">GM - MODIFICACION</td>
													</tr>
													<tr>
														<td style="padding:0 0">GB - BAJA</td>
													</tr>													
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecTratPrepago" style="width: 138px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">PEND. PREPAGO</td>
													</tr>
													<tr>
														<td style="padding:0 0">GA - ALTA</td>
													</tr>
													<tr>
														<td style="padding:0 0">GM - MODIFICACION</td>
													</tr>
													<tr>
														<td style="padding:0 0">GB - BAJA</td>
													</tr>													
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecPendPrepago" style="width: 138px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">ERR. PREPAGO</td>
													</tr>
													<tr id="LPREA">
														<td style="padding:0 0">GA - ALTA</td>
													</tr>
													<tr id="LPREM">
														<td style="padding:0 0">GM - MODIFICACION</td>
													</tr>
													<tr id="LPREB">
														<td style="padding:0 0">GB - BAJA</td>
													</tr>													
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecErrorPrepago" style="width: 138px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">ERR. INF. PREPAGO</td>
													</tr>
													<tr id="LPRWA">
														<td style="padding:0 0">GA - ALTA</td>
													</tr>
													<tr id="LPRWM">
														<td style="padding:0 0">GM - MODIFICACION</td>
													</tr>
													<tr id="LPRWB">
														<td style="padding:0 0">GB - BAJA</td>
													</tr>																							
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecInfoPrepago" style="width: 138px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										<!-- FIN REINYECCION MOVIMIENTOS PREPAGO -->
									</div>	
								</td>								
								<td style="border: 0 solid #DDDDDD;">								
									<div class="colReinyeccA" style="width: 245px;">
										<!-- REINYECCION MOVIMIENTOS TOTALES -->
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">MOV. TOTALES</td>
													</tr>
													<tr>
														<td style="padding:0 0">TRATADOS</td>
													</tr>
													<tr>
														<td style="padding:0 0">PENDIENTES</td>
													</tr>
													<tr>
														<td style="padding:0 0">ERRORES</td>
													</tr>
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecMovTot" style="width: 139px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td style="padding:0 0; padding-left:12px;">TRAT. TOTALES</td>
													</tr>
													<tr>
														<td style="padding:0 0">ALTA</td>
													</tr>
													<tr>
														<td style="padding:0 0">MODIFICACION</td>
													</tr>
													<tr>
														<td style="padding:0 0">BAJA</td>
													</tr>													
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecTratTot" style="width: 139px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td style="padding:0 0; padding-left:12px;">PEND. TOTALES</td>
													</tr>
													<tr>
														<td style="padding:0 0">ALTA</td>
													</tr>
													<tr>
														<td style="padding:0 0">MODIFICACION</td>
													</tr>
													<tr>
														<td style="padding:0 0">BAJA</td>
													</tr>													
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecPendTot" style="width: 139px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">ERR. TOTALES</td>
													</tr>
													<tr id="LTREA">
														<td style="padding:0 0">ALTA</td>
													</tr>
													<tr id="LTREM">
														<td style="padding:0 0">MODIFICACION</td>
													</tr>
													<tr id="LTREB">
														<td style="padding:0 0">BAJA</td>
													</tr>													
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecErrorTot" style="width: 139px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										
										<div class="table_container_left" style="width: 95px;">
											<table class="left_table">
												<tbody>
													<tr>
														<td class="noBrTable">&nbsp;</td>
													</tr>
													<tr>
														<td class="tab15" style="padding:0 0">ERR. INF. TOT.</td>
													</tr>
													<tr id="LTRWA">
														<td style="padding:0 0">ALTA</td>
													</tr>
													<tr id="LTRWM">
														<td style="padding:0 0">MODIFICACION</td>
													</tr>
													<tr id="LTRWB">
														<td style="padding:0 0">BAJA</td>
													</tr>													
												</tbody>
											</table>
										</div>
										
										<div class="table_container_right" id="tableReinyecInfoTot" style="width: 139px;overflow-x: hidden;">
										</div>
										<br style="clear:both;">
										<!-- FIN REINYECCION MOVIMIENTOS TOTALES -->
									</div>								
								</td>
							</tr>
						</table>
						</div>
					</div>
										
					<div id="divErrores" class="divDatosGenerales" style="display:none;width: 753px; padding:0;">
						<table class="tableErrores">
							<thead>
								<tr>
									<th class="colCabecera">CODIGOS DE ERROR</th>
									<th class="colCabecera">DESCRIPCION DE ERRORES</th>
									<th class="colCabecera">INFORMATIVOS</th>
									<th class="colCabecera">CALIDAD DEL DATO</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>QGC001</td>
									<td>DATOS DE ENTRADA SIN INFORMAR</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC002</td>
									<td>NUMERO DE DOC EXISTE EN BB.DD. PARA OTRO CLIENTE</td>
									<td>SI</td>
									<td>SI</td>
								</tr>
								<tr>
									<td>QGC003</td>
									<td>ALTA EXISTENTE EN LA BB DD</td>
									<td>SI</td>
									<td>SI</td>
								</tr>
								<tr>
									<td>QGC004</td>
									<td>EXISTENTE DNI PARA OTRO CUC DE LA MISMA LN</td>
									<td>SI</td>
									<td>SI</td>
								</tr>
								<tr>
									<td>QGC005</td>
									<td>EL CLIENTE NO EXISTE</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC006</td>
									<td>EL CLIENTE YA HA SIDO HISTORIFICADO </td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC007</td>
									<td>YA EXISTE EL CLIENTE EN MOVIL</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC008</td>
									<td>NO EXISTE EL CLIENTE EN MOVIL</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC009</td>
									<td>EL CLIENTE YA HA SIDO HISTORIFICADO  EN MOVIL</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC010</td>
									<td>YA EXISTE EL CLIENTE EN FIJA</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC011</td>
									<td>NO EXISTE EL CLIENTE EN FIJA</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC012</td>
									<td>EL CLIENTE YA HA SIDO HISTORIFICADO  EN FIJA</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC013</td>
									<td>EXISTE MOVIMIENTO EN CVCL</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC014</td>
									<td>EXISTE MOVIMIENTO EN ERCL</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC015</td>
									<td>NO EXISTE MOVIMIENTO EN CVCL</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC016</td>
									<td>NO EXISTE MOVIMIENTO EN ERCL</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC017</td>
									<td>NUMERO DE DOCUMENTO NO VALIDO PARA ESTE TIPO DE DOCUMENTO</td>
									<td>SI</td>
									<td>SI</td>
								</tr>
								<tr>
									<td>QGC018</td>
									<td>SEGMENTO Y SUBSEGMENTO ERRONEOS</td>
									<td>SI</td>
									<td>SI</td>
								</tr>
								<tr>
									<td>QGC019</td>
									<td>SEGMENTO ERRONEO</td>
									<td>SI</td>
									<td>SI</td>
								</tr>
								<tr>
									<td>QGC020</td>
									<td>ESTADO DE CLIENTE 4 NO VALIDO PARA MOVIMIENTO DE ALTA/MODIFICACION</td>
									<td>SI</td>
									<td>SI</td>
								</tr>
								<tr>
									<td>QGC021</td>
									<td>ESTADO DE CLIENTE DISTINTO DE 4 NO VALIDO PARA MOVIMIENTO DE BAJA</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC022</td>
									<td>ESTADO EN  NSCO INCORRECTO</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC023</td>
									<td>CODIGO DE PAIS DE NSCO INEXISTENTE</td>
									<td>SI</td>
									<td>SI</td>
								</tr>
								<tr>
									<td>QGC024</td>
									<td>MOVIMIENTO DE BAJA PARA CLIENTE INEXISTENTE EN LA BBDD</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC025</td>
									<td>TIPO Y NUMERO DE DOCUMENTO SIN INFORMAR</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC026</td>
									<td>NOMBRE, APELLIDOS Y RAZON SOCIAL SIN INFORMAR</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC027</td>
									<td>TIPO DE DOCUMENTO NO VALIDO</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC028</td>
									<td>LETRA CIF INVALIDA</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC029</td>
									<td>LETRA RESIDENTE INVALIDA</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC030</td>
									<td>Cliente NSCO duplicado en ICGB</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC031</td>
									<td>ERROR EN ACCESO A DB2</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC032</td>
									<td>FALTA TIPO-MOV O CUC O DOCUMENTO O NOMBRE</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC033</td>
									<td>FALTA TIPO-MOV O CUC</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGC034</td>
									<td>TIPO DE DOCUMENTO C</td>
									<td>SI</td>
									<td>SI</td>
								</tr>
								<tr>
									<td>QGCS00</td>
									<td>ERROR EN COMUNICACIÓN DE SEGMENTO A LA OTRA LN</td>
									<td>SI</td>
									<td></td>
								</tr>
								<tr>
									<td>QGCXXX</td>
									<td>CONTENCIÓN DB2</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>QGO007</td>
									<td>FORMATO ERRONEO DE FECHAS</td>
									<td>SI</td>
									<td>SI</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div>
				<html:img styleClass="imgInfL" page="/images/QGrecContInfIzq.gif" />
				<html:img styleClass="imgInfR" page="/images/QGrecContInfDrch.gif" />
			</div>	
		</div>
	</div>
</div>		
<iframe id="ifExportar" style="display:none"></iframe>
<br class="clear"/>
</sec:authorize>