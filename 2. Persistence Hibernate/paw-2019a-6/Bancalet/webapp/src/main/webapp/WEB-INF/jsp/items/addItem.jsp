<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<title>Bancalet</title>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- Material Design Lite CSS -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet"href="<c:url value="/resources/material-design-lite/material.min.css"/>" />
<!--Import materialize.css-->
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/materialize.min.css"/>"  media="screen,projection"/>
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

<script>
$(document).ready(function(){
    $('.datepicker').datepicker({format:'yyyy-mm-dd'});
  });
$(document).ready(function(){
    $('select').formSelect();
  });
function ErrorFunction() {	    	
 	  swal({
 	    title: "<strong><spring:message code="file.label_title" /></strong>",
 	    html: "<spring:message code="file.label_file_too_big" /><p> <spring:message code="file.label_file_maxsize" />",	    	   
 	    confirmButtonColor: '#f7505a',	    	  
 	    confirmButtonText: "<spring:message code="popup.confirm" />",
 	  

 	  }).then(function(result) {
 		 
 	  })
 	}
function ErrorNumberFunction() {	    	
	  swal({
	    title: "<strong>Numero de imagen 3 maximos</strong>",
	    html: "<spring:message code="file.label_file_too_big" /><p> <spring:message code="file.label_file_maxsize" />",	    	   
	    confirmButtonColor: '#f7505a',	    	  
	    confirmButtonText: "<spring:message code="popup.confirm" />",
	  

	  }).then(function(result) {
		 
	  })
	}
</script>

</head>
<body
	class="page-container page-header-fixed sidemenu-closed-hidelogo page-content-white page-md header-white
             dark-sidebar-color logo-dark">

	<div class="page-wrapper">
		<!-- start header -->
		<jsp:include page="/WEB-INF/jsp/encabezadopagina.jsp" />
		<!-- end header -->
		<jsp:include page="/WEB-INF/jsp/piepagina.jsp" />
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
									action="${postPath}" method="post"
									enctype = "multipart/form-data"
									>
									<div class="card-body">

									<!-- image item -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<input type="file" id="file" name="fileUploadMultiple"  accept="image/*" multiple="multiple"/>
											</div>
										</div>

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
												<p>
													<spring:message code="item.tipo_form_label" />
													<select name="item_tipo">
														<option value="1"><spring:message code="item.tipo1"/></option>
													    <option value="2"><spring:message code="item.tipo2"/></option>
													    <option value="3"><spring:message code="item.tipo3"/></option>
													    <option value="4"><spring:message code="item.tipo4"/></option>
													    <option value="5"><spring:message code="item.tipo5"/></option>
												  	</select>
													<form:errors path="tipo" cssClass="formError" element="p" />
												</p>
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
											<div class="form-group">
	  											<label class="col-sm-2 control-label">	<spring:message code="item.fecha_cad_form_label" /></label>
											    <div class="col-sm-10">
											    	<form:input type="text" id="datepicker" path="fecha_caducidad" cssClass="datepicker"/>
											    	<form:errors path="fecha_caducidad" cssClass="formError" element="p" />
											    </div>
											</div>
										</div>


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
	</div>
	<!-- start js include path -->
	<script src="<c:url value="/resources/js/materialize.min.js"/>"></script>
	<!-- Common js-->
	
	<!-- Material -->
	<script src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
	 <script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.3.5/sweetalert2.all.min.js"></script>
	<script type="text/javascript">
		var uploadField = document.getElementById("file");
		
		uploadField.onchange = function() {
			if(this.files.length > 3){
				ErrorNumberFunction();
			    this.value = "";
			    return false;
			}
		
		    if(this.files[0].size > 1048576*2){
		    	ErrorFunction();
		       this.value = "";
		       return false;
		    };
		    if(this.files[1].size > 1048576*2){
		    	ErrorFunction();
		       this.value = "";
		       return false;
		    };
		    if(this.files[2].size > 1048576*2){
		    	ErrorFunction();
		       this.value = "";
		       return false;
		    };
		};
	</script>
</body>
</html>
