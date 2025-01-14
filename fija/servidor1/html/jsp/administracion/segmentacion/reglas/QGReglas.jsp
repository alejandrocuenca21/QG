<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html:xhtml/>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
<html:form action="/Reglas" method="POST">
	<html:hidden styleId="hiddenOrigenVuelta" property="origen" name="QGSegmentacionesReglasForm"/>
	<html:hidden styleId="hiddenEntradaVuelta" property="segmentacionReglasJSON" name="QGSegmentacionesReglasForm"/>
</html:form>	

<div class="recCentral">
	<div class="recHead" id="divTituloPrincipal">
		<html:img styleClass="imgSupL" page="/images/QGrecContSupIzq.gif" />
		<html:img styleClass="imgSupR" page="/images/QGrecContSupDrch.gif" />
		<h2>Reglas Segmentaci&oacute;n</h2>
	</div>
	
	<div class="recHead" id="divTituloHistorico" style="display: none;">
		<html:img styleClass="imgSupL" page="/images/QGrecContSupIzq.gif" />
		<html:img styleClass="imgSupR" page="/images/QGrecContSupDrch.gif" />
		<h2>Hist&oacute;rico Reglas Segmentaci&oacute;n</h2>
	</div>
	
	<div class="recCentCont">
		
	<html:form action="/Contratos" method="POST">
    	<input id="idMethod" type="hidden" name="method" />

		<input type="hidden" id="hiddenUsuarioConectado"/>
		
			<html:form action="/Contratos" method="POST">
				<input id="hiddenOrigen" type="hidden" name="origen"/>
				<input id="hiddenEntrada" type="hidden" name="segmenRegContratosJSON"/>
			</html:form>	
		
		
		<div id="divPrincipal" class="divPPalTrad">
			<div class="titCriterios">Criterios de b&uacute;squeda</div>
			
			<div class="formBusc colA">
				<div class="pRel">
					<html:img styleClass="imgBuscFormTL" page="/images/QGBuscCTL.gif" />
					<html:img styleClass="imgBuscFormTR" page="/images/QGBuscCTR.gif" />
				</div>
				<div class="busquedaTrad" style="padding: 16px;">
					
					<div class="grupoRad">
						<input id="rbReg" type="radio" value="reglas" name="rbReg">
						<label>Reglas</label>
					</div>
					<div class="subFormulario mTop10">
							
						<span class="fieldset">Regla</span>
						<table>
							<tbody>
								<tr>
									<td>
										<select id="selCodRegla">
										<option value="">Todas</option>
										</select>
									</td>
									<td>
										<input type="text" class="wtc30 dis" maxlength="50" id="txtDescripcionRegla" readonly="readonly"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- Añadidos los 4 combos -->
					<table>
						<colgroup><col width="115" /><col width="115" /><col width="115" /><col width="*" /></colgroup>
						<tbody>
							<tr>
								<td>
									<label>Nº L&iacute;neas Fijas:</label><br>
									<input type="text" maxlength="3" class="wtc3" id="txtLinFix" />
									<!--  
									<select id="selLinFix">
										<option value="">Todas</option>
									</select>
									-->
								</td>
								<td>
									<label>Nº L&iacute;neas M&oacute;vil:</label><br>
									<input type="text" maxlength="3" class="wtc3" id="txtLinMov" />
									<!--  
									<select id="selLinMov">
										<option value="">Todas</option>
									</select>
									-->
								</td>
								<td>
									<label>Nº Total de L&iacute;neas:</label><br>
									<input type="text" maxlength="3" class="wtc3" id="txtLinTot" />
									<!--  
									<select id="selLinTot">
										<option value="">Todas</option>
									</select>
									-->
								</td>
								<td>
									<label>Nº de D&iacute;as:</label><br>
									<select id="selDias">
										<option value="">Todos</option>
									</select>
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
			
			<div class="formBusc colB">
				<div class="pRel">
					<html:img styleClass="imgBuscFormTL" page="/images/QGBuscCTL.gif" />
					<html:img styleClass="imgBuscFormTR" page="/images/QGBuscCTR.gif" />
				</div>
				<div class="busquedaTrad" style="padding: 16px;">
					
					<div class="grupoRad">
						<input id="rbSeg" type="radio" value="segmentacion" name="rbSeg">
						<label>Segmentaci&oacute;n</label>
					</div>
					<table>
						<colgroup><col width="345" /><col width="*" /></colgroup>
						<tbody>
							<tr>
								<td>
									<div class="subFormulario  mTop10">
										<span class="fieldset">Segmento</span>
										<table>
											<tbody>
												<tr>
													<td>
														<select id="selCodSeg">
														<option value="">Todos</option>
														</select>
													</td>
													<td>
														<input type="text" class="wtc20 dis" maxlength="50" id="txtDescripcionSeg" readonly="readonly"/>
													</td>
												</tr>
											</tbody>
										</table>
									</div>								
								</td>
								<td>
									<label>Oficina atenci&oacute;n:</label><br />
									<select id="selOfAtenc" disabled="disabled">
										<option value="">Todos</option>
									</select>
								</td>
							</tr>
							<tr>
								<td>						
									<div class="subFormulario mTop10">
										<span class="fieldset">Subsegmento</span>
										<table>
											<tbody>
												<tr>
													<td>
														<select id="selCodSubSeg" disabled="disabled">
															<option value="">Todos</option>
														</select>
													</td>
													<td>
														<input type="text" class="wtc20 dis" maxlength="50" id="txtDescripcionSubSeg" readonly="readonly"/>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</td>
								<td>
									<label>T&aacute;ndem:</label><br />
									<select id="selTandem" disabled="disabled">
										<option value="">Todos</option>
									</select>
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
			
			<div class="divBotones">
				<html:image styleClass="btn" styleId="btnFiltrar" page="/images/botones/QGbtFiltrar.gif" alt="Filtrar" onclick="return false;"/>
				<html:image styleClass="btn" styleId="btnVerTodos" page="/images/botones/QGbtVerTodos.gif" alt="Ver Todos" onclick="return false;" />
			</div>
			
			<div id="divGrid"></div>
			
			<div class="divBotones">
				<!--  
				<html:image styleClass="btn fLeft" styleId="btnHistorico" page="/images/botones/QGbtHistorico.gif" alt="Historico" onclick="return false;"/>
				-->	
				<!--
				<html:image styleClass="btn fLeft" styleId="btnContratos" page="/images/botones/QGbtContratos.gif" alt="Contratos" onclick="return false;"/> 
				-->
				<span class="edicion">
				<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AP">	
					<html:image styleClass="btn btnDis" styleId="btnModificar" disabled="true" page="/images/botones/QGbtModificar_des.gif" onclick="return false;" />
				</sec:authorize>
				</span>
				<%--<html:image styleClass="btn" styleId="btnImprimir" page="/images/botones/QGbtImprimir.gif" alt="Imprimir" onclick="return false;"/> --%>
			</div>

		</div><!-- FIN DIV divPrincipal -->
		
		<!-- HISTORICO DE REGLAS -->
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
		</html:form>
	</div>
	
