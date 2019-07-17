<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
	class="page-header-fixed page-content-white page-md header-white logo-dark">
	<div class="page-wrapper">
		<!-- start header -->
		<jsp:include page="/WEB-INF/jsp/encabezadopagina.jsp" />
		<!-- end header -->

		<!-- start page container -->
		<div class="dishes-page-container">

			<!-- start page content -->
			<div class="page-content-wrapper">
				<div class="dishes-page-content">
					<div class="row">
						<div class="col-md-12">
							<div class="row">
								<div class="col-md-10 mx-auto mt-5">
									<div class="card card-topline-red text-center">
										<div class="card-head">
											<header>${title}</header>
										</div>
										<div class="card-body">
											<div class="table-responsive">${body}</div>

											<div class="text-center mt-3">
												<form action="<c:url value="/"/>">
													<button type="submit" class="btn rebeccapurple-color">
														<i class="fa fa-undo"></i>
														<spring:message code="bancalet.app_name" />
													</button>
												</form>
											</div>
										</div>
									</div>
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
	<!-- end js include path -->

</body>
</html>
