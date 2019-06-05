<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
<!-- google font -->
<link
	href="<c:url value="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700"/>"
	rel="stylesheet" type="text/css" />
<!-- icons -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">>
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
		<!-- start header -->
		<jsp:include page="/WEB-INF/jsp/encabezadopagina.jsp" />
		<!-- end header -->

		<!-- start page container -->
		<div class="page-container">
			<jsp:include page="/WEB-INF/jsp/barralateral.jsp" />

			<!-- start page content -->
			<div class="page-content-wrapper">
				<div class="page-content">
					<div class="row">
						<div class="col-sm-12">
							<div class="card-box">

								<div class="card-head">
									<header>
										<spring:message code="user.greeting"
											arguments="${user.username}" />
									</header>
								</div>

								<div class="card-body">
									<div class="col-lg-15 p-t-20">
										<div
											class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
											<h5>
												<spring:message code="user.id" arguments="${user.id}" />
											</h5>
											<h5>
												<spring:message code="user.email" arguments="${user.email}" />
											</h5>
											<h5>
												<spring:message code="user.telf" arguments="${user.telf}" />
											</h5>
											<h5>
												<spring:message code="user.city" arguments="${user.city}" />
											</h5>
											<h5>
												<spring:message code="user.address"
													arguments="${user.direccion}" />
											</h5>
											<h5>
												<spring:message code="user.code" arguments="${user.code}" />
											</h5>
											<h5>
												<spring:message code="user.country"
													arguments="${user.country}" />
											</h5>

										</div>
									</div>
									<sec:authorize access="hasRole('ROLE_USER')">
	                                	<a href="<c:url value="/users/editUsr"/>"class="btn btn-default rebeccapurple-color ">
	                                	<spring:message code="user.button_edit"/></a><br>
	                                	<br></br>
		                   			</sec:authorize>

									<sec:authorize access="hasRole('ROLE_ADMIN')">
	                                	<a href="<c:url value="/admin/editUsr/${user.id}"/>"class="btn btn-default rebeccapurple-color ">
	                                	<spring:message code="user.button_edit"/></a><br>
		                   			</sec:authorize>

									<sec:authorize access="hasRole('ROLE_USER')">
	                                	<a href="<c:url value="/users/editPass"/>"class="btn btn-default rebeccapurple-color ">
	                                	<spring:message code="user.button_changepass"/></a><br>
		                   			</sec:authorize>

									<br></br>
									<sec:authorize access="hasRole('ROLE_ADMIN')">
	                                	<a href="<c:url value="/admin/editPass/${user.id}"/>"class="btn btn-default rebeccapurple-color ">
	                                	<spring:message code="user.button_changepass"/></a><br>
		                   			</sec:authorize>

									<br></br>
									<sec:authorize access="hasRole('ROLE_ADMIN')">
	                                	<a href="<c:url value="/admin/ventas/${user.id}"/>"class="btn btn-default rebeccapurple-color ">
	                                	<spring:message code="user.button_ventas"/></a><br>
		                   			</sec:authorize>

		                   			<br></br>
		                   			<sec:authorize access="hasRole('ROLE_ADMIN')">
	                                	<a href="<c:url value="/admin/compras/${user.id}"/>"class="btn btn-default rebeccapurple-color ">
	                                	<spring:message code="user.button_compras"/></a><br>
		                   			</sec:authorize>
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

	<!-- start js include path -->
	<!-- bootstrap -->
<script src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
</body>
</html>
