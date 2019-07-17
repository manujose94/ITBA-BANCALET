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
<!-- free maps --> 
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.5.1/dist/leaflet.css"/>
<script src="https://unpkg.com/leaflet@1.5.1/dist/leaflet.js"></script>
<!-- Material Design Lite CSS -->
<link rel="stylesheet" href="<c:url value="/resources/material-design-lite/material.min.css"/>" />

<!--Import materialize.css-->

<link rel="stylesheet"
	href="<c:url value="/resources/css/materialize.min.css"/>" />
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
<link href="<c:url value="/resources/css/estrellas_grises.css"/>"
	rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/estrellas_naranjas.css"/>"
	rel="stylesheet" type="text/css">
<!-- Favicon -->
<link rel="shortcut icon" type="image/x-icon"
	href="<c:url value="/resources/img/bancalet/appicon.ico"/>" />
	
<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>  
<sec:authorize access="hasRole('ROLE_ADMIN')">
<script type="text/javascript">

    function confirmBajaFunction(form) {
    	event.preventDefault();
    	  swal({
    		  title: "<strong><spring:message code="popup.baja_user" /></strong>",
      	    text: "<spring:message code="popup.baja_message_user" />",
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
    
    function confirmAltaFunction(form) {
    	event.preventDefault();
    	  swal({
    		  title: "<strong><spring:message code="popup.alta_user" /></strong>",
      	    text: "<spring:message code="popup.alta_message_user" />",
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
</sec:authorize>
</head>
<c:if test="${user.role == 'ADMIN' || user_index.role != 'ADMIN' }">
<body
	class="page-header-fixed page-content-white page-md header-white logo-dark">
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
						<c:choose>
							    <c:when test="${user_index.estado == 0}">
							        <c:set var="varclass" value=""/>
							    </c:when>
							    <c:otherwise>
							        <c:set var="varclass" value="card-box"/>
							    </c:otherwise>
							</c:choose>

							<div class="${varclass}">
								<div class="card-head">
									<header>
									
									<c:if test="${user_index.urlImg != null && user_index.urlImg != ''}">
												<img src="<c:url value="data:image/jpg;base64,${user_index.urlImg}"/>" alt="Avatar" class="avatar-userimg" >
									</c:if>	
												<c:if test="${user_index.urlImg == null || user_index.urlImg == '' }">
													<c:if test="${user_index.role == 'ADMIN' }">
														<img src="<c:url value="/resources/img/admin/admin${user_index.numImg}.png"/>"  class="avatar" style="width:100px;height:100px;" >
													</c:if>
													<c:if test="${user_index.role == 'USER' }">
														<img src="<c:url value="/resources/img/avatar/avatar${user_index.numImg}.png"/>"  class="avatar" style="width:100px;height:100px;" >
													</c:if>
									</c:if>		
									<spring:message code="user.greeting" arguments="${user_index.username}" />
									<c:if test="${maxSeller == 1}">
									  <div class="chip">
										  <img src="<c:url value="/resources/img/bancalet/bestseller.png"/>" >
										    <spring:message code="user.bestSeller" />
									  </div>
									</c:if>
									<c:if test="${maxBuyer == 1}">
									  <div class="chip">
										  <img src="<c:url value="/resources/img/bancalet/bestbuyer.png"/>" >
										    <spring:message code="user.bestBuyer" />
									  </div>
									</c:if>
									<c:if test="${maxRated == 1}">
									  <div class="chip">
										  <img src="<c:url value="/resources/img/bancalet/bestrated.png"/>" >
										    <spring:message code="user.bestReviwed" />
									  </div>
									</c:if>
									<c:if test="${rateo == 5}">
									  <div class="chip">
										  <img src="<c:url value="/resources/img/bancalet/fivestars.png"/>" >
										    <spring:message code="user.fiveStars" />
									  </div>
									</c:if>
									</header>
								</div>
								<div class="card-body">
										<div class="col s6"> 	
			      							<div class="col-lg-15 p-t-20">										
													<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
														<h4>
														<b><spring:message code="user.rate" /></b>
															<a>
																<c:forEach var="i" begin="1" end="5" step="1">
																	<c:if test="${rateo >= i}">
																		<label class="clasificacionOrange">&#9733;</label>
				         											</c:if>
				         											<c:if test="${rateo < i}">
				         												<label class="clasificacionGrey">&#9733;</label>
				      												</c:if>
				      											</c:forEach>
				      										</a>
														</h4><p>
														<h4><b>
															<spring:message code="user.totalsales" arguments=": " /></b> ${totalSales}
														</h4><p>
														<h4><b>
															<spring:message code="user.email" arguments=": " /></b> ${user_index.email}
														</h4><p>
														<h4>
															<b><spring:message code="user.telf" arguments=": " /></b> ${user_index.telf}
														</h4><p>
														<h4>
															<b><spring:message code="user.city" arguments=": " /></b> ${user_index.city}
														</h4><p>
														<h4>
															<b><spring:message code="user.address" arguments=": " /></b> ${user_index.direccion}
														</h4><p>
														<h4>
															<b><spring:message code="user.code" arguments=": " /></b> ${user_index.code}
														</h4><p>
														<h4>
															<b><spring:message code="user.country" arguments=": " /></b> ${user_index.country}
														</h4><p>
			
													</div>
												</div>
												<sec:authorize access="hasRole('ROLE_USER')">
				                                	<a href="<c:url value="/users/editUsr"/>"class="btn btn-default rebeccapurple-color ">
				                                	<spring:message code="user.button_edit"/></a><br>
				                                	<br></br>
					                   			</sec:authorize>
			
												<sec:authorize access="hasRole('ROLE_ADMIN')">
				                                	<a href="<c:url value="/admin/editUsr/${user_index.userId}"/>"class="btn btn-default rebeccapurple-color ">
				                                	<spring:message code="user.button_edit"/></a><br>
					                   			</sec:authorize>
			
												<sec:authorize access="hasRole('ROLE_USER')">
				                                	<a href="<c:url value="/users/editPass"/>"class="btn btn-default rebeccapurple-color ">
				                                	<spring:message code="user.button_changepass"/></a><br>
					                   			</sec:authorize>
			
												<br></br>
												<sec:authorize access="hasRole('ROLE_ADMIN')">
				                                	<a href="<c:url value="/admin/editPass/${user_index.userId}"/>"class="btn btn-default rebeccapurple-color ">
				                                	<spring:message code="user.button_changepass"/></a><br>
					                   			</sec:authorize>
			
												<br></br>
												<sec:authorize access="hasRole('ROLE_ADMIN')">
													<c:if test="${user_index.role == 'USER' }">
					                                	<a href="<c:url value="/admin/ventas/${user_index.userId}"/>"class="btn btn-default rebeccapurple-color ">
					                                	<spring:message code="user.button_ventas"/></a><br>
				                                	</c:if>
					                   			</sec:authorize>
			
					                   			<br></br>
					                   			<sec:authorize access="hasRole('ROLE_ADMIN')">
					                   				<c:if test="${user_index.role == 'USER' }">
					                                	<a href="<c:url value="/admin/compras/${user_index.userId}"/>"class="btn btn-default rebeccapurple-color ">
					                                	<spring:message code="user.button_compras"/></a><br>
				                                	</c:if>
					                   			</sec:authorize>
					                   	</div>
					                   	
					      				<div class="col s6 text-center"><p>
					      				<sec:authorize access="hasRole('ROLE_USER')">
						      				<a id="button" href="<c:url value="/users/listUsersItems"/>" class="mdl-button mdl-js-button ">
	     											<i class="material-icons">location_on</i>  <span
												class="title"><spring:message code="user.label_vendedores_cercanos" arguments=" " /></span></a>
											</sec:authorize>
					      					<jsp:include page="/WEB-INF/jsp/mapa.jsp" />
					      				</div>	
					      					
								</div>
								<c:if test="${user_index.userId != user.userId}">
								<sec:authorize access="hasRole('ROLE_ADMIN')">
									<div class="col-lg-12 text-center">
										<c:if test="${user_index.estado == 1}">
											<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.3.5/sweetalert2.all.min.js"></script>
											<c:url value="/admin/${user_index.userId}/bajaUser" var="postPathBaja" />
											<form  name="formDelete" id="formDelete" method="post" action="${postPathBaja}">
	       										<input type="submit" onclick="confirmBajaFunction(this.form)" class="btn btn-warning" id="baja" name="Baja" value="<spring:message
																code="admin.user_baja" />" /><br>
	    									</form>
										</c:if>
									</div>
								</sec:authorize>
								<sec:authorize access="hasRole('ROLE_ADMIN')">
									<div class="col-lg-12 text-center">
										<c:if test="${user_index.estado == 0}">
											<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.3.5/sweetalert2.all.min.js"></script>
											<c:url value="/admin/${user_index.userId}/altaUser" var="postPathBaja" />
											<form  name="formDelete" id="formDelete" method="post" action="${postPathBaja}">
	       										<input type="submit" onclick="confirmAltaFunction(this.form)" class="btn btn-warning" id="baja" name="Baja" value="<spring:message
																code="admin.user_alta" />" /><br>
	    									</form>
										</c:if>
									</div>
								</sec:authorize>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<!-- bootstrap -->
<script src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
<script type="text/javascript">
	//Inicializar ubicacion mapa.jsp
	findUbicacionUserIndex("user_index");
</script>

</body>
</c:if>
<c:if test="${user.role == 'USER' && user_index.role == 'ADMIN' }">
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
											<header><spring:message code="error.403.title"></spring:message></header>
										</div>
										<div class="card-body">
											<div class="table-responsive"><spring:message code="error.403.body"></spring:message></div>

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
	</div>
	<!-- start js include path -->
	<!-- Material -->
	<script src="/resources/js/materialize.min.js"></script>
	<!-- end js include path -->
	

</body>
</c:if>
</html>
