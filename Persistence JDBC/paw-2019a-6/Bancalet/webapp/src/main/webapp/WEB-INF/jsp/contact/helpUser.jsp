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

<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<!--bootstrap -->
<!-- Material Design Lite CSS -->
<link rel="stylesheet" href="<c:url value="/resources/material-design-lite/material.min.css"/>" />
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
			<jsp:include page="/WEB-INF/jsp/barralateral.jsp" />
			<!-- start page content -->
			<div class="page-content-wrapper">
				<div class="page-content-register-user" >
					<div class="row justify-content-center">
						<div class="col-sm-12">
							<div class="card-box card-box-user mx-auto mt-3">
								<div class="card-head">
									<header>
										<spring:message code="index.contact_header" />
									</header>
								</div>
								<div class="card-body">
									<c:url value="/users/contactSucces" var="postPath" />
									<form:form modelAttribute="ContactAdminForm" action="${postPath}"
										method="post">

										<!-- User subject -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
												<form:label type="text" path="subject"
													cssClass="mdl-textfield__label">
													<spring:message code="index.asunto_user" />
												</form:label>
												<form:input type="paragraph_text" path="subject"
													cssClass="mdl-textfield__input" />
												<form:errors path="subject" cssClass="formError"
													element="p" />
											</div>
										</div>

										<!-- User mensaje -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
												<form:label type="text" path="mensaje"
													cssClass="mdl-textfield__label">
													<spring:message code="index.mensaje_user" />
												</form:label>
												<form:input type="paragraph_text" path="mensaje"
													cssClass="mdl-textfield__input" />
												<form:errors path="mensaje" cssClass="formError"
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
									<a href="<c:url value="/"/>"class="btn btn-default rebeccapurple-color ">
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

	<!-- bootstrap -->

	<!-- Common js-->
	<script src="<c:url value="/resources/js/app.js"/>"></script>
	<script src="<c:url value="/resources/js/layout.js"/>"></script>
	<!-- Material -->
<script src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
</body>
</html>
