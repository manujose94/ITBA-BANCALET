<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<meta content="width=device-width, initial-scale=1" name="viewport" />
<title>Bancalet</title>
<!-- google font -->
<link
	href="<c:url value="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700"/>"
	rel="stylesheet" type="text/css" />
<!--Import Google Icon Font-->
      <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<!-- Free maps --> 
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.5.1/dist/leaflet.css"/>
<script src="https://unpkg.com/leaflet@1.5.1/dist/leaflet.js"></script>
<!-- Material Design Lite CSS -->
<link rel="stylesheet"
	href="<c:url value="/resources/material-design-lite/material.min.css"/>" />
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
	
<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>      
<script>
	$(document).ready(function(){
	    $('select').formSelect();
	  });	     
	var buscadopunto=0;
	MyFunction = function(){
		
		buscadopunto=1,
		//nor reload page
		event.preventDefault();
        //launch maparegister function
        findUbicacion(document.getElementById("lat").value,document.getElementById("lon").value);
        return false;
    }
	
	
	 function ErrorFunction() {	    	
	    	  swal({
	    	    title: "<strong><spring:message code="file.label_title" /></strong>",
	    	    html: "<spring:message code="file.label_file_too_big" /><p> <spring:message code="file.label_file_maxsize" />",	    	   
	    	    confirmButtonColor: '#f7505a',	    	  
	    	    confirmButtonText: "<spring:message code="popup.confirm" />",
	    	  

	    	  }).then(function(result) {
	    		 
	    	  })
	    	}
</script>
</head>
<body
	class="page-container page-header-fixed page-content-white page-md header-white logo-dark">

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

				<div class="page-content-register-user">
					<div class="row justify-content-center">
						<div class="col-sm-12">
							<div class="card-box card-box-user mx-auto mt-3">
								<div class="card-head">
									<header>
										<spring:message code="user.register_header" />
									</header>
								</div>
								<div class="card-body">
								<div class="input-field col s12 m12">
									
									<c:url value="/admin/createUser" var="postPath" />
									<form:form modelAttribute="AdminRegisterForm" action="${postPath}"
										method="post" enctype = "multipart/form-data">   
										
										<div class="col-lg-12 p-t-20">
											<select name="avatar_tipo" >
										      <option value="0" selected><spring:message code="user.select_avatar" /></option>
										      <option value="1" data-icon="<c:url value="/resources/img/avatar/avatar1.png"/>">AVATAR 1</option>
										      <option value="2" data-icon="<c:url value="/resources/img/avatar/avatar2.png"/>">AVATAR 2</option>
										      <option value="3" data-icon="<c:url value="/resources/img/avatar/avatar3.png"/>">AVATAR 3</option>
										      <option value="4" data-icon="<c:url value="/resources/img/avatar/avatar4.png"/>">AVATAR 4</option>
										      <option value="5" data-icon="<c:url value="/resources/img/avatar/avatar5.png"/>">AVATAR 5</option>
										      <option value="6" data-icon="<c:url value="/resources/img/avatar/avatar6.png"/>">AVATAR 6</option>
								    		</select>
							     		</div>
							   			
							   			<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
												
												<input type="file"id="file" name="fileUpload" size="50" accept="image/*"/>
											</div>
										</div>
											
										<!-- User username -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
												<form:label type="text" path="username"
													cssClass="mdl-textfield__label">
													<spring:message code="user.username_form_label" />
												</form:label>
												<form:input type="text" path="username"
													cssClass="mdl-textfield__input" />
												<form:errors path="username" cssClass="formError"
													element="p" />
											</div>
										</div>

										<!-- User password -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="password" cssClass="mdl-textfield__label">
													<spring:message code="user.password_form_label" />
												</form:label>
												<form:input type="password" path="password"
													cssClass="mdl-textfield__input" />
												<form:errors path="password" cssClass="formError"
													element="p" />
											</div>
										</div>

										<!-- User repeatPassword -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="repeatPassword"
													cssClass="mdl-textfield__label">
													<spring:message code="user.repeat_password_form_label" />
												</form:label>
												<form:input type="password" path="repeatPassword"
													cssClass="mdl-textfield__input" />
												<form:errors path="repeatPassword" cssClass="formError"
													element="p" />
											</div>
										</div>

										<!-- User telf -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="telf" cssClass="mdl-textfield__label">
													<spring:message code="user.register_telf" />
												</form:label>
												<form:input type="text" path="telf"
													cssClass="mdl-textfield__input" />
												<form:errors path="telf" cssClass="formError" element="p" />
											</div>
										</div>

										<!-- User email -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="email" cssClass="mdl-textfield__label">
													<spring:message code="user.register_email" />
												</form:label>
												<form:input type="text" path="email"
													cssClass="mdl-textfield__input" />
												<form:errors path="email" cssClass="formError" element="p" />
											</div>
										</div>

										<!-- User city -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="city" cssClass="mdl-textfield__label">
													<spring:message code="user.register_city" />
												</form:label>
												<form:input type="text" path="city"
													cssClass="mdl-textfield__input" />
												<form:errors path="city" cssClass="formError" element="p" />
											</div>
										</div>

										<!-- User direccion -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="direccion" cssClass="mdl-textfield__label">
													<spring:message code="user.direccion" />
												</form:label>
												<form:input type="text" path="direccion"
													cssClass="mdl-textfield__input" />
												<form:errors path="direccion" cssClass="formError" element="p" />
											</div>
										</div>

										<!-- User code -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="code" cssClass="mdl-textfield__label">
													<spring:message code="user.register_code" />
												</form:label>
												<form:input type="text" path="code"
													cssClass="mdl-textfield__input" />
												<form:errors path="code" cssClass="formError" element="p" />
											</div>
										</div>

										<!-- User country -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
												<jsp:include page="/WEB-INF/jsp/paises.jsp" />
												<form:errors path="country" cssClass="formError" element="p" />
											</div>
										</div>
										
										<!-- User role -->
										<div class="col-lg-12 p-t-20">
											<form:select name="role" path="role">
										      <option value="ADMIN">ADMIN</option>
										      <option selected="selected" value="USER">USER</option>
								    		</form:select>						
											<form:errors path="role" cssClass="formError" element="p" />
											
										</div>
										
										<!-- User lat and lon -->
										<form:input id="lat" path="lat" type="hidden" value="" />
										<form:input id="lon" path="lon" type="hidden" value="" />
										
										<div class="col-lg-12 p-t-20 text-center">										
											<button id="button" onclick="javascript:MyFunction()" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent waves-effect waves-light">
     											<i class="material-icons">location_on</i>  <span
											class="title"><spring:message code="mapa.button_busqueda_puntoencuentro" /></span>
    										</button><br><p>
												<jsp:include page="/WEB-INF/jsp/maparegister.jsp" />
											</p>
											<br>
											<button type="submit"
												class="btn btn-default rebeccapurple-color ">
												<spring:message code="register.button_label" />
											</button>
										</div>
									</form:form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end page content -->
		</div>
		</div>
		<!-- end page container -->
	<script src="<c:url value="/resources/js/materialize.min.js"/>"></script>
	<!-- Material -->
	<script src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
	<script src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.3.5/sweetalert2.all.min.js"></script>
		<script type="text/javascript">
		var uploadField = document.getElementById("file");

		uploadField.onchange = function() {
		    if(this.files[0].size > 1048576*2){
		    	ErrorFunction();
		       this.value = "";
		    };
		};
	</script>
     
</body>
</html>
