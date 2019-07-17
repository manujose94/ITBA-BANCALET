<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import = "java.io.*,java.util.*" %>
<%@ page language="java"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
    <!-- google font -->
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700"/>" rel="stylesheet"
          type="text/css"/>
	<!--Import Google Icon Font-->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- Material Design Lite CSS -->
    <!--Import materialize.css-->
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/materialize.min.css"/>"  media="screen,projection"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/material_style.css"/>"/>
    <!-- Template Styles -->
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/plugins.min.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/responsive.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/bancalet/bancalet.css"/>" rel="stylesheet" type="text/css">
 <!-- Favicon -->
<link rel="shortcut icon" type="image/x-icon"
	href="<c:url value="/resources/img/bancalet/appicon.ico"/>" />
    
	<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
	<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>    
<script>	
	$(document).ready(function(){
    	$('.slider').slider({
    	height: 600
  	   	});
  });
	 		   
      </script>
</head>
<body class="page-header-fixed page-content-white page-md header-white logo-dark" >
	<div class="page-wrapper">
	 
    <!-- start header -->
    <jsp:include page="/WEB-INF/jsp/encabezadopagina.jsp"/>
    <!-- end header -->
  	<jsp:include page="/WEB-INF/jsp/piepagina.jsp" />
    <!-- start page container -->
    <div class="page-container">
		<jsp:include page="/WEB-INF/jsp/inicio/barralateralNologin.jsp" />
		
        <!-- start page content -->
        <div class="page-content-wrapper">
        <div style="    margin-left: 235px;">
         <div class="slider">
			    <ul class="slides">
			      <li>
			        <img src="<c:url value="/resources/img/index/1_index.jpg"/>"/> <!-- random image -->
			        <div class="caption center-align">
			          <h3><spring:message code="index.label1" /></h3>
			          <h5 class="light grey-text text-lighten-3"><spring:message code="index.label2" /></h5>
			        </div>
			      </li>
			      <li>
			        <img src="<c:url value="/resources/img/index/2_index.jpg"/>"/> <!-- random image -->
			        <div class="caption left-align">
			          <h3><spring:message code="index.label3" /></h3>
			          <h5 class="light grey-text text-lighten-3"><spring:message code="index.label4" /></h5>
			        </div>
			      </li>
			      <li>
			        <img src="<c:url value="/resources/img/index/3_index.jpg"/>"/> <!-- random image -->
			        <div class="caption right-align">
			          <h3><spring:message code="index.label5" /></h3>
			          <h5 class="light grey-text text-lighten-3"><spring:message code="index.label6" /></h5>
			        </div>
			      </li>
			      <li>
			        <img src="<c:url value="/resources/img/index/4_index.jpg"/>"/> <!-- random image -->
			        <div class="caption center-align">
			          <h3><spring:message code="index.label7" /></h3>
			          <h5 class="light grey-text text-lighten-3"><spring:message code="index.label8" /></h5>
			        </div>
			      </li>
			    </ul>
  		  </div>
  		  </div>
        </div>
    </div>
    <!-- end page content -->
	</div>
    <!-- Common js-->
    <script src="<c:url value="/resources/js/app.js"/>"></script>
    <script src="<c:url value="/resources/js/layout.js"/>"></script>
    <script src="<c:url value="/resources/js/materialize.min.js"/>"></script>
    <!-- Material -->
	<script src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
    <!-- end js include path -->
</body>
</html>