<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta charset="UTF-8" />
<title>Bancalet Virtual</title>
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
	rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/bancalet/bancalet.css"/>"
	rel="stylesheet" type="text/css">
<!-- Favicon -->
<link rel="shortcut icon" type="image/x-icon"
	href="<c:url value="/resources/img/bancalet/appicon.ico"/>" />
</head>

<body
	class="page-header-fixed sidemenu-closed-hidelogo page-content-white page-md header-white
             dark-sidebar-color logo-dark">
	<div class="page-wrapper">
		<!-- start header -->
		<jsp:include page="/WEB-INF/jsp/encabezadopagina.jsp" />
		<!-- end header -->

		<!-- start page container -->
		<div class="page-container">
			<!-- start sidebar menu -->
			<jsp:include page="/WEB-INF/jsp/barralateral.jsp" />
			<!-- end sidebar menu -->

			<!-- start page content -->
			<div class="page-content-wrapper">
				<div class="page-content">
					<!-- start widget -->
					<div class="row">
						<div class="col-lg-6 col-md-6 col-sm-12 col-12">
							<div class="card card-box">
								<div class="card-head">
									<header>
										<spring:message code="admin.header" />
									</header>
								</div>
								<div class="card-body">
									<div class="col-lg-15 p-t-20">
										<div
											class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
											<h5>
												<spring:message code="admin.id" arguments="${admin.id}" />
											</h5>
											<h5>
												<spring:message code="admin.username" arguments="${admin.username}" />
											</h5>
										</div>
									</div>
									<c:url value="/admin/editPass/${admin.id}" var="editPassUrl" />
									<form action="${editPassUrl}" method="post"
										enctype="application/x-www-form-urlencoded">
										<div class="col-lg-12 p-t-20 text-center">
											<input type="submit"
												value="<spring:message code="user.button_changepass"/>"
												class="btn btn-default rebeccapurple-color ">
										</div>
									</form>
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

	<!-- Material -->
<script src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
	<!-- Common js-->
	<script src="<c:url value="/resources/js/app.js"/>"></script>
	<script src="<c:url value="/resources/js/layout.js"/>"></script>
	<!-- end js include path -->
</body>
</html>
