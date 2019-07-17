<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<title>Bancalet</title>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$( function() {$( "#datepicker" ).datepicker({ dateFormat: "yy-mm-dd" });} );
</script>

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
<!-- Material Design Lite CSS -->
<link rel="stylesheet"
	href="<c:url value="/webjars/material-design-lite/1.1.0/material.min.css"/>" />
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
					<div class="row">
						<div class="col-sm-12">
							<div class="card-box mt-3">
								<div class="card-head">
									<header>
										<spring:message code="item.register_header" />
									</header>
								</div>
								<c:url value="/items/create" var="postPath" />
								<form:form modelAttribute="ItemRegisterForm"
									action="${postPath}" method="post">
									<div class="card-body">
										
										<!-- nombre item -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
												<form:label type="text" path="name"
													cssClass="mdl-textfield__label">
													<spring:message code="item.name_form_label" />
												</form:label>
												<form:input type="text" path="name"
													cssClass="mdl-textfield__input" />
												<form:errors path="name" cssClass="formError" element="p" />
											</div>
										</div>
										
										<!-- descripcion item -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="description"
													cssClass="mdl-textfield__label">
													<spring:message code="item.description_form_label" />
												</form:label>
												<form:input type="description" path="description"
													cssClass="mdl-textfield__input" />
												<form:errors path="description" cssClass="formError"
													element="p" />
											</div>
										</div>
										
										<!-- tipo item -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">											
												<select name="item_tipo">												
													<option value="1"><spring:message code="item.tipo1"/></option>
												    <option value="2"><spring:message code="item.tipo2"/></option>
												    <option value="3"><spring:message code="item.tipo3"/></option>
												    <option value="4"><spring:message code="item.tipo4"/></option>
												    <option value="5"><spring:message code="item.tipo5"/></option>
											  	</select>
												<form:errors path="tipo" cssClass="formError" element="p" />
											</div>
										</div>
										
										<!-- price item -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="price" cssClass="mdl-textfield__label">
													<spring:message code="item.price_form_label" />
												</form:label>
												<form:input  type="number" path="price"  step=".01"   pattern="0.00"
													cssClass="mdl-textfield__input" />
												<form:errors path="price" cssClass="formError" element="p" />
											</div>
										</div>
										
										<!-- fecha_caducidad item -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="fecha_caducidad" cssClass="mdl-textfield__label">
													<spring:message code="item.fecha_cad_form_label" />
												</form:label>
												<form:input type="text" id="datepicker" path="fecha_caducidad"
													cssClass="mdl-textfield__input" />
												<form:errors path="fecha_caducidad" cssClass="formError" element="p" />
											</div>
										</div>
										
										<!-- image item -->
										<%-- <div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="image" cssClass="mdl-textfield__label">
													<spring:message code="item.image" />
												</form:label>
												<form:input type="image" path="image"
													cssClass="mdl-textfield__input" />
												<form:errors path="image" cssClass="formError" element="p" />
											</div>
										</div> --%>
										
										<!-- Aceptar item -->
										<div class="col-lg-12 p-t-20 text-center">
											<button type="submit"
												class="btn btn-default rebeccapurple-color ">
												<spring:message code="accept_button" />
											</button>
										</div>
									</div>
								</form:form>
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
	<!-- Material -->
	<script
		src="<c:url value="/webjars/material-design-lite/1.1.0/material.min.js"/>"></script>
</body>
</html>