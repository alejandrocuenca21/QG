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
			<h2>Consentimientos y Derechos</h2>
		</div>
		
		<div class="recCentCont">
			<html:form action="DetalleConsentimientosDerechos" method="POST">
				<input id="idMethod" type="hidden" name="method" />
				<input id="codDerecho" type="hidden" name="codDerecho" />
				
				<div id="divGrid"></div>
			
				<div class="divBotones">
					<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA,ROLE_AB">
					<span class="edicion">
						<html:image styleClass="btn" styleId="btnNuevo" page="/images/botones/QGbtListadoNuevo.gif"/>
						<html:image styleClass="btn btnDis" styleId="btnEliminar" disabled="true" page="/images/botones/QGbtListadoBaja_des.gif" onclick="return false;" />
					</span>
					</sec:authorize>
					
					<html:image styleClass="btn btnDis" styleId="btnVerDetalle" disabled="true" page="/images/botones/QGbtListadoDetalle_des.gif"/>
					<%--<html:image styleClass="btn" styleId="btnImprimir" page="/images/botones/QGbtImprimir.gif" alt="Imprimir" onclick="return false;"/> --%>
				
				</div>
				<div>
					<html:img styleClass="imgInfL" page="/images/QGrecContInfIzq.gif" />
					<html:img styleClass="imgInfR" page="/images/QGrecContInfDrch.gif" />
				</div>				
			</html:form>
		</div>
		<iframe id="ifExportar" style="display:none"></iframe>
</div>
</sec:authorize>