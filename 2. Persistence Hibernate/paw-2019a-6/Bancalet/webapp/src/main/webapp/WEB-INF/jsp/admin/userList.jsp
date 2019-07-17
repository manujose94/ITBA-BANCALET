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
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700"/>" rel="stylesheet"
          type="text/css"/>
      <!-- icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- Material Design Lite CSS -->
<link rel="stylesheet" href="<c:url value="/resources/material-design-lite/material.min.css"/>" />
    <!--bootstrap -->
    <link href="<c:url value="/webjars/bootstrap/4.0.0/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
    <!-- Material Design Lite CSS -->
    <link rel="stylesheet" href="<c:url value="/webjars/material-design-lite/1.1.0/material.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/material_style.css"/>"/>
    <!-- Template Styles -->
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/plugins.min.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/responsive.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/bancalet/bancalet.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
<link rel="shortcut icon" type="image/x-icon"
	href="<c:url value="/resources/img/bancalet/appicon.ico"/>" />
  <!-- Slider con Jquery -->
  	  <link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"  rel = "stylesheet">
      <script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
      <script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
</head>
<body
	class="page-container page-header-fixed page-content-white page-md header-white logo-dark">

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
										<spring:message code="users.lista_label"
											arguments="${total_users}" />
									</header>
								</div>
								<div class="card-body">
									<div
										class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
										<div class="search">
											 <form action="">
											  	<input type="search" name="item_nombre" value="${item_nombre}" class="form-control" placeholder="
												<spring:message code="item.search_name_form_label"/>">
											<br>
											<p>
												<b><spring:message code="users.tipo_form_label" /></b>
												<select name="user_roles" >
													<option value="${user_roles}" selected><spring:message code="user.role_${user_roles}"/></option>
													<c:if test="${user_roles != 0}">
														<option value="0"><spring:message code="user.role_0"/></option>
													</c:if>
													<c:if test="${user_roles != 1}">
														<option value="1"><spring:message code="user.role_1"/></option>
													</c:if>
													<c:if test="${user_roles != 2}">
													    <option value="2"><spring:message code="user.role_2"/></option>
													</c:if>
											  	</select>
											</p>
											<p>
												<b><spring:message code="users.estado_form_label" /></b>
												<select name="users_estado" >
													<option value="${users_estado}" selected><spring:message code="user.estado_${users_estado}"/></option>
													<c:if test="${users_estado != 0}">
														<option value="0"><spring:message code="user.estado_0"/></option>
													</c:if>
													<c:if test="${users_estado != 1}">
														<option value="1"><spring:message code="user.estado_1"/></option>
													</c:if>
													<c:if test="${users_estado != 2}">
													    <option value="2"><spring:message code="user.estado_2"/></option>
													</c:if>
											  	</select>
											</p>
											<button type="submit" class="btn btn-primary search-btn">
												<spring:message code="item.search_form_button"/>
											</button>
											
											<p>
											<br>
												<b><spring:message code="item.filtro" /></b>
													<c:if test="${user_roles == 1}">
														<spring:message code="user.role_1" />
													</c:if>
													<c:if test="${user_roles == 2}">
														<spring:message code="user.role_2" />
													</c:if>
													<c:if test="${users_estado == 0}">
														<spring:message code="user.estado_0" />
													</c:if>
													<c:if test="${users_estado == 1}">
														<spring:message code="user.estado_1" />
													</c:if>
													${item_nombre}
											</p>
											</form>
							     	 	</div>
									<div class="col-lg-15 p-t-20">

											<div class="table-responsive">

												<table class="table table-striped custom-table">
													<thead class="text-left">
														<tr>
															<th></th>
															<th><spring:message code="user.username_form_label" /></th>
															<th><spring:message code="user.id_form_label" /></th>
															<th><spring:message code="user.rol_form_label" /></th>
															<th></th>
														</tr>
													</thead>
													<tbody>
													<c:if test="${not empty users}">
														<c:forEach items="${users}" var="user">
															<tr>
															<td><c:if test="${user.urlImg != null && user.urlImg != ''}">
																<img src="<c:url value="data:image/jpg;base64,${user.urlImg}"/>"  alt="Avatar" class="avatar-userimg" >
															</c:if>	
															<c:if test="${user.urlImg == null || user.urlImg == '' }">
																<c:if test="${user.role == 'ADMIN' }">
																	<img src="<c:url value="/resources/img/admin/admin${user.numImg}.png"/>"  class="avatar" style="width:100px;height:100px;" >
																</c:if>
																<c:if test="${user.role == 'USER' }">
																	<img src="<c:url value="/resources/img/avatar/avatar${user.numImg}.png"/>"  class="avatar" style="width:100px;height:100px;" >
																</c:if>
															</c:if>	</td>	
																<td><c:out value="${user.username}" /></td>
																<td><c:out value="${user.userId}" /></td>
																<td><c:out value="${user.role}" /></td>
																<td>
																	<div class="col-lg-12 text-center">
																		<a href="<c:url value="/admin/${user.userId}"/>"
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
