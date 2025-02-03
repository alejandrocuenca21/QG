<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page import="com.telefonica.ssnn.qg.buscador.clientes.dto.QGHistorialClienteDto"%>
<%@page import="java.text.SimpleDateFormat"%>
<html:xhtml/>

<html:form action="/ListaClientesTW" method="POST">
	<html:hidden styleId="hiddenVueltaFC" property="vuelta" name="TWListaClientesForm"/>
</html:form>		
<%

QGHistorialClienteDto[] historialClientes = (QGHistorialClienteDto[])request.getSession().getAttribute("historial");
SimpleDateFormat format = new SimpleDateFormat("HH:mm");

%>

<!-- Menu de la izquierda -->
<div class="divIzq">
	<div class="divHistorialSesion">
		<!-- Esquinas redondeadas superiores -->
		<div>
			<div class="sup"><div class="supIzq"></div><div class="supMed"></div><div class="supDrch"></div></div>
			
			<div class="cont">
				<div class="contIzq"><div class="contDrch">
					<h3>Historial de sesi&oacute;n</h3>
					<ul id="historialBuscador" class="menuIzq">
					<%
							if(historialClientes != null){
								for(int i = 0; i < 5; i++){ 
									QGHistorialClienteDto historial = historialClientes[i];
									if(historial != null){
									%>
									<li>
										<div idCliente="<%=historial.getCodCliente()%>">
											<span class="hora"><%=format.format(historial.getFechaConsulta())%></span>
											<span class="dni"><%=historial.getNumDocumento()%></span>
											<span class="name"><%=historial.getNombre()%></span>
										</div>
									</li>
								<%	} 
								}
							}else{%>
							<li class="noHist">
								<div>
									<span class="bold">No hay clientes en el historial.</span>
								</div>
							</li>
							<%} %>
					</ul>
				</div></div>
			</div>
			
			<!-- Esquinas redondeadas inferiores -->
			<div class="inf"><div class="infIzq"></div><div class="infMed"></div><div class="infDrch"></div></div>
		</div>
		
		
		<div class="borrarHistorial">
			<span id="lnkBorrarHistorial">Borrar historial</span>
		</div>
	</div>
</div>
<!-- Contenido de la derecha -->
<div class="divTwoColCliente">
	<div class="recCentral">
		
		<div class="recHead">
			<html:img styleClass="imgSupL" page="/images/QGrecContSupIzq.gif" />
			<html:img styleClass="imgSupR" page="/images/QGrecContSupDrch.gif" />			
			<h2 class="titBuscador">Buscador de Clientes</h2>
		</div>
			
			<div class="recCentCont">
				
				<div class="titCriterios">Criterios de b&uacute;squeda</div>
				<div class="formBusc">
					<div class="pRel">
						<html:img styleClass="imgBuscFormTL" page="/images/QGBuscCTL.gif" />
						<html:img styleClass="imgBuscFormTR" page="/images/QGBuscCTR.gif" />
					</div>
					<div class="formBuscCont">
						
						<div class="colA" style="width:160px">
						<table>
								<tbody>
									<tr>									
										<td>
											<label>C&oacute;digo de cliente:</label><br />
											<input type="text" maxlength="11" class="wtc10" id="txtCodCliente" />
										</td>
									</tr>
									
								</tbody>
							</table>
						</div>
						<div class="colB" style="width:33%">
							<table>
								<tbody>
									<tr>										
										<td>
											<label>Tipo Documento:</label><br />
												<select name="select" id="selTipoDoc" class="wtc10"></select>
										</td>
									</tr>
									<tr>										
										<td>
											<label>Documento identificaci&oacute;n:</label><br />								
											<input name="text" maxlength="17" type="text" class="wtc8" id="txtNumeroDoc" />
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="colC">
							<table>
								<tbody>
									<tr>							
										<td><label>Nombre:</label><br /><input type="text" maxlength="50" id="txtNombreCliente" class="wtc8"/></td>
										<td><label>Apellido 1</label><br /><input type="text" maxlength="50" id="txtApellido1" class="wtc10"/></td>
										<td><label>Apellido 2</label><br /><input type="text" maxlength="50" id="txtApellido2" class="wtc10"/></td>
									</tr>
									<tr>		
										<td colspan="3"><label>Raz&oacute;n social</label><br /><input type="text" maxlength="140" class="wtc30" id="txtRazonSocial" /></td>
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
					<html:image styleClass="btn" styleId="btnBuscar" page="/images/botones/QGbtBuscar.gif" alt="Buscar" onclick="return false;"/>
					<html:image styleClass="btn" styleId="btnLimpiar" page="/images/botones/QGbtLimpiar.gif" alt="Limpiar" onclick="return false;" />
				</div>
				
				

				<div id="divResultados" style="display:none;">
					<div class="nResultados">Resultados de la b&uacute;squeda <span class="bold" id="totalReg"></span></div>
					<div id="divGrid"></div>
				
				
					<div class="divBotones">					
						<html:image styleClass="btn" styleId="btnVerDetalle" page="/images/botones/QGbtVerDetalle_des.gif" alt="Ver Detalle"/>
						<%--<html:image styleClass="btn" styleId="btnImprimir" page="/images/botones/QGbtImprimir.gif" alt="Imprimir" onclick="return false;"/> --%>
					</div>
				</div>
				
				<div>
					<html:img styleClass="imgInfL" page="/images/QGrecContInfIzq.gif" />
					<html:img styleClass="imgInfR" page="/images/QGrecContInfDrch.gif" />
				</div>
			</div>
	</div>
	<iframe id="ifExportar" style="display:none"></iframe>
</div>
<!-- POPUP HISTORICO -->
<div id="popUpHistorico" class="x-hidden">
	<div class="divFormPP">
		<table>
			<colgroup><col width="190" /><col width="290" /><col width="290" /></colgroup>
			<tbody>
				<tr>
					<td>
						<label>C&oacute;d. Cli. Global.</label>
					</td>
					<td>
						<label>Documento Identificaci&oacute;n</label>
						
					</td>
					<td>
						<label>Nombre / Raz&oacute;n Social</label>
					</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="txtCodCliGlo" class="wtc10 dis" readonly="readonly"/>
					</td>
					<td>
						<input type="text" id="txtTipoDoc" class="wtc10 dis" readonly="readonly"/>
						<input type="text" id="txtNumDoc" class="wtc10 dis" readonly="readonly"/>
					</td>
					<td>
						<input type="text" id="txtNombre" class="wtc25 dis" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>
						<label>Origen</label>									
					</td>
					<td>
						<label>Segmento</label>
					</td>
					<td>
						<label>Subsegmento</label>
					</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="txtOrigen" class="wtc10 dis" readonly="readonly"/>
					</td>
					<td>
						<input type="text" id="txtCodSegmento" class="wtc2 dis"  readonly="readonly"/>
						<input type="text" id="txtDesSegmento" class="wtc15 dis" readonly="readonly"/>
					</td>
					<td>
						<input type="text" id="txtCodSubSegmento" class="wtc2 dis"  readonly="readonly"/>
						<input type="text" id="txtDesSubSegmento" class="wtc15 dis" readonly="readonly"/>
					</td>
				</tr>
			</tbody>
		</table>
		
		<br/>
		<span class="titFormulario">Historia</span>
		<div id="divGridHistoricoCli"></div>
		
	</div>
	<div class="divBotones">
		<html:image styleClass="btn" styleId="btnVolver" page="/images/botones/QGbtVolver.gif" onclick="return false;" />
	</div>
</div><!-- FIN POPUP HISTORICO -->