<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html:xhtml/>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
<div class="recCentral">

	<div class="recHead">
		<html:img styleClass="imgSupL" page="/images/QGrecContSupIzq.gif" />
		<html:img styleClass="imgSupR" page="/images/QGrecContSupDrch.gif" />
		<h2 id="tituloCabecera">Autorizaciones</h2>
		
		<div class="bTitBotones">
		</div>
	</div>
	
	<div id="recPPal" class="recCentContFC">
		
		<br />
		
		<div id="divPes" class="divPes">
			<ul>
				<li id="pesAutorizaciones" class="activa">
					<span>Autorizaciones</span>
				</li>
				<sec:authorize ifAnyGranted="ROLE_SA">
				<li id="pesSisExt">
					<span>Sistemas externos</span>
				</li>
				</sec:authorize>
				<li id="pesServiciosNA">
					<span>Servicios NA</span>
				</li>
			</ul>
			
			<div class="contPest">
				<html:img styleClass="imgCPesL" page="/images/QGcontPestLR.gif" />
				<html:img styleClass="imgCPesR" page="/images/QGcontPestLR.gif" />
				
				<!-- INICIO PESTANA AUTORIZACIONES -->
				<div id="divAutorizaciones" class="divDatosGenerales">
					<html:form action="/Autorizaciones">
						<html:hidden styleId="hiddenUsuarioConectado" name="QGAutorizacionesForm" property="usuarioLogado" />
					</html:form>
					<table>
						<tbody>
							<tr>
								<td>
									<div class="subFormulario">
										<span class="fieldset">Sistema externo</span>
										<table>
											<colgroup><col width="110"/><col width="*"/></colgroup>
											<tbody>
												<tr>
													<td>
														<label class="txtDefault">C&oacute;digo</label><br />
														<select class="wtc10" id="selCodigoSE">
															<option value="">Seleccione...</option>
														</select>
													</td>
													<td>
														<label class="txtDefault">Descripci&oacute;n:</label><br />
														<input type="text" class="wtc20 dis" readonly="readonly" maxlength="50" id="txtDescripcionSE" />
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</td>
								<td>
									<div class="subFormulario">
										<span class="fieldset">Servicio NA</span>
										<table>
											<colgroup><col width="110"/><col width="*"/></colgroup>
											<tbody>
												<tr>
													<td>
														<label class="txtDefault">C&oacute;digo</label><br />
														<select class="wtc10" id="selCodigoSNA">
															<option value="">Seleccione...</option>
														</select>
													</td>
													<td>
														<label class="txtDefault">Descripci&oacute;n:</label><br />
														<input type="text" class="wtc20 dis" readonly="readonly" maxlength="50" id="txtDescripcionSNA" />
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</td>
								<td>
									<div class="divBtnAutorizaciones">
										<html:image styleClass="btn" styleId="btnFiltrar" page="/images/botones/QGbtFiltrar.gif" alt="Filtrar" onclick="return false;"/>
										<html:image styleClass="btn" styleId="btnVerTodos" page="/images/botones/QGbtVerTodos.gif" alt="Ver Todos" onclick="return false;" />
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					
					<div id="divGridAutorizaciones" class="mTop10"></div>
					
					<div class="divBotones">
						<span class="edicion">
							<html:image styleClass="btn" styleId="btnHistorico" page="/images/botones/QGbtHistorico.gif" alt="Historico" onclick="return false;"/>
						</span>
						<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
						<span class="edicion">
							<html:image styleClass="btn" styleId="btnNuevo" page="/images/botones/QGbtListadoNuevo.gif" onclick="return false;" />
							<html:image styleClass="btn btnDis" styleId="btnEliminar" disabled="true" page="/images/botones/QGbtEliminar_des.gif" onclick="return false;" />
						</span>
						</sec:authorize>
						<%--<html:image styleClass="btn" styleId="btnImprimir" page="/images/botones/QGbtImprimir.gif" alt="Imprimir" onclick="return false;"/> --%>
					</div>
					
					<div id="idFormAutorizaciones" class="formListDet" style="display: none;">
						<span class="titFormListado">
							Autorizaci&oacute;n
							<sec:authorize ifAnyGranted="ROLE_SA">
							<span class="txtRed normal">(si no existe alg&uacute;n Sistema Externo proceder a su alta en la pesta&ntilde;a correspondiente)</span>
							</sec:authorize>
							<sec:authorize ifAnyGranted="ROLE_AD">
							<span class="txtRed normal">(si no existe alg&uacute;n Sistema Externo solicitar su alta al Superadministrador de la aplicaci&oacute;n)</span>
							</sec:authorize>
						</span>
						
						
						<div class="formBusc">
							<div class="pRel">
								<html:img styleClass="imgBuscFormTL" page="/images/QGBuscCTL.gif" />
								<html:img styleClass="imgBuscFormTR" page="/images/QGBuscCTR.gif" />
							</div>
							<div class="formBuscCont">
								<div class="fLeft subFormulario">
									<span class="fieldset">Sistema externo</span>
									<table>
										<colgroup><col width="110"/><col width="*"/></colgroup>
										<tbody>
											<tr>
												<td>
													<label>C&oacute;digo</label><br />
													<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
													<select class="wtc10" id="selCodigoSEed">
														<option value="">Seleccione...</option>
													</select>
													</sec:authorize>
													<sec:authorize ifAnyGranted="ROLE_AU">
													<select class="wtc10" id="selCodigoSEed" disabled="disabled">
														<option value="">Seleccione...</option>
													</select>
													</sec:authorize>
												</td>
												<td>
													<label>Descripci&oacute;n:</label><br />
													<input type="text" class="w138 dis" maxlength="50" readonly="readonly" id="txtDescripcionSEed" />
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								
								<div class="fLeft subFormulario mLeft10">
									<span class="fieldset">Servicio NA</span>
									<table>
										<colgroup><col width="110"/><col width="*"/></colgroup>
										<tbody>
											<tr>
												<td>
													<label>C&oacute;digo</label><br />
													<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
													<select class="wtc10" id="selCodigoSNAed">
														<option value="">Seleccione...</option>
													</select>
													</sec:authorize>
													<sec:authorize ifAnyGranted="ROLE_AU">
													<select class="wtc10" id="selCodigoSNAed" disabled="disabled">
														<option value="">Seleccione...</option>
													</select>
													</sec:authorize>
												</td>
												<td>
													<label>Descripci&oacute;n:</label><br />
													<input type="text" class="w138 dis" maxlength="50" id="txtDescripcionSNAed" readonly="readonly" />
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								
								<div class="fLeft mLeft10 mTop10">
									<label>Linea negocio</label><br/>
									<sec:authorize ifAnyGranted="ROLE_SA">
									<select id="lineaNegocio" class="wtc10">
										<option value=""></option>
										<option value="C">Convergente</option>
										<option value="F">Fija</option>
										<option value="M">M&oacute;vil</option>
									</select>
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_AD,ROLE_AU">
									<select id="lineaNegocio" class="wtc10" disabled="disabled">
										<option value=""></option>
										<option value="C">Convergente</option>
										<option value="F">Fija</option>
										<option value="M">M&oacute;vil</option>
									</select>
									</sec:authorize>
								</div>
								
								<div class="fRight">
									<table>
										<colgroup><col width="110"/><col width="*"/></colgroup>
										<tbody>
											<tr>
												<td>
													<label>Usuario de alta</label><br />
													<input type="text" class="wtc8 dis" id="txtUsuarioAlta" readonly="readonly" />
												</td>
												<td>
													<label>Usuario de modificaci&oacute;n</label><br />
													<input type="text" class="wtc8 dis" id="txtUsuarioMod" readonly="readonly" />
												</td>
											</tr>
											<tr>
												<td class="vBot">
													<label>Intervalo de vigencia</label><br />
													<div class="divCalendar" id="divTxtFechaInicio">
														<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
														<input type="text" maxlength="10" class="inputCalendar" id="txtFechaInicio" />
														</sec:authorize>
														<sec:authorize ifAnyGranted="ROLE_AU">
														<input type="text" maxlength="10" class="inputCalendar" id="txtFechaInicio" disabled="disabled"/>
														</sec:authorize>
														<html:image styleClass="icoCalendar" styleId="btnCalendarFechaInicio" page="/images/iconos/QGicoCalendar.gif"/>
													</div>
												</td>
												<td class="vBot">
													<div class="divCalendar" id="divTxtFechaFin">
														<input type="text" maxlength="10" class="inputCalendar" id="txtFechaFin" />
														<html:image styleClass="icoCalendar" styleId="btnCalendarFechaFin" page="/images/iconos/QGicoCalendar.gif"/>
													</div>
												</td>
											</tr>
										</tbody>
									</table>			
								</div>						
							</div><!-- FIN formBuscCont -->
							
							<div class="pRel">
								<html:img styleClass="imgBuscFormBL" page="/images/QGBuscCBL.gif" />
								<html:img styleClass="imgBuscFormBR" page="/images/QGBuscCBR.gif" />
							</div>
						</div><!-- FIN formBusc -->
						
						<div class="divBotones">
							<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
							<html:image styleClass="btn" styleId="btnGuardar" page="/images/botones/QGbtGuardar.gif" onclick="return false;" />
							<html:image styleClass="btn" styleId="btnCancelar" page="/images/botones/QGbtCancelarNuevo.gif" onclick="return false;"/>
							</sec:authorize>
						</div>
						
					</div><!-- FIN idFormAutorizaciones -->
				</div><!-- FIN PESTANA AUTORIZACIONES -->
				
				<!-- INICIO PESTANA SISTEMAS EXTERNOS -->
				<sec:authorize ifAnyGranted="ROLE_SA">
				<div id="divSisExt" class="divDatosGenerales" style="display: none;">
				
					<div id="divGridSisExt"></div>
				
					<div class="divBotones">			
						<span class="edicion">
							<html:image styleClass="btn" styleId="btnNuevoSis" page="/images/botones/QGbtListadoNuevo.gif" onclick="return false;" />
							<html:image styleClass="btn btnDis" styleId="btnEliminarSis" disabled="true" page="/images/botones/QGbtEliminar_des.gif" onclick="return false;" />
						</span>
						<%--<html:image styleClass="btn" styleId="btnImprimirSis" page="/images/botones/QGbtImprimir.gif" alt="Imprimir" onclick="return false;"/> --%>
					</div>
									
					<div id="idFormSistemasExternos" class="formListDet" style="display: none;">
						<span class="titFormListado">Sistemas Externos</span>
						<div class="formBusc">
							<div class="pRel">
								<html:img styleClass="imgBuscFormTL" page="/images/QGBuscCTL.gif" />
								<html:img styleClass="imgBuscFormTR" page="/images/QGBuscCTR.gif" />
							</div>
							<div class="formBuscCont">
								
								
								<div class="fLeft">
									<table>
										<colgroup>
											<col width="60"/><col width="129"/><col width="*"/><col width="132"/><col width="*"/>
										</colgroup>
										<tbody>
											<tr>
												<td>
													<label>Anagrama</label><br />
													<input type="text" class="wtc3" id="txtAnagramaSis" />
												</td>
												<td>
													<label>Nombre</label><br />
													<input type="text" maxlength="" class="wtc10" id="txtNombreSis" />
												</td>
												<td class="vBot">
													<label>Intervalo de vigencia</label><br />
													<div class="divCalendar" id="divTxtFechaInicioSis">
														<input type="text" maxlength="10" class="inputCalendar" id="txtFechaInicioSis" />
														<html:image styleClass="icoCalendar" styleId="btnCalendarFechaInicioSis" page="/images/iconos/QGicoCalendar.gif"/>
													
													</div>
												</td>
												<td class="vBot">
													<div class="divCalendar" id="divTxtFechaFinSis">
														<input type="text" maxlength="10" class="inputCalendar" id="txtFechaFinSis" />
														<html:image styleClass="icoCalendar" styleId="btnCalendarFechaFinSis" page="/images/iconos/QGicoCalendar.gif"/>
													</div>
												</td>
												<td>
													<label>Linea negocio</label><br/>
													<select id="lineaNegocioSis" class="wtc5">
														<option value=""></option>
													</select>
													<input type="text" class="dis" readonly="readonly" maxlength="50" id="txtDescripcionLNS" />
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="fRight">
									<table>
										<colgroup><col width="110"/><col width="*"/></colgroup>
										<tbody>
											<tr>
												<td>
													<label>Usuario de alta</label><br />
													<input type="text" class="wtc8 dis" id="txtUsuarioAltaSis" readonly="readonly" />
												</td>
												<td>
													<label>Usuario de modificaci&oacute;n</label><br />
													<input type="text" class="wtc8 dis" id="txtUsuarioModSis" readonly="readonly" />
												</td>
											</tr>
										</tbody>
									</table>				
								</div>
								
								<br class="clear"/>
								<div>
									<label>Descripci&oacute;n</label><br />
									<input type="text" maxlength="60" class="wtc60" id="txtDescripcionSis" />
								</div>				
							</div><!-- FIN formBuscCont -->
							
							<div class="pRel">
								<html:img styleClass="imgBuscFormBL" page="/images/QGBuscCBL.gif" />
								<html:img styleClass="imgBuscFormBR" page="/images/QGBuscCBR.gif" />
							</div>
						</div><!-- FIN formBusc -->
						<div class="divBotones">
							<html:image styleClass="btn" styleId="btnGuardarSis" page="/images/botones/QGbtGuardar.gif" onclick="return false;" />
							<html:image styleClass="btn" styleId="btnCancelarSis" page="/images/botones/QGbtCancelarNuevo.gif" onclick="return false;"/>
						</div>
					</div><!-- FIN idFormSistemasExternos -->
					
				</div><!-- FIN PESTANA SISTEMAS EXTERNOS -->
				</sec:authorize>
				
				<!-- INICIO PESTANA SERVICIOS NA -->
				<div id="divServiciosNA" class="divDatosGenerales" style="display: none;">
			
					<div id="divGridServiciosNA"></div>
				
					<div class="divBotones">
						<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
						<span class="edicion">
							<html:image styleClass="btn" styleId="btnNuevoSer" page="/images/botones/QGbtListadoNuevo.gif" onclick="return false;" />
							<html:image styleClass="btn btnDis" styleId="btnEliminarSer" disabled="true" page="/images/botones/QGbtEliminar_des.gif" onclick="return false;" />
						</span>
						</sec:authorize>
						<%--<html:image styleClass="btn" styleId="btnImprimirSer" page="/images/botones/QGbtImprimir.gif" alt="Imprimir" onclick="return false;"/> --%>
					</div>
					
					
					
					
					<div id="idFormServiciosNA" class="formListDet" style="display: none;">
						<span class="titFormListado">Servicio NA</span>
						<div class="formBusc">
							<div class="pRel">
								<html:img styleClass="imgBuscFormTL" page="/images/QGBuscCTL.gif" />
								<html:img styleClass="imgBuscFormTR" page="/images/QGBuscCTR.gif" />
							</div>
							<div class="formBuscCont">
								
								
								<div class="fLeft">
									<table>
										<colgroup><col width="109"/><col width="*"/></colgroup>
										<tbody>
											<tr>
												<td>
													<label>C&oacute;digo</label><br />
													<input type="text" class="wtc8" id="txtCodigoSer" />
												</td>
												<td>
													<label>Descripci&oacute;n</label><br />
													<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
													<input type="text" maxlength="60" class="wtc50" id="txtDescripcionSer" />
													</sec:authorize>
													<sec:authorize ifAnyGranted="ROLE_AU">
													<input type="text" maxlength="60" class="wtc50" id="txtDescripcionSer" disabled="disabled"/>
													</sec:authorize>
												</td>
											</tr>
										</tbody>
									</table>
									<table>
										<colgroup>
											<col width="110"/><col width="110"/><col width="160"/>
											<col width="*"/><col width="*"/><col width="*"/>
										</colgroup>
										<tbody>
											<tr>
												<td><label>Funci&oacute;n</label></td>
												<td><label>Programa</label></td>
												<td colspan="4"><label>Tipo Servicio</label></td>
											</tr>
											<tr>
												<td>
													<sec:authorize ifAnyGranted="ROLE_SA">
													<select id="selFuncionSer" class="wtc10">
														<option value=""></option>
														<option value="000">TODAS LAS FUNCIONES</option>
														<option value="100">ADMINISTRACI&Oacute;N DEL SISTEMA</option>
														<option value="200">CONSULTAS</option>
													</select>
													</sec:authorize>
													<sec:authorize ifAnyGranted="ROLE_AD,ROLE_AU">
													<select id="selFuncionSer" class="wtc10" disabled="disabled">
														<option value=""></option>
														<option value="000">TODAS LAS FUNCIONES</option>
														<option value="100">ADMINISTRACI&Oacute;N DEL SISTEMA</option>
														<option value="200">CONSULTAS</option>
													</select>
													</sec:authorize>
												</td>
												<td class="vTop">
													<div class="grupoRad">
														<sec:authorize ifAnyGranted="ROLE_SA">
														<input id="rbBatch" type="radio" value="batch" name="rbBatch">
														</sec:authorize>
														<sec:authorize ifAnyGranted="ROLE_AD,ROLE_AU">
														<input id="rbBatch" type="radio" value="batch" name="rbBatch" disabled="disabled">
														</sec:authorize>
														<label>Batch</label>
														<sec:authorize ifAnyGranted="ROLE_SA">
														<input id="rbOnline" type="radio" value="online" name="rbOnline">
														</sec:authorize>
														<sec:authorize ifAnyGranted="ROLE_AD,ROLE_AU">
														<input id="rbOnline" type="radio" value="online" name="rbOnline" disabled="disabled">
														</sec:authorize>
														<label>Online</label>
													</div>
												</td>
												<td class="vTop">
													<div class="grupoRad">
														<sec:authorize ifAnyGranted="ROLE_SA">
														<input id="rbConsulta" type="radio" value="consulta" name="rbConsulta">
														</sec:authorize>
														<sec:authorize ifAnyGranted="ROLE_AD,ROLE_AU">
														<input id="rbConsulta" type="radio" value="consulta" name="rbConsulta" disabled="disabled">
														</sec:authorize>
														<label>Consulta</label>
														<sec:authorize ifAnyGranted="ROLE_SA">
														<input id="rbAutorizacion" type="radio" value="autorizacion" name="rbAutorizacion">
														</sec:authorize>
														<sec:authorize ifAnyGranted="ROLE_AD,ROLE_AU">
														<input id="rbAutorizacion" type="radio" value="autorizacion" name="rbAutorizacion" disabled="disabled">
														</sec:authorize>
														<label>Actualizaci&oacute;n</label>
													</div>
												</td>
												<td class="vTop">
													<div class="grupoRad">
														<sec:authorize ifAnyGranted="ROLE_SA">
														<input type="checkbox" id="chkDis" />
														</sec:authorize>
														<sec:authorize ifAnyGranted="ROLE_AD,ROLE_AU">
														<input type="checkbox" id="chkDis" disabled="disabled"/>
														</sec:authorize>
														<label>Display en SYSOUT</label>
													</div>
												</td>
												<td class="vTop">
													<div class="grupoRad">
														<sec:authorize ifAnyGranted="ROLE_SA">
														<input type="checkbox" id="chkLog" />
														</sec:authorize>
														<sec:authorize ifAnyGranted="ROLE_AD,ROLE_AU">
														<input type="checkbox" id="chkLog" disabled="disabled"/>
														</sec:authorize>
														<label>LOG ejecuci&oacute;n</label>
													</div>
												</td>
												<td class="vTop">
													<div class="grupoRad">
														<sec:authorize ifAnyGranted="ROLE_SA">
														<input type="checkbox" id="chkHab" />
														</sec:authorize>
														<sec:authorize ifAnyGranted="ROLE_AD,ROLE_AU">
														<input type="checkbox" id="chkHab" disabled="disabled"/>
														</sec:authorize>
														<label>Habilitado</label>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="fRight">
									<table>
										<colgroup><col width="110"/><col width="*"/></colgroup>
										<tbody>
											<tr>
												<td>
													<label>Usuario de alta</label><br />
													<input type="text" class="wtc8 dis" id="txtUsuarioAltaSer" readonly="readonly" />
												</td>
												<td>
													<label>Usuario de modificaci&oacute;n</label><br />
													<input type="text" class="wtc8 dis" id="txtUsuarioModSer" readonly="readonly" />
												</td>
											</tr>
											<tr>
												<td class="vBot">
													<label>Intervalo de vigencia</label><br />
													<div class="divCalendar" id="divTxtFechaInicioSer">
														<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
														<input type="text" maxlength="10" class="inputCalendar" id="txtFechaInicioSer" />
														</sec:authorize>
														<sec:authorize ifAnyGranted="ROLE_AU">
														<input type="text" maxlength="10" class="inputCalendar" id="txtFechaInicioSer" disabled="disabled"/>
														</sec:authorize>
														<html:image styleClass="icoCalendar" styleId="btnCalendarFechaInicioSer" page="/images/iconos/QGicoCalendar.gif"/>
													
													</div>
												</td>
												<td class="vBot">
													<div class="divCalendar" id="divTxtFechaFinSer">
														<input type="text" maxlength="10" class="inputCalendar" id="txtFechaFinSer" />
														<html:image styleClass="icoCalendar" styleId="btnCalendarFechaFinSer" page="/images/iconos/QGicoCalendar.gif"/>
													</div>
												</td>
											</tr>
										</tbody>
									</table>				
								</div>		
							</div><!-- FIN formBuscCont -->
							
							<div class="pRel">
								<html:img styleClass="imgBuscFormBL" page="/images/QGBuscCBL.gif" />
								<html:img styleClass="imgBuscFormBR" page="/images/QGBuscCBR.gif" />
							</div>
						</div><!-- FIN formBusc -->
						<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD">
						<div class="divBotones">
							<html:image styleClass="btn" styleId="btnGuardarSer" page="/images/botones/QGbtGuardar.gif" onclick="return false;" />
							<html:image styleClass="btn" styleId="btnCancelarSer" page="/images/botones/QGbtCancelarNuevo.gif" onclick="return false;"/>
						</div>
						</sec:authorize>
					</div><!-- FIN idFormServiciosNA -->
					
				</div><!-- FIN PESTANA SERVICIOS NA -->

			</div>
		</div><!-- FIN DIV divPes -->
		
		<div id="divHistorico" style="display: none;">
			<div id="divGridHistorico"></div>
			<div class="divBotones">
				<html:image styleClass="btn" styleId="btnVolver" page="/images/botones/QGbtVolver.gif" alt="Volver" onclick="return false;"/>
			</div>
		</div>
		
		
		<div>
			<html:img styleClass="imgInfL" page="/images/QGrecContInfIzq.gif" />
			<html:img styleClass="imgInfR" page="/images/QGrecContInfDrch.gif" />
		</div>	
	</div>
	<iframe id="ifExportar" style="display:none"></iframe>
</div>		
</sec:authorize>