</div>	

<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AP">	
<!-- POPUP ANADIR REGLAS -->
<div id="popUpAnadirRegla" class="x-hidden">
	<div class="divFormPP">

	<table>
		<colgroup><col width="345" /><col width="345" /><col width="*" /></colgroup>
		<tbody>
			<tr>
				<td>	
					<div class="subFormulario mTop10">							
						<span class="fieldset">Regla</span>
						<table>
							<tbody>
								<tr>
									<td>
										<select id="selCodReglaAS" disabled>
										<option value=""></option>
										</select>
									</td>
									<td>
										<input type="text" class="wtc30 dis" maxlength="50" id="txtDescripcionReglaAS" readonly="readonly"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</td>
				<td>
					<div class="subFormulario  mTop10">
						<span class="fieldset">Segmento</span>
						<table>
							<tbody>
								<tr>
									<td>
										<select id="selCodSegAS">
										<option value=""></option>
										</select>
									</td>
									<td>
										<input type="text" class="wtc20 dis" maxlength="50" id="txtDescripcionSegAS" readonly="readonly"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>								
				</td>
				<td>
					<label>Oficina atenci&oacute;n:</label><br />
					<select id="selOfAtencAS" disabled="disabled">
						<option value="" selected></option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<colgroup><col width="90" /><col width="90" /><col width="100" /><col width="*" /></colgroup>
						<tbody>
							<tr>
								<td>
									<label>Nº L&iacute;neas Fijas:</label><br>
									<input type="text" maxlength="3" class="wtc3" id="txtLinFixAS" />
									<!--  
									<select id="selLinFixAS">
										<option value=""></option>
									</select>
									-->
								</td>
								<td>
									<label>Nº L&iacute;neas M&oacute;vil:</label><br>
									<input type="text" maxlength="3" class="wtc3" id="txtLinMovAS" />
									<!--  
									<select id="selLinMovAS">
										<option value=""></option>
									</select>
									-->
								</td>
								<td>
									<label>Nº Total de L&iacute;neas:</label><br>
									<input type="text" maxlength="3" class="wtc3" id="txtLinTotAS" />
									<!--  
									<select id="selLinTotAS" disabled="disabled">
										<option value=""></option>
									</select>
									-->
								</td>
								<td>
									<label>Nº de D&iacute;as:</label><br>
									<select id="selDiasAS">
										<option value=""></option>
									</select>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
				<td>						
					<div class="subFormulario mTop10">
						<span class="fieldset">Subsegmento</span>
						<table>
							<tbody>
								<tr>
									<td>
										<select id="selCodSubSegAS" disabled="disabled">
											<option value=""></option>
										</select>
									</td>
									<td>
										<input type="text" class="wtc20 dis" maxlength="50" id="txtDescripcionSubSegAS" readonly="readonly"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</td>
				<td>
					<label>T&aacute;ndem:</label><br />
					<select id="selTandemAS" disabled="disabled">
						<option value="" selected></option>
					</select>
				</td>
			</tr>
			<tr>				
				<td>
					<table>
						<colgroup><col width="130" /><col width="130" /><col width="*" /></colgroup>
						<tbody>
							<tr>
								<td>				
									<label>Intervalo de vigencia:</label><br />
									<div id="divTxtIntervaloVigASIni" class="divCalendar">
										<input type="text" id="txtIntervaloVigASIni" class="inputCalendar" maxlength="10">
										<input type="image" class="icoCalendar" id="btnCalendarIntervaloVigASIni" src="./images/iconos/QGicoCalendar.gif" name="">										
									</div>
								</td>
								<td class="vBot">
									<div id="divTxtIntervaloVigASFin" class="divCalendar">
										<input type="text" id="txtIntervaloVigASFin" class="inputCalendar" maxlength="10">
										<input type="image" class="icoCalendar" id="btnCalendarIntervaloVigASFin" src="./images/iconos/QGicoCalendar.gif" name="">
									</div>									
								</td>
								<td>&nbsp;</td>								
							</tr>
						</tbody>
					</table>								
				<td colspan="2">&nbsp;</td>
			</tr>		
		</tbody>
	</table>

	<div class="divBotones">	
		<html:image styleClass="btn" styleId="btnGuardar" disabled="true" page="/images/botones/QGbtGuardar.gif" onclick="return false;"/>
		<html:image styleClass="btn" styleId="btnCancelar" page="/images/botones/QGbtCancelarNuevo.gif" onclick="return false;"/>
	</div>	
</div>
</div>

</sec:authorize><!-- FIN POPUP ANADIR REGLAS -->
</sec:authorize>