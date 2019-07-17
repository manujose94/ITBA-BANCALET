<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@page language="java"%>

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
	MyFunction = function(){
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

			<!-- start page content -->
			<div class="page-content-wrapper">
				<div class="page-content-register-user">
					<div class="row justify-content-center">
						<div class="col-sm-12">
							<div class="card-box card-box-user mx-auto mt-3">
								<div class="card-head">
									<header>
										<spring:message code="user.edit_header" />
									<c:if test="${user.urlImg != null && user.urlImg != ''}">
												<img src="<c:url value="data:image/jpg;base64,${user.urlImg}"/>"  alt="Avatar" class="avatar-userimg" >
															</c:if>	
															<c:if test="${user.urlImg == null || user.urlImg == '' }">
																<c:if test="${user.role == 'ADMIN' }">
																	<img src="<c:url value="/resources/img/admin/admin${user.numImg}.png"/>"  class="avatar" style="width:100px;height:100px;" >
																</c:if>
																<c:if test="${user.role == 'USER' }">
																	<img src="<c:url value="/resources/img/avatar/avatar${user.numImg}.png"/>"  class="avatar" style="width:100px;height:100px;" >
																</c:if>
															</c:if>		
									<spring:message code="user.greeting" arguments="${user.username}" />
								
									</header>
								</div>
								<div class="card-body">
								<sec:authorize access="hasRole('ROLE_ADMIN')">
								
									<c:url value="/admin/editAccept/${userId}" var="postPath" />
									<form:form modelAttribute="AdminUserEditForm" action="${postPath}"
										enctype = "multipart/form-data"
										method="post">
										
										<div class="col-lg-12 p-t-20">
											<form:select name="${user.numImg}" path="tipo">
										      <option value="${user.numImg}" selected="selected" data-icon="<c:url value="/resources/img/avatar/avatar${user.numImg}.png"/>">AVATAR ${user.numImg}</option>
										      <c:if test="${user.numImg != 1}">
										      	<option value="1" data-icon="<c:url value="/resources/img/avatar/avatar1.png"/>">AVATAR 1</option>
										      </c:if>
										      <c:if test="${user.numImg != 2}">
										      	<option value="2" data-icon="<c:url value="/resources/img/avatar/avatar2.png"/>">AVATAR 2</option>
										      </c:if>
										      <c:if test="${user.numImg != 3}">
										      	<option value="3" data-icon="<c:url value="/resources/img/avatar/avatar3.png"/>">AVATAR 3</option>
										     </c:if>	
										     <c:if test="${user.numImg != 4}">
										     	<option value="4" data-icon="<c:url value="/resources/img/avatar/avatar4.png"/>">AVATAR 4</option>
										     </c:if>
										     <c:if test="${user.numImg != 5}">
										      	<option value="5" data-icon="<c:url value="/resources/img/avatar/avatar5.png"/>">AVATAR 5</option>
										     </c:if>
										     <c:if test="${user.numImg != 6}">
										      	<option value="6" data-icon="<c:url value="/resources/img/avatar/avatar6.png"/>">AVATAR 6</option>
								    		 </c:if>
								    		</form:select>
							     		</div>
							   			<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
												
												<input type="file"id="file" name="fileUpload" size="50" accept="image/*"/>
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
													cssClass="mdl-textfield__input" value="${user.telf}" />
												<form:errors path="telf" cssClass="formError" element="p" />
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
													cssClass="mdl-textfield__input" value="${user.code}" />
												<form:errors path="code" cssClass="formError" element="p" />
											</div>
										</div>


										<!-- User Direccion -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="direccion" cssClass="mdl-textfield__label">
													<spring:message code="user.direccion" />
												</form:label>
												<form:input type="text" path="direccion"
													cssClass="mdl-textfield__input" value="${user.direccion}" />
												<form:errors path="direccion" cssClass="formError" element="p" />
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
													cssClass="mdl-textfield__input" value="${user.city}"/>
												<form:errors path="city" cssClass="formError" element="p" />
											</div>
										</div>

						
										
										<!-- User country -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
												<form:select type="text" path="country" value ="${user.country}">
													<jsp:include page="/WEB-INF/jsp/paisesEdit.jsp" />
												</form:select>
												<form:errors path="country" cssClass="formError" element="p" />
											</div>
										</div>
										<!-- User lat and lon -->
										<form:input id="lat" path="lat" type="hidden" value="${user.lat}" />
										<form:input id="lon" path="lon" type="hidden" value="${user.lon}" />
										<div class="col-lg-12 p-t-20 text-center">
										<button id="button" onclick="javascript:MyFunction()" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent waves-effect waves-light">
     											<i class="material-icons">location_on</i>  <span
											class="title"><spring:message code="mapa.button_busqueda_puntoencuentro"/></span>
    										</button>
    										<br>
    										<p>
											<jsp:include page="/WEB-INF/jsp/maparegister.jsp" />
											</p>
										<br>
											<button type="submit"
												class="btn btn-default rebeccapurple-color ">
												<spring:message code="accept_button" />
											</button>
	                                			<a href="<c:url value="/admin/${userId}"/>"class="btn btn-default rebeccapurple-color ">
	                                			<spring:message code="cancel_button"/></a><br>
										</div>
									</form:form>
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
								</sec:authorize>
								<sec:authorize access="hasRole('ROLE_USER')">
									<c:url value="/users/editAccept" var="postPath" />
									<form:form modelAttribute="UserEditForm" action="${postPath}" method="post"
									enctype = "multipart/form-data">
									
										<div class="col-lg-12 p-t-20">
											<form:select name="${user.numImg}" path="tipo">
											     <option value="${user.numImg}" selected="selected" data-icon="<c:url value="/resources/img/avatar/avatar${user.numImg}.png"/>">AVATAR ${user.numImg}</option>
											     <c:if test="${user.numImg != 1}">
											      	<option value="1" data-icon="<c:url value="/resources/img/avatar/avatar1.png"/>">AVATAR 1</option>
											      </c:if>
											      <c:if test="${user.numImg != 2}">
											      	<option value="2" data-icon="<c:url value="/resources/img/avatar/avatar2.png"/>">AVATAR 2</option>
											      </c:if>
											      <c:if test="${user.numImg != 3}">
											      	<option value="3" data-icon="<c:url value="/resources/img/avatar/avatar3.png"/>">AVATAR 3</option>
											     </c:if>	
											     <c:if test="${user.numImg != 4}">
											     	<option value="4" data-icon="<c:url value="/resources/img/avatar/avatar4.png"/>">AVATAR 4</option>
											     </c:if>
											     <c:if test="${user.numImg != 5}">
											      	<option value="5" data-icon="<c:url value="/resources/img/avatar/avatar5.png"/>">AVATAR 5</option>
											     </c:if>
											     <c:if test="${user.numImg != 6}">
											      	<option value="6" data-icon="<c:url value="/resources/img/avatar/avatar6.png"/>">AVATAR 6</option>
									    		 </c:if>
								    		</form:select>
							     		</div>
							   			<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
												
												<input type="file"id="file" name="fileUpload" size="50" accept="image/*"/>
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
													cssClass="mdl-textfield__input" value="${user.telf}"/>
												<form:errors path="telf" cssClass="formError" element="p" />
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
													cssClass="mdl-textfield__input" value="${user.city}" />
												<form:errors path="city" cssClass="formError" element="p" />
											</div>
										</div>

									

										<!-- User Direccion -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

												<form:label path="direccion" cssClass="mdl-textfield__label">
													<spring:message code="user.direccion" />
												</form:label>
												<form:input type="text" path="direccion"
													cssClass="mdl-textfield__input" value="${user.direccion}"/>
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
													cssClass="mdl-textfield__input" value="${user.code}"/>
												<form:errors path="code" cssClass="formError" element="p" />
											</div>
										</div>
										<!-- User country -->
										<div class="col-lg-12 p-t-20">
											<div
												class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
												<form:select type="text" path="country" value ="${user.country}">
													<jsp:include page="/WEB-INF/jsp/paisesEdit.jsp" />
												</form:select>
												<form:errors path="country" cssClass="formError" element="p" />
											</div>
										</div>
										<!-- User lat and lon -->
										<form:input id="lat" path="lat" type="hidden" value="${user.lat}" />
										<form:input id="lon" path="lon" type="hidden" value="${user.lon}" />
										<div class="col-lg-12 p-t-20 text-center">
										<button id="button" onclick="javascript:MyFunction()" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent waves-effect waves-light">
     											<i class="material-icons">location_on</i>  <span class="title"><spring:message code="mapa.button_busqueda_puntoencuentro" /></span>
    										</button><br><p>
											<jsp:include page="/WEB-INF/jsp/maparegister.jsp" />
											<br>
											<button type="submit"
												class="btn btn-default rebeccapurple-color ">
												<spring:message code="accept_button" />
											</button>
			                                	<a href="<c:url value="/users/returnIndex"/>"class="btn btn-default rebeccapurple-color ">
			                                	<spring:message code="cancel_button"/></a><br>
										</div>
									</form:form>
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
									</sec:authorize>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end page content -->
		</div>
		<!-- end page container -->
	</div>

	<script src="<c:url value="/resources/js/materialize.min.js"/>"></script>
	<!-- Material -->
	<script src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
	<script src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
	
	
</body>
</html>

