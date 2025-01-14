<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html:xhtml/>
<sec:authorize ifAnyGranted="ROLE_SA">

<html:form action="/MensError">
	<html:hidden styleId="hiddenUsuarioConectado" name="QGMensajeErrorForm" property="usuarioLogado" />
</html:form>

<div class="recCentral">
		<div class="recHead">
			<html:img styleClass="imgSupL" page="/images/QGrecContSupIzq.gif" />
			<html:img styleClass="imgSupR" page="/images/QGrecContSupDrch.gif" />
			<h2>Mensajes de error</h2>
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
				<%--<html:image styleClass="btn" styleId="btnImprimir" page="/images/botones/QGbtImprimir.gif" alt="Imprimir" onclick="return false;"/> --%>
			</div>
			
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
			<div id="idFormMensajeError" class="formListDet" style="display:none;">
			<html:form action="/MensError">
				<html:hidden styleId="hiddenUsuarioAlta" name="QGMensajeErrorForm" property="usuarioLogado" />
			</html:form>
				<span class="titFormListado">Mensajes de error</span>
				<div class="formCont">
					<div class="pRel">
						<html:img styleClass="imgLTFormTL" page="/images/QGadmFormTL.gif" />
						<html:img styleClass="imgLTFormTR" page="/images/QGadmFormTR.gif" />
					</div>
					<div class="formDatAdm">
						<div class="fLeft">
							<table>
								<tbody>
									<tr>
										<td>
											<table>
												<colgroup><col width="120"/><col width="107"/><col width="139"/></colgroup>
												<tbody>
													<tr>
														<td>
															<label>C&oacute;digo</label><br />
															<input type="text" class="wtc8" id="txtCodigo" />
														</td>
														<td class="vBot">
															<label>Intervalo de vigencia</label><br />
															<div class="divCalendar" id="divTxtFechaInicio">
																<input type="text" maxlength="10" class="inputCalendar" id="txtFechaInicio" />
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
										</td>
										<td>
											<div class="subFormulario mLeft10">
												<span class="fieldset">Grupo Responsable</span>
												<table>
													<colgroup><col width="92"/><col width="*"/></colgroup>
													<tbody>
														<tr>
															<td>
																<label>C&oacute;digo</label><br />
																<select class="wtc8" id="selGrupoRes">
																	<option value=""></option>
																	<option value="Calidad Dato">C</option>
																	<option value="Sistema QG">S</option>
																	<option value="Mixto">X</option>
																	<option value="No procede análisis">N</option>
																</select>
															</td>
															<td>
																<label>Descripci&oacute;n</label><br />
																<input type="text" class="wtc10" id="txtDescripcionGr" />
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
						<div class="fRight">
							<table>
								<colgroup><col width="110"/><col width="*"/></colgroup>
								<tbody>
									<tr>
										<td>
											<label>Usuario de alta</label><br />
											<input type="text" class="wtc8" id="txtUsuarioAlta" />
										</td>
										<td>
											<label>Usuario de modificaci&oacute;n</label><br />
											<input type="text" class="wtc8" id="txtUsuarioMod" />
										</td>
									</tr>
								</tbody>
							</table>				
						</div>
						
						<br class="clear"/>
						
						<div>						
							<table>
								<tbody>
									<tr>
										<td>
											<table>
												<colgroup><col width="100"/></colgroup>
												<tbody>
													<tr>
														<td>
															<label>Descripci&oacute;n</label><br />
															<input type="text" maxlength="60" class="wtc60" id="txtDescripcion"/>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					
					<div class="pRel">
						<html:img styleClass="imgLTFormBL" page="/images/QGadmFormBL.gif" />
						<html:img styleClass="imgLTFormBR" page="/images/QGadmFormBR.gif" />
					</div>		
						
				</div>
				<div class="divBotones">
					<html:image styleClass="btn" styleId="btnGuardar" page="/images/botones/QGbtGuardar.gif" onclick="return false;" />
					<html:image styleClass="btn" styleId="btnCancelar" page="/images/botones/QGbtCancelarNuevo.gif" onclick="return false;"/>
				</div>
			</div>
			</sec:authorize>
			<div>
				<html:img styleClass="imgInfL" page="/images/QGrecContInfIzq.gif" />
				<html:img styleClass="imgInfR" page="/images/QGrecContInfDrch.gif" />
			</div>				
			
		</div>
		<iframe id="ifExportar" style="display:none"></iframe>
</div>
</sec:authorize>