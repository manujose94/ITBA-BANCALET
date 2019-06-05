<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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

	<script type="text/javascript">

    function confirmFunction(form) {
    	event.preventDefault();
    	  swal({
    	    title: "<strong><spring:message code="popup.delete_anuncio" /></strong>",
    	    text: "<spring:message code="popup.delete_message" />",
    	    showCancelButton: true,
    	    confirmButtonColor: '#f7505a',
    	    cancelButtonColor: '#f7505a',
    	    confirmButtonText: "<spring:message code="popup.confirm" />",
    	    cancelButtonText: "<spring:message code="popup.cancel" />"

    	  }).then(function(result) {
    		  if (result.value){
    			  form.submit();
    	    	 //$('#formDelete').submit();
    		  }
    	  })
    	}

    function confirmBajaFunction(form) {
    	event.preventDefault();
    	  swal({
    		  title: "<strong><spring:message code="popup.baja_anuncio" /></strong>",
      	    text: "<spring:message code="popup.delete_message" />",
    	    showCancelButton: true,
    	    confirmButtonColor: '#ffd100',
    	    cancelButtonColor: '#ffd100',
    	    confirmButtonText: "<spring:message code="popup.confirm" />",
    	    cancelButtonText: "<spring:message code="popup.cancel" />"

    	  }).then(function(result) {
    		  if (result.value){
    			  form.submit();
    	    	 //$('#formDelete').submit();
    		  }
    	  })
    	}

    function contactFunction(form) {
    	event.preventDefault();
    	  swal({
    	    title: "<strong><spring:message code="contacto.contactado_con" /><u><c:out value="  ${seller.username}"/></u></strong>",
    	    html:
    	        '<b><spring:message code="contacto.mensaje_enviado" /></b><p>, '+
    	        '<c:out value="${contactado.mensaje}"/> ',
    	    showCancelButton: false,
    	    confirmButtonColor: '#428bca',
    	    cancelButtonColor: '#428bca',
    	    confirmButtonText: "<b><spring:message code="contacto.ir_mis_mensajes" /></b>",
    	    cancelButtonText: "No",
    	    reverseButtons: true

    	  }).then(function(result) {
 		 		//console.log(result.value);
 		 		 if (result.value){

    			  	//form.action='users/miscontactose';
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

											<c:if test="${propietario ==  true and item.estado == 'ALTA'}">

														<a href="<c:url value="/items/${item.itemid}/edit"/>"
															class="btn btn-default rebeccapurple-color"><spring:message
																code="item.editar_form_label" /></a><br>


													</c:if>

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
														<tr>
														<th>
														<c:if test="${propietario ==  false}">
															<c:if test="${item.estado == 'ALTA'}">
															<c:choose>
								    							<c:when test="${contactado == null}">
								    							<c:url value="/items/contactar/${item.itemid}" var="post1" />
								    							<form  name="contactar" id="contactar" method="post" action="${post1}">
								    								<input type="submit"  class="btn btn-primary" id="contact" name="Contactar" value="<spring:message code="item.contactar_label" />" />
								        						</form>
								        							<!-- <th><a href="<c:url value="/items/contactar/${item.itemid}"/>" class="btn btn-primary "><spring:message
															code="item.contactar_label" /></a></th> -->
								   								 </c:when>
								    							<c:otherwise>
								    							<c:url value="/items/contactado" var="post2" />
								    							<form  name="contactar" id="contactar" method="post" action="${post2}">
								    							<input type="submit"   onclick="contactFunction(this.form)" class="btn btn-info" id="detalle_contactado" name="Contactado" value="<spring:message code="item.contacto_label" />" /><c:out value="${contactado.fecha_contacto}" /><br>
								        							<!--<th><a href="<c:url value="/items/contactar/${item.itemid}"/>" class="btn btn-info ">Contatado con el vendedor   <c:out value="${contactado.fecha_contacto}" /></a></th>
								    							-->
								    							</form>
								    							</c:otherwise>
															</c:choose>
															</c:if>
														</c:if>
														</th>
														</tr>
													</tbody>
												</table>
											</div>
										</div>

										<div class="col-lg-12 text-center">
											<sec:authorize access="hasRole('ROLE_USER')">
											<c:if test="${propietario ==  true and item.estado == 'ALTA'}">
												<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.3.5/sweetalert2.all.min.js"></script>
												 <c:url value="/items/${item.itemid}/delete" var="postPathDelete" />
												<form  name="formDelete" id="formDelete" method="post" action="${postPathDelete}">
	        										<input type="submit" onclick="confirmFunction(this.form)" class="btn btn-danger" id="delete" name="Eliminar" value="<spring:message
																	code="item.delete_form_label" />" /><br>
	     										</form>

											</c:if>
											<c:if test="${propietario ==  true and item.estado == 'ALTA'}">
												<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.3.5/sweetalert2.all.min.js"></script>
												 <c:url value="/items/${item.itemid}/confirmarbaja" var="postPathBaja" />
												<form  name="formDelete" id="formDelete" method="post" action="${postPathBaja}">
	        										<input type="submit" onclick="confirmBajaFunction(this.form)" class="btn btn-warning" id="baja" name="Baja" value="<spring:message
																	code="item.baja_form_label" />" /><br>
	     										</form>

											</c:if>
											</sec:authorize>
											<sec:authorize access="hasRole('ROLE_ADMIN')">
												<c:if test="${item.estado == 'ALTA'}">
													<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.3.5/sweetalert2.all.min.js"></script>
														 <c:url value="/items/${item.itemid}/delete" var="postPathDelete" />
														<form  name="formDelete" id="formDelete" method="post" action="${postPathDelete}">
			        										<input type="submit" onclick="confirmFunction(this.form)" class="btn btn-danger" id="delete" name="Eliminar" value="<spring:message
																			code="item.delete_form_label" />" /><br>
			     										</form>
		     									</c:if>
	     									</sec:authorize>
											</div>
										</div>
										<p>
										<sec:authorize access="hasRole('ROLE_ADMIN')">
											<div class="col-lg-12 text-center">
												<a href="<c:url value="/users/${seller.id}"/>"
													class="btn btn-default rebeccapurple-color"><spring:message
														code="admin.volver_perfil_user" /></a><br>
											</div>
										</sec:authorize>
										<sec:authorize access="hasRole('ROLE_USER')">
											<div class="col-lg-12 text-center">
												<a href="<c:url value="/items/"/>"
													class="btn btn-default rebeccapurple-color"><spring:message
														code="item.return_to_items_list" /></a><br>
											</div>
										</sec:authorize>
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
