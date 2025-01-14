<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html:xhtml/>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
<!-- Menu de la izquierda -->
<div class="divIzqInf">
	<div class="divInformes">
		<!-- Esquinas redondeadas superiores -->
		<div class="sup"><div class="supIzq"></div><div class="supMed"></div><div class="supDrch"></div></div>
		<h2>Informes</h2>
		<div class="cont">
			<div class="contIzq"><div class="contDrch">
				<ul class="menuIzq">
					<li class="linkDis">
						<!--<html:link action="vInformesDuplicados">Duplicados</html:link>-->
						<a>Duplicados</a>
					</li>
					<li class="sel">
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
			
			<h2 class="titInformes">Informe de errores</h2>
			<div class="tiempoConsulta"><label>&Uacute;ltima toma de datos:</label><span class="horaConsulta" id="horaConsulta"></span></div>
		</div>
			
			<div class="recCentCont">
				
				<table class="formInformes">
					<colgroup>
						<col width="200"/><col width="120"/><col width="35"/><col width="*"/>
						<col width="52"/><col width="*"/><col width="*"/>
					</colgroup>
					<tbody>
						<tr>
							<td><label>Buscar los recogidos en la fecha:</label></td>
							<td>
								<div class="divCalendar" id="divTxtFechaBusqueda">
									<input type="text" maxlength="10" class="inputCalendar" id="txtFechaBusqueda" />
									<html:image styleClass="icoCalendar" styleId="btnCalendarFechaBusqueda" page="/images/iconos/QGicoCalendar.gif"/>
								</div>
							</td>
							<!-- 
							
							<td><span>hasta</span></td>
							<td>
								<div class="divCalendar" id="divTxtFechaFin">
									<input type="text" maxlength="10" class="inputCalendar" id="txtFechaFin" />
									<html:image styleClass="icoCalendar" styleId="btnCalendarFechaFin" page="/images/iconos/QGicoCalendar.gif"/>
								</div>
							</td>  -->
							
							<td>
								<html:image styleClass="btn" styleId="btnBuscar" page="/images/botones/QGbtBuscarInformes.gif" alt="Buscar" onclick="return false;" />
							</td>
							<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA">
							<td><label>Con estado de revisi&oacute;n:</label></td>
							<td>
								<select class="w120" id="selEstado">
									<option>Todos</option>
									<option>Revisados</option>
									<option>No revisados</option>
								</select>
							</td>
							</sec:authorize>
						</tr>
					</tbody>
				</table>
				<div class="divEstadisticas">
					<div class="colA">
						<div class="divProcE">
							<html:img styleClass="imgL" page="/images/QGInfEstEL.gif" />
							<span class="txt proces">PROCESADOS</span><span class="result" id="procesados"></span>
							<html:img styleClass="imgR" page="/images/QGInfEstER.gif" />
						</div>
					</div>
					
					<div class="colB">
						<div class="divSup">
							<html:img styleClass="imgL" page="/images/QGInfEstLg.gif" />
							<span class="txt acept">CORRECTOS</span><span class="result" id="correctos"></span>
							<html:img styleClass="imgR" page="/images/QGInfEstRg.gif" />
						</div>
						
						<div class="colsFM">
							<div class="divFijo">
								<html:img styleClass="imgL" page="/images/QGInfEstLp.gif" />
								<span class="txt fijo">FIJO</span><span class="result" id="cFijo"></span>
								<html:img styleClass="imgR" page="/images/QGInfEstRp.gif" />
							</div>
							
							<div class="divMovil">
								<html:img styleClass="imgL" page="/images/QGInfEstLp.gif" />
								<span class="txt movil">M&Oacute;VIL</span><span class="result" id="cMovil"></span>
								<html:img styleClass="imgR" page="/images/QGInfEstRp.gif" />
							</div>
						</div>
					</div>
					
					<div class="colC">
						<div class="divSupE">
							<html:img styleClass="imgL" page="/images/QGinfEstELg.gif" />
							<span class="txt rech">CON ERROR</span><span class="result" id="conError"></span>
							<html:img styleClass="imgR" page="/images/QGinfEstERg.gif" />
						</div>
						
						<div class="colsFM">
							<div class="divFijo">
								<html:img styleClass="imgL" page="/images/QGInfEstLp.gif" />
								<span class="txt fijo">FIJO</span><span class="result" id="errorFijo"></span>
								<html:img styleClass="imgR" page="/images/QGInfEstRp.gif" />
							</div>
							
							<div class="divMovil">
								<html:img styleClass="imgL" page="/images/QGInfEstLp.gif" />
								<span class="txt movil">M&Oacute;VIL</span><span class="result" id="errorMovil"></span>
								<html:img styleClass="imgR" page="/images/QGInfEstRp.gif" />
							</div>
						</div>
					</div>
					
				</div>
				
				<div class="nResultados">Registros CON ERROR + Fijo + M&oacute;vil <span class="bold">[<span id="totalReg"></span>]</span></div>
				
				<div id="divGrid"></div>
				
				<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA">
				<div class="seleccion">seleccionar: <span id="btnTodo" onclick="return false;">Todo</span> | <span id="btnNada" onclick="return false;">Nada</span></div>
				</sec:authorize>
				
				<div class="divBotones">
					<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA">
					<span class="edicion">
						<html:image styleClass="btn" styleId="btnLimpiar" page="/images/botones/QGbtLimpiar.gif" alt="Limpiar" onclick="return false;" />
						<html:image styleClass="btn btnDis" styleId="btnRevisado" page="/images/botones/QGbtRevisado_des.gif" disabled="disabled" alt="Revisado" onclick="return false;" />
					</span>
					</sec:authorize>
					
					<span class="edicion">
						<html:image styleClass="btn" styleId="btnExportar" page="/images/botones/QGbtnExportarExcel.gif" alt="Exportar Excel" onclick="return false;" />
						<html:image styleClass="btn" styleId="btnImprimir" page="/images/botones/QGbtImprimir.gif" alt="Imprimir" onclick="return false;"/>
					</span>
					
					<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA">
					<html:image styleClass="btn btnDis" styleId="btnGuardar" page="/images/botones/QGbtGuardarCambios_des.gif" alt="Guardar cambios" onclick="return false;"/>
					</sec:authorize>
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
</sec:authorize>