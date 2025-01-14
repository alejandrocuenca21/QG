<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html:xhtml/>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
<div class="recCentral">
	<html:hidden styleId="codDerecho" property="codDerecho" name="QGConsentimientosDerechosForm"/>
	
	<div class="recHead">
		<html:img styleClass="imgSupL" page="/images/QGrecContSupIzq.gif" /><html:img styleClass="imgSupR" page="/images/QGrecContSupDrch.gif" />
		<h2 id="idSubTitulo"></h2>
	</div>
	
	<div class="bhBotones">
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
		<div class="bth bthGuardar" id="btnGuardar">
			<span class="bthCont">
				<html:img styleClass="imageSupL" page="/images/botones/QGbackBhL.gif" />
				Guardar
				<html:img styleClass="imageSupR" page="/images/botones/QGbackBhR.gif" />
			</span>
		</div>
		</sec:authorize>
		
		<div class="bth bthCerrar" id="btnCerrar">
			<span class="bthCont">
				<html:img styleClass="imageSupL" page="/images/botones/QGbackBhL.gif" />
				Cerrar
				<html:img styleClass="imageSupR" page="/images/botones/QGbackBhR.gif" />
			</span>
		</div>
		<div class="bhSep"></div>
		
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
		<div class="bth bthEliminar" id="btnEliminar">
			<span class="bthCont">
				<html:img styleClass="imageSupL" page="/images/botones/QGbackBhL.gif" />
				Eliminar
				<html:img styleClass="imageSupR" page="/images/botones/QGbackBhR.gif" />
			</span>
		</div>
		</sec:authorize>
	</div>
	
	<div class="recCentCont">
		
		
		<div class="formDC">
			<table class="">
				<colgroup><col width="50%" /><col width="50%" /></colgroup>
				<tbody>
					<tr>
						<td class="vTop">
							<div>
								<label><span class="important"></span>C&oacute;digo del derecho</label><br/> 
								<input type="text" maxlength="3" class="wtc3" id="txtCodigo" value="" />
							</div>
							<div>
								<label><span class="important"></span>Descripci&oacute;n</label><br/>
								<input type="text" class="wtc50" maxlength="100" id="txtDescripcion" value="" />
							</div>
							<div>
								<label><span class="important"></span>Descripci&oacute;n detallada</label><br/>
								<textarea id="txtDescDetallada" onKeyPress="return maxlengthArea(this.id,event,160);" class="wTA500" rows="5"></textarea>
							</div>
							<div>
								<label><span class="important"></span>Texto Legal</label><br/>
								<textarea id="txtTextoLegal" class="wTA500" rows="8"></textarea>
							</div>
							<table>
								<colgroup>
									<col width="*" /><col width="*" /><col width="80" />
									<col width="*" /><col width="95" />
								</colgroup>
								<tbody>
									<tr>
										<td><label><span class="important"></span>Exclusivo:</label></td>
										<td>
											<span class="grupoRad">
												<input type="radio" name="rbExclusivo" id="rbExclusivoSi" value="Si" />
												<span>S&iacute;</span>
											</span>
										</td>
										<td>
											<span class="grupoRad">
												<input type="radio" name="rbExclusivo" id="rbExclusivoNo" value="No" />
												<span>No</span>
											</span>
										</td>
										<td colspan="2">
											<label><span class="important"></span>Nivel de Aplicaci&oacute;n:</label>
											<select id="selNivel" class="w120"></select>
										</td>
									</tr>
									<tr>
										<td><label><span class="important"></span>Origen de Contrataci&oacute;n:</label></td>
										<td>
											<span class="grupoRad">
												<input type="radio" name="rbOrigen" id="rbOrigenSi" value="Si" />
												<span>S&iacute;</span>
											</span>
										</td>
										<td>
											<span class="grupoRad">
												<input type="radio" name="rbOrigen" id="rbOrigenNo" value="No" />
												<span>No</span>
											</span>
										</td>
										<td colspan="2">
                                            <label><span class="important"></span>Tipo consentimiento:</label>
                                            <select id="selConsen" class="w120"></select>
                                        </td>
									</tr>
									<tr>
										<td><label><span class="important"></span>L&oacute;gica:</label></td>
										<td>
											<span class="grupoRad">
												<input type="radio" name="rbLogica" id="rbLogicaT" value="T" />
												<span>T</span>
											</span>
										</td>
										<td>
											<span class="grupoRad">
												<input type="radio" name="rbLogica" id="rbLogicaE" value="E" />
												<span>E</span>
											</span>
										</td>
										<td><label>D&iacute;as Liberaci&oacute;n:</label></td>
										<td><input type="text" class="wtn3 dis" maxlength="3" readonly="readonly" id="txtLiberacion" onKeyPress="return bloquearNoNumerico(event)" onBlur="comprobarNumerico(this.value,this)" value="" /></td>
									</tr>
									<tr>
										<td colspan="3">&nbsp;</td>
										<td><label>D&iacute;as nueva comunicaci&oacute;n:</label></td>
										<td><input type="text" class="wtn3" maxlength="3" id="txtComunicacion" onKeyPress="return bloquearNoNumerico(event)" onBlur="comprobarNumerico(this.value,this)" value="" /></td>
									</tr>
									<tr>
										<td><label><span class="important"></span>Aplica a p&uacute;blico contactado:</label></td>
										<td>
											<span class="grupoRad">
												<input type="radio" name="rbContactado" id="rbContactadoSi" value="Si" />
												<span>S&iacute;</span>
											</span>
										</td>
										<td colspan="3">
											<span class="grupoRad">
												<input type="radio" name="rbContactado" id="rbContactadoNo" value="No" />
												<span>No</span>
											</span>
										</td>
									</tr>
									<tr>
										<td><label><span class="important"></span>Fecha entrada en vigor:</label></td>
										<td colspan="4">
											<div class="divCalendar" id="inputCalOblig">
												<input type="text" maxlength="10" class="inputCalendar" id="txtFechaVigor" value="" />
												<html:image styleClass="icoCalendar" styleId="btnCalendarFechaInicio" page="/images/iconos/QGicoCalendar.gif"/>
											</div>
										</td>								
									</tr>
									<tr>
										<td><label>Fecha fin de vigencia:</label></td>
										<td colspan="4">
											<div class="divCalendar" id="divCalFechaFin">
												<input type="text" maxlength="10" class="inputCalendar" id="txtFinVigencia" value="" />
												<html:image styleClass="icoCalendar" styleId="btnCalendarFechaFin" page="/images/iconos/QGicoCalendar.gif"/>
											</div>
										</td>
									</tr>
									<tr>
										<td><label><span class="important"></span>Tipo objeto:</label></td>
										<td colspan="4"><select id="selObjeto" class="w340"></select></td>
									</tr>
								</tbody>
							</table>
						</td>
						<td class="vTop">
							<table class="fRight">
								<colgroup><col width="230" /><col width="90" /><col width="*" /></colgroup>
								<tbody>
									<tr>
										<td>
											<label><span class="important"></span>&Aacute;mbito</label><br/>
											<select class="w210" id="selAmbito"></select>
										</td>
										<td>
											<label>C&oacute;digo en TdE</label><br />
											<input type="text" maxlength="3" class="wtc3" id="txtCodigoTde" value="" />
										</td>
										<td>
											<label>C&oacute;digo en TME</label><br />
											<input type="text" maxlength="3" class="wtc3" id="txtCodigoTme" value="" />
										</td>
									</tr>
									<tr>
										<td colspan="3">
											<label><span class="important"></span>Unidad de Aplicaci&oacute;n</label><br />
											<span class="grupoRad">
												<input type="radio" name="rbUnidad" id="rbFija" value="Fija" /><span>Fija</span>
												<input type="radio" name="rbUnidad" id="rbMovil" value="Movil" /><span>M&oacute;vil</span>
												<input type="radio" name="rbUnidad" id="rbAmbas" value="Ambas" /><span>Ambas</span>
											</span>
										</td>
									</tr>
									<tr>
										<td colspan="3">
											<label><span class="important"></span>Segmento/subsegmento de aplicaci&oacute;n:</label><br />
											<div id="capaAniadirSegmento"></div>
										</td>
									</tr>
									<tr>
										<td colspan="3">
											<label><span class="important"></span>Medios de Comunicaci&oacute;n(Canales):</label><br />
											<div id="capaAniadirMedio"></div>
										</td>
									</tr>
									<tr>
										<td colspan="3">
											<label>Observaciones:</label><br />
											<textarea id="txtObservaciones" onKeyPress="return maxlengthArea(this.id,event,160);" rows="7" class="wTA400"></textarea>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
		
		</div>
		
		<div>
			<html:img styleClass="imgInfL" page="/images/QGrecContInfIzq.gif" />
			<html:img styleClass="imgInfR" page="/images/QGrecContInfDrch.gif" />
		</div>	
	</div>
	
</div>
</sec:authorize>