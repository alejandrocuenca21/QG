<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html:xhtml/>

<!-- Menu de la izquierda -->
<div class="divIzqInf">
	<div class="divInformes">
		<!-- Esquinas redondeadas superiores -->
		<div class="sup"><div class="supIzq"></div><div class="supMed"></div><div class="supDrch"></div></div>
		<h2>Informes</h2>
		<div class="cont">
			<div class="contIzq"><div class="contDrch">
				<ul class="menuIzq">
					<li class="sel">
						<html:link action="vInformesDuplicados">Duplicados</html:link>
					</li>
					<li>
						<html:link action="vMovimientosTratados">Errores</html:link>
					</li>
					<li class="link">
						<html:link action="vConvivencia">Convivencia</html:link>
					</li>
					<li class="link">
						<html:link action="vConciliacion">Conciliaci&oacute;n</html:link>
					</li>
				</ul>
			</div></div>
		</div>
		<!-- Esquinas redondeadas inferiores -->
		<div class="inf"><div class="infIzq"></div><div class="infMed"></div><div class="infDrch"></div></div>
	</div>
</div>

<!-- Contenido de la derecha -->
			
<div class="divCenterTwoCol">
	<div class="recCentral">
		<div class="recHead">
			<html:img styleClass="imgSupL" page="/images/QGrecContSupIzq.gif" />
			<html:img styleClass="imgSupR" page="/images/QGrecContSupDrch.gif" />
			
			<h2 class="titInformes">informes de duplicados</h2>
			<div class="tiempoConsulta"><label>&Uacute;ltima toma de datos:</label><span class="horaConsulta" id="horaConsulta"></span></div>
		</div>
			
			<div class="recCentCont">
				
				<table class="formInformes">
					<colgroup><col width="200"/><col width="120"/><col width="35"/><col width="*"/><col width="*"/></colgroup>
					<tbody>
						<tr>
							<td><label>Buscar los recogidos entre las fechas:</label></td>
							<td>
								<div class="divCalendar" id="divTxtFechaInicio">
									<input type="text" maxlength="10" class="inputCalendar" id="txtFechaInicio" />
									<html:image styleClass="icoCalendar" styleId="btnCalendarFechaInicio" page="/images/iconos/QGicoCalendar.gif"/>
								</div>
							</td>
							<td><span>hasta</span></td>
							<td>
								<div class="divCalendar" id="divTxtFechaFin">
									<input type="text" maxlength="10" class="inputCalendar" id="txtFechaFin" />
									<html:image styleClass="icoCalendar" styleId="btnCalendarFechaFin" page="/images/iconos/QGicoCalendar.gif"/>
								</div>
							</td>
							<td>
								<html:image styleClass="btn" styleId="btnBuscar" page="/images/botones/QGbtBuscarInformes.gif" alt="Buscar" onclick="return false;" />
							</td>
						</tr>
					</tbody>
				</table>
				<div class="divEstadisticas">
					<div class="colA">
						<div class="divProc">
							<html:img styleClass="imgL" page="/images/QGInfEstL.gif" />
							<span class="txt proces">PROCESADOS</span><span class="result" id="procesados"></span>
							<html:img styleClass="imgR" page="/images/QGInfEstR.gif" />
						</div>
					</div>
					
					<div class="colB">
						<div class="divSup">
							<html:img styleClass="imgL" page="/images/QGInfEstLg.gif" />
							<span class="txt acept">ACEPTADOS</span><span class="result" id="aceptados"></span>
							<html:img styleClass="imgR" page="/images/QGInfEstRg.gif" />
						</div>
						
						<div class="colsFM">
							<div class="divFijo">
								<html:img styleClass="imgL" page="/images/QGInfEstLp.gif" />
								<span class="txt fijo">FIJO</span><span class="result" id="aceptFijo"></span>
								<html:img styleClass="imgR" page="/images/QGInfEstRp.gif" />
							</div>
							
							<div class="divMovil">
								<html:img styleClass="imgL" page="/images/QGInfEstLp.gif" />
								<span class="txt movil">M&Oacute;VIL</span><span class="result" id="aceptMovil"></span>
								<html:img styleClass="imgR" page="/images/QGInfEstRp.gif" />
							</div>
						</div>
					</div>
					
					<div class="colC">
						<div class="divSup">
							<html:img styleClass="imgL" page="/images/QGInfEstLg.gif" />
							<span class="txt rech">RECHAZADOS</span><span class="result" id="rechazados"></span>
							<html:img styleClass="imgR" page="/images/QGInfEstRg.gif" />
						</div>
						
						<div class="colsFM">
							<div class="divFijo">
								<html:img styleClass="imgL" page="/images/QGInfEstLp.gif" />
								<span class="txt fijo">FIJO</span><span class="result" id="rechFijo"></span>
								<html:img styleClass="imgR" page="/images/QGInfEstRp.gif" />
							</div>
							
							<div class="divMovil">
								<html:img styleClass="imgL" page="/images/QGInfEstLp.gif" />
								<span class="txt movil">M&Oacute;VIL</span><span class="result" id="rechMovil"></span>
								<html:img styleClass="imgR" page="/images/QGInfEstRp.gif" />
							</div>
						</div>
					</div>
					
				</div>
				<div>
					<div class="nResultados">Resultados de la b&uacute;squeda <span class="bold">[<span id="totalReg"></span>]</span></div>
					<div id="divGrid"></div>
					<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA">
					<div class="seleccion">seleccionar: <span id="btnTodo" onclick="return false;">Todo</span> | <span id="btnNada" onclick="return false;">Nada</span></div>
					</sec:authorize>
					
					<div class="divBotones">
						<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA">
						<span class="edicion">
							<html:image styleClass="btn" styleId="btnLimpiar" page="/images/botones/QGbtLimpiar.gif" alt="Limpiar" onclick="return false;" />
							<html:image styleClass="btn btnDis" styleId="btnAceptar" page="/images/botones/QGbtAceptar_des.gif" disabled="disabled" alt="Aceptar" onclick="return false;" />
							<html:image styleClass="btn btnDis" styleId="btnRechazar" page="/images/botones/QGbtRechazar_des.gif" disabled="disabled" alt="Rechazar" onclick="return false;"/>
						</span>
						</sec:authorize>
						<span class="edicion">
							<html:image styleClass="btn" styleId="btnExportar" page="/images/botones/QGbtnExportarExcel.gif" alt="Exoirtar Excel" onclick="return false;" />
							<html:image styleClass="btn" styleId="btnImprimir" page="/images/botones/QGbtImprimir.gif" alt="Imprimir" onclick="return false;"/>
						</span>
						<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA">
						<html:image styleClass="btn btnDis" styleId="btnGuardar" page="/images/botones/QGbtGuardarCambios_des.gif" disabled="disabled" alt="Guardar cambios" onclick="return false;"/>
						</sec:authorize>
					</div>
				</div>
				
				<div>
					<html:img styleClass="imgInfL" page="/images/QGrecContInfIzq.gif" />
					<html:img styleClass="imgInfR" page="/images/QGrecContInfDrch.gif" />
				</div>				
				
			</div>
	</div>
</div>
<iframe id="ifExportar" style="display:none"></iframe>
<br class="clear"/>