<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import = "java.io.*,java.util.*" %>
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

<link rel="stylesheet"
	href="<c:url value="/resources/material-design-lite/material.min.css"/>" />
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

      <link href="<c:url value="/resources/css/estrellas_grises.css"/>"
      	rel="stylesheet" type="text/css">
      <link href="<c:url value="/resources/css/estrellas_naranjas.css"/>"
      	rel="stylesheet" type="text/css">
</head>
<body class="page-container page-header-fixed page-content-white page-md header-white logo-dark" >

    <!-- start header -->
    <jsp:include page="/WEB-INF/jsp/encabezadopagina.jsp"/>
    <!-- end header -->

     <jsp:include page="/WEB-INF/jsp/piepagina.jsp" />

    <!-- start page container -->
    <div class="page-container">
    	<jsp:include page="/WEB-INF/jsp/barralateral.jsp"/>
        <!-- start page content -->
        <div class="page-content-wrapper">
            <div class="page-content">
                <div class="row">
                    <div class="col-sm-12">
	                    <c:if test="${listaSize == 0}">
			                <div class="card-box">
		                            <div class="card-head">
		                                <header>
		                                		<spring:message code="hist.sin_compras"/>
		                                		<sec:authorize access="hasRole('ROLE_ADMIN')">
				                                	<a href="<c:url value="/admin/${user_index.userId}"/>"class="btn btn-default rebeccapurple-color ">
				                                	<spring:message code="button_atras"/></a><br>
					                   			</sec:authorize>
		                                </header>
		                            </div>
			                  </div>
			                  <div class="card-body">
		                                <div class="col-lg-15 p-t-20">
		                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
		                                    </div>
		                                </div>
		                     </div>
		                  </c:if>
                    <c:forEach items="${items}" var="item">
                        <div class="card-box">
                            <div class="card-head">
                                <header>
                                	<img src="<c:url value="/resources/img/type_food/${item.tipo}.png"/>" class="avatar" style="width:50px;height:50px;" >
                                	<spring:message code="item.item_comprado" /> ${item.name}
                                	<sec:authorize access="hasRole('ROLE_ADMIN')">
	                                	<a href="<c:url value="/admin/${user_index.userId}"/>"class="btn btn-default rebeccapurple-color ">
	                                	<spring:message code="button_atras"/></a><br>
		                   			</sec:authorize>
                                </header>
                            </div>
                            <div class="card-body">
                                <div class="col-lg-15 p-t-20">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

                                        <div class="table-responsive">
                                            <table class="table table-striped custom-table">
                                                <thead class="text-left">
                                                <tr>
                                                	<th><spring:message code="item.vendedor"/></th>
                                                	<th><spring:message code="item.price_form_label"/></th>
                                                	<th><spring:message code="hist.valoracion"/></th>
                                                    <th><spring:message code="hist.estrellas"/></th>
                                                    <th><spring:message code="hist.fecha_compra"/></th>
                                                    <th><spring:message code="hist.ver_anuncio"/></th>
                                                    <sec:authorize access="hasRole('ROLE_USER')">
                                                      <th><spring:message code="hist.valorar"/></th>
                                                    </sec:authorize>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${lista}" var="hist">
                                                <c:if test="${hist.itemId == item.itemId}">
                                                    <tr>
                                                    	 <c:if test="${hist.idComprador < 0}">
                                                    	 	<td><spring:message code="hist.vendido_fuera"/></td>
                                                    	 </c:if>
                                                    	 <c:if test="${hist.idComprador >= 0}">
                                                    	 	<% int second = 0;%>
                                                    	 	<c:forEach items="${vendedores}" var="vendedor">
                                                    	 		<c:if test="${hist.idVendedor == vendedor.userId and hist.itemId == item.itemId and item.idVendedor == hist.idVendedor and second != 1}">
                                                    				<%if(second == 0){%>
                                                    					<td><c:out value="${vendedor.username}"/></td>

                                                            <% second = 1;}%>
                                                    			</c:if>
                                                    		</c:forEach>
                                                    	</c:if>
                                                    	<td><c:out value="${item.price}"/></td>
														                             <c:if test="${hist.valoracion == ''}">
                                                        	<td><spring:message code="hist.sin_valoracion"/></td>
                                                         </c:if>
                                                         <c:if test="${hist.valoracion != ''}">
                                                        	<td><c:out value="${hist.valoracion}"/></td>
                                                         </c:if>
                                                         <td>
                             															<a>
                             																<c:forEach var="i" begin="1" end="5" step="1">
                             																	<c:if test="${hist.estrellas >= i}">
                             																		<label class="clasificacionOrange">&#9733;</label>
                             				         											</c:if>
                             				         											<c:if test="${hist.estrellas < i}">
                             				         												<label class="clasificacionGrey">&#9733;</label>
                             				      												</c:if>
                             				      											</c:forEach>
                             				      										</a>
                                                        </td>
                                                        <td><c:out value="${hist.fechaCompra}"/></td>

                                                         <td>
													                                <div class="col-lg-12 text-center">
	                                        				<a href="<c:url value="/items/${hist.itemId}"/>"
	                                           				class="btn btn-default rebeccapurple-color"><spring:message
	                                               				 code="item.view"/></a><br>
	                                    					</div>
	                                    					 </td>
                                                 <sec:authorize access="hasRole('ROLE_USER')">
                                                 <td>
                                                   <c:if test="${hist.estrellas < 0}">
                                                       <div class="col-lg-12 text-center">
                                                                       <a href="<c:url value="/items/${hist.itemId}/ratehistoric/"/>"
                                                                         class="btn btn-default rebeccapurple-color"><spring:message
                                                                              code="hist.valorar"/></a><br>
                                                                   </div>
                                                                 </c:if>
                                                                 <c:if test="${hist.estrellas >= 0}">
                                                                   <spring:message code="hist.valoracionOK"/>
                                                                 </c:if>
                                                </td>
                                               </sec:authorize>
                                                    </tr>
                                                    </c:if>
                                              </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </c:forEach>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <!-- Common js-->
    <script src="<c:url value="/resources/js/app.js"/>"></script>
    <script src="<c:url value="/resources/js/layout.js"/>"></script>
    <!-- Material -->
    <script
		src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
    <!-- end js include path -->
</body>
</html>
