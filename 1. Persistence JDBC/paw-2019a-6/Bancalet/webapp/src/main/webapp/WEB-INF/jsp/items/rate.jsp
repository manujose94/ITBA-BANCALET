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
<!-- icons -->
<link
	href="<c:url value="/webjars/font-awesome/4.7.0/css/font-awesome.min.css"/>"
	rel="stylesheet" type="text/css" />
<!--bootstrap -->
<link
	href="<c:url value="/webjars/bootstrap/4.0.0/css/bootstrap.min.css"/>"
	rel="stylesheet" type="text/css" />
<!-- Material Design Lite CSS -->
<link rel="stylesheet"
	href="<c:url value="/webjars/material-design-lite/1.1.0/material.min.css"/>" />
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
</head>
<body
	class="page-header-fixed page-content-white page-md header-white logo-dark">
	<div class="page-wrapper">
		<!-- start header -->
		<jsp:include page="/WEB-INF/jsp/encabezadopagina.jsp" />
		<!-- end header -->

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
										
										<!-- User role -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
												
												<p>
													<spring:message code="rate.stars" />
													<form:select type="text" path="estrellas" name="estrellas" cssClass="mdl-textfield__input" >
														<option value="0">0</option>
													    <option value="1">1</option>
													    <option value="2">2</option>
													    <option value="3">3</option>
													    <option value="4">4</option>
													    <option value="5">5</option>
												  	</form:select>
												 </p>
												<form:errors path="estrellas" cssClass="formError" element="p" />
											
											</div>
										</div>
										
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
			<!-- end page content -->
		</div>
		<!-- end page container -->

		<!-- start footer -->
		<jsp:include page="/WEB-INF/jsp/piepagina.jsp" />
		<!-- end footer -->
	</div>


	<!-- start js include path -->
	<script src="<c:url value="/webjars/jquery/3.0.0/jquery.min.js"/>"></script>
	<script
		src="<c:url value="/webjars/jQuery-slimScroll/1.3.8/jquery.slimscroll.min.js"/>"></script>
	<!-- bootstrap -->
	<script src="<c:url value="/webjars/bootstrap/4.0.0/js/bootstrap.js"/>"></script>
	<!-- Common js-->
	<script src="<c:url value="/resources/js/app.js"/>"></script>
	<script src="<c:url value="/resources/js/layout.js"/>"></script>
	<!-- Material -->
	<script
		src="<c:url value="/webjars/material-design-lite/1.1.0/material.min.js"/>"></script>
</body>
</html>
