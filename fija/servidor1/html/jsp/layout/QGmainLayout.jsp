<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page import="org.springframework.security.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.Authentication" %>
<%@ page import="com.telefonica.ssnn.qg.seguridad.QGUsuario" %>
<%@ page import="com.telefonica.ssnn.qg.seguridad.dto.AccesosDTO" %>
<%@ page import="com.telefonica.ssnn.qg.buscador.clientes.dto.QGHistorialClienteDto" %>
<%@ page import="java.text.SimpleDateFormat" %>

<html:html xhtml="true">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

  <tiles:useAttribute id="styles" name="lstStyles" classname="java.util.List" />
  <tiles:useAttribute id="scripts" name="lstScripts" classname="java.util.List" />

  <link rel="stylesheet" type="text/css" href="<html:rewrite page="/ext/resources/css/ext-all.css" />" />
  <link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/QGcglobal-theme.css" />" />
  <link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/QGicon.css" />" />

  <!--[if IE 6]>
    <link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/QGstyleie6.css" />" />
  <![endif]-->

  <![if !IE 6]>
    <link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/QGstyle.css" />" />
  <![endif]>

  <c:forEach var="style" items="${styles}">
    <link rel="stylesheet" type="text/css" href="<html:rewrite page="/" /><c:out value="${style}" />" />
  </c:forEach>

  <!-- Librerías Ext y plugins -->
  <script type="text/javascript" src="<html:rewrite page="/ext/adapter/ext/ext-base.js" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/ext/ext-all.js" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/ext/ext-lang-es.js" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/ext/plugins/objectreader/objectReader.js" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/ext/plugins/editorpastecopygrid/EditorPasteCopyGrid.js" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/ext/plugins/editorpastecopygrid/ExcelCellSelectionModel.js" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/js/QGfuncionesComunes.js" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/js/QGNoRefreshPaginator.js" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/ext/plugins/pagingmemoryproxy/PagingMemoryProxy.js" />"></script>
  <!-- Otros DJS del proyecto -->
  <script type="text/javascript" src="<html:rewrite page="/djs/administracion/menserror/QGPagingToolbarMensajeErrorDjs.jsp" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/djs/administracion/autorizaciones/QGPagingToolbarSisExtPstDjs.jsp" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/djs/administracion/autorizaciones/QGPagingToolbarSrvPstDjs.jsp" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/djs/administracion/autorizaciones/QGPagingToolbarAutorizPstDjs.jsp" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/djs/administracion/autorizaciones/QGPagingToolbarAutorizPstHistDjs.jsp" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/djs/administracion/segmentacion/presegmentacion/QGPagingToolbarPresegmentacionDjs.jsp" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/djs/administracion/segmentacion/presegmentacion/QGPagingToolbarPresegmentacionHistDjs.jsp" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/js/QGjsLayout.js" />"></script>

  <!-- jscalendar -->
  <link rel="stylesheet" type="text/css" href="<html:rewrite page="/jscalendar/css/jscal2.css" />" />
  <link rel="stylesheet" type="text/css" href="<html:rewrite page="/jscalendar/css/border-radius.css" />" />
  <link rel="stylesheet" type="text/css" href="<html:rewrite page="/jscalendar/css/cglobal/calendarCglobal.css" />" />
  <script type="text/javascript" src="<html:rewrite page="/jscalendar/jscal2.js" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/jscalendar/lang/es.js" />"></script>

  <script type="text/javascript">
    var contexto = '<html:rewrite page="/" />';
    Ext.BLANK_IMAGE_URL = '<html:rewrite page="/ext/resources/images/default/s.gif" />';
  </script>

  <c:forEach var="script" items="${scripts}">
    <script type="text/javascript" src="<html:rewrite page="/" /><c:out value="${script}" />"></script>
  </c:forEach>

  <title><tiles:getAsString name="title" /></title>

  <!-- ============== Estilos y JS para el “modal” ============== -->
  <style>
    /* Overlay e interior popup Accesos */
    #overlayModal {
      position: fixed; 
      top: 0; left: 0;
      width: 100%; height: 100%;
      background-color: rgba(0,0,0,0.5);
      z-index: 10000;
      display: none;
    }
    #popupAccesos {
      position: fixed;
      top: 50%; left: 50%;
      transform: translate(-50%, -50%);
      width: 320px;
      min-height: 150px;
      background: #fff;
      border: 1px solid #ccc;
      border-radius: 6px;
      padding: 15px;
      z-index: 10001;
      display: none;
    }
    #popupAccesos .popup-title {
      font-weight: bold;
      margin-bottom: 10px;
    }
    #popupAccesos .popup-opciones label {
      display: block;
      margin-bottom: 6px;
    }
    #popupAccesos .popup-buttons {
      text-align: right;
      margin-top: 10px;
    }

    /* Permite que ul.menuHistorial se muestre al “hover” en #divPerfil */
    .divMenu #divPerfil:hover ul.menuHistorial,
    .divMenu #divPerfil.hover ul.menuHistorial {
      display: block;
    }
  </style>
