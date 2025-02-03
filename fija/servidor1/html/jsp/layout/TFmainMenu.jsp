<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page import="org.springframework.security.context.SecurityContextHolder"%>
<%@ page import="org.springframework.security.Authentication"%>
<%@ page import="com.telefonica.ssnn.qg.seguridad.QGUsuario"%>
<%@ page import="com.telefonica.ssnn.qg.seguridad.dto.AccesosDTO"%>
<%@ page import="java.text.SimpleDateFormat"%>

<html:html xhtml="true">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

  <!-- =================== Estilos Ext y tus CSS =================== -->
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
    <link rel="stylesheet" type="text/css"
          href="<html:rewrite page="/" /><c:out value="${style}" />" />
  </c:forEach>

  <!-- =================== Librerías JS Ext =================== -->
  <script type="text/javascript" src="<html:rewrite page="/ext/adapter/ext/ext-base.js" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/ext/ext-all.js" />"></script>
  <script type="text/javascript" src="<html:rewrite page="/ext/ext-lang-es.js" />"></script>

  <!-- Tus JS propios -->
  <script type="text/javascript" src="<html:rewrite page="/js/QGfuncionesComunes.js" />"></script>

  <!-- Otras referencias que necesites -->
  <c:forEach var="script" items="${scripts}">
    <script type="text/javascript"
            src="<html:rewrite page="/" /><c:out value="${script}" />"></script>
  </c:forEach>

  <title><tiles:getAsString name="title" /></title>

  <!-- =========================
       Referencias a Ext BLANK
  ========================== -->
  <script type="text/javascript">
    var contexto = '<html:rewrite page="/" />';
    Ext.BLANK_IMAGE_URL = '<html:rewrite page="/ext/resources/images/default/s.gif" />';
  </script>

<%
  // ========================
  // Recuperamos la info de Spring Security
  // ========================
  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  QGUsuario user = null;
  AccesosDTO[] listaAccesos = null;

  if (auth != null && auth.getPrincipal() instanceof QGUsuario) {
    user = (QGUsuario) auth.getPrincipal();
    listaAccesos = user.getAccesos();
  }

  if (auth != null) {
    request.setAttribute("principal", auth.getPrincipal());
  }

  SimpleDateFormat format = new SimpleDateFormat("HH:mm");
%>

<!-- ===================== Estilos "modal" incrustado ===================== -->
<style type="text/css">
  
  /* Estilo del logout */
  #cerrar {
    float: right;
    cursor: pointer;
  }
</style>

<script type="text/javascript">
  var accesosJS = [
  <%
    if (user != null && listaAccesos != null) {
      for (int i = 0; i < listaAccesos.length; i++) {
        out.print("\"" + listaAccesos[i].getAplicacion() + "\"");
        if (i < listaAccesos.length - 1) {
          out.print(", ");
        }
      }
    }
  %>
  ];

  // ============== Lógica para logout y llenar la tabla (unicolumna) ==============
  window.onload = function() {
    // -- Botón logout --
    var btnLogout = document.getElementById("cerrar");
    if (btnLogout) {
      btnLogout.onclick = function() {
        // Ajusta la ruta de logout si es distinta
        window.location.href = contexto + "logout";
      };
    }

    // Tabla
    var tbAccesos = document.getElementById("tbodyAccesos");
    if (!tbAccesos) return;

    if (accesosJS.length === 0) {
      // Caso: No hay accesos
      var trNoData = document.createElement("tr");
      var tdNoData = document.createElement("td");
      tdNoData.textContent = "No hay accesos disponibles";
      trNoData.appendChild(tdNoData);
      tbAccesos.appendChild(trNoData);
      return;
    }

    // Llenamos la tabla con una fila por acceso
    for (var i = 0; i < accesosJS.length; i++) {
      var originalValue = accesosJS[i];

      // Regla de mapeo
      var displayValue = "";
      if (originalValue === "QG") {
        displayValue = "Acceso Cliente Global";
      } else if (originalValue === "TW") {
        displayValue = "Acceso Cliente Línea fija";
      } else if (originalValue === "D4") {
        displayValue = "Gestión de la información para Publicaciones y servicios de Información";
      }
      else {
        displayValue = originalValue; // Mantiene el valor
      }

      var tr = document.createElement("tr");
      var tdPerfil = document.createElement("td");
      tdPerfil.textContent = displayValue;
      tr.appendChild(tdPerfil);

      // Al hacer clic en la fila
      tr.onclick = (function(val) {
        return function() {
          if (val === "QG") {
              window.location.href = "vIndex.do";
            }  else if (val === "D4") {
            	 window.location.href = "vIndexMenuD4.do";
            }  else if (val === "TW") {
                window.location.href = "vIndexMenuTW.do";
            } else {
                alert("Otro valor: " + val);
           }
        };
      })(originalValue);

      tbAccesos.appendChild(tr);
    }
  };
</script>

</head>

<body>

<div class="content clearfix">
  <!-- ===================== CABECERA ==================== -->
  <div class="cabecera">
    <div class="logo">
      <html:img alt="TELEFONICA" page="/images/QGlogoTelefonica.gif" />
    </div>
   <!-- <div class="RefClientes">
       Imagen "Referencia clientes" (cambia la ruta y la imagen que desees) 
      <html:img alt="Referencia clientes" page="/images/QGlogoTelefonica.gif" />
    </div>-->
    <div class="user">
      <span class="id" id="idUsuario"><c:out value="${principal.username}" /></span>
      <span class="name"><c:out value="${principal.perfil}" /></span>
    </div>
      <div class="logout">
        <html:img alt="Logout" styleId="cerrar" styleClass="icoPoint" page="/images/QGlogout.gif" />
      </div>
  </div>
  <!-- ================ FIN CABECERA ================ -->

  <div class="divMenu">	
    <ul class="dropdown">				     
    </ul>
  </div>

  <!-- ===================== CONTENIDO PRINCIPAL ==================== -->
  <div class="contenido" style="padding: 15px;">
    <div class="modalContainer">
      <!-- Cabecera azulada del "modal" -->
      <div class="modalHeader">Referencia de clientes</div>

      <!-- Cuerpo del modal -->
      <div class="modalBody">
        <div class="modalIntro">
          Seleccione el perfil con el que desea acceder a la aplicación
        </div>

        <!-- Tabla unicolumna -->
        <table class="tablaPerfiles">
          <thead>
            <tr class="tablaTitleRow">
              <th>Perfiles disponibles</th>
            </tr>
          </thead>
          <tbody id="tbodyAccesos">
            <!-- Se llena dinámicamente en window.onload -->
          </tbody>
        </table>
      </div>
      <!-- fin .modalBody -->
    </div><!-- fin .modalContainer -->
  </div><!-- fin .contenido -->
</div><!-- fin .content -->

<!-- ===================== PIE DE PÁGINA ==================== -->
<div class="divFoot">
  <html:img alt="" styleClass="imgSupL" page="/images/QGfootLeft.gif" />
  <html:img alt="" styleClass="imgSupR" page="/images/QGfootRight.gif" />
  <div class="name">
    <span>Referencia Clientes</span> Cliente Global
  </div>
  <div class="fecha">
    <span class="dia"></span>, <span class="date"></span>
  </div>
</div><!--Fin divFoot -->


</body>
</html:html>
