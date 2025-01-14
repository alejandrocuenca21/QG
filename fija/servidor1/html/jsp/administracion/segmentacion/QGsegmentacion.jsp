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
		<h2>Evoluci&oacute;n Segmentaci&oacute;n</h2>
	</div>
	
	<div class="recHead" id="divTituloHistorico" style="display: none;">
		<html:img styleClass="imgSupL" page="/images/QGrecContSupIzq.gif" />
		<html:img styleClass="imgSupR" page="/images/QGrecContSupDrch.gif" />
		<h2>Hist&oacute;rico Segmentaci&oacute;n</h2>
	</div>
	
	<div class="recCentCont">
		<div id="divPrincipal">
			<div class="titCriterios">Criterios de b&uacute;squeda</div>
			<div class="formBusc">
				<div class="pRel">
					<html:img styleClass="imgBuscFormTL" page="/images/QGBuscCTL.gif" />
					<html:img styleClass="imgBuscFormTR" page="/images/QGBuscCTR.gif" />
				</div>
				<div class="busquedaSeg">
					
					<div class="colA">
						<table>
							<colgroup><col width="80" /><col width="*" /></colgroup>
							<tbody>
								<tr>
									<td>
										<label>Unidad:</label><br />
										<select id="selUnidad">
											<option value="">Todos</option>
											<option value="01">LNF</option>
											<option value="02">LNM</option>
										</select>
									</td>
									<td>
										<label>Operaci&oacute;n:</label><br />
										<select id="selOperacion">
											<option value="">Todos</option>
											<option value="A">Alta</option>
											<option value="B">Baja</option>
										</select>
									</td>
								</tr>
							</tbody>
						</table>
						
					</div>
					<div class="colB">
						<div class="subFormulario">
							<span class="fieldset">Segmento Origen</span>
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
											<input type="text" class="wtc25 dis" maxlength="50" id="txtDescripcionSeg" readonly="readonly"/>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="colC">
						<div class="subFormulario">
							<span class="fieldset">Subsegmento Origen</span>
							<table>
								<tbody>
									<tr>
										<td>
											<label>C&oacute;digo:</label><br />
											<select id="selCodSubSeg">
												<option value="">Todos</option>
											</select>
										</td>
										<td>
											<label>Descripci&oacute;n:</label><br />
											<input type="text" class="wtc25 dis" maxlength="50" id="txtDescripcionSubSeg" readonly="readonly"/>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
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
				<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AS">	
					<html:image styleClass="btn" styleId="btnNuevo" page="/images/botones/QGbtListadoNuevo.gif" onclick="return false;" />
					<html:image styleClass="btn btnDis" styleId="btnEliminar" disabled="true" page="/images/botones/QGbtBaja_des.gif" onclick="return false;" />
				</sec:authorize>
				</span>
				<%--<html:image styleClass="btn" styleId="btnImprimir" page="/images/botones/QGbtImprimir.gif" alt="Imprimir" onclick="return false;"/> --%>
			</div>

		</div><!-- FIN DIV divPrincipal -->
		
		<!-- HISTORICO DE SEGMENTACIONES -->
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
	</div>
	
	<iframe id="ifExportar" style="display:none"></iframe>
</div>
<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AS">	
<!-- POPUP ANADIR SEGMENTO -->
<div id="popUpAnadirSegmentacion" class="x-hidden">

	<div class="divFormPP">
		<div class="titCriterios">Segmentaci&oacute;n</div>
		<table>
			<colgroup><col width="230" /><col width="230" /><col width="110" /><col width="125" /><col width="*" /></colgroup>
			<tbody>
				<tr>
					<td>
						<label>Unidad:</label><br />
						<select class="w210" id="selUnidadAS">
							<option value=""></option>
							<option value="01">LNF</option>
							<option value="02">LNM</option>
						</select>
					</td>
					<td>
						<label>Operaci&oacute;n:</label><br />
						<select class="w210" id="selOperacionAS">
							<option value=""></option>
							<option value="A">Alta</option>
							<option value="B">Baja</option>
						</select>
					</td>
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
					<td>
						<label>Usuario de alta:</label><br />
						<input type="text" class="wtc8 dis" maxlength="8" id="txtUsuarioAltaAS" readonly="readonly"/>
					</td>
				</tr>
			</tbody>
		</table>
		
		<br/>
		<span class="titFormulario">Origen</span>
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
				
				<div class="subFormulario colB h40">
					<span class="fieldset">Subsegmento</span>
					<table>
						<tbody>
							<tr>
								<td>
									<label>C&oacute;digo:</label><br />
									<select class="w150" id="selCodSubSegAS">
										 
									</select>
								</td>
								<td>
									<label>Descripci&oacute;n:</label><br />
									<input type="text" class="wtc25 dis" maxlength="50" id="txtDescripcionSubSegAS" readonly="readonly"/>
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
		<span class="titFormulario">Destino</span>
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
									<select class="w150" id="selCodDestSegAS" disabled="disabled">
										<option value=""></option>
									</select>
								</td>
								<td>
									<label>Descripci&oacute;n:</label><br />
									<input type="text" class="wtc25 dis" maxlength="50" id="txtDescDestSegAS" readonly="readonly" />
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
									<select class="w150" id="selCodSubSegDestAS">
										<option value=""></option>
									</select>
								</td>
								<td>
									<label>Descripci&oacute;n:</label><br />
									<input type="text" class="wtc25 dis" maxlength="50" id="txtDescSubSegDestAS" readonly="readonly" />
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
</sec:authorize>
</sec:authorize>
<!-- FIN POPUP ANADIR SEGMENTO -->