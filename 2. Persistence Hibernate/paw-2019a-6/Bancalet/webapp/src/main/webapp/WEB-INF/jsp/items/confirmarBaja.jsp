<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<!-- google font -->
<link
	href="<c:url value="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700"/>"
	rel="stylesheet" type="text/css" />
<!-- icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<!--bootstrap -->
<link
	href="<c:url value="/webjars/bootstrap/4.0.0/css/bootstrap.min.css"/>"
	rel="stylesheet" type="text/css" />
	
<!-- Material Design Lite CSS -->
<link rel="stylesheet" href="<c:url value="/resources/material-design-lite/material.min.css"/>" />

<!--Import materialize.css-->
 <link type="text/css" rel="stylesheet" href="/resources/css/materialize.min.css"  media="screen,projection"/>
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
<script src="<c:url value="/resources/js/confirmJs.js"/>"></script>
</head>
<body
	class="page-header-fixed page-content-white page-md header-white logo-dark" data-customvalueone="${item.estado}" >

	<div class="page-wrapper">
		<!-- start header -->
		<jsp:include page="/WEB-INF/jsp/encabezadopagina.jsp" />
		<!-- end header -->
		<jsp:include page="/WEB-INF/jsp/piepagina.jsp" />
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
										<spring:message code="item.greeting"
											arguments=": ${item.name}" />
										
											
									</header>
								</div>
								<div class="card-body">
									<div class="col-lg-15 p-t-20">
										<div
											class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

											<div class="table-responsive">
												<table class="table table-striped custom-table">
													<thead class="text-left">
														<tr>														
														<th><img src="<c:url value="/resources/img/type_food/${item.tipo}.png"/>" class="avatar" style="width:100px;height:100px;" >
														<th><spring:message code="item.name_form_label" /></th>
															<td><c:out value="${item.name}" /></td>														                                             											
														</tr>
													</thead>
													<tbody>
													<tr>
													
													</tbody>
												</table>
											</div>
										</div>
								
											<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.3.5/sweetalert2.all.min.js"></script>
											 <c:url value="/items/${item.itemId}/baja" var="postPathBaja" />
											 
											<form   method="post" action="${postPathBaja}">
												
													<select name="comprador">
													
														<c:forEach items="${users}" var="user">
									    						<option value="${user.userId}">${user.username}</option>
														</c:forEach>
													
														<option value="-1"><spring:message code="contact.outside_user" /></option>
														
													</select> 	
        										<input type="submit" class="btn btn-warning" id="baja" name="Baja" value="<spring:message
													code="item.baja_form_label" />" /><br>																
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

	<!-- start js include path -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.3.5/sweetalert2.all.min.js"></script>
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
	<!-- end js include path -->
</body>
</html>
