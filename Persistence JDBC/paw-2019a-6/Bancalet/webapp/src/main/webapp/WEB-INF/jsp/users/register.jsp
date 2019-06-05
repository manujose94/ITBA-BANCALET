<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
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
</head>
<body
	class="page-header-fixed page-content-white page-md header-white logo-dark">
	<div class="page-wrapper">
		<!-- start header -->		
		<jsp:include page="/WEB-INF/jsp/encabezadopagina.jsp" />
		<!-- end header -->

		<!-- start page container -->
		<div class="page-container">
		<jsp:include page="/WEB-INF/jsp/inicio/barralateralNologin.jsp" />
			<!-- start page content -->
			
			<div class="page-content-wrapper">
			
				<div class="page-content-register-user" >
					<div class="row justify-content-center">
						<div class="col-sm-12">
							<div class="card-box card-box-user mx-auto mt-3">
								<div class="card-head">
									<header>
										<spring:message code="user.register_header" />
									</header>
								</div>
								<div class="card-body">
								<div class="input-field col s12 m6">
							    <select class="icons">
							      <option value="" disabled selected>Eligue tu avatar</option>
							      <option value="" data-icon="<c:url value="/resources/img/avatar/boy.png"/>">Boy</option>
							      <option value="" data-icon="<c:url value="/resources/img/avatar/boy-1.png"/>">Boy 1</option>
							      <option value="" data-icon="<c:url value="/resources/img/avatar/girl.png"/>">Girl</option>
							    </select>
							    <label>Images in select</label>
							 
									<c:url value="/users/create" var="postPath" />
									<form:form modelAttribute="registerForm" action="${postPath}"
										method="post">    
										<!-- User username -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
												<form:label type="text" path="username"
													cssClass="mdl-textfield__label">
													<spring:message code="user.username_form_label" />
												</form:label>
												<form:input type="text" path="username"
													cssClass="mdl-textfield__input" />
												<form:errors path="username" cssClass="formError"
													element="p" />
											</div>
										</div>
										
										<!-- User password -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="password" cssClass="mdl-textfield__label">
													<spring:message code="user.password_form_label" />
												</form:label>
												<form:input type="password" path="password"
													cssClass="mdl-textfield__input" />
												<form:errors path="password" cssClass="formError"
													element="p" />
											</div>
										</div>
										
										<!-- User repeatPassword -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="repeatPassword"
													cssClass="mdl-textfield__label">
													<spring:message code="user.repeat_password_form_label" />
												</form:label>
												<form:input type="password" path="repeatPassword"
													cssClass="mdl-textfield__input" />
												<form:errors path="repeatPassword" cssClass="formError"
													element="p" />
											</div>
										</div>

										<!-- User telf -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="telf" cssClass="mdl-textfield__label">
													<spring:message code="user.register_telf" />
												</form:label>
												<form:input type="text" path="telf"
													cssClass="mdl-textfield__input" />
												<form:errors path="telf" cssClass="formError" element="p" />
											</div>
										</div>
										
										<!-- User email -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="email" cssClass="mdl-textfield__label">
													<spring:message code="user.register_email" />
												</form:label>
												<form:input type="text" path="email"
													cssClass="mdl-textfield__input" />
												<form:errors path="email" cssClass="formError" element="p" />
											</div>
										</div>

										<!-- User city -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="city" cssClass="mdl-textfield__label">
													<spring:message code="user.register_city" />
												</form:label>
												<form:input type="text" path="city"
													cssClass="mdl-textfield__input" />
												<form:errors path="city" cssClass="formError" element="p" />
											</div>
										</div>
										
										<!-- User direccion -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="direccion" cssClass="mdl-textfield__label">
													<spring:message code="user.direccion" />
												</form:label>
												<form:input type="text" path="direccion"
													cssClass="mdl-textfield__input" />
												<form:errors path="direccion" cssClass="formError" element="p" />
											</div>
										</div>
										
										<!-- User code -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="code" cssClass="mdl-textfield__label">
													<spring:message code="user.register_code" />
												</form:label>
												<form:input type="text" path="code"
													cssClass="mdl-textfield__input" />
												<form:errors path="code" cssClass="formError" element="p" />
											</div>
										</div>
										
										<!-- User country -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="country" cssClass="mdl-textfield__label">
													<spring:message code="user.register_country" />
												</form:label>
												<form:input type="text" path="country"
													cssClass="mdl-textfield__input" />
												<form:errors path="country" cssClass="formError" element="p" />
											</div>
										</div>

										<div class="col-lg-12 p-t-20 text-center">
											<button type="submit"
												class="btn btn-default rebeccapurple-color ">
												<spring:message code="register.button_label" />
											</button>
										</div>
									</form:form>
									<a href="<c:url value="/index"/>"class="btn btn-default rebeccapurple-color ">
	                                	<spring:message code="cancel_button"/></a><br>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end page content -->
		</div>
	</div>
		<!-- end page container -->

		<!-- start footer -->
		<jsp:include page="/WEB-INF/jsp/piepagina.jsp" />
		<!-- end footer -->
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
