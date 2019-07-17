<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java"%>
<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<meta content="width=device-width, initial-scale=1" name="viewport" />
<title>Bancalet</title>
<!-- google font -->
<link
	href="<c:url value="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700"/>"
	rel="stylesheet" type="text/css" />
<!--Import Google Icon Font-->
      <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<!-- Material Design Lite CSS -->
<link rel="stylesheet"
	href="<c:url value="/resources/material-design-lite/material.min.css"/>" />
<!--Import materialize.css-->
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/materialize.min.css"/>"  media="screen,projection"/>

<link rel="stylesheet"
	href="<c:url value="/resources/css/material_style.css"/>" />
<!-- Template Styles -->
<link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet"
	type="text/css" />
<link href="<c:url value="/resources/css/plugins.min.css"/>"
	rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/responsive.css"/>"
	rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/bancalet/bancalet.css"/>"
	rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/estrellas.css"/>"
	rel="stylesheet" type="text/css">
<!-- Favicon -->
<link rel="shortcut icon" type="image/x-icon"
	href="<c:url value="/resources/img/bancalet/appicon.ico"/>" />
	
<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>      
<script>
	$(document).ready(function(){
	    $('select').formSelect();
	  });	       		   
</script>
<body
	class="page-header-fixed page-content-white page-md header-white logo-dark">
	<div class="page-wrapper">
		<!-- start header -->
		<jsp:include page="/WEB-INF/jsp/encabezadopagina.jsp" />
		<!-- end header -->
	<jsp:include page="/WEB-INF/jsp/piepagina.jsp" />
		<!-- start page container -->
		<div class="page-container">

			<!-- start page content -->
			<div class="page-content-wrapper">
				<div class="page-content-register-user" >
					<div class="row justify-content-center">
						<div class="col-sm-12">
							<div class="card-box card-box-user mx-auto mt-3">
								<div class="card-head">
									<header>
										<spring:message code="rate.valoracion" arguments="${vendedor.username}" />
									</header>
								</div>
								<div class="card-body">
									<c:url value="/items/${id}/rateSucces/" var="postPath" />
									<form:form modelAttribute="ItemRateForm" action="${postPath}"
										method="post">
										
										<!-- User Valoracion -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
												<form:label type="text" path="valoracion"
													cssClass="mdl-textfield__label">
													<spring:message code="rate.valoracion" arguments="${vendedor.username}" />
												</form:label>
												<form:input type="paragraph_text" path="valoracion"
													cssClass="mdl-textfield__input" />
												<form:errors path="valoracion" cssClass="formError"
													element="p" />
											</div>
										</div>
										
										<!-- User rate -->
										<div class="col-lg-12 p-t-20">
											
											<!--<form name="estrellas">--->
												  <p class="clasificacion">
												    <input class="clasificacion" id="radio1" type="radio" name="estrellas" value="5"><!--
												    --><label class="clasificacion" for="radio1">&#9733;</label><!--
												    --><input class="clasificacion" id="radio2" type="radio" name="estrellas" value="4"><!--
												    --><label class="clasificacion" for="radio2">&#9733;</label><!--
												    --><input class="clasificacion" id="radio3" type="radio" name="estrellas" value="3"><!--
												    --><label class="clasificacion" for="radio3">&#9733;</label><!--
												    --><input class="clasificacion" id="radio4" type="radio" name="estrellas" value="2"><!--
												    --><label class="clasificacion" for="radio4">&#9733;</label><!--
												    --><input class="clasificacion" id="radio5" type="radio" name="estrellas" value="1"><!--
												    --><label class="clasificacion" for="radio5">&#9733;</label>
												  </p>
												<!--</form>-->
												<form:errors path="estrellas" cssClass="formError" element="p" />
										</div>

										<div class="col-lg-12 p-t-20 text-center">
											<button type="submit"
												class="btn btn-default rebeccapurple-color ">
												<spring:message code="contact.button" />
											</button>
										</div>
									</form:form>
									<a href="<c:url value="/users/miscompras"/>"class="btn btn-default rebeccapurple-color ">
	                                	<spring:message code="cancel_button"/></a><br>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		<!-- end page container -->
	</div>
</div>
	
	<!-- Common js-->
	<script src="<c:url value="/resources/js/app.js"/>"></script>
	<script src="<c:url value="/resources/js/layout.js"/>"></script>
	<script src="<c:url value="/resources/js/materialize.min.js"/>"></script>
	<!-- Material -->
	<script
		src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
	<!--JavaScript at end of body for optimized loading-->
     
</body>
</html>

