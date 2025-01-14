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
					<li class="link">
						<html:link action="vMovimientosTratados">Errores</html:link>
					</li>
					<li class="link">
						<html:link action="vConvivencia">Convivencia</html:link>
					</li>									
					<li class="sel">
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
			
			<h2 class="titInformes">Informe de conciliaci&oacute;n de clientes</h2>
		</div>
			
		<div class="recCentCont">
			
			<table class="formInformes">
				<colgroup>
					<col width="75"/><col width="106"/><col width="273"/>
				</colgroup>
				<tbody>
					<tr>
						<td><label>Informe del d&iacute;a:</label></td>
						<td>
							<div class="divCalendar" id="divTxtFechaBusqueda">
								<input type="text" maxlength="10" class="inputCalendar" id="txtFechaBusqueda" />
								<html:image styleClass="icoCalendar" styleId="btnCalendarFechaBusqueda" page="/images/iconos/QGicoCalendar.gif"/>
							</div>
						</td>

						<td>
							<html:image styleClass="btn" styleId="btnBuscar" page="/images/botones/QGbtBuscarInformes.gif" alt="Buscar" onclick="return false;" />
						</td>
						<td>
							<div class="divBotones">																		
								<span class="edicion">
									<html:image styleClass="btn" styleId="btnExportar" page="/images/botones/QGbtnExportarExcel.gif" alt="Exportar Excel" onclick="return false;" />
									<html:image styleClass="btn" styleId="btnImprimir" page="/images/botones/QGbtImprimir.gif" alt="Imprimir" onclick="return false;"/>
								</span>
							</div>						
						</td>							
					</tr>
				</tbody>
			</table>	
					
			<!-- Inicio Pestañas -->
			<div id="divPes" class="divPes">
				<ul>
					<li id="pesInforme" class="activa">
						<span>Informe</span>
					</li>
					<li id="pesNSCO">
						<span>NSCO</span>
					</li>
					<li id="pesPREPAGO">
						<span>PREPAGO</span>
					</li>					
					<li id="pesTW">
						<span>TW</span>
					</li>
					<li id="pesInformativo">
						<span>Informativo</span>
					</li>
				</ul>
				
				<div class="contPest" id="informeSalida">	
	
					<div id="divInforme" class="divDatosGenerales" style="padding: 0pt;">
						<div class="table_container_left"  style="width:275px;">
							<table class="left_table">
								<tbody>
									<tr>
										<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td>N&uacute;mero de clientes recibidos <br> &nbsp;</td>
									</tr>
									<tr>
										<td>N&uacute;mero de clientes descartados por no cumplir <br>condiciones de carga y haber quedado excluidos de QG</td>
									</tr>
									<tr>
										<td>N&uacute;mero de clientes descartados por <br>fecha modif. Posterior a conciliaci&oacute;n. </td>
									</tr>
									<tr>
										<td>N&uacute;mero de clientes descartados por tipo de doc.<br> o nombre / raz&oacute;n social err&oacute;neo</td>
									</tr>
									<tr>
										<td>Clientes de QG que no existen en origen por <br>regularizaciones en sistemas origen</td>
									</tr>
									<tr>
										<td>N&uacute;mero de clientes para el proceso de conciliaci&oacute;n <br> &nbsp;</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableDepDatosCli" style="width:466px;">
						</div>
						<br style="clear:both;">
						
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td>N&uacute;mero de clientes para el proceso de conciliaci&oacute;n</td>
									</tr>
									<tr>
										<td>Clientes correctos sin discrepancias</td>
									</tr>
									<tr>
										<td>Clientes a dar de alta en QG</td>
									</tr>
									<tr>
										<td>Clientes a modificar en QG</td>
									</tr>
									<tr>
										<td>Clientes a dar de baja en QG</td>
									</tr>									
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableCliConciliados" style="width:420px;">
						</div>	
						<br style="clear:both;">
						
						<div class="table_container_left"  style="width:322px;">
							<table class="left_table">
								<tbody>
									<tr>
										<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="noBrTable">&nbsp;</td>
									</tr>									
									<tr>
										<td>N&uacute;mero de direcciones recibidas</td>
									</tr>
									<tr>
										<td>Direc. no conciliadas por pertenecer a excli. no cargados en QG</td>
									</tr>
									<tr>
										<td>Direcciones a conciliar</td>
									</tr>
									<tr>
										<td>Direcciones correctas sin discrepancias</td>
									</tr>
									<tr>
										<td>Direcciones a dar de alta en QG</td>
									</tr>
									<tr>
										<td>Direcciones a modificar en QG</td>
									</tr>
									<tr>
										<td>Direcciones a dar de baja en QG</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableConciliacionDir"  style="width:241px; overflow-x: hidden;">
						</div>	
						<br style="clear:both;">
					</div>
					
					<div id="divNSCO" class="divDatosGenerales"  style="display:none;">
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">TOTAL CLIENTES NSCO EN SISTEM ORIGEN</td>
									</tr>
									<tr>
										<td>    - EXCLIENTES NO TRATADOS POR NO EXISTIR EN QG:</td>
									</tr>
									<tr>
										<td>    - CLIENTES DE NSCO ORIGEN NO TRAT. POR EL TMS.:</td>
									</tr>
									<tr>
										<td>    - CLI. RECHAZ. POR TIPO Y/O N. DOCUM.  ERRONEO:</td>
									</tr>
									<tr>
										<td>    - CLI. RECHAZ. POR NOM/APELL/RAZ. SOC. ERRONEO:</td>
									</tr>
									<tr>
										<td class="tab30">TOTAL CLIENTES NSCO EN TABLAS QG</td>
									</tr>
									<tr>
										<td>  - CLIENTES ERR. EN QG POR NO EXISTIR EN ORIGEN:</td>
									</tr>
									<tr>
										<td>  - CLIENTES DE CLGL NO TRAT. POR VALOR DEL TMS:</td>
									</tr>
									<tr>
										<td class="tab30">CLIENTES NSCO EN SISTEMA ORIGEN CONCILIADOS</td>
									</tr>
									<tr>
										<td class="tab30">CLIENTES NSCO EN QG CONCILIADOS</td>
									</tr>
									<tr>
										<td>    - CLIENTES NSCO OK  EN TABLAS QG:</td>
									</tr>
									<tr>
										<td>    - TOTAL MOVS. DE ALTA DE CLIENTES NSCO EN QG:</td>
									</tr>
									<tr>
										<td>    - TOTAL MOVS. DE MOD. DE CLIENTES NSCO EN QG:</td>
									</tr>
									<tr>
										<td class="tab30">TOTAL DIRECCIONES NSCO EN SIST. ORIGEN</td>
									</tr>
									<tr>
										<td class="tab30">TOTAL DIRECCIONES NSCO EN TABLAS QG</td>
									</tr>
									<tr>
										<td class="tab30">TOTAL DIRECC. OK EN TABLAS DE QG</td>
									</tr>
									<tr>
										<td>modificar</td>
									</tr>
									<tr>
										<td>alta</td>
									</tr>
									<tr>
										<td>baja</td>
									</tr>
									<tr>
										<td>de exclientes</td>
									</tr>									
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableNSCO"  style="overflow-x: hidden;width:275px;">
						</div>	
						<br style="clear:both;">						
					</div>
					
					<div id="divPREPAGO" class="divDatosGenerales"  style="display:none;">
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">TOTAL CLIENTES PREPAGO EN SISTEM ORIGEN</td>
									</tr>
									<tr>
										<td>    - CLIENTES DE NSCO ORIGEN NO TRAT. POR EL TMS.:</td>
									</tr>
									<tr>
										<td>    - CLI. RECHAZ. POR TIPO Y/O N. DOCUM.  ERRONEO:</td>
									</tr>
									<tr>
										<td class="tab30">TOTAL CLIENTES PREPAGO EN TABLAS QG</td>
									</tr>
									<tr>
										<td>  - CLIENTES ERR. EN QG POR NO EXISTIR EN ORIGEN:</td>
									</tr>
									<tr>
										<td>  - CLIENTES DE CLGL NO TRAT. POR VALOR DEL TMS:</td>
									</tr>
									<tr>
										<td class="tab30">CLIENTES PREPAGO EN SIST. ORIGEN CONCILIADOS</td>
									</tr>
									<tr>
										<td class="tab30">CLIENTES PREPAGO EN QG CONCILIADOS</td>
									</tr>
									<tr>
										<td>    - CLIENTES PREPAGO OK  EN TABLAS QG:</td>
									</tr>
									<tr>
										<td>    - TOTAL MOV. DE ALTA DE CLIENTES PREPAGO EN QG:</td>
									</tr>
									<tr>
										<td>    - TOTAL MOV. DE MOD. DE CLIENTES PREPAGO EN QG:</td>
									</tr>
									<tr>
										<td>    - TOTAL MOVS. PASO EXCLIENTES DE PREPAGO EN QG:</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tablePREPAGO"  style="overflow-x: hidden;width:275px;">
						</div>	
						<br style="clear:both;">						
					</div>					

					<div id="divTW" class="divDatosGenerales"  style="display:none;">
						<div class="table_container_left">
							<table class="left_table">
								<tbody>
									<tr>
									<td class="noBrTable">&nbsp;</td>
									</tr>
									<tr>
										<td class="tab30">TOTAL CLIENTES TW EN SISTEMA ORIGEN</td>
									</tr>
									<tr>
										<td>   - EXCLIENTES NO TRATADOS POR NO EXISTIR EN QG:</td>
									</tr>
									<tr>
										<td>   - CLIENTES DE TW ORIGEN NO TRAT. POR EL TMS.:</td>
									</tr>
									<tr>
										<td>   - CLI. RECHAZ. POR TIPO Y/O N. DOCUM.  ERRONEO:</td>
									</tr>
									<tr>
										<td>   - CLI. RECHAZ. POR NOM/APELL/RAZ. SOC. ERRONEO:</td>
									</tr>
									<tr>
										<td class="tab30">TOTAL CLIENTES TW EN TABLAS QG:</td>
									</tr>
									<tr>
										<td>  - CLIENTES ERR. EN QG POR NO EXISTIR EN ORIGEN:</td>
									</tr>
									<tr>
										<td>  - CLIENTES DE CLGL NO TRAT. POR VALOR DEL TMS:</td>
									</tr>
									<tr>
										<td class="tab30">CLIENTES TW EN SISTEMA ORIGEN CONCILIADOS</td>
									</tr>
									<tr>
										<td class="tab30">CLIENTES TW EN QG CONCILIADOS</td>
									</tr>
									<tr>
										<td>    - CLIENTES TW OK  EN TABLAS QG:</td>
									</tr>
									<tr>
										<td>    - TOTAL MOVS. DE MOD. DE CLIENTES TW EN QG:</td>
									</tr>
									<tr>
										<td>    - TOTAL MOVS. DE ALTA DE CLIENTES TW EN QG:</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="table_container_right" id="tableTW"  style="overflow-x: hidden;width:275px;">
						</div>	
						<br style="clear:both;">						
					</div>

					<div id="divInformativo" class="divDatosGenerales"  style="display:none;width:752px; padding:0;">
					
					
						<table class="tableErrores wT100">
							<colgroup><col width="30%" /><col width="70%" /></colgroup>
							<thead>
								<tr>
									<th colspan="2" class="colCabecera">CAMPOS PESTA&Ntilde;A NSCO</th>
								</tr>
								<tr>
									<th class="colCabecera">CAMPO</th>
									<th class="colCabecera">SIGNIFICADO DEL CAMPO</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>TOTAL CLIENTES NSCO EN SISTEM ORIGEN</td>
									<td>N&deg; de Clientes del Sistema Origen enviados en el fichero de Conciliaci&oacute;n</td>
								</tr>
								<tr>
									<td>    - EXCLIENTES NO TRATADOS POR NO EXISTIR EN QG:</td>
									<td>Exclientes de NSCO que no se cargaron en QG </td>
								</tr>
								<tr>
									<td>    - CLIENTES DE NSCO ORIGEN NO TRAT. POR EL TMS.:</td>
									<td>Cliente de NSCO con actulalizaci&oacute;n en QG posterior a la creaci&oacute;n del fichero de Conciliaci&oacute;n de<br>NSCO, estos clientes no se tratan, se conciliar&aacute;n en la siguiente ejecuci&oacute;n de la Conciliaci&oacute;n</td>
								</tr>
								<tr>
									<td>    - CLI. RECHAZ. POR TIPO Y/O N. DOCUM.  ERRONEO:</td>
									<td>Se valida que el n&uacute;mero de documento sea distinto de espacios y ceros, y que el tipo de<br>documento tenga alguno de los siguientes valores: N, C, P &oacute; T.</td>
								</tr>
								<tr>
									<td>    - CLI. RECHAZ. POR NOM/APELL/RAZ. SOC. ERRONEO:</td>
									<td>Las validaciones que se realizan son:<br>
										*  Si el nombre est&aacute; informado, el apellido-1 tiene que estar informado, en caso contrario ser&iacute;a <br> err&oacute;neo.<br>
										*  Si el nombre no viene informado, la raz&oacute;n social tiene que venir informada, en caso contrario<br>ser&iacute;a err&oacute;neo.<br>
										*  Si el anagrama de cliente global calculado es igual a espacios ser&iacute;a err&oacute;neo.</td>
								</tr>
								<tr>
									<td>TOTAL CLIENTES NSCO EN TABLAS QG</td>
									<td>N&deg; de clientes de NSCO que est&aacute;n en QG</td>
								</tr>
								<tr>
									<td>  - CLIENTES ERR. EN QG POR NO EXISTIR EN ORIGEN:</td>
									<td>Clientes que no se concilian porque est&aacute;n en QG y no est&aacute;n en el fichero de NSCO,<br> son los clientes Cancelados</td>
								</tr>
								<tr>
									<td>  - CLIENTES DE CLGL NO TRAT. POR VALOR DEL TMS.:</td>
									<td>Cliente de QG con actulalizaci&oacute;n en QG posterior a la creaci&oacute;n del fichero de Conciliaci&oacute;n de<br>NSCO,estos clientes no se tratan, se conciliar&aacute;n en la siguiente ejecuci&oacute;n de la Conciliaci&oacute;n</td>
								</tr>
								<tr>
									<td>CLIENTES NSCO EN SISTEMA ORIGEN CONCILIADOS</td>
									<td>N&deg; de clientes de NSCO que se han conciliado (A3-A4-A5-A6-A7)</td>
								</tr>
								<tr>
									<td>CLIENTES NSCO EN QG CONCILIADOS</td>
									<td>N&deg; de clientes de QG que se han conciliado (A10-A11-A12)</td>
								</tr>
								<tr>
									<td>    - CLIENTES NSCO OK  EN TABLAS QG:</td>
									<td>Clientes que est&aacute;n igual en QG y en NSCO</td>
								</tr>
								<tr>
									<td>    - TOTAL MOVS. DE ALTA DE CLIENTES NSCO EN QG:</td>
									<td>Clientes que deber&iacute;an existir en QG y no existen</td>
								</tr>
								<tr>
									<td>    - TOTAL MOVS. DE MOD. DE CLIENTES NSCO EN QG:</td>
									<td>Clientes que tienen informaci&oacute;n desactualizada en QG frente a NSCO</td>
								</tr>
							</tbody>
						</table>
						
						<br /><br />
						
						<table class="tableErrores wT100">
							<colgroup><col width="30%" /><col width="70%" /></colgroup>
							<thead>
								<tr>
									<th colspan="2" class="colCabecera">CAMPOS PESTA&Ntilde;A PREPAGO</th>
								</tr>
								<tr>
									<th class="colCabecera">CAMPO</th>
									<th class="colCabecera">SIGNIFICADO DEL CAMPO</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>TOTAL CLIENTES PREPAGO EN SISTEM ORIGEN</td>
									<td>N&deg; de Clientes del Sistema Origen enviados en el fichero de Conciliaci&oacute;n</td>
								</tr>
								<tr>
									<td>    - CLIENTES DE NSCO ORIGEN NO TRAT. POR EL TMS.:</td>
									<td>Cliente de NSCO con actulalizaci&oacute;n en QG posterior a la creaci&oacute;n del fichero de Conciliaci&oacute;n<br>de NSCO, estos clientes no se tratan, se conciliar&aacute;n en la siguiente ejecuci&oacute;n de la<br>Conciliaci&oacute;n</td>
								</tr>
								<tr>
									<td>    - CLI. RECHAZ. POR TIPO Y/O N. DOCUM.  ERRONEO:</td>
									<td>Se valida que el n&uacute;mero de documento sea distinto de espacios y ceros, y que el tipo de <br> documento tenga alguno de los siguientes valores: N, C, P &oacute; T.</td>
								</tr>
								<tr>
									<td>TOTAL CLIENTES PREPAGO EN TABLAS QG</td>
									<td>N&deg; de clientes de NSCO que est&aacute;n en QG</td>
								</tr>
								<tr>
									<td>  - CLIENTES ERR. EN QG POR NO EXISTIR EN ORIGEN..:</td>
									<td>Clientes que no se concilian porque est&aacute;n en QG y no est&aacute;n en el fichero de NSCO,<br> son los clientes Cancelados</td>
								</tr>
								<tr>
									<td>  - CLIENTES DE CLGL NO TRAT. POR VALOR DEL TMS...:</td>
									<td>Cliente de QG con actulalizaci&oacute;n en QG posterior a la creaci&oacute;n del fichero de Conciliaci&oacute;n de<br>NSCO,estos clientes no se tratan, se conciliar&aacute;n en la siguiente ejecuci&oacute;n de la<br>Conciliaci&oacute;n</td>
								</tr>
								<tr>
									<td>CLIENTES PREPAGO EN SISTEMA ORIGEN CONCILIADOS</td>
									<td>N&deg; de clientes de NSCO que se han conciliado (A3-A4-A5-A6-A7)</td>
								</tr>
								<tr>
									<td>CLIENTES PREPAGO EN QG CONCILIADOS</td>
									<td>N&deg; de clientes de QG que se han conciliado (A10-A11-A12)</td>
								</tr>
								<tr>
									<td>    - CLIENTES PREPAGO OK  EN TABLAS QG:</td>
									<td>Clientes que est&aacute;n igual en QG y en NSCO</td>
								</tr>
								<tr>
									<td>    - TOTAL MOV. DE ALTA DE CLIENTES PREPAGO EN QG:</td>
									<td>Clientes que deber&iacute;an existir en QG y no existen</td>
								</tr>
								<tr>
									<td>    - TOTAL MOV. DE MOD. DE CLIENTES PREPAGO EN QG:</td>
									<td>Clientes que tienen informaci&oacute;n desactualizada en QG frente a NSCO</td>
								</tr>
								<tr>
									<td>    - TOTAL MOVS. PASO EXCLIENTES DE PREPAGO EN QG:</td>
									<td>Clientes que deber&iacute;an existir en PREPAGO y no existen</td>
								</tr>								
							</tbody>
						</table>
						
						<br /><br />						
						<table class="tableErrores wT100">
							<colgroup><col width="30%" /><col width="70%" /></colgroup>
							<thead>
								<tr>
									<th colspan="2" class="colCabecera">CAMPOS PESTA&Ntilde;A TW</th>
								</tr>
								<tr>
									<th class="colCabecera">CAMPO</th>
									<th class="colCabecera">SIGNIFICADO DEL CAMPO</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>TOTAL CLIENTES TW EN SISTEM ORIGEN</td>
									<td>N&deg; de Clientes del Sistema Origen enviados en el fichero de Conciliaci&oacute;n</td>
								</tr>
								<tr>
									<td>    - EXCLIENTES NO TRATADOS POR NO EXISTIR EN QG:</td>
									<td>Si el cliente existente en TW tiene un estado distinto de 1, 2, 3 &oacute; 4<br>(reactivable (motivo: 15 &oacute; 91))</td>
								</tr>
								<tr>
									<td>    - CLIENTES DE TW ORIGEN NO TRAT. POR EL TMS.:</td>
									<td>Cliente de TW con actulalizaci&oacute;n en QG posterior a la creaci&oacute;n del fichero de Conciliaci&oacute;n de TW,<br> estos clientes no se tratan, se conciliar&aacute;n en la siguiente ejecuci&oacute;n de la Conciliaci&oacute;n</td>
								</tr>
								<tr>
									<td>    - CLI. RECHAZ. POR TIPO Y/O N. DOCUM. ERRONEO:</td>
									<td>Se valida que el n&uacute;mero de documento sea distinto de espacios y ceros, y que el<br>tipo de documento tenga alguno de los siguientes valores:<br>D, P, R, C &oacute; L</td>
								</tr>
								<tr>
									<td>    - CLI. RECHAZ. POR NOM/APELL/RAZ. SOC. ERRONEO:</td>
									<td>Las validaciones que se realizan son: <br>
										*  Si el nombre, apellido-1 y raz&oacute;n social vienen informados ser&iacute;a err&oacute;neo.<br>
										*  Si la raz&oacute;n social no viene informada y el nombre y/&oacute; apellido-1 no est&aacute; informado, ser&iacute;a <br>err&oacute;neo.<br>
										*  Si el anagrama de cliente global calculado es igual a espacios ser&iacute;a err&oacute;neo.</td>
								</tr>
								<tr>
									<td>TOTAL CLIENTES TW EN TABLAS QG</td>
									<td>N&deg; de clientes de TW que est&aacute;n en QG</td>
								</tr>
								<tr>
									<td>  - CLIENTES ERR. EN QG POR NO EXISTIR EN ORIGEN:</td>
									<td>Clientes que no se concilian porque est&aacute;n en QG y no est&aacute;n en el fichero de TW, <br> son altas que han llegado a QG entre que se ha realizado el backup de TW <br>y el inicio de la Conciliaci&oacute;n.</td>
								</tr>
								<tr>
									<td>  - CLIENTES DE CLGL NO TRAT. POR VALOR DEL TMS.:</td>
									<td>Clientes de QG con actulalizaci&oacute;n en QG posterior a la creaci&oacute;n del fichero de Conciliaci&oacute;n de TW,<br>estos clientes no se tratan, se conciliar&aacute;n en la siguiente ejecuci&oacute;n de la Conciliaci&oacute;n</td>
								</tr>
								<tr>
									<td>CLIENTES TW EN SISTEMA ORIGEN CONCILIADOS</td>
									<td>N&deg; de clientes de TW que se han conciliado (A24-A25-A26-A27-A28)</td>
								</tr>
								<tr>
									<td>CLIENTES TW EN QG CONCILIADOS</td>
									<td>N&deg; de clientes de QG que se han conciliado (A31-A32-A33)</td>
								</tr>
								<tr>
									<td>    - CLIENTES TW OK  EN TABLAS QG:</td>
									<td>Clientes que est&aacute;n igual en QG y en TW</td>
								</tr>
								<tr>
									<td>    - TOTAL MOVS. DE ALTA DE CLIENTES TW EN QG:</td>
									<td>Clientes que deber&iacute;an existir en QG y no existen</td>
								</tr>
								<tr>
									<td>    - TOTAL MOVS. DE MOD. DE CLIENTES TW EN QG:</td>
									<td>Clientes que tienen informaci&oacute;n desactualizada en QG frente a TW</td>
								</tr>
							</tbody>
						</table>								
					</div>
				</div>

			</div>
			<div>
				<html:img styleClass="imgInfL" page="/images/QGrecContInfIzq.gif" />
				<html:img styleClass="imgInfR" page="/images/QGrecContInfDrch.gif" />
			</div>	
		</div><!-- fin div class recCentCont -->
	</div>	
</div>
<iframe id="ifExportar" style="display:none"></iframe>
<br class="clear"/>
</sec:authorize>