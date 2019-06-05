<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
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
		<jsp:include page="/WEB-INF/jsp/inicio/barralateralNologin.jsp" />
			<!-- start page content -->
			<div class="page-content-wrapper">
				<div class="page-content">
					<div class="row">

						<div class="col-sm-12">
						<c:choose>
							    <c:when test="${item.estado == 'BAJA'}">
							        <c:set var="varclass" value=""/>
							    </c:when>
							    <c:otherwise>
							        <c:set var="varclass" value="card-box"/>
							    </c:otherwise>
							</c:choose>

								<div class="${varclass}">
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

													</tr>
														<tr>
														<th><spring:message code="item.tipo_form_label" /></th>
															<td><spring:message code="item.tipo${item.tipo}"/></td>
														<tr>
															<th><spring:message code="item.description_form_label" /></th>
															<td> <c:out value="${item.description}" /></td>
														</tr>
														<tr>
														<th><spring:message code="item.price_form_label" /></th>
														<td><c:out value="${item.price}" /></td>
														</tr>
														<tr>
														<th><spring:message code="item.fecha_registro_form_label" /></th>
														<td><c:out value="${item.fecha_publicacion}" /></td>
														</tr>
														<tr>
														<th><spring:message code="item.fecha_cad_form_label" /></th>
														<td><c:out value="${item.fecha_caducidad}" /></td>
														</tr>
														<tr>
														<th><spring:message code="item.state_form_label" />

														</th>
														<c:if test="${item.estado == 'ALTA'}">
														<td><i class="large material-icons">check_circle</i></td>
														</c:if>
														<c:if test="${item.estado == 'BAJA'}">
														<td><i class="large material-icons">do_not_disturb_on</i></td>
														</c:if>
														</tr>
														<tr>
														<th><spring:message code="item.seller_form_label" /></th>
														<td><c:out value="${seller.username}" /> </td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>

										<div class="col-lg-12 text-center">
											<div class="col-lg-12 text-center">
												<a href="<c:url value="/list"/>"
													class="btn btn-default rebeccapurple-color"><spring:message
														code="item.return_to_items_list" /></a><br>
											</div>
									</div>
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
	<!-- start footer -->
	<jsp:include page="/WEB-INF/jsp/piepagina.jsp" />
	<!-- end footer -->

	<!-- start js include path -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.3.5/sweetalert2.all.min.js"></script>

	<!-- bootstrap -->

	<!-- Common js-->
	<script src="<c:url value="/resources/js/app.js"/>"></script>
	<script src="<c:url value="/resources/js/layout.js"/>"></script>
	<!-- Material -->

<script src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
	<!-- end js include path -->
</body>
</html>
