<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html:xhtml/>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
<html:form action="/Presegmentacion">
	<html:hidden styleId="hiddenUsuarioConectado" name="QGSegmentacionesPresegForm" property="usuarioLogado" />
</html:form>

<div class="recCentral">
	<div class="recHead" id="divTituloPrincipal">
		<html:img styleClass="imgSupL" page="/images/QGrecContSupIzq.gif" />
		<html:img styleClass="imgSupR" page="/images/QGrecContSupDrch.gif" />
		<h2>Presegmentaci&oacute;n</h2>
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
		 <div class="bTitBotones">
			<div class="bTit bTitCerrar" id="btnAdmin">
				<span>
					<b class="iconoAdmin"></b>
					<span id="titAdmin" style="font-size: 10pt;font-weight: bold"> Administraci&oacute;n </span>
				</span>
			</div>
		</div> 
		</sec:authorize>
	</div>
	
	<div class="recHead" id="divTituloHistorico" style="display: none;">
		<html:img styleClass="imgSupL" page="/images/QGrecContSupIzq.gif" />
		<html:img styleClass="imgSupR" page="/images/QGrecContSupDrch.gif" />
		<h2>Hist&oacute;rico Presegmentaci&oacute;n</h2>
	</div>
	 
	<div id="recPPal" class="recCentCont">
		<div id="divPrincipal">
			<div class="titCriterios">Criterios de b&uacute;squeda</div>
			
			<div>
				<table>
					<colgroup><col width="725" /><col width="*" /></colgroup>
						<tbody>
							<tr>
								<td>
									<div class="formBusc" style="width:725px">
									<div class="pRel">
										<html:img styleClass="imgBuscFormTL" page="/images/QGBuscCTL.gif" />
										<html:img styleClass="imgBuscFormTR" page="/images/QGBuscCTR.gif" />
									</div>
					
									<div class="busquedaSeg">
										<table>
												<tbody>
													<tr>
														<td>
															<div class="colA" style="width:255px">
																<table>
																	<colgroup><col width="*" /><col width="*" /></colgroup>
																	<tbody>
																		<tr>
																			<label>Documento identificaci&oacute;n:</label><br/>
																			<td>
																				<select id="selTipoDoc">
																					<option value="">Todos</option>
																				</select>
																			</td>
																			<td>
																				<input type="text" class="wtc10" maxlength="50" id="txtDescripTipoDoc"/>
																			</td>
																		</tr>
																	</tbody>
																</table>			
															</div>
														</td>
														<td>
															<div class="colB" style="width:260px">
																<div class="subFormulario" style="width:250px">
																	<span class="fieldset">Segmento</span>
																	<table>
																		<tbody>
																			<tr>
																				<td>
																					<label>C&oacute;digo:</label><br />
																					<select id="selCodSeg">
																					<option value="">Todos</option>
																					</select>
																				</td>
																				<td>
																					<label>Descripci&oacute;n:</label><br />
																					<input type="text" class="wtc15 dis" maxlength="50" id="txtDescripcionSeg" readonly="readonly"/>
																				</td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</div>
														</td>
															
														<td>
															<div class="colC" style="width:160px">
																<table>
																	<tbody>
																		<tr>
																			<td>
																				<label>Oficina atenci&oacute;n:</label><br />
																				<select id="selOfAtenc" disabled="disabled">
																					<option value="">Todos</option>
																				</select>
																			</td>
																			<td>
																				<label>T&aacute;ndem:</label><br />
																				<select id="selTandem" disabled="disabled">
																					<option value="">Todos</option>
																				</select>									
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<input type="checkbox" id="chkAct" checked="checked"/>	
																				<label>Activos</label>
																			</td>
																			<td>
																				<input type="checkbox" id="chkIna" checked="checked"/>	
																				<label>Inactivos</label>
																			</td>
																		</tr>
																	</tbody>
																</table>
															</div>  
														</td>
													</tr>
												</tbody>
											</table>
									</div>
									
									<div class="pRel">
										<html:img styleClass="imgBuscFormBL" page="/images/QGBuscCBL.gif" />
										<html:img styleClass="imgBuscFormBR" page="/images/QGBuscCBR.gif" />
									</div>
								</div>									
							</td>
								<td>
									<div id="botFiltro" class="divBotones" style="position:relative; left:21px">
										<html:image styleClass="btn" styleId="btnFiltrar" page="/images/botones/QGbtFiltrar.gif" alt="Filtrar" onclick="return false;"/>
										<html:image styleClass="btn" styleId="btnVerTodos" page="/images/botones/QGbtVerTodos.gif" alt="Ver Todos" onclick="return false;" />
									</div>
									<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">	
									<div id="menuAdmin" class="subFormulario" style="width:183px; display:none; position:relative; left:21px">
										<h2>Administraci&oacute;n</h2><br/><br/>
										<div style="position:relative; left:57px">
											<table>
												<tbody>
													<tr>
														<td>
															<div>
																<label>Meses a aplicar:</label><br/>
																<select id="selMeses">
																		<option value="">Nunca</option>
																</select>
															</div>
														</td>
													</tr>
												</tbody>
											</table>
										</div><br/><br/>
										<html:image styleClass="btn btnDis" styleId="btnGuardarAd" page="/images/botones/QGbtGuardar_des.gif" onclick="return false;" />
										<html:image styleClass="btn" styleId="btnCancelarAd" page="/images/botones/QGbtCancelarNuevo.gif" onclick="return false;"/>
									</div>
									</sec:authorize>
								</td>
							</tr>
						</tbody>
				</table>
			</div>
			
			<div id="divGrid"></div>
			
			
			<div class="divBotones">
				<html:image styleClass="btn fLeft" styleId="btnHistorico" page="/images/botones/QGbtHistorico.gif" alt="Historico" onclick="return false;"/>
				<span class="edicion">
				<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">	
					<html:image styleClass="btn" styleId="btnNuevo" page="/images/botones/QGbtListadoNuevo.gif" onclick="return false;" />					
					<html:image styleClass="btn btnDis" styleId="btnModificar" disabled="true" page="/images/botones/QGbtModificar_des.gif" onclick="return false;" />
					<html:image styleClass="btn btnDis" styleId="btnEliminar" disabled="true" page="/images/botones/QGbtBaja_des.gif" onclick="return false;" />
				</sec:authorize>
				</span>
				<html:image styleClass="btn" styleId="btnExportar" page="/images/botones/QGbtnExportarExcel.gif" alt="Exportar Excel" onclick="return false;" />
				<%--<html:image styleClass="btn" styleId="btnImprimir" page="/images/botones/QGbtImprimir.gif" alt="Imprimir" onclick="return false;"/> --%>
			</div>

		</div><!-- FIN DIV divPrincipal -->
		
		<!-- HISTORICO DE PRESEGMENTACIONES -->
		<div id="divHistorico"  style="display: none;">
			<div id="divGridHistorico"></div>
			<div class="divBotones">
				<html:image styleClass="btn" styleId="btnVolver" page="/images/botones/QGbtVolver.gif" alt="Volver" onclick="return false;"/>
			</div>
		</div>
		
		<div>
			<html:img styleClass="imgInfL" page="/images/QGrecContInfIzq.gif" />
			<html:img styleClass="imgInfR" page="/images/QGrecContInfDrch.gif" />
		</div>	
		
		<iframe id="ifExportar" style="display:none"></iframe>
	</div>
