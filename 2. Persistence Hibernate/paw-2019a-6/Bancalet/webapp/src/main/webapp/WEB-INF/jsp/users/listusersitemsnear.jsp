<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<!-- google font -->
	<link href="<c:url value=" https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700 "/>" rel="stylesheet" type="text/css"
	/>
	<!-- icons -->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<!-- free maps -->
	<link rel="stylesheet" href="https://unpkg.com/leaflet@1.5.1/dist/leaflet.css" />
	<script src="https://unpkg.com/leaflet@1.5.1/dist/leaflet.js"></script>

	<!--Import materialize.css-->
<link type="text/css"  rel="stylesheet" href="<c:url value="/resources/css/nouislider.css"/>"  media="screen,projection"/>
	<link rel="stylesheet" href="<c:url value="/resources/css/materialize.min.css "/>" media="screen,projection" />
	
	<!-- Template Styles -->
	<link href="<c:url value="/resources/css/style.css "/>" rel="stylesheet" type="text/css" />
	<link href="<c:url value="/resources/css/plugins.min.css "/>" rel="stylesheet" type="text/css" />
	<link href="<c:url value="/resources/css/responsive.css "/>" rel="stylesheet" type="text/css">
	<link href="<c:url value="/resources/css/bancalet/bancalet.css "/>" rel="stylesheet" type="text/css">
 	<script src="<c:url value="/resources/js/nouislider.js"/>"></script>
	<!-- Favicon -->
	<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/img/bancalet/appicon.ico "/>" />
	<!--  Estrellas -->

<link href="<c:url value="/resources/css/estrellas_grises.css"/>" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/estrellas_naranjas.css"/>" rel="stylesheet" type="text/css" />
<!-- Material Design Lite CSS -->
<link rel="stylesheet" href="<c:url value="/resources/material-design-lite/material.min.css"/>" />
	<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
	<script src="https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<script type="text/javascript">
			$(document).ready(function () {
				$('.collapsible').collapsible();
			});
		</script>
	</sec:authorize>
</head>

