<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 
<fmt:setBundle basename="messages" />
<fmt:message key="message.password" var="noPass" />
<fmt:message key="message.username" var="noUser" />

<html>
<head>



<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<title>Bancalet</title>
<!-- google font -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<!-- icons -->

<!-- Material Design Lite CSS -->

<link rel="stylesheet"
	href="<c:url value="/resources/material-design-lite/material.min.css"/>" />
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
<script type="text/javascript">
function validate() {
    if (document.f.username.value == "" && document.f.password.value == "") {
        alert("${noUser} and ${noPass}");
    document.f.username.focus();
    return false;
    }
    if (document.f.username.value == "") {
    alert("${noUser}");
    document.f.username.focus();
    return false;
     }
     if (document.f.password.value == "") {
    alert("${noPass}");
    document.f.password.focus();
    return false;
     }
}
</script>
<body 
	class="page-header-fixed page-content-white page-md header-white logo-dark">

	<div class="page-wrapper">
		<!-- start header -->
		<jsp:include page="/WEB-INF/jsp/encabezadopagina.jsp" />
		<!-- start page container -->
		<div class="page-container">
			<jsp:include page="/WEB-INF/jsp/inicio/barralateralNologin.jsp" />
		<!-- end header -->
		
		<!-- start page content -->
		<div class="page-content-wrapper">
			<div class="page-content-register-user">
				<div class="row justify-content-center">
					<div class="col-sm-12">
						<div class="card-box card-box-user mx-auto mt-3">
							<div class="card-head">
								<header>
									<spring:message code="login.header" />
								</header>
							</div>

							<div class="card-body">
								<c:url value="/users/login" var="postPath" />
								<form:form modelAttribute="loginForm" action="${postPath}"
									method="post">

									<div class="card-body">

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

										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="password" cssClass="mdl-textfield__label">
													<spring:message code="user.password_form_label" />
												</form:label>
												<form:input type="password" path="password"
													cssClass="mdl-textfield__input" />
												<form:errors path="password" cssClass="formError" element="p">
													<spring:message code="login.password_incorrect" />
													</form:errors>
											</div>
										</div>
										<div class="clearfix" style="text-align: center">
						                	<label class="pull-left checkbox-inline" style="margin-left: 3px"><input type="checkbox" name="rememberme"><spring:message code="user.ask_remember_me"/></label>
						               	</div>
										<div class="col-lg-12 p-t-20 text-center">
											<button type="submit"
												class="btn btn-default rebeccapurple-color ">
												<spring:message code="login.button_login" />
											</button>
										</div>
									</div>
								</form:form>
								<c:url value="/users/register" var="registrarUrl" />
								<form action="${registrarUrl}" method="post"
									enctype="application/x-www-form-urlencoded">
									<div class="col-lg-12 p-t-20 text-center">
										<input type="submit"
											value="<spring:message code="login.button_registrar"/>"
											class="btn btn-default rebeccapurple-color ">
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				</div>
			</div>
		</div>
		<!-- end page content -->
		
		<!-- start footer -->
		<jsp:include page="/WEB-INF/jsp/piepagina.jsp" />
		<!-- end footer -->
	</div>
	<!-- Material -->
	<script
		src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
</body>
</html>