</head>

<%
  // ========================
  //  Lógica recuperando Security
  // ========================
  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  QGHistorialClienteDto[] historialClientes = null;
  SimpleDateFormat format = new SimpleDateFormat("HH:mm");
  AccesosDTO[] listaAccesos = null;
  QGUsuario user = null;

  if (auth != null) {
    request.setAttribute("principal", auth.getPrincipal());
  }
  if (auth != null && auth.getPrincipal() instanceof QGUsuario) {
    user = (QGUsuario) auth.getPrincipal();
    listaAccesos = user.getAccesos();
  }
  historialClientes = (QGHistorialClienteDto[]) request.getSession().getAttribute("historial");
%>

<script type="text/javascript">
  // Generamos accesosJS en base a "listaAccesos"
  var accesosJS = [
  <%
    if (user != null && listaAccesos != null && listaAccesos.length > 0) {
      for (int i = 0; i < listaAccesos.length; i++) {
        out.print("\"" + listaAccesos[i].getAplicacion() + "\"");
        if (i < listaAccesos.length - 1) {
          out.print(", ");
        }
      }
    }
  %>
  ];

  // console.log para depurar
  // window.onload con asignación directa (o addEventListener)
  window.onload = function() {
    console.log("onload: accesosJS =", accesosJS);

    var ulPerfil = document.getElementById("ulPerfil");
    if (!ulPerfil) return;  // seguridad

    // Si no hay accesos, mostramos "No hay accesos disponibles"
    if (!accesosJS || accesosJS.length === 0) {
      var liNo = document.createElement("li");
      liNo.className = "noHist";
      var dv = document.createElement("div");
      dv.textContent = "No hay accesos disponibles";
      liNo.appendChild(dv);
      ulPerfil.appendChild(liNo);
      return;
    }

    // Caso: sí hay accesos => los pintamos
    for (var i = 0; i < accesosJS.length; i++) {
      var valor = accesosJS[i];
      // Mapeo del valor
      var label = "";
      if (valor === "QG") {
        label = "Linea Global";
      } else if (valor === "TW") {
        label = "Cliente Linea Fija";
      } else {
        label = valor;
      }

      var li = document.createElement("li");
      var divAcc = document.createElement("div");
      divAcc.textContent = label;
      divAcc.onclick = (function(acceso) {
        return function() {         
          // Redirigir si quieres:
           if (acceso === "QG") {
             window.location.href = contexto + "vIndex.do";
           } else if (acceso === "TW") {
             window.location.href = contexto + "vIndexMenu.do";
           } else {
             alert("Otro valor: " + acceso);
           }
        };
      })(valor);

      li.appendChild(divAcc);
      ulPerfil.appendChild(li);
    }
  };
</script>

<body>

