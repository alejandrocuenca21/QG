<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html:xhtml/>

<div class="recCentral">					
	<html:hidden styleId="FCHistorialOrigen" property="origen" name="QGDatosGeneralesForm"/>
	<html:hidden styleId="FCHistorialModalidad" property="modalidad" name="QGDatosGeneralesForm"/>	
	<html:hidden styleId="FCHistorialCodCliente" property="codCliente" name="QGDatosGeneralesForm"/>
	<html:hidden styleId="FCHistorialTipoDoc" property="tipoDoc" name="QGDatosGeneralesForm"/>
	<html:hidden styleId="FCHistorialDocIdentif" property="docIdentif" name="QGDatosGeneralesForm"/>
	<html:hidden styleId="FCHistorialRazonSocial" property="razonSocial" name="QGDatosGeneralesForm"/>
	<html:hidden styleId="FCHistorialNombre" property="nombre" name="QGDatosGeneralesForm"/>
	<html:hidden styleId="FCHistorialPrimerApellido" property="primerApellido" name="QGDatosGeneralesForm"/>
	<html:hidden styleId="FCHistorialSegundoApellido" property="segundoApellido" name="QGDatosGeneralesForm"/>
	<html:hidden styleId="FCHistorialEstado" property="estado" name="QGDatosGeneralesForm"/>
	<html:hidden styleId="FCHistorialSegmento" property="segmento" name="QGDatosGeneralesForm"/>
	<html:hidden styleId="FCHistorialSubsegmento" property="subsegmento" name="QGDatosGeneralesForm"/>

	
	<html:form action="/ListaClientes" method="POST">
		<input id="hiddenVuelta" type="hidden" name="vuelta"/>
	</html:form>	

	<div class="recHead">
		<html:img styleClass="imgSupL" page="/images/QGrecContSupIzq.gif" /><html:img styleClass="imgSupR" page="/images/QGrecContSupDrch.gif" />
		<h2>Ficha de cliente</h2>
		  <div class="bTitBotones">
			<div class="bTit bTitConver activo" id="btnDatosConvergentes">
				<span class="bTitCont">
					<b class="bTitL"></b>
					<b class="bTitR"></b>
					<b class="ico"></b>
					Datos CONVERGENTES
				</span>
			</div>
			
			<div class="bTit bTitDatLNF" id="btnDatosLNF">
				<span class="bTitCont">
					<b class="bTitL"></b>
					<b class="bTitR"></b>
					<b class="ico"></b>
					Datos LNF
				</span>
			</div>
			
			<div class="bTit bTitDatLNM" id="btnDatosLNMC">
				<span class="bTitCont">
					<b class="bTitL"></b>
					<b class="bTitR"></b>
					<b class="ico"></b>
					Datos LNM/Contrato
				</span>
			</div>
			
			<div class="bTit bTitDatLNM" id="btnDatosLNMP">
				<span class="bTitCont">
					<b class="bTitL"></b>
					<b class="bTitR"></b>
					<b class="ico"></b>
					Datos LNM/Prepago
				</span>
			</div>			
			
			<div class="bTit bTitCerrar" id="btnCerrar">
				<span class="bTitCont">
					<b class="bTitL"></b>
					<b class="bTitR"></b>
					<b class="icoCerrar"></b>
					Cerrar
				</span>
			</div>
		</div>
	</div>
	
	<div id="bhBotones" class="bhBotones">
	
		<sec:authorize ifAnyGranted="ROLE_AD,ROLE_SA, ROLE_AC">
		<div class="bth bthNuevaComunicacion" id="btnNuevaComunicacion">
			<span class="bthCont">
				<html:img styleClass="imageSupL" page="/images/botones/QGbackBhL.gif" />
				<html:img styleClass="imageSupR" page="/images/botones/QGbackBhR.gif" />
				Nueva comunicaci&oacute;n...
			</span>
		</div>
		
		<div class="bhSep"></div>
		</sec:authorize>
		
	</div>
	
	<div class="recCentContFC">
		
		
		<div class="formFC">
			<table>
				<colgroup>
					<col width="45" /><col width="45" /><col width="113" /><col width="55" /><col width="141" />
					<col width="256" /><col width="60" /><col width="80" /><col width="*" />
				</colgroup>
				<tbody>
					<tr>
						<td>
							<label>Origen</label><br />
							<span id="lblOrigen"></span>
						</td>
						<td>
							<label>Modalidad</label><br />
							<span id="lblModalidad"></span>
						</td>						
						<td>
							<label>C&oacute;d. cliente Global</label><br />
							<span id="lblCodCliente"></span>
						</td>
						<td>
							<label>Tipo doc.</label><br />
							<span id="lblTipoDoc"></span>
						</td>
						<td>
							<label>N&ordm; documento</label><br />
							<span id="lblNumDoc"></span>
						</td>
						<td>
							<label>Nombre/Raz&oacute;n social</label><br />
							<span id="lblNombreRazon"></span>
						</td>
						<td>
							<label>Segmento</label><br />
							<span id="lblSegmento"></span>
						</td>
						<td>
							<label>Subsegmento</label><br />
							<span id="lblSubseg"></span>
						</td>
						<td>
							<label>Estado</label><br />
							<span id="lblEstado"></span>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div id="divPes" class="divPes">
			<ul>
				<li id="pesDatGen">
					
					<span>Datos generales</span>
					
				</li>
				<li id="pesConsentimientos">
					
					<span>Consentimientos</span>
					
				</li>
				<li id="pesDomicilios">
					
					<span>Domicilios</span>
					
				</li>
			</ul>
			
			<div class="contPest">
				<html:img styleClass="imgCPesL" page="/images/QGcontPestLR.gif" />
				<html:img styleClass="imgCPesR" page="/images/QGcontPestLR.gif" />
				
				<div id="divDatosGenerales" class="divDatosGenerales">
					<div class="colA">
						<h3>Datos principales</h3>
						
						<table>
							<colgroup><col width="200" /><col width="190" /><col width="*" /></colgroup>
							<tbody>
								<tr class="sep">
									<td>
										<label id="etiqCodCliente">C&oacute;digo cliente Global:</label><br/><span id="lblDGCodCliente"></span>
									</td>
									<td>
										<label>Tipo documento:</label><br/><span id="lblDGTipoDoc"></span>
									</td>
									<td>
										<label>N&ordm; documento:</label><br/><span id="lblDGNDoc"></span>
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<label>Nombre/raz&oacute;n social:</label><br/><span id="lblDGNombreRazon"></span>
									</td>
								</tr>
								<tr class="sep">
									<td>
										<label>Apellido 1:</label><br/><span id="lblDGApellido1"></span>
									</td>
									<td colspan="2">
										<label>Apellido 2:</label><br/><span id="lblDGApellido2"></span>
									</td>
								</tr>
								<tr class="sep">
									<td>
										<label>Segmento:</label><br/><span id="lblDGSegmento"></span>
									</td>
									<td>
										<label>Subsegmento:</label><br/><span id="lblDGSubsegmento"></span>
									</td>
									<td>
										<label>Fecha cambio segmento:</label><br/><span id="lblDGFechaCambioSeg"></span>
									</td>
								</tr>
								<tr>
									<td>
										<label>Estado:</label><br/><span id="lblDGEstado"></span>
									</td>
									<td colspan="2">
										<label>Fecha cambio de estado:</label><br/><span id="lblDGFechaCambioEstado"></span>
									</td>									
								</tr>
								<tr class="sep">
									<td colspan="3">
										<label>Motivo del estado:</label><br/><span id="lblDGMotivoEstado"></span>
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<label>Observaciones del cliente:</label><br/><span id="lblDGObservaciones"></span>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div id="colDatosAdicionales" class="colB">
						<h3>Datos adicionales</h3>
						<div class="contColBCG">
							<table>
								<colgroup><col width="50%"/><col width="50%"/></colgroup>
								<tbody>
									<tr>
										<td class="colLbl"><label>CLIENTE INTERNACIONAL</label></td>
										<td class="colDat"><span id="lblDGdaClienteInter"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>PAIS</label></td>
										<td class="colDat"><span id="lblDGdaPais"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>ULTIMA REACTIVACION</label></td>
										<td class="colDat"><span id="lblDGdaUltimaReactivacion"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>EXENCI&Oacute;N DE IMPUESTOS</label></td>
										<td class="colDat"><span id="lblDGdaExencionImpuestos"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>TIPO DE IMPRESI&Oacute;N DE FACTURA</label></td>
										<td class="colDat"><span id="lblDGdaTipoImpresion"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>MONEDA</label></td>
										<td class="colDat"><span id="lblDGdaMoneda"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>NIVEL DE CONFIDENCIALIDAD</label></td>
										<td class="colDat"><span id="lblDGdaNivelConf"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>OFICINA DE ATENCION</label></td>
										<td class="colDat"><span id="lblDGdaOficinaAtencion"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>TIPO DE CLIENTE</label></td>
										<td class="colDat"><span id="lblDGdaTipoCliente"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>CABECERA</label></td>
										<td class="colDat"><span id="lblDGdaCabecera"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>SECTOR ECON&Oacute;MICO</label></td>
										<td class="colDat"><span id="lblDGdaSectorEconomico"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>IDIOMA</label></td>
										<td class="colDat"><span id="lblDGdaIdioma"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>PLAN DE CUENTAS</label></td>
										<td class="colDat"><span id="lblDGdaPlanCuentas"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>ACTIVIDAD ECONOMICA</label></td>
										<td class="colDat"><span id="lblDGdaActividadEconom"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>SEGMENTO DWH</label></td>
										<td class="colDat"><span id="lblDGdaSegmentoSWH"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>NIVELES DE ATENCION PARA LOS CRC</label></td>
										<td class="colDat"><span id="lblDGdaAtencionCliente"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>UNIDAD DE NEGOCIO GLOBAL</label></td>
										<td class="colDat"><span id="lblDGdaUnidadNegGlobal"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>IDENTIFICACION</label></td>
										<td class="colDat"><span id="lblDGdaIdentificacion"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>INICIO VIGENCIA en CGBL</label></td>
										<td class="colDat"><span id="lblDGdaFInicioVigenciaCGBL"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>FIN VIGENCIA en CGBL</label></td>
										<td class="colDat"><span id="lblDGdaFinVigenciaCGBL"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>CLIENTE PADRE</label></td>
										<td class="colDat"><span id="lblDGdaClientePadre"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>TIPO DE CAR&Aacute;CTER INTERNACIONAL</label></td>
										<td class="colDat"><span id="lblDGdaTipoCarInter"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>NOMBRE CORTO</label></td>
										<td class="colDat"><span id="lblDGdaNombreCorto"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>CABECERA EN LNF</label></td>
										<td class="colDat"><span id="lblDGdaCabLNF"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>INICIO VIGENCIA LNF</label></td>
										<td class="colDat"><span id="lblDGdaFInicioVigenciaLNF"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>FIN VIGENCIA LNF</label></td>
										<td class="colDat"><span id="lblDGdaFinVigenciaLNF"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>NOMBRE/RAZON SOCIAL EN LNM</label></td>
										<td class="colDat"><span id="lblDGdaNombreRazonLNM"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>APELLIDO 1 en LNM</label></td>
										<td class="colDat"><span id="lblDGdaApellido1LNM"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>APELLIDO 2 en LNM</label></td>
										<td class="colDat"><span id="lblDGdaApellido2LNM"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>TIPO DOCUMENTO EN LNM</label></td>
										<td class="colDat"><span id="lblDGdaTipoDocLNM"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>TIPO DE CLIENTE FISCAL</label></td>
										<td class="colDat"><span id="lblDGdaTipoClienteFis"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>TIPO DE SEGMENTO</label></td>
										<td class="colDat"><span id="lblDGdaTipoSeg"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>MOTIVO DE LA SEGMENTACION</label></td>
										<td class="colDat"><span id="lblDGdaMotivoSeg"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>CABECERA EN LNM</label></td>
										<td class="colDat"><span id="lblDGdaCabLNM"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>PAIS LNM</label></td>
										<td class="colDat"><span id="lblDGdaPaisLNM"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>TIPO CLIENTE LNM</label></td>
										<td class="colDat"><span id="lblDGdaTipoClienteLNM"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>ALTA LNM</label></td>
										<td class="colDat"><span id="lblDGdaAltaLNM"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>INICIO VIGENCIA LNM</label></td>
										<td class="colDat"><span id="lblDGdaFInicioVigenciaLNM"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>FIN VIGENCIA LNM</label></td>
										<td class="colDat"><span id="lblDGdaFinVigenciaLNM"></span></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div id="colDatosRepresentante" class="colB">
						<h3>Datos Representante</h3>
						<div class="contColBCG">
							<table>
								<colgroup><col width="50%"/><col width="50%"/></colgroup>
								<tbody>
									<tr>
										<td class="colLbl"><label>TIPO DOCUMENTO</label></td>
										<td class="colDat"><span id="lblDGrpsTipoDoc"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>N&deg; DOCUMENTO</label></td>
										<td class="colDat"><span id="lblDGrpsNumDocRPS"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>PAIS</label></td>
										<td class="colDat"><span id="lblDGrpsPais"></span></td>
									</tr>			
																								<tr>
										<td class="colLbl"><label>NOMBRE/RAZON SOCIAL</label></td>
										<td class="colDat"><span id="lblDGrpsNombreRazon"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>APELLIDO 1</label></td>
										<td class="colDat"><span id="lblDGrpsApellido1"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>APELLIDO 2</label></td>
										<td class="colDat"><span id="lblDGrpsApellido2"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>CODIGO POSTAL</label></td>
										<td class="colDat"><span id="lblDGrpsCoPostal"></span></td>
									</tr>		
																									<tr>
										<td class="colLbl"><label>ALTA</label></td>
										<td class="colDat"><span id="lblDGrpsAlta"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>INICIO VIGENCIA</label></td>
										<td class="colDat"><span id="lblDGrpsFInicioVigencia"></span></td>
									</tr>
									<tr>
										<td class="colLbl"><label>FIN VIGENCIA</label></td>
										<td class="colDat"><span id="lblDGrpsFinVigencia"></span></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>					
				</div>			
				
				<div id="divConsentimientos" class="divConsentimientos">
				
					<div class="formBusc">
						<div class="pRel">
							<html:img styleClass="imgBuscFormTL" page="/images/QGBuscCTL.gif" />
							<html:img styleClass="imgBuscFormTR" page="/images/QGBuscCTR.gif" />
						</div>
						<div class="formBuscCont">
							<table class="filtroConsent">
								<colgroup><col width="160" /><col width="190" /><col width="140" /><col width="*" /></colgroup>
								<tbody>
									<tr>
										<td>
											<label>Tipo:</label><br/>
											<select id="selObjeto" class="w150"></select>
										</td>
										<td>
											<label>Estado de la gesti&oacute;n:</label><br/>
											<select id="selEstGestion" class="w180"></select>
										</td>
										<td>
											<label>Estado del consent./dcho.</label><br/>
											<select id="selEstadoCD" disabled="disabled" class="w120">
												<option value="">Todos</option>
												<option value="s">Si consiente</option>
												<option value="n">No consiente</option>
											</select>
										</td>
										<td>
											<label>Intervalo de fecha de cambio de estado:</label><br/>
											<div class="divCalendar">
												<input type="text" class="inputCalendar" id="txtFechaInicio" maxlength ="10" />
												<html:image styleClass="icoCalendar" styleId="btnCalendarFechaInicio" page="/images/iconos/QGicoCalendar.gif"/>
											</div>
											<div class="divCalendar margin0">
												<input type="text" class="inputCalendar" id="txtFechaFin" maxlength ="10"/>
												<html:image styleClass="icoCalendar" styleId="btnCalendarFechaFin" page="/images/iconos/QGicoCalendar.gif"/>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
							
							<div class="divBotonesCons">
								<html:image styleClass="btn" styleId="btnFiltrar" page="/images/botones/QGbtFiltrar.gif" alt="Filtrar" onclick="return false;"/>
								<html:image styleClass="btn" styleId="btnVerTodos" page="/images/botones/QGbtVerTodos.gif" alt="Ver Todos" onclick="return false;" />
							</div>
						</div>
						
						<div class="pRel">
							<html:img styleClass="imgBuscFormBL" page="/images/QGBuscCBL.gif" />
							<html:img styleClass="imgBuscFormBR" page="/images/QGBuscCBR.gif" />
						</div>
					</div>
					
					<div id="divExpandirColCons" class="divExpColAll">
						<span id="expandAllCons">expandir todo</span> | <span id="colAllCon">colapsar todo</span>
					</div>
					<div id="divCabeceraConsent"></div>
					<div class="resFiltro"></div>
				</div>

				<div id="divDomicilios" class="divDomicilios">
					
					<div class="divExpColAll">
						<span id="expandAll">expandir todo</span> | <span id="colAll">colapsar todo</span>
					</div>
					
					<div id="divDomGeo">
						<div class="cabDatDom">
							<b class="headTitL"></b>
							<b class="headTitR"></b>
							<div class="tit" id="titDom"><span>Domicilios Geogr&aacute;ficos L&iacute;nea Fija<b id="contDom"></b></span></div>
							<div class="expandCol"><span id="expDom">expandir</span></div>
						</div>
						<div class="formDatos">
							<html:img styleClass="imgFCDatTL" page="/images/QGFCDomFormTL.gif" />
							<html:img styleClass="imgFCDatTR" page="/images/QGFCDomFormTR.gif" />
							<div id="domGeograficos" class="formDatCont"></div>
							<html:img styleClass="imgFCDatBL" page="/images/QGFCDomFormBL.gif" />
							<html:img styleClass="imgFCDatBR" page="/images/QGFCDomFormBR.gif" />
						</div>
					</div>
					
					<div id="divDomGeoLNM">
                        <div class="cabDatDom">
                            <b class="headTitL"></b>
                            <b class="headTitR"></b>
                            <div class="tit" id="titDomLNM"><span>Domicilios Geogr&aacute;ficos L&iacute;nea M&oacute;vil<b id="contDomLNM"></b></span></div>
                            <div class="expandCol"><span id="expDomLNM">expandir</span></div>
                        </div>
                        <div class="formDatos">
                            <html:img styleClass="imgFCDatTL" page="/images/QGFCDomFormTL.gif" />
                            <html:img styleClass="imgFCDatTR" page="/images/QGFCDomFormTR.gif" />
                            <div id="domGeograficosLNM" class="formDatCont"></div>
                            <html:img styleClass="imgFCDatBL" page="/images/QGFCDomFormBL.gif" />
                            <html:img styleClass="imgFCDatBR" page="/images/QGFCDomFormBR.gif" />
                        </div>
                    </div>
                    
                    <div id="divDomLogicas">
                        <div class="cabDatDom">
                            <b class="headTitL"></b>
                            <b class="headTitR"></b>
                            <div class="tit" id="titDomLog"><span>Direcciones L&oacute;gicas<b id="contDomLog"></b></span></div>
                            <div class="expandCol"><span id="expDomLogicas">expandir</span></div>
                        </div>
                        <div class="formDatos">
                            <html:img styleClass="imgFCDatTL" page="/images/QGFCDomFormTL.gif" />
                            <html:img styleClass="imgFCDatTR" page="/images/QGFCDomFormTR.gif" />
                            <div id="domDirLogicas" class="formDatCont"></div>
                            <html:img styleClass="imgFCDatBL" page="/images/QGFCDomFormBL.gif" />
                            <html:img styleClass="imgFCDatBR" page="/images/QGFCDomFormBR.gif" />
                        </div>
                    </div>
				</div>
			</div>
		</div>
		
		<div>
			<html:img styleClass="imgInfL" page="/images/QGrecContInfIzq.gif" />
			<html:img styleClass="imgInfR" page="/images/QGrecContInfDrch.gif" />
		</div>	
	</div>
	
</div>		