</div>	

<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">	
		<!-- POPUP ANADIR SEGMENTO -->
		<div id="popUpAnadirPreSegmentacion" class="x-hidden">
		
			<div class="divFormPP">
		
				<div class="formBusc">
					<div class="pRel">
						<html:img styleClass="imgBuscFormTL" page="/images/QGBuscCTL.gif" />
						<html:img styleClass="imgBuscFormTR" page="/images/QGBuscCTR.gif" />
					</div>
					<div class="divTwoCol">
						<div class="colA" style="width:500px">
							<table>
								<tbody>
									<tr>
										<td>
											<table>
												<tbody>
													<tr>
														<td>
															<div class="subFormulario">
																<span class="fieldset">Segmento</span>
																<table>
																	<tbody>
																		<tr>
																			<td>
																				<label>C&oacute;digo:</label><br />
																				<select class="w150" id="selCodSegAS">
																					 
																				</select>
																			</td>
																			<td>
																				<label>Descripci&oacute;n:</label><br />
																				<input type="text" class="wtc25 dis" maxlength="50" id="txtDescripcionSegAS" readonly="readonly"/>
																			</td>
																		</tr>
																	</tbody>
																</table>
															</div>
														</td>						
													</tr>
													<tr>
														<td>
															<div class="subFormulario">
																<span class="fieldset">Subsegmento</span>
																<table>
																	<tbody>
																		<tr>
																			<td>
																				<label>C&oacute;digo:</label><br />
																				<select class="w150" id="selCodSubSegAS" disabled="disabled">
																					 
																				</select>
																			</td>
																			<td>
																				<label>Descripci&oacute;n:</label><br />
																				<input type="text" class="wtc25 dis" maxlength="50" id="txtDescripcionSubSegAS" readonly="readonly" disabled="disabled"/>
																			</td>
																		</tr>
																	</tbody>
																</table>
															</div>							
														</td>						
													</tr>
												</tbody>
											</table>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="colB" style="width:260px">
						<label>Documento identificaci&oacute;n:</label>
						<br/>
						<select id="selTipoDocAS">
							<option value="">Todos</option>
						</select>
						<input type="text" class="wtc10" maxlength="50" id="txtDescripTipoDocAS"/>
						<br/>
						<label>Oficina atenci&oacute;n:</label><br />
						<select id="selOfAtencAS" disabled="disabled">
							<option value="">Todos</option>
						</select>
						<br/>											
						<label>T&aacute;ndem:</label><br />
						<select id="selTandemAS" disabled="disabled">
							<option value="">Todos</option>
						</select>
					</div>
					
		
				</div>
				<div class="pRel">
					<html:img styleClass="imgBuscFormBL" page="/images/QGBuscCBL.gif" />
					<html:img styleClass="imgBuscFormBR" page="/images/QGBuscCBR.gif" />
				</div>
			</div>
			<div class="divBotones">
				<html:image styleClass="btn" styleId="btnGuardar" page="/images/botones/QGbtGuardar.gif" onclick="return false;" />
				<html:image styleClass="btn" styleId="btnCancelar" page="/images/botones/QGbtCancelarNuevo.gif" onclick="return false;"/>
			</div>
			
		</div>
		
	</div>
	<!-- FIN POPUP ANADIR SEGMENTO -->
</sec:authorize>
</sec:authorize>	