<!-- Layout principal -->
<div class="marco" id="marco">
  <div class="content clearfix">

    <!-- Cabecera -->
    <div class="cabecera">
      <div class="logo">
        <html:img alt="TELEFONICA" page="/images/QGlogoTelefonica.gif" />
      </div>
      <div class="logION">
        <html:img alt="ION Cliente Global" page="/images/QGlogoION.gif" />
      </div>
      <div class="user">
        <span class="id" id="idUsuario"><c:out value="${principal.username}" /></span>
        <span class="name"><c:out value="${principal.perfil}" /></span>
      </div>
      <div class="logout">
        <html:img alt="Logout" styleId="cerrar" styleClass="icoPoint" page="/images/QGlogout.gif" />
      </div>
    </div><!--Fin cabecera -->

    <!-- ================ Menú, Historial, etc. ================== -->
    <div class="divMenu">
      <ul class="dropdown">
        <li class="first">
          <html:link action="vListaClientes" styleClass="icoBuscar">Buscador Clientes</html:link>
        </li>
        <!-- Si quieres más <li> -->
      </ul>

      <!-- Bloque "Perfil cliente global" -->
         <!-- Bloque Historial original -->
      <div class="historial" id="divPerfil" style="margin-right: 15px;">
        <span>Perfil cliente global</span>
        <ul class="menuHistorial" id="ulPerfil">       
          
        </ul>
      </div><!--fin divHistorial -->
	  
	        <!-- Bloque Historial original -->
      <div class="historial" id="divHistorial">
        <span>Historial</span>
        <ul class="menuHistorial" id="historial">
          <%
            if (historialClientes != null) {
              for(int i = 0; i < 5; i++){
                QGHistorialClienteDto h = historialClientes[i];
                if (h != null) {
          %>
            <li <%= (i == 0 ? "class=\"first\"" : "") %>>
              <div idCliente="<%=h.getCodCliente()%>">
                <span class="hora"><%=format.format(h.getFechaConsulta())%></span>
                <span class="dni"><%=h.getNumDocumento()%></span>
                <span class="name"><%=h.getNombre()%></span>
              </div>
            </li>
          <%
                }
              }
            } else {
          %>
            <li class="noHist">
              <div>
                <span>No hay clientes en el historial.</span>
              </div>
            </li>
          <%
            }
          %>
        </ul>
      </div><!--fin divHistorial -->
    </div><!--fin divMenu -->

    <div class="contenido">
      <html:form action="/DatosGenerales" method="POST">
        <div>
          <input id="historialIdMethod" type="hidden" name="method" />
          <input id="historialOrigen" type="hidden" name="origen" />
          <input id="historialModalidad" type="hidden" name="modalidad" />
          <input id="historialCodCliente" type="hidden" name="codCliente" />
          <input id="historialTipoDoc" type="hidden" name="tipoDoc" />
          <input id="historialDocIdentif" type="hidden" name="docIdentif" />
          <input id="historialRazonSocial" type="hidden" name="razonSocial" />
          <input id="historialNombre" type="hidden" name="nombre" />
          <input id="historialPrimerApellido" type="hidden" name="primerApellido" />
          <input id="historialSegundoApellido" type="hidden" name="segundoApellido" />
          <input id="historialEstado" type="hidden" name="estado" />
          <input id="historialSegmento" type="hidden" name="segmento" />
          <input id="historialSubsegmento" type="hidden" name="subsegmento" />
        </div>
      </html:form>

      <tiles:insert attribute="contenido" flush="false" />
    </div><!-- Fin .contenido -->
  </div><!-- fin .content -->
</div><!-- fin #marco -->

<!-- Pie de página -->
<div class="divFoot">
  <html:img alt="" styleClass="imgSupL" page="/images/QGfootLeft.gif" />
  <html:img alt="" styleClass="imgSupR" page="/images/QGfootRight.gif" />
  <div class="name">
    <span>Test -</span> Cliente Global
  </div>
  <div class="fecha">
    <span class="dia"></span>, <span class="date"></span>
  </div>
</div><!--Fin divFoot -->

<!-- Form si quisieras POST en onClick -->
<form id="formSeleccionAcceso" action="/eligeAcceso" method="post">
  <input type="hidden" name="seleccionar" value="seleccionar" />
  <input type="hidden" name="accesoSeleccionado" id="acceso" />
</form>

<!-- Overlay y popup (opcional) -->
<div id="overlayModal"></div>
<div id="popupAccesos">
  <div class="popup-title">Título Popup</div>
  <div class="popup-opciones">
    <label>Opción 1</label>
    <label>Opción 2</label>
  </div>
  <div class="popup-buttons">
    <button onclick="cerrarModal()">Cerrar</button>
  </div>
</div>

</body>
</html:html>