<body class="page-container page-header-fixed page-content-white page-md header-white logo-dark">
	<!-- start header -->
	<jsp:include page="/WEB-INF/jsp/encabezadopagina.jsp" />
	<!-- end header -->

	<!-- start page container -->
	<div class="page-container">
		<jsp:include page="/WEB-INF/jsp/barralateral.jsp" />

		<!-- start page content -->
		<div class="page-content-wrapper">
			<div class="page-content">
				<p class="range-field">

					<div class="row">
						<div class="card">
						<div class="row">
						<div class="card-heard">
						<div class="col s12" style="margin:10px;margin-bottom:30px"><spring:message  code="user.label_filtro_cercano" arguments="${rangeKm} Km" /></div>
						</div>
						 <form action="">
							<div class="card-body">
										<div class="col s2">
											<i class="material-icons">place</i>  <spring:message code="user.label_filtro_cercano_medida" arguments="" />
										</div>
								
										<div class="col s10">
											<input id="rangeKm" name="rangeKmSlider" type="hidden" value="" />
											<div id = "slider-rangeKm"></div>
											
										</div>
										<p>
								</div>
								<p>
								<div class="card-body">
								<button type="submit" class="btn"><i class="small material-icons">search</i>
	     							<span class="title" style="align:center;"><b><spring:message code="user.label_vendedores_cercanos" arguments=" " /></b></span></button>
									</div>
							</form>
							</div>
							
							</div>
						</div>
						<div class="col-sm-12">
							<c:forEach items="${users}" var="user_index" >
							<c:if test="${user_index.userId != user.userId}">
								<div class="card-box">
									<div class="card-head">
										<header>
											<c:if test="${user_index.urlImg != null && user_index.urlImg != ''}">
												<img src="<c:url value="data:image/jpg;base64,${user_index.urlImg} "/>" alt="Avatar" class="avatar-userimg">
											</c:if>
											<c:if test="${user_index.urlImg == null || user_index.urlImg == '' }">
											<c:if test="${user_index.role == 'ADMIN' }">
												<img src="<c:url value="/resources/img/admin/admin${user_index.numImg}.png "/>" class="avatar" style="width:80px;height:80px;">
											</c:if>
											<c:if test="${user_index.role == 'USER' }">
												<img src="<c:url value="/resources/img/avatar/avatar${user_index.numImg}.png "/>" class="avatar" style="width:80px;height:80px;">
											</c:if>
											</c:if>
											${user_index.username}
											
										<p><br><b><spring:message code="user.city" arguments="" /> ${user_index.city}</b></p>
										</header>
									</div>
									<div class="card-body">
										<ul class="collapsible">
										<li>
												<div class="collapsible-header">
													<i class="material-icons">perm_identity</i><spring:message code="user.label_informacion" arguments=" " /> <spring:message code="item.seller_form_label" /></div>
												<div class="collapsible-body">
													<h4><b><spring:message code="user.rate" /></b>
															<a>
																<c:forEach var="i" begin="1" end="5" step="1">
																	<c:if test="${user_index.rate >= i}">
																		<label class="clasificacionOrange">&#9733;</label>
				         											</c:if>
				         											<c:if test="${user_index.rate < i}">
				         												<label class="clasificacionGrey">&#9733;</label>
				      												</c:if>
				      											</c:forEach>
				      										</a>
													</h4><p>
														<h4><b><spring:message code="user.email" arguments=": " />
															</b>${user_index.email}
													</h4><p>
													<h4><b><spring:message code="user.telf" arguments=": " />
															</b>${user_index.telf}
													</h4><p>
													<h4><b><spring:message code="user.address"  arguments=": "/></b> ${user_index.direccion}
													</h4><p>

												</div>
											</li>
											<li>
												<div class="collapsible-header" style="width=100%">
													<i class="material-icons">filter_drama</i><spring:message code="user.label_lista_items" arguments=" " /> <span class="new badge" data-badge-caption="En venta">${fn:length(treeMap[user_index.userId])}</span></div>
										<div class="collapsible-body">
											<ul class="collection">
													<c:forEach items="${treeMap}" var="entry">
													<c:if test="${user_index.userId == entry.key}">
														<c:forEach var="item" items="${entry.value}">
														<li class="collection-item avatar">
																<img src="<c:url value="/resources/img/type_food/${item.tipo}.png"/>" class="circle"  />
															<span class="title"><b>${item.name}</b></span>
															<p><b><spring:message code="item.fecha_cad_form_label" arguments=": "/></b><c:out value="${item.fechaCaducidad}" />
																<br><b><spring:message code="item.description_form_label" arguments=": "/></b>${item.description}
																<br><b><spring:message code="item.price_form_label"  arguments=": "/></b> ${item.price}

															</p>
															<a href="../items/${item.itemId}" class="secondary-content">
																<i class="material-icons">add_circle</i>
															</a>
														</li>
														</c:forEach>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</li>
										</ul>
									</div>
									</div>
									</c:if>
								</c:forEach>
								</div>
						</div>
					</div>
			</div>	
<script src="<c:url value="/resources/js/wNumb.js"/>"></script>
	<!-- end footer -->
	<script type="text/javascript" src="<c:url value="/resources/js/materialize.min.js "/>"></script>
	<!-- start js include path -->
	<!-- bootstrap -->
	<script type="text/javascript">
		$(document).ready(function () {
			$('.collapsible').collapsible();
		});

	</script>
	<script>
  	 var maxim = "${rangeKm}";
     var slider = document.getElementById('slider-rangeKm');

    noUiSlider.create(slider, {
	   start: ["${rangeKm}"],
	   connect: [true, false],
	   step: 1,
	   orientation: 'horizontal',
	   range: {
	     'min': 0,
	     'max': 3185// mitad Radio tierra en Km
	   },
	   pips: {mode: 'count', values: 10},
	     behaviour: 'tap-drag',
	     tooltips: true,
	     format: wNumb({
	         decimals: 0
	     }),
	
	 });
	    
	 slider.noUiSlider.on('update', function (values, handle) {
	    document.getElementById("rangeKm").value=values[0];
	    console.log(document.getElementById("rangeKm").value)
	
	});
  </script>
</body>
</html>
