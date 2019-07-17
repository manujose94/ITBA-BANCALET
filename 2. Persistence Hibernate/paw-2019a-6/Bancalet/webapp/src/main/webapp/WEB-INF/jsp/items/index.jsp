<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<!-- MyImage W3C -->
<link rel="stylesheet"
	href="<c:url value="/resources/css/imagen.css"/>" />
<!--  Materialize -->
<link rel="stylesheet"
	href="<c:url value="/resources/css/materialize.min.css"/>" />
<!-- Material Design Lite CSS -->
<link rel="stylesheet"
	href="<c:url value="/resources/material-design-lite/material.min.css"/>" />
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
    		  }
    	  })
    	}
    
    function confirmImageDeleteFunction(form) {
    	event.preventDefault();
    	  swal({
    	    title: "<strong><spring:message code="item.del_img_form_label" /></strong>",
    	    text: "<spring:message code="item.delete_img_message" />",
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
    		  title: "<strong><spring:message code="popup.baja_anuncio" /></strong>",
      	    text: "<spring:message code="popup.baja_message" />",
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

    function contactFunction(form) {
    	event.preventDefault();
    	  swal({
    	    title: "<strong><spring:message code="contacto.contactado_con" /><u><c:out value="  ${seller.username}"/></u></strong>",
    	    html:
    	        '<b><spring:message code="contacto.mensaje_enviado" /></b><p> '+
    	        '<c:out value="${contactado.mensaje}"/> ',
    	    showCancelButton: false,
    	    confirmButtonColor: '#428bca',
    	    cancelButtonColor: '#428bca',
    	    confirmButtonText: "<b><spring:message code="contacto.ir_mis_mensajes" /></b>",
    	    cancelButtonText: "No",
    	    reverseButtons: true

    	  }).then(function(result) {
 		 		 if (result.value){
	    		  	form.submit();
 		 		 }

    	  })
    	}
    
  
	</script>
		<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
	<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>    
<script>	
	$(document).ready(function(){
    	$('.slider').slider({
    	height: 600
  	   	});
  });
	 		   
      </script>
</head>
<body
	class="page-container page-header-fixed page-content-white page-md ">

	<div class="page-wrapper">
		<!-- start header -->
		<jsp:include page="/WEB-INF/jsp/encabezadopagina.jsp" />
		<!-- end header -->
		<jsp:include page="/WEB-INF/jsp/piepagina.jsp" />
		<!-- start page container -->
		<div class="page-container">
			
			<sec:authorize access="!isAuthenticated()">
			 	<jsp:include page="/WEB-INF/jsp/inicio/barralateralNologin.jsp" />
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
			 	<jsp:include page="/WEB-INF/jsp/barralateral.jsp"/>
			</sec:authorize>

			<!-- start page content -->
			<div class="page-content-wrapper">
				<div class="page-content">
					<div class="row">

						<div class="col-sm-12">
						<c:choose>
							    <c:when test="${item.estado == 0}">
							        <c:set var="varclass" value=""/>
							    </c:when>
							    <c:otherwise>
							        <c:set var="varclass" value="card-box"/>
							    </c:otherwise>
							</c:choose>

								<div class="${varclass}">
								
								<div class="card-head">
								<div class="col s6 "> 
									<header>									
										<spring:message code="item.greeting"
											arguments=": ${item.name}" />

											<c:if test="${propietario ==  true and item.estado == 1}">
												<a href="<c:url value="/items/${item.itemId}/edit"/>" class="btn btn-default rebeccapurple-color">
												<spring:message code="item.editar_form_label" />
												</a>
												<br>
											</c:if>

									</header>
									</div>
									<div class="col s6 text-right" > 
										<p style="margin-top:15px">
												<sec:authorize access="isAuthenticated()">
													<sec:authorize access="hasRole('ROLE_ADMIN')">
														
															<a href="<c:url value="/admin/${seller.userId}"/>"
																class="btn btn-default rebeccapurple-color"><spring:message
																	code="admin.volver_perfil_user" /></a><br>
														
													</sec:authorize>
													<sec:authorize access="hasRole('ROLE_USER')">
														
															<a href="<c:url value="/items/"/>"
																class="btn btn-default rebeccapurple-color"><spring:message
																	code="item.return_to_items_list" /></a><br>
														
													</sec:authorize>
												</sec:authorize>
												<sec:authorize access="!isAuthenticated()">
													
															<a href="<c:url value="/items/"/>"
																class="btn btn-default rebeccapurple-color"><spring:message
																	code="item.return_to_items_list" /></a><br>
													
												</sec:authorize>
			
											</p>
									</div>
									
								</div>
								<div class="card-body">
									<div class="col-lg-15 p-t-20">
										<div
											class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
											<div class="col s6"> 
											<div class="table-responsive">
												<table class="striped highlight responsive-table">
													<thead class="text-left ">
														<tr>

														<th><img src="<c:url value="/resources/img/type_food/${item.tipo}.png"/>" class="avatar" style="width:100px;height:100px;" >
														</th>
															<td><c:out value="${item.name}" /></td>
														</tr>
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
														<td><c:out value="${item.fechaPublicacion}" /></td>
														</tr>
														<tr>
														<th><spring:message code="item.num_visitas" /></th>
														<td><c:out value="${item.numeroVisitas}" /></td>
														</tr>
														<tr>
														<th><spring:message code="item.fecha_cad_form_label" /></th>
														<td><c:out value="${item.fechaCaducidad}" /></td>
														</tr>
														<tr>
														<th><spring:message code="item.state_form_label" />

														</th>
														<c:if test="${item.estado == 1}">
														<td><i class=" material-icons">check_circle</i></td>
														</c:if>
														<c:if test="${item.estado == 0}">
														<td><i class=" material-icons">do_not_disturb_on</i></td>
														</c:if>
														</tr>
														<tr>
														<th><spring:message code="item.seller_form_label" /></th>
														<td><c:out value="${seller.username}" /> </td>
														</tr>
														<tr>
														<th><spring:message code="user.rate" /></th>
														<td>
																<c:forEach var="i" begin="1" end="5" step="1">
																	<c:if test="${rateo >= i}">
																		<label class="clasificacionOrange">&#9733;</label>
				         											</c:if>
				         											<c:if test="${rateo < i}">
				         												<label class="clasificacionGrey">&#9733;</label>
				      												</c:if>
				      											</c:forEach>
				      									</td>
				      									</tr>
				      									<sec:authorize access="isAuthenticated()">
														<sec:authorize access="hasRole('ROLE_USER')">
															<tr>
															<th>
															<c:if test="${propietario ==  false}">
																<c:if test="${item.estado == 1}">
																<c:choose>
									    							<c:when test="${contactado == null}">
									    							<c:url value="/items/contactar/${item.itemId}" var="post1" />
									    							<form  name="contactar" id="contactar" method="post" action="${post1}">
									    								<input type="submit"  class="btn btn-primary" id="contact" name="Contactar" value="<spring:message code="item.contactar_label" />" />
									        						</form>
									   								 </c:when>
									    							<c:otherwise>
									    							<c:url value="/items/contactado" var="post2" />
									    							<form  name="contactar" id="contactar" method="post" action="${post2}">
									    							<input type="submit"   onclick="contactFunction(this.form)" class="btn btn-info" id="detalle_contactado" name="Contactado" value="<spring:message code="item.contacto_label" />" /><br>
									    							</form>
									    							</c:otherwise>
																</c:choose>
																</c:if>
															</c:if>
															</th>
															<td>
															 <c:out value="${contactado.fechaContacto}" />
															</td>
															</tr>
														</sec:authorize>
														</sec:authorize>
													</tbody>
												</table>
											</div>
										</div>
										<div class="col s6 text-center">
													
										<!-- <c:if test="${item.urlImg != null && item.urlImg != ''}">
													
											<img id="myImg" class="z-depth-3" alt="Imagen del producto ${item.name}" src="<c:url value="data:image/jpg;base64,${item.urlImg}"/>"   width="400" height="300" >
														
										</c:if>	-->
										 <c:if test="${fn:length(imagesItem) > 0}">
											<div class="slider" style="border-radius: 15px;">
											    <ul class="slides" style="border-radius: 15px;">
											    <c:forEach items="${imagesItem}" var="image64" >
											      <li style="border-radius: 15px;">
											       <img class="z-depth-3g" alt="Imagen del producto ${item.name}" src="<c:url value="data:image/jpg;base64,${image64}"/>"   width="400" height="300" >
											       
											      </li>
											      
											      </c:forEach>
											    </ul>
											    <br>
											    <br>
											    <c:if test="${propietario ==  true and item.estado == 1}">
														<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.3.5/sweetalert2.all.min.js"></script>
														 <c:url value="/items/${item.itemId}/delitemimages" var="postPathDeleteImage" />
														<form  name="formDeleteImage" id="formDeleteImage" method="post" action="${postPathDeleteImage}">
			        										<input type="submit" onclick="confirmImageDeleteFunction(this.form)" class="btn btn-warning" id="baja" name="Baja" value="<spring:message
																			code="item.del_img_form_label" />" /><br>
			     										</form>
												</c:if>
								  		  </div>
								  		  </c:if>
												
				      					</div>
										</div>
										<sec:authorize access="isAuthenticated()">
											<div class="col-lg-12 text-center">
												<sec:authorize access="hasRole('ROLE_USER')">
												<c:if test="${propietario ==  true and item.estado == 1}">
													<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.3.5/sweetalert2.all.min.js"></script>
													 <c:url value="/items/${item.itemId}/delete" var="postPathDelete" />
													<form  name="formDelete" id="formDelete" method="post" action="${postPathDelete}">
		        										<input type="submit" onclick="confirmFunction(this.form)" class="btn btn-danger" id="delete" name="Eliminar" value="<spring:message
																		code="item.delete_form_label" />" /><br>
		     										</form>
												</c:if>
												<c:if test="${propietario ==  true and item.estado == 1}">
													<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.3.5/sweetalert2.all.min.js"></script>
													 <c:url value="/items/${item.itemId}/confirmarbaja" var="postPathBaja" />
													<form  name="formDelete" id="formDelete" method="post" action="${postPathBaja}">
		        										<input type="submit" onclick="confirmBajaFunction(this.form)" class="btn btn-warning" id="baja" name="Baja" value="<spring:message
																		code="item.baja_form_label" />" /><br>
		     										</form>
												</c:if>
												</sec:authorize>
												<sec:authorize access="hasRole('ROLE_ADMIN')">
													<c:if test="${item.estado == 1}">
														<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.3.5/sweetalert2.all.min.js"></script>
															 <c:url value="/items/${item.itemId}/delete" var="postPathDelete" />
															<form  name="formDelete" id="formDelete" method="post" action="${postPathDelete}">
				        										<input type="submit" onclick="confirmFunction(this.form)" class="btn btn-danger" id="delete" name="Eliminar" value="<spring:message
																				code="item.delete_form_label" />" /><br>
				     										</form>
			     									</c:if>
		     									</sec:authorize>
											</div>
										</sec:authorize>
										</div>
										
											
										<p>
											<jsp:include page="/WEB-INF/jsp/mapa.jsp" />
										</p>
									</div>
								</div>
							</div>
	
						</div>
					</div>
				</div>
				
		</div>
			</div>
			<!-- The Modal Image -->
			
	<!-- end page content -->
		<script type="text/javascript">
		//Inicializar ubicacion mapa.jsp
		findUbicacionSeller("seller");
	</script>

	<!-- start js include path -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.3.5/sweetalert2.all.min.js"></script>
	<!-- bootstrap -->
	<script src="<c:url value="/resources/js/materialize.min.js"/>"></script>
	<!-- Material -->
	<script src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
	

	<!-- The Modal -->
	<script>
		// Get the modal
		var modal = document.getElementById('myModal');
		
		// Get the image and insert it inside the modal - use its "alt" text as a caption
		var img = document.getElementById('myImg');
		var modalImg = document.getElementById("img01");
		var captionText = document.getElementById("caption2");
		img.onclick = function(){
		  modal.style.display = "block";
		  modalImg.src = this.src;
		  captionText.innerHTML = this.alt;
		}
		
		// Get the <span> element that closes the modal
		var span = document.getElementsByClassName("close2")[0];
		
		// When the user clicks on <span> (x), close the modal
		span.onclick = function() {
		  modal.style.display = "none";
		}
	</script>
	<!-- end js include path -->
</body>
</html>
