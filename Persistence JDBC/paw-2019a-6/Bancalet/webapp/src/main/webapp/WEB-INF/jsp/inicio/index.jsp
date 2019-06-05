<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import = "java.io.*,java.util.*" %>
<html>
<head>
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
    <link rel="shortcut icon" type="image/x-icon" href="/resources/img/bancalet/appicon.ico"/>
    
	<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
	<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>    
	<script>
		
	//$(document).ready(function(){
	 //   $('.carousel').carousel({
	  //  	shift: 20,
	  //  	dist: -50
	  // 
	  //  	});
	    
	 // });
	
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
  
    <!-- start page container -->
    <div class="page-container">
		<jsp:include page="/WEB-INF/jsp/inicio/barralateralNologin.jsp" />
		
        <!-- start page content -->
        <div class="page-content-wrapper">
        <div style="    margin-left: 235px;">
         <div class="slider">
			    <ul class="slides">
			      <li>
			        <img src="https://live.staticflickr.com/4057/4525335981_e6491dde02_z.jpg"> <!-- random image -->
			        <div class="caption center-align">
			          <h3>Esta es una venta de Campo!!</h3>
			          <h5 class="light grey-text text-lighten-3">Lugar de la tierra Valenciana</h5>
			        </div>
			      </li>
			      <li>
			        <img src="https://farm6.staticflickr.com/5233/14563455255_ae5ee99bf8_o.jpg"> <!-- random image -->
			        <div class="caption left-align">
			          <h3>Sin conservantes ni aromas</h3>
			          <h5 class="light grey-text text-lighten-3">Agricultura de la Comunidad Valenciana</h5>
			        </div>
			      </li>
			      <li>
			        <img src="http://upload.wikimedia.org/wikipedia/commons/e/ea/Alm%C3%A0ssera._Horta.JPG"> <!-- random image -->
			        <div class="caption right-align">
			          <h3>Una comunidad agricula</h3>
			          <h5 class="light grey-text text-lighten-3">Ayudar a un bien común para todos</h5>
			        </div>
			      </li>
			      <li>
			        <img src="https://www.hermeneus.es/Files/Images/PPAs/2015/10/6ebfa68c-d540-419b-a8ba-09fd82bdeb68/3b772553-846d-43ac-9612-0a23f729694b.png"> <!-- random image -->
			        <div class="caption center-align">
			          <h3>Nunca más derroche</h3>
			          <h5 class="light grey-text text-lighten-3">Sobra alimento, hay que repartirlo mejor</h5>
			        </div>
			      </li>
			    </ul>
  		  </div>
  		  </div>
           <!--   <div class="page-content">
            HOLA, ESTE ES EL ESPACIO DE PRESENTACION DE BANCALET VIRTUAL, ACTUALMENTE ESTA EN CONSTRUCCION
            <div class="carousel">
				<a class="carousel-item" href="#one!"><img width="640"  src="https://img.europapress.es/fotoweb/fotonoticia_20170630153549_640.jpg"></a>
				<a class="carousel-item" href="#two!"><img src="https://valenciaenbici.org/wp-content/uploads/2016/01/DSCN3311-copia.jpg"></a>
				<a class="carousel-item" href="#three!"><img src="https://live.staticflickr.com/4057/4525335981_e6491dde02_z.jpg"></a>
				<a class="carousel-item" href="#four!"><img src="https://www.hortaviva.net/img/foto4.JPG"></a>
				<a class="carousel-item" href="#five!"><img src="https://img.europapress.es/fotoweb/fotonoticia_20180403172928_640.jpg"></a>
		</div>
            
			</div>-->
        </div>
    </div>
    <!-- end page content -->
	</div>

    <!-- start footer -->
    <jsp:include page="/WEB-INF/jsp/piepagina.jsp"/>
    <!-- end footer -->

    <!-- start js include path -->
   
  
    <!-- Common js-->
    <script src="<c:url value="/resources/js/app.js"/>"></script>
    <script src="<c:url value="/resources/js/layout.js"/>"></script>
    <script src="<c:url value="/resources/js/materialize.min.js"/>"></script>
    <!-- Material -->
	<script src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
    <!-- end js include path -->
</body>
</html>