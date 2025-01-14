<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html:xhtml/>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
<div class="recCentral">
	<div class="recHead" id="divTituloPrincipal">
		<html:img styleClass="imgSupL" page="/images/QGrecContSupIzq.gif" />
		<html:img styleClass="imgSupR" page="/images/QGrecContSupDrch.gif" />
		<h2>Traducci&oacute;n Fijo-M&oacute;vil</h2>
	</div>
	
	<div class="recHead" id="divTituloHistorico" style="display: none;">
		<html:img styleClass="imgSupL" page="/images/QGrecContSupIzq.gif" />
		<html:img styleClass="imgSupR" page="/images/QGrecContSupDrch.gif" />
		<h2>Hist&oacute;rico Traducci&oacute;n</h2>
	</div>
	
	<div class="recCentCont">
	
	<input type="hidden" id="hiddenUsuarioConectado"/>
	
		<div id="divPrincipal" class="divPPalTrad">
			<div class="titCriterios">Criterios de b&uacute;squeda</div>
			
			<div class="formBusc colA">
				<div class="pRel">
					<html:img styleClass="imgBuscFormTL" page="/images/QGBuscCTL.gif" />
					<html:img styleClass="imgBuscFormTR" page="/images/QGBuscCTR.gif" />
				</div>
				<div class="busquedaTrad">
					
					<div class="titFormulario">Fijo</div>
					<div class="subFormulario mTop10">
							
						<span class="fieldset">Segmento</span>
						<table>
							<tbody>
								<tr>
									<td>
										<select id="selCodSegFijo">
										<option value="">Todos</option>
										</select>
									</td>
									<td>
										<input type="text" class="wtc30 dis" maxlength="50" id="txtDescripcionSegFijo" readonly="readonly"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					
					<div class="subFormulario mTop10">
						<span class="fieldset">Subsegmento</span>
						<table>
							<tbody>
								<tr>
									<td>
										<select id="selCodSubSegFijo">
											<option value="">Todos</option>
										</select>
									</td>
									<td>
										<input type="text" class="wtc30 dis" maxlength="50" id="txtDescripcionSubSegFijo" readonly="readonly"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
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
				<div class="busquedaTrad">
					
					<div class="titFormulario">M&oacute;vil</div>
					<div class="subFormulario mTop10">
						<span class="fieldset">Segmento</span>
						<table>
							<tbody>
								<tr>
									<td>
										<select id="selCodSegMov">
										<option value="">Todos</option>
										</select>
									</td>
									<td>
										<input type="text" class="wtc30 dis" maxlength="50" id="txtDescripcionSegMov" readonly="readonly"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					
					<div class="subFormulario mTop10">
						<span class="fieldset">Subsegmento</span>
						<table>
							<tbody>
								<tr>
									<td>
										<select id="selCodSubSegMov">
											<option value="">Todos</option>
										</select>
									</td>
									<td>
										<input type="text" class="wtc30 dis" maxlength="50" id="txtDescripcionSubSegMov" readonly="readonly"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
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
				<html:image styleClass="btn fLeft" styleId="btnHistorico" page="/images/botones/QGbtHistorico.gif" alt="Historico" onclick="return false;"/>
				<span class="edicion">
				<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AS">	
					<html:image styleClass="btn" styleId="btnNuevo" page="/images/botones/QGbtListadoNuevo.gif" onclick="return false;" />
					<html:image styleClass="btn btnDis" styleId="btnEliminar" disabled="true" page="/images/botones/QGbtBaja_des.gif" onclick="return false;" />
				</sec:authorize>
				</span>
				<%--<html:image styleClass="btn" styleId="btnImprimir" page="/images/botones/QGbtImprimir.gif" alt="Imprimir" onclick="return false;"/> --%>
			</div>

		</div><!-- FIN DIV divPrincipal -->
		
		<!-- HISTORICO DE TRADUCCION -->
		<div id="divHistorico">
			<div id="divGridHistorico"></div>
			<div id="divBotonesHistorico" class="divBotones" style="display:none;">
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
<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AS">	
<!-- POPUP ANADIR TRADUCCION -->
<div id="popUpAnadirTraduccion" class="x-hidden">

	<div class="divFormPP">
		<table>
			<colgroup><col width="115" /><col width="110" /><col width="*" /></colgroup>
			<tbody>
				<tr>
					<td>
						<label>Plan de Cuentas:</label><br />
						<input type="text" class="wtc8 dis" id="txtPlanCuentas" readonly="readonly"/>
					</td>
					<td>
						<label>Intervalo de vigencia:</label><br />
						<div id="divTxtIntervaloVigIni" class="divCalendar">
							<input type="text" id="txtIntervaloVigIni" class="inputCalendar" maxlength="10">
							<input type="image" class="icoCalendar" id="btnCalendarIntervaloVigIni" src="./images/iconos/QGicoCalendar.gif" name="">
						</div>
					</td>
					<td class="vBot">
						<div id="divTxtIntervaloVigFin" class="divCalendar" >
							<input type="text" id="txtIntervaloVigFin" class="inputCalendar" maxlength="10">
							<input type="image" class="icoCalendar" id="btnCalendarIntervaloVigFin" src="./images/iconos/QGicoCalendar.gif" name="">
						</div>
						<div id="divTxtIntervaloVigFinBaja" class="divCalendar" >
							<input type="text" id="txtIntervaloVigFinBaja" class="inputCalendar" maxlength="10">
							<input type="image" class="icoCalendar" id="btnCalendarIntervaloVigFinBaja" src="./images/iconos/QGicoCalendar.gif" name="">
						</div>
					</td>
					
				</tr>
			</tbody>
		</table>
		
		<br/>
		<span class="titFormulario">Fijo</span>
		<div class="formBusc">
			<div class="pRel">
				<html:img styleClass="imgBuscFormTL" page="/images/QGBuscCTL.gif" />
				<html:img styleClass="imgBuscFormTR" page="/images/QGBuscCTR.gif" />
			</div>
			<div class="divTwoCol h65ie6">
				<div class="subFormulario colA h40">
					<span class="fieldset">Segmento</span>
					<table>
						<tbody>
							<tr>
								<td>
									<label>C&oacute;digo:</label><br />
									<select class="w150" id="selCodSegFijoPop">
										 
									</select>
								</td>
								<td>
									<label>Descripci&oacute;n:</label><br />
									<input type="text" class="wtc25 dis" maxlength="50" id="txtDescSegFijoPop" readonly="readonly"/>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<div class="subFormulario colB h40">
					<span class="fieldset">Subsegmento</span>
					<table>
						<tbody>
							<tr>
								<td>
									<label>C&oacute;digo:</label><br />
									<select class="w150" id="selCodSubSegFijoPop">
										 
									</select>
								</td>
								<td>
									<label>Descripci&oacute;n:</label><br />
									<input type="text" class="wtc25 dis" maxlength="50" id="txtDescSubSegFijoPop" readonly="readonly"/>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="pRel">
				<html:img styleClass="imgBuscFormBL" page="/images/QGBuscCBL.gif" />
				<html:img styleClass="imgBuscFormBR" page="/images/QGBuscCBR.gif" />
			</div>
		</div>
		
		<br/>
		<span class="titFormulario">M&oacute;vil</span>
		<div class="formBusc">
			<div class="pRel">
				<html:img styleClass="imgBuscFormTL" page="/images/QGBuscCTL.gif" />
				<html:img styleClass="imgBuscFormTR" page="/images/QGBuscCTR.gif" />
			</div>
			<div class="divTwoCol h65ie6">
				<div class="subFormulario colA h40">
					<span class="fieldset">Segmento</span>
					<table>
						<tbody>
							<tr>
								<td>
									<label>C&oacute;digo:</label><br />
									<select class="w150" id="selCodSegMovilPop">
										<option value=""></option>
									</select>
								</td>
								<td>
									<label>Descripci&oacute;n:</label><br />
									<input type="text" class="wtc25 dis" maxlength="50" id="txtDescSegMovilPop" readonly="readonly" />
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<div class="subFormulario colB h40">
					<span class="fieldset">Subsegmento</span>
					<table>
						<tbody>
							<tr>
								<td>
									<label>C&oacute;digo:</label><br />
									<select class="w150" id="selCodSubSegMovilPop">
										<option value=""></option>
									</select>
								</td>
								<td>
									<label>Descripci&oacute;n:</label><br />
									<input type="text" class="wtc25 dis" maxlength="50" id="txtDescSubSegMovilPop" readonly="readonly" />
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="pRel">
				<html:img styleClass="imgBuscFormBL" page="/images/QGBuscCBL.gif" />
				<html:img styleClass="imgBuscFormBR" page="/images/QGBuscCBR.gif" />
			</div>
		</div>
		
	</div>
	<div class="divBotones">
		<html:image styleClass="btn" styleId="btnGuardar" page="/images/botones/QGbtGuardar.gif" onclick="return false;" />
		<html:image styleClass="btn" styleId="btnCancelar" page="/images/botones/QGbtCancelarNuevo.gif" onclick="return false;"/>
	</div>
</div>
</sec:authorize><!-- FIN POPUP ANADIR SEGMENTO -->
</sec:authorize>