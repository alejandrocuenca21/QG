<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize ifAnyGranted="ROLE_SA,ROLE_AD,ROLE_AB,ROLE_AM,ROLE_AS,ROLE_AP,ROLE_AU">
<div>

	<input type="button" id="btnGuardar" value="Guardar" />
	<input type="button" id="btnCerrar" value="Cerrar" />
	<input type="button" id="btnEliminar" value="Eliminar" style="display: none" />
	
	<br/>
	
	<input type="text" id="txtCodigo" value="" />
	
	<select id="selAmbito"></select>
	
	<input type="text" id="txtCodigoTde" value="" />
	<input type="text" id="txtCodigoTme" value="" />
	<input type="text" id="txtDescripcion" value="" />
	
	<input type="radio" name="rbUnidad" id="rbFija" value="Fija" />Fija
	<input type="radio" name="rbUnidad" id="rbMovil" value="Movil" />Móvil
	<input type="radio" name="rbUnidad" id="rbAmbas" value="Ambas" />Ambas
	<br />
	<textarea id="txtDescDetallada" rows="20" cols="10"></textarea>
	
	<div id="capaAniadirSegmento"></div>
		
	<textarea id="txtTextoLegal" rows="20" cols="10" ></textarea>
	
	<div id="capaAniadirMedio"></div>
			
	<input type="radio" name="rbExclusivo" id="rbExclusivoSi" value="Si" />Sí
	<input type="radio" name="rbExclusivo" id="rbExclusivoNo" value="No" />No
	
	<select id="selNivel"></select>
	
	<br />
	
	<input type="radio" name="rbOrigen" id="rbOrigenSi" value="Si" />Sí
	<input type="radio" name="rbOrigen" id="rbOrigenNo" value="No" />No
	
	<select id="selConsen"></select>
	
	<br />
	
	<input type="radio" name="rbLogica" id="rbLogicaT" value="T" />T
	<input type="radio" name="rbLogica" id="rbLogicaE" value="E" />E
	<input type="text" id="txtLiberacion" value="" />
	<textarea id="txtObservaciones" rows="20" cols="10"></textarea>
	
	<br />
	
	<input type="text" id="txtComunicacion" value="" />
	
	<br />
	
	<input type="radio" name="rbContactado" id="rbContactadoSi" value="Si" />Sí
	<input type="radio" name="rbContactado" id="rbContactadoNo" value="No" />No
	
	<input type="text" id="txtFechaVigor" value="" />
	<input type="text" id="txtFinVigencia" value="" />
	<select id="selObjeto"></select>
	
</div>
</sec:authorize>