<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html:xhtml/>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
<html:form action="/POEncartes">
					<html:hidden styleId="hiddenUsuarioConectado" name="QGEncartesForm" property="usuarioLogado" />
</html:form>

<div class="recCentral">
		<div class="recHead">
			<html:img styleClass="imgSupL" page="/images/QGrecContSupIzq.gif" />
			<html:img styleClass="imgSupR" page="/images/QGrecContSupDrch.gif" />
			<h2>P&uacute;blico objetivo para encartes</h2>
		</div>
		
		<div class="recCentCont">
			
			<div id="divGrid"></div>
			
			<div class="divBotones">
				<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
				<span class="edicion">
					<html:image styleClass="btn" styleId="btnNuevo" page="/images/botones/QGbtListadoNuevo.gif" onclick="return false;" />
					<html:image styleClass="btn btnDis" styleId="btnEliminar" disabled="true" page="/images/botones/QGbtEliminar_des.gif" onclick="return false;" />
				</span>
				</sec:authorize>
				<%--<html:image styleClass="btn" styleId="btnImprimirEnc" page="/images/botones/QGbtImprimir.gif" alt="Imprimir" onclick="return false;"/> --%>
			</div>
			
			
<!-- FORMULARIO -->			
			
			<div id="idFormEncartes" class="formListDet" style="display: none;" >
				<span class="titFormListado">Encarte</span>
				<div class="formCont">
					<div class="pRel">
						<html:img styleClass="imgLTFormTL" page="/images/QGadmFormTL.gif" />
						<html:img styleClass="imgLTFormTR" page="/images/QGadmFormTR.gif" />
					</div>
					<div class="formDatAdm">
						<div class="fLeft">
							<table>
								<colgroup><col width="140"/><col width="*"/><col width="*"/></colgroup>
								<tbody>
									<tr>
										<td>
											<label>L&iacute;nea Negocio</label><br />
											<select class="w120" id="selLineaNegocio">
											<option value=""></option>
												<option value="01">LNF</option>
											<option value="02">LNM</option>
											</select>
										</td>
										<td class="vBot">
											<label>Intervalo de vigencia</label><br />
											<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
											<div class="divCalendar" id="divTxtFechaInicio">
												<input type="text" maxlength="10" class="inputCalendar" id="txtFechaInicio" />
												
												<html:image styleClass="icoCalendar" styleId="btnCalendarFechaInicio" page="/images/iconos/QGicoCalendar.gif"/>
												
											</div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="ROLE_AU">
												<input type="text" maxlength="10" class="wtc8 dis" id="txtFechaInicio" readOnly="readonly"/>
												
												
												</sec:authorize>
											
										</td>
										<td class="vBot">
											<br/>
											<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
											<div class="divCalendar" id="divTxtFechaFin">
												<input type="text" maxlength="10" class="inputCalendar" id="txtFechaFin" />												
												<html:image styleClass="icoCalendar" styleId="btnCalendarFechaFin" page="/images/iconos/QGicoCalendar.gif"/>												
											</div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="ROLE_AU">
												<input type="text" maxlength="10" class="wtc8 dis" id="txtFechaFin" readOnly="readonly"/>																								 										
											</sec:authorize>
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
											<input type="text" class="wtc8 dis" id="txtUsuarioAlta"  readonly="readonly"/>
										</td>
										<td>
											<label>Usuario de modificaci&oacute;n</label><br />
											<input type="text" class="wtc8 dis" id="txtUsuarioMod"  readonly="readonly"/>
										</td>
									</tr>
								</tbody>
							</table>				
						</div>
							
						<br class="clear"/>
						<br/>
						<div class="fLeft subFormulario">
							<span class="fieldset">Segmento</span>
							<table>
								<colgroup><col width="140"/><col width="*"/></colgroup>
								<tbody>
									<tr>
										<td>
											<label>C&oacute;digo</label><br />
											<select class="w120" id="selCodigoSeg">
												 
											</select>
										</td>
										<td>
											<label>Descripci&oacute;n:</label><br />
											<input type="text" class="wtc25 dis" maxlength="50" id="txtDescripcionSeg" readonly="readonly"/>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="fRight subFormulario">
							<span class="fieldset">Derecho</span>
							<table>
								<colgroup><col width="140"/><col width="*"/></colgroup>
								<tbody>
									<tr>
										<td>
											<label>C&oacute;digo</label><br />
											<select class="w120" id="selCodigoDer">
												 
											</select>
										</td>
										<td>
											<label>Descripci&oacute;n:</label><br />
											<input type="text" class="wtc25 dis" maxlength="50" id="txtDescripcionDer" readonly="readonly"/>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<br class="clear"/>
						<br />
					</div>
					<div class="pRel">
						<html:img styleClass="imgLTFormBL" page="/images/QGadmFormBL.gif" />
						<html:img styleClass="imgLTFormBR" page="/images/QGadmFormBR.gif" />
					</div>
				</div>
				<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
				<div class="divBotones">
					<html:image styleClass="btn btnDis" styleId="btnGuardar"  disabled="true"  page="/images/botones/QGbtGuardar_des.gif" onclick="return false;" />
					<html:image styleClass="btn" styleId="btnCancelar" page="/images/botones/QGbtCancelarNuevo.gif" onclick="return false;"/>
				</div>
				</sec:authorize>
			</div>
			
			<div>
				<html:img styleClass="imgInfL" page="/images/QGrecContInfIzq.gif" />
				<html:img styleClass="imgInfR" page="/images/QGrecContInfDrch.gif" />
			</div>				
			
		</div>
		<iframe id="ifExportar" style="display:none"></iframe>
</div>

<!-- POPUP HISTORICO -->
<div id="popUpHistorico" class="x-hidden">
	<div class="divFormPP">
		<table>
			<colgroup><col width="90" /><col width="*" /><col width="290" /><col width="*" /><col width="*" /></colgroup>
			<tbody>
				<tr>
					<td>
						<label>L&iacute;nea negocio</label>
					</td>
					<td colspan="2">
						<label>Segmento</label>
					</td>
					<td colspan="2">
						<label>Derecho/Consentimiento</label>
					</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="txtNegocio" class="wtc2 dis" readonly="readonly"/>										
					</td>
					<td>
						<input type="text" id="txtCodSegmento" class="wtc2 dis"  readonly="readonly"/>
					</td>
					<td>
						<input type="text" id="txtDesSegmento" class="wtc25 dis" readonly="readonly"/>
					</td>
					<td>
						<input type="text" id="txtCodConsent" class="wtn3 dis" readonly="readonly"/>
					</td>
					<td>
						<input type="text" id="txtDesConsent" class="wtc25 dis" readonly="readonly"/>
					</td>
				</tr>
			</tbody>
		</table>
		
		<br/>
		<span class="titFormulario">Historia</span>
		<div id="divGridHistoricoPO"></div>
		
	</div>
	<div class="divBotones">
		<html:image styleClass="btn" styleId="btnVolver" page="/images/botones/QGbtVolver.gif" onclick="return false;" />
	</div>
</div><!-- FIN POPUP HISTORICO -->
</sec:authorize>