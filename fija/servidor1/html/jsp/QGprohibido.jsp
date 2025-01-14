<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	
	<link rel="stylesheet" type="text/css" href="<html:rewrite page="/ext/resources/css/ext-all.css" />" />
	<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/QGcglobal-theme.css" />" />
	
	<!--[if IE 6]>
    	<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/QGstyleie6.css" />" />
  	<![endif]-->
  	
  	 <![if !IE 6]>
    	<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/QGstyle.css" />" />
  	<![endif]>
  	
  	
</head>
<body>
	<%--Marco general de la pagina --%>
	<div class="marco" id="marco">
		<div class="content clearfix">
			<%--Cabecera --%>
			<div class="cabecera">
				<div class="logo">
					<html:img alt="TELEFONICA" page="/images/QGlogoTelefonica.gif" />
				</div>
				<div class="logION">
					<html:img alt="ION Cliente Global" page="/images/QGlogoION.gif" />
				</div>
			</div><%--Fin cabecera --%>
			
			<div class="contenido">
				<div class="divLock">
					<div class="pRel">
						<html:img styleClass="imgLockTL" page="/images/QGLockCTL.gif" />
						<html:img styleClass="imgLockTR" page="/images/QGLockCTR.gif" />
					</div>
						
					<div class="lock">Prohibido</div>
					
					<div class="pRel">
						<html:img styleClass="imgLockBL" page="/images/QGLockCBL.gif" />
						<html:img styleClass="imgLockBR" page="/images/QGLockCBR.gif" />
					</div>
				</div>
				
			</div>
		</div><%--fin content --%>
	</div><%--fin marco --%>
	<div class="divFoot">
		<html:img styleClass="imgSupL" page="/images/QGfootLeft.gif" />
		<html:img styleClass="imgSupR" page="/images/QGfootRight.gif" />
		<div class="name">
			<span>ION -</span> Cliente Global
		</div>
	</div><%--Fin divFoot --%>
</body>
</html>