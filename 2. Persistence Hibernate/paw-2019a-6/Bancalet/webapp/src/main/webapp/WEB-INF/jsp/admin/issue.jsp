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

	<script type="text/javascript">

    function confirmFunction(form) {
    	event.preventDefault();
    	  swal({
    	    title: "<strong><spring:message code="issue.delete" /></strong>",
    	    text: "<spring:message code="issue.delete_message" />",
    	    showCancelButton: true,
    	    confirmButtonColor: '#f7505a',
    	    cancelButtonColor: '#f7505a',
    	    confirmButtonText: "<spring:message code="popup.confirm" />",
    	    cancelButtonText: "<spring:message code="popup.cancel" />"

    	  }).then(function(result) {
    		  if (result.value){
    			  form.submit();
    		  }
    	  })
    	}

    function confirmBajaFunction(form) {
    	event.preventDefault();
    	  swal({
    		  title: "<strong><spring:message code="issue.baja" /></strong>",
      	    text: "<spring:message code="issue.baja_message" />",
    	    showCancelButton: true,
    	    confirmButtonColor: '#ffd100',
    	    cancelButtonColor: '#ffd100',
    	    confirmButtonText: "<spring:message code="popup.confirm" />",
    	    cancelButtonText: "<spring:message code="popup.cancel" />"

    	  }).then(function(result) {
    		  if (result.value){
    			  form.submit();
    		  }
    	  })
    	}
	</script>
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
						<c:choose>
							    <c:when test="${issue.estado == 0}">
							        <c:set var="varclass" value=""/>
							    </c:when>
							    <c:otherwise>
							        <c:set var="varclass" value="card-box"/>
							    </c:otherwise>
							</c:choose>

								<div class="${varclass}">
								<div class="card-head">
									<header>
										<spring:message code="issue.greeting" />
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
														<th>
															<spring:message code="issue.name" />
														</th>
															<td><c:out value="${issue.name}" /></td>
														</tr>
														<tr>
														<th>
															<spring:message code="issue.email" />
														</th>
															<td><c:out value="${issue.email}" /></td>
														</tr>
													</thead>
													<tbody>
														<tr>
														<th>
															<spring:message code="issue.fecha_contacto" />
														</th>
															<td><c:out value="${issue.fechaContacto}" /></td>
														</tr>
														<tr>
														<th>
															<spring:message code="issue.asunto" />
														</th>
															<td><c:out value="${issue.asunto}" /></td>
														</tr>
														<tr>
														<th>
															<spring:message code="issue.mensaje" />
														</th>
															<td><c:out value="${issue.mensaje}" /></td>
														</tr>
														<c:if test="${issue.estado == 0}">
															<tr>
															<th>
																<spring:message code="issue.fecha_resolucion" />
															</th>
																<td><c:out value="${issue.fechaResolucion}" /></td>
															</tr>
															<tr>
															<th>
																<spring:message code="issue.informe" />
															</th>
																<td><c:out value="${issue.informe}" /></td>
															</tr>															
														</c:if>
													</tbody>
												</table>
											</div>
										</div>

										<div class="col-lg-12 text-center">
											
											<c:if test="${issue.estado == 1}">
												<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.3.5/sweetalert2.all.min.js"></script>
												 <c:url value="/admin/issues/${issue.idAyuda}/delete" var="postPathDelete" />
												<form  name="formDelete" id="formDelete" method="post" action="${postPathDelete}">
	        										<input type="submit" onclick="confirmFunction(this.form)" class="btn btn-danger" id="delete" name="Eliminar" value="<spring:message
																	code="item.delete_form_label" />" /><br>
	     										</form>

											</c:if>
											<c:if test="${issue.estado == 1}">
												<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.3.5/sweetalert2.all.min.js"></script>
												 <c:url value="/admin/issues/${issue.idAyuda}/archive" var="postPathBaja" />
												<form  name="formDelete" id="formDelete" method="post" action="${postPathBaja}">
	        										<input type="submit" onclick="confirmBajaFunction(this.form)" class="btn btn-warning" id="baja" name="Baja" value="<spring:message
																	code="issues.baja_form_label" />" /><br>
	     										</form>
											</c:if>
											<a href="<c:url value="/admin/issues/"/>"
															class="btn btn-default rebeccapurple-color"><spring:message
																code="issues.go_issues" /></a><br>
											</div>
										</div>
										<p>
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
