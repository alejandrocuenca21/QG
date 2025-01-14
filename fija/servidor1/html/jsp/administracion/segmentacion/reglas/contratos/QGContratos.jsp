<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html:xhtml/>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
<html:form action="/Contratos">
	<html:hidden styleId="hiddenUsuarioConectado" name="QGSegmenRegContratosForm" property="usuarioLogado" />
</html:form>


<html:form action="/Contratos" method="POST">
	<html:hidden styleId="hiddenOrigen" property="origen" name="QGSegmenRegContratosForm"/>
	<html:hidden styleId="hiddenEntrada" property="segmenRegContratosJSON" name="QGSegmenRegContratosForm"/>
</html:form>	

<div class="recCentral">
		<div class="recHead">
			<html:img styleClass="imgSupL" page="/images/QGrecContSupIzq.gif" />
			<html:img styleClass="imgSupR" page="/images/QGrecContSupDrch.gif" />
			<h2>Contratos de negocio para la l&iacute;nea m&oacute;vil</h2>
		</div>
		
		<div class="recCentCont">
			<html:form action="/Reglas" method="POST">
    			<input id="idMethod" type="hidden" name="method" />
    			<html:form action="/Reglas" method="POST">
					<input id="hiddenOrigenV" type="hidden" name="origen"/>
					<input id="hiddenEntradaV" type="hidden" name="segmentacionReglasJSON"/>
				</html:form>
				

			<div id="divGrid"></div>
			
			<div class="divBotones">
				<div id="divVolver">
					<html:image styleClass="btn fLeft" styleId="btnVolver" page="/images/botones/QGbtVolver.gif" alt="Volver" onclick="return false;"/>
				</div>
				<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
				<span class="edicion">
					<html:image styleClass="btn" styleId="btnNuevo" page="/images/botones/QGbtListadoNuevo.gif" onclick="return false;" />
				</span>
				</sec:authorize>
				<%--<html:image styleClass="btn" styleId="btnImprimir" page="/images/botones/QGbtImprimir.gif" alt="Imprimir" onclick="return false;"/> --%>
			</div>
			</html:form>
			
<!-- FORMULARIO -->			
			<div id="idFormContratos" class="formListDet"  style="display: none;">
				<span class="titFormListado">Contrato</span>
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
											<label>C&oacute;digo</label><br />
											<input type="text" class="wtc8" id="txtCodigo"/>
										</td>
										<td class="vBot">
											<label>Intervalo de vigencia</label><br />
											<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
											<div class="divCalendar" id="divTxtFechaInicio">
												<input type="text" maxlength="10" class="inputCalendar" id="txtFechaInicio" />
												
												<html:image styleClass="icoCalendar" styleId="btnCalendarFechaInicio" page="/images/iconos/QGicoCalendar.gif"/>
												
											</div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="ROLE_AU,ROLE_AB,ROLE_AC,ROLE_AD,ROLE_CO,ROLE_AS,ROLE_AM">
												<input type="text" maxlength="10" class="wtc8 dis" id="txtFechaInicio" readOnly="readonly"/>
											</sec:authorize>
											
										</td>
										<td class="vBot">
											<br/>
											<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
											<div class="divCalendar" id="divTxtFechaFin">
												<input type="text" maxlength="10" class="inputCalendar" id="txtFechaFin" />												
												<html:image styleClass="icoCalendar" styleId="btnCalendarFechaFin" page="/images/iconos/QGicoCalendar.gif"/>												
											</div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="ROLE_AU,ROLE_AB,ROLE_AC,ROLE_AD,ROLE_CO,ROLE_AS,ROLE_AM">
												<input type="text" maxlength="10" class="wtc8 dis" id="txtFechaFin" readOnly="readonly"/>																								 										
											</sec:authorize>
										</td>
									</tr>
									<tr>
										<td>
											<label>Descripci&oacute;n:</label><br />
											<input type="text" class="wtc20" id="txtDescripcion"/>
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

					</div>
					<div class="pRel">
						<html:img styleClass="imgLTFormBL" page="/images/QGadmFormBL.gif" />
						<html:img styleClass="imgLTFormBR" page="/images/QGadmFormBR.gif" />
					</div>
				</div>
				<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AP">
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
</sec:authorize>