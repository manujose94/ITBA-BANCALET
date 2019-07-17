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
				<div class="page-content">
					<div class="row">
						<div class="col-sm-12">
							<div class="card-box">
								<div class="card-head">
									<header>
										<spring:message code="issues.lista_label"
											arguments="${total_issues}" />

									</header>
								</div>
								<div class="card-body">
									<div
										class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
										<div class="search">
											 <form action="">
											  	<input type="search" name="issue_nombre" class="form-control" placeholder="
												<spring:message code="issue.search_name_form_label"/>">

											<p>
												<spring:message code="issue.tipo_form_label" />
												<select name="issue_tipo" >
													<option value="0"><spring:message code="issue.select_all"/></option>
													<option value="1"><spring:message code="issue.select_inside"/></option>
												    <option value="2"><spring:message code="issue.select_outside"/></option>
											  	</select>
											</p>
											<button type="submit" class="btn btn-primary search-btn">
												<spring:message code="issue.search_form_label"/>

											</button>
											<p>
												<spring:message code="item.filtro" />
													<c:if test="${issue_tipo == 1}">
														<spring:message code="issue.select_inside" />
													</c:if>
													<c:if test="${issue_tipo == 2}">
														<spring:message code="issue.select_outside" />
													</c:if>
													${issue_nombre}
											</p>
											</form>
							     	 	</div>
									<div class="col-lg-15 p-t-20">

											<div class="table-responsive">

												<table class="table table-striped custom-table">
													<thead class="text-left">
														<tr>
															<th><spring:message code="issue.name_label" /></th>
															<th><spring:message code="issue.asunto_label" /></th>
															<th><spring:message code="issue.fecha_cont_label" /></th>
														</tr>
													</thead>
													<tbody>
													<c:if test="${not empty issues}">
														<c:forEach items="${issues}" var="issue">
															<tr>
																<td><c:out value="${issue.name}" /></td>
																<td><c:out value="${issue.asunto}" /></td>
																<td><c:out value="${issue.fecha_contacto}" /></td>
																<td>
																	<div class="col-lg-12 text-center">
																		<a href="<c:url value="/admin/${issue.idayuda}"/>"
																			class="btn btn-default rebeccapurple-color"><spring:message
																				code="item.view" /></a><br>
																	</div>
																</td>
															</tr>
														</c:forEach>
														</c:if>
													</tbody>
												</table>

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

	<!-- bootstrap -->

	<!-- Common js-->
	<script src="<c:url value="/resources/js/app.js"/>"></script>
	<script src="<c:url value="/resources/js/layout.js"/>"></script>
	<!-- Material -->
<script src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
	<!-- end js include path -->
</body>
</html>
