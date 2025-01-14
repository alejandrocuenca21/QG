<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html:xhtml/>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
<div class="recCentral">
		<div class="recHead">
			<html:img styleClass="imgSupL" page="/images/QGrecContSupIzq.gif" />
			<html:img styleClass="imgSupR" page="/images/QGrecContSupDrch.gif" />
			<h2>Medios de comunicaci&oacute;n</h2>
		</div>
		
		<div class="recCentCont">
			
			<div id="divGrid"></div>
		
			<div class="divBotones">
				<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
				<!-- <span class="edicion"> -->
					<html:image styleClass="btn" styleId="btnNuevo" page="/images/botones/QGbtListadoNuevo.gif" onclick="return false;" />
					<html:image styleClass="btn btnDis" styleId="btnEliminar" disabled="true" page="/images/botones/QGbtEliminar_des.gif" onclick="return false;" />
				<!-- </span> -->
				</sec:authorize> 
				<%--<html:image styleClass="btn" styleId="btnImprimir" page="/images/botones/QGbtImprimir.gif" alt="Imprimir" onclick="return false;"/> --%>
			</div>
			
			<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
			<div id="idFormMediosComunicacion" class="formListDet" style="display:none;">
			<html:form action="/MediosComunicacion">
				<html:hidden styleId="hiddenUsuarioAlta" name="QGMediosComunicacionForm" property="usuarioLogado" />
			</html:form>
				<span class="titFormListado">Medio de comunicaci&oacute;n</span>
				<div class="formCont">
					<div class="pRel">
						<html:img styleClass="imgLTFormTL" page="/images/QGadmFormTL.gif" />
						<html:img styleClass="imgLTFormTR" page="/images/QGadmFormTR.gif" />
					</div>
					<div class="formDatAdm">
						<div class="fLeft">
							<table>
								<colgroup><col width="60"/><col width="*"/><col width="*"/></colgroup>
								<tbody>
									<tr>
										<td>
											<label>C&oacute;digo</label><br />
											<input type="text" maxlength="3" class="wtc3" id="txtCodigo" />
										</td>
										<td class="vBot">
											<label>Intervalo de vigencia</label><br />
											<div class="divCalendar" id="divTxtFechaInicio">
												<input type="text" maxlength="10" class="inputCalendar" id="txtFechaInicio" />
												<html:image styleClass="icoCalendar" styleId="btnCalendarFechaInicio" page="/images/iconos/QGicoCalendar.gif"/>
											
											</div>
										</td>
										<td class="vBot">
											<br/>
											<div class="divCalendar" id="divTxtFechaFin">
												<input type="text" maxlength="10" class="inputCalendar" id="txtFechaFin" />
												<html:image styleClass="icoCalendar" styleId="btnCalendarFechaFin" page="/images/iconos/QGicoCalendar.gif"/>
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
							<label>Descripci&oacute;n</label><br />
							<input type="text" maxlength="60" class="wtc60" id="txtDescripcion" />
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