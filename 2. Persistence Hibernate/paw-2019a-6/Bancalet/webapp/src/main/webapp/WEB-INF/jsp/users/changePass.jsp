<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
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
		<jsp:include page="/WEB-INF/jsp/piepagina.jsp" />
		<!-- start page container -->
		<div class="page-container">

			<!-- start page content -->
			<div class="page-content-wrapper">
				<div class="page-content-register-user">
					<div class="row justify-content-center">
						<div class="col-sm-12">
							<div class="card-box card-box-user mx-auto mt-3">
								<div class="card-head">
									<header>
										<spring:message code="user.change_pass_header" />
									</header>
								</div>
								<div class="card-body">
								<sec:authorize access="hasRole('ROLE_ADMIN')">
									<c:url value="/admin/editPassAccept/${userId}" var="postPath" />
									<form:form modelAttribute="UserEditPassForm" action="${postPath}"
										method="post">
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
										<div class="col-lg-12 p-t-20 text-center">
											<button type="submit"
												class="btn btn-default rebeccapurple-color ">
												<spring:message code="accept_button" />
											</button>
										</div>

									</form:form>
								</sec:authorize>
								<sec:authorize access="hasRole('ROLE_USER')">
									<c:url value="/users/editPassAccept" var="postPath" />
									<form:form modelAttribute="UserEditPassForm" action="${postPath}"
										method="post">
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
										<div class="col-lg-12 p-t-20 text-center">
											<button type="submit"
												class="btn btn-default rebeccapurple-color ">
												<spring:message code="accept_button" />
											</button>
										</div>
									</form:form>
									</sec:authorize>
									<sec:authorize access="hasRole('ROLE_USER')">
	                                	<a href="<c:url value="/users/returnIndex"/>"class="btn btn-default rebeccapurple-color ">
	                                	<spring:message code="cancel_button"/></a><br>
		                   			</sec:authorize>
									<sec:authorize access="hasRole('ROLE_ADMIN')">
	                                	<a href="<c:url value="/admin/${userId}"/>"class="btn btn-default rebeccapurple-color ">
	                                	<spring:message code="cancel_button"/></a><br>
		                   			</sec:authorize>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end page content -->
		</div>
		<!-- end page container -->

	</div>

	<!-- Common js-->
	<script src="<c:url value="/resources/js/app.js"/>"></script>
	<script src="<c:url value="/resources/js/layout.js"/>"></script>
	<!-- Material -->
<script src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
</body>
</html>
